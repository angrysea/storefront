package com.storefront.storefrontbeans;

import java.util.*;
import javax.servlet.*;
import java.sql.*;
import javax.naming.*;
import javax.mail.*;
import javax.mail.internet.*;
import com.storefront.storefrontrepository.*;
import com.adaptinet.transmitter.*;

public class CouponBean
    extends BaseBean {
  final static private String fields[] = {
      "code", "itemid", "manufacturerid", "description", "quantityLimit", "quantityrequired", "priceminimum",
      "discount", "discounttype", "precludes", "oneperhousehold", "singleuse", "display", "imageurl", "expirationdate"};
  final static private String select =
      "select id, code, itemid, manufacturerid, description, quantityLimit, quantityrequired, priceminimum, " +
      "discount, discounttype, precludes, oneperhousehold, singleuse, display, imageurl, " +
      "expirationdate from coupon where code = ?";
  final static private String selectbyid =
      "select id, code, itemid, manufacturerid, description, quantityLimit, quantityrequired, priceminimum, " +
      "discount, discounttype, precludes, oneperhousehold, singleuse, display, imageurl, " +
      "expirationdate from coupon where id = ?";
  final static private String selectall =
      "select a.id, code, itemid, manufacturerid, description, quantityLimit, quantityrequired, priceminimum, " +
      "discount, discounttype, precludes, oneperhousehold, singleuse, display, imageurl, count(b.id), expirationdate " +
      "from coupon a left outer join coupontrx b on a.id=b.couponcode group by a.id";

  final static private String selectdisplayable =
      "select a.id, code, itemid, manufacturerid, description, quantityLimit, quantityrequired, priceminimum, "+
      "discount, discounttype, precludes, oneperhousehold, singleuse, display, imageurl, count(b.id), expirationdate " +
      "from coupon  a left outer join coupontrx b on a.id=b.couponcode where display = 1 and expirationdate > NOW() group by a.id";

/*      "select a.id, code, itemid, manufacturerid, description, quantityLimit, quantityrequired, priceminimum, " +
      "discount, discounttype, precludes, oneperhousehold, singleuse, display, count(b.id), expirationdate " +
      "from ( select * from coupon where display = 1 and expirationdate > NOW()) a " +
      "left outer join coupontrx b on a.id=b.couponcode group by a.id";
 */

  final static private String tablename = "coupon";

  public CouponBean() throws ServletException {
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

  public AddCouponResponse AddCoupon(AddCouponRequest request) throws
      ServletException {
    Connection conn = null;
    AddCouponResponse response = new AddCouponResponse();
    try {
      conn = datasource.getConnection();
      addCoupon(conn, request.getcoupon());
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

  void addCoupon(Connection conn, Coupon coupon) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement(getInsertString());
      int col = 0;
      pstmt.setString(++col, coupon.getcode());
      pstmt.setInt(++col, coupon.getitemid());
      pstmt.setInt(++col, coupon.getmanufacturerid());
      pstmt.setString(++col, coupon.getdescription());
      pstmt.setInt(++col, coupon.getquantityLimit());
      pstmt.setInt(++col, coupon.getquantityrequired());
      pstmt.setDouble(++col, coupon.getpriceminimum());
      pstmt.setDouble(++col, coupon.getdiscount());
      pstmt.setInt(++col, coupon.getdiscounttype());
      pstmt.setBoolean(++col, coupon.getprecludes());
      pstmt.setBoolean(++col, coupon.getoneperhousehold());
      pstmt.setBoolean(++col, coupon.getsingleuse());
      pstmt.setBoolean(++col, coupon.getdisplay());
      pstmt.setString(++col, coupon.getimageurl());
      pstmt.setTimestamp(++col, new Timestamp(coupon.getexpirationDate().getTime()));
      if ( (pstmt.executeUpdate()) > 0) {
        coupon.setid(getLastInsertID(conn));
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

  public UpdateCouponResponse UpdateCoupon(UpdateCouponRequest request) throws
      ServletException {
    Connection conn = null;
    UpdateCouponResponse response = new UpdateCouponResponse();
    try {
      conn = datasource.getConnection();
      Coupon coupon = (Coupon) request.getcoupon();
      int col = 0;
      pstmt = conn.prepareStatement(getUpdateString());
      pstmt.setString(++col, coupon.getcode());
      pstmt.setInt(++col, coupon.getitemid());
      pstmt.setInt(++col, coupon.getmanufacturerid());
      pstmt.setString(++col, coupon.getdescription());
      pstmt.setInt(++col, coupon.getquantityLimit());
      pstmt.setInt(++col, coupon.getquantityrequired());
      pstmt.setDouble(++col, coupon.getpriceminimum());
      pstmt.setDouble(++col, coupon.getdiscount());
      pstmt.setInt(++col, coupon.getdiscounttype());
      pstmt.setBoolean(++col, coupon.getprecludes());
      pstmt.setBoolean(++col, coupon.getoneperhousehold());
      pstmt.setBoolean(++col, coupon.getsingleuse());
      pstmt.setBoolean(++col, coupon.getdisplay());
      pstmt.setString(++col, coupon.getimageurl());
      pstmt.setTimestamp(++col, new Timestamp(coupon.getexpirationDate().getTime()));
      pstmt.setInt(++col, coupon.getid());
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

  public DeleteCouponResponse DeleteCoupon(DeleteCouponRequest request) throws
      ServletException {
    DeleteCouponResponse response = new DeleteCouponResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("delete from coupon where code=?");
      pstmt.setString(1, request.getcode());
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
      try {
        if (conn != null) conn.close();
        conn = null;
      }
      catch (SQLException sqle) {}
    }
    return response;
  }

  static Coupon GetCoupon(Connection conn, String request) throws
      ServletException {
    Coupon coupon = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = conn.prepareStatement(select);
      pstmt.setString(1, request);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        int col = 0;
        coupon = new Coupon();
        coupon.setid(rs.getInt(++col));
        coupon.setcode(rs.getString(++col));
        coupon.setitemid(rs.getInt(++col));
        coupon.setmanufacturerid(rs.getInt(++col));
        coupon.setdescription(rs.getString(++col));
        coupon.setquantityLimit(rs.getInt(++col));
        coupon.setquantityrequired(rs.getInt(++col));
        coupon.setpriceminimum(rs.getDouble(++col));
        coupon.setdiscount(rs.getDouble(++col));
        coupon.setdiscounttype(rs.getInt(++col));
        coupon.setprecludes(rs.getBoolean(++col));
        coupon.setoneperhousehold(rs.getBoolean(++col));
        coupon.setsingleuse(rs.getBoolean(++col));
        coupon.setdisplay(rs.getBoolean(++col));
        coupon.setimageurl(rs.getString(++col));
        Timestamp ts = rs.getTimestamp(++col);
        if(ts!=null)
          coupon.setexpirationDate(new java.util.Date(ts.getTime()));
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
    return coupon;
  }

  static Coupon GetCoupon(Connection conn, int request) throws
      ServletException {
    Coupon coupon = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = conn.prepareStatement(selectbyid);
      pstmt.setInt(1, request);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        int col = 0;
        coupon = new Coupon();
        coupon.setid(rs.getInt(++col));
        coupon.setcode(rs.getString(++col));
        coupon.setitemid(rs.getInt(++col));
        coupon.setmanufacturerid(rs.getInt(++col));
        coupon.setdescription(rs.getString(++col));
        coupon.setquantityLimit(rs.getInt(++col));
        coupon.setquantityrequired(rs.getInt(++col));
        coupon.setpriceminimum(rs.getDouble(++col));
        coupon.setdiscount(rs.getDouble(++col));
        coupon.setdiscounttype(rs.getInt(++col));
        coupon.setprecludes(rs.getBoolean(++col));
        coupon.setoneperhousehold(rs.getBoolean(++col));
        coupon.setsingleuse(rs.getBoolean(++col));
        coupon.setdisplay(rs.getBoolean(++col));
        coupon.setimageurl(rs.getString(++col));
        Timestamp ts = rs.getTimestamp(++col);
        if(ts!=null)
          coupon.setexpirationDate(new java.util.Date(ts.getTime()));
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
    return coupon;
  }

  static void RedeemCoupons(Connection conn, SalesOrder salesOrder) throws
      ServletException {

    PreparedStatement pstmt = null;

    try {
      pstmt = conn.prepareStatement(
          "insert into coupontrx (customerid, salesorderid, couponcode, redemptiondate) values (?, ?, ?, ?)");
      StringTokenizer tokenizer = new StringTokenizer(salesOrder.getcouponcode());
      while (tokenizer.hasMoreTokens()) {
        String token = tokenizer.nextToken();
        Coupon coupon = CouponBean.GetCoupon(conn, token);
        int col = 0;
        pstmt.setInt(++col, salesOrder.getcustomer().getid());
        pstmt.setInt(++col, salesOrder.getid());
        pstmt.setInt(++col, coupon.getid());
        pstmt.setTimestamp(++col, new Timestamp(new java.util.Date().getTime()));
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
    }
  }

  static boolean IsCouponRedeemed(Connection conn, int customerid, int salesorderid, Coupon coupon) throws
      ServletException {

    boolean bRet = false;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      int col=0;
      if(coupon.getsingleuse()) {
        pstmt = conn.prepareStatement(
            "select redemptiondate from coupontrx where couponcode=? and salesorderid!=?");
        pstmt.setInt(++col, coupon.getid());
        pstmt.setInt(++col, salesorderid);
      }
      else if(coupon.getoneperhousehold()) {
        pstmt = conn.prepareStatement("select redemptiondate from coupontrx where customerid=? and salesorderid!=? and couponcode=?");
        pstmt.setInt(++col, customerid);
        pstmt.setInt(++col, salesorderid);
        pstmt.setInt(++col, coupon.getid());
      }
      else {
        return false;
      }
      rs = pstmt.executeQuery();
      bRet = rs.next();
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
    return bRet;
  }

  public GetCouponResponse GetCoupon(GetCouponRequest request) throws
      ServletException {
    GetCouponResponse response = new GetCouponResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      if(request.getid()>0) {
        response.setcoupon(getCoupon(conn, request.getid()));
      }
      else {
      response.setcoupon(getCoupon(conn, request.getcode()));
      }
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

  Coupon getCoupon(Connection conn, String code) throws
      ServletException {
    Coupon coupon = null;
    try {
      pstmt = conn.prepareStatement(select);
      pstmt.setString(1, code);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        int col=0;
        coupon = new Coupon();
        coupon.setid(rs.getInt(++col));
        coupon.setcode(rs.getString(++col));
        coupon.setitemid(rs.getInt(++col));
        coupon.setmanufacturerid(rs.getInt(++col));
        coupon.setdescription(rs.getString(++col));
        coupon.setquantityLimit(rs.getInt(++col));
        coupon.setquantityrequired(rs.getInt(++col));
        coupon.setpriceminimum(rs.getDouble(++col));
        coupon.setdiscount(rs.getDouble(++col));
        coupon.setdiscounttype(rs.getInt(++col));
        coupon.setprecludes(rs.getBoolean(++col));
        coupon.setoneperhousehold(rs.getBoolean(++col));
        coupon.setsingleuse(rs.getBoolean(++col));
        coupon.setdisplay(rs.getBoolean(++col));
        coupon.setimageurl(rs.getString(++col));
        Timestamp ts = rs.getTimestamp(++col);
        if(ts!=null)
          coupon.setexpirationDate(new java.util.Date(ts.getTime()));
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
    return coupon;
  }

  Coupon getCoupon(Connection conn, int id) throws
      ServletException {
    Coupon coupon = null;
    try {
      pstmt = conn.prepareStatement(selectbyid);
      pstmt.setInt(1, id);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        int col=0;
        coupon = new Coupon();
        coupon.setid(rs.getInt(++col));
        coupon.setcode(rs.getString(++col));
        coupon.setitemid(rs.getInt(++col));
        coupon.setmanufacturerid(rs.getInt(++col));
        coupon.setdescription(rs.getString(++col));
        coupon.setquantityLimit(rs.getInt(++col));
        coupon.setquantityrequired(rs.getInt(++col));
        coupon.setpriceminimum(rs.getDouble(++col));
        coupon.setdiscount(rs.getDouble(++col));
        coupon.setdiscounttype(rs.getInt(++col));
        coupon.setprecludes(rs.getBoolean(++col));
        coupon.setoneperhousehold(rs.getBoolean(++col));
        coupon.setsingleuse(rs.getBoolean(++col));
        coupon.setdisplay(rs.getBoolean(++col));
        coupon.setimageurl(rs.getString(++col));
        Timestamp ts = rs.getTimestamp(++col);
        if(ts!=null)
          coupon.setexpirationDate(new java.util.Date(ts.getTime()));
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
    return coupon;
  }

  public GetCouponsResponse GetCoupons(GetCouponsRequest request) throws
      ServletException {
    GetCouponsResponse response = new GetCouponsResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      if(request.getdisplayable()) {
        pstmt = conn.prepareStatement(selectdisplayable);
      }
      else {
        pstmt = conn.prepareStatement(selectall);
      }
      rs = pstmt.executeQuery();
      while (rs.next()) {
        int col=0;
        Coupon coupon = new Coupon();
        coupon.setid(rs.getInt(++col));
        coupon.setcode(rs.getString(++col));
        coupon.setitemid(rs.getInt(++col));
        coupon.setmanufacturerid(rs.getInt(++col));
        coupon.setdescription(rs.getString(++col));
        coupon.setquantityLimit(rs.getInt(++col));
        coupon.setquantityrequired(rs.getInt(++col));
        coupon.setpriceminimum(rs.getDouble(++col));
        coupon.setdiscount(rs.getDouble(++col));
        coupon.setdiscounttype(rs.getInt(++col));
        coupon.setprecludes(rs.getBoolean(++col));
        coupon.setoneperhousehold(rs.getBoolean(++col));
        coupon.setsingleuse(rs.getBoolean(++col));
        coupon.setdisplay(rs.getBoolean(++col));
        coupon.setimageurl(rs.getString(++col));
        coupon.setredemptions(rs.getInt(++col));
        Timestamp ts = rs.getTimestamp(++col);
        if(ts!=null)
          coupon.setexpirationDate(new java.util.Date(ts.getTime()));
        response.setcoupons(coupon);
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

  void generateSalesOrderCoupon(Company company, SalesOrder salesorder) throws Exception {

    Connection conn = null;
    try {
      if(company.getsalesordercoupon()!=null &&
         company.getsalesordercoupon().length()>0) {
        conn = datasource.getConnection();
        Coupon coupon = getCoupon(conn, company.getsalesordercoupon());
        generateCoupon(conn, coupon);
        salesorder.setsalescoupon(coupon.getcode());
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
  }

  void generateCoupon(Connection conn, Coupon coupon) throws Exception {
    try {
      coupon.setexpirationDate(new java.util.Date(new java.util.Date().getTime()+2592900000l));
      coupon.setcode(Long.toString(coupon.getexpirationDate().getTime()));
      coupon.setdescription(coupon.getdescription());
      addCoupon(conn, coupon);
    }
    catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  public SendFriendsCouponsResponse SendFriendsCoupons(SendFriendsCouponsRequest request) throws
      ServletException {

    SendFriendsCouponsResponse response = null;
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      SalesOrderBean sobean = new SalesOrderBean();
      SalesOrder salesorder = sobean.getSalesOrder(conn, request.getsalesorderid());
      CouponBook book = new CouponBook();
      String code = request.getcoupon().getcode()+Integer.toString(salesorder.getid());
      Iterator it = salesorder.getitemsIterator();
      int i = 0;
      while(it.hasNext()) {
        SalesOrderItem item = (SalesOrderItem)it.next();
        Coupon coupon = new Coupon();
        coupon.setcode(code+Integer.toString(++i));
        coupon.setitemid(item.getid());
        coupon.setmanufacturerid(request.getcoupon().getmanufacturerid());
        coupon.setdescription(coupon.getdescription() + " " + item.getproductname());
        coupon.setquantityLimit(request.getcoupon().getquantityLimit());
        coupon.setquantityrequired(request.getcoupon().getquantityrequired());
        coupon.setpriceminimum(request.getcoupon().getpriceminimum());
        coupon.setdiscount(request.getcoupon().getdiscount());
        coupon.setdiscounttype(request.getcoupon().getdiscounttype());
        coupon.setprecludes(request.getcoupon().getprecludes());
        coupon.setoneperhousehold(request.getcoupon().getoneperhousehold());
        coupon.setsingleuse(request.getcoupon().getsingleuse());
        coupon.setdisplay(request.getcoupon().getdisplay());
        coupon.setimageurl(request.getcoupon().getimageurl());
        coupon.setexpirationDate(request.getcoupon().getexpirationDate());
        addCoupon(conn, coupon);
        book.setcoupons(coupon);
      }
      StringTokenizer tokenizer = new StringTokenizer(request.getemailadresses(), ",");
      EMailListBean emailListBean = new EMailListBean();
      while (tokenizer.hasMoreTokens()) {
        String email = tokenizer.nextToken().trim();
        emailListBean.addEMailList(conn, email, false);
        sendCouponMail(book, email, conn);
      }
    }
    catch(Exception e) {
      throw new ServletException(e.getMessage());
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

  private void sendCouponMail(CouponBook book, String email, Connection conn) throws
      ServletException {

    MailConfiguration mailconfig = new MailConfiguration();

    try {
      pstmt = conn.prepareStatement("select mailhost, mailfromaddress, mailauthuser, mailauthpassword, " +
                                    "url, mailsubject from mailconfig where id = 'order'");
      rs = pstmt.executeQuery();
      if (rs.next()) {
        mailconfig.setMailHost(rs.getString(1));
        mailconfig.setMailFromAddress(rs.getString(2));
        mailconfig.setMailAuthUser(rs.getString(3));
        mailconfig.setMailAuthPassword(rs.getString(4));
        mailconfig.setMailURL(rs.getString(5));
        mailconfig.setMailSubject(rs.getString(6));

        Context initial = new InitialContext();
        Session session = (Session) initial.lookup(
            "java:comp/env/storefrontmail");

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(mailconfig.getMailFromAddress()));
        InternetAddress dests[] = new InternetAddress[] {
            new InternetAddress(email)};
        msg.setRecipients(Message.RecipientType.TO, dests);

        msg.setSentDate(new java.util.Date());
        String subject = mailconfig.getMailSubject();
        msg.setSubject(subject);

        XMLTransmitter transmitter = new XMLTransmitter();
        String url = mailconfig.getMailURL();
        Iterator it = book.getcouponsIterator();
        //+Integer.toString(salesorder.getid())
        transmitter.setUrl(url);
        String body = transmitter.doTransaction("");
        msg.setContent(body, "text/html");

        Transport transport = session.getTransport("smtp");
        transport.connect(mailconfig.getMailHost(), mailconfig.getMailAuthUser(),
                          mailconfig.getMailAuthPassword());
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();
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
}
