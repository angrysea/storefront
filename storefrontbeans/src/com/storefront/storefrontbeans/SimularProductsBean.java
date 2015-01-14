package com.storefront.storefrontbeans;

import java.util.Iterator;
import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class SimularProductsBean extends BaseBean
{
  final static private String fields[] = { "itemNumber","comment"};
  final static private String tablename = "SimularProducts";

  public SimularProductsBean() throws ServletException
  {
    super();
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

  public void AddSimularProducts(int id, Iterator it) throws ServletException
  {
    Connection conn = null;
    try
    {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement(getInsertString());

      while(it.hasNext())
      {
        pstmt.setInt(1, id);
        pstmt.setString(2, (String)it.next());
        pstmt.executeUpdate();
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
  }

  public void GetSimularProducts(int id, Details detail) throws ServletException
  {
    Connection conn = null;
    try
    {
      conn = datasource.getConnection();
      pstmt = conn.prepareStatement("select id,comment from SimularProducts where itemnumber = ?");
      pstmt.setInt(1,id);
      ResultSet rs = pstmt.executeQuery();
      while(rs.next())
      {
        SimularProduct simularproduct = new SimularProduct();
        simularproduct.setid(rs.getInt(1));
        simularproduct.setcomment(rs.getString(2));
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
  }
}
