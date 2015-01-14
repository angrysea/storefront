package com.storefront.storefrontbeans;

import java.util.Iterator;
import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class CategoryItemBean
    extends BaseBean {
  public CategoryItemBean() throws ServletException {
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

  public void AddCategoryItems(Connection conn, int id, Iterator it) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement(
          "insert into categoryitem (category, itemnumber) values (?, ?)");
      while (it.hasNext()) {
        Category category = (Category) it.next();
        pstmt.setInt(1, category.getid());
        pstmt.setInt(2, id);
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

  public void UpdateCategoryItems(Connection conn, int id, Iterator it) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement(
          "delete from categoryitem where itemnumber=?");
      pstmt.setInt(1, id);
      pstmt.executeUpdate();

      pstmt = conn.prepareStatement(
          "insert into categoryitem (category, itemnumber) values (?, ?)");
      while (it.hasNext()) {
        Category category = (Category) it.next();
        pstmt.setInt(1, category.getid());
        pstmt.setInt(2, id);
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

  public void DeleteCategoryItems(Connection conn, int id) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement(
          "delete from categoryitem where itemnumber=?");
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

  public void GetCategoryItems(Connection conn, Details detail) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement("select a.category, b.groupid, b.name from categoryitem a, category b where a.category = b.id and a.itemnumber = ?");
      pstmt.setInt(1, detail.getid());
      rs = pstmt.executeQuery();
      while (rs.next()) {
        Category category = new Category();
        category.setid(rs.getInt(1));
        category.setgroup(rs.getInt(2));
        category.setname(rs.getString(3));
        detail.setcategories(category);
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
