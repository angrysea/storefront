package com.storefront.storefrontbeans;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import org.apache.poi.hssf.usermodel.*;
import com.storefront.storefrontrepository.*;
import com.storefront.ftp.*;

public class ShoppingFeed extends BaseBean {

  private static String mpn = "MPN";
  private static String manufacturer_name = "Manufacturer Name";
  private static String upc = "UPC";
  private static String product_name = "Product Name";
  private static String product_description = "Product Description";
  private static String product_price = "Product Price";
  private static String product_url = "Product URL";
  private static String image_url = "Image URL";
  private static String shopping_com_categorization = "Shopping.com Categorization";
  private static String stock_availability = "Stock Availability";
  private static String stock_description = "Stock Description";
  private static String ground_shipping = "Ground Shipping";
  private static String second_day_shipping = "2nd Day Shipping";
  private static String overnight_shipping = "Overnight Shipping";
  private static String weight = "Weight";
  private static String zip_code = "Zip Code";

  String zipcode = null;

  private Company company = null;
  private Theme theme = null;
  int currentrow = 0;

  final static private String selectitem =
      "select a.id, a.catalog, a.isin, a.productname, a.manufacturer, b.name, " +
      "a.distributor, a.quantity, a.allocated, a.sold, a.quantitytoorder, a.backordered, a.minimumonhand, " +
      "a.reorderquantity, a.quantityonorder, a.status, c.status, a.listPrice, a.ourPrice, " +
      "a.ourcost from item a, manufacturer b, itemstatus c where " +
      "a.status=c.id and manufacturer=b.id and manufacturer = ? ";

  final static private String selectdetails =
      "select a.itemnumber, a.manufactureritemnumber, a.distributoritemnumber, b.zip, " +
      "a.description, a.imageUrlSmall, a.imageUrlMedium, a.imageUrlLarge, a.salesRank, a.availability, " +
      "a.shippingweight, a.height, a.length, a.width, a.handlingcharges, a.url from details a left outer join distributor b on " +
      "distributoritemnumber=b.id where itemnumber = ?";

  String localpath = null;

  String[] getfields() {
    return null;
  }

  String gettableName() {
    return null;
  }

  String getindexName() {
    return null;
  }

  public ShoppingFeed() throws ServletException {
    super();
    this.localpath="c:\\feed\\shopping\\";
  }

  public ShoppingFeed(String localpath) throws ServletException {
    super();
    this.localpath=localpath;
  }

  public void GenerateFeedFile() throws Exception {

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = datasource.getConnection();
      company = GetCompany(conn, 1);
      theme = GetTheme(conn);
      HSSFWorkbook wb = new HSSFWorkbook();
      HSSFSheet sheet = wb.createSheet("shopping feed");

      HSSFRow row = sheet.createRow((short)currentrow);
      short currentcell = -1;
      HSSFCell cell = null;
      cell = row.createCell((short)++currentcell);
      cell.setCellValue(mpn);
      cell = row.createCell((short)++currentcell);
      cell.setCellValue(manufacturer_name);
      /*
      cell = row.createCell((short)++currentcell);
      cell.setCellValue(upc);
      */
      cell = row.createCell((short)++currentcell);
      cell.setCellValue(product_name);
      cell = row.createCell((short)++currentcell);
      cell.setCellValue(product_description);
      cell = row.createCell((short)++currentcell);
      cell.setCellValue(product_price);
      cell = row.createCell((short)++currentcell);
      cell.setCellValue(product_url);
      cell = row.createCell((short)++currentcell);
      cell.setCellValue(image_url);
      cell = row.createCell((short)++currentcell);
      cell.setCellValue(shopping_com_categorization);
      cell = row.createCell((short)++currentcell);
      cell.setCellValue(stock_availability);
      cell = row.createCell((short)++currentcell);
      cell.setCellValue(stock_description);
      /*
      cell = row.createCell((short)++currentcell);
      cell.setCellValue(ground_shipping);
      cell = row.createCell((short)++currentcell);
      cell.setCellValue(second_day_shipping);
      cell = row.createCell((short)++currentcell);
      cell.setCellValue(overnight_shipping);
      */
      cell = row.createCell((short)++currentcell);
      cell.setCellValue(weight);
      cell = row.createCell((short)++currentcell);
      cell.setCellValue(zipcode);

      pstmt = conn.prepareStatement("select id, name, longname, prefix, description, shortdescription from manufacturer order by name");
      rs = pstmt.executeQuery();
      Manufacturer manufacturer = null;
      DataFeed datafeed = GetDataFeed(conn, "shopping");

      while (rs.next()) {
        int col=0;
        manufacturer = new Manufacturer();
        manufacturer.setid(rs.getInt(++col));
        manufacturer.setname(rs.getString(++col));
        manufacturer.setlongname(rs.getString(++col));
        manufacturer.setprefix(rs.getString(++col));
        manufacturer.setdescription(rs.getString(++col));
        manufacturer.setshortdescription(rs.getString(++col));
        GetItems(conn, manufacturer, sheet, datafeed);
      }
      File dir = new File(localpath);
      if (!dir.exists()) {
        dir.mkdirs();
      }

      FileOutputStream fileOut = new FileOutputStream(localpath+datafeed.getfilename());
      wb.write(fileOut);
      fileOut.close();

      SendFile(conn, datafeed);
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

  private void GetItems(Connection conn, Manufacturer manufacturer, HSSFSheet sheet, DataFeed datafeed)throws Exception {

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
        item.setmanufacturer(rs.getString(++col));
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
        item.setstatus(rs.getString(++col));
        item.setlistprice(rs.getDouble(++col));
        item.setourprice(rs.getDouble(++col));
        item.setourcost(rs.getDouble(++col));
        GetDetail(conn, item);

        HSSFRow row = sheet.createRow((short)++currentrow);
        short currentcell = -1;
        HSSFCell cell = null;

        cell = row.createCell((short)++currentcell);
        cell.setCellValue(item.getisin().substring(item.getisin().indexOf("-")+1));
        cell = row.createCell((short)++currentcell);
        cell.setCellValue(item.getmanufacturer());
        /* UPC
        cell = row.createCell((short)++currentcell);
        cell.setCellValue("");
        */
        cell = row.createCell((short)++currentcell);
        cell.setCellValue(item.getproductname());
        cell = row.createCell((short)++currentcell);

        boolean bhtml = false;
        StringBuffer buffer = new StringBuffer();
        String value = item.getdetails().getdescription();
        byte[] data = value.getBytes();
        int size = data.length;
        for (int x = 0; x < size; x++) {
          if (!bhtml) {
            if (data[x] == '<') {
              bhtml = true;
            }
            else if (data[x] > 30) {
              buffer.append(value.charAt(x));
            }
          }
          else if (data[x] == '>') {
            bhtml = false;
          }
        }
        cell.setCellValue(buffer.toString());

        cell = row.createCell((short)++currentcell);
        cell.setCellValue(item.getourprice());
        cell = row.createCell((short)++currentcell);
        cell.setCellValue(StoreFrontUrls.getproductdetailsModRewrite(company, item) + "index.html");
        cell = row.createCell((short)++currentcell);
        cell.setCellValue(theme.getimagebaseurl() + item.getdetails().getimageUrlSmall());
        cell = row.createCell((short)++currentcell);
        cell.setCellValue(datafeed.getcategory()); //"Sport and Outdoor"
        cell = row.createCell((short)++currentcell);
        cell.setCellValue((item.getquantity()>0) ? "Y" : "N");
        cell = row.createCell((short)++currentcell);
        cell.setCellValue(item.getstatus());

        /*
        cell = row.createCell((short)++currentcell);
        cell.setCellValue(ground_shipping);
        cell = row.createCell((short)++currentcell);
        cell.setCellValue(second_day_shipping);
        cell = row.createCell((short)++currentcell);
        cell.setCellValue(overnight_shipping);
        */

        cell = row.createCell((short)++currentcell);
        cell.setCellValue(item.getdetails().getshippingweight());
        cell = row.createCell((short)++currentcell);
        cell.setCellValue(zipcode);
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

  private void GetDetail(Connection conn, Item item) throws Exception {

    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      Details detail = new Details();
      pstmt = conn.prepareStatement(selectdetails);
      pstmt.setInt(1, item.getid());
      rs = pstmt.executeQuery();
      if (rs.next()) {
        int col = 0;
        detail.setid(rs.getInt(++col));
        detail.setmanufactureritemnumber(rs.getString(++col));
        detail.setdistributoritemnumber(rs.getString(++col));
        zipcode = rs.getString(++col);
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

  static String selectcompany = "select company, description, address1, address2, "+
      "address3, city, state, zip, country, phone, customerservice, support, fax, " +
      "email1, email2, email3, defaultshipping, baseurl, basesecureurl, " +
      "url, sitemap, webstatid, usemodrewrite, keyword, keyword1, " +
      "keyword2, keyword3, keyword4, keyword5, pw from company where id = ?";

  Company GetCompany(Connection conn, int id) throws Exception {

    Company company = new Company();
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      pstmt = conn.prepareStatement(selectcompany);
      company.setid(id);
      pstmt.setInt(1, id);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        int col = 0;
        company.setcompany(rs.getString(++col));
        company.setdescription(rs.getString(++col));
        company.setaddress1(rs.getString(++col));
        company.setaddress2(rs.getString(++col));
        company.setaddress3(rs.getString(++col));
        company.setcity(rs.getString(++col));
        company.setstate(rs.getString(++col));
        company.setzip(rs.getString(++col));
        company.setcountry(rs.getString(++col));
        company.setphone(rs.getString(++col));
        company.setcustomerservice(rs.getString(++col));
        company.setsupport(rs.getString(++col));
        company.setfax(rs.getString(++col));
        company.setemail1(rs.getString(++col));
        company.setemail2(rs.getString(++col));
        company.setemail3(rs.getString(++col));
        company.setdefaultshipping(rs.getInt(++col));
        company.setbaseurl(rs.getString(++col));
        company.setbasesecureurl(rs.getString(++col));
        company.seturl(rs.getString(++col));
        company.setsitemap(rs.getString(++col));
        company.setwebstatid(rs.getString(++col));
        company.setusemodrewrite(rs.getBoolean(++col));
        company.setkeyword(rs.getString(++col));
        company.setkeyword1(rs.getString(++col));
        company.setkeyword2(rs.getString(++col));
        company.setkeyword3(rs.getString(++col));
        company.setkeyword4(rs.getString(++col));
        company.setkeyword5(rs.getString(++col));
        company.setpw(rs.getString(++col));
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
    return company;
  }

  Theme GetTheme(Connection conn) throws
      Exception {

    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Theme theme = new Theme();

    try {
      pstmt = conn.prepareStatement("select id, name, color1, color2, " +
                                    "color3, color4, color5, image1, image2, " +
                                    "image3, image4, image5, heading1, heading2, " +
                                    "heading3, heading4, heading5, " +
                                    "titleinfo, brandsfirst, metacontenttype, metakeywords, metadescription, " +
                                    "robots, googlebots, imagebaseurl, mostpopularcount, " +
                                    "searchresultcount, featureditemcount from themes");

      rs = pstmt.executeQuery();
      while (rs.next()) {
        int col=0;
        theme.setid(rs.getInt(++col));
        theme.setname(rs.getString(++col));
        theme.setcolor1(rs.getString(++col));
        theme.setcolor2(rs.getString(++col));
        theme.setcolor3(rs.getString(++col));
        theme.setcolor4(rs.getString(++col));
        theme.setcolor5(rs.getString(++col));
        theme.setimage1(rs.getString(++col));
        theme.setimage2(rs.getString(++col));
        theme.setimage3(rs.getString(++col));
        theme.setimage4(rs.getString(++col));
        theme.setimage5(rs.getString(++col));
        theme.setheading1(rs.getString(++col));
        theme.setheading2(rs.getString(++col));
        theme.setheading3(rs.getString(++col));
        theme.setheading4(rs.getString(++col));
        theme.setheading5(rs.getString(++col));
        theme.settitleinfo(rs.getString(++col));
        theme.setbrandsfirst(rs.getBoolean(++col));
        theme.setmetacontenttype(rs.getString(++col));
        theme.setmetakeywords(rs.getString(++col));
        theme.setmetadescription(rs.getString(++col));
        theme.setrobots(rs.getString(++col));
        theme.setgooglebots(rs.getString(++col));
        theme.setimagebaseurl(rs.getString(++col));
        theme.setmostpopularcount(rs.getInt(++col));
        theme.setsearchresultcount(rs.getInt(++col));
        theme.setfeatureditemcount(rs.getInt(++col));
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
    return theme;
  }

  public void SendFile(Connection conn, DataFeed datafeed) {

    try {
      FtpClient client = new FtpClient(datafeed.gethost(),datafeed.getuserid(),datafeed.getpassword());
      client.connect();
      client.changeDir(localpath, false);
      client.putFile(datafeed.getfilename(), datafeed.getfilename());
      client.disconnect();

    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  DataFeed GetDataFeed(Connection conn, String feed) throws
      Exception {

    PreparedStatement pstmt = null;
    ResultSet rs = null;
    DataFeed datafeed = new DataFeed();

    try {
      pstmt = conn.prepareStatement("select id, name, category, filename, type, host, userid, password from datafeed where name = ?");
      pstmt.setString(1, feed);

      rs = pstmt.executeQuery();
      while (rs.next()) {
        int col=0;

        datafeed.setid(rs.getInt(++col));
        datafeed.setname(rs.getString(++col));
        datafeed.setcategory(rs.getString(++col));
        datafeed.setfilename(rs.getString(++col));
        datafeed.settype(rs.getString(++col));
        datafeed.sethost(rs.getString(++col));
        datafeed.setuserid(rs.getString(++col));
        datafeed.setpassword(rs.getString(++col));

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
    return datafeed;
  }
}
