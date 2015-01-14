<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    Theme theme = null;
    Company company = null;
    try {
        CompanyBean companyBean = new CompanyBean();
        GetThemeRequest getThemeRequest = new GetThemeRequest();
        getThemeRequest.setname("corporate");
        GetThemeResponse getThemeResponse = companyBean.GetTheme(request, getThemeRequest);
        theme = getThemeResponse.gettheme();

        GetCompanyResponse getCompanyResponse = companyBean.GetCompany(request, new GetCompanyRequest());
        company = getCompanyResponse.getcompany();
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<%
    try {
        UserBean userBean = new UserBean();
        GetUserResponse getUserResponse = userBean.GetUser(request, response);

        // Do we have items in the shopping cart?
        GetShoppingCartRequest getShoppingCartRequest = new GetShoppingCartRequest();
        getShoppingCartRequest.setid(getUserResponse.getuser().getid());
        GetShoppingCartResponse getShoppingCartResponse = userBean.GetShoppingCart(getShoppingCartRequest);
        Iterator shoppingCartIterator = getShoppingCartResponse.getshoppingcartitemsIterator();
        int shoppingCartItems = 0;
        for( ; shoppingCartIterator.hasNext() ; shoppingCartIterator.next())
        {
            shoppingCartItems++;
        }

        if(shoppingCartItems == 0)
        {
            response.sendRedirect(StoreFrontUrls.geturl(request, company, "shoppingcart"));
        }
        // If we're already logged in, go to the Confirm Bill/Ship page
        else if(getUserResponse.getuser() != null && getUserResponse.getuser().getloggedin())
        {
            response.sendRedirect(StoreFrontUrls.getsecureurl(request, company, "confirmbillship"));
        }
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<HTML>
    <%@ include file="commonscript.jsp" %>
    <SCRIPT LANGUAGE="JavaScript">
        function OnSubmitForm()
        {
            // Existing account
            if(document.form1.radio[0].checked)
            {
                if(checkEmail(document.form1.emailaddress.value) == false)
                {
                    document.form1.emailaddress.focus();
                    return false;
                }
                if(document.form1.password.value.length < 6)
                {
                    alert("Password must be at least 6 characters");
                    document.form1.emailaddress.focus();
                    return false;
                }

                return true;
            }
            // Create an account
            else if(document.form1.radio[1].checked)
            {
                document.form1.action = "<%=StoreFrontUrls.getsecureurl(request, company, "createaccount", company.getbasesecureurl() + "confirmbillship.jsp")%>";
            }
            // Checkout without an account
            else
            {
                document.form1.action = "<%=StoreFrontUrls.getsecureurl(request, company, "checkoutnoaccount")%>";
            }

            return true;
        }
    </SCRIPT>
    <HEAD>
        <%@ include file="titlemetadata.jsp" %>
        <LINK href="<%=company.getbaseurl()%>storefront.css" rel="STYLESHEET"></LINK>
    </HEAD>
    <BODY leftMargin="0" topMargin="0" onload="" marginwidth="0" marginheight="0">
        <%@ include file="toppane.jsp" %>
        <table cellSpacing="0" cellPadding="0" border="0">
            <tr>
                <td vAlign="top" width="20">
                    <IMG src="<%=theme.getimagebaseurl()%>spacer.gif" border="0">
                </td>
                <td vAlign="top">
                    <table id="headingTable" border="0">
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="5" height="15"></td>
                        </tr>
                    </table>
                    <form name="form1" action="<%=company.getbasesecureurl()%>checkoutmethod_result.jsp" method="post" onsubmit="return(OnSubmitForm());">
                    <input name="user" type="hidden" value="<%=request.getParameter("user")==null?"":request.getParameter("user")%>"/>
                    <input name="login" type="hidden" value="<%=request.getParameter("login")==null?"":request.getParameter("login")%>"/>
                    <input name="gotourl" type="hidden" value="<%=request.getParameter("gotourl")==null?company.getbasesecureurl()+"confirmbillship.jsp":request.getParameter("gotourl")%>" />
                    <table cellSpacing="1" cellPadding="1" width="200" border="0">
                        <tr>
                            <td><br></td>
                        </tr>
                    <%
                        if(request.getParameter("error") != null)
                        {
                    %>
                        <tr>
                            <td nowrap colspan="2" class="errorText"><%=request.getParameter("error")%></td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                    <%
                        }
                    %>
                        <tr bgcolor="<%=theme.getcolor2()%>">
                            <td colspan="5" nowrap class="producttitle" width="500">Please select a checkout method</td>
                        </tr>
                        <tr>
                            <td colspan="3"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="1" height="15" /></td>
                        </tr>
                        <tr>
                            <td align="right"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="20" height="1"/><input name="radio" type="radio" name="action" checked /></td>
                            <td colspan="3" class="generaltitle">I have an existing account</td>
                        </tr>
                        <tr>
                            <td></td>
                            <td align="right">Enter your e-mail address:</td>
                            <td><input name="emailaddress" type="text" size="25"/></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td align="right">Password:</td>
                            <td><input name="password" type="password" size="25"/></td>
                        </tr>
                        <tr>
                            <td colspan="3"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="1" height="10"/></td>
                        </tr>
                        <tr>
                            <td align="right"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="20" height="1"/><input name="radio" type="radio" name="action" value="new-tmp"/></td>
                            <td colspan="3" class="generaltitle">Create a new account</td>
                        </tr>
                        <tr>
                            <td colspan="3"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="1" height="10"/></td>
                        </tr>
                        <tr>
                            <td align="right"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="20" height="1"/><input name="radio" type="radio" name="action" value="new-tmp"/></td>
                            <td colspan="3" class="generaltitle">Check out without an account</td>
                        </tr>
                        <tr>
                            <td colspan="3"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="1" height="10"/></td>
                        </tr>
                        <tr>
                            <td colspan="3"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="10" height="1"/><input type="image" alt="continue" src="<%=theme.getimagebaseurl()%>continue_button.gif"/></td>
                        </tr>
                    </table>
                    </form>
                </td>
            </tr>
        </table>
        <%@ include file="bottompane.jsp" %>
    </BODY>
    <script LANGUAGE="JavaScript">
        document.form1.emailaddress.focus();
    </script>
</HTML>

