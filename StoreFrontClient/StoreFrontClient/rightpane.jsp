<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    Theme rpTheme = null;
    Company rpCompany = null;
    try {
        CompanyBean rpCompanyBean = new CompanyBean();
        GetThemeRequest rpGetThemeRequest = new GetThemeRequest();
        rpGetThemeRequest.setname("corporate");
        GetThemeResponse rpGetThemeResponse = rpCompanyBean.GetTheme(request, rpGetThemeRequest);
        rpTheme = rpGetThemeResponse.gettheme();

        GetCompanyResponse getCompanyResponse = rpCompanyBean.GetCompany(request, new GetCompanyRequest());
        rpCompany = getCompanyResponse.getcompany();
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<td valign="top" nowrap>
    <img src="<%=rpTheme.getimagebaseurl()%>spacer.gif" width="20" height="1" />
</td>
<td vAlign="top">
<%
if(rpCompany.getfreeshipping() > 0)
{
%>
    <table cellSpacing="0" cellPadding="0" border="0" bgcolor="<%=rpTheme.getcolor1()%>" width="164">
        <tr>
            <td align="center">
                <table width="162" cellSpacing="0" cellPadding="0" border="0" bgcolor="#ffffff">
                    <tr>
                        <td colspan="3" align="center" bgcolor="<%=rpTheme.getcolor1()%>"><img src="<%=rpTheme.getimagebaseurl()%>spacer.gif" width="160" height="1"></td>
                    </tr>
                    <tr>
                        <td colspan="3"><img align="Free Shipping" src="<%=rpTheme.getimagebaseurl()%>freeshipping.gif"></td>
                    </tr>
                    <tr>
                        <td colspan="3" align="center" bgcolor="<%=rpTheme.getcolor1()%>"><img src="<%=rpTheme.getimagebaseurl()%>spacer.gif" width="160" height="1"></td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    <br>
<%
}
%>
    <table cellSpacing="0" cellPadding="0" border="0" bgcolor="<%=rpTheme.getcolor1()%>" width="164">
        <tr>
            <td align="center">
                <table width="162" cellSpacing="0" cellPadding="0" border="0" bgcolor="#ffffff">
                    <tr>
                        <td colspan="3" align="center" bgcolor="<%=rpTheme.getcolor1()%>"><img src="<%=rpTheme.getimagebaseurl()%>spacer.gif" width="160" height="1"></td>
                    </tr>
                    <tr>
                        <td colspan="3"><a href="<%=StoreFrontUrls.geturl(request, rpCompany, "coupons")%>"><img border="0" align="Coupons" src="<%=rpTheme.getimagebaseurl()%>coupons.gif"></a></td>
                    </tr>
                    <tr>
                        <td colspan="3" align="center" bgcolor="<%=rpTheme.getcolor1()%>"><img src="<%=rpTheme.getimagebaseurl()%>spacer.gif" width="160" height="1"></td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    <br>
</td>
