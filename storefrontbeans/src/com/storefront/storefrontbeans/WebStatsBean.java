package com.storefront.storefrontbeans;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class WebStatsBean
    extends BaseBean {
  final static private String fields[] = {
      "userid", "remotehost", "sourceurl", "referer", "hitdate" };
  final static private String tablename = "webstat";

  final static private String bydate =
      " where DATE_FORMAT(hitdate,'%Y %m %d') between " +
      "DATE_FORMAT(?,'%Y %m %d') and  DATE_FORMAT(?,'%Y %m %d') ";
  final static private String orderbycreation = " order by hitdate";

  public WebStatsBean() throws ServletException {
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

  public AddWebStatResponse AddWebStat(HttpServletRequest httprequest, User user) throws ServletException {

    AddWebStatResponse response = new AddWebStatResponse();
    Connection conn = null;
    try {

      String src = httprequest.getParameter("source");
      if (src == null) {
        src = httprequest.getParameter("src");
      }

      String referer = httprequest.getHeader("Referer");
      String remotehost = httprequest.getRemoteHost();

      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getInsertString());
      int col=0;
      pstmt.setInt(++col, user.getid());
      pstmt.setString(++col, remotehost);
      pstmt.setString(++col, src);
      pstmt.setString(++col, referer);
      pstmt.setTimestamp(++col,
                         new Timestamp(new java.util.Date().getTime()));
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

  public AddWebStatResponse AddWebStat(AddWebStatRequest request) throws ServletException {

    AddWebStatResponse response = new AddWebStatResponse();
    Connection conn = null;
    try {

      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getInsertString());
      WebStat webstat = (WebStat)request.getwebstat();
      int col=0;
      pstmt.setInt(++col, webstat.getuserid());
      pstmt.setString(++col, webstat.getremotehost());
      pstmt.setString(++col, webstat.getsourceurl());
      pstmt.setString(++col, webstat.getreferer());
      pstmt.setTimestamp(++col,
                         new Timestamp(webstat.gethitdate().getTime()));

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

  public UpdateWebStatResponse UpdateWebStat(UpdateWebStatRequest request) throws ServletException {

    UpdateWebStatResponse response = new UpdateWebStatResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getUpdateString());
      WebStat webstat = (WebStat)request.getwebstat();
      int col=0;
      pstmt.setInt(++col, webstat.getuserid());
      pstmt.setString(++col, webstat.getremotehost());
      pstmt.setString(++col, webstat.getsourceurl());
      pstmt.setString(++col, webstat.getreferer());
      pstmt.setTimestamp(++col,
                         new Timestamp(webstat.gethitdate().getTime()));
      pstmt.setInt(col++, webstat.getid());
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

  public DeleteWebStatResponse DeleteWebStat(DeleteWebStatRequest request) throws ServletException {

    DeleteWebStatResponse response = new DeleteWebStatResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("delete from webstat where id = ?");
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

  public GetWebStatResponse GetWebStat(GetWebStatRequest request) throws
      ServletException {

    Connection conn = null;
    GetWebStatResponse response = new GetWebStatResponse();
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getSelectWithIDByIDString());
      pstmt.setInt(1, request.getid());
      rs = pstmt.executeQuery();

      if(rs.next()) {
        WebStat webstat = new WebStat();
        int col=0;
        webstat.setid(rs.getInt(++col));
        webstat.setuserid(rs.getInt(++col));
        webstat.setremotehost(rs.getString(++col));
        webstat.setsourceurl(rs.getString(++col));
        webstat.setreferer(rs.getString(++col));
        webstat.sethitdate(new java.util.Date(rs.getTimestamp(++col).getTime()));
        response.setwebstat(webstat);
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

  public GetWebStatsResponse GetWebStats(GetWebStatsRequest request) throws
      ServletException {

    Connection conn = null;
    GetWebStatsResponse response = new GetWebStatsResponse();
    try {
      conn = datasource.getConnection();
      String query = getSelectWithIDString();
      if (request.getstartdate() != null) {
        query += bydate;
      }
      query += orderbycreation;

      pstmt = conn.prepareStatement(query);

      if (request.getstartdate() != null) {
        pstmt.setTimestamp(1, new Timestamp(request.getstartdate().getTime()));
        pstmt.setTimestamp(2, new Timestamp(request.getenddate().getTime()));
      }
      rs = pstmt.executeQuery();

      while(rs.next()) {
        WebStat webstat = new WebStat();
        int col=0;
        webstat.setid(rs.getInt(++col));
        webstat.setuserid(rs.getInt(++col));
        webstat.setremotehost(rs.getString(++col));
        webstat.setsourceurl(rs.getString(++col));
        webstat.setreferer(rs.getString(++col));
        webstat.sethitdate(new java.util.Date(rs.getTimestamp(++col).getTime()));
        response.setwebstat(webstat);
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





