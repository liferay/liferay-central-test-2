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
	String installStatus = ParamUtil.getString(request, "installStatus");
	if (Validator.isNull(installStatus)) {
		installStatus = PluginPackageImpl.STATUS_NOT_INSTALLED_OR_OLDER_VERSION_INSTALLED;
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
			<%= LanguageUtil.get(pageContext, "install-status") %>
		</td>
	</tr>
	<tr>
		<td>
			<input name="<portlet:namespace />keywords" size="20" type="text" value="<%= keywords %>">
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
			<select name="<portlet:namespace/>installStatus">
				<option <%=(installStatus.equals(PluginPackageImpl.STATUS_NOT_INSTALLED_OR_OLDER_VERSION_INSTALLED))? "selected" : "" %> value="<%=PluginPackageImpl.STATUS_NOT_INSTALLED_OR_OLDER_VERSION_INSTALLED%>"><%= LanguageUtil.get(pageContext, "not-installed-or-older-version-installed") %></option>
				<option <%=(installStatus.equals(PluginPackageImpl.STATUS_OLDER_VERSION_INSTALLED))? "selected" : "" %> value="<%=PluginPackageImpl.STATUS_OLDER_VERSION_INSTALLED%>"><%= LanguageUtil.get(pageContext, "older-version-installed") %></option>
				<option <%=(installStatus.equals(PluginPackageImpl.STATUS_NOT_INSTALLED))? "selected" : "" %> value="<%=PluginPackageImpl.STATUS_NOT_INSTALLED%>"><%= LanguageUtil.get(pageContext, "not-installed") %></option>
				<option <%=(installStatus.equals(PluginPackageImpl.STATUS_ALL)) ? "selected" : "" %> value="<%=PluginPackageImpl.STATUS_ALL%>"><%= LanguageUtil.get(pageContext, "all") %></option>
			</select>
		</td>
	</tr>
	</table>

	<br>

	<input type="button" value='<%= LanguageUtil.get(pageContext, "search") %>' onClick="<portlet:namespace/>searchPlugins('<%=redirect%>')">

	<br><br>

	<div class="separator" style="clear: both;"></div><br>

	<%
	List headerNames = new ArrayList();

	headerNames.add(pluginType + "-package");
	headerNames.add("tags");
	headerNames.add("installed-version");
	headerNames.add("available-version");

	SearchContainer searchContainer = new SearchContainer(
		renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM,
		SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

	Hits hits = PluginPackageUtil.search(
		keywords, pluginType, tag, license, repositoryURL, installStatus);

	Hits results =
		hits.subset(searchContainer.getStart(), searchContainer.getEnd());
	int total = hits.getLength();

	searchContainer.setTotal(total);

	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.getLength(); i++) {
		Document doc = results.doc(i);

		String pluginPackageModuleId = doc.get("moduleId");
		String pluginPackageGroupId = doc.get("groupId");
		String pluginPackageArtifactId = doc.get("artifactId");
		String pluginPackageName = doc.get(LuceneFields.TITLE);
		String pluginPackageAvailableVersion = doc.get("version");
		String pluginPackageTags = doc.get("tags");
		String pluginPackageShortDescription = doc.get("shortDescription");
		String pluginPackageChangeLog = doc.get("changeLog");
		String pluginPackageRepositoryURL = doc.get("repositoryURL");

		PluginPackage installedPluginPackage = PluginPackageUtil.getLatestInstalledPluginPackage(pluginPackageGroupId, pluginPackageArtifactId);

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

		StringMaker sm = new StringMaker();

		sm.append("<a href='");
		sm.append(rowURL.toString());
		sm.append("'>");
		sm.append("<b>");
		sm.append(pluginPackageName);
		sm.append("</b> ");
		sm.append(pluginPackageAvailableVersion);
		sm.append("</a>");

		if (Validator.isNotNull(pluginPackageShortDescription)) {
			sm.append("<br>");
			sm.append("<span style=\"font-size: xx-small;\">");
			sm.append(LanguageUtil.get(pageContext, "package-id"));
			sm.append(": ");
			sm.append(pluginPackageModuleId);
			sm.append("<br>");
			sm.append(pluginPackageShortDescription);
			sm.append("</span>");
		}

		row.addText(sm.toString());

		// Tags

		TextSearchEntry rowTextEntry = new TextSearchEntry(SearchEntry.DEFAULT_ALIGN, SearchEntry.DEFAULT_VALIGN, LanguageUtil.get(pageContext, pluginPackageTags), null, null, null);

		row.addText(rowTextEntry);

		// Installed version

		if (installedPluginPackage != null) {
			row.addText(installedPluginPackage.getVersion());
		}
		else {
			row.addText(StringPool.DASH);
		}

		// Available version

		row.addText(pluginPackageAvailableVersion + getNoteIcon(themeDisplay, pluginPackageChangeLog));

		// Add result row

		resultRows.add(row);
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

    <br>

	<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />

	<div class="separator" style="clear: both;"/>

	<div>
		<c:if test="<%= PluginPackageUtil.getLastUpdateDate() != null %>">
			 <%= LanguageUtil.format(pageContext, "list-of-plugins-was-last-refreshed-on-x", dateFormatDateTime.format(PluginPackageUtil.getLastUpdateDate())) %>
		</c:if>
		<input type="button" value='<%= LanguageUtil.get(pageContext, "refresh") %>'  onClick="<portlet:namespace/>reloadRepositories('<%= currentURL %>');">

		<br>

		<liferay-util:include page="/html/portlet/admin/repository_report.jsp" />

	</div>
<%
}
catch (PluginPackageException e) {
	_log.warn("Error browsing the repository", e);
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