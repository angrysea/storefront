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
                <form name="form1" action="<%=company.getbasesecureurl()%>salesorderadd.jsp" method="post">
                <input name="user" type="hidden" value="<%=request.getParameter("user")==null?"":request.getParameter("user")%>"/>
                <input name="login" type="hidden" value="<%=request.getParameter("login")==null?"":request.getParameter("login")%>"/>
                    <input name="shippingmethod" type="hidden" value="<%=request.getParameter("shippingmethod")%>"/>
                <%
                    if(request.getParameter("optimizeshipping") != null)
                    {
                %>
                    <input name="optimizeshipping" type="hidden" value="<%=request.getParameter("optimizeshipping")%>"/>
                <%
                    }
                %>
                <%
                    if(request.getParameter("coupon") != null && request.getParameter("coupon").length() > 0)
                    {
                %>
                    <input name="coupon" type="hidden" value="<%=request.getParameter("coupon")%>"/>
                <%
                    }
                %>
                    <input name="cardnumber" type="hidden" value="<%=request.getParameter("cardnumber")%>"/>
                    <input name="expirationmonth" type="hidden" value="<%=request.getParameter("expirationmonth")%>"/>
                    <input name="expirationyear" type="hidden" value="<%=request.getParameter("expirationyear")%>"/>
                </form>
                    <table border="0">
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="5" height="25"></td>
                        </tr>
                        <tr>
                            <td nowrap><h2><font color="black">Please wait while we authorize your credit card...</font></h2></td>
                        </tr>
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>wait.gif"></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </BODY>
    <script LANGUAGE="JavaScript">
        document.form1.submit();
    </script>
</HTML>
