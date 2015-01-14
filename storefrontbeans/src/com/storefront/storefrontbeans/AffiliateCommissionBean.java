package com.storefront.storefrontbeans;

import java.util.*;
import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class AffiliateCommissionBean
    extends BaseBean {
  final static private String fields[] = {
      "affiliateid", "salesorderitem", "percent", "commission", "creationdate", "paiddate"};
  final static private String tablename = "affiliatecommission";

  public AffiliateCommissionBean() throws ServletException {
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

  void addAffiliateCommission(Connection conn, Iterator it) throws ServletException {

    try {
      pstmt = conn.prepareStatement(getInsertString());
      while (it.hasNext()) {
        AffiliateCommission commission = (AffiliateCommission) it.next();
        int col = 0;
        pstmt.setInt(++col, commission.getaffiliateid());
        pstmt.setInt(++col, commission.getsalesorderitem());
        pstmt.setDouble(++col, commission.getpercent());
        pstmt.setDouble(++col, commission.getcommission());
        pstmt.setTimestamp(++col,
                           new Timestamp(commission.getcreationdate().getTime()));
        pstmt.setTimestamp(++col, null);
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

  void AddAffiliateCommission(Connection conn, AffiliateCommission commission) throws ServletException {

    try {
      pstmt = conn.prepareStatement(getInsertString());
      int col = 0;
      pstmt.setInt(++col, commission.getaffiliateid());
      pstmt.setInt(++col, commission.getsalesorderitem());
      pstmt.setDouble(++col, commission.getpercent());
      pstmt.setDouble(++col, commission.getcommission());
      pstmt.setTimestamp(++col,
                         new Timestamp(commission.getcreationdate().getTime()));
      pstmt.setTimestamp(++col, null);
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

  void UpdateAffiliateCommission(Connection conn, Iterator it) throws ServletException {

    try {
      pstmt = conn.prepareStatement(getUpdateString());
      while (it.hasNext()) {
        AffiliateCommission commission = (AffiliateCommission) it.next();
        int col = 0;
        pstmt.setInt(++col, commission.getaffiliateid());
        pstmt.setInt(++col, commission.getsalesorderitem());
        pstmt.setDouble(++col, commission.getpercent());
        pstmt.setDouble(++col, commission.getcommission());
        pstmt.setTimestamp(++col,
                           new Timestamp(commission.getcreationdate().getTime()));
        pstmt.setTimestamp(++col, null);
        pstmt.setInt(++col, commission.getid());
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

  void DeleteAffiliateCommission(Connection conn, int id) throws ServletException {

    try {
      pstmt = conn.prepareStatement("delete from affiliatecommission where id = ?");
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

  void GetAffiliateCommission(Connection conn, Affiliate affiliate) throws
      ServletException {

    try {
      pstmt = conn.prepareStatement(getSelectWithIDByIDString());
      pstmt.setInt(1, affiliate.getid());
      rs = pstmt.executeQuery();

      if(rs.next()) {
        AffiliateCommission commission = new AffiliateCommission();
        int col=0;
        commission.setid(rs.getInt(++col));
        commission.setaffiliateid(rs.getInt(++col));
        commission.setsalesorderitem(rs.getInt(++col));
        commission.setpercent(rs.getDouble(++col));
        commission.setcommission(rs.getDouble(++col));
        commission.setcreationdate(new java.util.Date(rs.getTimestamp(++col).getTime()));
        commission.setpaiddate(new java.util.Date(rs.getTimestamp(++col).getTime()));
        affiliate.setcommissions(commission);
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
