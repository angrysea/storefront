<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    GetCouponResponse getCouponResponse = new GetCouponResponse();
    Coupon coupon = null;
    try {
        CouponBean couponBean = new CouponBean();

        GetCouponRequest getCouponRequest = new GetCouponRequest();
        getCouponRequest.setcode(request.getParameter("code"));
        getCouponResponse = couponBean.GetCoupon(getCouponRequest);
        coupon = getCouponResponse.getcoupon();
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>


<HTML>
    <SCRIPT LANGUAGE="JavaScript">
        function OnClickUpdate()
        {
            if(document.form1.code.value.length == 0)
            {
                alert("Invalid Code!");
                document.form1.code.focus();
                return;
            }

            if(document.form1.description.value.length == 0)
            {
                alert("Invalid Description!");
                document.form1.description.focus();
                return;
            }

            if(document.form1.discounttype.value == 0)
            {
                alert("Invalid Discount Type!");
                document.form1.discounttype.focus();
                return;
            }


            if(document.form1.discounttype.value != 3)
            {
                if(document.form1.discount.value.length == 0)
                {
                    alert("Invalid Discount!");
                    document.form1.discount.focus();
                    return;
                }
            }

            if(document.form1.expirationdate.value.length == 0)
            {
                alert("Invalid Expiration Date!");
                document.form1.expirationdate.focus();
                return;
            }

            document.form1.submit();
        }

        function OnClickDelete()
        {
            window.location="./coupondelete_result.jsp?code=<%=request.getParameter("code")%>";
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
                            <td class="producttitle">Update Coupon</td>
                        </tr>
                    </table>
                    <br>
                    <form name="form1" action="./couponupdate_result.jsp" method="post">
                    <TABLE cellSpacing="1" cellPadding="3" width="95%" border="0">
                    <tr>
                        <td noWrap>ID:</td>
                        <td><input name="id" type="text" size="5" readonly value="<%=Integer.toString(coupon.getid())%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Code:</td>
                        <td><input name="code" type="text" size="20" value="<%=coupon.getcode()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Description:</td>
                        <td><input name="description" type="text" size="40" value="<%=coupon.getdescription()%>"/></td>
                    </tr>
                    <tr>
                        <td nowrap>Item ID:</td>
                        <td><input name="itemid" type="text" size="5" value="<%=coupon.getitemid()==0?"":Integer.toString(coupon.getitemid())%>"/></td>
                    </tr>
                    <tr>
                        <td nowrap>Manufacturer:</td>
                        <td>
                            <select name="manufacturerid">
                                <option value="0">< select ></option>
                        <%
                            ListsBean lists = new ListsBean();
                            GetManufacturersRequest getManufacturersRequest = new GetManufacturersRequest();
                            GetManufacturersResponse getManufacturersResponse = lists.GetManufacturers(getManufacturersRequest);
                            Iterator it = getManufacturersResponse.getmanufacturersIterator();
                            Manufacturer manufacturer = null;
                            while(it.hasNext())
                            {
                                manufacturer = (Manufacturer)it.next();
                        %>
                                <option value="<%=Integer.toString(manufacturer.getid())%>" <%=manufacturer.getid()==coupon.getmanufacturerid()?"selected":""%>><%=manufacturer.getname()%></option>
                        <%
                            }
                        %>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td noWrap>Quantity Limit:</td>
                        <td><input name="quantitylimit" type="text" size="4" value="<%=coupon.getquantityLimit()==0?"":Integer.toString(coupon.getquantityLimit())%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Quantity Required:</td>
                        <td><input name="quantityrequired" type="text" size="4" value="<%=coupon.getquantityrequired()==0?"":Integer.toString(coupon.getquantityrequired())%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Minimum Price Trigger:</td>
                        <td><input name="minimumprice" type="text" size="5" value="<%=coupon.getpriceminimum()==0.0?"":Double.toString(coupon.getpriceminimum())%>"/></td>
                    </tr>
                    <tr>
                        <td>Discount Type:</td>
                        <td>
                            <select name="discounttype">
                                <option value="0">< select ></option>
                                <option value="1">Dollar Amount</option>
                                <option value="2">Percentage</option>
                                <option value="3">Free Shipping</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td noWrap>Discount (i.e. 0.1 = 10%):</td>
                        <td><input name="discount" type="text" size="5" value="<%=Double.toString(coupon.getdiscount())%>"/></td>
                    </tr>
                    <tr>
                        <td>Precludes All Other Coupons:</td>
                        <td><input type="checkbox" name="precludes" <%=coupon.getprecludes()==true?"checked":""%>></td>
                    </tr>
                    <tr>
                        <td>Single Use:</td>
                        <td><input type="checkbox" name="singleuse" <%=coupon.getsingleuse()==true?"checked":""%>></td>
                    </tr>
                    <tr>
                        <td>Display On Web:</td>
                        <td><input type="checkbox" name="displayonweb" <%=coupon.getdisplay()==true?"checked":""%>></td>
                    </tr>
                    <tr>
                        <td>Expiration Date (mm/dd/yyyy):</td>
                        <td><input name="expirationdate" type="text" size="15" value="<%=dateFormat.format(coupon.getexpirationDate())%>"/></td>
                    </tr>
                    <tr>
                        <td>Redemptions:</td>
                        <td><input name="redemptions" type="text" size="5" value="<%=Integer.toString(coupon.getredemptions())%>" readonly="readonly"/></td>
                    </tr>
                    </TABLE>
                    <br>
                    <TABLE border="0">
                    <tr>
                        <td><input type="button" value="Update" onclick="OnClickUpdate()"></td>
                        <td><input type="button" value="Delete" onclick="OnClickDelete()"></td>
                    </tr>
                    </TABLE>
                    </form>
                </td>
            </tr>
        </table>
    </BODY>
    <SCRIPT LANGUAGE="JavaScript">
        document.form1.discounttype.value = '<%=Integer.toString(coupon.getdiscounttype())%>';
    </SCRIPT>
</HTML>
