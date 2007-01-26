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

%>

<liferay-ui:tabs names="remote-deploy" backURL="<%=redirect%>"/>

<liferay-ui:error key="invalidUrl" message="invalid-url"/>
<liferay-ui:error key="errorResponseFromServer" message="error-response-form-server"/>
<liferay-ui:error key="errorConnectingToServer" message="error-connecting-to-server"/>

<!-- moduleId: <%= moduleId%> -->
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
		<%= LanguageUtil.get(pageContext, "page-url") %>:
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<a href="<%= plugin.getPageURL() %>"><%= plugin.getPageURL() %></a>
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
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "long-description") %>:
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<%= plugin.getLongDescription() %>
	</td>
</tr>
<tr>
	<td>
		&nbsp;
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<form action="<%=installURL.toString()%>" method="post">
			<input type="submit" value="<%=LanguageUtil.get(pageContext, "install")%>">
		</form>
	</td>
</tr>
</table>