<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    Company company = null;
    try {
        CompanyBean companyBean = new CompanyBean();
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
                            <td class="producttitle">Company Information</td>
                        </tr>
                    </table>
                    <br>
                    <form name="form1" action="./company_result.jsp" method="post">
                        <input name="id" type="hidden" value="<%=new Integer(company.getid()).toString()%>"/>
                    <TABLE cellSpacing="1" cellPadding="3" width="95%" border="0">
                    <tr>
                        <td noWrap>Company Name:</td>
                        <td><input name="companyname" type="text" size="50" value="<%=company.getcompany()==null?"":company.getcompany()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap valign="top">Description:</td>
                        <td><textarea name="description" rows="10" cols="70"><%=company.getdescription()==null?"":company.getdescription()%></textarea></td>
                    </tr>
                    <tr>
                        <td noWrap>Address 1:</td>
                        <td><input name="address1" type="text" size="60" value="<%=company.getaddress1()==null?"":company.getaddress1()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Address 2:</td>
                        <td><input name="address2" type="text" size="60" value="<%=company.getaddress2()==null?"":company.getaddress2()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Address 3:</td>
                        <td><input name="address3" type="text" size="60" value="<%=company.getaddress3()==null?"":company.getaddress3()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>City:</td>
                        <td><input name="city" type="text" size="30" value="<%=company.getcity()==null?"":company.getcity()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>State:</td>
                        <td><input name="state" type="text" size="3" value="<%=company.getstate()==null?"":company.getstate()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Zip Code:</td>
                        <td><input name="zipcode" type="text" size="8" value="<%=company.getzip()==null?"":company.getzip()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Country Code:</td>
                        <td><input name="countrycode" type="text" size="3" value="<%=company.getcountry()==null?"":company.getcountry()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Primary Telephone:</td>
                        <td><input name="primarytelephone" type="text" size="20" value="<%=company.getphone()==null?"":company.getphone()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Customer Service:</td>
                        <td><input name="customerservice" type="text" size="20" value="<%=company.getcustomerservice()==null?"":company.getcustomerservice()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Fax:</td>
                        <td><input name="fax" type="text" size="20" value="<%=company.getfax()==null?"":company.getfax()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>E-Mail 1:</td>
                        <td><input name="email1" type="text" size="30" value="<%=company.getemail1()==null?"":company.getemail1()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>E-Mail 2:</td>
                        <td><input name="email2" type="text" size="30" value="<%=company.getemail2()==null?"":company.getemail2()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>E-Mail 3:</td>
                        <td><input name="email3" type="text" size="30" value="<%=company.getemail3()==null?"":company.getemail3()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Base URL:</td>
                        <td><input name="baseurl" type="text" size="30" value="<%=company.getbaseurl()==null?"":company.getbaseurl()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Base Secure URL:</td>
                        <td><input name="basesecureurl" type="text" size="30" value="<%=company.getbasesecureurl()==null?"":company.getbasesecureurl()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Visual URL:</td>
                        <td nowrap><input name="visualurl" type="text" size="30" value="<%=company.geturl()==null?"":company.geturl()%>"/>&nbsp;(The URL displayed to the user)</td>
                    </tr>
                    <tr>
                        <td noWrap>Impersonate Password:</td>
                        <td><input name="impersonatepassword" type="text" size="20" value="<%=company.getpw()==null?"":company.getpw()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Use Mod-Rewrite:</td>
                        <td><input name="usemodrewrite" type="checkbox" value="<%=company.getusemodrewrite()==true?"checked":""%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Mod-Rewrite Keyword:</td>
                        <td><input name="modrewritekeyword" type="text" size="15" value="<%=company.getkeyword()==null?"":company.getkeyword()%>"/></td>
                    </tr>
                    </TABLE>
                    <br>
                    <TABLE border="0">
                    <tr>
                        <td><input type="submit" value="Update"></td>
                    </tr>
                    </TABLE>
                    </form>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>
