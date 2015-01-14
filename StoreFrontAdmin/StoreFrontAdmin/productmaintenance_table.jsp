<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    Theme theme = null;
    try {
        CompanyBean companyBean = new CompanyBean();
        GetThemeRequest getThemeRequest = new GetThemeRequest();
        getThemeRequest.setname("corporate");
        GetThemeResponse getThemeResponse = companyBean.GetTheme(request, getThemeRequest);
        theme = getThemeResponse.gettheme();
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>
<%
    DecimalFormat decimalFormat = new DecimalFormat("###.00");

    ListsBean lists = new ListsBean();

    // If we are updating, get the item
    GetItemResponse getItemResponse = null;
    boolean updating = false;
    boolean details = false;
    int nextItem = 0;
    int previousItem = 0;
    if(request.getParameter("id") != null)
    {
        ItemBean item = new ItemBean();
        GetItemRequest getItemRequest = new GetItemRequest();
        getItemRequest.setid(new Integer(request.getParameter("id")).intValue());
        getItemResponse = item.GetItem(getItemRequest);
        updating = true;

        if(getItemResponse.getitem().getdetails() != null)
            details = true;

        nextItem = item.GetNext(new Integer(request.getParameter("id")).intValue(), new Integer(request.getParameter("manufacturer")).intValue());
        previousItem = item.GetPrev(new Integer(request.getParameter("id")).intValue(), new Integer(request.getParameter("manufacturer")).intValue());
    }
%>
    <%@ include file="commonscript.jsp" %>
    <SCRIPT LANGUAGE="JavaScript">
        function OnClickValidate()
        {
            if(document.form1.manufacturer.value == 0)
            {
                alert("A manufacturer must be selected!");
                document.form1.manufacturer.focus();
                return;
            }

            if(document.form1.manufactureritemnumber.value.length == 0)
            {
                alert("Invalid Manufacturer Item #!");
                document.form1.manufactureritemnumber.focus();
                return;
            }

            if(document.form1.productname.value.length == 0)
            {
                alert("Invalid Product Name!");
                document.form1.productname.focus();
                return;
            }

            if(document.form1.listprice.value.length == 0)
            {
                alert("Invalid List Price!");
                document.form1.listprice.focus();
                return;
            }

            if(document.form1.ourcost.value.length == 0)
            {
                alert("Invalid Our Cost!");
                document.form1.ourcost.focus();
                return;
            }

            if(document.form1.ourprice.value.length == 0)
            {
                alert("Invalid Our Price!");
                document.form1.ourprice.focus();
                return;
            }

            if(document.form1.shippingweight.value.length == 0)
            {
                alert("Invalid Shipping Weight!");
                document.form1.shippingweight.focus();
                return;
            }

            if(document.form1.distributor.value == 0)
            {
                alert("A distributor must be selected!");
                document.form1.distributor.focus();
                return;
            }

            if(document.form1.handlingfee.value.length == 0)
            {
                alert("Invalid Handling Fee!");
                document.form1.handlingfee.focus();
                return;
            }

            if(document.form1.description.value.length == 0)
            {
                alert("Invalid Description!");
                document.form1.description.focus();
                return;
            }

            if(document.form1.status.value == 0)
            {
                alert("A status must be selected!");
                document.form1.status.focus();
                return;
            }

            document.form1.submit();
        }

        <%
        if(updating)
        {
        %>
            function OnClickDelete()
            {
                var answer = confirm("Delete this product?");
                if(answer)
                {
                    window.location = "./productdelete_result.jsp?id=<%=request.getParameter("id")%>&productname=<%=getItemResponse.getitem().getproductname()%>";
                }
            }
        <%
        }
        %>

        function OnClickCancel()
        {
            window.location = "./products.jsp";
        }

        function OnChangeManufacturerItemNumber()
        {
        <%
            GetManufacturersRequest getManufacturersRequest = new GetManufacturersRequest();
            GetManufacturersResponse getManufacturersResponse = lists.GetManufacturers(getManufacturersRequest);
            Iterator it = getManufacturersResponse.getmanufacturersIterator();
            Manufacturer manufacturer = null;
            while(it.hasNext())
            {
                manufacturer = (Manufacturer)it.next();
        %>
                if(document.form1.manufacturer.value == <%=new Integer(manufacturer.getid()).toString()%>)
                    document.form1.inventoryid.value = "<%=manufacturer.getprefix()%>" + "-" + document.form1.manufactureritemnumber.value.trim();
        <%
            }
        %>
            var url = document.form1.inventoryid.value.trim() + "small.gif";
            document.form1.smallimageurl.value = url;
            url = document.form1.inventoryid.value.trim() + "medium.gif";
            document.form1.mediumimageurl.value = url;
            url = document.form1.inventoryid.value.trim() + "large.gif";
            document.form1.largeimageurl.value = url;
        }
    </SCRIPT>
                    <TABLE cellSpacing="1" cellPadding="3" width="95%" border="0">
                    <input type="hidden" name="smallimageurl" value="<%=(updating==true&&details==true)?getItemResponse.getitem().getdetails().getimageUrlSmall():""%>"/>
                    <input type="hidden" name="mediumimageurl" value="<%=(updating==true&&details==true)?getItemResponse.getitem().getdetails().getimageUrlMedium():""%>"/>
                    <input type="hidden" name="largeimageurl" value="<%=(updating==true&&details==true)?getItemResponse.getitem().getdetails().getimageUrlLarge():""%>"/>
                    <%
                    if(updating)
                    {
                    %>
                        <input name="id" type="hidden" value="<%=new Integer(getItemResponse.getitem().getid()).toString()%>"/>
                        <tr>
                            <td>
                        <%
                            if(previousItem > 0)
                            {
                        %>
                                <a href="./productupdate.jsp?id=<%=new Integer(previousItem).toString()%>&manufacturer=<%=request.getParameter("manufacturer")%>"><< previous</a>
                        <%
                            }
                        %>
                            </td>
                            <td align="right">
                        <%
                            if(nextItem > 0)
                            {
                        %>
                                <a href="./productupdate.jsp?id=<%=new Integer(nextItem).toString()%>&manufacturer=<%=request.getParameter("manufacturer")%>">next >></a>
                        <%
                            }
                        %>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2"><a href="<%=theme.getimagebaseurl()%><%=getItemResponse.getitem().getdetails().getimageUrlLarge()%>" target="_blank"><img border="0" src="<%=theme.getimagebaseurl()%><%=getItemResponse.getitem().getdetails().getimageUrlSmall()%>" /></a></td>
                        </tr>
                    <%
                    }
                    %>
                    <tr>
                        <td>Manufacturer:</td>
                        <td>
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td>
                                        <select name="manufacturer" onchange="OnChangeManufacturerItemNumber()">
                                            <option value="0">< select ></option>
                                            <%
	                                        getManufacturersResponse = lists.GetManufacturers(new GetManufacturersRequest());
                                                Iterator itManufacturer = getManufacturersResponse.getmanufacturersIterator();
                                                manufacturer = null;
                                                while(itManufacturer.hasNext())
                                                {
                                                    manufacturer = (Manufacturer)itManufacturer.next();
                                            %>
                                                    <option value="<%=manufacturer.getid()%>"><%=manufacturer.getname()%></option>
                                            <%
                                                }
                                            %>
                                        </select>
                                    </td>
                                    <td noWrap>&nbsp;&nbsp;Item #:</td>
                                    <td><input name="manufactureritemnumber" type="text" size="20" value="<%=(updating==true&&details==true)?getItemResponse.getitem().getdetails().getmanufactureritemnumber():""%>" onchange="OnChangeManufacturerItemNumber()" /></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td noWrap>Inventory ID:</td>
                        <td><input readOnly name="inventoryid" type="text" size="10" value="<%=(updating==true)?getItemResponse.getitem().getisin():""%>" /></td>
                    </tr>
                    <tr>
                        <td noWrap>Product Name:</td>
                        <td><input name="productname" type="text" size="40" value="<%=(updating==true)?getItemResponse.getitem().getproductname():""%>"/></td>
                    </tr>
                    <tr>
                        <td>Prices:</td>
                        <td>
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td nowrap>List Price</td>
                                    <td></td>
                                    <td nowrap>Our Cost</td>
                                    <td></td>
                                    <td nowrap>Our Price</td>
                                </tr>
                                <tr>
                                    <td><input name="listprice" type="text" size="10" value="<%=(updating==true)?decimalFormat.format(getItemResponse.getitem().getlistprice()):""%>"/></td>
                                    <td>&nbsp;</td>
                                    <td><input name="ourcost" type="text" size="10" value="<%=(updating==true)?decimalFormat.format(getItemResponse.getitem().getourcost()):""%>"/></td>
                                    <td>&nbsp;</td>
                                    <td><input name="ourprice" type="text" size="10" value="<%=(updating==true)?decimalFormat.format(getItemResponse.getitem().getourprice()):""%>"/></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td noWrap>Shipping Weight (lbs):</td>
                        <td><input name="shippingweight" type="text" size="10" value="<%=(updating==true&&details==true)?decimalFormat.format(getItemResponse.getitem().getdetails().getshippingweight()):""%>"/></td>
                    </tr>
                    <%
                        GetGroupsResponse getGroupsResponse = lists.GetGroups(new GetGroupsRequest());
                        Iterator itGroups = getGroupsResponse.getgroupsIterator();
                        Group group = null;
                        while(itGroups.hasNext())
                        {
                            group = (Group)itGroups.next();
                    %>
                    <tr>
                        <td valign="top" noWrap><%=group.getname()%>:</td>
                        <td>
                        <table border="0" cellpadding="1" cellspacing="1">
                            <tr>
                        <%
                            GetCategoriesRequest getCategoriesRequest = new GetCategoriesRequest();
                            getCategoriesRequest.setactive(true);
                            getCategoriesRequest.setgroup(group.getid());
                            GetCategoriesResponse getCategoriesResponse = lists.GetCategories(getCategoriesRequest);
                            Iterator itCategories = getCategoriesResponse.getcategoriesIterator();
                            Category category = null;
                            for(int i=0 ; itCategories.hasNext() ; i++)
                            {
                                category = (Category)itCategories.next();

                                // If we're updating, check if the item has the current category
                                boolean checked = false;
                                if(updating)
                                {
                                    Iterator itItemCategories = getItemResponse.getitem().getdetails().getcategoriesIterator();
                                    while(itItemCategories.hasNext())
                                    {
                                        Category itemCategory = (Category)itItemCategories.next();
                                        if(category.getid() == itemCategory.getid())
                                        {
                                            checked = true;
                                            break;
                                        }
                                    }
                                }

                                if(i > 6)
                                {
                                    i = 0;
                        %>
                            </tr>
                            <tr>
                        <%
                                }
                        %>
                                <td noWrap>
                                    <input type="checkbox" name="category<%=new Integer(category.getid()).toString()%>" <%=checked==true?"checked=true":""%>><%=category.getname()%>
                                </td>
			<%
                            }
                        %>
                            </tr>
                        </table>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                    <tr>
                        <td noWrap>Distributor:</td>
                        <td>
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td>
                                        <select name="distributor">
                                           <option value="0">< select ></option>
                                            <%
                                                GetDistributorsResponse getDistributorsResponse = lists.GetDistributors(new GetDistributorsRequest());
                                                Iterator itDistributor = getDistributorsResponse.getdistributorsIterator();
                                                Distributor distributor = null;
                                                while(itDistributor.hasNext())
                                                {
                                                    distributor = (Distributor)itDistributor.next();
                                            %>
                                                    <option value="<%=distributor.getid()%>"><%=distributor.getname()%></option>
                                            <%
                                                }
                                            %>
                                        </select>
                                    </td>
                                    <td>&nbsp;</td>
                                    <td>Item #:</td>
                                    <td><input name="distributoritemnumber" type="text" size="10" value="<%=(updating==true&&getItemResponse.getitem().getdetails().getdistributoritemnumber()!=null)?getItemResponse.getitem().getdetails().getdistributoritemnumber():""%>"/></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td noWrap>Handling Fee:</td>
                        <td><input name="handlingfee" type="text" size="5" value="<%=(updating==true)?decimalFormat.format(getItemResponse.getitem().getdetails().gethandlingcharges()):""%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap valign="top">Description:</td>
                        <td><textarea name="description" rows="5" cols="130"><%=(updating==true&&details==true)?getItemResponse.getitem().getdetails().getdescription():""%></textarea></td>
                    </tr>
                    </tr>
                        <td valign="top" noWrap>Specifications:</td>
                        <td>
                        <table border="0">
                            <tr>
                            <%
                                GetSpecificationsResponse getSpecificationsResponse = lists.GetSpecifications(new GetSpecificationsRequest());
                                Iterator itSpecs = getSpecificationsResponse.getspecificationsIterator();
                                Specification specification = null;
                                for(int i=0 ; itSpecs.hasNext() ; i++)
                                {
                                    specification = (Specification)itSpecs.next();

                                    // if updating, find the matching item specification
                                    String specificationValue = null;
                                    if(updating)
                                    {
                                        Iterator itItemSpecs = getItemResponse.getitem().getdetails().getspecificationsIterator();
                                        while(itItemSpecs.hasNext())
                                        {
                                            ItemSpecification itemSpecification = (ItemSpecification)itItemSpecs.next();
                                            if(itemSpecification.getspecid() == specification.getid())
                                            {
                                                specificationValue = itemSpecification.getdescription();
                                                break;
                                            }
                                        }
                                    }

                                    if(i > 2)
                                    {
                                        i = 0;
                            %>
                                    </tr>
                                    <tr>
                            <%
                                    }
                            %>
                                    <td>
                            <%
                                if(i != 0)
                                {
                            %>
                                      &nbsp;&nbsp;
                            <%
                                }
                            %>
                                    </td>
                                    <td align="right" noWrap><%=specification.getname()%>:</td>
                                    <td><input name="specification<%=new Integer(specification.getid()).toString()%>" type="text" size="20" value="<%=(updating==true&&specificationValue!=null)?specificationValue:""%>"/></td>
                            <%
                                }
                            %>
                            </tr>
                        </table>
			</td>
                    </tr>
                    <tr>
                        <td noWrap>Status:</td>
                        <td>
                            <select name="status">
                                <option value="0">< select ></option>
                                <%
                                    GetItemStatusesResponse getItemStatusesResponse = lists.GetItemStatuses(new GetItemStatusesRequest());

                                    Iterator itItemStatuses = getItemStatusesResponse.getstatusesIterator();
                                    ItemStatus itemstatus = null;
                                    while(itItemStatuses.hasNext())
                                    {
                                        itemstatus = (ItemStatus)itItemStatuses.next();
                                %>
                                        <option value="<%=itemstatus.getid()%>"><%=itemstatus.getstatus()%></option>
                                <%
                                    }
                                %>
                            </select>
                        </td>
                    </tr>
                    </table>
    <SCRIPT LANGUAGE="JavaScript">
    <%
        if(updating)
        {
    %>
        document.form1.manufacturer.value = '<%=new Integer(getItemResponse.getitem().getmanufacturerid()).toString()%>';
        document.form1.distributor.value = '<%=new Integer(getItemResponse.getitem().getdistributorid()).toString()%>';
        document.form1.status.value = '<%=new Integer(getItemResponse.getitem().getstatusid()).toString()%>';
    <%
        }
    %>
    </SCRIPT>
