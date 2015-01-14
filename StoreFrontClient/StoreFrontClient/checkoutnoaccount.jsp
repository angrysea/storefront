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
        CustomerBean customerBean = new CustomerBean();

        GetUserResponse getUserResponse = userBean.GetUser(request, response);
        User olduser = getUserResponse.getuser();

        // Create a new user and a new customer
        AddUserRequest addUserRequest = new AddUserRequest();
        addUserRequest.setuser(olduser);
        AddUserResponse addUserResponse = userBean.AddUser(addUserRequest, request, response);

	// Convert the cart to the new user
        //userBean.ConvertShoppingCart(olduser.getid(), addUserResponse.getid());

	// Add a customer and associate it with the new user
        AddCustomerRequest addCustomerRequest = new AddCustomerRequest();
        Customer customer = new Customer();
        customer.setid(addUserResponse.getid());
        addCustomerRequest.setcustomer(customer);
        customerBean.AddCustomer(addCustomerRequest);

        response.sendRedirect(StoreFrontUrls.getsecureurl(request, company, "confirmbillship", StoreFrontUrls.getsecureurl(request, company, "salesorder")));
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>


<HTML>
    <HEAD>
        <%@ include file="titlemetadata.jsp" %>
        <LINK href="<%=company.getbaseurl()%>storefront.css" rel="STYLESHEET"></LINK>
    </HEAD>
    <BODY leftMargin="0" topMargin="0" onload="" marginwidth="0" marginheight="0">
    </BODY>
</HTML>

