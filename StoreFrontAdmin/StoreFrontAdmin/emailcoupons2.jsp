<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    Theme theme = null;
    Company company = null;
    try {
        CompanyBean companyBean = new CompanyBean();
        GetThemeRequest getThemeRequest = new GetThemeRequest();
        getThemeRequest.setname("corporate");
        GetThemeResponse getThemeResponse = companyBean.GetTheme(request, getThemeRequest);
        theme = getThemeResponse.gettheme();

        GetCompanyResponse getCompanyResponse = companyBean.GetCompany(request, new GetCompanyRequest());
        company = getCompanyResponse.getcompany();
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<%
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    GetCouponResponse getCouponResponse = new GetCouponResponse();
    Coupon coupon = null;
    String discountType = "";
    try {
        CouponBean couponBean = new CouponBean();

        GetCouponRequest getCouponRequest = new GetCouponRequest();
        getCouponRequest.setcode(request.getParameter("code"));
        getCouponResponse = couponBean.GetCoupon(getCouponRequest);
        coupon = getCouponResponse.getcoupon();

        if(coupon.getdiscounttype() == 1)
            discountType = "Dollar Amount";
        else if(coupon.getdiscounttype() == 2)
            discountType = "Percentage";
        else
            discountType = "Free Shipping";
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>


<HTML>
    <SCRIPT LANGUAGE="JavaScript">
    function OnClickSendTest()
    {
        window.location = "./emailcoupons_test.jsp?id=<%=Integer.toString(coupon.getid())%>&code=<%=request.getParameter("code")%>";
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
                    <table id="headingTable" border="0">
                        <tr>
                            <td><img src="./images/spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td class="producttitle">Send Coupon via E-Mail</td>
                        </tr>
                    </table>
                    <br>
                    <form name="form1" action="./emailcoupons3.jsp" method="post">
                    <input type="hidden" name="id" value="<%=Integer.toString(coupon.getid())%>"/>
                    <input type="hidden" name="code" value="<%=coupon.getcode()%>"/>
                    <TABLE cellSpacing="1" cellPadding="3" border="0">
                    <tr>
                        <td noWrap>ID:</td>
                        <td><%=Integer.toString(coupon.getid())%></td>
                    </tr>
                    <tr>
                        <td noWrap>Code:</td>
                        <td><%=coupon.getcode()%></td>
                    </tr>
                    <tr>
                        <td noWrap>Description:</td>
                        <td><%=coupon.getdescription()%></td>
                    </tr>
                    <tr>
                        <td nowrap>Item ID:</td>
                        <td><%=coupon.getitemid()==0?"":Integer.toString(coupon.getitemid())%></td>
                    </tr>
                    <tr>
                        <td noWrap>Quantity Limit:</td>
                        <td><%=coupon.getquantityLimit()==0?"":Integer.toString(coupon.getquantityLimit())%></td>
                    </tr>
                    <tr>
                        <td>Discount Type:</td>
                        <td><%=discountType%></td>
                    </tr>
                    <tr>
                        <td noWrap>Discount (i.e. 0.1 = 10%):</td>
                        <td><%=Double.toString(coupon.getdiscount())%></td>
                    </tr>
                    <tr>
                        <td>Precludes All Other Coupons:</td>
                        <td><%=coupon.getprecludes()==true?"yes":"no"%></td>
                    </tr>
                    <tr>
                        <td>Single Use:</td>
                        <td><%=coupon.getsingleuse()==true?"yes":"no"%></td>
                    </tr>
                    <tr>
                        <td>Expiration Date (mm/dd/yyyy):</td>
                        <td><%=dateFormat.format(coupon.getexpirationDate())%></td>
                    </tr>
                    <tr>
                        <td>Redemptions:</td>
                        <td><%=Integer.toString(coupon.getredemptions())%></td>
                    </tr>
                    </TABLE>
                    <br>
                    <TABLE cellSpacing="1" cellPadding="3" border="0">
                    <tr>
                        <td><input type="button" value="Send Coupon to Test E-Mail Address" onclick="OnClickSendTest()"></td>
                        <td>(Test E-Mail Address: <%=company.getemail1()%>)</td>
                    </tr>
                    </TABLE>
                    <br>
                    <TABLE border="0">
                    <tr>
                        <td><font color="red">WARNING: Use this feature with caution.  The coupon selected will be sent to everyone in the e-mail list.</font></td>
                    </tr>
                    <tr>
                        <td><input type="submit" value="Send Coupon to E-Entire Mail List"></td>
                    </tr>
                    </TABLE>
                    </form>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>
