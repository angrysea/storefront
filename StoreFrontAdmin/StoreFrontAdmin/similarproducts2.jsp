<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    Theme theme = null;
    try {
        CompanyBean companyBean = new CompanyBean();
        GetThemeRequest getThemeRequest = new GetThemeRequest();
        getThemeRequest.setname("corporate");
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

    ListsBean listsBean = new ListsBean();
    ItemBean itemBean = new ItemBean();
    Item item = null;
    int nextItem = 0;
    int previousItem = 0;
    try {
        if(request.getParameter("id") != null)
        {
            GetItemRequest getItemRequest = new GetItemRequest();
            getItemRequest.setid(new Integer(request.getParameter("id")).intValue());
            GetItemResponse getItemResponse = itemBean.GetItem(getItemRequest);
            item = getItemResponse.getitem();

            nextItem = itemBean.GetNext(new Integer(request.getParameter("id")).intValue());
            previousItem = itemBean.GetPrev(new Integer(request.getParameter("id")).intValue());
        }

        SimilarProductsBean similarProductsBean = new SimilarProductsBean();
        if(request.getParameter("add") != null)
        {
            AddSimilarProductsRequest addSimilarProductsRequest = new AddSimilarProductsRequest();
            SimilarProduct similarProduct = new SimilarProduct();
            similarProduct.setitemnumber(item.getid());
            similarProduct.setsimilaritem(new Integer(request.getParameter("add")).intValue());
            addSimilarProductsRequest.setitems(similarProduct);
            similarProductsBean.AddSimilarProducts(addSimilarProductsRequest);
        }
        if(request.getParameter("remove") != null)
        {
            DeleteSimilarProductsRequest deleteSimilarProductsRequest = new DeleteSimilarProductsRequest();
            SimilarProduct similarProduct = new SimilarProduct();
            similarProduct.setitemnumber(item.getid());
            similarProduct.setsimilaritem(new Integer(request.getParameter("remove")).intValue());
            deleteSimilarProductsRequest.setitems(similarProduct);
            similarProductsBean.DeleteSimilarProducts(deleteSimilarProductsRequest);
        }
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>


<HTML>
    <SCRIPT LANGUAGE="JavaScript">
        function OnChangeManufacturer()
        {
            document.form1.submit();
        }
    </SCRIPT>
    <HEAD>
        <LINK href="./storefront.css" rel="STYLESHEET"></LINK>
    </HEAD>
    <BODY leftMargin="0" topMargin="0" onload="" marginwidth="0" marginheight="0">
        <%@ include file="toppane.jsp" %>
        <table cellSpacing="0" cellPadding="0" border="0">
            <tr>
                <%@ include file="leftpane.jsp" %>
                <td vAlign="top" width="20">
                    <IMG src="./images/spacer.gif" border="0">
                </td>
                <td vAlign="top">
                    <table id="headingTable" height="100%" border="0">
                        <tr>
                            <td><img src="./images/spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td class="producttitle">Similar Products Wizard</td>
                        </tr>
                        <tr>
                            <td><img src="./images/spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td><h2>Step 2 - Choose Related Products</h2></td>
                        </tr>
                    </table>
                    <br />
                    <table cellSpacing="1" cellPadding="1" width="700" border="0">
                        <input name="id" type="hidden" value="<%=new Integer(item.getid()).toString()%>"/>
                        <tr>
                            <td><a href="./similarproducts2.jsp?id=<%=new Integer(previousItem).toString()%>"><< previous</a></td>
                            <td align="right"><a href="./similarproducts2.jsp?id=<%=new Integer(nextItem).toString()%>">next >></a></td>
                        </tr>
                    </table>
                    <br />
                    <table cellSpacing="1" cellPadding="1" width="775" border="0">
                        <tr>
                            <td class="producttitle" noWrap><%=item.getmanufacturer()%>: <%=item.getproductname()%></td>
                        </tr>
                        <tr>
                            <td nowrap>Item: <%=item.getisin()%></td>
                        </tr>
                    </table>
                    <table cellSpacing="0" cellPadding="0" width="775" border="0">
                        <tr>
                            <td align="center" valign="middle">
  			        <img border="0" src="<%=theme.getimagebaseurl()%><%=item.getdetails().getimageUrlSmall()%>" />
                            </td>
                            <td nowrap>&nbsp;&nbsp;</td>
                            <td bgcolor="<%=theme.getcolor1()%>" rowspan="250">
                                <img src="<%=theme.getimagebaseurl()%>spacer2.gif" width="1" height="1" />
                            </td>
                            <td nowrap>&nbsp;&nbsp;</td>
                            <td width="600">
                                <table border="0" cellspacing="1" cellpadding="1">
                                    <tr>
                                        <td colspan=2><b>Product Description:</b></td>
                                    </tr>
                                    <tr>
                                        <td colspan=2><%=item.getdetails().getdescription()%></td>
                                    </tr>
                                    <tr>
                                        <td><br></td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <table border="0" cellspacing="1" cellpadding="1">
                                                <tr>
                                                    <td nowrap>Retail Price:</td>
                                                    <td nowrap><%=moneyFormat.format(item.getlistprice())%></td>
                                                </tr>
                                                <tr>
                                                    <td nowrap>Our Price:</td>
                                                    <td nowrap><%=moneyFormat.format(item.getourprice())%></td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
            <%
                SimilarProductsBean similarProductsBean = new SimilarProductsBean();
                GetSimilarProductsRequest getSimilarProductsRequest = new GetSimilarProductsRequest();
                getSimilarProductsRequest.setid(item.getid());
                GetSimilarProductsResponse getSimilarProductsResponse = similarProductsBean.GetSimilarProducts(getSimilarProductsRequest);
                Iterator itItems = getSimilarProductsResponse.getitemsIterator();
                if(itItems.hasNext())
                {

            %>
                    <table border="0">
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td colspan="5">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <tr bgcolor="<%=theme.getcolor1()%>">
                                        <td><img src="<%=theme.getimagebaseurl()%>spacer.gif" width="775" height="1" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>

                    <table border="0">
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
                                <table width="180" cellSpacing="0" cellPadding="0" border="0">
                                    <tr>
                                        <td>
                                            <table border="0" cellspacing="1" cellpadding="1">
                                                <tr>
                                                    <td colspan="2" align="center"><img alt="Knife - <%=similaritem.getmanufacturer()%> <%=similaritem.getproductname()%>" src="<%=theme.getimagebaseurl()%><%=similaritem.getdetails().getimageUrlSmall()%>" border="0"/></td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2" align="center"><b><%=similaritem.getmanufacturer()%> <%=similaritem.getproductname()%></b></td>
                                                </tr>
                                                <tr>
                                            <%
                                                if(request.getParameter("manufacturer") != null)
                                                {
                                            %>
                                                    <td align="center"><a href="./similarproducts2.jsp?id=<%=new Integer(item.getid()).toString()%>&remove=<%=new Integer(similaritem.getid()).toString()%>&manufacturer=<%=request.getParameter("manufacturer")%>">remove</a></td>
                                            <%
                                                }
                                                else
                                                {
                                            %>
                                                    <td align="center"><a href="./similarproducts2.jsp?id=<%=new Integer(item.getid()).toString()%>&remove=<%=new Integer(similaritem.getid()).toString()%>">remove</a></td>
                                            <%
                                                }
                                            %>
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

                    <table border="0">
                        <tr>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td colspan="5">
                                <table border="0" cellpadding="0" cellspacing="0">
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

                    <form name="form1" action="./similarproducts2.jsp" method="GET">
                    <input type="hidden" name="id" value="<%=new Integer(item.getid()).toString()%>"/>
                    <table>
                        <tr>
                            <td noWrap>Select a Manufacturer:</td>
                            <td>
                                <select name="manufacturer" onchange="OnChangeManufacturer()">
                                    <option value="-1">< select ></option>
                                    <%
	                                GetManufacturersResponse getManufacturersResponse = listsBean.GetManufacturers(new GetManufacturersRequest());
                                        Iterator itManufacturer = getManufacturersResponse.getmanufacturersIterator();
                                        Manufacturer manufacturer = null;
                                        while(itManufacturer.hasNext())
                                        {
                                            manufacturer = (Manufacturer)itManufacturer.next();
                                    %>
                                            <option value="<%=manufacturer.getid()%>"><%=manufacturer.getname()%></option>
                                    <%
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                    </table>
                    </form>
                    <TABLE cellSpacing="1" cellPadding="3" width="775" border="0">
                        <tr>
            <%
                            GetItemsRequest getItemsRequest = new GetItemsRequest();
                            GetItemsResponse getItemsResponse = null;
                            if(request.getParameter("manufacturer") != null)
                            {
                                getItemsRequest.setselectby("manufacturer");
                                getItemsRequest.setid(new Integer(request.getParameter("manufacturer")).intValue());
                                getItemsResponse = similarProductsBean.GetNotSimilarProducts(item.getid(), getItemsRequest);
                                Iterator itPossibleItems = getItemsResponse.getitemsIterator();

                                for(int i=0 ; itPossibleItems.hasNext() ; i++)
                                {
                                    Item possibleitem = (Item)itPossibleItems.next();
            %>
                        <td align="center">
                            <table width="180" cellSpacing="0" cellPadding="0" border="0">
                                <tr>
                                    <td>
                                        <table border="0" cellspacing="1" cellpadding="1">
                                            <tr>
                                                <td colspan="2" align="center"><img src="<%=theme.getimagebaseurl()%><%=possibleitem.getdetails().getimageUrlSmall()%>" border="0"/></td>
                                            </tr>
                                            <tr>
                                                <td colspan="2" align="center"><b><%=possibleitem.getmanufacturer()%> <%=possibleitem.getproductname()%></b></td>
                                            </tr>
                                            <tr>
                                                <td align="center"><a href="./similarproducts2.jsp?id=<%=new Integer(item.getid()).toString()%>&add=<%=new Integer(possibleitem.getid()).toString()%>&manufacturer=<%=request.getParameter("manufacturer")%>">add</a></td>
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
                        }
            %>
                    </TABLE>
                </td>
            </tr>
        </table>
    </BODY>
    <SCRIPT LANGUAGE="JavaScript">
        <%
            if(request.getParameter("manufacturer") != null)
            {
        %>
                document.form1.manufacturer.value = '<%=request.getParameter("manufacturer")%>';
        <%
            }
        %>
    </SCRIPT>
</HTML>
