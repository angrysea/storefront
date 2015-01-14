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

    GetItemResponse getItemResponse = new GetItemResponse();
    GetManufacturerResponse getManufacturerResponse = new GetManufacturerResponse();
    GetUserResponse getUserResponse = null;
    Item item = null;
    try {
        UserBean userBean = new UserBean();
        getUserResponse = userBean.GetUser(request, response);

        ItemBean itemBean = new ItemBean();
        GetItemRequest getItemRequest = new GetItemRequest();
        getItemRequest.setid(new Integer(request.getParameter("itemid")).intValue());
        getItemResponse = itemBean.GetItem(getItemRequest, getUserResponse.getuser());
        item = getItemResponse.getitem();

        ListsBean lists = new ListsBean();
        GetManufacturerRequest getManufacturerRequest = new GetManufacturerRequest();
        getManufacturerRequest.setid(item.getmanufacturerid());
        getManufacturerResponse = lists.GetManufacturer(getManufacturerRequest);

        // Set the keywords for this page
        request.setAttribute("keyword1", item.getmanufacturer() + " " + company.getkeyword());
        request.setAttribute("keyword2", item.getproductname() + " " + company.getkeyword());
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>


<HTML>
    <SCRIPT LANGUAGE="JavaScript">
        //preload image file
        largeimage = new Image()
        largeimage.src = "<%=theme.getimagebaseurl()%><%=item.getdetails().getimageUrlLarge()%>"

        function DetectImageSize(picName, picTitle)
        {
            picURL=picName.src;
            newWindow=window.open(picURL, 'newWin', 'toolbar=no, width='+picName.width+', height='+picName.height);
            newWindow.document.write('<html><head><title>'+picTitle+'<\/title><\/head><body background="'+picURL+'"><\/body><\/html>');
            newWindow.resizeBy(picName.width-newWindow.document.body.clientWidth, picName.height-newWindow.document.body.clientHeight);
            newWindow.focus();
        }

        function OnClickAddToCart()
        {
            window.location = "<%=StoreFrontUrls.getshoppingcart(request, company, item, "add", 1)%>";
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
                    <table summary="<%=request.getAttribute("keyword2")%>" id="headingTable" border="0">
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="5" height="5"></td>
                        </tr>
                    </table>
                    <br>
                    <table summary="<%=request.getAttribute("keyword1")%>" cellSpacing="1" cellPadding="1" width="775" border="0">
                        <tr>
                            <td class="producttitle" noWrap><%=item.getmanufacturer()%>: <%=item.getproductname()%></td>
                        </tr>
                        <tr>
                            <td nowrap>Item: <%=item.getisin()%></td>
                        </tr>
                    </table>
                    <table summary="<%=request.getAttribute("keyword2")%>" cellSpacing="0" cellPadding="0" width="775" border="0">
                        <tr>
                            <td align="center" valign="middle">
                        <%
                            if(item.getdetails().getimageUrlLarge() != null && item.getdetails().getimageUrlLarge().length() > 0)
                            {
                        %>
                                <a href="javascript:DetectImageSize(largeimage,'<%=item.getproductname()%>')">
				    <img border="0" align="medium image" src="<%=theme.getimagebaseurl()%><%=item.getdetails().getimageUrlMedium()%>" />
                                </a>
                        <%
                            }
                            else
                            {
                        %>
				    <img border="0" align="medium image" src="<%=theme.getimagebaseurl()%><%=item.getdetails().getimageUrlMedium()%>" />
                        <%
                            }
                        %>
                            </td>
                            <td nowrap>&nbsp;&nbsp;</td>
                            <td bgcolor="<%=theme.getcolor1()%>" rowspan="250">
                                <img src="<%=theme.getimagebaseurl()%>spacer.gif" width="1" height="1" />
                            </td>
                            <td nowrap>&nbsp;&nbsp;</td>
                            <td width="375">
                                <table summary="<%=request.getAttribute("keyword1")%>" border="0" cellspacing="1" cellpadding="1">
                            <%
                                if(item.getdetails().getdescription() != null && item.getdetails().getdescription().length() > 0)
                                {
                            %>
                                    <tr>
                                        <td><b>Product Description:</b></td>
                                    </tr>
                                    <tr>
                                        <td><%=item.getdetails().getdescription()%></td>
                                    </tr>
                                    <tr>
                                        <td><br></td>
                                    </tr>
                            <%
                                }
                            %>
                                    <tr>
                                        <td>
                                            <table summary="<%=request.getAttribute("keyword2")%>" border="0" cellspacing="1" cellpadding="1">
                                            <tr>
                                                <td colspan="2" noWrap><b>Specifications:</b></td>
                                            </tr>
                                            <%
                                                Iterator itSpecs = getItemResponse.getitem().getdetails().getspecificationsIterator();
                                                while(itSpecs.hasNext())
                                                {
                                                    ItemSpecification itemSpecification = (ItemSpecification)itSpecs.next();
                                            %>
                                                    <tr>
                                                        <td noWrap><%=itemSpecification.getname()%>:</td>
                                                        <td noWrap><%=itemSpecification.getdescription()%></td>
                                                    </tr>
                                            <%
                                                }
                                            %>
                                            <tr>
                                                <td><br></td>
                                            </tr>
                                            <tr>
                                                <td nowrap>List Price:</td>
                                                <td nowrap><%=moneyFormat.format(item.getlistprice())%></td>
                                            </tr>
                                            <tr>
                                                <td nowrap>Your Price:</td>
                                                <td nowrap class="productprice"><%=moneyFormat.format(item.getourprice())%></td>
                                            </tr>
                                            <tr>
                                                <td><br></td>
                                            </tr>
                                            <tr>
                                                <td nowrap>Status:</td>
                                                <td><%=item.getstatus()%></td>
                                            </tr>
                                            <tr>
                                                <td><br></td>
                                            </tr>
                                            <tr>
                                                <td><input type="image" alt="add to cart" src="<%=theme.getimagebaseurl()%>addtocart_button.gif" onclick="OnClickAddToCart()"/></td>
                                            </tr>
                                                <tr>
                                                    <td><br></td>
                                                </tr>
                                                <tr>
                                                    <td nowrap><a href="#" onClick="history.go(-1)">continue shopping</a></td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                    <br>

            <%
                SimilarProductsBean similarProductsBean = new SimilarProductsBean();
                GetSimilarProductsRequest getSimilarProductsRequest = new GetSimilarProductsRequest();
                getSimilarProductsRequest.setid(item.getid());
                GetSimilarProductsResponse getSimilarProductsResponse = similarProductsBean.GetSimilarProducts(getSimilarProductsRequest);
                Iterator itItems = getSimilarProductsResponse.getitemsIterator();
                if(itItems.hasNext())
                {

            %>
                    <table summary="<%=request.getAttribute("keyword1")%>" border="0">
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td colspan="5">
                                <table summary="<%=request.getAttribute("keyword2")%>" border="0" cellpadding="0" cellspacing="0">
                                    <tr bgcolor="<%=theme.getcolor1()%>">
                                        <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="775" height="1" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>

                    <table summary="<%=request.getAttribute("keyword1")%>" border="0">
                        <tr>
                            <td nowrap><b>Similar Products:</b></td>
                        </tr>
                        <tr>
            <%
                    for(int i=0 ; itItems.hasNext() ; i++)
                    {
                        Item similaritem = (Item)itItems.next();
            %>
                            <td align="center">
                                <table summary="<%=request.getAttribute("keyword2")%>" width="180" cellSpacing="0" cellPadding="0" border="0">
                                    <tr>
                                        <td>
                                            <table summary="<%=request.getAttribute("keyword1")%>" border="0" cellspacing="1" cellpadding="1">
                                                <tr>
                                                    <td colspan="2" align="center"><a href="<%=StoreFrontUrls.getproductdetails(request, company, similaritem)%>"><img alt="<%=company.getkeyword()%> - <%=similaritem.getmanufacturer()%> <%=similaritem.getproductname()%>" src="<%=theme.getimagebaseurl()%><%=similaritem.getdetails().getimageUrlSmall()%>" border="0"/></a></td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2" align="center"><b><%=similaritem.getmanufacturer()%> <%=similaritem.getproductname()%></b></td>
                                                </tr>
                                                <tr>
                                                    <td colspan="3" align="center" nowrap><strike><%=moneyFormat.format(similaritem.getlistprice())%></strike> <font color="red"><%=moneyFormat.format(similaritem.getourprice())%></font></td>
                                                </tr>
                                                <tr>
                                                    <td align="center" noWrap><%=similaritem.getavailability()%></td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
            <%
                        if(i > 2)
                        {
                            i = -1;
            %>
                        </tr>
                        <tr>
            <%
                        }
                    }
            %>
                        </tr>
                    </table>

                    <table summary="<%=request.getAttribute("keyword2")%>" border="0">
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td colspan="5">
                                <table summary="<%=request.getAttribute("keyword1")%>" border="0" cellpadding="0" cellspacing="0">
                                    <tr bgcolor="<%=theme.getcolor1()%>">
                                        <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="775" height="1" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
            <%
                }
            %>
                    <table summary="<%=request.getAttribute("keyword2")%>" border="0" width="775">
                        <tr>
                            <td nowrap><b>About <%=item.getmanufacturer()%></b></td>
                        </tr>
                        <tr>
                            <td><%=getManufacturerResponse.getmanufacturer().getdescription()%></td>
                        </tr>
                    </table>
            <%
                RecentlyViewedBean recentlyViewedBean = new RecentlyViewedBean();
                RecentlyViewedRequest recentlyViewedRequest = new RecentlyViewedRequest();
                recentlyViewedRequest.setid(getUserResponse.getuser().getid());
                recentlyViewedRequest.setcount(5);
                RecentlyViewedResponse recentlyViewedResponse = recentlyViewedBean.GetRecentlyViewed(recentlyViewedRequest);

                Iterator itRecentlyViewed = recentlyViewedResponse.getrecentlyviewedIterator();
                if(itRecentlyViewed.hasNext())
                {
                    itRecentlyViewed.next();
            %>
                    <br>
                    <br>
                    <br>
                    <br>
                    <table summary="<%=request.getAttribute("keyword1")%>" border="0" width="775">
                        <tr>
                            <td colspan="5">
                                <table summary="<%=request.getAttribute("keyword2")%>" border="0" cellpadding="0" cellspacing="0">
                                    <tr bgcolor="<%=theme.getcolor1()%>">
                                        <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="775" height="1" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                    <table summary="<%=request.getAttribute("keyword1")%>" border="0">
                        <tr>
                            <td nowrap><b>Recently Viewed Products:</b></td>
                        </tr>
                        <tr>
            <%
                    while(itRecentlyViewed.hasNext())
                    {
                        Item recentitem = (Item)itRecentlyViewed.next();
            %>
                            <td align="center">
                                <table summary="<%=request.getAttribute("keyword2")%>" width="180" cellSpacing="0" cellPadding="0" border="0">
                                    <tr>
                                        <td>
                                            <table summary="<%=request.getAttribute("keyword1")%>" border="0" cellspacing="1" cellpadding="1">
                                                <tr>
                                                    <td colspan="2" align="center"><a href="<%=StoreFrontUrls.getproductdetails(request, company, recentitem)%>"><img alt="<%=company.getkeyword()%> - <%=recentitem.getmanufacturer()%> <%=recentitem.getproductname()%>" src="<%=theme.getimagebaseurl()%><%=recentitem.getdetails().getimageUrlSmall()%>" border="0"/></a></td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2" align="center"><b><%=recentitem.getmanufacturer()%> <%=recentitem.getproductname()%></b></td>
                                                </tr>
                                                <tr>
                                                    <td colspan="3" align="center" nowrap><strike><%=moneyFormat.format(recentitem.getlistprice())%></strike> <font color="red"><%=moneyFormat.format(recentitem.getourprice())%></font></td>
                                                </tr>
                                                <tr>
                                                    <td align="center" noWrap><%=recentitem.getavailability()%></td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
            <%
                    }
            %>
                        </tr>
                    </table>
            <%
                }
            %>
                    <%@ include file="bottompane.jsp" %>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>

