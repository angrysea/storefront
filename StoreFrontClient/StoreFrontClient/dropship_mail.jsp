<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    Theme theme = null;
    Company company = null;
    try {
        CompanyBean companyBean = new CompanyBean();
        GetThemeRequest getThemeRequest = new GetThemeRequest();
        getThemeRequest.setname("corporate");
        GetThemeResponse getThemeResponse = companyBean.GetTheme(request, getThemeRequest);
        theme = getThemeResponse.gettheme();

        GetCompanyResponse getCompanyResponse = companyBean.GetCompany(request, new GetCompanyRequest());
        company = getCompanyResponse.getcompany();
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<%
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    DecimalFormat moneyFormat = new DecimalFormat("$#,##0.00");

    SalesOrder salesOrder = null;
    try {
        SalesOrderBean salesOrderBean = new SalesOrderBean();
        GetSalesOrderRequest getSalesOrderRequest = new GetSalesOrderRequest();
        getSalesOrderRequest.setid(new Integer(request.getParameter("orderid")).intValue());
        GetSalesOrderResponse getSalesOrderResponse = salesOrderBean.GetSalesOrder(getSalesOrderRequest);
        salesOrder = getSalesOrderResponse.getsalesorder();
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<HTML>
    <HEAD>
        <LINK href="<%=company.getbaseurl()%>storefront.css" rel="STYLESHEET"></LINK>
    </HEAD>
    <BODY leftMargin="0" topMargin="0" onload="" marginwidth="0" marginheight="0">
<TABLE valign="0" cellSpacing="0" cellPadding="0" border="0">
    <TR>
        <TD><img src="<%=theme.getimagebaseurl()%>logo.gif" /></TD>
        <TD width="250" height="10" background="<%=theme.getimagebaseurl()%>spacer.gif"></TD>
        <TD align="right" width="180" height="82" background="<%=theme.getimagebaseurl()%>scenery.gif"></TD>
    </TR>
</TABLE>
<TABLE align="0" cellSpacing="0" cellPadding="0" width="100%" border="0">
    <TR>
        <TD><img src="<%=theme.getimagebaseurl()%>filler_left.gif" width="300" height="24"></TD>
        <TD width="100%" background="<%=theme.getimagebaseurl()%>toolbar_filler.gif"><img src="<%=theme.getimagebaseurl()%>toolbar_filler.gif" width="5" height="24"></TD>
    </TR>
</TABLE>
        <br>
        <table cellSpacing="0" cellPadding="0" border="0">
            <tr>
                <td vAlign="top" width="20">
                    <IMG src="<%=theme.getimagebaseurl()%>spacer.gif" border="0">
                </td>
                <td vAlign="top">
                    <table border="0">
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="5" height="15"></td>
                        </tr>
                        <tr bgcolor="<%=theme.getcolor2()%>">
                            <td nowrap class="producttitle" width="550">Purchase Order</td>
                        </tr>
                    </table>
                    <table border="0" cellpadding="0" cellspacing="0" width="550">
                        <tr>
                            <td><b>PO Number:</b> <%=new Integer(salesOrder.getid()).toString()%></td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td><%=company.getcompany()%></td>
                        </tr>
                        <tr>
                            <td><%=company.getaddress1()%></td>
                        </tr>
                <%
                    if(company.getaddress2() != null)
                    {
                %>
                        <tr>
                            <td><%=company.getaddress2()%></td>
                        </tr>
                <%
                    }
                %>
                <%
                    if(company.getaddress3() != null)
                    {
                %>
                        <tr>
                            <td><%=company.getaddress3()%></td>
                        </tr>
                <%
                    }
                %>
                        <tr>
                            <td><%=company.getcity()%>, <%=company.getstate()%> <%=company.getzip()%></td>
                        </tr>
                        <tr>
                            <td><%=company.getcustomerservice()%></td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                    </table>
                    <table border="0" width="550">
                        <tr>
                            <td colspan="2"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td valign="top">
                                <table border="0" cellSpacing="0" cellPadding="0">
                                <tr>
                                    <td nowrap><b>Shipping Address</b></td>
                                </tr>
                                <tr>
                                    <td nowrap><%=salesOrder.getshippingaddress().getfirst()%> <%=salesOrder.getshippingaddress().getlast()%></td>
                                </tr>
                                <tr>
                                    <td nowrap><%=salesOrder.getshippingaddress().getaddress1()%></td>
                                </tr>
                                <%
                                if(salesOrder.getshippingaddress().getaddress2() != null && salesOrder.getshippingaddress().getaddress2().length() > 0)
                                {
                                %>
                                    <tr>
                                        <td nowrap><%=salesOrder.getshippingaddress().getaddress2()%></td>
                                    </tr>
                                <%
                                }
                                %>
                                <tr>
                                    <td nowrap><%=salesOrder.getshippingaddress().getcity()%>, <%=salesOrder.getshippingaddress().getstate()%> <%=salesOrder.getshippingaddress().getzip()%> </td>
                                </tr>
                                <tr>
                                    <td nowrap><%=salesOrder.getshippingaddress().getphone()%></td>
                                </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                    <br>
                    <table cellSpacing="1" cellPadding="2" border="0" width="550">
                    <tr>
                        <td nowrap align="left"><b>Item</b></td>
                        <td nowrap align="center"><b>Quantity</b></td>
                    </tr>
                    <tr>
                        <td colspan="4">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td bgcolor="<%=theme.getcolor1()%>"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="550" height="2"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <%
                        Iterator itSalesOrderItems = salesOrder.getitemsIterator();
                        while(itSalesOrderItems.hasNext())
                        {
                            SalesOrderItem salesorderitem = (SalesOrderItem)itSalesOrderItems.next();
                    %>
                            <tr>
                                <td align="left" width="350"><%=salesorderitem.getmanufacturer()%> <%=salesorderitem.getproductname()%>
                                    <br>Item number: <%=salesorderitem.getisin()%>
                                </td>
                                <td align="center"><%=new Integer(salesorderitem.getquantity()).toString()%></td>
                            </tr>
                    <%
                        }
                    %>
                    <tr>
                        <td colspan="4">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td bgcolor="<%=theme.getcolor1()%>"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="550" height="1"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    </table>
                    <br>
                    <br>
                    <table border="0" width="550">
                        <tr>
                            <td><b>Special Instructions:</b></td>
                        </tr>
                        <tr>
                            <td>Please send an e-mail to <a href="mailto:<%=company.getemail2()%>"><%=company.getemail2()%></a> when this order ships.  Also, please include the <b>tracking number</b> and the <b>purchase order number</b> in the e-mail. Please do not include any of your own promotional information in the box for shipment to our customer.</td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                    </table>
                    <br>
                    <table border="0" cellpadding="0" cellspacing="0" width="550">
                        <tr>
                            <td colspan="2">If you have any questions please contact us promptly at <%=company.getcustomerservice()%>.</td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td><b>Thank you!</b></td>
                        </tr>
                        <tr>
                            <td><%=company.getcompany()%></td>
                        </tr>
                        <tr>
                            <td><a href="<%=company.geturl()%>"><%=company.geturl()%></a></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>
