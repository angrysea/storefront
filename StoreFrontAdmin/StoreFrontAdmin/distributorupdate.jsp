<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.net.*" %>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    DecimalFormat numberFormat = new DecimalFormat("#,##0.00");

    Distributor distributor = null;
    try {
        ListsBean lists = new ListsBean();

        GetDistributorRequest getDistributorRequest = new GetDistributorRequest();
        getDistributorRequest.setid(new Integer(request.getParameter("id")).intValue());
        GetDistributorResponse getDistributorResponse = lists.GetDistributor(getDistributorRequest);
        distributor = getDistributorResponse.getdistributor();
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

        function OnClickDelete()
        {
            window.location="./distributordelete_result.jsp?distributorid=<%=request.getParameter("id")%>&name=<%=distributor.getname()%>";
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
                            <td class="producttitle">Update/Delete Distributor</td>
                        </tr>
                    </table>
                    <br>
                    <form name="form1" action="./distributorupdate_result.jsp" method="post">
                    <TABLE cellSpacing="1" cellPadding="3" width="95%" border="0">
                    <tr>
                        <td noWrap>Distributor ID:</td>
                        <td><input name="distributorid" type="text" size="10" readonly value="<%=new Integer(distributor.getid()).toString()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Name:</td>
                        <td><input name="name" type="text" size="25" value="<%=distributor.getname()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap valign="top">Description:</td>
                        <td><textarea name="description" rows="10" cols="100"><%=distributor.getdescription()%></textarea></td>
                    </tr>
                    <tr>
                        <td noWrap>Drop Ship Fee:</td>
                        <td><input name="dropshipfee" type="text" size="10" value="<%=numberFormat.format(distributor.getdropshipfee())%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>E-Mail:</td>
                        <td><input name="email" type="text" size="30" value="<%=distributor.getemail()==null?"":distributor.getemail()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Address 1:</td>
                        <td><input name="address1" type="text" size="50" value="<%=distributor.getaddress1()==null?"":distributor.getaddress1()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Address 2:</td>
                        <td><input name="address2" type="text" size="50" value="<%=distributor.getaddress2()==null?"":distributor.getaddress2()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Address 3:</td>
                        <td><input name="address3" type="text" size="50" value="<%=distributor.getaddress3()==null?"":distributor.getaddress3()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>City:</td>
                        <td><input name="city" type="text" size="30" value="<%=distributor.getcity()==null?"":distributor.getcity()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>State:</td>
                        <td><input name="state" type="text" size="5" value="<%=distributor.getstate()==null?"":distributor.getstate()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Zip Code:</td>
                        <td><input name="zipcode" type="text" size="10" value="<%=distributor.getzip()==null?"":distributor.getzip()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Country Code:</td>
                        <td><input name="country" type="text" size="5" value="<%=distributor.getcountry()==null?"":distributor.getcountry()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Telephone:</td>
                        <td><input name="telephone" type="text" size="15" value="<%=distributor.getphone()==null?"":distributor.getphone()%>"/></td>
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
    <SCRIPT LANGUAGE="JavaScript">
    </SCRIPT>
</HTML>
