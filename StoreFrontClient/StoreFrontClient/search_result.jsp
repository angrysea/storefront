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

    ListsBean listsBean = new ListsBean();

    int NUMBER_TO_DISPLAY = theme.getsearchresultcount() * 3;

    GetSearchResultsResponse getSearchResultsResponse = null;
    GetUserResponse getUserResponse = null;
    int pagenumber = 0;
    int searchid = 0;
    int itemsfound = 0;
    int startindex = 0;
    int endindex = 0;
    int totalPages = 0;
    String hrefs = null;
    String hrefPrevious = null;
    String hrefNext = null;
    String searchTitle = "";
    String manufacturerDescription = "";
    try {
        UserBean userBean = new UserBean();
        if(validParameter(request, "sortby")) {
           getUserResponse = userBean.GetUser(request, response, true);
        }
	else {
           getUserResponse = userBean.GetUser(request, response);
	}
        // Update the sort by value if it has changed
        if(validParameter(request, "sortby"))
        {
            UpdateUserRequest updateUserRequest = new UpdateUserRequest();
            User user = getUserResponse.getuser();
            user.setlastsortorder(new Integer(request.getParameter("sortby")).intValue());
            updateUserRequest.setuser(user);
            userBean.UpdateUserSortOrder(updateUserRequest);
            getUserResponse = userBean.GetUser(request, response);
        }

        if(validParameter(request, "page"))
            pagenumber = new Integer(request.getParameter("page")).intValue();
        if(validParameter(request, "itemsfound"))
            itemsfound = new Integer(request.getParameter("itemsfound")).intValue();

        SearchItemsBean searchItemsBean = new SearchItemsBean();
        SearchResponse searchResponse = null;

        if(pagenumber == 1)
        {
            SearchRequest searchRequest = new SearchRequest();
            if(validParameter(request, "manufacturerid"))
                searchRequest.setmanufacturerid(request.getParameter("manufacturerid"));
            if(validParameter(request, "categoryid1"))
            {
                // Check if it is price range category
                Category category = listsBean.GetCategory(Integer.parseInt(request.getParameter("categoryid1")));
                if(category.getendprice() > 0.0)
                {
                    searchRequest.setstartprice(category.getstartprice());
                    searchRequest.setendprice(category.getendprice());
                }
                else
                {
                    searchRequest.setcategoryids(request.getParameter("categoryid1"));
                }
            }
            if(validParameter(request, "search"))
                searchRequest.setsearchphrase(request.getParameter("search"));
            searchRequest.setsortfieldid(new Integer(getUserResponse.getuser().getlastsortorder()).toString());

            searchResponse = searchItemsBean.SearchItems(searchRequest, getUserResponse.getuser());
            itemsfound = searchResponse.getresults().getitemsfound();
        }

        startindex = ((pagenumber - 1) * NUMBER_TO_DISPLAY) + 1;
        endindex = (startindex + NUMBER_TO_DISPLAY) - 1;
        if(endindex > itemsfound)
            endindex = itemsfound;
        GetSearchResultsRequest getSearchResultsRequest = new GetSearchResultsRequest();
        getSearchResultsRequest.setidxstart(startindex);
        getSearchResultsRequest.setidxend(endindex);
        if(validParameter(request, "searchid"))
            searchid = new Integer(request.getParameter("searchid")).intValue();
        else
            searchid = searchResponse.getresults().getid();
        getSearchResultsRequest.setsearchid(searchid);
        getSearchResultsResponse = searchItemsBean.GetSearchResults(getSearchResultsRequest);

        // Calculate the total number of pages
        if(itemsfound > 0)
        {
            totalPages = itemsfound / NUMBER_TO_DISPLAY;
            if(itemsfound % NUMBER_TO_DISPLAY > 0)
                totalPages++;
        }

        // Build the search title
        if(validParameter(request, "search"))
        {
            searchTitle = "Search Result: " + request.getParameter("search");
            request.setAttribute("searchtitle", request.getParameter("search"));
        }
        else if(validParameter(request, "description"))
        {
            searchTitle = "Browse by: " + request.getParameter("description").replaceAll("-", " ");
            request.setAttribute("searchtitle", request.getParameter("description").replaceAll("-", " "));
        }

        // Determine next and previous result links
        hrefs = StoreFrontUrls.getsearchresults(request,
                				company,
                				request.getParameter("description"),
                				Integer.toString(searchid),
                                              	Integer.toString(itemsfound),
                                              	request.getParameter("search"),
                				request.getParameter("manufacturerid"),
                				request.getParameter("categoryid1"),
                				request.getParameter("sortby"),
                                                Integer.toString(pagenumber));
      	if(pagenumber != 1) {
           hrefPrevious =
           	StoreFrontUrls.getsearchresults(request,
                				company,
                				request.getParameter("description"),
                				Integer.toString(searchid),
                                              	Integer.toString(itemsfound),
                                              	request.getParameter("search"),
                				request.getParameter("manufacturerid"),
                				request.getParameter("categoryid1"),
                				request.getParameter("sortby"),
                                                Integer.toString(pagenumber-1));
      	}

        if(pagenumber + 1 <= totalPages) {
      	   hrefNext =
           	StoreFrontUrls.getsearchresults(request,
                				company,
                				request.getParameter("description"),
                				Integer.toString(searchid),
                                              	Integer.toString(itemsfound),
                                              	request.getParameter("search"),
                				request.getParameter("manufacturerid"),
                				request.getParameter("categoryid1"),
                				request.getParameter("sortby"),
                                                Integer.toString(pagenumber+1));
    	}

        if(validParameter(request, "manufacturerid")) {
            GetManufacturerRequest getManufacturerRequest = new GetManufacturerRequest();
            getManufacturerRequest.setid(new Integer(request.getParameter("manufacturerid")).intValue());
            GetManufacturerResponse getManufacturerResponse = listsBean.GetManufacturer(getManufacturerRequest);
            manufacturerDescription = getManufacturerResponse.getmanufacturer().getshortdescription();
        }
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<%!
    public boolean validParameter(HttpServletRequest request, String parameter)
    {
        if(request.getParameter(parameter) != null && request.getParameter(parameter).compareToIgnoreCase("0") != 0)
            return true;
        return false;
    }
%>


<HTML>
    <SCRIPT LANGUAGE="JavaScript">
        function OnChangeBrand()
        {
        <%
            hrefs = StoreFrontUrls.getsearchresultsurl(request, company,
            	request.getParameter("description"), "0", "0", request.getParameter("search"), null,
                request.getParameter("categoryid1"), "0", "1");
        %>
            if(document.form1.brand.value == "all")
                window.location = "<%=hrefs%>" + "&manufacturerid=0";
            else
            {
                window.location = "<%=hrefs%>" + "&manufacturerid=" + document.form1.brand.value;
            }
        }

        function OnChangeSortBy()
        {
        <%
            hrefs = StoreFrontUrls.getsearchresultsurl(request, company,
            	request.getParameter("description"), "0", "0", request.getParameter("search"),
                request.getParameter("manufacturerid"),
                request.getParameter("categoryid1"), (String)null, "1");

            GetSortFieldsRequest getSortFieldsRequest = new GetSortFieldsRequest();
            getSortFieldsRequest.settype("search");
            GetSortFieldsResponse getSortFieldsResponse = listsBean.GetSortFields(getSortFieldsRequest);
            Iterator itSortFields = getSortFieldsResponse.getsortFieldsIterator();
            while(itSortFields.hasNext())
            {
                SortFields sortfields = (SortFields)itSortFields.next();
        %>
                if(document.form1.sortby.value == "<%=new Integer(sortfields.getid()).toString()%>")
                {
                    window.location = "<%=hrefs%>" + "&sortby=" + "<%=new Integer(sortfields.getid()).toString()%>";
                }
        <%
            }
        %>
        }
    </SCRIPT>
    <HEAD>
        <%@ include file="titlemetadata.jsp" %>
        <LINK href="<%=company.getbaseurl()%>storefront.css" rel="STYLESHEET"></LINK>
    </HEAD>
    <BODY leftMargin="0" topMargin="0" onload="" marginwidth="0" marginheight="0">
        <%@ include file="toppane.jsp" %>
        <table summary="<%=request.getAttribute("keyword1")%>" cellSpacing="0" cellPadding="0" border="0">
            <tr>
                <%@ include file="leftpane.jsp" %>
                <td vAlign="top" width="20">
                    <IMG src="<%=theme.getimagebaseurl()%>spacer.gif" border="0">
                </td>
                <td vAlign="top">
                    <form name="form1" action="<%=company.getbasesecureurl()%>search_result.jsp" method="get">
                    <input name="user" type="hidden" value="<%=request.getParameter("user")==null?"":request.getParameter("user")%>"/>
                    <input name="login" type="hidden" value="<%=request.getParameter("login")==null?"":request.getParameter("login")%>"/>
                    <table summary="<%=request.getAttribute("keyword2")%>" border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="5" height="15"></td>
                        </tr>
                        <tr>
                            <td class="producttitle"><%=searchTitle%></td>
                        </tr>
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="5" height="10"></td>
                        </tr>
                    </table>

                <%
                    if(request.getParameter("manufacturerid") != null)
                    {
                %>
                    <table summary="<%=request.getAttribute("keyword1")%>" cellSpacing="1" cellPadding="1" width="575" border="0">
                        <tr>
                            <td><%=manufacturerDescription%></td>
                        </tr>
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="5" height="5"></td>
                        </tr>
                    </table>
                <%
                    }
                %>
                    <table summary="<%=request.getAttribute("keyword2")%>" cellSpacing="1" cellPadding="1" width="575" border="0">
                        <tr>
                            <td align="left" valign="bottom">Brand:
                                <select name="brand" onchange="OnChangeBrand()">
                                    <option value="all">All</option>
                                <%
                                    GetManufacturersRequest getManufacturersRequest = new GetManufacturersRequest();
                                    getManufacturersRequest.setactive(true);
                                    GetManufacturersResponse getManufacturersResponse = listsBean.GetManufacturers(getManufacturersRequest);
                                    Iterator itManufacturers = getManufacturersResponse.getmanufacturersIterator();
                                    while(itManufacturers.hasNext())
                                    {
                                        Manufacturer manufacturer = (Manufacturer)itManufacturers.next();
                                        if(request.getParameter("manufacturerid") != null &&
                                            manufacturer.getid() == new Integer(request.getParameter("manufacturerid")).intValue())
                                        {
                                %>
                                            <option selected value="<%=new Integer(manufacturer.getid()).toString()%>"><%=manufacturer.getname()%></option>
                                <%
                                        }
                                        else
                                        {
                                %>
                                            <option value="<%=new Integer(manufacturer.getid()).toString()%>"><%=manufacturer.getname()%></option>
                                <%
                                        }
                                    }
                                %>
                                </select>
                            <%
                                if(itemsfound > 0)
                                {
                            %>
                                    &nbsp;
                                    Sort By:
                                    <select name="sortby" onchange="OnChangeSortBy()">
                            <%
                                    getSortFieldsRequest = new GetSortFieldsRequest();
                                    getSortFieldsRequest.settype("search");
                                    getSortFieldsResponse = listsBean.GetSortFields(getSortFieldsRequest);
                                    itSortFields = getSortFieldsResponse.getsortFieldsIterator();
                                    while(itSortFields.hasNext())
                                    {
                                        SortFields sortfields = (SortFields)itSortFields.next();
                                        if(sortfields.getid() == getUserResponse.getuser().getlastsortorder())
                                        {
                            %>
                                        <option selected value="<%=new Integer(sortfields.getid()).toString()%>"><%=sortfields.getdescription()%></option>
                            <%
                                        }
                                        else
                                        {
                            %>
                                        <option value="<%=new Integer(sortfields.getid()).toString()%>"><%=sortfields.getdescription()%></option>
                            <%
                                        }
                                    }
                            %>
                                    </select>
                            <%
                                }
                            %>
                            </td>
                            <td align="right">
                                <table summary="<%=request.getAttribute("keyword1")%>" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td>
                                            <%
                                                if(hrefPrevious != null)
                                                {
                                            %>
                                                    <a href="<%=hrefPrevious%>"><font color="red"><< previous</font></a>
                                            <%
                                                }
                                            %>
                                            &nbsp;
                                            <%
                                                if(hrefNext != null)
                                                {
                                            %>
                                                    <a href="<%=hrefNext%>"><font color="red">more results >></font></a>
                                            <%
                                                }
                                            %>
                                        </td>
                                    </tr>
                                </table>
                                <table summary="<%=request.getAttribute("keyword2")%>" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td>Search results: <%=new Integer(startindex).toString()%>-<%=new Integer(endindex).toString()%> of <%=new Integer(itemsfound).toString()%></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>

                    <table summary="<%=request.getAttribute("keyword1")%>" cellSpacing="1" cellPadding="3" width="575" border="0">
                    <%
                        if(itemsfound == 0)
                        {
                    %>
                            <tr>
                                <td colspan="5">
                                    <table summary="<%=request.getAttribute("keyword2")%>" border="0" cellpadding="0" cellspacing="0">
                                        <tr bgcolor="<%=theme.getcolor1()%>">
                                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="575" height="1" /></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                    <%
                        }
                    %>
                    <%
                    Iterator itSearchItems = getSearchResultsResponse.getitemsIterator();
                    boolean firsttime = true;
                    for(int i=0 ; itSearchItems.hasNext() ; i++)
                    {
                        Item item = (Item)itSearchItems.next();

                        boolean newrow = false;
                        if(i > (theme.getsearchresultcount() - 1))
                        {
                            i = 0;
                            newrow = true;
                        }

                        if(newrow || firsttime)
                        {
                    %>
                            </tr>
                            <tr>
                                <td colspan="5">
                                    <table summary="<%=request.getAttribute("keyword1")%>" border="0" cellpadding="0" cellspacing="0">
                                        <tr bgcolor="<%=theme.getcolor1()%>">
                                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="575" height="1" /></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr valign="bottom">
                    <%
                            firsttime = false;
                        }
                    %>
                            <td align="center">
                                <table summary="<%=request.getAttribute("keyword2")%>" border="0" cellspacing="1" cellpadding="1">
                                <tr>
                                    <td colspan="2" align="center"><a href="<%=StoreFrontUrls.getproductdetails(request, company, item)%>"> <img alt="<%=company.getkeyword()%> - <%=item.getmanufacturer()%> <%=item.getproductname()%>" src="<%=theme.getimagebaseurl()%><%=item.getdetails().getimageUrlSmall()%>" border="0"/></a></td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="center"><b><%=item.getmanufacturer()%> <%=item.getproductname()%></b></td>
                                </tr>
                                <tr>
                                    <td colspan="3" align="center" nowrap><strike><%=moneyFormat.format(item.getlistprice())%></strike> <font color="red"><%=moneyFormat.format(item.getourprice())%></font></td>
                                </tr>
                                <tr>
                                    <td align="center" noWrap><%=item.getavailability()%></td>
                                </tr>
                                </table>
                            </td>
                    <%
                    }
                    %>
                    </table>
                    </form>
                <%
                    if(itemsfound > 0)
                    {
                %>
                    <table summary="<%=request.getAttribute("keyword1")%>" cellSpacing="1" cellPadding="1" width="575" border="0">
                        <tr>
                            <td colspan="20">
                                <table summary="<%=request.getAttribute("keyword2")%>" border="0" cellpadding="0" cellspacing="0">
                                    <tr bgcolor="<%=theme.getcolor1()%>">
                                        <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="575" height="1" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="20">
                                <table summary="<%=request.getAttribute("keyword1")%>" border="0" cellspacing="0" cellpadding="0" width="575">
                                    <tr>
                                        <td align="right">Search results: <%=new Integer(startindex).toString()%>-<%=new Integer(endindex).toString()%> of <%=new Integer(itemsfound).toString()%></td>
                                    </tr>
                                </table>
                                <table summary="<%=request.getAttribute("keyword2")%>" border="0" cellspacing="0" cellpadding="0" width="575">
                                    <tr>
                                        <td align="right">
                                            <%
                                                if(hrefPrevious != null)
                                                {
                                            %>
                                                    <a href="<%=hrefPrevious%>"><font color="red"><< previous</font></a>
                                            <%
                                                }
                                            %>
                                            &nbsp;
                                            <%
                                                if(hrefNext != null)
                                                {
                                            %>
                                                    <a href="<%=hrefNext%>"><font color="red">more results >></font></a>
                                            <%
                                                }
                                            %>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                <%
                    }
                %>
                    <%@ include file="bottompane.jsp" %>
                </td>
                <%@ include file="rightpane.jsp" %>
            </tr>
        </table>
    </BODY>
</HTML>

