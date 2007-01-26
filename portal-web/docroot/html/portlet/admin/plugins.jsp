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

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);
portletURL.setParameter("struts_action", "/admin/view_plugin");

%>

<liferay-ui:tabs names="remote-deploy" backURL="<%=redirect%>"/>

<%
try {
%>

<%= LanguageUtil.get(pageContext, "list-of-available-plugins") %>
<br><br>

<%
	List headerNames = new ArrayList();

	headerNames.add("application");
	headerNames.add("type");
	headerNames.add("tags");
	headerNames.add(StringPool.BLANK);

	SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

	List results = PluginUtil.getAllPlugins();
	int total = results.size();

	searchContainer.setTotal(total);

	searchContainer.setResults(results);

	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		Plugin plugin = (Plugin) results.get(i);

		ResultRow row = new ResultRow(plugin, plugin.getModuleId(), i);

		PortletURL rowURL = renderResponse.createRenderURL();

		rowURL.setWindowState(WindowState.MAXIMIZED);

		rowURL.setParameter("struts_action", "/admin/view_plugin");
		rowURL.setParameter("redirect", currentURL);
		rowURL.setParameter("moduleId", plugin.getModuleId());
		rowURL.setParameter("repositoryURL", plugin.getRepositoryURL());

		TextSearchEntry rowTextEntry = new TextSearchEntry(SearchEntry.DEFAULT_ALIGN, SearchEntry.DEFAULT_VALIGN, plugin.getName(), rowURL.toString(), null, plugin.getShortDescription());

		// Name

		row.addText(rowTextEntry);

		// Type

		rowTextEntry = (TextSearchEntry) rowTextEntry.clone();

		rowTextEntry.setName(plugin.getType());

		row.addText(rowTextEntry);

		// Tags

		rowTextEntry = (TextSearchEntry) rowTextEntry.clone();

		StringBuffer tags = new StringBuffer();
		Iterator itr = plugin.getTags().iterator();

		while (itr.hasNext()) {
			String tag = (String) itr.next();
			tags.append(tag);
			if (itr.hasNext()) {
				tags.append(StringPool.COMMA + StringPool.SPACE);
			}
		}

		rowTextEntry.setName(tags.toString());

		row.addText(rowTextEntry);

		// Action

		row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/admin/plugin_action.jsp");

		// Add result row

		resultRows.add(row);
	}
%>

<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />

<%
}
catch (RuntimeException e) {
	e.printStackTrace();
%>
	<span class="error"><%= LanguageUtil.get(pageContext, "error-obtaining-available-plugins") %></span>
<%
}
%>