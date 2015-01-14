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

        // Does an account with this e-mail address already exist?
        CheckUserRequest checkUserRequest = new CheckUserRequest();
        checkUserRequest.setemailaddress(request.getParameter("emailaddress").trim());
        CheckUserResponse checkUserResponse = userBean.CheckUser(checkUserRequest);
        if(checkUserResponse.getexists())
        {
            String url = StoreFrontUrls.getsecureurlex(request, company, "createaccount", "error=A user with this e-mail address already exists.");
            Enumeration enum = request.getParameterNames();
            while(enum.hasMoreElements())
            {
                String parameter = (String)enum.nextElement();
                url += "&" + parameter + "=" + request.getParameter(parameter);
            }

            response.sendRedirect(url);
            return;
        }

        // Create a new user
        GetUserResponse getUserResponse = userBean.GetUser(request, response, true);
        User olduser = getUserResponse.getuser();
        AddUserRequest addUserRequest = new AddUserRequest();
        addUserRequest.setuser(olduser);
        AddUserResponse addUserResponse = userBean.AddUser(addUserRequest, request, response);

	// Add a customer - associate it with the new user
        AddCustomerRequest addCustomerRequest = new AddCustomerRequest();
        Customer customer = new Customer();
        customer.setid(addUserResponse.getid());
        customer.setemail1(request.getParameter("emailaddress").trim());
        if(request.getParameter("title") != null && request.getParameter("title").compareToIgnoreCase("0") != 0)
            customer.setsalutation(request.getParameter("title"));
        customer.setfirst(request.getParameter("firstname").trim());
        customer.setlast(request.getParameter("lastname").trim());
        if(request.getParameter("suffix") != null && request.getParameter("suffix").compareToIgnoreCase("0") != 0)
            customer.setsuffix(request.getParameter("suffix"));
        Address billingaddress = new Address();
        customer.setaddress(billingaddress);
        billingaddress.settype("currentbilling");
        billingaddress.setfirst(request.getParameter("firstname").trim());
        billingaddress.setlast(request.getParameter("lastname").trim());
        billingaddress.setphone(request.getParameter("phone").trim());
        billingaddress.setaddress1(request.getParameter("billingaddress1").trim());
        if(request.getParameter("billingaddress2") != null)
            billingaddress.setaddress2(request.getParameter("billingaddress2").trim());
        billingaddress.setcity(request.getParameter("city").trim());
        billingaddress.setstate(request.getParameter("state"));
        billingaddress.setzip(request.getParameter("zipcode").trim());
        billingaddress.setcountry(request.getParameter("country"));
        if(request.getParameter("companyname") != null)
            billingaddress.setcompany(request.getParameter("companyname").trim());
        addCustomerRequest.setcustomer(customer);
        customerBean.AddCustomer(addCustomerRequest);

        // Update the new user
        User newuser = new User();
        newuser.setid(addUserResponse.getid());
        newuser.setemail(request.getParameter("emailaddress").trim());
        newuser.setpassword(request.getParameter("password"));
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setuser(newuser);
        userBean.UpdateUser(updateUserRequest);

        // now, log them in
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setemailaddress(request.getParameter("emailaddress").trim());
        loginRequest.setpassword(request.getParameter("password"));
        LoginResponse loginResponse = userBean.Login(loginRequest, request, response);

	// Redirect
        String url = request.getParameter("gotourl");
        if(url == null)
            url = StoreFrontUrls.geturl(request, company, "index");
        else
            url = StoreFrontUrls.updateurl(request, company, url);
        response.sendRedirect(url);
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

