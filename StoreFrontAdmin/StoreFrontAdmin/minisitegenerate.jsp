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
            if(document.form1.minisite.value == -1)
            {
                alert("A minisite must be selected!");
                document.form1.minisite.focus();
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
                            <td class="producttitle">Minisite Generation</td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                        <tr>
                            <td>Select a minisite to generate below.  HTML will be generated for the site and uploaded to the site via FTP.</td>
                        </tr>
                        <tr>
                            <td><font color="red">WARNING: Use this feature with caution.  The minisite will be affected immediately.</font></td>
                        </tr>
                    </table>
                    <form name="form1" action="./minisitegenerate_result.jsp" method="GET" onsubmit="return(OnSubmitForm())">
                    <TABLE cellSpacing="1" cellPadding="1" width="300" border="0">
                        <tr>
                            <td noWrap>Minisite:</td>
                            <td>
                                <select name="minisite">
                                    <option value="-1">< select ></option>
                                    <%
                                        CompanyBean companyBean = new CompanyBean();
                                        GetCompaniesResponse getCompaniesResponse = companyBean.GetCompanies(new GetCompaniesRequest());
                                        Iterator itCompanies = getCompaniesResponse.getcompaniesIterator();
                                        while(itCompanies.hasNext())
                                        {
                                            Company company = (Company)itCompanies.next();
                                            if(company.getid() == 1)
                                                continue;
                                    %>
                                            <option value="<%=new Integer(company.getid()).toString()%>"><%=company.getcompany()%></option>
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
                            <td colspan="2"><input type="submit" value="Generate the Minisite"/></td>
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
