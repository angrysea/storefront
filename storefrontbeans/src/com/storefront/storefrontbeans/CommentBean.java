package com.storefront.storefrontbeans;

import java.util.Iterator;
import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class CommentBean extends BaseBean
{
  final static private String fields[] = { "id","comment"};
  final static private String tablename = "comments";

  public CommentBean() throws ServletException
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

  public void AddComments(int id, Iterator it) throws ServletException
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
}
