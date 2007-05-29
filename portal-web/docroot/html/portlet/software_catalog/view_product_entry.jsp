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

String tabs2 = ParamUtil.getString(request, "tabs2", "comments");

SCProductEntry productEntry = (SCProductEntry) request.getAttribute(WebKeys.SOFTWARE_CATALOG_PRODUCT_ENTRY);
SCProductVersion latestProductVersion = productEntry.getLatestVersion();

long productEntryId = BeanParamUtil.getLong(productEntry, request, "productEntryId");

PortletURL viewProductEntryURL = renderResponse.createRenderURL();

viewProductEntryURL.setWindowState(WindowState.MAXIMIZED);

viewProductEntryURL.setParameter("struts_action", "/software_catalog/view_product_entry");
viewProductEntryURL.setParameter("tabs2", tabs2);
viewProductEntryURL.setParameter("redirect", redirect);
viewProductEntryURL.setParameter("productEntryId", String.valueOf(productEntryId));

PortletURL editProductEntryURL = renderResponse.createRenderURL();

editProductEntryURL.setWindowState(WindowState.MAXIMIZED);

editProductEntryURL.setParameter("struts_action", "/software_catalog/edit_product_entry");
editProductEntryURL.setParameter("tabs2", tabs2);
editProductEntryURL.setParameter("redirect", currentURL);
editProductEntryURL.setParameter("productEntryId", String.valueOf(productEntryId));

PortletURL addProductVersionURL = renderResponse.createRenderURL();

addProductVersionURL.setWindowState(WindowState.MAXIMIZED);

addProductVersionURL.setParameter("struts_action", "/software_catalog/edit_product_version");
addProductVersionURL.setParameter(Constants.CMD, Constants.ADD);
addProductVersionURL.setParameter("tabs2", tabs2);
addProductVersionURL.setParameter("redirect", currentURL);
addProductVersionURL.setParameter("productEntryId", String.valueOf(productEntryId));

%>

<liferay-ui:tabs
	names="product"
	backURL="<%= redirect %>"
/>

<h2><%= productEntry.getName() %> <%= (latestProductVersion == null)?"":latestProductVersion.getVersion() %></h2>

<table class="liferay-table">
<tr>
	<td>
		<liferay-ui:message key="type" />:
	</td>
	<td>
		<%= LanguageUtil.get(pageContext, productEntry.getType()) %>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="licenses" />:
	</td>
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

<c:if test="<%= Validator.isNotNull(productEntry.getPageURL()) %>">
	<tr>
		<td>
			<liferay-ui:message key="page-url" />:
		</td>
		<td>
			<a href="<%= productEntry.getPageURL() %>"><%= productEntry.getPageURL() %></a>
		</td>
	</tr>
</c:if>

<tr>
	<td>
		<liferay-ui:message key="short-description" />:
	</td>
	<td>
		<%= productEntry.getShortDescription() %>
	</td>
</tr>

<c:if test="<%= Validator.isNotNull(productEntry.getLongDescription()) %>">
	<tr>
		<td>
			<liferay-ui:message key="long-description" />:
		</td>
		<td>
			<%= productEntry.getLongDescription() %>
		</td>
	</tr>
</c:if>

<tr>
	<td>
		<liferay-ui:message key="screenshots" />
	</td>
	<td>

		<%
		String imageName = SCProductEntryImpl.MAIN_IMAGE_NAME;
		String imageId = productEntry.getImageId(imageName);
		if (imageId != null) {
			String imageURL = themeDisplay.getPathImage() + "/software_catalog?img_id=" + imageId;
		%>
			<a href="<%=imageURL + "&large=1"%>"><img src="<%=imageURL + "&small=1"%>" border="0"/>
		<%
		}
		for (int i = 1; i < 4; i++) {
			imageName = String.valueOf(i);
			imageId = productEntry.getImageId(imageName);
			if (imageId != null) {
				String imageURL = themeDisplay.getPathImage() + "/software_catalog?img_id=" + imageId;
		%>
				<a href="<%=imageURL + "&large=1"%>"><img src="<%=imageURL + "&small=1"%>" border="0"/>
		<%
			}
		}
		%>
	</td>
</tr>

<%
if (latestProductVersion != null) {
%>
<tr>
	<td colspan="2">
		&nbsp;
	</td>
</tr>
<tr>
	<th colspan="2">
		<liferay-ui:message key="information-about-the-latest-version" />:
	</th>
</tr>
<tr>
	<td>
		<liferay-ui:message key="release-date" />:
	</td>
	<td>
		<%= latestProductVersion.getModifiedDate() %>
	</td>
</tr><tr>
	<td>
		<liferay-ui:message key="changeLog" />:
	</td>
	<td>
		<%= latestProductVersion.getChangeLog() %>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="supported-framework-versions" />:
	</td>
	<td>
		<%= _buildFrameworkVersions(latestProductVersion.getFrameworkVersions()) %>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="download-links" />:
	</td>
	<td>
		<% if (Validator.isNotNull(latestProductVersion.getDirectDownloadURL())) { %>
			<a href="<%=latestProductVersion.getDirectDownloadURL()%>">
				<img src="/html/themes/classic/images/common/download.png" onmousemove="ToolTip.show(event, this, '<%=LanguageUtil.get(pageContext, "direct-download")%>')" alt="<%=LanguageUtil.get(pageContext, "direct-download")%>" align="absmiddle" border="0" />
			</a>
		<% } %>
		<% if (Validator.isNotNull(latestProductVersion.getDownloadPageURL())) { %>
			<a href="<%=latestProductVersion.getDownloadPageURL()%>">
				<img src="/html/themes/classic/images/common/download.png" onmousemove="ToolTip.show(event, this, '<%=LanguageUtil.get(pageContext, "download-page")%>')" alt="<%=LanguageUtil.get(pageContext, "download-page")%>" align="absmiddle" border="0" />
			</a>
		<% } %>
	</td>
</tr>
<%
}
else {
%>
	<tr>
		<td style="color:red" colspan="2">
			<b><%=LanguageUtil.get(pageContext, "note") %></b>: <%=LanguageUtil.get(pageContext, "this-product-has-not-yet-uploaded-any-release")%>
		</td>
	</tr>
<%
}
%>

</table>

<br />

<liferay-ui:ratings
	className="<%= SCProductEntry.class.getName() %>"
	classPK="<%= productEntry.getProductEntryId() %>"
	url='<%= themeDisplay.getPathMain() + "/software_catalog/rate_product_entry" %>'
/>

<c:if test="<%= SCProductEntryPermission.contains(permissionChecker, productEntryId, ActionKeys.UPDATE) %>">
	<div class="actions" style="margin-bottom: 20px; margin-top:15px">
		<form action="<%=editProductEntryURL.toString()%>" method="post" style="display:inline">
			<input type="submit" value="<%=LanguageUtil.get(pageContext,"edit-product-entry")%>"/>
		</form>
		<form action="<%=addProductVersionURL.toString()%>" method="post" style="display:inline">
			<input type="submit" value="<%=LanguageUtil.get(pageContext,"add-product-version")%>"/>
		</form>
	</div>
</c:if>

<liferay-ui:tabs
	param="tabs2"
	names="comments,version-history"
	portletURL="<%= viewProductEntryURL %>"
/>

<c:choose>
	<c:when test='<%= tabs2.equals("comments") %>'>
		<portlet:actionURL var="discussionURL">
			<portlet:param name="struts_action" value="/software_catalog/edit_product_entry_discussion" />
		</portlet:actionURL>

		<liferay-ui:discussion
			formAction="<%= discussionURL %>"
			className="<%= SCProductEntry.class.getName() %>"
			classPK="<%= productEntry.getProductEntryId() %>"
			userId="<%= productEntry.getUserId() %>"
			subject="<%= productEntry.getName() %>"
			redirect="<%= currentURL %>"
		/>
	</c:when>
	<c:when test='<%= tabs2.equals("version-history") %>'>
		<div class="version-history">
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

			SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "cur1", SearchContainer.DEFAULT_DELTA, viewProductVersionURL, headerNames, null);

			int total = SCProductVersionServiceUtil.getProductVersionsCount(productEntryId);

			searchContainer.setTotal(total);

			List results = SCProductVersionServiceUtil.getProductVersions(productEntryId, searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(results);

			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				SCProductVersion curProductVersion = (SCProductVersion) results.get(i);

				ResultRow row = new ResultRow(curProductVersion, String.valueOf(curProductVersion.getProductVersionId()), i);

				// Name and description

				StringMaker sm = new StringMaker();

				sm.append("<b>");
				sm.append(curProductVersion.getVersion());
				sm.append("</b>");

				if (Validator.isNotNull(curProductVersion.getChangeLog())) {
					sm.append("<br />");
					sm.append("<span style=\"font-size: xx-small;\">");
					sm.append(curProductVersion.getChangeLog());
					sm.append("</span>");
				}

				sm.append("</a>");

				row.addText(sm.toString());

				row.addText(_buildFrameworkVersions(curProductVersion.getFrameworkVersions()));
				row.addText("" + curProductVersion.getModifiedDate());

				// Action

				row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/software_catalog/product_version_action.jsp");

				// Add result row

				resultRows.add(row);
			}
			%>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

			<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />


		</div>
	</c:when>
</c:choose>