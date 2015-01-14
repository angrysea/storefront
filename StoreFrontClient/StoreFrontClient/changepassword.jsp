<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    Theme theme = null;
    Company company = null;
    try {
        CompanyBean companyBean = new CompanyBean();
        GetThemeRequest getThemeRequest = new GetThemeRequest();
        getThemeRequest.setname("corporate");
        GetThemeResponse getThemeResponse = companyBean.GetTheme(request, getThemeRequest);
        theme = getThemeResponse.gettheme();

        GetCompanyResponse getCompanyResponse = companyBean.GetCompany(request, new GetCompanyRequest());
        company = getCompanyResponse.getcompany();
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<HTML>
    <SCRIPT LANGUAGE="JavaScript">
    function OnSubmitForm()
    {
        if(document.form1.oldpassword.value.length < 6)
        {
            alert("The old password must be at least 6 characters!");
            document.form1.oldpassword.focus();
            return false;
        }

        if(document.form1.newpassword.value.length < 6)
        {
            alert("The new password must be at least 6 characters");
            document.form1.newpassword.focus();
            return false;
        }

        if(document.form1.newpassword.value != document.form1.confirmedpassword.value)
        {
            alert("The confirmed password does not match the new password!");
            document.form1.confirmedpassword.focus();
            return false;
        }

        return true;
    }
    </SCRIPT>
    <HEAD>
        <%@ include file="titlemetadata.jsp" %>
        <LINK href="<%=company.getbaseurl()%>storefront.css" rel="STYLESHEET"></LINK>
    </HEAD>
    <BODY leftMargin="0" topMargin="0" onload="" marginwidth="0" marginheight="0">
        <%@ include file="toppane.jsp" %>
        <table cellSpacing="0" cellPadding="0" border="0">
            <tr>
                <td vAlign="top" width="20">
                    <IMG src="<%=theme.getimagebaseurl()%>spacer.gif" border="0">
                </td>
                <td vAlign="top">
                    <table id="headingTable" border="0">
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="5" height="5"></td>
                        </tr>
                    </table>
                    <form name="form1" action="<%=company.getbasesecureurl()%>changepassword_result.jsp" method="post" onsubmit="return(OnSubmitForm());">
                    <input name="user" type="hidden" value="<%=request.getParameter("user")==null?"":request.getParameter("user")%>"/>
                    <input name="login" type="hidden" value="<%=request.getParameter("login")==null?"":request.getParameter("login")%>"/>
                    <table cellSpacing="1" cellPadding="1" width="550" border="0">
                        <tr>
                            <td><br></td>
                        </tr>
                    <%
                        if(request.getParameter("error") != null)
                        {
                    %>
                        <tr>
                            <td nowrap colspan="2" class="errorText"><%=request.getParameter("error")%></td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                    <%
                        }
                    %>
                        <tr bgcolor="<%=theme.getcolor2()%>">
                            <td colspan="5" nowrap class="producttitle">Change your password</td>
                        </tr>
                        <tr>
                            <td colspan="2"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="1" height="4" /></td>
                        </tr>
                        <tr>
                            <td nowrap align="right">Old password:</td>
                            <td><input name="oldpassword" type="password" size="25"/></td>
                        </tr>
                        <tr>
                            <td nowrap align="right">New password:</td>
                            <td><input name="newpassword" type="password" size="25"/></td>
                        </tr>
                        <tr>
                            <td nowrap align="right">Confirm your new password:</td>
                            <td><input name="confirmedpassword" type="password" size="25"/></td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td align="right"><input type="image" alt="submit" src="<%=theme.getimagebaseurl()%>submit_button.gif"/></td>
                        </tr>
                    </table>
                    </form>
                </td>
            </tr>
        </table>
        <%@ include file="bottompane.jsp" %>
    </BODY>
    <script LANGUAGE="JavaScript">
        document.form1.oldpassword.focus();
    </script>
</HTML>

