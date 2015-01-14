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
    DecimalFormat percentFormat = new DecimalFormat("##0.0");

    GetManufacturersResponse getManufacturersResponse = new GetManufacturersResponse();
    try {
        ListsBean lists = new ListsBean();

	GetManufacturersRequest getManufacturersRequest = new GetManufacturersRequest();
        getManufacturersResponse = lists.GetManufacturers(getManufacturersRequest);
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
                    <table id="headingTable" height="100%" border="0">
                        <tr>
                            <td><img src="./images/spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td class="producttitle">Manufacturers</td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                    </table>
                    <TABLE cellSpacing="1" cellPadding="3" width="400" border="0">
                        <TR>
                            <TD class="columnheader" noWrap align="left"><b>ID</b></TD>
                            <TD class="columnheader" noWrap align="left"><b>Name</b></TD>
                            <TD class="columnheader" noWrap align="right"><b>Markup</b></TD>
                            <TD class="columnheader" noWrap align="center"><b>Active</b></TD>
                        </TR>
                        <tr>
                            <td colspan="10">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td bgcolor="<%=theme.getcolor1()%>"><img src="./spacer.gif" width="400" height="1" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <%
                            Iterator it = getManufacturersResponse.getmanufacturersIterator();
                            Manufacturer manufacturer = null;
                            while(it.hasNext())
                            {
                                manufacturer = (Manufacturer)it.next();
                        %>
                                <TR>
                                    <TD noWrap align="left"><a href="./manufacturerupdate.jsp?id=<%=manufacturer.getid()%>"><%=manufacturer.getid()%></a></TD>
                                    <TD noWrap align="left"><%=manufacturer.getname()==null?"":manufacturer.getname()%></TD>
                                    <TD noWrap align="right"><%=percentFormat.format(manufacturer.getmarkup()*100.0)%>%</TD>
                                    <TD noWrap align="center"><%=manufacturer.getactive()==true?"yes":"no"%></TD>
                                </TR>
                        <%
                            }
                        %>
                    </TABLE>
                    <br>
                    <table border="0">
                        <tr>
                            <td class="smallprompt"><A href="./manufactureradd.jsp">Add a new Manufacturer</A></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>
