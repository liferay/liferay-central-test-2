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
String downloadProgressId = "downloadPlugin" + System.currentTimeMillis();
String uploadProgressId = "uploadPlugin" + System.currentTimeMillis();
%>

<input type="hidden" name="<portlet:namespace/>progressId" value="">

<liferay-ui:tabs
	names="repositories,direct-install,configuration"
	param="tabs2"
	url="<%= portletURL.toString() %>"
/>

<c:choose>
	<c:when test='<%=tabs2.equals("direct-install")%>'>
		<liferay-ui:success key="pluginUploaded" message="the-plugin-was-uploaded-successfully-and-is-now-being-installed" />

		<liferay-ui:error exception="<%= UploadException.class %>" message="an-unexpected-error-occurred-while-uploading-your-file" />

		<%= LanguageUtil.get(pageContext, "upload-a-war-file-to-install-a-layout-template,-portlet,-or-theme") %>

		<br><br>

		<input class="form-text" name="<portlet:namespace />file" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" type="file">

		<br><br>

		<%= LanguageUtil.get(pageContext, "specify-an-optional-plugin-file-name") %>

		<br><br>

		<input class="form-text" name="<portlet:namespace />localDeployWARName" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;">

		<br><br>

		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "install") %>' onClick="<%= uploadProgressId %>.startProgress(); <portlet:namespace />saveServer('localDeploy', '<%= uploadProgressId %>');">

		<liferay-ui:upload-progress
			id="<%= uploadProgressId %>"
			message="uploading"
			redirect="<%= currentURL %>"
		/>

		<br><br><div class="beta-separator" style="clear: both"></div><br>

		<liferay-ui:success key="pluginDownloaded" message="the-plugin-was-downloaded-successfully-and-is-now-being-installed" />

		<%= LanguageUtil.get(pageContext, "specify-a-URL-for-a-remote-layout-template,-portlet,-or-theme") %>

		<%= LanguageUtil.format(pageContext, "for-example-x", "<i>http://easynews.dl.sourceforge.net/sourceforge/lportal/sample-jsp-portlet-" + ReleaseInfo.getVersion() + ".war</i>") %>

		<br><br>

		<input class="form-text" name="<portlet:namespace />url" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" value="">

		<br><br>

		<%= LanguageUtil.get(pageContext, "specify-an-optional-plugin-file-name") %>

		<%= LanguageUtil.format(pageContext, "for-example-x", "<i>sample-jsp-portlet.war</i>") %>

		<br><br>

		<input class="form-text" name="<portlet:namespace />remoteDeployWARName" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" value="">

		<br><br>

		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "install") %>' onClick="<%= downloadProgressId %>.startProgress(); <portlet:namespace />saveServer('remoteDeploy', '<%= downloadProgressId %>');">

		<liferay-ui:upload-progress
			id="<%= downloadProgressId %>"
			message="downloading"
			redirect="<%= currentURL %>"
		/>
	</c:when>
	<c:when test='<%=tabs2.equals("configuration")%>'>
		<table border="0" cellpadding="0" cellspacing="0">
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

				<%@ include file="/html/portlet/admin/repository_report.jsp" %>
			</td>
		</tr>
		</table>

		<br>

		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "save") %>' onClick="<portlet:namespace />saveServer('deployConfiguration', '<%= downloadProgressId %>');">
	</c:when>
	<c:otherwise>

		<%
		String moduleId = ParamUtil.getString(request, "moduleId");
		String repositoryURL = ParamUtil.getString(request, "repositoryURL");
		%>

		<c:choose>
			<c:when test="<%= Validator.isNotNull(moduleId) && Validator.isNotNull(repositoryURL) %>">
				<%@ include file="/html/portlet/admin/view_plugin.jsp" %>
			</c:when>
			<c:otherwise>
				<%@ include file="/html/portlet/admin/plugins.jsp" %>
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>