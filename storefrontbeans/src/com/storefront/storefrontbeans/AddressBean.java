package com.storefront.storefrontbeans;

import java.util.Iterator;
import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class AddressBean
    extends BaseBean {
  final static private String fields[] = {
      "customerid", "type", "first", "last", "mi", "salutation", "suffix",
      "address1", "address2", "address3", "city", "state", "zip", "country",
      "phone", "company"};
  final static private String tablename = "address";

  final static private String update =
      "update address set type=?, first=?, last=?, " +
      "mi=?, salutation=?, suffix=?, address1=?, address2=?, address3=?, city=?, state=?, zip=?, " +
      "country=?, phone=?, company=? where id=?";

  private CreditCardBean creditCardBean = new CreditCardBean();

  public AddressBean() throws ServletException {
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

  public void AddAdresses(Connection conn, int id, Iterator it) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement(getInsertString());
      while (it.hasNext()) {
        Address address = (Address) it.next();
        pstmt.setInt(1, id);
        pstmt.setString(2, address.gettype());
        pstmt.setString(3, address.getfirst());
        pstmt.setString(4, address.getlast());
        pstmt.setString(5, address.getmi());
        pstmt.setString(6, address.getsalutation());
        pstmt.setString(7, address.getsuffix());
        pstmt.setString(8, address.getaddress1());
        pstmt.setString(9, address.getaddress2());
        pstmt.setString(10, address.getaddress3());
        pstmt.setString(11, address.getcity());
        pstmt.setString(12, address.getstate());
        pstmt.setString(13, address.getzip());
        pstmt.setString(14, address.getcountry());
        pstmt.setString(15, address.getphone());
        pstmt.setString(16, address.getcompany());
        if ( (pstmt.executeUpdate()) > 0) {
          address.setid(getLastInsertID(conn));
          creditCardBean.AddCreditCard(conn, address.getid(),
                                           address.getcreditcard());
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
    }
  }

  public void UpdateAdresses(Connection conn, int id, Iterator it) throws
      ServletException {
    PreparedStatement pstmtadd = null;
    try {
      pstmtadd = conn.prepareStatement(getInsertString());
      pstmt = conn.prepareStatement(update);
      while (it.hasNext()) {
        Address address = (Address) it.next();
        if (address.getid() < 1) {
          pstmtadd.setInt(1, id);
          pstmtadd.setString(2, address.gettype());
          pstmtadd.setString(3, address.getfirst());
          pstmtadd.setString(4, address.getlast());
          pstmtadd.setString(5, address.getmi());
          pstmtadd.setString(6, address.getsalutation());
          pstmtadd.setString(7, address.getsuffix());
          pstmtadd.setString(8, address.getaddress1());
          pstmtadd.setString(9, address.getaddress2());
          pstmtadd.setString(10, address.getaddress3());
          pstmtadd.setString(11, address.getcity());
          pstmtadd.setString(12, address.getstate());
          pstmtadd.setString(13, address.getzip());
          pstmtadd.setString(14, address.getcountry());
          pstmtadd.setString(15, address.getphone());
          pstmtadd.setString(16, address.getcompany());
          if ( (pstmtadd.executeUpdate()) > 0) {
            address.setid(getLastInsertID(conn));
            creditCardBean.AddCreditCard(conn, address.getid(),
                                         address.getcreditcard());
          }
        }
        else {
          pstmt.setString(1, address.gettype());
          pstmt.setString(2, address.getfirst());
          pstmt.setString(3, address.getlast());
          pstmt.setString(4, address.getmi());
          pstmt.setString(5, address.getsalutation());
          pstmt.setString(6, address.getsuffix());
          pstmt.setString(7, address.getaddress1());
          pstmt.setString(8, address.getaddress2());
          pstmt.setString(9, address.getaddress3());
          pstmt.setString(10, address.getcity());
          pstmt.setString(11, address.getstate());
          pstmt.setString(12, address.getzip());
          pstmt.setString(13, address.getcountry());
          pstmt.setString(14, address.getphone());
          pstmt.setString(15, address.getcompany());
          pstmt.setInt(16, address.getid());
          pstmt.executeUpdate();
          creditCardBean.UpdateCreditCard(conn, address.getid(),
                                          address.getcreditcard());
        }
      }
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

  public void DeleteAdresses(Connection conn, int id) throws ServletException {
    try {
      pstmt = conn.prepareStatement("delete from address where customerid = ?");
      pstmt.setInt(1, id);
      pstmt.executeUpdate();
      creditCardBean.DeleteCreditCard(conn, id);
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

  public void GetAddresses(Connection conn, Customer customer) throws
      ServletException {
    try {
      String select = "select id, type, first, last, mi, salutation, suffix, address1, address2, address3, " +
          "city, state, zip, country, phone, company from address where customerid = ?";
      pstmt = conn.prepareStatement(select);
      pstmt.setInt(1, customer.getid());
      rs = pstmt.executeQuery();
      while (rs.next()) {
        Address address = new Address();
        address.setid(rs.getInt(1));
        address.setcustomerid(customer.getid());
        address.settype(rs.getString(2));
        address.setfirst(rs.getString(3));
        address.setlast(rs.getString(4));
        address.setmi(rs.getString(5));
        address.setsalutation(rs.getString(6));
        address.setsuffix(rs.getString(7));
        address.setaddress1(rs.getString(8));
        address.setaddress2(rs.getString(9));
        address.setaddress3(rs.getString(10));
        address.setcity(rs.getString(11));
        address.setstate(rs.getString(12));
        address.setzip(rs.getString(13));
        address.setcountry(rs.getString(14));
        address.setphone(rs.getString(15));
        address.setcompany(rs.getString(16));
        creditCardBean.GetCreditCard(conn, address);
        customer.setaddress(address);
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

  public Address GetAddress(Connection conn, int id) throws ServletException {
    Address address = null;
    try {
      String select = "select customerid, type, first, last, mi, salutation, suffix, address1, address2, address3, " +
          "city, state, zip, country, phone, company from address where id = ?";
      pstmt = conn.prepareStatement(select);
      pstmt.setInt(1, id);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        address = new Address();
        address.setid(id);
        address.setcustomerid(rs.getInt(1));
        address.settype(rs.getString(2));
        address.setfirst(rs.getString(3));
        address.setlast(rs.getString(4));
        address.setmi(rs.getString(5));
        address.setsalutation(rs.getString(6));
        address.setsuffix(rs.getString(7));
        address.setaddress1(rs.getString(8));
        address.setaddress2(rs.getString(9));
        address.setaddress3(rs.getString(10));
        address.setcity(rs.getString(11));
        address.setstate(rs.getString(12));
        address.setzip(rs.getString(13));
        address.setcountry(rs.getString(14));
        address.setphone(rs.getString(15));
        address.setcompany(rs.getString(16));
        creditCardBean.GetCreditCard(conn, address);
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
    return address;
  }

  public Address GetAddressCurrentShipping(Connection conn, int id) throws
      ServletException {
    Address address = null;
    try {
      String select = "select id, type, first, last, mi, salutation, suffix, address1, address2, address3, " +
          "city, state, zip, country, phone, company from address where customerid = ? and type = 'currentshipping'";
      pstmt = conn.prepareStatement(select);
      pstmt.setInt(1, id);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        address = new Address();
        address.setid(rs.getInt(1));
        address.setcustomerid(id);
        address.settype(rs.getString(2));
        address.setfirst(rs.getString(3));
        address.setlast(rs.getString(4));
        address.setmi(rs.getString(5));
        address.setsalutation(rs.getString(6));
        address.setsuffix(rs.getString(7));
        address.setaddress1(rs.getString(8));
        address.setaddress2(rs.getString(9));
        address.setaddress3(rs.getString(10));
        address.setcity(rs.getString(11));
        address.setstate(rs.getString(12));
        address.setzip(rs.getString(13));
        address.setcountry(rs.getString(14));
        address.setphone(rs.getString(15));
        address.setcompany(rs.getString(16));
        creditCardBean.GetCreditCard(conn, address);
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
    return address;
  }

  public Address GetAddressCurrentbilling(Connection conn, int id) throws
      ServletException {
    Address address = null;
    try {
      String select = "select id, type, first, last, mi, salutation, suffix, address1, address2, address3, " +
          "city, state, zip, country, phone, company from address where customerid = ? and type = 'currentbilling'";
      pstmt = conn.prepareStatement(select);
      pstmt.setInt(1, id);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        address = new Address();
        address.setid(rs.getInt(1));
        address.setcustomerid(id);
        address.settype(rs.getString(2));
        address.setfirst(rs.getString(3));
        address.setlast(rs.getString(4));
        address.setmi(rs.getString(5));
        address.setsalutation(rs.getString(6));
        address.setsuffix(rs.getString(7));
        address.setaddress1(rs.getString(8));
        address.setaddress2(rs.getString(9));
        address.setaddress3(rs.getString(10));
        address.setcity(rs.getString(11));
        address.setstate(rs.getString(12));
        address.setzip(rs.getString(13));
        address.setcountry(rs.getString(14));
        address.setphone(rs.getString(15));
        address.setcompany(rs.getString(16));
        creditCardBean.GetCreditCard(conn, address);
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
    return address;
  }

  static String GetName(com.storefront.storefrontrepository.Address address) {
    String fullname = new String("");
    if (address.getsalutation() != null &&
        address.getsalutation().length() > 0) {
      fullname += address.getsalutation() + ".";
    }
    if (address.getfirst() != null && address.getfirst().length() > 0) {
      if (fullname != null)
        fullname += " ";
      fullname += address.getfirst();
    }
    if (address.getmi() != null && address.getmi().length() > 0) {
      if (fullname != null)
        fullname += " ";
      fullname += address.getmi() + ".";
    }
    if (address.getlast() != null && address.getlast().length() > 0) {
      if (fullname != null)
        fullname += " ";
      fullname += address.getlast();
    }
    if (address.getsuffix() != null && address.getsuffix().length() > 0) {
      if (fullname != null)
        fullname += ", ";
      fullname += address.getsuffix();
    }
    return fullname;
  }
}
