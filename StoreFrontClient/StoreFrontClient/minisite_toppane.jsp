<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    Company tpCompany = null;
    Theme tpTheme = null;
    try {
        CompanyBean companyBean = new CompanyBean();
        GetCompanyRequest getCompanyRequest = new GetCompanyRequest();
        if(request.getParameter("company") != null)
            getCompanyRequest.setid(new Integer(request.getParameter("company")).intValue());
        GetCompanyResponse getCompanyResponse = companyBean.GetCompany(request, getCompanyRequest);
        tpCompany = getCompanyResponse.getcompany();

        GetThemeRequest getThemeRequest = new GetThemeRequest();
        getThemeRequest.setid(new Integer(tpCompany.getthemeid()).toString());
        GetThemeResponse getThemeResponse = companyBean.GetTheme(request, getThemeRequest);
        tpTheme = getThemeResponse.gettheme();
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<TABLE summary="<%=request.getAttribute("keyword1")%>" valign="0" cellSpacing="0" cellPadding="0" border="0">
    <TR>
        <TD><a href="<%=tpCompany.getbaseurl()%>minisite_index.jsp?company=<%=request.getParameter("company")%>"><img border="0" src="<%=tpTheme.getimagebaseurl()%><%=tpTheme.getimage1()%>" alt="Home - <%=request.getAttribute("keyword1")%> - <%=tpCompany.getcompany()%>"></a></TD>
        <TD width="325" height="10" background="<%=tpTheme.getimagebaseurl()%>spacer.gif"></TD>
        <TD align="right" width="180" height="82" background="<%=tpTheme.getimagebaseurl()%><%=tpTheme.getimage2()%>" title="<%=request.getAttribute("keyword1")%> - <%=tpCompany.getcompany()%>"></TD>
    </TR>
</TABLE>
<TABLE summary="<%=request.getAttribute("keyword1")%>" align="0" cellSpacing="0" cellPadding="0" width="100%" border="0">
    <TR>
        <TD width="100%" background="<%=tpTheme.getimagebaseurl()%><%=tpTheme.getimage3()%>"><img src="<%=tpTheme.getimagebaseurl()%><%=tpTheme.getimage3()%>"></TD>
    </TR>
</TABLE>
