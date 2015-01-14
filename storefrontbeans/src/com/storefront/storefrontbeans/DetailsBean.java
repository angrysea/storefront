package com.storefront.storefrontbeans;

import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class DetailsBean
    extends BaseBean {
  final static private String fields[] = {
      "itemnumber", "manufactureritemnumber", "distributoritemnumber",
      "description", "alt_description", "imageUrlSmall",
      "imageUrlMedium", "imageUrlLarge", "salesRank", "availability",
      "shippingweight", "height", "length", "width", "handlingcharges", "url"};

  final static private String select =
      "select itemnumber, manufactureritemnumber, distributoritemnumber, " +
      "description, alt_description, imageUrlSmall, imageUrlMedium, imageUrlLarge, salesRank, availability, " +
      "shippingweight, height, length, width, handlingcharges, url from details where itemnumber = ?";
  final static private String tablename = "Details";

  private VariationsBean variationsBean = new VariationsBean();
  private CategoryItemBean categoryitem = new CategoryItemBean();
  private SpecificationBean specificationBean = new SpecificationBean();

  public DetailsBean() throws ServletException {
    super();
  }

  String[] getfields() {
    return fields;
  }

  String gettableName() {
    return tablename;
  }

  String getindexName() {
    return "itemnumber";
  }

  public void AddDetail(Connection conn, int id, Details detail) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement(getInsertString());
      int col=0;
      pstmt.setInt(++col, id);
      pstmt.setString(++col, detail.getmanufactureritemnumber());
      pstmt.setString(++col, detail.getdistributoritemnumber());
      pstmt.setString(++col, detail.getdescription());
      pstmt.setString(++col, detail.getalt_description());
      pstmt.setString(++col, detail.getimageUrlSmall());
      pstmt.setString(++col, detail.getimageUrlMedium());
      pstmt.setString(++col, detail.getimageUrlLarge());
      pstmt.setInt(++col, detail.getsalesRank());
      pstmt.setString(++col, detail.getavailability());
      pstmt.setDouble(++col, detail.getshippingweight());
      pstmt.setDouble(++col, detail.getheight());
      pstmt.setDouble(++col, detail.getlength());
      pstmt.setDouble(++col, detail.getwidth());
      pstmt.setDouble(++col, detail.gethandlingcharges());
      pstmt.setString(++col, detail.geturl());
      pstmt.executeUpdate();

      categoryitem.AddCategoryItems(conn, id, detail.getcategoriesIterator());
      specificationBean.AddSpecifications(conn, id,
                                          detail.getspecificationsIterator());
      variationsBean.AddVariations(conn, id, detail.getvariationsIterator());
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

  public void UpdateDetail(Connection conn, int id, Details detail) throws
      ServletException {
    try {
      pstmt = conn.prepareStatement(getUpdateString());
      int col=0;
      pstmt.setInt(++col, id);
      pstmt.setString(++col, detail.getmanufactureritemnumber());
      pstmt.setString(++col, detail.getdistributoritemnumber());
      pstmt.setString(++col, detail.getdescription());
      pstmt.setString(++col, detail.getalt_description());
      pstmt.setString(++col, detail.getimageUrlSmall());
      pstmt.setString(++col, detail.getimageUrlMedium());
      pstmt.setString(++col, detail.getimageUrlLarge());
      pstmt.setInt(++col, detail.getsalesRank());
      pstmt.setString(++col, detail.getavailability());
      pstmt.setDouble(++col, detail.getshippingweight());
      pstmt.setDouble(++col, detail.getheight());
      pstmt.setDouble(++col, detail.getlength());
      pstmt.setDouble(++col, detail.getwidth());
      pstmt.setDouble(++col, detail.gethandlingcharges());
      pstmt.setString(++col, detail.geturl());
      pstmt.setInt(++col, id);
      pstmt.executeUpdate();

      categoryitem.UpdateCategoryItems(conn, id, detail.getcategoriesIterator());
      specificationBean.UpdateSpecifications(conn, id,
                                             detail.getspecificationsIterator());
      variationsBean.UpdateVariations(conn, id, detail.getvariationsIterator());
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

  public void DeleteDetail(Connection conn, int id) throws ServletException {
    try {
      pstmt = conn.prepareStatement("delete from details where itemnumber=?");
      pstmt.setInt(1, id);
      pstmt.executeUpdate();

      categoryitem.DeleteCategoryItems(conn, id);
      specificationBean.DeleteSpecifications(conn, id);
      variationsBean.DeleteVariations(conn, id);
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

  public void GetDetail(Connection conn, Item item) throws ServletException {

    ResultSet rs = null;
    try {
      Details detail = new Details();
      pstmt = conn.prepareStatement(select);
      pstmt.setInt(1, item.getid());
      rs = pstmt.executeQuery();
      if (rs.next()) {
        int col=0;
        detail.setid(rs.getInt(++col));
        detail.setmanufactureritemnumber(rs.getString(++col));
        detail.setdistributoritemnumber(rs.getString(++col));
        detail.setdescription(rs.getString(++col));
        detail.setalt_description(rs.getString(++col));
        detail.setimageUrlSmall(rs.getString(++col));
        detail.setimageUrlMedium(rs.getString(++col));
        detail.setimageUrlLarge(rs.getString(++col));
        detail.setsalesRank(rs.getInt(++col));
        detail.setavailability(rs.getString(++col));
        detail.setshippingweight(rs.getDouble(++col));
        detail.setheight(rs.getDouble(++col));
        detail.setlength(rs.getDouble(++col));
        detail.setwidth(rs.getDouble(++col));
        detail.sethandlingcharges(rs.getDouble(++col));
        detail.seturl(rs.getString(++col));
        categoryitem.GetCategoryItems(conn, detail);
        specificationBean.GetSpecifications(conn, detail);
        variationsBean.GetVariations(conn, detail);
        item.setdetails(detail);
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
