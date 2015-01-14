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
    try {
        UserBean userBean = new UserBean();
        GetUserResponse getUserResponse = userBean.GetUser(request, response);

        // Get the customer
        CustomerBean customerBean = new CustomerBean();
        GetCustomerRequest getCustomerRequest = new GetCustomerRequest();
        getCustomerRequest.setid(getUserResponse.getuser().getid());
        GetCustomerResponse getCustomerResponse = customerBean.GetCustomer(getCustomerRequest);
        Customer customer = getCustomerResponse.getcustomer();

        // Update the credit card information
        String cardnumber = request.getParameter("cardnumber");
        String checkforxxxx = cardnumber.substring(0, 4);
        if(checkforxxxx.compareToIgnoreCase("XXXX") != 0)
        {
            CreditCard creditcard = new CreditCard();
            creditcard.setnumber(request.getParameter("cardnumber"));
            creditcard.setexpmonth(request.getParameter("expirationmonth"));
            creditcard.setexpyear(request.getParameter("expirationyear"));
            UpdateCreditcardRequest updateCreditcardRequest = new UpdateCreditcardRequest();
            updateCreditcardRequest.setcreditcard(creditcard);
            updateCreditcardRequest.setcustomerid(getUserResponse.getuser().getid());
            customerBean.UpdateCreditcard(updateCreditcardRequest);
        }

        // Generate the sales order
        SalesOrderBean salesOrderBean = new SalesOrderBean();
        salesOrder = salesOrderBean.GenerateSalesOrder(customer.getid());

        // Recalculate the shipping costs
        if(request.getParameter("shippingmethod") != null && request.getParameter("shippingmethod").length() > 0)
        {
            ShippingMethod shippingMethod = new ShippingMethod();
            shippingMethod.setid(new Integer(request.getParameter("shippingmethod")).intValue());
            salesOrder.setshippingmethod(shippingMethod);
            salesOrderBean.CalculateShippingCost(salesOrder);
        }

        // Optimize shipping checked?
        if(request.getParameter("optimizeshipping") != null && request.getParameter("optimizeshipping").compareToIgnoreCase("on") == 0)
        {
            salesOrder.setoptimizeshipping(true);
        }

        // Apply a coupon if any
        if(request.getParameter("coupon") != null && request.getParameter("coupon").length() > 0)
        {
            salesOrder.setcouponcode(request.getParameter("coupon"));
            salesOrderBean.CalculateCouponSavings(salesOrder);
        }

        // Authorize the credit card
        PayFlowBean payFlowBean = new PayFlowBean();
        PayFlowProRequest payFlowProRequest = new PayFlowProRequest();
        payFlowProRequest.settrxtype("A");
        payFlowProRequest.setsalesorder(salesOrder);
        PayFlowProResponse payFlowProResponse = payFlowBean.CreditCardTransaction(payFlowProRequest);
        String result = payFlowProResponse.getResult();
        String avsaddr = payFlowProResponse.getAVSAddr();
        String avszip = payFlowProResponse.getAVSZip();
        boolean failure = false;
        String error = "General credit card error, please try again.";
        if(result.compareToIgnoreCase("0") != 0)
        {
            error = "Error processing your credit card, please try again or use another card.";
            failure = true;
        }
        if(avsaddr == null || avsaddr.compareToIgnoreCase("N") == 0)
        {
            error = "The billing address or zip code does not match the credit card used, please correct and try again.";
            failure = true;
        }
        if(avszip == null || avszip.compareToIgnoreCase("N") == 0)
        {
            error = "The billing address or zip code does not match the credit card used, please correct and try again.";
            failure = true;
        }
        if(failure)
        {
            String url = StoreFrontUrls.getsecureurlex(request, company, "salesorder", "cardnumber=" + request.getParameter("cardnumber") +
               "&expirationmonth=" + request.getParameter("expirationmonth") +
               "&expirationyear=" + request.getParameter("expirationyear") +
               "&shippingmethod=" + request.getParameter("shippingmethod") +
               "&cardnumber=" + request.getParameter("cardnumber") +
               "&expirationmonth=" + request.getParameter("expirationmonth") +
               "&expirationmonth=" + request.getParameter("expirationyear") +
               "&optimizeshipping=" + request.getParameter("optimizeshipping") +
               "&error=" + error);
            response.sendRedirect(url);
            return;
        }
        if(payFlowProResponse.getAuthCode() != null)
            salesOrder.setauthorizationcode(payFlowProResponse.getAuthCode());
        if(payFlowProResponse.getPNRef() != null)
            salesOrder.setpnref(payFlowProResponse.getPNRef());

        // Add it
        AddSalesOrderRequest addSalesOrderRequest = new AddSalesOrderRequest();
        addSalesOrderRequest.setsalesorder(salesOrder);
        addSalesOrderRequest.setcompany(company);
        AddSalesOrderResponse addSalesOrderResponse = salesOrderBean.AddSalesOrder(addSalesOrderRequest);
        salesOrder.setid(addSalesOrderResponse.getid());
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
                            <td nowrap class="producttitle" width="550">Thank You for your Order</td>
                        </tr>
                    </table>
                    <table border="0" width="550">
                        <tr>
                            <td>Your order number is <b><%=new Integer(salesOrder.getid()).toString()%></b>.  You will receive an e-mail confirmation of your order.</td>
                        </tr>
                        <tr>
                            <td>You may also print this page for your records.</td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td>You Purchased:</td>
                        </tr>
                    </table>
                    <table border="0" width="550">
                        <tr>
                            <td colspan="2"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td valign="top">
                                <table border="0" cellSpacing="0" cellPadding="0">
                                <tr>
                                    <td nowrap><b>Billing Address</b></td>
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
                                    <td nowrap><%=salesOrder.getcustomer().getemail1()%></td>
                                </tr>
                                </table>
                            </td>
                            <td valign="top">
                                <table border="0" cellSpacing="0" cellPadding="0">
                                <tr>
                                    <td nowrap><b>Shipping Address</b></td>
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
                            <td valign="top">
                                <table border="0" cellSpacing="0" cellPadding="0">
                                <tr>
                                    <td colspan="2" nowrap><b>Credit Card Used</b></td>
                                </tr>
                                <tr>
                                    <td nowrap>Number:</td>
                                    <td><%=DisplayCreditCard(salesOrder.getbillingaddress().getcreditcard().getnumber())%></td>
                                </tr>
                                <tr>
                                    <td nowrap>Expiration:</td>
                                    <td nowrap><%=salesOrder.getbillingaddress().getcreditcard().getexpmonth()%>/<%=salesOrder.getbillingaddress().getcreditcard().getexpyear()%></td>
                                </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                    <br>
                    <table cellSpacing="1" cellPadding="2" border="0" width="550">
                    <tr>
                        <td nowrap align="left"><b>Item</b></td>
                        <td nowrap align="right"><b>Unit Price</b></td>
                        <td nowrap align="center"><b>Quantity</b></td>
                        <td nowrap align="right"><b>Item Total</b></td>
                    </tr>
                    <tr>
                        <td colspan="4">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td bgcolor="<%=theme.getcolor1()%>"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="550" height="2"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <%
                        Iterator itSalesOrderItems = salesOrder.getitemsIterator();
                        while(itSalesOrderItems.hasNext())
                        {
                            SalesOrderItem salesorderitem = (SalesOrderItem)itSalesOrderItems.next();
                    %>
                            <tr>
                                <td align="left" width="350"><%=salesorderitem.getmanufacturer()%> <%=salesorderitem.getproductname()%>
                                    <br>Item number: <%=salesorderitem.getisin()%>, <%=salesorderitem.getitemstatus()%>
                                </td>
                                <td align="right"><%=moneyFormat.format(salesorderitem.getunitprice())%></td>
                                <td align="center"><%=new Integer(salesorderitem.getquantity()).toString()%></td>
                                <td align="right"><%=moneyFormat.format(((double)salesorderitem.getquantity()*salesorderitem.getunitprice()))%></td>
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
                        <td colspan="3" align="right" nowrap><b>Merchandise Total:</b></td>
                        <td align="right"><b><%=moneyFormat.format(salesOrder.gettotalcost())%></b></td>
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
                        <td colspan="1"></td>
                        <td colspan="3" align="right">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td bgcolor="<%=theme.getcolor1()%>"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="250" height="1"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3" align="right" nowrap>Shipping Costs(<%=salesOrder.getshippingmethod().getdescription()%>):</td>
                        <td align="right"><%=moneyFormat.format(salesOrder.getshipping()+salesOrder.gethandling())%></td>
                    </tr>
                    <tr>
                        <td colspan="3" align="right" nowrap><%=salesOrder.gettaxesdescription()%>:</td>
                        <td align="right"><%=moneyFormat.format(salesOrder.gettaxes())%></td>
                    </tr>
                    <tr>
                        <td colspan="1"></td>
                        <td colspan="3" align="right">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td bgcolor="<%=theme.getcolor1()%>"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="250" height="1"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3" align="right" nowrap><b>Grand Total:</b></td>
                        <td align="right"><b><%=moneyFormat.format(salesOrder.gettotal())%></b></td>
                    </tr>
                    </table>
                    <%@ include file="bottompane.jsp" %>
            <%
                if(company.getgoogleconversionid() != null && company.getgoogleconversionid().length() > 0)
                {
            %>
                    <br>
                    <br>
                    <table cellSpacing="1" cellPadding="2" border="0" width="550">
                    <tr>
                        <td align="center">
                        <!-- Google Conversion Code -->
                        <script language="JavaScript">
                        <!--
                        google_conversion_id = <%=company.getgoogleconversionid()%>;
                        google_conversion_language = "en_US";
                        -->
                        </script>
                        <script language="JavaScript" src="https://www.googleadservices.com/pagead/conversion.js">
                        </script>
                        <noscript>
                        <a href="https://services.google.com/sitestats/en_US.html" target=_blank>
                        <img height=27 width=135 border=0 src="https://www.googleadservices.com/pagead/conversion/<%=company.getgoogleconversionid()%>/?hl=en">
                        </a>
                        </noscript>
                        </td>
                    </tr>
                    </table>
            <%
                }
            %>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>

