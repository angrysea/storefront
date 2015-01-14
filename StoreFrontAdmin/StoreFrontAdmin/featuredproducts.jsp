<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
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
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<%
    GetFeaturedProductsResponse getFeaturedProductsResponse = null;
    try {
        FeaturedProductsBean featuredProductsBean = new FeaturedProductsBean();
        GetFeaturedProductsRequest getFeaturedProductsRequest = new GetFeaturedProductsRequest();
        if(request.getParameter("company") != null)
            getFeaturedProductsRequest.setcompanyid(request.getParameter("company"));
        getFeaturedProductsResponse = featuredProductsBean.GetFeaturedProducts(getFeaturedProductsRequest);
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>


<HTML>
    <SCRIPT LANGUAGE="JavaScript">
        function OnChangeCompany()
        {
            document.form1.submit();
        }
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
                    <table id="headingTable" height="100%" border="0">
                        <tr>
                            <td><img src="./images/spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td class="producttitle">Featured Products</td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                    </table>
                    <form name="form1" action="./featuredproducts.jsp" method="GET">
                    <table>
                        <tr>
                            <td noWrap>Company:</td>
                            <td>
                                <select name="company" onchange="OnChangeCompany()">
                                    <%
                                        CompanyBean companyBean = new CompanyBean();
                                        GetCompaniesResponse getCompaniesResponse = companyBean.GetCompanies(new GetCompaniesRequest());
                                        Iterator itCompanies = getCompaniesResponse.getcompaniesIterator();
                                        while(itCompanies.hasNext())
                                        {
                                            Company company = (Company)itCompanies.next();
                                            if(request.getParameter("company") != null && company.getid() == new Integer(request.getParameter("company")).intValue())
                                            {
                                    %>
                                                <option value="<%=new Integer(company.getid()).toString()%>" selected="selected"><%=company.getcompany()%></option>
                                    <%
                                            }
                                            else
                                            {
                                    %>
                                                <option value="<%=new Integer(company.getid()).toString()%>"><%=company.getcompany()%></option>
                                    <%
                                            }
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                    </table>
                    </form>
                    <TABLE cellSpacing="1" cellPadding="3" width="450" border="0">
                        <TR>
                            <TD class="columnheader" align="left"><b>ID</b></TD>
                            <TD class="columnheader" align="left"><b>Heading</b></TD>
                            <TD class="columnheader" align="left"><b>Sort Order</b></TD>
                            <TD class="columnheader" align="left"><b>Active</b></TD>
                        </TR>
                        <tr>
                            <td colspan="10">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td bgcolor="<%=theme.getcolor1()%>"><img src="./spacer.gif" width="450" height="1" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <%
                            Iterator itFeaturedProducts = getFeaturedProductsResponse.getfeaturedproductsIterator();
                            while(itFeaturedProducts.hasNext())
                            {
                                FeaturedProducts featuredProducts = (FeaturedProducts)itFeaturedProducts.next();
                                String active = featuredProducts.getactive() ? "yes" : "no";
                        %>
                                <tr>
                                    <td align="left"><a href="featuredproductupdate.jsp?id=<%=new Integer(featuredProducts.getid()).toString()%>"><%=new Integer(featuredProducts.getid()).toString()%></a></td>
                                    <td align="left"><%=featuredProducts.getheading()%></td>
                                    <td align="left"><%=new Integer(featuredProducts.getsortorder()).toString()%></td>
                                    <td align="left"><%=active%></td>
                                </tr>
                        <%
                            }
                        %>
                    </TABLE>
                    <br>
                    <table border="0">
                        <tr>
                            <td class="smallprompt"><A href="./featuredproductadd.jsp">Add a new Featured Product</A></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>
