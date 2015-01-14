<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
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
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    DecimalFormat percentFormat = new DecimalFormat("##0.0");

    GetLinksResponse getLinksResponse = null;
    try {
        LinksBean linksBean = new LinksBean();
        GetLinksRequest getLinksRequest = new GetLinksRequest();
        if(request.getParameter("company") != null)
            getLinksRequest.setcompanyid(request.getParameter("company"));
        getLinksResponse = linksBean.GetLinks(getLinksRequest);
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>


<HTML>
    <SCRIPT LANGUAGE="JavaScript">
        function OnChangeCompany()
        {
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
                    <table id="headingTable" height="100%" border="0">
                        <tr>
                            <td><img src="./images/spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td class="producttitle">Links</td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                    </table>
                    <form name="form1" action="./links.jsp" method="GET">
                    <table>
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
                                            if(request.getParameter("company") != null && company.getid() == new Integer(request.getParameter("company")).intValue())
                                            {
                                    %>
                                                <option value="<%=new Integer(company.getid()).toString()%>" selected="selected"><%=company.getcompany()%></option>
                                    <%
                                            }
                                            else
                                            {
                                    %>
                                                <option value="<%=new Integer(company.getid()).toString()%>"><%=company.getcompany()%></option>
                                    <%
                                            }
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                    </table>
                    </form>
                    <TABLE cellSpacing="1" cellPadding="3" width="700" border="0">
                        <TR>
                            <TD class="columnheader" noWrap align="left"><b>ID</b></TD>
                            <TD class="columnheader" noWrap align="left"><b>URL</b></TD>
                            <TD class="columnheader" noWrap align="center"><b>Link Back</b></TD>
                            <TD class="columnheader" noWrap align="right"><b>E-Mail's<br>Sent</b></TD>
                            <TD class="columnheader" noWrap align="left"><b>Last E-Mail<br>Date</b></TD>
                            <TD></TD>
                        </TR>
                        <tr>
                            <td colspan="10">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td bgcolor="<%=theme.getcolor1()%>"><img src="./spacer.gif" width="700" height="1" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <%
                            int numberOfLinks = 0;
                            Iterator it = getLinksResponse.getlinkIterator();
                            Link link = null;
                            while(it.hasNext())
                            {
                                link = (Link)it.next();
                                if(link.getlinkback() == true)
                                    numberOfLinks++;
                        %>
                                <TR>
                                    <TD noWrap align="left"><a href="./linkupdate.jsp?id=<%=link.getid()%>"><%=link.getid()%></a></TD>
                                    <TD noWrap align="left"><a href="<%=link.geturl()%>" target="_blank"><%=link.geturl()==null?"":link.geturl()%></a></TD>
                                    <TD noWrap align="center"><%=link.getlinkback()==true?"yes":"no"%></TD>
                                    <TD noWrap align="right"><%=Integer.toString(link.getemailssent())%></TD>
                                    <TD noWrap align="left"><%=link.getemailssentdate()==null?"":dateFormat.format(link.getemailssentdate())%></TD>
                                    <TD noWrap align="left">
                                <%
                                    if(link.getemail() != null && link.getemail().length() > 0 && link.getlinkback() == false)
                                    {
                                %>
                                        <a href="./linkemail_result.jsp?id=<%=Integer.toString(link.getid())%>&emailaddress=<%=link.getemail()%>">send e-mail</a>
                                <%
                                    }
                                %>
                                    </TD>
                                </TR>
                        <%
                            }
                        %>
                        <tr>
                            <td colspan="10">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td bgcolor="<%=theme.getcolor1()%>"><img src="./spacer.gif" width="700" height="1" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <TR>
                            <TD colspan="2"></TD>
                            <TD noWrap align="center"><%=Integer.toString(numberOfLinks)%></TD>
                        </TR>
                    </TABLE>
                    <br>
                    <table border="0">
                        <tr>
                            <td class="smallprompt"><A href="./linkadd.jsp">Add a new Link</A></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>
