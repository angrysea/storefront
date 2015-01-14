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
    int noteCount = 0;
    try {
        LinksBean linksBean = new LinksBean();

        GetLinkRequest getLinkRequest = new GetLinkRequest();
        getLinkRequest.setid(Integer.parseInt(request.getParameter("id")));
        getLinkResponse = linksBean.GetLink(getLinkRequest);
        link = getLinkResponse.getlink();

        NotesBean notesBean = new NotesBean();
        GetNotesRequest getNotesRequest = new GetNotesRequest();
        getNotesRequest.setreferencenumber(new Integer(link.getid()).toString());
        getNotesRequest.settype("link");
        GetNotesResponse getNotesResponse = notesBean.GetNotes(getNotesRequest);
        Iterator itNotes = getNotesResponse.getnoteIterator();
        for(noteCount=0 ; itNotes.hasNext() ; noteCount++)
            itNotes.next();
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>


<HTML>
    <SCRIPT LANGUAGE="JavaScript">
        function OnClickNotes()
        {
            mywindow = window.open("./notes.jsp?referencenumber=<%=new Integer(link.getid()).toString()%>&type=link", "Notes", "location=0,status=0,toolbar=0,scrollbars=1,menubar=0,directories=0,resizable=0,width=600,height=500");
        }

        function OnClickUpdate()
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

        function OnClickDelete()
        {
            var answer = confirm("Are you sure you want to delete this link?");
            if(answer)
            {
                window.location="./linkdelete_result.jsp?id=<%=request.getParameter("id")%>";
            }
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
                            <td class="producttitle">Update Link</td>
                        </tr>
                    </table>
                    <br>
                    <form name="form1" action="./linkupdate_result.jsp" method="post">
                    <TABLE cellSpacing="1" cellPadding="3" width="95%" border="0">
                    <tr>
                        <td noWrap>ID:</td>
                        <td><input name="id" type="text" size="5" readonly value="<%=Integer.toString(link.getid())%>" /></td>
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
                                        if(company.getid() == link.getcompanyid())
                                        {
                                %>
                                            <option value="<%=new Integer(company.getid()).toString()%>" selected><%=company.getcompany()%></option>
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
                    <tr>
                        <td noWrap>URL:</td>
                        <td>
                            <table border="0">
                                <tr>
                                    <td><input name="url" type="text" size="40" value="<%=link.geturl()%>"/></td>
                                    <td><a href="<%=link.geturl()%>" target="_blank">go to link</a></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td noWrap>Heading:</td>
                        <td><input name="header" type="text" size="40" value="<%=link.getheader()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap valign="top">Description:</td>
                        <td><textarea name="description" rows="10" cols="70"><%=link.getdescription()%></textarea></td>
                    </tr>
                    <tr>
                        <td noWrap>E-Mail Address:</td>
                        <td><input name="emailaddress" type="text" size="40" value="<%=link.getemail()==null?"":link.getemail()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>E-Mails Sent:</td>
                        <td><input name="emailssent" type="text" size="5" readonly value="<%=Integer.toString(link.getemailssent())%>"/></td>
                    </tr>
                    <tr>
                        <td>Linked Back:</td>
                        <td><input type="checkbox" name="linkback" <%=link.getlinkback()==true?"checked":""%>></td>
                    </tr>
                    <tr>
                        <td>Last E-Mail Sent:</td>
                        <td><input type="text" size="20" readonly value="<%=link.getemailssentdate()==null?"":dateFormat.format(link.getemailssentdate())%>"/></td>
                    </tr>
                    </TABLE>
                    <table border="0">
                        <tr>
                            <td><input type="button" onclick="OnClickNotes()" value="Notes (<%=new Integer(noteCount).toString()%>)" /></td>
                        </tr>
                    </table>
                    <br>
                    <TABLE border="0">
                    <tr>
                        <td><input type="button" value="Update" onclick="OnClickUpdate()"></td>
                        <td><input type="button" value="Delete" onclick="OnClickDelete()"></td>
                    </tr>
                    </TABLE>
                    </form>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>
