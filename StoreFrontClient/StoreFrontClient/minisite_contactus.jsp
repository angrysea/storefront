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
        GetCompanyRequest getCompanyRequest = new GetCompanyRequest();
        if(request.getParameter("company") != null)
            getCompanyRequest.setid(new Integer(request.getParameter("company")).intValue());
        GetCompanyResponse getCompanyResponse = companyBean.GetCompany(request, getCompanyRequest);
        company = getCompanyResponse.getcompany();

        GetThemeRequest getThemeRequest = new GetThemeRequest();
        getThemeRequest.setid(new Integer(company.getthemeid()).toString());
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
        <%@ include file="minisite_titlemetadata.jsp" %>
        <LINK href="<%=company.getbaseurl()%>minisite.css" rel="STYLESHEET"></LINK>
    </HEAD>
    <BODY leftMargin="0" topMargin="0" onload="" marginwidth="0" marginheight="0">
        <%@ include file="minisite_toppane.jsp" %>
        <table summary="<%=request.getAttribute("keyword1")%>" cellSpacing="0" cellPadding="0" border="0">
            <tr>
                <td vAlign="top" width="20">
                    <IMG src="<%=theme.getimagebaseurl()%>spacer.gif" border="0">
                </td>
                <td vAlign="top">
                    <table summary="<%=request.getAttribute("keyword1")%>" id="headingTable" border="0">
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="5" height="5"></td>
                        </tr>
                    </table>
                    <br>
                    <table summary="<%=request.getAttribute("keyword1")%>" cellSpacing="0" cellPadding="0" width="550" border="0">
                        <tr bgcolor="<%=theme.getcolor2()%>">
                            <td class="producttitle" noWrap>Contact Us</td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td nowrap><a href="mailto:<%=company.getemail1()%>"><%=company.getemail1()%></a></td>
                        </tr>
                        <tr>
                            <td>E-mail one of our Customer Service representatives any time.</td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <%@ include file="minisite_bottompane.jsp" %>
                    </table>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>

