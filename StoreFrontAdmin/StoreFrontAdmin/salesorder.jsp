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

    SalesOrder salesOrder = null;
    GetNotesResponse getNotesResponse = new GetNotesResponse();
    int noteCount = 0;
    try {
        SalesOrderBean salesOrderBean = new SalesOrderBean();
        GetSalesOrderRequest getSalesOrderRequest = new GetSalesOrderRequest();
        getSalesOrderRequest.setid(new Integer(request.getParameter("orderid")).intValue());
        GetSalesOrderResponse getSalesOrderResponse = salesOrderBean.GetSalesOrder(getSalesOrderRequest);
        salesOrder = getSalesOrderResponse.getsalesorder();

        NotesBean notesBean = new NotesBean();
        GetNotesRequest getNotesRequest = new GetNotesRequest();
        getNotesRequest.setreferencenumber(new Integer(salesOrder.getid()).toString());
        getNotesRequest.settype("salesorder");
        getNotesResponse = notesBean.GetNotes(getNotesRequest);
        Iterator itNotes = getNotesResponse.getnoteIterator();
        for(noteCount=0 ; itNotes.hasNext() ; noteCount++)
            itNotes.next();
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<HTML>
    <SCRIPT LANGUAGE="JavaScript">
        function OnClickNotes()
        {
            mywindow = window.open("./notes.jsp?referencenumber=<%=new Integer(salesOrder.getid()).toString()%>&type=salesorder", "Notes", "location=0,status=0,toolbar=0,scrollbars=1,menubar=0,directories=0,resizable=0,width=600,height=500");
        }

        function OnSubmitDeleteForm()
        {
            var answer = confirm("Are you sure you want to delete this sales order?");
            if(answer)
            {
                return true;
            }

            return false;
        }

        function OnSubmitDropShipForm()
        {
            if(document.dropshipform.distributor.value == 0)
            {
                alert("A distributor must be selected when drop shipping an order.");
                document.dropshipform.distributor.focus();
                return false;
            }

            var answer = confirm("Are you sure you want to drop ship this sales order?");
            if(answer)
            {
                return true;
            }

            return false;
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
                            <td class="producttitle">Sales Order</td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                    </table>

                    <table border="0" width="100">
                        <tr>
                            <td nowrap align="left"><b>ID:</b></td>
                            <td align="left"><%=new Integer(salesOrder.getid()).toString()%></td>
                        </tr>
                        <tr>
                            <td align="left"><b>Date:</b></td>
                            <td align="left" nowrap><%=dateFormat.format(salesOrder.getcreationdate())%></td>
                        </tr>
                        <tr>
                            <td align="left"><b>Status:</b></td>
                            <td align="left" nowrap><%=salesOrder.getstatus()%></td>
                        </tr>
                        <tr>
                            <td align="left" nowrap><b>Mail Server Msg:</b></td>
                            <td align="left" nowrap><%=salesOrder.getemailstatus()%></td>
                        </tr>
                        <tr>
                            <td align="left" nowrap><b>Optimize Shipping:</b></td>
                            <td align="left" nowrap><%=salesOrder.getoptimizeshipping()==true?"Yes":"No"%></td>
                        </tr>
                        <tr>
                            <td align="left" nowrap><b>Drop Ship Requested:</b></td>
                            <td align="left" nowrap><%=salesOrder.getdropshipped()==true?"Yes":"No"%></td>
                        </tr>
                <%
                    if(salesOrder.getreferencenumber() > 0)
                    {
                %>
                        <tr>
                            <td align="left" nowrap><b>Purchase Order:</b></td>
                            <td align="left" nowrap><a href="./purchaseorderupdate.jsp?purchaseorderid=<%=new Integer(salesOrder.getreferencenumber()).toString()%>"><%=new Integer(salesOrder.getreferencenumber()).toString()%></a></td>
                        </tr>
                <%
                    }
                %>
                    </table>
                    <table border="0" width="600">
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
                                    <td nowrap><%=salesOrder.getcustomer().getfullname()%></td>
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
                                    <td><%=salesOrder.getbillingaddress().getcreditcard().getnumber()%></td>
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
                    <table cellSpacing="1" cellPadding="2" border="0" width="600">
                    <tr>
                        <td nowrap align="left"><b>Item</b></td>
                        <td align="right"><b>Unit Price</b></td>
                        <td nowrap align="center"><b>Quantity</b></td>
                        <td align="right"><b>Item Total</b></td>
                        <td align="right"><b>Item Status</b></td>
                    </tr>
                    <tr>
                        <td colspan="5">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td bgcolor="<%=theme.getcolor1()%>"><img src="./images/spacer.gif" width="600" height="2"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <%
                        ListsBean listsBean = new ListsBean();
                        Iterator itSalesOrderItems = salesOrder.getitemsIterator();
                        while(itSalesOrderItems.hasNext())
                        {
                            SalesOrderItem salesorderitem = (SalesOrderItem)itSalesOrderItems.next();
                            Manufacturer manufacturer = listsBean.GetManufacturerByName(salesorderitem.getmanufacturer());
                    %>
                            <tr>
                                <td align="left" width="350"><a href="./productupdate.jsp?id=<%=new Integer(salesorderitem.getitemnumber()).toString()%>&manufacturer=<%=new Integer(manufacturer.getid()).toString()%>"><%=salesorderitem.getmanufacturer()%> <%=salesorderitem.getproductname()%></a>
                                    <br>Item number: <%=salesorderitem.getisin()%>, <%=salesorderitem.getitemstatus()%>
                                </td>
                                <td align="right"><%=moneyFormat.format(salesorderitem.getunitprice())%></td>
                                <td align="center"><%=new Integer(salesorderitem.getquantity()).toString()%></td>
                                <td align="right"><%=moneyFormat.format(((double)salesorderitem.getquantity()*salesorderitem.getunitprice()))%></td>
                                <td align="right"><%=salesorderitem.getstatus()%></td>
                            </tr>
                    <%
                        }
                    %>
                    <tr>
                        <td colspan="5">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td bgcolor="<%=theme.getcolor1()%>"><img src="./images/spacer.gif" width="600" height="1"></td>
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
                        <td colspan="4" align="right">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td bgcolor="<%=theme.getcolor1()%>"><img src="./images/spacer.gif" width="250" height="1"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3" align="right" nowrap>Shipping Costs (<%=salesOrder.getshippingmethod().getdescription()%>):</td>
                        <td align="right"><%=moneyFormat.format(salesOrder.getshipping()+salesOrder.gethandling())%></td>
                    </tr>
                    <tr>
                        <td colspan="3" align="right" nowrap><%=salesOrder.gettaxesdescription()%>:</td>
                        <td align="right"><%=moneyFormat.format(salesOrder.gettaxes())%></td>
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
                        <td colspan="3" align="right" nowrap><b>Grand Total:</b></td>
                        <td align="right"><b><%=moneyFormat.format(salesOrder.gettotal())%></b></td>
                    </tr>
                    </table>
                    <br>
                    <table border="0">
                        <tr>
                            <td><input type="button" onclick="OnClickNotes()" value="Notes (<%=new Integer(noteCount).toString()%>)" /></td>
                        </tr>
                    </table>
            <%
                if(salesOrder.getstatus().compareToIgnoreCase("new") == 0)
                {
            %>
                    <form action="./salesorderdelete.jsp" method="GET"  onsubmit="return(OnSubmitDeleteForm())">
                        <input type="hidden" name="orderid" value="<%=request.getParameter("orderid")%>"/>
                    <table border="0">
                        <tr>
                            <td><input type="submit" value="Delete This Order"></td>
                        </tr>
                    </table>
                    </form>
            <%
                }
            %>
                    <form name="dropshipform" action="./salesorderdropship.jsp" method="GET" onsubmit="return(OnSubmitDropShipForm())">
                        <input type="hidden" name="orderid" value="<%=request.getParameter("orderid")%>"/>
                        <input type="hidden" name="priordropship" value="<%=salesOrder.getdropshipped()==true?"1":"0"%>"/>
                    <table border="0">
                    <tr>
                        <td colspan="20">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td bgcolor="<%=theme.getcolor1()%>"><img src="./images/spacer.gif" width="600" height="1"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    </table>
                    <table border="0" width="350">
            <%
                if(salesOrder.getdropshipped())
                {
            %>
                        <tr>
                            <td colspan="5"><font color="red">A "drop ship" was already requested for this order.  By requesting a "drop ship" again, a Drop Ship Order request will be sent to the distributor.</font></td>
                        </tr>
            <%
                }
            %>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td><input type="submit" value="Drop Ship This Order"></td>
                            <td nowrap>&nbsp;&nbsp;From:</td>
                            <td>
                                <select name="distributor">
                                    <option value="0">< select ></option>
                                    <%
                                        ListsBean lists = new ListsBean();
	                                GetDistributorsResponse getDistributorsResponse = lists.GetDistributors(new GetDistributorsRequest());
                                        Iterator itDistributor = getDistributorsResponse.getdistributorsIterator();
                                        Distributor distributor = null;
                                        while(itDistributor.hasNext())
                                        {
                                            distributor = (Distributor)itDistributor.next();
                                    %>
                                            <option value="<%=distributor.getid()%>"><%=distributor.getname()%></option>
                                    <%
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td><a href="./salesorderdropship_mail.jsp?salesorderid=<%=new Integer(salesOrder.getid()).toString()%>">Printable Drop Ship Order</a></td>
                        </tr>
                    </table>
                    </form>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>
