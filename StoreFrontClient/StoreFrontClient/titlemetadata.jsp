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
        GetThemeRequest getThemeRequest = new GetThemeRequest();
        getThemeRequest.setname("corporate");
        GetThemeResponse getThemeResponse = companyBean.GetTheme(request, getThemeRequest);
        tmTheme = getThemeResponse.gettheme();

        GetCompanyResponse getCompanyResponse = companyBean.GetCompany(request, new GetCompanyRequest());
        tmCompany = getCompanyResponse.getcompany();

        if(request.getAttribute("keyword1") == null)
        {
            if(tmCompany.getkeyword1() != null)
                request.setAttribute("keyword1", tmCompany.getkeyword1());
        }
        if(request.getAttribute("keyword2") == null)
        {
            if(tmCompany.getkeyword2() != null)
                request.setAttribute("keyword2", tmCompany.getkeyword2());
        }

        if(request.getRequestURI().endsWith("landingpage.jsp"))
        {
            LandingPageBean landingPageBean = new LandingPageBean();
            GetLandingPageRequest getLandingPageRequest = new GetLandingPageRequest();
            getLandingPageRequest.setid(new Integer(request.getParameter("id")).intValue());
            GetLandingPageResponse getLandingPageResponse = landingPageBean.GetLandingPage(getLandingPageRequest);
            landingPageKeyword = getLandingPageResponse.getlandingPage().getheading();

            request.setAttribute("keyword1", landingPageKeyword);

            // Make sure the keyword1 and keyword2 are not the same
            String keyword1 = (String)request.getAttribute("keyword1");
            String keyword2 = (String)request.getAttribute("keyword2");
            if(keyword1.compareToIgnoreCase(keyword2) == 0)
            {
                request.setAttribute("keyword2", tmCompany.getkeyword1());
            }
        }
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>
<%
    if(landingPageKeyword != null)
    {
%>
        <TITLE><%=landingPageKeyword%> and <%=tmCompany.getkeyword()%> - <%=tmCompany.getcompany()%></TITLE>
<%
    }
    else if(request.getAttribute("searchtitle") == null)
    {
%>
        <TITLE><%=request.getAttribute("keyword1")%>, <%=request.getAttribute("keyword2")%> and <%=tmCompany.getkeyword()%> - <%=tmCompany.getcompany()%></TITLE>
<%
    }
    else
    {
%>
        <TITLE><%=request.getAttribute("searchtitle")%> - <%=request.getAttribute("keyword1")%>, <%=request.getAttribute("keyword2")%> - <%=tmCompany.getcompany()%></TITLE>
<%
    }
%>
        <META NAME="KEYWORDS" CONTENT="<%=request.getAttribute("keyword1")%>,<%=request.getAttribute("keyword2")%>">
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
