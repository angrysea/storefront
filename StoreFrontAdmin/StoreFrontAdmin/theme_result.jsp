<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    try {
        CompanyBean companyBean = new CompanyBean();
        UpdateThemeRequest updateThemeRequest = new UpdateThemeRequest();

        Theme theme = new Theme();
        theme.setid(new Integer(request.getParameter("id")).intValue());
        if(request.getParameter("themename") != null)
            theme.setname(request.getParameter("themename"));
        if(request.getParameter("color1") != null)
            theme.setcolor1(request.getParameter("color1"));
        if(request.getParameter("color2") != null)
            theme.setcolor2(request.getParameter("color2"));
        if(request.getParameter("color3") != null)
            theme.setcolor3(request.getParameter("color3"));
        if(request.getParameter("color4") != null)
            theme.setcolor4(request.getParameter("color4"));
        if(request.getParameter("color5") != null)
            theme.setcolor5(request.getParameter("color5"));
        if(request.getParameter("image1") != null)
            theme.setimage1(request.getParameter("image1"));
        if(request.getParameter("image2") != null)
            theme.setimage2(request.getParameter("image2"));
        if(request.getParameter("image3") != null)
            theme.setimage3(request.getParameter("image3"));
        if(request.getParameter("image4") != null)
            theme.setimage4(request.getParameter("image4"));
        if(request.getParameter("image5") != null)
            theme.setimage5(request.getParameter("image5"));
        if(request.getParameter("heading1") != null)
            theme.setheading1(request.getParameter("heading1"));
        if(request.getParameter("heading2") != null)
            theme.setheading2(request.getParameter("heading2"));
        if(request.getParameter("heading3") != null)
            theme.setheading3(request.getParameter("heading3"));
        if(request.getParameter("heading4") != null)
            theme.setheading4(request.getParameter("heading4"));
        if(request.getParameter("heading5") != null)
            theme.setheading5(request.getParameter("heading5"));
        if(request.getParameter("titleinfo") != null)
            theme.settitleinfo(request.getParameter("titleinfo"));
        if(request.getParameter("metacontenttype") != null)
            theme.setmetacontenttype(request.getParameter("metacontenttype"));
        if(request.getParameter("metakeywords") != null)
            theme.setmetakeywords(request.getParameter("metakeywords"));
        if(request.getParameter("metadescription") != null)
            theme.setmetadescription(request.getParameter("metadescription"));
        if(request.getParameter("mostpopularcount") != null)
            theme.setmostpopularcount(new Integer(request.getParameter("mostpopularcount")).intValue());
        if(request.getParameter("searchresultcount") != null)
            theme.setsearchresultcount(new Integer(request.getParameter("searchresultcount")).intValue());
        if(request.getParameter("featureditemcount") != null)
            theme.setfeatureditemcount(new Integer(request.getParameter("featureditemcount")).intValue());
        if(request.getParameter("imagebaseurl") != null)
            theme.setimagebaseurl(request.getParameter("imagebaseurl"));

        updateThemeRequest.settheme(theme);
        companyBean.UpdateTheme(updateThemeRequest);
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
                            <td class="producttitle">Company Update Result</td>
                        </tr>
                    </table>
                    <br>
                    <TABLE cellSpacing="1" cellPadding="3" width="95%" border="0">
                    <tr>
                        <td noWrap>The theme information has been updated successfully.</td>
                    </tr>
                    </TABLE>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>

