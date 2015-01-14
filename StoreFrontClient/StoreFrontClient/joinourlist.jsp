<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    Company company = null;
    Theme theme = null;
    AddEMailListResponse addEMailListResponse = null;
    try {
        CompanyBean companyBean = new CompanyBean();
        GetCompanyResponse getCompanyResponse = companyBean.GetCompany(request, new GetCompanyRequest());
        company = getCompanyResponse.getcompany();

        GetThemeRequest getThemeRequest = new GetThemeRequest();
        getThemeRequest.setname("corporate");
        GetThemeResponse getThemeResponse = companyBean.GetTheme(request, getThemeRequest);
        theme = getThemeResponse.gettheme();

        EMailListBean emailListBean = new EMailListBean();
        AddEMailListRequest addEMailListRequest = new AddEMailListRequest();
        EMailList eMailList = new EMailList();
        eMailList.setemail(request.getParameter("emailaddress"));
        addEMailListRequest.setemaillist(eMailList);
        addEMailListResponse = emailListBean.AddEMailList(addEMailListRequest);

        if(addEMailListResponse != null && addEMailListResponse.getid() > 0)
        {
            emailListBean.SendMail(eMailList, "joinourlist");
        }
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
        <table summary="<%=request.getAttribute("keyword1")%>" cellSpacing="0" cellPadding="0" border="0">
            <tr>
                <%@ include file="leftpane.jsp" %>
                <td vAlign="top" width="20">
                    <IMG src="<%=theme.getimagebaseurl()%>spacer.gif" border="0">
                </td>
                <td vAlign="top">
                    <table summary="<%=request.getAttribute("keyword2")%>" id="headingTable" border="0">
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="5" height="5"></td>
                        </tr>
                    </table>
                    <br>
                    <table summary="<%=request.getAttribute("keyword1")%>" cellSpacing="0" cellPadding="0" width="550" border="0">
                        <tr bgcolor="<%=theme.getcolor2()%>">
                            <td class="producttitle" noWrap>Thank You for Joining Our List</td>
                        </tr>
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="5" height="5"></td>
                        </tr>
            <%
                if(addEMailListResponse != null && addEMailListResponse.getid() > 0)
                {
            %>
                        <tr>
                            <td><b><%=request.getParameter("emailaddress")%></b> has been subscribed to the <%=company.getcompany()%> e-mail list. Thank you.</td>
                        </tr>
            <%
                }
                else
                {
            %>
                        <tr>
                            <td><b><%=request.getParameter("emailaddress")%></b> is already on our list. Thank you.</td>
                        </tr>
            <%
                }
            %>
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td>We respect your privacy. <%=company.getcompany()%> does not share e-mail addresses with third parties. Instructions for unsubscribing come with every e-mail.</td>
                        </tr>
                        <tr>
                            <td><br></td>
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

