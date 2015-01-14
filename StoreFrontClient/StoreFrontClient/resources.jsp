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
    GetLinksResponse getLinksResponse = null;
    int nextPage = 0;
    try {
        CompanyBean companyBean = new CompanyBean();
        GetCompanyResponse getCompanyResponse = companyBean.GetCompany(request, new GetCompanyRequest());
        company = getCompanyResponse.getcompany();

        GetThemeRequest getThemeRequest = new GetThemeRequest();
        getThemeRequest.setname("corporate");
        GetThemeResponse getThemeResponse = companyBean.GetTheme(request, getThemeRequest);
        theme = getThemeResponse.gettheme();

        int currentPage = 1;
        if(request.getParameter("page") != null)
            currentPage = Integer.parseInt(request.getParameter("page"));

        LinksBean linksBean = new LinksBean();
        GetLinksRequest getLinksRequest = new GetLinksRequest();
        getLinksRequest.setorderby("header");
        getLinksRequest.setpage(currentPage);
        getLinksRequest.setmax(50);
        getLinksResponse = linksBean.GetLinks(getLinksRequest);

        nextPage = currentPage + 1;
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
                            <td class="producttitle" noWrap>Links</td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td nowrap><b>Link to <%=company.getcompany()%></b></td>
                        </tr>
                        <tr>
                            <td>Link popularity is fast becoming one of the highest weighted criteria used in ranking your site in the search engines. The more related sites linking to yours the higher your sites ranking climbs in the search engine results. We like to link with sites that complement ours or can offer our clients a valid service. We will not exchange links with any website that is unduly offensive or fraudulently boastful in its claims.</td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td nowrap><b>How do I swap links with <%=company.getcompany()%>?</b></td>
                        </tr>
                        <tr>
                            <td>Exchanging links between our two sites is easy. Send an email to <a href="mailto:<%=company.getemail1()%>"><%=company.getemail1()%></a> with a one or two sentence description of your site and the exact URL. We'll review your site as soon as possible, validate the reciprocal link on your site and if we feel that your site provide good complimentary content, we will reciprocate the link.<br><br>
                            To add <%=company.getcompany()%> link to your website, simply use the following html code:<br><br>
                            <textarea rows="8" cols="50" disabled><p><a href="<%=company.geturl()%>" title="<%=company.getkeyword1()%>"><%=company.getkeyword1()%></a> - <%=company.getlinkexchangetext()%></p></textarea>
                            <br><br>
                            Thank you kindly, <br><br>
                            <%=company.getcompany()%> web site manager<br><br><br><br>
                            </td>
                        </tr>
                    <%
                        Iterator itLinks = getLinksResponse.getlinkIterator();
                        while(itLinks.hasNext())
                        {
                            Link link = (Link)itLinks.next();
                    %>
                        <tr>
                            <td><a href="<%=link.geturl()%>" target="_blank"><%=link.getheader()%></a></td>
                        </tr>
                        <tr>
                            <td><%=link.getdescription()%></td>
                        </tr>
                        <tr>
                            <td><br></td>
                        </tr>
                    <%
                        }
                    %>
                        <tr>
                            <td><br></td>
                        </tr>
                    <%
                        if(getLinksResponse.getlast() == false)
                        {
                    %>
                        <tr>
                            <td><a href="<%=StoreFrontUrls.getresourceurl(request, company, nextPage)%>">More >></a></td>
                        </tr>
                    <%
                        }
                    %>
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

