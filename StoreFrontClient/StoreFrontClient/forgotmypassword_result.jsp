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
    try {
        UserBean userBean = new UserBean();
        userBean.Forgotpassword(request.getParameter("emailaddress"));
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<HTML>
    <SCRIPT LANGUAGE="JavaScript">
        function OnClickContinue()
        {
            window.location = "<%=StoreFrontUrls.geturl(request, company, "index")%>";
        }
    </SCRIPT>
    <HEAD>
        <%@ include file="titlemetadata.jsp" %>
        <LINK href="<%=company.getbaseurl()%>storefront.css" rel="STYLESHEET"></LINK>
    </HEAD>
    <BODY leftMargin="0" topMargin="0" onload="" marginwidth="0" marginheight="0">
        <%@ include file="toppane.jsp" %>
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
                    <table cellSpacing="1" cellPadding="1" width="200" border="0">
                    <input name="gotourl" type="hidden" value="<%=request.getParameter("gotourl")%>" />
                        <tr>
                            <td><br></tr>
                        </tr>
                        <tr bgcolor="<%=theme.getcolor2()%>">
                            <td colspan="2" nowrap class="producttitle" width="500">Forgot my password</td>
                        </tr>
                        <tr>
                            <td colspan="2"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="1" height="4" /></td>
                        </tr>
                        <tr>
                            <td colspan="2">Your password has been sent to e-mail address <i><%=request.getParameter("emailaddress")%></i></td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                    </table>
                    <table border="0">
                        <tr>
                            <td><input type="image" alt="continue" src="<%=theme.getimagebaseurl()%>continue_button.gif" onclick="OnClickContinue()"/></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <%@ include file="bottompane.jsp" %>
    </BODY>
</HTML>

