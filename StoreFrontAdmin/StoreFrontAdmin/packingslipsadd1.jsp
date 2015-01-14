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
        GetThemeResponse getThemeResponse = companyBean.GetTheme(getThemeRequest);
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

    GetSalesOrdersResponse getSalesOrdersResponse = null;
    try {
        SalesOrderBean salesOrderBean = new SalesOrderBean();
        GetSalesOrdersRequest getSalesOrdersRequest = new GetSalesOrdersRequest();
        getSalesOrdersResponse = salesOrderBean.GetOpenSalesOrders(getSalesOrdersRequest);
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
            if(AreItemsSelected() == false)
                return false;

            return QtyToShipOK();
        }

        function AreItemsSelected()
        {
            <%
                Iterator itSalesOrders = getSalesOrdersResponse.getsalesorderIterator();
                while(itSalesOrders.hasNext())
                {
                    SalesOrder salesOrder = (SalesOrder)itSalesOrders.next();
                    Iterator itItems = salesOrder.getitemsIterator();
                    while(itItems.hasNext())
                    {
                        SalesOrderItem salesOrderItem = (SalesOrderItem)itItems.next();
            %>
                        if(document.form1.item<%=new Integer(salesOrderItem.getid()).toString()%>.checked)
                        {
                            return true;
                        }
            <%
                    }
                }
            %>

            alert("At least one item must be selected for shipment.");
            return false;
        }

        function QtyToShipOK()
        {
            var valid = '0123456789';
            <%
                itSalesOrders = getSalesOrdersResponse.getsalesorderIterator();
                while(itSalesOrders.hasNext())
                {
                    SalesOrder salesOrder = (SalesOrder)itSalesOrders.next();
                    Iterator itItems = salesOrder.getitemsIterator();
                    while(itItems.hasNext())
                    {
                        SalesOrderItem salesOrderItem = (SalesOrderItem)itItems.next();
            %>
                        if(document.form1.item<%=new Integer(salesOrderItem.getid()).toString()%>.checked)
                        {
                            if(document.form1.qtytoship<%=new Integer(salesOrderItem.getid()).toString()%>.value.length == 0 || IsValid(document.form1.qtytoship<%=new Integer(salesOrderItem.getid()).toString()%>.value, valid) == false)
                            {
                                alert("An invalid quantity to ship was entered.");
                                document.form1.qtytoship<%=new Integer(salesOrderItem.getid()).toString()%>.focus();
                                return false;
                            }
                            var qtyentered = parseInt(document.form1.item('qtytoship<%=new Integer(salesOrderItem.getid()).toString()%>').value);
                            if(qtyentered <= 0)
                            {
                                alert("An invalid quantity to ship was entered.");
                                document.form1.qtytoship<%=new Integer(salesOrderItem.getid()).toString()%>.focus();
                                return false;
                            }
                        }
            <%
                    }
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
                    <table border="0">
                        <tr>
                            <td><img src="./images/spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td class="producttitle">Create Packing Slips</td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                    </table>
                    <form name="form1" action="./packingslipsadd2.jsp" method="get" onsubmit="return(OnSubmitForm());">
                    <table cellSpacing="1" cellPadding="3" width="500" border="0">
                        <tr>
                            <td><b>Select Item</b></td>
                            <td><b>Sales Order ID</b></td>
                            <td><b>Created</b></td>
                            <td><b>Product ID</b></td>
                            <td align="center"><b>Qty Ordered</b></td>
                            <td align="center"><b>Qty To Ship</b></td>
                            <td><b>Status</b></td>
                        </tr>
                        <tr>
                            <td colspan="10">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td bgcolor="<%=theme.getcolor1()%>"><img src="./spacer.gif" width="500" height="1" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <%
                            itSalesOrders = getSalesOrdersResponse.getsalesorderIterator();
                            while(itSalesOrders.hasNext())
                            {
                                SalesOrder salesOrder = (SalesOrder)itSalesOrders.next();
                                Iterator itItems = salesOrder.getitemsIterator();
                                while(itItems.hasNext())
                                {
                                    SalesOrderItem salesOrderItem = (SalesOrderItem)itItems.next();
                        %>
                                    <tr>
                                        <td valign="top"><input type="checkbox" name="item<%=new Integer(salesOrderItem.getid()).toString()%>" /></td>
                                        <td valign="top"><a href="./salesorder.jsp?orderid=<%=new Integer(salesOrder.getid()).toString()%>"><%=new Integer(salesOrder.getid()).toString()%></a></td>
                                        <td valign="top" nowrap><%=dateFormat.format(salesOrder.getcreationdate())%></td>
                                        <td valign="top"><a href="./productupdate.jsp?id=<%=new Integer(salesOrderItem.getid()).toString()%>"><%=salesOrderItem.getisin()%></a></td>
                                        <td valign="top" align="center"><%=new Integer(salesOrderItem.getquantity()).toString()%></td>
                                        <td valign="top" align="center"><input type="text" name="qtytoship<%=new Integer(salesOrderItem.getid()).toString()%>" size="5" value="<%=new Integer(salesOrderItem.getquantitytoship()).toString()%>"/></td>
                                        <td valign="top" nowrap><%=salesOrderItem.getstatus()!=null?salesOrderItem.getstatus():""%></td>
                                    </tr>
                        <%
                                }
                            }
                        %>
                        <tr>
                            <td colspan="10">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td bgcolor="<%=theme.getcolor1()%>"><img src="./spacer.gif" width="500" height="1" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                    <table border="0">
                        <tr>
                            <td><input type="submit" value="Create Packing Slips" /></td>
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
