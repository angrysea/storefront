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

    SalesOrder salesOrder = new SalesOrder();
    try {
        UserBean userBean = new UserBean();
        GetUserResponse getUserResponse = userBean.GetUser(request, response);
        User user = getUserResponse.getuser();

        SalesOrderBean salesOrderBean = new SalesOrderBean();
        GetSalesOrderRequest getSalesOrderRequest = new GetSalesOrderRequest();
        getSalesOrderRequest.setid(new Integer(request.getParameter("orderid")).intValue());
        GetSalesOrderResponse getSalesOrderResponse = salesOrderBean.GetSalesOrder(getSalesOrderRequest);
        salesOrder = getSalesOrderResponse.getsalesorder();
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
                            <td colspan="5" nowrap class="producttitle">Order</td>
                        </tr>
                        <tr>
                            <td colspan="2"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="1" height="4" /></td>
                        </tr>
                    </table>

                    <table border="0" width="100">
                        <tr>
                            <td nowrap align="left"><b>ID:</b></td>
                            <td align="left"><%=new Integer(salesOrder.getid()).toString()%></td>
                        </tr>
                        <tr>
                            <td align="left"><b>Date:</b></td>
                            <td align="left"><%=dateFormat.format(salesOrder.getcreationdate())%></td>
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
                        <td colspan="3" align="right" nowrap><b>Amount Charged to Your Credit Card:</b></td>
                        <td align="right"><b><%=moneyFormat.format(salesOrder.gettotal())%></b></td>
                    </tr>
                    </table>
                    <table border="0">
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td colspan="5" align="right"><a href="<%=StoreFrontUrls.geturl(request, company, "orderhistory")%>"><img alt="continue" border="0" src="<%=theme.getimagebaseurl()%>continue_button.gif"/></a></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <%@ include file="bottompane.jsp" %>
    </BODY>
</HTML>

