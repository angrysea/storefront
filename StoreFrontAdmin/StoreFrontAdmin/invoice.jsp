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

    Invoice invoice = null;
    try {
        InvoiceBean invoiceBean = new InvoiceBean();
        GetInvoiceRequest getInvoiceRequest = new GetInvoiceRequest();
        getInvoiceRequest.setid(new Integer(request.getParameter("id")).intValue());
        GetInvoiceResponse getInvoiceResponse = invoiceBean.GetInvoice(getInvoiceRequest);
        invoice = getInvoiceResponse.getinvoice();
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<HTML>
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
                    <table id="headingTable" height="100%" border="0">
                        <tr>
                            <td><img src="./images/spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td class="producttitle">Invoice</td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                    </table>

                    <table border="0" width="100">
                        <tr>
                            <td nowrap align="left"><b>Invoice ID:</b></td>
                            <td align="left"><%=new Integer(invoice.getid()).toString()%></td>
                        </tr>
                        <tr>
                            <td align="left"><b>Date:</b></td>
                            <td align="left" nowrap><%=dateFormat.format(invoice.getcreationdate())%></td>
                        </tr>
                        <tr>
                            <td align="left"><b>Status:</b></td>
                            <td align="left" nowrap><%=invoice.getstatus()==null?"":invoice.getstatus()%></td>
                        </tr>
                        <tr>
                            <td align="left"><b>Authorization Code:</b></td>
                            <td align="left" nowrap><%=invoice.getauthorizationcode()==null?"":invoice.getauthorizationcode()%></td>
                        </tr>
                        <tr>
                            <td align="left"><b>Sales Order:</b></td>
                            <td align="left" nowrap><a href="./salesorder.jsp?orderid=<%=new Integer(invoice.getsalesorderid()).toString()%>"><%=new Integer(invoice.getsalesorderid()).toString()%></a></td>
                        </tr>
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
                                    <td nowrap><%=invoice.getcustomer().getfullname()%></td>
                                </tr>
                                <tr>
                                    <td nowrap><%=invoice.getbilling().getaddress1()%></td>
                                </tr>
                                <%
                                if(invoice.getbilling().getaddress2() != null && invoice.getbilling().getaddress2().length() > 0)
                                {
                                %>
                                    <tr>
                                        <td nowrap><%=invoice.getbilling().getaddress2()%></td>
                                    </tr>
                                <%
                                }
                                %>
                                <tr>
                                    <td nowrap><%=invoice.getbilling().getcity()%>, <%=invoice.getbilling().getstate().compareTo("0")==0?"":invoice.getbilling().getstate()%> <%=invoice.getbilling().getzip()%> </td>
                                </tr>
                                <tr>
                                    <td nowrap><%=invoice.getbilling().getcountry()%></td>
                                </tr>
                                <tr>
                                    <td nowrap><%=invoice.getbilling().getphone()%></td>
                                </tr>
                                <tr>
                                    <td nowrap><%=invoice.getcustomer().getemail1()%></td>
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
                                    <td><%=invoice.getbilling().getcreditcard().getnumber()%></td>
                                </tr>
                                <tr>
                                    <td nowrap>Expiration:</td>
                                    <td nowrap><%=invoice.getbilling().getcreditcard().getexpmonth()%>/<%=invoice.getbilling().getcreditcard().getexpyear()%></td>
                                </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                    <br>
                    <table cellSpacing="1" cellPadding="2" border="0" width="550">
                    <tr>
                        <td nowrap align="left"><b>Item</b></td>
                        <td nowrap align="right"></td>
                        <td nowrap align="right"><b>Unit Price</b></td>
                        <td nowrap align="center"><b>Quantity</b></td>
                        <td nowrap align="right"><b>Item Total</b></td>
                    </tr>
                    <tr>
                        <td colspan="5">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td bgcolor="<%=theme.getcolor1()%>"><img src="./images/spacer.gif" width="550" height="2"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <%
                        Iterator itInvoiceItems = invoice.getitemsIterator();
                        while(itInvoiceItems.hasNext())
                        {
                            InvoiceItem invoiceitem = (InvoiceItem)itInvoiceItems.next();
                    %>
                            <tr>
                                <td align="left"><%=new Integer(invoiceitem.getitemnumber()).toString()%></td>
                                <td align="right"></td>
                                <td align="right"><%=moneyFormat.format(invoiceitem.getunitPrice())%></td>
                                <td align="center"><%=new Integer(invoiceitem.getquantity()).toString()%></td>
                                <td align="right"><%=moneyFormat.format(((double)invoiceitem.getquantity()*invoiceitem.getunitPrice()))%></td>
                            </tr>
                    <%
                        }
                    %>
                    <tr>
                        <td colspan="5">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td bgcolor="<%=theme.getcolor1()%>"><img src="./images/spacer.gif" width="550" height="1"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="4" align="right" nowrap><b>Merchandise Total:</b></td>
                        <td align="right"><b><%=moneyFormat.format(invoice.gettotalcost())%></b></td>
                    </tr>
            <%
                if(invoice.getdiscount() > 0.0)
                {
            %>
                    <tr>
                        <td colspan="3" align="right" nowrap><%=invoice.getdiscountdescription()%></td>
                        <td align="right"><font color="red">- <%=moneyFormat.format(invoice.getdiscount())%></font></td>
                        <td></td>
                    </tr>
            <%
                }
            %>
                    <tr>
                        <td colspan="1"></td>
                        <td colspan="4" align="right">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td bgcolor="<%=theme.getcolor1()%>"><img src="./images/spacer.gif" width="250" height="1"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="4" align="right" nowrap>Shipping Costs:</td>
                        <td align="right"><%=moneyFormat.format(invoice.gethandling() + invoice.getshippingcost())%></td>
                    </tr>
                    <tr>
                        <td colspan="4" align="right" nowrap><%=invoice.gettaxesdescription()%>:</td>
                        <td align="right"><%=moneyFormat.format(invoice.gettaxes())%></td>
                    </tr>
                    <tr>
                        <td colspan="1"></td>
                        <td colspan="4" align="right">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td bgcolor="<%=theme.getcolor1()%>"><img src="./images/spacer.gif" width="250" height="1"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="4" align="right" nowrap><b>Grand Total:</b></td>
                        <td align="right"><b><%=moneyFormat.format(invoice.gettotal())%></b></td>
                    </tr>
                    </table>
                    <table border="0">
                        <tr>
                            <td><br /></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>
