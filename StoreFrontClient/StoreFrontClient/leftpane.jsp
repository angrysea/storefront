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

<%@ include file="commonscript.jsp" %>
<SCRIPT LANGUAGE="JavaScript">
    function OnSubmitSearchForm()
    {
        <%
        String href = StoreFrontUrls.getsearchresultsurl(request, lpCompany, null, "0", "0", null, "0", "0", "0", "1");
        %>
        window.location = "<%=href%>" + "&search=" + document.searchform.search.value + "&description=" + document.searchform.search.value;

        return false;
    }

    function OnSubmitJoinOurListForm()
    {
         if(checkEmail(document.joinourlistform.emailaddress.value) == false)
         {
            document.joinourlistform.emailaddress.focus();
            return false;
         }
         return true;
    }
</SCRIPT>
<td vAlign="top" bgcolor="<%=lpTheme.getcolor2()%>" nowrap rowspan="500">
    &nbsp;&nbsp;
</td>
<td vAlign="top" bgcolor="<%=lpTheme.getcolor2()%>" rowspan="500">
    <form name="searchform" action="<%=StoreFrontUrls.getsecureurl(request, lpCompany, "search_result")%>" method="get" onsubmit="return(OnSubmitSearchForm());">
    <input name="user" type="hidden" value="<%=request.getParameter("user")==null?"":request.getParameter("user")%>"/>
    <input name="login" type="hidden" value="<%=request.getParameter("login")==null?"":request.getParameter("login")%>"/>
    <TABLE summary="<%=request.getAttribute("keyword1")%>" width="160" cellSpacing="0" cellPadding="0" border="0">
        <input type="hidden" name="page" value="1">
        <tr>
            <td><img src="<%=lpTheme.getimagebaseurl()%>spacer.gif" width="5" height="10"></td>
        </tr>
        <tr>
            <td colspan="3"><img alt="<%=request.getAttribute("keyword1")%> and <%=request.getAttribute("keyword2")%> product search" src="<%=lpTheme.getimagebaseurl()%>product_search.gif" width="150" height="20"></td>
        </tr>
        <tr>
            <td><img src="<%=lpTheme.getimagebaseurl()%>spacer.gif" width="5" height="3"></td>
        </tr>
        <tr>
            <td><input name="search" type="text" size="20"></td>
            <td><input type="image" alt="<%=request.getAttribute("keyword1")%> and <%=request.getAttribute("keyword2")%>" src="<%=lpTheme.getimagebaseurl()%>go_button.gif"></td>
            <td><img src="<%=lpTheme.getimagebaseurl()%>spacer.gif" width="8" height="1"/></td>
        </tr>
    </TABLE>
    </form>

<%
    if(lpTheme.getbrandsfirst() == true)
    {
%>
        <%@ include file="leftpane_brands.jsp" %>
        <%@ include file="leftpane_groups.jsp" %>
<%
    }
    else
    {
%>
        <%@ include file="leftpane_groups.jsp" %>
        <%@ include file="leftpane_brands.jsp" %>
<%
    }
%>
    <TABLE summary="<%=request.getAttribute("keyword1")%>" cellSpacing="0" cellPadding="0" border="0">
        <tr>
            <td><img alt="<%=request.getAttribute("keyword1")%> and <%=request.getAttribute("keyword2")%> - Help" src="<%=lpTheme.getimagebaseurl()%>help.gif" width="150" height="20"></td>
        </tr>
        <tr>
            <td><a href="<%=StoreFrontUrls.geturl(request, lpCompany, "contactus")%>" title="<%=request.getAttribute("keyword1")%> and <%=request.getAttribute("keyword2")%> - Contact Us">Contact Us</a></td>
        </tr>
        <tr>
            <td><a href="<%=StoreFrontUrls.geturl(request, lpCompany, "returnpolicy")%>" title="<%=request.getAttribute("keyword1")%> and <%=request.getAttribute("keyword2")%> - Return Policy">Return Policy</a></td>
        </tr>
        <tr>
            <td><a href="<%=StoreFrontUrls.geturl(request, lpCompany, "privacysecurity")%>" title="<%=request.getAttribute("keyword1")%> and <%=request.getAttribute("keyword2")%> - Privacy & Security">Privacy & Security</a></td>
        </tr>
        <tr>
            <td><a href="<%=StoreFrontUrls.geturl(request, lpCompany, "resources")%>" title="<%=request.getAttribute("keyword1")%> and <%=request.getAttribute("keyword2")%> - Resources">Resources</a></td>
        </tr>
        <tr>
            <td><a href="<%=StoreFrontUrls.geturl(request, lpCompany, lpCompany.getsitemap())%>" title="<%=request.getAttribute("keyword1")%> and <%=request.getAttribute("keyword2")%> - Site Map">Site Map</a></td>
        </tr>
    </TABLE>
    <form name="joinourlistform" action="<%=StoreFrontUrls.getsecureurl(request, lpCompany, "joinourlist")%>" method="get" onsubmit="return(OnSubmitJoinOurListForm());">
    <input name="user" type="hidden" value="<%=request.getParameter("user")==null?"":request.getParameter("user")%>"/>
    <input name="login" type="hidden" value="<%=request.getParameter("login")==null?"":request.getParameter("login")%>"/>
    <TABLE summary="<%=request.getAttribute("keyword1")%>" width="160" cellSpacing="0" cellPadding="0" border="0">
        <tr>
            <td colspan="3"><img alt="<%=request.getAttribute("keyword1")%> and <%=request.getAttribute("keyword2")%> - Join Our List" src="<%=lpTheme.getimagebaseurl()%>join_our_list.gif" width="150" height="20"></td>
        </tr>
        <tr>
            <td><img src="<%=lpTheme.getimagebaseurl()%>spacer.gif" width="5" height="3"></td>
        </tr>
        <tr>
            <td colspan="3">Enter your e-mail address</td>
        </tr>
        <tr>
            <td colspan="3"><input name="emailaddress" type="text" size="26"></td>
        </tr>
        <tr>
            <td><img src="<%=lpTheme.getimagebaseurl()%>spacer.gif" width="5" height="3"></td>
        </tr>
        <tr>
            <td colspan="3"><input type="image" alt="<%=request.getAttribute("keyword1")%> and <%=request.getAttribute("keyword2")%>" src="<%=lpTheme.getimagebaseurl()%>join_button.gif"></td>
        </tr>
    </TABLE>
    </form>
    <table summary="<%=request.getAttribute("keyword2")%>" border="0">
        <tr>
            <td><img src="<%=lpTheme.getimagebaseurl()%>spacer.gif" width="1" height="200"/></td>
        </tr>
    </table>
</td>
