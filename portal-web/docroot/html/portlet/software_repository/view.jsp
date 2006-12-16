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

	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setWindowState(WindowState.MAXIMIZED);

	portletURL.setParameter("struts_action", "/software_repository/view");
%>

<liferay-ui:tabs
	names="products,my-products,framework-versions,licenses"
	url="<%= portletURL.toString() %>"
/>

<c:choose>
	<c:when test='<%= tabs1.equals("products") || tabs1.equals("my-products")%>'>
	<%
		if (tabs1.equals("products")) {
	%>
		<form action="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/software_repository/search" /></portlet:renderURL>" method="post" name="<portlet:namespace />searchForm"
			style="float: right">
			<input class="form-text" name="<portlet:namespace />keywords" size="30" type="text">
			<select name="<portlet:namespace/>type">
				<option value=""><%= LanguageUtil.get(pageContext, "any-type") %></option>
				<option value="portlet"><%= LanguageUtil.get(pageContext, "portlet") %></option>
				<option value="theme"><%= LanguageUtil.get(pageContext, "theme") %></option>
				<option value="layout"><%= LanguageUtil.get(pageContext, "layout") %></option>
				<option value="extension"><%= LanguageUtil.get(pageContext, "extension") %></option>
			</select>
			<input class="portlet-form-button" type="submit" value="<%= LanguageUtil.get(pageContext, "search-product-entries") %>">
		</form>
	<%
		} else {
	%>
		<p><%=LanguageUtil.get(pageContext, "product-entries-added-by") + " " + user.getFullName() %></p>
	<%
		}
	%>
		<form method="post" name="<portlet:namespace />">

		<%
			List headerNames = new ArrayList();

			headerNames.add("product-name");
			headerNames.add("type");
			headerNames.add("licenses");
			headerNames.add("modified-date");
			headerNames.add(StringPool.BLANK);

			SearchContainer searchContainer = new SearchContainer(
				renderRequest, null, null, "cur1",
				SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

			int total;
			if (tabs1.equals("products")) {
				total = SRProductEntryServiceUtil.getProductEntriesCount(portletGroupId);
			} else {
				total = SRProductEntryServiceUtil.getProductEntriesCountByUserId(portletGroupId, user.getUserId());
			}

			searchContainer.setTotal(total);

			List results;
			if (tabs1.equals("products")) {
				results = SRProductEntryServiceUtil.getProductEntries(
					portletGroupId, searchContainer.getStart(),
					searchContainer.getEnd());
			} else {
				results = SRProductEntryServiceUtil.getProductEntriesByUserId(
					portletGroupId, user.getUserId(), searchContainer.getStart(),
					searchContainer.getEnd());
			}

			searchContainer.setResults(results);

			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				SRProductEntry curProductEntry = (SRProductEntry) results.get(i);

				ResultRow row = new ResultRow(
					curProductEntry, Long.toString(curProductEntry.getPrimaryKey()), i);

				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setWindowState(WindowState.MAXIMIZED);

				rowURL
					.setParameter("struts_action", "/software_repository/view_product_entry");
				rowURL.setParameter(
					"productEntryId", Long.toString(curProductEntry.getProductEntryId()));

				// Name and description

				StringBuffer sb = new StringBuffer();

				sb.append("<a href=\"");
				sb.append(rowURL);
				sb.append("\"><b>");
				sb.append(curProductEntry.getName());
				sb.append("</b>");

				if (Validator.isNotNull(curProductEntry.getShortDescription())) {
					sb.append("<br>");
					sb.append("<span style=\"font-size: xx-small;\">");
					sb.append(curProductEntry.getShortDescription());
					sb.append("</span>");
				}

				sb.append("</a>");

				row.addText(sb.toString());

				row.addText(""+curProductEntry.getType());
				row.addText(""+curProductEntry.getLicenseNames());
				row.addText(""+curProductEntry.getModifiedDate());

				// Action

				row.addJSP(
					"right", SearchEntry.DEFAULT_VALIGN,
					"/html/portlet/software_repository/product_entry_action.jsp");

				// Add result row

				resultRows.add(row);
			}
		%>

			<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "add-product-entry") %>' onClick="self.location = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/software_repository/edit_product_entry" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>';"><br>

			<c:if test="<%= results.size() > 0 %>">
				<br>
			</c:if>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

		<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />

		</form>
	</c:when>
	<c:when test='<%= tabs1.equals("framework-versions") %>'>
		<form method="post" name="<portlet:namespace />">

		<%
			List headerNames = new ArrayList();

			headerNames.add("name");
			headerNames.add("active");
			headerNames.add("priority");
			headerNames.add(StringPool.BLANK);

			SearchContainer searchContainer = new SearchContainer(
				renderRequest, null, null, "cur1",
				SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

			int total = SRFrameworkVersionServiceUtil.getFrameworkVersionsCount(portletGroupId);

			searchContainer.setTotal(total);

			List results = SRFrameworkVersionServiceUtil.getFrameworkVersions(portletGroupId);

			searchContainer.setResults(results);

			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				SRFrameworkVersion curFrameworkVersion =
					(SRFrameworkVersion) results.get(i);

				ResultRow row = new ResultRow(
					curFrameworkVersion,
					Long.toString(curFrameworkVersion.getPrimaryKey()), i);

				// Name and description

				StringBuffer sb = new StringBuffer();

				sb.append("<a href=\"");
				sb.append(curFrameworkVersion.getUrl());
				sb.append("\"><b>");
				sb.append(curFrameworkVersion.getName());
				sb.append("</b>");
				sb.append("</a>");

				row.addText(sb.toString());

				row.addText(LanguageUtil.get(pageContext, curFrameworkVersion.getActive()?"yes":"no"));
				row.addText(Integer.toString(curFrameworkVersion.getPriority()));

				// Action

				row.addJSP(
					"right", SearchEntry.DEFAULT_VALIGN,
					"/html/portlet/software_repository/framework_version_action.jsp");

				// Add result row

				resultRows.add(row);
			}
		%>

			<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "add-framework-version") %>' onClick="self.location = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/software_repository/edit_framework_version" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>';"><br>

			<c:if test="<%= results.size() > 0 %>">
				<br>
			</c:if>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

		<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />

		</form>
	</c:when>
	<c:when test='<%= tabs1.equals("licenses") %>'>
		<form method="post" name="<portlet:namespace />">

		<%
			List headerNames = new ArrayList();

			headerNames.add("name");
			headerNames.add("active");
			headerNames.add("openSource");
			headerNames.add("recommended");
			headerNames.add(StringPool.BLANK);

			SearchContainer searchContainer = new SearchContainer(
				renderRequest, null, null, "cur1",
				SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

			int total = SRLicenseServiceUtil.getLicensesCount();

			searchContainer.setTotal(total);

			List results = SRLicenseServiceUtil.getLicenses();

			searchContainer.setResults(results);

			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				SRLicense curLicense = (SRLicense) results.get(i);

				ResultRow row = new ResultRow(
					curLicense,
					Long.toString(curLicense.getPrimaryKey()), i);

				// Name and description

				StringBuffer sb = new StringBuffer();

				sb.append("<a href=\"");
				sb.append(curLicense.getUrl());
				sb.append("\"><b>");
				sb.append(curLicense.getName());
				sb.append("</b>");
				sb.append("</a>");

				row.addText(sb.toString());

				row.addText(LanguageUtil.get(pageContext, curLicense.getActive()?"yes":"no"));
				row.addText(LanguageUtil.get(pageContext, curLicense.getOpenSource()?"yes":"no"));
				row.addText(LanguageUtil.get(pageContext, curLicense.getRecommended()?"yes":"no"));

				// Action

				row.addJSP(
					"right", SearchEntry.DEFAULT_VALIGN,
					"/html/portlet/software_repository/license_action.jsp");

				// Add result row

				resultRows.add(row);
			}
		%>

			<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "add-license") %>' onClick="self.location = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/software_repository/edit_license" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>';"><br>

			<c:if test="<%= results.size() > 0 %>">
				<br>
			</c:if>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

		<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />

		</form>
	</c:when>
	<c:when test='<%= tabs1.equals("my-entries")%>'>
		my-entries
	</c:when>
</c:choose>