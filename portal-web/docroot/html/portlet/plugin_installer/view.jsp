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
	<liferay-ui:message key="this-tool-can-only-be-used-by-omniadmin-users" />
</c:if>

<c:if test="<%= OmniadminUtil.isOmniadmin(user.getUserId()) %>">
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

	String referer = ParamUtil.getString(request, WebKeys.REFERER, "");
	String redirect = ParamUtil.getString(request, "redirect", currentURL);

	String downloadProgressId = "downloadPlugin" + System.currentTimeMillis();
	String uploadProgressId = "uploadPlugin" + System.currentTimeMillis();

	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setWindowState(WindowState.MAXIMIZED);

	portletURL.setParameter("struts_action", "/plugin_installer/view");
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
			submitForm(document.<portlet:namespace />fm, '<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><liferay-portlet:param name="struts_action" value="/plugin_installer/install_plugin" /></portlet:actionURL>');
		}

		function <portlet:namespace />saveDeployConfiguration() {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'deployConfiguration';
			submitForm(document.<portlet:namespace />fm, '<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/plugin_installer/install_plugin" /></portlet:actionURL>');
		}

		function <portlet:namespace />reloadRepositories(redirect) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "reloadRepositories";
			document.<portlet:namespace />fm.<portlet:namespace />redirect.value = redirect;
			submitForm(document.<portlet:namespace />fm, '<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/plugin_installer/install_plugin" /></portlet:actionURL>')
		}

		function <portlet:namespace />searchPlugins(redirect) {
			document.<portlet:namespace />fm.<portlet:namespace />redirect.value = redirect;
			submitForm(document.<portlet:namespace />fm, "<%= portletURL.toString() %>");
		}

	</script>
	<form method="post" name="<portlet:namespace />fm">
	<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
	<input name="<portlet:namespace />tabs1" type="hidden" value="<%= tabs1 %>" />
	<input name="<portlet:namespace />tabs2" type="hidden" value="<%= tabs2 %>" />
	<input name="<portlet:namespace />referer" type="hidden" value="<%=referer%>" />
	<input name="<portlet:namespace />redirect" type="hidden" value="<%=currentURL%>" />
	<input name="<portlet:namespace />pluginType" type="hidden" value="<%= pluginType %>" />
	<input name="<portlet:namespace />progressId" type="hidden" value="" />

	<liferay-ui:tabs
		names="<%=tabs1Options%>"
		param="tabs1"
		url="<%= portletURL.toString() %>"
		backURL="<%= referer %>"
	/>

	<c:choose>
		<c:when test='<%=tabs1.equals("upload-file") %>'>
			<%@ include file="/html/portlet/plugin_installer/upload_file.jspf" %>
		</c:when>
		<c:when test='<%=tabs1.equals("download-file") %>'>
			<%@ include file="/html/portlet/plugin_installer/download_file.jspf" %>
		</c:when>
		<c:when test='<%=tabs1.equals("configuration") %>'>
			<c:if test="<%= !PrefsPropsUtil.getBoolean(PropsUtil.AUTO_DEPLOY_ENABLED) %>">
				<liferay-ui:message key="installation-of-plugins-is-disabled" />

				<br /><br />
			</c:if>

			<table class="liferay-table">
			<tr>
				<td>
					<liferay-ui:message key="enabled" />
				</td>
				<td>
					<liferay-ui:input-checkbox param="enabled" defaultValue="<%= PrefsPropsUtil.getBoolean(PropsUtil.AUTO_DEPLOY_ENABLED) %>" />
				</td>
			</tr>
			<tr>
				<td>
					<liferay-ui:message key="deploy-directory" />
				</td>
				<td>
					<input name="<portlet:namespace />deployDir" size="75" type="text" value="<%= PrefsPropsUtil.getString(PropsUtil.AUTO_DEPLOY_DEPLOY_DIR) %>" />
				</td>
			</tr>
			<tr>
				<td>
					<liferay-ui:message key="dest-directory" />
				</td>
				<td>
					<input name="<portlet:namespace />destDir" size="75" type="text" value="<%= PrefsPropsUtil.getString(PropsUtil.AUTO_DEPLOY_DEST_DIR) %>" />
				</td>
			</tr>
			<tr>
				<td>
					<liferay-ui:message key="interval" />
				</td>
				<td>
					<select name="<portlet:namespace />interval">
						<option value="0"><liferay-ui:message key="disable" /></option>

						<%
						long interval = PrefsPropsUtil.getLong(PropsUtil.AUTO_DEPLOY_INTERVAL);

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
					<liferay-ui:message key="blacklist-threshold" />
				</td>
				<td>
					<select name="<portlet:namespace />blacklistThreshold">

						<%
						int blacklistThreshold = PrefsPropsUtil.getInteger(PropsUtil.AUTO_DEPLOY_BLACKLIST_THRESHOLD);

						for (int i = 0; i < blacklistThreshold; i++) {
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
					<liferay-ui:message key="unpack-war" />
				</td>
				<td>
					<liferay-ui:input-checkbox param="unpackWar" defaultValue="<%= PrefsPropsUtil.getBoolean(PropsUtil.AUTO_DEPLOY_UNPACK_WAR) %>" />
				</td>
			</tr>

			<%
			int jbossPrefix = GetterUtil.getInteger(PrefsPropsUtil.getString(PropsUtil.AUTO_DEPLOY_JBOSS_PREFIX));
			%>

			<c:choose>
				<c:when test="<%= ServerDetector.isJBoss() %>">
					<tr>
						<td colspan="2">
							<br />
						</td>
					</tr>
					<tr>
						<td>
							<liferay-ui:message key="jboss-prefix" />
						</td>
						<td>
							<select name="<portlet:namespace />jbossPrefix">
								<option></option>

								<%
								for (int i = 1; i < 9; i++) {
								%>

									<option <%= jbossPrefix == i ? "selected" : "" %> value="<%= i %>"><%= i %></option>

								<%
								}
								%>

							</select>
						</td>
					</tr>
				</c:when>
				<c:otherwise>
					<input name="<portlet:namespace />jbossPrefix" type="hidden" value="<%= jbossPrefix %>" />
				</c:otherwise>
			</c:choose>

			<%
			String tomcatConfDir = PrefsPropsUtil.getString(PropsUtil.AUTO_DEPLOY_TOMCAT_CONF_DIR);
			String tomcatLibDir = PrefsPropsUtil.getString(PropsUtil.AUTO_DEPLOY_TOMCAT_LIB_DIR);
			%>

			<c:choose>
				<c:when test="<%= ServerDetector.isTomcat() %>">
					<tr>
						<td colspan="2">
							<br />
						</td>
					</tr>
					<tr>
						<td>
							<liferay-ui:message key="tomcat-conf-dir" />
						</td>
						<td>
							<input name="<portlet:namespace />tomcatConfDir" size="75" type="text" value="<%= tomcatConfDir %>" />
						</td>
					</tr>
					<tr>
						<td>
							<liferay-ui:message key="tomcat-lib-dir" />
						</td>
						<td>
							<input name="<portlet:namespace />tomcatLibDir" size="75" type="text" value="<%= tomcatLibDir %>" />
						</td>
					</tr>
				</c:when>
				<c:otherwise>
					<input name="<portlet:namespace />tomcatConfDir" type="hidden" value="<%= tomcatConfDir %>" />
					<input name="<portlet:namespace />tomcatLibDir" type="hidden" value="<%= tomcatLibDir %>" />
				</c:otherwise>
			</c:choose>

			<tr>
				<td colspan="2">
					<br />
				</td>
			</tr>
			<tr>
				<td>
					<liferay-ui:message key="plugin-repositories" /><br />

					<span style="font-size: xx-small;">(<liferay-ui:message key="enter-one-url-per-line" />)</span>
				</td>
				<td>
					<textarea class="liferay-textarea" name="<portlet:namespace />pluginRepositories" wrap="soft"><%= PrefsPropsUtil.getString(PropsUtil.PLUGIN_REPOSITORIES) %></textarea>

					<liferay-util:include page="/html/portlet/plugin_installer/repository_report.jsp" />
				</td>
			</tr>
			</table>

			<br />

			<input type="button" value="<liferay-ui:message key="save" />" onClick="<portlet:namespace />saveDeployConfiguration();" />
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
					<%@ include file="/html/portlet/plugin_installer/view_plugin_package.jspf" %>
				</c:when>
				<c:otherwise>
					<%@ include file="/html/portlet/plugin_installer/browse_repository.jspf" %>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>

	</form>
</c:if>