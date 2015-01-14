<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    Theme theme = null;
    try {
        CompanyBean companyBean = new CompanyBean();
        GetThemeRequest getThemeRequest = new GetThemeRequest();
        getThemeRequest.setname("corporate");
        GetThemeResponse getThemeResponse = companyBean.GetTheme(request, getThemeRequest);
        theme = getThemeResponse.gettheme();

        FeaturedProductsBean featuredProductsBean = new FeaturedProductsBean();
        DeleteFeaturedProductRequest deleteFeaturedProductRequest = new DeleteFeaturedProductRequest();
        deleteFeaturedProductRequest.setid(new Integer(request.getParameter("id")).intValue());
        featuredProductsBean.DeleteFeaturedProduct(deleteFeaturedProductRequest);
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
        <LINK href="./storefront.css" rel="STYLESHEET"></LINK>
    </HEAD>
    <BODY leftMargin="0" topMargin="0" onload="" marginwidth="0" marginheight="0">
        <%@ include file="toppane.jsp" %>
        <table cellSpacing="0" cellPadding="0" border="0">
            <tr>
                <%@ include file="leftpane.jsp" %>
                <td vAlign="top" width="20">
                    <IMG src="./images/spacer.gif" border="0">
                </td>
                <td vAlign="top">
                    <table border="0" width="600">
                        <tr>
                            <td><img src="./images/spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td class="producttitle">Featured Product Delete Result</td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                        <tr>
                            <td>Featured product <%=request.getParameter("id")%> was deleted successfully.</td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </BODY>
    <SCRIPT LANGUAGE="JavaScript">
    </SCRIPT>
</HTML>
