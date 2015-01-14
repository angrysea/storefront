package com.storefront.minisitecrawler;

import java.io.*;
import java.sql.*;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;

public class MiniSiteCrawler{

  private HttpClient client = new HttpClient();
  private String localpath = "/minisite/temp";
  private String baseurl = null;

  public MiniSiteCrawler(String baseurl) {
    this.baseurl=baseurl;
  }

  String doTransaction(String page) {

    String name = null;
    try {
      String resp = getPage(page);
      if (resp != null) {
        PageProcessor processor = new PageProcessor(this, localpath, baseurl);
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


  public void ProcessMiniSite(String index) {

    try {
      PageProcessor processor = new PageProcessor(this, localpath, baseurl);

      processor.init();
      doTransaction(index);
      processor.SendFiles();
      processor.shutdown();
    }
    catch (Exception e) {
    }
  }

  public static void main(String args[]) {

    try {
      MiniSiteCrawler siteCrawler = new MiniSiteCrawler("http://www.hunting-knives-info.com");
      PageProcessor processor = new PageProcessor(siteCrawler, "", "");
      processor.init();
      siteCrawler.ProcessMiniSite("http://10.1.1.50:81/minisite_index.jsp?company=2");
      processor.shutdown();
    }
    catch (Exception e) {
    }
  }
}
