package com.storefront.storefrontbeans;

import java.util.Iterator;
import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class RelatedProductsBean
    extends BaseBean {
  final static private String fields[] = {
      "itemNumber", "relateditem" };
  final static private String tablename = "relatedproducts";

  final static private String select =
      "select DISTINCT a.id, a.catalog, isin, productname, manufacturer, b.name, " +
      "distributor, c.name, quantity, allocated, backordered, minimumonhand, " +
      "reorderquantity, quantityonorder, a.status, d.status, listPrice, ourPrice, ourcost " +
      "from Item a, manufacturer b, distributor c, itemstatus d, relatedproducts e " +
      "where e.itemnumber = ? and a.id = e.relateditem and b.id=manufacturer " +
      "and c.id=distributor and d.id=a.status";
  final static private String delete =
      "delete from relatedproducts where itemNumber=? and relateditem=?";
  final static private String deleteitem =
      "delete from relatedproducts where relateditem=? ";

  private DetailsBean detailsBean = new DetailsBean();

  public RelatedProductsBean() throws ServletException {
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

  public AddRelatedProductsResponse AddRelatedProducts(AddRelatedProductsRequest request) throws ServletException {

    AddRelatedProductsResponse response = new AddRelatedProductsResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getInsertString());
      Iterator it = request.getitemsIterator();
      while(it.hasNext()) {
        RelatedProduct relatedProduct = (RelatedProduct) it.next();
        pstmt.setInt(1, relatedProduct.getitemnumber());
        pstmt.setInt(2, relatedProduct.getrelateditem());
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

  public DeleteRelatedProductsResponse DeleteRelatedProducts(DeleteRelatedProductsRequest request) throws ServletException {

    DeleteRelatedProductsResponse response = new DeleteRelatedProductsResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(delete);
      Iterator it = request.getitemsIterator();
      while(it.hasNext()) {
        RelatedProduct relatedProduct = (RelatedProduct) it.next();
        pstmt.setInt(1, relatedProduct.getitemnumber());
        pstmt.setInt(2, relatedProduct.getrelateditem());
        pstmt.setInt(3, relatedProduct.getrelateditem());
        pstmt.setInt(4, relatedProduct.getitemnumber());
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

  static void DeleteRelatedProduct(Connection conn, int id) throws ServletException {

    DeleteRelatedProductsResponse response = new DeleteRelatedProductsResponse();
    PreparedStatement pstmt = null;
    try {
      pstmt = conn.prepareStatement(deleteitem);
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

  public GetRelatedProductsResponse GetRelatedProducts(GetRelatedProductsRequest request) throws
      ServletException {

    Connection conn = null;
    GetRelatedProductsResponse response = new GetRelatedProductsResponse();
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(select);
      pstmt.setInt(1, request.getid());
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

  public GetItemsResponse GetNotRelatedProducts(int id, GetItemsRequest request) throws
      ServletException {

    ItemBean itemBean = new ItemBean();
    GetItemsResponse items = itemBean.GetItems(request);

    Connection conn = null;
    GetItemsResponse response = new GetItemsResponse();
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("select id from relatedproducts where id = ? and itemnumber = ?");

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
