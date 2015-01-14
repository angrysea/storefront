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
    Customer customer = null;
    try {
        UserBean userBean = new UserBean();
        GetUserResponse getUserResponse = userBean.GetUser(request, response, true);

        // Are they logged in?
        if(getUserResponse.getuser() !=null && getUserResponse.getuser().getloggedin())
        {
            CustomerBean customerBean = new CustomerBean();
            GetCustomerRequest getCustomerRequest = new GetCustomerRequest();
            getCustomerRequest.setid(getUserResponse.getuser().getid());
            GetCustomerResponse getCustomerResponse = customerBean.GetCustomer(getCustomerRequest);
            customer = getCustomerResponse.getcustomer();
        }
        else
        {
            // failed, go back to the login screen
            response.sendRedirect(StoreFrontUrls.getsecureurl(request, company, "login", StoreFrontUrls.getsecureurl(request, company, "youraccount")));
        }

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
        <table cellSpacing="0" cellPadding="0" border="0">
            <tr>
                <td vAlign="top" width="20">
                    <IMG src="<%=theme.getimagebaseurl()%>spacer.gif" border="0">
                </td>
                <td vAlign="top">
                    <table id="headingTable" border="0">
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="5" height="5"></td>
                        </tr>
                    </table>
                    <form name="form1" action="" method="post">
                    <table border="0" cellpadding="0" cellpadding="0">
                        <tr bgcolor="<%=theme.getcolor2()%>">
                            <td nowrap class="producttitle" width="500">Account Information for <%=customer==null?"":customer.getfirst()%> <%=customer==null?"":customer.getlast()%></td>
                        </tr>
                        <tr>
                            <td colspan="2"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="1" height="4" /></td>
                        </tr>
                        <tr>
                            <td><a href="<%=StoreFrontUrls.getsecureurl(request, company, "changeemail")%>">Change E-Mail</a></td>
                        </tr>
                        <tr>
                            <td>Change your e-mail address here.</td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td><a href="<%=StoreFrontUrls.getsecureurl(request, company, "changepassword")%>">Change Password</a></td>
                        </tr>
                        <tr>
                            <td>Change your password here.</td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td><a href="<%=StoreFrontUrls.getsecureurl(request, company, "confirmbillship", StoreFrontUrls.getsecureurl(request, company, "confirmbillship_result_youraccount"))%>">Change Billing / Shipping Address</a></td>
                        </tr>
                        <tr>
                            <td>Moved recently? Change your billing and shipping address.</td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td><a href="<%=StoreFrontUrls.getsecureurl(request, company, "creditcard")%>">Credit Card</a></td>
                        </tr>
                        <tr>
                            <td>Change or update your credit card information here.</td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td><a href="<%=StoreFrontUrls.getsecureurl(request, company, "orderhistory")%>">Order History</a></td>
                        </tr>
                        <tr>
                            <td>View your past purchases.</td>
                        </tr>
                    </table>
                    </form>
                </td>
            </tr>
        </table>
        <%@ include file="bottompane.jsp" %>
    </BODY>
</HTML>

