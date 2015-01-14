<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    AddEMailListResponse addEMailListResponse = null;
    try {
        EMailListBean emailListBean = new EMailListBean();
        AddEMailListRequest addEMailListRequest = new AddEMailListRequest();
        EMailList eMailList = new EMailList();
        eMailList.setemail(request.getParameter("emailaddress"));
        addEMailListRequest.setemaillist(eMailList);
        addEMailListResponse = emailListBean.AddEMailList(addEMailListRequest);
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
                            <td class="producttitle">Add E-Mail Address Result</td>
                        </tr>
                    </table>
                    <br>
                    <TABLE cellSpacing="1" cellPadding="3" width="95%" border="0">
            <%
                if(addEMailListResponse != null && addEMailListResponse.getid() > 0)
                {
            %>
                    <tr>
                        <td noWrap><%=request.getParameter("emailaddress")%> was added successfully.</td>
                    </tr>
            <%
                }
                else
                {
            %>
                    <tr>
                        <td noWrap><%=request.getParameter("emailaddress")%> is already in the list.</td>
                    </tr>
            <%
                }
            %>
                    <tr>
                        <td><br></td>
                    </tr>
                    <tr>
                        <td class="smallprompt"><A href="./emaillistadd.jsp">Add another E-Mail Address</A></td>
                    </tr>
                    </TABLE>
                    <br>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>

