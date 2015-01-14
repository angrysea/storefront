package com.storefront.storefrontbeans;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class ListsBean extends BaseBean
{
  private final static String shippingselect = "select id, carrier, code, country, fixedprice, " +
      "freeshippingamount, description, notes from shippingmethods ";
  private final static String shippingcountry = "where country = ? or country = 'ALL' ";
  private final static String shippingus = "where country = ? ";
  private final static String orderby = "order by id";

  public ListsBean() throws ServletException {
    super();
  }

  String[] getfields() {
    return null;
  }

  String gettableName() {
    return null;
  }

  String getindexName() {
    return null;
  }

  public AddManufacturerResponse AddManufacturer(AddManufacturerRequest request) throws ServletException
  {
    AddManufacturerResponse response = new AddManufacturerResponse();
    Connection conn = null;
    try
    {
      Manufacturer manufacturer = request.getmanufacturer();
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("insert into manufacturer (name, longname, prefix, markup, active, url, logo, description, shortdescription) values (?, ?, ?, ?, ?, ?, ?, ?)");
      int col=0;
      pstmt.setString(++col, manufacturer.getname());
      pstmt.setString(++col, manufacturer.getlongname());
      pstmt.setString(++col, manufacturer.getprefix());
      pstmt.setDouble(++col, manufacturer.getmarkup());
      pstmt.setBoolean(++col, manufacturer.getactive());
      pstmt.setString(++col, manufacturer.geturl());
      pstmt.setString(++col, manufacturer.getlogo());
      pstmt.setString(++col, manufacturer.getdescription());
      pstmt.setString(++col, manufacturer.getshortdescription());
      if((pstmt.executeUpdate())>0)
      {
        response.setid(getLastInsertID(conn));
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try { if(pstmt!=null) pstmt.close(); } catch(SQLException sqle ){}
      try { if(conn!=null) conn.close(); } catch(SQLException sqle ){}
    }
    return response;
  }

  public UpdateManufacturerResponse UpdateManufacturer(UpdateManufacturerRequest request) throws ServletException
  {
    UpdateManufacturerResponse response = new UpdateManufacturerResponse();
    Connection conn = null;
    try
    {
      Manufacturer manufacturer = request.getmanufacturer();
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("update manufacturer set name=?, longname=?, prefix=?, markup=?, active=?, url=?, logo=?, description=?, shortdescription=? where id=?");
      int col=0;
      pstmt.setString(++col, manufacturer.getname());
      pstmt.setString(++col, manufacturer.getlongname());
      pstmt.setString(++col, manufacturer.getprefix());
      pstmt.setDouble(++col, manufacturer.getmarkup());
      pstmt.setBoolean(++col, manufacturer.getactive());
      pstmt.setString(++col, manufacturer.geturl());
      pstmt.setString(++col, manufacturer.getlogo());
      pstmt.setString(++col, manufacturer.getdescription());
      pstmt.setString(++col, manufacturer.getshortdescription());
      pstmt.setInt(++col, manufacturer.getid());
      pstmt.executeUpdate();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try { if(pstmt!=null) pstmt.close(); pstmt=null; } catch(SQLException sqle ){}
      try { if(conn!=null) conn.close(); conn=null; } catch(SQLException sqle ){}
    }
    return response;
  }

  public GetManufacturersResponse GetManufacturers(GetManufacturersRequest
      request) throws ServletException {
    GetManufacturersResponse response = new GetManufacturersResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      if(request.getactive()) {
        pstmt = conn.prepareStatement("select id, name, longname, prefix, markup, active, url, logo, description, shortdescription from manufacturer where active=1 order by name");
      }
      else {
        pstmt = conn.prepareStatement("select id, name, longname, prefix, markup, active, url, logo, description, shortdescription from manufacturer order by name");
      }
      rs = pstmt.executeQuery();
      Manufacturer manufacturer = null;
      while (rs.next()) {
        int col=0;
        manufacturer = new Manufacturer();
        manufacturer.setid(rs.getInt(++col));
        manufacturer.setname(rs.getString(++col));
        manufacturer.setlongname(rs.getString(++col));
        manufacturer.setprefix(rs.getString(++col));
        manufacturer.setmarkup(rs.getDouble(++col));
        manufacturer.setactive(rs.getBoolean(++col));
        manufacturer.seturl(rs.getString(++col));
        manufacturer.setlogo(rs.getString(++col));
        manufacturer.setdescription(rs.getString(++col));
        manufacturer.setshortdescription(rs.getString(++col));
        response.setmanufacturers(manufacturer);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try { if(rs!=null) rs.close(); rs=null;} catch(SQLException sqle ){}
      try { if(pstmt!=null) pstmt.close(); pstmt=null; } catch(SQLException sqle ){}
      try { if(conn!=null) conn.close(); conn=null; } catch(SQLException sqle ){}
    }
    return response;
  }

  public GetManufacturerResponse GetManufacturer(GetManufacturerRequest
      request) throws ServletException {
    GetManufacturerResponse response = new GetManufacturerResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("select id, name, longname, prefix, markup, active, url, logo, description, shortdescription from manufacturer where id = ?");
      pstmt.setInt(1,request.getid());
      rs = pstmt.executeQuery();
      Manufacturer manufacturer = null;
      if(rs.next()) {
        int col=0;
        manufacturer = new Manufacturer();
        manufacturer.setid(rs.getInt(++col));
        manufacturer.setname(rs.getString(++col));
        manufacturer.setlongname(rs.getString(++col));
        manufacturer.setprefix(rs.getString(++col));
        manufacturer.setmarkup(rs.getDouble(++col));
        manufacturer.setactive(rs.getBoolean(++col));
        manufacturer.seturl(rs.getString(++col));
        manufacturer.setlogo(rs.getString(++col));
        manufacturer.setdescription(rs.getString(++col));
        manufacturer.setshortdescription(rs.getString(++col));
        response.setmanufacturer(manufacturer);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try { if(rs!=null) rs.close(); rs=null;} catch(SQLException sqle ){}
      try { if(pstmt!=null) pstmt.close(); pstmt=null; } catch(SQLException sqle ){}
      try { if(conn!=null) conn.close(); conn=null; } catch(SQLException sqle ){}
    }
    return response;
  }

  public Manufacturer GetManufacturerByName(String request) throws ServletException {

    Manufacturer manufacturer = null;
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("select id, name, longname, prefix, markup, active, url, logo, description, shortdescription from manufacturer where name = ?");
      pstmt.setString(1,request);
      rs = pstmt.executeQuery();
      if(rs.next()) {
        int col=0;
        manufacturer = new Manufacturer();
        manufacturer.setid(rs.getInt(++col));
        manufacturer.setname(rs.getString(++col));
        manufacturer.setlongname(rs.getString(++col));
        manufacturer.setprefix(rs.getString(++col));
        manufacturer.setmarkup(rs.getDouble(++col));
        manufacturer.setactive(rs.getBoolean(++col));
        manufacturer.seturl(rs.getString(++col));
        manufacturer.setlogo(rs.getString(++col));
        manufacturer.setdescription(rs.getString(++col));
        manufacturer.setshortdescription(rs.getString(++col));
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try { if(rs!=null) rs.close(); rs=null;} catch(SQLException sqle ){}
      try { if(pstmt!=null) pstmt.close(); pstmt=null; } catch(SQLException sqle ){}
      try { if(conn!=null) conn.close(); conn=null; } catch(SQLException sqle ){}
    }
    return manufacturer;
  }

  public DeleteManufacturerResponse DeleteManufacturer(DeleteManufacturerRequest
      request) throws ServletException {
    DeleteManufacturerResponse response = new DeleteManufacturerResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("delete from manufacturer where id = ?");
      pstmt.setInt(1,request.getid());
      pstmt.executeUpdate();
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try { if(pstmt!=null) pstmt.close(); pstmt=null; } catch(SQLException sqle ){}
      try { if(conn!=null) conn.close(); conn=null; } catch(SQLException sqle ){}
    }
    return response;
  }

  public AddDistributorResponse AddDistributor(AddDistributorRequest request) throws ServletException
  {
    AddDistributorResponse response = new AddDistributorResponse();
    Connection conn = null;
    try
    {
      Distributor distributor = request.getdistributor();
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("insert into distributor (name, description, dropshipfee, " +
      "handlingfee, email, address1, address2, address3, city, state, zip, country, phone) " +
      "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
      int col=0;
      pstmt.setString(++col, distributor.getname());
      pstmt.setString(++col, distributor.getdescription());
      pstmt.setDouble(++col, distributor.getdropshipfee());
      pstmt.setDouble(++col, distributor.gethandlingfee());
      pstmt.setString(++col, distributor.getemail());
      pstmt.setString(++col, distributor.getaddress1());
      pstmt.setString(++col, distributor.getaddress2());
      pstmt.setString(++col, distributor.getaddress3());
      pstmt.setString(++col, distributor.getcity());
      pstmt.setString(++col, distributor.getstate());
      pstmt.setString(++col, distributor.getzip());
      pstmt.setString(++col, distributor.getcountry());
      pstmt.setString(++col, distributor.getphone());
      if((pstmt.executeUpdate())>0)
      {
        response.setid(getLastInsertID(conn));
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try { if(pstmt!=null) pstmt.close(); pstmt=null; } catch(SQLException sqle ){}
      try { if(conn!=null) conn.close(); conn=null; } catch(SQLException sqle ){}
    }
    return response;
  }

  public UpdateDistributorResponse UpdateDistributor(UpdateDistributorRequest request) throws ServletException
  {
    UpdateDistributorResponse response = new UpdateDistributorResponse();
    Connection conn = null;
    try
    {
      Distributor distributor = request.getdistributor();
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("update distributor set name=?, description=?, dropshipfee=?, handlingfee=?, " +
                                    "email=?, address1=?, address2=?, address3=?, city=?, state=?, zip=?, " +
                                    "country=?, phone=? where id = ?");

      int col=0;
      pstmt.setString(++col, distributor.getname());
      pstmt.setString(++col, distributor.getdescription());
      pstmt.setDouble(++col, distributor.getdropshipfee());
      pstmt.setDouble(++col, distributor.gethandlingfee());
      pstmt.setString(++col, distributor.getemail());
      pstmt.setString(++col, distributor.getaddress1());
      pstmt.setString(++col, distributor.getaddress2());
      pstmt.setString(++col, distributor.getaddress3());
      pstmt.setString(++col, distributor.getcity());
      pstmt.setString(++col, distributor.getstate());
      pstmt.setString(++col, distributor.getzip());
      pstmt.setString(++col, distributor.getcountry());
      pstmt.setString(++col, distributor.getphone());
      pstmt.setInt(++col, distributor.getid());
      pstmt.executeUpdate();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try { if(pstmt!=null) pstmt.close(); pstmt=null; } catch(SQLException sqle ){}
      try { if(conn!=null) conn.close(); conn=null; } catch(SQLException sqle ){}
    }
    return response;
  }

  public GetDistributorsResponse GetDistributors(GetDistributorsRequest
      request) throws ServletException {
    GetDistributorsResponse response = new GetDistributorsResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("select id, name, description, dropshipfee, handlingfee, " +
                                    "email, address1, address2, address3, city, state, zip, " +
                                    "country, phone from distributor order by name");
      rs = pstmt.executeQuery();
      Distributor distributor = null;
      while (rs.next()) {
        int col=0;
        distributor = new Distributor();
        distributor.setid(rs.getInt(++col));
        distributor.setname(rs.getString(++col));
        distributor.setdescription(rs.getString(++col));
        distributor.setdropshipfee(rs.getDouble(++col));
        distributor.sethandlingfee(rs.getDouble(++col));
        distributor.setemail(rs.getString(++col));
        distributor.setaddress1(rs.getString(++col));
        distributor.setaddress2(rs.getString(++col));
        distributor.setaddress3(rs.getString(++col));
        distributor.setcity(rs.getString(++col));
        distributor.setstate(rs.getString(++col));
        distributor.setzip(rs.getString(++col));
        distributor.setcountry(rs.getString(++col));
        distributor.setphone(rs.getString(++col));
        response.setdistributors(distributor);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try { if(rs!=null) rs.close(); rs=null;} catch(SQLException sqle ){}
      try { if(pstmt!=null) pstmt.close(); pstmt=null; } catch(SQLException sqle ){}
      try { if(conn!=null) conn.close(); conn=null; } catch(SQLException sqle ){}
    }
    return response;
  }

  public GetDistributorResponse GetDistributor(GetDistributorRequest
      request) throws ServletException {
    GetDistributorResponse response = new GetDistributorResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      response.setdistributor(getDistributor(conn, request.getid()));
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try { if(conn!=null) conn.close(); conn=null; } catch(SQLException sqle ){}
    }
    return response;
  }

  static Distributor getDistributor(Connection conn, int id) throws ServletException {
    Distributor distributor = null;

    PreparedStatement pstmt =null;
    ResultSet rs = null;
    try {
      pstmt = conn.prepareStatement("select id, name, description, dropshipfee, " +
                                    "handlingfee, email, address1, address2, " +
                                    "address3, city, state, zip, country, phone " +
                                    "from distributor where id = ?");
      pstmt.setInt(1,id);
      rs = pstmt.executeQuery();
      if(rs.next()) {
        int col=0;
        distributor = new Distributor();
        distributor.setid(rs.getInt(++col));
        distributor.setname(rs.getString(++col));
        distributor.setdescription(rs.getString(++col));
        distributor.setdropshipfee(rs.getDouble(++col));
        distributor.sethandlingfee(rs.getDouble(++col));
        distributor.setemail(rs.getString(++col));
        distributor.setaddress1(rs.getString(++col));
        distributor.setaddress2(rs.getString(++col));
        distributor.setaddress3(rs.getString(++col));
        distributor.setcity(rs.getString(++col));
        distributor.setstate(rs.getString(++col));
        distributor.setzip(rs.getString(++col));
        distributor.setcountry(rs.getString(++col));
        distributor.setphone(rs.getString(++col));
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try { if(rs!=null) rs.close(); rs=null;} catch(SQLException sqle ){}
      try { if(pstmt!=null) pstmt.close(); pstmt=null; } catch(SQLException sqle ){}
    }
    return distributor;
  }

  public DeleteDistributorResponse DeleteDistributor(DeleteDistributorRequest
      request) throws ServletException {
    DeleteDistributorResponse response = new DeleteDistributorResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("delete from distributor where id = ?");
      pstmt.setInt(1,request.getid());
      pstmt.executeUpdate();
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try { if(pstmt!=null) pstmt.close(); pstmt=null; } catch(SQLException sqle ){}
      try { if(conn!=null) conn.close(); conn=null; } catch(SQLException sqle ){}
    }
    return response;
  }

  public GetCategoriesResponse GetCategories(GetCategoriesRequest
      request) throws ServletException {
    GetCategoriesResponse response = new GetCategoriesResponse();
    Connection conn = null;
    try {
      String query = "select id, groupid, sortorder, url, active, name, longname, startprice, endprice, description from category ";
      String where = null;
      conn = datasource.getConnection();
      if(request.getgroup()>0)
      {
        where = (where==null) ? "where groupid = ? " : where + "and groupid = ? ";
      }
      if(request.getactive()==true)
      {
        where = (where==null) ? "where active = ? " : where + "and active = ? ";
      }

      if(where!=null)
        query += where;
      query += "order by sortorder";
      pstmt = conn.prepareStatement(query);
      int bind=0;

      if(request.getgroup()>0)
      {
        pstmt.setInt(++bind, request.getgroup());
      }
      if(request.getactive()==true)
      {
        pstmt.setBoolean(++bind, request.getactive());
      }

      rs = pstmt.executeQuery();
      Category category = null;
      while (rs.next()) {
        category = new Category();
        int col=0;
        category.setid(rs.getInt(++col));
        category.setgroup(rs.getInt(++col));
        category.setsortorder(rs.getInt(++col));
        category.seturl(rs.getString(++col));
        category.setactive(rs.getBoolean(++col));
        category.setname(rs.getString(++col));
        category.setlongname(rs.getString(++col));
        category.setstartprice(rs.getDouble(++col));
        category.setendprice(rs.getDouble(++col));
        category.setdescription(rs.getString(++col));
        response.setcategories(category);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try { if(rs!=null) rs.close(); rs=null;} catch(SQLException sqle ){}
      try { if(pstmt!=null) pstmt.close(); pstmt=null; } catch(SQLException sqle ){}
      try { if(conn!=null) conn.close(); conn=null; } catch(SQLException sqle ){}
    }
    return response;
  }

  public Category GetCategory(int id) throws ServletException {

    Connection conn = null;
    Category category = null;
    try {
      conn = datasource.getConnection();
        pstmt = conn.prepareStatement(
            "select id, groupid, sortorder, url, active, name, longname, startprice, endprice, description from category where id = ?");
        pstmt.setInt(1, id);

      rs = pstmt.executeQuery();
      while (rs.next()) {
        category = new Category();
        int col=0;
        category.setid(rs.getInt(++col));
        category.setgroup(rs.getInt(++col));
        category.setsortorder(rs.getInt(++col));
        category.seturl(rs.getString(++col));
        category.setactive(rs.getBoolean(++col));
        category.setname(rs.getString(++col));
        category.setlongname(rs.getString(++col));
        category.setstartprice(rs.getDouble(++col));
        category.setendprice(rs.getDouble(++col));
        category.setdescription(rs.getString(++col));
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try { if(rs!=null) rs.close(); rs=null;} catch(SQLException sqle ){}
      try { if(pstmt!=null) pstmt.close(); pstmt=null; } catch(SQLException sqle ){}
      try { if(conn!=null) conn.close(); conn=null; } catch(SQLException sqle ){}
    }
    return category;
  }

  public GetGroupsResponse GetGroups(GetGroupsRequest
      request) throws ServletException {
    GetGroupsResponse response = new GetGroupsResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("select id, sortorder, name, description, image from grouptype order by sortorder");
      rs = pstmt.executeQuery();
      Group group = null;
      while (rs.next()) {
        int col = 0;
        group = new Group();
        group.setid(rs.getInt(++col));
        group.setsortorder(rs.getInt(++col));
        group.setname(rs.getString(++col));
        group.setdescription(rs.getString(++col));
        group.setimage(rs.getString(++col));
        response.setgroups(group);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try { if(rs!=null) rs.close(); rs=null;} catch(SQLException sqle ){}
      try { if(pstmt!=null) pstmt.close(); pstmt=null; } catch(SQLException sqle ){}
      try { if(conn!=null) conn.close(); conn=null; } catch(SQLException sqle ){}
    }
    return response;
  }

  public GetGroupResponse GetGroupByCategory(GetGroupRequest
      request) throws ServletException {
    GetGroupResponse response = new GetGroupResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("select a.id, a.sortorder, a.name, a.description, a.image from grouptype a, category b where a.id = b.groupid and b.id = ?");
      pstmt.setInt(1,request.getid());
      rs = pstmt.executeQuery();
      Group group = null;
      if (rs.next()) {
        int col = 0;
        group = new Group();
        group.setid(rs.getInt(++col));
        group.setsortorder(rs.getInt(++col));
        group.setname(rs.getString(++col));
        group.setdescription(rs.getString(++col));
        group.setimage(rs.getString(++col));
        response.setgroup(group);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try { if(rs!=null) rs.close(); rs=null;} catch(SQLException sqle ){}
      try { if(pstmt!=null) pstmt.close(); pstmt=null; } catch(SQLException sqle ){}
      try { if(conn!=null) conn.close(); conn=null; } catch(SQLException sqle ){}
    }
    return response;
  }

  public GetGroupResponse GetGroup(GetGroupRequest
      request) throws ServletException {
    GetGroupResponse response = new GetGroupResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("select id, sortorder, name, description, image from grouptype where id = ?");
      pstmt.setInt(1,request.getid());
      rs = pstmt.executeQuery();
      Group group = null;
      if (rs.next()) {
        int col = 0;
        group = new Group();
        group.setid(rs.getInt(++col));
        group.setsortorder(rs.getInt(++col));
        group.setname(rs.getString(++col));
        group.setdescription(rs.getString(++col));
        group.setimage(rs.getString(++col));
        response.setgroup(group);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try { if(rs!=null) rs.close(); rs=null;} catch(SQLException sqle ){}
      try { if(pstmt!=null) pstmt.close(); pstmt=null; } catch(SQLException sqle ){}
      try { if(conn!=null) conn.close(); conn=null; } catch(SQLException sqle ){}
    }
    return response;
  }

  public GetItemStatusesResponse GetItemStatuses(GetItemStatusesRequest
      request) throws ServletException {
    GetItemStatusesResponse response = new GetItemStatusesResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("select id, status from itemstatus order by status");
      rs = pstmt.executeQuery();
      ItemStatus status = null;
      while (rs.next()) {
        status = new ItemStatus();
        status.setid(rs.getInt(1));
        status.setstatus(rs.getString(2));
        response.setstatuses(status);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try { if(rs!=null) rs.close(); rs=null;} catch(SQLException sqle ){}
      try { if(pstmt!=null) pstmt.close(); pstmt=null; } catch(SQLException sqle ){}
      try { if(conn!=null) conn.close(); conn=null; } catch(SQLException sqle ){}
    }
    return response;
  }

  public GetSpecificationsResponse GetSpecifications(GetSpecificationsRequest request) throws ServletException
  {
    GetSpecificationsResponse response = new GetSpecificationsResponse();
    Connection conn = null;
    try
    {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("select id, name from specifications order by id");
      rs = pstmt.executeQuery();
      while(rs.next())
      {
        Specification specification = new Specification();
        specification.setid(rs.getInt(1));
        specification.setname(rs.getString(2));
        response.setspecifications(specification);
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try { if(rs!=null) rs.close(); rs=null;} catch(SQLException sqle ){}
      try { if(pstmt!=null) pstmt.close(); pstmt=null; } catch(SQLException sqle ){}
      try { if(conn!=null) conn.close(); conn=null; } catch(SQLException sqle ){}
    }
    return response;
  }

  public GetSpecificationResponse GetSpecification(GetSpecificationRequest request) throws ServletException
  {
    GetSpecificationResponse response = new GetSpecificationResponse();
    Connection conn = null;
    try
    {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("select id, name from specifications where id = ?");
      pstmt.setInt(1,request.getid());
      rs = pstmt.executeQuery();
      while(rs.next())
      {
        Specification specification = new Specification();
        specification.setid(rs.getInt(1));
        specification.setname(rs.getString(2));
        response.setspecification(specification);
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try { if(rs!=null) rs.close(); rs=null;} catch(SQLException sqle ){}
      try { if(pstmt!=null) pstmt.close(); pstmt=null; } catch(SQLException sqle ){}
      try { if(conn!=null) conn.close(); conn=null; } catch(SQLException sqle ){}
    }
    return response;
  }

  public GetShippingMethodsResponse GetShippingMethods(GetShippingMethodsRequest
      request) throws ServletException {
    GetShippingMethodsResponse response = new GetShippingMethodsResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();

      String query = shippingselect;
      if(request.getcountry()!=null) {
        if(request.getcountry().equalsIgnoreCase("US"))
          query += shippingus;
       else
         query += shippingcountry;
      }
      query += orderby;
      pstmt = conn.prepareStatement(query);
      if(request.getcountry()!=null)
        pstmt.setString(1, request.getcountry());
      rs = pstmt.executeQuery();
      while (rs.next()) {
        ShippingMethod shippingMethod = new ShippingMethod();
        int col=0;
        shippingMethod.setid(rs.getInt(++col));
        shippingMethod.setcarrier(rs.getString(++col));
        shippingMethod.setcode(rs.getString(++col));
        shippingMethod.setcountry(rs.getString(++col));
        shippingMethod.setfixedprice(rs.getDouble(++col));
        shippingMethod.setfreeshippingamount(rs.getDouble(++col));
        shippingMethod.setdescription(rs.getString(++col));
        shippingMethod.setnotes(rs.getString(++col));
        response.setshippingmethods(shippingMethod);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try { if(rs!=null) rs.close(); rs=null;} catch(SQLException sqle ){}
      try { if(pstmt!=null) pstmt.close(); pstmt=null; } catch(SQLException sqle ){}
      try { if(conn!=null) conn.close(); conn=null; } catch(SQLException sqle ){}
    }
    return response;
  }

  ShippingMethod GetShippingMethod(Connection conn, int id) throws ServletException {

    ShippingMethod shippingMethod = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("select id, carrier, code, country, fixedprice, freeshippingamount, " +
                                    "description, notes from shippingmethods where id = ?");
      pstmt.setInt(1, id);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        shippingMethod = new ShippingMethod();
        int col=0;
        shippingMethod.setid(rs.getInt(++col));
        shippingMethod.setcarrier(rs.getString(++col));
        shippingMethod.setcode(rs.getString(++col));
        shippingMethod.setcountry(rs.getString(++col));
        shippingMethod.setfixedprice(rs.getDouble(++col));
        shippingMethod.setfreeshippingamount(rs.getDouble(++col));
        shippingMethod.setdescription(rs.getString(++col));
        shippingMethod.setnotes(rs.getString(++col));
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try { if(rs!=null) rs.close(); rs=null;} catch(SQLException sqle ){}
      try { if(pstmt!=null) pstmt.close(); pstmt=null; } catch(SQLException sqle ){}
      try { if(conn!=null) conn.close(); conn=null; } catch(SQLException sqle ){}
    }
    return shippingMethod;
  }

  public GetStatesResponse GetStateCodes(GetStatesRequest
      request) throws ServletException {
    GetStatesResponse response = new GetStatesResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("select code, name from statecodes order by name");
      rs = pstmt.executeQuery();
      while (rs.next()) {
        StateCode stateCode = new StateCode();
        stateCode.setcode(rs.getString(1));
        stateCode.setname(rs.getString(2));
        response.setstates(stateCode);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try { if(rs!=null) rs.close(); rs=null;} catch(SQLException sqle ){}
      try { if(pstmt!=null) pstmt.close(); pstmt=null; } catch(SQLException sqle ){}
      try { if(conn!=null) conn.close(); conn=null; } catch(SQLException sqle ){}
    }
    return response;
  }

  public GetCountriesResponse GetContryCodes(GetCountriesRequest
      request) throws ServletException {
    GetCountriesResponse response = new GetCountriesResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("select code, name, postalcode from countrycodes order by name");
      rs = pstmt.executeQuery();
      while (rs.next()) {
        CountryCode countryCode = new CountryCode();
        countryCode.setcode(rs.getString(1));
        countryCode.setname(rs.getString(2));
        countryCode.setpostalcode(rs.getString(3));
        response.setcountries(countryCode);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try { if(rs!=null) rs.close(); rs=null;} catch(SQLException sqle ){}
      try { if(pstmt!=null) pstmt.close(); pstmt=null; } catch(SQLException sqle ){}
      try { if(conn!=null) conn.close(); conn=null; } catch(SQLException sqle ){}
    }
    return response;
  }

  static String GetCountryName(Connection conn, String code) throws ServletException {

    String response = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      pstmt = conn.prepareStatement("select name from countrycodes where code = ?");
      pstmt.setString(1, code);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        response = rs.getString(1);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try { if(rs!=null) rs.close(); rs=null;} catch(SQLException sqle ){}
      try { if(pstmt!=null) pstmt.close(); pstmt=null; } catch(SQLException sqle ){}
      try { if(conn!=null) conn.close(); conn=null; } catch(SQLException sqle ){}
    }
    return response;
  }


  static String GetCountryPostalCode(Connection conn, String code) throws ServletException {

      String response = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      try {
        pstmt = conn.prepareStatement("select postalcode from countrycodes where code = ?");
        pstmt.setString(1, code);
        rs = pstmt.executeQuery();
        while (rs.next()) {
          response = rs.getString(1);
        }
      }
      catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
      }
      finally {
        try { if(rs!=null) rs.close(); rs=null;} catch(SQLException sqle ){}
        try { if(pstmt!=null) pstmt.close(); pstmt=null; } catch(SQLException sqle ){}
        try { if(conn!=null) conn.close(); conn=null; } catch(SQLException sqle ){}
      }
      return response;
    }


  public GetSortFieldsResponse GetSortFields(GetSortFieldsRequest
      request) throws ServletException {
    GetSortFieldsResponse response = new GetSortFieldsResponse();
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      if(request.gettype()==null) {
      pstmt = conn.prepareStatement("select id, description, fieldname, direction from sortfields order by id asc");
      }
      else {
        pstmt = conn.prepareStatement("select id, description, fieldname, fieldtype, direction from sortfields where type = ? order by id asc");
        pstmt.setString(1, request.gettype());
      }
      rs = pstmt.executeQuery();
      while (rs.next()) {
        SortFields sortFields = new SortFields();
        sortFields.setid(rs.getInt(1));
        sortFields.setdescription(rs.getString(2));
        sortFields.setfieldname(rs.getString(3));
        sortFields.setfieldtype(rs.getString(4));
        sortFields.setdirection(rs.getString(5));
        response.setsortFields(sortFields);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try { if(rs!=null) rs.close(); rs=null;} catch(SQLException sqle ){}
      try { if(pstmt!=null) pstmt.close(); pstmt=null; } catch(SQLException sqle ){}
      try { if(conn!=null) conn.close(); conn=null; } catch(SQLException sqle ){}
    }
    return response;
  }

  SortFields GetSortField(int id) throws ServletException {

    SortFields sortFields = null;
    Connection conn = null;
    try {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("select id, description, fieldname, fieldtype, direction from sortfields where id = ?");
      pstmt.setInt(1, id);

      rs = pstmt.executeQuery();
      while (rs.next()) {
        sortFields = new SortFields();
        sortFields.setid(rs.getInt(1));
        sortFields.setdescription(rs.getString(2));
        sortFields.setfieldname(rs.getString(3));
        sortFields.setfieldtype(rs.getString(4));
        sortFields.setdirection(rs.getString(5));
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try { if(rs!=null) rs.close(); rs=null;} catch(SQLException sqle ){}
      try { if(pstmt!=null) pstmt.close(); pstmt=null; } catch(SQLException sqle ){}
      try { if(conn!=null) conn.close(); conn=null; } catch(SQLException sqle ){}
    }
    return sortFields;
  }

  int GetCurrentCatalog(HttpServletRequest request) throws ServletException {

    Connection conn = null;
    int catalog = 0;
    try {
      String url = request.getRequestURL().toString();
      String catalogurl = url.substring(url.indexOf("//") + 1,
                                          url.indexOf(":"));
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(
          "select id from catalog where url = ?");
      pstmt.setString(1, catalogurl);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        catalog = rs.getInt(1);
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
    return catalog;
  }
}
