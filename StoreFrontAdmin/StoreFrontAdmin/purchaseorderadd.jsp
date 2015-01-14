<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
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
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    DecimalFormat moneyFormat = new DecimalFormat("$#,##0.00");

    GetItemsResponse getItemsResponse = new GetItemsResponse();
    try {
        ItemBean itemBean = new ItemBean();
        GetItemsRequest getItemsRequest = new GetItemsRequest();
        getItemsRequest.setorderby("distributor");
        getItemsRequest.setdirection("asc");
        getItemsResponse = itemBean.GetItemsToOrder(getItemsRequest);
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
            if(document.form1.distributor.value == 0)
            {
                alert("A distributor must be selected!");
                document.form1.distributor.focus();
                return false;
            }

            if(document.form1.shippingmethod.value == 0)
            {
                alert("A shipping method must be selected!");
                document.form1.shippingmethod.focus();
                return false;
            }

            if(AreItemsSelected() == false)
                return false;

            if(QtyToOrderOK() == false)
                return false;

            return true;
        }

        function AreItemsSelected()
        {
            <%
                Iterator itItems = getItemsResponse.getitemsIterator();
                while(itItems.hasNext())
                {
                    Item item = (Item)itItems.next();
            %>
                    if(document.form1.item<%=new Integer(item.getid()).toString()%>.checked)
                    {
                        return true;
                    }
            <%
                }
            %>

            alert("At least one item must be selected.");
            return false;
        }

        function QtyToOrderOK()
        {
            var valid = '0123456789';
            <%
                itItems = getItemsResponse.getitemsIterator();
                while(itItems.hasNext())
                {
                    Item item = (Item)itItems.next();
            %>
                    if(document.form1.item<%=new Integer(item.getid()).toString()%>.checked)
                    {
                        if(document.form1.qtytoorder<%=new Integer(item.getid()).toString()%>.value.length == 0 || IsValid(document.form1.qtytoorder<%=new Integer(item.getid()).toString()%>.value, valid) == false)
                        {
                            alert("An invalid quantity to order was entered.");
                            document.form1.qtytoorder<%=new Integer(item.getid()).toString()%>.focus();
                            return false;
                        }
                        var qtytoorder = parseInt(document.form1.qtytoorder<%=new Integer(item.getid()).toString()%>.value);
                        if(qtytoorder == 0)
                        {
                            alert("A zero quantity to order is not valid.");
                            document.form1.qtytoorder<%=new Integer(item.getid()).toString()%>.focus();
                            return false;
                        }
                    }
            <%
                }
            %>

            return true;
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
                    <table border="0" width="750">
                        <tr>
                            <td><img src="./images/spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td class="producttitle">Create Purchase Orders</td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                        <tr>
                            <td>Sales orders have been generated for the following items and the inventory levels are too low to fill the orders.  Please select the items to be ordered.</td>
                        </tr>
                    </table>
                    <form name="form1" action="./purchaseorderadd_result.jsp" method="get" onsubmit="return(OnSubmitForm());">
                    <table border="0" width="750">
                        <tr>
                            <td>
                                Distributor:
                                <select name="distributor">
                                    <option value="0">< select ></option>
                                    <%
                                        ListsBean lists = new ListsBean();
	                                GetDistributorsResponse getDistributorsResponse = lists.GetDistributors(new GetDistributorsRequest());
                                        Iterator itDistributors = getDistributorsResponse.getdistributorsIterator();
                                        while(itDistributors.hasNext())
                                        {
                                            Distributor distributor = (Distributor)itDistributors.next();
                                    %>
                                            <option value="<%=distributor.getid()%>"><%=distributor.getname()%></option>
                                    <%
                                        }
                                    %>
                                </select>
                                &nbsp;
                                Shipping Method:
                                <select name="shippingmethod">
                                    <option value="0">< select ></option>
                                    <%
                                        ShippingMethod currentShippingMethod = null;

                                        GetShippingMethodsRequest getShippingMethodsRequest = new GetShippingMethodsRequest();
                                        GetShippingMethodsResponse getShippingMethodsResponse = lists.GetShippingMethods(getShippingMethodsRequest);
                                        Iterator itShippingMethods = getShippingMethodsResponse.getshippingmethodsIterator();
                                        while(itShippingMethods.hasNext())
                                        {
                                            ShippingMethod shippingMethod = (ShippingMethod)itShippingMethods.next();
                                    %>
                                            <option value="<%=new Integer(shippingMethod.getid()).toString()%>"><%=shippingMethod.getdescription()%></option>
                                    <%
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                    </table>
                    <br>
                    <table cellSpacing="1" cellPadding="3" width="750" border="0">
                        <tr>
                            <td class="columnheader" align="left"><b>Select Item</b></td>
                            <TD class="columnheader" align="left"><b>Inventory ID</b></TD>
                            <TD class="columnheader" align="left"><b>Name</b></TD>
                            <TD class="columnheader" align="left"><b>Mfgr</b></TD>
                            <TD class="columnheader" align="left"><b>Default Distributor</b></TD>
                            <TD class="columnheader" align="center"><b>Our Cost</b></TD>
                            <TD class="columnheader" align="center"><b>Quantity On Hand</b></TD>
                            <TD class="columnheader" align="center"><b>Quantity To Order</b></TD>
                        </tr>
                        <tr>
                            <td colspan="10">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td bgcolor="<%=theme.getcolor1()%>"><img src="./spacer.gif" width="750" height="1" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    <%
                        itItems = getItemsResponse.getitemsIterator();
                        while(itItems.hasNext())
                        {
                            Item item = (Item)itItems.next();
                    %>
                            <tr>
                                <td><input type="checkbox" name="item<%=new Integer(item.getid()).toString()%>" /></td>
                                <td nowrap><a href="./productupdate.jsp?id=<%=new Integer(item.getid()).toString()%>&manufacturer=<%=new Integer(item.getmanufacturerid()).toString()%>"><%=item.getisin()%></a></td>
                                <td><%=item.getproductname()%></td>
                                <td nowrap><%=item.getmanufacturer()%></td>
                                <td nowrap><%=item.getdistributor()%></td>
                                <td nowrap align="right"><%=moneyFormat.format(item.getourcost())%></td>
                                <td nowrap align="center"><%=new Integer(item.getquantity()).toString()%></td>
                                <td nowrap align="center"><input type="text" name="qtytoorder<%=new Integer(item.getid()).toString()%>" size="5" value="<%=new Integer(item.getquantitytoorder()).toString()%>"/></td>
                            </tr>
                    <%
                        }
                    %>
                        <tr>
                            <td colspan="10">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td bgcolor="<%=theme.getcolor1()%>"><img src="./spacer.gif" width="750" height="1" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                    <table border="0">
                        <tr>
                            <td><input type="submit" value="Create Purchase Order" /></td>
                        </tr>
                    </table>
                    </form>
                </td>
            </tr>
        </table>
    </BODY>
    <SCRIPT LANGUAGE="JavaScript">
    </SCRIPT>
</HTML>
