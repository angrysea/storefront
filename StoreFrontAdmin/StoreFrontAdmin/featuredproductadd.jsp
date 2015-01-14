<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<HTML>
    <%@ include file="commonscript.jsp" %>
    <SCRIPT LANGUAGE="JavaScript">
        function OnSubmitForm()
        {
            if(document.form1.heading.value.length == 0)
            {
                alert("A heading must be entered!");
                document.form1.heading.focus();
                return false;
            }

            if(document.form1.description.value.length == 0)
            {
                alert("A description must be entered!");
                document.form1.description.focus();
                return false;
            }

            var valid = '0123456789';
            if(document.form1.sortorder.value.length == 0 || IsValid(document.form1.sortorder.value, valid) == false)
            {
                alert("An invalid sortorder was entered.  Please enter numbers only.");
                document.form1.sortorder.focus();
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
                    <table id="headingTable" border="0">
                        <tr>
                            <td><img src="./images/spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td class="producttitle">Add Featured Product Heading</td>
                        </tr>
                    </table>
                    <br>
                    <form name="form1" action="./featuredproductadd_result.jsp" method="get" onsubmit="return(OnSubmitForm())">
                    <table cellSpacing="1" cellPadding="3" width="500" border="0">
                        <tr>
                            <td colspan=2>Please enter the featured product heading information before adding actual featured products.  Use the update facility to add actual products to this heading.</td>
                        </tr>
                        <tr>
                            <td><img src="./images/spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td noWrap>Company:</td>
                            <td>
                                <select name="company" onchange="OnChangeCompany()">
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
                            <td noWrap>Heading:</td>
                            <td><input name="heading" type="text" size="40"/></td>
                        </tr>
                        <tr>
                            <td noWrap valign="top">Description:</td>
                            <td><textarea name="description" rows="10" cols="70"></textarea></td>
                        </tr>
                        <tr>
                            <td noWrap>Sort Order:</td>
                            <td><input name="sortorder" type="text" size="5"/></td>
                        </tr>
                        <br>
                    </table>
                    <table border="0">
                        <tr>
                            <td><input type="submit" value="Add"></td>
                        </tr>
                    </table>
                    </form>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>
