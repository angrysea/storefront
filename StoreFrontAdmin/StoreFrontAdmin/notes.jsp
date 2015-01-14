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
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    try {
        if(request.getParameter("notetext") != null)
        {
            NotesBean notesBean = new NotesBean();
            AddNoteRequest addNoteRequest = new AddNoteRequest();
            Note note = new Note();
            note.setreferencenumber(new Integer(request.getParameter("referencenumber")).intValue());
            note.settype(request.getParameter("type"));
            note.settext(request.getParameter("notetext"));
            addNoteRequest.setnote(note);
            notesBean.AddNote(addNoteRequest);
        }
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<HTML>
    <SCRIPT LANGUAGE="JavaScript">
        function OnSubmitForm()
        {
            if(document.form1.notetext.value.length == 0)
            {
                alert("Invalid note text.");
                document.form1.notetext.focus();
                return false;
            }

            return true;
        }
    </SCRIPT>
    <HEAD>
        <LINK href="./storefront.css" rel="STYLESHEET"></LINK>
    </HEAD>
    <BODY leftMargin="0" topMargin="0" onload="" marginwidth="0" marginheight="0">
        <table cellSpacing="0" cellPadding="0" border="0">
            <tr>
                <td vAlign="top" width="20">
                    <IMG src="./images/spacer.gif" border="0">
                </td>
                <td vAlign="top">
                    <table id="headingTable" height="100%" border="0">
                        <tr>
                            <td><img src="./images/spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td class="producttitle">Notes</td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                    </table>

                    <table border="0">
                        <tr>
                            <td nowrap align="left"><b>Reference Number:</b></td>
                            <td align="left"><%=request.getParameter("referencenumber")%></td>
                        </tr>
                        <tr>
                            <td nowrap align="left"><b>Type:</b></td>
                            <td align="left"><%=request.getParameter("type")%></td>
                        </tr>
                    </table>
                    <br>
                    <table cellSpacing="1" cellPadding="2" border="0" width="490">
                    <tr>
                        <td nowrap"left"><b>Date</b></td>
                        <td nowrap><b>Note</b></td>
                    </tr>
                    <tr>
                        <td colspan="4">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td bgcolor="<%=theme.getcolor1()%>"><img src="./images/spacer.gif" width="490" height="1"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
            <%
                NotesBean notesBean = new NotesBean();
                GetNotesRequest getNotesRequest = new GetNotesRequest();
                getNotesRequest.setreferencenumber(request.getParameter("referencenumber"));
                getNotesRequest.settype(request.getParameter("type"));
                GetNotesResponse getNotesResponse = notesBean.GetNotes(getNotesRequest);
                Iterator itNotes = getNotesResponse.getnoteIterator();
                while(itNotes.hasNext())
                {
                    Note note = (Note)itNotes.next();
            %>
                    <tr>
                        <td nowrap valign="top"><%=dateFormat.format(note.getcreationdate())%></td>
                        <td><%=note.gettext()%></td>
                    </tr>
            <%
                }
            %>
                    </table>

                    <form action="./notes.jsp" name="form1" method="GET" onsubmit="return(OnSubmitForm())">
                    <table border="0">
                        <input type="hidden" name="referencenumber" value="<%=request.getParameter("referencenumber")%>"/>
                        <input type="hidden" name="type" value="<%=request.getParameter("type")%>"/>
                        <tr>
                            <td nowrap>Note:</td>
                        </tr>
                        <tr>
                            <td><textarea name="notetext" cols="100" rows="4"></textarea></td>
                        </tr>
                        <tr>
                            <td><input type="submit" value="Add Note"/></td>
                        </tr>
                    </table>
                    </form>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>
