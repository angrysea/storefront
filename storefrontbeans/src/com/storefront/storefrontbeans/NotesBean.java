package com.storefront.storefrontbeans;

import java.util.Iterator;
import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class NotesBean
    extends BaseBean {
  final static private String fields[] = {
      "referencenumber", "type", "text", "creationdate" };
  final static private String tablename = "notes";

  public NotesBean() throws ServletException {
    super();
  }

  String getdburl() {
    return "jdbc:mysql:///storefront";
  }

  String[] getfields() {
    return fields;
  }

  String gettableName() {
    return tablename;
  }

  String getindexName() {
    return "id";
  }

  public AddNoteResponse AddNote(AddNoteRequest request) throws ServletException {

    AddNoteResponse response = new AddNoteResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getInsertString());
      Note note = (Note)request.getnote();
      int col=0;
      pstmt.setInt(++col, note.getreferencenumber());
      pstmt.setString(++col, note.gettype());
      pstmt.setString(++col, note.gettext());
      if(note.getcreationdate()==null)
        pstmt.setTimestamp(++col,
                           new Timestamp(new java.util.Date().getTime()));
      else
        pstmt.setTimestamp(++col,
                           new Timestamp(note.getcreationdate().getTime()));
      pstmt.executeUpdate();
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try {
        if (pstmt != null) pstmt.close();
        pstmt = null;
      }
      catch (SQLException sqle) {}
      try {
        if (conn != null) conn.close();
        conn = null;
      }
      catch (SQLException sqle) {}
    }
    return response;
  }

  public UpdateNoteResponse UpdateNote(UpdateNoteRequest request) throws ServletException {

    UpdateNoteResponse response = new UpdateNoteResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getUpdateString());
      Note note = (Note)request.getnote();
      int col=0;
      pstmt.setInt(++col, note.getreferencenumber());
      pstmt.setString(++col, note.gettype());
      pstmt.setString(++col, note.gettext());
      pstmt.setTimestamp(++col,
                         new Timestamp(note.getcreationdate().getTime()));
      pstmt.setInt(++col, note.getid());
      pstmt.executeUpdate();
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try {
        if (pstmt != null) pstmt.close();
        pstmt = null;
      }
      catch (SQLException sqle) {}
      try {
        if (conn != null) conn.close();
        conn = null;
      }
      catch (SQLException sqle) {}
    }
    return response;
  }

  public DeleteNoteResponse DeleteNote(DeleteNoteRequest request) throws ServletException {

    DeleteNoteResponse response = new DeleteNoteResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("delete from note where id = ?");
      pstmt.setInt(1, request.getid());
      pstmt.executeUpdate();
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try {
        if (pstmt != null) pstmt.close();
        pstmt = null;
      }
      catch (SQLException sqle) {}
      try {
        if (conn != null) conn.close();
        conn = null;
      }
      catch (SQLException sqle) {}
    }
    return response;
  }

  public GetNoteResponse GetNote(GetNoteRequest request) throws
      ServletException {

    Connection conn = null;
    GetNoteResponse response = new GetNoteResponse();
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getSelectWithIDByIDString());
      pstmt.setInt(1, request.getid());
      rs = pstmt.executeQuery();

      if(rs.next()) {
        int col=0;
        Note note = new Note();
        note.setid(rs.getInt(col++));
        note.setreferencenumber(rs.getInt(col++));
        note.settype(rs.getString(col++));
        note.settext(rs.getString(col++));
        note.setcreationdate(new java.util.Date(rs.getTimestamp(++col).getTime()));
        response.setnote(note);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try {
        if (rs != null) rs.close();
        rs = null;
      }
      catch (SQLException sqle) {}
      try {
        if (pstmt != null) pstmt.close();
        pstmt = null;
      }
      catch (SQLException sqle) {}
      try {
        if (conn != null) conn.close();
        conn = null;
      }
      catch (SQLException sqle) {}
    }
    return response;
  }

  public GetNotesResponse GetNotes(GetNotesRequest request) throws
      ServletException {

    Connection conn = null;
    GetNotesResponse response = new GetNotesResponse();
    try {
      conn = datasource.getConnection();
        String query = "select id, referencenumber, type, text, creationdate from notes ";
      if(request.getreferencenumber()!=null && request.gettype()!=null) {
        query += " where referencenumber = " +
            request.getreferencenumber() +
            " and type = '" + request.gettype() +"'";
      }
      pstmt = conn.prepareStatement(query);
      rs = pstmt.executeQuery();

      while(rs.next()) {
        int col=0;
        Note note = new Note();
        note.setid(rs.getInt(++col));
        note.setreferencenumber(rs.getInt(++col));
        note.settype(rs.getString(++col));
        note.settext(rs.getString(++col));
        note.setcreationdate(new java.util.Date(rs.getTimestamp(++col).getTime()));
        response.setnote(note);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try {
        if (rs != null) rs.close();
        rs = null;
      }
      catch (SQLException sqle) {}
      try {
        if (pstmt != null) pstmt.close();
        pstmt = null;
      }
      catch (SQLException sqle) {}
      try {
        if (conn != null) conn.close();
        conn = null;
      }
      catch (SQLException sqle) {}
    }
    return response;
  }
}




