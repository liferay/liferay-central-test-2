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

String tag = ParamUtil.getString(renderRequest, "tag");
String type = ParamUtil.getString(renderRequest, "type");

PortletURL searchURL = renderResponse.createRenderURL();

searchURL.setWindowState(WindowState.MAXIMIZED);
searchURL.setParameter("struts_action", "/admin/view");
searchURL.setParameter("tabs1", tabs1);
searchURL.setParameter("tabs2", tabs2);
searchURL.setParameter("redirect", redirect);

PortletURL viewPluginURL = renderResponse.createRenderURL();

viewPluginURL.setWindowState(WindowState.MAXIMIZED);
viewPluginURL.setParameter("struts_action", "/admin/view");
viewPluginURL.setParameter("tabs1", tabs1);
viewPluginURL.setParameter("tabs2", tabs2);

PortletURL reloadRepositoriesURL = renderResponse.createActionURL();

reloadRepositoriesURL.setWindowState(WindowState.MAXIMIZED);
reloadRepositoriesURL.setParameter("struts_action", "/admin/edit_server");
reloadRepositoriesURL.setParameter("tabs1", tabs1);
reloadRepositoriesURL.setParameter("tabs2", tabs2);
reloadRepositoriesURL.setParameter("cmd", "reloadRepositories");
reloadRepositoriesURL.setParameter("redirect", currentURL);

try {
%>

	<form action="<%= searchURL.toString() %>" method="post">

	<table border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<%= LanguageUtil.get(pageContext, "type") %>
		</td>
		<td style="padding-left: 5px;"></td>
		<td>
			<%= LanguageUtil.get(pageContext, "tag") %>
		</td>
		<td style="padding-left: 5px;"></td>
		<td>
			<%= LanguageUtil.get(pageContext, "repository") %>
		</td>
	</tr>
	<tr>
		<td>
			<select name="<portlet:namespace/>type">
				<option value=""><%= LanguageUtil.get(pageContext, "all") %></option>

				<%
				String[] types = PluginUtil.getSupportedTypes();

				for (int i = 0; i < types.length; i++) {
					String curType = types[i];
				%>

					<option <%= (type.equals(curType)) ? "selected" : "" %> value="<%= curType %>"><%= LanguageUtil.get(pageContext, curType) %></option>

				<%
				}
				%>

			</select>
		</td>
		<td style="padding-left: 5px;"></td>
		<td>
			<select name="<portlet:namespace/>tag">
				<option value=""><%= LanguageUtil.get(pageContext, "all") %></option>

				<%
				Iterator itr = PluginUtil.getAvailableTags().iterator();

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
				String[] repositoryURLs = PluginUtil.getRepositoryURLs();

				for (int i = 0; i < repositoryURLs.length; i++) {
					String curRepositoryURL = repositoryURLs[i];
				%>

					<option <%= (repositoryURL.equals(curRepositoryURL)) ? "selected" : "" %> value="<%= curRepositoryURL %>"><%= curRepositoryURL %></option>

				<%
				}
				%>

			</select>
		</td>
	</tr>
	</table>

	<br>

	<input class="portlet-form-button" type="submit" value="<%= LanguageUtil.get(pageContext, "search") %>">

	</form>

	<br><div class="beta-separator" style="clear: both;"></div><br>

	<%
	List headerNames = new ArrayList();

	headerNames.add("plugin-name");
	headerNames.add("type");
	headerNames.add("tags");
	headerNames.add(StringPool.BLANK);

	SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, viewPluginURL, headerNames, null);

	List plugins = PluginUtil.search(type, tag, repositoryURL);

	int total = plugins.size();

	searchContainer.setTotal(total);

	List results = ListUtil.subList(plugins, searchContainer.getStart(), searchContainer.getEnd());

	searchContainer.setResults(results);

	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		Plugin plugin = (Plugin) results.get(i);

		ResultRow row = new ResultRow(plugin, plugin.getModuleId(), i);

		PortletURL rowURL = renderResponse.createRenderURL();

		rowURL.setWindowState(WindowState.MAXIMIZED);

		rowURL.setParameter("struts_action", "/admin/view");
		rowURL.setParameter("tabs1", tabs1);
		rowURL.setParameter("tabs2", tabs2);
		rowURL.setParameter("redirect", currentURL);
		rowURL.setParameter("moduleId", plugin.getModuleId());
		rowURL.setParameter("repositoryURL", plugin.getRepositoryURL());

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

		TextSearchEntry rowTextEntry = new TextSearchEntry(SearchEntry.DEFAULT_ALIGN, SearchEntry.DEFAULT_VALIGN, LanguageUtil.get(pageContext, plugin.getType()), null, null, null);

		row.addText(rowTextEntry);

		// Tags

		rowTextEntry = (TextSearchEntry)rowTextEntry.clone();

		sb = new StringBuffer();

		itr = plugin.getTags().iterator();

		while (itr.hasNext()) {
			String curTag = (String)itr.next();

			sb.append(curTag);

			if (itr.hasNext()) {
				sb.append(StringPool.COMMA + StringPool.SPACE);
			}
		}

		rowTextEntry.setName(sb.toString());

		row.addText(rowTextEntry);

		// Action

		row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/admin/plugin_action.jsp");

		// Add result row

		resultRows.add(row);
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

	<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />

	<c:if test="<%= PluginUtil.getLastUpdateDate() != null %>">
		<br>

		<%= LanguageUtil.format(pageContext, "list-of-plugins-was-last-refreshed-on-x", dateFormatDateTime.format(PluginUtil.getLastUpdateDate())) %><br>
	</c:if>

	<%@ include file="/html/portlet/admin/repository_report.jsp" %>

	<br>

	<form action="<%= reloadRepositoriesURL.toString() %>" method="post">

	<input class="portlet-form-button" type="submit" value="<%= LanguageUtil.get(pageContext, "refresh") %>">

	</form>

<%
}
catch (PluginException e) {
	_log.error(e, e);
%>

	<span class="portlet-msg-error">
	<%= LanguageUtil.get(pageContext, "error-obtaining-available-plugins") %>
	</span>

<%
}
%>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.admin.plugins.jsp");
%>