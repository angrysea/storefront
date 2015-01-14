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
        <%@ include file="toppane.jsp" %>
        <table summary="<%=request.getAttribute("keyword1")%>" cellSpacing="0" cellPadding="0" border="0">
            <tr>
                <%@ include file="leftpane.jsp" %>
                <td vAlign="top" width="20">
                    <IMG src="<%=theme.getimagebaseurl()%>spacer.gif" border="0">
                </td>
                <td vAlign="top">
                    <table summary="<%=request.getAttribute("keyword2")%>" id="headingTable" border="0">
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
                            <td nowrap><%=company.getcompany()%></td>
                        </tr>
                        <tr>
                            <td nowrap><%=company.getaddress1()%></td>
                        </tr>
                        <%
                            if(company.getaddress2() != null && company.getaddress2().length() > 0)
                            {
                        %>
                            <tr>
                                <td nowrap><%=company.getaddress2()%></td>
                            </tr>
                        <%
                            }
                        %>
                        <%
                            if(company.getaddress3() != null && company.getaddress3().length() > 0)
                            {
                        %>
                            <tr>
                                <td nowrap><%=company.getaddress3()%></td>
                            </tr>
                        <%
                            }
                        %>
                        <tr>
                            <td nowrap><%=company.getcity()%>, <%=company.getstate()%> <%=company.getzip()%></td>
                        </tr>
                        <tr>
                            <td nowrap><%=company.getcountry()%></td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td nowrap><b>E-Mail</b></td>
                        </tr>
                        <tr>
                            <td nowrap><a href="mailto:<%=company.getemail1()%>"><%=company.getemail1()%></a></td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td><b>Telephone</b></td>
                        </tr>
                        <tr>
                            <td nowrap><%=company.getcustomerservice()%> phone</td>
                        </tr>
                        <tr>
                            <td nowrap><%=company.getfax()%> fax</td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td>E-mail or call any time to speak with one of our Customer Service representatives.</td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <%@ include file="bottompane.jsp" %>
                    </table>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>

