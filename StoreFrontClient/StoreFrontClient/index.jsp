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
    DecimalFormat moneyFormat = new DecimalFormat("$#,###.00");

    FeaturedProductsBean featuredProductsBean = new FeaturedProductsBean();
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
        <table cellSpacing="0" cellPadding="0" border="0">
            <tr>
                <%@ include file="leftpane.jsp" %>
                <td vAlign="top" width="16"><IMG src="<%=theme.getimagebaseurl()%>spacer.gif" border="0"></td>
                <!-- This is index.jsp  -->
                <td vAlign="top" width="575">
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="1" height="30"/></td>
                        </tr>
                        <tr>
                            <td><%=company.getdescription()%></td>
                        </tr>
                    </table>
                <%
      		    GetFeaturedProductsRequest getFeaturedProductsRequest = new GetFeaturedProductsRequest();
                    getFeaturedProductsRequest.setactive(true);
		    GetFeaturedProductsResponse getFeaturedProductsResponse = featuredProductsBean.GetFeaturedProducts(getFeaturedProductsRequest);
                    Iterator itFeaturedProducts = getFeaturedProductsResponse.getfeaturedproductsIterator();
                    while(itFeaturedProducts.hasNext())
                    {
                        FeaturedProducts featuredProducts = (FeaturedProducts)itFeaturedProducts.next();
                %>
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="1" height="25"/></td>
                        </tr>
                        <tr bgcolor="<%=theme.getcolor1()%>">
                            <td colspan="6"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="575" height="1"/></td>
                        </tr>
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="1" height="5"/></td>
                        </tr>
                        <tr>
                            <td colspan="6" class="producttitle"><%=featuredProducts.getheading()%></td>
                        </tr>
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="1" height="5"/></td>
                        </tr>
                        <tr>
                            <td colspan="6"><%=featuredProducts.getcomments()%></td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr align="left">
                    <%
                        Iterator itItems = featuredProducts.getitemsIterator();
                        for(int i=0 ; itItems.hasNext() ; i++)
                        {
                            Item item = (Item)itItems.next();
                    %>
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td colspan="3" align="center"><a href="<%=StoreFrontUrls.getproductdetails(request, company, item)%>"><img border="0" alt="<%=company.getkeyword()%> - <%=item.getmanufacturer()%> <%=item.getproductname()%>" src="<%=theme.getimagebaseurl()%><%=item.getdetails().getimageUrlSmall()%>"/></a></td>
                                        </tr>
                                        <tr>
                                            <td>&nbsp;</td>
                                            <td align="center"><%=item.getmanufacturer()%> <%=item.getproductname()%></td>
                                            <td>&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td colspan="3" align="center" nowrap><strike><%=moneyFormat.format(item.getlistprice())%></strike> <font color="red"><%=moneyFormat.format(item.getourprice())%></font></td>
                                        </tr>
                                    </table>
                                </td>
                    <%
                        if(i > (theme.getfeatureditemcount()-2) )
                        {
                            i = -1;
                    %>
                        </tr>
                        <tr>
                    <%
                        }
                    }
                    %>
                        </tr>
                    </table>
                <%
                    }
                %>
                    <br />
                    <br />
                    <%@ include file="bottompane.jsp" %>
                </td>
                <%@ include file="rightpane_index.jsp" %>
            </tr>
        </table>
    </BODY>
</HTML>
