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
        function OnClickAdd()
        {
            if(document.form1.name.value.length == 0)
            {
                alert("Invalid Name!");
                document.form1.name.focus();
                return;
            }

            if(document.form1.description.value.length == 0)
            {
                alert("Invalid Description!");
                document.form1.description.focus();
                return;
            }

            if(document.form1.email.value.length == 0)
            {
                alert("Invalid E-Mail!");
                document.form1.email.focus();
                return;
            }

            if(document.form1.zipcode.value.length == 0)
            {
                alert("Invalid Zip Code!");
                document.form1.zipcode.focus();
                return;
            }

            if(document.form1.country.value.length == 0)
            {
                alert("Invalid Country Code!");
                document.form1.country.focus();
                return;
            }

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
                    <table id="headingTable" border="0">
                        <tr>
                            <td><img src="./images/spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td class="producttitle">Add Distributor</td>
                        </tr>
                    </table>
                    <br>
                    <form name="form1" action="./distributoradd_result.jsp" method="post">
                    <TABLE cellSpacing="1" cellPadding="3" width="95%" border="0">
                    <tr>
                        <td noWrap>Distributor ID:</td>
                        <td><input name="distributorid" type="text" size="10" readonly/></td>
                    </tr>
                    <tr>
                        <td noWrap>Name:</td>
                        <td><input name="name" type="text" size="25"/></td>
                    </tr>
                    <tr>
                        <td noWrap valign="top">Description:</td>
                        <td><textarea name="description" rows="10" cols="70"></textarea></td>
                    </tr>
                    <tr>
                        <td noWrap>Drop Ship Fee:</td>
                        <td><input name="dropshipfee" type="text" size="10"/></td>
                    </tr>
                    <tr>
                        <td noWrap>E-Mail:</td>
                        <td><input name="email" type="text" size="30"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Address 1:</td>
                        <td><input name="address1" type="text" size="50"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Address 2:</td>
                        <td><input name="address2" type="text" size="50"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Address 3:</td>
                        <td><input name="address3" type="text" size="50"/></td>
                    </tr>
                    <tr>
                        <td noWrap>City:</td>
                        <td><input name="city" type="text" size="30"/></td>
                    </tr>
                    <tr>
                        <td noWrap>State:</td>
                        <td><input name="state" type="text" size="5"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Zip Code:</td>
                        <td><input name="zipcode" type="text" size="10"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Country Code:</td>
                        <td><input name="country" type="text" size="5"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Telephone:</td>
                        <td><input name="telephone" type="text" size="15"/></td>
                    </tr>
                    </TABLE>
                    <br>
                    <TABLE border="0">
                    <tr>
                        <td><input type="button" value="Add" onclick="OnClickAdd()"></td>
                    </tr>
                    </TABLE>
                    </form>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>
