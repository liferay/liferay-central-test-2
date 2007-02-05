<%@ page import="com.liferay.portal.language.LanguageUtil" %>
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
String redirect = ParamUtil.getString(request, "redirect");

Plugin plugin = PluginUtil.getPluginById(moduleId, repositoryURL);

PortletURL installURL = renderResponse.createActionURL();

installURL.setWindowState(WindowState.MAXIMIZED);
installURL.setParameter("struts_action", "/admin/edit_server");
installURL.setParameter("cmd", "remoteDeploy");
installURL.setParameter("redirect", currentURL.toString());

// Breadcrumbs

PortletURL breadcrumbsURL = renderResponse.createRenderURL();

breadcrumbsURL.setWindowState(WindowState.MAXIMIZED);

breadcrumbsURL.setParameter("struts_action", "/admin/view");
breadcrumbsURL.setParameter("tabs1", tabs1);
breadcrumbsURL.setParameter("tabs2", tabs2);

String breadcrumbs = "<a href=\"" + breadcrumbsURL.toString() + "\">" + LanguageUtil.get(pageContext, "repositories") + "</a> &raquo; ";

breadcrumbsURL.setParameter("moduleId", moduleId);
breadcrumbsURL.setParameter("repositoryURL", repositoryURL);

breadcrumbs += "<a href=\"" + breadcrumbsURL.toString() + "\">" + plugin.getName() + "</a>";

%>

<style type="text/css">
	a.thumbnail img {
		border: 1px solid gray;
		padding: 5px;
		margin: 5px;
		background-color: #eee;
		width: 120px;
	}
</style>

<%=breadcrumbs%>

<br><br>

<liferay-ui:error key="invalidUrl" message="invalid-url"/>
<liferay-ui:error key="errorResponseFromServer" message="error-response-from-the-server"/>
<liferay-ui:error key="errorConnectingToServer" message="error-connecting-to-server"/>
<liferay-ui:success key="pluginDownloaded" message="the-plugin-has-been-downloaded-and-is-being-installed"/>

<table border="0" cellpadding="3" cellspacing="0">
<tr>
	<td align="right">
		<%= LanguageUtil.get(pageContext, "name") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<b><a href="<%= plugin.getPageURL() %>"><%= plugin.getName() %></a></b> (v<%= plugin.getVersion() %>) <a href="<%= plugin.getArtifactURL() %>">[<%= LanguageUtil.get(pageContext, "download") %>]</a>
	</td>
</tr>
<tr>
	<td align="right">
		<%= LanguageUtil.get(pageContext, "author") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<%= LanguageUtil.get(pageContext, plugin.getAuthor()) %>
	</td>
</tr>
<tr>
	<td align="right">
		<%= LanguageUtil.get(pageContext, "type") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<%= LanguageUtil.get(pageContext, plugin.getType()) %>
	</td>
</tr>
<tr>
	<td align="right">
		<%= LanguageUtil.get(pageContext, "tags") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>

		<%
		Iterator itr = plugin.getTags().iterator();

		while (itr.hasNext()) {
			String tag = (String)itr.next();
		%>

			<%= tag %><c:if test="<%= itr.hasNext() %>">, </c:if>

		<%
		}
		%>

	</td>
</tr>
<tr>
	<td align="right">
		<%= LanguageUtil.get(pageContext, "licenses") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>

		<%
		Iterator itr2 = plugin.getLicenses().iterator();

		while (itr2.hasNext()) {
			License license = (License)itr2.next();
		%>
		    <% if (Validator.isNotNull(license.getUrl())) { %>
				<a href="<%= license.getUrl()%>">
			<% } %>
			<%= license.getName() %>
			<% if (Validator.isNotNull(license.getUrl())) { %>
			</a>
			<% } %>
			<% if (license.isOsiApproved()) { %>
				(<%= LanguageUtil.get(pageContext, "open-source")%>)
			<% } %>

			<c:if test="<%= itr2.hasNext() %>">, </c:if>

		<%
		}
		%>

	</td>
</tr>
<tr>
	<td align="right">
		<%= LanguageUtil.get(pageContext, "supported-liferay-versions") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>

		<%
		Iterator itr3 = plugin.getLiferayVersions().iterator();

		while (itr3.hasNext()) {
			String liferayVersion = (String)itr3.next();
		%>

			<%= liferayVersion %><c:if test="<%= itr3.hasNext() %>">, </c:if>

		<%
		}
		%>

	</td>
</tr>
<tr>
	<td align="right">
		<%= LanguageUtil.get(pageContext, "repository") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<a href="<%= plugin.getRepositoryURL() %>"><%= plugin.getRepositoryURL() %></a>
	</td>
</tr>
<tr>
	<td align="right">
		<%= LanguageUtil.get(pageContext, "short-description") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<%= plugin.getShortDescription() %>
	</td>
</tr>
<% if ((plugin.getScreenshotURLs() != null) && !plugin.getScreenshotURLs().isEmpty()) { %>
	<tr>
		<td align="right" valign="top">
			<%= LanguageUtil.get(pageContext, "screenshots") %>
		</td>
		<td style="padding-left: 10px;"></td>
		<td valign="top">
			<%
			Iterator itr4 = plugin.getScreenshotURLs().iterator();

			while (itr4.hasNext()) {
				String screenshotURL = (String)itr4.next();
			%>

				<a href="<%= screenshotURL %>" class="thumbnail">
					<img alt="Thumbnail" src="<%= screenshotURL %>" align="left"/>
				</a>
			<%
			}
			%>
		</td>
	</tr>
<% } %>
<% if (Validator.isNotNull(plugin.getLongDescription())) { %>
	<tr>
		<td align="right" valign="top">
			<%= LanguageUtil.get(pageContext, "long-description") %>
		</td>
		<td style="padding-left: 10px;"></td>
		<td>
			<%= plugin.getLongDescription() %>
		</td>
	</tr>
<% } %>
<tr>
	<td colspan="2">
		&nbsp;
	</td>
</tr>
<tr>
	<td>
		&nbsp;
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<input type="hidden" name="<portlet:namespace/>url" value="<%=plugin.getArtifactURL()%>">
		<input class="portlet-form-button" type="button" value='<%=LanguageUtil.get(pageContext, "deploy")%>' onclick="<%= downloadProgressId%>.startProgress(); <portlet:namespace />saveServer('remoteDeploy', '<%=downloadProgressId%>', '<%=currentURL%>');">
	</td>
</tr>
</table>
<liferay-ui:upload-progress
	id="<%= downloadProgressId %>"
	message="downloading"
	redirect="<%= currentURL %>"
/>