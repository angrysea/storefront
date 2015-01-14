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

    GetItemRankingResponse getItemRankingResponse = null;
    try {
        ItemRankingBean itemRankingBean = new ItemRankingBean();
        GetItemRankingRequest getItemRankingRequest = new GetItemRankingRequest();
        if(request.getParameter("numbertodisplay") != null)
            getItemRankingRequest.setcount(new Integer(request.getParameter("numbertodisplay")).intValue());
        else
            getItemRankingRequest.setcount(25);
        getItemRankingResponse = itemRankingBean.GetItemRankingViewed(getItemRankingRequest);
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>


<HTML>
    <%@ include file="commonscript.jsp" %>
    <SCRIPT LANGUAGE="JavaScript">
        function OnSubmitForm()
        {
            var valid = '0123456789';
            if(document.form1.numbertodisplay.value.length == 0 || IsValid(document.form1.numbertodisplay.value, valid) == false)
            {
                alert("Please enter numbers only.");
                document.form1.numbertodisplay.focus();
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
                    <table id="headingTable" height="100%" border="0">
                        <tr>
                            <td><img src="./images/spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td class="producttitle">Most Viewed Products</td>
                        </tr>
                    </table>
                    <form name="form1" action="./report_mostpopularproducts.jsp" method="GET" onsubmit="return(OnSubmitForm())">
                    <table>
                        <tr>
                            <td noWrap>Number To Display:</td>
                            <td noWrap><input type="text" name="numbertodisplay" size="3" value="<%=request.getParameter("numbertodisplay")==null?"25":request.getParameter("numbertodisplay")%>"/></td>
                            <td noWrap>&nbsp;&nbsp<input type="submit" value="Go"/></td>
                        </tr>
                    </table>
                    </form>
                    <TABLE cellSpacing="1" cellPadding="3" width="750" border="0">
                        <TR>
                            <TD class="columnheader" align="left"><b>Image</b></TD>
                            <TD class="columnheader" align="left"><b>ID</b></TD>
                            <TD class="columnheader" align="left"><b>Inventory ID</b></TD>
                            <TD class="columnheader" align="left"><b>Name</b></TD>
                            <TD class="columnheader" align="right"><b>Quantity in Stock</b></TD>
                            <TD class="columnheader" align="right"><b>Our Price</b></TD>
                            <TD class="columnheader" align="right"><b>Number of Views</b></TD>
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
                            Iterator itItems = getItemRankingResponse.getitemsIterator();
                            while(itItems.hasNext())
                            {
                                Item item = (Item)itItems.next();
                        %>
                                <TR>
                                    <TD align="left"><a href="<%=theme.getimagebaseurl()%><%=item.getdetails().getimageUrlLarge()%>" target="_blank"><img border="0" src="<%=theme.getimagebaseurl()%><%=item.getdetails().getimageUrlSmall()%>" /></a></TD>
                                    <TD align="left" valign="top"><a href="./productupdate.jsp?id=<%=new Integer(item.getid()).toString()%>&manufacturer=<%=new Integer(item.getmanufacturerid()).toString()%>"><%=new Integer(item.getid()).toString()%></a></TD>
                                    <TD align="left" valign="top"><%=item.getisin()%></TD>
                                    <TD align="left" valign="top"><%=item.getmanufacturer()%> <%=item.getproductname()%></TD>
                                    <TD align="right" valign="top"><%=new Integer(item.getquantity()).toString()%></TD>
                                    <TD align="right" valign="top"><%=moneyFormat.format(item.getourprice())%></TD>
                                    <TD align="right" valign="top"><%=new Integer(item.getrank()).toString()%></TD>
                                </TR>
                        <%
                            }
                        %>
                    </TABLE>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>
