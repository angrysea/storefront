package com.storefront.storefrontbeans;

import com.adaptinet.transmitter.XMLTransmitter;

public class SiteCrawler {

  public String doTransaction(String page, String baseDirectory, String webRoot) {

    String name = null;
    if (webRoot == null || webRoot.length() < 1)
      webRoot = "./";
    try {
      String resp = getPage(page);
      if (resp != null) {
        PageProcessor processor = new PageProcessor(this, baseDirectory,
            webRoot);
        name = processor.processPage(resp, page);
      }
    }
    catch (Exception e) {}
    return name;
  }

  String getPage(String page) throws Exception {

    XMLTransmitter client = new XMLTransmitter();
    client.setUrl(page);

    String resp = null;
    resp = client.doTransaction("");
    if (resp != null) {
      if (client.getResponseCode() > 200) {
        throw new Exception("Error");
      }
    }
    return resp;
  }
}
