package com.storefront.storefrontbeans;

import javax.servlet.*;
import java.sql.*;
import java.util.*;
import com.storefront.storefrontrepository.*;

public class ItemBean
    extends BaseBean {
  final static private String fields[] = {
      "catalog", "isin", "productName", "manufacturer", "distributor",
      "quantity", "allocated", "sold", "quantitytoorder", "backordered",
      "minimumonhand","reorderquantity", "quantityonorder", "status",
      "listPrice", "ourPrice", "ourCost"};
  final static private String select =
      "select a.id, a.catalog, isin, productname, manufacturer, b.name, " +
      "distributor, c.name, quantity, allocated, sold, quantitytoorder, backordered, minimumonhand, " +
      "reorderquantity, quantityonorder, a.status, d.status, d.availability, listPrice, ourPrice, " +
      "ourcost from item a, manufacturer b, distributor c, itemstatus d " +
      "where b.id=manufacturer and c.id=distributor and d.id=a.status";
  final static private String byid = " and a.id = ?";
  final static private String orderbyid = " order by a.id";
  final static private String tablename = "Item";

  final static private String getnext = "select id from item where id > ? order by id asc";
  final static private String getprev = "select id from item where id < ? order by id desc";
  final static private String getnextformanufacturer = "select id from item where id > ? and manufacturer =  ? order by id asc";
  final static private String getprevformanufacturer = "select id from item where id < ? and manufacturer =  ? order by id desc";

  private DetailsBean detailsBean = new DetailsBean();
  private KeyWordsBean keyWordsBean = new KeyWordsBean();
  private ItemRankingBean itemRankingBean = new ItemRankingBean();
  private RecentlyViewedBean recentlyViewedBean = new RecentlyViewedBean();

  public ItemBean() throws ServletException {
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

  public AddItemResponse AddItem(AddItemRequest request) throws
      ServletException {
    int id = 0;
    AddItemResponse response = new AddItemResponse();
    Connection conn = null;
    try {
      Item item = request.getitem();
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getInsertString());
      int col=0;
      pstmt.setInt(++col, item.getcatalog());
      pstmt.setString(++col, item.getisin());
      pstmt.setString(++col, item.getproductname());
      pstmt.setInt(++col, item.getmanufacturerid());
      pstmt.setInt(++col, item.getdistributorid());
      pstmt.setInt(++col, item.getquantity());
      pstmt.setInt(++col, item.getallocated());
      pstmt.setInt(++col, item.getsold());
      pstmt.setInt(++col, item.getquantitytoorder());
      pstmt.setInt(++col, item.getbackordered());
      pstmt.setInt(++col, item.getminimumonhand());
      pstmt.setInt(++col, item.getreorderquantity());
      pstmt.setInt(++col, item.getquantityonorder());
      pstmt.setInt(++col, item.getstatusid());
      pstmt.setDouble(++col, item.getlistprice());
      pstmt.setDouble(++col, item.getourprice());
      pstmt.setDouble(++col, item.getourcost());
      if ( (pstmt.executeUpdate()) > 0) {
        id = getLastInsertID(conn);
        detailsBean.AddDetail(conn, id, item.getdetails());
        keyWordsBean.AddKeyWords(conn, id, item);
        itemRankingBean.AddItemRanking(conn, id);
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

  public UpdateItemResponse UpdateItem(UpdateItemRequest request) throws
      ServletException {
    UpdateItemResponse response = new UpdateItemResponse();
    Connection conn = null;
    try {
      Item item = request.getitem();
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getUpdateString());

      int col=0;
      pstmt.setInt(++col, item.getcatalog());
      pstmt.setString(++col, item.getisin());
      pstmt.setString(++col, item.getproductname());
      pstmt.setInt(++col, item.getmanufacturerid());
      pstmt.setInt(++col, item.getdistributorid());
      pstmt.setInt(++col, item.getquantity());
      pstmt.setInt(++col, item.getallocated());
      pstmt.setInt(++col, item.getsold());
      pstmt.setInt(++col, item.getquantitytoorder());
      pstmt.setInt(++col, item.getbackordered());
      pstmt.setInt(++col, item.getminimumonhand());
      pstmt.setInt(++col, item.getreorderquantity());
      pstmt.setInt(++col, item.getquantityonorder());
      pstmt.setInt(++col, item.getstatusid());
      pstmt.setDouble(++col, item.getlistprice());
      pstmt.setDouble(++col, item.getourprice());
      pstmt.setDouble(++col, item.getourcost());
      pstmt.setInt(++col, item.getid());
      if ( (pstmt.executeUpdate()) > 0) {
        detailsBean.UpdateDetail(conn, item.getid(), item.getdetails());
        keyWordsBean.UpdateKeyWords(conn, item.getid(), item);
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

  public DeleteItemResponse DeleteItem(DeleteItemRequest request) throws
      ServletException {
    DeleteItemResponse response = new DeleteItemResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("delete from item where id = ?");
      pstmt.setInt(1, request.getid());
      pstmt.executeUpdate();
      detailsBean.DeleteDetail(conn, request.getid());
      keyWordsBean.DeleteKeyWords(conn, request.getid());
      itemRankingBean.DeleteItemRanking(conn, request.getid());
      SimilarProductsBean.DeleteSimilarProduct(conn, request.getid());
      RelatedProductsBean.DeleteRelatedProduct(conn, request.getid());
      FeaturedProductsBean.deleteFeaturedProducts(conn, request.getid());
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

  public GetItemResponse GetItem(GetItemRequest request, User user) throws
      ServletException {
    GetItemResponse response = new GetItemResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      response.setitem(GetItem(conn, request.getid()));
      RecentlyViewed recentlyviewed = new RecentlyViewed();
      recentlyviewed.setitemNumber(response.getitem().getid());
      recentlyviewed.setuserid(user.getid());
      recentlyViewedBean.AddRecentlyViewed(conn, recentlyviewed);
      itemRankingBean.UpdateItemViewed(conn, request.getid());
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

  public GetItemResponse GetItem(GetItemRequest request) throws
      ServletException {
    GetItemResponse response = new GetItemResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      response.setitem(GetItem(conn, request.getid()));
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

  public GetItemsResponse GetItems(GetItemsRequest request) throws
      ServletException {
    GetItemsResponse response = new GetItemsResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      String query = select;

      if(request.getcatalog()!=null)
        query += " and " + request.getcatalog();
      if(request.getselectby()!=null) {
        query += " and " + request.getselectby() + " = ";
        if(request.getvalue()!=null)
            query += request.getvalue();
          else
            query += Integer.toString(request.getid());
      }

      if (request.getorderby() != null) {
        query += " order by " + request.getorderby();
        if(request.getdirection()!=null)
            query += " " + request.getdirection();
      }
      else {
        query += orderbyid;
      }

      response = getItems(conn, query);
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

  public GetItemsResponse GetItemsToOrder(GetItemsRequest request) throws
      ServletException {
    GetItemsResponse response = new GetItemsResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      String query = select;

      if(request.getcatalog()!=null)
        query += " and " + request.getcatalog();
      if(request.getselectby()!=null) {
        query += " and " + request.getselectby() + " = ";
        if(request.getvalue()!=null)
            query += request.getvalue();
          else
            query += Integer.toString(request.getid());
      }

      query += " and quantitytoorder > 0 ";
      if (request.getorderby() != null) {
        query += " order by " + request.getorderby();
        if(request.getdirection()!=null)
            query += " " + request.getdirection();
      }
      else {
        query += orderbyid;
      }
      response = getItems(conn, query);
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

  GetItemsResponse getItems(Connection conn, String query) throws
      ServletException {
    GetItemsResponse response = new GetItemsResponse();
    try {
      pstmt = conn.prepareStatement(query);
      rs = pstmt.executeQuery();
      Item item = null;

      while (rs.next()) {
        int col=0;
        item = new Item();
        item.setid(rs.getInt(++col));
        item.setcatalog(rs.getInt(++col));
        item.setisin(rs.getString(++col));
        item.setproductname(rs.getString(++col));
        item.setmanufacturerid(rs.getInt(++col));
        item.setmanufacturer(rs.getString(++col));
        item.setdistributorid(rs.getInt(++col));
        item.setdistributor(rs.getString(++col));
        item.setquantity(rs.getInt(++col));
        item.setallocated(rs.getInt(++col));
        item.setsold(rs.getInt(++col));
        item.setquantitytoorder(rs.getInt(++col));
        item.setbackordered(rs.getInt(++col));
        item.setminimumonhand(rs.getInt(++col));
        item.setreorderquantity(rs.getInt(++col));
        item.setquantityonorder(rs.getInt(++col));
        item.setstatusid(rs.getInt(++col));
        item.setstatus(rs.getString(++col));
        item.setavailability(rs.getString(++col));
        item.setlistprice(rs.getDouble(++col));
        item.setourprice(rs.getDouble(++col));
        item.setourcost(rs.getDouble(++col));
        detailsBean.GetDetail(conn, item);
        response.setitems(item);
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

  Item GetItem(Connection conn, int id) throws ServletException {

    Item item = null;
    try {
      item = new Item();
      String query = select;
      query += byid;
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, id);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        int col=0;
        item.setid(rs.getInt(++col));
        item.setcatalog(rs.getInt(++col));
        item.setisin(rs.getString(++col));
        item.setproductname(rs.getString(++col));
        item.setmanufacturerid(rs.getInt(++col));
        item.setmanufacturer(rs.getString(++col));
        item.setdistributorid(rs.getInt(++col));
        item.setdistributor(rs.getString(++col));
        item.setquantity(rs.getInt(++col));
        item.setallocated(rs.getInt(++col));
        item.setsold(rs.getInt(++col));
        item.setquantitytoorder(rs.getInt(++col));
        item.setbackordered(rs.getInt(++col));
        item.setminimumonhand(rs.getInt(++col));
        item.setreorderquantity(rs.getInt(++col));
        item.setquantityonorder(rs.getInt(++col));
        item.setstatusid(rs.getInt(++col));
        item.setstatus(rs.getString(++col));
        item.setavailability(rs.getString(++col));
        item.setlistprice(rs.getDouble(++col));
        item.setourprice(rs.getDouble(++col));
        item.setourcost(rs.getDouble(++col));
        detailsBean.GetDetail(conn, item);
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
    return item;
  }

  public int GetNext(int id) throws ServletException {
    return GetNext(id, 0);
  }


  public int GetNext(int id, int manufactureried) throws ServletException {

    Connection conn = null;
    int next = 0;
    try {
      conn = datasource.getConnection();
      if(manufactureried>0) {
        pstmt = conn.prepareStatement(getnextformanufacturer);
        pstmt.setInt(1, id);
        pstmt.setInt(2, manufactureried);
      }
      else {
        pstmt = conn.prepareStatement(getnext);
        pstmt.setInt(1, id);
      }
      rs = pstmt.executeQuery();
      if (rs.next()) {
        next = rs.getInt(1);
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
    return next;
  }

  public int GetPrev(int id) throws ServletException {
    return GetPrev(id, 0);
  }

  public int GetPrev(int id, int manufactureried) throws ServletException {

    Connection conn = null;
    int prev = 0;
    try {
      conn = datasource.getConnection();
      if(manufactureried>0) {
        pstmt = conn.prepareStatement(getprevformanufacturer);
        pstmt.setInt(1, id);
        pstmt.setInt(2, manufactureried);
      }
      else {
        pstmt = conn.prepareStatement(getprev);
        pstmt.setInt(1, id);
      }
      rs = pstmt.executeQuery();
      if (rs.next()) {
        prev = rs.getInt(1);
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
    return prev;
  }

  public OrderItemsResponse OrderItems(OrderItemsRequest request) throws ServletException {

    OrderItemsResponse response = new OrderItemsResponse();
    Connection conn = null;
    PreparedStatement pstmt = null;
    try {
      conn = datasource.getConnection();
      Iterator it = request.getitemsIterator();
      while(it.hasNext()) {
        OrderItemsItem item = (OrderItemsItem)it.next();
        OrderItem(conn, item.getid(), item.getqty());
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
    return response;
  }

  void AllocateItem(Connection conn, int id, int qty) throws ServletException {

    try {
      Item item = GetItem(conn, id);

      int onhand = item.getquantity();
      int allocated = item.getallocated();
      int quantitytoorder = item.getquantitytoorder();
      int minimumonhand = item.getminimumonhand();
      int reorderquantity = item.getreorderquantity();
      int quantityonorder = item.getquantityonorder();

      //Reduce current quantity
      onhand -= qty;
      allocated += qty;
      if (onhand < 0)
        onhand = 0;

      //Check to see if we need to place an order
      if(onhand<minimumonhand) {
        //calculate how many we have and have ordered or are going to order
        // minus the amount already allocated.
        int count = (onhand + quantityonorder + quantitytoorder) - allocated;
        //Now lets see how many we need to order.
        int needed = count - minimumonhand;
        needed = (needed > reorderquantity) ? needed : reorderquantity;
        quantitytoorder += needed;
      }

      item.setquantity(onhand);
      item.setallocated(allocated);
      item.setquantitytoorder(quantitytoorder);

      String update = "update item set quantity=?, allocated=?, " +
          "quantitytoorder=? where id = ?";
      pstmt = conn.prepareStatement(update);
      pstmt.setInt(1, item.getquantity());
      pstmt.setInt(2, item.getallocated());
      pstmt.setInt(3, item.getquantitytoorder());
      pstmt.setInt(4, item.getid());
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

  void UnAllocateItem(Connection conn, int id, int qty) throws ServletException {

    try {
      String update = "update item set allocated=allocated-?, " +
          "quantitytoorder=quantitytoorder-? where id = ?";
      pstmt = conn.prepareStatement(update);
      pstmt.setInt(1, qty);
      pstmt.setInt(2, qty);
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

  static public void OrderItem(Connection conn, int id, int qty) throws ServletException {

    PreparedStatement pstmt = null;
    try {
      String update = "update item set quantitytoorder=quantitytoorder-?, " +
          "quantityonorder=quantityonorder+? where id = ?";
      pstmt = conn.prepareStatement(update);
      pstmt.setInt(1, qty);
      pstmt.setInt(2, qty);
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

  static public void RecieveItem(Connection conn, int id, int qty) throws ServletException {
    PreparedStatement pstmt = null;

    try {
      String update = "update item set quantity=quantity+?, " +
          "quantityonorder=quantityonorder-? where id = ?";
      pstmt = conn.prepareStatement(update);
      pstmt.setInt(1, qty);
      pstmt.setInt(2, qty);
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

  static void ShipItem(Connection conn, int id, int qty) throws ServletException {

    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      String update = "update item set quantity=quantity-?, allocated=allocated-?, sold=sold+? where id=?";
      pstmt = conn.prepareStatement(update);
      pstmt.setInt(1, qty);
      pstmt.setInt(2, qty);
      pstmt.setInt(3, qty);
      pstmt.setInt(4, id);
      pstmt.executeUpdate();
      pstmt.close();
      String getqty = "select quantity from item where id=?";
      pstmt = conn.prepareStatement(getqty);
      pstmt.setInt(1,id);
      rs = pstmt.executeQuery();
      if(rs.next()) {
        qty = rs.getInt(1);
        if(qty<1) {
          pstmt.close();
          String updatestatus = "update item set status=2 where id=?";
          pstmt = conn.prepareStatement(updatestatus);
          pstmt.setInt(1,id);
          pstmt.executeUpdate();
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
        if (pstmt != null) pstmt.close();
        pstmt = null;
      }
      catch (SQLException sqle) {}
    }
  }

  public int PriceUpdate() throws ServletException {

    int count = 0;
    try {
      ListsBean listsBean = new ListsBean();
      GetManufacturersRequest request = new GetManufacturersRequest();
      GetManufacturersResponse response = listsBean.GetManufacturers(request);
      Iterator it = response.getmanufacturersIterator();

      while (it.hasNext()) {
        Manufacturer manufacturer = (Manufacturer)it.next();
        count += PriceUpdate(manufacturer);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    return count;
  }

  public int PriceUpdate(int manufacturerid) throws ServletException {
    ListsBean listsBean = new ListsBean();
    GetManufacturerRequest request = new GetManufacturerRequest();
    request.setid(manufacturerid);
    GetManufacturerResponse response = listsBean.GetManufacturer(request);
    return PriceUpdate(response.getmanufacturer());
  }

  public int PriceUpdate(Manufacturer manufacturer) throws ServletException {

    Connection conn = null;
    PreparedStatement pstmtget = null;
    PreparedStatement pstmtset = null;
    int count =0;
    try {
      double markup = manufacturer.getmarkup();
      if (markup > 0) {
        conn = datasource.getConnection();
        pstmtget = conn.prepareStatement(
            "select id, ourcost, ourprice, listprice from item where manufacturer = ? ");
        pstmtset = conn.prepareStatement(
            "update item set ourprice = ? where id = ? ");

        pstmtget.setInt(1, manufacturer.getid());
        rs = pstmtget.executeQuery();

        while (rs.next()) {
          int id = rs.getInt(1);
          double currentcost = rs.getDouble(2);
          double currentprice = rs.getDouble(3);
          double listprice = rs.getDouble(4);
          double ourprice = CalculateOurPrice(markup, currentcost, currentprice,
                                              listprice);
          if (ourprice > 0) {
            pstmtset.setDouble(1, ourprice);
            pstmtset.setInt(2, id);
            count += pstmtset.executeUpdate();

          }
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
        if (pstmtset != null) pstmtset.close();
        pstmtset = null;
      }
      catch (SQLException sqle) {}
      try {
        if (pstmtget != null) pstmtget.close();
        pstmtget = null;
      }
      catch (SQLException sqle) {}
      try {
       if (conn != null) conn.close();
       conn = null;
     }
     catch (SQLException sqle) {}
   }
    return count;
  }

  public double CalculateOurPrice(double markup, double currentcost,
                                  double currentprice, double listprice) {

    double ourprice = currentcost * (1.0 + markup);
    int dollars = (int) ourprice;
    double fortynine = (double) dollars + 0.49;
    double ninetynine = (double) dollars + 0.99;
    if (fortynine > ourprice)
      ourprice = fortynine;
    else
      ourprice = ninetynine;

    if (ourprice != currentprice) {
      if (ourprice < currentcost)
        ourprice = currentcost;
      if (ourprice > listprice)
        ourprice = listprice;
    }
    else
      ourprice=0;

    return ourprice;
  }

}
