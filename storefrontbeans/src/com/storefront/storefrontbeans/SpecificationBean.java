package com.storefront.storefrontbeans;

import java.util.Iterator;
import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class SpecificationBean
    extends BaseBean {
  public SpecificationBean() throws ServletException {
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

  public void AddSpecifications(Connection conn, int id, Iterator it) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement("insert into itemspecifications (itemnumber, specid, description) values (?, ?, ?)");
      while (it.hasNext()) {
        ItemSpecification specification = (ItemSpecification) it.next();
        pstmt.setInt(1, id);
        pstmt.setInt(2, specification.getspecid());
        pstmt.setString(3, specification.getdescription());
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

  public void UpdateSpecifications(Connection conn, int id, Iterator it) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement(
          "delete from itemspecifications where itemnumber=?");
      pstmt.setInt(1, id);
      pstmt.executeUpdate();
      pstmt.close();

      pstmt = conn.prepareStatement("insert into itemspecifications (itemnumber, specid, description) values (?, ?, ?)");
      while (it.hasNext()) {
        ItemSpecification specification = (ItemSpecification) it.next();
        pstmt.setInt(1, id);
        pstmt.setInt(2, specification.getspecid());
        pstmt.setString(3, specification.getdescription());
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

  public void DeleteSpecifications(Connection conn, int id) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement(
          "delete from itemspecifications where itemnumber=?");
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

  public void GetSpecifications(Connection conn, Details detail) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement("select a.specid, b.name, a.description from itemspecifications a, specifications b where a.specid = b.id and itemnumber = ?");
      pstmt.setInt(1, detail.getid());
      rs = pstmt.executeQuery();
      while (rs.next()) {
        ItemSpecification specification = new ItemSpecification();
        specification.setspecid(rs.getInt(1));
        specification.setname(rs.getString(2));
        specification.setdescription(rs.getString(3));
        detail.setspecifications(specification);
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
