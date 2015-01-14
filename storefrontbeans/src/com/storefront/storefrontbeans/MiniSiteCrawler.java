package com.storefront.storefrontbeans;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;

public class MiniSiteCrawler extends BaseBean {

  private HttpClient client = new HttpClient();
  private String localpath = "/minisite/";
  private String baseurl = null;
  private Connection conn = null;

  String[] getfields() {
    return null;
  }

  String gettableName() {
    return null;
  }

  String getindexName() {
    return null;
  }

  public MiniSiteCrawler() throws ServletException {
    super();
  }

  String doTransaction(String page) {

    String name = null;
    try {
      String resp = getPage(page);
      if (resp != null) {
        PageProcessor processor = new PageProcessor(conn, this, localpath+baseurl.substring(7), baseurl);
        name = processor.processPage(resp,page);
      }
    }
    catch (Exception e) {}
    return name;
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

  public void ProcessMiniSite(String baseurl, String index) {

    ProcessMiniSite(baseurl, index, true);
  }

  public void ProcessMiniSite(String baseurl, String index, boolean bsendfiles) {

    try {
      this.baseurl=baseurl;
      conn = datasource.getConnection();
      PageProcessor processor = new PageProcessor(conn, this, localpath+baseurl.substring(7), baseurl);

      processor.init();
      doTransaction(index);
      if(bsendfiles) {
        processor.SendFiles();
      }
      processor.shutdown();
    }
    catch (Exception e) {
    }
    finally {
      try { if(conn!=null) conn.close(); conn=null; } catch(SQLException sqle ){}
    }
  }
}
