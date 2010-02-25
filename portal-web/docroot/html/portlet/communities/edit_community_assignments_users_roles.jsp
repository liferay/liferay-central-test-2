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
String tabs1 = (String)request.getAttribute("edit_community_assignments.jsp-tabs1");
String tabs2 = (String)request.getAttribute("edit_community_assignments.jsp-tabs2");

String redirect = (String)request.getAttribute("edit_community_assignments.jsp-redirect");

String cur = (String)request.getAttribute("edit_community_assignments.jsp-cur");

Group group = (Group)request.getAttribute("edit_community_assignments.jsp-group");
User selUser = (User)request.getAttribute("edit_community_assignments.jsp-selUser");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_community_assignments.jsp-portletURL");

portletURL.setParameter("p_u_i_d", String.valueOf(selUser.getUserId()));
%>

<aui:input name="p_u_i_d" type="hidden" value="<%= selUser.getUserId() %>" />
<aui:input name="addRoleIds" type="hidden" />
<aui:input name="removeRoleIds" type="hidden" />

<liferay-ui:message key="edit-community-roles-for-user" />: <%= selUser.getFullName() %>

<br /><br />

<%
RoleSearch searchContainer = new RoleSearch(renderRequest, portletURL);

searchContainer.setRowChecker(new UserGroupRoleRoleChecker(renderResponse, selUser, group));
%>

<liferay-ui:search-form
	page="/html/portlet/enterprise_admin/role_search.jsp"
	searchContainer="<%= searchContainer %>"
/>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">

	<%
	RoleSearchTerms searchTerms = (RoleSearchTerms)searchContainer.getSearchTerms();

	int total = RoleLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), new Integer(RoleConstants.TYPE_COMMUNITY));

	searchContainer.setTotal(total);

	List results = RoleLocalServiceUtil.search(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), new Integer(RoleConstants.TYPE_COMMUNITY), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

	searchContainer.setResults(results);

	PortletURL updateRoleAssignmentsURL = renderResponse.createRenderURL();

	updateRoleAssignmentsURL.setParameter("struts_action", "/communities/edit_community_assignments");
	updateRoleAssignmentsURL.setParameter("tabs1", tabs1);
	updateRoleAssignmentsURL.setParameter("tabs2", tabs2);
	updateRoleAssignmentsURL.setParameter("redirect", redirect);
	updateRoleAssignmentsURL.setParameter("p_u_i_d", String.valueOf(selUser.getUserId()));
	updateRoleAssignmentsURL.setParameter("groupId", String.valueOf(group.getGroupId()));
	%>

	<div class="separator"><!-- --></div>

	<%
	String taglibOnClick = renderResponse.getNamespace() + "updateUserGroupRole('" + updateRoleAssignmentsURL.toString() + StringPool.AMPERSAND + renderResponse.getNamespace() + "cur=" + cur + "');";
	%>

	<aui:button onClick="<%= taglibOnClick %>" value="update-associations" />

	<br /><br />

	<%
	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		Role role = (Role)results.get(i);

		role = role.toEscapedModel();

		ResultRow row = new ResultRow(role, role.getRoleId(), i);

		// Name

		row.addText(role.getTitle(locale));

		// Type

		row.addText(LanguageUtil.get(pageContext, role.getTypeLabel()));

		// Description

		row.addText(role.getDescription());

		// CSS

		row.setClassName(EnterpriseAdminUtil.getCssClassName(role));
		row.setClassHoverName(EnterpriseAdminUtil.getCssClassName(role));

		// Add result row

		resultRows.add(row);
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
</c:if>