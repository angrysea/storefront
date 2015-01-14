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

<%
    ListsBean listsBean = null;
    try {
        listsBean = new ListsBean();
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
                    <table summary="<%=request.getAttribute("keyword2")%>" id="headingTable" border="0">
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="5" height="5"></td>
                        </tr>
                    </table>
                    <br>
                    <table summary="<%=request.getAttribute("keyword1")%>" cellSpacing="0" cellPadding="0" width="575" border="0">
                        <tr bgcolor="<%=theme.getcolor2()%>">
                            <td class="producttitle" noWrap>Site Map</td>
                        </tr>
                    </table>
                    <table summary="<%=request.getAttribute("keyword1")%>" cellSpacing="0" cellPadding="0" width="575" border="0">
                        <tr>



                            <td width="190" valign="top">
    <TABLE summary="<%=request.getAttribute("keyword2")%>" cellSpacing="0" cellPadding="0" border="0">
        <tr>
            <td><br></td>
        </tr>
        <tr>
            <td nowrap><b>Featured Products:</b></td>
        </tr>
        <tr>
            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="1" height="5"/></td>
        </tr>
    <%
        FeaturedProductsBean featuredProductsBean = new FeaturedProductsBean();
        GetFeaturedProductsRequest getFeaturedProductsRequest = new GetFeaturedProductsRequest();
        getFeaturedProductsRequest.setactive(true);
        if(request.getParameter("company") != null)
            getFeaturedProductsRequest.setcompanyid(request.getParameter("company"));
        GetFeaturedProductsResponse getFeaturedProductsResponse = featuredProductsBean.GetFeaturedProducts(getFeaturedProductsRequest);
        Iterator itFeaturedProducts = getFeaturedProductsResponse.getfeaturedproductsIterator();
        for(int i=0 ; itFeaturedProducts.hasNext() ; i++)
        {
            FeaturedProducts featuredProducts = (FeaturedProducts)itFeaturedProducts.next();
            Iterator itItems = featuredProducts.getitemsIterator();
            while(itItems.hasNext())
            {
                Item item = (Item)itItems.next();
    %>
                <tr>
                    <td nowrap="nowrap"><a href="<%=company.getbaseurl()%>minisite_productdetails.jsp?company=<%=new Integer(company.getid()).toString()%>&itemid=<%=new Integer(item.getid()).toString()%>"><%=item.getmanufacturer()%> <%=item.getproductname()%></a></td>
                </tr>
    <%
            }
        }
    %>
    </TABLE>
                            </td>

                        </tr>
                    </table>
                    <table summary="<%=request.getAttribute("keyword1")%>" cellSpacing="0" cellPadding="0" width="575" border="0">
                        <%@ include file="minisite_bottompane.jsp" %>
                    </table>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>

