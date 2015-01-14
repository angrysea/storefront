package com.storefront.storefrontbeans;

import java.util.Iterator;
import java.util.StringTokenizer;
import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class SearchItemsBean
    extends BaseBean {

  final static private String selectresults =
      "select a.id, isin, productname, " +
      "manufacturer, c.name, distributor, quantity, d.availability, listPrice, ourPrice, " +
      "ourcost from item a, searchresultsitem b, manufacturer c, itemstatus d where a.id = b.itemid and " +
      "b.searchid=? and idx between ? and ? and a.manufacturer = c.id and d.id=a.status order by b.idx";

  final static private String selectoldsearches =
      "select id from searchresults " +
      "where userid = ? and DATE_FORMAT(searchtime,'%Y %m %d') < DATE_FORMAT(?,'%Y %m %d')";

  final static private String selectsavedsearches =
      "select id, userid, search, searchtime " +
      "from searchresults where userid = ?";

  final static private String deleteoldresults =
      "delete from searchresults where id = ?";
  final static private String deleteolditems =
      "delete from searchresultsItem where searchid = ?";

  final static private String srawsearch = "Raw Search";
  final static private String scategory = "Category";
  final static private String smanufacturer = "Manufacturer";

  public SearchItemsBean() throws ServletException {
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

  private SearchResponse PerformSearchItems(String query, int userid) throws
      ServletException {

    CleanupOldResults(userid);
    SearchResponse response = new SearchResponse();
    Connection conn = null;
    int searchid = 0;
    try {
      SearchResult searchResult = new SearchResult();
      searchResult.setsearch(query);
      searchResult.setsearchtime(new java.util.Date());

      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(query);
      rs = pstmt.executeQuery();
      PreparedStatement pstmt3 = null;
      int i = 1;
      try {
        pstmt3 = conn.prepareStatement(
            "insert into searchresultsitem (searchid, idx, itemid) values (?, ?, ?)");
        for (; rs.next(); i++) {
          if (i == 1) {
            PreparedStatement pstmt2 = null;
            try {
              pstmt2 = conn.prepareStatement(
                  "insert into searchresults (userid, search, searchtime) values (?, ?, ?)");
              pstmt2.setInt(1, userid);
              pstmt2.setString(2, query);
              pstmt2.setTimestamp(3,
                                  new Timestamp(searchResult.getsearchtime().
                                                getTime()));
              if ( (pstmt2.executeUpdate()) > 0) {
                searchid = getLastInsertID(conn);
                searchResult.setid(searchid);
              }
              else {
                throw new Exception("Error storing search results");
              }
            }
            catch (SQLException sqlex) {
              throw new ServletException(sqlex.getMessage());
            }
            finally {
              try {
                if (pstmt2 != null) pstmt2.close();
                pstmt2 = null;
              }
              catch (SQLException sqle) {
              }
            }
          }
          pstmt3.setInt(1, searchid);
          pstmt3.setInt(2, i);
          pstmt3.setInt(3, rs.getInt(1));
          pstmt3.executeUpdate();
        }
      }
      catch (SQLException sqlex) {
        throw new ServletException(sqlex.getMessage());
      }
      finally {
        try {
          if (pstmt3 != null) pstmt3.close();
          pstmt3 = null;
        }
        catch (SQLException sqle) {
        }
      }

      PreparedStatement pstmt4 = null;
      if (--i > 0) {
        searchResult.setitemsfound(i);
        try {
          pstmt4 = conn.prepareStatement(
              "update searchresults set itemsfound=? where id = ?");
          pstmt4.setInt(1, i);
          pstmt4.setInt(2, searchid);
          pstmt4.executeUpdate();
          response.setresults(searchResult);
        }
        catch (SQLException sqlex) {
          throw new ServletException(sqlex.getMessage());
        }
        finally {
          try {
            if (pstmt4 != null) pstmt4.close();
            pstmt4 = null;
          }
          catch (SQLException sqle) {
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
    return response;
  }

  public SearchResponse SearchItems(SearchRequest request, User user) throws
      ServletException {

    char ctab = 'd';
    String query = "select DISTINCT a.id from item a, manufacturer b, company c";
    String where = " where a.manufacturer = b.id and b.active=1 and c.id = 1 and (c.instockonly = 0 or a.status=c.instockonly)";
    String orderby = " order by ";

    if(user.getviewonlystatus()>0) {
      where +=  "and a.status="+Integer.toString(user.getviewonlystatus());
    }

    SortFields sortfield  = null;

    if(request.getmanufacturerid()!=null && request.getmanufacturerid().length()>0) {
      where += " and a.manufacturer = " + request.getmanufacturerid();
      ctab++;
    }

    if(request.getendprice() > 0) {
         where += " and ourPrice >= " + Double.toString(request.getstartprice()) +
             " and ourPrice <= " + Double.toString(request.getendprice());
    }

    if(request.getcatalog()!=null && request.getcatalog().length()>0) {
      where += " and a.catalog = " + request.getcatalog();
      ctab++;
    }

    if(request.getsortfieldid()!=null)
      sortfield  = new ListsBean().GetSortField(Integer.parseInt(request.getsortfieldid()));
    else
      sortfield  = new ListsBean().GetSortField(1);

    if (sortfield!=null && sortfield.getfieldname() != null) {
      if (sortfield.getfieldname().startsWith("specid=")) {
        query += ", itemspecifications " + ctab;
        where += " and a.id = " + ctab + ".itemnumber and " + ctab + "." +
            sortfield.getfieldname();
        if(sortfield.getfieldtype().equals("numeric"))
          orderby += ctab + ".description " + sortfield.getdirection();
        else
          orderby += ctab + ".value " + sortfield.getdirection();;
        ctab++;
      }
      else if (sortfield.getfieldname().startsWith("views") ||
               sortfield.getfieldname().startsWith("sold")) {
        query += ", itemranking " + ctab;
        where += " and a.id = " + ctab + ".itemnumber";
        orderby += ctab + "." + sortfield.getfieldname() + " " + sortfield.getdirection();
        ctab++;
      }
      else if (sortfield.getfieldname().startsWith("category")) {
        query += ", categoryitem " + ctab;
        where += " and a.id=" + ctab + ".itemnumber";
        orderby += ctab + "." + sortfield.getfieldname() + " " + sortfield.getdirection();
        ctab++;
      }
      else if (sortfield.getfieldname() != null) {
        orderby += sortfield.getfieldname() + " " + sortfield.getdirection();
      }
    }
    else if(request.getendprice() > 0) {
      orderby += "ourprice";
    }
    else
      orderby += "productname";

    if (request.getsearchphrase() != null) {
      StringTokenizer token = new StringTokenizer(request.getsearchphrase(),
                                                  " ");
      while (token.hasMoreTokens()) {
        if (ctab > 'z')
          break;
        query += ", keywords " + ctab;
        where += " and a.id = " + ctab + ".itemnumber and " +
            ctab + ".value like '%" + token.nextToken() + "%'";
        ctab++;
      }
    }

    Iterator it = request.getcategoryidsIterator();
    while(it.hasNext()) {
      if(ctab>'z')
        break;
      query += ", categoryitem " + ctab;
      where += " and a.id=" + ctab + ".itemnumber and " + ctab + ".category=" + (String)it.next();
      ctab++;
    }

    if(where!=null)
      query += where;

    if(orderby!=null)
      query += orderby;

    SearchResponse response = PerformSearchItems(query, user.getid());
    updateSearchCounts(request);

    return response;
  }

  public GetSearchResultsResponse GetSearchResults(GetSearchResultsRequest request) throws
      ServletException {

    Connection conn = null;
    GetSearchResultsResponse response = new GetSearchResultsResponse();
    try {
      conn = datasource.getConnection();
      String query = selectresults;
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, request.getsearchid());
      pstmt.setInt(2, request.getidxstart());
      pstmt.setInt(3, request.getidxend());
      rs = pstmt.executeQuery();
      Item item = null;

      while (rs.next()) {
        item = new Item();
        item.setid(rs.getInt(1));
        item.setisin(rs.getString(2));
        item.setproductname(rs.getString(3));
        item.setmanufacturerid(rs.getInt(4));
        item.setmanufacturer(rs.getString(5));
        item.setdistributorid(rs.getInt(6));
        item.setquantity(rs.getInt(7));
        item.setavailability(rs.getString(8));
        item.setlistprice(rs.getDouble(9));
        item.setourprice(rs.getDouble(10));
        item.setourcost(rs.getDouble(11));
        DetailsBean details = new DetailsBean();
        details.GetDetail(conn, item);
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

  public GetOldSearchesResponse GetOldSearches(GetOldSearchesRequest request) throws
      ServletException {
    GetOldSearchesResponse response = new GetOldSearchesResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(selectsavedsearches);
      pstmt.setInt(1, request.getuserid());
      rs = pstmt.executeQuery();
      SearchResult searchResults = null;
      while (rs.next()) {
        searchResults = new SearchResult();
        searchResults.setid(rs.getInt(1));
        searchResults.setitemsfound(rs.getInt(2));
        searchResults.setsearch(rs.getString(3));
        searchResults.setsearchtime(new java.util.Date(rs.getTimestamp(4).
            getTime()));
        response.setresults(searchResults);
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

  private void CleanupOldResults(int userid) throws
      ServletException {

    Connection conn = null;
    PreparedStatement pstmt2 = null;
    PreparedStatement pstmt3 = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(selectoldsearches);
      pstmt.setInt(1, userid);
      pstmt.setTimestamp(2,
                         new Timestamp(new java.util.Date().getTime()));
      rs = pstmt.executeQuery();
      pstmt2 = conn.prepareStatement(deleteoldresults);
      pstmt3 = conn.prepareStatement(deleteolditems);
      while (rs.next()) {
        int id = rs.getInt(1);
        pstmt2.setInt(1, id);
        pstmt2.executeUpdate();
        pstmt3.setInt(1, id);
        pstmt3.executeUpdate();
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
        if (pstmt2 != null) pstmt2.close();
      }
      catch (SQLException sqle) {}
      try {
        if (pstmt3 != null) pstmt3.close();
      }
      catch (SQLException sqle) {}
      try {
        if (conn != null) conn.close();
        conn = null;
      }
      catch (SQLException sqle) {}
    }
  }

  static void DeleteUsersSearches(Connection conn, int userid) throws
      ServletException {

    PreparedStatement pstmt = null;
    PreparedStatement pstmt2 = null;
    PreparedStatement pstmt3 = null;
    ResultSet rs = null;
    try {
      pstmt = conn.prepareStatement(selectoldsearches);
      pstmt.setInt(1, userid);
      pstmt.setTimestamp(2,
                         new Timestamp(new java.util.Date().getTime()));
      rs = pstmt.executeQuery();
      pstmt2 = conn.prepareStatement(deleteoldresults);
      pstmt3 = conn.prepareStatement(deleteolditems);
      while (rs.next()) {
        int id = rs.getInt(1);
        pstmt2.setInt(1, id);
        pstmt2.executeUpdate();
        pstmt3.setInt(1, id);
        pstmt3.executeUpdate();
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
        if (pstmt2 != null) pstmt2.close();
      }
      catch (SQLException sqle) {}
      try {
        if (pstmt3 != null) pstmt3.close();
      }
      catch (SQLException sqle) {}
    }
  }

  public SearchCountReportResponse SearchCountReport(SearchCountReportRequest request) throws
      ServletException {

    SearchCountReportResponse response = new SearchCountReportResponse();
    Connection conn = null;
    String query = "select search, views, type from seachranking order by views desc";

    try {
      int max = request.getmax();
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(query);
      rs = pstmt.executeQuery();
      int count=0;
      while(rs.next()&& count<max) {
        SearchCountItem item = new SearchCountItem();
        item.setname(rs.getString(1));
        item.setcount(rs.getInt(2));
        item.settype(rs.getString(3));
        response.setitems(item);
        count++;
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

  private void updateSearchCounts(SearchRequest request) throws
      ServletException {

    Connection conn = null;
    boolean searchfound = false;
    String  type = null;
    String search = null;
    String update = "update seachranking set views=views+1 where search='";
    String insert = "insert into seachranking (search, type, views) values ('";
    try {
      conn = datasource.getConnection();
     if (request.getmanufacturerid() != null &&
          request.getmanufacturerid().length() > 0) {
        pstmt = conn.prepareStatement("select name from manufacturer where id="+request.getmanufacturerid());
        rs = pstmt.executeQuery();
        if(rs.next()) {
          String manufacturer = rs.getString(1);
          rs.close();
          pstmt.close();
          searchfound=true;
          type = (type==null) ? smanufacturer : type+", " + smanufacturer;
          search = (search==null) ? manufacturer : search + " " + manufacturer;
        }
      }

      Iterator it = request.getcategoryidsIterator();
      while (it.hasNext()) {
        String id = (String) it.next();
        pstmt = conn.prepareStatement("select name from category where id=" +
                                      id);
        rs = pstmt.executeQuery();
        if (rs.next()) {
          String category = rs.getString(1);
          rs.close();
          pstmt.close();
          searchfound=true;
          type = (type==null) ? scategory : type + ", " + scategory;
          search = (search==null) ? category : search + " " + category;
        }
      }

      if (request.getsearchphrase() != null && request.getsearchphrase().length()>0) {
        type = (type==null) ? srawsearch : type + ", " + srawsearch;
        search = (search==null)?request.getsearchphrase():search+" "+request.getsearchphrase();
        searchfound=true;
      }

      if(searchfound) {
        update+=search+"'";
        pstmt = conn.prepareStatement(update);
        if(pstmt.executeUpdate()<=0) {
          pstmt.close();
          insert+=search+"', '"+type+"', 1)";
          pstmt = conn.prepareStatement(insert);
          pstmt.executeUpdate();
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
  }
}
