<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
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
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    DecimalFormat moneyFormat = new DecimalFormat("$#,##0.00");

    Packingslip packingslip = null;
    Address shippingaddress = null;
    Invoice invoice = null;
    CreditCard creditcard = null;
    try {
        PackingslipBean packingslipBean = new PackingslipBean();
        GetPackingslipRequest getPackingslipRequest = new GetPackingslipRequest();
        getPackingslipRequest.setid(new Integer(request.getParameter("packingslipid")).intValue());
        GetPackingslipResponse getPackingslipResponse = packingslipBean.GetPackingslip(getPackingslipRequest);
        packingslip = getPackingslipResponse.getpackingslip();
        shippingaddress = packingslip.getshipping();

        InvoiceBean invoiceBean = new InvoiceBean();
        GetInvoiceRequest getInvoiceRequest = new GetInvoiceRequest();
        getInvoiceRequest.setid(packingslip.getinvoiceid());
        GetInvoiceResponse getInvoiceResponse = invoiceBean.GetInvoice(getInvoiceRequest);
        invoice = getInvoiceResponse.getinvoice();
        creditcard = invoice.getbilling().getcreditcard();
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
        <LINK href="<%=company.getbaseurl()%>storefront.css" rel="STYLESHEET"></LINK>
    </HEAD>
    <BODY leftMargin="0" topMargin="0" onload="" marginwidth="0" marginheight="0">
<TABLE valign="0" cellSpacing="0" cellPadding="0" border="0">
    <TR>
        <TD height="74" width="223" background="<%=theme.getimagebaseurl()%>logo.gif"></TD>
        <TD width="250" height="10" background="<%=theme.getimagebaseurl()%>spacer.gif"></TD>
        <TD align="right" width="180" height="82" background="<%=theme.getimagebaseurl()%>scenery.gif"></TD>
    </TR>
</TABLE>
<TABLE align="0" cellSpacing="0" cellPadding="0" width="100%" border="0">
    <TR>
        <TD><img src="<%=theme.getimagebaseurl()%>filler_left.gif" width="223" height="24"></TD>
        <TD width="100%" background="<%=theme.getimagebaseurl()%>toolbar_filler.gif"><img src="<%=theme.getimagebaseurl()%>toolbar_filler.gif" width="5" height="24"></TD>
    </TR>
</TABLE>
<br>
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
                            <td nowrap class="producttitle" width="550">The following item(s) have shipped</td>
                        </tr>
                    </table>
                    <br>
                    <table width="250" border="0">
                        <tr>
                            <td nowrap><b>Packing Slip ID:</b></td>
                            <td nowrap><%=new Integer(packingslip.getid()).toString()%></td>
                        </tr>
                        <tr>
                            <td nowrap><b>Date/Time:</b></td>
                            <td nowrap><%=dateFormat.format(packingslip.getcreationdate())%></td>
                        </tr>
                    </table>
                    <br>
                    <table cellSpacing="1" cellPadding="1" width="550" border="0">
                        <tr>
                            <td valign="top">
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td nowrap><b>Carrier Information</b></td>
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
                                        <td nowrap>Tracking #:<%=packingslip.gettrackingNumber()==null?"":packingslip.gettrackingNumber()%></td>
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
                                <table border="0" cellSpacing="0" cellPadding="0">
                                <tr>
                                    <td colspan="2" nowrap><b>Credit Card Used</b></td>
                                </tr>
                                <tr>
                                    <td nowrap>Number:</td>
                                    <td><%=DisplayCreditCard(creditcard.getnumber())%></td>
                                </tr>
                                <tr>
                                    <td nowrap>Expiration:</td>
                                    <td nowrap><%=creditcard.getexpmonth()%>/<%=creditcard.getexpyear()%></td>
                                </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                    <br>
                    <table cellSpacing="1" cellPadding="3" width="550" border="0">
                        <tr>
                            <td><b>Product ID</b></td>
                            <td><b>Description</b></td>
                            <td align="right"><b>Unit Price</b></td>
                            <td align="center"><b>Qty Shipped</b></td>
                            <td align="right"><b>Item Total</b></td>
                        </tr>
                        <tr>
                            <td colspan="10">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td bgcolor="<%=theme.getcolor1()%>"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="550" height="1" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <%
                            double subtotal = 0.0;
                            Iterator itInvoiceItems = invoice.getitemsIterator();
                            Iterator itPackingslipItems = packingslip.getitemsIterator();
                            while(itInvoiceItems.hasNext() && itPackingslipItems.hasNext())
                            {
                                InvoiceItem invoiceItem = (InvoiceItem)itInvoiceItems.next();
                                subtotal += invoiceItem.gettotalPrice();
                                PackingslipItem packingslipItem = (PackingslipItem)itPackingslipItems.next();
                        %>
                                    <tr>
                                        <td><%=packingslipItem.getisin()%></td>
                                        <td><%=packingslipItem.getproductname()%></td>
                                        <td align="right"><%=moneyFormat.format(invoiceItem.getunitPrice())%></td>
                                        <td align="center"><%=new Integer(packingslipItem.getquantity()).toString()%></td>
                                        <td align="right"><%=moneyFormat.format(invoiceItem.gettotalPrice())%></td>
                                    </tr>
                        <%
                            }
                        %>
                        <tr>
                            <td colspan="10">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td bgcolor="<%=theme.getcolor1()%>"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="550" height="1" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td colspan="2" align="right">Merchandise Total:</td>
                            <td align="right"><%=moneyFormat.format(subtotal)%></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td colspan="3" align="right">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td bgcolor="<%=theme.getcolor1()%>"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="220" height="1" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td colspan="2" align="right">Shipping Cost:</td>
                            <td align="right"><%=moneyFormat.format(invoice.getshippingcost()+invoice.gethandling())%></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td colspan="2" align="right">Taxes:</td>
                            <td align="right"><%=moneyFormat.format(invoice.gettaxes())%></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td colspan="3" align="right">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td bgcolor="<%=theme.getcolor1()%>"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="220" height="1" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td colspan="2" align="right">Total:</td>
                            <td align="right"><%=moneyFormat.format(invoice.gettotal())%></td>
                        </tr>
                    </table>
                    <br>
                    <br>
                    <table border="0" width="550">
                        <tr>
                            <td colspan="2">If you have any questions, you can reply to this e-mail or call us anytime.  Please note that it may take up to 24 hours for the tracking # to become active in the carrier's system.</td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <table border="0">
                                    <tr>
                                        <td>Customer Support:</td>
                                        <td><%=company.getcustomerservice()%></td>
                                    </tr>
                                    <tr>
                                        <td>E-mail:</td>
                                        <td><a href="mailto:<%=company.getemail2()%>"><%=company.getemail2()%></a></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td><b>Thanks again for your order!</b></td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td><%=company.getcompany()%></td>
                        </tr>
                        <tr>
                            <td><a href="<%=company.geturl()%>"><%=company.geturl()%></a></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </BODY>
    <SCRIPT LANGUAGE="JavaScript">
    </SCRIPT>
</HTML>
