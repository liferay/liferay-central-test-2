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

<c:if test="<%= OmniadminUtil.isOmniadmin(user.getUserId())  && PrefsPropsUtil.getBoolean(PropsUtil.AUTO_DEPLOY_ENABLED)%>">
	<input class="portlet-form-button" type="button" onClick="submitForm(document.<portlet:namespace />fm, '<%= installPluginsURL.toString() %>');" value='<%=LanguageUtil.get(pageContext, "install-more-themes")%>'/>
	<br><br>
</c:if>
<%
List headerNames = new ArrayList();

headerNames.add("theme");
headerNames.add("active");
headerNames.add("roles");

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

List themes = themes = ThemeLocalUtil.getThemes(company.getCompanyId());

int total = themes.size();

searchContainer.setTotal(total);

List results = ListUtil.subList(themes, searchContainer.getStart(), searchContainer.getEnd());

searchContainer.setResults(results);

List resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.size(); i++) {
	Theme theme2 = (Theme) results.get(i);
	PluginSetting pluginSetting = PluginSettingLocalServiceUtil.getSettingOrDefault(company.getCompanyId(), theme2.getThemeId(), "theme");

	ResultRow row = new ResultRow(theme2, theme2.getThemeId().toString(), i);

	PortletURL rowURL = renderResponse.createRenderURL();

	rowURL.setWindowState(WindowState.MAXIMIZED);

	rowURL.setParameter("struts_action", "/admin/edit_plugin_setting");
	rowURL.setParameter("redirect", currentURL);
	rowURL.setParameter("pluginId", theme2.getThemeId());
	rowURL.setParameter("pluginType", ThemeImpl.PLUGIN_TYPE);

	// Name and Thumbnail

	StringBuffer sb = new StringBuffer();

	sb.append("<a href='");
	sb.append(rowURL.toString());
	sb.append("'>");
	sb.append("<img src='");
	sb.append(theme2.getContextPath());
	sb.append(theme2.getImagesPath());
	sb.append("/thumbnail.png");
	sb.append("' width='100' align='left' style='margin-right: 10px'/>");
	sb.append("<b>");
	sb.append(theme2.getName());
	sb.append("</b>");
	sb.append("</a>");
	sb.append("<br>");
	sb.append("<span style=\"font-size: xx-small;\">");
	sb.append(LanguageUtil.get(pageContext, "package"));
	sb.append(": ");
	sb.append((theme2.getPluginPackage() == null)?LanguageUtil.get(pageContext, "unknown"):(theme2.getPluginPackage().getName() + " (" + theme2.getPluginPackage().getModuleId() + ")"));
	sb.append("<br>");
	sb.append(LanguageUtil.get(pageContext, "color-schemes"));
	sb.append(": ");
	List colorSchemes = theme2.getColorSchemes();
	for (int j = 0; j < colorSchemes.size(); j++) {
		ColorScheme colorScheme2 = (ColorScheme) colorSchemes.get(j);
		sb.append(colorScheme2.getName());
		if ((j + 1) < colorSchemes.size()) {
			sb.append(", ");
		}
	}
	sb.append("</span>");
	row.addText(sb.toString());

	// Active

	boolean isActive = pluginSetting.isActive();
	row.addText(LanguageUtil.get(pageContext, (isActive ? "yes" : "no")));

	// Roles

	String roles = pluginSetting.getRoles();
	row.addText(roles);

	// Add result row

	resultRows.add(row);
}
%>

<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />