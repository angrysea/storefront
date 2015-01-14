package com.storefront.storefrontbeans;

import javax.servlet.*;
import java.sql.*;
import javax.naming.*;
import javax.mail.*;
import javax.mail.internet.*;
import com.adaptinet.transmitter.*;
import com.storefront.storefrontrepository.*;

public class EMailListBean
    extends BaseBean {
  final static private String fields[] = {
      "email", "optout", "creationdate"};
  final static private String tablename = "emaillist";

  public EMailListBean() throws ServletException {
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

  public AddEMailListResponse AddEMailList(AddEMailListRequest request) throws
      ServletException {

    AddEMailListResponse response = new AddEMailListResponse();
    Connection conn = null;
    try {
      EMailList emaillist = request.getemaillist();
      conn = datasource.getConnection();
      EMailList el = getIdFromEmail(conn, emaillist.getemail());
      if (el != null && el.getid() > 0) {
        OptInofMailList(emaillist.getemail(), emaillist.getoptout());
        response.setid(0);
      }
      else {
        response.setid(addEMailList(conn, emaillist.getemail(),
                                    emaillist.getoptout()));
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

  int addEMailList(Connection conn, String email, boolean optout) throws
      SQLException {

    int id = 0;
    try {
      if (email != null && email.length() > 0) {
        pstmt = conn.prepareStatement(getInsertString());
        int col = 0;
        pstmt.setString(++col, email);
        pstmt.setBoolean(++col, optout);
        pstmt.setTimestamp(++col,
                           new Timestamp(new java.util.Date().getTime()));
        id = pstmt.executeUpdate();
      }
    }
    catch (SQLException ex) {
      id = 0;
    }
    finally {
      try {
        if (pstmt != null) pstmt.close();
        pstmt = null;
      }
      catch (SQLException sqle) {}
    }
    return id;
  }

  public UpdateEMailListResponse UpdateEMailList(UpdateEMailListRequest request) throws ServletException {

    UpdateEMailListResponse response = new UpdateEMailListResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      updateEMailList(conn, request.getemaillist());
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

  void updateEMailList(Connection conn, EMailList emaillist) throws ServletException {

    UpdateEMailListResponse response = new UpdateEMailListResponse();
    try {
      pstmt = conn.prepareStatement("update emaillist set email=?, optout=? where id=?");
      int col = 0;
      pstmt.setString(++col, emaillist.getemail());
      pstmt.setBoolean(++col, emaillist.getoptout());
      pstmt.setInt(++col, emaillist.getid());
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

  EMailList getIdFromEmail(Connection conn, String request) throws ServletException {

    EMailList emaillist = null;
    ResultSet rs = null;

    try {
      pstmt = conn.prepareStatement("select id, optout from emaillist where email=?");
      pstmt.setString(1, request);
      rs = pstmt.executeQuery();
      if(rs.next()) {
        emaillist = new EMailList();
        int col=0;
        emaillist.setid(rs.getInt(++col));
        emaillist.setoptout(rs.getBoolean(++col));
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
    return emaillist;
  }

  public void OptOutofMailList(String request) throws ServletException {

    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("update emaillist set optout=1 where email=?");
      pstmt.setString(1, request);
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
  }

  public void OptInofMailList(String request, boolean optout) throws ServletException {

    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("update emaillist set optout=? where email=?");
      pstmt.setBoolean(1, optout);
      pstmt.setString(2, request);
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
  }

  public DeleteEMailListResponse DeleteEMailList(DeleteEMailListRequest request) throws ServletException {

    DeleteEMailListResponse response = new DeleteEMailListResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("delete from emaillist where id = ?");
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

  public GetEMailListResponse GetEMailList(GetEMailListRequest request) throws
      ServletException {

    Connection conn = null;
    GetEMailListResponse response = new GetEMailListResponse();
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getSelectWithIDByIDString());
      pstmt.setInt(1, request.getid());
      rs = pstmt.executeQuery();

      if(rs.next()) {
        EMailList emaillist = new EMailList();
        int col=0;
        emaillist.setid(rs.getInt(++col));
        emaillist.setemail(rs.getString(++col));
        emaillist.setoptout(rs.getBoolean(++col));
        emaillist.setcreationdate(new java.util.Date(rs.getTimestamp(++col).getTime()));
        response.setemaillist(emaillist);
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

  public GetEMailListsResponse GetEMailLists(GetEMailListsRequest request) throws
      ServletException {

    Connection conn = null;
    GetEMailListsResponse response = new GetEMailListsResponse();
    try {
      conn = datasource.getConnection();
      String query = getSelectWithIDString();
      if(request.getorderby()!=null) {
        query += " order by " + request.getorderby();
        if(request.getdirection()!=null)
          query += " " + request.getdirection();
      }

      pstmt = conn.prepareStatement(query);
      rs = pstmt.executeQuery();

      while(rs.next()) {
        EMailList emaillist = new EMailList();
        int col=0;
        emaillist.setid(rs.getInt(++col));
        emaillist.setemail(rs.getString(++col));
        emaillist.setoptout(rs.getBoolean(++col));
        emaillist.setcreationdate(new java.util.Date(rs.getTimestamp(++col).getTime()));
        response.setemaillist(emaillist);
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

  public void SendMail(EMailList email, String letter) throws ServletException {

    SendMail(email, letter, 0, 0);
  }

  public void SendMail(EMailList email, String letter, int coupon, int article) throws ServletException {

    MailConfiguration mailconfig = new MailConfiguration();
    Connection conn = null;

    try {
      String mailquery = "select mailhost, mailfromname, mailfromaddress, " +
            "mailauthuser, mailauthpassword, url, mailsubject " +
            "from mailconfig where id = '" + letter + "'";

      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(mailquery);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        int col = 0;
        mailconfig.setMailHost(rs.getString(++col));
        mailconfig.setMailFromName(rs.getString(++col));
        mailconfig.setMailFromAddress(rs.getString(++col));
        mailconfig.setMailAuthUser(rs.getString(++col));
        mailconfig.setMailAuthPassword(rs.getString(++col));
        mailconfig.setMailURL(rs.getString(++col));
        mailconfig.setMailSubject(rs.getString(++col));

        rs.close();
        pstmt.close();

        Context initial = new InitialContext();
        Session session = (Session) initial.lookup(
            "java:comp/env/storefrontmail");

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(mailconfig.getMailFromAddress()));

        msg.setSentDate(new java.util.Date());
        if(mailconfig.getMailSubject()!=null) {
          msg.setSubject(mailconfig.getMailSubject());
        }

        String body = new String();
        XMLTransmitter transmitter = new XMLTransmitter();
        if (mailconfig.getMailURL() != null) {
          String urlend = "";
          if(coupon>0) {
            urlend += "&coupon="+Integer.toString(coupon);
          }
          if(article>0) {
            urlend += "&article="+Integer.toString(article);
          }

          transmitter.setUrl(mailconfig.getMailURL()+email.getemail()+urlend);
          body = transmitter.doTransaction("");
          msg.setContent(body, "text/html");
        }
        else {
          if (mailconfig.getMailIntroduction() != null)
            body = mailconfig.getMailIntroduction();
          if (mailconfig.getMailSignature() != null) {
            if (body != null)
              body += " ";
            body += mailconfig.getMailSignature();
          }
          msg.setText(body);
        }

        InternetAddress dests[] = new InternetAddress[] {
            new InternetAddress(email.getemail())};
        msg.setRecipients(Message.RecipientType.TO, dests);
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

  public void SendMailToList(String letter, int coupon, int article) throws ServletException {

    MailConfiguration mailconfig = new MailConfiguration();
    Connection conn = null;

    try {
      String mailquery = "select mailhost, mailfromname, mailfromaddress, " +
            "mailauthuser, mailauthpassword, url, mailsubject " +
            "from mailconfig where id = '" + letter + "'";

      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(mailquery);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        int col = 0;
        mailconfig.setMailHost(rs.getString(++col));
        mailconfig.setMailFromName(rs.getString(++col));
        mailconfig.setMailFromAddress(rs.getString(++col));
        mailconfig.setMailAuthUser(rs.getString(++col));
        mailconfig.setMailAuthPassword(rs.getString(++col));
        mailconfig.setMailURL(rs.getString(++col));
        mailconfig.setMailSubject(rs.getString(++col));

        rs.close();
        pstmt.close();

        Context initial = new InitialContext();
        Session session = (Session) initial.lookup(
            "java:comp/env/storefrontmail");


        String body = new String();
        XMLTransmitter transmitter = new XMLTransmitter();
        pstmt = conn.prepareStatement("select email from emaillist where optout=0");
        rs = pstmt.executeQuery();
        String url = mailconfig.getMailURL();
        String urlend = "";
        if(coupon>0) {
          urlend += "&coupon="+Integer.toString(coupon);
        }
        if(article>0) {
          urlend += "&article="+Integer.toString(article);
        }

        Transport transport = session.getTransport("smtp");
        transport.connect(mailconfig.getMailHost(), mailconfig.getMailAuthUser(),
                          mailconfig.getMailAuthPassword());
        while(rs.next()) {
          Message msg = new MimeMessage(session);
          msg.setFrom(new InternetAddress(mailconfig.getMailFromAddress()));

          msg.setSentDate(new java.util.Date());
          if(mailconfig.getMailSubject()!=null) {
            msg.setSubject(mailconfig.getMailSubject());
          }
          String emailaddress=rs.getString(1);
          if (mailconfig.getMailURL() != null) {
            transmitter.setUrl(url+emailaddress+urlend);
            body = transmitter.doTransaction("");
            msg.setContent(body, "text/html");
          }
          else {
            if (mailconfig.getMailIntroduction() != null)
              body = mailconfig.getMailIntroduction();
            if (mailconfig.getMailSignature() != null) {
              if (body != null)
                body += " ";
              body += mailconfig.getMailSignature();
            }
            msg.setText(body);
          }

          try {
            InternetAddress dests[] = new InternetAddress[] {
                new InternetAddress(emailaddress)};
            msg.setRecipients(Message.RecipientType.TO, dests);
            transport.sendMessage(msg, msg.getAllRecipients());
          }
          catch (Exception exx) {
          }
        }
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




