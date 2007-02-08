<%
/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/software_catalog/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String tabs1 = ParamUtil.getString(request, "tabs1", "comments");

SCProductEntry productEntry = (SCProductEntry) request.getAttribute(WebKeys.SOFTWARE_CATALOG_PRODUCT_ENTRY);

long productEntryId = BeanParamUtil.getLong(productEntry, request, "productEntryId");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/software_catalog/view_product_entry");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("productEntryId", String.valueOf(productEntryId));
%>

<liferay-ui:tabs
	names="product"
	backURL="<%= redirect %>"
/>

<table border="0" cellpadding="0" cellspacing="0">
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "name") %>:
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<%= productEntry.getName() %>
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "type") %>:
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<%= LanguageUtil.get(pageContext, productEntry.getType()) %>
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "licenses") %>:
	</td>
	<td style="padding-left: 10px;"></td>
	<td>

		<%
		Iterator itr = productEntry.getLicenses().iterator();

		while (itr.hasNext()) {
			SCLicense license = (SCLicense) itr.next();
		%>

			<a href="<%= license.getUrl() %>" target="_blank"><%= license.getName() %></a><c:if test="<%= itr.hasNext() %>">, </c:if>

		<%
		}
		%>

	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "page-url") %>:
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<a href="<%= productEntry.getPageURL() %>"><%= productEntry.getPageURL() %></a>
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "short-description") %>:
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<%= productEntry.getShortDescription() %>
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "long-description") %>:
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<%= productEntry.getLongDescription() %>
	</td>
</tr>
</table>

<br>

<liferay-ui:ratings
	className="<%= SCProductEntry.class.getName() %>"
	classPK="<%= String.valueOf(productEntry.getPrimaryKey()) %>"
	url='<%= themeDisplay.getPathMain() + "/software_catalog/rate_product_entry?productEntryId=" + productEntryId %>'
/>

<liferay-ui:tabs
	names="comments,version-history"
	url="<%= portletURL.toString() %>"
/>

<c:choose>
	<c:when test='<%= tabs1.equals("comments") %>'>
		<portlet:actionURL var="discussionURL">
			<portlet:param name="struts_action" value="/software_catalog/edit_product_entry_discussion" />
		</portlet:actionURL>

		<liferay-ui:discussion
			formAction="<%= discussionURL %>"
			className="<%= SCProductEntry.class.getName() %>"
			classPK="<%= String.valueOf(productEntry.getPrimaryKey()) %>"
			userId="<%= productEntry.getUserId() %>"
			subject="<%= productEntry.getName() %>"
			redirect="<%= currentURL %>"
		/>
	</c:when>
	<c:when test='<%= tabs1.equals("version-history") %>'>
	</c:when>
</c:choose>

<%--<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL">
	<portlet:param name="struts_action" value="/software_catalog/view_product_entry" />
	<portlet:param name="productEntryId" value="<%= String.valueOf(productEntry.getProductEntryId()) %>" />
</portlet:renderURL>

<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editProductEntryURL">
	<portlet:param name="struts_action" value="/software_catalog/edit_product_entry" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="productEntryId" value="<%= String.valueOf(productEntry.getProductEntryId()) %>" />
</portlet:renderURL>

<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="addProductVersionURL">
	<portlet:param name="struts_action" value="/software_catalog/edit_product_version" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="cmd" value="add" />
	<portlet:param name="productEntryId" value="<%= String.valueOf(productEntry.getProductEntryId()) %>" />
</portlet:renderURL>

<portlet:renderURL var="listURL">
	<portlet:param name="struts_action" value="/software_catalog/view" />
</portlet:renderURL>

<div class="product-entry-detail">
	<h2>
		<%=productEntry.getName()%>
		<%=LanguageUtil.get(pageContext, "by")%> <span class="author"><%= productEntry.getUserName() %></span>
	</h2>
	<div class="ratings" style="display: inline; margin-bottom: 35px" align="left">
		<liferay-ui:ratings
			className="<%= SCProductEntry.class.getName() %>"
			classPK="<%= String.valueOf(productEntry.getPrimaryKey()) %>"
			url='<%= themeDisplay.getPathMain() + "/software_catalog/rate_product_entry?productEntryId=" + productEntryId %>'
		/>
	</div>

	<div class="preview-image"  style="float:right" align="right">
		<img src="http://www.liferay.com/image/company_logo?img_id=liferay.com" alt="<%=LanguageUtil.get(pageContext, "product-screenshot")%>" />
		<p>
		 <a href="">More screenshots</a>
		</p>
	</div>

	<p class="short-description"><%=productEntry.getShortDescription()%></p>
	<p class="long-description"><%=productEntry.getLongDescription()%></p>

	<div class="links">
		<a href="<%=productEntry.getPageURL()%>" class="home-page"><%=LanguageUtil.get(pageContext,"home-page")%></a>
	</div>
	<%
		PortletURL viewProductVersionURL = renderResponse.createRenderURL();

		viewProductVersionURL.setWindowState(WindowState.MAXIMIZED);

		viewProductVersionURL.setParameter("struts_action", "/software_catalog/view_product_entry");
		viewProductVersionURL.setParameter("productEntryId", "" + productEntryId);

		List headerNames = new ArrayList();

		headerNames.add("version");
		headerNames.add("supported-framework-versions");
		headerNames.add("date");
		headerNames.add(StringPool.BLANK);

		SearchContainer searchContainer = new SearchContainer(
			renderRequest, null, null, "cur1",
			SearchContainer.DEFAULT_DELTA, viewProductVersionURL, headerNames, null);

		int total = SCProductVersionServiceUtil.getProductVersionsCount(productEntryId);

		searchContainer.setTotal(total);
		List results = SCProductVersionServiceUtil.getProductVersions(
			productEntryId, searchContainer.getStart(),
			searchContainer.getEnd());
	%>

	<%
		if (results.size() > 0) {
			SCProductVersion lastVersion = (SCProductVersion) results.get(0);
	%>
	<div class="last-product-version">
		<h2><%=LanguageUtil.get(pageContext, "last-version")%>: <%= lastVersion.getVersion()%></h2>
		<p>
			<%= lastVersion.getChangeLog()%>
		</p>
		<p>
			<%= LanguageUtil.get(pageContext, "supported-framework-versions") + ": " + lastVersion.getFrameworkVersionNames()%>
		</p>
		<p>
			<%= LanguageUtil.get(pageContext, "date") + ": " + lastVersion.getModifiedDate()%>
		</p>
		<div class="links">
			<% if (Validator.isNotNull(lastVersion.getDirectDownloadURL())) { %>
				<a href="<%=lastVersion.getDirectDownloadURL()%>" class="direct-download-url">
				<img src="/html/themes/classic/images/common/download.gif" onmousemove="ToolTip.show(event, this, '<%=LanguageUtil.get(pageContext, "direct-download")%>')" alt="<%=LanguageUtil.get(pageContext, "direct-download")%>" align="absmiddle" border="0">
				<%=LanguageUtil.get(pageContext,"direct-download")%></a>
				<br>
			<% } %>
			<% if (Validator.isNotNull(lastVersion.getDownloadPageURL())) { %>
				<a href="<%=lastVersion.getDownloadPageURL()%>" class="download-page-url">
				<img src="/html/themes/classic/images/common/download.gif" onmousemove="ToolTip.show(event, this, '<%=LanguageUtil.get(pageContext, "download page")%>')" alt="<%=LanguageUtil.get(pageContext, "download-page")%>" align="absmiddle" border="0">
				<%=LanguageUtil.get(pageContext,"download-page")%></a>
			<% } %>
		</div>
	</div>
	<%
		} else {
	%>
			<p style="color:red">
				<b>Note</b>: <%=LanguageUtil.get(pageContext, "this-product-has-not-yet-uploaded-any-release")%>
			</p>
	<%
		}
	%>

	<div class="actions" style="margin-bottom: 20px; margin-top:15px">
		<form action="<%=editProductEntryURL.toString()%>" method="post" style="display:inline">
			<input type="submit" value="<%=LanguageUtil.get(pageContext,"edit-product-entry")%>"/>
		</form>
		<form action="<%=addProductVersionURL.toString()%>" method="post" style="display:inline">
			<input type="submit" value="<%=LanguageUtil.get(pageContext,"add-product-version")%>"/>
		</form>
	</div>

	<liferay-ui:tabs
		names="comments,version-history"
		param="tabs2"
		url="<%= portletURL.toString() %>"
	/>
	<c:choose>
		<c:when test='<%= tabs2.equals("comments") %>'>
			<div class="comments">
				<portlet:actionURL var="discussionURL">
					<portlet:param name="struts_action" value="/software_catalog/edit_product_entry_discussion" />
				</portlet:actionURL>

				<liferay-ui:discussion
					formAction="<%= discussionURL %>"
					className="<%= SCProductEntry.class.getName() %>"
					classPK="<%= String.valueOf(productEntry.getPrimaryKey()) %>"
					userId="<%= productEntry.getUserId() %>"
					subject="<%= productEntry.getName() %>"
					redirect="<%= currentURL %>"
				/>
			</div>
		</c:when>
		<c:when test='<%= tabs2.equals("version-history") %>'>
			<div class="version-history">
				<%

					searchContainer.setResults(results);

					List resultRows = searchContainer.getResultRows();

					for (int i = 0; i < results.size(); i++) {
						SCProductVersion curProductVersion =
							(SCProductVersion) results.get(i);

						ResultRow row = new ResultRow(
							curProductVersion,
							String.valueOf(curProductVersion.getPrimaryKey()), i);

						PortletURL rowURL = renderResponse.createRenderURL();

						rowURL.setWindowState(WindowState.MAXIMIZED);

						rowURL
							.setParameter(
								"struts_action",
								"/software_catalog/view_product_version");
						rowURL.setParameter(
							"productVersionId",
							String.valueOf(curProductVersion.getProductVersionId()));

						// Name and description

						StringBuffer sb = new StringBuffer();

						sb.append("<a href=\"");
						sb.append(rowURL);
						sb.append("\"><b>");
						sb.append(curProductVersion.getVersion());
						sb.append("</b>");

						if (Validator
							.isNotNull(curProductVersion.getChangeLog())) {
							sb.append("<br>");
							sb.append("<span style=\"font-size: xx-small;\">");
							sb.append(curProductVersion.getChangeLog());
							sb.append("</span>");
						}

						sb.append("</a>");

						row.addText(sb.toString());

						row.addText(curProductVersion.getFrameworkVersionNames());
						row.addText("" + curProductVersion.getModifiedDate());

						// Action

						row.addJSP(
							"right", SearchEntry.DEFAULT_VALIGN,
							"/html/portlet/software_catalog/product_version_action.jsp");

						// Add result row

						resultRows.add(row);
					}
				%>

					<c:if test="<%= results.size() > 0 %>">
						<br>
					</c:if>

				<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

				<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />


			</div>

		</c:when>
	</c:choose>
</div>--%>