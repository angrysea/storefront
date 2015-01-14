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

<%
    DecimalFormat rpMoneyFormat = new DecimalFormat("$#,###.00");

    GetItemRankingResponse getItemRankingResponse = null;
    try {
        ItemRankingBean itemRankingBean = new ItemRankingBean();
        GetItemRankingRequest getItemRankingRequest = new GetItemRankingRequest();
        getItemRankingRequest.setcount(rpTheme.getmostpopularcount());
        getItemRankingResponse = itemRankingBean.GetItemRankingViewed(getItemRankingRequest);
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<SCRIPT LANGUAGE="JavaScript">
</SCRIPT>
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
    <table cellSpacing="0" cellPadding="0" border="0" bgcolor="<%=rpTheme.getcolor1()%>" width="164">
        <tr>
            <td align="center">
                <table width="162" cellSpacing="0" cellPadding="0" border="0" bgcolor="#ffffff">
                    <tr>
                        <td colspan="3"><img align="Most Popular" src="<%=rpTheme.getimagebaseurl()%>most_popular.gif"></td>
                    </tr>
                </table>
                <table width="162" cellSpacing="0" cellPadding="0" border="0" bgcolor="#ffffff">
                    <%
                        if(getItemRankingResponse != null)
                        {
                            Iterator itItems = getItemRankingResponse.getitemsIterator();
                            while(itItems.hasNext())
                            {
                                Item item = (Item)itItems.next();
                    %>
                                <tr>
                                    <td><br></td>
                                </tr>
                                <tr>
                                    <td colspan="3" align="center"><a href="<%=StoreFrontUrls.getproductdetails(request, rpCompany, item)%>"><img border="0" alt="<%=rpCompany.getkeyword()%> - <%=item.getmanufacturer()%> <%=item.getproductname()%>" src="<%=rpTheme.getimagebaseurl()%><%=item.getdetails().getimageUrlSmall()%>"/></a></td>
                                </tr>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td align="center"><b><%=item.getmanufacturer()%> <%=item.getproductname()%></b></td>
                                    <td>&nbsp;</td>
                                </tr>
                                <tr>
                                    <td colspan="3" align="center" nowrap><strike><%=rpMoneyFormat.format(item.getlistprice())%></strike> <font color="red"><%=rpMoneyFormat.format(item.getourprice())%></font></td>
                                </tr>
                                <tr>
                                    <td><br></td>
                                </tr>
                                <%
                                    if(itItems.hasNext())
                                    {
                                %>
                                        <tr>
                                            <td colspan="3" align="center">
                                                <table border="0" cellpadding="0" cellspacing="0">
                                                    <tr>
                                                        <td bgcolor="<%=rpTheme.getcolor1()%>"><img src="<%=rpTheme.getimagebaseurl()%>spacer.gif" width="140" height="1"></td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                <%
                                    }
                                %>
                    <%
                            }
                        }
                    %>
                    <tr>
                        <td colspan="3" align="center" bgcolor="<%=rpTheme.getcolor1()%>"><img src="<%=rpTheme.getimagebaseurl()%>spacer.gif" width="160" height="1"></td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    <br>
</td>
