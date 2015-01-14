<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    AddItemResponse addItemResponse = new AddItemResponse();
    try {
        ItemBean itembean = new ItemBean();

	// Store the item information
        AddItemRequest addItemRequest = new AddItemRequest();
        Item item = new Item();
	item.setisin(request.getParameter("inventoryid"));
        item.setproductname(request.getParameter("productname"));
        item.setlistprice(new Double(request.getParameter("listprice")).doubleValue());
        item.setourcost(new Double(request.getParameter("ourcost")).doubleValue());
        item.setourprice(new Double(request.getParameter("ourprice")).doubleValue());
        item.setmanufacturerid(new Integer(request.getParameter("manufacturer")).intValue());
        item.setdistributorid(new Integer(request.getParameter("distributor")).intValue());
        item.setstatusid(new Integer(request.getParameter("status")).intValue());

        Details details = new Details();
        if(request.getParameter("distributoritemnumber") != null)
            details.setdistributoritemnumber(request.getParameter("distributoritemnumber"));
        details.setshippingweight(new Double(request.getParameter("shippingweight")).doubleValue());
        details.setimageUrlSmall(request.getParameter("smallimageurl"));
        details.setimageUrlMedium(request.getParameter("mediumimageurl"));
        details.setimageUrlLarge(request.getParameter("largeimageurl"));
        if(request.getParameter("description") != null)
            details.setdescription(request.getParameter("description"));
        details.setmanufactureritemnumber(request.getParameter("manufactureritemnumber"));
        details.sethandlingcharges(new Double(request.getParameter("handlingfee")).doubleValue());

        // Store the categories
        ListsBean lists = new ListsBean();
        GetCategoriesRequest getCategoriesRequest = new GetCategoriesRequest();
        getCategoriesRequest.setactive(true);
        getCategoriesRequest.setgroup(0);
        GetCategoriesResponse getCategoriesResponse = lists.GetCategories(getCategoriesRequest);
        Iterator itCategories = getCategoriesResponse.getcategoriesIterator();
        while(itCategories.hasNext())
        {
            Category category = (Category)itCategories.next();
            String searchfor = "category" + new Integer(category.getid()).toString();
            String value = request.getParameter(searchfor);
            if(value != null && value.compareToIgnoreCase("on") == 0)
            {
                details.setcategories(category);
            }
        }

        // Store the specifications
        GetSpecificationsResponse getSpecificationsResponse = lists.GetSpecifications(new GetSpecificationsRequest());
        Iterator itSpecs = getSpecificationsResponse.getspecificationsIterator();
        while(itSpecs.hasNext())
        {
            Specification specification = (Specification)itSpecs.next();
            String searchfor = "specification" + new Integer(specification.getid()).toString();
            String value = request.getParameter(searchfor);
            if(value.length() > 0)
            {
                ItemSpecification itemSpecification = new ItemSpecification();
                itemSpecification.setspecid(specification.getid());
                itemSpecification.setdescription(value);
                details.setspecifications(itemSpecification);
            }
        }


	item.setdetails(details);
	addItemRequest.setitem(item);
        addItemResponse = itembean.AddItem(addItemRequest);
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>


<HTML>
    <SCRIPT LANGUAGE="JavaScript">
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
                    <table id="headingTable" border="0">
                        <tr>
                            <td><img src="./images/spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td class="producttitle">Add Product Result</td>
                        </tr>
                    </table>
                    <br>
                    <TABLE cellSpacing="1" cellPadding="3" width="95%" border="0">
                    <tr>
                        <td noWrap>The product [<%=request.getParameter("productname")%>] was added successfully.</td>
                    </tr>
                    </TABLE>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>

