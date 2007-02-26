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

<%
String keywords = ParamUtil.getString(request, "keywords");
String tag = ParamUtil.getString(request, "tag");
String license = ParamUtil.getString(request, "license");
String status = ParamUtil.getString(request, "status");
if (Validator.isNull(status)) {
	status = "notInstalledOrNewVersion";
}

try {
%>

<%= breadcrumbs.toString() %>

<br><br>

<input type="hidden" name="<portlet:namespace/>pluginType" value="<%=pluginType%>">

	<table border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<%= LanguageUtil.get(pageContext, "keywords") %>
		</td>
		<td style="padding-left: 5px;"></td>
		<td>
			<%= LanguageUtil.get(pageContext, "tag") %>
		</td>
		<td style="padding-left: 5px;"></td>
		<td>
			<%= LanguageUtil.get(pageContext, "repository") %>
		</td>
		<td style="padding-left: 5px;"></td>
		<td>
			<%= LanguageUtil.get(pageContext, "status") %>
		</td>
	</tr>
	<tr>
		<td>
			<input class="form-text" name="<portlet:namespace />keywords" size="20" type="text" value="<%= keywords %>">
		</td>
		<td style="padding-left: 5px;"></td>
		<td>
			<select name="<portlet:namespace/>tag">
				<option value=""><%= LanguageUtil.get(pageContext, "all") %></option>

				<%
				Iterator itr = PluginPackageUtil.getAvailableTags().iterator();

				while (itr.hasNext()) {
					String curTag = (String)itr.next();
				%>

					<option <%= (tag.equals(curTag)) ? "selected": "" %> value="<%= curTag %>"><%= curTag %></option>

				<%
				}
				%>

			</select>
		</td>
		<td style="padding-left: 5px;"></td>
		<td>
			<select name="<portlet:namespace/>repositoryURL">
				<option value=""><%= LanguageUtil.get(pageContext, "all") %></option>

				<%
				String[] repositoryURLs = PluginPackageUtil.getRepositoryURLs();

				for (int i = 0; i < repositoryURLs.length; i++) {
					String curRepositoryURL = repositoryURLs[i];
				%>

					<option <%= (repositoryURL.equals(curRepositoryURL)) ? "selected" : "" %> value="<%= curRepositoryURL %>"><%= curRepositoryURL %></option>

				<%
				}
				%>

			</select>
		</td>
		<td style="padding-left: 5px;"></td>
		<td>
			<select name="<portlet:namespace/>status">
				<option <%= (status.equals("notInstalledOrNewVersion")) ? "selected": "" %> value="notInstalledOrNewVersion"><%= LanguageUtil.get(pageContext, "not-installed-or-new-version") %></option>
				<option <%= (status.equals("newVersion")) ? "selected": "" %> value="newVersion"><%= LanguageUtil.get(pageContext, "new-version") %></option>
				<option <%= (status.equals("notInstalled")) ? "selected": "" %> value="notInstalled"><%= LanguageUtil.get(pageContext, "not-installed") %></option>
				<option <%= (status.equals("all")) ? "selected": "" %> value="all"><%= LanguageUtil.get(pageContext, "all") %></option>
			</select>
		</td>
	</tr>
	</table>

	<br>

	<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "search") %>' onClick="<portlet:namespace/>searchPlugins('<%=redirect%>')">

	<br><br>

	<div class="separator" style="clear: both;"></div><br>

	<%
	List headerNames = new ArrayList();

	headerNames.add(pluginType + "-package");
	headerNames.add("tags");
	headerNames.add("status");
	headerNames.add(StringPool.BLANK);

	SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

	Hits hits = PluginPackageUtil.search(keywords, pluginType, tag, license, repositoryURL, status);

	Hits results = hits.subset(searchContainer.getStart(), searchContainer.getEnd());
	int total = hits.getLength();

	searchContainer.setTotal(total);

	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.getLength(); i++) {
		Document doc = results.doc(i);

		String pluginPackageModuleId = doc.get("moduleId");
		String pluginPackageName = doc.get(LuceneFields.TITLE);
		String pluginPackageVersion = doc.get("version");
		String pluginPackageTags = doc.get("tags");
		String pluginPackageShortDescription = doc.get("shortDescription");
		String pluginPackageRepositoryURL = doc.get("repositoryURL");
		String pluginPackageStatus = doc.get("status");

		ResultRow row = new ResultRow(doc, pluginPackageModuleId, i);

		PortletURL rowURL = renderResponse.createRenderURL();

		rowURL.setWindowState(WindowState.MAXIMIZED);

		rowURL.setParameter("struts_action", "/admin/plugin_installer");
		rowURL.setParameter("referer", referer);
		rowURL.setParameter("redirect", currentURL);
		rowURL.setParameter("tabs1", tabs1);
		rowURL.setParameter("tabs2", tabs2);
		rowURL.setParameter("moduleId", pluginPackageModuleId);
		rowURL.setParameter("repositoryURL", pluginPackageRepositoryURL);

		// Name and short description

		StringBuffer sb = new StringBuffer();

		sb.append("<a href='");
		sb.append(rowURL.toString());
		sb.append("'>");
		sb.append("<b>");
		sb.append(pluginPackageName);
		sb.append("</b> ");
		sb.append(pluginPackageVersion);
		sb.append("</a>");

		if (Validator.isNotNull(pluginPackageShortDescription)) {
			sb.append("<br>");
			sb.append("<span style=\"font-size: xx-small;\">");
			sb.append(LanguageUtil.get(pageContext, "package-id"));
			sb.append(": ");
			sb.append(pluginPackageModuleId);
			sb.append("<br>");
			sb.append(pluginPackageShortDescription);
			sb.append("</span>");
		}

		row.addText(sb.toString());

		// Tags

		TextSearchEntry rowTextEntry = new TextSearchEntry(SearchEntry.DEFAULT_ALIGN, SearchEntry.DEFAULT_VALIGN, LanguageUtil.get(pageContext, pluginPackageTags), null, null, null);

		row.addText(rowTextEntry);

		// Status

		rowTextEntry = (TextSearchEntry)rowTextEntry.clone();

		rowTextEntry.setName(pluginPackageStatus);

		row.addText(rowTextEntry);

		// Action

		row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/admin/plugin_action.jsp");

		// Add result row

		resultRows.add(row);
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

	<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />

	<c:if test="<%= PluginPackageUtil.getLastUpdateDate() != null %>">
		<br>

		<%= LanguageUtil.format(pageContext, "list-of-plugins-was-last-refreshed-on-x", dateFormatDateTime.format(PluginPackageUtil.getLastUpdateDate())) %><br>
	</c:if>

	<liferay-util:include page="/html/portlet/admin/repository_report.jsp" />

	<br>

	<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "refresh") %>'  onClick="<portlet:namespace/>reloadRepositories('<%= currentURL %>');">

<%
}
catch (PluginPackageException e) {
	_log.error("Error browsing the repository", e);
%>

	<span class="portlet-msg-error">
	<%= LanguageUtil.get(pageContext, "error-obtaining-available-plugins") %>
	</span>

<%
}
%>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.admin.browse_repository.jsp");
%>