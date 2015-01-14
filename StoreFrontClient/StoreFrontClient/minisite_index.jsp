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
    DecimalFormat moneyFormat = new DecimalFormat("$#,###.00");

    FeaturedProductsBean featuredProductsBean = new FeaturedProductsBean();
    try {
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
        <table cellSpacing="0" cellPadding="0" border="0">
            <tr>
                <td vAlign="top" width="24"><IMG src="<%=theme.getimagebaseurl()%>spacer.gif" border="0"></td>
                <td vAlign="top" width="725">
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="1" height="15"/></td>
                        </tr>
                        <tr>
                            <td class="producttitle"><b>Welcome to <%=company.getcompany()%></b></td>
                        </tr>
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="1" height="5"/></td>
                        </tr>
                        <tr>
                            <td><%=company.getdescription()%></td>
                        </tr>
                    </table>
                <%
      		    GetFeaturedProductsRequest getFeaturedProductsRequest = new GetFeaturedProductsRequest();
                    getFeaturedProductsRequest.setactive(true);
                    if(request.getParameter("company") != null)
                        getFeaturedProductsRequest.setcompanyid(request.getParameter("company"));
                    GetFeaturedProductsResponse getFeaturedProductsResponse = featuredProductsBean.GetFeaturedProducts(getFeaturedProductsRequest);
                    Iterator itFeaturedProducts = getFeaturedProductsResponse.getfeaturedproductsIterator();
                    for(int i=0 ; itFeaturedProducts.hasNext() ; i++)
                    {
                        FeaturedProducts featuredProducts = (FeaturedProducts)itFeaturedProducts.next();
                %>
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="1" height="25"/></td>
                        </tr>
                        <tr bgcolor="<%=theme.getcolor1()%>">
                            <td colspan="6"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="725" height="1"/></td>
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
                    <%
                        Iterator itItems = featuredProducts.getitemsIterator();
                        if(itItems.hasNext())
                        {
                            Item item = (Item)itItems.next();
                            if(i % 2 > 0)
                            {
                    %>
                            <tr>
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td colspan="3" align="center"><a href="<%=company.getbaseurl()%>minisite_productdetails.jsp?company=<%=new Integer(company.getid()).toString()%>&itemid=<%=new Integer(item.getid()).toString()%>"><img border="0" alt="<%=company.getkeyword()%> - <%=item.getmanufacturer()%> <%=item.getproductname()%>" src="<%=theme.getimagebaseurl()%><%=item.getdetails().getimageUrlSmall()%>"/></a></td>
                                        </tr>
                                        <tr>
                                            <td>&nbsp;</td>
                                            <td align="center"><a href="<%=company.getbaseurl()%>minisite_productdetails.jsp?company=<%=new Integer(company.getid()).toString()%>&itemid=<%=new Integer(item.getid()).toString()%>" title="<%=request.getAttribute("keyword1")%> - <%=item.getmanufacturer()%> <%=item.getproductname()%>"><%=item.getmanufacturer()%> <%=item.getproductname()%></a></td>
                                            <td>&nbsp;</td>
                                        </tr>
                                    </table>
                                </td>
                                <td>&nbsp;&nbsp;</td>
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr valign="middle">
                                            <td><%=featuredProducts.getcomments()%></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                    <%
                            }
                            else
                            {
                    %>
                            <tr>
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr valign="middle">
                                            <td><%=featuredProducts.getcomments()%></td>
                                        </tr>
                                    </table>
                                </td>
                                <td>&nbsp;&nbsp;</td>
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td colspan="3" align="center"><a href="<%=company.getbaseurl()%>minisite_productdetails.jsp?company=<%=new Integer(company.getid()).toString()%>&itemid=<%=new Integer(item.getid()).toString()%>"><img border="0" alt="<%=company.getkeyword()%> - <%=item.getmanufacturer()%> <%=item.getproductname()%>" src="<%=theme.getimagebaseurl()%><%=item.getdetails().getimageUrlSmall()%>"/></a></td>
                                        </tr>
                                        <tr>
                                            <td>&nbsp;</td>
                                            <td align="center"><a href="<%=company.getbaseurl()%>minisite_productdetails.jsp?company=<%=new Integer(company.getid()).toString()%>&itemid=<%=new Integer(item.getid()).toString()%>" title="<%=request.getAttribute("keyword1")%> - <%=item.getmanufacturer()%> <%=item.getproductname()%>"><%=item.getmanufacturer()%> <%=item.getproductname()%></a></td>
                                            <td>&nbsp;</td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                    <%
                            }
                        }
                    %>
                    </table>
                <%
                    }
                %>
                    <br />
                    <br />
                    <%@ include file="minisite_bottompane.jsp" %>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>
