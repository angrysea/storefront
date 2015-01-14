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

        // Get the current customer
        GetCustomerRequest getCustomerRequest = new GetCustomerRequest();
        getCustomerRequest.setid(getUserResponse.getuser().getid());
        GetCustomerResponse getCustomerResponse = customerBean.GetCustomer(getCustomerRequest);
        Customer customer = getCustomerResponse.getcustomer();

        boolean customerChanged = false;

        CreditCard creditcard = null;

	// was the bill to address changed?
        if(request.getParameter("billaddresschanged").compareToIgnoreCase("1") == 0)
        {
            customerChanged = true;

            // Cycle through the existing addresses and change the "currentbilling" address
            // to "billing"
            Iterator itAddresses = customer.getaddressIterator();
            while(itAddresses.hasNext())
            {
                Address address = (Address)itAddresses.next();
                if(address.gettype().compareToIgnoreCase("currentbilling") == 0)
                {
                    address.settype("billing");

                    // Save the credit card associated with the billing address
                    creditcard = address.getcreditcard();
                    break;
                }
            }

            // Save the customer information
            if(request.getParameter("emailaddress") != null && request.getParameter("emailaddress").trim().length() > 0)
                customer.setemail1(request.getParameter("emailaddress").trim());
            if(request.getParameter("billingtitle") != null)
                customer.setsalutation(request.getParameter("billingtitle"));
            customer.setfirst(request.getParameter("billingfirstname").trim());
            customer.setlast(request.getParameter("billinglastname").trim());
            if(request.getParameter("billingsuffix") != null)
                customer.setsuffix(request.getParameter("billingsuffix"));

            // Add the a new address with the information
            Address address = new Address();
            address.settype("currentbilling");
            if(request.getParameter("billingtitle") != null)
                address.setsalutation(request.getParameter("billingtitle"));
            address.setfirst(request.getParameter("billingfirstname").trim());
            address.setlast(request.getParameter("billinglastname").trim());
            if(request.getParameter("billingsuffix") != null)
                address.setsuffix(request.getParameter("billingsuffix"));
            address.setaddress1(request.getParameter("billingaddress1").trim());
            if(request.getParameter("billingaddress2") != null)
                address.setaddress2(request.getParameter("billingaddress2").trim());
            address.setcity(request.getParameter("billingcity").trim());
            if(request.getParameter("billingstate") != null)
                address.setstate(request.getParameter("billingstate"));
            address.setzip(request.getParameter("billingzipcode").trim());
            address.setcountry(request.getParameter("billingcountry"));
            address.setphone(request.getParameter("billingphone").trim());
            if(request.getParameter("billingcompanyname") != null)
                address.setcompany(request.getParameter("billingcompanyname").trim());
            if(creditcard != null)
                address.setcreditcard(creditcard);
            customer.setaddress(address);
        }

        // was the ship to address changed?
        if(request.getParameter("shipaddresschanged").compareToIgnoreCase("1") == 0)
        {
            customerChanged = true;

            // Cycle through the existing addresses and change the "currentshipping" address
            // to "shipping"
            Iterator itAddresses = customer.getaddressIterator();
            while(itAddresses.hasNext())
            {
                Address address = (Address)itAddresses.next();
                if(address.gettype().compareToIgnoreCase("currentshipping") == 0)
                {
                    address.settype("shipping");
                    break;
                }
            }

            // Add the a new address with the information
            Address address = new Address();
            address.settype("currentshipping");
            if(request.getParameter("shippingtitle") != null)
                address.setsalutation(request.getParameter("shippingtitle"));
            address.setfirst(request.getParameter("shippingfirstname").trim());
            address.setlast(request.getParameter("shippinglastname").trim());
            if(request.getParameter("shippingsuffix") != null)
                address.setsuffix(request.getParameter("shippingsuffix"));
            address.setaddress1(request.getParameter("shippingaddress1").trim());
            if(request.getParameter("shippingaddress2") != null)
                address.setaddress2(request.getParameter("shippingaddress2").trim());
            address.setcity(request.getParameter("shippingcity").trim());
            if(request.getParameter("shippingstate") != null)
                address.setstate(request.getParameter("shippingstate"));
            address.setzip(request.getParameter("shippingzipcode").trim());
            address.setcountry(request.getParameter("shippingcountry"));
            address.setphone(request.getParameter("shippingphone").trim());
            if(request.getParameter("shippingcompanyname") != null)
                address.setcompany(request.getParameter("shippingcompanyname").trim());
            customer.setaddress(address);
        }

	// Update the customer
        if(customerChanged)
        {
            UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest();
            updateCustomerRequest.setcustomer(customer);
            customerBean.UpdateCustomer(updateCustomerRequest);
        }

        String url = StoreFrontUrls.getsecureurl(request, company, "salesorder");
        if(request.getParameter("gotourl") != null && request.getParameter("gotourl").length() > 0)
            url = StoreFrontUrls.updateurl(request, company, request.getParameter("gotourl"));
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

