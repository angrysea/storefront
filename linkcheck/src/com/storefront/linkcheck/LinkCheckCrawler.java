package com.storefront.linkcheck;

import java.io.*;
import java.sql.*;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;

public class LinkCheckCrawler{

  private HttpClient client = new HttpClient();
  private String baseurl = null;
  private PageProcessor processor = null;
  private static String dbUrl =
      "jdbc:mysql://10.1.1.50:3306/storefront?user=storefront&password=storefront";

  public LinkCheckCrawler() {
  }

  void setBaseUrl(String baseurl) {
    this.baseurl=baseurl;
  }

  boolean ProcessLinkCheck(String index) {

    boolean bRet = false;
    try {
      processor = new PageProcessor(this);
      processor.init();
      processor.setURLs("www.fernknives.com", index);
      bRet = doTransaction(index);
      processor.shutdown();
    }
    catch (Exception e) {
    }
    return bRet;
  }

  boolean doTransaction(String page) throws Exception {

    boolean bRet = false;
    String resp = getPage(page);
    if (resp != null) {
      processor.processPage(resp, page);
      bRet = processor.wasFound();
    }
    return bRet;
  }

  byte[] getRawPage(String page) throws Exception {

    HttpMethod method = null;
    int statusCode = -1;

    for (int attempt = 0; statusCode == -1 && attempt < 3; attempt++) {
      try {
        method = new GetMethod(page);
        statusCode = client.executeMethod(method);
      } catch (HttpRecoverableException e) {
        System.err.println("A recoverable exception occurred, retrying." + e.getMessage());
      } catch (IOException e) {
        System.err.println("Failed to download file.");
        e.printStackTrace();
      }
    }

    if (statusCode == -1) {
      System.err.println("Failed to recover from exception.");
      return null;
    }

    byte[] responseBody = method.getResponseBody();

    method.releaseConnection();

    return responseBody;
  }

  String getPage(String page) throws Exception {
    return new String(getRawPage(page));
  }


  public static void main(String args[]) {

    Connection conn = null;
    PreparedStatement pstmt = null;
    PreparedStatement pstmtupd = null;

    ResultSet rs = null;
    try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
      conn = DriverManager.getConnection(dbUrl);
      pstmt = conn.prepareStatement("select id, linkback, url from link");
      pstmtupd = conn.prepareStatement("update link set linkback=1 where id = ?");
      rs = pstmt.executeQuery();

      while(rs.next()) {
        LinkCheckCrawler linkCheckCrawler = new LinkCheckCrawler();
        int col=0;
        int id = rs.getInt(++col);
        boolean bLinked = rs.getBoolean(++col);
        String url = rs.getString(++col);
        if(!bLinked) {
          linkCheckCrawler.setBaseUrl(url);
          if(linkCheckCrawler.ProcessLinkCheck(url)) {
            pstmtupd.setInt(1, id);
            pstmtupd.executeUpdate();
          }
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      try {
        if (conn != null) conn.close();
        conn = null;
      }
      catch (SQLException sqle) {}
      try {
        if (pstmt != null) pstmt.close();
        pstmt = null;
      }
      catch (SQLException sqle) {}
      try {
        if (pstmtupd != null) pstmtupd.close();
        pstmtupd = null;
      }
      catch (SQLException sqle) {}
      try {
        if (rs != null) rs.close();
        rs = null;
      }
      catch (SQLException sqle) {}
    }
  }
}
