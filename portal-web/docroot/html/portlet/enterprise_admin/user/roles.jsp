<%
/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
User selUser = (User)request.getAttribute("user.selUser");
List<Role> regularRoles = (List<Role>)request.getAttribute("user.regularRoles");
%>

<h3><liferay-ui:message key="roles" /></h3>

<%
SearchContainer searchContainer = new SearchContainer();

List<String> headerNames = new ArrayList<String>();

headerNames.add("name");

if (selUser.getUserId() != user.getUserId()) {
	headerNames.add(StringPool.BLANK);
}

searchContainer.setHeaderNames(headerNames);

if (selUser.getUserId() == user.getUserId()) {
	searchContainer.setEmptyResultsMessage("you-do-not-have-any-regular-roles");
}
else {
	searchContainer.setEmptyResultsMessage("the-user-does-not-have-any-regular-roles");
}

List resultRows = searchContainer.getResultRows();

for (int i = 0; i < regularRoles.size(); i++) {
	Role role = (Role) regularRoles.get(i);

	ResultRow row = new ResultRow(new Object[] {selUser, role, currentURL}, role.getRoleId(), i);

	// Name

	row.addText(role.getName());

	// Action

	if (selUser.getUserId() != user.getUserId()) {
		row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/enterprise_admin/user_role_action.jsp");
	}

	// Add result row

	resultRows.add(row);
}
%>

<h4><liferay-ui:message key="regular-roles" /></h4>

<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

<br />
<input onclick="javascript: location.href = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_user_regular_role_assignments" /><portlet:param name="redirect" value='<%= currentURL + "&" + renderResponse.getNamespace() + "tabs2=regular-roles" %>' /><portlet:param name="p_u_i_d" value="<%= String.valueOf(selUser.getUserId()) %>" /></portlet:renderURL>';" type="button" value="<liferay-ui:message key="assign-regular-roles" />" />