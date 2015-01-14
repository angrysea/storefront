<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    ListsBean lpgroupsListsBean = null;
    Theme lpgroupsTheme = null;
    Company lpgroupsCompany = null;
    try {
        lpgroupsListsBean = new ListsBean();
        CompanyBean lpgroupsCompanyBean = new CompanyBean();
        GetThemeRequest lpGetThemeRequest = new GetThemeRequest();
        lpGetThemeRequest.setname("corporate");
        GetThemeResponse lpGetThemeResponse = lpgroupsCompanyBean.GetTheme(request, lpGetThemeRequest);
        lpgroupsTheme = lpGetThemeResponse.gettheme();

        GetCompanyResponse getCompanyResponse = lpgroupsCompanyBean.GetCompany(request, new GetCompanyRequest());
        lpgroupsCompany = getCompanyResponse.getcompany();
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<%
    GetGroupsResponse getGroupsResponse = lpgroupsListsBean.GetGroups(new GetGroupsRequest());
    Iterator itGroups = getGroupsResponse.getgroupsIterator();
    while(itGroups.hasNext())
    {
        Group group = (Group)itGroups.next();
%>

    <TABLE summary="<%=request.getAttribute("keyword2")%>" cellSpacing="0" cellPadding="0" border="0">
        <tr>
            <td><img alt="Browse <%=request.getAttribute("keyword1")%> and <%=request.getAttribute("keyword2")%> by <%=group.getname()%>" src="<%=lpgroupsTheme.getimagebaseurl()%><%=group.getimage()%>" width="150" height="20"></td>
        </tr>
        <%
            GetCategoriesRequest getCategoriesRequest = new GetCategoriesRequest();
            getCategoriesRequest.setactive(true);
            getCategoriesRequest.setgroup(group.getid());
            GetCategoriesResponse getCategoriesResponse = lpgroupsListsBean.GetCategories(getCategoriesRequest);
            Iterator itCategories = getCategoriesResponse.getcategoriesIterator();
            for(int i=0 ; itCategories.hasNext() ; i++)
            {
                Category category = (Category)itCategories.next();
        %>
                <tr>
                    <td><a href="<%=StoreFrontUrls.getsearchresults(request, lpgroupsCompany, category.getname(), "0", "0", "0", "0", Integer.toString(category.getid()), "0", "1")%>" title="<%=request.getAttribute("keyword1")%> and <%=request.getAttribute("keyword2")%> - <%=category.getname()%>"><%=category.getname()%></a></td>
                </tr>
        <%
            }
        %>
        <tr>
            <td><img src="<%=lpgroupsTheme.getimagebaseurl()%>spacer.gif" width="5" height="10"></td>
        </tr>
    </TABLE>
<%
    }
%>
