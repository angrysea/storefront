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
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    DateFormat dateFormat2 = new SimpleDateFormat("MM/dd/yyyy");
    DecimalFormat moneyFormat = new DecimalFormat("$#,##0.00");

    String fromDateString = null;
    String toDateString = null;

    GetUsersResponse getUsersResponse = null;
    SourceURLReportResponse sourceURLReportResponse = null;
    UserBean userBean = new UserBean();
    try {
        java.util.Date fromdate = Calendar.getInstance().getTime();
        java.util.Date todate = Calendar.getInstance().getTime();

        if(request.getParameter("fromdate") != null && request.getParameter("todate") != null)
        {
            fromdate = dateFormat2.parse(request.getParameter("fromdate"));
            todate = dateFormat2.parse(request.getParameter("todate"));
        }

        GetUsersRequest getUsersRequest = new GetUsersRequest();
        getUsersRequest.setstartdate(fromdate);
        getUsersRequest.setenddate(todate);
        getUsersResponse = userBean.GetUsers(getUsersRequest);

        SourceURLReportRequest sourceURLReportRequest = new SourceURLReportRequest();
        sourceURLReportRequest.setstartdate(fromdate);
        sourceURLReportRequest.setenddate(todate);
        sourceURLReportResponse = userBean.SourceURLReport(sourceURLReportRequest);

        fromDateString = dateFormat2.format(fromdate);
        toDateString = dateFormat2.format(todate);
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<HTML>
    <%@ include file="datevalidation.jsp" %>
    <SCRIPT LANGUAGE="JavaScript">
        function OnSubmitForm()
        {
            if(ValidateDate(document.form1.fromdate) == false)
            {
                return false;
            }

            if(ValidateDate(document.form1.todate) == false)
            {
                return false;
            }

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
                            <td class="producttitle">Users</td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                    </table>
                    <form name="form1" action="./users.jsp" method="GET" onsubmit="return(OnSubmitForm())">
                    <table border="0">
                        <tr>
                            <td>From:</td>
                            <td><input type="text" name="fromdate" size="12"></td>
                            <td nowrap>&nbsp;&nbsp;</td>
                            <td>To:</td>
                            <td><input type="text" name="todate" size="12"></td>
                            <td nowrap>&nbsp;&nbsp;</td>
                            <td><input type="submit" name="go" value="Go"/></td>
                        </tr>
                    </table>
                    </form>
                    <TABLE cellSpacing="1" cellPadding="3" width="700" border="0">
                        <tr>
                            <td align="left"><b>ID</b></td>
                            <td align="left"><b>Created</b></td>
                            <td align="center"><b>Shopping Cart Items</b></td>
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
                            Iterator itUsers = getUsersResponse.getusersIterator();
                            for( ; itUsers.hasNext() ; )
                            {
                                User user = (User)itUsers.next();

                                GetShoppingCartRequest getShoppingCartRequest = new GetShoppingCartRequest();
                                getShoppingCartRequest.setid(user.getid());
                                GetShoppingCartResponse getShoppingCartResponse = userBean.GetShoppingCart(getShoppingCartRequest);
                                Iterator itShoppingCartItems = getShoppingCartResponse.getshoppingcartitemsIterator();
                                int count = 0;
                                for( ; itShoppingCartItems.hasNext() ; count++)
                                    itShoppingCartItems.next();
                        %>
                                <tr>
                                    <td nowrap><%=new Integer(user.getid()).toString()%></td>
                                    <td nowrap><%=dateFormat.format(user.getcreationdate())%></td>
                                    <td align="center"><a href="./shoppingcart.jsp?userid=<%=user.getid()%>"><%=new Integer(count).toString()%></a></td>
                                </tr>
                        <%
                            }
                        %>
                        <tr>
                            <td><br></td>
                        </tr>
                    </TABLE>
                </td>
            </tr>
        </table>
    </BODY>
    <SCRIPT LANGUAGE="JavaScript">
        document.form1.fromdate.value = '<%=fromDateString%>';
        document.form1.todate.value = '<%=toDateString%>';
    </SCRIPT>
</HTML>
