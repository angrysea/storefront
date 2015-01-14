package com.storefront.storefrontbeans;

import java.util.*;
import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class CustomerBean
    extends BaseBean {
  final static private String fields[] = {
      "id", "first", "last", "mi", "salutation", "suffix", "fullname",
      "nickname", "home", "mobil", "work", "fax", "email1", "email2", "email3",
      "birthdaymonth", "birthdayyear", "ccflag", "optout", "url"};

  final static private String tablename = "Customer";

  public CustomerBean() throws ServletException {
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

  String getFullName(Customer customer) {
    String fullname = new String("");
    if (customer.getsalutation() != null &&
        customer.getsalutation().length() > 0 &&
        !customer.getsalutation().equals("0")) {
      fullname += customer.getsalutation() + ".";
    }
    if (customer.getfirst() != null && customer.getfirst().length() > 0) {
      if (fullname != null)
        fullname += " ";
      fullname += customer.getfirst();
    }
    if (customer.getmi() != null && customer.getmi().length() > 0) {
      if (fullname != null)
        fullname += " ";
      fullname += customer.getmi() + ".";
    }
    if (customer.getlast() != null && customer.getlast().length() > 0) {
      if (fullname != null)
        fullname += " ";
      fullname += customer.getlast();
    }
    if (customer.getsuffix() != null &&
        customer.getsuffix().length() > 0 &&
        !customer.getsuffix().equals("0")) {
      if (fullname != null)
        fullname += ", ";
      fullname += customer.getsuffix();
    }
    return fullname;
  }

  public AddCustomerResponse AddCustomer(AddCustomerRequest request) throws
      ServletException {
    AddCustomerResponse response = new AddCustomerResponse();
    Connection conn = null;
    try {
      Customer customer = request.getcustomer();
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getInsertString());
      int col=0;
      pstmt.setInt(++col, customer.getid());
      pstmt.setString(++col, customer.getfirst());
      pstmt.setString(++col, customer.getlast());
      pstmt.setString(++col, customer.getmi());
      pstmt.setString(++col, customer.getsalutation());
      pstmt.setString(++col, customer.getsuffix());
      pstmt.setString(++col, getFullName(customer));
      pstmt.setString(++col, customer.getnickname());
      pstmt.setString(++col, customer.gethome());
      pstmt.setString(++col, customer.getmobil());
      pstmt.setString(++col, customer.getwork());
      pstmt.setString(++col, customer.getfax());
      pstmt.setString(++col, customer.getemail1());
      pstmt.setString(++col, customer.getemail2());
      pstmt.setString(++col, customer.getemail3());
      pstmt.setString(++col, customer.getbirthdaymonth());
      pstmt.setString(++col, customer.getbirthdayyear());
      pstmt.setBoolean(++col, customer.getccflag());
      pstmt.setBoolean(++col, customer.getoptout());
      pstmt.setString(++col, customer.geturl());
      if ( (pstmt.executeUpdate()) > 0) {
        response.setid(getLastInsertID(conn));
        new AddressBean().AddAdresses(conn, customer.getid(),
                                      customer.getaddressIterator());
        EMailListBean emailListBean = new EMailListBean();
        EMailList emaillist = emailListBean.getIdFromEmail(conn, customer.getemail1());
        if(emaillist==null || emaillist.getid()==0) {
          emailListBean.addEMailList(conn, customer.getemail1(), customer.getoptout());
        }
      }
      else {
        throw new Exception("Unable to add customer.");
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

  public UpdateCustomerResponse UpdateCustomer(UpdateCustomerRequest request) throws
      ServletException {
    UpdateCustomerResponse response = new UpdateCustomerResponse();
    Connection conn = null;
    try {
      Customer customer = request.getcustomer();
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getUpdateString());
      int col=0;
      pstmt.setInt(++col, customer.getid());
      pstmt.setString(++col, customer.getfirst());
      pstmt.setString(++col, customer.getlast());
      pstmt.setString(++col, customer.getmi());
      pstmt.setString(++col, customer.getsalutation());
      pstmt.setString(++col, customer.getsuffix());
      pstmt.setString(++col, getFullName(customer));
      pstmt.setString(++col, customer.getnickname());
      pstmt.setString(++col, customer.gethome());
      pstmt.setString(++col, customer.getmobil());
      pstmt.setString(++col, customer.getwork());
      pstmt.setString(++col, customer.getfax());
      pstmt.setString(++col, customer.getemail1());
      pstmt.setString(++col, customer.getemail2());
      pstmt.setString(++col, customer.getemail3());
      pstmt.setString(++col, customer.getbirthdaymonth());
      pstmt.setString(++col, customer.getbirthdayyear());
      pstmt.setBoolean(++col, customer.getccflag());
      pstmt.setBoolean(++col, customer.getoptout());
      pstmt.setString(++col, customer.geturl());
      pstmt.setInt(++col, customer.getid());
      if ( (pstmt.executeUpdate()) > 0) {
        new AddressBean().UpdateAdresses(conn, customer.getid(),
                                         customer.getaddressIterator());
        EMailListBean emailListBean = new EMailListBean();
        EMailList emaillist = emailListBean.getIdFromEmail(conn, customer.getemail1());
        if(emaillist==null || emaillist.getid()==0) {
          emailListBean.addEMailList(conn, customer.getemail1(), customer.getoptout());
        }
      }
      else {
        throw new Exception("Unable to update customer.");
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

  public UpdateCustomerResponse UpdateCustomerEmail(UpdateCustomerRequest request) throws
      ServletException {
    UpdateCustomerResponse response = new UpdateCustomerResponse();
    Connection conn = null;
    try {
      Customer customer = request.getcustomer();
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("update customer set email1=? where id=?");
      pstmt.setString(1, customer.getemail1());
      pstmt.setInt(2, customer.getid());
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

  public UpdateCreditcardResponse UpdateCreditcard(UpdateCreditcardRequest request) throws ServletException
  {
    UpdateCreditcardResponse response = null;
    SalesOrder salesorder = null;
    Connection conn = null;
    try
    {
      salesorder = new SalesOrder();
      conn = datasource.getConnection();
      Customer customer = GetCustomer(conn, request.getcustomerid());
      Iterator it = customer.getaddressIterator();
      Address address = null;
      while (it.hasNext()&& address==null) {
        address = (Address) it.next();
        if (address.gettype().equalsIgnoreCase("currentbilling"))
          break;
        else
          address=null;
      }
      if(address != null)
      {
        CreditCard cc = request.getcreditcard();
        cc.setid(address.getid());
        new CreditCardBean().UpdateCreditCard(conn, address.getid(), cc);
        response = new UpdateCreditcardResponse();
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

  public DeleteCustomerResponse DeleteCustomer(DeleteCustomerRequest request) throws
      ServletException {
    DeleteCustomerResponse response = new DeleteCustomerResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("delete from customer where id = ?");
      pstmt.setInt(1, request.getid());
      new AddressBean().DeleteAdresses(conn, request.getid());
      pstmt.executeUpdate();
      new UserBean().DeleteUser(conn, request.getid());
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

  public GetCustomerResponse GetCustomer(GetCustomerRequest
                                         request) throws ServletException {
    GetCustomerResponse response = new GetCustomerResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      response.setcustomer(GetCustomer(conn, request.getid()));
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

  public Address GetAddressCurrentbilling(int id) throws ServletException {
    Address address=null;
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      address = new AddressBean().GetAddressCurrentbilling(conn, id);
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
    return address;
  }

  public Address GetAddressCurrentShipping(int id) throws ServletException {
    Address address=null;
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      address = new AddressBean().GetAddressCurrentShipping(conn, id);
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
    return address;
  }

  public Customer GetCustomer(Connection conn, int id) throws ServletException {
    Customer customer = null;
    try {
      pstmt = conn.prepareStatement(getSelectByIDString());
      pstmt.setInt(1, id);
      ResultSet rs = pstmt.executeQuery();
      customer = new Customer();
      if (rs.next()) {
        int col=0;
        customer = new Customer();
        customer.setid(rs.getInt(++col));
        customer.setfirst(rs.getString(++col));
        customer.setlast(rs.getString(++col));
        customer.setmi(rs.getString(++col));
        customer.setsalutation(rs.getString(++col));
        customer.setsuffix(rs.getString(++col));
        customer.setfullname(rs.getString(++col));
        customer.setnickname(rs.getString(++col));
        customer.sethome(rs.getString(++col));
        customer.setmobil(rs.getString(++col));
        customer.setwork(rs.getString(++col));
        customer.setfax(rs.getString(++col));
        customer.setemail1(rs.getString(++col));
        customer.setemail2(rs.getString(++col));
        customer.setemail3(rs.getString(++col));
        customer.setbirthdaymonth(rs.getString(++col));
        customer.setbirthdayyear(rs.getString(++col));
        customer.setccflag(rs.getBoolean(++col));
        customer.setoptout(rs.getBoolean(++col));
        customer.seturl(rs.getString(++col));
        new AddressBean().GetAddresses(conn, customer);
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
    return customer;
  }

  public Customer GetCustomer(int id) throws ServletException {
    Customer customer = null;
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getSelectByIDString());
      pstmt.setInt(1, id);
      ResultSet rs = pstmt.executeQuery();
      customer = new Customer();
      if (rs.next()) {
        customer = new Customer();
        int col=0;
        customer.setid(rs.getInt(++col));
        customer.setfirst(rs.getString(++col));
        customer.setlast(rs.getString(++col));
        customer.setmi(rs.getString(++col));
        customer.setsalutation(rs.getString(++col));
        customer.setsuffix(rs.getString(++col));
        customer.setfullname(rs.getString(++col));
        customer.setnickname(rs.getString(++col));
        customer.sethome(rs.getString(++col));
        customer.setmobil(rs.getString(++col));
        customer.setwork(rs.getString(++col));
        customer.setfax(rs.getString(++col));
        customer.setemail1(rs.getString(++col));
        customer.setemail2(rs.getString(++col));
        customer.setemail3(rs.getString(++col));
        customer.setbirthdaymonth(rs.getString(++col));
        customer.setbirthdayyear(rs.getString(++col));
        customer.setccflag(rs.getBoolean(++col));
        customer.setoptout(rs.getBoolean(++col));
        customer.seturl(rs.getString(++col));
        new AddressBean().GetAddresses(conn, customer);
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
    return customer;
  }

  public GetCustomerByEmailResponse GetCustomerByEmail(
      GetCustomerByEmailRequest
      request) throws ServletException {
    GetCustomerByEmailResponse response = new GetCustomerByEmailResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      String select =
          "select id, first, last, mi, salutation, suffix, fullname, " +
          "nickname, home, mobil, work, fax, email1, email2, email3, birthdaymonth, " +
          "birthdayyear, ccflag, optout, url from customer where email = ?";
      pstmt = conn.prepareStatement(select);
      pstmt.setString(1, request.getemail());
      ResultSet rs = pstmt.executeQuery();
      Customer customer = null;
      if (rs.next()) {
        customer = new Customer();
        int col=0;
        customer.setid(rs.getInt(++col));
        customer.setfirst(rs.getString(++col));
        customer.setlast(rs.getString(++col));
        customer.setmi(rs.getString(++col));
        customer.setsalutation(rs.getString(++col));
        customer.setsuffix(rs.getString(++col));
        customer.setfullname(rs.getString(++col));
        customer.setnickname(rs.getString(++col));
        customer.sethome(rs.getString(++col));
        customer.setmobil(rs.getString(++col));
        customer.setwork(rs.getString(++col));
        customer.setfax(rs.getString(++col));
        customer.setemail1(rs.getString(++col));
        customer.setemail2(rs.getString(++col));
        customer.setemail3(rs.getString(++col));
        customer.setbirthdaymonth(rs.getString(++col));
        customer.setbirthdayyear(rs.getString(++col));
        customer.setccflag(rs.getBoolean(++col));
        customer.setoptout(rs.getBoolean(++col));
        customer.seturl(rs.getString(++col));
        new AddressBean().GetAddresses(conn, customer);
        response.setcustomer(customer);
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

  public GetCustomersResponse GetCustomers(GetCustomersRequest
                                           request) throws ServletException {
    GetCustomersResponse response = new GetCustomersResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      String select = getSelectString();
      if(request.getorderby()!=null) {
        select += " order by " + request.getorderby();
        if(request.getdirection()!=null)
            select += " " + request.getdirection();
      }
      pstmt = conn.prepareStatement(select);
      ResultSet rs = pstmt.executeQuery();
      Customer customer = null;
      while (rs.next()) {
        customer = new Customer();
        int col=0;
        customer.setid(rs.getInt(++col));
        customer.setfirst(rs.getString(++col));
        customer.setlast(rs.getString(++col));
        customer.setmi(rs.getString(++col));
        customer.setsalutation(rs.getString(++col));
        customer.setsuffix(rs.getString(++col));
        customer.setfullname(rs.getString(++col));
        customer.setnickname(rs.getString(++col));
        customer.sethome(rs.getString(++col));
        customer.setmobil(rs.getString(++col));
        customer.setwork(rs.getString(++col));
        customer.setfax(rs.getString(++col));
        customer.setemail1(rs.getString(++col));
        customer.setemail2(rs.getString(++col));
        customer.setemail3(rs.getString(++col));
        customer.setbirthdaymonth(rs.getString(++col));
        customer.setbirthdayyear(rs.getString(++col));
        customer.setccflag(rs.getBoolean(++col));
        customer.setoptout(rs.getBoolean(++col));
        customer.seturl(rs.getString(++col));
        new AddressBean().GetAddresses(conn, customer);
        response.setcustomer(customer);
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

  public GetCustomersResponse GetCustomersWithOpenOrders(GetCustomersRequest
                                           request) throws ServletException {
    GetCustomersResponse response = new GetCustomersResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      String select = "select distinct a.id, first, last, mi, salutation, suffix, " +
                "fullname, nickname, home, mobil, work, fax, email1, email2, " +
                "email3, birthdaymonth, birthdayyear, ccflag, optout, url " +
                "from customer a, salesorder b, salesorderitem c " +
                "where a.id=b.customerid and b.id = c.salesorderid and c.status != 'Shipped' ";

      if(request.getorderby()!=null)
        select += " order by " + request.getorderby();
      pstmt = conn.prepareStatement(select);
      ResultSet rs = pstmt.executeQuery();
      Customer customer = null;
      while (rs.next()) {
        customer = new Customer();
        int col=0;
        customer.setid(rs.getInt(++col));
        customer.setfirst(rs.getString(++col));
        customer.setlast(rs.getString(++col));
        customer.setmi(rs.getString(++col));
        customer.setsalutation(rs.getString(++col));
        customer.setsuffix(rs.getString(++col));
        customer.setfullname(rs.getString(++col));
        customer.setnickname(rs.getString(++col));
        customer.sethome(rs.getString(++col));
        customer.setmobil(rs.getString(++col));
        customer.setwork(rs.getString(++col));
        customer.setfax(rs.getString(++col));
        customer.setemail1(rs.getString(++col));
        customer.setemail2(rs.getString(++col));
        customer.setemail3(rs.getString(++col));
        customer.setbirthdaymonth(rs.getString(++col));
        customer.setbirthdayyear(rs.getString(++col));
        customer.setccflag(rs.getBoolean(++col));
        customer.setoptout(rs.getBoolean(++col));
        customer.seturl(rs.getString(++col));
        //new AddressBean().GetAddresses(conn, customer);
        response.setcustomer(customer);
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
