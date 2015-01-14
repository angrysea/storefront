package com.storefront.storefrontbeans;

import java.util.*;
import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class AffiliateCommissionLevelsBean
    extends BaseBean {
  final static private String fields[] = {
      "programid", "description", "linktype", "start", "end", "percent"};
  final static private String tablename = "affiliatecommissionlevel";

  public AffiliateCommissionLevelsBean() throws ServletException {
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

  void AddAffiliateCommissionLevels(Connection conn, AffiliateProgram program) throws ServletException {

    try {
      pstmt = conn.prepareStatement(getInsertString());
      Iterator it = program.getcommissionLevelsIterator();
      while (it.hasNext()) {
        AffiliateCommissionLevel commissionlevel = (AffiliateCommissionLevel) it.next();
        int col = 0;
        pstmt.setInt(++col, commissionlevel.getprogramid());
        pstmt.setString(++col, commissionlevel.getdescription());
        pstmt.setString(++col, commissionlevel.getlinktype());
        pstmt.setDouble(++col, commissionlevel.getstart());
        pstmt.setDouble(++col, commissionlevel.getend());
        pstmt.setDouble(++col, commissionlevel.getpercent());
        pstmt.executeUpdate();
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

  void UpdateAffiliateCommissionLevels(Connection conn, AffiliateProgram program) throws ServletException {

    try {
      pstmt = conn.prepareStatement(getUpdateString());
      Iterator it = program.getcommissionLevelsIterator();
      while (it.hasNext()) {
        AffiliateCommissionLevel commissionlevel = (AffiliateCommissionLevel) it.next();
        int col = 0;
        pstmt.setInt(++col, commissionlevel.getprogramid());
        pstmt.setString(++col, commissionlevel.getdescription());
        pstmt.setString(++col, commissionlevel.getlinktype());
        pstmt.setDouble(++col, commissionlevel.getstart());
        pstmt.setDouble(++col, commissionlevel.getend());
        pstmt.setDouble(++col, commissionlevel.getpercent());
        pstmt.setInt(++col, commissionlevel.getid());
        pstmt.executeUpdate();
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

  void DeleteAffiliateCommissionLevels(Connection conn, int id) throws ServletException {

    try {
      pstmt = conn.prepareStatement("delete from affiliatecommissionlevels where programid = ?");
      pstmt.setInt(1, id);
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
    }
  }

  void GetAffiliateCommissionLevels(Connection conn, AffiliateProgram program) throws
      ServletException {

    try {
      pstmt = conn.prepareStatement(getSelectWithIDByIDString());
      pstmt.setInt(1, program.getid());
      rs = pstmt.executeQuery();

      if(rs.next()) {
        AffiliateCommissionLevel commissionlevel = new AffiliateCommissionLevel();
        int col=0;
        commissionlevel.setid(rs.getInt(++col));
        commissionlevel.setprogramid(rs.getInt(++col));
        commissionlevel.setdescription(rs.getString(++col));
        commissionlevel.setlinktype(rs.getString(++col));
        commissionlevel.setstart(rs.getDouble(++col));
        commissionlevel.setend(rs.getDouble(++col));
        commissionlevel.setpercent(rs.getDouble(++col));
        program.setcommissionLevels(commissionlevel);
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
  }

}
