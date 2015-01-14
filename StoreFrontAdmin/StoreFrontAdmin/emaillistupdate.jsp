<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

    EMailList eMailList = null;
    try {
        EMailListBean emailListBean = new EMailListBean();
        GetEMailListRequest getEMailListRequest = new GetEMailListRequest();
        getEMailListRequest.setid(Integer.parseInt(request.getParameter("id")));
        GetEMailListResponse getEMailListResponse = emailListBean.GetEMailList(getEMailListRequest);
        eMailList = getEMailListResponse.getemaillist();
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>


<HTML>
<%@ include file="commonscript.jsp" %>
    <SCRIPT LANGUAGE="JavaScript">
        function OnClickUpdate()
        {
            if(checkEmail(document.form1.emailaddress.value) == false)
            {
               document.form1.emailaddress.focus();
               return;
            }
            document.form1.submit();
        }
        function OnClickDelete()
        {
            window.location = "./emaillistdelete_result.jsp?id=<%=request.getParameter("id")%>";
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
                            <td class="producttitle">Update an E-Mail Address</td>
                        </tr>
                    </table>
                    <br>
                    <form name="form1" action="./emaillistupdate_result.jsp" method="post">
                    <input type="hidden" name="id" value="<%=Integer.toString(eMailList.getid())%>">
                    <TABLE cellSpacing="1" cellPadding="3" width="95%" border="0">
                    <tr>
                        <td noWrap>E-Mail Address:</td>
                        <td><input name="emailaddress" type="text" size="30" value="<%=eMailList.getemail()%>"/></td>
                    </tr>
                    <tr>
                        <td>Unsubscribed:</td>
                        <td><input type="checkbox" name="unsubscribed" <%=eMailList.getoptout()==true?"checked":""%>></td>
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
