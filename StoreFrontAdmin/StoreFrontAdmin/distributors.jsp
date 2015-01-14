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
    GetDistributorsResponse getDistributorsResponse = new GetDistributorsResponse();
    try {
        ListsBean lists = new ListsBean();

	GetDistributorsRequest getDistributorsRequest = new GetDistributorsRequest();
        getDistributorsResponse = lists.GetDistributors(getDistributorsRequest);
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
                            <td class="producttitle">Distributors</td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                    </table>
                    <TABLE cellSpacing="1" cellPadding="3" width="400" border="0">
                        <TR>
                            <TD class="columnheader" noWrap align="left"><b>ID</b></TD>
                            <TD class="columnheader" noWrap align="left"><b>Name</b></TD>
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
                            Iterator it = getDistributorsResponse.getdistributorsIterator();
                            Distributor distributor = null;
                            while(it.hasNext())
                            {
                                distributor = (Distributor)it.next();
                        %>
                                <TR>
                                    <TD noWrap align="left"><a href="./distributorupdate.jsp?id=<%=distributor.getid()%>"><%=distributor.getid()%></a></TD>
                                    <TD noWrap align="left"><%=distributor.getname()==null?"":distributor.getname()%></TD>
                                </TR>
                        <%
                            }
                        %>
                    </TABLE>
                    <br>
                    <table border="0">
                        <tr>
                            <td class="smallprompt"><A href="./distributoradd.jsp">Add a new Distributor</A></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>
