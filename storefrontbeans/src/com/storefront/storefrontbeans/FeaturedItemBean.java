package com.storefront.storefrontbeans;

import java.util.Iterator;
import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class FeaturedItemBean
    extends BaseBean {
  final static private String fields[] = {
      "featuredproductsid", "itemnumber"};

  final static private String select =
      "select DISTINCT a.id, a.catalog, isin, productname, manufacturer, b.name, " +
      "distributor, c.name, quantity, allocated, backordered, minimumonhand, " +
      "reorderquantity, quantityonorder, a.status, d.status, listPrice, ourPrice, ourcost " +
      "from item a, manufacturer b, distributor c, itemstatus d, featureditems e " +
      "where e.featuredproductsid = ? and a.id = e.itemnumber and b.id=manufacturer " +
      "and c.id=distributor and d.id=a.status order by productname";

  final static private String tablename = "featureditems";

  public FeaturedItemBean() throws ServletException {
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

  public void AddFeaturedItems(Connection conn, FeaturedProducts featuredproducts) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement(getInsertString());
      Iterator it = featuredproducts.getitemsIterator();
      while (it.hasNext()) {
        Item item = (Item) it.next();
        pstmt.setInt(1, featuredproducts.getid());
        pstmt.setInt(2, item.getid());
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

  public void AddFeaturedItem(int feateredid, int itemid) throws
      ServletException {

    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getInsertString());
      pstmt.setInt(1, feateredid);
      pstmt.setInt(2, itemid);
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
      try {
        if (conn != null) conn.close();
        conn = null;
      }
      catch (SQLException sqle) {}
    }
  }

  public void UpdateFeaturedItems(Connection conn, FeaturedProducts featuredproducts) throws
      ServletException {
    PreparedStatement pstmtadd = null;
    try {
      pstmtadd = conn.prepareStatement(getInsertString());
      DeleteFeaturedItems(conn, featuredproducts.getid());
      AddFeaturedItems(conn, featuredproducts);
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

  void DeleteFeaturedItems(Connection conn, int id) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement(
          "delete from featureditems where featuredproductsid = ?");
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

  static void deleteFeaturedItems(Connection conn, int id) throws
      ServletException {
    PreparedStatement pstmt = null;
    try {
      pstmt = conn.prepareStatement(
          "delete from featureditems where featuredproductsid = ?");
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

  void DeleteFeaturedItem(int feateredid, int itemid) throws
      ServletException {

    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(
          "delete from featureditems where featuredproductsid = ? and itemnumber = ?");
      pstmt.setInt(1, feateredid);
      pstmt.setInt(2, itemid);
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
      try {
        if (conn != null) conn.close();
        conn = null;
      }
      catch (SQLException sqle) {}
    }
  }

  void GetFeaturedItems(Connection conn, FeaturedProducts featuredProducts) throws
      ServletException {

    GetRelatedProductsResponse response = new GetRelatedProductsResponse();
    DetailsBean detailsBean = new DetailsBean();
    try {
      pstmt = conn.prepareStatement(select);
      pstmt.setInt(1, featuredProducts.getid());
      rs = pstmt.executeQuery();

      while (rs.next()) {
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
        featuredProducts.setitems(item);
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
