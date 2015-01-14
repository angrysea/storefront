package com.storefront.storefrontbeans;

import java.util.Iterator;
import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class KeyPhrasesBean extends BaseBean
{
  final static private String fields[] = { "itemnumber","phrase"};
  final static private String tablename = "KeyPhrases";

  public KeyPhrasesBean() throws ServletException
  {
    super();
  }

  public KeyPhrasesBean(Connection conn) throws ServletException
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

  public void AddKeyPhrases(Connection conn, int id, Iterator it) throws ServletException
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
  }

  public void GetKeyPhrases(Connection conn, int id, Details detail) throws ServletException
  {
    try
    {
      pstmt = conn.prepareStatement("select phrase from KeyPhrases where itemnumber = ?");
      pstmt.setInt(1,id);
      ResultSet rs = pstmt.executeQuery();
      while(rs.next())
      {
        detail.setkeyPhrase(rs.getString(1));
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
  }
}
