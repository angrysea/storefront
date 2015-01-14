package com.storefront.storefrontbeans;

import java.util.*;
import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class VariationsBean
    extends BaseBean {
  final static private String fields[] = {
      "id", "quantity", "clothingsize", "clothingcolor", "fabric", "price",
      "saleprice", "shipdate",
      "availability", "multimerchant", "merchantsku", "imageurl1", "imageurl2",
      "imageurl3", "imageurl4"};

  final static private String select =
      "select id, quantity, clothingsize, clothingcolor, fabric, price, saleprice, " +
      "shipdate, availability, multimerchant, merchantsku, imageurl1 " +
      "imageurl2, imageurl3, imageurl4 from variations where id = ?";

  final static private String tablename = "Variations";

  public VariationsBean() throws ServletException {
    super();
  }

  String[] getfields() {
    return fields;
  }

  String gettableName() {
    return tablename;
  }

  String getindexName() {
    return "itemnumber";
  }

  public void AddVariations(Connection conn, int id, Iterator it) throws
      ServletException {
    try {
      while (it.hasNext()) {
        Variation variation = (Variation) it.next();
        pstmt = conn.prepareStatement(getInsertString());
        pstmt.setInt(1, id);
        pstmt.setInt(2, variation.getquantity());
        pstmt.setString(3, variation.getclothingSize());
        pstmt.setString(4, variation.getclothingColor());
        pstmt.setString(5, variation.getfabric());
        pstmt.setDouble(6, variation.getprice());
        pstmt.setDouble(7, variation.getsalePrice());
        pstmt.setString(8, variation.getshipDate());
        pstmt.setString(9, variation.getavailability());
        pstmt.setString(10, variation.getmultiMerchant());
        pstmt.setString(11, variation.getmerchantSku());
        pstmt.setString(12, variation.getimageUrl1());
        pstmt.setString(13, variation.getimageUrl2());
        pstmt.setString(14, variation.getimageUrl3());
        pstmt.setString(15, variation.getimageUrl4());
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

  public void UpdateVariations(Connection conn, int id, Iterator it) throws
      ServletException {
    try {
      while (it.hasNext()) {
        Variation variation = (Variation) it.next();
        pstmt = conn.prepareStatement(getUpdateString());
        pstmt.setInt(1, id);
        pstmt.setInt(2, variation.getquantity());
        pstmt.setString(3, variation.getclothingSize());
        pstmt.setString(4, variation.getclothingColor());
        pstmt.setString(5, variation.getfabric());
        pstmt.setDouble(6, variation.getprice());
        pstmt.setDouble(7, variation.getsalePrice());
        pstmt.setString(8, variation.getshipDate());
        pstmt.setString(9, variation.getavailability());
        pstmt.setString(10, variation.getmultiMerchant());
        pstmt.setString(11, variation.getmerchantSku());
        pstmt.setString(12, variation.getimageUrl1());
        pstmt.setString(13, variation.getimageUrl2());
        pstmt.setString(14, variation.getimageUrl3());
        pstmt.setString(15, variation.getimageUrl4());
        pstmt.setInt(16, id);
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

  public void DeleteVariations(Connection conn, int id) throws ServletException {
    try {
      pstmt = conn.prepareStatement("delete from variations where id=?");
      pstmt.setInt(1, id);
      pstmt.executeUpdate();

      CategoryItemBean categoryitem = new CategoryItemBean();
      categoryitem.DeleteCategoryItems(conn, id);
      SpecificationBean specificationBean = new SpecificationBean();
      specificationBean.DeleteSpecifications(conn, id);
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

  public void GetVariations(Connection conn, Details detail) throws
      ServletException {
    try {
      Variation variation = new Variation();
      pstmt = conn.prepareStatement(select);
      pstmt.setInt(1, detail.getid());
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        variation.setid(rs.getInt(1));
        variation.setquantity(rs.getInt(2));
        variation.setclothingSize(rs.getString(3));
        variation.setclothingColor(rs.getString(4));
        variation.setfabric(rs.getString(5));
        variation.setprice(rs.getDouble(6));
        variation.setsalePrice(rs.getDouble(7));
        variation.setshipDate(rs.getString(8));
        variation.setavailability(rs.getString(9));
        variation.setmultiMerchant(rs.getString(10));
        variation.setmerchantSku(rs.getString(11));
        variation.setimageUrl1(rs.getString(12));
        variation.setimageUrl2(rs.getString(13));
        variation.setimageUrl3(rs.getString(14));
        variation.setimageUrl4(rs.getString(15));
        detail.setvariations(variation);
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
