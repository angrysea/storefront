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
    Customer customer = null;
    AddPackingslipResponse addPackingslipResponse = null;
    boolean itemFound = false;
    try {
        CustomerBean customerBean = new CustomerBean();
        GetCustomerRequest getCustomerRequest = new GetCustomerRequest();
        getCustomerRequest.setid(new Integer(request.getParameter("customerid")).intValue());
        GetCustomerResponse getCustomerResponse = customerBean.GetCustomer(getCustomerRequest);
        customer = getCustomerResponse.getcustomer();

        PackingslipBean packingslipBean = new PackingslipBean();
        AddPackingslipRequest addPackingslipRequest = new AddPackingslipRequest();
        Packingslip packingslip = new Packingslip();
        packingslip.setcustomerid(new Integer(request.getParameter("customerid")).intValue());
        packingslip.setcarrierName(request.getParameter("shippingmethod"));
        packingslip.settrackingNumber(request.getParameter("trackingnumber"));

        SalesOrderBean salesOrderBean = new SalesOrderBean();
        GetSalesOrdersRequest getSalesOrdersRequest = new GetSalesOrdersRequest();
        getSalesOrdersRequest.setcustomerid(customer.getid());
        GetSalesOrdersResponse getSalesOrdersResponse = salesOrderBean.GetOpenSalesOrders(getSalesOrdersRequest);

        Iterator itSalesOrders = getSalesOrdersResponse.getsalesorderIterator();
        while(itSalesOrders.hasNext())
        {
            SalesOrder salesOrder = (SalesOrder)itSalesOrders.next();
            Iterator itItems = salesOrder.getitemsIterator();
            while(itItems.hasNext())
            {
                SalesOrderItem salesOrderItem = (SalesOrderItem)itItems.next();

                String name = "item" + new Integer(salesOrderItem.getid()).toString();
                if(request.getParameter(name) != null && request.getParameter(name).compareToIgnoreCase("on") == 0)
                {
                    itemFound = true;

                    PackingslipItem packingslipItem = new PackingslipItem();
                    packingslipItem.setsalesorderitemid(salesOrderItem.getid());
                    packingslip.setitems(packingslipItem);
                }
            }
        }

        // Add the packing slip
        if(itemFound)
        {
            addPackingslipRequest.setpackingslip(packingslip);
            addPackingslipResponse = packingslipBean.AddPackingslip(addPackingslipRequest);
        }

        // Update the sales order status
        itSalesOrders = getSalesOrdersResponse.getsalesorderIterator();
        while(itSalesOrders.hasNext())
        {
            SalesOrder salesOrder = (SalesOrder)itSalesOrders.next();
            salesOrderBean.UpdateStatus(salesOrder.getid());
        }

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
                            <td class="producttitle">Create a Packing Slip</td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                        <%
                            if(itemFound)
                            {
                        %>
                            <tr>
                                <td>Packing slip <%=new Integer(addPackingslipResponse.getid()).toString()%> has been created for <%=customer.getfirst()%> <%=customer.getlast()%></td>
                            </tr>
                        <%
                            }
                            else
                            {
                        %>
                            <tr>
                                <td><font color="red">The packing slip was not created.  No items were selected for the packing slip.</font></td>
                            </tr>
                        <%
                            }
                        %>
                    </table>
                </td>
            </tr>
        </table>
    </BODY>
    <SCRIPT LANGUAGE="JavaScript">
    </SCRIPT>
</HTML>
