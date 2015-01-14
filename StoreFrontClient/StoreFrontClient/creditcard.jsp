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
    CreditCard creditcard = null;
    try {
        UserBean userBean = new UserBean();
        CustomerBean customerBean = new CustomerBean();

        GetUserResponse getUserResponse = userBean.GetUser(request, response, true);

        GetCustomerRequest getCustomerRequest = new GetCustomerRequest();
        getCustomerRequest.setid(getUserResponse.getuser().getid());
        GetCustomerResponse getCustomerResponse = customerBean.GetCustomer(getCustomerRequest);
        Customer customer = getCustomerResponse.getcustomer();
        Iterator itAddresses = customer.getaddressIterator();
        Address billingaddress = null;
        while(itAddresses.hasNext())
        {
            Address address = (Address)itAddresses.next();
            if(address.gettype().compareToIgnoreCase("currentbilling") == 0)
                billingaddress = address;
        }
        creditcard = billingaddress.getcreditcard();
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<%!
    public String DisplayCreditCard(String cardnumber)
    {
        if(cardnumber.length() == 0)
            return "";

        if(cardnumber.length() == 15)
            return "XXXXXXXXXXX" + cardnumber.subSequence(11, 15);
        else
            return "XXXXXXXXXXXX" + cardnumber.subSequence(12, 16);
    }
%>

<HTML>
    <%@ include file="commonscript.jsp" %>
    <%@ include file="creditcardvalidation.jsp" %>
    <SCRIPT LANGUAGE="JavaScript">
    function OnSubmitForm()
    {
        if(document.form1.cardnumber.value.length > 0)
        {
            var cardnumber = document.form1.cardnumber.value;
            var firstpart = cardnumber.substring(0, 2);
            if(firstpart != "XX")
            {
                if(ValidateCreditCard(document.form1.cardnumber.value) == false)
                {
                    document.form1.cardnumber.focus();
                    return false;
                }

                if(document.form1.expirationmonth.value == 0)
                {
                    alert("Enter a valid expiration month!");
                    document.form1.expirationmonth.focus();
                    return false;
                }

                if(document.form1.expirationyear.value == 0)
                {
                    alert("Enter a valid expiration year!");
                    document.form1.expirationyear.focus();
                    return false;
                }
            }
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
                    <form name="form1" action="<%=company.getbasesecureurl()%>creditcard_result.jsp" method="post" onsubmit="return(OnSubmitForm());">
                    <input name="user" type="hidden" value="<%=request.getParameter("user")==null?"":request.getParameter("user")%>"/>
                    <input name="login" type="hidden" value="<%=request.getParameter("login")==null?"":request.getParameter("login")%>"/>
                    <table cellSpacing="1" cellPadding="3" width="550" border="0">
                        <tr bgcolor="<%=theme.getcolor2()%>">
                            <td colspan="5" nowrap class="producttitle">Credit Card Information (Optional)</td>
                        </tr>
                        <tr>
                            <td colspan="2">
                            <table border="0">
                                <tr>
                                    <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="90" height="4" /></td>
                                    <td align="center">Your credit card information will be saved safely<br>and securely so you only enter it once.</td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td align="center"><img alt="visa mastercard american express discover" src="<%=theme.getimagebaseurl()%>creditcards.gif" /></td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td align="center">We accept Visa®, Visa®, Mastercard®, Discover®<br>and American Express® cards.</td>
                                </tr>
                            </table>
                            </td>
                        </tr>
                        <tr>
                            <td nowrap align="right">Card Number:</td>
                            <td><input name="cardnumber" type="text" size="30" value="<%=creditcard==null||creditcard.getnumber()==null?"":DisplayCreditCard(creditcard.getnumber())%>"/></td>
                        </tr>
                        <tr>
                            <td nowrap align="right">Expiration Date:</td>
                            <td nowrap>
                                <select name="expirationmonth">
                                <option value="0"> Month
                                </option>
                                <option value="01">Jan</option>
                                <option value="02">Feb</option>
                                <option value="03">March</option>
                                <option value="04">April</option>
                                <option value="05">May</option>
                                <option value="06">June</option>
                                <option value="07">July</option>
                                <option value="08">August</option>
                                <option value="09">Sept</option>
                                <option value="10">Oct</option>
                                <option value="11">Nov</option>
                                <option value="12">Dec</option>
                                </select>
                            &nbsp;
                                <select name="expirationyear">
                                    <option value="0">Year</option>
                            <%
        			DateFormat dateFormat = new SimpleDateFormat("yyyy");
                                int year = new Integer(dateFormat.format(Calendar.getInstance().getTime())).intValue();
                                for(int i=0 ; i<20 ; i++)
                                {
                            %>
                                    <option value="<%=new Integer(year + i).toString()%>"><%=new Integer(year + i).toString()%></option>
                            <%
                                }
                            %>
                                </select>
                            </td>
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
    <SCRIPT LANGUAGE="JavaScript">
    <%
        if(creditcard != null && creditcard.getnumber() != null && creditcard.getnumber().length() > 0)
        {
    %>
            document.form1.expirationmonth.value = '<%=creditcard.getexpmonth()%>';
            document.form1.expirationyear.value = '<%=creditcard.getexpyear()%>';
    <%
        }
    %>
    </SCRIPT>
    <script LANGUAGE="JavaScript">
        document.form1.cardnumber.focus();
    </script>
</HTML>

