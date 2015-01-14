package com.storefront.storefrontbeans;

import java.util.*;
import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class AffiliateProgramBean
    extends BaseBean {
  final static private String fields[] = {
      "description", "type", "minpercent","maxpercent"};
  final static private String tablename = "affiliateprogram";

  public AffiliateProgramBean() throws ServletException {
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

  public AddAffiliateProgramResponse AddAffiliateProgram(AddAffiliateProgramRequest request) throws ServletException {

    AddAffiliateProgramResponse response = new AddAffiliateProgramResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getInsertString());
      AffiliateProgram affiliateprogram = (AffiliateProgram)request.getaffiliateprogram();
      Iterator it = affiliateprogram.getcommissionLevelsIterator();
      while (it.hasNext()) {
        AffiliateCommissionLevel commissionlevel = (AffiliateCommissionLevel) it.next();
        if(affiliateprogram.getminpercent()>commissionlevel.getstart()) {
          affiliateprogram.setminpercent(commissionlevel.getstart());
        }
        if(affiliateprogram.getmaxpercent()<commissionlevel.getend()) {
          affiliateprogram.setmaxpercent(commissionlevel.getend());
        }
      }
      int col=0;
      pstmt.setString(++col, affiliateprogram.getdescription());
      pstmt.setInt(++col, affiliateprogram.gettype());
      pstmt.setDouble(++col, affiliateprogram.getminpercent());
      pstmt.setDouble(++col, affiliateprogram.getmaxpercent());
      int id = 0;
      if((id=pstmt.executeUpdate())>0) {
        affiliateprogram.setid(id);
        new AffiliateCommissionLevelsBean().AddAffiliateCommissionLevels(conn, affiliateprogram);
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
      try {
        if (conn != null) conn.close();
        conn = null;
      }
      catch (SQLException sqle) {}
    }
    return response;
  }

  public UpdateAffiliateProgramResponse UpdateAffiliateProgram(UpdateAffiliateProgramRequest request) throws ServletException {

    UpdateAffiliateProgramResponse response = new UpdateAffiliateProgramResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getUpdateString());
      AffiliateProgram affiliateprogram = (AffiliateProgram)request.getaffiliateprogram();
      Iterator it = affiliateprogram.getcommissionLevelsIterator();
      while (it.hasNext()) {
        AffiliateCommissionLevel commissionlevel = (AffiliateCommissionLevel) it.next();
        if(affiliateprogram.getminpercent()>commissionlevel.getstart()) {
          affiliateprogram.setminpercent(commissionlevel.getstart());
        }
        if(affiliateprogram.getmaxpercent()<commissionlevel.getend()) {
          affiliateprogram.setmaxpercent(commissionlevel.getend());
        }
      }
      int col = 0;
      pstmt.setString(++col, affiliateprogram.getdescription());
      pstmt.setInt(++col, affiliateprogram.gettype());
      pstmt.setDouble(++col, affiliateprogram.getminpercent());
      pstmt.setDouble(++col, affiliateprogram.getmaxpercent());
      pstmt.setInt(++col, affiliateprogram.getid());
      pstmt.executeUpdate();
      new AffiliateCommissionLevelsBean().UpdateAffiliateCommissionLevels(conn, affiliateprogram);
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

  public DeleteAffiliateProgramResponse DeleteAffiliateProgram(DeleteAffiliateProgramRequest request) throws ServletException {

    DeleteAffiliateProgramResponse response = new DeleteAffiliateProgramResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("delete from affiliateprogram where id = ?");
      pstmt.setInt(1, request.getid());
      pstmt.executeUpdate();
      new AffiliateCommissionLevelsBean().DeleteAffiliateCommissionLevels(conn, request.getid());
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

  public GetAffiliateProgramResponse GetAffiliateProgram(GetAffiliateProgramRequest request) throws
      ServletException {

    Connection conn = null;
    GetAffiliateProgramResponse response = new GetAffiliateProgramResponse();
    try {
      conn = datasource.getConnection();
      response.setaffiliateprogram(getAffiliateProgram(conn, request.getid()));
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

  AffiliateProgram getAffiliateProgram(Connection conn, int id) throws
      ServletException {

    AffiliateProgram affiliateprogram = null;
    try {
      pstmt = conn.prepareStatement(getSelectWithIDByIDString());
      pstmt.setInt(1, id);
      rs = pstmt.executeQuery();

      if(rs.next()) {
        affiliateprogram = new AffiliateProgram();
        int col=0;
        affiliateprogram.setid(rs.getInt(++col));
        affiliateprogram.setdescription(rs.getString(++col));
        affiliateprogram.settype(rs.getInt(++col));
        affiliateprogram.setminpercent(rs.getDouble(++col));
        affiliateprogram.setmaxpercent(rs.getDouble(++col));
        new AffiliateCommissionLevelsBean().GetAffiliateCommissionLevels(conn, affiliateprogram);
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
    return affiliateprogram;
  }

  public GetAffiliateProgramsResponse GetAffiliatePrograms(GetAffiliateProgramsRequest request) throws
      ServletException {

    Connection conn = null;
    GetAffiliateProgramsResponse response = new GetAffiliateProgramsResponse();
    try {
      conn = datasource.getConnection();
      String query = getSelectWithIDString();
      if(request.getorderby()!=null) {
        query += request.getorderby();
        if(request.getdirection()!=null)
          query += " " + request.getdirection();
      }

      pstmt = conn.prepareStatement(query);
      rs = pstmt.executeQuery();

      while(rs.next()) {
        AffiliateProgram affiliateprogram = new AffiliateProgram();
        int col=0;
        affiliateprogram.setid(rs.getInt(++col));
        affiliateprogram.setdescription(rs.getString(++col));
        affiliateprogram.settype(rs.getInt(++col));
        affiliateprogram.setminpercent(rs.getDouble(++col));
        affiliateprogram.setmaxpercent(rs.getDouble(++col));
        response.setaffiliateprogram(affiliateprogram);
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
