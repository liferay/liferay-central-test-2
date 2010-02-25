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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
PortletURL portletURL = (PortletURL)request.getAttribute("view.jsp-portletURL");

String viewUsersRedirect = ParamUtil.getString(request, "viewUsersRedirect");

if (Validator.isNotNull(viewUsersRedirect)) {
	portletURL.setParameter("viewUsersRedirect", viewUsersRedirect);
}
%>

<liferay-ui:error exception="<%= RequiredUserException.class %>" message="you-cannot-delete-or-deactivate-yourself" />

<liferay-util:include page="/html/portlet/enterprise_admin/user/toolbar.jsp">
	<liferay-util:param name="toolbarItem" value="view-all" />
	<liferay-util:param name="backURL" value="<%= viewUsersRedirect %>" />
</liferay-util:include>

<c:if test="<%= Validator.isNotNull(viewUsersRedirect) %>">
	<aui:input name="viewUsersRedirect" type="hidden" value="<%= viewUsersRedirect %>" />
</c:if>

<liferay-ui:search-container
	rowChecker="<%= new RowChecker(renderResponse) %>"
	searchContainer="<%= new UserSearch(renderRequest, portletURL) %>"
>
	<aui:input name="deleteUserIds" type="hidden" />
	<aui:input name="usersRedirect" type="hidden" value="<%= portletURL.toString() %>" />

	<%
	UserDisplayTerms displayTerms = (UserDisplayTerms)searchContainer.getDisplayTerms();
	UserSearchTerms searchTerms = (UserSearchTerms)searchContainer.getSearchTerms();

	if (!searchTerms.isAdvancedSearch() && !searchTerms.hasActive()) {
		displayTerms.setActive(Boolean.TRUE);
		searchTerms.setActive(Boolean.TRUE);
	}

	long organizationId = searchTerms.getOrganizationId();
	long roleId = searchTerms.getRoleId();
	long userGroupId = searchTerms.getUserGroupId();

	Organization organization = null;

	if ((organizationId > 0)) {
		try {
			organization = OrganizationLocalServiceUtil.getOrganization(organizationId);
		}
		catch (NoSuchOrganizationException nsoe) {
		}
	}

	Role role = null;

	if (roleId > 0) {
		try {
			role = RoleLocalServiceUtil.getRole(roleId);
		}
		catch (NoSuchRoleException nsre) {
		}
	}

	UserGroup userGroup = null;

	if (userGroupId > 0) {
		try {
			userGroup = UserGroupLocalServiceUtil.getUserGroup(userGroupId);
		}
		catch (NoSuchUserGroupException nsuge) {
		}
	}
	%>

	<c:if test="<%= organization != null %>">
		<aui:input name="<%= UserDisplayTerms.ORGANIZATION_ID %>" type="hidden" value="<%= organization.getOrganizationId() %>" />

		<h3><%= LanguageUtil.format(pageContext, "users-of-x", HtmlUtil.escape(organization.getName())) %></h3>

		<%
		EnterpriseAdminUtil.addPortletBreadcrumbEntries(organization, request, renderResponse);

		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "view-users"), currentURL);
		%>

	</c:if>

	<c:if test="<%= role != null %>">
		<aui:input name="<%= UserDisplayTerms.ROLE_ID %>" type="hidden" value="<%= role.getRoleId() %>" />

		<h3><%= LanguageUtil.format(pageContext, "users-with-role-x", HtmlUtil.escape(role.getTitle(locale))) %></h3>

		<%
		PortalUtil.addPortletBreadcrumbEntry(request, role.getName(), null);
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "view-users"), currentURL);
		%>

	</c:if>

	<c:if test="<%= userGroup != null %>">
		<aui:input name="<%= UserDisplayTerms.USER_GROUP_ID %>" type="hidden" value="<%= userGroup.getUserGroupId() %>" />

		<h3><%= LanguageUtil.format(pageContext, "users-of-x", HtmlUtil.escape(userGroup.getName())) %></h3>

		<%
		PortalUtil.addPortletBreadcrumbEntry(request, userGroup.getName(), null);
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "view-users"), currentURL);
		%>

	</c:if>

	<liferay-ui:search-form
		page="/html/portlet/enterprise_admin/user_search.jsp"
	/>

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">

		<%
		LinkedHashMap userParams = new LinkedHashMap();

		if (organizationId > 0) {
			userParams.put("usersOrgs", new Long(organizationId));
		}
		else {
			if (filterManageableOrganizations && !UserPermissionUtil.contains(permissionChecker, ResourceConstants.PRIMKEY_DNE, ActionKeys.VIEW)) {
				Long[] organizationIds = EnterpriseAdminUtil.getOrganizationIds(user.getOrganizations());

				userParams.put("usersOrgs", organizationIds);
			}
		}

		if (roleId > 0) {
			userParams.put("usersRoles", new Long(roleId));
		}

		if (userGroupId > 0) {
			userParams.put("usersUserGroups", new Long(userGroupId));
		}
		%>

		<liferay-ui:search-container-results>
			<c:choose>
				<c:when test="<%= PropsValues.USERS_SEARCH_WITH_INDEX %>">
					<%@ include file="/html/portlet/enterprise_admin/user_search_results_index.jspf" %>
				</c:when>
				<c:otherwise>
					<%@ include file="/html/portlet/enterprise_admin/user_search_results_database.jspf" %>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.User"
			escapedModel="<%= true %>"
			keyProperty="userId"
			modelVar="user2"
		>
			<liferay-portlet:renderURL varImpl="rowURL">
				<portlet:param name="struts_action" value="/enterprise_admin/edit_user" />
				<portlet:param name="redirect" value="<%= searchContainer.getIteratorURL().toString() %>" />
				<portlet:param name="p_u_i_d" value="<%= String.valueOf(user2.getUserId()) %>" />
			</liferay-portlet:renderURL>

			<%@ include file="/html/portlet/enterprise_admin/user/search_columns.jspf" %>

			<liferay-ui:search-container-column-jsp
				align="right"
				path="/html/portlet/enterprise_admin/user_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<div class="separator"><!-- --></div>

		<%
		boolean hasButtons = false;
		%>

		<c:if test="<%= searchTerms.hasActive() && (searchTerms.isActive() || (!searchTerms.isActive() && PropsValues.USERS_DELETE)) %>">

			<%
			hasButtons = true;

			String taglibOnClick = renderResponse.getNamespace() + "deleteUsers('" + (searchTerms.isActive() ? Constants.DEACTIVATE : Constants.DELETE) + "');";
			%>

			<aui:button onClick="<%= taglibOnClick %>" value='<%= searchTerms.isActive() ? Constants.DEACTIVATE : Constants.DELETE %>' />
		</c:if>

		<c:if test="<%= searchTerms.hasActive() && !searchTerms.isActive() %>">

			<%
			hasButtons = true;

			String taglibOnClick = renderResponse.getNamespace() + "deleteUsers('" + Constants.RESTORE + "');";
			%>

			<aui:button onClick="<%= taglibOnClick %>" value="restore" />
		</c:if>

		<c:if test="<%= hasButtons %>">
			<div>
				<br />
			</div>
		</c:if>

		<%
		if (!hasButtons) {
			searchContainer.setRowChecker(null);
		}
		%>

		<liferay-ui:search-iterator />
	</c:if>
</liferay-ui:search-container>