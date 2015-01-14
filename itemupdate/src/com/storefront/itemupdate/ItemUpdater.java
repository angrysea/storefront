package com.storefront.itemupdate;

import java.util.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class ItemUpdater{

  final static private String selectitem =
      "select id, catalog, isin, productname, manufacturer, " +
      "distributor, quantity, allocated, sold, quantitytoorder, backordered, minimumonhand, " +
      "reorderquantity, quantityonorder, status, listPrice, ourPrice, " +
      "ourcost from item where manufacturer = ? ";

  final static private String selectdetails =
      "select itemnumber, manufactureritemnumber, distributoritemnumber, " +
      "description, imageUrlSmall, imageUrlMedium, imageUrlLarge, salesRank, availability, " +
      "shippingweight, height, length, width, handlingcharges, url from details where itemnumber = ?";

  final static private String updatedetails =
      "update details set imageUrlSmall=?, imageUrlMedium=?, imageUrlLarge=? where itemnumber = ?";

  static final int mens = 10;
  static final int womens = 20;

  public ItemUpdater() {
  }

  public void UpdateItemData() throws Exception {
    UpdateItemData(null);
  }

  public void UpdateItemData(String manufacturer_name) throws Exception {

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
      conn = DriverManager.getConnection("jdbc:mysql://192.168.1.40:3306/parkwatches?user=parkwatches&password=parkwatches");

      if(manufacturer_name!=null) {
        pstmt = conn.prepareStatement("select id, name, longname, prefix, description, shortdescription from manufacturer where name = ? order by name");
        pstmt.setString(1, manufacturer_name);
      }
      else {
        pstmt = conn.prepareStatement("select id, name, longname, prefix, description, shortdescription from manufacturer order by name");
      }

      rs = pstmt.executeQuery();
      Manufacturer manufacturer = null;
      while (rs.next()) {
        int col=0;
        manufacturer = new Manufacturer();
        manufacturer.setid(rs.getInt(++col));
        manufacturer.setname(rs.getString(++col));
        manufacturer.setlongname(rs.getString(++col));
        manufacturer.setprefix(rs.getString(++col));
        manufacturer.setdescription(rs.getString(++col));
        manufacturer.setshortdescription(rs.getString(++col));
        UpdateItems(conn, manufacturer);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new Exception(ex.getMessage());
    }
    finally {
      try { if(rs!=null) rs.close(); rs=null;} catch(SQLException sqle ){}
      try { if(pstmt!=null) pstmt.close(); pstmt=null; } catch(SQLException sqle ){}
      try { if(conn!=null) conn.close(); conn=null; } catch(SQLException sqle ){}
    }
  }

  private void UpdateItems(Connection conn, Manufacturer manufacturer)throws Exception {

    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {

      pstmt = conn.prepareStatement(selectitem);
      pstmt.setInt(1, manufacturer.getid());
      rs = pstmt.executeQuery();
      Item item = null;

      while (rs.next()) {
        int col=0;
        item = new Item();
        item.setid(rs.getInt(++col));
        item.setcatalog(rs.getInt(++col));
        item.setisin(rs.getString(++col));
        item.setproductname(rs.getString(++col));
        item.setmanufacturerid(rs.getInt(++col));
        item.setdistributorid(rs.getInt(++col));
        item.setquantity(rs.getInt(++col));
        item.setallocated(rs.getInt(++col));
        item.setsold(rs.getInt(++col));
        item.setquantitytoorder(rs.getInt(++col));
        item.setbackordered(rs.getInt(++col));
        item.setminimumonhand(rs.getInt(++col));
        item.setreorderquantity(rs.getInt(++col));
        item.setquantityonorder(rs.getInt(++col));
        item.setstatusid(rs.getInt(++col));
        item.setlistprice(rs.getDouble(++col));
        item.setourprice(rs.getDouble(++col));
        item.setourcost(rs.getDouble(++col));
        GetDetail(conn, item);

        Details detail = item.getdetails();
        Iterator it = detail.getspecificationsIterator();
        boolean bupdate = false;
        while(it.hasNext()) {
          ItemSpecification specification = (ItemSpecification)it.next();
          if(specification.getdescription().equalsIgnoreCase("mens")) {
            bupdate = true;
            Category category = new Category();
            category.setid(mens);
            detail.setcategories(category);
            break;
          }
          else if(specification.getdescription().equalsIgnoreCase("ladies")) {
            bupdate = true;
            Category category = new Category();
            category.setid(womens);
            detail.setcategories(category);
            break;
          }
        }
        if(bupdate)
          UpdateCategoryItems(conn, item.getid(), detail.getcategoriesIterator());
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new Exception(ex.getMessage());
    }
    finally {
      try { if(rs!=null) rs.close(); rs=null;} catch(SQLException sqle ){}
      try { if(pstmt!=null) pstmt.close(); pstmt=null; } catch(SQLException sqle ){}
    }
  }

  public void GetSpecifications(Connection conn, Details detail) throws
      Exception {
    PreparedStatement pstmt = null;
    ResultSet rs = null;

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
      throw ex;
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
  private void GetDetail(Connection conn, Item item) throws Exception {

    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      Details detail = new Details();
      pstmt = conn.prepareStatement(selectdetails);
      pstmt.setInt(1, item.getid());
      rs = pstmt.executeQuery();
      if (rs.next()) {
        int col=0;
        detail.setid(rs.getInt(++col));
        detail.setmanufactureritemnumber(rs.getString(++col));
        detail.setdistributoritemnumber(rs.getString(++col));
        detail.setdescription(rs.getString(++col));
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
        GetSpecifications(conn, detail);
        item.setdetails(detail);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new Exception(ex.getMessage());
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

  private void UpdateDetails(Connection conn, Item item) throws Exception {

    PreparedStatement pstmt = null;

    try {
      Details detail = item.getdetails();
      pstmt = conn.prepareStatement(updatedetails);
      int col=0;
      pstmt.setString(++col, detail.getimageUrlSmall());
      pstmt.setString(++col, detail.getimageUrlMedium());
      pstmt.setString(++col, detail.getimageUrlLarge());
      pstmt.setInt(++col, detail.getid());
      pstmt.executeUpdate();
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new Exception(ex.getMessage());
    }
    finally {
      try {
        if (pstmt != null) pstmt.close();
        pstmt = null;
      }
      catch (SQLException sqle) {}
    }
  }

  private void UpdateItem(Connection conn, Item item) throws Exception {

    PreparedStatement pstmt = null;

    try {
      pstmt = conn.prepareStatement("update item set isin = ? where id = ?");
      int col=0;
      pstmt.setString(++col, item.getisin());
      pstmt.setInt(++col, item.getid());
      pstmt.executeUpdate();
      UpdateDetails(conn, item);
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new Exception(ex.getMessage());
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
      Exception {

    PreparedStatement pstmt = null;
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
      throw new Exception(ex.getMessage());
    }
    finally {
      try {
        if (pstmt != null) pstmt.close();
        pstmt = null;
      }
      catch (SQLException sqle) {}
    }
  }

  public static void main(String args[]) {

    try {
      ItemUpdater gifupdater = new ItemUpdater();
      gifupdater.UpdateItemData();
    }
    catch (Exception e) {
    }
  }
}
