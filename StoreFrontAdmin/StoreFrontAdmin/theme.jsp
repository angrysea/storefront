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
                            <td class="producttitle">Theme</td>
                        </tr>
                    </table>
                    <br>
                    <form name="form1" action="./theme_result.jsp" method="post">
                        <input name="id" type="hidden" value="<%=new Integer(theme.getid()).toString()%>"/>
                    <TABLE cellSpacing="1" cellPadding="3" width="95%" border="0">
                    <tr>
                        <td noWrap>Theme Name:</td>
                        <td><input name="themename" type="text" size="20" value="<%=theme.getname()==null?"":theme.getname()%>" readonly="readonly"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Color 1:</td>
                        <td><input name="color1" type="text" size="10" value="<%=theme.getcolor1()==null?"":theme.getcolor1()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Color 2:</td>
                        <td><input name="color2" type="text" size="10" value="<%=theme.getcolor2()==null?"":theme.getcolor2()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Color 3:</td>
                        <td><input name="color3" type="text" size="10" value="<%=theme.getcolor3()==null?"":theme.getcolor3()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Color 4:</td>
                        <td><input name="color4" type="text" size="10" value="<%=theme.getcolor4()==null?"":theme.getcolor4()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Color 5:</td>
                        <td><input name="color5" type="text" size="10" value="<%=theme.getcolor5()==null?"":theme.getcolor5()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Image 1:</td>
                        <td><input name="image1" type="text" size="40" value="<%=theme.getimage1()==null?"":theme.getimage1()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Image 2:</td>
                        <td><input name="image2" type="text" size="40" value="<%=theme.getimage2()==null?"":theme.getimage2()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Image 3:</td>
                        <td><input name="image3" type="text" size="40" value="<%=theme.getimage3()==null?"":theme.getimage3()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Image 4:</td>
                        <td><input name="image4" type="text" size="40" value="<%=theme.getimage4()==null?"":theme.getimage4()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Image 5:</td>
                        <td><input name="image5" type="text" size="40" value="<%=theme.getimage5()==null?"":theme.getimage5()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Heading 1:</td>
                        <td><input name="heading1" type="text" size="40" value="<%=theme.getheading1()==null?"":theme.getheading1()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Heading 2:</td>
                        <td><input name="heading2" type="text" size="40" value="<%=theme.getheading2()==null?"":theme.getheading2()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Heading 3:</td>
                        <td><input name="heading3" type="text" size="40" value="<%=theme.getheading3()==null?"":theme.getheading3()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Heading 4:</td>
                        <td><input name="heading4" type="text" size="40" value="<%=theme.getheading4()==null?"":theme.getheading4()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap>Heading 5:</td>
                        <td><input name="heading5" type="text" size="40" value="<%=theme.getheading5()==null?"":theme.getheading5()%>"/></td>
                    </tr>
                    <tr>
                        <td noWrap valign="top">TitleInfo:</td>
                        <td><textarea name="titleinfo" rows="5" cols="70"><%=theme.gettitleinfo()==null?"":theme.gettitleinfo()%></textarea></td>
                    </tr>
                    <tr>
                        <td noWrap valign="top">MetaContentType:</td>
                        <td><textarea name="metacontenttype" rows="5" cols="70"><%=theme.getmetacontenttype()==null?"":theme.getmetacontenttype()%></textarea></td>
                    </tr>
                    <tr>
                        <td noWrap valign="top">MetaKeywords:</td>
                        <td><textarea name="metakeywords" rows="5" cols="70"><%=theme.getmetakeywords()==null?"":theme.getmetakeywords()%></textarea></td>
                    </tr>
                    <tr>
                        <td noWrap valign="top">MetaDescription:</td>
                        <td><textarea name="metadescription" rows="10" cols="70"><%=theme.getmetadescription()==null?"":theme.getmetadescription()%></textarea></td>
                    </tr>
                    <tr>
                        <td noWrap>Most Popular Count:</td>
                        <td noWrap><input name="mostpopularcount" type="text" size="5" value="<%=new Integer(theme.getmostpopularcount()).intValue()%>"/>&nbsp;(Number of items displayed on the 'Most Popular' pane)</td>
                    </tr>
                    <tr>
                        <td noWrap>Search Result Count:</td>
                        <td noWrap><input name="searchresultcount" type="text" size="5" value="<%=new Integer(theme.getsearchresultcount()).intValue()%>"/>&nbsp;(Number of products displayed on a single line for the search results page)</td>
                    </tr>
                    <tr>
                        <td noWrap>Featured Item Count:</td>
                        <td noWrap><input name="featureditemcount" type="text" size="5" value="<%=new Integer(theme.getfeatureditemcount()).intValue()%>"/>&nbsp;(Number of products displayed on a line in a 'Featured Item' grouping)</td>
                    </tr>
                    <tr>
                        <td noWrap>Image Base URL:</td>
                        <td><input name="imagebaseurl" type="text" size="50" value="<%=theme.getimagebaseurl()==null?"":theme.getimagebaseurl()%>"/></td>
                    </tr>
                    </TABLE>
                    <br>
                    <TABLE border="0">
                    <tr>
                        <td><input type="submit" value="Update"></td>
                    </tr>
                    </TABLE>
                    </form>
                </td>
            </tr>
        </table>
    </BODY>
</HTML>
