package com.storefront.storefrontbeans;

import java.util.*;
import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class KeyWordsBean
    extends BaseBean {
  final static private String fields[] = {
      "id", "itemnumber", "value"};
  final static private String tablename = "keywords";
  private int itemnumber = 0;

  public KeyWordsBean() throws ServletException {
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

  public RebuildKeywordsResponse RebuildKeywords(RebuildKeywordsRequest request) throws
      ServletException {

    Connection conn = null;
    PreparedStatement pstmtdelete = null;
    RebuildKeywordsResponse response = new RebuildKeywordsResponse();

    try {
      GetItemsRequest itemrequest = new GetItemsRequest();
      itemrequest.setcatalog(request.getcatalog());
      itemrequest.setselectby(request.getselectby());
      itemrequest.setvalue(request.getvalue());
      itemrequest.setid(request.getid());
      GetItemsResponse itemresponse = new ItemBean().GetItems(itemrequest);

      conn = datasource.getConnection();
      PreparedStatement stmtdelete = conn.prepareStatement(
          "delete from keywords where itemnumber = ?");
      Iterator it = itemresponse.getitemsIterator();
      int count = 0;
      while (it.hasNext()) {
        Item item = (Item) it.next();
        stmtdelete.setInt(1, item.getid());
        stmtdelete.executeUpdate();
        AddKeyWords(conn, item.getid(), item);
        count++;
      }
      response.setcount(count);
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try {
        if (pstmtdelete != null) pstmtdelete.close();
        pstmtdelete = null;
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

  public void AddKeyWords(Connection conn, int id, Item item) throws
      ServletException {
    try {
      String manufacturer = null;
      String distributor = null;
      itemnumber = id;

      try {
        pstmt = conn.prepareStatement(
            "select name from manufacturer where id = ?");
        pstmt.setInt(1, item.getmanufacturerid());
        rs = pstmt.executeQuery();
        if (rs.next()) {
          manufacturer = rs.getString(1);
        }
      }
      catch (Exception e) {
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
      try {
        pstmt = conn.prepareStatement(
            "select name from distributor where id = ?");
        pstmt.setInt(1, item.getdistributorid());
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
          distributor = rs.getString(1);
        }
      }
      catch (Exception e) {
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

      pstmt = conn.prepareStatement(
          "insert into keywords (itemnumber,value) values (?,?)");

      addNow(manufacturer);
      addNow(distributor);
      addNow(item.getisin());
      addNow(item.getproductname());
      Details detail = item.getdetails();
      addNow(detail.getmanufactureritemnumber());
      Iterator it = detail.getcategoriesIterator();
      while (it.hasNext()) {
        Category category = (Category) it.next();
        addNow(category.getname());
      }
      it = detail.getspecificationsIterator();
      while (it.hasNext()) {
        ItemSpecification specification = (ItemSpecification) it.next();
        addNow(specification.getdescription());
      }
      addDescription(item.getdetails().getdescription());
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

  void addDescription(String description) throws Exception {
    StringTokenizer tokenizer = new StringTokenizer(description);
    while(tokenizer.hasMoreTokens()) {
      String token = tokenizer.nextToken();
      addNow(token);
    }
  }

  void addNow(String word) throws Exception {
    if (itemnumber > 0 && word != null) {
      word = word.trim();
      int len = word.length();
      if(word.length()>2) {
        if(len>3 || checkthreeletterwords(word)) {
          pstmt.setInt(1, itemnumber);
          pstmt.setString(2, word);
          pstmt.executeUpdate();
        }
      }
    }
  }

  boolean checkthreeletterwords(String token) {

    boolean bret = true;

    if (token.equalsIgnoreCase("the") ||
        token.equalsIgnoreCase("and") ||
        token.equalsIgnoreCase("for") ||
        token.equalsIgnoreCase("you") ||
        token.equalsIgnoreCase("out") ||
        token.equalsIgnoreCase("our") ||
        token.equalsIgnoreCase("are") ||
        token.equalsIgnoreCase("any") ||
        token.equalsIgnoreCase("yet") ||
        token.equalsIgnoreCase("but") ||
        token.equalsIgnoreCase("not") ||
        token.equalsIgnoreCase("has") ||
        token.equalsIgnoreCase("its") ||
        token.equalsIgnoreCase("use") ||
        token.equalsIgnoreCase("can") ||
        token.equalsIgnoreCase("buy") ||
        token.equalsIgnoreCase("had")) {
      bret = false;
    }

    return bret;
  }

  public void UpdateKeyWords(Connection conn, int id, Item item) throws
      ServletException {
    try {
      DeleteKeyWords(conn, id);
      AddKeyWords(conn, id, item);
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

  public void DeleteKeyWords(Connection conn, int id) throws ServletException {
    try {
      pstmt = conn.prepareStatement("delete from keywords where itemnumber=?");
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
}
