package com.storefront.storefrontbeans;

import java.util.*;
import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;


public class PackingslipBean
    extends BaseBean {
  final static private String fields[] = {
      "customerid", "invoiceid", "shippingaddress", "trackingnumber", "carriername",
      "shippingmethodid", "salescoupon", "creationdate"};

  final static private String tablename = "packingslip";
  final static private String whereopen = " where trackingnumber is null";

  final static private String selectbydate =
      " DATE_FORMAT(creationdate,'%Y %m %d') between " +
      "DATE_FORMAT(?,'%Y %m %d') and  DATE_FORMAT(?,'%Y %m %d') order by creationdate";

  private PackingslipItemBean packingslipItemBean = new PackingslipItemBean();
  private AddressBean addressbean = new AddressBean();
  private ListsBean listsbean = new ListsBean();

  public PackingslipBean() throws ServletException {
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

  void AddPackingslip(Connection conn, Packingslip packingslip) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement(getInsertString());
      int col=0;
      pstmt.setInt(++col, packingslip.getcustomerid());
      pstmt.setInt(++col, packingslip.getinvoiceid());
      pstmt.setInt(++col, packingslip.getshipping().getid());
      pstmt.setString(++col, packingslip.gettrackingNumber());
      pstmt.setString(++col, packingslip.getcarrierName());
      pstmt.setInt(++col, packingslip.getshippingmethod().getid());
      pstmt.setString(++col, packingslip.getsalescoupon());
      pstmt.setTimestamp(++col,
                         new Timestamp(new java.util.Date().
                                       getTime()));
      if ( (pstmt.executeUpdate()) > 0) {
        packingslip.setid(getLastInsertID(conn));
        packingslipItemBean.AddPackingslipItems(conn, packingslip.getid(),
                                                packingslip.getitemsIterator());
      }
      else {
        throw new Exception("Unable to add packingslip.");
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

  public UpdatePackingslipsResponse UpdatePackingslips(UpdatePackingslipsRequest
      request) throws
      ServletException {
    UpdatePackingslipsResponse response = new UpdatePackingslipsResponse();
    Connection conn = null;
    try {
      Iterator it = request.getpackingslipIterator();
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getInsertString());
      while (it.hasNext()) {
        Packingslip packingslip = (Packingslip) it.next();
        DeletePackingslip(conn, packingslip.getid());
        int col=0;
        pstmt = conn.prepareStatement(getUpdateString());
        pstmt.setInt(++col, packingslip.getcustomerid());
        pstmt.setInt(++col, packingslip.getinvoiceid());
        pstmt.setInt(++col, packingslip.getshipping().getid());
        pstmt.setString(++col, packingslip.gettrackingNumber());
        pstmt.setString(++col, packingslip.getcarrierName());
        pstmt.setInt(++col, packingslip.getshippingmethod().getid());
        pstmt.setString(++col, packingslip.getsalescoupon());
        pstmt.setTimestamp(++col,
                           new Timestamp(packingslip.getcreationdate().
                                         getTime()));
        pstmt.setInt(++col, packingslip.getid());
        if ( (pstmt.executeUpdate()) > 0) {
          new PackingslipItemBean().UpdatePackingslipItems(conn,
              packingslip.getid(),
              packingslip.getitemsIterator());
        }
        else {
          throw new Exception("Unable to update packingslip.");
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
      try {
        if (conn != null) conn.close();
        conn = null;
      }
      catch (SQLException sqle) {}
    }
    return response;
  }

  public DeletePackingslipResponse DeletePackingslip(DeletePackingslipRequest
      request) throws
      ServletException {
    DeletePackingslipResponse response = new DeletePackingslipResponse();
    Connection conn = null;
    try {
      DeletePackingslip(conn, request.getid());
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

  public void DeletePackingslip(Connection conn, int id) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement("delete from packingslip where id = ?");
      pstmt.setInt(1, id);
      new PackingslipItemBean().DeletePackingslipItems(conn, id);
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

  public GetPackingslipResponse GetPackingslip(GetPackingslipRequest
                                               request) throws ServletException {
    GetPackingslipResponse response = new GetPackingslipResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      response.setpackingslip(GetPackingslip(conn, request.getid()));
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

  public Packingslip GetPackingslip(Connection conn, int id) throws
      ServletException {
    Packingslip packingslip = null;
    try {
      pstmt = conn.prepareStatement(getSelectWithIDByIDString());
      pstmt.setInt(1, id);
      ResultSet rs = pstmt.executeQuery();
      packingslip = new Packingslip();
      if (rs.next()) {
        packingslip = new Packingslip();
        int col=0;
        packingslip.setid(rs.getInt(++col));
        packingslip.setcustomerid(rs.getInt(++col));
        packingslip.setinvoiceid(rs.getInt(++col));
        int shippingaddrid = rs.getInt(++col);
        packingslip.settrackingNumber(rs.getString(++col));
        packingslip.setcarrierName(rs.getString(++col));
        int shippingmethodid = rs.getInt(++col);
        packingslip.setsalescoupon(rs.getString(++col));
        packingslip.setcreationdate(new java.util.Date(rs.getTimestamp(++col).
            getTime()));
        packingslip.setshipping(addressbean.GetAddress(conn, shippingaddrid));
        packingslip.setshippingmethod(listsbean.GetShippingMethod(conn,
            shippingmethodid));
        packingslipItemBean.GetPackingslipItems(conn, packingslip);
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
    return packingslip;
  }

  public GetPackingslipsResponse GetPackingslips(GetPackingslipsRequest
                                                 request) throws
      ServletException {
    GetPackingslipsResponse response = new GetPackingslipsResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      String query = getSelectWithIDString();
      boolean bwhere = false;
      if (request.getcustomerid() > 0) {
        if (bwhere == false) {
          query += " where";
          bwhere = true;
        }
        query += " customerid = ? ";
      }
      if (request.getstartdate() != null) {
        if (bwhere == false) {
          query += " where ";
          bwhere = true;
        }
        else {
          query += " and ";
        }
        query += selectbydate;
      }
      if (request.getorderby() != null) {
        query += " order by " + request.getorderby();
        if(request.getdirection()!=null)
            query += " " + request.getdirection();
      }
      pstmt = conn.prepareStatement(query);

      int col = 0;
      if (request.getcustomerid() > 0) {
        col++;
        pstmt.setInt(col, request.getcustomerid());
      }
      if (request.getstartdate() != null) {
        col++;
        pstmt.setTimestamp(col, new Timestamp(request.getstartdate().getTime()));
        col++;
        pstmt.setTimestamp(col, new Timestamp(request.getenddate().getTime()));
      }
      ResultSet rs = pstmt.executeQuery();
      Packingslip packingslip = null;
      AddressBean addressbean = new AddressBean();
      while (rs.next()) {
        packingslip = new Packingslip();
        col=0;
        packingslip.setid(rs.getInt(++col));
        packingslip.setcustomerid(rs.getInt(++col));
        packingslip.setinvoiceid(rs.getInt(++col));
        int shippingaddrid = rs.getInt(++col);
        packingslip.settrackingNumber(rs.getString(++col));
        packingslip.setcarrierName(rs.getString(++col));
        int shippingmethodid = rs.getInt(++col);
        packingslip.setsalescoupon(rs.getString(++col));
        packingslip.setcreationdate(new java.util.Date(rs.getTimestamp(++col).
            getTime()));
        packingslip.setshipping(addressbean.GetAddress(conn, shippingaddrid));
        packingslip.setshippingmethod(listsbean.GetShippingMethod(conn,
            shippingmethodid));
        packingslipItemBean.GetPackingslipItems(conn, packingslip);
        response.setpackingslip(packingslip);
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

  public GetPackingslipsResponse GetOpenPackingslips(GetPackingslipsRequest
                                                 request) throws
      ServletException {
    GetPackingslipsResponse response = new GetPackingslipsResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      String query = getSelectWithIDString() + whereopen;
      if (request.getcustomerid() > 0) {
        query += " and customerid = ? ";
      }
      if (request.getstartdate() != null) {
        query += " and ";
        query += selectbydate;
      }
      if (request.getorderby() != null) {
        query += " order by " + request.getorderby();
        if(request.getdirection()!=null)
            query += " " + request.getdirection();
      }
      pstmt = conn.prepareStatement(query);

      int col = 0;
      if (request.getcustomerid() > 0) {
        col++;
        pstmt.setInt(col, request.getcustomerid());
      }
      if (request.getstartdate() != null) {
        col++;
        pstmt.setTimestamp(col, new Timestamp(request.getstartdate().getTime()));
        col++;
        pstmt.setTimestamp(col, new Timestamp(request.getenddate().getTime()));
      }
      ResultSet rs = pstmt.executeQuery();
      Packingslip packingslip = null;
      AddressBean addressbean = new AddressBean();
      while (rs.next()) {
        col=0;
        packingslip = new Packingslip();
        packingslip.setid(rs.getInt(++col));
        packingslip.setcustomerid(rs.getInt(++col));
        packingslip.setinvoiceid(rs.getInt(++col));
        int shippingaddrid = rs.getInt(++col);
        packingslip.settrackingNumber(rs.getString(++col));
        packingslip.setcarrierName(rs.getString(++col));
        int shippingmethodid = rs.getInt(++col);
        packingslip.setsalescoupon(rs.getString(++col));
        packingslip.setcreationdate(new java.util.Date(rs.getTimestamp(++col).
            getTime()));
        packingslip.setshipping(addressbean.GetAddress(conn, shippingaddrid));
        packingslip.setshippingmethod(listsbean.GetShippingMethod(conn,
            shippingmethodid));
        packingslipItemBean.GetPackingslipItems(conn, packingslip);
        response.setpackingslip(packingslip);
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
