package com.storefront.storefrontbeans;

import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class ArticleBean
    extends BaseBean {
  final static private String fields[] = {
      "heading", "description", "url","image" };
  final static private String tablename = "article";

  public ArticleBean() throws ServletException {
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

  public AddArticleResponse AddArticle(AddArticleRequest request) throws ServletException {

    AddArticleResponse response = new AddArticleResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getInsertString());
      Article article = (Article)request.getarticle();
      int col=0;
      pstmt.setString(++col, article.getheading());
      pstmt.setString(++col, article.getdescription());
      pstmt.setString(++col, article.geturl());
      pstmt.setString(++col, article.getimage());
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

  public UpdateArticleResponse UpdateArticle(UpdateArticleRequest request) throws ServletException {

    UpdateArticleResponse response = new UpdateArticleResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getUpdateString());
      Article article = (Article)request.getarticle();
      int col=0;
      pstmt.setString(++col, article.getheading());
      pstmt.setString(++col, article.getdescription());
      pstmt.setString(++col, article.geturl());
      pstmt.setString(++col, article.getimage());
      pstmt.setInt(col++, article.getid());
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

  public DeleteArticleResponse DeleteArticle(DeleteArticleRequest request) throws ServletException {

    DeleteArticleResponse response = new DeleteArticleResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("delete from article where id = ?");
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

  public GetArticleResponse GetArticle(GetArticleRequest request) throws
      ServletException {

    Connection conn = null;
    GetArticleResponse response = new GetArticleResponse();
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getSelectWithIDByIDString());
      pstmt.setInt(1, request.getid());
      rs = pstmt.executeQuery();

      if(rs.next()) {
        Article article = new Article();
        int col = 0;
        article.setid(rs.getInt(++col));
        article.setheading(rs.getString(++col));
        article.setdescription(rs.getString(++col));
        article.seturl(rs.getString(++col));
        article.setimage(rs.getString(++col));
        response.setarticle(article);
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

  public GetArticleResponse GetArticles(GetArticlesRequest request) throws
      ServletException {

    Connection conn = null;
    GetArticleResponse response = new GetArticleResponse();
    try {
      conn = datasource.getConnection();
      String query = getSelectWithIDString();
      if(request.getorderby()!=null) {
        query += request.getorderby();
        if(request.getdirection()!=null)
          query += " " + request.getdirection();
      }

      pstmt = conn.prepareStatement(query);
      rs = pstmt.executeQuery();

      while(rs.next()) {
        Article article = new Article();
        int col=0;
        article.setid(rs.getInt(++col));
        article.setheading(rs.getString(++col));
        article.setdescription(rs.getString(++col));
        article.seturl(rs.getString(++col));
        article.setimage(rs.getString(++col));
        response.setarticle(article);
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




