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
    DateFormat dateFormat2 = new SimpleDateFormat("MM/dd/yyyy");
    DecimalFormat moneyFormat = new DecimalFormat("$#,##0.00");

    String fromDateString = null;
    String toDateString = null;

    GetInvoicesResponse getInvoicesResponse = null;
    boolean declinedinvoicesonly = false;
    try {
        java.util.Date fromdate = Calendar.getInstance().getTime();
        java.util.Date todate = Calendar.getInstance().getTime();

        if(request.getParameter("fromdate") != null && request.getParameter("todate") != null)
        {
            fromdate = dateFormat2.parse(request.getParameter("fromdate"));
            todate = dateFormat2.parse(request.getParameter("todate"));
        }

        if( request.getParameter("declinedinvoicesonly") != null &&
            request.getParameter("declinedinvoicesonly").compareToIgnoreCase("on") == 0)
        {
            declinedinvoicesonly = true;
        }

        InvoiceBean invoiceBean = new InvoiceBean();
        GetInvoicesRequest getInvoicesRequest = new GetInvoicesRequest();
        getInvoicesRequest.setstartdate(fromdate);
        getInvoicesRequest.setenddate(todate);
        getInvoicesRequest.setdeclined(declinedinvoicesonly);
        getInvoicesResponse = invoiceBean.GetInvoices(getInvoicesRequest);

        fromDateString = dateFormat2.format(fromdate);
        toDateString = dateFormat2.format(todate);
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<HTML>
    <%@ include file="datevalidation.jsp" %>
    <SCRIPT LANGUAGE="JavaScript">
        function OnSubmitForm()
        {
            if(ValidateDate(document.form1.fromdate) == false)
            {
                return false;
            }

            if(ValidateDate(document.form1.todate) == false)
            {
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
                            <td class="producttitle">Invoices</td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                    </table>
                    <form name="form1" action="./invoices.jsp" method="GET" onsubmit="return(OnSubmitForm())">
                    <table border="0">
                        <tr>
                            <td>From:</td>
                            <td><input type="text" name="fromdate" size="12" onchange="OnChangeDate()" ></td>
                            <td nowrap>&nbsp;&nbsp;</td>
                            <td>To:</td>
                            <td><input type="text" name="todate" size="12" onchange="OnChangeDate()" ></td>
                            <td nowrap>&nbsp;&nbsp;</td>
                            <td><input type="submit" name="go" value="Go"/></td>
                        </tr>
                        <tr>
                            <td colspan="5" nowrap="nowrap">Display declined invoices only&nbsp;
                                <input type="checkbox" name="declinedinvoicesonly" <%=declinedinvoicesonly==false?"":"checked"%>/>
                            </td>
                        </tr>
                    </table>
                    </form>
                    <TABLE cellSpacing="1" cellPadding="3" width="550" border="0">
                        <tr>
                            <td align="left"><b>Invoice Number</b></td>
                            <td aligh="left"><b>Creation Date</b></td>
                            <td aligh="left"><b>Customer</b></td>
                            <td align="right"><b>Invoice Total</b></td>
                            <td align="left"><b>Status</b></td>
                        </tr>
                        <tr>
                            <td colspan="12">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <td bgcolor="<%=theme.getcolor1()%>"><img src="./images/spacer.gif" width="550" height="1" /></td>
                                </table>
                            </td>
                        </tr>
                    <%
                        double grandtotal = 0.0;
                        Iterator itInvoices = getInvoicesResponse.getinvoiceIterator();
                        while(itInvoices.hasNext())
                        {
                            Invoice invoice = (Invoice)itInvoices.next();
                            grandtotal += invoice.gettotal();
                    %>
                        <tr>
                            <td align="left"><a href="./invoice.jsp?id=<%=new Integer(invoice.getid()).toString()%>"><%=new Integer(invoice.getid()).toString()%></a></td>
                            <td nowrap align="left"><%=dateFormat.format(invoice.getcreationdate())%></td>
                            <td nowrap align="left"><a href="./customer.jsp?customerid=<%=new Integer(invoice.getcustomer().getid()).toString()%>"><%=invoice.getcustomer().getlast()%>, <%=invoice.getcustomer().getfirst()%></td>
                            <td nowrap align="right"><%=moneyFormat.format(invoice.gettotal())%></td>
                            <td nowrap align="left"><%=invoice.getstatus()%></td>
                        </tr>
                    <%
                        }
                    %>
                    <%
                        if(grandtotal > 0.0)
                        {
                    %>
                        <tr>
                            <td colspan="12">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <td bgcolor="<%=theme.getcolor1()%>"><img src="./images/spacer.gif" width="550" height="1" /></td>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3"></td>
                            <td nowrap align="right"><%=moneyFormat.format(grandtotal)%></td>
                        </tr>
                    <%
                        }
                    %>
                    </TABLE>
                </td>
            </tr>
        </table>
    </BODY>
    <SCRIPT LANGUAGE="JavaScript">
        document.form1.fromdate.value = '<%=fromDateString%>';
        document.form1.todate.value = '<%=toDateString%>';
    </SCRIPT>
</HTML>
