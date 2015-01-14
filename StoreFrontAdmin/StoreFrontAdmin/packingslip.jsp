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
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    DecimalFormat numberFormat = new DecimalFormat("#,##0.00");

    Customer customer = null;
    Packingslip packingslip = null;
    Address shippingaddress = null;
    try {
        CustomerBean customerBean = new CustomerBean();
        GetCustomerRequest getCustomerRequest = new GetCustomerRequest();
        getCustomerRequest.setid(new Integer(request.getParameter("customerid")).intValue());
        GetCustomerResponse getCustomerResponse = customerBean.GetCustomer(getCustomerRequest);
        customer = getCustomerResponse.getcustomer();

        PackingslipBean packingslipBean = new PackingslipBean();
        GetPackingslipRequest getPackingslipRequest = new GetPackingslipRequest();
        getPackingslipRequest.setid(new Integer(request.getParameter("packingslipid")).intValue());
        GetPackingslipResponse getPackingslipResponse = packingslipBean.GetPackingslip(getPackingslipRequest);
        packingslip = getPackingslipResponse.getpackingslip();
        shippingaddress = packingslip.getshipping();
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
                            <td class="producttitle">Packing Slip</td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                    </table>
                    <table cellSpacing="0" cellPadding="0" width="250" border="0">
                        <tr>
                            <td nowrap><b>Packing Slip ID:</b></td>
                            <td nowrap><%=new Integer(packingslip.getid()).toString()%></td>
                        </tr>
                        <tr>
                            <td nowrap><b>Created:</b></td>
                            <td nowrap><%=dateFormat.format(packingslip.getcreationdate())%></td>
                        </tr>
                        <tr>
                            <td nowrap><b>Invoice ID:</b></td>
                            <td nowrap><a href="./invoice.jsp?id=<%=new Integer(packingslip.getinvoiceid()).toString()%>"><%=new Integer(packingslip.getinvoiceid()).toString()%></a></td>
                        </tr>
                    </table>
                    <br>
                    <table cellSpacing="1" cellPadding="1" width="500" border="0">
                        <tr>
                            <td valign="top">
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td nowrap colspan="2"><b>Customer</b></td>
                                    </tr>
                                    <tr>
                                        <td nowrap><%=customer.getfirst()%> <%=customer.getlast()%></td>
                                    </tr>
                                    <tr>
                                        <td nowrap><%=customer.getemail1()%></td>
                                    </tr>
                                </table>
                            </td>
                            <td valign="top">
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td nowrap colspan="2"><b>Shipping Address</b></td>
                                    </tr>
                                    <tr>
                                        <td nowrap><%=shippingaddress.getfirst()%> <%=shippingaddress.getlast()%></td>
                                    </tr>
                                    <%
                                        if(shippingaddress.getcompany() != null && shippingaddress.getcompany().length() > 0)
                                        {
                                    %>
                                            <tr>
                                                <td nowrap><%=shippingaddress.getcompany()%></td>
                                            </tr>
                                    <%
                                        }
                                    %>
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
                                        <td nowrap><%=shippingaddress.getcity()%>, <%=shippingaddress.getstate().compareTo("0")==0?"":shippingaddress.getstate()%> <%=shippingaddress.getzip()%></td>
                                    </tr>
                                    <tr>
                                        <td nowrap><%=shippingaddress.getcountry()%></td>
                                    </tr>
                                    <tr>
                                        <td nowrap><%=shippingaddress.getphone()%></td>
                                    </tr>
                                </table>
                            </td>
                            <td valign="top">
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td nowrap><b>Carrier Information</b></td>
                                    </tr>
                                    <tr>
                                        <td nowrap><%=packingslip.getcarrierName()==null?"No carrier information":packingslip.getcarrierName()%></td>
                                    </tr>
                                <%
                                    if(packingslip.getshippingmethod() != null)
                                    {
                                %>
                                    <tr>
                                        <td nowrap><%=packingslip.getshippingmethod().getdescription()%></td>
                                    </tr>
                                <%
                                    }
                                %>
                                    <tr>
                                        <td nowrap><%=packingslip.gettrackingNumber()==null?"":packingslip.gettrackingNumber()%></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                    <br>
                    <table cellSpacing="1" cellPadding="3" width="500" border="0">
                        <tr>
                            <td><b>Qty Shipped</b></td>
                            <td><b>Product ID</b></td>
                            <td><b>Description</b></td>
                            <td><b>Weight</b></td>
                            <td><b>Sales Order ID</b></td>
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
                            Iterator itPackingslipItems = packingslip.getitemsIterator();
                            while(itPackingslipItems.hasNext())
                            {
                                PackingslipItem packingslipItem = (PackingslipItem)itPackingslipItems.next();
                        %>
                                    <tr>
                                        <td><%=new Integer(packingslipItem.getquantity()).toString()%></td>
                                        <td><%=packingslipItem.getisin()%></td>
                                        <td><%=packingslipItem.getproductname()%></td>
                                        <td><%=numberFormat.format(packingslipItem.getshippingweight())%></td>
                                        <td><a href="./salesorder.jsp?orderid=<%=new Integer(packingslipItem.getsalesorderid()).toString()%>"><%=new Integer(packingslipItem.getsalesorderid()).toString()%></a></td>
                                    </tr>
                        <%
                            }
                        %>
                        <tr>
                            <td colspan="10">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td bgcolor="<%=theme.getcolor1()%>"><img src="./spacer.gif" width="500" height="1" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </BODY>
    <SCRIPT LANGUAGE="JavaScript">
    </SCRIPT>
</HTML>
