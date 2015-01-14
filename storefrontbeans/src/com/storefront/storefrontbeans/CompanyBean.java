package com.storefront.storefrontbeans;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class CompanyBean
    extends BaseBean {

  final static private String fields[] = {
      "company", "description", "address1", "address2", "address3", "city",
      "state", "zip", "country", "phone", "customerservice", "support", "fax",
      "email1", "email2", "email3", "defaultshipping", "freeshipping", "baseurl",
      "basesecureurl", "baseaffiliateurl", "currentaffiliateplan", "url",
      "linkexchangetext", "sitemap", "webstatid", "googleconversionid", "shoppingconversionid",
      "themeid", "usemodrewrite", "siteseal", "salesordercoupon", "keyword",
      "keyword1", "keyword2", "keyword3", "keyword4", "keyword5", "instockonly", "pw"};

  static final String selecttheme =
      "select id, name, color1, color2, " +
      "color3, color4, color5, image1, image2, " +
      "image3, image4, image5, heading1, heading2, " +
      "heading3, heading4, heading5, " +
      "titleinfo, brandsfirst, metacontenttype, metakeywords, metadescription, " +
      "robots, googlebots, imagebaseurl, mostpopularcount, " +
      "searchresultcount, featureditemcount from themes where ";

  final static private String tablename = "company";

  String[] getfields() {
    return fields;
  }

  String gettableName() {
    return tablename;
  }

  String getindexName() {
    return "id";
  }

  public CompanyBean() throws ServletException {
    super();
  }

  public AddCompanyResponse AddCompany(AddCompanyRequest request) throws
      ServletException {

    AddCompanyResponse response = new AddCompanyResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getInsertString());
      Company company = request.getcompany();
      int col = 0;
      pstmt.setString(++col, company.getcompany());
      pstmt.setString(++col, company.getdescription());
      pstmt.setString(++col, company.getaddress1());
      pstmt.setString(++col, company.getaddress2());
      pstmt.setString(++col, company.getaddress3());
      pstmt.setString(++col, company.getcity());
      pstmt.setString(++col, company.getstate());
      pstmt.setString(++col, company.getzip());
      pstmt.setString(++col, company.getcountry());
      pstmt.setString(++col, company.getphone());
      pstmt.setString(++col, company.getcustomerservice());
      pstmt.setString(++col, company.getsupport());
      pstmt.setString(++col, company.getfax());
      pstmt.setString(++col, company.getemail1());
      pstmt.setString(++col, company.getemail2());
      pstmt.setString(++col, company.getemail3());
      pstmt.setInt(++col, company.getdefaultshipping());
      pstmt.setInt(++col, company.getfreeshipping());
      pstmt.setString(++col, company.getbaseurl());
      pstmt.setString(++col, company.getbasesecureurl());
      pstmt.setString(++col, company.getbaseaffiliateurl());
      pstmt.setInt(++col, company.getcurrentaffiliateplan());
      pstmt.setString(++col, company.geturl());
      pstmt.setString(++col, company.getlinkexchangetext());
      pstmt.setString(++col, company.getsitemap());
      pstmt.setString(++col, company.getwebstatid());
      pstmt.setString(++col, company.getgoogleconversionid());
      pstmt.setString(++col, company.getshoppingconversionid());
      pstmt.setInt(++col, company.getthemeid());
      pstmt.setBoolean(++col, company.getusemodrewrite());
      pstmt.setBoolean(++col, company.getsiteseal());
      pstmt.setString(++col, company.getsalesordercoupon());
      pstmt.setString(++col, company.getkeyword());
      pstmt.setString(++col, company.getkeyword1());
      pstmt.setString(++col, company.getkeyword2());
      pstmt.setString(++col, company.getkeyword3());
      pstmt.setString(++col, company.getkeyword4());
      pstmt.setString(++col, company.getkeyword5());
      pstmt.setInt(++col, company.getinstockonly());
      pstmt.setString(++col, company.getpw());
      if ( (pstmt.executeUpdate()) > 0) {
        company.setid(getLastInsertID(conn));
        response.setid(company.getid());
      }
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
      try {
        if (conn != null) conn.close();
        conn = null;
      }
      catch (SQLException sqle) {}
    }
    return response;
  }

  public UpdateCompanyResponse UpdateCompany(UpdateCompanyRequest request) throws
      ServletException {

    UpdateCompanyResponse response = new UpdateCompanyResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getUpdateString());
      Company company = request.getcompany();
      int col = 0;
      pstmt.setString(++col, company.getcompany());
      pstmt.setString(++col, company.getdescription());
      pstmt.setString(++col, company.getaddress1());
      pstmt.setString(++col, company.getaddress2());
      pstmt.setString(++col, company.getaddress3());
      pstmt.setString(++col, company.getcity());
      pstmt.setString(++col, company.getstate());
      pstmt.setString(++col, company.getzip());
      pstmt.setString(++col, company.getcountry());
      pstmt.setString(++col, company.getphone());
      pstmt.setString(++col, company.getcustomerservice());
      pstmt.setString(++col, company.getsupport());
      pstmt.setString(++col, company.getfax());
      pstmt.setString(++col, company.getemail1());
      pstmt.setString(++col, company.getemail2());
      pstmt.setString(++col, company.getemail3());
      pstmt.setInt(++col, company.getdefaultshipping());
      pstmt.setInt(++col, company.getfreeshipping());
      pstmt.setString(++col, company.getbaseurl());
      pstmt.setString(++col, company.getbasesecureurl());
      pstmt.setString(++col, company.getbaseaffiliateurl());
      pstmt.setInt(++col, company.getcurrentaffiliateplan());
      pstmt.setString(++col, company.geturl());
      pstmt.setString(++col, company.getlinkexchangetext());
      pstmt.setString(++col, company.getsitemap());
      pstmt.setString(++col, company.getwebstatid());
      pstmt.setString(++col, company.getgoogleconversionid());
      pstmt.setString(++col, company.getshoppingconversionid());
      pstmt.setInt(++col, company.getthemeid());
      pstmt.setBoolean(++col, company.getusemodrewrite());
      pstmt.setBoolean(++col, company.getsiteseal());
      pstmt.setString(++col, company.getsalesordercoupon());
      pstmt.setString(++col, company.getkeyword());
      pstmt.setString(++col, company.getkeyword1());
      pstmt.setString(++col, company.getkeyword2());
      pstmt.setString(++col, company.getkeyword3());
      pstmt.setString(++col, company.getkeyword4());
      pstmt.setString(++col, company.getkeyword5());
      pstmt.setInt(++col, company.getinstockonly());
      pstmt.setString(++col, company.getpw());
      pstmt.setInt(++col, company.getid());
      pstmt.executeUpdate();
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
      try {
        if (conn != null) conn.close();
        conn = null;
      }
      catch (SQLException sqle) {}
    }
    return response;
  }

  public GetCompanyResponse GetCompany(HttpServletRequest httprequest,
                                       GetCompanyRequest request) throws
      ServletException {
    GetCompanyResponse response = new GetCompanyResponse();
    Connection conn = null;
    try {
      Company company = null;
      if ( (company = (Company) httprequest.getAttribute("companyobject")) == null) {
        conn = datasource.getConnection();
        company = GetCompany(conn, request.getid());
        httprequest.setAttribute("companyobject", company);
      }
      response.setcompany(company);
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try {
        if (conn != null) conn.close();
        conn = null;
      }
      catch (SQLException sqle) {}
    }
    return response;
  }

  Company GetCompany(Connection conn, int id) throws
      ServletException {

    Company company = new Company();
    try {

      if (id < 1)
        id = 1;

      pstmt = conn.prepareStatement(getSelectWithIDByIDString());
      company.setid(id);
      pstmt.setInt(1, id);

      rs = pstmt.executeQuery();
      if (rs.next()) {
        int col = 0;
        company.setid(rs.getInt(++col));
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
        company.setfreeshipping(rs.getInt(++col));
        company.setbaseurl(rs.getString(++col));
        company.setbasesecureurl(rs.getString(++col));
        company.setbaseaffiliateurl(rs.getString(++col));
        company.setcurrentaffiliateplan(rs.getInt(++col));
        company.seturl(rs.getString(++col));
        company.setlinkexchangetext(rs.getString(++col));
        company.setsitemap(rs.getString(++col));
        company.setwebstatid(rs.getString(++col));
        company.setgoogleconversionid(rs.getString(++col));
        company.setshoppingconversionid(rs.getString(++col));
        company.setthemeid(rs.getInt(++col));
        company.setusemodrewrite(rs.getBoolean(++col));
        company.setsiteseal(rs.getBoolean(++col));
        company.setsalesordercoupon(rs.getString(++col));
        company.setkeyword(rs.getString(++col));
        company.setkeyword1(rs.getString(++col));
        company.setkeyword2(rs.getString(++col));
        company.setkeyword3(rs.getString(++col));
        company.setkeyword4(rs.getString(++col));
        company.setkeyword5(rs.getString(++col));
        company.setinstockonly(rs.getInt(++col));
        company.setpw(rs.getString(++col));
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
    return company;
  }

  public GetCompaniesResponse GetCompanies(GetCompaniesRequest request) throws
      ServletException {
    GetCompaniesResponse response = new GetCompaniesResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getSelectWithIDString() + " order by id");
      rs = pstmt.executeQuery();
      while (rs.next()) {
        Company company = new Company();
        int col = 0;
        company.setid(rs.getInt(++col));
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
        company.setfreeshipping(rs.getInt(++col));
        company.setbaseurl(rs.getString(++col));
        company.setbasesecureurl(rs.getString(++col));
        company.setbaseaffiliateurl(rs.getString(++col));
        company.setcurrentaffiliateplan(rs.getInt(++col));
        company.seturl(rs.getString(++col));
        company.setlinkexchangetext(rs.getString(++col));
        company.setsitemap(rs.getString(++col));
        company.setwebstatid(rs.getString(++col));
        company.setgoogleconversionid(rs.getString(++col));
        company.setshoppingconversionid(rs.getString(++col));
        company.setthemeid(rs.getInt(++col));
        company.setusemodrewrite(rs.getBoolean(++col));
        company.setsiteseal(rs.getBoolean(++col));
        company.setsalesordercoupon(rs.getString(++col));
        company.setkeyword(rs.getString(++col));
        company.setkeyword1(rs.getString(++col));
        company.setkeyword2(rs.getString(++col));
        company.setkeyword3(rs.getString(++col));
        company.setkeyword4(rs.getString(++col));
        company.setkeyword5(rs.getString(++col));
        company.setinstockonly(rs.getInt(++col));
        company.setpw(rs.getString(++col));
        response.setcompanies(company);
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
      try {
        if (conn != null) conn.close();
        conn = null;
      }
      catch (SQLException sqle) {}
    }
    return response;
  }

  public AddCatalogResponse AddCatalog(AddCatalogRequest request) throws
      ServletException {

    AddCatalogResponse response = new AddCatalogResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      String inscatalog =
          "insert into catalog (id, name, description, address1, address2, address3, " +
          "city, state, zip, country, phone, customerservice, support, fax, email1, " +
          "email2, email3, baseurl, basesecureurl, theme, url) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

      pstmt = conn.prepareStatement(inscatalog);
      Catalog catalog = request.getcatalog();
      int col = 0;
      pstmt.setString(++col, catalog.getname());
      pstmt.setString(++col, catalog.getdescription());
      pstmt.setString(++col, catalog.getaddress1());
      pstmt.setString(++col, catalog.getaddress2());
      pstmt.setString(++col, catalog.getaddress3());
      pstmt.setString(++col, catalog.getcity());
      pstmt.setString(++col, catalog.getstate());
      pstmt.setString(++col, catalog.getzip());
      pstmt.setString(++col, catalog.getcountry());
      pstmt.setString(++col, catalog.getphone());
      pstmt.setString(++col, catalog.getcustomerservice());
      pstmt.setString(++col, catalog.getsupport());
      pstmt.setString(++col, catalog.getfax());
      pstmt.setString(++col, catalog.getemail1());
      pstmt.setString(++col, catalog.getemail2());
      pstmt.setString(++col, catalog.getemail3());
      pstmt.setString(++col, catalog.getbaseurl());
      pstmt.setString(++col, catalog.getbasesecureurl());
      pstmt.setString(++col, catalog.geturl());
      pstmt.setInt(++col, catalog.gettheme());
      if ( (pstmt.executeUpdate()) > 0) {
        catalog.setid(getLastInsertID(conn));
        response.setid(catalog.getid());
      }
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
      try {
        if (conn != null) conn.close();
        conn = null;
      }
      catch (SQLException sqle) {}
    }
    return response;
  }

  public UpdateCatalogResponse UpdateCatalog(UpdateCatalogRequest request) throws
      ServletException {

    UpdateCatalogResponse response = new UpdateCatalogResponse();
    Connection conn = null;
    try {
      String updcatalog =
          "update catalog set name=?, description=?, address1=?, address2=?, address3=?, " +
          "city=?, state=?, zip=?, country=?, phone=?, customerservice=?, support=?, fax=?, email1=?, " +
          "email2=?, email3=?, baseurl=?, basesecureurl=?, theme=?, url=?) where id=?";
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(updcatalog);
      Catalog catalog = request.getcatalog();
      int col = 0;
      pstmt.setString(++col, catalog.getname());
      pstmt.setString(++col, catalog.getdescription());
      pstmt.setString(++col, catalog.getaddress1());
      pstmt.setString(++col, catalog.getaddress2());
      pstmt.setString(++col, catalog.getaddress3());
      pstmt.setString(++col, catalog.getcity());
      pstmt.setString(++col, catalog.getstate());
      pstmt.setString(++col, catalog.getzip());
      pstmt.setString(++col, catalog.getcountry());
      pstmt.setString(++col, catalog.getphone());
      pstmt.setString(++col, catalog.getcustomerservice());
      pstmt.setString(++col, catalog.getsupport());
      pstmt.setString(++col, catalog.getfax());
      pstmt.setString(++col, catalog.getemail1());
      pstmt.setString(++col, catalog.getemail2());
      pstmt.setString(++col, catalog.getemail3());
      pstmt.setString(++col, catalog.getbaseurl());
      pstmt.setString(++col, catalog.getbasesecureurl());
      pstmt.setString(++col, catalog.geturl());
      pstmt.setInt(++col, catalog.gettheme());
      pstmt.setInt(++col, catalog.getid());
      pstmt.executeUpdate();
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
      try {
        if (conn != null) conn.close();
        conn = null;
      }
      catch (SQLException sqle) {}
    }
    return response;
  }

  public GetCatalogResponse GetCatalog(GetCatalogRequest request) throws
      ServletException {
    GetCatalogResponse response = new GetCatalogResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      response.setcatalog(GetCatalog(conn, request.getid()));
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try {
        if (conn != null) conn.close();
        conn = null;
      }
      catch (SQLException sqle) {}
    }
    return response;
  }

  Catalog GetCatalog(Connection conn, int id) throws
      ServletException {
    Catalog catalog = null;
    try {

      String selcatalog =
          "select id, name, description, address1, address2, address3, " +
          "city, state, zip, country, phone, customerservice, support, fax, email1, " +
          "email2, email3, baseurl, basesecureurl, theme, url where id = ?";

      pstmt = conn.prepareStatement(selcatalog);
      pstmt.setInt(1, id);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        int col = 0;
        catalog = new Catalog();
        catalog.setid(id);
        catalog.setname(rs.getString(++col));
        catalog.setdescription(rs.getString(++col));
        catalog.setaddress1(rs.getString(++col));
        catalog.setaddress2(rs.getString(++col));
        catalog.setaddress3(rs.getString(++col));
        catalog.setcity(rs.getString(++col));
        catalog.setstate(rs.getString(++col));
        catalog.setzip(rs.getString(++col));
        catalog.setcountry(rs.getString(++col));
        catalog.setphone(rs.getString(++col));
        catalog.setcustomerservice(rs.getString(++col));
        catalog.setsupport(rs.getString(++col));
        catalog.setfax(rs.getString(++col));
        catalog.setemail1(rs.getString(++col));
        catalog.setemail2(rs.getString(++col));
        catalog.setemail3(rs.getString(++col));
        catalog.setbaseurl(rs.getString(++col));
        catalog.setbasesecureurl(rs.getString(++col));
        catalog.settheme(rs.getInt(++col));
        catalog.seturl(rs.getString(++col));
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
    return catalog;
  }

  public AddThemeResponse AddTheme(AddThemeRequest request) throws
      ServletException {
    AddThemeResponse response = new AddThemeResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(
          "insert into themes (name, color1, color2, " +
          "color3, color4, color5, image1, image2, " +
          "image3, image4, image5, heading1, heading2, " +
          "heading3, heading4, heading5, " +
          "titleinfo, metacontenttype, metakeywords, metadescription, " +
          "robots, googlebots, imagebaseurl, mostpopularcount, searchresultcount, " +
          "featureditemcount) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

      Theme theme = new Theme();
      int col = 0;
      pstmt.setString(++col, theme.getname());
      pstmt.setString(++col, theme.getcolor1());
      pstmt.setString(++col, theme.getcolor2());
      pstmt.setString(++col, theme.getcolor3());
      pstmt.setString(++col, theme.getcolor4());
      pstmt.setString(++col, theme.getcolor5());
      pstmt.setString(++col, theme.getimage1());
      pstmt.setString(++col, theme.getimage2());
      pstmt.setString(++col, theme.getimage3());
      pstmt.setString(++col, theme.getimage4());
      pstmt.setString(++col, theme.getimage5());
      pstmt.setString(++col, theme.getheading1());
      pstmt.setString(++col, theme.getheading2());
      pstmt.setString(++col, theme.getheading3());
      pstmt.setString(++col, theme.getheading4());
      pstmt.setString(++col, theme.getheading5());
      pstmt.setString(++col, theme.gettitleinfo());
      pstmt.setString(++col, theme.getmetacontenttype());
      pstmt.setString(++col, theme.getmetakeywords());
      pstmt.setString(++col, theme.getmetadescription());
      pstmt.setString(++col, theme.getrobots());
      pstmt.setString(++col, theme.getgooglebots());
      pstmt.setString(++col, theme.getimagebaseurl());
      pstmt.setInt(++col, theme.getmostpopularcount());
      pstmt.setInt(++col, theme.getsearchresultcount());
      pstmt.setInt(++col, theme.getfeatureditemcount());
      if ( (pstmt.executeUpdate()) > 0) {
        theme.setid(getLastInsertID(conn));
        response.setid(theme.getid());
      }
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
      try {
        if (conn != null) conn.close();
        conn = null;
      }
      catch (SQLException sqle) {}
    }
    return response;
  }

  public UpdateThemeResponse UpdateTheme(UpdateThemeRequest request) throws
      ServletException {
    UpdateThemeResponse response = new UpdateThemeResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(
          "update themes set name=?, color1=?, color2=?, " +
          "color3=?, color4=?, color5=?, image1=?, image2=?, " +
          "image3=?, image4=?, image5=?, heading1=?, heading2=?, " +
          "heading3=?, heading4=?, heading5=?, " +
          "titleinfo=?, metacontenttype=?, metakeywords=?, metadescription=?, " +
          "robots=?, googlebots=?, imagebaseurl=?, mostpopularcount=?, " +
          "searchresultcount=?, featureditemcount=? where id=?");

      Theme theme = request.gettheme();
      int col = 0;
      pstmt.setString(++col, theme.getname());
      pstmt.setString(++col, theme.getcolor1());
      pstmt.setString(++col, theme.getcolor2());
      pstmt.setString(++col, theme.getcolor3());
      pstmt.setString(++col, theme.getcolor4());
      pstmt.setString(++col, theme.getcolor5());
      pstmt.setString(++col, theme.getimage1());
      pstmt.setString(++col, theme.getimage2());
      pstmt.setString(++col, theme.getimage3());
      pstmt.setString(++col, theme.getimage4());
      pstmt.setString(++col, theme.getimage5());
      pstmt.setString(++col, theme.getheading1());
      pstmt.setString(++col, theme.getheading2());
      pstmt.setString(++col, theme.getheading3());
      pstmt.setString(++col, theme.getheading4());
      pstmt.setString(++col, theme.getheading5());
      pstmt.setString(++col, theme.gettitleinfo());
      pstmt.setString(++col, theme.getmetacontenttype());
      pstmt.setString(++col, theme.getmetakeywords());
      pstmt.setString(++col, theme.getmetadescription());
      pstmt.setString(++col, theme.getrobots());
      pstmt.setString(++col, theme.getgooglebots());
      pstmt.setString(++col, theme.getimagebaseurl());
      pstmt.setInt(++col, theme.getmostpopularcount());
      pstmt.setInt(++col, theme.getsearchresultcount());
      pstmt.setInt(++col, theme.getfeatureditemcount());
      pstmt.setInt(++col, theme.getid());
      pstmt.executeUpdate();
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
      try {
        if (conn != null) conn.close();
        conn = null;
      }
      catch (SQLException sqle) {}
    }
    return response;
  }

  public GetThemeResponse GetTheme(HttpServletRequest httprequest,
                                   GetThemeRequest request) throws
      ServletException {
    GetThemeResponse response = new GetThemeResponse();
    Connection conn = null;
    try {
      Theme theme = null;

      if ( (theme = (Theme) httprequest.getAttribute("themeobject")) == null) {

        conn = datasource.getConnection();
        String query = selecttheme;
        if (request.getid() != null)
          query += "id = " + request.getid();
        else if (request.getname() != null)
          query += "name = '" + request.getname() + "'";
        else
          query += "id = 1";

        pstmt = conn.prepareStatement(query);
        rs = pstmt.executeQuery();

        if (rs.next()) {
          theme = new Theme();
          int col = 0;
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
        response.settheme(theme);
      }
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
      try {
        if (conn != null) conn.close();
        conn = null;
      }
      catch (SQLException sqle) {}
    }
    return response;
  }

  public GetThemesResponse GetThemes(GetThemesRequest request) throws
      ServletException {
    GetThemesResponse response = new GetThemesResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("select id, name, color1, color2, " +
                                    "color3, color4, color5, image1, image2, " +
                                    "image3, image4, image5, heading1, heading2, " +
                                    "heading3, heading4, heading5, " +
                                    "titleinfo, brandsfirst, metacontenttype, metakeywords, metadescription, " +
                                    "robots, googlebots, imagebaseurl, mostpopularcount, " +
                                    "searchresultcount, featureditemcount from themes");

      rs = pstmt.executeQuery();
      while (rs.next()) {
        int col = 0;
        Theme theme = new Theme();
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
        response.settheme(theme);
      }
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
      try {
        if (conn != null) conn.close();
        conn = null;
      }
      catch (SQLException sqle) {}
    }
    return response;
  }
}
