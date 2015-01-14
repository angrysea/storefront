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

    String fromDateString = null;
    String toDateString = null;

    GetPackingslipsResponse getPackingslipsResponse = null;
    try {
        java.util.Date fromdate = Calendar.getInstance().getTime();
        java.util.Date todate = Calendar.getInstance().getTime();

        if(request.getParameter("fromdate") != null && request.getParameter("todate") != null)
        {
            fromdate = dateFormat2.parse(request.getParameter("fromdate"));
            todate = dateFormat2.parse(request.getParameter("todate"));
        }

        PackingslipBean packingslipBean = new PackingslipBean();
        GetPackingslipsRequest getPackingslipsRequest = new GetPackingslipsRequest();
        getPackingslipsRequest.setstartdate(fromdate);
        getPackingslipsRequest.setenddate(todate);
        getPackingslipsResponse = packingslipBean.GetPackingslips(getPackingslipsRequest);

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
                            <td class="producttitle">Packing Slips</td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                    </table>
                    <form name="form1" action="./packingslips.jsp" method="GET" onsubmit="return(OnSubmitForm())">
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
                    </table>
                    </form>
                    <TABLE cellSpacing="1" cellPadding="3" width="700" border="0">
                        <tr>
                            <td><b>ID</b></td>
                            <td><b>Creation Date</b></td>
                            <td><b>Customer</b></td>
                            <td><b>Carrier</b></td>
                            <td><b>Tracking Number</b></td>
                        </tr>
                        <tr>
                            <td colspan="12">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <td bgcolor="<%=theme.getcolor1()%>"><img src="./images/spacer.gif" width="700" height="1" /></td>
                                </table>
                            </td>
                        </tr>
                        <%
                            boolean packingslips = false;
                            CustomerBean customerBean = new CustomerBean();

                            Iterator itPackingslips = getPackingslipsResponse.getpackingslipIterator();
                            while(itPackingslips.hasNext())
                            {
                                Packingslip packingslip = (Packingslip)itPackingslips.next();
                                packingslips = true;

                                GetCustomerRequest getCustomerRequest = new GetCustomerRequest();
                                getCustomerRequest.setid(packingslip.getcustomerid());
                                GetCustomerResponse getCustomerResponse = customerBean.GetCustomer(getCustomerRequest);
                                Customer customer = getCustomerResponse.getcustomer();
                        %>
                                <tr>
                                    <td nowrap><a href="./packingslip.jsp?packingslipid=<%=new Integer(packingslip.getid()).toString()%>&customerid=<%=new Integer(customer.getid()).toString()%>"><%=new Integer(packingslip.getid()).toString()%></a></td>
                                    <td nowrap><%=dateFormat.format(packingslip.getcreationdate())%></td>
                                    <td nowrap><%=customer.getfirst()%> <%=customer.getlast()%></td>
                                    <td nowrap><%=packingslip.getcarrierName()==null?"none":packingslip.getcarrierName()%></td>
                                    <td nowrap><%=packingslip.gettrackingNumber()==null?"none":packingslip.gettrackingNumber()%></td>
                                </tr>
                        <%
                            }
                        %>
                        <%
                            if(packingslips == true)
                            {
                        %>
                            <tr>
                                <td colspan="12">
                                    <table border="0" cellpadding="0" cellspacing="0">
                                        <td bgcolor="<%=theme.getcolor1()%>"><img src="./images/spacer.gif" width="700" height="1" /></td>
                                    </table>
                                </td>
                            </tr>
                        <%
                            }
                        %>
                        <%
                            if(packingslips == false)
                            {
                        %>
                        <tr>
                            <td colspan="12" nowrap>There are no packing slips in the system for this time period.</td>
                        </tr>
                        <%
                            }
                        %>
                        <tr>
                            <td><br></td>
                        </tr>
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
