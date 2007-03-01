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
<c:if test="<%= OmniadminUtil.isOmniadmin(user.getUserId()) && PrefsPropsUtil.getBoolean(PropsUtil.AUTO_DEPLOY_ENABLED) %>">
	<input class="portlet-form-button" type="button" onClick="submitForm(document.<portlet:namespace />fm, '<%= installPluginsURL.toString() %>');" value='<%=LanguageUtil.get(pageContext, "install-more-portlets")%>'/>
	<br><br>
</c:if>


<%
List headerNames = new ArrayList();

headerNames.add("portlet");
headerNames.add("active");
//headerNames.add("indexed");
headerNames.add("roles");

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

List portlets = PortletLocalServiceUtil.getPortlets(company.getCompanyId(), false, false);

Collections.sort(portlets, new PortletTitleComparator(application, locale));

int total = portlets.size();

searchContainer.setTotal(total);

List results = ListUtil.subList(portlets, searchContainer.getStart(), searchContainer.getEnd());

searchContainer.setResults(results);

List resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.size(); i++) {
	Portlet portlet = (Portlet)results.get(i);

	ResultRow row = new ResultRow(portlet, portlet.getPrimaryKey().toString(), i);

	PortletURL rowURL = renderResponse.createRenderURL();

	rowURL.setWindowState(WindowState.MAXIMIZED);

	rowURL.setParameter("struts_action", "/admin/edit_portlet");
	rowURL.setParameter("redirect", currentURL);
	rowURL.setParameter("portletId", portlet.getPortletId());

	// Name and description

	StringMaker sm = new StringMaker();

	String title = PortalUtil.getPortletTitle(portlet, application, locale);
	String displayName = portlet.getDisplayName();

	sm.append("<a href='");
	sm.append(rowURL.toString());
	sm.append("'>");
	sm.append("<b>");
	sm.append(title);
	sm.append("</b>");
	sm.append("</a>");

	sm.append("<br>");
	sm.append("<span style=\"font-size: xx-small;\">");
	sm.append(LanguageUtil.get(pageContext, "package"));
	sm.append(": ");
	sm.append((portlet.getPluginPackage() == null)?LanguageUtil.get(pageContext, "unknown"):(portlet.getPluginPackage().getName() + " (" + portlet.getPluginPackage().getModuleId() + ")"));
	if (Validator.isNotNull(displayName) && !title.equals(displayName)) {
		sm.append("<br>");
		sm.append(portlet.getDisplayName());
	}
	sm.append("</span>");

	row.addText(sm.toString(), rowURL);

	// Active

	row.addText(LanguageUtil.get(pageContext, (portlet.isActive() ? "yes" : "no")));

	// Indexed

	//row.addText(LanguageUtil.get(pageContext, (Validator.isNotNull(portlet.getIndexerClass()) ? "yes" : "no")), rowURL);

	// Roles

	row.addText(StringUtil.merge(portlet.getRolesArray(), ", "));

	// Add result row

	resultRows.add(row);
}
%>

<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />