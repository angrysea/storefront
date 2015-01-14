<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    GetManufacturerResponse getManufacturerResponse = new GetManufacturerResponse();
    try {
        ListsBean lists = new ListsBean();

        GetManufacturerRequest getManufacturerRequest = new GetManufacturerRequest();
        getManufacturerRequest.setid(new Integer(request.getParameter("id")).intValue());
        getManufacturerResponse = lists.GetManufacturer(getManufacturerRequest);
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>


<HTML>
    <SCRIPT LANGUAGE="JavaScript">
        function OnClickUpdate()
        {
            if(document.form1.name.value.length == 0)
            {
                alert("Invalid Name!");
                document.form1.name.focus();
                return;
            }

            if(document.form1.longname.value.length == 0)
            {
                alert("Invalid Long Name!");
                document.form1.longname.focus();
                return;
            }

            if(document.form1.description.value.length == 0)
            {
                alert("Invalid Description!");
                document.form1.description.focus();
                return;
            }

            if(document.form1.longdescription.value.length == 0)
            {
                alert("Invalid Long Description!");
                document.form1.longdescription.focus();
                return;
            }

            if(document.form1.prefix.value.length == 0)
            {
                alert("Invalid Prefix!");
                document.form1.prefix.focus();
                return;
            }

            if(document.form1.markup.value.length == 0)
            {
                alert("Invalid Markup Percentage!");
                document.form1.markup.focus();
                return;
            }

            document.form1.submit();
        }

        function OnClickDelete()
        {
            window.location="./manufacturerdelete_result.jsp?manufacturerid=<%=request.getParameter("id")%>&name=<%=getManufacturerResponse.getmanufacturer().getname()%>";
        }

        function OnClickCancel()
        {
            window.location="./manufacturers.jsp";
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
                            <td class="producttitle">Update/Delete Manufacturer</td>
                        </tr>
                    </table>
                    <br>
                    <form name="form1" action="./manufacturerupdate_result.jsp" method="post">
                    <TABLE cellSpacing="1" cellPadding="3" width="95%" border="0">
                    <tr>
                        <td noWrap>Active</td>
                        <td><input type="checkbox" name="active" <%=getManufacturerResponse.getmanufacturer().getactive()==true?"checked":""%>></td>
                    </tr>
                    <tr>
                        <td noWrap>Manufacturer ID:</td>
                        <td><input name="manufacturerid" type="text" size="10" value="<%=new Integer(getManufacturerResponse.getmanufacturer().getid()).toString()%>" readonly/></td>
                    </tr>
                    <tr>
                        <td noWrap>Name:</td>
                        <td><input name="name" type="text" size="25" value="<%=getManufacturerResponse.getmanufacturer().getname()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Long Name:</td>
                        <td><input name="longname" type="text" size="40" value="<%=getManufacturerResponse.getmanufacturer().getlongname()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap valign="top">Description:</td>
                        <td><textarea name="description" rows="5" cols="70"><%=getManufacturerResponse.getmanufacturer().getshortdescription()%></textarea></td>
                    </tr>
                    <tr>
                        <td noWrap valign="top">Long Description:</td>
                        <td><textarea name="longdescription" rows="10" cols="70"><%=getManufacturerResponse.getmanufacturer().getdescription()%></textarea></td>
                    </tr>
                    <tr>
                        <td noWrap>Prefix:</td>
                        <td><input name="prefix" type="text" size="4" value="<%=getManufacturerResponse.getmanufacturer().getprefix()%>"/></td>
                    </tr>
                    <tr>
                        <td>Markup Percent (i.e. 40 = 40%):</td>
                        <td><input name="markup" type="text" size="4" value="<%=new Double(getManufacturerResponse.getmanufacturer().getmarkup()*100.0).toString()%>"/></td>
                    </tr>
                    </TABLE>
                    <br>
                    <TABLE border="0">
                    <tr>
                        <td><input type="button" value="Update" onclick="OnClickUpdate()"></td>
                        <td><input type="button" value="Delete" onclick="OnClickDelete()"></td>
                    </tr>
                    </TABLE>
                    </form>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>
