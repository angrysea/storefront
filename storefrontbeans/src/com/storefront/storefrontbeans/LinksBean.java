package com.storefront.storefrontbeans;

import javax.servlet.*;
import java.util.*;
import java.sql.*;
import javax.naming.*;
import javax.mail.*;
import javax.mail.internet.*;
import com.storefront.storefrontrepository.*;
import com.adaptinet.transmitter.*;

public class LinksBean
    extends BaseBean {
  final static private String fields[] = {
      "companyid", "url", "header", "description", "email", "emailssent", "emailssentdate", "linkback" };
  final static private String tablename = "link";
  final static private String updmailsent = "update link set emailssent=emailssent+1, emailssentdate=? where id = ?";

  public LinksBean() throws ServletException {
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

  public AddLinkResponse AddLink(AddLinkRequest request) throws ServletException {

    AddLinkResponse response = new AddLinkResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getInsertString());
      Link link = (Link)request.getlink();
      int col = 0;
      pstmt.setInt(++col, link.getcompanyid());
      pstmt.setString(++col, link.geturl());
      pstmt.setString(++col, link.getheader());
      pstmt.setString(++col, link.getdescription());
      pstmt.setString(++col, link.getemail());
      pstmt.setInt(++col, link.getemailssent());
      pstmt.setTimestamp(++col, null);
      pstmt.setBoolean(++col, link.getlinkback());
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

  public UpdateLinkResponse UpdateLink(UpdateLinkRequest request) throws ServletException {

    UpdateLinkResponse response = new UpdateLinkResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("update link set companyid=?, url=?, header=?, description=?, email=?, linkback=? where id =?");
      Link link = (Link)request.getlink();
      int col = 0;
      pstmt.setInt(++col, link.getcompanyid());
      pstmt.setString(++col, link.geturl());
      pstmt.setString(++col, link.getheader());
      pstmt.setString(++col, link.getdescription());
      pstmt.setString(++col, link.getemail());
      pstmt.setBoolean(++col, link.getlinkback());
      pstmt.setInt(++col, link.getid());
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

  void updateMailsSent(Connection conn, int id) throws ServletException {

    try {
      pstmt = conn.prepareStatement(updmailsent);
      int col=0;
      pstmt.setTimestamp(++col, new Timestamp(new java.util.Date().getTime()));
      pstmt.setInt(++col, id);
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

  public DeleteLinkResponse DeleteLink(DeleteLinkRequest request) throws ServletException {

    DeleteLinkResponse response = new DeleteLinkResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("delete from link where id = ?");
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

  public GetLinkResponse GetLink(GetLinkRequest request) throws
      ServletException {

    Connection conn = null;
    GetLinkResponse response = new GetLinkResponse();
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getSelectWithIDByIDString());
      pstmt.setInt(1, request.getid());
      rs = pstmt.executeQuery();

      if(rs.next()) {
        Link link = new Link();
        int col=0;
        link.setid(rs.getInt(++col));
        link.setcompanyid(rs.getInt(++col));
        link.seturl(rs.getString(++col));
        link.setheader(rs.getString(++col));
        link.setdescription(rs.getString(++col));
        link.setemail(rs.getString(++col));
        link.setemailssent(rs.getInt(++col));
        Timestamp ts = rs.getTimestamp(++col);
        if (ts!=null) {
          link.setemailssentdate(new java.util.Date(ts.getTime()));
        }
        else {
          link.setemailssentdate(null);
        }
        link.setlinkback(rs.getBoolean(++col));
        response.setlink(link);
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

  public GetLinksResponse GetLinks(GetLinksRequest request) throws
      ServletException {

    Connection conn = null;
    GetLinksResponse response = new GetLinksResponse();
    try {
      conn = datasource.getConnection();
      String query = getSelectWithIDString();

      if(request.getcompanyid()==null) {
        query+=" where companyid=1";
      }
      else {
        query+=" where companyid="+request.getcompanyid();
      }

      if(request.getorderby()!=null) {
        query += " order by " + request.getorderby();
        if(request.getdirection()!=null)
          query += request.getdirection();
      }
      else {
        query += " order by id";
      }

      pstmt = conn.prepareStatement(query);
      rs = pstmt.executeQuery();

      int max = request.getmax();
      if(max==0) {
        max=Integer.MAX_VALUE;
      }
      else {
        int page = request.getpage()-1;
        if(page>0) {
          int skip = page*max;
          for(int i=0; i<skip; i++) {
            if(!rs.next())
              return response;
          }
        }
      }
      int i=0;
      for(; i<max&&rs.next(); i++) {
        Link link = new Link();
        int col=0;
        link.setid(rs.getInt(++col));
        link.setcompanyid(rs.getInt(++col));
        link.seturl(rs.getString(++col));
        link.setheader(rs.getString(++col));
        link.setdescription(rs.getString(++col));
        link.setemail(rs.getString(++col));
        link.setemailssent(rs.getInt(++col));
        Timestamp ts = rs.getTimestamp(++col);
        if (ts!=null) {
          link.setemailssentdate(new java.util.Date(ts.getTime()));
        }
        else {
          link.setemailssentdate(null);
        }
        link.setlinkback(rs.getBoolean(++col));
        response.setlink(link);
      }
      if(i<max || !rs.next()) {
        response.setlast(true);
      }
      else {
        response.setlast(false);
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

  public void sendLinkExchangeMail(int id, String email) throws ServletException {

      MailConfiguration mailconfig = new MailConfiguration();
      Connection conn = null;
      try {
        conn = datasource.getConnection();
        pstmt = conn.prepareStatement("select mailhost, mailfromaddress, " +
                                      "mailauthuser, mailauthpassword, mailintroduction, mailbody, " +
                                      "mailsignature, mailsubject from mailconfig where id = 'linkexchange'");
        rs = pstmt.executeQuery();
        if (rs.next()) {
          int col = 0;
          mailconfig.setMailHost(rs.getString(++col));
          mailconfig.setMailFromAddress(rs.getString(++col));
          mailconfig.setMailAuthUser(rs.getString(++col));
          mailconfig.setMailAuthPassword(rs.getString(++col));
          mailconfig.setMailIntroduction(rs.getString(++col));
          mailconfig.setMailBody(rs.getString(++col));
          mailconfig.setMailSignature(rs.getString(++col));
          mailconfig.setMailSubject(rs.getString(++col));

          Context initial = new InitialContext();
          Session session = (Session) initial.lookup(
              "java:comp/env/storefrontmail");

          Message msg = new MimeMessage(session);
          msg.setFrom(new InternetAddress(mailconfig.getMailFromAddress()));
          InternetAddress dests[] = new InternetAddress[] {
              new InternetAddress(email)};
          msg.setRecipients(Message.RecipientType.TO, dests);

          msg.setSentDate(new java.util.Date());
          msg.setSubject(mailconfig.getMailSubject());

          String body = mailconfig.getMailIntroduction() + " ";
          body += "\r\n\r\n";
          body += mailconfig.getMailBody();
          body += "\r\n\r\n";
          body += mailconfig.getMailSignature();
          msg.setText(body);

          Transport transport = session.getTransport("smtp");
          transport.connect(mailconfig.getMailHost(), mailconfig.getMailAuthUser(),
                            mailconfig.getMailAuthPassword());
          transport.sendMessage(msg, msg.getAllRecipients());
          transport.close();
          updateMailsSent(conn, id);
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

}
