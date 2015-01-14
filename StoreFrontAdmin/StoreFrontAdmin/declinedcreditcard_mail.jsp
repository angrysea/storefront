<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
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

<%!
    public String DisplayCreditCard(String cardnumber)
    {
        if(cardnumber.length() == 0)
            return "";

        if(cardnumber.length() == 15)
            return "XXXXXXXXXXX" + cardnumber.subSequence(11, 15);
        else
            return "XXXXXXXXXXXX" + cardnumber.subSequence(12, 16);
    }
%>

<HTML>
    <HEAD>
        <LINK href="<%=company.getbaseurl()%>storefront.css" rel="STYLESHEET"></LINK>
    </HEAD>
    <BODY leftMargin="0" topMargin="0" onload="" marginwidth="0" marginheight="0">
<TABLE valign="0" cellSpacing="0" cellPadding="0" border="0">
    <TR>
        <TD height="74" width="223" background="<%=theme.getimagebaseurl()%>logo.gif"></TD>
        <TD width="250" height="10" background="<%=theme.getimagebaseurl()%>spacer.gif"></TD>
        <TD align="right" width="180" height="82" background="<%=theme.getimagebaseurl()%>scenery.gif"></TD>
    </TR>
</TABLE>
<TABLE align="0" cellSpacing="0" cellPadding="0" width="100%" border="0">
    <TR>
        <TD><img src="<%=theme.getimagebaseurl()%>filler_left.gif" width="223" height="24"></TD>
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
                            <td nowrap class="producttitle" width="550">Your credit card has been declined</td>
                        </tr>
                    </table>
                    <br>
                    <table border="0" width="550">
                        <tr>
                            <td>Your credit card for order # <%=new Integer(salesOrder.getid()).toString()%> has been declined.  Please contact us with your new credit card information, or if you have an account with us, log into our web site and update your credit card information.</td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td colspan="2">If you have any questions, you can reply to this e-mail or call us anytime.</td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <table border="0">
                                    <tr>
                                        <td>Customer Support:</td>
                                        <td><%=company.getcustomerservice()%></td>
                                    </tr>
                                    <tr>
                                        <td>E-mail:</td>
                                        <td><a href="mailto:<%=company.getemail2()%>"><%=company.getemail2()%></a></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td><b>Thanks again for your order!</b></td>
                        </tr>
                        <tr>
                            <td><br></td>
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
    <SCRIPT LANGUAGE="JavaScript">
    </SCRIPT>
</HTML>
