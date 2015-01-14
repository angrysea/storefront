<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    Theme theme = null;
    FeaturedProducts featuredProducts = null;
    FeaturedProductsBean featuredProductsBean = new FeaturedProductsBean();
    ListsBean listsBean = new ListsBean();
    ItemBean itemBean = new ItemBean();
    try {
        CompanyBean companyBean = new CompanyBean();
        GetThemeRequest getThemeRequest = new GetThemeRequest();
        getThemeRequest.setname("corporate");
        GetThemeResponse getThemeResponse = companyBean.GetTheme(request, getThemeRequest);
        theme = getThemeResponse.gettheme();

        // Adding an item?
        if(request.getParameter("additem") != null)
        {
            featuredProductsBean.AddFeaturedItem(new Integer(request.getParameter("id")).intValue(), new Integer(request.getParameter("additem")).intValue());
        }

        // Deleting an item?
        if(request.getParameter("deleteitem") != null)
        {
            featuredProductsBean.DeleteFeaturedItem(new Integer(request.getParameter("id")).intValue(), new Integer(request.getParameter("deleteitem")).intValue());
        }

        // Get the featured product to update
        GetFeaturedProductsRequest getFeaturedProductsRequest = new GetFeaturedProductsRequest();
        getFeaturedProductsRequest.setid(request.getParameter("id"));
        GetFeaturedProductsResponse getFeaturedProductsResponse = featuredProductsBean.GetFeaturedProducts(getFeaturedProductsRequest);
        Iterator itFeaturedProducts = getFeaturedProductsResponse.getfeaturedproductsIterator();
        featuredProducts = (FeaturedProducts)itFeaturedProducts.next();

        // Updating the header
        if(request.getParameter("heading") != null)
        {
            UpdateFeaturedProductRequest updateFeaturedProductRequest = new UpdateFeaturedProductRequest();
            featuredProducts.setcompanyid(new Integer(request.getParameter("company")).intValue());
            featuredProducts.setheading(request.getParameter("heading"));
            featuredProducts.setcomments(request.getParameter("description"));
            featuredProducts.setsortorder(new Integer(request.getParameter("sortorder")).intValue());
            if(request.getParameter("active") != null && request.getParameter("active").compareToIgnoreCase("on") == 0)
                featuredProducts.setactive(true);
            else
                featuredProducts.setactive(false);
            updateFeaturedProductRequest.setfeaturedproducts(featuredProducts);
            featuredProductsBean.UpdateFeaturedProduct(updateFeaturedProductRequest);
        }
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>


<HTML>
    <%@ include file="commonscript.jsp" %>
    <SCRIPT LANGUAGE="JavaScript">
        function OnSubmitForm()
        {
            if(document.form1.heading.value.length == 0)
            {
                alert("A heading must be entered!");
                document.form1.heading.focus();
                return false;
            }

            if(document.form1.description.value.length == 0)
            {
                alert("A description must be entered!");
                document.form1.description.focus();
                return false;
            }

            var valid = '0123456789';
            if(document.form1.sortorder.value.length == 0 || IsValid(document.form1.sortorder.value, valid) == false)
            {
                alert("An invalid sortorder was entered.  Please enter numbers only.");
                document.form1.sortorder.focus();
                return false;
            }

            return true;
        }

        function OnClickDeleteFeaturedProduct()
        {
            var answer = confirm("Delete this featured product?");
            if(answer)
            {
                window.location = "./featuredproductdelete_result.jsp?id=<%=request.getParameter("id")%>";
            }
        }

        function OnChangeManufacturer()
        {
            document.form1.submit();
        }
    </SCRIPT>
    <HEAD>
        <LINK href="./storefront.css" rel="STYLESHEET"></LINK>
    </HEAD>
    <BODY leftMargin="0" topMargin="0" onload="" marginwidth="0" marginheight="0">
        <%@ include file="toppane.jsp" %>
        <table cellSpacing="0" cellPadding="0" border="0">
            <tr>
                <%@ include file="leftpane.jsp" %>
                <td vAlign="top" width="20">
                    <IMG src="./images/spacer.gif" border="0">
                </td>
                <td vAlign="top">
                    <table id="headingTable" border="0">
                        <tr>
                            <td><img src="./images/spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td class="producttitle">Update Featured Product</td>
                        </tr>
                    </table>
                    <br>
                    <form name="form1" action="./featuredproductupdate.jsp" method="get" onsubmit="return(OnSubmitForm())">
                    <table cellSpacing="1" cellPadding="3" width="775" border="0">
                        <tr>
                            <td noWrap>ID:</td>
                            <td><input name="id" type="text" size="5" value="<%=request.getParameter("id")%>" readonly/></td>
                        </tr>
                        <tr>
                            <td noWrap>Company:</td>
                            <td>
                                <select name="company">
                                    <%
                                        CompanyBean companyBean = new CompanyBean();
                                        GetCompaniesResponse getCompaniesResponse = companyBean.GetCompanies(new GetCompaniesRequest());
                                        Iterator itCompanies = getCompaniesResponse.getcompaniesIterator();
                                        while(itCompanies.hasNext())
                                        {
                                            Company company = (Company)itCompanies.next();
                                            if(company.getid() == featuredProducts.getcompanyid())
                                            {
                                    %>
                                                <option value="<%=new Integer(company.getid()).toString()%>" selected="selected"><%=company.getcompany()%></option>
                                    <%
                                            }
                                            else
                                            {
                                    %>
                                                <option value="<%=new Integer(company.getid()).toString()%>"><%=company.getcompany()%></option>
                                    <%
                                            }
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td noWrap>Heading:</td>
                            <td><input name="heading" type="text" size="40" value="<%=featuredProducts.getheading()%>"/></td>
                        </tr>
                        <tr>
                            <td noWrap valign="top">Description:</td>
                            <td><textarea name="description" rows="10" cols="70"><%=featuredProducts.getcomments()%></textarea></td>
                        </tr>
                        <tr>
                            <td noWrap>Sort Order:</td>
                            <td><input name="sortorder" type="text" size="5" value="<%=new Integer(featuredProducts.getsortorder()).toString()%>"/></td>
                        </tr>
                        <tr>
                            <td noWrap>Active:</td>
                    <%
                        if(featuredProducts.getactive() == true)
                        {
                    %>
                            <td><input name="active" type="checkbox" checked="checked"/></td>
                    <%
                        }
                        else
                        {
                    %>
                            <td><input name="active" type="checkbox"/></td>
                    <%
                        }
                    %>
                        </tr>
                        <br>
                    </table>
                    <table border="0">
                        <tr>
                            <td><input type="submit" value="Update Header"></td>
                            <td><input type="button" value="Delete Featured Product" onclick="OnClickDeleteFeaturedProduct()"></td>
                        </tr>
                    </table>

                    <br>
                    <table border="0">
                        <tr>
                            <td colspan="5">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <tr bgcolor="<%=theme.getcolor1()%>">
                                        <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="775" height="1" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td nowrap><b>Featured Products:</b></td>
                        </tr>
                    </table>

                    <table border="0">
                        <tr>
            <%
                    Iterator itItems = featuredProducts.getitemsIterator();
                    for(int i=0 ; itItems.hasNext() ; i++)
                    {
                        Item item = (Item)itItems.next();
            %>
                            <td align="center">
                                <table width="180" cellSpacing="0" cellPadding="0" border="0">
                                    <tr>
                                        <td>
                                            <table border="0" cellspacing="1" cellpadding="1">
                                                <tr>
                                                    <td colspan="2" align="center"><img alt="Knife - <%=item.getmanufacturer()%> <%=item.getproductname()%>" src="<%=theme.getimagebaseurl()%><%=item.getdetails().getimageUrlSmall()%>" border="0"/></td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2" align="center"><b><%=item.getmanufacturer()%> <%=item.getproductname()%></b></td>
                                                </tr>
                                                <tr>
                                            <%
                                                if(request.getParameter("manufacturer") != null)
                                                {
                                            %>
                                                    <td align="center"><a href="./featuredproductupdate.jsp?id=<%=new Integer(featuredProducts.getid()).toString()%>&deleteitem=<%=new Integer(item.getid()).toString()%>&manufacturer=<%=request.getParameter("manufacturer")%>">remove</a></td>
                                            <%
                                                }
                                                else
                                                {
                                            %>
                                                    <td align="center"><a href="./featuredproductupdate.jsp?id=<%=new Integer(featuredProducts.getid()).toString()%>&deleteitem=<%=new Integer(item.getid()).toString()%>">remove</a></td>
                                            <%
                                                }
                                            %>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
            <%
                        if(i > 2)
                        {
                            i = -1;
            %>
                        </tr>
                        <tr>
            <%
                        }
                    }
            %>
                        </tr>
                    </table>

                    <br>
                    <table>
                        <tr>
                            <td noWrap>Select a Manufacturer:</td>
                            <td>
                                <select name="manufacturer" onchange="OnChangeManufacturer()">
                                    <option value="-1">< select ></option>
                                    <%
	                                GetManufacturersResponse getManufacturersResponse = listsBean.GetManufacturers(new GetManufacturersRequest());
                                        Iterator itManufacturer = getManufacturersResponse.getmanufacturersIterator();
                                        Manufacturer manufacturer = null;
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
                        </tr>
                    </table>
                    </form>

                    <table cellSpacing="1" cellPadding="3" width="775" border="0">
                        <tr>
            <%
                            GetItemsRequest getItemsRequest = new GetItemsRequest();
                            GetItemsResponse getItemsResponse = null;
                            if(request.getParameter("manufacturer") != null)
                            {
                                getItemsRequest.setselectby("manufacturer");
                                getItemsRequest.setid(new Integer(request.getParameter("manufacturer")).intValue());
                                getItemsResponse = itemBean.GetItems(getItemsRequest);
                                itItems = getItemsResponse.getitemsIterator();

                                for(int i=0 ; itItems.hasNext() ; i++)
                                {
                                    Item item = (Item)itItems.next();

                                    // Skip this item if it is already a featured item
                                    boolean found = false;
                                    Iterator itCurrentItems = featuredProducts.getitemsIterator();
                                    while(itCurrentItems.hasNext())
                                    {
                                        Item currentItem = (Item)itCurrentItems.next();
                                        if(item.getid() == currentItem.getid())
                                        {
                                            found = true;
                                            break;
                                        }
                                    }
                                    if(found)
                                    {
                                        i--;
                                        continue;
                                    }
            %>
                        <td align="center">
                            <table width="180" cellSpacing="0" cellPadding="0" border="0">
                                <tr>
                                    <td>
                                        <table border="0" cellspacing="1" cellpadding="1">
                                            <tr>
                                                <td colspan="2" align="center"><img src="<%=theme.getimagebaseurl()%><%=item.getdetails().getimageUrlSmall()%>" border="0"/></td>
                                            </tr>
                                            <tr>
                                                <td colspan="2" align="center"><b><%=item.getmanufacturer()%> <%=item.getproductname()%></b></td>
                                            </tr>
                                            <tr>
                                                <td align="center"><a href="./featuredproductupdate.jsp?id=<%=new Integer(featuredProducts.getid()).toString()%>&additem=<%=new Integer(item.getid()).toString()%>&manufacturer=<%=request.getParameter("manufacturer")%>">add</a></td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                        </td>
            <%
                                if(i > 2)
                                {
                                    i = -1;
            %>
                        </tr>
                        <tr>
            <%
                                }
                            }
                        }
            %>
                    </table>

                </td>
            </tr>
        </table>
    </BODY>
    <SCRIPT LANGUAGE="JavaScript">
        <%
            if(request.getParameter("manufacturer") != null)
            {
        %>
                document.form1.manufacturer.value = '<%=request.getParameter("manufacturer")%>';
        <%
            }
        %>
    </SCRIPT>
</HTML>
