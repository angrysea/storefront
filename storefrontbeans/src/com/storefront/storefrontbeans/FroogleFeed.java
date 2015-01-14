package com.storefront.storefrontbeans;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import org.apache.poi.hssf.usermodel.*;
import com.storefront.storefrontrepository.*;
import com.storefront.ftp.*;

public class FroogleFeed extends BaseBean {


  private static String product_url = "product_url";
  private static String name = "name";
  private static String descritpion = "description";
  private static String image_url = "image_url";
  private static String category = "category";
  private static String price = "price";

  private static final short colurl = 0;
  private static final short colname = 1;
  private static final short coldesc = 2;
  private static final short colimage = 3;
  private static final short colcat = 4;
  private static final short colprice = 5;
  private Company company = null;
  private Theme theme = null;
  int currentrow = 0;

  final static private String selectitem =
      "select a.id, a.catalog, a.isin, a.productname, a.manufacturer, b.name, " +
      "a.distributor, a.quantity, a.allocated, a.sold, a.quantitytoorder, a.backordered, a.minimumonhand, " +
      "a.reorderquantity, a.quantityonorder, a.status, a.listPrice, a.ourPrice, " +
      "a.ourcost from item a, manufacturer b where manufacturer=b.id and manufacturer = ? ";

  final static private String selectdetails =
      "select itemnumber, manufactureritemnumber, distributoritemnumber, " +
      "description, imageUrlSmall, imageUrlMedium, imageUrlLarge, salesRank, availability, " +
      "shippingweight, height, length, width, handlingcharges, url from details where itemnumber = ?";

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

  public FroogleFeed() throws ServletException {
    super();
    this.localpath="c:\\feed\\froogle\\";
  }

  public FroogleFeed(String localpath) throws ServletException {
    super();
    this.localpath=localpath;
  }

  public void SendFile(Connection conn, HSSFSheet sheet, DataFeed datafeed) {

    HSSFCell cell = null;
    int last = sheet.getLastRowNum() + 1;
    StringBuffer buffer = new StringBuffer();
    buffer.append("product_url");
    buffer.append("\t");
    buffer.append("name");
    buffer.append("\t");
    buffer.append("description");
    buffer.append("\t");
    buffer.append("image_url");
    buffer.append("\t");
    buffer.append("category");
    buffer.append("\t");
    buffer.append("price");
    buffer.append("\r\n");

    try {
      boolean bhtml = false;
      for (int i = 1; i < last; i++) {
        HSSFRow row = sheet.getRow(i);
        for (short col = 0; col < 6; col++) {
          if ( (cell = row.getCell(col)) != null) {
            switch (col) {
              case colimage:
              case colname:
              case colcat:
              case colurl:
                buffer.append(cell.getStringCellValue());
                buffer.append("\t");
                break;

              case coldesc:
                buffer.append("\"");
                buffer.append(cell.getStringCellValue());
                buffer.append("\"\t");
                break;

              case colprice:
                buffer.append(cell.getNumericCellValue());
                buffer.append("\r\n");
                break;

              default:
                break;
            }
          }
        }
      }

      BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(new File(localpath + datafeed.getfilename())));
      os.write(buffer.toString().getBytes());
      os.flush();
      os.close();
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

  public void GenerateFeedFile() throws Exception {

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = datasource.getConnection();
      company = GetCompany(conn, 1);
      theme = GetTheme(conn);
      HSSFWorkbook wb = new HSSFWorkbook();
      HSSFSheet sheet = wb.createSheet("froogle feed");

      HSSFRow row = sheet.createRow((short)currentrow);
      short currentcell = -1;
      HSSFCell cell = null;
      cell = row.createCell((short)++currentcell);
      cell.setCellValue(product_url);
      cell = row.createCell((short)++currentcell);
      cell.setCellValue(name);
      cell = row.createCell((short)++currentcell);
      cell.setCellValue(descritpion);
      cell = row.createCell((short)++currentcell);
      cell.setCellValue(image_url);
      cell = row.createCell((short)++currentcell);
      cell.setCellValue(category);
      cell = row.createCell((short)++currentcell);
      cell.setCellValue(price);

      pstmt = conn.prepareStatement("select id, name, longname, prefix, description, shortdescription from manufacturer order by name");
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
        GetItems(conn, manufacturer, sheet);
      }
      File dir = new File(localpath);
      if (!dir.exists()) {
        dir.mkdirs();
      }
      DataFeed datafeed = GetDataFeed(conn, "froogle");

      FileOutputStream fileOut = new FileOutputStream(localpath+"frooglefeed.xls");
      wb.write(fileOut);
      fileOut.close();

      SendFile(conn, sheet, datafeed);
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

  private void GetItems(Connection conn, Manufacturer manufacturer, HSSFSheet sheet)throws Exception {

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
        item.setlistprice(rs.getDouble(++col));
        item.setourprice(rs.getDouble(++col));
        item.setourcost(rs.getDouble(++col));
        GetDetail(conn, item);

        HSSFRow row = sheet.createRow((short)++currentrow);
        short currentcell = -1;
        HSSFCell cell = null;

        String url =
            StoreFrontUrls.getproductdetailsModRewrite(company, item) +
            "index.html";

        cell = row.createCell((short)++currentcell);
        cell.setCellValue(url);
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
        cell.setCellValue(theme.getimagebaseurl() + item.getdetails().getimageUrlSmall());
        cell = row.createCell((short)++currentcell);
        cell.setCellValue(manufacturer.getname() + " " + company.getkeyword());
        cell = row.createCell((short)++currentcell);
        cell.setCellValue(item.getourprice());

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

  DataFeed GetDataFeed(Connection conn, String feed) throws
      Exception {

    PreparedStatement pstmt = null;
    ResultSet rs = null;
    DataFeed datafeed = new DataFeed();

    try {
      pstmt = conn.prepareStatement("select id, name, filename, type, host, userid, password from datafeed where name = ?");
      pstmt.setString(1, feed);

      rs = pstmt.executeQuery();
      while (rs.next()) {
        int col=0;

        datafeed.setid(rs.getInt(++col));
        datafeed.setname(rs.getString(++col));
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

  public static void main(String args[]) {

    try {
      FroogleFeed frooglefeed = new FroogleFeed("/feed/froogle/");
      frooglefeed.GenerateFeedFile();
    }
    catch (Exception e) {
    }
  }
}
