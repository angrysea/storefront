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
    Company basecompany = null;
    try {
        CompanyBean companyBean = new CompanyBean();
        GetCompanyRequest getCompanyRequest = new GetCompanyRequest();
        if(request.getParameter("company") != null)
            getCompanyRequest.setid(new Integer(request.getParameter("company")).intValue());
        GetCompanyResponse getCompanyResponse = companyBean.GetCompany(request, getCompanyRequest);
        company = getCompanyResponse.getcompany();

        getCompanyResponse = companyBean.GetCompany(request, new GetCompanyRequest());
        basecompany = getCompanyResponse.getcompany();

        GetThemeRequest getThemeRequest = new GetThemeRequest();
        getThemeRequest.setid(new Integer(company.getthemeid()).toString());
        GetThemeResponse getThemeResponse = companyBean.GetTheme(request, getThemeRequest);
        theme = getThemeResponse.gettheme();
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
    Item item = null;
    try {
        ItemBean itemBean = new ItemBean();
        GetItemRequest getItemRequest = new GetItemRequest();
        getItemRequest.setid(new Integer(request.getParameter("itemid")).intValue());
        getItemResponse = itemBean.GetItem(getItemRequest);
        item = getItemResponse.getitem();

        ListsBean lists = new ListsBean();
        GetManufacturerRequest getManufacturerRequest = new GetManufacturerRequest();
        getManufacturerRequest.setid(item.getmanufacturerid());
        getManufacturerResponse = lists.GetManufacturer(getManufacturerRequest);

        // Set the keywords for this page
        request.setAttribute("keyword1", item.getmanufacturer() + " " + company.getkeyword());
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
            var picURL=picName.src;
            newWindow=window.open(picURL, 'newWin', 'toolbar=no, width='+picName.width+', height='+picName.height);
            newWindow.document.write('<html><head><title>'+picTitle+'<\/title><\/head><body background="'+picURL+'"><\/body><\/html>');
            newWindow.resizeBy(picName.width-newWindow.document.body.clientWidth, picName.height-newWindow.document.body.clientHeight);
            newWindow.focus();
        }
    </SCRIPT>
    <HEAD>
        <%@ include file="minisite_titlemetadata.jsp" %>
        <LINK href="<%=company.getbaseurl()%>minisite.css" rel="STYLESHEET"></LINK>
    </HEAD>
    <BODY leftMargin="0" topMargin="0" onload="" marginwidth="0" marginheight="0">
        <%@ include file="minisite_toppane.jsp" %>
        <table summary="<%=request.getAttribute("keyword1")%>" cellSpacing="0" cellPadding="0" border="0">
            <tr>
                <td vAlign="top" width="20">
                    <IMG src="<%=theme.getimagebaseurl()%>spacer.gif" border="0">
                </td>
                <td vAlign="top">
                    <table summary="<%=request.getAttribute("keyword1")%>" id="headingTable" border="0">
                        <tr>
                            <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="5" height="5"></td>
                        </tr>
                    </table>
                    <br>
                    <table summary="<%=request.getAttribute("keyword1")%>" cellSpacing="1" cellPadding="1" width="725" border="0">
                        <tr>
                            <td class="producttitle" noWrap><%=item.getmanufacturer()%>: <%=item.getproductname()%></td>
                        </tr>
                        <tr>
                            <td nowrap>Item: <%=item.getisin()%></td>
                        </tr>
                    </table>
                    <table summary="<%=request.getAttribute("keyword1")%>" cellSpacing="0" cellPadding="0" width="725" border="0">
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
                                String description = null;
                                if(item.getdetails().getalt_description() != null && item.getdetails().getalt_description().length() > 0)
                                    description = item.getdetails().getalt_description();
                                else if(item.getdetails().getdescription() != null && item.getdetails().getdescription().length() > 0)
                                    description = item.getdetails().getdescription();
                                if(description != null)
                                {
                            %>
                                    <tr>
                                        <td><b>Product Description:</b></td>
                                    </tr>
                                    <tr>
                                        <td><%=description%></td>
                                    </tr>
                                    <tr>
                                        <td><br></td>
                                    </tr>
                            <%
                                }
                            %>
                                    <tr>
                                        <td>
                                            <table summary="<%=request.getAttribute("keyword1")%>" border="0" cellspacing="1" cellpadding="1">
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
                                                <td nowrap>Retail Price:</td>
                                                <td nowrap><%=moneyFormat.format(item.getlistprice())%></td>
                                            </tr>
                                            <tr>
                                                <td nowrap>Your Price:</td>
                                                <td nowrap class="productprice"><font color="red"><%=moneyFormat.format(item.getourprice())%></font></td>
                                            </tr>
                                            <tr>
                                                <td><br></td>
                                            </tr>
                                            <tr>
                                                <td colspan="2" nowrap><a href="<%=basecompany.getbaseurl()%>shoppingcart.jsp?task=add&itemid=<%=item.getid()%>&quantity=1" target="_blank"><font color="red"><b>Click here to order now...</b></font></a></td>
                                            </tr>
                                            <tr>
                                                <td><br></td>
                                            </tr>
                                            <tr>
                                                <td colspan="2" nowrap><a href="<%=company.getbaseurl()%>minisite_index.jsp?company=<%=new Integer(company.getid()).toString()%>" title="<%=request.getAttribute("keyword1")%> - Home">Back to the Home Page</a></td>
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
                                <table summary="<%=request.getAttribute("keyword1")%>" border="0" cellpadding="0" cellspacing="0">
                                    <tr bgcolor="<%=theme.getcolor1()%>">
                                        <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="725" height="1" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>

                    <table summary="<%=request.getAttribute("keyword1")%>" border="0">
                        <tr>
                            <td nowrap><b>Product Variations:</b></td>
                        </tr>
                        <tr>
            <%
                    for(int i=0 ; itItems.hasNext() ; i++)
                    {
                        Item similaritem = (Item)itItems.next();
            %>
                            <td align="center">
                                <table summary="<%=request.getAttribute("keyword1")%>" width="180" cellSpacing="0" cellPadding="0" border="0">
                                    <tr>
                                        <td>
                                            <table summary="<%=request.getAttribute("keyword1")%>" border="0" cellspacing="1" cellpadding="1">
                                                <tr>
                                                    <td colspan="2" align="center"><a href="<%=company.getbaseurl()%>minisite_productdetails.jsp?company=<%=new Integer(company.getid()).toString()%>&itemid=<%=new Integer(similaritem.getid()).toString()%>"><img alt="<%=company.getkeyword()%> - <%=similaritem.getmanufacturer()%> <%=similaritem.getproductname()%>" src="<%=theme.getimagebaseurl()%><%=similaritem.getdetails().getimageUrlSmall()%>" border="0"/></a></td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2" align="center"><b><%=similaritem.getmanufacturer()%> <%=similaritem.getproductname()%></b></td>
                                                </tr>
                                                <tr>
                                                    <td align="center">List Price: <%=moneyFormat.format(similaritem.getlistprice())%></td>
                                                </tr>
                                                <tr>
                                                    <td align="center">Our Price: <font color="red"><%=moneyFormat.format(similaritem.getourprice())%></font></td>
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
            <%
                }
            %>

                    <table summary="<%=request.getAttribute("keyword1")%>" border="0">
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td colspan="5">
                                <table summary="<%=request.getAttribute("keyword1")%>" border="0" cellpadding="0" cellspacing="0">
                                    <tr bgcolor="<%=theme.getcolor1()%>">
                                        <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="725" height="1" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>

                    <table summary="<%=request.getAttribute("keyword1")%>" border="0" width="725">
                        <tr>
                            <td nowrap><b>About <%=item.getmanufacturer()%></b></td>
                        </tr>
                        <tr>
                            <td><%=getManufacturerResponse.getmanufacturer().getdescription()%></td>
                        </tr>
                    </table>
                    <%@ include file="minisite_bottompane.jsp" %>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>

