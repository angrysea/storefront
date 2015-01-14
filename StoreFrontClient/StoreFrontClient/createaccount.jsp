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
    <%@ include file="commonscript.jsp" %>
    <SCRIPT LANGUAGE="JavaScript">
    function OnSubmitForm()
    {
        if(checkEmail(document.form1.emailaddress.value) == false)
        {
            document.form1.emailaddress.focus();
            return false;
        }

        if(document.form1.password.value.length < 6)
        {
            alert("Password must be at least 6 characters");
            document.form1.password.focus();
            return false;
        }

        if(document.form1.confirmpassword.value.length < 6)
        {
            alert("Password must be at least 6 characters");
            document.form1.confirmpassword.focus();
            return false;
        }

        if(document.form1.password.value != document.form1.confirmpassword.value)
        {
            alert("The confirmed password does not match!");
            document.form1.confirmpassword.focus();
            return false;
        }

        if(document.form1.firstname.value.trim().length == 0)
        {
            alert("First name is required!");
            document.form1.firstname.focus();
            return false;
        }

        if(document.form1.lastname.value.trim().length == 0)
        {
            alert("Last name is required!");
            document.form1.lastname.focus();
            return false;
        }

        if(document.form1.billingaddress1.value.trim().length == 0)
        {
            alert("Billing address 1 is required!");
            document.form1.billingaddress1.focus();
            return false;
        }

        if(document.form1.city.value.trim().length == 0)
        {
            alert("City is required!");
            document.form1.city.focus();
            return false;
        }

        if(document.form1.country.value == "US")
        {
            if(document.form1.state.value == 0)
            {
                alert("A state must be selected!");
                document.form1.state.focus();
                return false;
            }
        }

        if(document.form1.zipcode.value.trim().length == 0)
        {
            alert("Zip/Postal Code is required!");
            document.form1.zipcode.focus();
            return false;
        }

        if(document.form1.phone.value.trim().length < 10)
        {
            alert("A valid phone number is required!");
            document.form1.phone.focus();
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
                    <form name="form1" action="<%=company.getbasesecureurl()%>createaccount_result.jsp" method="post" onsubmit="return(OnSubmitForm());">
                    <input name="user" type="hidden" value="<%=request.getParameter("user")==null?"":request.getParameter("user")%>"/>
                    <input name="login" type="hidden" value="<%=request.getParameter("login")==null?"":request.getParameter("login")%>"/>
                    <input name="gotourl" type="hidden" value="<%=request.getParameter("gotourl")%>" />
                    <table cellSpacing="1" cellPadding="3" width="200" border="0">
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
                            <td colspan="5" nowrap class="producttitle" width="500">Create a New Account</td>
                        </tr>
                        <tr>
                            <td colspan="2"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="1" height="4" /></td>
                        </tr>
                        <tr>
                            <td nowrap align="right"><font color="red">*</font> Enter your e-mail address:</td>
                            <td><input name="emailaddress" type="text" size="25" value="<%=request.getParameter("emailaddress")==null?"":request.getParameter("emailaddress").trim()%>"/></td>
                        </tr>
                        <tr>
                            <td nowrap align="right"><font color="red">*</font> Password:</td>
                            <td><input name="password" type="password" size="25" value="<%=request.getParameter("password")==null?"":request.getParameter("password")%>"/></td>
                        </tr>
                        <tr>
                            <td nowrap align="right"><font color="red">*</font> Confirm Password:</td>
                            <td><input name="confirmpassword" type="password" size="25" value="<%=request.getParameter("password")==null?"":request.getParameter("password")%>"/></td>
                        </tr>
                        <tr>
                            <td nowrap align="right">Title:</td>
                            <td>
			        <select  size="1" name="title">
                                    <%@ include file="selecttitle.jsp" %>
			        </select>
                            </td>
                        </tr>
                        <tr>
                            <td nowrap align="right"><font color="red">*</font> First Name:</td>
                            <td><input name="firstname" type="text" size="25" value="<%=request.getParameter("firstname")==null?"":request.getParameter("firstname").trim()%>"/></td>
                        </tr>
                        <tr>
                            <td nowrap align="right"><font color="red">*</font> Last Name:</td>
                            <td><input name="lastname" type="text" size="25" value="<%=request.getParameter("lastname")==null?"":request.getParameter("lastname").trim()%>"/></td>
                        </tr>
                        <tr>
                            <td align="right" nowrap>Suffix:</td>
                            <td>
				<select size="1" name="suffix">
                                    <%@ include file="selectsuffix.jsp" %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td nowrap align="right"><font color="red">*</font> Billing Address 1:</td>
                            <td><input name="billingaddress1" type="text" size="35" value="<%=request.getParameter("billingaddress1")==null?"":request.getParameter("billingaddress1").trim()%>"/></td>
                        </tr>
                        <tr>
                            <td nowrap align="right">Billing Address 2:</td>
                            <td><input name="billingaddress2" type="text" size="35" value="<%=request.getParameter("billingaddress2")==null?"":request.getParameter("billingaddress2").trim()%>"/></td>
                        </tr>
                        <tr>
                            <td nowrap align="right"><font color="red">*</font> City:</td>
                            <td><input name="city" type="text" size="20" value="<%=request.getParameter("city")==null?"":request.getParameter("city").trim()%>"/></td>
                        </tr>
                        <tr>
                            <td nowrap align="right"><font color="red">*</font> State/Province:</td>
                            <td>
                                <select name="state" size="1">
                                    <%@ include file="selectstate.jsp" %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td nowrap align="right"><font color="red">*</font> Zip/Postal Code:</td>
                            <td><input name="zipcode" type="text" size="10" value="<%=request.getParameter("zipcode")==null?"":request.getParameter("zipcode").trim()%>"/></td>
                        </tr>
                        <tr>
                            <td nowrap align="right"><font color="red">*</font> Country:</td>
                            <td>
                                <select name="country">
                                <%
                                ListsBean listsBean = new ListsBean();
                                GetCountriesResponse getCountriesResponse = listsBean.GetContryCodes(new GetCountriesRequest());
                                Iterator itCountries = getCountriesResponse.getcountriesIterator();
                                while(itCountries.hasNext())
                                {
                                    CountryCode countryCode = (CountryCode)itCountries.next();
                                %>
                                    <option value="<%=countryCode.getcode()%>"><%=countryCode.getname()%></option>
                                <%
                                }
                                %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td nowrap align="right"><font color="red">*</font> Phone:</td>
                            <td><input name="phone" type="text" size="20" value="<%=request.getParameter("phone")==null?"":request.getParameter("phone").trim()%>"/></td>
                        </tr>
                        <tr>
                            <td colspan="2"><center><I>If the above address is your company address,<br>please enter your company name in this field.</I></center></td>
                        </tr>
                        <tr>
                            <td nowrap align="right">Company Name:</td>
                            <td><input name="companyname" type="text" size="25" value="<%=request.getParameter("companyname")==null?"":request.getParameter("companyname").trim()%>"/></td>
                        </tr>
                    </table>
                    <br>
                    <table border="0" cellspacing="0" cellpadding="0" width="500">
                        <tr bgcolor="<%=theme.getcolor1()%>">
                            <td colspan="10"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="500" height="1"></td>
                        </tr>
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td align="right"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="15" height="1"/><input type="image" alt="create account" src="<%=theme.getimagebaseurl()%>createaccount_button.gif"/></td>
                        </tr>
                    </table>
                    </form>
                </td>
            </tr>
        </table>
        <%@ include file="bottompane.jsp" %>
    </BODY>
    <SCRIPT LANGUAGE="JavaScript">
            document.form1.country.value = 'US';
    <%
        if(request.getParameter("error") != null)
        {
    %>
            document.form1.title.value = '<%=request.getParameter("title")%>';
            document.form1.suffix.value = '<%=request.getParameter("suffix")%>';
            document.form1.state.value = '<%=request.getParameter("state")%>';
            document.form1.country.value = '<%=request.getParameter("country")%>';
    <%
        }
    %>
    </SCRIPT>
    <script LANGUAGE="JavaScript">
        document.form1.emailaddress.focus();
    </script>
</HTML>

