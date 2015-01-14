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
                    <table summary="<%=request.getAttribute("keyword1")%>" cellSpacing="1" cellPadding="1" width="550" border="0">
                        <tr bgcolor="<%=theme.getcolor2()%>">
                            <td class="producttitle" noWrap>Privacy & Security</td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td>We keep your personal information private and secure. When you make a purchase from our site, you provide your name, email address, credit card information, address, phone number and a password. We use this information to process your orders, to keep you updated on your orders and to personalize your shopping experience. We do not sell, trade or rent this information to other parties.<br><br>
Our secure servers protect the information using advanced encryption and firewall technology. Your personal information cannot be read as it travels to our ordering system.<br><br>
In order to most efficiently serve you, credit card transactions and order fulfillment are handled by reputable third-party banking and distribution institutions. They receive the information needed to verify and authorize your credit card and to process your order. They are under strict obligation to keep your personal information private.
			    </td>
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

