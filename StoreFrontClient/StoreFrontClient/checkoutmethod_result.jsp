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

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setemailaddress(request.getParameter("emailaddress"));
        loginRequest.setpassword(request.getParameter("password"));
        LoginResponse loginResponse = userBean.Login(loginRequest, request, response);

        if(loginResponse.getuser() != null && loginResponse.getuser().getloggedin())
        {
            // redirect to the goto url - login successful
            response.sendRedirect(StoreFrontUrls.updateurl(request, company, request.getParameter("gotourl")));
        }
        else
        {
            // failed, go back to the checkoutmethod screen
            userBean.GetUser(request, response);
            response.sendRedirect(StoreFrontUrls.getsecureurl(request, company, "checkoutmethod", request.getParameter("gotourl") + "&error=Invalid e-mail address or password."));
        }

    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>


<HTML>
    <HEAD>
        <%@ include file="titlemetadata.jsp" %>
        <LINK href="<%=company.getbaseurl()%>storefront.css" rel="STYLESHEET"></LINK>
    </HEAD>
    <BODY leftMargin="0" topMargin="0" onload="" marginwidth="0" marginheight="0">
    </BODY>
</HTML>

