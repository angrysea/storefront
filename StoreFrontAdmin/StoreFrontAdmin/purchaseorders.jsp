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

    GetPurchaseOrdersResponse getPurchaseOrdersResponse = null;
    try {
        java.util.Date fromdate = Calendar.getInstance().getTime();
        java.util.Date todate = Calendar.getInstance().getTime();

        if(request.getParameter("fromdate") != null && request.getParameter("todate") != null)
        {
            fromdate = dateFormat2.parse(request.getParameter("fromdate"));
            todate = dateFormat2.parse(request.getParameter("todate"));
        }

        PurchaseOrderBean purchaseOrderBean = new PurchaseOrderBean();
        GetPurchaseOrdersRequest getPurchaseOrdersRequest = new GetPurchaseOrdersRequest();
        getPurchaseOrdersRequest.setstartdate(fromdate);
        getPurchaseOrdersRequest.setenddate(todate);
        getPurchaseOrdersResponse = purchaseOrderBean.GetPurchaseOrders(getPurchaseOrdersRequest);

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
                            <td class="producttitle">Purchase Orders</td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                    </table>
                    <form name="form1" action="./purchaseorders.jsp" method="GET" onsubmit="return(OnSubmitForm())">
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
                    </table>
                    </form>
                    <TABLE cellSpacing="1" cellPadding="3" width="700" border="0">
                        <tr>
                            <td><b>ID</b></td>
                            <td><b>Creation Date</b></td>
                            <td><b>Sales Order ID</b></td>
                            <td><b>Total Cost</b></td>
                            <td><b>Distributor</b></td>
                            <td><b>Shipping Method</b></td>
                            <td><b>Tracking #</b></td>
                            <td><b>Status</b></td>
                        </tr>
                        <tr>
                            <td colspan="12">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <td bgcolor="<%=theme.getcolor1()%>"><img src="./images/spacer.gif" width="700" height="1" /></td>
                                </table>
                            </td>
                        </tr>
                        <%
                            Iterator itPurchaseOrders = getPurchaseOrdersResponse.getpurchaseorderIterator();
                            while(itPurchaseOrders.hasNext())
                            {
                                PurchaseOrder purchaseorder = (PurchaseOrder)itPurchaseOrders.next();
                        %>
                            <tr>
                                <td><a href="./purchaseorderupdate.jsp?purchaseorderid=<%=new Integer(purchaseorder.getid()).toString()%>"><%=new Integer(purchaseorder.getid()).toString()%></a></td>
                                <td nowrap="nowrap"><%=dateFormat.format(purchaseorder.getcreationdate())%></td>
                        <%
                                if(purchaseorder.getreferencenumber() > 0)
                                {
                        %>
                                <td><a href="./salesorder.jsp?orderid=<%=new Integer(purchaseorder.getreferencenumber()).toString()%>"><%=new Integer(purchaseorder.getreferencenumber()).toString()%></a></td>
                        <%
                                }
                                else
                                {
                        %>
                                <td></td>
                        <%
                                }
                        %>
                                <td><%=moneyFormat.format(purchaseorder.gettotal())%></td>
                                <td nowrap><%=purchaseorder.getdistributor().getname()%></td>
                                <td nowrap><%=purchaseorder.getshippingmethod().getdescription()%></td>
                                <td nowrap><%=purchaseorder.gettrackingnumber()==null?"":purchaseorder.gettrackingnumber()%></td>
                                <td nowrap><%=purchaseorder.getstatus()==null?"":purchaseorder.getstatus()%></td>
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
