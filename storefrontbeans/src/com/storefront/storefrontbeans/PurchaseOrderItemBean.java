package com.storefront.storefrontbeans;

import java.util.Iterator;
import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class PurchaseOrderItemBean
    extends BaseBean {
  final static private String fields[] = {
      "purchaseorderid", "itemnumber", "trxtype", "quantity", "quantityreceived", "ourcost", "status"};
  final static private String tablename = "purchaseorderitem";

  final static private String selectitem =
      "select a.id, a.itemnumber, a.trxtype, b.isin, b.productname, " +
      "d.name, a.quantity, a.quantityreceived, a.ourcost, c.shippingweight, " +
      "a.status from purchaseorderitem a, item b, details c, manufacturer d " +
      "where a.itemnumber=b.id and a.itemnumber=c.itemnumber and b.manufacturer=d.id " +
      "and purchaseorderid=?";

  public PurchaseOrderItemBean() throws ServletException {
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

  void AddPurchaseOrderItem(Connection conn, int id, Iterator it) throws
      ServletException {

    try {
      pstmt = conn.prepareStatement(getInsertString());
      while (it.hasNext()) {
        PurchaseOrderItem purchaseorderitem = (PurchaseOrderItem) it.next();
        pstmt.setInt(1, id);
        pstmt.setInt(2, purchaseorderitem.getitemnumber());
        pstmt.setString(3, purchaseorderitem.gettrxtype());
        pstmt.setInt(4, purchaseorderitem.getquantity());
        pstmt.setInt(5, purchaseorderitem.getquantityreceived());
        pstmt.setDouble(6, purchaseorderitem.getourcost());
        pstmt.setString(7, purchaseorderitem.getstatus());
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

  void updatePurchaseOrderItem(Connection conn, int id,
                               PurchaseOrderItem purchaseorderitem) throws
      ServletException {

    try {
      pstmt = conn.prepareStatement(getUpdateString());
      int col=0;
      pstmt.setInt(++col, id);
      pstmt.setInt(++col, purchaseorderitem.getitemnumber());
      pstmt.setString(++col, purchaseorderitem.gettrxtype());
      pstmt.setInt(++col, purchaseorderitem.getquantity());
      pstmt.setInt(++col, purchaseorderitem.getquantityreceived());
      pstmt.setDouble(++col, purchaseorderitem.getourcost());
      pstmt.setString(++col, purchaseorderitem.getstatus());
      pstmt.setInt(++col, purchaseorderitem.getid());
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

  public void DeletePurchaseOrderItem(Connection conn, int id) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement(
          "delete from purchaseorderitem where purchaseorderid=?");
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
      }
      catch (SQLException sqle) {}
    }
  }

  public void GetPurchaseOrderItem(Connection conn, PurchaseOrder po) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement(selectitem);
      pstmt.setInt(1, po.getid());
      rs = pstmt.executeQuery();
      while (rs.next()) {
        PurchaseOrderItem purchaseorderitem = new PurchaseOrderItem();
        purchaseorderitem.setid(rs.getInt(1));
        purchaseorderitem.setitemnumber(rs.getInt(2));
        purchaseorderitem.settrxtype(rs.getString(3));
        purchaseorderitem.setisin(rs.getString(4));
        purchaseorderitem.setproductname(rs.getString(5));
        purchaseorderitem.setmanufacturer(rs.getString(6));
        purchaseorderitem.setquantity(rs.getInt(7));
        purchaseorderitem.setquantityreceived(rs.getInt(8));
        purchaseorderitem.setourcost(rs.getDouble(9));
        purchaseorderitem.setshippingweight(rs.getDouble(10));
        purchaseorderitem.setstatus(rs.getString(11));
        po.setitems(purchaseorderitem);
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
