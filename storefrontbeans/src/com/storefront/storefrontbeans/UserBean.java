package com.storefront.storefrontbeans;

import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.storefront.storefrontrepository.*;
import javax.naming.*;
import javax.mail.*;
import javax.mail.internet.*;

public class UserBean
    extends BaseBean {
  final static private String fields[] = {
      "email", "pw", "affiliate", "lastsortorder", "lastlogintime", "creationdate"};
  final static private String update =
      "update users set email=?, pw=?, logintime=? where id=?";
  final static private String updateemail =
      "update users set email=? where id=?";
  final static private String updatesortorder =
      "update users set lastsortorder=? where id=?";
  final static private String select = "select a.id, email, affiliate, b.fullname, lastsortorder, lastlogintime, creationdate, logintime from users a left outer join customer b using(id) where a.id=?";
  final static private String selectbyid = "select a.id, email, affiliate, b.fullname, lastsortorder, lastlogintime, creationdate, logintime from users a left outer join customer b using(id) where a.id=?";
  final static private String selectbyemail = "select a.id, email, b.fullname, pw, lastsortorder, lastlogintime, creationdate, logintime from users a left outer join customer b using(id) where email=?";
  final static private String selectall = "select a.id, email, affiliate, b.fullname, lastsortorder, lastlogintime, creationdate, logintime from users a left outer join customer b using(id)";
  final static private String login = "select a.id, logintime from users a, company b where email=? and ? in(a.pw, b.pw)";
  final static private String loginupdate =
      "update users set logintime=?, lastlogintime=? where id=?";
  final static private String logoutupdate =
      "update users set logintime=? where id=?";
  final static private String orderbycreation = " order by creationdate";
  final static private String bydate =
      " where DATE_FORMAT(creationdate,'%Y %m %d') between " +
      "DATE_FORMAT(?,'%Y %m %d') and  DATE_FORMAT(?,'%Y %m %d') ";
  final static private String tablename = "users";
  final static private String cookiename = "storefront_userid";
  final static private String logcookiename = "storefront_loggedin";
  final static private User defaultuser = new User();

  public UserBean() throws ServletException {
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

  public int getUserID(HttpServletRequest request) {

    int id = 0;
    try {
      Cookie cookie = (Cookie)request.getAttribute("usercookie");
      if(cookie==null) {
        Cookie cookies[] = request.getCookies();
        if (cookies != null) {
          for (int i = 0; i < cookies.length && cookie == null; i++) {
            if (cookies[i].getName().equalsIgnoreCase(cookiename))
              cookie = cookies[i];
          }
        }
      }

      String value = null;
      if (cookie != null) {
        value = cookie.getValue();
      }
      else if((value = request.getParameter("user"))==null) {
        value = (String)request.getAttribute("user");
      }
      else {
        request.setAttribute("user", value);
      }

      if(value!=null) {
        id = Integer.parseInt(value);
      }
      else {
        request.setAttribute("user", "0");
      }
    }
    catch (Exception ex) {
      id = 0;
    }
    return id;
  }

  void setUserAttribute(HttpServletRequest request, int id) {
    try {
      Cookie cookie = (Cookie) request.getAttribute("usercookie");
      if (cookie!=null) {
        return;
      }

      Cookie cookies[] = request.getCookies();
      if (cookies != null) {
        for (int i = 0; i < cookies.length && cookie == null; i++) {
          if (cookies[i].getName().equalsIgnoreCase(cookiename)) {
            return;
          }
        }
      }

      request.setAttribute("user", Integer.toString(id));
      if(request.getAttribute("login")==null) {
        request.setAttribute("login", "0");
      }
    }
    catch (Exception ex) {
    }
  }

  boolean setUserCookie(HttpServletRequest request, HttpServletResponse response, int id) {

    boolean ret = true;
    try {
      Cookie cookie = (Cookie)request.getAttribute("usercookie");
      if(cookie==null) {
        cookie = new Cookie(cookiename, Integer.toString(id));
        cookie.setMaxAge(155520000);
        response.addCookie(cookie);
      }
      else if(id>0) {
        cookie.setValue(Integer.toString(id));
      }
      setUserAttribute(request, id);
    }
    catch (Exception ex) {
      ret = false;
    }
    return ret;
  }

  long getLoginCookie(HttpServletRequest request) {

    long logintime = 0;
    try {
      Cookie cookies[] = request.getCookies();
      Cookie cookie = null;
      if (cookies != null) {
        for (int i = 0; i < cookies.length && cookie == null; i++) {
          if (cookies[i].getName().equalsIgnoreCase(logcookiename))
            cookie = cookies[i];
        }
      }

      String value = null;
      if (cookie != null) {
        value = cookie.getValue();
      }
      else if((value = request.getParameter("login"))==null) {
        value = (String)request.getAttribute("login");
      }

      if(value!=null) {
         request.setAttribute("login", value);
         logintime = Long.parseLong(value);
      }
      else {
        request.setAttribute("login", "0");
      }
    }
    catch (Exception ex) {
      logintime = 0;
    }
    return logintime;
  }

  boolean setLoginCookie(HttpServletRequest request, HttpServletResponse response, long value) {

    boolean ret = true;
    try {
      Cookie cookie = new Cookie(logcookiename, Long.toString(value));
      cookie.setMaxAge(7776000);
      response.addCookie(cookie);
      request.setAttribute("login", Long.toString(value));
    }
    catch (Exception ex) {
      ret = false;
    }
    return ret;
  }

  public boolean IsLoggedIn(int id, HttpServletRequest request) throws
      ServletException {
    boolean bret = false;
    User user = null;
    Connection conn = null;
    try {
      long login = getLoginCookie(request);
      if (login>0) {
        conn = datasource.getConnection();
        pstmt = conn.prepareStatement(select);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
          int col=0;
          user = new User();
          user.setid(rs.getInt(++col));
          user.setemail(rs.getString(++col));
          user.setaffiliate(rs.getInt(++col));
          user.setname(rs.getString(++col));
          user.setlastsortorder(rs.getInt(++col));
          java.sql.Timestamp ts = rs.getTimestamp(++col);
          if (ts != null && ts.getTime()>0)
            user.setlastlogindate(new java.util.Date(ts.getTime()));
          user.setcreationdate(new java.util.Date(rs.getTimestamp(++col).getTime()));
          ts = rs.getTimestamp(++col);
          if (ts!=null && ts.getTime()/1000==getLoginCookie(request)/1000) {
            user.setlogindate(new java.util.Date(ts.getTime()));
            long loginexpires = user.getlogindate().getTime() + 5400001;
            long currentTime = new java.util.Date().getTime();
            if (loginexpires > currentTime)
              bret = true;
          }
        }
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
    return bret;
  }

  public User GetUser(Connection conn, int id, HttpServletRequest request) throws
      ServletException {
    User user = null;
    try {
      pstmt = conn.prepareStatement(select);
      pstmt.setInt(1, id);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        int col=0;
        user = new User();
        user.setid(rs.getInt(++col));
        user.setemail(rs.getString(++col));
        user.setaffiliate(rs.getInt(++col));
        user.setname(rs.getString(++col));
        user.setlastsortorder(rs.getInt(++col));
        java.sql.Timestamp ts = rs.getTimestamp(++col);

        if (ts != null && ts.getTime()>0)
          user.setlastlogindate(new java.util.Date(ts.getTime()));

        user.setcreationdate(new java.util.Date(rs.getTimestamp(++col).getTime()));

        ts = rs.getTimestamp(++col);
        if (ts!=null && ts.getTime()/1000==getLoginCookie(request)/1000) {
          user.setlogindate(new java.util.Date(ts.getTime()));
          long loginexpires = user.getlogindate().getTime() + 5400001;
          long currentTime = new java.util.Date().getTime();
          if (loginexpires > currentTime)
            user.setloggedin(true);
        }
        else {
          user.setloggedin(false);
        }
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
    return user;
  }

  public GetUserResponse GetUser(HttpServletRequest request,
                                 HttpServletResponse resp) throws
      ServletException {
    return GetUser(request,resp, false);
  }

  public GetUserResponse GetUser(HttpServletRequest request,
                                 HttpServletResponse resp,
                                 boolean bActive) throws
      ServletException {
    GetUserResponse response = new GetUserResponse();
    Connection conn = null;
    User user = null;
    try {

      int id = 0;
      conn = datasource.getConnection();

      if ( (user = (User) request.getAttribute("userobject")) == null) {

        id = getUserID(request);

        if (id > 0) {
          if ( (user = GetUser(conn, id, request)) != null) {
            request.setAttribute("userobject", user);
          }
          else {
            id = 0;
          }
        }

        if (id == 0) {
          user = new User();

          int affiliate = 0;
          try {
            affiliate = Integer.parseInt(request.getHeader("Affiliate"));
          }
          catch (Exception ex) {
            affiliate = 0;
          }

          if (affiliate == 0 && bActive == false) {
            user = defaultuser;
          }
          else {
            pstmt = conn.prepareStatement(getInsertString());
            int col = 0;
            pstmt.setString(++col, null); //email
            pstmt.setString(++col, null); //pw
            pstmt.setInt(++col, affiliate);
            pstmt.setInt(++col, 1);
            pstmt.setTimestamp(++col, null);
            java.util.Date creationdate = new java.util.Date();
            pstmt.setTimestamp(++col,
                               new Timestamp(creationdate.getTime()));
            if ( (pstmt.executeUpdate()) > 0) {
              user = new User();
              user.setid(getLastInsertID(conn));
              user.setcreationdate(creationdate);
              request.setAttribute("userobject", user);
              getLoginCookie(request);
            }
          }
        }
        setUserCookie(request, resp, user.getid());
      }

      response.setuser(user);
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

  public LoginResponse Login(LoginRequest request, HttpServletRequest req,
                             HttpServletResponse resp) throws
      ServletException {
    LoginResponse response = new LoginResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(login);
      pstmt.setString(1, request.getemailaddress());
      pstmt.setString(2, request.getpassword());
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        User user = GetUser(conn, rs.getInt(1), req);
        user.setloggedin(true);
        response.setuser(user);
        try {
          if (pstmt != null) pstmt.close();
          pstmt = null;
        }
        catch (SQLException sqle) {}
        int id = 0;
        if ( (id = getUserID(req)) != user.getid()) {
          ConvertShoppingCart(id, user.getid());
        }
        req.setAttribute("userobject", user);
        setUserCookie(req, resp, user.getid());

        long currenttime = new java.util.Date().getTime();
        setLoginCookie(req, resp, currenttime);
        pstmt = conn.prepareStatement(loginupdate);
        pstmt.setTimestamp(1, new Timestamp(currenttime));
        pstmt.setTimestamp(2, new Timestamp(currenttime));
        pstmt.setInt(3, user.getid());
        pstmt.executeUpdate();
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

  public boolean CheckPassword(String email, String password) throws
      ServletException {
    Connection conn = null;
    boolean bvalid = false;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(login);
      pstmt.setString(1, email);
      pstmt.setString(2, password);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        bvalid = true;
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
    return bvalid;
  }

  public void Logout(User user, HttpServletRequest request, HttpServletResponse response) throws
      ServletException {
    Connection conn = null;
    try {
      setLoginCookie(request, response, 0);
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(logoutupdate);
      pstmt.setTimestamp(1, null);
      pstmt.setInt(2, user.getid());
      pstmt.executeUpdate();
      user.setloggedin(false);
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

  public AddUserResponse AddUser(AddUserRequest request,
                                 HttpServletRequest req,
                                 HttpServletResponse resp) throws
      ServletException {

    AddUserResponse response = new AddUserResponse();
    Connection conn = null;
    try {
      User user = request.getuser();
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getInsertString());
      int col=0;
      if(user==null) {
        pstmt.setString(++col, null);
        pstmt.setString(++col, null);
        pstmt.setInt(++col, 0);
        pstmt.setInt(++col, 1);
        pstmt.setTimestamp(++col, null);
        pstmt.setTimestamp(++col,
                         new Timestamp(new java.util.Date().getTime()));
      }
      else {
        pstmt.setString(++col, user.getemail());
        pstmt.setString(++col, user.getpassword());
        pstmt.setInt(++col, user.getaffiliate());
        pstmt.setInt(++col, user.getlastsortorder());
        pstmt.setTimestamp(++col, null);
        pstmt.setTimestamp(++col,
                           new Timestamp(new java.util.Date().getTime()));
      }
      if ( (pstmt.executeUpdate()) > 0) {
        response.setid(getLastInsertID(conn));
        setUserCookie(req, resp, response.getid());
        if(user.getid()!=response.getid()) {
          ConvertShoppingCart(user.getid(), response.getid());
        }
        user.setid(response.getid());
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

  public UpdateUserResponse UpdateUser(UpdateUserRequest request) throws
      ServletException {
    UpdateUserResponse response = new UpdateUserResponse();
    Connection conn = null;
    try {
      User user = request.getuser();
      if (user.getpassword().length() < 6)
        return null;
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(update);
      pstmt.setString(1, user.getemail());
      pstmt.setString(2, user.getpassword());
      pstmt.setTimestamp(3,
                         new Timestamp(new java.util.Date().
                                       getTime()));
      pstmt.setInt(4, user.getid());
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

  public UpdateUserResponse UpdateUserEmail(UpdateUserRequest request) throws
      ServletException {
    UpdateUserResponse response = new UpdateUserResponse();
    Connection conn = null;
    try {
      User user = request.getuser();
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(updateemail);
      pstmt.setString(1, user.getemail());
      pstmt.setInt(2, user.getid());
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

  public UpdateUserResponse UpdateUserSortOrder(UpdateUserRequest request) throws
      ServletException {
    UpdateUserResponse response = new UpdateUserResponse();
    Connection conn = null;
    try {
      User user = request.getuser();
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(updatesortorder);
      pstmt.setInt(1, user.getlastsortorder());
      pstmt.setInt(2, user.getid());
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

  public DeleteUserResponse DeleteUser(DeleteUserRequest request) throws
      ServletException {
    DeleteUserResponse response = new DeleteUserResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      DeleteUser(conn, request.getid());
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

  public void DeleteUser(Connection conn, int id) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement("delete from users where id=?");
      pstmt.setInt(1, id);
      pstmt.executeUpdate();
      new RecentlyViewedBean().DeleteRecentlyViewed(conn, id);
      ClearShoppingCart(conn, id);
      SearchItemsBean.DeleteUsersSearches(conn, id);
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

  public GetUsersResponse GetUsers(GetUsersRequest request) throws
      ServletException {

    GetUsersResponse response = new GetUsersResponse();
    Connection conn = null;

    try {
      conn = datasource.getConnection();
      String query = selectall;

      if (request.getstartdate() != null) {
        query += bydate;
      }

      query += orderbycreation;
      pstmt = conn.prepareStatement(query);

      if (request.getstartdate() != null) {
        pstmt.setTimestamp(1, new Timestamp(request.getstartdate().getTime()));
        pstmt.setTimestamp(2, new Timestamp(request.getenddate().getTime()));
      }

      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        int col=0;
        User user = new User();
        user.setid(rs.getInt(++col));
        user.setemail(rs.getString(++col));
        user.setaffiliate(rs.getInt(++col));
        user.setname(rs.getString(++col));
        user.setlastsortorder(rs.getInt(++col));
        java.sql.Timestamp ts = rs.getTimestamp(++col);
        if (ts != null && ts.getTime()>0)
          user.setlastlogindate(new java.util.Date(ts.getTime()));
        user.setcreationdate(new java.util.Date(rs.getTimestamp(++col).getTime()));
        ts = rs.getTimestamp(++col);
        if (ts != null && ts.getTime()>0)
          user.setlogindate(new java.util.Date(ts.getTime()));
        response.setusers(user);
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

  public User GetUser(int id) throws
      ServletException {
    User user = null;
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(selectbyid);
      pstmt.setInt(1, id);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        int col=0;
        user = new User();
        user.setid(rs.getInt(++col));
        user.setemail(rs.getString(++col));
        user.setaffiliate(rs.getInt(++col));
        user.setname(rs.getString(++col));
        user.setlastsortorder(rs.getInt(++col));
        java.sql.Timestamp ts = rs.getTimestamp(++col);
        if (ts != null && ts.getTime()>0)
          user.setlastlogindate(new java.util.Date(ts.getTime()));
        user.setcreationdate(new java.util.Date(rs.getTimestamp(++col).getTime()));
        ts = rs.getTimestamp(++col);
        if (ts != null && ts.getTime()>0)
          user.setlogindate(new java.util.Date(ts.getTime()));
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
    return user;
  }

  public boolean Forgotpassword(String email) throws
      ServletException {
    User user = null;
    Connection conn = null;
    boolean bsent = false;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(selectbyemail);
      pstmt.setString(1, email);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        int col=0;
        user = new User();
        user.setid(rs.getInt(++col));
        user.setemail(rs.getString(++col));
        user.setname(rs.getString(++col));
        user.setpassword(rs.getString(++col));
        user.setlastsortorder(rs.getInt(++col));
        java.sql.Timestamp ts = rs.getTimestamp(++col);
        if (ts != null && ts.getTime()>0)
          user.setlastlogindate(new java.util.Date(ts.getTime()));
        user.setcreationdate(new java.util.Date(rs.getTimestamp(++col).getTime()));
        ts = rs.getTimestamp(++col);
        if (ts != null && ts.getTime()>0)
          user.setlogindate(new java.util.Date(ts.getTime()));
        sendMail(user, conn);
        bsent = true;
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
    return bsent;
  }

  public SourceURLReportResponse SourceURLReport(SourceURLReportRequest request) throws
      ServletException {
    SourceURLReportResponse response = new SourceURLReportResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      String query = "select count(id), sourceurl from users ";
      if (request.getstartdate() != null) {
        query += bydate;
      }

      query += "group by sourceurl";
      pstmt = conn.prepareStatement(query);
      if (request.getstartdate() != null) {
        pstmt.setTimestamp(1, new Timestamp(request.getstartdate().getTime()));
        pstmt.setTimestamp(2, new Timestamp(request.getenddate().getTime()));
      }

      ResultSet rs = pstmt.executeQuery();
      while(rs.next()) {
        SourceURLItem item = new SourceURLItem();
        item.setcount(rs.getInt(1));
        item.setname(rs.getString(2));
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

  public CheckUserResponse CheckUser(CheckUserRequest request) throws
      ServletException {
    CheckUserResponse response = new CheckUserResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("select id from users where email=?");
      pstmt.setString(1, request.getemailaddress());
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        response.setexists(true);
      }
      else {
        response.setexists(false);
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

  public GetShoppingCartResponse GetShoppingCart(GetShoppingCartRequest request) throws
      ServletException {
    GetShoppingCartResponse response = new GetShoppingCartResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(
          "select a.id, a.quantity, a.itemid, a.giftoption, a.addeddate, c.zip, c.country " +
          "from shoppingcartitems a, item b, distributor c " +
          "where a.itemid = b.id and b.distributor=c.id and userid = ? order by addeddate asc");
      pstmt.setInt(1, request.getid());
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        ShoppingCartItem shoppingcartitem = new ShoppingCartItem();
        shoppingcartitem.setid(rs.getInt(1));
        shoppingcartitem.setquantity(rs.getInt(2));
        int itemid = rs.getInt(3);
        shoppingcartitem.setgiftoption(rs.getBoolean(4));
        shoppingcartitem.setaddedDate(new java.util.Date(rs.getTimestamp(5).
            getTime()));
        shoppingcartitem.setitem(new ItemBean().GetItem(conn, itemid));
        response.setshoppingcartitems(shoppingcartitem);
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

  public ShoppingCart GetShoppingCart(Connection conn, int id) throws
      ServletException {
    ShoppingCart shoppingcart = new ShoppingCart();
    try {
      shoppingcart.setid(id);
      pstmt = conn.prepareStatement(
          "select a.id, a.quantity, a.itemid, a.giftoption, a.addeddate, c.zip, c.country " +
          "from shoppingcartitems a, item b, distributor c " +
          "where a.itemid = b.id and b.distributor=c.id and userid = ? order by addeddate asc");
      pstmt.setInt(1, id);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        ShoppingCartItem shoppingcartitem = new ShoppingCartItem();
        shoppingcartitem.setid(rs.getInt(1));
        shoppingcartitem.setquantity(rs.getInt(2));
        int itemid = rs.getInt(3);
        shoppingcartitem.setgiftoption(rs.getBoolean(4));
        shoppingcartitem.setaddedDate(new java.util.Date(rs.getTimestamp(5).
            getTime()));
        shoppingcartitem.setitem(new ItemBean().GetItem(conn, itemid));
        shoppingcartitem.setzipcode(rs.getString(6));
        shoppingcartitem.setcountry(rs.getString(7));
        shoppingcart.setcartitem(shoppingcartitem);
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
    return shoppingcart;
  }

  public AddShoppingCartItemResponse AddShoppingCartItem(
      AddShoppingCartItemRequest request) throws ServletException {
    AddShoppingCartItemResponse response = new AddShoppingCartItemResponse();
    Connection conn = null;
    try {
      ShoppingCartItem shoppingcartitem = request.getshoppingcartitem();
      conn = datasource.getConnection();
      try {
        pstmt = conn.prepareStatement(
            "select id from shoppingcartitems where userid=? and itemid=?");
        pstmt.setInt(1, request.getuserid());
        pstmt.setInt(2, shoppingcartitem.getitem().getid());
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
          response.setid(0);
          return response;
        }
      }
      catch (SQLException sqlex) {
        try {
          if (conn != null) conn.close();
          conn = null;
        }
        catch (SQLException sqle) {}
        sqlex.printStackTrace();
        throw new ServletException(sqlex.getMessage());
      }
      finally {
        try {
          if (pstmt != null) pstmt.close();
          pstmt = null;
        }
        catch (SQLException sqle) {}
      }

      pstmt = conn.prepareStatement("insert into shoppingcartitems (userid, quantity, itemid, giftoption, addeddate) values (?,?,?,?,?)");
      pstmt.setInt(1, request.getuserid());
      pstmt.setInt(2, shoppingcartitem.getquantity());
      pstmt.setInt(3, shoppingcartitem.getitem().getid());
      pstmt.setBoolean(4, shoppingcartitem.getgiftoption());
      pstmt.setTimestamp(5,
                         new Timestamp(new GregorianCalendar().getTime().
                                       getTime()));

      if ( (pstmt.executeUpdate()) > 0) {
        response.setid(getLastInsertID(conn));
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

  public UpdateShoppingCartItemResponse UpdateShoppingCartItem(
      UpdateShoppingCartItemRequest request) throws ServletException {
    UpdateShoppingCartItemResponse response = new
        UpdateShoppingCartItemResponse();
    Connection conn = null;
    try {
      ShoppingCartItem shoppingcartitem = request.getshoppingcartitem();
      conn = datasource.getConnection();
      if (shoppingcartitem.getquantity() == 0) {
        DeleteShoppingCart(conn, shoppingcartitem.getitem().getid());
      }
      else {
        pstmt = conn.prepareStatement(
            "update shoppingcartitems set quantity=?, giftoption=? where itemid = ?");
        pstmt.setInt(1, shoppingcartitem.getquantity());
        pstmt.setBoolean(2, shoppingcartitem.getgiftoption());
        pstmt.setInt(3, shoppingcartitem.getitem().getid());
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
      try {
        if (conn != null) conn.close();
        conn = null;
      }
      catch (SQLException sqle) {}
    }
    return response;
  }

  public void ConvertShoppingCart(int olduser, int newuser) throws
      ServletException {

    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(
          "update shoppingcartitems set userid = ? where userid = ?");

      pstmt.setInt(1, newuser);
      pstmt.setInt(2, olduser);
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

  public DeleteShoppingCartItemResponse DeleteShoppingCart(
      DeleteShoppingCartItemRequest request) throws ServletException {
    DeleteShoppingCartItemResponse response = new
        DeleteShoppingCartItemResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      DeleteShoppingCart(conn, request.getid());
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

  public void DeleteShoppingCart(Connection conn, int id) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement(
          "delete from shoppingcartitems where itemid = ?");
      pstmt.setInt(1, id);
      pstmt.executeUpdate();
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

  public ClearShoppingCartResponse ClearShoppingCart(ClearShoppingCartRequest
      request) throws ServletException {
    ClearShoppingCartResponse response = new ClearShoppingCartResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      ClearShoppingCart(conn, request.getid());
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

  public ClearShoppingCartResponse ClearShoppingCart(Connection conn, int id) throws
      ServletException {
    ClearShoppingCartResponse response = new ClearShoppingCartResponse();
    try {
      pstmt = conn.prepareStatement(
          "delete from shoppingcartitems where userid = ?");
      pstmt.setInt(1, id);
      pstmt.executeUpdate();
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
    return response;
  }

  private void sendMail(User user, Connection conn) throws ServletException {
    MailConfiguration mailconfig = new MailConfiguration();

    try {
      pstmt = conn.prepareStatement("select mailhost, mailfromaddress, " +
                                    "mailauthuser, mailauthpassword, mailintroduction, mailbody, " +
                                    "mailsignature, mailsubject from mailconfig where id = 'password'");
      rs = pstmt.executeQuery();
      if (rs.next()) {
        mailconfig.setMailHost(rs.getString(1));
        mailconfig.setMailFromAddress(rs.getString(2));
        mailconfig.setMailAuthUser(rs.getString(3));
        mailconfig.setMailAuthPassword(rs.getString(4));
        mailconfig.setMailIntroduction(rs.getString(5));
        mailconfig.setMailBody(rs.getString(6));
        mailconfig.setMailSignature(rs.getString(7));
        mailconfig.setMailSubject(rs.getString(8));

        Context initial = new InitialContext();
        Session session = (Session) initial.lookup(
            "java:comp/env/storefrontmail");

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(mailconfig.getMailFromAddress()));
        InternetAddress dests[] = new InternetAddress[] {
            new InternetAddress(user.getemail())};
        msg.setRecipients(Message.RecipientType.TO, dests);

        msg.setSentDate(new java.util.Date());
        msg.setSubject(mailconfig.getMailSubject());

        String body = mailconfig.getMailIntroduction() + " ";
        body += user.getemail();
        body += " is ";
        body += user.getpassword();
        body += "\r\n\r\n";
        body += mailconfig.getMailSignature();
        msg.setText(body);

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
