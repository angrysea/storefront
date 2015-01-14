package com.storefront.storefrontbeans;

import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class CreditCardBean
    extends BaseBean {
  final static private String fields[] = {
      "id", "type", "number", "expmonth", "expyear", "cardholder"};

  public CreditCardBean() throws ServletException {
    super();
  }

  String[] getfields() {
    return fields;
  }

  String gettableName() {
    return "creditcard";
  }

  String getindexName() {
    return "id";
  }

  public void AddCreditCard(Connection conn, int id, CreditCard cc) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement(getInsertString());
      cc.setid(id);
      pstmt.setInt(1, cc.getid());
      pstmt.setString(2, cc.gettype());
      pstmt.setString(3, cc.getnumber());
      pstmt.setString(4, cc.getexpmonth());
      pstmt.setString(5, cc.getexpyear());
      pstmt.setString(6, cc.getcardholder());
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

  public void UpdateCreditCard(Connection conn, int id, CreditCard cc) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement(getUpdateString());
      pstmt.setInt(1, id);
      pstmt.setString(2, cc.gettype());
      pstmt.setString(3, cc.getnumber());
      pstmt.setString(4, cc.getexpmonth());
      pstmt.setString(5, cc.getexpyear());
      pstmt.setString(6, cc.getcardholder());
      pstmt.setInt(7, id);
      if(pstmt.executeUpdate()<=0)
        AddCreditCard(conn, id, cc);
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

  public void DeleteCreditCard(Connection conn, int id) throws ServletException {
    try {
      pstmt = conn.prepareStatement("delete from creditcard where id=?");
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

  public void GetCreditCard(Connection conn, Address address) throws
      ServletException {
    try {
      CreditCard cc = new CreditCard();
      pstmt = conn.prepareStatement(getSelectByIDString());
      pstmt.setInt(1, address.getid());
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        cc.setid(rs.getInt(1));
        cc.settype(rs.getString(2));
        cc.setnumber(rs.getString(3));
        cc.setexpmonth(rs.getString(4));
        cc.setexpyear(rs.getString(5));
        cc.setcardholder(rs.getString(6));
        address.setcreditcard(cc);
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
