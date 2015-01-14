package com.storefront.storefrontbeans;

import java.util.*;
import javax.servlet.*;
import java.sql.*;

import com.storefront.storefrontrepository.*;
import com.adaptinet.transmitter.*;

public class EMailRecordBean
    extends BaseBean {
  final static private String fields[] = {
      "referenceno", "type", "sentdate", "recipientid", "recipient", "subject", "body"};
  final static private String tablename = "emailrecord";
  final static private String bydate =
      "DATE_FORMAT(sentdate,'%Y %m %d') between " +
      "DATE_FORMAT(?,'%Y %m %d') and  DATE_FORMAT(?,'%Y %m %d')";

  public EMailRecordBean() throws ServletException {
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

  public AddEMailRecordResponse AddEMailRecord(AddEMailRecordRequest request) throws
      ServletException {
    Connection conn = null;
    AddEMailRecordResponse response = new AddEMailRecordResponse();
    try {
      conn = datasource.getConnection();
      addEMailRecord(conn, request.getemailrecord());
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try {
        if (conn != null) conn.close();
        conn = null;
      }
      catch (SQLException sqle) {}
    }
    return response;
  }

  void addEMailRecord(Connection conn, EMailRecord emailrecord) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement(getInsertString());
      pstmt.setInt(1, emailrecord.getreferenceno());
      pstmt.setString(2, emailrecord.gettype());
      pstmt.setTimestamp(3, new Timestamp(new java.util.Date().getTime()));
      pstmt.setInt(4, emailrecord.getrecipientid());
      pstmt.setString(5, emailrecord.getrecipient());
      pstmt.setString(6, emailrecord.getsubject());
      pstmt.setString(7, emailrecord.getbody());
      if ( (pstmt.executeUpdate()) > 0) {
        emailrecord.setid(getLastInsertID(conn));
      }
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
    }
  }

  public UpdateEMailRecordResponse UpdateEMailRecord(UpdateEMailRecordRequest request) throws
      ServletException {
    Connection conn = null;
    UpdateEMailRecordResponse response = new UpdateEMailRecordResponse();
    try {
      conn = datasource.getConnection();
      EMailRecord emailrecord = (EMailRecord) request.getemailrecord();
      pstmt = conn.prepareStatement(getUpdateString());
      pstmt.setInt(1, emailrecord.getreferenceno());
      pstmt.setString(2, emailrecord.gettype());
      pstmt.setTimestamp(3, new Timestamp(emailrecord.getsentdate().getTime()));
      pstmt.setInt(4, emailrecord.getrecipientid());
      pstmt.setString(5, emailrecord.getrecipient());
      pstmt.setString(6, emailrecord.getsubject());
      pstmt.setString(7, emailrecord.getbody());
      pstmt.setInt(8, emailrecord.getid());
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

  public DeleteEMailRecordResponse DeleteEMailRecord(DeleteEMailRecordRequest request) throws
      ServletException {
    DeleteEMailRecordResponse response = new DeleteEMailRecordResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("delete from emailrecord where id=?");
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

  public GetEMailRecordResponse GetEMailRecord(GetEMailRecordRequest request) throws
      ServletException {

    GetEMailRecordResponse response = new GetEMailRecordResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getSelectWithIDByIDString());
      pstmt.setInt(1, request.getid());
      rs = pstmt.executeQuery();
      if (rs.next()) {
        EMailRecord emailrecord = new EMailRecord();
        emailrecord.setid(rs.getInt(1));
        emailrecord.setreferenceno(rs.getInt(2));
        emailrecord.settype(rs.getString(3));
        emailrecord.setsentdate(new java.util.Date(rs.getTimestamp(4).getTime()));
        emailrecord.setrecipientid(rs.getInt(5));
        emailrecord.setrecipient(rs.getString(6));
        emailrecord.setsubject(rs.getString(7));
        emailrecord.setbody(rs.getString(8));
        response.setemailrecord(emailrecord);
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
    }
    return response;
  }

  public GetEMailRecordsResponse GetEMailRecords(GetEMailRecordsRequest request) throws
      ServletException {
    GetEMailRecordsResponse response = new GetEMailRecordsResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();

      String query = getSelectWithIDString();
      String where = null;
      String orderby = " order by ";

      if (request.getrecipientid() > 0)
        where += "recipientid = ? ";

      if (request.getreferenceid() > 0)
        where += "referenceid = ? ";

      if (request.getstartdate() != null) {
        where = (where==null) ? (where = " where " + bydate) : (where += " and " + bydate);
        orderby += " sentdate ";
      }
      else
        orderby += " recipientid ";

      if (where != null)
        query += where;

      if(orderby!=null)
        query += orderby;

      pstmt = conn.prepareStatement(query);
      int col = 0;
      if (request.getreferenceid() > 0)
        pstmt.setInt(++col, request.getreferenceid());

      if (request.getrecipientid() > 0)
        pstmt.setInt(++col, request.getrecipientid());

      if (request.getstartdate() != null) {
        pstmt.setTimestamp(++col, new Timestamp(request.getstartdate().getTime()));
        pstmt.setTimestamp(++col, new Timestamp(request.getenddate().getTime()));
      }

      rs = pstmt.executeQuery();
      while (rs.next()) {
        EMailRecord emailrecord = new EMailRecord();
        emailrecord.setid(rs.getInt(1));
        emailrecord.setreferenceno(rs.getInt(2));
        emailrecord.settype(rs.getString(3));
        emailrecord.setsentdate(new java.util.Date(rs.getTimestamp(4).getTime()));
        emailrecord.setrecipientid(rs.getInt(5));
        emailrecord.setrecipient(rs.getString(6));
        emailrecord.setsubject(rs.getString(7));
        emailrecord.setbody(rs.getString(8));
        response.setemailrecords(emailrecord);
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



