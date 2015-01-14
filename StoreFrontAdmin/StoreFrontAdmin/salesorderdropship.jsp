<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    try {
        SalesOrderBean salesOrderBean = new SalesOrderBean();

        if(request.getParameter("priordropship") != null && request.getParameter("priordropship").compareTo("0") == 0)
        {
            DropShipSalesOrderRequest dropShipSalesOrderRequest = new DropShipSalesOrderRequest();
            dropShipSalesOrderRequest.setsalesorderid(new Integer(request.getParameter("orderid")).intValue());
            dropShipSalesOrderRequest.setdistributorid(new Integer(request.getParameter("distributor")).intValue());
            salesOrderBean.DropShipSalesOrder(dropShipSalesOrderRequest);
        }
        else
        {
            salesOrderBean.SendDropShipMail(new Integer(request.getParameter("orderid")).intValue(), new Integer(request.getParameter("distributor")).intValue());
        }
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>


<HTML>
    <SCRIPT LANGUAGE="JavaScript">
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
                            <td class="producttitle">Drop Ship Sales Order</td>
                        </tr>
                    </table>
                    <br>
                    <TABLE cellSpacing="1" cellPadding="3" width="95%" border="0">
                    <tr>
                        <td nowrap>A drop ship request has been sent to the distributor for sales order #: <%=request.getParameter("orderid")%>.</td>
                    </tr>
                    </TABLE>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>

