package com.storefront.linkcheck;

import java.io.*;
import java.net.*;
import java.sql.*;

import com.storefront.storefrontrepository.*;
import com.storefront.ftp.*;
import com.adaptinet.adaptinetex.*;
import com.adaptinet.parser.*;

public class PageProcessor extends DefaultHandler {

  private LinkCheckCrawler crawler = null;
  private String baseurl = null;
  private String companyurl = null;
  Connection conn = null;
  ResultSet rs = null;
  boolean bFound = false;

  protected PreparedStatement pstmtgetskip = null;
  protected PreparedStatement pstmtgetvisited = null;
  protected PreparedStatement pstmtsavevisited = null;
  protected PreparedStatement pstmt = null;

  private static String dbUrl =
      "jdbc:mysql://10.1.1.50:3306/storefront?user=storefront&password=storefront";

  private BufferedOutputStream os = null;
  private String currenturl = null;

  public PageProcessor(LinkCheckCrawler crawler) throws Exception {

    this.crawler = crawler;

    try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
      conn = DriverManager.getConnection(dbUrl);
      pstmtgetskip = conn.prepareStatement(
          "select url from skipurl where url = ?");
      pstmtgetvisited = conn.prepareStatement(
          "select url from visitedurl where url = ?");
      pstmtsavevisited = conn.prepareStatement(
          "insert into visitedurl (url) values (?)");
    }
    catch (Exception e) {
      throw e;
    }
  }

  void init() {

    PreparedStatement pstmt = null;
    try {
      pstmt = conn.prepareStatement("delete from visitedurl");
      pstmt.executeUpdate();
      pstmt.close();
    }
    catch (Exception e) {
    }
    finally {
      try {
        if (pstmt != null) pstmt.close();
        pstmt = null;
      }
      catch (SQLException sqle) {}
    }
  }

  void resetFound() {
    bFound = false;
  }

  void setURLs(String companyurl, String baseurl) {

    bFound = false;
    if(baseurl.endsWith("/")) {
      this.baseurl = baseurl.substring(0,baseurl.length()-1).toLowerCase();
    }
    else {
      this.baseurl = baseurl.toLowerCase();
    }

    if(companyurl.endsWith("/")) {
      this.companyurl = companyurl.substring(0,companyurl.length()-1).toLowerCase();
    }
    else {
      this.companyurl = companyurl.toLowerCase();
    }
  }

  boolean wasFound() {
    return bFound;
  }

  String processPage(String data, String url) {

    String filename = null;
    currenturl = url;
    try {
      HTMLReader xr = new HTMLReader();
      xr.setContentHandler(this);
      xr.parse(new InputSource(new ByteArrayInputStream(data.getBytes())));
    }
    catch(Exception e) {
      System.out.println(e.getMessage());
    }
    return filename;
  }

  void shutdown() {
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

  int checkName(String href) {

    int bRet = 1;
    try {
      if(href==null || href.length()<1) {
        return 0;
      }
      pstmtgetskip.setString(1, href);
      rs = pstmtgetskip.executeQuery();
      if (rs.next())
        bRet = 0;
      rs.close();
      pstmtgetvisited.setString(1, href);
      rs = pstmtgetvisited.executeQuery();
      if (rs.next()) {
        bRet = 0;
      }
      else {
        pstmtsavevisited.setString(1, href);
        pstmtsavevisited.executeUpdate();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      try {
        if (rs != null) rs.close();
        rs = null;
      }
      catch (SQLException sqle) {}
    }

    return bRet;
  }

  public void startElement(String uri, String localName, String qName,
                           Attributes attributes) throws ParserException {

    String value = null;
    int nattibute = -1;
    if (bFound == false) {
      if (qName.equalsIgnoreCase("a") || qName.equalsIgnoreCase("link")) {
        nattibute = attributes.getIndex("href");
        if (nattibute > -1) {
          value = attributes.getValue(nattibute);
          if (value.toLowerCase().indexOf(companyurl) > -1) {
            bFound = true;
          }
          else {
            String url = null;
            if (value.toLowerCase().startsWith(baseurl)) {
              url = value;
            }
            else if (value.startsWith("./")) {
              url = baseurl + value.substring(1);
            }
            else if (value.startsWith("/")) {
              url = baseurl + value;
            }
            else if (!value.startsWith("http://") &&
                     !value.startsWith("https://") &&
                     value.indexOf("javascript") < 0 &&
                     value.indexOf("mailto") < 0) {
              url = baseurl + "/" + value;
            }
            if (url != null) {
              if (checkName(url) > 0) {
                try {
                  try {
                    bFound = crawler.doTransaction(url);
                  }
                  catch (Exception e) {
                    e.printStackTrace();
                  }
                }
                catch (Exception e) {
                  e.printStackTrace();
                }
              }
            }
          }
        }
      }
    }
  }

  public void endElement(String uri, String localName, String qName) throws
      ParserException {
  }

  public void characters(char ch[], int start, int length) throws
      ParserException {
  }
}
