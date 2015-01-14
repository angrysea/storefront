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
    DecimalFormat moneyFormat = new DecimalFormat("$#,##0.00");

    SalesOrder salesOrder = null;
    Customer customer = null;
    CreditCard creditCard = null;
    try {
        UserBean userBean = new UserBean();
        GetUserResponse getUserResponse = userBean.GetUser(request, response);

        // Are we updating the cart
        String task = request.getParameter("task");
        if(task != null && task.compareToIgnoreCase("update") == 0)
        {
            UpdateShoppingCartItemRequest updateShoppingCartItemRequest = new UpdateShoppingCartItemRequest();

            // Cycle through the current shopping cart items
            GetShoppingCartRequest getShoppingCartRequest = new GetShoppingCartRequest();
            getShoppingCartRequest.setid(getUserResponse.getuser().getid());
            GetShoppingCartResponse getShoppingCartResponse = userBean.GetShoppingCart(getShoppingCartRequest);
            Iterator itShoppingCartItems = getShoppingCartResponse.getshoppingcartitemsIterator();
            while(itShoppingCartItems.hasNext())
            {
                ShoppingCartItem shoppingCartItem = (ShoppingCartItem)itShoppingCartItems.next();
                String searchfor = "itemid" + new Integer(shoppingCartItem.getitem().getid()).toString();
                if(request.getParameter(searchfor) != null)
                {
                    shoppingCartItem.setquantity(new Integer(request.getParameter(searchfor)).intValue());
                    updateShoppingCartItemRequest.setshoppingcartitem(shoppingCartItem);

                    // Update it
                    userBean.UpdateShoppingCartItem(updateShoppingCartItemRequest);
                }
            }
        }
        // Are we removing
        else if(task != null && task.compareToIgnoreCase("remove") == 0)
        {
            DeleteShoppingCartItemRequest deleteShoppingCartItemRequest = new DeleteShoppingCartItemRequest();
            deleteShoppingCartItemRequest.setid(new Integer(request.getParameter("itemid")).intValue());
            userBean.DeleteShoppingCart(deleteShoppingCartItemRequest);
        }

        // Generate the sales order
        SalesOrderBean salesOrderBean = new SalesOrderBean();
        CustomerBean customerBean = new CustomerBean();
        GetCustomerRequest getCustomerRequest = new GetCustomerRequest();
        getCustomerRequest.setid(getUserResponse.getuser().getid());
        GetCustomerResponse getCustomerResponse = customerBean.GetCustomer(getCustomerRequest);
        customer = getCustomerResponse.getcustomer();
        salesOrder = salesOrderBean.GenerateSalesOrder(customer.getid());
        creditCard = salesOrder.getbillingaddress().getcreditcard();

        // Use the first shipping method in the list as the default
        ListsBean listsBean = new ListsBean();
        ShippingMethod shippingMethod = new ShippingMethod();
        GetShippingMethodsRequest getShippingMethodsRequest = new GetShippingMethodsRequest();
        getShippingMethodsRequest.setcountry(salesOrder.getshippingaddress().getcountry());
        GetShippingMethodsResponse getShippingMethodsResponse = listsBean.GetShippingMethods(getShippingMethodsRequest);
        Iterator itShippingMethods = getShippingMethodsResponse.getshippingmethodsIterator();
        if(itShippingMethods.hasNext())
            shippingMethod = (ShippingMethod)itShippingMethods.next();

        // Calculate Shipping
        if(request.getParameter("shippingmethod") != null && request.getParameter("shippingmethod").length() > 0)
        {
            shippingMethod = new ShippingMethod();
            shippingMethod.setid(new Integer(request.getParameter("shippingmethod")).intValue());
        }
        if(shippingMethod.getid() > 0)
        {
            salesOrder.setshippingmethod(shippingMethod);
            salesOrderBean.CalculateShippingCost(salesOrder);
        }

        // Apply a coupon if any
        if(request.getParameter("coupon") != null && request.getParameter("coupon").length() > 0)
        {
            salesOrder.setcouponcode(request.getParameter("coupon"));
            salesOrderBean.CalculateCouponSavings(salesOrder);
        }
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<%!
    public String DisplayCreditCard(String cardnumber)
    {
        if(cardnumber.length() == 0)
            return "";

        if(cardnumber.length() == 15)
            return "XXXXXXXXXXX" + cardnumber.subSequence(11, 15);
        else
            return "XXXXXXXXXXXX" + cardnumber.subSequence(12, 16);
    }
%>

<HTML>
    <%@ include file="creditcardvalidation.jsp" %>
    <SCRIPT LANGUAGE="JavaScript">
    function OnChangeShippingMethod()
    {
        document.form1.action = "<%=StoreFrontUrls.getsecureurl(request, company, "salesorder")%>";
        document.form1.submit();
    }

    function OnClickUpdateQuantities()
    {
        document.form1.action = "<%=StoreFrontUrls.getsecureurl(request, company, "salesorder")%>";
        document.form1.submit();
    }

    function OnClickApplyCoupon()
    {
        document.form1.action = "<%=StoreFrontUrls.getsecureurl(request, company, "salesorder")%>";
        document.form1.submit();
    }

    function OnSubmitForm()
    {
    <%
        if(salesOrder.gettotal() == 0.0)
        {
    %>
            alert("There are no items in this order.");
            return false;
    <%
        }
    %>

        if(document.form1.cardnumber.value.length < 15)
        {
            alert("Enter a valid credit card number!");
            document.form1.cardnumber.focus();
            return false;
        }

        var cardnumber = document.form1.cardnumber.value;
        var firstpart = cardnumber.substring(0, 10);
        if(firstpart != "XXXXXXXXXX")
        {
            if(ValidateCreditCard(document.form1.cardnumber.value) == false)
            {
                document.form1.cardnumber.focus();
                return false;
            }
        }

        if(document.form1.expirationmonth.value == 0)
        {
            alert("Enter a valid expiration month!");
            document.form1.expirationmonth.focus();
            return false;
        }

        if(document.form1.expirationyear.value == 0)
        {
            alert("Enter a valid expiration year!");
            document.form1.expirationyear.focus();
            return false;
        }

        return true;
    }
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
                    <table border="0">
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="5" height="15"></td>
                        </tr>
                        <tr bgcolor="<%=theme.getcolor2()%>">
                            <td nowrap class="producttitle" width="550">Review Your Order</td>
                        </tr>
                    <%
                        if(request.getParameter("error") != null)
                        {
                    %>
                        <tr width="550">
                            <td class="errorText"><%=request.getParameter("error")%></td>
                        </tr>
                    <%
                        }
                    %>
                    </table>
                    <form name="form1" action="<%=company.getbasesecureurl()%>salesorderauthorize.jsp" method="post" onsubmit="return(OnSubmitForm());">
                    <input name="user" type="hidden" value="<%=request.getParameter("user")==null?"":request.getParameter("user")%>"/>
                    <input name="login" type="hidden" value="<%=request.getParameter("login")==null?"":request.getParameter("login")%>"/>
                    <input name="task" type="hidden" value="update"/>
                    <table cellSpacing="1" cellPadding="2" border="0" width="550">
                    <tr>
                        <td nowrap align="left"><b>Item</b></td>
                        <td nowrap align="right"><b>Unit Price</b></td>
                        <td nowrap align="center"><b>Quantity</b></td>
                        <td nowrap align="right"><b>Item Total</b></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td colspan="4">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td bgcolor="<%=theme.getcolor1()%>"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="550" height="1"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <%
                        int numberOfItems = 0;
                        int numberOfRows = 0;
                        Iterator itSalesOrderItems = salesOrder.getitemsIterator();
                        while(itSalesOrderItems.hasNext())
                        {
                            SalesOrderItem salesorderitem = (SalesOrderItem)itSalesOrderItems.next();
                            numberOfItems += salesorderitem.getquantity();
                            numberOfRows++;
                    %>
                            <tr>
                                <td align="left" width="350"><%=salesorderitem.getmanufacturer()%> <%=salesorderitem.getproductname()%>
                                    <br>Item number: <%=salesorderitem.getisin()%>, <%=salesorderitem.getitemstatus()%>
                                </td>
                                <td align="right"><%=moneyFormat.format(salesorderitem.getunitprice())%></td>
                                <td align="center">
                                    <input name="itemid<%=new Integer(salesorderitem.getitemnumber()).toString()%>" type="text" size="5" value="<%=new Integer(salesorderitem.getquantity()).toString()%>" />
                                </td>
                                <td align="right"><%=moneyFormat.format(((double)salesorderitem.getquantity()*salesorderitem.getunitprice()))%></td>
                                <td nowrap valign="middle">&nbsp;&nbsp;<a href="<%=StoreFrontUrls.getsalesorder(request, company, salesorderitem, "remove")%>">remove</a></td>
                            </tr>
                    <%
                        }
                    %>
                    <%
                        if(numberOfRows == 0)
                        {
                    %>
                        <tr>
                            <td align="left" width="350">There are no more items in this order.</td>
                        </tr>
                    <%
                        }
                    %>
                    <tr>
                        <td colspan="4">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td bgcolor="<%=theme.getcolor1()%>"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="550" height="1"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2"></td>
                        <td align="center" nowrap><a href="<%=StoreFrontUrls.geturl(request, company, "salesorder")%>" onclick="OnClickUpdateQuantities();return false;">update quantities</a></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td colspan="4" align="right">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td bgcolor="<%=theme.getcolor1()%>"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="450" height="1"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3" align="right" nowrap><b>Merchandise Total:</b></td>
                        <td align="right"><b><%=moneyFormat.format(salesOrder.gettotalcost())%></b></td>
                        <td></td>
                    </tr>
            <%
                if(salesOrder.getdiscount() > 0.0)
                {
            %>
                    <tr>
                        <td colspan="3" align="right" nowrap><%=salesOrder.getdiscountdescription()%></td>
                        <td align="right"><font color="red">- <%=moneyFormat.format(salesOrder.getdiscount())%></font></td>
                        <td></td>
                    </tr>
            <%
                }
            %>
                    <tr>
                        <td colspan="4" align="right">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td bgcolor="<%=theme.getcolor1()%>"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="450" height="1"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3" align="right" nowrap>Shipping Method:&nbsp;
                            <select name="shippingmethod" onchange="OnChangeShippingMethod()">
                            <%
                                ShippingMethod currentShippingMethod = null;

                                ListsBean listsBean = new ListsBean();
                                GetShippingMethodsRequest getShippingMethodsRequest = new GetShippingMethodsRequest();
                                getShippingMethodsRequest.setcountry(salesOrder.getshippingaddress().getcountry());
                                GetShippingMethodsResponse getShippingMethodsResponse = listsBean.GetShippingMethods(getShippingMethodsRequest);
                                Iterator itShippingMethods = getShippingMethodsResponse.getshippingmethodsIterator();
                                while(itShippingMethods.hasNext())
                                {
                                    ShippingMethod shippingMethod = (ShippingMethod)itShippingMethods.next();
                            %>
                            <%
                                    if(salesOrder.getshippingmethod().getid() == shippingMethod.getid())
                                    {
                                        currentShippingMethod = shippingMethod;
                            %>
                                        <option selected value="<%=new Integer(shippingMethod.getid()).toString()%>"><%=shippingMethod.getdescription()%></option>
                            <%
                                    }
                                    else
                                    {
                            %>
                                        <option value="<%=new Integer(shippingMethod.getid()).toString()%>"><%=shippingMethod.getdescription()%></option>
                            <%
                                    }
                                }
                            %>
                            </select>
                        </td>
                        <td align="right"><%=moneyFormat.format(salesOrder.getshipping()+salesOrder.gethandling())%></td>
                        <td></td>
                    </tr>
                    <tr>
                <%
                    if(salesOrder.getshipping() > 0.0)
                    {
                %>
                        <td colspan="3" align="right"><font color="red"><%=currentShippingMethod.getnotes()%></font></td>
                <%
                    }
                    else
                    {
                %>
                        <td colspan="3" align="right"><font color="red">Error calculating shipping costs. Please continue with your order.  Shipping costs will be determined manually and e-mailed to you for your approval.</font></td>
                <%
                    }
                %>
                        <td></td>
                        <td></td>
                    </tr>
                <%
                    if(numberOfItems > 1)
                    {
                %>
                        <tr>
                        <%
                            if(request.getParameter("optimizeshipping") != null && request.getParameter("optimizeshipping").compareToIgnoreCase("on") == 0)
                            {
                        %>
                            <td colspan="3" align="right"><input type="checkbox" name="optimizeshipping" checked="checked"/>I want my items faster. Ship items as they become available.<br>(at additional shipping cost)</td>
                        <%
                            }
                            else
                            {
                        %>
                            <td colspan="3" align="right"><input type="checkbox" name="optimizeshipping"/>I want my items faster. Ship items as they become available.<br>(at additional shipping cost)</td>
                        <%
                            }
                        %>
                            <td></td>
                            <td></td>
                        </tr>
                <%
                    }
                %>
                    <tr>
                        <td colspan="4" align="right">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td bgcolor="<%=theme.getcolor1()%>"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="450" height="1"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2"></td>
                        <td align="right" nowrap><%=salesOrder.gettaxesdescription()%>:</td>
                        <td align="right"><%=moneyFormat.format(salesOrder.gettaxes())%></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td colspan="4" align="right">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td bgcolor="<%=theme.getcolor1()%>"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="175" height="1"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2"></td>
                        <td align="right"><b>Amount Due:</b></td>
                        <td align="right"><b><%=moneyFormat.format(salesOrder.gettotal())%></b></td>
                        <td></td>
                    </tr>
                    </table>
                    <table border="0" width="550">
                        <tr>
                            <td colspan="3"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="5" height="25"></td>
                        </tr>
                        <tr bgcolor="<%=theme.getcolor2()%>">
                            <td colspan="3" nowrap class="producttitle" width="550">Coupon or Promotion Code</td>
                        </tr>
                        <tr>
                            <td colspan="3" nowrap><b>If you have a coupon or promotion code, please enter it here.</b></td>
                        </tr>
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="5" height="5"></td>
                        </tr>
                    </table>
                    <table border="0" width="250">
                        <tr>
                            <td nowrap>Enter Coupon:</td>
                            <td><input name="coupon" type="text" size="30" value="<%=salesOrder.getcouponcode()==null?"":salesOrder.getcouponcode()%>" /></td>
                            <td nowrap>&nbsp;<a href="<%=StoreFrontUrls.geturl(request, company, "salesorder")%>" onclick="OnClickApplyCoupon();return false;">apply coupon</a></td>
                        </tr>
                    </table>
                    <table border="0" width="550">
                        <tr>
                            <td colspan="2"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="5" height="25"></td>
                        </tr>
                        <tr bgcolor="<%=theme.getcolor2()%>">
                            <td colspan="2" nowrap class="producttitle" width="550">Review Billing & Shipping Addresses</td>
                        </tr>
                        <tr>
                            <td colspan="2" nowrap><b>Please double-check your billing and shipping addresses.</b></td>
                        </tr>
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td valign="top">
                                <table border="0" cellSpacing="0" cellPadding="0">
                                <tr>
                                    <td nowrap><b>Billing Address</b></td>
                                    <td nowrap>&nbsp;<a href="<%=StoreFrontUrls.getsecureurl(request, company, "confirmbillship", StoreFrontUrls.getsecureurl(request, company, "salesorder"))%>">change this</a></td>
                                </tr>
                                <tr>
                                    <td nowrap><%=salesOrder.getbillingaddress().getfirst()%> <%=salesOrder.getbillingaddress().getlast()%></td>
                                </tr>
                                <tr>
                                    <td nowrap><%=salesOrder.getbillingaddress().getaddress1()%></td>
                                </tr>
                                <%
                                if(salesOrder.getbillingaddress().getaddress2() != null && salesOrder.getbillingaddress().getaddress2().length() > 0)
                                {
                                %>
                                    <tr>
                                        <td nowrap><%=salesOrder.getbillingaddress().getaddress2()%></td>
                                    </tr>
                                <%
                                }
                                %>
                                <tr>
                                    <td nowrap><%=salesOrder.getbillingaddress().getcity()%>, <%=salesOrder.getbillingaddress().getstate().compareTo("0")==0?"":salesOrder.getbillingaddress().getstate()%> <%=salesOrder.getbillingaddress().getzip()%> </td>
                                </tr>
                                <tr>
                                    <td nowrap><%=salesOrder.getbillingaddress().getcountry()%></td>
                                </tr>
                                <tr>
                                    <td nowrap><%=salesOrder.getbillingaddress().getphone()%></td>
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
                                    <td nowrap>&nbsp;<a href="<%=StoreFrontUrls.getsecureurl(request, company, "confirmbillship", StoreFrontUrls.getsecureurl(request, company, "salesorder"))%>">change this</a></td>
                                </tr>
                                <tr>
                                    <td nowrap><%=salesOrder.getshippingaddress().getfirst()%> <%=salesOrder.getshippingaddress().getlast()%></td>
                                </tr>
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
                                    <td nowrap><%=salesOrder.getshippingaddress().getcity()%>, <%=salesOrder.getshippingaddress().getstate().compareTo("0")==0?"":salesOrder.getshippingaddress().getstate()%> <%=salesOrder.getshippingaddress().getzip()%> </td>
                                </tr>
                                <tr>
                                    <td nowrap><%=salesOrder.getshippingaddress().getcountry()%></td>
                                </tr>
                                <tr>
                                    <td nowrap><%=salesOrder.getshippingaddress().getphone()%></td>
                                </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                    <table border="0" width="550">
                        <tr>
                            <td colspan="2"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="5" height="25"></td>
                        </tr>
                        <tr bgcolor="<%=theme.getcolor2()%>">
                            <td colspan="2" nowrap class="producttitle" width="550">Credit Card Information</td>
                        </tr>
                        <tr>
                            <td colspan="2" nowrap><b>To complete your order, enter your credit card information below.</b></td>
                        </tr>
                        <tr>
                            <td colspan="2">
                            <table border="0">
                                <tr>
                                    <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="30" height="4" /></td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td align="center"><img alt="visa mastercard american express discover" src="<%=theme.getimagebaseurl()%>creditcards.gif" /></td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td align="center">We accept Visa®, Visa®, Mastercard®, Discover®<br>and American Express® cards.</td>
                                </tr>
                            </table>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <table>
                                <tr>
                                    <td nowrap align="right">Card Number:</td>
                                <%
                                    if(request.getParameter("cardnumber") != null)
                                    {
                                %>
                                    <td><input name="cardnumber" type="text" size="30" value="<%=request.getParameter("cardnumber")%>" /></td>
                                <%
                                    }
                                    else
                                    {
                                %>
                                    <td><input name="cardnumber" type="text" size="30" value="<%=creditCard!=null&&creditCard.getnumber()!=null?DisplayCreditCard(creditCard.getnumber()):""%>" /></td>
                                <%
                                    }
                                %>
                                </tr>
                                <tr>
                                    <td nowrap align="right">Expiration Date:</td>
                                    <td>
                                        <select name="expirationmonth" >
                                        <option value="0"> Month
                                        </option>
                                        <option value="01">Jan</option>
                                        <option value="02">Feb</option>
                                        <option value="03">March</option>
                                        <option value="04">April</option>
                                        <option value="05">May</option>
                                        <option value="06">June</option>
                                        <option value="07">July</option>
                                        <option value="08">August</option>
                                        <option value="09">Sept</option>
                                        <option value="10">Oct</option>
                                        <option value="11">Nov</option>
                                        <option value="12">Dec</option>
                                        </select>
                                    &nbsp;
                                        <select name="expirationyear" >
                                            <option value="0">Year</option>
                                    <%
        			        DateFormat dateFormat = new SimpleDateFormat("yyyy");
                                        int year = new Integer(dateFormat.format(Calendar.getInstance().getTime())).intValue();
                                        for(int i=0 ; i<20 ; i++)
                                        {
                                    %>
                                            <option value="<%=new Integer(year + i).toString()%>"><%=new Integer(year + i).toString()%></option>
                                    <%
                                        }
                                    %>
                                        </select>
                                    </td>
                                </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                    <table border="0" width="550">
                    <tr>
                        <td colspan="10">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td bgcolor="<%=theme.getcolor1()%>"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="550" height="1"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="5" height="5"></td>
                    </tr>
                    <tr>
                        <td colspan="10" align="right"><input type="image" alt="send order" src="<%=theme.getimagebaseurl()%>sendorder_button.gif" /></td>
                    </tr>
                    <tr>
                        <td><br></td>
                    </tr>
                    </table>
                    </form>
                    <%@ include file="bottompane.jsp" %>
                </td>
            </tr>
        </table>
    </BODY>
    <SCRIPT LANGUAGE="JavaScript">
    <%
        if(request.getParameter("cardnumber") != null)
        {
    %>
            document.form1.expirationmonth.value ='<%=request.getParameter("expirationmonth")%>';
            document.form1.expirationyear.value = '<%=request.getParameter("expirationyear")%>';
    <%
        }
        else if(creditCard != null && creditCard.getnumber() != null && creditCard.getnumber().length() > 0)
        {
    %>
            document.form1.expirationmonth.value ='<%=salesOrder.getbillingaddress().getcreditcard().getexpmonth()%>';
            document.form1.expirationyear.value = '<%=salesOrder.getbillingaddress().getcreditcard().getexpyear()%>';
    <%
        }
    %>
    </SCRIPT>
</HTML>

