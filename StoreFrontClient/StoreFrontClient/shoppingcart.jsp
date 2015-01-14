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
    DecimalFormat moneyFormat = new DecimalFormat("$#,##0.00");

    GetShoppingCartResponse getShoppingCartResponse = null;
    GetUserResponse getUserResponse = null;
    boolean cookiesSupported = true;
    try {
        UserBean userBean = new UserBean();
        getUserResponse = userBean.GetUser(request, response, true);

        // Are we adding to the cart?
        String task = request.getParameter("task");
        if(task != null && task.compareToIgnoreCase("add") == 0)
        {
            int id = userBean.getUserID(request);
            if(id>0)
                cookiesSupported = false;

            AddShoppingCartItemRequest addShoppingCartItemRequest = new AddShoppingCartItemRequest();
            addShoppingCartItemRequest.setuserid(getUserResponse.getuser().getid());
            ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
            Item item = new Item();
            item.setid(new Integer(request.getParameter("itemid")).intValue());
            shoppingCartItem.setitem(item);
            shoppingCartItem.setquantity(new Integer(request.getParameter("quantity")).intValue());
            addShoppingCartItemRequest.setshoppingcartitem(shoppingCartItem);
            userBean.AddShoppingCartItem(addShoppingCartItemRequest);
        }
        // Are we updating
        else if(task != null && task.compareToIgnoreCase("update") == 0)
        {
            UpdateShoppingCartItemRequest updateShoppingCartItemRequest = new UpdateShoppingCartItemRequest();

            // Cycle through the current shopping cart items
            GetShoppingCartRequest getShoppingCartRequest = new GetShoppingCartRequest();
            getShoppingCartRequest.setid(getUserResponse.getuser().getid());
            getShoppingCartResponse = userBean.GetShoppingCart(getShoppingCartRequest);
            Iterator itShoppingCartItems = getShoppingCartResponse.getshoppingcartitemsIterator();
            while(itShoppingCartItems.hasNext())
            {
                ShoppingCartItem shoppingCartItem = (ShoppingCartItem)itShoppingCartItems.next();
                String searchfor = "itemid" + new Integer(shoppingCartItem.getitem().getid()).toString();
                if(request.getParameter(searchfor) != null)
                {
                    shoppingCartItem.setquantity(new Integer(request.getParameter(searchfor)).intValue());
                    updateShoppingCartItemRequest.setshoppingcartitem(shoppingCartItem);

                    // Update it
                    userBean.UpdateShoppingCartItem(updateShoppingCartItemRequest);
                }
            }
        }
        // Are we removing
        else if(task != null && task.compareToIgnoreCase("remove") == 0)
        {
            DeleteShoppingCartItemRequest deleteShoppingCartItemRequest = new DeleteShoppingCartItemRequest();
            deleteShoppingCartItemRequest.setid(new Integer(request.getParameter("itemid")).intValue());
            userBean.DeleteShoppingCart(deleteShoppingCartItemRequest);
        }

        GetShoppingCartRequest getShoppingCartRequest = new GetShoppingCartRequest();
        getShoppingCartRequest.setid(getUserResponse.getuser().getid());
        getShoppingCartResponse = userBean.GetShoppingCart(getShoppingCartRequest);
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<HTML>
    <SCRIPT LANGUAGE="JavaScript">
        function OnClickUpdateQuantities()
        {
            window.shoppingcartform.submit();
        }
    </SCRIPT>
    <HEAD>
        <%@ include file="titlemetadata.jsp" %>
        <LINK href="<%=company.getbaseurl()%>storefront.css" rel="STYLESHEET"></LINK>
    </HEAD>
    <BODY leftMargin="0" topMargin="0" onload="" marginwidth="0" marginheight="0">
        <%@ include file="toppane.jsp" %>
        <table cellSpacing="0" cellPadding="0" border="0">
            <tr>
                <%@ include file="leftpane.jsp" %>
                <td vAlign="top" width="20">
                    <IMG src="<%=theme.getimagebaseurl()%>spacer.gif" border="0">
                </td>
                <td vAlign="top">
                    <table id="headingTable" border="0" width="600">
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="5" height="25"></td>
                        </tr>
                        <tr>
                            <td bgcolor="<%=theme.getcolor2()%>" class="producttitle">Shopping Cart</td>
                        </tr>
                        <tr>
                            <td>Here are the contents of your Shopping Cart. To change item quantities in your cart, enter the new amount under the 'Quantity' column and hit the 'update quantities' link at the bottom of the page.</td>
                        </tr>
                    </table>
                    <br>
                    <form name="shoppingcartform" action="<%=company.getbasesecureurl()%>shoppingcart.jsp" method="get">
                    <input name="user" type="hidden" value="<%=request.getParameter("user")==null?"":request.getParameter("user")%>"/>
                    <input name="login" type="hidden" value="<%=request.getParameter("login")==null?"":request.getParameter("login")%>"/>
                    <input name="task" type="hidden" value="update"/>
                    <table cellSpacing="1" cellPadding="2" border="0" width="600">
                    <tr>
                        <td nowrap align="left"><b>Item</b></td>
                        <td nowrap align="right"><b>Unit Price</b></td>
                        <td nowrap align="center"><b>Quantity</b></td>
                        <td nowrap align="right"><b>Your Price</b></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td colspan="5" valign="top">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td bgcolor="<%=theme.getcolor1()%>"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="600" height="1" /></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <%
                        double totalPrice = 0.0;
                        boolean itemsfound = false;

                        Iterator itShoppingCartItems = getShoppingCartResponse.getshoppingcartitemsIterator();
                        while(itShoppingCartItems.hasNext())
                        {
                            itemsfound = true;
                            ShoppingCartItem shoppingCartItem = (ShoppingCartItem)itShoppingCartItems.next();
                            totalPrice += (shoppingCartItem.getitem().getourprice() * shoppingCartItem.getquantity());
                    %>
                            <tr>
                                <td nowrap valign="middle" align="left">
                                    <a href="<%=StoreFrontUrls.getproductdetails(request, company, shoppingCartItem.getitem())%>"><%=shoppingCartItem.getitem().getmanufacturer()%> <%=shoppingCartItem.getitem().getproductname()%></a>
                                    <br>Item number: <%=shoppingCartItem.getitem().getisin()%>, <%=shoppingCartItem.getitem().getstatus()%>
                                </td>
                                <td nowrap valign="middle" align="right"><%=moneyFormat.format(shoppingCartItem.getitem().getourprice())%></td>
                                <td nowrap valign="middle" align="center"><input name="itemid<%=new Integer(shoppingCartItem.getitem().getid()).toString()%>" type="text" size="5" value="<%=new Integer(shoppingCartItem.getquantity()).toString()%>" /></td>
                                <td nowrap valign="middle" align="right"><%=moneyFormat.format(shoppingCartItem.getitem().getourprice() * shoppingCartItem.getquantity())%></td>
                                <td nowrap valign="middle">&nbsp;&nbsp;<a href="<%=StoreFrontUrls.getshoppingcart(request, company, shoppingCartItem, "remove")%>">remove</a></td>
                            </tr>
                            <tr>
                                <td colspan="5" valign="top">
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td bgcolor="<%=theme.getcolor1()%>"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="600" height="1" /></td>
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
                                <td colspan="5" nowrap align="left">There are no items in your shopping cart.</td>
                            </tr>
                            <tr>
                                <td colspan="5" valign="top">
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td bgcolor="<%=theme.getcolor1()%>"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="600" height="1" /></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                    <%
                        }
                    %>
                    <tr>
                        <td></td>
                        <td></td>
                        <td nowrap align="center"><a href="<%=StoreFrontUrls.geturl(request, company, "shoppingcart")%>" onclick="OnClickUpdateQuantities();return false;">update quantities</a></td>
                        <td nowrap align="right"><%=moneyFormat.format(totalPrice)%></td>
                    </tr>
                    <tr>
                        <td nowrap align="left"><a href="<%=StoreFrontUrls.geturl(request, company, "index")%>">continue shopping</a></td>
                    </tr>
                    <tr>
                        <td colspan="5" align="right"><a href="<%=StoreFrontUrls.getsecureurl(request, company, "checkoutmethod")%>"><img alt="checkout" border="0" src="<%=theme.getimagebaseurl()%>checkout_button.gif" /></a></td>
                    </tr>
                    <tr>
                        <td><br></td>
                    </tr>
            <%
                if(company.getfreeshipping() > 0)
                {
            %>
                    <tr>
                        <td>
                            <table cellSpacing="0" cellPadding="0" border="0" bgcolor="<%=theme.getcolor1()%>" width="164">
                                <tr>
                                    <td align="center">
                                        <table width="162" cellSpacing="0" cellPadding="0" border="0" bgcolor="#ffffff">
                                            <tr>
                                                <td colspan="3" align="center" bgcolor="<%=theme.getcolor1()%>"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="160" height="1"></td>
                                            </tr>
                                            <tr>
                                                <td colspan="3"><img align="Free Shipping" src="<%=theme.getimagebaseurl()%>freeshipping.gif"></td>
                                            </tr>
                                            <tr>
                                                <td colspan="3" align="center" bgcolor="<%=theme.getcolor1()%>"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="160" height="1"></td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td><br></td>
                    <tr>
            <%
                }
            %>
                    <tr>
                        <td>
                            <table cellSpacing="0" cellPadding="0" border="0" bgcolor="<%=theme.getcolor1()%>" width="164">
                                <tr>
                                    <td align="center">
                                        <table width="162" cellSpacing="0" cellPadding="0" border="0" bgcolor="#ffffff">
                                            <tr>
                                                <td colspan="3" align="center" bgcolor="<%=theme.getcolor1()%>"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="160" height="1"></td>
                                            </tr>
                                            <tr>
                                                <td colspan="3"><a href="<%=StoreFrontUrls.geturl(request, company, "coupons")%>"><img border="0" align="Coupons" src="<%=theme.getimagebaseurl()%>coupons.gif"></a></td>
                                            </tr>
                                            <tr>
                                                <td colspan="3" align="center" bgcolor="<%=theme.getcolor1()%>"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="160" height="1"></td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td><br></td>
                    <tr>
                    <tr bgcolor="<%=theme.getcolor2()%>" valign="top">
                        <td>About the Shopping Cart</td>
                    </tr>
                    <tr>
                        <td>
                        <ul>
                            <li>Items in your Shopping Cart always reflect the most recent price displayed on their product pages.</li>
                            <li>Items remain in your Shopping Cart for 90 days.</li>
                        </ul>
                        </td>
                    </tr>
                    </table>
                    </form>
                    <%@ include file="bottompane.jsp" %>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>

