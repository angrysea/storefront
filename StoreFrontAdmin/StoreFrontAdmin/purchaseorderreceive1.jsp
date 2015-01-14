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

    GetPurchaseOrdersResponse getPurchaseOrdersResponse = null;
    try {
        PurchaseOrderBean purchaseOrderBean = new PurchaseOrderBean();
        getPurchaseOrdersResponse = purchaseOrderBean.GetOpenPurchaseOrders(new GetPurchaseOrdersRequest());
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<HTML>
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
                            <td class="producttitle">Open Purchase Orders</td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                    </table>
                    <TABLE cellSpacing="1" cellPadding="3" width="775" border="0">
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
                                    <td bgcolor="<%=theme.getcolor1()%>"><img src="./images/spacer.gif" width="775" height="1" /></td>
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
                                <td><a href="./purchaseorderreceive2.jsp?purchaseorderid=<%=new Integer(purchaseorder.getid()).toString()%>"><%=new Integer(purchaseorder.getid()).toString()%></a></td>
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
</HTML>
