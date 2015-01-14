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
    DecimalFormat moneyFormat = new DecimalFormat("$#,##0.00");

    Customer customer = null;
    GetSalesOrdersResponse getSalesOrdersResponse = null;
    try {
        CustomerBean customerBean = new CustomerBean();
        GetCustomerRequest getCustomerRequest = new GetCustomerRequest();
        getCustomerRequest.setid(new Integer(request.getParameter("customerid")).intValue());
        GetCustomerResponse getCustomerResponse = customerBean.GetCustomer(getCustomerRequest);
        customer = getCustomerResponse.getcustomer();

        SalesOrderBean salesOrderBean = new SalesOrderBean();
        GetSalesOrdersRequest getSalesOrdersRequest = new GetSalesOrdersRequest();
        getSalesOrdersRequest.setcustomerid(customer.getid());
        getSalesOrdersResponse = salesOrderBean.GetOpenSalesOrders(getSalesOrdersRequest);
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
            if(document.form1.shippingmethod.value == '0')
            {
                alert("Please choose a shipping method.");
                document.form1.shippingmethod.focus();
                return false;
            }

            if(document.form1.trackingnumber.value.trim().length == 0)
            {
                alert("A tracking number is required.");
                document.form1.trackingnumber.focus();
                return false;
            }

            return true;
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
                            <td class="producttitle">Create a Packing Slip</td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                        <tr>
                            <td><h2>Step 2 - Select the items to be shipped</h2></td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                    </table>
                    <table cellSpacing="1" cellPadding="1" width="250" border="0">
                        <tr>
                            <td nowrap colspan="2"><b>Customer</b></td>
                        </tr>
                        <tr>
                            <td nowrap>ID:</td>
                            <td nowrap><%=new Integer(customer.getid()).toString()%></td>
                        </tr>
                        <tr>
                            <td nowrap>Name:</td>
                            <td nowrap><%=customer.getfirst()%> <%=customer.getlast()%></td>
                        </tr>
                        <tr>
                            <td nowrap>E-Mail:</td>
                            <td nowrap><%=customer.getemail1()%></td>
                        </tr>
                    </table>
                    <form name="form1" action="./packingslipadd3.jsp" method="get" onsubmit="return(OnSubmitForm());">
                    <table border="0">
                    <input type="hidden" name="customerid" value="<%=request.getParameter("customerid")%>" />
                        <tr>
                            <td nowrap>Shipping Method:</td>
                            <td>
                                <select name="shippingmethod">
                                    <option value="0">< select ></option>
                                <%
                                    ListsBean listsBean = new ListsBean();
                                    GetShippingMethodsResponse getShippingMethodsResponse = listsBean.GetShippingMethods(new GetShippingMethodsRequest());
                                    Iterator itShippingMethods = getShippingMethodsResponse.getshippingmethodsIterator();
                                    while(itShippingMethods.hasNext())
                                    {
                                        ShippingMethod shippingMethod = (ShippingMethod)itShippingMethods.next();
                                %>
                                        <option value="<%=shippingMethod.getdescription()%>"><%=shippingMethod.getdescription()%></option>
                                <%
                                    }
                                %>
                                </select>
                            </td>
                            <td>&nbsp;&nbsp;</td>
                            <td nowrap>Tracking #:</td>
                            <td><input name="trackingnumber" type="text" size="25" /></td>
                        </tr>
                    </table>
                    <br>
                    <table cellSpacing="1" cellPadding="3" width="700" border="0">
                        <tr>
                            <td><b>Select Item</b></td>
                            <td><b>Sales Order ID</b></td>
                            <td><b>Created</b></td>
                            <td><b>Ship To</b></td>
                            <td><b>Product ID</b></td>
                            <td align="center"><b>Qty</b></td>
                            <td><b>Status</b></td>
                            <td></td>
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
                            Iterator itSalesOrders = getSalesOrdersResponse.getsalesorderIterator();
                            while(itSalesOrders.hasNext())
                            {
                                SalesOrder salesOrder = (SalesOrder)itSalesOrders.next();
                                Iterator itItems = salesOrder.getitemsIterator();
                                while(itItems.hasNext())
                                {
                                    SalesOrderItem salesOrderItem = (SalesOrderItem)itItems.next();
                        %>
                                    <tr>
                                        <td valign="top"><input type="checkbox" name="item<%=new Integer(salesOrderItem.getid()).toString()%>" /></td>
                                        <td valign="top"><%=new Integer(salesOrder.getid()).toString()%></td>
                                        <td valign="top" nowrap><%=dateFormat.format(salesOrder.getcreationdate())%></td>
                                        <td valign="top">
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td nowrap><%=salesOrder.getshippingaddress().getfirst()%> <%=salesOrder.getshippingaddress().getlast()%></td>
                                                </tr>
                                <%
                                    if(salesOrder.getshippingaddress().getcompany() != null && salesOrder.getshippingaddress().getcompany().length() > 0)
                                    {
                                %>
                                                <tr>
                                                    <td nowrap><%=salesOrder.getshippingaddress().getcompany()%></td>
                                                </tr>
                                <%
                                    }
                                %>
                                                <tr>
                                                    <td nowrap><%=salesOrder.getshippingaddress().getaddress1()%></td>
                                                </tr>
                                <%
                                    if(salesOrder.getshippingaddress().getaddress2() != null && salesOrder.getshippingaddress().getaddress2().length() > 0)
                                    {
                                %>
                                                <tr>
                                                    <td nowrap><%=salesOrder.getshippingaddress().getaddress2()%></td>
                                                </tr>
                                <%
                                    }
                                %>
                                                <tr>
                                                    <td nowrap><%=salesOrder.getshippingaddress().getcity()%>, <%=salesOrder.getshippingaddress().getstate()%> <%=salesOrder.getshippingaddress().getzip()%></td>
                                                </tr>
                                                <tr>
                                                    <td nowrap><%=salesOrder.getshippingaddress().getcountry()%></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td valign="top"><a href="./productupdate.jsp?id=<%=new Integer(salesOrderItem.getid()).toString()%>"><%=salesOrderItem.getisin()%></a></td>
                                        <td valign="top" align="center"><%=new Integer(salesOrderItem.getquantity()).toString()%></td>
                                        <td valign="top" nowrap><%=salesOrderItem.getstatus()!=null?salesOrderItem.getstatus():""%></td>
                                        <td valign="top"><a href="./salesorder.jsp?orderid=<%=new Integer(salesOrder.getid()).toString()%>">view order</a></td>
                                    </tr>
                        <%
                                }
                            }
                        %>
                        <tr>
                            <td colspan="10">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td bgcolor="<%=theme.getcolor1()%>"><img src="./spacer.gif" width="700" height="1" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                    <table border="0">
                        <tr>
                            <td><input type="submit" name="createpackingslip" value="Create Packing Slip" /></td>
                        </tr>
                    </table>
                    </form>
                </td>
            </tr>
        </table>
    </BODY>
    <SCRIPT LANGUAGE="JavaScript">
    </SCRIPT>
</HTML>
