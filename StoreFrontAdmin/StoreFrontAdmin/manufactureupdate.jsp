<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    try {
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
        <LINK href="./knifestore.css" rel="STYLESHEET"></LINK>
    </HEAD>
    <BODY leftMargin="0" topMargin="0" onload="" marginwidth="0" marginheight="0">
        <%@ include file="toppane.jsp" %>
        <table cellSpacing="0" cellPadding="0" border="0">
            <tr>
                <%@ include file="leftpane.jsp" %>
                <td vAlign="top" width="20">
                    <IMG src="./images/white_filler.gif" border="0">
                </td>
                <td vAlign="top">
                    <table id="headingTable" border="0">
                        <tr>
                            <td><img src="./images/white_filler.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td class="producttitle">Update/Delete Manufacturer</td>
                        </tr>
                    </table>
                    <br>
                    <TABLE cellSpacing="1" cellPadding="3" width="95%" border="0">
                    <tr>
                        <td noWrap>Manufacturer ID:</td>
                        <td><input id="manufacturerid" type="text" size="10"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Name:</td>
                        <td><input id="name" type="text" size="25"/></td>
                    </tr>
                    <tr>
                        <td noWrap valign="top">Description:</td>
                        <td><textarea name="description" rows="10" cols="100"></textarea></td>
                    </tr>
                    </TABLE>
                    <br>
                    <TABLE border="0">
                    <tr>
                        <td><input type="submit" value="Update"></td>
                        <td><input type="button" value="Delete"></td>
                        <td><input type="button" value="Cancel"></td>
                    </tr>
                    </TABLE>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>
