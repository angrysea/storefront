package com.storefront.storefrontbeans;

import java.util.Iterator;
import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class StoreBean
    extends BaseBean {
  final static private String fields[] = {
      "name", "description", "address1", "address2", "address3", "city",
      "state", "zip", "country", "phone", "fax", "email", "zipstart", "zipend"};

  final static private String findstore = "select name, description, address1, " +
      "address2, address3, city, state, zip, country, phone, fax, email from store " +
      "where ? between zipstart and zipend order by zip";
  final static private String tablename = "store";

  String[] getfields() {
    return fields;
  }

  String gettableName() {
    return tablename;
  }

  String getindexName() {
    return "id";
  }

  public StoreBean() throws ServletException {
    super();
  }

  public FindStoresNearMeResponse FindStoresNearMe(FindStoresNearMeRequest request) throws
      ServletException {
    FindStoresNearMeResponse response = new FindStoresNearMeResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(findstore);
      pstmt.setString(1, request.getzip());
      rs = pstmt.executeQuery();
      if (rs.next()) {
        Store store = new Store();
        store.setname(rs.getString(1));
        store.setdescription(rs.getString(2));
        store.setaddress1(rs.getString(3));
        store.setaddress2(rs.getString(4));
        store.setaddress3(rs.getString(5));
        store.setcity(rs.getString(6));
        store.setstate(rs.getString(7));
        store.setzip(rs.getString(8));
        store.setcountry(rs.getString(9));
        store.setphone(rs.getString(10));
        store.setfax(rs.getString(11));
        store.setemail(rs.getString(12));
        response.setstore(store);
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
  public GetStoreResponse GetStore(GetStoreRequest request) throws
      ServletException {
    GetStoreResponse response = new GetStoreResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getSelectString());
      rs = pstmt.executeQuery();
      if (rs.next()) {
        Store store = new Store();
        store.setname(rs.getString(1));
        store.setdescription(rs.getString(2));
        store.setaddress1(rs.getString(3));
        store.setaddress2(rs.getString(4));
        store.setaddress3(rs.getString(5));
        store.setcity(rs.getString(6));
        store.setstate(rs.getString(7));
        store.setzip(rs.getString(8));
        store.setcountry(rs.getString(9));
        store.setphone(rs.getString(10));
        store.setfax(rs.getString(11));
        store.setemail(rs.getString(12));
        store.setstartzip(rs.getString(13));
        store.setendzip(rs.getString(14));
        response.setstore(store);
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
}
