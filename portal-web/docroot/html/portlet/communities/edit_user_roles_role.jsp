<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/communities/init.jsp" %>

<%
String redirect = (String)request.getAttribute("edit_user_roles.jsp-redirect");

Group group = (Group)request.getAttribute("edit_user_roles.jsp-group");
int roleType = (Integer)request.getAttribute("edit_user_roles.jsp-roleType");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_user_roles.jsp-portletURL");
%>

<div class="portlet-section-body results-row" style="border: 1px solid; padding: 5px;">
	<%= LanguageUtil.format(pageContext, "step-x-of-x", new String[] {"1", "2"}) %>

	<liferay-ui:message key="choose-a-role" />
</div>

<br />

<liferay-ui:tabs
	names="roles"
/>

<%
RoleSearch searchContainer = new RoleSearch(renderRequest, portletURL);
%>

<liferay-ui:search-form
	page="/html/portlet/enterprise_admin/role_search.jsp"
	searchContainer="<%= searchContainer %>"
/>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">

	<%
	RoleSearchTerms searchTerms = (RoleSearchTerms)searchContainer.getSearchTerms();

	List<Role> roles = RoleLocalServiceUtil.search(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), new Integer(roleType), QueryUtil.ALL_POS, QueryUtil.ALL_POS, searchContainer.getOrderByComparator());

	roles = EnterpriseAdminUtil.filterRoles(permissionChecker, roles);

	int total = roles.size();

	searchContainer.setTotal(total);

	List results = ListUtil.subList(roles, searchContainer.getStart(), searchContainer.getEnd());

	searchContainer.setResults(results);
	%>

	<div class="separator"><!-- --></div>

	<%
	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		Role curRole = (Role)results.get(i);

		curRole = curRole.toEscapedModel();

		ResultRow row = new ResultRow(curRole, curRole.getRoleId(), i);

		PortletURL rowURL = renderResponse.createRenderURL();

		rowURL.setParameter("struts_action", "/communities/edit_user_roles");
		rowURL.setParameter("redirect", redirect);
		rowURL.setParameter("groupId", String.valueOf(group.getGroupId()));
		rowURL.setParameter("roleId", String.valueOf(curRole.getRoleId()));

		// Name

		row.addText(curRole.getTitle(locale), rowURL);

		// Type

		row.addText(LanguageUtil.get(pageContext, curRole.getTypeLabel()), rowURL);

		// Description

		row.addText(curRole.getDescription(), rowURL);

		// Add result row

		resultRows.add(row);
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
</c:if>