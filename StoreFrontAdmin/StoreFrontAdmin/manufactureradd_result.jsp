<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    try {
        ListsBean lists = new ListsBean();

        Manufacturer manufacturer = new Manufacturer();
        if(request.getParameter("active") != null && request.getParameter("active").compareToIgnoreCase("on") == 0)
            manufacturer.setactive(true);
        else
            manufacturer.setactive(false);
        manufacturer.setname(request.getParameter("name"));
        manufacturer.setlongname(request.getParameter("longname"));
        manufacturer.setshortdescription(request.getParameter("description"));
        manufacturer.setdescription(request.getParameter("longdescription"));
        manufacturer.setprefix(request.getParameter("prefix"));
        manufacturer.setmarkup(new Double(request.getParameter("markup")).doubleValue() / 100.0);
        AddManufacturerRequest addManufacturerRequest = new AddManufacturerRequest();
        addManufacturerRequest.setmanufacturer(manufacturer);

        lists.AddManufacturer(addManufacturerRequest);
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
                    <table id="headingTable" border="0">
                        <tr>
                            <td><img src="./images/spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td class="producttitle">Add Manufacturer Result</td>
                        </tr>
                    </table>
                    <br>
                    <TABLE cellSpacing="1" cellPadding="3" width="95%" border="0">
                    <tr>
                        <td noWrap>The manufacturer [<%=request.getParameter("name")%>] was added successfully.</td>
                    </tr>
                    </TABLE>
                    <br>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>

