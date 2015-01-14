<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    Theme theme = null;
    Company company = null;
    try {
        CompanyBean companyBean = new CompanyBean();
        GetThemeRequest getThemeRequest = new GetThemeRequest();
        getThemeRequest.setname("corporate");
        GetThemeResponse getThemeResponse = companyBean.GetTheme(request, getThemeRequest);
        theme = getThemeResponse.gettheme();

        GetCompanyResponse getCompanyResponse = companyBean.GetCompany(request, new GetCompanyRequest());
        company = getCompanyResponse.getcompany();
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<%
    DecimalFormat moneyFormat = new DecimalFormat("$#,###.00");

    LandingPage landingPage = null;
    SearchResponse searchResponse = null;
    GetSearchResultsResponse getSearchResultsResponse = null;
    String hrefmore = "";
    try {
        UserBean userBean = new UserBean();
        GetUserResponse getUserResponse = userBean.GetUser(request, response);

        LandingPageBean landingPageBean = new LandingPageBean();
        GetLandingPageRequest getLandingPageRequest = new GetLandingPageRequest();
        getLandingPageRequest.setid(new Integer(request.getParameter("id")).intValue());
        GetLandingPageResponse getLandingPageResponse = landingPageBean.GetLandingPage(getLandingPageRequest);
        landingPage = getLandingPageResponse.getlandingPage();

        SearchItemsBean searchItemsBean = new SearchItemsBean();
        SearchRequest searchRequest = new SearchRequest();
        if(landingPage.gettype().compareToIgnoreCase("manufacturer") == 0)
            searchRequest.setmanufacturerid(new Integer(landingPage.gettypeid()).toString());
        else
            searchRequest.setcategoryids(new Integer(landingPage.gettypeid()).toString());
        searchResponse = searchItemsBean.SearchItems(searchRequest, getUserResponse.getuser());

        GetSearchResultsRequest getSearchResultsRequest = new GetSearchResultsRequest();
        getSearchResultsRequest.setidxstart(1);
        getSearchResultsRequest.setidxend(4);
        int searchid = searchResponse.getresults().getid();
        getSearchResultsRequest.setsearchid(searchid);
        getSearchResultsResponse = searchItemsBean.GetSearchResults(getSearchResultsRequest);
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>


<HTML>
    <HEAD>
        <%@ include file="titlemetadata.jsp" %>
        <LINK href="<%=company.getbaseurl()%>storefront.css" rel="STYLESHEET"></LINK>
    </HEAD>
    <BODY leftMargin="0" topMargin="0" onload="" marginwidth="0" marginheight="0">
        <%@ include file="toppane.jsp" %>
        <table cellSpacing="0" cellPadding="0" border="0">
            <tr>
                <td vAlign="top" width="20">
                    <IMG src="<%=theme.getimagebaseurl()%>spacer.gif" border="0">
                </td>
                <td vAlign="top">
                    <table id="headingTable" border="0">
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="5" height="5"></td>
                        </tr>
                    </table>
                    <br>
            <%
                if(landingPage.geturl() != null && landingPage.geturl().length() > 0)
                {
            %>
                    <table border="0">
                        <tr>
                            <td><img alt="<%=landingPage.getheading()%>" src="<%=landingPage.geturl()%>"/></td>
                        </tr>
                    </table>
            <%
                }
                else
                {
            %>
                    <table border="0">
                        <tr>
                            <td class="producttitle" noWrap><%=landingPage.getheading()%></td>
                        </tr>
                    </table>
            <%
                }
            %>
                    <table border="0" width="750">
                        <tr>
                            <td><%=landingPage.getdescription()%></td>
                        </tr>
                    </table>
                    <br>
                    <table border="0" width="750">
                        <tr>
                            <td nowrap colspan="2"><b>Our Most Popular:</b> <%=landingPage.getheading()%></td>
                        </tr>
                    </table>

                    <table cellSpacing="1" cellPadding="3" width="750" border="0">
                        <tr>
                            <td colspan="5">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <tr bgcolor="<%=theme.getcolor1()%>">
                                        <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="750" height="1" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr valign="bottom">
                <%
                    Iterator itSearchItems = getSearchResultsResponse.getitemsIterator();
                    for(int i=0 ; itSearchItems.hasNext() && i<4 ; i++)
                    {
                        Item item = (Item)itSearchItems.next();
                %>
                            <td align="center">
                                <table border="0" cellspacing="1" cellpadding="1">
                                <tr>
                                    <td colspan="2" align="center"><a href="<%=StoreFrontUrls.getproductdetails(request, company, item)%>"><img alt="<%=company.getkeyword()%> - <%=item.getmanufacturer()%> <%=item.getproductname()%>" src="<%=theme.getimagebaseurl()%><%=item.getdetails().getimageUrlSmall()%>" border="0"/></a></td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="center"><b><%=item.getmanufacturer()%> <%=item.getproductname()%></b></td>
                                </tr>
                                <tr>
                                    <td align="center" noWrap>List Price: <%=moneyFormat.format(item.getlistprice())%></td>
                                </tr>
                                <tr>
                                    <td align="center" noWrap>Our Price: <font color="red"><%=moneyFormat.format(item.getourprice())%></font></td>
                                </tr>
                                <tr>
                                    <td align="center" noWrap><%=item.getavailability()%></td>
                                </tr>
                                </table>
                            </td>
                <%
                    }
                %>
                        </tr>
                        <tr>
                            <td colspan="5">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <tr bgcolor="<%=theme.getcolor1()%>">
                                        <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="750" height="1" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                    <table cellSpacing="1" cellPadding="1" width="750" border="0">
                        <tr>
                            <td colspan="20">
                                <table border="0" cellspacing="0" cellpadding="0" width="750">
                                    <tr>
                                        <td align="right">
            	<%
                    if(landingPage.gettype().compareToIgnoreCase("manufacturer") == 0)
                    {
            	%>
                    <a href="<%=StoreFrontUrls.getsearchresults(request, company, landingPage.getheading(), "0", "0", "0", Integer.toString(landingPage.gettypeid()), "0", "0", "1")%>"><font color="red">More <%=landingPage.getheading()%> >></font></a>
            	<%
                    }
                    else
                    {
            	%>
                    <a href="<%=StoreFrontUrls.getsearchresults(request, company, landingPage.getheading(), "0", "0", "0", "0", Integer.toString(landingPage.gettypeid()), "0", "1")%>"><font color="red">More <%=landingPage.getheading()%> >></font></a>
            	<%
                    }
          	%>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                    <br>
                    <br>
                    <table width="750" border="0">
                        <tr>
                            <td align="center"><a href="<%=StoreFrontUrls.geturl(request, company, "index")%>"><img alt="More <%=landingPage.getheading()%>" src="<%=theme.getimagebaseurl()%>enterourstore_button.gif" border="0" /></a></td>
                        </tr>
                    </table>
                    <%@ include file="bottompane.jsp" %>
                </td>
                <%@ include file="rightpane.jsp" %>
            </tr>
        </table>
    </BODY>
</HTML>
