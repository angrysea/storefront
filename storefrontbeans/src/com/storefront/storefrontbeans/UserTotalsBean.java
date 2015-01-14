package com.storefront.storefrontbeans;

import java.util.Iterator;
import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class UserTotalsBean
    extends BaseBean {
  final static private String fields[] = {
      "sourceurl", "count", "creationdate" };
  final static private String tablename = "usertotals";
  final static private String selecttotals =
      "SELECT count(creationdate), DATE_FORMAT(creationdate,'%Y %m %d'), " +
      "sourceurl from users where  email is null and  " +
      "DATE_FORMAT(creationdate,'%Y %m %d') " +
      "between DATE_FORMAT(?,'%Y %m %d') and DATE_FORMAT(?,'%Y %m %d') " +
      "group by DATE_FORMAT(creationdate,'%Y %m %d'), sourceurl order by creationdate";

  final static private String update = "update usertotals set sourceurl=?, count=?, creationdate=? where sourceurl=? and creationdate=?";

  final static private String deleteusers =
      "delete from users where  email is null and  " +
      "DATE_FORMAT(creationdate,'%Y %m %d') " +
      "between DATE_FORMAT(?,'%Y %m %d') and DATE_FORMAT(?,'%Y %m %d')";

  final static private String orderbycreation = " ";

  public UserTotalsBean() throws ServletException {
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

  public int AddUserTotals(PreparedStatement pstmt, UserTotals userTotals) throws ServletException {

    int count = 0;
    try {
      pstmt.setString(1, userTotals.getsourceurl());
      pstmt.setInt(2, userTotals.getcount());
      pstmt.setTimestamp(3, new Timestamp(userTotals.getcreationdate().getTime()));
      count = pstmt.executeUpdate();
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
    return count;
  }

  public int UpdateUserTotals(PreparedStatement pstmt, UserTotals userTotals) throws ServletException {

    int count = 0;
    try {
      pstmt.setString(1, userTotals.getsourceurl());
      pstmt.setInt(2, userTotals.getcount());
      pstmt.setTimestamp(3, new Timestamp(userTotals.getcreationdate().getTime()));
      pstmt.setString(4, userTotals.getsourceurl());
      pstmt.setTimestamp(5, new Timestamp(userTotals.getcreationdate().getTime()));
      count = pstmt.executeUpdate();
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
    return count;
  }

  public GetUserTotalsResponse GetUserTotals(GetUserTotalsRequest request) throws
      ServletException {

    Connection conn = null;
    GetUserTotalsResponse response = new GetUserTotalsResponse();
    try {
      conn = datasource.getConnection();
      String query = getSelectWithIDString();
      pstmt = conn.prepareStatement(getSelectString());
      rs = pstmt.executeQuery();

      while(rs.next()) {
        UserTotals userTotals = new UserTotals();
        userTotals.setsourceurl(rs.getString(1));
        userTotals.setcount(rs.getInt(2));
        userTotals.setcreationdate(new java.util.Date(rs.getTimestamp(3).getTime()));
        response.setitems(userTotals);
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

  public ClearUsersResponse ClearUsers(ClearUsersRequest request) throws
      ServletException {

    PreparedStatement pstmtinsert = null;
    PreparedStatement pstmtupdate = null;
    Connection conn = null;
    ClearUsersResponse response = new ClearUsersResponse();

    try {
      pstmtinsert = conn.prepareStatement(getInsertString());
      pstmtupdate = conn.prepareStatement(update);
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(selecttotals);
      if (request.getstartdate() != null) {
        pstmt.setTimestamp(1, new Timestamp(request.getstartdate().getTime()));
        pstmt.setTimestamp(2, new Timestamp(request.getenddate().getTime()));
      }
      rs = pstmt.executeQuery();

      while(rs.next()) {
        UserTotals userTotals = new UserTotals();
        userTotals.setcount(rs.getInt(1));
        userTotals.setsourceurl(rs.getString(2));
        userTotals.setcreationdate(new java.util.Date(rs.getTimestamp(3).getTime()));
        if(UpdateUserTotals(pstmt, userTotals)==0)
          AddUserTotals(pstmt, userTotals);
      }
      pstmt.close();
      pstmt = conn.prepareStatement(deleteusers);
      if (request.getstartdate() != null) {
        pstmt.setTimestamp(1, new Timestamp(request.getstartdate().getTime()));
        pstmt.setTimestamp(2, new Timestamp(request.getenddate().getTime()));
      }
      pstmt.executeUpdate();
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
        if (pstmtinsert != null) pstmtinsert.close();
        pstmtinsert = null;
      }
      catch (SQLException sqle) {}
      try {
        if (pstmtupdate != null) pstmtupdate.close();
        pstmtupdate = null;
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
