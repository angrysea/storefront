<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    ListsBean lpbrandsListsBean = null;
    Theme lpbrandsTheme = null;
    Company lpbrandsCompany = null;
    try {
        lpbrandsListsBean = new ListsBean();
        CompanyBean lpbrandsCompanyBean = new CompanyBean();
        GetThemeRequest lpGetThemeRequest = new GetThemeRequest();
        lpGetThemeRequest.setname("corporate");
        GetThemeResponse lpGetThemeResponse = lpbrandsCompanyBean.GetTheme(request, lpGetThemeRequest);
        lpbrandsTheme = lpGetThemeResponse.gettheme();

        GetCompanyResponse getCompanyResponse = lpbrandsCompanyBean.GetCompany(request, new GetCompanyRequest());
        lpbrandsCompany = getCompanyResponse.getcompany();
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

    <TABLE summary="<%=request.getAttribute("keyword2")%>" cellSpacing="0" cellPadding="0" border="0">
        <tr>
            <td><img alt="Browse <%=request.getAttribute("keyword1")%> and <%=request.getAttribute("keyword2")%> by brand" src="<%=lpbrandsTheme.getimagebaseurl()%>browse_by_brand.gif" width="150" height="20"></td>
        </tr>
        <%
            GetManufacturersRequest getManufacturersRequest = new GetManufacturersRequest();
            getManufacturersRequest.setactive(true);
            GetManufacturersResponse lpGetManufacturersResponse = lpbrandsListsBean.GetManufacturers(getManufacturersRequest);
            Iterator lpitManufacturers = lpGetManufacturersResponse.getmanufacturersIterator();
            while(lpitManufacturers.hasNext())
            {
                Manufacturer manufacturer = (Manufacturer)lpitManufacturers.next();
        %>
                <tr>
                    <td><a href="<%=StoreFrontUrls.getsearchresults(request, lpbrandsCompany, manufacturer.getname(), "0", "0", "0", Integer.toString(manufacturer.getid()), "0", "0", "1")%>" title="<%=request.getAttribute("keyword1")%> and <%=request.getAttribute("keyword2")%> - <%=manufacturer.getname()%>"><%=manufacturer.getname()%></a></td>
                </tr>
        <%
            }
        %>
        <tr>
            <td><img src="<%=lpbrandsTheme.getimagebaseurl()%>spacer.gif" width="5" height="10"></td>
        </tr>
    </TABLE>
