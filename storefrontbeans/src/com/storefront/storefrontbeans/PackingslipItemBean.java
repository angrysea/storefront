package com.storefront.storefrontbeans;

import java.util.Iterator;
import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class PackingslipItemBean
    extends BaseBean {
  final static private String fields[] = {
      "packingslipid", "qtyshipped", "salesorderitemid"};

  final static private String tablename = "packingslipitem";
  ItemBean itemBean = new ItemBean();

  public PackingslipItemBean() throws ServletException {
    super();
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

  public void AddPackingslipItems(Connection conn, int id, Iterator it) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement(getInsertString());
      while (it.hasNext()) {
        PackingslipItem packingslipItem = (PackingslipItem) it.next();
        pstmt.setInt(1, id);
        pstmt.setInt(2, packingslipItem.getquantity());
        pstmt.setInt(3, packingslipItem.getsalesorderitemid());
        if ( (pstmt.executeUpdate()) > 0) {
          packingslipItem.setid(getLastInsertID(conn));
        }
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

  public void UpdatePackingslipItems(Connection conn, int id, Iterator it) throws
      ServletException {
    PreparedStatement pstmtadd = null;
    try {
      pstmtadd = conn.prepareStatement(getInsertString());
      DeletePackingslipItems(conn, id);
      while (it.hasNext()) {
        PackingslipItem packingslipItem = (PackingslipItem) it.next();
        pstmt.setInt(1, packingslipItem.getpackingslipid());
        pstmt.setInt(2, packingslipItem.getquantity());
        pstmt.setInt(3, packingslipItem.getsalesorderitemid());
        pstmtadd.setInt(4, packingslipItem.getid());
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try {
        if (pstmtadd != null) pstmtadd.close();
        pstmtadd = null;
      }
      catch (SQLException sqle) {}
      try {
        if (pstmt != null) pstmt.close();
        pstmt = null;
      }
      catch (SQLException sqle) {}
    }
  }

  public void DeletePackingslipItems(Connection conn, int id) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement(
          "delete from packingslipitem where packingslipid = ?");
      pstmt.setInt(1, id);
      pstmt.executeUpdate();
      new CreditCardBean().DeleteCreditCard(conn, id);
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

  public void GetPackingslipItems(Connection conn, Packingslip packingslip) throws
      ServletException {
    try {
      String select =
          "select a.id, a.packingslipid, a.qtyshipped, b.salesorderid, a.salesorderitemid, " +
          "b.itemnumber, c.isin, c.productname, d.shippingweight " +
          "from packingslipitem a, salesorderitem b, item c, details d " +
          "where a.salesorderitemid=b.id and b.itemnumber = c.id and " +
          "b.itemnumber = d.itemnumber and packingslipid = ?";

      pstmt = conn.prepareStatement(select);
      pstmt.setInt(1, packingslip.getid());
      rs = pstmt.executeQuery();
      while (rs.next()) {
        PackingslipItem packingslipItem = new PackingslipItem();
        packingslipItem.setid(rs.getInt(1));
        packingslipItem.setpackingslipid(rs.getInt(2));
        packingslipItem.setquantity(rs.getInt(3));
        packingslipItem.setsalesorderid(rs.getInt(4));
        packingslipItem.setsalesorderitemid(rs.getInt(5));
        packingslipItem.setitemid(rs.getInt(6));
        packingslipItem.setisin(rs.getString(7));
        packingslipItem.setproductname(rs.getString(8));
        packingslipItem.setshippingweight(rs.getDouble(9));
        packingslip.setitems(packingslipItem);

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

  void ShipPackingslipItems(Connection conn, Iterator it) throws
      ServletException {
    try {
      ItemBean itemBean = new ItemBean();
      SalesOrderItemBean salesOrderItemBean = new SalesOrderItemBean();
      while (it.hasNext()) {
        PackingslipItem packingslipItem = (PackingslipItem) it.next();
        salesOrderItemBean.Shipped(conn,
                                   packingslipItem.getsalesorderitemid(),
                                   packingslipItem.getquantity());
        itemBean.ShipItem(conn,
                          packingslipItem.getitemid(),
                          packingslipItem.getquantity());
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
  }
}
