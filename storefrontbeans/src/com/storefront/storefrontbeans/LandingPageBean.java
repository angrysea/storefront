package com.storefront.storefrontbeans;

import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class LandingPageBean
    extends BaseBean {
  final static private String fields[] = {
      "type", "typeid", "sortorder", "url", "active", "keyword", "heading", "description"};
  final static private String tablename = "landingPages";

  public LandingPageBean() throws ServletException {
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

  public AddLandingPageResponse AddLandingPage(AddLandingPageRequest request) throws ServletException {

    AddLandingPageResponse response = new AddLandingPageResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getInsertString());
      LandingPage landingPage = (LandingPage)request.getlandingPage();
      int col=0;
      pstmt.setString(++col, landingPage.gettype());
      pstmt.setInt(++col, landingPage.gettypeid());
      pstmt.setInt(++col, landingPage.getsortorder());
      pstmt.setString(++col, landingPage.geturl());
      pstmt.setBoolean(++col, landingPage.getactive());
      pstmt.setString(++col, landingPage.getkeyword());
      pstmt.setString(++col, landingPage.getheading());
      pstmt.setString(++col, landingPage.getdescription());
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

  public UpdateLandingPageResponse UpdateLandingPage(UpdateLandingPageRequest request) throws ServletException {

    UpdateLandingPageResponse response = new UpdateLandingPageResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getUpdateString());
      LandingPage landingPage = (LandingPage)request.getlandingPage();
      int col=0;
      pstmt.setString(++col, landingPage.gettype());
      pstmt.setInt(++col, landingPage.gettypeid());
      pstmt.setInt(++col, landingPage.getsortorder());
      pstmt.setString(++col, landingPage.geturl());
      pstmt.setBoolean(++col, landingPage.getactive());
      pstmt.setString(++col, landingPage.getkeyword());
      pstmt.setString(++col, landingPage.getheading());
      pstmt.setString(++col, landingPage.getdescription());
      pstmt.setInt(++col, landingPage.getid());
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

  public DeleteLandingPageResponse DeleteLandingPage(DeleteLandingPageRequest request) throws ServletException {

    DeleteLandingPageResponse response = new DeleteLandingPageResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("delete from landingPages where id = ?");
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

  public GetLandingPageResponse GetLandingPage(GetLandingPageRequest request) throws
      ServletException {

    Connection conn = null;
    GetLandingPageResponse response = new GetLandingPageResponse();
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getSelectWithIDByIDString());
      pstmt.setInt(1, request.getid());
      rs = pstmt.executeQuery();

      if(rs.next()) {
        LandingPage landingPage = new LandingPage();
        int col=0;
        landingPage.setid(rs.getInt(++col));
        landingPage.settype(rs.getString(++col));
        landingPage.settypeid(rs.getInt(++col));
        landingPage.setsortorder(rs.getInt(++col));
        landingPage.seturl(rs.getString(++col));
        landingPage.setactive(rs.getBoolean(++col));
        landingPage.setkeyword(rs.getString(++col));
        landingPage.setheading(rs.getString(++col));
        landingPage.setdescription(rs.getString(++col));
        response.setlandingPage(landingPage);
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

  public GetLandingPagesResponse GetLandingPages(GetLandingPagesRequest request) throws
      ServletException {

    Connection conn = null;
    GetLandingPagesResponse response = new GetLandingPagesResponse();
    try {
      conn = datasource.getConnection();
      String query = getSelectWithIDString();
      String where = null;

      if(request.getkeyword()!=null) {
        where = (where==null)?" where " : where+" and ";
        where += "keyword = '" + request.getkeyword() + "'";
      }

      if(request.gettype()!=null) {
        where = (where==null)?" where " : where+" and ";
        where += "type = '" + request.gettype() + "'";
      }

      if(request.getactive()==true) {
        where = (where==null)?" where " : where+" and ";
        where += "active > 0";
      }

      if(where!=null)
        query+=where;

      query += " order by sortorder";
      pstmt = conn.prepareStatement(query);
      rs = pstmt.executeQuery();

      while(rs.next()) {
        LandingPage landingPage = new LandingPage();
        int col=0;
        landingPage.setid(rs.getInt(++col));
        landingPage.settype(rs.getString(++col));
        landingPage.settypeid(rs.getInt(++col));
        landingPage.setsortorder(rs.getInt(++col));
        landingPage.seturl(rs.getString(++col));
        landingPage.setactive(rs.getBoolean(++col));
        landingPage.setkeyword(rs.getString(++col));
        landingPage.setheading(rs.getString(++col));
        landingPage.setdescription(rs.getString(++col));
        response.setlandingPages(landingPage);
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




