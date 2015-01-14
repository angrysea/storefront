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

        Distributor distributor = new Distributor();
        distributor.setname(request.getParameter("name"));
        distributor.setdescription(request.getParameter("description"));
        if(request.getParameter("dropshipfee") != null)
            distributor.setdropshipfee(new Double(request.getParameter("dropshipfee")).doubleValue());
        if(request.getParameter("email") != null)
            distributor.setemail(request.getParameter("email"));
        if(request.getParameter("address1") != null)
            distributor.setaddress1(request.getParameter("address1"));
        if(request.getParameter("address2") != null)
            distributor.setaddress2(request.getParameter("address2"));
        if(request.getParameter("address3") != null)
            distributor.setaddress3(request.getParameter("address3"));
        if(request.getParameter("city") != null)
            distributor.setcity(request.getParameter("city"));
        if(request.getParameter("state") != null)
            distributor.setstate(request.getParameter("state"));
        if(request.getParameter("zipcode") != null)
            distributor.setzip(request.getParameter("zipcode"));
        if(request.getParameter("country") != null)
            distributor.setcountry(request.getParameter("country"));
        if(request.getParameter("telephone") != null)
            distributor.setphone(request.getParameter("telephone"));

        AddDistributorRequest addDistributorRequest = new AddDistributorRequest();
        addDistributorRequest.setdistributor(distributor);

        lists.AddDistributor(addDistributorRequest);
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
                            <td class="producttitle">Add Distributor Result</td>
                        </tr>
                    </table>
                    <br>
                    <TABLE cellSpacing="1" cellPadding="3" width="95%" border="0">
                    <tr>
                        <td noWrap>The distributor [<%=request.getParameter("name")%>] was added successfully.</td>
                    </tr>
                    </TABLE>
                    <br>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>

