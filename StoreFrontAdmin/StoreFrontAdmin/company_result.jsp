<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    try {
        CompanyBean companyBean = new CompanyBean();
        UpdateCompanyRequest updateCompanyRequest = new UpdateCompanyRequest();
        Company company = new Company();

        company.setid(new Integer(request.getParameter("id")).intValue());
        if(request.getParameter("companyname") != null)
            company.setcompany(request.getParameter("companyname"));
        if(request.getParameter("description") != null)
            company.setdescription(request.getParameter("description"));
        if(request.getParameter("address1") != null)
            company.setaddress1(request.getParameter("address1"));
        if(request.getParameter("address2") != null)
            company.setaddress2(request.getParameter("address2"));
        if(request.getParameter("address2") != null)
            company.setaddress2(request.getParameter("address2"));
        if(request.getParameter("address3") != null)
            company.setaddress3(request.getParameter("address3"));
        if(request.getParameter("city") != null)
            company.setcity(request.getParameter("city"));
        if(request.getParameter("state") != null)
            company.setstate(request.getParameter("state"));
        if(request.getParameter("zipcode") != null)
            company.setzip(request.getParameter("zipcode"));
        if(request.getParameter("countrycode") != null)
            company.setcountry(request.getParameter("countrycode"));
        if(request.getParameter("primarytelephone") != null)
            company.setphone(request.getParameter("primarytelephone"));
        if(request.getParameter("customerservice") != null)
            company.setcustomerservice(request.getParameter("customerservice"));
        if(request.getParameter("fax") != null)
            company.setfax(request.getParameter("fax"));
        if(request.getParameter("email1") != null)
            company.setemail1(request.getParameter("email1"));
        if(request.getParameter("email2") != null)
            company.setemail2(request.getParameter("email2"));
        if(request.getParameter("email3") != null)
            company.setemail3(request.getParameter("email3"));
        if(request.getParameter("baseurl") != null)
            company.setbaseurl(request.getParameter("baseurl"));
        if(request.getParameter("basesecureurl") != null)
            company.setbasesecureurl(request.getParameter("basesecureurl"));
        if(request.getParameter("visualurl") != null)
            company.seturl(request.getParameter("visualurl"));
        if(request.getParameter("impersonatepassword") != null)
            company.setpw(request.getParameter("impersonatepassword"));
        if(request.getParameter("usemodrewrite") != null && request.getParameter("usemodrewrite").compareToIgnoreCase("on")==0)
            company.setusemodrewrite(true);
        else
            company.setusemodrewrite(false);
        if(request.getParameter("modrewritekeyword") != null)
            company.setkeyword(request.getParameter("modrewritekeyword"));

        updateCompanyRequest.setcompany(company);
        companyBean.UpdateCompany(updateCompanyRequest);
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
        <LINK href="./storefront.css" rel="STYLESHEET"></LINK>
    </HEAD>
    <BODY leftMargin="0" topMargin="0" onload="" marginwidth="0" marginheight="0">
        <%@ include file="toppane.jsp" %>
        <table cellSpacing="0" cellPadding="0" border="0">
            <tr>
                <%@ include file="leftpane.jsp" %>
                <td vAlign="top" width="20">
                    <IMG src="./images/spacer.gif" border="0">
                </td>
                <td vAlign="top">
                    <table id="headingTable" border="0">
                        <tr>
                            <td><img src="./images/spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td class="producttitle">Company Update Result</td>
                        </tr>
                    </table>
                    <br>
                    <TABLE cellSpacing="1" cellPadding="3" width="95%" border="0">
                    <tr>
                        <td noWrap>The company information has been updated successfully.</td>
                    </tr>
                    </TABLE>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>

