package com.storefront.storefrontbeans;

import java.util.*;
import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class DistributorInventoryBean
    extends BaseBean {
  final static private String fields[] = {
      "distributorid", "itemid", "onhand", };
  final static private String tablename = "distributorinventory";

  public DistributorInventoryBean() throws ServletException {
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

  public AddDistributorInventoryResponse AddDistributorInventory(
      AddDistributorInventoryRequest request) throws ServletException {

    AddDistributorInventoryResponse response = new
        AddDistributorInventoryResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getInsertString());
      DistributorInventory distributorinventory = (DistributorInventory)
          request.getdistributorinventory();
      int col = 0;
      pstmt.setInt(++col, distributorinventory.getdistributorid());
      pstmt.setInt(++col, distributorinventory.getitemid());
      pstmt.setInt(++col, distributorinventory.getonhand());
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
    return response;
  }

  public UpdateDistributorInventoryResponse UpdateDistributorInventory(
      UpdateDistributorInventoryRequest request) throws ServletException {

    UpdateDistributorInventoryResponse response = new
        UpdateDistributorInventoryResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getUpdateString());
      DistributorInventory distributorinventory = (DistributorInventory)
          request.getdistributorinventory();
      int col = 0;
      pstmt.setInt(++col, distributorinventory.getdistributorid());
      pstmt.setInt(++col, distributorinventory.getitemid());
      pstmt.setInt(++col, distributorinventory.getonhand());
      pstmt.setInt(++col, distributorinventory.getid());
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
    return response;
  }

  public DeleteDistributorInventoryResponse DeleteDistributorInventory(
      DeleteDistributorInventoryRequest request) throws ServletException {

    DeleteDistributorInventoryResponse response = new
        DeleteDistributorInventoryResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(
          "delete from distributorinventory where id = ?");
      pstmt.setInt(1, request.getid());
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
    return response;
  }

  DistributorInventory GetDistributorInventoryByID(Connection conn, int id) throws
      ServletException {

    DistributorInventory distributorinventory = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getSelectWithIDByIDString());
      pstmt.setInt(1, id);

      rs = pstmt.executeQuery();
      while (rs.next()) {
        distributorinventory = new DistributorInventory();
        int col = 0;
        distributorinventory.setid(rs.getInt(++col));
        distributorinventory.setdistributorid(rs.getInt(++col));
        distributorinventory.setitemid(rs.getInt(++col));
        distributorinventory.setonhand(rs.getInt(++col));
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
    return distributorinventory;
  }

  Vector GetDistributorInventoryByDistributor(Connection conn,
                                              int id) throws ServletException {

    Vector inventories = new Vector();
    try {
      conn = datasource.getConnection();
      String query = getSelectWithIDString() +
          " where distributorid = ? order by itemid";

      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, id);

      rs = pstmt.executeQuery();
      while (rs.next()) {
        DistributorInventory distributorinventory = new DistributorInventory();
        int col = 0;
        distributorinventory.setid(rs.getInt(++col));
        distributorinventory.setdistributorid(rs.getInt(++col));
        distributorinventory.setitemid(rs.getInt(++col));
        distributorinventory.setonhand(rs.getInt(++col));
        inventories.add(distributorinventory);
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
    return inventories;
  }

  Vector GetDistributorInventoryByItem(Connection conn,
                                       int id) throws ServletException {

    Vector inventories = new Vector();
    try {
      conn = datasource.getConnection();
      String query = getSelectWithIDString() +
          " where itemid = ? order by distributorid";

      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, id);

      rs = pstmt.executeQuery();
      while (rs.next()) {
        DistributorInventory distributorinventory = new DistributorInventory();
        int col = 0;
        distributorinventory.setid(rs.getInt(++col));
        distributorinventory.setdistributorid(rs.getInt(++col));
        distributorinventory.setitemid(rs.getInt(++col));
        distributorinventory.setonhand(rs.getInt(++col));
        inventories.add(distributorinventory);
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
    return inventories;
  }

  public GetDistributorInventoryResponse GetDistributorInventory(
      GetDistributorInventoryRequest request) throws ServletException {

    GetDistributorInventoryResponse response = new
        GetDistributorInventoryResponse();
    Connection conn = null;
    DistributorInventory distributorinventory = null;
    try {
      conn = datasource.getConnection();
      String query = getSelectWithIDString();
      String where = null;
      if (request.getdistributorid() > 0) {
        where = (where == null) ? " where distributorid = ?" :
            where + " and distributorid = ?";
      }
      if (request.getitemid() > 0) {
        where = (where == null) ? " where itemid = ?" :
            where + " and itemid = ?";
      }

      pstmt = conn.prepareStatement(query);
      if (request.getdistributorid() > 0) {
        pstmt.setInt(1, request.getdistributorid());
      }
      if (request.getitemid() > 0) {
        pstmt.setInt(1, request.getitemid());
      }

      rs = pstmt.executeQuery();
      while (rs.next()) {
        distributorinventory = new DistributorInventory();
        int col = 0;
        distributorinventory.setid(rs.getInt(++col));
        distributorinventory.setdistributorid(rs.getInt(++col));
        distributorinventory.setitemid(rs.getInt(++col));
        distributorinventory.setonhand(rs.getInt(++col));
        response.setdistributorinventory(distributorinventory);
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
