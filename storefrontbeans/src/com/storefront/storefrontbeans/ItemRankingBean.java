package com.storefront.storefrontbeans;

import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class ItemRankingBean
    extends BaseBean {
  final static private String fields[] = {
      "id", "itemnumber", "views", "sold"};
  final static private String tablename = "itemranking";
  final static private String selectsales =
      "select a.id, isin, productname, b.name, c.name, quantity, d.status, d.availability, " +
      "listPrice, ourPrice, ourcost, e.sold from Item a, manufacturer b, distributor c, itemstatus d, itemranking e " +
      "where b.id=a.manufacturer and c.id=a.distributor and d.id=a.status and a.id=e.itemnumber order by e.sold desc";
  final static private String selectviews =
      "select a.id, isin, productname, b.name, c.name, quantity, d.status, d.availability, " +
      "listPrice, ourPrice, ourcost, e.views from Item a, manufacturer b, distributor c, itemstatus d, itemranking e " +
      "where b.id=a.manufacturer and c.id=a.distributor and d.id=a.status and a.id=e.itemnumber order by e.views desc";

  public ItemRankingBean() throws ServletException {
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

  public void AddItemRanking(Connection conn, int id) throws ServletException {
    try {
      pstmt = conn.prepareStatement(
          "insert into itemranking (itemnumber) values (?)");
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

  public void AddItemSold(Connection conn, int id, int qty) throws ServletException {
    try {
      pstmt = conn.prepareStatement("update itemranking set sold=sold+? where itemnumber=?");
      pstmt.setInt(1, qty);
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
      }
      catch (SQLException sqle) {}
    }
  }

  public void SubtractItemSold(Connection conn, int id, int qty) throws ServletException {
    try {
      pstmt = conn.prepareStatement("update itemranking set sold=sold+? where itemnumber=?");
      pstmt.setInt(1, qty);
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
      }
      catch (SQLException sqle) {}
    }
  }

  public void UpdateItemViewed(Connection conn, int id) throws ServletException {
    try {
      pstmt = conn.prepareStatement(
          "update itemranking set views=views+1 where itemnumber=?");
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

  public void DeleteItemRanking(Connection conn, int id) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement(
          "delete from itemranking where itemnumber=?");
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

  public GetItemRankingResponse GetItemRankingSales(GetItemRankingRequest
      request) throws ServletException {
    GetItemRankingResponse response = new GetItemRankingResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(selectsales);
      rs = pstmt.executeQuery();
      Item item = null;

      for (int i = 0; rs.next() && request.getcount() > i; i++) {
        item = new Item();
        int col = 0;
        item.setid(rs.getInt(++col));
        item.setisin(rs.getString(++col));
        item.setproductname(rs.getString(++col));
        item.setmanufacturer(rs.getString(++col));
        item.setdistributor(rs.getString(++col));
        item.setquantity(rs.getInt(++col));
        item.setstatus(rs.getString(++col));
        item.setavailability(rs.getString(++col));
        item.setlistprice(rs.getDouble(++col));
        item.setourprice(rs.getDouble(++col));
        item.setourcost(rs.getDouble(++col));
        item.setrank(rs.getInt(++col));
        new DetailsBean().GetDetail(conn, item);
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

  public GetItemRankingResponse GetItemRankingViewed(GetItemRankingRequest
      request) throws ServletException {
    GetItemRankingResponse response = new GetItemRankingResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(selectviews);
      rs = pstmt.executeQuery();
      Item item = null;

      for (int i = 0; rs.next() && request.getcount() > i; i++) {
        item = new Item();
        int col = 0;
        item.setid(rs.getInt(++col));
        item.setisin(rs.getString(++col));
        item.setproductname(rs.getString(++col));
        item.setmanufacturer(rs.getString(++col));
        item.setdistributor(rs.getString(++col));
        item.setquantity(rs.getInt(++col));
        item.setstatus(rs.getString(++col));
        item.setavailability(rs.getString(++col));
        item.setlistprice(rs.getDouble(++col));
        item.setourprice(rs.getDouble(++col));
        item.setourcost(rs.getDouble(++col));
        item.setrank(rs.getInt(++col));
        new DetailsBean().GetDetail(conn, item);
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
}
