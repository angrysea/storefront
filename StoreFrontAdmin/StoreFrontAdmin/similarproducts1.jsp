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
    DecimalFormat moneyFormat = new DecimalFormat("$#,###.00");

    GetItemsResponse getItemsResponse = new GetItemsResponse();
    try {
        ItemBean item = new ItemBean();

        GetItemsRequest getItemsRequest = new GetItemsRequest();
        if(request.getParameter("manufacturer") != null)
        {
            if(request.getParameter("manufacturer").compareToIgnoreCase("0") != 0)
            {
                getItemsRequest.setselectby("manufacturer");
                getItemsRequest.setid(new Integer(request.getParameter("manufacturer")).intValue());
            }
            getItemsResponse = item.GetItems(getItemsRequest);
        }
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>


<HTML>
    <SCRIPT LANGUAGE="JavaScript">
        function OnChangeManufacturer()
        {
            document.form1.submit();
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
                    <table id="headingTable" height="100%" border="0">
                        <tr>
                            <td><img src="./images/spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td class="producttitle">Similar Products Wizard</td>
                        </tr>
                        <tr>
                            <td><img src="./images/spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td><h2>Step 1 - Select a Product</h2></td>
                        </tr>
                    </table>
                    <form name="form1" action="./similarproducts1.jsp" method="GET">
                    <table>
                        <tr>
                            <td noWrap>Manufacturer:</td>
                            <td>
                                <select name="manufacturer" onchange="OnChangeManufacturer()">
                                    <option value="-1">< select ></option>
                                    <option value="0">all</option>
                                    <%
                                        ListsBean lists = new ListsBean();
	                                GetManufacturersResponse getManufacturersResponse = lists.GetManufacturers(new GetManufacturersRequest());
                                        Iterator itManufacturer = getManufacturersResponse.getmanufacturersIterator();
                                        Manufacturer manufacturer = null;
                                        while(itManufacturer.hasNext())
                                        {
                                            manufacturer = (Manufacturer)itManufacturer.next();
                                    %>
                                            <option value="<%=manufacturer.getid()%>"><%=manufacturer.getname()%></option>
                                    <%
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                    </table>
                    </form>
                    <TABLE cellSpacing="1" cellPadding="3" width="750" border="0">
                        <TR>
                            <TD class="columnheader" align="left"><b>ID</b></TD>
                            <TD class="columnheader" align="left"><b>Inventory ID</b></TD>
                            <TD class="columnheader" align="left"><b>Name</b></TD>
                            <TD class="columnheader" align="right"><b>List Price</b></TD>
                            <TD class="columnheader" align="right"><b>Our Price</b></TD>
                            <TD class="columnheader" align="left"><b>Mfgr</b></TD>
                            <TD class="columnheader" align="left"><b>Distributor</b></TD>
                            <TD class="columnheader" align="left"><b>Status</b></TD>
                        </TR>
                        <tr>
                            <td colspan="10">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td bgcolor="<%=theme.getcolor1()%>"><img src="./spacer.gif" width="750" height="1" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <%
                            Iterator it = getItemsResponse.getitemsIterator();
                            Item item = null;
                            while(it.hasNext())
                            {
                                item = (Item)it.next();
                        %>
                                <TR>
                                    <TD noWrap align="left"><a href="./similarproducts2.jsp?id=<%=new Integer(item.getid()).toString()%>"><%=new Integer(item.getid()).toString()%></a></TD>
                                    <TD noWrap align="left"><%=item.getisin()==null?"":item.getisin()%></TD>
                                    <TD noWrap align="left"><%=item.getproductname()==null?"":item.getproductname()%></TD>
                                    <TD noWrap align="right"><%=moneyFormat.format(item.getlistprice())%></TD>
                                    <TD noWrap align="right"><%=moneyFormat.format(item.getourprice())%></TD>
                                    <TD noWrap align="left"><%=item.getmanufacturer()==null?"":item.getmanufacturer()%></TD>
                                    <TD noWrap align="left"><%=item.getdistributor()==null?"":item.getdistributor()%></TD>
                                    <TD noWrap align="left"><%=item.getstatus()==null?"":item.getstatus()%></TD>
                                </TR>
                        <%
                            }
                        %>
                    </TABLE>
                    <br>
                </td>
            </tr>
        </table>
    </BODY>
    <SCRIPT LANGUAGE="JavaScript">
        <%
            if(request.getParameter("manufacturer") != null)
            {
        %>
                document.form1.manufacturer.value = '<%=request.getParameter("manufacturer")%>';
        <%
            }
        %>
    </SCRIPT>
</HTML>
