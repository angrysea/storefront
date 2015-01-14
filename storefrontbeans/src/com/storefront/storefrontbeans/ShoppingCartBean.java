package com.storefront.storefrontbeans;

import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class ShoppingCartBean extends BaseBean
{
  final static private String fields[] = { "id","customerid","creationtime" };

  final static private String tablename = "ShoppingCart";

  public ShoppingCartBean() throws ServletException
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

}
