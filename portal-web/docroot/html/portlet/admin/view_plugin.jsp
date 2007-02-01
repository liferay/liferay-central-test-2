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

<%@ include file="/html/portlet/admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String moduleId = ParamUtil.getString(request, "moduleId");

String repositoryURL = ParamUtil.getString(request, "repositoryURL");

Plugin plugin = PluginUtil.getPluginById(moduleId, repositoryURL);

PortletURL installURL = renderResponse.createActionURL();

installURL.setWindowState(WindowState.MAXIMIZED);
installURL.setParameter("struts_action", "/admin/edit_server");
installURL.setParameter("cmd", "remoteDeploy");
installURL.setParameter("url", plugin.getArtifactURL());
installURL.setParameter("redirect", currentURL.toString());

String uploadProgressId = "pluginInstaller" + System.currentTimeMillis();
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

<liferay-ui:tabs names="remote-deploy" backURL="<%=redirect%>"/>

<liferay-ui:error key="invalidUrl" message="invalid-url"/>
<liferay-ui:error key="errorResponseFromServer" message="error-response-form-server"/>
<liferay-ui:error key="errorConnectingToServer" message="error-connecting-to-server"/>

<table border="0" cellpadding="0" cellspacing="0">
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "name") %>:
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<%= plugin.getName() %>
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "version") %>:
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<%= plugin.getVersion() %>
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "author") %>:
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<%= LanguageUtil.get(pageContext, plugin.getAuthor()) %>
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "type") %>:
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<%= LanguageUtil.get(pageContext, plugin.getType()) %>
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "tags") %>:
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
	<td>
		<%= LanguageUtil.get(pageContext, "licenses") %>:
	</td>
	<td style="padding-left: 10px;"></td>
	<td>

		<%
		Iterator itr2 = plugin.getLicenses().iterator();

		while (itr2.hasNext()) {
			String license = (String)itr2.next();
		%>

			<%= license %><c:if test="<%= itr2.hasNext() %>">, </c:if>

		<%
		}
		%>

	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "supported-liferay-versions") %>:
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
	<td>
		<%= LanguageUtil.get(pageContext, "page-url") %>:
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<a href="<%= plugin.getPageURL() %>"><%= plugin.getPageURL() %></a>
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "plugin-url") %>:
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<a href="<%= plugin.getArtifactURL() %>"><%= StringUtil.shorten(plugin.getArtifactURL(), 80) %></a>
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "repository") %>:
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<a href="<%= plugin.getRepositoryURL() %>"><%= plugin.getRepositoryURL() %></a>
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "short-description") %>:
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<%= plugin.getShortDescription() %>
	</td>
</tr>
<% if ((plugin.getScreenshotURLs() != null) && !plugin.getScreenshotURLs().isEmpty()) { %>
	<tr>
		<td>
			<%= LanguageUtil.get(pageContext, "screenshots") %>:
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
		<td>
			<%= LanguageUtil.get(pageContext, "long-description") %>:
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
		<form action="<%=installURL.toString()%>" method="post">
			<input type="hidden" name="<portlet:namespace/>progressId" value="<%=uploadProgressId%>"/>
			<input type="submit" value="<%=LanguageUtil.get(pageContext, "install")%>" onclick='<%= uploadProgressId%>.startProgress()'>
		</form>
	</td>
</tr>
</table>
<liferay-ui:upload-progress
	id="<%= uploadProgressId %>"
	message="downloading"
	redirect="<%= redirect %>"
/>
