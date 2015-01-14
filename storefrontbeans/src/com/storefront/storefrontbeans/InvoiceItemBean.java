package com.storefront.storefrontbeans;

import java.util.Iterator;
import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class InvoiceItemBean
    extends BaseBean {
  final static private String fields[] = {
      "invoiceid", "salesorderitemid", "unitPrice",
      "totalPrice", "itemnumber", "quantity", "giftoption", };

  final static private String tablename = "invoiceitem";
  ItemBean itemBean = new ItemBean();

  public InvoiceItemBean() throws ServletException {
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

  void AddInvoiceItems(Connection conn, int id, Iterator it) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement(getInsertString());
      while (it.hasNext()) {
        InvoiceItem invoiceItem = (InvoiceItem) it.next();
        pstmt.setInt(1, id);
        pstmt.setInt(2, invoiceItem.getsalesorderitemid());
        pstmt.setDouble(3, invoiceItem.getunitPrice());
        pstmt.setDouble(4, invoiceItem.gettotalPrice());
        pstmt.setInt(5, invoiceItem.getitemnumber());
        pstmt.setInt(6, invoiceItem.getquantity());
        pstmt.setBoolean(7, invoiceItem.getgiftoption());
        if ( (pstmt.executeUpdate()) > 0) {
          invoiceItem.setid(getLastInsertID(conn));
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

  void UpdateInvoiceItems(Connection conn, int id, Iterator it) throws
      ServletException {
    PreparedStatement pstmtadd = null;
    try {
      pstmtadd = conn.prepareStatement(getInsertString());
      DeleteInvoiceItems(conn, id);
      while (it.hasNext()) {
        InvoiceItem invoiceItem = (InvoiceItem) it.next();
        pstmt.setInt(1, invoiceItem.getinvoiceid());
        pstmt.setInt(2, invoiceItem.getsalesorderitemid());
        pstmt.setDouble(3, invoiceItem.getunitPrice());
        pstmt.setDouble(4, invoiceItem.gettotalPrice());
        pstmt.setInt(5, invoiceItem.getitemnumber());
        pstmt.setInt(6, invoiceItem.getquantity());
        pstmt.setBoolean(7, invoiceItem.getgiftoption());
        pstmtadd.setInt(8, invoiceItem.getid());
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

  void DeleteInvoiceItems(Connection conn, int id) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement(
          "delete from invoiceitem where invoiceid = ?");
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

  public void GetInvoiceItems(Connection conn, Invoice invoice) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement(this.getSelectString(
          " invoiceid = ? "));
      pstmt.setInt(1, invoice.getid());
      rs = pstmt.executeQuery();
      while (rs.next()) {
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setinvoiceid(rs.getInt(1));
        invoiceItem.setsalesorderitemid(rs.getInt(2));
        invoiceItem.setunitPrice(rs.getDouble(3));
        invoiceItem.settotalPrice(rs.getDouble(4));
        invoiceItem.setitemnumber(rs.getInt(5));
        invoiceItem.setquantity(rs.getInt(6));
        invoiceItem.setgiftoption(rs.getBoolean(7));
        invoice.setitems(invoiceItem);
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
