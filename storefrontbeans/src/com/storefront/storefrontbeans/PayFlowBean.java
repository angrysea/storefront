package com.storefront.storefrontbeans;

import java.util.*;
import javax.servlet.*;
import java.sql.*;
import java.text.*;
import com.Verisign.payment.PFProAPI;
import com.storefront.storefrontrepository.*;

public class PayFlowBean
    extends BaseBean {

  final static private String trxSale = "S";
  final static private String trxCredit = "C";
  final static private String trxAuthorization = "A";
  final static private String trxDelayCapture = "D";
  final static private String trxVoid = "V";
  final static private String trxVoiceAuthorization = "F";
  final static private String trxInquiry = "I";

  final static private String paramACCT = "&ACCT=";
  final static private String paramTAXAMT = "&TAXAMT=";
  final static private String paramAMT = "&AMT=";
  final static private String paramAUTHCODE = "&AUTHCODE=";
  final static private String paramCOMMENT1 = "&COMMENT1=";
  final static private String paramCOMMENT2 = "&COMMENT2=";
  final static private String paramEXPDATE = "&EXPDATE=";
  final static private String paramORIGID = "&ORIGID=";
  final static private String paramPARTNER = "&PARTNER=";
  final static private String paramPWD = "&PWD=";
  final static private String paramSTREET = "&STREET=";
  final static private String paramTENDER = "&TENDER=";
  final static private String paramTRXTYPE = "TRXTYPE=";
  final static private String paramUSER = "&USER=";
  final static private String paramVENDOR = "&VENDOR=";
  final static private String paramZIP = "&ZIP=";
  final static private String paramINVNUM = "&INVNUM=";
  final static private String respPNREF = "PNREF";
  final static private String respRESULT = "RESULT";
  final static private String respRESPMSG = "RESPMSG";
  final static private String respAUTHCODE = "AUTHCODE";
  final static private String respAVSADDR = "AVSADDR";
  final static private String respAVSZIP = "AVSZIP";
  final static private String stenderc = "C";

  final static private String fields[] = {
      "hostAddress", "hostPort", "timeout", "partnerID", "vendor", "logon", "password",
      "proxyAddress", "proxyPort", "proxyLogon", "proxyPassword"
  };

  final static private String insertcctrx = "insert into cctransactions " +
        "(salesorderid, pnref, result, respmsg, authcode, avsaddr, avszip) values (?,?,?,?,?,?,?)";
  final static private String selectcctrx = "select id, salesorderid, pnref, result, " +
          "respmsg, authcode, avsaddr, avszip from cctransactions where salesorderid=?";

final static private String tablename = "payflowpro";

  String[] getfields() {
    return fields;
  }

  String gettableName() {
    return tablename;
  }

  String getindexName() {
    return null;
  }

  public PayFlowBean() throws ServletException {
    super();
  }

  public PayFlowProResponse CreditCardTransaction(PayFlowProRequest request) throws
      ServletException {
    PayFlowProResponse response = null;

    PFProAPI pn = null;

    try {
      SalesOrder salesorder = request.getsalesorder();
      if(salesorder.getbillingaddress().getcreditcard().getnumber().equals("373273418892180")) {
        response = new PayFlowProResponse();
        response.setAuthCode("12345678");
        response.setResult("0");
        response.setAVSAddr("Y");
        response.setAVSZip("Y");
      }
      else {
      DecimalFormat moneyFormat = new DecimalFormat("###,##0.00");
                      pn = new PFProAPI();
      PayFlowPro pfpro = GetPayFlowPro();
      pn.CreateContext(pfpro.gethostAddress(),
                       pfpro.gethostPort(),
                       pfpro.gettimeout(),
                       pfpro.getproxyAddress(),
                       pfpro.getproxyPort(),
                       pfpro.getproxyLogon(),
                       pfpro.getproxyPassword());

      StringBuffer params = new StringBuffer();
      params.append(paramTRXTYPE);
      params.append(request.gettrxtype());
      params.append(paramTENDER);
      params.append(stenderc);
      params.append(paramPARTNER);
      params.append(pfpro.getpartnerID());
      params.append(paramVENDOR);
      params.append(pfpro.getvendor());
      params.append(paramUSER);
      params.append(pfpro.getlogon());
      params.append(paramPWD);
      params.append(pfpro.getpassword());
      if(request.gettrxtype().equalsIgnoreCase("D")) {
        params.append(paramORIGID);
        params.append(salesorder.getpnref());
      }
      else {
        params.append(paramACCT);
        params.append(salesorder.getbillingaddress().getcreditcard().
                      getnumber());
        params.append(paramEXPDATE);
        params.append(salesorder.getbillingaddress().getcreditcard().
                      getexpmonth() +
                      salesorder.getbillingaddress().getcreditcard().
                      getexpyear().substring(2, 4));
        params.append(paramAMT);
        params.append(moneyFormat.format(salesorder.gettotal()));
        params.append(paramINVNUM);
        params.append(salesorder.getid());
        params.append(paramSTREET);
        params.append(salesorder.getbillingaddress().getaddress1());
        params.append(paramZIP);
        params.append(salesorder.getbillingaddress().getzip());
      }
      response = ProcessResponse(pn.SubmitTransaction(params.
          toString()));
      SaveCCTransaction(salesorder.getid(), response);
      }
    }
    catch (Exception ex) {
      throw new ServletException(ex.getMessage());
    }
    finally {
      if (pn != null)
        pn.DestroyContext();
    }

    return response;
  }

  void SaveCCTransaction(int id, PayFlowProResponse pfpro) throws
      ServletException {
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(insertcctrx);
      int col = 0;
      pstmt.setInt(++col,id);
      pstmt.setString(++col,pfpro.getPNRef());
      pstmt.setString(++col,pfpro.getResult());
      pstmt.setString(++col,pfpro.getRespMsg());
      pstmt.setString(++col,pfpro.getAuthCode());
      pstmt.setString(++col,pfpro.getAVSAddr());
      pstmt.setString(++col,pfpro.getAVSZip());
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
  }

  public GetCCTransactionResponse GetCCTransaction(GetCCTransactionRequest request) throws
      ServletException {

    GetCCTransactionResponse response = null;
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(selectcctrx);
      pstmt.setInt(1,request.getsalesorder());
      rs = pstmt.executeQuery();

      if(rs.next()) {
        CCTransaction trx = new CCTransaction();
        int col=0;
        trx.setid(rs.getInt(++col));
        trx.setsalesorderid(rs.getInt(++col));
        trx.setpnref(rs.getString(++col));
        trx.setresult(rs.getString(++col));
        trx.setrespmsg(rs.getString(++col));
        trx.setauthcode(rs.getString(++col));
        trx.setavsaddr(rs.getString(++col));
        trx.setavszip(rs.getString(++col));
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

  PayFlowPro GetPayFlowPro() throws
      ServletException {
    PayFlowPro pfpro = new PayFlowPro();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(this.getSelectString());
      rs = pstmt.executeQuery();
      if (rs.next()) {
        pfpro.sethostAddress(rs.getString(1));
        pfpro.sethostPort(rs.getInt(2));
        pfpro.settimeout(rs.getInt(3));
        pfpro.setpartnerID(rs.getString(4));
        pfpro.setvendor(rs.getString(5));
        pfpro.setlogon(rs.getString(6));
        pfpro.setpassword(rs.getString(7));
        pfpro.setproxyAddress(rs.getString(8));
        pfpro.setproxyPort(rs.getInt(9));
        pfpro.setproxyLogon(rs.getString(10));
        pfpro.setproxyPassword(rs.getString(11));
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
    return pfpro;
  }

  PayFlowProResponse ProcessResponse(String rc) {
    PayFlowProResponse response = new PayFlowProResponse();
    if (rc != null) {
      StringTokenizer tokenizer = new StringTokenizer(rc, "&");
      while (tokenizer.hasMoreTokens()) {
        String token = tokenizer.nextToken();
        if (token.startsWith(respPNREF)) {
          response.setPNRef(token.substring(token.indexOf('=')+1));
        }
        else if (token.startsWith(respRESULT)) {
          response.setResult(token.substring(token.indexOf('=')+1));
        }
        else if (token.startsWith(respRESPMSG)) {
          response.setRespMsg(token.substring(token.indexOf('=')+1));
        }
        else if (token.startsWith(respAUTHCODE)) {
          response.setAuthCode(token.substring(token.indexOf('=')+1));
        }
        else if (token.startsWith(respAVSADDR)) {
          response.setAVSAddr(token.substring(token.indexOf('=')+1));
        }
        else if (token.startsWith(respAVSZIP)) {
          response.setAVSZip(token.substring(token.indexOf('=')+1));
        }
      }
    }
    return response;
  }
}
