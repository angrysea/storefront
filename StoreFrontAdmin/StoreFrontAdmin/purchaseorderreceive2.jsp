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

    PurchaseOrder purchaseorder = null;
    try {
        PurchaseOrderBean purchaseOrderBean = new PurchaseOrderBean();
        GetPurchaseOrderRequest getPurchaseOrderRequest = new GetPurchaseOrderRequest();
        getPurchaseOrderRequest.setid(new Integer(request.getParameter("purchaseorderid")).intValue());
        GetPurchaseOrderResponse getPurchaseOrderResponse = purchaseOrderBean.GetPurchaseOrder(getPurchaseOrderRequest);
        purchaseorder = getPurchaseOrderResponse.getpurchaseorder();
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<HTML>
    <%@ include file="commonscript.jsp" %>
    <SCRIPT LANGUAGE="JavaScript">
        function OnSubmitForm()
        {
            if(QtyToOrderOK() == false)
                return false;

            return true;
        }

        function QtyToOrderOK()
        {
            var valid = '0123456789';
            <%
                Iterator itPurchaseOrderItems = purchaseorder.getitemsIterator();
                while(itPurchaseOrderItems.hasNext())
                {
                    PurchaseOrderItem purchaseorderitem = (PurchaseOrderItem)itPurchaseOrderItems.next();
            %>
                    if(document.form1.qtyreceiving<%=new Integer(purchaseorderitem.getid()).toString()%>.value.length == 0 || IsValid(document.form1.qtyreceiving<%=new Integer(purchaseorderitem.getid()).toString()%>.value, valid) == false)
                    {
                        alert("An invalid receiving quantity was entered.");
                        document.form1.qtyreceiving<%=new Integer(purchaseorderitem.getid()).toString()%>.focus();
                        return false;
                    }
                    if(document.form1.qtyreceiving<%=new Integer(purchaseorderitem.getid()).toString()%>.value > <%=new Integer(purchaseorderitem.getquantity()-purchaseorderitem.getquantityreceived()).toString()%>)
                    {
                        alert("Quantity receiving cannot exceed quantity ordered + quantity already received!");
                        document.form1.qtyreceiving<%=new Integer(purchaseorderitem.getid()).toString()%>.focus();
                        return false;
                    }
            <%
                }
            %>

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
                    <table id="headingTable" height="100%" border="0">
                        <tr>
                            <td><img src="./images/spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td class="producttitle">Receive Purchase Order</td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                    </table>

                    <form name="form1" action="./purchaseorderreceive3.jsp" method="GET" onsubmit="return(OnSubmitForm());">
                        <input type="hidden" name="purchaseorderid" value="<%=request.getParameter("purchaseorderid")%>"/>
                    <table border="0" width="100">
                        <tr>
                            <td nowrap align="left"><b>ID:</b></td>
                            <td align="left"><%=new Integer(purchaseorder.getid()).toString()%></td>
                        </tr>
                        <tr>
                            <td align="left"><b>Date:</b></td>
                            <td align="left" nowrap><%=dateFormat.format(purchaseorder.getcreationdate())%></td>
                        </tr>
                        <tr>
                            <td align="left"><b>Status:</b></td>
                            <td align="left" nowrap><%=purchaseorder.getstatus()%></td>
                        </tr>
                        <tr>
                            <td align="left" nowrap><b>Distributor:</b></td>
                            <td align="left" nowrap><%=purchaseorder.getdistributor().getname()%></td>
                        </tr>
                        <tr>
                            <td align="left" nowrap><b>Shipping Method:</b></td>
                            <td align="left" nowrap><%=purchaseorder.getshippingmethod().getdescription()%></td>
                        </tr>
                        <tr>
                            <td align="left" nowrap><b>Tracking #:</b></td>
                            <td align="left" nowrap><%=purchaseorder.gettrackingnumber()==null?"":purchaseorder.gettrackingnumber()%></td>
                        </tr>
                    </table>
                    <br>
                    <table cellSpacing="1" cellPadding="2" border="0" width="550">
                    <tr>
                        <td align="left"><b>Item</b></td>
                        <td align="right"><b>Unit Cost</b></td>
                        <td align="center"><b>Quantity Ordered</b></td>
                        <td align="center"><b>Quantity Recieved to Date</b></td>
                        <td align="center"><b>Receiving</b></td>
                        <td align="right"><b>Total Cost</b></td>
                    </tr>
                    <tr>
                        <td colspan="6">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td bgcolor="<%=theme.getcolor1()%>"><img src="./images/spacer.gif" width="550" height="2"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <%
                        itPurchaseOrderItems = purchaseorder.getitemsIterator();
                        while(itPurchaseOrderItems.hasNext())
                        {
                            PurchaseOrderItem purchaseorderitem = (PurchaseOrderItem)itPurchaseOrderItems.next();
                    %>
                            <tr>
                                <td align="left" width="250" nowrap><%=purchaseorderitem.getmanufacturer()%> <%=purchaseorderitem.getproductname()%>
                                    <br>Item number: <%=purchaseorderitem.getisin()%>
                                </td>
                                <td align="right"><%=moneyFormat.format(purchaseorderitem.getourcost())%></td>
                                <td align="center"><%=new Integer(purchaseorderitem.getquantity()).toString()%></td>
                                <td align="center"><%=new Integer(purchaseorderitem.getquantityreceived()).toString()%></td>
                                <td align="center"><input type="text" size="4" name="qtyreceiving<%=new Integer(purchaseorderitem.getid()).toString()%>" value="0"/></td>
                                <td align="right"><%=moneyFormat.format(purchaseorderitem.getourcost()*purchaseorderitem.getquantity())%></td>
                            </tr>
                    <%
                        }
                    %>
                    <tr>
                        <td colspan="6">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td bgcolor="<%=theme.getcolor1()%>"><img src="./images/spacer.gif" width="550" height="1"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="5" align="right" nowrap><b>Total Cost:</b></td>
                        <td align="right"><b><%=moneyFormat.format(purchaseorder.gettotal())%></b></td>
                    </tr>
                    </table>
                    <table border="0">
                        <tr>
                            <td><input type="submit" value="Receive Purchase Order"/></td>
                        </tr>
                    </table>
                    </form>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>
