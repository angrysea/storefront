package com.storefront.storefrontbeans;

import java.util.Iterator;
import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class ProductDescriptionBean extends BaseBean
{
  final static private String fields[] = { "itemnumber","description"};
  final static private String tablename = "ProductDescription";

  public ProductDescriptionBean() throws ServletException
  {
    super();
  }

  public ProductDescriptionBean(Connection conn) throws ServletException
  {
    super(conn);
  }

  String getdburl()
  {
    return "jdbc:mysql:///storefront";
  }

  String[] getfields()
  {
    return fields;
  }

  String gettableName()
  {
    return tablename;
  }

  String getindexName()
  {
    return "id";
  }

  public void AddProductDescriptions(int id, Iterator it) throws ServletException
  {
    try
    {
      pstmt = conn.prepareStatement(getInsertString());

      while(it.hasNext())
      {
        pstmt.setInt(1, id);
        pstmt.setString(2, (String)it.next());
        pstmt.executeUpdate();
      }
      pstmt.close();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
  }

  public void GetProductDescription(int id, Item item) throws ServletException
  {
    try
    {
      pstmt = conn.prepareStatement("select description from productdescription where itemnumber = ?");
      pstmt.setInt(1,id);
      ResultSet rs = pstmt.executeQuery();
      while(rs.next())
      {
        item.setdescription(rs.getString(1));
      }
      pstmt.close();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
  }
}
