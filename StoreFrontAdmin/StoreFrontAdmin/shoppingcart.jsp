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

    User user = null;
    GetShoppingCartResponse getShoppingCartResponse = null;
    try {
        UserBean userBean = new UserBean();
        user = userBean.GetUser(new Integer(request.getParameter("userid")).intValue());

        GetShoppingCartRequest getShoppingCartRequest = new GetShoppingCartRequest();
        getShoppingCartRequest.setid(user.getid());
        getShoppingCartResponse = userBean.GetShoppingCart(getShoppingCartRequest);
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<HTML>
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
                    <table height="100%" border="0">
                        <tr>
                            <td><img src="./images/spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td class="producttitle">Shopping Cart</td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                    </table>

                    <table border="0" width="250">
                        <tr>
                            <td nowrap align="left"><b>User ID:</b></td>
                            <td align="left"><%=new Integer(user.getid()).toString()%></td>
                        </tr>
                    </table>
                    <br>
                    <table cellSpacing="1" cellPadding="2" border="0" width="600">
                        <tr>
                            <td nowrap align="left"><b>Item</b></td>
                            <td nowrap align="right"><b>Unit Price</b></td>
                            <td nowrap align="center"><b>Quantity</b></td>
                            <td nowrap align="right"><b>Price</b></td>
                            <td nowrap align="left"><b>Date Added</b></td>
                        </tr>
                        <tr>
                            <td colspan="5" valign="top">
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td bgcolor="<%=theme.getcolor1()%>"><img src="./images/spacer.gif" width="600" height="1" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    <%
                        double totalPrice = 0.0;
                        Iterator itShoppingCartItems = getShoppingCartResponse.getshoppingcartitemsIterator();
                        boolean itemsfound = false;
                        while(itShoppingCartItems.hasNext())
                        {
                            itemsfound = true;
                            ShoppingCartItem shoppingCartItem = (ShoppingCartItem)itShoppingCartItems.next();
                            totalPrice += (shoppingCartItem.getitem().getourprice() * shoppingCartItem.getquantity());
                    %>
                            <tr>
                                <td nowrap valign="middle" align="left">
                                    <a href="./productupdate.jsp?id=<%=shoppingCartItem.getitem().getid()%>&manufacturer=<%=new Integer(shoppingCartItem.getitem().getmanufacturerid()).toString()%>"><%=shoppingCartItem.getitem().getmanufacturer()%> <%=shoppingCartItem.getitem().getproductname()%></a>
                                    <br>Item number: <%=shoppingCartItem.getitem().getisin()%>, <%=shoppingCartItem.getitem().getstatus()%>
                                </td>
                                <td nowrap valign="middle" align="right"><%=moneyFormat.format(shoppingCartItem.getitem().getourprice())%></td>
                                <td nowrap valign="middle" align="center"><%=new Integer(shoppingCartItem.getquantity()).toString()%></td>
                                <td nowrap valign="middle" align="right"><%=moneyFormat.format(shoppingCartItem.getitem().getourprice() * shoppingCartItem.getquantity())%></td>
                                <td nowrap valign="middle" align="left"><%=dateFormat.format(shoppingCartItem.getaddedDate())%></td>
                            </tr>
                            <tr>
                                <td colspan="5" valign="top">
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td bgcolor="<%=theme.getcolor1()%>"><img src="./images/spacer.gif" width="600" height="1" /></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                    <%
                        }

                        if(! itemsfound)
                        {
                    %>
                            <tr>
                                <td colspan="5" nowrap align="left">There are no items in the shopping cart.</td>
                            </tr>
                            <tr>
                                <td colspan="5" valign="top">
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td bgcolor="<%=theme.getcolor1()%>"><img src="./images/spacer.gif" width="600" height="1" /></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                    <%
                        }
                    %>
                    </table>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>
