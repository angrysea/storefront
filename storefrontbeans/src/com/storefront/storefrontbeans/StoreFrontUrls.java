package com.storefront.storefrontbeans;

import javax.servlet.http.*;
import com.storefront.storefrontrepository.*;

public class StoreFrontUrls {

  public StoreFrontUrls() {
  }

  public static String geturl(HttpServletRequest request, Company company,
                              String page) {

    String ret = null;
    String user = (String)request.getAttribute("user");
    if (company.getusemodrewrite()) {
      ret = company.getbaseurl();
      if (user != null && !user.equals("0")) {
        ret += user + "_" +
            request.getAttribute("login") + "/";
      }
      ret+= page + ".html";
    }
    else {
      ret = company.getbaseurl() + page + ".jsp";
      if (user != null && !user.equals("0")) {
        ret += "?user=" + request.getAttribute("user") + "&login=" +
            request.getAttribute("login");
      }
    }

    return ret;
  }

  public static String getnonrwurl(HttpServletRequest request, Company company,
                              String page) {

    String user = (String)request.getAttribute("user");
    String ret = ret = company.getbaseurl() + page + ".jsp";
    if (user != null && !user.equals("0")) {
      ret += "?user=" + request.getAttribute("user") + "&login=" +
          request.getAttribute("login");
    }
    return ret;
  }

  public static String geturl(HttpServletRequest request, Company company,
                              String page, String gotourl) {

    String ret = company.getbaseurl() + page + ".jsp?gotourl=" + gotourl;
    String user = (String)request.getAttribute("user");
    if (user!=null && !user.equals("0")) {
      ret += "&user=" + request.getAttribute("user") +
          "&login=" + request.getAttribute("login");
    }
    return ret;
  }

  public static String getsecureurl(HttpServletRequest request, Company company,
                                    String page) {

    String user = (String)request.getAttribute("user");
    String ret = company.getbasesecureurl() + page + ".jsp";
    if (user!=null && !user.equals("0")) {
      ret += "?user=" + request.getAttribute("user") + "&login=" +
          request.getAttribute("login");
    }

    return ret;
  }

  public static String getsecureurl(HttpServletRequest request, Company company,
                                    String page, String gotourl) {

    String user = (String)request.getAttribute("user");
    String ret = company.getbasesecureurl() + page + ".jsp?gotourl=" + gotourl;
    if (user!=null && !user.equals("0")) {
      ret += "&user=" + request.getAttribute("user") +
          "&login=" + request.getAttribute("login");
    }
    return ret;
  }

  public static String getsecureurlex(HttpServletRequest request, Company company,
                                    String page, String ex) {

    String user = (String)request.getAttribute("user");
    String ret = company.getbasesecureurl() + page + ".jsp?" + ex;
    if (user!=null && !user.equals("0")) {
      ret += "&user=" + request.getAttribute("user") +
          "&login=" + request.getAttribute("login");
    }
    return ret;
  }

  public static String getlandingpage(HttpServletRequest request,
                                      Company company, LandingPage landingPage) {

    String ret = null;
    String user = (String)request.getAttribute("user");
    if (company.getusemodrewrite()) {
      ret = company.getbaseurl() +
          landingPage.getheading().replaceAll(" ", "-").toLowerCase() + "/" +
          Integer.toString(landingPage.getid()) + "/";
      if(request.getAttribute("user")!=null) {
        ret += user + "_" +
            request.getAttribute("login") + "/";
      }
      ret += "index.html";
    }
    else {
      ret += company.getbaseurl() + "landingpage.jsp?id=" +
          Integer.toString(landingPage.getid());
      if (request.getAttribute("user")!=null) {
        ret += "&user=" + request.getAttribute("user") +
            "&login=" + request.getAttribute("login");
      }
    }
    return ret;
  }

  public static String getlinksurl(HttpServletRequest request,
                                      Company company, int page) {

       String ret = null;
       String user = (String)request.getAttribute("user");
       if (company.getusemodrewrite()) {
         ret = company.getbaseurl();
         if (user != null && !user.equals("0")) {
           ret += user + "_" +
               request.getAttribute("login") + "/";
         }
         ret+= "links" +Integer.toString(page) + ".html";
       }
       else {
         ret = company.getbaseurl() + "links.jsp?page="+Integer.toString(page);
         if (user != null && !user.equals("0")) {
           ret += "?user=" + request.getAttribute("user") + "&login=" +
               request.getAttribute("login");
         }
       }

       return ret;
  }

  public static String getarticlepage(HttpServletRequest request,
                                      Company company, int articleid) {

    String ret = null;
    String user = (String) request.getAttribute("user");
    if (company.getusemodrewrite()) {
      try {
        GetArticleRequest getarticle = new GetArticleRequest();
        getarticle.setid(articleid);
        ArticleBean articleBean = new ArticleBean();

        GetArticleResponse response = articleBean.GetArticle(getarticle);
        ret = company.getbaseurl() +
            response.getarticle().getheading().replaceAll(" ", "-").toLowerCase() +
            "/";
        if (request.getAttribute("user") != null) {
          ret += user + "_" +
              request.getAttribute("login") + "/";
        }
        ret += "index.html/" + Integer.toString(response.getarticle().getid());
        return ret;
      }
      catch (Exception ex) {
      }
    }

    ret += company.getbaseurl() + "landingpage.jsp?id=" +
        Integer.toString(articleid);
    if (request.getAttribute("user") != null) {
      ret += "&user=" + request.getAttribute("user") +
          "&login=" + request.getAttribute("login");
    }
    return ret;
  }

  public static String getproductdetails(HttpServletRequest request,
                                         Company company, Item item) {

    String ret = null;
    String user = (String)request.getAttribute("user");
    if (company.getusemodrewrite()) {
      ret = getproductdetailsModRewrite(company, item);
      if (user!=null && !user.equals("0")) {
        ret += user + "_" +
            request.getAttribute("login") + "/";
      }
      ret += "index.html";
    }
    else {
      ret = company.getbaseurl() + "productdetails.jsp?itemid=" +
          Integer.toString(item.getid());
      if (user!=null && !user.equals("0")) {
        ret += "&user=" + request.getAttribute("user") +
            "&login=" + request.getAttribute("login");
      }
    }
    return ret;
  }

  static String getproductdetailsModRewrite(Company company, Item item) {
    String ret = null;
    ret = company.getbaseurl() +
        item.getmanufacturer().replaceAll(" ", "-").replaceAll(",", "").
        replaceAll("---", "-").toLowerCase() + "/" +
        item.getproductname().replaceAll(" ", "-").replaceAll(",", "").
        replaceAll("---", "-").toLowerCase() + "/" +
        Integer.toString(item.getid()) + "/";
    return ret;
  }

  public static String getsalesorder(HttpServletRequest request,
                                     Company company,
                                     SalesOrderItem salesorderitem,
                                     String task) {

    String user = (String)request.getAttribute("user");
    String ret = company.getbaseurl() + "salesorder.jsp?itemid=" +
        Integer.toString(salesorderitem.getitemnumber()) +
        "&task=" + task;
    if (user!=null && !user.equals("0")) {
      ret += "&user=" + request.getAttribute("user") +
          "&login=" + request.getAttribute("login");
    }
    return ret;
  }

  public static String getshoppingcart(HttpServletRequest request,
                                       Company company,
                                       ShoppingCartItem item,
                                       String task) {

    String ret = null;
    String user = (String)request.getAttribute("user");
    ret = company.getbaseurl() + "shoppingcart.jsp?itemid=" +
        Integer.toString(item.getitem().getid()) + "&task=" + task;
    if (user!=null && !user.equals("0")) {
      ret += "&user=" + request.getAttribute("user") +
          "&login=" + request.getAttribute("login");
    }
    return ret;
  }

  public static String getshoppingcart(HttpServletRequest request,
                                       Company company,
                                       Item item,
                                       String task,
                                       int quantity) {

    String ret = null;
    String user = (String)request.getAttribute("user");
    ret = company.getbaseurl() + "shoppingcart.jsp?itemid=" +
        Integer.toString(item.getid()) +
        "&task=" + task +
        "&quantity=" + Integer.toString(quantity);
    if (user!=null && !user.equals("0")) {
      ret += "&user=" + request.getAttribute("user") +
          "&login=" + request.getAttribute("login");
    }
    return ret;
  }

  public static String getorderdetails(HttpServletRequest request,
                                       Company company, SalesOrder salesorder) {

    String ret = null;
    String user = (String)request.getAttribute("user");
    ret = company.getbaseurl() + "orderdetails.jsp?orderid=" +
        Integer.toString(salesorder.getid());
    if (user!=null && !user.equals("0")) {
      ret += "&user=" + request.getAttribute("user") +
          "&login=" + request.getAttribute("login");
    }

    return ret;
  }

  public static String getcreateaccount(HttpServletRequest request,
                                        Company company) {

    String ret = null;
    String user = (String)request.getAttribute("user");
    ret = company.getbasesecureurl() + "createaccount.jsp?gotourl=" +
        request.getParameter("gotourl");
    if (user!=null && !user.equals("0")) {
      ret += "&user=" + request.getAttribute("user") +
          "&login=" + request.getAttribute("login");
    }
    return ret;
  }

  public static String getsearchresults(HttpServletRequest request,
                                        Company company,
                                        String heading,
                                        String searchid,
                                        String itemsfound,
                                        String search,
                                        String manufacturerid,
                                        String categoryid,
                                        String sortby,
                                        String page) {

    String url = null;


    String user = (String)request.getAttribute("user");
    if (company.getusemodrewrite()) {
      if (searchid == null) {
        searchid = "0";
      }
      if (itemsfound == null) {
        itemsfound = "0";
      }
      if (search == null) {
        search = "0";
      }
      if (manufacturerid == null) {
        manufacturerid = "0";
      }
      if (categoryid == null) {
        categoryid = "0";
      }
      if (sortby == null) {
        sortby = "0";
      }
      if (page == null) {
        page = "0";
      }
      url = company.getbaseurl() +
          heading.replaceAll(" ", "-").replaceAll("/", "-") + "/";
      if (user != null && !user.equals("0")) {
        url += user + "_" +
            request.getAttribute("login") + "/";
      }
      url += searchid + "_" + itemsfound + "_" + search + "_" +
          manufacturerid + "_" + categoryid + "_" + sortby + "_" + page +
          "/index.html";
    }
    else {
      url = company.getbaseurl() + "search_result.jsp";
      if(request.getAttribute("user")!=null) {
          url += "?user=" + request.getAttribute("user") +
          "&login=" + request.getAttribute("login") + "&";
      }
      else {
        url+="?";
      }

      if(searchid!=null) {
        url += "searchid=" + searchid;
      }

      if(itemsfound!=null) {
        url += "&itemsfound=" + itemsfound;
      }
      if(search!=null) {
        url += "&search" + search;
      }
      if(manufacturerid!=null) {
        url += "&manufacturerid=" + manufacturerid;
      }
      if(categoryid!=null) {
        url += "&categoryid1=" + categoryid;
      }
      if(heading!=null) {
        url += "&description=" + heading.replaceAll(" ", "-");
      }
      if(sortby!=null) {
        url += "&sortby=" + sortby;
      }
      if(page!=null) {
        url += "&page=" + page;
      }
    }
    return url;
  }

  public static String getsearchresultsurl(HttpServletRequest request,
                                           Company company,
                                           String heading,
                                           String searchid,
                                           String itemsfound,
                                           String search,
                                           String manufacturerid,
                                           String categoryid,
                                           String sortby,
                                           String page) {


    String user = (String)request.getAttribute("user");
    String url = company.getbaseurl() + "search_result.jsp";

    if(request.getAttribute("user")!=null) {
        url += "?user=" + request.getAttribute("user") +
        "&login=" + request.getAttribute("login") + "&";
    }
    else {
      url+="?";
    }

    if(searchid!=null) {
      url += "searchid=" + searchid;
    }

    if(itemsfound!=null) {
      url += "&itemsfound=" + itemsfound;
    }
    if(search!=null) {
      url += "&search" + search;
    }
    if(manufacturerid!=null) {
      url += "&manufacturerid=" + manufacturerid;
    }
    if(categoryid!=null) {
      url += "&categoryid1=" + categoryid;
    }
    if(heading!=null) {
      url += "&description=" + heading.replaceAll(" ", "-");
    }
    if(sortby!=null) {
      url += "&sortby=" + sortby;
    }
    if(page!=null) {
      url += "&page=" + page;
    }

    return url;
  }

  public static String updateurl(HttpServletRequest request, Company company,
                                 String url) {

    String user = (String)request.getAttribute("user");
    if (user!=null && !user.equals("0")) {
      if (url.indexOf("?") > 0) {
        url += "&";
      }
      else {
        url += "?";
      }

      url += "user=" + user +
          "&login=" + request.getAttribute("login");
    }
    return url;
  }
}
