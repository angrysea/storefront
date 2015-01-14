package com.storefront.storefrontbeans;

import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.net.*;
import java.sql.*;

import com.storefront.storefrontrepository.*;
import com.storefront.ftp.*;
import com.adaptinet.adaptinetex.*;
import com.adaptinet.parser.*;

public class PageProcessor extends DefaultHandler {

  private MiniSiteCrawler crawler = null;
  private StringBuffer page = new StringBuffer();
  Connection conn = null;
  ResultSet rs = null;
  protected PreparedStatement pstmtgetskip = null;
  protected PreparedStatement pstmtgetvisited = null;
  protected PreparedStatement pstmtsavevisited = null;
  protected PreparedStatement pstmt = null;
  private FtpClient ftpclient = null;
  private DataFeed datafeed = null;
  private boolean binscript = false;

  private BufferedOutputStream os = null;
  private String baseDirectory = null;
  private String baseurl = null;

  public PageProcessor(Connection conn, MiniSiteCrawler crawler, String baseDirectory, String baseurl) {

    this.crawler = crawler;
    this.baseDirectory = baseDirectory;
    this.baseurl = baseurl;
    this.conn = conn;

    try {
      if(baseDirectory!=null) {
        File dir = new File(baseDirectory);
        if (!dir.isDirectory() && !dir.exists()) {
          dir.mkdirs();
        }
      }
    }
    catch (Exception e) {
    }

    try {
      pstmtgetskip = conn.prepareStatement(
          "select url from skipurl where url = ?");
      pstmtgetvisited = conn.prepareStatement(
          "select url from visitedurl where url = ?");
      pstmtsavevisited = conn.prepareStatement(
          "insert into visitedurl (url) values (?)");
    }
    catch (Exception e) {
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

    try {
      File dir = new File(baseDirectory);
      if (dir.exists()) {
        dir.delete();
      }
    }
    catch (Exception e) {
    }
  }

  public void SendFiles() {

    try {
      datafeed = GetDataFeed(conn);
      ftpclient = new FtpClient(datafeed.gethost(), datafeed.getuserid(),
                                       datafeed.getpassword());
      ftpclient.setTransactionTimeout(1000);
      ftpclient.connect();
      ftpclient.setType("BINARY");
      if(datafeed.getremotedir()!=null&&datafeed.getremotedir().length()>0)
        ftpclient.changeDir(datafeed.getremotedir(), true);

      File dir = new File(baseDirectory);
      ftpclient.changeDir("c:\\", false);
      ftpclient.changeDir(baseDirectory, false);
      processDir(dir);

      ftpclient.disconnect();
    }
    catch(Exception e){}
  }

  void processDir(File dir) {

    try {
      String[] children = dir.list();
      for (int i = 0; i < children.length; i++) {
        File child = new File(dir, children[i]);
        if (child.isDirectory()) {
          try {
            ftpclient.makeDir(child.getName(), true);
          }
          catch(Exception ex){
            //Thows exception if dir already exits
          }
          ftpclient.changeDir(child.getName(), false);
          ftpclient.changeDir(child.getName(), true);
          processDir(child);
          ftpclient.changeDir("..", false);
          ftpclient.changeDir("..", true);
        }
        else {
          ftpclient.putFile(children[i], children[i]);
        }
      }
    }
    catch(Exception e){}
  }

  DataFeed GetDataFeed(Connection conn) throws
      Exception {

    PreparedStatement pstmt = null;
    ResultSet rs = null;
    DataFeed datafeed = new DataFeed();

    try {
      pstmt = conn.prepareStatement("select id, name, filename, localdir, remotedir, type, host, userid, password from datafeed where name = ?");
      pstmt.setString(1, baseurl);

      rs = pstmt.executeQuery();
      while (rs.next()) {
        int col=0;
        datafeed.setid(rs.getInt(++col));
        datafeed.setname(rs.getString(++col));
        datafeed.setfilename(rs.getString(++col));
        datafeed.setlocaldir(rs.getString(++col));
        datafeed.setremotedir(rs.getString(++col));
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


  String processPage(String data, String url) {

    String filename = null;
    try {
      filename = generatename(url);
      HTMLReader xr = new HTMLReader();
      xr.setContentHandler(this);
      xr.parse(new InputSource(new ByteArrayInputStream(data.getBytes())));
      createSubDirectories(baseDirectory+"/"+filename);
      BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(new File(baseDirectory+"/"+filename)));
      os.write(page.toString().getBytes());
    }
    catch(Exception e) {
      System.out.println(e.getMessage());
    }
    return filename;
  }

  String createSubDirectories(String url) {
    String directory = null;

    int x = url.lastIndexOf("/");
    if(x>0) {
      directory = url.substring(0,x);
      File dir = new File(directory);
      if (!dir.exists()) {
        dir.mkdirs();
      }
    }
    else
      directory = null;
    return directory;
  }

  void writeFile(String data) {

    try {
      if(data!=null)
        os.write(data.getBytes());
    }
    catch(Exception e) {
      System.out.println(e.getMessage());
    }
  }

  String getName(String url) {

    String name = null;
    int x = url.indexOf("//"+2);
    if(x>0) {
      name = url.substring(x);
    }
    else {
      name = url;
    }

    x = name.lastIndexOf("/");
    if(x>0) {
      name = name.substring(x);
    }

    return name;
  }

  String getFullName(String url) {

    String name = null;
    int x = url.indexOf("//");
    if(x>0) {
      name = url.substring(x+2);
    }
    else {
      name = url;
    }

    x = name.indexOf("/");
    if(x>0) {
      name = name.substring(x);
    }

    return name;
  }

  String getDirectoryName(String fullpath) {
    String directory = null;
    int x = fullpath.lastIndexOf("/");
    if(x>0) {
      directory = fullpath.substring(x+2);
    }
    else {
      directory = fullpath;
    }

    return directory;
  }

  String checkSubDirectory(String url) {
    String directory = null;

    int x = url.indexOf("//");
    if(x>0) {
      directory = url.substring(x+2);
    }
    else {
      directory = url;
    }

    x = directory.indexOf("/");
    if(x>0) {
      directory = directory.substring(x+1);
    }
    else {
      directory = url;
    }

    x = directory.lastIndexOf("/");
    if(x>0) {
      directory = directory.substring(0,x);
      File dir = new File(baseDirectory+"/"+directory);
      if (!dir.exists()) {
        dir.mkdirs();
      }
    }
    else
      directory = null;
    return directory;
  }

  String writeFileAny(String name) {

    String nameout = getFullName(name);
    try {

      byte data[] = crawler.getRawPage(name);
      checkSubDirectory(name);
      File file = new File(baseDirectory + nameout);
      if (!file.exists()) {
        BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(file));
        os.write(data);
        os.flush();
        os.close();
      }
    }
    catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return nameout;
  }

  String writeFileGIF(String name) {

    String nameout = getFullName(name);
    try {

      byte image[] = crawler.getRawPage(name);
      checkSubDirectory(name);
      File file = new File(baseDirectory + nameout);
      if (!file.exists()) {
        BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(file));
        os.write(image);
        os.flush();
        os.close();
      }
    }
    catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return nameout;
  }

  String writeFileJPG(String name) {

    String nameout = getFullName(name);
    try {

      URL url = new URL(name);
      BufferedImage image = ImageIO.read(url);
      checkSubDirectory(name);
      File file = new File(baseDirectory + nameout);
      if (!file.exists()) {
        BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(file));
        ImageIO.write(image, "jpg", os);
        os.flush();
        os.close();
      }
    }
    catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return nameout;
  }

  void OpenFile(String name) {

    try {
      os = new BufferedOutputStream(new FileOutputStream(new File(name)));
    }
    catch(Exception e) {
      System.out.println(e.getMessage());
    }
  }

  int checkName(String href) {

    int bRet = 1;
    try {
      if(href.indexOf("shoppingcart.jsp")>-1)
        bRet=-1;
      else {
        pstmtgetskip.setString(1, href);
        rs = pstmtgetskip.executeQuery();
        if (rs.next())
          bRet = 0;
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

  public String generatename(String url) throws java.
      io.UnsupportedEncodingException {

    String filename = null;
    if(url.indexOf("minisite_index.jsp")>-1)
      return "index.html";

    int len = url.length();
    int jspat = url.indexOf(".jsp");
    filename = url.substring(url.lastIndexOf('/')+1, jspat);
    if(len>jspat+4) {
      if(filename.startsWith("minisite")) {
        if(filename.startsWith("minisite_product")) {
          filename = "index";
        }
        else {
          filename = filename.substring(8);
        }
    }
      filename = url.substring(jspat+4) + "/" + filename;
    }
    char chars[]=filename.toCharArray();
    len = filename.length();

    int j = 0;
    for (int i = 0; i < len; i++, j++) {
      if (chars[i] == '&') {
        chars[i] = '/';
      }
      else if (chars[i] != '/') {
        while (i < len && (chars[i] == ' ' ||
                           (!Character.isDigit(chars[i]) &&
                            !Character.isUpperCase(chars[i]) &&
                            !Character.isLowerCase(chars[i])))) {
          i++;
        }
      }
      if (i < len)
        chars[j] = chars[i];
    }
    filename = new String(chars, 0, j);
    filename+=".html";
    return filename;
  }

  public void startElement(String uri, String localName, String qName,
                           Attributes attributes) throws ParserException {

    String newurl = null;
    String value = null;
    int nattibute = -1;
    if (qName.equalsIgnoreCase("a")) {
      nattibute = attributes.getIndex("href");
      if (nattibute > -1) {
        value = attributes.getValue(nattibute);
        if (value.indexOf(".jsp") > 0) {
          int check = 0;
          if ((check=checkName(value))>0) {
            try {
              pstmtgetvisited.setString(1, value);
              rs = pstmtgetvisited.executeQuery();
              if (!rs.next()) {
                pstmtsavevisited.setString(1, value);
                pstmtsavevisited.executeUpdate();
                String name = crawler.doTransaction(value);
                if (name != null) {
                  String directory = checkSubDirectory(value);
                  if (directory != null)
                    newurl = baseurl + "/" + directory + "/" + name;
                  else
                    newurl = baseurl + "/" + name;
                  attributes.setValue(nattibute, newurl);
                }
              }
              else {
                String name = generatename(value);
                if (name != null) {
                  String directory = checkSubDirectory(value);
                  if (directory != null)
                    newurl = baseurl + "/" + directory + "/" + name;
                  else
                    newurl = baseurl + "/" + name;
                  attributes.setValue(nattibute, newurl);
                }
              }
            }
            catch (Exception e) {
              e.printStackTrace();
            }
          }
          else if(check>-1) {
            try {
              String name = generatename(value);
              if (name != null) {
                String directory = checkSubDirectory(value);
                if (directory != null)
                  newurl = baseurl + "/" + directory + "/" + name;
                else
                  newurl = baseurl + "/" + name;
                attributes.setValue(nattibute, newurl);
              }
            }
            catch (Exception e) {
              e.printStackTrace();
            }
          }
        }
      }
    }
    else if (qName.equalsIgnoreCase("img")) {
      nattibute = attributes.getIndex("src");
      if (nattibute > -1) {
        value = attributes.getValue(nattibute);
        if (value.indexOf(".jpg") > 0) {
          attributes.setValue(nattibute, baseurl + writeFileJPG(value));
        }
        else if (value.indexOf(".gif") > 0) {
          attributes.setValue(nattibute, baseurl + writeFileGIF(value));
        }
      }
    }
    else if (qName.equalsIgnoreCase("td")) {
      nattibute = attributes.getIndex("background");
      if (nattibute > -1) {
        value = attributes.getValue(nattibute);
        if (value.indexOf(".jpg") > 0) {
          attributes.setValue(nattibute, baseurl + writeFileJPG(value));
        }
        else if (value.indexOf(".gif") > 0) {
          attributes.setValue(nattibute, baseurl + writeFileGIF(value));
        }
      }
    }
    else if (qName.equalsIgnoreCase("link")) {
      nattibute = attributes.getIndex("href");
      if (nattibute > -1) {
        value = attributes.getValue(nattibute);
        attributes.setValue(nattibute, baseurl + writeFileAny(value));
      }
    }
    else if (qName.equalsIgnoreCase("script")) {
      binscript=true;
    }

    page.append("<");
    page.append(qName);
    int size = attributes.getLength();
    if (size > 0) {
      page.append(" ");
    }
    for (int i = 0; i < size; i++) {
      page.append(attributes.getQName(i));
      page.append("=");
      page.append("\"");
      page.append(attributes.getValue(i));
      page.append("\"");
      if(i!=size-1) {
        page.append(" ");
      }
    }
    page.append(">");
  }

  public void endElement(String uri, String localName, String qName) throws
      ParserException {
    page.append("</");
    page.append(qName);
    page.append(">");
    if (qName.equalsIgnoreCase("script")) {
      binscript=false;
    }
  }

  public void characters(char ch[], int start, int length) throws
      ParserException {
    if(binscript) {
      page.append(processScript(new String(ch, start, length)));
    }
    else {
      page.append(ch, start, length);
    }
  }

  private String processScript(String script) {
    StringBuffer buffer = new StringBuffer();
    int loc = script.indexOf("http://");
    if(loc<0) {
      return script;
    }
    buffer.append(script.substring(0, loc));
    script = script.substring(loc);
    loc = script.indexOf("\"");
    String url = script.substring(0,loc);
    buffer.append(processFile(url));
    buffer.append(script.substring(loc));
    return buffer.toString();
  }

  private String processFile(String url) {
    String outName = null;
    if (url.indexOf(".jpg") > 0) {
      outName = baseurl + writeFileJPG(url);
    }
    else if (url.indexOf(".gif") > 0) {
      outName = baseurl + writeFileGIF(url);
    }
    else {
      outName = baseurl + writeFileAny(url);
    }
    return outName;
  }

}
