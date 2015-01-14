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

<%
    Customer customer = null;
    Address billingaddress = null;
    Address shippingaddress = null;
    GetUserResponse getUserResponse = null;
    boolean loggedIn = false;
    try {
        UserBean userBean = new UserBean();
        CustomerBean customerBean = new CustomerBean();

        getUserResponse = userBean.GetUser(request, response);
        loggedIn = (getUserResponse.getuser() != null && getUserResponse.getuser().getloggedin())?true:false;

        GetCustomerRequest getCustomerRequest = new GetCustomerRequest();
        getCustomerRequest.setid(getUserResponse.getuser().getid());
        GetCustomerResponse getCustomerResponse = customerBean.GetCustomer(getCustomerRequest);
        customer = getCustomerResponse.getcustomer();
        Iterator itAddresses = customer.getaddressIterator();
        while(itAddresses.hasNext())
        {
            Address address = (Address)itAddresses.next();
            if(address.gettype().compareToIgnoreCase("currentbilling") == 0)
                billingaddress = address;
            if(address.gettype().compareToIgnoreCase("currentshipping") == 0)
                shippingaddress = address;
        }
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

      <%
      if(loggedIn == false)
      {
      %>
    	if(loggedIn == false) {
	   if(checkEmail(document.form1.emailaddress.value) == false)
	   {
              document.form1.emailaddress.focus();
              return false;
           }
    	}
      <%
      }
      %>

        if(document.form1.billingfirstname.value.trim().length == 0)
        {
            alert("First name is required!");
            document.form1.billingfirstname.focus();
            return false;
        }

        if(document.form1.billinglastname.value.trim().length == 0)
        {
            alert("Last name is required!");
            document.form1.billinglastname.focus();
            return false;
        }

        if(document.form1.billingaddress1.value.trim().length == 0)
        {
            alert("Billing address 1 is required!");
            document.form1.billingaddress1.focus();
            return false;
        }

        if(document.form1.billingcity.value.trim().length == 0)
        {
            alert("Billing city is required!");
            document.form1.billingcity.focus();
            return false;
        }

        if(document.form1.billingcountry.value == "US")
        {
            if(document.form1.billingstate.value == 0)
            {
                alert("A billing state must be selected!");
                document.form1.billingstate.focus();
                return false;
            }
        }

        if(document.form1.billingzipcode.value.trim().length == 0)
        {
            alert("Billing zip/postal Code is required!");
            document.form1.billingzipcode.focus();
            return false;
        }

        if(document.form1.billingphone.value.trim().length < 10)
        {
            alert("A valid billing phone phone number is required!");
            document.form1.billingphone.focus();
            return false;
        }


        if(document.form1.shippingfirstname.value.trim().length == 0)
        {
            alert("First name is required!");
            document.form1.shippingfirstname.focus();
            return false;
        }

        if(document.form1.shippinglastname.value.trim().length == 0)
        {
            alert("Last name is required!");
            document.form1.shippinglastname.focus();
            return false;
        }

        if(document.form1.shippingaddress1.value.trim().length == 0)
        {
            alert("Shipping address 1 is required!");
            document.form1.shippingaddress1.focus();
            return false;
        }

        if(document.form1.shippingcity.value.trim().length == 0)
        {
            alert("Shipping city is required!");
            document.form1.shippingcity.focus();
            return false;
        }

        if(document.form1.shippingcountry.value == "US")
        {
            if(document.form1.shippingstate.value == 0)
            {
                alert("A shipping state must be selected!");
                document.form1.shippingstate.focus();
                return false;
            }
        }

        if(document.form1.shippingzipcode.value.trim().length == 0)
        {
            alert("Shipping zip/postal Code is required!");
            document.form1.shippingzipcode.focus();
            return false;
        }

        if(document.form1.shippingphone.value.trim().length < 10)
        {
            alert("A valid shipping phone phone number is required!");
            document.form1.shippingphone.focus();
            return false;
        }

        return true;
    }

    function OnClickSynchronizeShipping()
    {
        if(document.form1.synchronizeshipping.checked)
        {
            CopyBillingToShipping();
        }

        //DisableControls(document.form1.synchronizeshipping.checked);
        OnChangeShippingInfo();
    }

    function OnChangeBillingInfo()
    {
        document.form1.billaddresschanged.value = '1';
        if(document.form1.synchronizeshipping.checked)
        {
            CopyBillingToShipping();
        }
    }

    function OnChangeShippingInfo()
    {
        document.form1.shipaddresschanged.value = '1';
    }

    function CopyBillingToShipping()
    {
        document.form1.shippingtitle.value = document.form1.billingtitle.value;
        document.form1.shippingfirstname.value = document.form1.billingfirstname.value.trim();
        document.form1.shippinglastname.value = document.form1.billinglastname.value.trim();
        document.form1.shippingsuffix.value = document.form1.billingsuffix.value;
        document.form1.shippingaddress1.value = document.form1.billingaddress1.value.trim();
        document.form1.shippingaddress2.value = document.form1.billingaddress2.value.trim();
        document.form1.shippingcity.value = document.form1.billingcity.value.trim();
        document.form1.shippingstate.value = document.form1.billingstate.value;
        document.form1.shippingzipcode.value = document.form1.billingzipcode.value.trim();
        document.form1.shippingcountry.value = document.form1.billingcountry.value;
        document.form1.shippingphone.value = document.form1.billingphone.value.trim();
        document.form1.shippingcompanyname.value = document.form1.billingcompanyname.value.trim();
    }

    function DisableControls(disableit)
    {
          document.form1.shippingtitle.disabled = disableit;
          document.form1.shippingfirstname.disabled = disableit;
          document.form1.shippinglastname.disabled = disableit;
          document.form1.shippingsuffix.disabled = disableit;
          document.form1.shippingaddress1.disabled = disableit;
          document.form1.shippingaddress2.disabled = disableit;
          document.form1.shippingcity.disabled = disableit;
          document.form1.shippingstate.disabled = disableit;
          document.form1.shippingzipcode.disabled = disableit;
          document.form1.shippingcountry.disabled = disableit;
          document.form1.shippingphone.disabled = disableit;
          document.form1.shippingcompanyname.disabled = disableit;
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
                    <form name="form1" action="<%=company.getbasesecureurl()%>confirmbillship_result.jsp" method="get" onsubmit="return(OnSubmitForm());">
                    <input name="user" type="hidden" value="<%=request.getParameter("user")==null?"":request.getParameter("user")%>"/>
                    <input name="login" type="hidden" value="<%=request.getParameter("login")==null?"":request.getParameter("login")%>"/>
                    <table cellSpacing="1" cellPadding="3" width="550" border="0">
                    <input name="gotourl" type="hidden" value="<%=request.getParameter("gotourl")==null?"":request.getParameter("gotourl")%>" />
                    <input name="billaddresschanged" type="hidden" value="0"/>
                    <input name="shipaddresschanged" type="hidden" value="0"/>
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
                            <td colspan="5" nowrap class="producttitle" width="500">Billing Information</td>
                        </tr>
                        <tr>
                            <td colspan="2"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="1" height="4" /></td>
                        </tr>
                    <%
                        if(loggedIn == false)
                        {
                    %>
                        <tr>
                            <td nowrap align="right"><font color="red">*</font> E-mail Address:</td>
                            <td><input name="emailaddress" type="text" size="25" value="<%=customer==null||customer.getemail1()==null?"":customer.getemail1()%>" onchange="OnChangeBillingInfo()"/></td>
                        </tr>
                    <%
                        }
                    %>
                        <tr>
                            <td nowrap align="right">Title:</td>
                            <td>
			        <select  size="1" name="billingtitle" onchange="OnChangeBillingInfo()">
                                    <%@ include file="selecttitle.jsp" %>
			        </select>
                            </td>
                        </tr>
                        <tr>
                            <td nowrap align="right"><font color="red">*</font> First Name:</td>
                            <td><input name="billingfirstname" type="text" size="25" value="<%=customer==null||customer.getfirst()==null?"":customer.getfirst()%>" onchange="OnChangeBillingInfo()"/></td>
                        </tr>
                        <tr>
                            <td nowrap align="right"><font color="red">*</font> Last Name:</td>
                            <td><input name="billinglastname" type="text" size="25" value="<%=customer==null||customer.getlast()==null?"":customer.getlast()%>" onchange="OnChangeBillingInfo()"/></td>
                        </tr>
                        <tr>
                            <td align="right" nowrap>Suffix:</td>
                            <td>
				<select size="1" name="billingsuffix" onchange="OnChangeBillingInfo()">
                                    <%@ include file="selectsuffix.jsp" %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td nowrap align="right">Company Name:</td>
                            <td><input name="billingcompanyname" type="text" size="25" value="<%=billingaddress!=null&&billingaddress.getcompany()!=null?billingaddress.getcompany():""%>" onchange="OnChangeBillingInfo()" /></td>
                        </tr>
                        <tr>
                            <td nowrap align="right"><font color="red">*</font> Billing Address 1:</td>
                            <td><input name="billingaddress1" type="text" size="35" value="<%=billingaddress!=null?billingaddress.getaddress1():""%>" onchange="OnChangeBillingInfo()"/></td>
                        </tr>
                        <tr>
                            <td nowrap align="right">Billing Address 2:</td>
                            <td><input name="billingaddress2" type="text" size="35" value="<%=(billingaddress!=null&&billingaddress.getaddress2()!=null)?billingaddress.getaddress2():""%>" onchange="OnChangeBillingInfo()"/></td>
                        </tr>
                        <tr>
                            <td nowrap align="right"><font color="red">*</font> City:</td>
                            <td><input name="billingcity" type="text" size="20" value="<%=billingaddress!=null?billingaddress.getcity():""%>" onchange="OnChangeBillingInfo()"/></td>
                        </tr>
                        <tr>
                            <td nowrap align="right"><font color="red">*</font> State/Province:</td>
                            <td>
                                <select name="billingstate" onchange="OnChangeBillingInfo()">
                                    <%@ include file="selectstate.jsp" %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td nowrap align="right"><font color="red">*</font> Zip/Postal Code:</td>
                            <td><input name="billingzipcode" type="text" size="10" value="<%=billingaddress!=null?billingaddress.getzip():""%>" onchange="OnChangeBillingInfo()"/></td>
                        </tr>
                        <tr>
                            <td nowrap align="right"><font color="red">*</font> Country:</td>
                            <td>
                                <select name="billingcountry" onchange="OnChangeBillingInfo()">
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
                            <td><input name="billingphone" type="text" size="20" value="<%=billingaddress!=null?billingaddress.getphone():""%>" onchange="OnChangeBillingInfo()"/></td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>

                        <tr bgcolor="<%=theme.getcolor2()%>">
                            <td colspan="5" nowrap class="producttitle" width="500">Shipping Information</td>
                        </tr>
                        <tr>
                            <td colspan="2"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="1" height="4" /></td>
                        </tr>
                        <tr>
                            <td align="right"><input name="synchronizeshipping" type="checkbox" onclick="OnClickSynchronizeShipping()"/></td>
                            <td nowrap><b>Use my billing address as my shipping address</b></td>
                        </tr>
                        <tr>
                            <td nowrap align="right">Title:</td>
                            <td>
			        <select  size="1" name="shippingtitle" onchange="OnChangeShippingInfo()" >
                                    <%@ include file="selecttitle.jsp" %>
			        </select>
                            </td>
                        </tr>
                        <tr>
                            <td nowrap align="right"><font color="red">*</font> First Name:</td>
                            <td><input name="shippingfirstname" type="text" size="25" value="<%=shippingaddress!=null?shippingaddress.getfirst():""%>" onchange="OnChangeShippingInfo()" /></td>
                        </tr>
                        <tr>
                            <td nowrap align="right"><font color="red">*</font> Last Name:</td>
                            <td><input name="shippinglastname" type="text" size="25" value="<%=shippingaddress!=null?shippingaddress.getlast():""%>" onchange="OnChangeShippingInfo()" /></td>
                        </tr>
                        <tr>
                            <td align="right" nowrap>Suffix:</td>
                            <td>
				<select size="1" name="shippingsuffix" onchange="OnChangeShippingInfo()" >
                                    <%@ include file="selectsuffix.jsp" %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td nowrap align="right">Company Name:</td>
                            <td><input name="shippingcompanyname" type="text" size="25" value="<%=shippingaddress!=null&&shippingaddress.getcompany()!=null?shippingaddress.getcompany():""%>" onchange="OnChangeShippingInfo()" /></td>
                        </tr>
                        <tr>
                            <td nowrap align="right"><font color="red">*</font> Shipping Address 1:</td>
                            <td><input name="shippingaddress1" type="text" size="35" value="<%=shippingaddress!=null?shippingaddress.getaddress1():""%>" onchange="OnChangeShippingInfo()" /></td>
                        </tr>
                        <tr>
                            <td nowrap align="right">Shipping Address 2:</td>
                            <td><input name="shippingaddress2" type="text" size="35" value="<%=shippingaddress!=null&&shippingaddress.getaddress2()!=null?shippingaddress.getaddress2():""%>" onchange="OnChangeShippingInfo()" /></td>
                        </tr>
                        <tr>
                            <td nowrap align="right"><font color="red">*</font> City:</td>
                            <td><input name="shippingcity" type="text" size="20" value="<%=shippingaddress!=null?shippingaddress.getcity():""%>" onchange="OnChangeShippingInfo()" /></td>
                        </tr>
                        <tr>
                            <td nowrap align="right"><font color="red">*</font> State/Province:</td>
                            <td>
                                <select name="shippingstate" onchange="OnChangeShippingInfo()" >
                                    <%@ include file="selectstate.jsp" %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td nowrap align="right"><font color="red">*</font> Zip/Postal Code:</td>
                            <td><input name="shippingzipcode" type="text" size="10" value="<%=shippingaddress!=null?shippingaddress.getzip():""%>" onchange="OnChangeShippingInfo()" /></td>
                        </tr>
                        <tr>
                            <td nowrap align="right"><font color="red">*</font> Country:</td>
                            <td>
                                <select name="shippingcountry" onchange="OnChangeShippingInfo()" >
                                <%
                                listsBean = new ListsBean();
                                getCountriesResponse = listsBean.GetContryCodes(new GetCountriesRequest());
                                itCountries = getCountriesResponse.getcountriesIterator();
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
                            <td><input name="shippingphone" type="text" size="20" value="<%=shippingaddress!=null?shippingaddress.getphone():""%>" onchange="OnChangeShippingInfo()" /></td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                    </table>
                    <br>
                    <table border="0" cellspacing="0" cellpadding="0" width="550">
                        <tr bgcolor="<%=theme.getcolor1()%>">
                            <td colspan="10"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="550" height="1"></td>
                        </tr>
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td align="right"><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="15" height="1"/><input type="image" alt="continue" src="<%=theme.getimagebaseurl()%>continue_button.gif"/></td>
                        </tr>
                    </table>
                    </form>
                </td>
            </tr>
        </table>
        <%@ include file="bottompane.jsp" %>
    </BODY>
    <SCRIPT LANGUAGE="JavaScript">
        document.form1.billingcountry.value = 'US';
        document.form1.shippingcountry.value = 'US';

    <%
        if(customer != null)
        {
    %>
            document.form1.billingtitle.value = '<%=customer.getsalutation()!=null?customer.getsalutation():"0"%>';
            document.form1.billingsuffix.value = '<%=customer.getsuffix()!=null?customer.getsuffix():"0"%>';
    <%
        }
    %>

    <%
        if(billingaddress != null)
        {
    %>
            document.form1.billingstate.value ='<%=billingaddress.getstate()%>';
            document.form1.billingcountry.value = '<%=billingaddress.getcountry()%>';
    <%
        }
    %>

    <%
        if(shippingaddress != null)
        {
    %>
            document.form1.shippingtitle.value = '<%=shippingaddress.getsalutation()!=null?shippingaddress.getsalutation():"0"%>';
            document.form1.shippingsuffix.value = '<%=shippingaddress.getsuffix()!=null?shippingaddress.getsuffix():"0"%>';
            document.form1.shippingstate.value ='<%=shippingaddress.getstate()%>';
            document.form1.shippingcountry.value = '<%=shippingaddress.getcountry()%>';
    <%
        }
    %>
    </SCRIPT>
    <script LANGUAGE="JavaScript">
    <%
        if(loggedIn == true)
        {
    %>
            document.form1.billingfirstname.focus();
    <%
        }
        else
        {
    %>
            document.form1.emailaddress.focus();
    <%
        }
    %>
    </script>
</HTML>

