<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    try {
        CouponBean couponBean = new CouponBean();

        Coupon coupon = new Coupon();
        coupon.setid(Integer.parseInt(request.getParameter("id")));
        coupon.setcode(request.getParameter("code"));
        coupon.setdescription(request.getParameter("description"));
        if(request.getParameter("itemid") != null && request.getParameter("itemid").length() > 0)
            coupon.setitemid(Integer.parseInt(request.getParameter("itemid")));
        if(request.getParameter("manufacturerid") != null && request.getParameter("manufacturerid").length() > 0)
            coupon.setmanufacturerid(Integer.parseInt(request.getParameter("manufacturerid")));
        if(request.getParameter("quantitylimit") != null && request.getParameter("quantitylimit").length() > 0)
            coupon.setquantityLimit(Integer.parseInt(request.getParameter("quantitylimit")));
        if(request.getParameter("quantityrequired") != null && request.getParameter("quantityrequired").length() > 0)
            coupon.setquantityrequired(Integer.parseInt(request.getParameter("quantityrequired")));
        if(request.getParameter("minimumprice") != null && request.getParameter("minimumprice").length() > 0)
            coupon.setpriceminimum(Double.parseDouble(request.getParameter("minimumprice")));
        coupon.setdiscounttype(Integer.parseInt(request.getParameter("discounttype")));
        if(request.getParameter("discount") != null && request.getParameter("discount").length() > 0)
            coupon.setdiscount(Double.parseDouble(request.getParameter("discount")));
        if(request.getParameter("precludes") != null && request.getParameter("precludes").compareToIgnoreCase("on") == 0)
            coupon.setprecludes(true);
        else
            coupon.setprecludes(false);
        if(request.getParameter("singleuse") != null && request.getParameter("singleuse").compareToIgnoreCase("on") == 0)
            coupon.setsingleuse(true);
        else
            coupon.setsingleuse(false);
        if(request.getParameter("displayonweb") != null && request.getParameter("displayonweb").compareToIgnoreCase("on") == 0)
            coupon.setdisplay(true);
        else
            coupon.setdisplay(false);
        coupon.setexpirationDate(dateFormat.parse(request.getParameter("expirationdate")));

        UpdateCouponRequest updateCouponRequest = new UpdateCouponRequest();
        updateCouponRequest.setcoupon(coupon);
        couponBean.UpdateCoupon(updateCouponRequest);
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
                    <table id="headingTable" border="0">
                        <tr>
                            <td><img src="./images/spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td class="producttitle">Update Coupon Result</td>
                        </tr>
                    </table>
                    <br>
                    <TABLE cellSpacing="1" cellPadding="3" width="95%" border="0">
                    <tr>
                        <td noWrap>The coupon [<%=request.getParameter("code")%>] was updated successfully.</td>
                    </tr>
                    </TABLE>
                    <br>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>

