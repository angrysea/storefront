package com.storefront.storefrontbeans;

import java.util.*;
import javax.servlet.*;
import javax.naming.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;
import com.adaptinet.transmitter.*;

public class SalesOrderBean
    extends BaseBean {
  final static private String fields[] = {
      "customerid", "billingaddress", "shippingaddress", "referencenumber",
      "description", "couponcode", "totalcost", "discount", "discountdesc",
      "taxes", "taxesdesc", "status", "optimizeshipping", "dropshipped",
      "shipping", "shippingmethodid", "shippingweight", "handling", "total",
      "pnref", "authorizationcode", "mailstatus", "salescoupon", "creationtime"};
  final static private String tablename = "salesorder";

  final static private String bycustomer =
      " where customerid=? order by creationtime";
  final static private String bynotshipped =
      " where status != 'Shipped' order by creationtime";
  final static private String orderbycreation = "order by creationtime";
  final static private String bydate =
      " where DATE_FORMAT(creationtime,'%Y %m %d') between " +
      "DATE_FORMAT(?,'%Y %m %d') and  DATE_FORMAT(?,'%Y %m %d') order by creationtime";

  final static private String clearallocated =
      "update item set quantitytoship=0";
  final static private String selectbystatus =
      "select distinct a.id, customerid, billingaddress, shippingaddress, referencenumber, " +
      "description, couponcode, totalcost, discount, discountdesc, taxes, " +
      "taxesdesc, a.status, a.optimizeshipping, a.dropshipped, shipping, shippingmethodid, " +
      "shippingweight, handling, total, pnref, authorizationcode, mailstatus, salescoupon, " +
      "creationtime from salesorder a, salesorderitem b where a.id = b.salesorderid and " +
      "b.status != 'shipped' order by creationtime";

  protected String ENV_DESCRIPTION_IND = "S";

  public SalesOrderBean() throws ServletException {
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

  public SalesOrder GenerateSalesOrder(int id) throws ServletException {
    SalesOrder salesorder = null;
    Connection conn = null;
    UserBean userBean = new UserBean();
    CustomerBean customerbean = new CustomerBean();

    try {
      conn = datasource.getConnection();
      salesorder = new SalesOrder();
      salesorder.setcustomer(customerbean.GetCustomer(conn, id));
      int found = 0;
      Iterator it = salesorder.getcustomer().getaddressIterator();
      while (it.hasNext() && found < 2) {
        com.storefront.storefrontrepository.Address address = (com.storefront.
            storefrontrepository.Address) it.next();
        if (address.gettype().equalsIgnoreCase("currentbilling")) {
          salesorder.setbillingaddress(address);
          found++;
        }
        else if (address.gettype().equalsIgnoreCase("currentshipping")) {
          salesorder.setshippingaddress(address);
          found++;
        }
      }
      ShoppingCart shoppingcart = userBean.GetShoppingCart(conn, id);
      it = shoppingcart.getcartitemIterator();
      double totalcost = 0;
      double totalweight = 0;
      while (it.hasNext()) {
        ShoppingCartItem cartitem = (ShoppingCartItem) it.next();
        SalesOrderItem item = new SalesOrderItem();
        item.setitemnumber(cartitem.getitem().getid());
        item.setisin(cartitem.getitem().getisin());
        item.setproductname(cartitem.getitem().getproductname());
        item.setmanufacturerid(cartitem.getitem().getmanufacturerid());
        item.setmanufacturer(cartitem.getitem().getmanufacturer());
        item.setdistributor(Integer.toString(cartitem.getitem().
                                             getdistributorid()));
        item.setquantity(cartitem.getquantity());
        item.setunitprice(cartitem.getitem().getourprice());
        item.setshippingweight(cartitem.getitem().getdetails().
                               getshippingweight());
        item.sethandlingcharges(cartitem.getitem().getdetails().
                                gethandlingcharges());
        item.setstatus("New");
        item.setitemstatus(cartitem.getitem().getstatus());
        item.setzipcode(cartitem.getzipcode());
        item.setcountry(cartitem.getcountry());
        item.setcartid(cartitem.getid());
        totalcost += item.getunitprice() * item.getquantity();
        totalweight += cartitem.getitem().getdetails().getshippingweight() *
            item.getquantity();
        salesorder.setitems(item);
      }
      salesorder.setstatus("New");
      salesorder.setshippingweight(totalweight);
      salesorder.settotalcost(totalcost);
      calculateCouponSavings(conn, salesorder);
      calculateTax(conn, salesorder);
      salesorder.settotal( (salesorder.gettotalcost() - salesorder.getdiscount()) +
                          salesorder.gettaxes() +
                          salesorder.getshipping() + salesorder.gethandling());
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
    return salesorder;
  }

  public void CalculateShippingCost(SalesOrder salesorder) throws
      ServletException {
    salesorder.setshipping(0);
    salesorder.setshippingweight(0);
    ShippingBean shippingBean = new ShippingBean();

    shippingBean.GetShippingCost(salesorder);
  }

  private void calculateTotals(SalesOrder salesorder) throws ServletException {
    calculateTotals(salesorder, false);
  }

  private void calculateTotals(SalesOrder salesorder, boolean bReady) throws
      ServletException {

    try {
      double totalcost = 0;
      double totalweight = 0;
      Iterator it = salesorder.getitemsIterator();
      while (it.hasNext()) {
        SalesOrderItem item = (SalesOrderItem) it.next();
        if (bReady == false) {
          totalcost += item.getquantity() * item.getunitprice();
          totalweight += item.getquantity() * item.getshippingweight();
        }
        else {
          totalcost += item.getquantitytoship() * item.getunitprice();
          totalweight += item.getquantitytoship() * item.getshippingweight();
        }
      }

      salesorder.settotalcost(totalcost);
      salesorder.setshippingweight(totalweight);
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

  public void CalculateCouponSavings(SalesOrder salesorder) throws
      ServletException {
    Connection conn = null;
    try {

      conn = datasource.getConnection();
      calculateCouponSavings(conn, salesorder);
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

  void calculateCouponSavings(Connection conn, SalesOrder salesorder) throws
      ServletException {

    calculateCouponSavings(conn, salesorder, false);
    calculateTax(conn, salesorder, false);
  }

  void calculateCouponSavings(Connection conn, SalesOrder salesorder,
                              boolean bReady) throws
      ServletException {

    try {
      double totalpercent = 0;
      double totalamount = 0;
      boolean freeshipping = false;
      boolean precludes = false;
      String newcode = "";
      String description = "";

      if (salesorder.getcouponcode() != null) {
        StringTokenizer tokenizer = new StringTokenizer(salesorder.
            getcouponcode());
        HashSet coupons = new HashSet();
        while (tokenizer.hasMoreTokens() && !precludes) {
          String token = tokenizer.nextToken();
          if (coupons.contains(token))
            continue;
          coupons.add(token);
          Coupon coupon = CouponBean.GetCoupon(conn, token);
          if (coupon != null) {
            if (!CouponBean.IsCouponRedeemed(conn,
                                             salesorder.getcustomer().getid(),
                                             salesorder.getid(), coupon)) {
              long currentTime = new java.util.Date().getTime();
              long experationtime = coupon.getexpirationDate().getTime();
              if (currentTime < experationtime) {
                if (coupon.getprecludes()) {
                  precludes = true;
                  description = "";
                  newcode = "";
                  totalamount = 0;
                }

                if (coupon.getitemid() > 0 || coupon.getmanufacturerid() > 0) {
                  boolean bUsed=false;
                  Iterator it = salesorder.getitemsIterator();
                  while (it.hasNext()) {
                    SalesOrderItem item = (SalesOrderItem) it.next();
                    if (coupon.getitemid() == item.getitemnumber() ||
                        item.getmanufacturerid() == coupon.getmanufacturerid()) {

                      int qty = 0;
                      if (bReady == false) {
                        if (coupon.getquantityLimit()>0 &&
                            item.getquantity() > coupon.getquantityLimit()) {
                            qty = coupon.getquantityLimit();
                        }
                        else {
                            qty = item.getquantity();
                          }
                      }
                      else {
                        if (coupon.getquantityLimit() > 0 &&
                            (item.getquantitytoship() + item.getshipped()) >
                            coupon.getquantityLimit()) {
                          qty = coupon.getquantityLimit();
                        }
                        else {
                          qty = item.getquantitytoship();
                        }
                      }

                      if (coupon.getquantityrequired() > 0) {
                        if (coupon.getquantityrequired() > qty) {
                          continue;
                        }
                      }

                      if (coupon.getdiscounttype() == 1) {
                        totalamount += coupon.getdiscount() * qty;
                        if(!bUsed) {
                          if (description.length() > 0)
                            description += ", ";
                          description += coupon.getdescription();
                          newcode += coupon.getcode() + " ";
                        }
                        bUsed=true;
                      }
                      else if (coupon.getdiscounttype() == 2) {
                        totalamount += (item.getunitprice() * qty) *
                            coupon.getdiscount();
                        if (!bUsed) {
                          if (description.length() > 0)
                            description += ", ";
                          description += coupon.getdescription();
                          newcode += coupon.getcode() + " ";
                        }
                        bUsed=true;
                      }
                    }
                  }
                }
                else {
                  if (coupon.getquantityrequired() > 0) {
                    Iterator it = salesorder.getitemsIterator();
                    int qty = 0;
                    while (it.hasNext()) {
                      SalesOrderItem item = (SalesOrderItem) it.next();
                      qty += item.getquantity();
                    }
                    if (coupon.getquantityrequired() > qty) {
                      continue;
                    }
                  }
                  if (coupon.getdiscounttype() == 1) {
                    //Fixed amount off of the total.
                    if (coupon.getpriceminimum() < 1 ||
                        coupon.getpriceminimum() > salesorder.gettotalcost()) {
                      totalamount += coupon.getdiscount();
                      if (description.length() > 0)
                        description += ", ";
                      description += coupon.getdescription();
                      newcode += coupon.getcode() + " ";
                    }
                  }
                  else if (coupon.getdiscounttype() == 2) {
                    //Percent off of the total.
                    if (coupon.getpriceminimum() < 1 ||
                        coupon.getpriceminimum() > salesorder.gettotalcost()) {
                      totalpercent += coupon.getdiscount();
                      if (description.length() > 0)
                        description += ", ";
                      description += coupon.getdescription();
                      newcode += coupon.getcode() + " ";
                    }
                  }
                  else if (coupon.getdiscounttype() == 3) {
                    freeshipping = true;
                    if (description.length() > 0)
                      description += ", ";
                    description += coupon.getdescription();
                    newcode += coupon.getcode() + " ";
                  }
                }
              }
            }
          }
        }
      }

      double discountamt = 0;
      discountamt = totalamount;
      discountamt += (salesorder.gettotalcost() - discountamt) *
          totalpercent;
      if (freeshipping) {
        discountamt += salesorder.getshipping() + salesorder.gethandling();
      }

      salesorder.setdiscountdescription(description);
      salesorder.setcouponcode(newcode);
      salesorder.setdiscount(discountamt);
      salesorder.settotal(salesorder.gettotal() - discountamt);
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

  public void CalculateTax(SalesOrder salesorder) throws
      ServletException {
    Connection conn = null;
    try {

      conn = datasource.getConnection();
      calculateTax(conn, salesorder);
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

  private void calculateTax(Connection conn, SalesOrder salesorder) throws
      ServletException {
    calculateTax(conn, salesorder, false);
  }

  private void calculateTax(Connection conn, SalesOrder salesorder,
                            boolean bReady) throws ServletException {

    Company company = null;
    SalesTax statetax = null;
    SalesTax citytax = null;
    CompanyBean companyBean = new CompanyBean();

    try {
      salesorder.settaxesdescription(null);
      salesorder.settaxes(0);
      company = companyBean.GetCompany(conn, 1);
      citytax = SalesTaxBean.FindSalesTax(conn, company.getstate(),
                                          salesorder.getshippingaddress().
                                          getstate(), "city");
      statetax = SalesTaxBean.FindSalesTax(conn, company.getstate(),
                                           salesorder.getshippingaddress().
                                           getstate(), "state");
      if (citytax != null) {
        salesorder.settaxes(salesorder.gettaxes() +
                            ( (salesorder.gettotalcost() -
                               salesorder.getdiscount())
                             * citytax.gettaxrate()));
        String description = salesorder.gettaxesdescription();
        if (description != null) {
          description += " & " + citytax.getdescription();
        }
        else {
          description = citytax.getdescription();
        }
        salesorder.settaxesdescription(description);
      }
      if (statetax != null) {
        salesorder.settaxes(salesorder.gettaxes() +
                            ( (salesorder.gettotalcost() -
                               salesorder.getdiscount())
                             * statetax.gettaxrate()));
        String description = salesorder.gettaxesdescription();
        if (description != null) {
          description += " & " + statetax.getdescription();
        }
        else {
          description = statetax.getdescription();
        }
        salesorder.settaxesdescription(description);
      }
      if (salesorder.gettaxesdescription() == null)
        salesorder.settaxesdescription("Tax");
      salesorder.settotal( (salesorder.gettotalcost() - salesorder.getdiscount()) +
                          salesorder.gettaxes() +
                          salesorder.getshipping() + salesorder.gethandling());
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

  public DropShipSalesOrderResponse DropShipSalesOrder(
      DropShipSalesOrderRequest request) throws
      ServletException {

    DropShipSalesOrderResponse response = new DropShipSalesOrderResponse();
    Connection conn = null;

    try {
      conn = datasource.getConnection();
      ItemBean itemBean = new ItemBean();
      CreatePurchaseOrderRequest porequest = new CreatePurchaseOrderRequest();
      SalesOrder salesorder = getSalesOrder(conn, request.getsalesorderid());
      /*
             PurchaseOrderBean purchaseOrderBean = new PurchaseOrderBean();
             porequest.setdistributor(request.getdistributorid());
       porequest.setshippingmethod(salesorder.getshippingmethod().getid());
             porequest.setdropship(true);
       */
      Iterator it = salesorder.getitemsIterator();

      while (it.hasNext()) {
        SalesOrderItem soitem = (SalesOrderItem) it.next();
        Item item = itemBean.GetItem(conn, soitem.getitemnumber());
        item.setquantitytoorder(soitem.getquantity());
        porequest.setitems(item);
      }
      sendDropShipMail(salesorder.getid(), request.getdistributorid(), conn);
      /*
             response.setid(purchaseOrderBean.CreatePurchaseOrder(porequest).getpurchaseorder());
             purchaseOrderBean.UpdateReferenceNumber(conn, response.getid(), salesorder.getid());
       */
      updateDropShippedStatus(conn, salesorder.getid(), response.getid(),
                              request.getdistributorid());
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

  public AddSalesOrderResponse AddSalesOrder(AddSalesOrderRequest request) throws
      ServletException {
    AddSalesOrderResponse response = new AddSalesOrderResponse();
    Connection conn = null;

    try {
      conn = datasource.getConnection();
      SalesOrder salesorder = request.getsalesorder();
      if (request.getcompany() != null) {
        CouponBean couponBean = new CouponBean();
        couponBean.generateSalesOrderCoupon(request.getcompany(), salesorder);
      }
      response.setid(addSalesOrder(conn, salesorder));
      if (salesorder.getcouponcode() != null &&
          salesorder.getcouponcode().length() > 0) {
        CouponBean.RedeemCoupons(conn, salesorder);
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

  private int addSalesOrder(Connection conn, SalesOrder salesorder) throws
      ServletException {
    int id = 0;
    UserBean userBean = new UserBean();
    SalesOrderItemBean salesOrderItemBean = new SalesOrderItemBean();

    try {
      pstmt = conn.prepareStatement(getInsertString());
      int col = 0;
      pstmt.setInt(++col, salesorder.getcustomer().getid());
      pstmt.setInt(++col, salesorder.getbillingaddress().getid());
      pstmt.setInt(++col, salesorder.getshippingaddress().getid());
      pstmt.setInt(++col, salesorder.getreferencenumber());
      pstmt.setString(++col, salesorder.getdescription());
      pstmt.setString(++col, salesorder.getcouponcode());
      pstmt.setDouble(++col, salesorder.gettotalcost());
      pstmt.setDouble(++col, salesorder.getdiscount());
      pstmt.setString(++col, salesorder.getdiscountdescription());
      pstmt.setDouble(++col, salesorder.gettaxes());
      pstmt.setString(++col, salesorder.gettaxesdescription());
      pstmt.setString(++col, salesorder.getstatus());
      pstmt.setBoolean(++col, salesorder.getoptimizeshipping());
      pstmt.setBoolean(++col, salesorder.getdropshipped());
      pstmt.setDouble(++col, salesorder.getshipping());
      pstmt.setInt(++col, salesorder.getshippingmethod().getid());
      pstmt.setDouble(++col, salesorder.getshippingweight());
      pstmt.setDouble(++col, salesorder.gethandling());
      pstmt.setDouble(++col, salesorder.gettotal());
      pstmt.setString(++col, salesorder.getpnref());
      pstmt.setString(++col, salesorder.getauthorizationcode());
      pstmt.setString(++col, salesorder.getemailstatus());
      pstmt.setString(++col, salesorder.getsalescoupon());
      salesorder.setcreationdate(new java.util.Date());
      pstmt.setTimestamp(++col,
                         new Timestamp(salesorder.getcreationdate().getTime()));

      if ( (pstmt.executeUpdate()) > 0) {
        id = getLastInsertID(conn);
        salesOrderItemBean.AddSalesOrderItem(conn, id,
                                             salesorder.getitemsIterator());
        userBean.ClearShoppingCart(conn, salesorder.getcustomer().getid());
        salesorder.setid(id);
        try {
          sendOrderReceivedMail(salesorder, conn);
        }
        catch (ServletException se) {}
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

  public RecalculateSalesOrderResponse RecalculateSalesOrder(
      RecalculateSalesOrderRequest request) throws
      ServletException {

    RecalculateSalesOrderResponse response = new RecalculateSalesOrderResponse();
    Connection conn = null;
    try {
      SalesOrder salesorder = request.getsalesorder();
      conn = datasource.getConnection();
      calculateTotals(salesorder);
      if (salesorder.getcouponcode() != null)
        calculateCouponSavings(conn, salesorder);
      calculateTax(conn, salesorder);
      salesorder.settotal( (salesorder.gettotalcost() - salesorder.getdiscount()) +
                          salesorder.gettaxes() +
                          salesorder.getshipping() + salesorder.gethandling());
      response.setsalesorder(salesorder);
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

  public UpdateSalesOrderResponse UpdateSalesOrder(UpdateSalesOrderRequest
      request) throws
      ServletException {

    SalesOrderItemBean salesOrderItemBean = new SalesOrderItemBean();
    UpdateSalesOrderResponse response = new UpdateSalesOrderResponse();
    Connection conn = null;

    try {
      conn = datasource.getConnection();
      updateSalesOrder(conn, request.getsalesorder());
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

  private void updateSalesOrder(Connection conn, SalesOrder salesorder) throws
      ServletException {

    SalesOrderItemBean salesOrderItemBean = new SalesOrderItemBean();

    try {
      salesOrderItemBean.DeleteSalesOrderItem(conn, salesorder.getid());

      pstmt = conn.prepareStatement(getUpdateString());
      int col = 0;
      pstmt.setInt(++col, salesorder.getcustomer().getid());
      pstmt.setInt(++col, salesorder.getbillingaddress().getid());
      pstmt.setInt(++col, salesorder.getshippingaddress().getid());
      pstmt.setInt(++col, salesorder.getreferencenumber());
      pstmt.setString(++col, salesorder.getdescription());
      pstmt.setString(++col, salesorder.getcouponcode());
      pstmt.setDouble(++col, salesorder.gettotalcost());
      pstmt.setDouble(++col, salesorder.getdiscount());
      pstmt.setString(++col, salesorder.getdiscountdescription());
      pstmt.setDouble(++col, salesorder.gettaxes());
      pstmt.setString(++col, salesorder.gettaxesdescription());
      pstmt.setString(++col, salesorder.getstatus());
      pstmt.setBoolean(++col, salesorder.getoptimizeshipping());
      pstmt.setBoolean(++col, salesorder.getdropshipped());
      pstmt.setDouble(++col, salesorder.getshipping());
      pstmt.setInt(++col, salesorder.getshippingmethod().getid());
      pstmt.setDouble(++col, salesorder.getshippingweight());
      pstmt.setDouble(++col, salesorder.gethandling());
      pstmt.setDouble(++col, salesorder.gettotal());
      pstmt.setString(++col, salesorder.getpnref());
      pstmt.setString(++col, salesorder.getauthorizationcode());
      pstmt.setString(++col, salesorder.getemailstatus());
      pstmt.setString(++col, salesorder.getsalescoupon());
      salesorder.setcreationdate(new java.util.Date());
      pstmt.setTimestamp(++col,
                         new Timestamp(salesorder.getcreationdate().getTime()));
      pstmt.setInt(++col, salesorder.getid());

      if ( (pstmt.executeUpdate()) > 0) {
        salesOrderItemBean.AddSalesOrderItem(conn, salesorder.getid(),
                                             salesorder.getitemsIterator());
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

  public DeleteSalesOrderResponse DeleteSalesOrder(DeleteSalesOrderRequest
      request) throws ServletException {
    DeleteSalesOrderResponse response = new DeleteSalesOrderResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      SalesOrder salesOrder = getSalesOrder(conn, request.getid());
      ItemRankingBean rankingBean = new ItemRankingBean();
      ItemBean itemBean = new ItemBean();

      Iterator it = salesOrder.getitemsIterator();
      while (it.hasNext()) {
        SalesOrderItem salesorderitem = (SalesOrderItem) it.next();
        rankingBean.SubtractItemSold(conn, salesorderitem.getitemnumber(),
                                     salesorderitem.getquantity());
        itemBean.UnAllocateItem(conn, salesorderitem.getitemnumber(),
                                salesorderitem.getquantity());
      }
      SalesOrderItemBean salesOrderItemBean = new SalesOrderItemBean();
      deleteSalesOrder(conn, salesOrderItemBean, request.getid());
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

  private void deleteSalesOrder(Connection conn,
                                SalesOrderItemBean salesOrderItemBean, int id) throws
      ServletException {

    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("delete from salesorder where id = ?");
      pstmt.setInt(1, id);
      pstmt.executeUpdate();
      salesOrderItemBean.DeleteSalesOrderItem(conn, id);
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

  void UpdateStatus(Connection conn, int id) throws
      ServletException {

    try {
      UpdateStatus(conn, getSalesOrder(conn, id));
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
  }

  static void UpdateStatus(Connection conn, SalesOrder salesorder) throws
      ServletException {

    boolean hasShipped = false;
    boolean hasNew = false;
    boolean hasProcessed = false;
    boolean hasFailed = false;

    PreparedStatement pstmt = null;

    try {
      Iterator it = salesorder.getitemsIterator();

      while (it.hasNext()) {
        SalesOrderItem item = (SalesOrderItem) it.next();
        if (item.getstatus().length() == 0 ||
            item.getstatus().equalsIgnoreCase("New")) {
          hasNew = true;
        }
        else if (item.getstatus().equalsIgnoreCase("Shipped")) {
          hasShipped = true;
        }
        else if (item.getstatus().equalsIgnoreCase("Proccessed")) {
          hasProcessed = true;
          if (item.getquantity() > item.getquantitytoship() + item.getshipped())
            hasNew = true;
        }
        else if (item.getstatus().equalsIgnoreCase("Partially Proccessed")) {
          hasProcessed = true;
          hasNew = true;
        }
        else if (item.getstatus().equalsIgnoreCase("Failed")) {
          hasFailed = true;
        }
        else if (item.getstatus().equalsIgnoreCase("Partially Shipped")) {
          hasNew = true;
          hasShipped = true;
        }
      }

      String status = "";
      if (hasFailed)
        status += "Declined Credit Card ";

      if (hasProcessed) {
        if (!hasShipped && !hasNew && !hasFailed)
          status += "Proccessed ";
        else
          status += "Partially Proccessed ";
      }

      if (hasShipped) {
        if (!hasProcessed && !hasNew && !hasFailed)
          status += "Shipped ";
        else
          status += "Partially Shipped ";
      }

      if (hasShipped || hasProcessed || hasShipped || hasNew || hasFailed) {
        pstmt = conn.prepareStatement(
            "update salesorder set status = ? where id = ?");
        pstmt.setString(1, status);
        pstmt.setInt(2, salesorder.getid());
        pstmt.executeUpdate();
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

  private void updateDropShippedStatus(Connection conn, int id, int poid,
                                       int distributorid) throws
      ServletException {

    try {
      String description = "Sales Order dropped shipped from distributor # " +
          Integer.toString(distributorid) +
          ". For more details see purchase order # " +
          Integer.toString(poid) + ".";
      pstmt = conn.prepareStatement(
          "update salesorder set dropshipped=1, referencenumber=?, description=? where id = ?");
      pstmt.setInt(1, poid);
      pstmt.setString(2, description);
      pstmt.setInt(3, id);
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

  public GetSalesOrderResponse GetSalesOrder(GetSalesOrderRequest request) throws
      ServletException {
    GetSalesOrderResponse response = new GetSalesOrderResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      response.setsalesorder(getSalesOrder(conn, request.getid()));
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

  SalesOrder getSalesOrder(Connection conn, int id) throws
      ServletException {
    return getSalesOrder(conn, id, false);
  }

  SalesOrder getSalesOrder(Connection conn, int id, boolean bReady) throws
      ServletException {

    SalesOrder salesorder = null;
    try {
      CustomerBean customerbean = new CustomerBean();
      AddressBean addressbean = new AddressBean();
      ListsBean listsbean = new ListsBean();
      SalesOrderItemBean salesOrderItemBean = new SalesOrderItemBean();

      pstmt = conn.prepareStatement(getSelectWithIDByIDString());
      pstmt.setInt(1, id);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        salesorder = new SalesOrder();
        int col = 0;
        salesorder.setid(rs.getInt(++col));
        int customerid = rs.getInt(++col);
        int billingaddrid = rs.getInt(++col);
        int shippingaddrid = rs.getInt(++col);
        salesorder.setreferencenumber(rs.getInt(++col));
        salesorder.setdescription(rs.getString(++col));
        salesorder.setcouponcode(rs.getString(++col));
        salesorder.settotalcost(rs.getDouble(++col));
        salesorder.setdiscount(rs.getDouble(++col));
        salesorder.setdiscountdescription(rs.getString(++col));
        salesorder.settaxes(rs.getDouble(++col));
        salesorder.settaxesdescription(rs.getString(++col));
        salesorder.setstatus(rs.getString(++col));
        salesorder.setoptimizeshipping(rs.getBoolean(++col));
        salesorder.setdropshipped(rs.getBoolean(++col));
        salesorder.setshipping(rs.getDouble(++col));
        int shippingmethodid = rs.getInt(++col);
        salesorder.setshippingweight(rs.getDouble(++col));
        salesorder.sethandling(rs.getDouble(++col));
        salesorder.settotal(rs.getDouble(++col));
        salesorder.setpnref(rs.getString(++col));
        salesorder.setauthorizationcode(rs.getString(++col));
        salesorder.setemailstatus(rs.getString(++col));
        salesorder.setsalescoupon(rs.getString(++col));
        salesorder.setcreationdate(new java.util.Date(rs.getTimestamp(++col).
            getTime()));

        salesorder.setcustomer(customerbean.GetCustomer(conn, customerid));
        salesorder.setbillingaddress(addressbean.GetAddress(conn,
            billingaddrid));
        salesorder.setshippingaddress(addressbean.GetAddress(conn,
            shippingaddrid));
        salesorder.setshippingmethod(listsbean.GetShippingMethod(conn,
            shippingmethodid));
        if (bReady == true) {
          salesOrderItemBean.GetSalesOrderItemsReady(conn, salesorder);
          salesorder.setshipping(0);
          salesorder.setshippingweight(0);
          salesorder.setdiscount(0);
          salesorder.setdiscountdescription(null);
          salesorder.settaxes(0);
          salesorder.sethandling(0);
          salesorder.settaxesdescription(null);
          calculateTotals(salesorder, bReady);
          calculateCouponSavings(conn, salesorder, bReady);
          calculateTax(conn, salesorder, bReady);
          salesorder.settotal(salesorder.gettotalcost() + salesorder.gettaxes() +
                              salesorder.getshipping() + salesorder.gethandling());
        }
        else
          salesOrderItemBean.GetSalesOrderItem(conn, salesorder);
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
    return salesorder;
  }

  public GetSalesOrdersResponse GetSalesOrders(GetSalesOrdersRequest request) throws
      ServletException {
    GetSalesOrdersResponse response = new GetSalesOrdersResponse();
    Connection conn = null;
    try {
      CustomerBean customerbean = new CustomerBean();
      AddressBean addressbean = new AddressBean();
      ListsBean listsbean = new ListsBean();
      SalesOrderItemBean salesOrderItemBean = new SalesOrderItemBean();

      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getSelectWithIDString() + bycustomer);
      pstmt.setInt(1, request.getcustomerid());
      rs = pstmt.executeQuery();
      while (rs.next()) {
        SalesOrder salesorder = new SalesOrder();
        int col = 0;
        salesorder.setid(rs.getInt(++col));
        int customerid = rs.getInt(++col);
        int billingaddrid = rs.getInt(++col);
        int shippingaddrid = rs.getInt(++col);
        salesorder.setreferencenumber(rs.getInt(++col));
        salesorder.setdescription(rs.getString(++col));
        salesorder.setcouponcode(rs.getString(++col));
        salesorder.settotalcost(rs.getDouble(++col));
        salesorder.setdiscount(rs.getDouble(++col));
        salesorder.setdiscountdescription(rs.getString(++col));
        salesorder.settaxes(rs.getDouble(++col));
        salesorder.settaxesdescription(rs.getString(++col));
        salesorder.setstatus(rs.getString(++col));
        salesorder.setoptimizeshipping(rs.getBoolean(++col));
        salesorder.setdropshipped(rs.getBoolean(++col));
        salesorder.setshipping(rs.getDouble(++col));
        int shippingmethodid = rs.getInt(++col);
        salesorder.setshippingweight(rs.getDouble(++col));
        salesorder.sethandling(rs.getDouble(++col));
        salesorder.settotal(rs.getDouble(++col));
        salesorder.setpnref(rs.getString(++col));
        salesorder.setauthorizationcode(rs.getString(++col));
        salesorder.setemailstatus(rs.getString(++col));
        salesorder.setsalescoupon(rs.getString(++col));
        java.sql.Timestamp createtime = rs.getTimestamp(++col);
        if (createtime != null)
          salesorder.setcreationdate(new java.util.Date(createtime.getTime()));

        salesorder.setcustomer(customerbean.GetCustomer(conn, customerid));
        salesorder.setbillingaddress(addressbean.GetAddress(conn,
            billingaddrid));
        salesorder.setshippingaddress(addressbean.GetAddress(conn,
            shippingaddrid));
        salesorder.setshippingmethod(listsbean.GetShippingMethod(conn,
            shippingmethodid));
        salesOrderItemBean.GetSalesOrderItem(conn, salesorder);
        response.setsalesorder(salesorder);
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

  public GetSalesOrdersResponse GetUnshippedSalesOrders(GetSalesOrdersRequest
      request) throws
      ServletException {
    GetSalesOrdersResponse response = new GetSalesOrdersResponse();
    Connection conn = null;
    try {
      CustomerBean customerbean = new CustomerBean();
      AddressBean addressbean = new AddressBean();
      ListsBean listsbean = new ListsBean();
      SalesOrderItemBean salesOrderItemBean = new SalesOrderItemBean();

      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getSelectWithIDString() + bynotshipped);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        SalesOrder salesorder = new SalesOrder();
        int col = 0;
        salesorder.setid(rs.getInt(++col));
        int customerid = rs.getInt(++col);
        int billingaddrid = rs.getInt(++col);
        int shippingaddrid = rs.getInt(++col);
        salesorder.setreferencenumber(rs.getInt(++col));
        salesorder.setdescription(rs.getString(++col));
        salesorder.setcouponcode(rs.getString(++col));
        salesorder.settotalcost(rs.getDouble(++col));
        salesorder.setdiscount(rs.getDouble(++col));
        salesorder.setdiscountdescription(rs.getString(++col));
        salesorder.settaxes(rs.getDouble(++col));
        salesorder.settaxesdescription(rs.getString(++col));
        salesorder.setstatus(rs.getString(++col));
        salesorder.setoptimizeshipping(rs.getBoolean(++col));
        salesorder.setdropshipped(rs.getBoolean(++col));
        salesorder.setshipping(rs.getDouble(++col));
        int shippingmethodid = rs.getInt(++col);
        salesorder.setshippingweight(rs.getDouble(++col));
        salesorder.sethandling(rs.getDouble(++col));
        salesorder.settotal(rs.getDouble(++col));
        salesorder.setpnref(rs.getString(++col));
        salesorder.setauthorizationcode(rs.getString(++col));
        salesorder.setemailstatus(rs.getString(++col));
        salesorder.setsalescoupon(rs.getString(++col));
        java.sql.Timestamp createtime = rs.getTimestamp(++col);
        if (createtime != null)
          salesorder.setcreationdate(new java.util.Date(createtime.getTime()));

        salesorder.setcustomer(customerbean.GetCustomer(conn, customerid));
        salesorder.setbillingaddress(addressbean.GetAddress(conn,
            billingaddrid));
        salesorder.setshippingaddress(addressbean.GetAddress(conn,
            shippingaddrid));
        salesorder.setshippingmethod(listsbean.GetShippingMethod(conn,
            shippingmethodid));
        salesOrderItemBean.GetSalesOrderItem(conn, salesorder);
        response.setsalesorder(salesorder);
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

  public GetSalesOrdersResponse GetOpenSalesOrders(GetSalesOrdersRequest
      request) throws
      ServletException {
    GetSalesOrdersResponse response = new GetSalesOrdersResponse();
    Connection conn = null;
    try {
      CustomerBean customerbean = new CustomerBean();
      AddressBean addressbean = new AddressBean();
      ListsBean listsbean = new ListsBean();
      SalesOrderItemBean salesOrderItemBean = new SalesOrderItemBean();
      conn = datasource.getConnection();

      pstmt = conn.prepareStatement(clearallocated);
      pstmt.executeUpdate();
      pstmt.close();

      pstmt = conn.prepareStatement(selectbystatus);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        SalesOrder salesorder = new SalesOrder();
        int col = 0;
        salesorder.setid(rs.getInt(++col));
        int customerid = rs.getInt(++col);
        int billingaddrid = rs.getInt(++col);
        int shippingaddrid = rs.getInt(++col);
        salesorder.setreferencenumber(rs.getInt(++col));
        salesorder.setdescription(rs.getString(++col));
        salesorder.setcouponcode(rs.getString(++col));
        salesorder.settotalcost(rs.getDouble(++col));
        salesorder.setdiscount(rs.getDouble(++col));
        salesorder.setdiscountdescription(rs.getString(++col));
        salesorder.settaxes(rs.getDouble(++col));
        salesorder.settaxesdescription(rs.getString(++col));
        salesorder.setstatus(rs.getString(++col));
        salesorder.setoptimizeshipping(rs.getBoolean(++col));
        salesorder.setdropshipped(rs.getBoolean(++col));
        salesorder.setshipping(rs.getDouble(++col));
        int shippingmethodid = rs.getInt(++col);
        salesorder.setshippingweight(rs.getDouble(++col));
        salesorder.sethandling(rs.getDouble(++col));
        salesorder.settotal(rs.getDouble(++col));
        salesorder.setpnref(rs.getString(++col));
        salesorder.setauthorizationcode(rs.getString(++col));
        salesorder.setemailstatus(rs.getString(++col));
        salesorder.setsalescoupon(rs.getString(++col));
        salesorder.setcreationdate(new java.util.Date(rs.getTimestamp(++col).
            getTime()));

        salesOrderItemBean.GetOpenSalesOrderItems(conn, salesorder);
        if (salesorder.getitemsIterator().hasNext()) {
          salesorder.setcustomer(customerbean.GetCustomer(conn, customerid));
          salesorder.setbillingaddress(addressbean.GetAddress(conn,
              billingaddrid));
          salesorder.setshippingaddress(addressbean.GetAddress(conn,
              shippingaddrid));
          salesorder.setshippingmethod(listsbean.GetShippingMethod(conn,
              shippingmethodid));
          response.setsalesorder(salesorder);
        }
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

  public GetSalesOrderReportResponse GetSalesOrderReport(
      GetSalesOrderReportRequest request) throws
      ServletException {
    GetSalesOrderReportResponse response = new GetSalesOrderReportResponse();
    Connection conn = null;
    try {
      CustomerBean customerbean = new CustomerBean();
      AddressBean addressbean = new AddressBean();
      ListsBean listsbean = new ListsBean();
      SalesOrderItemBean salesOrderItemBean = new SalesOrderItemBean();

      conn = datasource.getConnection();
      if (request.getstartdate() != null) {
        pstmt = conn.prepareStatement(getSelectWithIDString() + bydate);
        pstmt.setTimestamp(1, new Timestamp(request.getstartdate().getTime()));
        pstmt.setTimestamp(2, new Timestamp(request.getenddate().getTime()));
      }
      else {
        pstmt = conn.prepareStatement(getSelectWithIDString() + orderbycreation);
      }

      rs = pstmt.executeQuery();
      while (rs.next()) {
        SalesOrder salesorder = new SalesOrder();
        int col = 0;
        salesorder.setid(rs.getInt(++col));
        int customerid = rs.getInt(++col);
        int billingaddrid = rs.getInt(++col);
        int shippingaddrid = rs.getInt(++col);
        salesorder.setreferencenumber(rs.getInt(++col));
        salesorder.setdescription(rs.getString(++col));
        salesorder.setcouponcode(rs.getString(++col));
        salesorder.settotalcost(rs.getDouble(++col));
        salesorder.setdiscount(rs.getDouble(++col));
        salesorder.setdiscountdescription(rs.getString(++col));
        salesorder.settaxes(rs.getDouble(++col));
        salesorder.settaxesdescription(rs.getString(++col));
        salesorder.setstatus(rs.getString(++col));
        salesorder.setoptimizeshipping(rs.getBoolean(++col));
        salesorder.setdropshipped(rs.getBoolean(++col));
        salesorder.setshipping(rs.getDouble(++col));
        int shippingmethodid = rs.getInt(++col);
        salesorder.setshippingweight(rs.getDouble(++col));
        salesorder.sethandling(rs.getDouble(++col));
        salesorder.settotal(rs.getDouble(++col));
        salesorder.setpnref(rs.getString(++col));
        salesorder.setauthorizationcode(rs.getString(++col));
        salesorder.setemailstatus(rs.getString(++col));
        salesorder.setsalescoupon(rs.getString(++col));
        salesorder.setcreationdate(new java.util.Date(rs.getTimestamp(++col).
            getTime()));

        salesorder.setcustomer(customerbean.GetCustomer(conn, customerid));
        salesorder.setbillingaddress(addressbean.GetAddress(conn,
            billingaddrid));
        salesorder.setshippingaddress(addressbean.GetAddress(conn,
            shippingaddrid));
        salesorder.setshippingmethod(listsbean.GetShippingMethod(conn,
            shippingmethodid));
        salesOrderItemBean.GetSalesOrderItem(conn, salesorder);
        response.setsalesorder(salesorder);
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

  boolean isReadyToShip(SalesOrder salesorder) {

    boolean bRet = true;
    Iterator it = salesorder.getitemsIterator();
    while (it.hasNext()) {
      SalesOrderItem item = (SalesOrderItem) it.next();
      if (!item.getstatus().equalsIgnoreCase("Ready to Ship") ||
          item.getquantity() != item.getquantitytoship())
        bRet = false;
    }

    return bRet;
  }

  private void sendOrderReceivedMail(SalesOrder salesorder, Connection conn) throws
      ServletException {

    MailConfiguration mailconfig = new MailConfiguration();

    try {
      pstmt = conn.prepareStatement(
          "select mailhost, mailfromname, mailfromaddress, " +
          "mailauthuser, mailauthpassword, url, mailsubject " +
          "from mailconfig where id = 'order'");
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
            new InternetAddress(salesorder.getcustomer().
                                getemail1())};
        msg.setRecipients(Message.RecipientType.TO, dests);

        msg.setSentDate(new java.util.Date());
        String subject = mailconfig.getMailSubject();
        subject += " Order # " + Integer.toString(salesorder.getid());
        msg.setSubject(subject);

        XMLTransmitter transmitter = new XMLTransmitter();
        transmitter.setUrl(mailconfig.getMailURL() +
                           Integer.toString(salesorder.getid()));
        String body = transmitter.doTransaction("");
        msg.setContent(body, "text/html");

        Transport transport = session.getTransport("smtp");
        transport.connect(mailconfig.getMailHost(), mailconfig.getMailAuthUser(),
                          mailconfig.getMailAuthPassword());

        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();
        pstmt.close();
        pstmt = conn.prepareStatement(
            "update salesorder set mailstatus='order received' where id=?");
        pstmt.setInt(1, salesorder.getid());
        pstmt.executeUpdate();

        EMailRecord emrequest = new EMailRecord();
        emrequest.setreferenceno(salesorder.getid());
        emrequest.settype("Order Recieved");
        emrequest.setrecipientid(salesorder.getcustomer().getid());
        emrequest.setrecipient(salesorder.getcustomer().getemail1());
        emrequest.setsubject(subject);
        emrequest.setbody(body);
        new EMailRecordBean().addEMailRecord(conn, emrequest);
      }
    }
    catch (javax.mail.MessagingException me) {
      try {
        pstmt = conn.prepareStatement(
            "update salesorder set mailstatus='order received failed' where id=?");
        pstmt.setInt(1, salesorder.getid());
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

  public void SendDropShipMail(int id, int distributorid) throws
      ServletException {
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      sendDropShipMail(id, distributorid, conn);
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

  private void sendDropShipMail(int id, int distributorid,
                                Connection conn) throws
      ServletException {

    MailConfiguration mailconfig = new MailConfiguration();

    try {
      Distributor distributor = ListsBean.getDistributor(conn, distributorid);
      if (distributor != null && distributor.getemail() != null) {
        pstmt = conn.prepareStatement(
            "select mailhost, mailfromname, mailfromaddress, " +
            "mailauthuser, mailauthpassword, url, mailsubject " +
            "from mailconfig where id = 'dropship_" +
            Integer.toString(distributorid) + "'");
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
          subject += " Purchase Order # " + Integer.toString(id);
          msg.setSubject(subject);

          XMLTransmitter transmitter = new XMLTransmitter();
          transmitter.setUrl(mailconfig.getMailURL() + Integer.toString(id));
          String body = transmitter.doTransaction("");
          msg.setContent(body, "text/html");

          Transport transport = session.getTransport("smtp");
          transport.connect(mailconfig.getMailHost(),
                            mailconfig.getMailAuthUser(),
                            mailconfig.getMailAuthPassword());

          transport.sendMessage(msg, msg.getAllRecipients());
          transport.close();
          pstmt.close();
          pstmt = conn.prepareStatement(
              "update salesorder set mailstatus='drop ship' where id=?");
          pstmt.setInt(1, id);
          pstmt.executeUpdate();

          EMailRecord emrequest = new EMailRecord();
          emrequest.setreferenceno(id);
          emrequest.settype("Order Recieved");
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
            "update salesorder set mailstatus='Drop shipped' where id=?");
        pstmt.setInt(1, id);
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

  public SplitSalesOrderResponse SplitSalesOrder(SplitSalesOrderRequest request) throws
      ServletException {

    SplitSalesOrderResponse response = new SplitSalesOrderResponse();
    Connection conn = null;

    try {
      conn = datasource.getConnection();
      SalesOrder salesorder = request.getsalesorder();
      SalesOrder splitsalesorder = new SalesOrder();

      splitsalesorder.setcustomer(salesorder.getcustomer());
      splitsalesorder.setbillingaddress(salesorder.getbillingaddress());
      splitsalesorder.setshippingaddress(salesorder.getshippingaddress());
      ShippingMethod shippingMethod = request.getshippingmethod();
      if (shippingMethod != null) {
        splitsalesorder.setshippingmethod(shippingMethod);
      }

      Iterator it = salesorder.getitemsIterator();

      double totalcost = 0;
      double totalweight = 0;
      double splittotalcost = 0;
      double splittotalweight = 0;
      while (it.hasNext()) {
        SalesOrderItem item = (SalesOrderItem) it.next();

        if (item.gettrxtype().equalsIgnoreCase("split")) {
          splittotalcost += item.getunitprice() * item.getquantity();
          splittotalweight += item.getshippingweight() * item.getquantity();
          splitsalesorder.setitems(item);
        }
        else {
          totalcost += item.getunitprice() * item.getquantity();
          totalweight += item.getshippingweight() * item.getquantity();
          it.remove();
        }
      }
      salesorder.setshippingweight(totalweight);
      salesorder.settotalcost(totalcost);

      if (salesorder.getcouponcode() != null)
        calculateCouponSavings(conn, salesorder);
      calculateTax(conn, salesorder);

      salesorder.settotal( (salesorder.gettotalcost() - salesorder.getdiscount()) +
                          salesorder.gettaxes() +
                          salesorder.getshipping() + salesorder.gethandling());
      updateSalesOrder(conn, salesorder);

      splitsalesorder.setstatus("New");
      splitsalesorder.setcouponcode(salesorder.getcouponcode());
      splitsalesorder.setshippingweight(splittotalweight);
      splitsalesorder.settotalcost(splittotalcost);

      if (splitsalesorder.getcouponcode() != null)
        calculateCouponSavings(conn, splitsalesorder);
      calculateTax(conn, splitsalesorder);

      splitsalesorder.settotal( (splitsalesorder.gettotalcost() -
                                 splitsalesorder.getdiscount()) +
                               splitsalesorder.gettaxes() +
                               splitsalesorder.getshipping() +
                               splitsalesorder.gethandling());
      response.setid(addSalesOrder(conn, splitsalesorder));

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
