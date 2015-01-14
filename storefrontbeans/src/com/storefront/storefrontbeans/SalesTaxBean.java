package com.storefront.storefrontbeans;

import java.util.Iterator;
import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class SalesTaxBean
    extends BaseBean {
  final static private String fields[] = {
      "type", "sellfrom", "sellto", "description", "taxrate"};

  final static private String findtaxrate = "select description, taxrate " +
      "from salestax where (sellfrom='any' or sellfrom=?) and sellto=? and type=?";
  final static private String select =
      "select id, type, sellfrom, sellto, description, taxrate from salestax";
  final static private String tablename = "salestax";

  String[] getfields() {
    return fields;
  }

  String gettableName() {
    return tablename;
  }

  String getindexName() {
    return "id";
  }

  public SalesTaxBean() throws ServletException {
    super();
  }

  public AddSalesTaxResponse AddSalesTax(AddSalesTaxRequest request) throws
      ServletException {
    Connection conn = null;
    AddSalesTaxResponse response = new AddSalesTaxResponse();
    try {
      conn = datasource.getConnection();
      SalesTax salestax = (SalesTax) request.getsalestax();
      pstmt = conn.prepareStatement(getInsertString());
      pstmt.setString(1, salestax.gettype());
      pstmt.setString(2, salestax.getsellfrom());
      pstmt.setString(3, salestax.getsellto());
      pstmt.setString(4, salestax.getdescription());
      pstmt.setDouble(5, salestax.gettaxrate());
      if ( (pstmt.executeUpdate()) > 0) {
        salestax.setid(getLastInsertID(conn));
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

  public FindSalesTaxResponse FindSalesTax(FindSalesTaxRequest request) throws
      ServletException {
    FindSalesTaxResponse response = new FindSalesTaxResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      response.setsalestax(FindSalesTax(conn, request.getsellfrom(),
                                        request.getsellto(), request.gettype()));
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

  static SalesTax FindSalesTax(Connection conn, String sellfrom, String sellto,
                               String type) throws
      ServletException {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    SalesTax salestax = null;
    try {
      pstmt = conn.prepareStatement(findtaxrate);
      pstmt.setString(1, sellfrom);
      pstmt.setString(2, sellto);
      pstmt.setString(3, type);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        salestax = new SalesTax();
        salestax.settype(type);
        salestax.setsellfrom(sellfrom);
        salestax.setsellto(sellto);
        salestax.setdescription(rs.getString(1));
        salestax.settaxrate(rs.getDouble(2));
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
    return salestax;
  }

  public GetSalesTaxResponse GetSalesTax(GetSalesTaxRequest request) throws
      ServletException {
    GetSalesTaxResponse response = new GetSalesTaxResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(this.getSelectByIDString());
      pstmt.setInt(1, request.getid());
      rs = pstmt.executeQuery();
      if (rs.next()) {
        SalesTax salestax = new SalesTax();
        salestax.setid(request.getid());
        salestax.settype(rs.getString(1));
        salestax.setsellfrom(rs.getString(2));
        salestax.setsellto(rs.getString(3));
        salestax.setdescription(rs.getString(4));
        salestax.settaxrate(rs.getDouble(5));
        response.setsalestax(salestax);
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

  public GetSalesTaxesResponse GetSalesTaxes(GetSalesTaxesRequest request) throws
      ServletException {
    GetSalesTaxesResponse response = new GetSalesTaxesResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(select);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        SalesTax salestax = new SalesTax();
        salestax.setid(rs.getInt(1));
        salestax.settype(rs.getString(2));
        salestax.setsellfrom(rs.getString(3));
        salestax.setsellto(rs.getString(4));
        salestax.setdescription(rs.getString(5));
        salestax.settaxrate(rs.getDouble(6));
        response.setsalestax(salestax);
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
