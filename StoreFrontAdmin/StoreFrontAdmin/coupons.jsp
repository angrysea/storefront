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
    DecimalFormat percentFormat = new DecimalFormat("##0.0");
    DecimalFormat moneyFormat = new DecimalFormat("$#,###.00");
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

    GetCouponsResponse getCouponsResponse = new GetCouponsResponse();
    try {
        CouponBean couponBean = new CouponBean();

	GetCouponsRequest getCouponsRequest = new GetCouponsRequest();
        getCouponsResponse = couponBean.GetCoupons(getCouponsRequest);
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>


<HTML>
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
                    <table id="headingTable" height="100%" border="0">
                        <tr>
                            <td><img src="./images/spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td class="producttitle">Coupons</td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                    </table>
                    <TABLE cellSpacing="1" cellPadding="3" width="775" border="0">
                        <TR>
                            <TD class="columnheader" align="left"><b>ID</b></TD>
                            <TD class="columnheader" align="left"><b>Code</b></TD>
                            <TD class="columnheader" align="left"><b>Mfgr</b></TD>
                            <TD class="columnheader" align="left"><b>Item</b></TD>
                            <TD class="columnheader" align="right"><b>Discount</b></TD>
                            <TD class="columnheader" align="center"><b>Single Use</b></TD>
                            <TD class="columnheader" align="left"><b>Expiration Date</b></TD>
                            <TD class="columnheader" align="center"><b>Display on Web</b></TD>
                            <TD class="columnheader" align="right"><b>Redemptions</b></TD>
                        </TR>
                        <tr>
                            <td colspan="10">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td bgcolor="<%=theme.getcolor1()%>"><img src="./spacer.gif" width="775" height="1" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <%
                            Iterator it = getCouponsResponse.getcouponsIterator();
                            while(it.hasNext())
                            {
                                Coupon coupon = (Coupon)it.next();
                        %>
                                <TR>
                                    <TD noWrap align="left"><%=Integer.toString(coupon.getid())%></TD>
                                    <TD noWrap align="left"><a href="./couponupdate.jsp?code=<%=coupon.getcode()%>"><%=coupon.getcode()%></a></TD>
                            <%
                                if(coupon.getmanufacturerid() > 0)
                                {
                                    ListsBean listsBean = new ListsBean();
                                    GetManufacturerRequest getManufacturerRequest = new GetManufacturerRequest();
                                    getManufacturerRequest.setid(coupon.getmanufacturerid());
                                    GetManufacturerResponse getManufacturerResponse = listsBean.GetManufacturer(getManufacturerRequest);
                                    Manufacturer manufacturer = getManufacturerResponse.getmanufacturer();
                            %>
                                    <TD noWrap align="left"><%=manufacturer.getname()%></TD>
                            <%
                                }
                                else
                                {
                            %>
                                    <TD noWrap align="left"</TD>
                            <%
                                }
                            %>
                            <%
                                if(coupon.getitemid() > 0)
                                {
                            %>
                                    <TD noWrap align="left"><%=Integer.toString(coupon.getitemid())%></TD>
                            <%
                                }
                                else
                                {
                            %>
                                    <TD noWrap align="left"></TD>
                            <%
                                }
                            %>
                            <%
                                if(coupon.getdiscounttype() == 1)
                                {
                            %>
                                    <TD noWrap align="right"><%=moneyFormat.format(coupon.getdiscount())%></TD>
                            <%
                                }
                                else if(coupon.getdiscounttype() == 2)
                                {
                            %>
                                    <TD noWrap align="right"><%=percentFormat.format(coupon.getdiscount()*100.0)%>%</TD>
                            <%
                                }
                                else
                                {
                            %>
                                    <TD noWrap align="right">Free Shipping</TD>
                            <%
                                }
                            %>
                                    <TD noWrap align="center"><%=coupon.getsingleuse()==true?"yes":"no"%></TD>
                                    <TD noWrap align="left"><%=dateFormat.format(coupon.getexpirationDate())%></TD>
                                    <TD noWrap align="center"><%=coupon.getdisplay()==true?"yes":"no"%></TD>
                                    <TD noWrap align="right"><%=Integer.toString(coupon.getredemptions())%></TD>
                                </TR>
                        <%
                            }
                        %>
                    </TABLE>
                    <br>
                    <table border="0">
                        <tr>
                            <td class="smallprompt"><A href="./couponadd.jsp">Add a new Coupon</A></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>
