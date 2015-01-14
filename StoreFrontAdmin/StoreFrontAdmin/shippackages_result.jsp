<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
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
    int count = 0;
    ShipPackagesResponse shipPackagesResponse = null;
    try
    {
        ShippingBean shippingBean = new ShippingBean();
        ShipPackagesRequest shipPackagesRequest = new ShipPackagesRequest();

        PackingslipBean packingslipBean = new PackingslipBean();
        GetPackingslipsResponse getPackingslipsResponse = packingslipBean.GetOpenPackingslips(new GetPackingslipsRequest());

        // Determine which items were selected
        Iterator itPackingslips = getPackingslipsResponse.getpackingslipIterator();
        while(itPackingslips.hasNext())
        {
            Packingslip packingslip = (Packingslip)itPackingslips.next();

            String packingslipParameter = "packingslip" + new Integer(packingslip.getid()).toString();
            if(request.getParameter(packingslipParameter) != null && request.getParameter(packingslipParameter).compareToIgnoreCase("on") == 0)
            {
                String trackingnumberParam = "trackingnumber" + new Integer(packingslip.getid()).toString();
                if(request.getParameter(trackingnumberParam) != null)
                    packingslip.settrackingNumber(request.getParameter(trackingnumberParam));
                shipPackagesRequest.setpackingslip(packingslip);
                count++;
            }
        }

        shipPackagesResponse = shippingBean.ShipPackages(shipPackagesRequest);
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>

<HTML>
    <%@ include file="commonscript.jsp" %>
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
                    <table border="0">
                        <tr>
                            <td><img src="./images/spacer.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                            <td class="producttitle">Shipping Packages Processed</td>
                        </tr>
                        <tr>
                            <td><br /></td>
                        </tr>
                    </table>
                    <table cellSpacing="1" cellPadding="3" width="700" border="0">
                        <tr>
                    <%
                        if(count == 1)
                        {
                    %>
                            <td><%=new Integer(count).toString()%> package was processed and an e-mail was sent to the customer.</td>
                    <%
                        }
                        else
                        {
                    %>
                            <td><%=new Integer(count).toString()%> packages were processed and e-mail's were sent to the customers.</td>
                    <%
                        }
                    %>
                        </tr>

                    </table>
                </td>
            </tr>
        </table>
    </BODY>
    <SCRIPT LANGUAGE="JavaScript">
    </SCRIPT>
</HTML>
