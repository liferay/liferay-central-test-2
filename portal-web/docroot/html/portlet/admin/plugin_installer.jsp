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

<%
String tabs1 = ParamUtil.getString(request, "tabs1");
String tabs2 = ParamUtil.getString(request, "tabs2");
if (Validator.isNull(tabs2)) {
	tabs2 = "portlets";
}

String pluginType = null;
if (tabs2.equals("themes")) {
	pluginType = ThemeImpl.PLUGIN_TYPE;
}
else if (tabs2.equals("layout-templates")) {
	pluginType = LayoutTemplateImpl.PLUGIN_TYPE;
}
else if (tabs2.equals("portlets")) {
	pluginType = PortletImpl.PLUGIN_TYPE;
}

String referer = request.getParameter(WebKeys.REFERER);
String redirect = ParamUtil.getString(request, "redirect");
if (Validator.isNull(redirect)) {
	redirect = currentURL;
}

String downloadProgressId = "downloadPlugin" + System.currentTimeMillis();
String uploadProgressId = "uploadPlugin" + System.currentTimeMillis();

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/admin/plugin_installer");
portletURL.setParameter("referer", referer);
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("tabs2", tabs2);

String tabs1Options = "browse-repository,upload-file,download-file,configuration";
if (!PrefsPropsUtil.getBoolean(PropsUtil.AUTO_DEPLOY_ENABLED)) {
	tabs1Options = "configuration";
	tabs1 = "configuration";
}

// Breadcrumbs

StringMaker breadcrumbs = new StringMaker();
breadcrumbs.append(LanguageUtil.get(pageContext, "repositories") + " &raquo; ");

breadcrumbs.append("<a href=\"" + portletURL.toString() + "\">" + LanguageUtil.get(pageContext, pluginType+"-packages") + "</a>");


%>
<script type="text/javascript">
	function <portlet:namespace />installPluginPackage(cmd, progressId, redirect) {
		if (cmd == "localDeploy") {
			document.<portlet:namespace />fm.encoding = "multipart/form-data";
		}

		if (progressId) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.PROGRESS_ID %>.value = progressId;
		}

		if (redirect) {
			document.<portlet:namespace />fm.<portlet:namespace />redirect.value = redirect;
		}

		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = cmd;
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/admin/edit_server" /></portlet:actionURL>");
	}

	function <portlet:namespace />saveDeployConfiguration() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'deployConfiguration';
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/admin/edit_server" /></portlet:actionURL>");
	}

	function <portlet:namespace />reloadRepositories(redirect) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "reloadRepositories";
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = redirect;
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/admin/edit_server" /></portlet:actionURL>")
	}

	function <portlet:namespace />searchPlugins(redirect) {
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = redirect;
		submitForm(document.<portlet:namespace />fm, "<%= portletURL.toString() %>");
	}

</script>
<form method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="">
<input name="<portlet:namespace />tabs1" type="hidden" value="<%= tabs1 %>">
<input name="<portlet:namespace />tabs2" type="hidden" value="<%= tabs2 %>">
<input name="<portlet:namespace />referer" type="hidden" value="<%=referer%>">
<input name="<portlet:namespace />redirect" type="hidden" value="<%=currentURL%>">
<input name="<portlet:namespace />pluginType" type="hidden" value="<%= pluginType %>">
<input name="<portlet:namespace />progressId" type="hidden" value="">

<h3><%= LanguageUtil.get(pageContext, "plugin-installer") %></h3>

<liferay-ui:tabs
	names="<%=tabs1Options%>"
	param="tabs1"
	url="<%= portletURL.toString() %>"
	backURL="<%= referer %>"
/>

<c:choose>
	<c:when test='<%=tabs1.equals("upload-file") %>'>
		<%@ include file="/html/portlet/admin/upload_file.jsp" %>
	</c:when>
	<c:when test='<%=tabs1.equals("download-file")%>'>
		<%@ include file="/html/portlet/admin/download_file.jsp" %>
	</c:when>
	<c:when test='<%=tabs1.equals("configuration")%>'>
		<table border="0" cellpadding="0" cellspacing="0">
		<% if (!PrefsPropsUtil.getBoolean(PropsUtil.AUTO_DEPLOY_ENABLED)) { %>
		<tr>
			<td colspan="3">
				<%= LanguageUtil.get(pageContext, "installation-of-plugins-is-disabled") %> <%-- -enable-and-configure-it-to-install-new-plugins --%>
			</td>
		</tr>
		<% } %>
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "enabled") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<liferay-ui:input-checkbox param="enabled" defaultValue="<%= PrefsPropsUtil.getBoolean(PropsUtil.AUTO_DEPLOY_ENABLED) %>" />
			</td>
		</tr>
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "deploy-directory") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<input class="form-text" name="<portlet:namespace />deployDir" size="75" type="text" value="<%= PrefsPropsUtil.getString(PropsUtil.AUTO_DEPLOY_DEPLOY_DIR) %>">
			</td>
		</tr>
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "dest-directory") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<input class="form-text" name="<portlet:namespace />destDir" size="75" type="text" value="<%= PrefsPropsUtil.getString(PropsUtil.AUTO_DEPLOY_DEST_DIR) %>">
			</td>
		</tr>
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "interval") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<select name="<portlet:namespace />interval">
					<option value="0"><%= LanguageUtil.get(pageContext, "disable") %></option>

					<%
					long interval = PrefsPropsUtil.getInteger(PropsUtil.AUTO_DEPLOY_INTERVAL);

					for (int i = 0;;) {
						if (i < Time.MINUTE) {
							i += Time.SECOND * 5;
						}
						else {
							i += Time.MINUTE;
						}
					%>

						<option <%= (interval == i) ? "selected" : "" %> value="<%= i %>"><%= LanguageUtil.getTimeDescription(pageContext, i) %></option>

					<%
						if (i >= (Time.MINUTE * 5)) {
							break;
						}
					}
					%>

				</select>
			</td>
		</tr>
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "unpack-war") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<liferay-ui:input-checkbox param="unpackWar" defaultValue="<%= PrefsPropsUtil.getBoolean(PropsUtil.AUTO_DEPLOY_UNPACK_WAR) %>" />
			</td>
		</tr>

		<c:if test="<%= ServerDetector.isTomcat() %>">
			<tr>
				<td colspan="3">
					<br>
				</td>
			</tr>
			<tr>
				<td>
					<%= LanguageUtil.get(pageContext, "tomcat-lib-dir") %>
				</td>
				<td style="padding-left: 10px;"></td>
				<td>
					<input class="form-text" name="<portlet:namespace />tomcatLibDir" size="75" type="text" value="<%= PrefsPropsUtil.getString(PropsUtil.AUTO_DEPLOY_TOMCAT_LIB_DIR) %>">
				</td>
			</tr>
		</c:if>

		<tr>
			<td colspan="3">
				<br>
			</td>
		</tr>
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "plugin-repositories") %><br>

				<span style="font-size: xx-small;">(<%= LanguageUtil.get(pageContext, "enter-one-url-per-line") %>)</span>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<textarea class="form-text" name="<portlet:namespace />pluginRepositories" style="height: <%= ModelHintsDefaults.TEXTAREA_DISPLAY_HEIGHT %>px; width: 400px;" wrap="soft"><%= PrefsPropsUtil.getString(PropsUtil.PLUGIN_REPOSITORIES) %></textarea>

				<liferay-util:include page="/html/portlet/admin/repository_report.jsp" />
			</td>
		</tr>
		</table>

		<br>

		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "save") %>' onClick="<portlet:namespace />saveDeployConfiguration();">
	</c:when>
	<c:otherwise>

		<liferay-ui:tabs
			names="portlet-packages,theme-packages,layout-template-packages"
			tabsValues="portlets,themes,layout-templates"
			param="tabs2"
			url="<%= portletURL.toString() %>"
		/>

		<%
		String moduleId = ParamUtil.getString(request, "moduleId");
		String repositoryURL = ParamUtil.getString(request, "repositoryURL");
		%>

		<c:choose>
			<c:when test="<%= Validator.isNotNull(moduleId) && Validator.isNotNull(repositoryURL) %>">
				<%@ include file="/html/portlet/admin/view_plugin_package.jsp" %>
			</c:when>
			<c:otherwise>
				<%@ include file="/html/portlet/admin/browse_repository.jsp" %>
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>

</form>