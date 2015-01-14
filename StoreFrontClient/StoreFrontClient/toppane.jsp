<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    Company tpCompany = null;
    Theme tpTheme = null;
    try {
        CompanyBean companyBean = new CompanyBean();
        GetCompanyResponse getCompanyResponse = companyBean.GetCompany(request, new GetCompanyRequest());
        tpCompany = getCompanyResponse.getcompany();

        GetThemeRequest getThemeRequest = new GetThemeRequest();
        getThemeRequest.setname("corporate");
        GetThemeResponse getThemeResponse = companyBean.GetTheme(request, getThemeRequest);
        tpTheme = getThemeResponse.gettheme();
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<%
    GetUserResponse getTPUserResponse = null;
    int tpShoppingCartItems = 0;
    try {
        UserBean userBean = new UserBean();
        getTPUserResponse = userBean.GetUser(request, response);

        if(getTPUserResponse.getuser().getid()>0)
        {
            GetShoppingCartRequest getTPShoppingCartRequest = new GetShoppingCartRequest();
            getTPShoppingCartRequest.setid(getTPUserResponse.getuser().getid());
            GetShoppingCartResponse getTPShoppingCartResponse = userBean.GetShoppingCart(getTPShoppingCartRequest);
            Iterator tpShoppingCartIterator = getTPShoppingCartResponse.getshoppingcartitemsIterator();
            for( ; tpShoppingCartIterator.hasNext() ; tpShoppingCartIterator.next())
            {
               tpShoppingCartItems++;
            }
	 }

        // Store stats
        WebStatsBean webStatsBean = new WebStatsBean();
        webStatsBean.AddWebStat(request, getTPUserResponse.getuser());
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<SCRIPT LANGUAGE="JavaScript">
    function OnClickTopBarLink(link)
    {
        window.location = link;
    }
</SCRIPT>
<TABLE summary="<%=request.getAttribute("keyword1")%>" valign="0" cellSpacing="0" cellPadding="0" border="0">
    <TR>
        <TD><a href="<%=StoreFrontUrls.geturl(request, tpCompany, "index")%>"><img border="0" alt="<%=request.getAttribute("keyword1")%> and <%=request.getAttribute("keyword2")%> - <%=tpCompany.getcompany()%>" src="<%=tpTheme.getimagebaseurl()%>logo.gif" /></a></TD>
        <TD width="560" height="10" background="<%=tpTheme.getimagebaseurl()%>spacer.gif"></TD>
        <TD align="right" width="180" height="82" background="<%=tpTheme.getimagebaseurl()%>scenery.gif" title="<%=request.getAttribute("keyword1")%> and <%=request.getAttribute("keyword2")%> - <%=tpCompany.getcompany()%>"></TD>
    </TR>
</TABLE>
<TABLE summary="<%=request.getAttribute("keyword2")%>" align="0" cellSpacing="0" cellPadding="0" width="100%" border="0">
    <TR>
        <TD><img src="<%=tpTheme.getimagebaseurl()%>filler_left.gif" width="300" height="24"></TD>
        <TD><input alt="home" type="image" src="<%=tpTheme.getimagebaseurl()%>home.gif" width="94" height="24" onclick="OnClickTopBarLink('<%=StoreFrontUrls.geturl(request, tpCompany, "index")%>')" / title="<%=request.getAttribute("keyword1")%> and <%=request.getAttribute("keyword2")%> - Home"></TD>
        <TD><input alt="your account" type="image" src="<%=tpTheme.getimagebaseurl()%>youraccount.gif" width="94" height="24" onclick="OnClickTopBarLink('<%=StoreFrontUrls.geturl(request, tpCompany, "youraccount")%>')" / title="<%=request.getAttribute("keyword1")%> and <%=request.getAttribute("keyword2")%> - Your account"></TD>
        <TD><input alt="shopping cart" type="image" src="<%=tpTheme.getimagebaseurl()%>cart.gif" width="94" height="24" onclick="OnClickTopBarLink('<%=StoreFrontUrls.geturl(request, tpCompany, "shoppingcart")%>')"/ title="<%=request.getAttribute("keyword1")%> and <%=request.getAttribute("keyword2")%> - Cart"></TD>
        <TD><input alt="checkout" type="image" src="<%=tpTheme.getimagebaseurl()%>checkout.gif" width="94" height="24" onclick="OnClickTopBarLink('<%=StoreFrontUrls.geturl(request, tpCompany, "checkoutmethod")%>')" / title="<%=request.getAttribute("keyword1")%> and <%=request.getAttribute("keyword2")%> - Checkout"></TD>
        <TD><input alt="contact us" type="image" src="<%=tpTheme.getimagebaseurl()%>contactus.gif" width="94" height="24" onclick="OnClickTopBarLink('<%=StoreFrontUrls.geturl(request, tpCompany, "contactus")%>')" / title="<%=request.getAttribute("keyword1")%> and <%=request.getAttribute("keyword2")%> - Contact us"></TD>
        <TD width="100%" background="<%=tpTheme.getimagebaseurl()%>toolbar_filler.gif"><img src="<%=tpTheme.getimagebaseurl()%>toolbar_filler.gif" width="5" height="24"></TD>
    </TR>
    <tr>
        <%
            if(getTPUserResponse.getuser() != null && getTPUserResponse.getuser().getloggedin())
            {
        %>
                <td nowrap colspan="3" align="center" width="450">
                    Welcome <%=getTPUserResponse.getuser().getname()%>,  Click here to <a href="<%=StoreFrontUrls.getsecureurl(request, tpCompany, "logout", request.getRequestURL().toString())%>">log out</a>
                </td>
        <%
            }
            else
            {
        %>
                <td colspan="3" align="center" width="525">Click here to <a href="<%=StoreFrontUrls.getsecureurl(request, tpCompany, "login", request.getRequestURL().toString())%>">log in</a></td>
        <%
            }
        %>
            <td nowrap colspan="4">
                <img src="<%=tpTheme.getimagebaseurl()%>spacer.gif" width="15" height="1" />
                <%=new Integer(tpShoppingCartItems).toString()%> item(s) in your <a href="<%=StoreFrontUrls.geturl(request, tpCompany, "shoppingcart")%>">cart</a>
            </td>
    </tr>
</TABLE>
