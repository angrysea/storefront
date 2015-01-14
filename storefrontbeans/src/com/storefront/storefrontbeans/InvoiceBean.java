package com.storefront.storefrontbeans;

import java.util.*;
import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class InvoiceBean
    extends BaseBean {
  final static private String fields[] = {
      "customerid", "billingaddress", "salesorderid", "totalcost", "discount", "discountdesc", "taxes",
      "taxesdescription", "shippingcost", "handling", "total", "paymentmethod",
      "authorizationcode", "status","creationtime"};

  final static private String bystatus =
      "status != 'closed'";
  final static private String bydate =
      "DATE_FORMAT(creationtime,'%Y %m %d') between " +
      "DATE_FORMAT(?,'%Y %m %d') and  DATE_FORMAT(?,'%Y %m %d')";

  final static private String tablename = "invoice";

  private InvoiceItemBean invoiceItemBean = new InvoiceItemBean();

  public InvoiceBean() throws ServletException {
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

  void AddInvoice(Connection conn, Invoice invoice) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement(getInsertString());
      int col=0;
      pstmt.setInt(++col, invoice.getcustomer().getid());
      pstmt.setInt(++col, invoice.getbilling().getid());
      pstmt.setInt(++col, invoice.getsalesorderid());
      pstmt.setDouble(++col, invoice.gettotalcost());
      pstmt.setDouble(++col, invoice.getdiscount());
      pstmt.setString(++col, invoice.getdiscountdescription());
      pstmt.setDouble(++col, invoice.gettaxes());
      pstmt.setString(++col, invoice.gettaxesdescription());
      pstmt.setDouble(++col, invoice.getshippingcost());
      pstmt.setDouble(++col, invoice.gethandling());
      pstmt.setDouble(++col, invoice.gettotal());
      pstmt.setInt(++col, invoice.getpaymentmethod());
      pstmt.setString(++col, invoice.getauthorizationcode());
      pstmt.setString(++col, invoice.getstatus());
      pstmt.setTimestamp(++col,
                         new Timestamp(new java.util.Date().
                                       getTime()));
      if ( (pstmt.executeUpdate()) > 0) {
        invoice.setid(getLastInsertID(conn));
        invoiceItemBean.AddInvoiceItems(conn, invoice.getid(),
                                        invoice.getitemsIterator());
      }
      else {
        throw new Exception("Unable to add invoice.");
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

  public void DeleteInvoice(Connection conn, int id) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement("delete from invoice where id = ?");
      pstmt.setInt(1, id);
      new InvoiceItemBean().DeleteInvoiceItems(conn, id);
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

  public GetInvoiceResponse GetInvoice(GetInvoiceRequest
                                               request) throws ServletException {
    GetInvoiceResponse response = new GetInvoiceResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      response.setinvoice(GetInvoice(conn, request.getid()));
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

  public Invoice GetInvoice(Connection conn, int id) throws
      ServletException {
    Invoice invoice = null;
    CustomerBean customerbean = new CustomerBean();
    AddressBean addressbean = new AddressBean();
    try {
      pstmt = conn.prepareStatement(getSelectWithIDByIDString());
      pstmt.setInt(1, id);
      ResultSet rs = pstmt.executeQuery();
      invoice = new Invoice();
      if (rs.next()) {
        invoice = new Invoice();
        int col=0;
        invoice.setid(rs.getInt(++col));
        invoice.setcustomer(customerbean.GetCustomer(conn, rs.getInt(++col)));
        invoice.setbilling(addressbean.GetAddress(conn, rs.getInt(++col)));
        invoice.setsalesorderid(rs.getInt(++col));
        invoice.settotalcost(rs.getDouble(++col));
        invoice.setdiscount(rs.getDouble(++col));
        invoice.setdiscountdescription(rs.getString(++col));
        invoice.settaxes(rs.getDouble(++col));
        invoice.settaxesdescription(rs.getString(++col));
        invoice.setshippingcost(rs.getDouble(++col));
        invoice.sethandling(rs.getDouble(++col));
        invoice.settotal(rs.getDouble(++col));
        invoice.setpaymentmethod(rs.getInt(++col));
        invoice.setauthorizationcode(rs.getString(++col));
        invoice.setstatus(rs.getString(++col));
        invoice.setcreationdate(new java.util.Date(rs.getTimestamp(++col).
            getTime()));
        invoiceItemBean.GetInvoiceItems(conn, invoice);
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
    return invoice;
  }

  public GetInvoicesResponse GetInvoices(GetInvoicesRequest
                                         request) throws ServletException {
    GetInvoicesResponse response = new GetInvoicesResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      Invoice invoice = null;
      CustomerBean customerbean = new CustomerBean();
      AddressBean addressbean = new AddressBean();
      String query = getSelectWithIDString();
      String where = null;
      String orderby = " order by ";

      if (request.getcustomerid() > 0)
        where += "customerid = ? ";

      if (request.getstartdate() != null) {
        where = (where==null) ? (where = " where " + bydate) : (where += " and " + bydate);
        orderby += " creationtime ";
      }
      else
        orderby += " customerid ";

      if (request.getdeclined()) {
        where = (where==null) ? (where = " where " + bystatus) : (where += " and " + bystatus);
      }

      if (where != null)
        query += where;

      if(orderby!=null)
        query += orderby;

      pstmt = conn.prepareStatement(query);
      int col = 0;
      if (request.getcustomerid() > 0)
        pstmt.setInt(++col, request.getcustomerid());

      if (request.getstartdate() != null) {
        pstmt.setTimestamp(++col, new Timestamp(request.getstartdate().getTime()));
        pstmt.setTimestamp(++col, new Timestamp(request.getenddate().getTime()));
      }

      ResultSet rs = pstmt.executeQuery();
      invoice = new Invoice();
      while (rs.next()) {
        invoice = new Invoice();
        col=0;
        invoice.setid(rs.getInt(++col));
        invoice.setcustomer(customerbean.GetCustomer(conn, rs.getInt(++col)));
        invoice.setbilling(addressbean.GetAddress(conn, rs.getInt(++col)));
        invoice.setsalesorderid(rs.getInt(++col));
        invoice.settotalcost(rs.getDouble(++col));
        invoice.setdiscount(rs.getDouble(++col));
        invoice.setdiscountdescription(rs.getString(++col));
        invoice.settaxes(rs.getDouble(++col));
        invoice.settaxesdescription(rs.getString(++col));
        invoice.setshippingcost(rs.getDouble(++col));
        invoice.sethandling(rs.getDouble(++col));
        invoice.settotal(rs.getDouble(++col));
        invoice.setpaymentmethod(rs.getInt(++col));
        invoice.setauthorizationcode(rs.getString(++col));
        invoice.setstatus(rs.getString(++col));
        invoice.setcreationdate(new java.util.Date(rs.getTimestamp(++col).
            getTime()));
        invoiceItemBean.GetInvoiceItems(conn, invoice);
        response.setinvoice(invoice);
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
