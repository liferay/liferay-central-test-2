<%
/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
%>

<%@ include file="/html/portlet/software_repository/init.jsp" %>

<%
	String tabs1 = ParamUtil.getString(request, "tabs1", "products");
	String redirect = ParamUtil.getString(request, "redirect");

	SRProductEntry productEntry = (SRProductEntry) request.getAttribute(WebKeys.SOFTWARE_REPOSITORY_PRODUCT_ENTRY);
	SRProductVersion productVersion= (SRProductVersion) request.getAttribute(WebKeys.SOFTWARE_REPOSITORY_PRODUCT_VERSION);

	long productEntryId = BeanParamUtil.getLong(productEntry, request, "productEntryId");
	long productVersionId = BeanParamUtil.getLong(productVersion, request, "productVersionId");
%>

<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL">
	<portlet:param name="struts_action" value="/software_repository/view_product_entry" />
	<portlet:param name="productEntryId" value="<%= String.valueOf(productEntry.getProductEntryId()) %>" />
</portlet:renderURL>

<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editProductEntryURL">
	<portlet:param name="struts_action" value="/software_repository/edit_product_entry" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="productEntryId" value="<%= String.valueOf(productEntry.getProductEntryId()) %>" />
</portlet:renderURL>

<portlet:renderURL var="viewProductEntryHistoryURL">
	<portlet:param name="struts_action" value="/software_repository/view_product_entry" />
	<portlet:param name="productEntryId" value="<%= String.valueOf(productEntry.getProductEntryId()) %>" />
	<portlet:param name="tabs2" value="version-history" />
</portlet:renderURL>
<%
%>
<liferay-ui:tabs
	names="products,my-products,recent-products,framework-versions,licenses"
	url="<%= portletURL.toString() %>"
	backURL="<%=Validator.isNull(redirect)?viewProductEntryHistoryURL.toString():redirect%>"
/>

<div class="product-entry-detail">
	<h2>
		<%=productEntry.getName()%> <%=LanguageUtil.get(pageContext, "version")%> <%= productVersion.getVersion()%>
		<%=LanguageUtil.get(pageContext, "by")%> <span class="author"><%=productEntry.getUserName()%></span>
	</h2>
	<div class="preview-image" align="right">
		<img src="http://www.liferay.com/image/company_logo?img_id=liferay.com" alt=""/>
		<!-- <a href="">More screenshots</a> -->
	</div>

	<p class="short-description"><%=productEntry.getShortDescription()%></p>
	<p class="long-description"><%=productEntry.getLongDescription()%></p>

	<div class="links">
		<a href="<%=productEntry.getPageURL()%>" class="home-page"><%=LanguageUtil.get(pageContext,"home-page")%></a>
	</div>
	<div class="product-version">
		<h2><%=LanguageUtil.get(pageContext, "selected-version")%></h2>
		<p>
			<%= productVersion.getChangeLog()%>
		</p>
		<p>
			<%= LanguageUtil.get(pageContext, "supported-framework-versions") + ": " + productVersion.getFrameworkVersionNames()%>
		</p>
		<p>
			<%= LanguageUtil.get(pageContext, "date") + ": " + productVersion.getModifiedDate()%>
		</p>
		<div class="links">
			<% if (Validator.isNotNull(productVersion.getDirectDownloadURL())) { %>
				<a href="<%=productVersion.getDirectDownloadURL()%>" class="direct-download-url">
				<img src="/html/themes/classic/images/common/download.gif" onmousemove="ToolTip.show(event, this, '<%=LanguageUtil.get(pageContext, "direct-download")%>')" alt="<%=LanguageUtil.get(pageContext, "direct-download")%>" align="absmiddle" border="0">
				<%=LanguageUtil.get(pageContext,"direct-download")%></a>
				<br>
			<% } %>
			<% if (Validator.isNotNull(productVersion.getDownloadPageURL())) { %>
				<a href="<%=productVersion.getDownloadPageURL()%>" class="download-page-url">
				<img src="/html/themes/classic/images/common/download.gif" onmousemove="ToolTip.show(event, this, '<%=LanguageUtil.get(pageContext, "download page")%>')" alt="<%=LanguageUtil.get(pageContext, "download-page")%>" align="absmiddle" border="0">
				<%=LanguageUtil.get(pageContext,"download-page")%></a>
			<% } %>
		</div>
	</div>

</div>