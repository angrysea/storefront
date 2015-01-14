package com.storefront.storefrontbeans;

import java.util.*;
import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class RecentlyViewedBean
    extends BaseBean {

  final static private String selectfind =
      "select a.id, b.id from item a, recentlyviewed b " +
      "where b.userid = ? and a.id = b.itemnumber order by b.viewedtime desc";

  final static private String selectget =
      "select a.id, a.catalog, isin, productname, manufacturer, b.name, " +
      "distributor, c.name, quantity, allocated, backordered, minimumonhand, " +
      "reorderquantity, quantityonorder, a.status, d.status, d.availability, listPrice, ourPrice, ourcost " +
      "from item a, manufacturer b, distributor c, itemstatus d,  recentlyviewed e " +
      "where a.id = e.itemnumber and b.id=manufacturer " +
      "and c.id=distributor and d.id=a.status and e.id in (";

  final static private String orderby =
      ") order by e.viewedtime desc";

  public RecentlyViewedBean() throws ServletException {
    super();
  }

  String[] getfields() {
    return null;
  }

  String gettableName() {
    return null;
  }

  String getindexName() {
    return null;
  }

  public void AddRecentlyViewed(Connection conn, RecentlyViewed recentlyviewed) throws
      ServletException {
    try {
      GregorianCalendar calendar = new GregorianCalendar();
      pstmt = conn.prepareStatement(
          "insert into recentlyviewed (userid, itemnumber, viewedtime) values (?, ?, ?)");
      pstmt.setInt(1, recentlyviewed.getuserid());
      pstmt.setInt(2, recentlyviewed.getitemNumber());
      pstmt.setTimestamp(3, new Timestamp(calendar.getTime().getTime()));
      pstmt.executeUpdate();
      pstmt.close();
      pstmt = null;

      pstmt = conn.prepareStatement(
          "delete from recentlyviewed where userid = ? and viewedtime < ?");
      pstmt.setInt(1, recentlyviewed.getuserid());
      pstmt.setTimestamp(2,
                         new Timestamp(calendar.getTime().getTime() - 7776000000l));
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

  public void DeleteRecentlyViewed(Connection conn, int id) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement("delete from recentlyviewed where userid = ?");
      pstmt.setInt(1, id);
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
    }
  }

  public RecentlyViewedResponse GetRecentlyViewed(RecentlyViewedRequest request) throws
      ServletException {
    RecentlyViewedResponse response = new RecentlyViewedResponse();
    DetailsBean detailsBean = new DetailsBean();

    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(selectfind);
      pstmt.setInt(1, request.getid());
      rs = pstmt.executeQuery();
      int i=0;
      int max=request.getcount();
      HashMap viewed = new HashMap();
      while (rs.next()&&(max==0||i<max)) {
        String item = rs.getString(1);
        String id = rs.getString(2);
        if(!viewed.containsKey(item)) {
          viewed.put(item, id);
          i++;
        }
      }
      rs.close();
      pstmt.close();
      StringBuffer query = new StringBuffer(selectget);
      Iterator it = viewed.values().iterator();

      if (it.hasNext()) {
        query.append(it.next());
      }

      while(it.hasNext()) {
        query.append(", ");
        query.append(it.next());
      }
      query.append(orderby);

      pstmt = conn.prepareStatement(query.toString());
      rs = pstmt.executeQuery();
      while (rs.next()) {
        Item item = new Item();
        int col=0;
        item.setid(rs.getInt(++col));
        item.setcatalog(rs.getInt(++col));
        item.setisin(rs.getString(++col));
        item.setproductname(rs.getString(++col));
        item.setmanufacturerid(rs.getInt(++col));
        item.setmanufacturer(rs.getString(++col));
        item.setdistributorid(rs.getInt(++col));
        item.setdistributor(rs.getString(++col));
        item.setquantity(rs.getInt(++col));
        item.setallocated(rs.getInt(++col));
        item.setbackordered(rs.getInt(++col));
        item.setminimumonhand(rs.getInt(++col));
        item.setreorderquantity(rs.getInt(++col));
        item.setquantityonorder(rs.getInt(++col));
        item.setstatusid(rs.getInt(++col));
        item.setstatus(rs.getString(++col));
        item.setavailability(rs.getString(++col));
        item.setlistprice(rs.getDouble(++col));
        item.setourprice(rs.getDouble(++col));
        item.setourcost(rs.getDouble(++col));
        detailsBean.GetDetail(conn, item);
        response.setrecentlyviewed(item);
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
