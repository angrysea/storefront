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
                            <td class="producttitle">Rebuild Product Keywords</td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                        <tr>
                            <td>This feature will iterate through the products from the manufacturer(s) selected and regenerate the keywords for each product.  The keywords are generated from the product name, specifications, details, etc.</td>
                        </tr>
                        <tr>
                            <td><font color="red">WARNING: Use this feature with caution.  Keywords are affected immediately and will affect customer search results.</font></td>
                        </tr>
                    </table>
                    <form name="form1" action="./rebuildkeywords_result.jsp" method="GET">
                    <table>
                        <tr>
                            <td noWrap>Select a Manufacturer:</td>
                            <td>
                                <select name="manufacturer">
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
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td colspan="10"><input type="submit" value="Rebuild the Keywords"/></td>
                        </tr>
                    </table>
                    </form>
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
