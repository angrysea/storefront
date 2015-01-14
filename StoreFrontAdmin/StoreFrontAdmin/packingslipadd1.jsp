<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    Theme theme = null;
    try {
        CompanyBean companyBean = new CompanyBean();
        GetThemeRequest getThemeRequest = new GetThemeRequest();
        getThemeRequest.setname("corporate");
        GetThemeResponse getThemeResponse = companyBean.GetTheme(getThemeRequest);
        theme = getThemeResponse.gettheme();
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<%
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    GetCustomersResponse getCustomersResponse = null;
    try {
        CustomerBean customerBean = new CustomerBean();
        getCustomersResponse = customerBean.GetCustomersWithOpenOrders(new GetCustomersRequest());
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<HTML>
    <%@ include file="datevalidation.jsp" %>
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
                    <table border="0">
                        <tr>
                            <td><img src="./images/spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td class="producttitle">Create a Packing Slip</td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                        <tr>
                            <td><h2>Step 1 - Select a customer with an open sales order</h2></td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                    </table>
                    <TABLE cellSpacing="1" cellPadding="3" width="500" border="0">
                        <tr>
                            <td align="left"><b>ID</b></td>
                            <td align="left"><b>Last Name</b></td>
                            <td align="left"><b>First Name</b></td>
                            <td align="left"><b>E-Mail</b></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td colspan="10">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td bgcolor="<%=theme.getcolor1()%>"><img src="./spacer.gif" width="500" height="1" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <%
                            UserBean userBean = new UserBean();

                            Iterator itCustomers = getCustomersResponse.getcustomerIterator();
                            while(itCustomers.hasNext())
                            {
                                Customer customer = (Customer)itCustomers.next();
                                User user = userBean.GetUser(customer.getid());
                        %>
                                <tr>
                                    <td><%=new Integer(customer.getid()).toString()%></td>
                                    <td><%=customer.getlast()%></td>
                                    <td><%=customer.getfirst()%></td>
                                    <td><%=customer.getemail1()%></td>
                                    <td nowrap><a href="./packingslipadd2.jsp?customerid=<%=new Integer(customer.getid()).toString()%>">select</a></td>
                                </tr>
                        <%
                            }
                        %>
                        <tr>
                            <td><br></td>
                        </tr>
                    </TABLE>
                </td>
            </tr>
        </table>
    </BODY>
    <SCRIPT LANGUAGE="JavaScript">
    </SCRIPT>
</HTML>
