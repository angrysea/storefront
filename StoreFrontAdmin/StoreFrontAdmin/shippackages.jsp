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
    DecimalFormat numberFormat = new DecimalFormat("##0.00");
    GetPackingslipsResponse getPackingslipsResponse = null;
    try
    {
        PackingslipBean packingslipBean = new PackingslipBean();
        getPackingslipsResponse = packingslipBean.GetOpenPackingslips(new GetPackingslipsRequest());
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
            if(AreItemsSelected() == false)
                return false;

            return(AreTrackingNumbersEntered());
        }

        function AreItemsSelected()
        {
        <%
            Iterator itPackingslips = getPackingslipsResponse.getpackingslipIterator();
            for(int i=0 ; itPackingslips.hasNext() ; i++)
            {
                Packingslip packingslip = (Packingslip)itPackingslips.next();
        %>
                if(document.form1.packingslip<%=new Integer(packingslip.getid()).toString()%>.checked)
                {
                    return true;
                }
        <%
            }
        %>
            alert("At least one item must be selected to create a package.");
            return false;
        }

        function AreTrackingNumbersEntered()
        {
        <%
            itPackingslips = getPackingslipsResponse.getpackingslipIterator();
            for(int i=0 ; itPackingslips.hasNext() ; i++)
            {
                Packingslip packingslip = (Packingslip)itPackingslips.next();
        %>
                if( document.form1.packingslip<%=new Integer(packingslip.getid()).toString()%>.checked && document.form1.trackingnumber<%=new Integer(packingslip.getid()).toString()%>.value.trim().length == 0)
                {
                    alert("A tracking number was not entered.");
                    document.form1.trackingnumber<%=new Integer(packingslip.getid()).toString()%>.focus();
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
                    <table border="0">
                        <tr>
                            <td><img src="./images/spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td class="producttitle">Ship Packages</td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                    </table>
            <%
                itPackingslips = getPackingslipsResponse.getpackingslipIterator();
                if(itPackingslips.hasNext())
                {
            %>
                    <form name="form1" action="./shippackages_result.jsp" method="get" onsubmit="return(OnSubmitForm());">
                    <table cellSpacing="1" cellPadding="3" width="700" border="0">
                        <tr>
                            <td colspan="10">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td bgcolor="<%=theme.getcolor1()%>"><img src="./spacer.gif" width="700" height="1" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    <%
                        itPackingslips = getPackingslipsResponse.getpackingslipIterator();
                        for(int i=0 ; itPackingslips.hasNext() ; i++)
                        {
                            Packingslip packingslip = (Packingslip)itPackingslips.next();
                    %>
                        <tr>
                            <td>
                                <table border="0">
                                    <tr>
                                        <td align="center"><b>Select</b></td>
                                        <td></td>
                                        <td><b>Ship To</b></td>
                                        <td><b>Shipping Method</b></td>
                                        <td><b>Tracking #</b></td>
                                    </tr>
                                    <tr>
                                        <td valign="top" align="center"><input type="checkbox" name="packingslip<%=new Integer(packingslip.getid()).intValue()%>"></td>
                                        <td>&nbsp;</td>
                                        <td valign="top" width="200">
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><%=packingslip.getshipping().getfirst()%> <%=packingslip.getshipping().getlast()%></td>
                                                </tr>
                                            <%
                                                if(packingslip.getshipping().getcompany() != null && packingslip.getshipping().getcompany().length() > 0)
                                                {
                                            %>
                                                <tr>
                                                    <td><%=packingslip.getshipping().getcompany()%></td>
                                                </tr>
                                            <%
                                                }
                                            %>
                                                <tr>
                                                    <td><%=packingslip.getshipping().getaddress1()%></td>
                                                </tr>
                                            <%
                                                if(packingslip.getshipping().getaddress2() != null && packingslip.getshipping().getaddress2().length() > 0)
                                                {
                                            %>
                                                <tr>
                                                    <td><%=packingslip.getshipping().getaddress2()%></td>
                                                </tr>
                                            <%
                                                }
                                            %>
                                                <tr>
                                                    <td><%=packingslip.getshipping().getcity()%>, <%=packingslip.getshipping().getstate().compareTo("0")==0?"":packingslip.getshipping().getstate()%> <%=packingslip.getshipping().getzip()%></td>
                                                </tr>
                                                <tr>
                                                    <td><%=packingslip.getshipping().getcountry()%></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td valign="top"><%=packingslip.getshippingmethod().getcarrier()%></td>
                                        <td valign="top"><input name="trackingnumber<%=new Integer(packingslip.getid()).toString()%>" type="text" size="25" /></td>
                                    </tr>
                                    <tr>
                                        <td><br /></td>
                                    </tr>
                                    <tr>
                                        <td></td>
                                        <td colspan="10">
                                            <table border="0" width="300">
                                                <tr>
                                                    <td nowrap><b>Item ID</b></td>
                                                    <td nowrap><b>Quantity</b></td>
                                                    <td nowrap><b>Product Name</b></td>
                                                    <td nowrap><b>Sales Order ID</b></td>
                                                </tr>
                                                <tr>
                                                    <td colspan="10">
                                                        <table border="0" cellpadding="0" cellspacing="0">
                                                            <tr>
                                                                <td bgcolor="<%=theme.getcolor1()%>"><img src="./spacer.gif" width="300" height="1" /></td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                            <%
                                                Iterator itPackingSlipItems = packingslip.getitemsIterator();
                                                while(itPackingSlipItems.hasNext())
                                                {
                                                    PackingslipItem packingslipItem = (PackingslipItem)itPackingSlipItems.next();
                                            %>
                                                <tr>
                                                    <td><%=packingslipItem.getisin()%></td>
                                                    <td><%=new Integer(packingslipItem.getquantity()).toString()%></td>
                                                    <td><%=packingslipItem.getproductname()%></td>
                                                    <td><a href="./salesorder.jsp?orderid=<%=new Integer(packingslipItem.getsalesorderid()).toString()%>"><%=new Integer(packingslipItem.getsalesorderid()).toString()%></a></td>
                                                </tr>
                                            <%
                                                }
                                            %>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="10">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td bgcolor="<%=theme.getcolor1()%>"><img src="./spacer.gif" width="700" height="1" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    <%
                        }
                    %>
                    </table>
                    <table border="0">
                        <tr>
                            <td><input type="submit" value="Ship Packages" /></td>
                        </tr>
                    </table>
                    </form>
            <%
                }
                else
                {
            %>
                    <table cellSpacing="1" cellPadding="3" width="700" border="0">
                        <tr>
                            <td>No packages are available to ship.  Packing slips must be created first.</td>
                        </tr>
                    </table>
            <%
                }
            %>
                </td>
            </tr>
        </table>
    </BODY>
    <SCRIPT LANGUAGE="JavaScript">
    </SCRIPT>
</HTML>
