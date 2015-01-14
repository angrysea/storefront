package com.storefront.storefrontbeans;

import java.util.*;
import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class AffiliateBean
    extends BaseBean {
  final static private String fields[] = {
      "first", "last", "payeename", "taxid", "company", "description", "address1",
      "address2", "address3", "city", "state", "zip", "country", "phone",
      "fax", "email", "websitename", "affiliateurl",
      "creationdate", "pw", "planid", "itemssold", "totalsales", "totalcommission"};
  final static private String tablename = "affiliate";

  public AffiliateBean() throws ServletException {
    super();
  }

  String getdburl() {
    return "jdbc:mysql:///storefront";
  }

  String[] getfields() {
    return fields;
  }

  String gettableName() {
    return tablename;
  }

  String getindexName() {
    return "id";
  }

  public AddAffiliateResponse AddAffiliate(AddAffiliateRequest request) throws ServletException {

    AddAffiliateResponse response = new AddAffiliateResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      Affiliate affiliate = (Affiliate)request.getaffiliate();
      if(checkAffiliate(conn, affiliate.getemail())>0)
        return response;
      pstmt = conn.prepareStatement(getInsertString());
      int col=0;
      pstmt.setString(++col, affiliate.getfirst());
      pstmt.setString(++col, affiliate.getlast());
      pstmt.setString(++col, affiliate.getpayeename());
      pstmt.setString(++col, affiliate.gettaxid());
      pstmt.setString(++col, affiliate.getcompany());
      pstmt.setString(++col, affiliate.getdescription());
      pstmt.setString(++col, affiliate.getaddress1());
      pstmt.setString(++col, affiliate.getaddress2());
      pstmt.setString(++col, affiliate.getaddress3());
      pstmt.setString(++col, affiliate.getcity());
      pstmt.setString(++col, affiliate.getstate());
      pstmt.setString(++col, affiliate.getzip());
      pstmt.setString(++col, affiliate.getcountry());
      pstmt.setString(++col, affiliate.getphone());
      pstmt.setString(++col, affiliate.getfax());
      pstmt.setString(++col, affiliate.getemail());
      pstmt.setString(++col, affiliate.getwebsitename());
      pstmt.setString(++col, affiliate.getaffiliateurl());
      pstmt.setTimestamp(++col,
                         new Timestamp(new java.util.Date().getTime()));
      pstmt.setString(++col, affiliate.getpw());
      pstmt.setInt(++col, affiliate.getplanid());
      pstmt.setInt(++col, affiliate.getitemssold());
      pstmt.setDouble(++col, affiliate.gettotalsales());
      pstmt.setDouble(++col, affiliate.gettotalcommission());
      response.setid(pstmt.executeUpdate());
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

  int checkAffiliate(Connection conn, String email) throws
      ServletException {

    int id = 0;
    try {
      pstmt = conn.prepareStatement("select id from affiliate where email=?");
      pstmt.setString(1, email);
      rs = pstmt.executeQuery();

      if(rs.next()) {
        id = rs.getInt(1);
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
    return id;
  }

  public UpdateAffiliateResponse UpdateAffiliate(UpdateAffiliateRequest request) throws ServletException {

    UpdateAffiliateResponse response = new UpdateAffiliateResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getUpdateString());
      Affiliate affiliate = (Affiliate)request.getaffiliate();
      int col = 0;
      pstmt.setString(++col, affiliate.getfirst());
      pstmt.setString(++col, affiliate.getlast());
      pstmt.setString(++col, affiliate.getpayeename());
      pstmt.setString(++col, affiliate.gettaxid());
      pstmt.setString(++col, affiliate.getcompany());
      pstmt.setString(++col, affiliate.getdescription());
      pstmt.setString(++col, affiliate.getaddress1());
      pstmt.setString(++col, affiliate.getaddress2());
      pstmt.setString(++col, affiliate.getaddress3());
      pstmt.setString(++col, affiliate.getcity());
      pstmt.setString(++col, affiliate.getstate());
      pstmt.setString(++col, affiliate.getzip());
      pstmt.setString(++col, affiliate.getcountry());
      pstmt.setString(++col, affiliate.getphone());
      pstmt.setString(++col, affiliate.getfax());
      pstmt.setString(++col, affiliate.getemail());
      pstmt.setString(++col, affiliate.getwebsitename());
      pstmt.setString(++col, affiliate.getaffiliateurl());
      pstmt.setTimestamp(++col,
                         new Timestamp(affiliate.getcreationdate().getTime()));
      pstmt.setString(++col, affiliate.getpw());
      pstmt.setInt(++col, affiliate.getplanid());
      pstmt.setInt(++col, affiliate.getitemssold());
      pstmt.setDouble(++col, affiliate.gettotalsales());
      pstmt.setDouble(++col, affiliate.gettotalcommission());
      pstmt.setInt(++col, affiliate.getid());
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

  void updateAffiliateTotals(Connection conn, Affiliate affiliate) throws ServletException {

    try {
      pstmt = conn.prepareStatement("update affiliate set itemssold=?, totalsales=?, totalcommission=? where id=?");
      int col = 0;
      pstmt.setInt(++col, affiliate.getitemssold());
      pstmt.setDouble(++col, affiliate.gettotalsales());
      pstmt.setDouble(++col, affiliate.gettotalcommission());
      pstmt.setInt(++col, affiliate.getid());
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
    }
  }

  public DeleteAffiliateResponse DeleteAffiliate(DeleteAffiliateRequest request) throws ServletException {

    DeleteAffiliateResponse response = new DeleteAffiliateResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("delete from affiliate where id = ?");
      pstmt.setInt(1, request.getid());
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

  public GetAffiliateResponse GetAffiliate(GetAffiliateRequest request) throws
      ServletException {

    Connection conn = null;
    GetAffiliateResponse response = new GetAffiliateResponse();
    try {
      conn = datasource.getConnection();
      response.setaffiliate(getAffiliate(conn, request.getid()));
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

  Affiliate getAffiliate(Connection conn, int id) throws
      ServletException {

    Affiliate affiliate = null;
    try {
      pstmt = conn.prepareStatement(getSelectWithIDByIDString());
      pstmt.setInt(1, id);
      rs = pstmt.executeQuery();

      if(rs.next()) {
        affiliate = new Affiliate();
        int col=0;
        affiliate.setid(rs.getInt(++col));
        affiliate.setfirst(rs.getString(++col));
        affiliate.setlast(rs.getString(++col));
        affiliate.setpayeename(rs.getString(++col));
        affiliate.settaxid(rs.getString(++col));
        affiliate.setcompany(rs.getString(++col));
        affiliate.setdescription(rs.getString(++col));
        affiliate.setaddress1(rs.getString(++col));
        affiliate.setaddress2(rs.getString(++col));
        affiliate.setaddress3(rs.getString(++col));
        affiliate.setcity(rs.getString(++col));
        affiliate.setstate(rs.getString(++col));
        affiliate.setzip(rs.getString(++col));
        affiliate.setcountry(rs.getString(++col));
        affiliate.setphone(rs.getString(++col));
        affiliate.setfax(rs.getString(++col));
        affiliate.setemail(rs.getString(++col));
        affiliate.setwebsitename(rs.getString(++col));
        affiliate.setaffiliateurl(rs.getString(++col));
        affiliate.setcreationdate(new java.util.Date(rs.getTimestamp(++col).getTime()));
        affiliate.setpw(rs.getString(++col));
        affiliate.setplanid(rs.getInt(++col));
        affiliate.setitemssold(rs.getInt(++col));
        affiliate.settotalsales(rs.getDouble(++col));
        affiliate.settotalcommission(rs.getDouble(++col));
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
    return affiliate;
  }

  public GetAffiliatesResponse GetAffiliates(GetAffiliatesRequest request) throws
      ServletException {

    Connection conn = null;
    GetAffiliatesResponse response = new GetAffiliatesResponse();
    try {
      conn = datasource.getConnection();
      String query = getSelectWithIDString();
      if(request.getorderby()!=null) {
        query += request.getorderby();
        if(request.getdirection()!=null)
          query += " " + request.getdirection();
      }

      pstmt = conn.prepareStatement(query);
      rs = pstmt.executeQuery();

      while(rs.next()) {
        Affiliate affiliate = new Affiliate();
        int col=0;
        affiliate.setid(rs.getInt(++col));
        affiliate.setfirst(rs.getString(++col));
        affiliate.setlast(rs.getString(++col));
        affiliate.setpayeename(rs.getString(++col));
        affiliate.settaxid(rs.getString(++col));
        affiliate.setcompany(rs.getString(++col));
        affiliate.setdescription(rs.getString(++col));
        affiliate.setaddress1(rs.getString(++col));
        affiliate.setaddress2(rs.getString(++col));
        affiliate.setaddress3(rs.getString(++col));
        affiliate.setcity(rs.getString(++col));
        affiliate.setstate(rs.getString(++col));
        affiliate.setzip(rs.getString(++col));
        affiliate.setcountry(rs.getString(++col));
        affiliate.setphone(rs.getString(++col));
        affiliate.setfax(rs.getString(++col));
        affiliate.setemail(rs.getString(++col));
        affiliate.setwebsitename(rs.getString(++col));
        affiliate.setaffiliateurl(rs.getString(++col));
        affiliate.setcreationdate(new java.util.Date(rs.getTimestamp(++col).getTime()));
        affiliate.setpw(rs.getString(++col));
        affiliate.setplanid(rs.getInt(++col));
        affiliate.setitemssold(rs.getInt(++col));
        affiliate.settotalsales(rs.getDouble(++col));
        affiliate.settotalcommission(rs.getDouble(++col));
        new AffiliateCommissionBean().GetAffiliateCommission(conn, affiliate);
        response.setaffiliate(affiliate);
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

  public LoginAffiliateResponse LoginAffiliate(LoginAffiliateRequest request) throws
      ServletException {

    Connection conn = null;
    LoginAffiliateResponse response = new LoginAffiliateResponse();
    try {
      conn = datasource.getConnection();
      Affiliate affiliate = null;
      String query = getSelectWithIDString() + " where email=? and pw=?";
      pstmt = conn.prepareStatement(query);
      pstmt.setString(1, request.getemail());
      pstmt.setString(2, request.getpw());
      rs = pstmt.executeQuery();

      if (rs.next()) {
        affiliate = new Affiliate();
        int col = 0;
        affiliate.setid(rs.getInt(++col));
        affiliate.setfirst(rs.getString(++col));
        affiliate.setlast(rs.getString(++col));
        affiliate.setpayeename(rs.getString(++col));
        affiliate.settaxid(rs.getString(++col));
        affiliate.setcompany(rs.getString(++col));
        affiliate.setdescription(rs.getString(++col));
        affiliate.setaddress1(rs.getString(++col));
        affiliate.setaddress2(rs.getString(++col));
        affiliate.setaddress3(rs.getString(++col));
        affiliate.setcity(rs.getString(++col));
        affiliate.setstate(rs.getString(++col));
        affiliate.setzip(rs.getString(++col));
        affiliate.setcountry(rs.getString(++col));
        affiliate.setphone(rs.getString(++col));
        affiliate.setfax(rs.getString(++col));
        affiliate.setemail(rs.getString(++col));
        affiliate.setwebsitename(rs.getString(++col));
        affiliate.setaffiliateurl(rs.getString(++col));
        affiliate.setcreationdate(new java.util.Date(rs.getTimestamp(++col).
            getTime()));
        affiliate.setpw(rs.getString(++col));
        affiliate.setplanid(rs.getInt(++col));
        affiliate.setitemssold(rs.getInt(++col));
        affiliate.settotalsales(rs.getDouble(++col));
        affiliate.settotalcommission(rs.getDouble(++col));
        new AffiliateCommissionBean().GetAffiliateCommission(conn, affiliate);
      }
      response.setaffiliate(affiliate);
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

  void AddCommissions(Connection conn, int affiliateid, Invoice invoice) throws ServletException {

    try {
      Affiliate affiliate = getAffiliate(conn, affiliateid);
      AffiliateProgram program = new AffiliateProgramBean().getAffiliateProgram(conn, affiliate.getplanid());

      int totalunits = 0;
      double total = 0;
      double percent = 0;
      double commission = 0;

      if(program.gettype()==0) {
        total = affiliate.gettotalsales();
      }
      else if(program.gettype()==1) {
        total = affiliate.getitemssold();
      }
      else if(program.gettype()==2) {
        total = affiliate.gettotalcommission();
      }

      Iterator it = program.getcommissionLevelsIterator();
      while(it.hasNext()) {
         AffiliateCommissionLevel commissionlevel = (AffiliateCommissionLevel)it.next();
         if(commissionlevel.getstart()>=total && commissionlevel.getend()<=total) {
           percent = commissionlevel.getpercent();
         }
      }

      AffiliateCommissionBean affiliateCommissionBean = new AffiliateCommissionBean();
      it = invoice.getitemsIterator();
      while(it.hasNext()) {
        InvoiceItem item = (InvoiceItem)it.next();
        totalunits += item.getquantity();
        total += item.gettotalPrice();
        commission += invoice.gettotalcost()*percent;
        AffiliateCommission affiliatecommission = new AffiliateCommission();
        affiliatecommission.setaffiliateid(affiliateid);
        affiliatecommission.setpercent(percent);
        affiliatecommission.setsalesorderitem(item.getsalesorderitemid());
        affiliatecommission.setcreationdate(new java.util.Date());
        affiliatecommission.setcommission(invoice.gettotalcost()*percent);
        affiliateCommissionBean.AddAffiliateCommission(conn, affiliatecommission);
      }
      affiliate.settotalsales(affiliate.gettotalsales()+total);
      affiliate.setitemssold(affiliate.getitemssold()+totalunits);
      affiliate.settotalcommission(affiliate.gettotalcommission()+commission);
      updateAffiliateTotals(conn, affiliate);
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
    }
  }
}
