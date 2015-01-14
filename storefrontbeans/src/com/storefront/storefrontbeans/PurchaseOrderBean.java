package com.storefront.storefrontbeans;

import java.util.*;
import javax.servlet.*;
import javax.naming.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;
import com.adaptinet.transmitter.*;

public class PurchaseOrderBean
    extends BaseBean {
  final static private String fields[] = {
      "distributorid", "referencenumber", "description", "totalcost", "dropship", "status", "shipping",
      "shippingmethodid", "shippingweight", "handling", "total", "trackingnumber",
      "mailstatus", "creationtime"};
  final static private String tablename = "purchaseorder";

  final static private String bydistributor =
      " distributorid=? ";
  final static private String bydate =
      " DATE_FORMAT(creationtime,'%Y %m %d') between " +
      "DATE_FORMAT(?,'%Y %m %d') and  DATE_FORMAT(?,'%Y %m %d') ";

  public PurchaseOrderBean() throws ServletException {
    super();
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

  public CreatePurchaseOrderResponse CreatePurchaseOrder(
      CreatePurchaseOrderRequest request) throws
      ServletException {

    CreatePurchaseOrderResponse response = new CreatePurchaseOrderResponse();
    Connection conn = null;

    try {
      conn = datasource.getConnection();
      response.setpurchaseorder(createPurchaseOrder(conn, request));
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

  int createPurchaseOrder(Connection conn, CreatePurchaseOrderRequest request) throws
      ServletException {

    int response = 0;
    ListsBean listsBean = new ListsBean();

    try {
      Distributor distributor = listsBean.getDistributor(conn, request.getdistributor());
      ShippingMethod shippingMethod = listsBean.GetShippingMethod(conn, request.getshippingmethod());

      if(distributor==null || shippingMethod==null)
        return response;

      PurchaseOrder purchaseOrder = new PurchaseOrder();
      purchaseOrder.setdistributor(distributor);
      purchaseOrder.setdescription(distributor.getname());
      purchaseOrder.setshippingmethod(shippingMethod);
      purchaseOrder.setstatus("Ordered");
      purchaseOrder.setdropship(request.getdropship());
      if(request.getdropship()) {
        purchaseOrder.sethandling(distributor.getdropshipfee());
      }
      else {
        purchaseOrder.sethandling(distributor.gethandlingfee());
      }

      double totalcost = 0;
      double totalweight = 0;
      Iterator it = request.getitemsIterator();
      while (it.hasNext()) {
        PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem();
        Item item = (Item)it.next();
        purchaseOrderItem.setitemnumber(item.getid());
        purchaseOrderItem.settrxtype("Order");
        purchaseOrderItem.setisin(item.getisin());
        purchaseOrderItem.setproductname(item.getisin());
        purchaseOrderItem.setmanufacturer(item.getmanufacturer());
        purchaseOrderItem.setquantity(item.getquantitytoorder());
        purchaseOrderItem.setourcost(item.getourcost());
        totalcost+=purchaseOrderItem.getourcost()*item.getquantitytoorder();
        purchaseOrderItem.setshippingweight(item.getdetails().getshippingweight());
        totalweight+=purchaseOrderItem.getshippingweight()*item.getquantitytoorder();
        purchaseOrderItem.setstatus("Ordered");
        purchaseOrder.setitems(purchaseOrderItem);
      }
      purchaseOrder.settotalcost(totalcost);
      purchaseOrder.setshipping(0);
      purchaseOrder.setshippingweight(totalweight);
      purchaseOrder.settotal(totalcost+purchaseOrder.gethandling());
      response = addPurchaseOrder(conn, purchaseOrder);

      try {
        if(!request.getdropship()) {
          SendPurchaseOrderEMail(purchaseOrder, conn);
        }
        it = purchaseOrder.getitemsIterator();
        while (it.hasNext()) {
          PurchaseOrderItem purchaseOrderItem = (PurchaseOrderItem)it.next();
          ItemBean.OrderItem(conn, purchaseOrderItem.getitemnumber(), purchaseOrderItem.getquantity());
        }
      }
      catch (ServletException se) {
        response=0;
        deletePurchaseOrder(conn, purchaseOrder.getid());
      }
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

  public UpdateTrackingNumberResponse UpdateTrackingNumber(UpdateTrackingNumberRequest request) throws
      ServletException {

    Connection conn = null;
    UpdateTrackingNumberResponse response = new  UpdateTrackingNumberResponse();
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("update purchaseorder set trackingnumber=? where id=?");
      pstmt.setString(1, request.gettrackingnumber());
      pstmt.setInt(2, request.getpurchaseorder());
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

  void UpdateReferenceNumber(Connection conn, int purchaseordernumber, int referencenumber) throws
      ServletException {

    try {
      pstmt = conn.prepareStatement("update purchaseorder set referencenumber=? where id=?");
      pstmt.setInt(1, referencenumber);
      pstmt.setInt(2, purchaseordernumber);
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

  public AddPurchaseOrderResponse AddPurchaseOrder(AddPurchaseOrderRequest request) throws
      ServletException {

        AddPurchaseOrderResponse response = new AddPurchaseOrderResponse();
    Connection conn = null;

    try {
      PurchaseOrder purchaseorder = request.getpurchaseorder();
      conn = datasource.getConnection();
      response.setid(addPurchaseOrder(conn, purchaseorder));
        try {
          SendPurchaseOrderEMail(purchaseorder, conn);
        }
        catch (ServletException se) {}
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

  int addPurchaseOrder(Connection conn, PurchaseOrder purchaseorder) throws
      ServletException {
    int id = 0;
    PurchaseOrderItemBean purchaseOrderItemBean = new PurchaseOrderItemBean();

    try {
      pstmt = conn.prepareStatement(getInsertString());
      int col=0;
      pstmt.setInt(++col, purchaseorder.getdistributor().getid());
      pstmt.setInt(++col, purchaseorder.getreferencenumber());
      pstmt.setString(++col, purchaseorder.getdescription());
      pstmt.setDouble(++col, purchaseorder.gettotalcost());
      pstmt.setBoolean(++col, purchaseorder.getdropship());
      pstmt.setString(++col, purchaseorder.getstatus());
      pstmt.setDouble(++col, purchaseorder.getshipping());
      pstmt.setInt(++col, purchaseorder.getshippingmethod().getid());
      pstmt.setDouble(++col, purchaseorder.getshippingweight());
      pstmt.setDouble(++col, purchaseorder.gethandling());
      pstmt.setDouble(++col, purchaseorder.gettotal());
      pstmt.setString(++col, purchaseorder.gettrackingnumber());
      pstmt.setString(++col, purchaseorder.getemailstatus());
      purchaseorder.setcreationdate(new java.util.Date());
      pstmt.setTimestamp(++col,
                         new Timestamp(purchaseorder.getcreationdate().getTime()));

      if ( (pstmt.executeUpdate()) > 0) {
        id = getLastInsertID(conn);
        purchaseOrderItemBean.AddPurchaseOrderItem(conn, id,
                                             purchaseorder.getitemsIterator());
        purchaseorder.setid(id);
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
    }
    return id;
  }

  public UpdatePurchaseOrderResponse UpdatePurchaseOrder(UpdatePurchaseOrderRequest request) throws
      ServletException {

    UpdatePurchaseOrderResponse response = new UpdatePurchaseOrderResponse();
    PurchaseOrderItemBean purchaseOrderItemBean = new PurchaseOrderItemBean();

    Connection conn = null;

    try {
      PurchaseOrder purchaseorder = request.getpurchaseorder();
      purchaseOrderItemBean.DeletePurchaseOrderItem(conn, purchaseorder.getid());
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getUpdateString());
      int col=0;
      pstmt.setInt(++col, purchaseorder.getdistributor().getid());
      pstmt.setInt(++col, purchaseorder.getreferencenumber());
      pstmt.setString(++col, purchaseorder.getdescription());
      pstmt.setDouble(++col, purchaseorder.gettotalcost());
      pstmt.setBoolean(++col, purchaseorder.getdropship());
      pstmt.setString(++col, purchaseorder.getstatus());
      pstmt.setDouble(++col, purchaseorder.getshipping());
      pstmt.setInt(++col, purchaseorder.getshippingmethod().getid());
      pstmt.setDouble(++col, purchaseorder.getshippingweight());
      pstmt.setDouble(++col, purchaseorder.gethandling());
      pstmt.setDouble(++col, purchaseorder.gettotal());
      pstmt.setString(++col, purchaseorder.gettrackingnumber());
      pstmt.setString(++col, purchaseorder.getemailstatus());
      purchaseorder.setcreationdate(new java.util.Date());
      pstmt.setTimestamp(++col,
                         new Timestamp(purchaseorder.getcreationdate().getTime()));
      pstmt.setInt(++col, purchaseorder.getid());

      if ( (pstmt.executeUpdate()) > 0) {
        purchaseOrderItemBean.AddPurchaseOrderItem(conn, purchaseorder.getid(),
                                             purchaseorder.getitemsIterator());
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

  public DeletePurchaseOrderResponse DeletePurchaseOrder(DeletePurchaseOrderRequest
      request) throws ServletException {
    DeletePurchaseOrderResponse response = new DeletePurchaseOrderResponse();
    Connection conn = null;
    try {
      PurchaseOrderItemBean purchaseOrderItemBean = new PurchaseOrderItemBean();

      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("delete from purchaseorder where id = ?");
      pstmt.setInt(1, request.getid());
      pstmt.executeUpdate();
      purchaseOrderItemBean.DeletePurchaseOrderItem(conn, request.getid());
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

  void deletePurchaseOrder(Connection conn, int id) throws ServletException {

    try {
      PurchaseOrderItemBean purchaseOrderItemBean = new PurchaseOrderItemBean();

      pstmt = conn.prepareStatement("delete from purchaseorder where id = ?");
      pstmt.setInt(1, id);
      pstmt.executeUpdate();
      purchaseOrderItemBean.DeletePurchaseOrderItem(conn, id);
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

  public GetPurchaseOrderResponse GetPurchaseOrder(GetPurchaseOrderRequest request) throws
      ServletException {
    GetPurchaseOrderResponse response = new GetPurchaseOrderResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      response.setpurchaseorder(GetPurchaseOrder(conn, request.getid()));
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


  public PurchaseOrder GetPurchaseOrder(Connection conn, int id) throws
      ServletException {
    return GetPurchaseOrder(conn, id, false);
  }

  public PurchaseOrder GetPurchaseOrder(Connection conn, int id, boolean bReady) throws
      ServletException {

    PurchaseOrder purchaseorder = null;
    try {
      ListsBean listsbean = new ListsBean();
      PurchaseOrderItemBean purchaseOrderItemBean = new PurchaseOrderItemBean();

      pstmt = conn.prepareStatement(getSelectWithIDByIDString());
      pstmt.setInt(1, id);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        purchaseorder = new PurchaseOrder();
        int col=0;

        purchaseorder.setid(rs.getInt(++col));
        int distributorid = rs.getInt(++col);
        purchaseorder.setreferencenumber(rs.getInt(++col));
        purchaseorder.setdescription(rs.getString(++col));
        purchaseorder.settotalcost(rs.getDouble(++col));
        purchaseorder.setdropship(rs.getBoolean(++col));
        purchaseorder.setstatus(rs.getString(++col));
        purchaseorder.setshipping(rs.getDouble(++col));
        int shippingmethodid = rs.getInt(++col);
        purchaseorder.setshippingweight(rs.getDouble(++col));
        purchaseorder.sethandling(rs.getDouble(++col));
        purchaseorder.settotal(rs.getDouble(++col));
        purchaseorder.settrackingnumber(rs.getString(++col));
        purchaseorder.setemailstatus(rs.getString(++col));
        java.sql.Timestamp createtime = rs.getTimestamp(++col);
        if(createtime!=null)
          purchaseorder.setcreationdate(new java.util.Date(createtime.getTime()));

        purchaseorder.setshippingmethod(listsbean.GetShippingMethod(conn,
            shippingmethodid));
        purchaseorder.setdistributor(listsbean.getDistributor(conn,
            distributorid));
        purchaseOrderItemBean.GetPurchaseOrderItem(conn, purchaseorder);
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
    return purchaseorder;
  }

  public GetPurchaseOrdersResponse GetOpenPurchaseOrders(GetPurchaseOrdersRequest request) throws
      ServletException {
    GetPurchaseOrdersResponse response = new GetPurchaseOrdersResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      String query = getSelectWithIDString();
      String where = null;
      String orderby = null;

      where = " where status != 'Received' ";

      if (request.getstartdate() != null) {
        where = (where==null) ? (" where " + bydate) : where + " and "+ bydate;
        orderby = (orderby==null) ? " order by distributor" : orderby + ", distributor";
      }

      if(request.getdistributorid()>0) {
        where = (where==null) ? (" where " + bydistributor) : where + " and " +bydistributor;
        orderby = (orderby==null) ? " order by creationtime" :  orderby + ", creationtime";
      }

      if(where!=null)
        query += where;

      if(orderby!=null)
        query += orderby;

      pstmt = conn.prepareStatement(query);
      int col = 0;
      if (request.getstartdate() != null) {
        pstmt.setTimestamp(++col, new Timestamp(request.getstartdate().getTime()));
        pstmt.setTimestamp(++col, new Timestamp(request.getenddate().getTime()));
      }
      if(request.getdistributorid()>0) {
        pstmt.setInt(++col,request.getdistributorid());
      }
      response = getPurchaseOrders(conn, pstmt);
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

  public GetPurchaseOrdersResponse GetPurchaseOrders(GetPurchaseOrdersRequest request) throws
      ServletException {
    GetPurchaseOrdersResponse response = new GetPurchaseOrdersResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      String query = getSelectWithIDString();
      String where = null;
      String orderby = null;

      if (request.getstartdate() != null) {
        where = (where==null) ? (" where " + bydate) : where + " and "+ bydate;
        orderby = (orderby==null) ? " order by creationtime" :  orderby + ", creationtime";
      }

      if(request.getdistributorid()>0) {
        where = (where==null) ? (" where " + bydistributor) : where + " and " +bydistributor;
        orderby = (orderby==null) ? " order by creationtime" :  orderby + ", creationtime";
      }

      if(where!=null)
        query += where;

      if(orderby!=null)
        query += orderby;

      pstmt = conn.prepareStatement(query);
      int col = 0;
      if (request.getstartdate() != null) {
        pstmt.setTimestamp(++col, new Timestamp(request.getstartdate().getTime()));
        pstmt.setTimestamp(++col, new Timestamp(request.getenddate().getTime()));
      }
      if(request.getdistributorid()>0) {
        pstmt.setInt(++col,request.getdistributorid());
      }
      response = getPurchaseOrders(conn, pstmt);
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

  GetPurchaseOrdersResponse getPurchaseOrders(Connection conn, PreparedStatement pstmt) throws
      ServletException {
    GetPurchaseOrdersResponse response = new GetPurchaseOrdersResponse();

    try {
      ListsBean listsBean = new ListsBean();
      PurchaseOrderItemBean purchaseOrderItemBean = new PurchaseOrderItemBean();

      rs = pstmt.executeQuery();
      while (rs.next()) {
        PurchaseOrder purchaseorder = new PurchaseOrder();
        int col=0;
        purchaseorder.setid(rs.getInt(++col));
        int distributorid = rs.getInt(++col);
        purchaseorder.setreferencenumber(rs.getInt(++col));
        purchaseorder.setdescription(rs.getString(++col));
        purchaseorder.settotalcost(rs.getDouble(++col));
        purchaseorder.setdropship(rs.getBoolean(++col));
        purchaseorder.setstatus(rs.getString(++col));
        purchaseorder.setshipping(rs.getDouble(++col));
        int shippingmethodid = rs.getInt(++col);
        purchaseorder.setshippingweight(rs.getDouble(++col));
        purchaseorder.sethandling(rs.getDouble(++col));
        purchaseorder.settotal(rs.getDouble(++col));
        purchaseorder.settrackingnumber(rs.getString(++col));
        purchaseorder.setemailstatus(rs.getString(++col));
        java.sql.Timestamp createtime = rs.getTimestamp(++col);
        if(createtime!=null)
          purchaseorder.setcreationdate(new java.util.Date(createtime.getTime()));

        purchaseorder.setdistributor(listsBean.getDistributor(conn, distributorid));
        purchaseorder.setshippingmethod(listsBean.GetShippingMethod(conn,
            shippingmethodid));
        purchaseOrderItemBean.GetPurchaseOrderItem(conn, purchaseorder);
        response.setpurchaseorder(purchaseorder);
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
    return response;
  }

  public ReceivePurchaseOrderResponse ReceivePurchaseOrder(ReceivePurchaseOrderRequest request) throws
      ServletException {

    ReceivePurchaseOrderResponse response = new ReceivePurchaseOrderResponse();
    Connection conn = null;

    try {
      conn = datasource.getConnection();
      receivePurchaseOrder(conn, request.getpurchaseorder(), false);
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

  void receivePurchaseOrder(Connection conn, PurchaseOrder purchaseOrder, boolean all) throws
      ServletException {

    try {
      PurchaseOrderItemBean purchaseOrderItemBean = new PurchaseOrderItemBean();
      Iterator it = purchaseOrder.getitemsIterator();
      boolean partial = false;
      boolean received = false;
      while (it.hasNext()) {
        PurchaseOrderItem purchaseOrderItem = (PurchaseOrderItem)it.next();

        if(all) {
          received=true;
          purchaseOrderItem.setquantityreceived(purchaseOrderItem.getquantity());
          purchaseOrderItem.setreceiving(purchaseOrderItem.getquantity());
          purchaseOrderItem.setstatus("Received");
        }
        else if(purchaseOrderItem.getreceiving()>0) {
          if(purchaseOrderItem.getquantityreceived()+purchaseOrderItem.getreceiving()<=
             purchaseOrderItem.getquantity()) {
            received=true;
            purchaseOrderItem.setquantityreceived(purchaseOrderItem.getquantityreceived()+
                                                  purchaseOrderItem.getreceiving());
            if(purchaseOrderItem.getquantity()==purchaseOrderItem.getquantityreceived()) {
              purchaseOrderItem.setstatus("Received");
            }
            else {
              purchaseOrderItem.setstatus("Partially Received");
              partial=true;
            }
          }
        }
        else {
          if(purchaseOrderItem.getquantity()>purchaseOrderItem.getquantityreceived())
            partial = true;
        }
        purchaseOrderItemBean.updatePurchaseOrderItem(conn,purchaseOrder.getid(), purchaseOrderItem);
        ItemBean.RecieveItem(conn, purchaseOrderItem.getitemnumber(), purchaseOrderItem.getreceiving());
      }
      String status = null;
      if (received) {
        if (partial) {
          status = "Partially Received";
        }
        else {
          status = "Received";
        }

        try {
          pstmt = conn.prepareStatement("update purchaseorder set status = ? where id = ?");
          pstmt.setString(1, status);
          pstmt.setInt(2, purchaseOrder.getid());
          pstmt.executeUpdate();
        }
        catch (SQLException sqle)
        {
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

  public void SendPurchaseOrderEMail(PurchaseOrder purchaseorder) throws
      ServletException {
    Connection conn = null;

    try {
      conn = datasource.getConnection();
      SendPurchaseOrderEMail(purchaseorder, conn);
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
  }

  private void SendPurchaseOrderEMail(PurchaseOrder purchaseorder, Connection conn) throws
      ServletException {

    MailConfiguration mailconfig = new MailConfiguration();

    try {
          Distributor distributor = ListsBean.getDistributor(conn,purchaseorder.getdistributor().getid());
          if (distributor != null && distributor.getemail() != null) {
            pstmt = conn.prepareStatement(
                "select mailhost, mailfromname, mailfromaddress, mailauthuser, mailauthpassword, " +
                "url, mailsubject from mailconfig where id = 'purchaseorder_" +
                purchaseorder.getdistributor().getid() + "'");
            rs = pstmt.executeQuery();
            if (rs.next()) {
              mailconfig.setMailHost(rs.getString(1));
              mailconfig.setMailFromName(rs.getString(2));
              mailconfig.setMailFromAddress(rs.getString(3));
              mailconfig.setMailAuthUser(rs.getString(4));
              mailconfig.setMailAuthPassword(rs.getString(5));
              mailconfig.setMailURL(rs.getString(6));
              mailconfig.setMailSubject(rs.getString(7));

              Context initial = new InitialContext();
              Session session = (Session) initial.lookup(
                  "java:comp/env/storefrontmail");

              Message msg = new MimeMessage(session);
              msg.setFrom(new InternetAddress(mailconfig.getMailFromAddress(),
                                              mailconfig.getMailFromName()));
              InternetAddress dests[] = new InternetAddress[] {
                  new InternetAddress(distributor.getemail())};
              msg.setRecipients(Message.RecipientType.TO, dests);

              msg.setSentDate(new java.util.Date());
              String subject = mailconfig.getMailSubject();

              subject += " Purchase Order # " +
                  Integer.toString(purchaseorder.getid());
              msg.setSubject(subject);

              XMLTransmitter transmitter = new XMLTransmitter();
              transmitter.setUrl(mailconfig.getMailURL() +
                                 Integer.toString(purchaseorder.getid()));
              String body = transmitter.doTransaction("");
              msg.setContent(body, "text/html");

              Transport transport = session.getTransport("smtp");
              transport.connect(mailconfig.getMailHost(),
                                mailconfig.getMailAuthUser(),
                                mailconfig.getMailAuthPassword());

              transport.sendMessage(msg, msg.getAllRecipients());
              transport.close();
              pstmt.close();
              pstmt = conn.prepareStatement("update purchaseorder set mailstatus='purchase order sent' where id=?");
              pstmt.setInt(1, purchaseorder.getid());
              pstmt.executeUpdate();
              EMailRecord emrequest = new EMailRecord();
              emrequest.setreferenceno(purchaseorder.getid());
              emrequest.settype("Send PurchaseOrder");
              emrequest.setrecipientid(distributor.getid());
              emrequest.setrecipient(distributor.getemail());
              emrequest.setsubject(subject);
              emrequest.setbody(body);
              new EMailRecordBean().addEMailRecord(conn, emrequest);
            }
          }
    }
    catch (javax.mail.MessagingException me) {
      try {
        pstmt = conn.prepareStatement(
            "update purchaseorder set mailstatus='purchase order failed' where id=?");
        pstmt.setInt(1, purchaseorder.getid());
        pstmt.executeUpdate();
        throw new ServletException(me.getMessage());
      }
      catch (Exception ex) {
        throw new ServletException(ex.getMessage());
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
  }
}
