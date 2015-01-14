<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    try {
        LinksBean linksBean = new LinksBean();

        Link link = new Link();
        link.setid(Integer.parseInt(request.getParameter("id")));
        link.setcompanyid(Integer.parseInt(request.getParameter("company")));
        link.seturl(request.getParameter("url"));
        link.setheader(request.getParameter("header"));
        link.setdescription(request.getParameter("description"));
        if(request.getParameter("emailaddress") != null && request.getParameter("emailaddress").length() > 0)
            link.setemail(request.getParameter("emailaddress"));
        if(request.getParameter("linkback") != null && request.getParameter("linkback").compareToIgnoreCase("on") == 0)
            link.setlinkback(true);
        else
            link.setlinkback(false);

        UpdateLinkRequest updateLinkRequest = new UpdateLinkRequest();
        updateLinkRequest.setlink(link);
        linksBean.UpdateLink(updateLinkRequest);
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
                            <td class="producttitle">Update Link Result</td>
                        </tr>
                    </table>
                    <br>
                    <TABLE cellSpacing="1" cellPadding="3" width="95%" border="0">
                    <tr>
                        <td noWrap>The link [<%=request.getParameter("url")%>] was updated successfully.</td>
                    </tr>
                    </TABLE>
                    <br>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>

