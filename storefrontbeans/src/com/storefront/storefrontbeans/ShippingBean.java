package com.storefront.storefrontbeans;

import java.util.*;
import javax.servlet.*;
import java.sql.*;
import javax.naming.*;
import javax.mail.*;
import javax.mail.internet.*;
import com.adaptinet.transmitter.*;
import com.adaptinet.xmlutils.*;
import com.storefront.storefrontrepository.*;
import com.storefront.shippingrequest.*;
import com.storefront.shippingresponse.*;
import com.storefront.webuspsrate.*;
import com.adaptinet.transmitter.*;

public class ShippingBean
    extends BaseBean {

  final static private String fields[] = {
      "license", "user", "password", "pickuptype",
      "version", "url"};

  final static private String tablename = "carrier";

  final static private String selectbycode = "select code, license, user, " +
      "password, pickuptype, version, url from carrier " +
      "where code = ?";

  final static private String selectreadysalesorders =
      "select distinct a.id from salesorder a, salesorderitem b " +
      "where a.id = b.salesorderid and b.status = 'Ready to Ship'";

  final static private String strpackage = "02";

  public ShippingBean() throws ServletException {
    super();
  }

  String[] getfields() {
    return fields;
  }

  String gettableName() {
    return tablename;
  }

  String getindexName() {
    return "code";
  }

  class ShippingPackage {
    String zip = null;
    String country = null;
    double weight = 0;
    double value = 0;
  }

  class ShippingCostHelper {
    double shipping;
    double cost;
    double taxes;
    double discounts;
  }

  public CreatePackingslipsResponse CreatePackingslips(
      CreatePackingslipsRequest request) throws
      ServletException {

    CreatePackingslipsResponse response = new CreatePackingslipsResponse();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    SalesOrderBean salesOrderBean = new SalesOrderBean();
    try {
      int total = 0;
      conn = datasource.getConnection();

      /*******************************************************
      *
      * Iterate through the list of salesorderitems to ship
      * marking each as ready to ship.
      *
      *******************************************************/
      pstmt = conn.prepareStatement("update salesorderitem set status=?, quantitytoship=? where id=?");
      Iterator it = request.getsalesorderitemsIterator();
      while (it.hasNext()) {
        SalesOrderItem salesorderitem = (SalesOrderItem) it.next();
        pstmt.setString(1, "Ready to Ship");
        pstmt.setInt(2, salesorderitem.getquantitytoship());
        pstmt.setInt(3, salesorderitem.getid());
        pstmt.executeUpdate();
      }
      pstmt.close();

      /*******************************************************
      *
      * Get a list of sales orders that have at least one item
      * marked as ready to ship.
      *
      *******************************************************/
      pstmt = conn.prepareStatement(selectreadysalesorders);
      rs = pstmt.executeQuery();

      while(rs.next()) {
        SalesOrder salesorder = salesOrderBean.getSalesOrder(conn, rs.getInt(1));
        if(salesOrderBean.isReadyToShip(salesorder)) {
          processSalesOrder(conn, salesorder);
          salesOrderBean.UpdateStatus(conn, salesorder);
        }
        else {
          SalesOrder readysalesorder = salesOrderBean.getSalesOrder(conn, rs.getInt(1), true);
          if(readysalesorder.getoptimizeshipping() || readysalesorder.getstatus().equalsIgnoreCase("New")) {
            GetShippingCost(readysalesorder, true);
          }
          else {
            ShippingCostHelper helper = new ShippingCostHelper();
            getShippingCharged(conn, salesorder.getid(), helper);
            if(helper.shipping>0) {
              double shippingleft = salesorder.getshipping()-helper.shipping;
              if(shippingleft>0) {
                if (salesorder.gettotalcost() - (helper.cost+readysalesorder.gettotalcost())<.01) {
                  readysalesorder.setshipping(shippingleft);
                  readysalesorder.settaxes(salesorder.gettaxes()-helper.taxes);
                  readysalesorder.setdiscount(salesorder.getdiscount()-helper.discounts);
                }
                else {
                  readysalesorder.setshipping(shippingleft *
                                              ( (salesorder.gettotalcost() -
                                                 helper.cost) /
                                               readysalesorder.gettotalcost()));
                }
              }
              readysalesorder.settotal(readysalesorder.gettotal()+readysalesorder.getshipping());
            }
          }
          processSalesOrder(conn, readysalesorder);
          salesOrderBean.UpdateStatus(conn, salesorder.getid());
        }
        total++;
      }
      response.setpackingslipscreated(total);
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

  private void getShippingCharged(Connection conn, int id, ShippingCostHelper helper) throws
      ServletException {

    ResultSet rs = rs = null;
    PreparedStatement psmt = null;
    try {
      psmt = conn.prepareStatement(
          "SELECT sum(shippingcost), sum(totalcost), sum(taxes), sum(discount) FROM invoice where salesorderid=?");
      psmt.setInt(1, id);
      rs = psmt.executeQuery();
      if(rs.next()) {
        helper.shipping=rs.getDouble(1);
        helper.cost=rs.getDouble(2);
        helper.taxes=rs.getDouble(3);
        helper.discounts=rs.getDouble(4);
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

  public ShipPackagesResponse ShipPackages(
      ShipPackagesRequest request) throws
      ServletException {

    PackingslipItemBean packingslipItemBean = new PackingslipItemBean();
    SalesOrderBean salesOrderBean = new SalesOrderBean();
    ShipPackagesResponse response = new ShipPackagesResponse();
    Connection conn = null;
    PreparedStatement pstmt = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("update packingslip set trackingnumber = ? where id = ?");
      Iterator it = request.getpackingslipIterator();
      while(it.hasNext()) {
        Packingslip packingslip = (Packingslip)it.next();
        pstmt.setString(1,packingslip.gettrackingNumber());
        pstmt.setInt(2, packingslip.getid());
        pstmt.executeUpdate();
        packingslipItemBean.ShipPackingslipItems(conn, packingslip.getitemsIterator());
        sendOrderShippedMail(packingslip, conn);

        Iterator it2 =  packingslip.getitemsIterator();
        while (it2.hasNext()) {
          PackingslipItem item = (PackingslipItem)it2.next();
          salesOrderBean.UpdateStatus(conn, item.getsalesorderid());
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
      try {
        if (conn != null) conn.close();
        conn = null;
      }
      catch (SQLException sqle) {}
    }
    return response;
  }

  private void processSalesOrder(Connection conn, SalesOrder salesorder) throws ServletException {

    try {
      PayFlowBean payflow = new PayFlowBean();
      InvoiceBean invoiceBean = new InvoiceBean();
      PackingslipBean packingslipBean = new PackingslipBean();
      Invoice invoice = GenerateInvoice(conn, salesorder);
      UpdateSalesOrderItem(conn, salesorder.getitemsIterator(), "Proccessing");
      PayFlowProRequest pfrequest = new PayFlowProRequest();
      pfrequest.setsalesorder(salesorder);
      pfrequest.settrxtype("D");
      PayFlowProResponse pfresponse = payflow.CreditCardTransaction(pfrequest);

      if (pfresponse.getResult().equals("0")) {
        invoice.setauthorizationcode(pfresponse.getAuthCode());
        Packingslip packingslip = GeneratePackingslip(conn, salesorder);
        invoice.setstatus("closed");
        invoiceBean.AddInvoice(conn, invoice);
        packingslip.setinvoiceid(invoice.getid());
        packingslipBean.AddPackingslip(conn, packingslip);
        UpdateSalesOrderItemProcessed(conn, salesorder.getitemsIterator());
      }
      else {
        invoice.setstatus(pfresponse.getRespMsg());
        invoiceBean.AddInvoice(conn, invoice);
        UpdateSalesOrderItem(conn, salesorder.getitemsIterator(), "Failed");
        sendDeclindedCreditCardMail(salesorder, conn);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
  }

  void UpdateSalesOrderItem(Connection conn, Iterator it, String status) throws
      ServletException {

    try {
      pstmt = conn.prepareStatement("update salesorderitem set status=? where id=?");
      while (it.hasNext()) {
        SalesOrderItem salesorderitem = (SalesOrderItem) it.next();
        salesorderitem.setstatus(status);
        pstmt.setString(1, status);
        pstmt.setInt(2, salesorderitem.getid());
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

  void UpdateSalesOrderItemProcessed(Connection conn, Iterator it) throws
      ServletException {

    try {
      pstmt = conn.prepareStatement("update salesorderitem set status=? where id=?");
      while (it.hasNext()) {
        SalesOrderItem salesorderitem = (SalesOrderItem) it.next();
        String status = null;
        if(salesorderitem.getquantity()==salesorderitem.getquantitytoship() + salesorderitem.getshipped())
          status = "Proccessed";
        else
          status = "Partially Proccessed";
        salesorderitem.setstatus(status);
        pstmt.setString(1, status);
        pstmt.setInt(2, salesorderitem.getid());
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

  private Invoice GenerateInvoice(Connection conn, SalesOrder salesorder) throws ServletException {
    Invoice invoice = null;
    try {
      invoice = new Invoice();
      invoice.setcustomer(salesorder.getcustomer());
      invoice.setbilling(salesorder.getbillingaddress());
      invoice.settotalcost(salesorder.gettotalcost());
      invoice.setsalesorderid(salesorder.getid());
      invoice.setdiscount(salesorder.getdiscount());
      invoice.setdiscountdescription(salesorder.getdiscountdescription());
      invoice.settaxes(salesorder.gettaxes());
      invoice.settaxesdescription(salesorder.gettaxesdescription());
      invoice.setshippingcost(salesorder.getshipping());
      invoice.sethandling(salesorder.gethandling());
      invoice.settotal(salesorder.gettotal());

      Iterator it = salesorder.getitemsIterator();
      while (it.hasNext()) {
        SalesOrderItem salesorderitem = (SalesOrderItem)it.next();
        InvoiceItem item = new InvoiceItem();
        item.setsalesorderitemid(salesorderitem.getid());
        item.setunitPrice(salesorderitem.getunitprice());
        item.settotalPrice(salesorderitem.getunitprice()*salesorderitem.getquantitytoship());
        item.setitemnumber(salesorderitem.getitemnumber());
        item.setquantity(salesorderitem.getquantitytoship());
        item.setgiftoption(salesorderitem.getgiftoption());
        invoice.setitems(item);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    return invoice;
  }

  private Packingslip GeneratePackingslip(Connection conn, SalesOrder salesorder) throws ServletException {
    Packingslip packingslip = null;
    try {
      packingslip = new Packingslip();
      packingslip.setcustomerid(salesorder.getcustomer().getid());
      packingslip.setshipping(salesorder.getshippingaddress());
      packingslip.setcarrierName(salesorder.getshippingmethod().getcarrier());
      packingslip.setshippingmethod(salesorder.getshippingmethod());
      packingslip.setsalescoupon(salesorder.getsalescoupon());
      packingslip.setcreationdate(new Timestamp(new java.util.Date().
          getTime()));

      Iterator it = salesorder.getitemsIterator();
      while (it.hasNext()) {
        SalesOrderItem salesorderitem = (SalesOrderItem) it.next();
        PackingslipItem packingslipItem = new PackingslipItem();
        packingslipItem.setsalesorderid(salesorder.getid());
        packingslipItem.setsalesorderitemid(salesorderitem.getid());
        packingslipItem.setquantity(salesorderitem.getquantitytoship());
        packingslipItem.setitemid(salesorderitem.getitemnumber());
        packingslipItem.setisin(salesorderitem.getisin());
        packingslipItem.setproductname(salesorderitem.getproductname());
        packingslip.setitems(packingslipItem);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    return packingslip;
  }

  void GetShippingCost(SalesOrder salesorder) throws ServletException {
    GetShippingCost(salesorder, false);
  }

  void GetShippingCost(SalesOrder salesorder, boolean bToShip) throws ServletException {

    Carrier carrier = null;
    Connection conn = null;
    double totalcharges = 0;
    double handlingfee = 0;
    double totalweight = 0;

    ShippingMethod shippingMethod = null;
    try {
      if (salesorder.getshippingmethod().getid() > 0) {
        try {
          conn = datasource.getConnection();
          shippingMethod = new ListsBean().GetShippingMethod(
              conn, salesorder.getshippingmethod().getid());
          salesorder.setshippingmethod(shippingMethod);
          pstmt = conn.prepareStatement(selectbycode);
          pstmt.setString(1, shippingMethod.getcarrier());
          ResultSet rs = pstmt.executeQuery();
          if (rs.next()) {
            carrier = new Carrier();
            carrier.setcode(rs.getString(1));
            carrier.setlicense(rs.getString(2));
            carrier.setuser(rs.getString(3));
            carrier.setpassword(rs.getString(4));
            carrier.setpickuptype(rs.getString(5));
            carrier.setversion(rs.getString(6));
            carrier.seturl(rs.getString(7));
          }
        }
        catch (Exception ex) {
          throw new Exception(ex.getMessage());
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

        double fixedprice = salesorder.getshippingmethod().getfixedprice();
        Iterator it = salesorder.getitemsIterator();

        HashMap packages = new HashMap();

        while (it.hasNext()) {
          SalesOrderItem item = (SalesOrderItem) it.next();
          int qty = 0;
          if(bToShip==false) {
            qty=item.getquantity();
          }
          else {
            qty=item.getquantitytoship();
          }

          totalweight = item.getshippingweight()*qty;
          String key = item.getzipcode() + item.getcountry();
          ShippingPackage shippingPackage = (ShippingPackage) packages.get(key);
          if (handlingfee < item.gethandlingcharges())
            handlingfee = item.gethandlingcharges();
          if (shippingPackage == null) {
            shippingPackage = new ShippingPackage();
            shippingPackage.zip = item.getzipcode();
            shippingPackage.country = item.getcountry();
            shippingPackage.weight = item.getshippingweight();
            shippingPackage.value = qty * item.getunitprice();
            packages.put(key, shippingPackage);
          }
          else {
            shippingPackage.weight += (item.getshippingweight()*qty);
            shippingPackage.value = qty * item.getunitprice();
          }
        }

        if (totalweight == 0)
          throw new Exception("Invalid Shipping information");

        salesorder.setshippingweight(totalweight);

        if (fixedprice > 0) {
          totalcharges=totalweight * fixedprice;
        }
        else if (carrier.getcode().equalsIgnoreCase("UPS")) {
          com.storefront.shippingrequest.AccessRequest access = new com.
              storefront.shippingrequest.AccessRequest();
          com.storefront.shippingrequest.AccessLicenseNumber alm = new com.
              storefront.shippingrequest.AccessLicenseNumber();
          alm.setContentData(carrier.getlicense());
          access.setAccessLicenseNumber(alm);
          com.storefront.shippingrequest.UserId userid = new com.storefront.
              shippingrequest.UserId();
          userid.setContentData(carrier.getuser());
          com.storefront.shippingrequest.Password pw = new com.storefront.
              shippingrequest.Password();
          pw.setContentData(carrier.getpassword());
          access.setAccessLicenseNumber(alm);
          access.setUserId(userid);
          access.setPassword(pw);

          it = packages.values().iterator();
          while (it.hasNext()) {
            ShippingPackage shippingPackage = (ShippingPackage) it.next();
            RatingServiceSelectionRequest ratingServiceSelectionRequest = new
                RatingServiceSelectionRequest();

            com.storefront.shippingrequest.XpciVersion xpciVersion = new com.
                storefront.shippingrequest.XpciVersion();
            xpciVersion.setContentData(carrier.getversion());
            com.storefront.shippingrequest.CustomerContext customerContext = new
                com.storefront.shippingrequest.CustomerContext();
            customerContext.setContentData("Rating and Service");

            com.storefront.shippingrequest.TransactionReference
                transactionReference = new com.storefront.shippingrequest.
                TransactionReference();
            transactionReference.setXpciVersion(xpciVersion);
            transactionReference.setCustomerContext(customerContext);

            com.storefront.shippingrequest.RequestAction requestAction = new
                com.storefront.shippingrequest.RequestAction();
            requestAction.setContentData("Rate");
            com.storefront.shippingrequest.RequestOption requestOption = new
                com.storefront.shippingrequest.RequestOption();
            requestOption.setContentData("Rate");

            com.storefront.shippingrequest.Request request = new com.storefront.
                shippingrequest.Request();
            request.setTransactionReference(transactionReference);
            request.setRequestAction(requestAction);
            request.setRequestOption(requestOption);

            com.storefront.shippingrequest.Code code = new com.storefront.
                shippingrequest.Code();
            code.setContentData(carrier.getpickuptype());
            com.storefront.shippingrequest.PickupType pickupType = new com.
                storefront.shippingrequest.PickupType();
            pickupType.setCode(code);

            com.storefront.shippingrequest.PostalCode postalCode = new com.
                storefront.shippingrequest.PostalCode();
            postalCode.setContentData(shippingPackage.zip);
            com.storefront.shippingrequest.Address address = new com.storefront.
                shippingrequest.Address();
            com.storefront.shippingrequest.CountryCode countrycode = new com.
                storefront.shippingrequest.CountryCode();
            countrycode.setContentData(shippingPackage.country);
            address.setPostalCode(postalCode);
            address.setCountryCode(countrycode);
            com.storefront.shippingrequest.Shipper shipper = new com.storefront.
                shippingrequest.Shipper();
            shipper.setAddress(address);

            com.storefront.shippingrequest.City cityTo = new com.
                storefront.shippingrequest.City();
            cityTo.setContentData(salesorder.getshippingaddress().getcity());
            com.storefront.shippingrequest.CountryCode countrycodeTo = new com.
                storefront.shippingrequest.CountryCode();
            countrycodeTo.setContentData(salesorder.getshippingaddress().getcountry());
            com.storefront.shippingrequest.PostalCode postalCodeTo = new com.
                storefront.shippingrequest.PostalCode();
            postalCodeTo.setContentData(salesorder.getshippingaddress().getzip());
            com.storefront.shippingrequest.Address addressTo = new com.
                storefront.
                shippingrequest.Address();
            addressTo.setCountryCode(countrycodeTo);
            addressTo.setPostalCode(postalCodeTo);
            ShipTo shipTo = new ShipTo();
            shipTo.setAddress(addressTo);

            com.storefront.shippingrequest.Code codeTo = new com.storefront.
                shippingrequest.Code();
            codeTo.setContentData(salesorder.getshippingmethod().getcode());
            com.storefront.shippingrequest.Service service = new com.storefront.
                shippingrequest.Service();
            service.setCode(codeTo);

            com.storefront.shippingrequest.Code packCode = new com.storefront.
                shippingrequest.Code();
            packCode.setContentData(strpackage);
            com.storefront.shippingrequest.Description description = new com.
                storefront.shippingrequest.Description();
            description.setContentData("Package");

            com.storefront.shippingrequest.PackagingType packagingType = new
                com.storefront.shippingrequest.PackagingType();
            packagingType.setCode(packCode);
            packagingType.setDescription(description);

            com.storefront.shippingrequest.Description rateDescript = new com.
                storefront.shippingrequest.Description();
            rateDescript.setContentData("Rate Shopping");
            com.storefront.shippingrequest.Shipment shipment = new com.
                storefront.shippingrequest.Shipment();
            shipment.setShipper(shipper);
            shipment.setShipTo(shipTo);
            shipment.setService(service);

            com.storefront.shippingrequest.Weight weight = new com.storefront.
                shippingrequest.Weight();
            weight.setContentData(Double.toString(shippingPackage.weight));
            com.storefront.shippingrequest.Code umcode = new com.storefront.
                shippingrequest.Code();
            umcode.setContentData("LBS");
            com.storefront.shippingrequest.UnitOfMeasurement uom = new com.
                storefront.shippingrequest.UnitOfMeasurement();
            com.storefront.shippingrequest.PackageWeight packageWeight = new
                com.storefront.shippingrequest.PackageWeight();
            packageWeight.setWeight(weight);
            packageWeight.setUnitOfMeasurement(uom);
            com.storefront.shippingrequest.Package shipPackage = new com.
                storefront.
                shippingrequest.Package();
            shipPackage.setPackagingType(packagingType);
            shipPackage.setDescription(rateDescript);
            shipPackage.setPackageWeight(packageWeight);
            com.storefront.shippingrequest.PackageServiceOptions packageServiceOptions = new
                com.storefront.shippingrequest.PackageServiceOptions();
            com.storefront.shippingrequest.InsuredValue insuredValue
                = new com.storefront.shippingrequest.InsuredValue();
            com.storefront.shippingrequest.CurrencyCode currencyCode
                = new com.storefront.shippingrequest.CurrencyCode();
            insuredValue.setCurrencyCode(currencyCode);
            currencyCode.setContentData("USD");
            com.storefront.shippingrequest.MonetaryValue monetaryValue
                = new com.storefront.shippingrequest.MonetaryValue();
            monetaryValue.setContentData(Double.toString(shippingPackage.value));
            insuredValue.setMonetaryValue(monetaryValue);
            packageServiceOptions.setInsuredValue(insuredValue);
            shipPackage.setPackageServiceOptions(packageServiceOptions);
            shipment.setPackage(shipPackage);

            ratingServiceSelectionRequest.setRequest(request);
            ratingServiceSelectionRequest.setPickupType(pickupType);
            ratingServiceSelectionRequest.setShipment(shipment);

            IXMLInputSerializer inserial = XMLSerializerFactory.
                getInputSerializer();
            IXMLOutputSerializer outserial = XMLSerializerFactory.
                getOutputSerializer();
            XMLTransmitter transmitter = new XMLTransmitter();

            String req1 = outserial.get(access);
            outserial = XMLSerializerFactory.getOutputSerializer();
            String req2 = outserial.get(ratingServiceSelectionRequest);
            transmitter.setUrl(carrier.geturl());
            String ret = transmitter.doTransaction(req1 + req2);

            inserial.setPackage("com.storefront.shippingresponse");

            RatingServiceSelectionResponse response = (
                RatingServiceSelectionResponse) inserial.get(ret);
            if (response.getResponse().getResponseStatusCode().getContentData().
                equalsIgnoreCase("0")) {
              it = response.getResponse().getErrorIterator();
              if (it.hasNext()) {
                throw new Exception("Invalid Shipping information");
              }
            }
            else {
              it = response.getRatedShipmentIterator();
              while (it.hasNext()) {
                RatedShipmentType ratedShipment = (RatedShipmentType) it.next();
                totalcharges +=
                    Double.parseDouble(ratedShipment.getTotalCharges().
                                       getMonetaryValue().getContentData());
              }
            }
          }
        }
        else if (carrier.getcode().equalsIgnoreCase("USPS")) {

        // http://production.shippingapis.com/ShippingAPI.dll?API=IntlRate&XML=<IntlRateRequest USERID="237IDATA0575" PASSWORD="434XM12EF043"><Package ID="0"><Pounds>0</Pounds><Ounces>1</Ounces><MailType>Package</MailType><Country>Great Britain and Northern Ireland</Country></Package></IntlRateRequest>
          String prefix = "&API=IntlRate&XML=<IntlRateRequest USERID=\"" + carrier.getuser() +
              "\" PASSWORD=\"" + carrier.getpassword() +"\">";
          it = packages.values().iterator();
          String request = new String("");
          while (it.hasNext()) {
            ShippingPackage shippingPackage = (ShippingPackage) it.next();
            request += "<Package ID=\"0\"><Pounds>";
            if(shippingPackage.weight<2)
              shippingPackage.weight = 2;

            request += Integer.toString((int)shippingPackage.weight);
            request += "</Pounds><Ounces>0</Ounces><MailType>Package</MailType><Country>";
            request += ListsBean.GetCountryPostalCode(conn, salesorder.getshippingaddress().getcountry());
            request += "</Country></Package></IntlRateRequest>";
          }

          IXMLInputSerializer inserial = XMLSerializerFactory.
              getInputSerializer();
          XMLTransmitter transmitter = new XMLTransmitter();

          transmitter.setUrl(carrier.geturl());
          String ret = transmitter.doTransaction(prefix+request);
          inserial.setPackage("com.storefront.webuspsrate");
          IntlRateResponse response = (IntlRateResponse)inserial.get(ret);
          Iterator it2 = response.getPackageIterator();
          double postage = 0;
          while(it2.hasNext()) {
            com.storefront.webuspsrate.Package packageresp =
                (com.storefront.webuspsrate.Package)it2.next();
            Iterator it3 = packageresp.getServiceIterator();
            while(it3.hasNext()) {
              com.storefront.webuspsrate.Service service =
                  (com.storefront.webuspsrate.Service)it3.next();

              if(service.getSvcDescription().getContentData().equals(salesorder.getshippingmethod().getcode())) {
                double d = Double.parseDouble(service.getPostage().getContentData());
                if(postage==0||postage>d)
                  postage = d;
              }
            }
          }
          totalcharges += postage;
        }
      }

      salesorder.setshipping(totalcharges);
      salesorder.sethandling(handlingfee);
      salesorder.setshippingweight(totalweight);

      double newdiscounts=0;
      if (shippingMethod.getfreeshippingamount() > 0 &&
          shippingMethod.getfreeshippingamount() <= salesorder.gettotalcost()) {
        newdiscounts = salesorder.gethandling() + salesorder.getshipping();
        salesorder.setdiscount(newdiscounts);
        salesorder.setdiscountdescription("Free Shipping");
      }
      salesorder.settotal((salesorder.gettotal()+totalcharges+handlingfee)-newdiscounts);

    }
    catch (Exception e) {
      salesorder.setshipping(totalcharges);
    }
    finally {
      try {
        if (conn != null) conn.close();
        conn = null;
      }
      catch (SQLException sqle) {}
    }

  }

  private void sendOrderShippedMail(Packingslip packingslip, Connection conn) throws
      ServletException {

    MailConfiguration mailconfig = new MailConfiguration();
    PreparedStatement mailpstmt = null;
    ResultSet mailrs = null;

    try {
      mailpstmt = conn.prepareStatement(
          "select mailhost, mailfromname, mailfromaddress, mailauthuser, mailauthpassword, " +
          "url, mailsubject from mailconfig where id = 'shipped'");
      mailrs = mailpstmt.executeQuery();
      if (mailrs.next()) {
        mailconfig.setMailHost(mailrs.getString(1));
        mailconfig.setMailFromName(mailrs.getString(2));
        mailconfig.setMailFromAddress(mailrs.getString(3));
        mailconfig.setMailAuthUser(mailrs.getString(4));
        mailconfig.setMailAuthPassword(mailrs.getString(5));
        mailconfig.setMailURL(mailrs.getString(6));
        mailconfig.setMailSubject(mailrs.getString(7));

        Context initial = new InitialContext();
        Session session = (Session) initial.lookup(
            "java:comp/env/storefrontmail");

        CustomerBean customerBean = new CustomerBean();
        Customer customer = customerBean.GetCustomer(conn, packingslip.getcustomerid());
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(mailconfig.getMailFromAddress()));
        InternetAddress dests[] = new InternetAddress[] {
            new InternetAddress(customer.getemail1())};
        msg.setRecipients(Message.RecipientType.TO, dests);

        msg.setSentDate(new java.util.Date());
        String subject = mailconfig.getMailSubject();
        subject += " Packing Slip # " + Integer.toString(packingslip.getid());
        msg.setSubject(subject);

        XMLTransmitter transmitter = new XMLTransmitter();
        transmitter.setUrl(mailconfig.getMailURL() +
                           Integer.toString(packingslip.getid()));
        String body = transmitter.doTransaction("");
        msg.setContent(body, "text/html");

        Transport transport = session.getTransport("smtp");
        transport.connect(mailconfig.getMailHost(), mailconfig.getMailAuthUser(),
                          mailconfig.getMailAuthPassword());
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();

        EMailRecord emrequest = new EMailRecord();
        emrequest.setreferenceno(packingslip.getid());
        emrequest.settype("Order Shipped");
        emrequest.setrecipientid(customer.getid());
        emrequest.setrecipient(customer.getemail1());
        emrequest.setsubject(subject);
        emrequest.setbody(body);
        new EMailRecordBean().addEMailRecord(conn, emrequest);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try {
        if (mailrs != null) mailrs.close();
        mailrs = null;
      }
      catch (SQLException sqle) {}
      try {
        if (mailpstmt != null) mailpstmt.close();
        mailpstmt = null;
      }
      catch (SQLException sqle) {}
    }
  }

  private void sendDeclindedCreditCardMail(SalesOrder salesorder, Connection conn) throws
      ServletException {

    MailConfiguration mailconfig = new MailConfiguration();
    PreparedStatement mailpstmt = null;
    ResultSet mailrs = null;
    try {
      mailpstmt = conn.prepareStatement("select mailhost, mailfromname, mailfromaddress, " +
                                    "mailauthuser, mailauthpassword, url, mailsubject " +
                                    "from mailconfig where id = 'declinedcreditcard'");
      mailrs = mailpstmt.executeQuery();
      if (mailrs.next()) {
        mailconfig.setMailHost(mailrs.getString(1));
        mailconfig.setMailFromName(mailrs.getString(2));
        mailconfig.setMailFromAddress(mailrs.getString(3));
        mailconfig.setMailAuthUser(mailrs.getString(4));
        mailconfig.setMailAuthPassword(mailrs.getString(5));
        mailconfig.setMailURL(mailrs.getString(6));
        mailconfig.setMailSubject(mailrs.getString(7));

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
        transmitter.setUrl(mailconfig.getMailURL()+Integer.toString(salesorder.getid()));
        String body = transmitter.doTransaction("");
        msg.setContent(body, "text/html");

        Transport transport = session.getTransport("smtp");
        transport.connect(mailconfig.getMailHost(), mailconfig.getMailAuthUser(),
                          mailconfig.getMailAuthPassword());
        msg.saveChanges();
                      transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();

        EMailRecord emrequest = new EMailRecord();
        emrequest.setreferenceno(salesorder.getid());
        emrequest.settype("Declined Credit Card");
        emrequest.setrecipientid(salesorder.getcustomer().getid());
        emrequest.setrecipient(salesorder.getcustomer().getemail1());
        emrequest.setsubject(subject);
        emrequest.setbody(body);
        new EMailRecordBean().addEMailRecord(conn, emrequest);
      }
    }
    catch(javax.mail.MessagingException me) {
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
        if (mailrs != null) mailrs.close();
        mailrs = null;
      }
      catch (SQLException sqle) {}
      try {
        if (mailpstmt != null) mailpstmt.close();
        mailpstmt = null;
      }
      catch (SQLException sqle) {}
    }
  }

}
