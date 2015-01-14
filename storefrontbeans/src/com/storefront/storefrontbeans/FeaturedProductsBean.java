package com.storefront.storefrontbeans;

import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class FeaturedProductsBean
    extends BaseBean {
  static private boolean skip = false;
  final static private String fields[] = {
      "companyid", "catalog", "sortorder", "type", "heading", "comments", "articleid", "active"};
  final static private String tablename = "featuredproducts";

  FeaturedItemBean featuredItemBean = new FeaturedItemBean();

  public FeaturedProductsBean() throws ServletException {
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

  public AddFeaturedProductResponse AddFeaturedProduct(
      AddFeaturedProductRequest request) throws ServletException {
    AddFeaturedProductResponse response = new AddFeaturedProductResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      AddFeaturedProduct(conn, request.getfeaturedproducts(), false);
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

  public AddFeaturedProductResponse AddFeaturedProductWithItems(
      AddFeaturedProductRequest request) throws ServletException {
    AddFeaturedProductResponse response = new AddFeaturedProductResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      AddFeaturedProduct(conn, request.getfeaturedproducts(), true);
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

  void AddFeaturedProduct(Connection conn,
                          FeaturedProducts featuredproducts,
                          boolean items) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement(getInsertString());
      int col=0;
      pstmt.setInt(++col, featuredproducts.getcompanyid());
      pstmt.setInt(++col, featuredproducts.getcatalog());
      pstmt.setInt(++col, featuredproducts.getsortorder());
      pstmt.setString(++col, featuredproducts.gettype());
      pstmt.setString(++col, featuredproducts.getheading());
      pstmt.setString(++col, featuredproducts.getcomments());
      pstmt.setInt(++col, featuredproducts.getarticleid());
      pstmt.setBoolean(++col, featuredproducts.getactive());
      if ( (pstmt.executeUpdate()) > 0) {
        featuredproducts.setid(getLastInsertID(conn));
        if(items)
          featuredItemBean.AddFeaturedItems(conn, featuredproducts);
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
    featuredItemBean.AddFeaturedItem(feateredid, itemid);
  }

  public UpdateFeaturedProductResponse UpdateFeaturedProduct(
      UpdateFeaturedProductRequest request) throws ServletException {
    UpdateFeaturedProductResponse response = new UpdateFeaturedProductResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      UpdateFeaturedProduct(conn, request.getfeaturedproducts(), false);
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

  public UpdateFeaturedProductResponse UpdateFeaturedProductWithItems(
      UpdateFeaturedProductRequest request) throws ServletException {
    UpdateFeaturedProductResponse response = new UpdateFeaturedProductResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      UpdateFeaturedProduct(conn, request.getfeaturedproducts(), true);
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

  void UpdateFeaturedProduct(Connection conn,
                             FeaturedProducts featuredproducts,
                             boolean items) throws ServletException {

    try {
      pstmt = conn.prepareStatement(getUpdateString());
      int col=0;
      pstmt.setInt(++col, featuredproducts.getcompanyid());
      pstmt.setInt(++col, featuredproducts.getcatalog());
      pstmt.setInt(++col, featuredproducts.getsortorder());
      pstmt.setString(++col, featuredproducts.gettype());
      pstmt.setString(++col, featuredproducts.getheading());
      pstmt.setString(++col, featuredproducts.getcomments());
      pstmt.setInt(++col, featuredproducts.getarticleid());
      pstmt.setBoolean(++col, featuredproducts.getactive());
      pstmt.setInt(++col, featuredproducts.getid());
      pstmt.executeUpdate();
      if(items)
        featuredItemBean.UpdateFeaturedItems(conn, featuredproducts);
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

  public DeleteFeaturedProductResponse DeleteFeaturedProduct(
      DeleteFeaturedProductRequest request) throws ServletException {
    DeleteFeaturedProductResponse response = new DeleteFeaturedProductResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      deleteFeaturedProducts(conn, request.getid());
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

  public void DeleteFeaturedItem(int feateredid, int itemid) throws
      ServletException {
    featuredItemBean.DeleteFeaturedItem(feateredid, itemid);
  }

  static void deleteFeaturedProducts(Connection conn, int id) throws
      ServletException {
    PreparedStatement pstmt = null;
    try {
      pstmt = conn.prepareStatement("delete from featuredproducts where id = ?");
      pstmt.setInt(1, id);
      pstmt.executeUpdate();
      FeaturedItemBean.deleteFeaturedItems(conn, id);
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

  public GetFeaturedProductsResponse GetFeaturedProducts(
      GetFeaturedProductsRequest request) throws ServletException {
    Connection conn = null;
    GetFeaturedProductsResponse response = new GetFeaturedProductsResponse();
    try {
      conn = datasource.getConnection();
      String query = getSelectWithIDString();
      String where = null;

      if (request.getid() != null) {
        where = " where id = " + request.getid();
      }
      else {
        if (request.getcompanyid() != null) {
           where =  " where companyid = " + request.getcompanyid();
        }
        else {
          where = " where companyid = 1";
        }
        if (request.getcatalog() != null) {
          where += " and catalog = " + request.getcatalog();
        }
        if (request.getactive()) {
          where += " and active > 0 ";
        }
        if(where!=null) {
        }
        where += " order by sortorder";
      }

      query += where;
      pstmt = conn.prepareStatement(query);
      ResultSet rs = pstmt.executeQuery();
      rs = pstmt.executeQuery();
      while (rs.next()) {
        FeaturedProducts product = new FeaturedProducts();
        int col=0;
        product.setid(rs.getInt(++col));
        product.setcompanyid(rs.getInt(++col));
        product.setcatalog(rs.getInt(++col));
        product.setsortorder(rs.getInt(++col));
        product.settype(rs.getString(++col));
        product.setheading(rs.getString(++col));
        product.setcomments(rs.getString(++col));
        product.setarticleid(rs.getInt(++col));
        product.setactive(rs.getBoolean(++col));
        featuredItemBean.GetFeaturedItems(conn, product);
        response.setfeaturedproducts(product);
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
