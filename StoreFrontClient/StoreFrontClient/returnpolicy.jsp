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
    </SCRIPT>
    <HEAD>
        <%@ include file="titlemetadata.jsp" %>
        <LINK href="<%=company.getbaseurl()%>storefront.css" rel="STYLESHEET"></LINK>
    </HEAD>
    <BODY leftMargin="0" topMargin="0" onload="" marginwidth="0" marginheight="0">
        <%@ include file="toppane.jsp" %>
        <table cellSpacing="0" cellPadding="0" border="0">
            <tr>
                <%@ include file="leftpane.jsp" %>
                <td vAlign="top" width="20">
                    <IMG src="<%=theme.getimagebaseurl()%>spacer.gif" border="0">
                </td>
                <td vAlign="top">
                    <table id="headingTable" border="0">
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="5" height="5"></td>
                        </tr>
                    </table>
                    <br>
                    <table cellSpacing="1" cellPadding="1" width="550" border="0">
                        <tr bgcolor="<%=theme.getcolor2()%>">
                            <td class="producttitle" noWrap>Our Return Policy</td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td>
We strive to provide our customers with the highest level of service possible. From first visit to order delivery, we want you to be completely satisfied with your experience.
Our friendly and knowledgeable sales staff is available to help you find the product that best fits your needs.<br>
<br>
You can shop with confidence as every product we ship is covered by a manufacturer's warranty, unless otherwise noted.  If the item is damaged or misrepresented, it is either replaced or you get a full refund.<br />
<br>
<b>Certain conditions and/or restrictions apply:</b><br>
<br>
For undamaged goods, you pay for return shipping and pay a restocking charge of 15% of the purchase price. The item must be returned within 30 days in the original packaging.<br /><br />
Refunds for authorized returns will be issued within 24 - 72 hours of receipt of the merchandise in its original condition. Requests for RMAs should be made to <a href="mailto:<%=company.getemail2()%>"><%=company.getemail2()%></a>. When you receive the RMA you will be given instructions where to return the merchandise.  Thank you.
			    </td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <%@ include file="bottompane.jsp" %>
                    </table>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>

