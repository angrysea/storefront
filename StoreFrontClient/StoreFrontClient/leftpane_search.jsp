<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    Theme lpTheme = null;
    Company lpCompany = null;
    try {
        CompanyBean lpCompanyBean = new CompanyBean();
        GetThemeRequest lpGetThemeRequest = new GetThemeRequest();
        lpGetThemeRequest.setname("corporate");
        GetThemeResponse lpGetThemeResponse = lpCompanyBean.GetTheme(request, lpGetThemeRequest);
        lpTheme = lpGetThemeResponse.gettheme();

        GetCompanyResponse getCompanyResponse = lpCompanyBean.GetCompany(request, new GetCompanyRequest());
        lpCompany = getCompanyResponse.getcompany();
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<%
    int BROWSE_BY_USE_ID = 1;
    int BROWSE_BY_TYPE_ID = 2;

    ListsBean lpListsBean = null;
    String searchDescription = null;
    try {
        lpListsBean = new ListsBean();
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>


<SCRIPT LANGUAGE="JavaScript">
</SCRIPT>
<td vAlign="top" bgcolor="<%=lpTheme.getcolor2()%>" nowrap rowspan="500">
    &nbsp;&nbsp;
</td>
<td vAlign="top" bgcolor="<%=lpTheme.getcolor2()%>" rowspan="500">
    <form name="searchform" action="<%=lpCompany.getbasesecureurl()%>search_result.jsp" method="get" onsubmit="return(OnSubmitForm());">
    <input name="user" type="hidden" value="<%=request.getParameter("user")==null?"":request.getParameter("user")%>"/>
    <input name="login" type="hidden" value="<%=request.getParameter("login")==null?"":request.getParameter("login")%>"/>
    <TABLE width="160" cellSpacing="0" cellPadding="0" border="0">
        <input type="hidden" name="page" value="1">
        <input type="hidden" name="description" value="Search Result">
        <tr>
            <td><img src="<%=lpTheme.getimagebaseurl()%>spacer.gif" width="5" height="10"></td>
        </tr>
        <tr>
            <td colspan="3"><img alt="product search" src="<%=lpTheme.getimagebaseurl()%>product_search.gif" width="150" height="20"></td>
        </tr>
        <tr>
            <td><img src="<%=lpTheme.getimagebaseurl()%>spacer.gif" width="5" height="3"></td>
        </tr>
        <tr>
            <td><input name="search" type="text" size="20"></td>
            <td><input type="image" alt="go" src="<%=lpTheme.getimagebaseurl()%>go_button.gif"></td>
            <td><img src="<%=lpTheme.getimagebaseurl()%>spacer.gif" width="8" height="1"/></td>
        </tr>
    </TABLE>
    </form>

<%
if(request.getParameter("searchroot").compareToIgnoreCase("manufacturer") == 0)
{
%>
    <TABLE cellSpacing="0" cellPadding="0" border="0">
        <tr>
            <td colspan="3"><img alt="browse by brand" src="<%=lpTheme.getimagebaseurl()%>browse_by_brand.gif" width="150" height="20"></td>
        </tr>
    <%
        GetManufacturerRequest getManufacturerRequest = new GetManufacturerRequest();
        getManufacturerRequest.setid(new Integer(request.getParameter("manufacturerid")).intValue());
        GetManufacturerResponse getManufacturerResponse = lpListsBean.GetManufacturer(getManufacturerRequest);
        Manufacturer manufacturer = getManufacturerResponse.getmanufacturer();
        searchDescription = manufacturer.getname();
    %>
        <tr>
            <td colspan="3"><a href="<%=StoreFrontUrls.getsearchresults(request, lpCompany, searchDescription, "0", "0", "0", Integer.toString(manufacturer.getid()), "0", "0", "1")%>"><%=manufacturer.getname()%></a></td>
        </tr>
    <%
        if(request.getParameter("categoryid1") != null)
        {
            GetGroupRequest getGroupRequest = new GetGroupRequest();
            getGroupRequest.setid(new Integer(request.getParameter("categoryid1")).intValue());
            GetGroupResponse getGroupResponse = lpListsBean.GetGroup(getGroupRequest);
            Group group = getGroupResponse.getgroup();
    %>
            <tr>
                <td>&nbsp;</td>
                <td colspan="2"><%=group.getname()%></td>
            </tr>
    <%
            GetCategoriesRequest getCategoriesRequest = new GetCategoriesRequest();
            getCategoriesRequest.setgroup(group.getid());
            GetCategoriesResponse getCategoriesResponse = lpListsBean.GetCategories(getCategoriesRequest);
            Iterator itCategories = getCategoriesResponse.getcategoriesIterator();
            for(int i=0 ; itCategories.hasNext() ; i++)
            {
                Category category = (Category)itCategories.next();
                searchDescription += category.getname();
    %>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td><a href="<%=StoreFrontUrls.getsearchresults(request, lpCompany, searchDescription, request.getParameter("searchroot"), "0", "0", Integer.toString(manufacturer.getid()), request.getParameter("categoryid1"), "0", "1")%>"><%=category.getname()%></a></td>
                </tr>
    <%
            }
        }
        else
        {
            GetGroupsResponse getGroupsResponse = lpListsBean.GetGroups(new GetGroupsRequest());
            Iterator itGroups = getGroupsResponse.getgroupsIterator();
            while(itGroups.hasNext())
            {
                Group group = (Group)itGroups.next();
    %>
                <tr>
                    <td>&nbsp;</td>
                    <td colspan="2"><b>By <%=group.getname()%></b></td>
                </tr>
    <%
                GetCategoriesRequest getCategoriesRequest = new GetCategoriesRequest();
                getCategoriesRequest.setgroup(group.getid());
                GetCategoriesResponse getCategoriesResponse = lpListsBean.GetCategories(getCategoriesRequest);
                Iterator itCategories = getCategoriesResponse.getcategoriesIterator();
                for(int i=0 ; itCategories.hasNext() ; i++)
                {
                    Category category = (Category)itCategories.next();
                    searchDescription += category.getname();
    %>
                    <tr>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td><a href="<%=StoreFrontUrls.getsearchresults(request, lpCompany, searchDescription, request.getParameter("searchroot"), "0", "0", Integer.toString(manufacturer.getid()), Integer.toString(category.getid()), "0", "1")%>"><%=category.getname()%></a></td>
                    </tr>
    <%
                }
            }
        }
    %>
    </TABLE>
<%
}
%>

<%
if(request.getParameter("searchroot").compareToIgnoreCase("use") == 0)
{
%>
    <TABLE cellSpacing="0" cellPadding="0" border="0">
        <tr>
            <td colspan="3"><img alt="browse by use" src="<%=lpTheme.getimagebaseurl()%>browse_by_use.gif" width="150" height="20"></td>
        </tr>
    <%
        Category category = lpListsBean.GetCategory(new Integer(request.getParameter("categoryid1")).intValue());
    %>
        <tr>
            <td><a href="<%=StoreFrontUrls.getsearchresults(request, lpCompany, searchDescription, "0", "0", "0", "0", Integer.toString(category.getid()), "0", "1")%>"><%=category.getname()%></a></td>
        </tr>
    <%
        if(request.getParameter("categoryid2") != null)
        {
            category = lpListsBean.GetCategory(new Integer(request.getParameter("categoryid2")).intValue());
    %>
            <tr>
                <td>&nbsp;</td>
                <td colspan="2"><%=category.getname()%></td>
            </tr>
    <%
            GetManufacturersResponse getManufacturersResponse = new GetManufacturersResponse();
            GetManufacturersRequest getManufacturersRequest = new GetManufacturersRequest();
            getManufacturersResponse = lpListsBean.GetManufacturers(getManufacturersRequest);
            Iterator itManufacturers = getManufacturersResponse.getmanufacturersIterator();
            while(itManufacturers.hasNext())
            {
                Manufacturer manufacturer = (Manufacturer)itManufacturers.next();
    %>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td><a href="<%=StoreFrontUrls.getsearchresults(request, lpCompany, searchDescription, request.getParameter("searchroot"), "0", "0", Integer.toString(manufacturer.getid()), Integer.toString(category.getid()), "0", "1")%>"><%=manufacturer.getname()%></a></td>
                </tr>
    <%
            }
        }
        else
        {
        }
    %>
    </TABLE>
<%
}
%>

    <TABLE cellSpacing="0" cellPadding="0" border="0">
        <tr>
            <td><img src="<%=lpTheme.getimagebaseurl()%>spacer.gif" width="5" height="10"></td>
        </tr>
        <tr>
            <td><img alt="help" src="<%=lpTheme.getimagebaseurl()%>help.gif" width="150" height="20"></td>
        </tr>
        <tr>
            <td><a href="<%=StoreFrontUrls.geturl(request, lpCompany, "contactus")%>">Contact Us</a></td>
        </tr>
        <tr>
            <td><a href="<%=StoreFrontUrls.geturl(request, lpCompany, "returnpolicy")%>">Return Policy</a></td>
        </tr>
        <tr>
            <td><a href="<%=StoreFrontUrls.geturl(request, lpCompany, "privacysecurity")%>">Privacy & Security</a></td>
        </tr>
    </TABLE>
    <table border="0">
        <tr>
            <td><img src="<%=lpTheme.getimagebaseurl()%>spacer.gif" width="1" height="200"/></td>
        </tr>
    </table>
</td>
