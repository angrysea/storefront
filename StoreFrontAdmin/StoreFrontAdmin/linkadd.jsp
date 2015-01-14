<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    try {
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>


<HTML>
    <SCRIPT LANGUAGE="JavaScript">
        function OnClickAdd()
        {
            if(document.form1.company.value == 0)
            {
                alert("Please select a company!");
                document.form1.company.focus();
                return;
            }

            if(document.form1.url.value.length == 0)
            {
                alert("Invalid URL!");
                document.form1.url.focus();
                return;
            }

            if(document.form1.header.value.length == 0)
            {
                alert("Invalid Heading!");
                document.form1.header.focus();
                return;
            }

            if(document.form1.description.value.length == 0)
            {
                alert("Invalid Description!");
                document.form1.description.focus();
                return;
            }

            document.form1.submit();
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
                            <td class="producttitle">Add Link</td>
                        </tr>
                    </table>
                    <br>
                    <form name="form1" action="./linkadd_result.jsp" method="post">
                    <TABLE cellSpacing="1" cellPadding="3" width="95%" border="0">
                    <tr>
                        <td noWrap>ID:</td>
                        <td><input name="id" type="text" size="5" readonly/></td>
                    </tr>
                    <tr>
                        <td noWrap>Company:</td>
                        <td>
                            <select name="company" onchange="OnChangeCompany()">
                                        <option value="0">< select ></option>
                                <%
                                    CompanyBean companyBean = new CompanyBean();
                                    GetCompaniesResponse getCompaniesResponse = companyBean.GetCompanies(new GetCompaniesRequest());
                                    Iterator itCompanies = getCompaniesResponse.getcompaniesIterator();
                                    while(itCompanies.hasNext())
                                    {
                                        Company company = (Company)itCompanies.next();
                                %>
                                        <option value="<%=new Integer(company.getid()).toString()%>"><%=company.getcompany()%></option>
                                <%
                                    }
                                %>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td noWrap>URL:</td>
                        <td><input name="url" type="text" size="40"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Heading:</td>
                        <td><input name="header" type="text" size="40"/></td>
                    </tr>
                    <tr>
                        <td noWrap valign="top">Description:</td>
                        <td><textarea name="description" rows="10" cols="70"></textarea></td>
                    </tr>
                    <tr>
                        <td noWrap>E-Mail Address:</td>
                        <td><input name="emailaddress" type="text" size="20"/></td>
                    </tr>
                    <tr>
                        <td noWrap>E-Mails Sent:</td>
                        <td><input name="emailssent" type="text" size="5" readonly/></td>
                    </tr>
                    <tr>
                        <td>Linked Back:</td>
                        <td><input type="checkbox" name="linkback"></td>
                    </tr>
                    </TABLE>
                    <br>
                    <TABLE border="0">
                    <tr>
                        <td><input type="button" value="Add" onclick="OnClickAdd()"></td>
                    </tr>
                    </TABLE>
                    </form>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>
