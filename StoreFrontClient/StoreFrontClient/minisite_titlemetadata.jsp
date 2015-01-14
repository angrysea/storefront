<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    Theme tmTheme = null;
    Company tmCompany = null;
    String landingPageKeyword = null;
    try {
        CompanyBean companyBean = new CompanyBean();
        GetCompanyRequest getCompanyRequest = new GetCompanyRequest();
        if(request.getParameter("company") != null)
            getCompanyRequest.setid(new Integer(request.getParameter("company")).intValue());
        GetCompanyResponse getCompanyResponse = companyBean.GetCompany(request, getCompanyRequest);
        tmCompany = getCompanyResponse.getcompany();

        GetThemeRequest getThemeRequest = new GetThemeRequest();
        getThemeRequest.setid(new Integer(tmCompany.getthemeid()).toString());
        GetThemeResponse getThemeResponse = companyBean.GetTheme(request, getThemeRequest);
        tmTheme = getThemeResponse.gettheme();

        if(request.getAttribute("keyword1") == null)
        {
            if(tmCompany.getkeyword1() != null)
                request.setAttribute("keyword1", tmCompany.getkeyword1());
        }
        if(request.getAttribute("keyword2") == null)
        {
            request.setAttribute("keyword2", tmCompany.getkeyword());
        }
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>
        <TITLE><%=request.getAttribute("keyword1")%> - <%=tmCompany.getcompany()%></TITLE>
        <META NAME="KEYWORDS" CONTENT="<%=request.getAttribute("keyword1")%>">
        <META NAME="Description" CONTENT="<%=request.getAttribute("keyword1")%> and <%=request.getAttribute("keyword2")%> - <%=tmCompany.getdescription()%>">
        <META NAME="Robots" CONTENT="index,follow">
        <META NAME="GOOGLEBOT" CONTENT="INDEX, FOLLOW">
        <!-- This site contains information about <%=request.getAttribute("keyword1")%> and <%=request.getAttribute("keyword2")%> -->
<%
    if(tmTheme.getmetacontenttype() != null)
    {
%>
        <meta http-equiv="Content-Type" <%=tmTheme.getmetacontenttype()%>>
<%
    }
%>
