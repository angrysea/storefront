<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    Company company = null;
    Theme theme = null;
    try {
        CompanyBean companyBean = new CompanyBean();
        GetCompanyResponse getCompanyResponse = companyBean.GetCompany(request, new GetCompanyRequest());
        company = getCompanyResponse.getcompany();

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
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    GetCouponsResponse getCouponsResponse = new GetCouponsResponse();
    try {
        CouponBean couponBean = new CouponBean();

	GetCouponsRequest getCouponsRequest = new GetCouponsRequest();
        getCouponsRequest.setdisplayable(true);
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
        <%@ include file="titlemetadata.jsp" %>
        <LINK href="<%=company.getbaseurl()%>storefront.css" rel="STYLESHEET"></LINK>
    </HEAD>
    <BODY leftMargin="0" topMargin="0" onload="" marginwidth="0" marginheight="0">
        <%@ include file="toppane.jsp" %>
        <table summary="<%=request.getAttribute("keyword1")%>" cellSpacing="0" cellPadding="0" border="0">
            <tr>
                <%@ include file="leftpane.jsp" %>
                <td vAlign="top" width="20">
                    <IMG src="<%=theme.getimagebaseurl()%>spacer.gif" border="0">
                </td>
                <td vAlign="top">
                    <table summary="<%=request.getAttribute("keyword2")%>" id="headingTable" border="0">
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="5" height="5"></td>
                        </tr>
                    </table>
                    <br>
                    <table summary="<%=request.getAttribute("keyword1")%>" cellSpacing="0" cellPadding="0" width="550" border="0">
                        <tr bgcolor="<%=theme.getcolor2()%>">
                            <td class="producttitle" noWrap>Coupons</td>
                        </tr>
                <%
                    int count = 0;
                    Iterator it = getCouponsResponse.getcouponsIterator();
                    for( ; it.hasNext() ; count++)
                    {
                        String image = null;
                        Coupon coupon = (Coupon)it.next();
                        Item item = null;
                        Manufacturer manufacturer = null;
                        if(coupon.getitemid() > 0)
                        {
                            ItemBean itemBean = new ItemBean();
                            GetItemRequest getItemRequest = new GetItemRequest();
                            getItemRequest.setid(coupon.getitemid());
                            GetItemResponse getItemResponse = itemBean.GetItem(getItemRequest);
                            item = getItemResponse.getitem();
                            image = getItemResponse.getitem().getdetails().getimageUrlSmall();
                        }
                        else if(coupon.getmanufacturerid() > 0)
                        {
                            ListsBean listsBean = new ListsBean();
                            GetManufacturerRequest getManufacturerRequest = new GetManufacturerRequest();
                            getManufacturerRequest.setid(coupon.getmanufacturerid());
                            GetManufacturerResponse getManufacturerResponse = listsBean.GetManufacturer(getManufacturerRequest);
                            manufacturer = getManufacturerResponse.getmanufacturer();
                            image = getManufacturerResponse.getmanufacturer().getlogo();
                        }
                %>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td>
                                <table cellSpacing="0" cellPadding="0" border="0" bgcolor="<%=theme.getcolor1()%>" width="302">
                                    <tr>
                                        <td align="center">
                                            <table width="162" cellSpacing="0" cellPadding="0" border="0" bgcolor="#ffffff">
                                                <tr>
                                                    <td colspan="2" align="center" bgcolor="<%=theme.getcolor1()%>"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="300" height="1"></td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2">
                                                <%
                                                    if(coupon.getitemid() > 0)
                                                    {
                                                %>
                                                        <a href="<%=StoreFrontUrls.getproductdetails(request, company, item)%>">
                                                <%
                                                    }
                                                    else
                                                    {
                                                %>
                                                        <a href="<%=StoreFrontUrls.getsearchresults(request, company, manufacturer.getlongname(), "0", "0", "0", Integer.toString(manufacturer.getid()), "0", "0", "1")%>">
                                                <%
                                                    }
                                                %>
                                                            <img border="0" align="" src="<%=theme.getimagebaseurl()%><%=image%>">
                                                        </a>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2">
                                                        <table border="0">
                                                            <tr>
                                                                <td noWrap><b><%=coupon.getdescription()%></b></td>
                                                            </tr>
                                                            <tr>
                                                                <td noWrap>Coupon Code: <%=coupon.getcode()%></td>
                                                            </tr>
                                                            <tr>
                                                                <td noWrap>Expiration Date: <%=dateFormat.format(coupon.getexpirationDate())%></td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2" align="center" bgcolor="<%=theme.getcolor1()%>"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="160" height="1"></td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                <%
                    }
                %>
                <%
                    if(count == 0)
                    {
                %>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td>I'm sorry, but no coupons are available at this time.</td>
                        </tr>
                <%
                    }
                %>
                        <tr>
                            <td><br></td>
                        </tr>
                        <%@ include file="bottompane.jsp" %>
                    </table>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>

