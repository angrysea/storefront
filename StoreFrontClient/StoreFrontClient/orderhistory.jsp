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
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    DecimalFormat moneyFormat = new DecimalFormat("$#,##0.00");

    User user = null;
    GetSalesOrdersResponse getSalesOrdersResponse = null;
    try {
        UserBean userBean = new UserBean();
        GetUserResponse getUserResponse = userBean.GetUser(request, response);
        user = getUserResponse.getuser();

        SalesOrderBean salesOrderBean = new SalesOrderBean();
        GetSalesOrdersRequest getSalesOrdersRequest = new GetSalesOrdersRequest();
        getSalesOrdersRequest.setcustomerid(user.getid());
        getSalesOrdersResponse = salesOrderBean.GetSalesOrders(getSalesOrdersRequest);
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<HTML>
    <%@ include file="commonscript.jsp" %>
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
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="5" height="20"></td>
                        </tr>
                    </table>
                    <table cellSpacing="1" cellPadding="1" width="550" border="0">
                        <tr bgcolor="<%=theme.getcolor2()%>">
                            <td colspan="5" nowrap class="producttitle">Your order history</td>
                        </tr>
                        <tr>
                            <td colspan="2"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="1" height="4" /></td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td nowrap align="left"><b>Order Number</b></td>
                            <td nowrap aligh="left"><b>Order Date</b></td>
                            <td nowrap align="right"><b>Order Amount</b></td>
                            <td nowrap align="right"><b>Order Status</b></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td colspan="5">
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td bgcolor="<%=theme.getcolor1()%>"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="550" height="1" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <%
                            Iterator itSalesOrders = getSalesOrdersResponse.getsalesorderIterator();
                            boolean orders = false;
                            while(itSalesOrders.hasNext())
                            {
                                orders = true;
                                SalesOrder salesOrder = (SalesOrder)itSalesOrders.next();
                        %>
                        <tr>
                            <td nowrap align="left"><%=new Integer(salesOrder.getid()).toString()%></td>
                            <td nowrap align="left"><%=dateFormat.format(salesOrder.getcreationdate())%></td>
                            <td nowrap align="right"><%=moneyFormat.format(salesOrder.gettotal())%></td>
                            <td nowrap align="right"><%=salesOrder.getstatus()%></td>
                            <td nowrap align="right"><a href="<%=StoreFrontUrls.getorderdetails(request, company, salesOrder)%>">view order</a></td>
                        </tr>
                        <tr>
                            <td colspan="5">
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td bgcolor="<%=theme.getcolor1()%>"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="550" height="1" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <%
                            }

                            if(orders == false)
                            {
                        %>
                        <tr>
                            <td colspan="5" nowrap>There are no orders under this customer account.</td>
                        </tr>
                        <%
                            }
                        %>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td colspan="5" align="right"><a href="<%=StoreFrontUrls.getsecureurl(request, company, "youraccount")%>"><img alt="continue" border="0" src="<%=theme.getimagebaseurl()%>continue_button.gif"/></a></td>
                        </tr>
                    </table>
                    </form>
                </td>
            </tr>
        </table>
        <%@ include file="bottompane.jsp" %>
    </BODY>
</HTML>

