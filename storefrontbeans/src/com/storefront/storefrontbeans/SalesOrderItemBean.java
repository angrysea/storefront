package com.storefront.storefrontbeans;

import java.util.Iterator;
import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class SalesOrderItemBean
    extends BaseBean {

  final static private String fields[] = {
      "salesorderid", "itemnumber", "trxtype", "quantity", "quantitytoship",
      "shipped", "unitprice", "status", "exchangeid", "giftoption"};

  final static private String updatequantitytoship = "update item set quantitytoship = quantitytoship+? where id=?";

  final static private String tablename = "salesorderitem";

  final static private String select =
      "select a.id, a.itemnumber, a.trxtype, b.isin, b.productname, " +
      "d.id, d.name, a.quantity, a.quantitytoship, a.shipped, unitprice, c.shippingweight, c.handlingcharges, a.status, f.status, exchangeid, " +
      "giftoption, e.id, e.zip, e.country from salesorderitem a, item b, details c, manufacturer d, distributor e, itemstatus f " +
      "where a.itemnumber=b.id and b.manufacturer=d.id and a.itemnumber=c.itemnumber and b.distributor=e.id and b.status = f.id " +
      "and salesorderid=?";

  final static private String selectitem =
      "select a.id, a.itemnumber, a.trxtype, b.isin, b.productname, " +
      "d.id, d.name, a.quantity, a.quantitytoship, a.shipped, unitprice, c.shippingweight, c.handlingcharges, a.status, f.status, exchangeid, " +
      "giftoption, e.id, e.zip, e.country from salesorderitem a, item b, details c, manufacturer d, distributor e, itemstatus f " +
      "where a.itemnumber=b.id and b.manufacturer=d.id and a.itemnumber=c.itemnumber and b.distributor=e.id and b.status = f.id " +
      "and a.id=?";

  final static private String selectopen =
      "select a.id, a.itemnumber, a.trxtype, b.isin, b.productname, " +
      "d.id, d.name, a.quantity, a.quantitytoship, a.shipped, unitprice, c.shippingweight, c.handlingcharges, " +
      "a.status, f.status, exchangeid, giftoption, e.id, e.zip, e.country, b.quantity, b.quantitytoship " +
      "from salesorderitem a, item b, details c, manufacturer d, distributor e, itemstatus f " +
      "where a.itemnumber=b.id and b.manufacturer=d.id and a.itemnumber=c.itemnumber and b.status = f.id " +
      "and b.distributor=e.id and salesorderid=? and (a.status = 'New' or a.status = 'Partially Shipped')";

  final static private String shipped = "update salesorderitem set status=?, quantitytoship=quantitytoship-?, shipped=shipped+?  where id=?";

  public SalesOrderItemBean() throws ServletException {
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

  void Shipped(Connection conn, int id, int qty) throws
      ServletException {
    try {
      SalesOrderItem item = GetSalesOrderItem(conn, id);
      String status = null;
      if((qty+item.getshipped())<item.getquantity())
        status = "Partially Shipped";
      else
        status = "Shipped";

      pstmt = conn.prepareStatement(shipped);
      int col=0;
      pstmt.setString(++col, status);
      pstmt.setInt(++col, qty);
      pstmt.setInt(++col, qty);
      pstmt.setInt(++col, id);
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

  void AddSalesOrderItem(Connection conn, int id, Iterator it) throws
      ServletException {
    ItemRankingBean rankingBean = new ItemRankingBean();
    ItemBean itemBean = new ItemBean();

    try {
      pstmt = conn.prepareStatement(getInsertString());
      while (it.hasNext()) {
        SalesOrderItem salesorderitem = (SalesOrderItem) it.next();
        pstmt.setInt(1, id);
        pstmt.setInt(2, salesorderitem.getitemnumber());
        pstmt.setString(3, salesorderitem.gettrxtype());
        pstmt.setInt(4, salesorderitem.getquantity());
        pstmt.setInt(5, salesorderitem.getquantitytoship());
        pstmt.setInt(6, salesorderitem.getshipped());
        pstmt.setDouble(7, salesorderitem.getunitprice());
        pstmt.setString(8, salesorderitem.getstatus());
        pstmt.setString(9, salesorderitem.getexchangeid());
        pstmt.setBoolean(10, salesorderitem.getgiftoption());
        pstmt.executeUpdate();
        itemBean.AllocateItem(conn, salesorderitem.getitemnumber(), salesorderitem.getquantity());
        rankingBean.AddItemSold(conn, salesorderitem.getitemnumber(), salesorderitem.getquantity());
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

  public void DeleteSalesOrderItem(Connection conn, int id) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement(
          "delete from salesorderitem where salesorderid=?");
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

  static SalesOrderItem GetSalesOrderItem(Connection conn, int id) throws
      ServletException {
    SalesOrderItem salesorderitem = null;
    PreparedStatement pstmt =null;
    ResultSet rs =null;
    try {
      pstmt = conn.prepareStatement(selectitem);
      pstmt.setInt(1, id);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        salesorderitem = new SalesOrderItem();
        int col=0;
        salesorderitem.setid(rs.getInt(++col));
        salesorderitem.setitemnumber(rs.getInt(++col));
        salesorderitem.settrxtype(rs.getString(++col));
        salesorderitem.setisin(rs.getString(++col));
        salesorderitem.setproductname(rs.getString(++col));
        salesorderitem.setmanufacturerid(rs.getInt(++col));
        salesorderitem.setmanufacturer(rs.getString(++col));
        salesorderitem.setquantity(rs.getInt(++col));
        salesorderitem.setquantitytoship(rs.getInt(++col));
        salesorderitem.setshipped(rs.getInt(++col));
        salesorderitem.setunitprice(rs.getDouble(++col));
        salesorderitem.setshippingweight(rs.getDouble(++col));
        salesorderitem.sethandlingcharges(rs.getDouble(++col));
        salesorderitem.setstatus(rs.getString(++col));
        salesorderitem.setitemstatus(rs.getString(++col));
        salesorderitem.setexchangeid(rs.getString(++col));
        salesorderitem.setgiftoption(rs.getBoolean(++col));
        salesorderitem.setdistributor(rs.getString(++col));
        salesorderitem.setzipcode(rs.getString(++col));
        salesorderitem.setcountry(rs.getString(++col));
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
    return salesorderitem;
  }

  public void GetSalesOrderItem(Connection conn, SalesOrder so) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement(select);
      pstmt.setInt(1, so.getid());
      rs = pstmt.executeQuery();
      while (rs.next()) {
        SalesOrderItem salesorderitem = new SalesOrderItem();
        int col=0;
        salesorderitem.setid(rs.getInt(++col));
        salesorderitem.setitemnumber(rs.getInt(++col));
        salesorderitem.settrxtype(rs.getString(++col));
        salesorderitem.setisin(rs.getString(++col));
        salesorderitem.setproductname(rs.getString(++col));
        salesorderitem.setmanufacturerid(rs.getInt(++col));
        salesorderitem.setmanufacturer(rs.getString(++col));
        salesorderitem.setquantity(rs.getInt(++col));
        salesorderitem.setquantitytoship(rs.getInt(++col));
        salesorderitem.setshipped(rs.getInt(++col));
        salesorderitem.setunitprice(rs.getDouble(++col));
        salesorderitem.setshippingweight(rs.getDouble(++col));
        salesorderitem.sethandlingcharges(rs.getDouble(++col));
        salesorderitem.setstatus(rs.getString(++col));
        salesorderitem.setitemstatus(rs.getString(++col));
        salesorderitem.setexchangeid(rs.getString(++col));
        salesorderitem.setgiftoption(rs.getBoolean(++col));
        salesorderitem.setdistributor(rs.getString(++col));
        salesorderitem.setzipcode(rs.getString(++col));
        salesorderitem.setcountry(rs.getString(++col));
        so.setitems(salesorderitem);
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

  public void GetOpenSalesOrderItems(Connection conn, SalesOrder so) throws
      ServletException {
    PreparedStatement pstmt2 = null;
    try {
      pstmt2 = conn.prepareStatement(updatequantitytoship);
      pstmt = conn.prepareStatement(selectopen);
      pstmt.setInt(1, so.getid());
      rs = pstmt.executeQuery();
      while (rs.next()) {
        SalesOrderItem salesorderitem = new SalesOrderItem();
        int col=0;
        salesorderitem.setid(rs.getInt(++col));
        salesorderitem.setitemnumber(rs.getInt(++col));
        salesorderitem.settrxtype(rs.getString(++col));
        salesorderitem.setisin(rs.getString(++col));
        salesorderitem.setproductname(rs.getString(++col));
        salesorderitem.setmanufacturerid(rs.getInt(++col));
        salesorderitem.setmanufacturer(rs.getString(++col));
        salesorderitem.setquantity(rs.getInt(++col));
        salesorderitem.setquantitytoship(rs.getInt(++col));
        salesorderitem.setshipped(rs.getInt(++col));
        salesorderitem.setunitprice(rs.getDouble(++col));
        salesorderitem.setshippingweight(rs.getDouble(++col));
        salesorderitem.sethandlingcharges(rs.getDouble(++col));
        salesorderitem.setstatus(rs.getString(++col));
        salesorderitem.setitemstatus(rs.getString(++col));
        salesorderitem.setexchangeid(rs.getString(++col));
        salesorderitem.setgiftoption(rs.getBoolean(++col));
        salesorderitem.setdistributor(rs.getString(++col));
        salesorderitem.setzipcode(rs.getString(++col));
        salesorderitem.setcountry(rs.getString(++col));
        int quantity = rs.getInt(++col);
        int quantitytoship = rs.getInt(++col);
        if(quantity<0)
          salesorderitem.setquantitytoship(salesorderitem.getquantity());
        else {
          int toShip = salesorderitem.getquantity() - salesorderitem.getshipped();
          if(toShip>(quantity-quantitytoship)) {
            toShip=(quantity-quantitytoship);
          }
          if(toShip<1) {
            toShip=0;
          }
          else {
            pstmt2.setInt(1,toShip);
            pstmt2.setInt(2,salesorderitem.getitemnumber());
            pstmt2.executeUpdate();
          }
          salesorderitem.setquantitytoship(toShip);
        }
        so.setitems(salesorderitem);
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
        if (pstmt2 != null) pstmt2.close();
        pstmt2 = null;
      }
      catch (SQLException sqle) {}
    }
  }

  public void GetSalesOrderItemsReady(Connection conn, SalesOrder so) throws
      ServletException {
    try {
      String openselect = select + " and a.status = 'Ready to Ship'";
      pstmt = conn.prepareStatement(openselect);
      pstmt.setInt(1, so.getid());
      rs = pstmt.executeQuery();
      while (rs.next()) {
        SalesOrderItem salesorderitem = new SalesOrderItem();
        int col = 0;
        salesorderitem.setid(rs.getInt(++col));
        salesorderitem.setitemnumber(rs.getInt(++col));
        salesorderitem.settrxtype(rs.getString(++col));
        salesorderitem.setisin(rs.getString(++col));
        salesorderitem.setproductname(rs.getString(++col));
        salesorderitem.setmanufacturerid(rs.getInt(++col));
        salesorderitem.setmanufacturer(rs.getString(++col));
        salesorderitem.setquantity(rs.getInt(++col));
        salesorderitem.setquantitytoship(rs.getInt(++col));
        salesorderitem.setshipped(rs.getInt(++col));
        salesorderitem.setunitprice(rs.getDouble(++col));
        salesorderitem.setshippingweight(rs.getDouble(++col));
        salesorderitem.sethandlingcharges(rs.getDouble(++col));
        salesorderitem.setstatus(rs.getString(++col));
        salesorderitem.setitemstatus(rs.getString(++col));
        salesorderitem.setexchangeid(rs.getString(++col));
        salesorderitem.setgiftoption(rs.getBoolean(++col));
        salesorderitem.setdistributor(rs.getString(++col));
        salesorderitem.setzipcode(rs.getString(++col));
        salesorderitem.setcountry(rs.getString(++col));
        so.setitems(salesorderitem);
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
