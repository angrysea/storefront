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
    int count = 0;
    CreatePackingslipsResponse createPackingslipsResponse = null;
    GetSalesOrdersResponse getSalesOrdersResponse = null;
    try
    {
        PackingslipBean packingslipBean = new PackingslipBean();
        CreatePackingslipsRequest createPackingslipsRequest = new CreatePackingslipsRequest();

        // Determine which items were selected
        SalesOrderBean salesOrderBean = new SalesOrderBean();
        GetSalesOrdersRequest getSalesOrdersRequest = new GetSalesOrdersRequest();
        getSalesOrdersResponse = salesOrderBean.GetOpenSalesOrders(getSalesOrdersRequest);
        Iterator itSalesOrders = getSalesOrdersResponse.getsalesorderIterator();
        while(itSalesOrders.hasNext())
        {
            SalesOrder salesOrder = (SalesOrder)itSalesOrders.next();
            Iterator itItems = salesOrder.getitemsIterator();
            while(itItems.hasNext())
            {
                SalesOrderItem salesOrderItem = (SalesOrderItem)itItems.next();
                String itemParameter = "item" + new Integer(salesOrderItem.getid()).toString();
                if(request.getParameter(itemParameter) != null && request.getParameter(itemParameter).compareToIgnoreCase("on") == 0)
                {
                    createPackingslipsRequest.setsalesorderitems(salesOrderItem);
                }
            }
        }

        // Create the packing slips
        createPackingslipsResponse = packingslipBean.CreatePackingslips(createPackingslipsRequest);

        // Add the packing slips
        AddPackingslipsRequest addPackingslipsRequest = new AddPackingslipsRequest();
        Iterator itPackingslips = createPackingslipsResponse.getpackingslipIterator();
        for( ; itPackingslips.hasNext() ; count++)
        {
            Packingslip packingslip = (Packingslip)itPackingslips.next();
            addPackingslipsRequest.setpackingslip(packingslip);
        }
        packingslipBean.AddPackingslips(addPackingslipsRequest);
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
                            <td class="producttitle">Packing Slip Wizard</td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                        <tr>
                            <td><h2>Step 3 - The packing slips have been generated.</h2></td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                    </table>
                    <table cellSpacing="1" cellPadding="3" width="700" border="0">
                        <tr>
                    <%
                        if(count == 1)
                        {
                    %>
                            <td><%=new Integer(count).toString()%> packing slip was added to the system.</td>
                    <%
                        }
                        else
                        {
                    %>
                            <td><%=new Integer(count).toString()%> packing slips were added to the system.</td>
                    <%
                        }
                    %>
                        </tr>

                    </table>
                </td>
            </tr>
        </table>
    </BODY>
    <SCRIPT LANGUAGE="JavaScript">
    </SCRIPT>
</HTML>
