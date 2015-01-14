package com.storefront.storefrontbeans;

import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class ShoppingCartItemBean extends BaseBean
{
  final static private String fields[] = { "id","cartid","itemid" };

  final static private String tablename = "ShoppingCartItems";

  public ShoppingCartItemBean() throws Exception
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
