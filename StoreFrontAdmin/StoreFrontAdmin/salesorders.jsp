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
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    DateFormat dateFormat2 = new SimpleDateFormat("MM/dd/yyyy");
    DecimalFormat moneyFormat = new DecimalFormat("$#,##0.00");

    String fromDateString = null;
    String toDateString = null;

    GetSalesOrderReportResponse getSalesOrderReportResponse = null;
    GetSalesOrdersResponse getSalesOrdersResponse = null;
    boolean openSalesOrdersOnly = false;
    try {
        java.util.Date fromdate = Calendar.getInstance().getTime();
        java.util.Date todate = Calendar.getInstance().getTime();

        if(request.getParameter("fromdate") != null && request.getParameter("todate") != null)
        {
            fromdate = dateFormat2.parse(request.getParameter("fromdate"));
            todate = dateFormat2.parse(request.getParameter("todate"));
        }

        SalesOrderBean salesOrderBean = new SalesOrderBean();
        if(request.getParameter("opensalesorders") != null && request.getParameter("opensalesorders").compareTo("on") == 0)
        {
            getSalesOrdersResponse = salesOrderBean.GetUnshippedSalesOrders(new GetSalesOrdersRequest());
            openSalesOrdersOnly = true;
        }
        else
        {
            GetSalesOrderReportRequest getSalesOrderReportRequest = new GetSalesOrderReportRequest();
            getSalesOrderReportRequest.setstartdate(fromdate);
            getSalesOrderReportRequest.setenddate(todate);
            getSalesOrderReportResponse = salesOrderBean.GetSalesOrderReport(getSalesOrderReportRequest);
        }

        fromDateString = dateFormat2.format(fromdate);
        toDateString = dateFormat2.format(todate);
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<HTML>
    <%@ include file="datevalidation.jsp" %>
    <SCRIPT LANGUAGE="JavaScript">
        function OnSubmitForm()
        {
            if(ValidateDate(document.form1.fromdate) == false)
            {
                return false;
            }

            if(ValidateDate(document.form1.todate) == false)
            {
                return false;
            }

            return true;
        }

        function OnClickOpenSalesOrders()
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
                    <table border="0">
                        <tr>
                            <td><img src="./images/spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td class="producttitle">Sales Orders</td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                    </table>
                    <form name="form1" action="./salesorders.jsp" method="GET" onsubmit="return(OnSubmitForm())">
                    <table border="0">
                        <tr>
                            <td>From:</td>
                            <td><input type="text" name="fromdate" size="12" onchange="OnChangeDate()" ></td>
                            <td nowrap>&nbsp;&nbsp;</td>
                            <td>To:</td>
                            <td><input type="text" name="todate" size="12" onchange="OnChangeDate()" ></td>
                            <td nowrap>&nbsp;&nbsp;</td>
                            <td><input type="submit" name="go" value="Go"/></td>
                        </tr>
                        <tr>
                            <td colspan="10"><input name="opensalesorders" type="checkbox" <%=openSalesOrdersOnly==true?"checked":""%> onclick="OnClickOpenSalesOrders()"/>&nbsp;Retrieve only Open Sales Orders (ignore date range)</td>
                        </tr>
                    </table>
                    </form>
                    <TABLE cellSpacing="1" cellPadding="3" width="775" border="0">
                        <tr>
                            <td align="left"><b>Order Number</b></td>
                            <td aligh="left"><b>Order Date</b></td>
                            <td aligh="left"><b>Customer</b></td>
                            <td align="right"><b>Order Amount</b></td>
                            <td align="right"><b>Taxes</b></td>
                            <td align="right"><b>COGS</b></td>
                            <td align="right"><b>Discounts</b></td>
                            <td align="right"><b>Gross Margin</b></td>
                            <td align="center"><b>Drop Ship Requested</b></td>
                            <td align="left"><b>Order Status</b></td>
                        </tr>
                        <tr>
                            <td colspan="12">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <td bgcolor="<%=theme.getcolor1()%>"><img src="./images/spacer.gif" width="775" height="1" /></td>
                                </table>
                            </td>
                        </tr>
                        <%
                            SalesOrderBean salesOrderBean = new SalesOrderBean();
                            ItemBean itemBean = new ItemBean();

                            Iterator itSalesOrders = null;
                            if(openSalesOrdersOnly)
                                itSalesOrders = getSalesOrdersResponse.getsalesorderIterator();
                            else
                                itSalesOrders = getSalesOrderReportResponse.getsalesorderIterator();
                            boolean orders = false;

                            double totalOrderAmount = 0.0;
                            double totalTaxes = 0.0;
                            double totalShipping = 0.0;
                            double totalMerchandise = 0.0;
                            double totalCogs = 0.0;
                            double totalDiscounts = 0.0;
                            double totalGrossMargin = 0.0;

                            while(itSalesOrders.hasNext())
                            {
                                orders = true;
                                SalesOrder salesOrder = (SalesOrder)itSalesOrders.next();

                                // Cycle through the items to determine COGS
                                double cogs = 0.0;
                                Iterator itSalesOrderItems = salesOrder.getitemsIterator();
                                while(itSalesOrderItems.hasNext())
                                {
                                    SalesOrderItem salesOrderItem = (SalesOrderItem)itSalesOrderItems.next();

                                    GetItemRequest getItemRequest = new GetItemRequest();
                                    getItemRequest.setid(salesOrderItem.getitemnumber());
                                    GetItemResponse getItemResponse = itemBean.GetItem(getItemRequest);
                                    cogs += (getItemResponse.getitem().getourcost() * salesOrderItem.getquantity());
                                }

                                double grossMargin = salesOrder.gettotalcost() - cogs - salesOrder.getdiscount();

                                totalOrderAmount += salesOrder.gettotal();
                                totalTaxes += salesOrder.gettaxes();
                                totalShipping += salesOrder.getshipping();
                                totalMerchandise += salesOrder.gettotalcost();
                                totalCogs += cogs;
                                totalDiscounts += salesOrder.getdiscount();
                                totalGrossMargin += grossMargin;
                        %>
                        <tr>
                            <td nowrap align="left"><a href="./salesorder.jsp?orderid=<%=new Integer(salesOrder.getid()).toString()%>"><%=new Integer(salesOrder.getid()).toString()%></a></td>
                            <td nowrap align="left"><%=dateFormat.format(salesOrder.getcreationdate())%></td>
                            <td nowrap align="left"><a href="./customer.jsp?customerid=<%=new Integer(salesOrder.getcustomer().getid()).toString()%>"><%=salesOrder.getcustomer().getlast()%>, <%=salesOrder.getcustomer().getfirst()%></td>
                            <td nowrap align="right"><%=moneyFormat.format(salesOrder.gettotal())%></td>
                            <td nowrap align="right"><%=moneyFormat.format(salesOrder.gettaxes())%></td>
                            <td nowrap align="right"><%=moneyFormat.format(cogs)%></td>
                            <td nowrap align="right"><%=moneyFormat.format(salesOrder.getdiscount())%></td>
                            <td nowrap align="right"><%=moneyFormat.format(grossMargin)%></td>
                            <td nowrap align="center"><%=salesOrder.getdropshipped()==true?"yes":"no"%></td>
                            <td nowrap align="left"><%=salesOrder.getstatus()%></td>
                        </tr>
                        <%
                            }
                        %>
                        <%
                            if(orders == true)
                            {
                        %>
                            <tr>
                                <td colspan="12">
                                    <table border="0" cellpadding="0" cellspacing="0">
                                        <td bgcolor="<%=theme.getcolor1()%>"><img src="./images/spacer.gif" width="775" height="1" /></td>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td align="right"><%=moneyFormat.format(totalOrderAmount)%></td>
                                <td align="right"><%=moneyFormat.format(totalTaxes)%></td>
                                <td align="right"><%=moneyFormat.format(totalCogs)%></td>
                                <td align="right"><%=moneyFormat.format(totalDiscounts)%></td>
                                <td align="right"><%=moneyFormat.format(totalGrossMargin)%></td>
                            </tr>
                        <%
                            }
                        %>
                        <%
                            if(orders == false)
                            {
                        %>
                        <tr>
                            <td colspan="5" nowrap>There are no orders in the system for this time period.</td>
                        </tr>
                        <%
                            }
                        %>
                        <tr>
                            <td><br></td>
                        </tr>
                    </TABLE>
                </td>
            </tr>
        </table>
    </BODY>
    <SCRIPT LANGUAGE="JavaScript">
        document.form1.fromdate.value = '<%=fromDateString%>';
        document.form1.todate.value = '<%=toDateString%>';
    </SCRIPT>
</HTML>
