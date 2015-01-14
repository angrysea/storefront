<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    GetLinkResponse getLinkResponse = new GetLinkResponse();
    Link link = null;
    try {
        LinksBean linksBean = new LinksBean();

        GetLinkRequest getLinkRequest = new GetLinkRequest();
        getLinkRequest.setid(Integer.parseInt(request.getParameter("id")));
        getLinkResponse = linksBean.GetLink(getLinkRequest);
        link = getLinkResponse.getlink();
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>


<HTML>
    <SCRIPT LANGUAGE="JavaScript">
        function OnClickSendEMail()
        {
        <%
            if(link.getemail() != null && link.getemail().length() > 0)
            {
        %>
                window.location="./linkemail_result.jsp?id=<%=Integer.toString(link.getid())%>&emailaddress=<%=link.getemail()%>";
        <%
            }
            else
            {
        %>
                alert("An e-mail cannot be generated! No email address is available for this link")
        <%
            }
        %>
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
                            <td class="producttitle">Send E-Mail to Link</td>
                        </tr>
                    </table>
                    <br>
                    <form name="form1" action="./linkemail_result.jsp" method="post">
                    <TABLE cellSpacing="1" cellPadding="3" width="95%" border="0">
                    <tr>
                        <td noWrap>ID:</td>
                        <td><%=Integer.toString(link.getid())%></td>
                    </tr>
                    <tr>
                        <td noWrap>Company:</td>
                        <td>
                                <%
                                    CompanyBean companyBean = new CompanyBean();
                                    GetCompaniesResponse getCompaniesResponse = companyBean.GetCompanies(new GetCompaniesRequest());
                                    Iterator itCompanies = getCompaniesResponse.getcompaniesIterator();
                                    while(itCompanies.hasNext())
                                    {
                                        Company company = (Company)itCompanies.next();
                                        if(company.getid() == link.getcompanyid())
                                        {
                                %>
                                            <%=company.getcompany()%>
                                <%
                                            break;
                                        }
                                    }
                                %>
                        </td>
                    </tr>
                    <tr>
                        <td noWrap>URL:</td>
                        <td>
                            <table border="0">
                                <tr>
                                    <td><%=link.geturl()%></td>
                                    <td>&nbsp;</td>
                                    <td><a href="<%=link.geturl()%>" target="_blank">go to link</a></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td noWrap>Heading:</td>
                        <td><%=link.getheader()%></td>
                    </tr>
                    <tr>
                        <td noWrap valign="top">Description:</td>
                        <td><textarea name="description" rows="10" cols="70" readonly="readonly"><%=link.getdescription()%></textarea></td>
                    </tr>
                    <tr>
                        <td noWrap>E-Mail Address:</td>
                        <td><%=link.getemail()==null?"":link.getemail()%></td>
                    </tr>
                    <tr>
                        <td noWrap>E-Mails Sent:</td>
                        <td><%=Integer.toString(link.getemailssent())%></td>
                    </tr>
                    <tr>
                        <td>Linked Back:</td>
                        <td><%=link.getlinkback()==true?"yes":"no"%></td>
                    </tr>
                    <tr>
                        <td>Last E-Mail Sent:</td>
                        <td><%=link.getemailssentdate()==null?"":dateFormat.format(link.getemailssentdate())%></td>
                    </tr>
                    </TABLE>
                    <br>
                    <TABLE border="0">
                    <tr>
                        <td><input type="button" value="Send E-Mail" onclick="OnClickSendEMail()"></td>
                    </tr>
                    </TABLE>
                    </form>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>
