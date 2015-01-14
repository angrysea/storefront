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
        GetThemeResponse getThemeResponse = companyBean.GetTheme(request, getThemeRequest);
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
    String sortby = "id";
    try {
        CustomerBean customerBean = new CustomerBean();
        GetCustomersRequest getCustomersRequest = new GetCustomersRequest();
        if(request.getParameter("sortby") != null)
            sortby = request.getParameter("sortby");
        getCustomersRequest.setorderby(sortby);
        getCustomersRequest.setdirection("asc");
        getCustomersResponse = customerBean.GetCustomers(getCustomersRequest);
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<HTML>
    <%@ include file="datevalidation.jsp" %>
    <SCRIPT LANGUAGE="JavaScript">
        function OnChangeSortBy()
        {
            document.form1.submit();
        }
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
                            <td class="producttitle">Customers</td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                    </table>
                    <form name="form1" action="./customers.jsp" method="GET">
                    <table border="0">
                        <tr>
                            <td noWrap>Sort By:</td>
                            <td>
                                <select name="sortby" onchange="OnChangeSortBy()">
                                    <option value="id">ID</option>
                                    <option value="last">Last Name</option>
                                    <option value="first">First Name</option>
                                    <option value="id">Created</option>
                                </select>
                            </td>
                        </tr>
                    </table>
                    </form>
                    <TABLE cellSpacing="1" cellPadding="3" width="700" border="0">
                        <tr>
                            <td align="left"><b>ID</b></td>
                            <td align="left"><b>Last Name</b></td>
                            <td align="left"><b>First Name</b></td>
                            <td align="left"><b>E-Mail</b></td>
                            <td align="left"><b>Created</b></td>
                            <td align="left"><b>Last Login</b></td>
                        </tr>
                        <tr>
                            <td colspan="10">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td bgcolor="<%=theme.getcolor1()%>"><img src="./spacer.gif" width="700" height="1" /></td>
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
                                if(customer.getlast() != null && customer.getlast().length() > 0)
                                {
                        %>
                                <tr>
                                    <td><a href="./customer.jsp?customerid=<%=new Integer(customer.getid()).toString()%>"><%=new Integer(customer.getid()).toString()%></td>
                                    <td><%=customer.getlast()==null?"":customer.getlast()%></td>
                                    <td><%=customer.getfirst()==null?"":customer.getfirst()%></td>
                                    <td><%=customer.getemail1()==null?"":customer.getemail1()%></td>
                                    <td><%=dateFormat.format(user.getcreationdate())%></td>
                                    <td><%=user.getlastlogindate()==null?"":dateFormat.format(user.getlastlogindate())%></td>
                                </tr>
                        <%
                                }
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
        document.form1.sortby.value = '<%=sortby%>';
    </SCRIPT>
</HTML>
