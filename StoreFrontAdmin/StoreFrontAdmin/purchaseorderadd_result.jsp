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
    CreatePurchaseOrderResponse createPurchaseOrderResponse = null;
    try
    {
        PurchaseOrderBean purchaseOrderBean = new PurchaseOrderBean();
        CreatePurchaseOrderRequest createPurchaseOrderRequest = new CreatePurchaseOrderRequest();
        createPurchaseOrderRequest.setdistributor(new Integer(request.getParameter("distributor")).intValue());
        createPurchaseOrderRequest.setshippingmethod(new Integer(request.getParameter("shippingmethod")).intValue());

        // Determine which items were selected
        GetItemsResponse getItemsResponse = new GetItemsResponse();
        ItemBean itemBean = new ItemBean();
        GetItemsRequest getItemsRequest = new GetItemsRequest();
        getItemsRequest.setorderby("distributor");
        getItemsRequest.setdirection("asc");
        getItemsResponse = itemBean.GetItemsToOrder(getItemsRequest);
        Iterator itItems = getItemsResponse.getitemsIterator();
        while(itItems.hasNext())
        {
            Item item = (Item)itItems.next();
            String itemParameter = "item" + new Integer(item.getid()).toString();
            if(request.getParameter(itemParameter) != null && request.getParameter(itemParameter).compareToIgnoreCase("on") == 0)
            {
                String param = "qtytoorder" + new Integer(item.getid()).toString();
                if(request.getParameter(param) != null)
                    item.setquantitytoorder(new Integer(request.getParameter(param)).intValue());
                createPurchaseOrderRequest.setitems(item);
            }
        }

        createPurchaseOrderResponse = purchaseOrderBean.CreatePurchaseOrder(createPurchaseOrderRequest);
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
                            <td class="producttitle">Create Purchase Order Result</td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                    </table>
                    <table cellSpacing="1" cellPadding="3" width="700" border="0">
                        <tr>
                    <%
                        if(createPurchaseOrderResponse.getpurchaseorder() == 0)
                        {
                    %>
                            <td><font color="red">An error occured creating the purchase order.</font></td>
                    <%
                        }
                        else
                        {
                    %>
                            <td>Purchase order <%=createPurchaseOrderResponse.getpurchaseorder()%> was created successfully and an e-mail was sent to the distributor.</td>
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
