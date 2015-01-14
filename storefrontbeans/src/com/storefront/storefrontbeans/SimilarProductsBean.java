package com.storefront.storefrontbeans;

import java.util.Iterator;
import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class SimilarProductsBean
    extends BaseBean {
  final static private String fields[] = {
      "itemNumber", "similaritem" };
  final static private String tablename = "similarproducts";

  final static private String select =
      "select DISTINCT a.id, a.catalog, isin, productname, manufacturer, b.name, " +
      "distributor, c.name, quantity, allocated, backordered, minimumonhand, " +
      "reorderquantity, quantityonorder, a.status, d.status, d.availability, listPrice, ourPrice, ourcost " +
      "from item a, manufacturer b, distributor c, itemstatus d, similarproducts e " +
      "where e.itemnumber = ? and a.id = e.similaritem and b.id=manufacturer " +
      "and c.id=distributor and d.id=a.status";

  final static private String delete =
      "delete from similarproducts where (itemNumber=? and similaritem=?) or " +
      "(itemNumber=? and similaritem=?)";
  final static private String deleteitem =
      "delete from similarproducts where itemNumber=? or similaritem=? ";

  private DetailsBean detailsBean = new DetailsBean();

  public SimilarProductsBean() throws ServletException {
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

  public AddSimilarProductsResponse AddSimilarProducts(AddSimilarProductsRequest request) throws ServletException {

    AddSimilarProductsResponse response = new AddSimilarProductsResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getInsertString());
      Iterator it = request.getitemsIterator();
      while(it.hasNext()) {
        SimilarProduct similarProduct = (SimilarProduct) it.next();
        pstmt.setInt(1, similarProduct.getitemnumber());
        pstmt.setInt(2, similarProduct.getsimilaritem());
        pstmt.executeUpdate();
        pstmt.setInt(1, similarProduct.getsimilaritem());
        pstmt.setInt(2, similarProduct.getitemnumber());
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
      try {
        if (conn != null) conn.close();
        conn = null;
      }
      catch (SQLException sqle) {}
    }
    return response;
  }

  public DeleteSimilarProductsResponse DeleteSimilarProducts(DeleteSimilarProductsRequest request) throws ServletException {

    DeleteSimilarProductsResponse response = new DeleteSimilarProductsResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(delete);
      Iterator it = request.getitemsIterator();
      while(it.hasNext()) {
        SimilarProduct similarProduct = (SimilarProduct) it.next();
        pstmt.setInt(1, similarProduct.getitemnumber());
        pstmt.setInt(2, similarProduct.getsimilaritem());
        pstmt.setInt(3, similarProduct.getsimilaritem());
        pstmt.setInt(4, similarProduct.getitemnumber());
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
      try {
        if (conn != null) conn.close();
        conn = null;
      }
      catch (SQLException sqle) {}
    }
    return response;
  }

  static void DeleteSimilarProduct(Connection conn, int id) throws ServletException {

    DeleteSimilarProductsResponse response = new DeleteSimilarProductsResponse();
    PreparedStatement pstmt = null;
    try {
      pstmt = conn.prepareStatement(deleteitem);
        pstmt.setInt(1, id);
        pstmt.setInt(2, id);
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

  public GetSimilarProductsResponse GetSimilarProducts(GetSimilarProductsRequest request) throws
      ServletException {

    Connection conn = null;
    GetSimilarProductsResponse response = new GetSimilarProductsResponse();
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(select);
      pstmt.setInt(1, request.getid());
      rs = pstmt.executeQuery();

      while (rs.next()) {
        Item item = new Item();
        int col=0;
        item.setid(rs.getInt(++col));
        item.setcatalog(rs.getInt(++col));
        item.setisin(rs.getString(++col));
        item.setproductname(rs.getString(++col));
        item.setmanufacturerid(rs.getInt(++col));
        item.setmanufacturer(rs.getString(++col));
        item.setdistributorid(rs.getInt(++col));
        item.setdistributor(rs.getString(++col));
        item.setquantity(rs.getInt(++col));
        item.setallocated(rs.getInt(++col));
        item.setbackordered(rs.getInt(++col));
        item.setminimumonhand(rs.getInt(++col));
        item.setreorderquantity(rs.getInt(++col));
        item.setquantityonorder(rs.getInt(++col));
        item.setstatusid(rs.getInt(++col));
        item.setstatus(rs.getString(++col));
        item.setavailability(rs.getString(++col));
        item.setlistprice(rs.getDouble(++col));
        item.setourprice(rs.getDouble(++col));
        item.setourcost(rs.getDouble(++col));
        detailsBean.GetDetail(conn, item);
        response.setitems(item);
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

  public GetItemsResponse GetNotSimilarProducts(int id, GetItemsRequest request) throws
      ServletException {

    ItemBean itemBean = new ItemBean();
    GetItemsResponse items = itemBean.GetItems(request);

    Connection conn = null;
    GetItemsResponse response = new GetItemsResponse();
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("select id from similarproducts where itemnumber = ? and similaritem = ?");

      Iterator it = items.getitemsIterator();
      while(it.hasNext()) {
        Item item = (Item)it.next();
        if(item.getid()!=id) {
          pstmt.setInt(1, id);
          pstmt.setInt(2, item.getid());
          rs = pstmt.executeQuery();
          if(!rs.next()) {
            response.setitems(item);
          }
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
