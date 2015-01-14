<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    Company company = null;
    Theme theme = null;
    try {
        CompanyBean companyBean = new CompanyBean();
        GetCompanyResponse getCompanyResponse = companyBean.GetCompany(request, new GetCompanyRequest());
        company = getCompanyResponse.getcompany();

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

<HTML>
    <SCRIPT LANGUAGE="JavaScript">
    </SCRIPT>
    <HEAD>
        <%@ include file="titlemetadata.jsp" %>
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
        <table cellSpacing="0" cellPadding="0" border="0">
            <tr>
                <td vAlign="top" width="20">
                    <IMG src="<%=theme.getimagebaseurl()%>spacer.gif" border="0">
                </td>
                <td vAlign="top">
                    <table id="headingTable" border="0">
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="5" height="5"></td>
                        </tr>
                    </table>
                    <br>
                    <table cellSpacing="0" cellPadding="0" width="550" border="0">
                        <tr bgcolor="<%=theme.getcolor2()%>">
                            <td class="producttitle" noWrap>Thank You for Joining Our List</td>
                        </tr>
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td>You have been subscribed to the <%=company.getcompany()%> e-mail list. Thank you.</td>
                        </tr>
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td>We respect your privacy. <%=company.getcompany()%> does not share e-mail addresses with third parties. Instructions for unsubscribing come with every e-mail.</td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                    </table>
                    <table border="0" width="550">
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td><b>Thank You!</b></td>
                        </tr>
                        <tr>
                            <td><%=company.getcompany()%></td>
                        </tr>
                        <tr>
                            <td><a href="<%=company.geturl()%>"><%=company.geturl()%></a></td>
                        </tr>
                    </table>
                    <br>
                    <table border="0" width="550" cellpadding="0" cellspacing="0">
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td>This email was sent to you by <%=company.getcompany()%>.</td>
                        </tr>
                        <tr>
                            <td>To ensure delivery to your inbox (not bulk or junk folders), please add <%=company.getemail1()%> to your address book.</td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td>We've contacted you because you subscribed at <%=company.getcompany()%> to our e-mail list. The e-mail address with which you subscribed is listed as <%=request.getParameter("emailaddress")%>. If you'd like to be removed from <%=company.getcompany()%> e-mail list, please <a href="<%=company.getbaseurl()%>joinourlist_optout.jsp?emailaddress=<%=request.getParameter("emailaddress")%>">unsubscribe</a>.</td>
                        </tr>
                    </table>
                    <br>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>

