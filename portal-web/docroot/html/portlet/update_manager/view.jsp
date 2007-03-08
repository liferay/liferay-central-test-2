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

<%@ include file="/html/portlet/admin/init.jsp" %>

<c:if test="<%= !OmniadminUtil.isOmniadmin(user.getUserId())%>">
	<%= LanguageUtil.get(pageContext, "this-tool-can-only-be-used-by-omniadmin-users") %>
</c:if>

<c:if test="<%= OmniadminUtil.isOmniadmin(user.getUserId()) && !PrefsPropsUtil.getBoolean(PropsUtil.AUTO_DEPLOY_ENABLED) %>">
	<%
	PortletURL configurationURL = ((RenderResponseImpl) renderResponse).createRenderURL(PortletKeys.ADMIN);
	configurationURL.setWindowState(WindowState.MAXIMIZED);

	configurationURL.setParameter("struts_action", "/admin/plugin_installer");
	configurationURL.setParameter("referer", currentURL);
	configurationURL.setParameter("tabs1", "configuration");
    %>
	<a href="<%=configurationURL%>"><%= LanguageUtil.get(pageContext, "auto-deploy-has-to-be-enabled-to-use-this-tool") %></a>
</c:if>

<c:if test="<%= OmniadminUtil.isOmniadmin(user.getUserId()) && PrefsPropsUtil.getBoolean(PropsUtil.AUTO_DEPLOY_ENABLED) %>">
<%
String redirect = currentURL;
if (!themeDisplay.getPortletDisplay().isStateMax()) {
	PortletURL refererURL = renderResponse.createRenderURL();
	refererURL.setWindowState(WindowState.MAXIMIZED);
	redirect = refererURL.toString();
}

PortletURL browseRepoURL = ((RenderResponseImpl) renderResponse).createRenderURL(PortletKeys.ADMIN);
browseRepoURL.setWindowState(WindowState.MAXIMIZED);

browseRepoURL.setParameter("struts_action", "/admin/plugin_installer");
browseRepoURL.setParameter("referer", redirect);
browseRepoURL.setParameter("tabs1", "browse-repository");

PortletURL installPluginsURL = ((RenderResponseImpl) renderResponse).createRenderURL(PortletKeys.ADMIN);

installPluginsURL.setWindowState(WindowState.MAXIMIZED);

installPluginsURL.setParameter("struts_action", "/admin/plugin_installer");
installPluginsURL.setParameter("referer", redirect);
installPluginsURL.setParameter("tabs1", "browse-repository");

PortletURL editServerURL = renderResponse.createActionURL();

editServerURL.setParameter("struts_action", "/admin/edit_server");
editServerURL.setParameter("redirect", redirect);
%>

<script type="text/javascript">
	function <portlet:namespace />reloadRepositories(redirect) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "reloadRepositories";
		submitForm(document.<portlet:namespace />fm, "<%=editServerURL%>")
	}
</script>

<form method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="">
<input name="<portlet:namespace />referer" type="hidden" value="<%=redirect%>">
<input name="<portlet:namespace />redirect" type="hidden" value="<%=redirect%>">


<%
try {

	String downloadProgressId = "update_manager_" + System.currentTimeMillis();

	List headerNames = new ArrayList();

	headerNames.add("plugin-package");
	headerNames.add("status");
	headerNames.add("installed-version");
	headerNames.add("available-version");
	headerNames.add(StringPool.BLANK);

	SearchContainer searchContainer = new SearchContainer(
		renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM,
		SearchContainer.DEFAULT_DELTA, browseRepoURL, headerNames, null);

	List pluginPackages = PluginPackageUtil.getInstalledPluginPackages();
	int total = pluginPackages.size();

	searchContainer.setTotal(total);

	pluginPackages = pluginPackages.subList(
		searchContainer.getStart(),
		Math.min(total, searchContainer.getEnd()));

	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < pluginPackages.size(); i++) {

		PluginPackage pluginPackage = (PluginPackage) pluginPackages.get(i);
		PluginPackage availablePluginPackage = null;
		try {
			availablePluginPackage = PluginPackageUtil.getLatestAvailablePluginPackage(pluginPackage.getGroupId(), pluginPackage.getArtifactId());
		}
		catch (Exception se) {
		}


		String pluginPackageModuleId = pluginPackage.getModuleId();
		String pluginPackageName = pluginPackage.getName();
		String pluginPackageVersion = pluginPackage.getVersion();
		String pluginPackageContext = pluginPackage.getContext();

		String pluginPackageStatus = "up-to-date";
		if (PluginPackageUtil.isInstallationInProcess(pluginPackage.getContext())) {
			pluginPackageStatus = "installation-in-process";
		}
		else if ((availablePluginPackage != null) && Version.getInstance(availablePluginPackage.getVersion()).isLaterVersionThan(pluginPackageVersion)) {
			pluginPackageStatus = "update-available";
		}
		else if (pluginPackage.getVersion().equals(Version.UNKNOWN)) {
			pluginPackageStatus = "unknown";
		}

		ResultRow row = new ResultRow(new Object[]{pluginPackage, availablePluginPackage, pluginPackageStatus, downloadProgressId, redirect}, pluginPackageModuleId, i);

		row.setClassName("status-" + pluginPackageStatus);

		// Name

		StringMaker sm = new StringMaker();

		sm.append("<b>");
		sm.append(pluginPackageName);
		sm.append("</b> ");
		sm.append("<br>/");
		sm.append(pluginPackageContext);

		row.addText(sm.toString());

		// Status

		row.addText(pluginPackageStatus);

		// Installed version

		row.addText(pluginPackageVersion);

		// Available version

		if (availablePluginPackage != null) {

			PortletURL rowURL = ((RenderResponseImpl) renderResponse)
				.createRenderURL(PortletKeys.ADMIN);

			rowURL.setWindowState(WindowState.MAXIMIZED);

			rowURL.setParameter("struts_action", "/admin/plugin_installer");
			rowURL.setParameter("referer", redirect);
			rowURL.setParameter("tabs1", "browse-repository");
			rowURL.setParameter(
				"repositoryURL", availablePluginPackage.getRepositoryURL());
			rowURL.setParameter(
				"moduleId", availablePluginPackage.getModuleId());

			sm = new StringMaker();
			sm.append("<a href='");
			sm.append(rowURL.toString());
			sm.append("'>");
			sm.append(availablePluginPackage.getVersion());
			sm.append(getNoteIcon(themeDisplay, availablePluginPackage.getChangeLog()));
			sm.append("</a>");
			row.addText(sm.toString());
		}
		else {
			row.addText(StringPool.DASH);
		}

		// Actions

		row.addJSP(
			"/html/portlet/update_manager/plugin_package_action.jsp");

		// Add result row

		resultRows.add(row);

	}

%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

	<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />

	<liferay-ui:upload-progress
		id="<%= downloadProgressId %>"
		message="downloading"
		redirect="<%= redirect %>"
/>
<%
	if (total == 0) {
%>
		<br>

		<%= LanguageUtil.get(pageContext, "there-are-no-plugins-installed")%>
<%
	}
%>

   <br>

   <input class="portlet-form-button" type="button" onClick="submitForm(document.<portlet:namespace />fm, '<%= installPluginsURL.toString() %>');" value='<%=LanguageUtil.get(pageContext, "install-more-plugins")%>'/>

	<div class="separator" style="clear: both;"/>

	<div>
	   <c:if test="<%= PluginPackageUtil.getLastUpdateDate() != null %>">
			<%= LanguageUtil.format(pageContext, "list-of-plugins-was-last-refreshed-on-x", dateFormatDateTime.format(PluginPackageUtil.getLastUpdateDate())) %>
	   </c:if>
	   <input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "refresh") %>'  onClick="<portlet:namespace/>reloadRepositories('<%= currentURL %>');">

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
</form>
</c:if>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.update_manager.view.jsp");
%>