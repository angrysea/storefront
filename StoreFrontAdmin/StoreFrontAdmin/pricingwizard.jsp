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

<%
    GetManufacturersResponse getManufacturersResponse = new GetManufacturersResponse();
    try {
        ListsBean lists = new ListsBean();

	GetManufacturersRequest getManufacturersRequest = new GetManufacturersRequest();
        getManufacturersResponse = lists.GetManufacturers(getManufacturersRequest);
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
            var valid = '0123456789';
            if(document.form1.markup.value.length == 0 || IsValid(document.form1.markup.value, valid) == false)
            {
                alert("An invalid markup percentage was entered.  Please enter numbers only.");
                document.form1.markup.focus();
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
                <%@ include file="leftpane.jsp" %>
                <td vAlign="top" width="20">
                    <IMG src="./images/spacer.gif" border="0">
                </td>
                <td vAlign="top">
                    <table border="0" width="600">
                        <tr>
                            <td><img src="./images/spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td class="producttitle">Pricing Wizard</td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                        <tr>
                            <td>The pricing wizard will iterate through the products from the manufacturers selected and set the prices to the markup specified in each manufacturer record.</td>
                        </tr>
                        <tr>
                            <td><font color="red">WARNING: Use this feature with caution.  Prices are affected immediately.</font></td>
                        </tr>
                    </table>
                    <form name="form1" action="./pricingwizard_result.jsp" method="GET" onsubmit="return(OnSubmitForm())">
                    <TABLE cellSpacing="1" cellPadding="1" width="300" border="0">
                        <tr>
                            <td align="right" nowrap>Select Manufacturer:</td>
                            <td>
                                <select name="manufacturer">
                                    <option value="all">All</option>
                                <%
                                    Iterator itManufacturers = getManufacturersResponse.getmanufacturersIterator();
                                    while(itManufacturers.hasNext())
                                    {
                                        Manufacturer manufacturer = (Manufacturer)itManufacturers.next();
                                %>
                                        <option value="<%=new Integer(manufacturer.getid()).toString()%>"><%=manufacturer.getname()%></option>
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
                            <td align="right"><input type="submit" value="Run the Pricing Wizard"/></td>
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
