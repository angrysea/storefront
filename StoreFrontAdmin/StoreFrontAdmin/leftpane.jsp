<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    Theme lpTheme = null;
    try {
        CompanyBean lpCompanyBean = new CompanyBean();
        GetThemeRequest lpGetThemeRequest = new GetThemeRequest();
        lpGetThemeRequest.setname("corporate");
        GetThemeResponse lpGetThemeResponse = lpCompanyBean.GetTheme(request, lpGetThemeRequest);
        lpTheme = lpGetThemeResponse.gettheme();
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<SCRIPT LANGUAGE="JavaScript">
</SCRIPT>
<td vAlign="top" bgcolor="<%=lpTheme.getcolor2()%>" nowrap rowspan="500">
    &nbsp;&nbsp;
</td>
<td vAlign="top" bgcolor="<%=lpTheme.getcolor2()%>" rowspan="500">
    <TABLE cellSpacing="1" cellPadding="1" border="0" width="160">
        <tr>
            <td><img src="./images/spacer.gif" width="5" height="5"></td>
        </tr>
        <tr>
            <td nowrap><b>Product Management:</b></td>
        </tr>
        <tr>
            <td><a href="./distributors.jsp">Distributors</a></td>
        </tr>
        <tr>
            <td><a href="./manufacturers.jsp">Manufacturers</a></td>
        </tr>
        <tr>
            <td><a href="./products.jsp">Products</a></td>
        </tr>
        <tr>
            <td><a href="./productadd.jsp">Add a Product</a></td>
        </tr>
        <tr>
            <td><a href="./similarproducts1.jsp">Similar Products Wizard</a></td>
        </tr>
        <tr>
            <td><a href="./featuredproducts.jsp">Featured Products</a></td>
        </tr>
        <tr>
            <td><a href="./pricingwizard.jsp">Pricing Wizard</a></td>
        </tr>
        <tr>
            <td><a href="./rebuildkeywords.jsp">Rebuild Product Keywords</a></td>
        </tr>
        <tr>
            <td><br></td>
        </td>
        <tr>
            <td nowrap><b>Customer Related:</b></td>
        </tr>
        <tr>
            <td><a href="./coupons.jsp">Coupons</a></td>
        </tr>
        <tr>
            <td><a href="./users.jsp">Users</a></td>
        </tr>
        <tr>
            <td><a href="./customers.jsp">Customers</a></td>
        </tr>
        <tr>
            <td><a href="./salesorders.jsp">Sales Orders</a></td>
        </tr>
        <tr>
            <td><a href="./invoices.jsp">Invoices</a></td>
        </tr>
        <tr>
            <td><br></td>
        </td>
        <tr>
            <td nowrap><b>Inventory Management:</b></td>
        </tr>
        <tr>
            <td><a href="./purchaseorderadd.jsp">Create Purchase Order</a></td>
        </tr>
        <tr>
            <td><a href="./purchaseorderreceive1.jsp">Receive Purchase Order</a></td>
        </tr>
        <tr>
            <td><a href="./purchaseorders.jsp">View Purchase Orders</a></td>
        </tr>
        <tr>
            <td><br></td>
        </td>
        <tr>
            <td nowrap><b>Logistics:</b></td>
        </tr>
        <tr>
            <td><a href="./packingslipsadd.jsp">Create Packing Slips</a></td>
        </tr>
        <tr>
            <td><a href="./shippackages.jsp">Ship Packages</a></td>
        </tr>
        <tr>
            <td><a href="./packingslips.jsp">View Packing Slips</a></td>
        </tr>
        <tr>
            <td><br></td>
        </td>
        <tr>
            <td nowrap><b>E-Mail Related:</b></td>
        </tr>
        <tr>
            <td><a href="./emaillist.jsp">E-Mail List</a></td>
        </tr>
        <tr>
            <td><a href="./emaillistadd.jsp">Add an E-Mail Address</a></td>
        </tr>
        <tr>
            <td><a href="./emailcoupons1.jsp">E-Mail a Coupon</a></td>
        </tr>
        <tr>
            <td><br></td>
        </td>
        <tr>
            <td nowrap><b>Link Related:</b></td>
        </tr>
        <tr>
            <td><a href="./links.jsp">Links</a></td>
        </tr>
        <tr>
            <td><a href="./linkadd.jsp">Add a Link</a></td>
        </tr>
        <tr>
            <td><br></td>
        </td>
        <tr>
            <td nowrap><b>Reports:</b></td>
        </tr>
        <tr>
            <td><a href="./report_webstats.jsp">Web Statistics</a></td>
        </tr>
        <tr>
            <td><a href="./report_mostviewedproducts.jsp">Most Viewed Products</a></td>
        </tr>
        <tr>
            <td><a href="./report_mostpurchasedproducts.jsp">Most Purchased Products</a></td>
        </tr>
        <tr>
            <td><a href="./report_mostpopularsearches.jsp">Most Popular Searches</a></td>
        </tr>
        <tr>
            <td><br></td>
        </td>
        <tr>
            <td nowrap><b>Utilities:</b></td>
        </tr>
        <tr>
            <td><a href="./minisitegenerate.jsp">Generate a Minisite</a></td>
        </tr>
        <tr>
            <td><a href="./uploadtofroogle.jsp">Upload to Froogle</a></td>
        </tr>
    </TABLE>
    <table border="0">
        <tr>
            <td><img src="./images/spacer.gif" width="1" height="700"/></td>
        </tr>
    </table>
</td>
