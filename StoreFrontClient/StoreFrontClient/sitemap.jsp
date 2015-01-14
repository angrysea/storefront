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
        <%@ include file="titlemetadata.jsp" %>
        <LINK href="<%=company.getbaseurl()%>storefront.css" rel="STYLESHEET"></LINK>
    </HEAD>
    <BODY leftMargin="0" topMargin="0" onload="" marginwidth="0" marginheight="0">
        <%@ include file="toppane.jsp" %>
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
<%
    GetGroupsResponse getGroupsResponse = listsBean.GetGroups(new GetGroupsRequest());
    Iterator itGroups = getGroupsResponse.getgroupsIterator();
    while(itGroups.hasNext())
    {
        Group group = (Group)itGroups.next();
%>
    <TABLE summary="<%=request.getAttribute("keyword2")%>" cellSpacing="0" cellPadding="0" border="0">
        <tr>
            <td><br></td>
        </tr>
        <tr>
            <td nowrap><b>Browse By <%=group.getname()%>:</b></td>
        </tr>
        <%
            GetCategoriesRequest getCategoriesRequest = new GetCategoriesRequest();
            getCategoriesRequest.setactive(true);
            getCategoriesRequest.setgroup(group.getid());
            GetCategoriesResponse getCategoriesResponse = listsBean.GetCategories(getCategoriesRequest);
            Iterator itCategories = getCategoriesResponse.getcategoriesIterator();
            for(int i=0 ; itCategories.hasNext() ; i++)
            {
                Category category = (Category)itCategories.next();
        %>
                <tr>
                    <td><a href="<%=StoreFrontUrls.getsearchresults(request, company, category.getname(), "0", "0", "0", "0", Integer.toString(category.getid()), "0", "1")%>" title="<%=category.getname()%>"><%=category.getname()%></a></td>
                </tr>
        <%
            }
        %>
    </TABLE>
<%
    }
%>
                            </td>


                            <td width="190" valign="top">
    <TABLE summary="<%=request.getAttribute("keyword2")%>" cellSpacing="0" cellPadding="0" border="0">
        <tr>
            <td><br></td>
        </tr>
        <tr>
            <td nowrap><b>Browse by Manufacturer:</b></td>
        </tr>
        <%
            GetManufacturersRequest getManufacturersRequest = new GetManufacturersRequest();
            getManufacturersRequest.setactive(true);
            GetManufacturersResponse lpGetManufacturersResponse = listsBean.GetManufacturers(getManufacturersRequest);
            Iterator lpitManufacturers = lpGetManufacturersResponse.getmanufacturersIterator();
            while(lpitManufacturers.hasNext())
            {
                Manufacturer manufacturer = (Manufacturer)lpitManufacturers.next();
        %>
                <tr>
                    <td><a href="<%=StoreFrontUrls.getsearchresults(request, company, manufacturer.getname(), "0", "0", "0", Integer.toString(manufacturer.getid()), "0", "0","1")%>" title="<%=manufacturer.getname()%> <%=company.getkeyword()%>"><%=manufacturer.getname()%></a></td>
                </tr>
        <%
            }
        %>
    </TABLE>
                            </td>

                            <td width="190" valign="top">
    <TABLE summary="<%=request.getAttribute("keyword2")%>" cellSpacing="0" cellPadding="0" border="0">
        <tr>
            <td><br></td>
        </tr>
        <tr>
            <td nowrap><b>Information Pages:</b></td>
        </tr>
        <%
        {
            LandingPageBean landingPageBean = new LandingPageBean();
            GetLandingPagesRequest getLandingPagesRequest = new GetLandingPagesRequest();
            GetLandingPagesResponse getLandingPagesResponse = landingPageBean.GetLandingPages(getLandingPagesRequest);
            Iterator itLandingPages = getLandingPagesResponse.getlandingPagesIterator();
            for(int i=0 ; itLandingPages.hasNext() ; i++)
            {
                LandingPage landingPage = (LandingPage)itLandingPages.next();
        %>
                <tr>
                    <td><a href="<%=StoreFrontUrls.getlandingpage(request, company, landingPage)%>" title="<%=landingPage.getheading()%>"><%=landingPage.getheading()%></a></td>
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
                        <%@ include file="bottompane.jsp" %>
                    </table>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>

