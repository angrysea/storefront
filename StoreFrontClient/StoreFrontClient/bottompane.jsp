<%@ page import="javax.servlet.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.storefront.storefrontbeans.*" %>
<%@ page import="com.storefront.storefrontrepository.*" %>

<%
    Theme bpTheme = null;
    Company bpCompany = null;
    String yearString = null;
    GetLandingPagesResponse getLandingPagesResponse = null;
    try {
        CompanyBean companyBean = new CompanyBean();
        GetThemeRequest getThemeRequest = new GetThemeRequest();
        getThemeRequest.setname("corporate");
        GetThemeResponse getThemeResponse = companyBean.GetTheme(request, getThemeRequest);
        bpTheme = getThemeResponse.gettheme();

        GetCompanyResponse getCompanyResponse = companyBean.GetCompany(request, new GetCompanyRequest());
        bpCompany = getCompanyResponse.getcompany();

        yearString = new Integer(new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime())).toString();

        LandingPageBean landingPageBean = new LandingPageBean();
        GetLandingPagesRequest getLandingPagesRequest = new GetLandingPagesRequest();
        getLandingPagesRequest.setactive(true);
        getLandingPagesResponse = landingPageBean.GetLandingPages(getLandingPagesRequest);
    }
    catch (Exception ex) {
        ex.printStackTrace();
        throw new ServletException(ex.getMessage());
    }
%>


<table summary="<%=request.getAttribute("keyword1")%>" width="350" align="center">
    <tr>
        <td><br></td>
    </tr>
    <tr>
        <td><br></td>
    </tr>
    <tr>
        <td><br></td>
    </tr>
    <tr>
        <td><br></td>
    </tr>
    <tr align="center">
        <td nowrap>
          	<a href="<%=StoreFrontUrls.geturl(request, bpCompany, "index")%>" title="<%=request.getAttribute("keyword1")%> - Home">Home</a>
		| <a href="<%=StoreFrontUrls.getsecureurl(request, bpCompany,"login")%>" title="<%=request.getAttribute("keyword1")%> - Log In">Log In</a>
		| <a href="<%=StoreFrontUrls.getsecureurl(request, bpCompany, "youraccount")%>" title="<%=request.getAttribute("keyword1")%> - Your Account">Your Account</a>
		| <a href="<%=StoreFrontUrls.updateurl(request, bpCompany, bpCompany.getbaseurl() + "shoppingcart.jsp")%>" title="<%=request.getAttribute("keyword1")%> - Shopping Cart">Shopping Cart</a>
		| <a href="<%=StoreFrontUrls.getsecureurl(request, bpCompany, "checkoutmethod")%>" title="<%=request.getAttribute("keyword1")%> - Checkout">Checkout</a>
        </td>
    </tr>
    <tr align="center">
        <td>
               <a href="<%=StoreFrontUrls.geturl(request, bpCompany, "coupons")%>" title="<%=request.getAttribute("keyword1")%> - Coupons">Coupons</a>
               | <a href="<%=StoreFrontUrls.geturl(request, bpCompany, "privacysecurity")%>" title="<%=request.getAttribute("keyword1")%> - Privacy & Security">Privacy & Security</a>
               | <a href="<%=StoreFrontUrls.geturl(request, bpCompany, "contactus")%>" title="<%=request.getAttribute("keyword1")%> - Contact Us">Contact Us</a>
        </td>
    </tr>
</table>
<br>
<table summary="<%=request.getAttribute("keyword1")%>" width="350" align="center">
    <tr align="center">
        <td nowrap>
    <%
        Iterator itLandingPages = getLandingPagesResponse.getlandingPagesIterator();
        for(int i=0 ; itLandingPages.hasNext() ; i++)
        {
            LandingPage bpLandingPage = (LandingPage)itLandingPages.next();
    %>
            <a href="<%=StoreFrontUrls.getlandingpage(request, bpCompany, bpLandingPage)%>" title="<%=bpLandingPage.getheading()%>"><%=bpLandingPage.getheading()%></a>
    <%
            if(i > 2)
            {
                i = -1;
    %>
                    </td>
                </tr>
                <tr align="center">
                   <td nowrap>
    <%
            }
            else if(itLandingPages.hasNext())
            {
    %>
            &nbsp;|&nbsp;
    <%
            }
        }
    %>
        </td>
    </tr>
</table>
<br>
<table summary="<%=request.getAttribute("keyword1")%>" width="350" align="center">
    <tr>
  	<td height="5"><img src="<%=bpTheme.getimagebaseurl()%>spacer.gif" height="5" width="1" border="0"></td>
    </tr>

    <tr>
        <td><CENTER>Copyright © <%=yearString%> <%=bpCompany.getcompany()%>. All Rights Reserved.</CENTER></td>
    </tr>
    <tr>
        <td><br /></td>
    </tr>
<%
    if(bpCompany.getsiteseal())
    {
%>
    <tr>
        <td align="center"><script src=https://seal.verisign.com/getseal?host_name=<%=bpCompany.geturl().replaceAll("http://", "")%>&size=S&use_flash=NO&use_transparent=NO></script></td>
    </tr>
<%
    }
%>
    <tr>
        <td align="center">
<%
    if(bpCompany.getwebstatid() != null && bpCompany.getwebstatid().length() > 0)
    {
%>
        <!-- BEGIN WebSTAT Activation Code -->
        <script type="text/javascript" language="JavaScript"
        src="http://hits.webstat.com/cgi-bin/wsv2.cgi?<%=bpCompany.getwebstatid()%>";></script>
        <noscript>
        <a href="http://www.webstat.com";>
        <img SRC="http://hits.webstat.com/scripts/wsb.php?ac=<%=bpCompany.getwebstatid()%>";
        border="0" alt="Website Statistics and Free Website Counter by
        WebSTAT"></a>
        </noscript>
        <!-- END WebSTAT Activation Code -->
<%
    }
%>
        </td>
    </tr>
</table>
