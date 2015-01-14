<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    Theme bpTheme = null;
    Company bpCompany = null;
    String yearString = null;
    try {
        CompanyBean companyBean = new CompanyBean();
        GetCompanyRequest getCompanyRequest = new GetCompanyRequest();
        if(request.getParameter("company") != null)
            getCompanyRequest.setid(new Integer(request.getParameter("company")).intValue());
        GetCompanyResponse getCompanyResponse = companyBean.GetCompany(request, getCompanyRequest);
        bpCompany = getCompanyResponse.getcompany();

        GetThemeRequest getThemeRequest = new GetThemeRequest();
        getThemeRequest.setid(new Integer(bpCompany.getthemeid()).toString());
        GetThemeResponse getThemeResponse = companyBean.GetTheme(request, getThemeRequest);
        bpTheme = getThemeResponse.gettheme();

        yearString = new Integer(new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime())).toString();
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>


<table summary="<%=request.getAttribute("keyword1")%>" width="350" align="center">
    <tr>
        <td><br></td>
    </tr>
    <tr>
        <td><br></td>
    </tr>
    <tr>
        <td><br></td>
    </tr>
    <tr>
        <td><br></td>
    </tr>
    <tr align="center">
        <td nowrap>    <a href="<%=bpCompany.getbaseurl()%>minisite_index.jsp?company=<%=new Integer(bpCompany.getid()).toString()%>" title="<%=request.getAttribute("keyword1")%> Home">Home</a>
		| <a href="<%=bpCompany.getbaseurl()%>minisite_contactus.jsp?company=<%=new Integer(bpCompany.getid()).toString()%>" title="<%=request.getAttribute("keyword1")%> - Contact Us">Contact Us</a>
		| <a href="<%=bpCompany.getbaseurl()%>minisite_resources.jsp?company=<%=new Integer(bpCompany.getid()).toString()%>" title="<%=request.getAttribute("keyword1")%> - Resources">Resources</a>
		| <a href="<%=bpCompany.getbaseurl()%>minisite_sitemap.jsp?company=<%=new Integer(bpCompany.getid()).toString()%>" title="<%=request.getAttribute("keyword1")%> - Sitemap">Sitemap</a>
        </td>
    </tr>
</table>
<br>
<table summary="<%=request.getAttribute("keyword1")%>" width="350" align="center">
    <tr>
  	<td height="5"><img src="<%=bpTheme.getimagebaseurl()%>spacer.gif" height="5" width="1" border="0"></td>
    </tr>

    <tr>
        <td><CENTER>Copyright © <%=yearString%> <%=bpCompany.getcompany()%>. All Rights Reserved.</CENTER></td>
    </tr>
    <tr>
        <td><br /></td>
    </tr>
</table>
