package com.storefront.storefrontbeans;

import java.util.*;
import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class CustomersThatBoughtBean
    extends BaseBean {

  private DetailsBean detailsBean = new DetailsBean();

  final static private String select1 =
      "select DISTINCT c.customerid from salesorder a, salesorderitem b, salesorder c, salesorderitem d " +
      "where  a.id=? and a.id=b.salesorderid and b.itemnumber=d.itemnumber and c.id=d.salesorderid and " +
      "c.customerid != a.customerid";
  final static private String select1_b =
      " and d.itemnumber not in (";

  final static private String select2 =
      "select b.itemnumber from salesorder a, salesorderitem b" +
      "where a.id=b.salesorderid and a.customerid in (";

  final static private String select2_b =
      " and b.itemnumber not in (";

  final static private String select =
      "select DISTINCT a.id, a.catalog, isin, productname, manufacturer, b.name, " +
      "distributor, c.name, quantity, allocated, backordered, minimumonhand, " +
      "reorderquantity, quantityonorder, a.status, d.status, listPrice, ourPrice, ourcost " +
      "from item a, manufacturer b, distributor c, itemstatus d " +
      "where and a.id = f.itemnumber and b.id=manufacturer and c.id=distributor " +
      "and d.id=a.status and a.id in (";

  final static private String orderby =
      "order by a.productname";

  public CustomersThatBoughtBean() throws ServletException {
    super();
  }

  String[] getfields() {
    return null;
  }

  String gettableName() {
    return null;
  }

  String getindexName() {
    return null;
  }

  public CustomersThatBoughtResponse GetCustomersThatBought(
      CustomersThatBoughtRequest request) throws
      ServletException {

    CustomersThatBoughtResponse response = new CustomersThatBoughtResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      SalesOrderItem soitem = null;
      SalesOrder salesorder = new SalesOrderBean().getSalesOrder(conn,
          request.getid());
      StringBuffer query = new StringBuffer(select1);
      if (salesorder != null) {
        Iterator it = salesorder.getitemsIterator();
        if (it.hasNext()) {
          query.append(select1_b);
          soitem = (SalesOrderItem) it.next();
          query.append(Integer.toBinaryString(soitem.getitemnumber()));
          while (it.hasNext()) {
            soitem = (SalesOrderItem) it.next();
            query.append(", ");
            query.append(Integer.toBinaryString(soitem.getitemnumber()));
          }
          query.append(")");
        }
      }
      pstmt = conn.prepareStatement(query.toString());
      pstmt.setInt(1, request.getid());
      rs = pstmt.executeQuery();

      int i = 0;
      int max = request.getcount();
      if (rs.next()) {
        i++;
        query = new StringBuffer(select2);
        query.append(rs.getString(1));
        while (rs.next() && (max == 0 || i < max)) {
          i++;
          query.append(", ");
          query.append(rs.getString(1));
        }
        query.append(")");
        Iterator it = salesorder.getitemsIterator();
        if (it.hasNext()) {
          query.append(select2_b);
          soitem = (SalesOrderItem) it.next();
          query.append(Integer.toBinaryString(soitem.getitemnumber()));
          while (it.hasNext()) {
            soitem = (SalesOrderItem) it.next();
            query.append(", ");
            query.append(Integer.toBinaryString(soitem.getitemnumber()));
          }
          query.append(")");
        }

        rs.close();
        pstmt.close();
        pstmt = conn.prepareStatement(query.toString());
        rs = pstmt.executeQuery();

        i = 0;
        query = new StringBuffer(select);
        if (rs.next()) {
          i++;
          query.append(rs.getString(1));
          while (rs.next() && (max == 0 || i < max)) {
            query.append(", ");
            query.append(rs.getString(1));
          }
          query.append(") ");
        }
        query.append(orderby);

        rs.close();
        pstmt.close();
        pstmt = conn.prepareStatement(query.toString());
        rs = pstmt.executeQuery();

        i = 0;
        while (rs.next() && (max == 0 || i < max)) {
          i++;
          Item item = new Item();
          item.setid(rs.getInt(1));
          item.setcatalog(rs.getInt(2));
          item.setisin(rs.getString(3));
          item.setproductname(rs.getString(4));
          item.setmanufacturerid(rs.getInt(5));
          item.setmanufacturer(rs.getString(6));
          item.setdistributorid(rs.getInt(7));
          item.setdistributor(rs.getString(8));
          item.setquantity(rs.getInt(9));
          item.setallocated(rs.getInt(10));
          item.setbackordered(rs.getInt(11));
          item.setminimumonhand(rs.getInt(12));
          item.setreorderquantity(rs.getInt(13));
          item.setquantityonorder(rs.getInt(14));
          item.setstatusid(rs.getInt(15));
          item.setstatus(rs.getString(16));
          item.setlistprice(rs.getDouble(17));
          item.setourprice(rs.getDouble(18));
          item.setourcost(rs.getDouble(19));
          detailsBean.GetDetail(conn, item);
          response.setitems(item);
        }
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
