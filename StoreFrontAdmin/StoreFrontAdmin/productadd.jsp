<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

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
                    <table id="headingTable" border="0">
                        <tr>
                            <td><img src="./images/spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td class="producttitle">Add Product</td>
                        </tr>
                    </table>
                    <br>
                    <form name="form1" action="./productadd_result.jsp" method="post">
                    <%@ include file="productmaintenance_table.jsp" %>
                    <br>
                    <TABLE border="0">
                    <tr>
                        <td><input type="button" value="Add" onclick="OnClickValidate()"></td>
                        <td><input type="button" value="Cancel" onclick="OnClickCancel()"></td>
                    </tr>
                    </TABLE>
                    </form>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>
