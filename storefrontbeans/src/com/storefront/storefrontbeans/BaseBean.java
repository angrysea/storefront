package com.storefront.storefrontbeans;

import javax.servlet.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;

abstract class BaseBean {
  private InitialContext ic;
  protected DataSource datasource = null;
  protected PreparedStatement pstmt = null;
  protected ResultSet rs = null;
  protected Statement stmt = null;

  abstract String[] getfields();

  abstract String gettableName();

  abstract String getindexName();

  BaseBean() throws ServletException {
    try {
      ic = new InitialContext();
      datasource = (DataSource) ic.lookup("java:comp/env/storefront");
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
  }

  protected final String getSelectString() {
    return getSelectString(null);
  }

  protected final String getSelectString(String where) {
    StringBuffer stmt = new StringBuffer("select ");
    String[] fields = getfields();

    int size = fields.length;

    for (int i = 0; i < size; i++) {
      stmt.append(fields[i]);
      if (i + 1 < size)
        stmt.append(",");
    }
    stmt.append(" from ");
    stmt.append(gettableName());

    if (where != null) {
      stmt.append(" where ");
      stmt.append(where);
    }

    return stmt.toString();
  }

  protected final String getSelectWithIDString() {
    return getSelectWithIDString(null);
  }

  protected final String getSelectWithIDString(String where) {
    StringBuffer stmt = new StringBuffer("select ");
    String[] fields = getfields();

    int size = fields.length;
    stmt.append(getindexName());
    stmt.append(", ");
    for (int i = 0; i < size; i++) {
      stmt.append(fields[i]);
      if (i + 1 < size)
        stmt.append(",");
    }
    stmt.append(" from ");
    stmt.append(gettableName());

    if (where != null) {
      stmt.append(" where ");
      stmt.append(where);
    }

    return stmt.toString();
  }

  protected final String getSelectByIDString() {
    StringBuffer stmt = new StringBuffer("select ");
    String[] fields = getfields();

    int size = fields.length;

    for (int i = 0; i < size; i++) {
      stmt.append(fields[i]);
      if (i + 1 < size)
        stmt.append(",");
    }
    stmt.append(" from ");
    stmt.append(gettableName());
    stmt.append(" where ");
    stmt.append(getindexName());
    stmt.append(" = ?");

    return stmt.toString();
  }

  protected final String getSelectWithIDByIDString() {
    StringBuffer stmt = new StringBuffer("select ");
    String[] fields = getfields();

    int size = fields.length;

    stmt.append(getindexName());
    stmt.append(", ");
    for (int i = 0; i < size; i++) {
      stmt.append(fields[i]);
      if (i + 1 < size)
        stmt.append(",");
    }
    stmt.append(" from ");
    stmt.append(gettableName());
    stmt.append(" where ");
    stmt.append(getindexName());
    stmt.append(" = ?");

    return stmt.toString();
  }

  protected final String getInsertString() {
    StringBuffer stmt = new StringBuffer("insert into " + gettableName() + " (");
    String[] fields = getfields();

    int size = fields.length;

    for (int i = 0; i < size; i++) {
      stmt.append(fields[i]);
      if (i + 1 < size)
        stmt.append(",");
    }
    stmt.append(") values (");
    for (int i = 0; i < size; i++) {
      stmt.append("?");
      if (i + 1 < size)
        stmt.append(",");
    }
    stmt.append(")");
    return stmt.toString();
  }

  protected final String getUpdateString() {
    StringBuffer stmt = new StringBuffer("update " + gettableName() + " set ");
    String[] fields = getfields();

    int size = fields.length;

    for (int i = 0; i < size; i++) {
      stmt.append(fields[i]);
      stmt.append("=?");
      if (i + 1 < size)
        stmt.append(",");
    }
    stmt.append(" where ");
    stmt.append(getindexName());
    stmt.append("=?");
    return stmt.toString();
  }

  int getLastInsertID(Connection conn) throws ServletException {
    int ID = 0;
    Statement stmt = null;
    try {
      String queryStr = "select LAST_INSERT_ID()";
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(queryStr);
      if (rs.next()) {
        ID = rs.getInt(1);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new ServletException(ex.getMessage());
    }
    finally {
      try {
        stmt.close();
      }
      catch (Exception ex) {}
      ;
    }
    return ID;
  }
}
