<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
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
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    DecimalFormat moneyFormat = new DecimalFormat("$#,##0.00");

    UserBean userBean = new UserBean();
    Customer customer = null;
    User user = null;
    Address billingaddress = null;
    Address shippingaddress = null;
    try {
        CustomerBean customerBean = new CustomerBean();
        GetCustomerRequest getCustomerRequest = new GetCustomerRequest();
        getCustomerRequest.setid(new Integer(request.getParameter("customerid")).intValue());
        GetCustomerResponse getCustomerResponse = customerBean.GetCustomer(getCustomerRequest);
        customer = getCustomerResponse.getcustomer();

        user = userBean.GetUser(customer.getid());

        billingaddress = customerBean.GetAddressCurrentbilling(customer.getid());
        shippingaddress = customerBean.GetAddressCurrentShipping(customer.getid());
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<HTML>
    <SCRIPT LANGUAGE="JavaScript">
        function OnClickDeleteCustomer()
        {
            var answer = confirm("Are you sure you want to delete this customer?");
            if(answer)
            {
                window.location = "./customerdelete.jsp?id=<%=new Integer(customer.getid()).toString()%>";
            }
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
                    <table height="100%" border="0">
                        <tr>
                            <td><img src="./images/spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td class="producttitle">Customer Information</td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                    </table>

                    <table border="0" width="250">
                        <tr>
                            <td nowrap align="left"><b>ID:</b></td>
                            <td align="left"><%=new Integer(customer.getid()).toString()%></td>
                        </tr>
                        <tr>
                            <td align="left" nowrap><b>Name:</b></td>
                            <td align="left" nowrap><%=customer.getfirst()%> <%=customer.getlast()%></td>
                        </tr>
                        <tr>
                            <td align="left" nowrap><b>Created:</b></td>
                            <td align="left" nowrap><%=dateFormat.format(user.getcreationdate())%></td>
                        </tr>
                    <%
                        if(user.getlastlogindate() != null)
                        {
                    %>
                        <tr>
                            <td align="left" nowrap><b>Last Login:</b></td>
                            <td align="left" nowrap><%=dateFormat.format(user.getlastlogindate())%></td>
                        </tr>
                    <%
                        }
                    %>
                    </table>
                    <table border="0" width="550">
                        <tr>
                            <td colspan="2"><img src="./images/spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td valign="top">
                                <table border="0" cellSpacing="0" cellPadding="0">
                                <tr>
                                    <td nowrap><b>Billing Address</b></td>
                                </tr>
                                <tr>
                                    <td nowrap><%=customer.getfullname()%></td>
                                </tr>
                                <tr>
                                    <td nowrap><%=billingaddress.getaddress1()%></td>
                                </tr>
                                <%
                                if(billingaddress.getaddress2() != null && billingaddress.getaddress2().length() > 0)
                                {
                                %>
                                    <tr>
                                        <td nowrap><%=billingaddress.getaddress2()%></td>
                                    </tr>
                                <%
                                }
                                %>
                                <tr>
                                    <td nowrap><%=billingaddress.getcity()%>, <%=billingaddress.getstate()%> <%=billingaddress.getzip()%> </td>
                                </tr>
                                <tr>
                                    <td nowrap><%=billingaddress.getphone()%></td>
                                </tr>
                                <tr>
                                    <td nowrap><%=customer.getemail1()%></td>
                                </tr>
                                </table>
                            </td>
                            <td valign="top">
                                <table border="0" cellSpacing="0" cellPadding="0">
                                <tr>
                                    <td nowrap><b>Shipping Address</b></td>
                                </tr>
                                <%
                                if(shippingaddress == null)
                                {
                                %>
                                    <tr>
                                        <td nowrap>none</td>
                                    </tr>
                                <%
                                }
                                else
                                {
                                %>
                                    <tr>
                                        <td nowrap><%=shippingaddress.getfirst()%> <%=shippingaddress.getlast()%></td>
                                    </tr>
                                    <tr>
                                        <td nowrap><%=shippingaddress.getaddress1()%></td>
                                    </tr>
                                    <%
                                    if(shippingaddress.getaddress2() != null && shippingaddress.getaddress2().length() > 0)
                                    {
                                    %>
                                        <tr>
                                            <td nowrap><%=shippingaddress.getaddress2()%></td>
                                        </tr>
                                    <%
                                    }
                                    %>
                                    <tr>
                                        <td nowrap><%=shippingaddress.getcity()%>, <%=shippingaddress.getstate()%> <%=shippingaddress.getzip()%> </td>
                                    </tr>
                                    <tr>
                                        <td nowrap><%=shippingaddress.getphone()%></td>
                                    </tr>
                                <%
                                }
                                %>
                                    </table>
                                </td>
                            <td valign="top">
                                <table border="0" cellSpacing="0" cellPadding="0">
                                <tr>
                                    <td colspan="2" nowrap><b>Current Credit Card</b></td>
                                </tr>
                                <%
                                if(billingaddress.getcreditcard() == null || billingaddress.getcreditcard().getnumber() == null || billingaddress.getcreditcard().getnumber().length() == 0)
                                {
                                %>
                                <tr>
                                    <td nowrap>none</td>
                                </tr>

                                <%
                                }
                                else
                                {
                                %>
                                <tr>
                                    <td nowrap>Number:</td>
                                    <td><%=billingaddress.getcreditcard().getnumber()%></td>
                                </tr>
                                <tr>
                                    <td nowrap>Expiration:</td>
                                    <td nowrap><%=billingaddress.getcreditcard().getexpmonth()%>/<%=billingaddress.getcreditcard().getexpyear()%></td>
                                </tr>
                                <%
                                }
                                %>
                                </table>
                            </td>
                        </tr>
                    </table>
                    <table cellSpacing="1" cellPadding="3" width="550" border="0">
                        <tr>
                            <td colspan="2"><img src="./images/spacer.gif" width="1" height="15" /></td>
                        </tr>
                        <tr>
                            <td align="left"><b>Sales Order Number</b></td>
                            <td aligh="left"><b>Order Date</b></td>
                            <td align="right"><b>Amount</b></td>
                            <td align="right"><b>Status</b></td>
                        </tr>
                        <tr>
                            <td colspan="5">
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td bgcolor="<%=theme.getcolor1()%>"><img src="./images/spacer.gif" width="550" height="1" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <%
                            GetSalesOrdersResponse getSalesOrdersResponse = null;
                            SalesOrderBean salesOrderBean = new SalesOrderBean();
                            GetSalesOrdersRequest getSalesOrdersRequest = new GetSalesOrdersRequest();
                            getSalesOrdersRequest.setcustomerid(user.getid());
                            getSalesOrdersResponse = salesOrderBean.GetSalesOrders(getSalesOrdersRequest);
                            Iterator itSalesOrders = getSalesOrdersResponse.getsalesorderIterator();
                            boolean orders = false;
                            double orderTotal = 0.0;
                            while(itSalesOrders.hasNext())
                            {
                                orders = true;
                                SalesOrder salesOrder = (SalesOrder)itSalesOrders.next();
                                orderTotal += salesOrder.gettotal();
                        %>
                        <tr>
                            <td nowrap align="left"><a href="./salesorder.jsp?orderid=<%=new Integer(salesOrder.getid()).toString()%>"><%=new Integer(salesOrder.getid()).toString()%></a></td>
                            <td nowrap align="left"><%=dateFormat.format(salesOrder.getcreationdate())%></td>
                            <td nowrap align="right"><%=moneyFormat.format(salesOrder.gettotal())%></td>
                            <td nowrap align="right"><%=salesOrder.getstatus()%></td>
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
                            else
                            {
                        %>
                        <tr>
                            <td colspan="5">
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td bgcolor="<%=theme.getcolor1()%>"><img src="./images/spacer.gif" width="550" height="1" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td align="right"><%=moneyFormat.format(orderTotal)%></td>
                            <td></td>
                            <td></td>
                        </tr>
                        <%
                            }
                        %>
                        <tr>
                            <td><br /></td>
                        </tr>
                    </table>
                    <table border="0">
                    <%
                        GetShoppingCartRequest getShoppingCartRequest = new GetShoppingCartRequest();
                        getShoppingCartRequest.setid(user.getid());
                        GetShoppingCartResponse getShoppingCartResponse = userBean.GetShoppingCart(getShoppingCartRequest);
                        Iterator itShoppingCartItems = getShoppingCartResponse.getshoppingcartitemsIterator();
                        int count = 0;
                        for( ; itShoppingCartItems.hasNext() ; count++)
                            itShoppingCartItems.next();
                    %>
                        <tr>
                            <td nowrap><a href="./shoppingcart.jsp?userid=<%=customer.getid()%>">Current Shopping Cart (<%=new Integer(count).toString()%>)</a></td>
                        </tr>
                    </table>
                    <table border="0">
                        <tr>
                            <td><br /></td>
                        </tr>
                        <tr>
                            <td><input type="button" value="Delete This Customer" onclick="OnClickDeleteCustomer()"/></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>
