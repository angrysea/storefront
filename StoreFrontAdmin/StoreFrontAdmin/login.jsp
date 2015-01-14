<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    Theme theme = null;
    try {
        CompanyBean companyBean = new CompanyBean();
        GetThemeRequest getThemeRequest = new GetThemeRequest();
        getThemeRequest.setname("corporate");
        GetThemeResponse getThemeResponse = companyBean.GetTheme(request, getThemeRequest);
        theme = getThemeResponse.gettheme();
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<HTML>
    <%@ include file="commonscript.jsp" %>
    <SCRIPT LANGUAGE="JavaScript">
    function OnSubmitForm()
    {
        if(checkEmail(document.form1.j_username.value) == false)
        {
            document.form1.j_username.focus();
            return false;
        }

        if(document.form1.j_password.value.length < 6)
        {
            alert("Password must be at least 6 characters");
            document.form1.j_password.focus();
            return false;
        }

        return true;
    }
    </SCRIPT>
    <HEAD>
        <LINK href="./storefront.css" rel="STYLESHEET"></LINK>
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
                    <form name="form1" method="POST" action='<%= response.encodeURL("j_security_check") %>' >
                    <table cellSpacing="1" cellPadding="1" width="200" border="0">
                    <input name="gotourl" type="hidden" value="<%=request.getParameter("gotourl")%>" />
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr bgcolor="<%=theme.getcolor2()%>">
                            <td colspan="5" nowrap class="producttitle">Please log in with your correct username and password</td>
                        </tr>
                        <tr>
                            <td colspan="2"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="1" height="4" /></td>
                        </tr>
    			<tr>
                           <td nowrap align="right">Username:</td>
                       	   <td align="left"><input type="text" name="j_username"></td>
    			</tr>
                        <tr>
      			   <td nowrap align="right">Password:</td>
      			   <td align="left"><input type="password" name="j_password"></td>
    			</tr>
                        <tr>
                            <td align="right"><input type="image" alt="login" src="<%=theme.getimagebaseurl()%>login_button.gif"/></td>
                        </tr>
                        <tr>
                            <td colspan="2"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="1" height="4" /></td>
                        </tr>
                    </table>
                    </form>
                </td>
            </tr>
        </table>
    </BODY>
    <script LANGUAGE="JavaScript">
        document.form1.j_username.focus();
    </script>
</HTML>

