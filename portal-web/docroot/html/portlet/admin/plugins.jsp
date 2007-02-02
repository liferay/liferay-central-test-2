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

<%
String redirect = ParamUtil.getString(request, "redirect");

String currentTag = ParamUtil.getString(renderRequest, "tag");
String currentType = ParamUtil.getString(renderRequest, "type");

PortletURL viewPluginURL = renderResponse.createRenderURL();

viewPluginURL.setWindowState(WindowState.MAXIMIZED);
viewPluginURL.setParameter("struts_action", "/admin/view");
viewPluginURL.setParameter("tabs1", tabs1);
viewPluginURL.setParameter("tabs2", tabs2);


PortletURL searchURL = renderResponse.createRenderURL();

searchURL.setWindowState(WindowState.MAXIMIZED);
searchURL.setParameter("struts_action", "/admin/view");
searchURL.setParameter("redirect", redirect);
searchURL.setParameter("tabs1", tabs1);
searchURL.setParameter("tabs2", tabs2);


PortletURL reloadRepositoriesURL = renderResponse.createActionURL();

reloadRepositoriesURL.setWindowState(WindowState.MAXIMIZED);
reloadRepositoriesURL.setParameter("struts_action", "/admin/edit_server");
reloadRepositoriesURL.setParameter("cmd", "reloadRepositories");
reloadRepositoriesURL.setParameter("redirect", currentURL);
reloadRepositoriesURL.setParameter("tabs1", tabs1);
reloadRepositoriesURL.setParameter("tabs2", tabs2);

%>

<%
try {
%>

<%= LanguageUtil.get(pageContext, "list-of-available-plugins") %>
<br><br>

<form action="<%=searchURL.toString()%>" method="post">
	<div style="float: left; padding-right: 5px;">
		<%= LanguageUtil.get(pageContext, "type") %><br />
		<select name="<portlet:namespace/>type">
			<option value=""><%= LanguageUtil.get(pageContext, "all") %></option>
			<%
				String[] types = PluginUtil.getSupportedTypes();
				for (int i = 0; i < types.length; i++) {
					String type = types[i];
			%>
				<option value="<%=type%>" <%= (type.equals(currentType))?"selected":"" %>><%= LanguageUtil.get(pageContext, type) %></option>
			<%
				}
			%>
		</select>

	</div>

	<div style="float: left; padding-right: 5px;">
		<%= LanguageUtil.get(pageContext, "tag") %><br />
		<select name="<portlet:namespace/>tag">
			<option value=""><%= LanguageUtil.get(pageContext, "all") %></option>
			<%
				Collection tags = PluginUtil.getAvailableTags();
				for (Iterator iterator = tags.iterator(); iterator.hasNext();) {
					String tag = (String) iterator.next();
			%>
				<option value="<%=tag%>" <%= (tag.equals(currentTag))?"selected":"" %>><%= tag %></option>
			<%
				}
			%>
		</select>
	</div>

	<div style="float: left; padding-right: 5px;">
		<%= LanguageUtil.get(pageContext, "repository") %><br />
		<select name="<portlet:namespace/>repositoryURL">
			<option value=""><%= LanguageUtil.get(pageContext, "all") %></option>
			<%
				String[] repositoryURLs = PluginUtil.getRepositoryURLs();
				for (int i = 0; i < repositoryURLs.length; i++) {
					String repositoryURL2 = repositoryURLs[i];
			%>
				<option value="<%=repositoryURL2%>" <%= (repositoryURL2.equals(repositoryURL))?"selected":"" %>><%= repositoryURL2 %></option>
			<%
				}
			%>
		</select>
	</div>
	<table border="0" cellpadding="0" cellspacing="0" style="clear: both; margin-top: 5px;">
	<tr>
		<td>
			<input class="portlet-form-button" type="submit" value="<%=LanguageUtil.get(pageContext, "search")%>">
		</td>
	</tr>
	</table>
</form>

<br><div class="beta-separator" style="clear: both"></div><br>

<%
	List headerNames = new ArrayList();

	headerNames.add("plugin-name");
	headerNames.add("type");
	headerNames.add("tags");
	headerNames.add(StringPool.BLANK);

	SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, viewPluginURL, headerNames, null);

	List results = PluginUtil.search(currentType, currentTag, repositoryURL);
	int total = results.size();

	searchContainer.setTotal(total);

	searchContainer.setResults(results);

	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		Plugin plugin = (Plugin) results.get(i);

		ResultRow row = new ResultRow(plugin, plugin.getModuleId(), i);

		PortletURL rowURL = renderResponse.createRenderURL();

		rowURL.setWindowState(WindowState.MAXIMIZED);

		rowURL.setParameter("struts_action", "/admin/view");
		rowURL.setParameter("redirect", currentURL);
		rowURL.setParameter("moduleId", plugin.getModuleId());
		rowURL.setParameter("repositoryURL", plugin.getRepositoryURL());
		rowURL.setParameter("tabs1", tabs1);
		rowURL.setParameter("tabs2", tabs2);

		// Name and short description

		StringBuffer sb = new StringBuffer();

		sb.append("<b>");
		sb.append(plugin.getName());
		sb.append("</b> ");
		sb.append(plugin.getVersion());

		if (Validator.isNotNull(plugin.getShortDescription())) {
			sb.append("<br>");
			sb.append("<span style=\"font-size: xx-small;\">");
			sb.append(plugin.getShortDescription());
			sb.append("</span>");
		}

		row.addText(sb.toString(), rowURL);

		// Type

		TextSearchEntry rowTextEntry = new TextSearchEntry(SearchEntry.DEFAULT_ALIGN, SearchEntry.DEFAULT_VALIGN, plugin.getType(), null, null, null);

		row.addText(rowTextEntry);

		// Tags

		rowTextEntry = (TextSearchEntry) rowTextEntry.clone();

		StringBuffer pluginTags = new StringBuffer();
		Iterator itr = plugin.getTags().iterator();

		while (itr.hasNext()) {
			String tag = (String) itr.next();
			pluginTags.append(tag);
			if (itr.hasNext()) {
				pluginTags.append(StringPool.COMMA + StringPool.SPACE);
			}
		}

		rowTextEntry.setName(pluginTags.toString());

		row.addText(rowTextEntry);

		// Action

		row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/admin/plugin_action.jsp");

		// Add result row

		resultRows.add(row);
	}
%>

<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />

<br><div class="beta-separator" style="clear: both"></div><br>

<% if (Validator.isNotNull("" + PluginUtil.getLastUpdateDate())) { %>
	<%=LanguageUtil.get(pageContext, "plugin-list-obtained-from-remote-repositories-on-date")%>: <%= dateFormatDateTime.format(PluginUtil.getLastUpdateDate())%>
<% } %>
<br>

<%@ include file="/html/portlet/admin/repository_report.jsp" %>

<br>

<form action="<%=reloadRepositoriesURL.toString()%>" method="post">
	<input class="portlet-form-button" type="submit" value="<%=LanguageUtil.get(pageContext, "reload")%>">
</form>


<%
}
catch (PluginException e) {
	e.printStackTrace();
%>
	<span class="error"><%= LanguageUtil.get(pageContext, "error-obtaining-available-plugins") %></span>
<%
}
%>