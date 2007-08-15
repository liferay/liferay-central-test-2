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

<%@ include file="/html/portlet/communities/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "users");
String tabs2 = ParamUtil.getString(request, "tabs2", "current");

String cur = ParamUtil.getString(request, "cur");

String redirect = ParamUtil.getString(request, "redirect");

Group group = (Group)request.getAttribute(WebKeys.GROUP);

User selectedUser = PortalUtil.getSelectedUser(request, false);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/communities/edit_community_assignments");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("groupId", String.valueOf(group.getGroupId()));
%>

<script type="text/javascript">
	function <portlet:namespace />updateGroupOrganizations(redirect) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "group_organizations";
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = redirect;
		document.<portlet:namespace />fm.<portlet:namespace />addOrganizationIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
		document.<portlet:namespace />fm.<portlet:namespace />removeOrganizationIds.value = Liferay.Util.listUncheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/communities/edit_community_assignments" /></portlet:actionURL>");
	}

	function <portlet:namespace />updateGroupUserGroups(redirect) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "group_user_groups";
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = redirect;
		document.<portlet:namespace />fm.<portlet:namespace />addUserGroupIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
		document.<portlet:namespace />fm.<portlet:namespace />removeUserGroupIds.value = Liferay.Util.listUncheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/communities/edit_community_assignments" /></portlet:actionURL>");
	}

	function <portlet:namespace />updateGroupUsers(redirect) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "group_users";
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = redirect;
		document.<portlet:namespace />fm.<portlet:namespace />addUserIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
		document.<portlet:namespace />fm.<portlet:namespace />removeUserIds.value = Liferay.Util.listUncheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/communities/edit_community_assignments" /></portlet:actionURL>");
	}

	function <portlet:namespace />updateUserGroupRole(redirect) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "user_group_role";
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = redirect;
		document.<portlet:namespace />fm.<portlet:namespace />addRoleIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
		document.<portlet:namespace />fm.<portlet:namespace />removeRoleIds.value = Liferay.Util.listUncheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/communities/edit_community_assignments" /></portlet:actionURL>");
	}
</script>

<form action="<%= portletURL.toString() %>" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />tabs1" type="hidden" value="<%= tabs1 %>" />
<input name="<portlet:namespace />tabs2" type="hidden" value="<%= tabs2 %>" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= redirect %>" />
<input name="<portlet:namespace />groupId" type="hidden" value="<%= String.valueOf(group.getGroupId()) %>" />

<table border="0" cellpadding="0" cellspacing="0" width="100%">
<tr>
	<td>
		<liferay-ui:message key="edit-assignments-for-community" />: <%= group.getName() %>
	</td>
	<td align="right">
		&laquo; <a href="<%= redirect %>"><liferay-ui:message key="back" /></a>
	</td>
</tr>
</table>

<br />

<liferay-ui:tabs
	names="users,organizations,locations,user-groups"
	param="tabs1"
	url="<%= portletURL.toString() %>"
/>

<c:choose>
	<c:when test='<%= tabs1.equals("users") %>'>
		<c:choose>
			<c:when test="<%= selectedUser == null %>">
				<input name="<portlet:namespace />addUserIds" type="hidden" value="" />
				<input name="<portlet:namespace />removeUserIds" type="hidden" value="" />

				<liferay-ui:tabs
					names="current,available"
					param="tabs2"
					url="<%= portletURL.toString() %>"
				/>

				<%
				UserSearch searchContainer = new UserSearch(renderRequest, portletURL);

				searchContainer.setRowChecker(new UserGroupChecker(renderResponse, group));
				%>

				<liferay-ui:search-form
					page="/html/portlet/enterprise_admin/user_search.jsp"
					searchContainer="<%= searchContainer %>"
				/>

				<%
				UserSearchTerms searchTerms = (UserSearchTerms)searchContainer.getSearchTerms();

				LinkedHashMap userParams = new LinkedHashMap();

				if (tabs2.equals("current")) {
					userParams.put("usersGroups", new Long(group.getGroupId()));
				}
				%>

				<%@ include file="/html/portlet/enterprise_admin/user_search_results.jsp" %>

				<div class="separator"><!-- --></div>

				<input type="button" value="<liferay-ui:message key="update-associations" />" onClick="<portlet:namespace />updateGroupUsers('<%= portletURL.toString() %>&<portlet:namespace />cur=<%= cur %>');" />

				<br /><br />

				<%
				List headerNames = new ArrayList();

				headerNames.add("name");
				headerNames.add("screen-name");
				headerNames.add("email-address");

				if (tabs2.equals("current")) {
					headerNames.add("community-roles");
					headerNames.add(StringPool.BLANK);
				}

				searchContainer.setHeaderNames(headerNames);

				List resultRows = searchContainer.getResultRows();

				for (int i = 0; i < results.size(); i++) {
					User user2 = (User)results.get(i);

					ResultRow row = new ResultRow(new Object[] {user2, group}, user2.getUserId(), i);

					// Name, screen name, and email address

					row.addText(user2.getFullName());
					row.addText(user2.getScreenName());
					row.addText(user2.getEmailAddress());

					// Community roles and action

					if (tabs2.equals("current")) {
						List userGroupRoles = UserGroupRoleLocalServiceUtil.getUserGroupRoles(user2.getUserId(), group.getGroupId());

						Iterator itr = userGroupRoles.iterator();

						StringMaker roleNames = new StringMaker();

						while (itr.hasNext()) {
							UserGroupRole userGroupRole = (UserGroupRole)itr.next();

							Role role = RoleLocalServiceUtil.getRole(userGroupRole.getRoleId());

							roleNames.append(role.getName());

							if (itr.hasNext()) {
								roleNames.append(StringPool.COMMA + StringPool.SPACE);
							}
						}

						row.addText(roleNames.toString());

						row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/communities/user_action.jsp");
					}

					// Add result row

					resultRows.add(row);
				}
				%>

				<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

				<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
			</c:when>
			<c:otherwise>
				<input name="<portlet:namespace />p_u_i_d" type="hidden" value="<%= selectedUser.getUserId() %>" />
				<input name="<portlet:namespace />addRoleIds" type="hidden" value="" />
				<input name="<portlet:namespace />removeRoleIds" type="hidden" value="" />

				<%= LanguageUtil.get(pageContext, "edit-community-roles-for-user")%>: <%= selectedUser.getFullName() %>

				<br /><br />

				<%
				RoleSearch searchContainer = new RoleSearch(renderRequest, portletURL);

				searchContainer.setRowChecker(new UserGroupRoleRoleChecker(renderResponse, selectedUser, group));

				List headerNames = searchContainer.getHeaderNames();
				%>

				<liferay-ui:search-form
					page="/html/portlet/enterprise_admin/role_search.jsp"
					searchContainer="<%= searchContainer %>"
				/>

				<c:if test="<%= renderRequest.getWindowState().equals(WindowState.MAXIMIZED) %>">

					<%
					RoleSearchTerms searchTerms = (RoleSearchTerms)searchContainer.getSearchTerms();

					int total = RoleLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), new Integer(RoleImpl.TYPE_COMMUNITY));

					searchContainer.setTotal(total);

					List results = RoleLocalServiceUtil.search(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), new Integer(RoleImpl.TYPE_COMMUNITY), searchContainer.getStart(), searchContainer.getEnd());

					searchContainer.setResults(results);

					PortletURL updateRoleAssignmentsURL = renderResponse.createRenderURL();

					updateRoleAssignmentsURL.setWindowState(WindowState.MAXIMIZED);

					updateRoleAssignmentsURL.setParameter("struts_action", "/communities/edit_community_assignments");
					updateRoleAssignmentsURL.setParameter("tabs1", tabs1);
					updateRoleAssignmentsURL.setParameter("tabs2", tabs2);
					updateRoleAssignmentsURL.setParameter("redirect", Http.decodeURL(Http.getParameter(redirect, renderResponse.getNamespace() + "redirect", false)));
					updateRoleAssignmentsURL.setParameter("groupId", String.valueOf(group.getGroupId()));
					%>

					<div class="separator"><!-- --></div>

					<input type="button" value="<liferay-ui:message key="update-associations" />" onClick="<portlet:namespace />updateUserGroupRole('<%= updateRoleAssignmentsURL.toString() %>&<portlet:namespace />cur=<%= cur %>');" />

					<br /><br />

					<%
					List resultRows = searchContainer.getResultRows();

					for (int i = 0; i < results.size(); i++) {
						Role role = (Role)results.get(i);

						ResultRow row = new ResultRow(role, role.getRoleId(), i);

						// Name

						row.addText(role.getName());

						// Add result row

						resultRows.add(row);
					}
					%>

					<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

					<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
				</c:if>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:when test='<%= tabs1.equals("organizations") || tabs1.equals("locations") %>'>
		<input name="<portlet:namespace />addOrganizationIds" type="hidden" value="" />
		<input name="<portlet:namespace />removeOrganizationIds" type="hidden" value="" />

		<liferay-ui:tabs
			names="current,available"
			param="tabs2"
			url="<%= portletURL.toString() %>"
		/>

		<%
		OrganizationSearch searchContainer = new OrganizationSearch(renderRequest, portletURL);

		searchContainer.setRowChecker(new OrganizationGroupChecker(renderResponse, group));
		%>

		<liferay-ui:search-form
			page="/html/portlet/enterprise_admin/organization_search.jsp"
			searchContainer="<%= searchContainer %>"
		/>

		<%
		boolean organizationsTab = tabs1.equals("organizations");

		OrganizationSearchTerms searchTerms = (OrganizationSearchTerms)searchContainer.getSearchTerms();

		long parentOrganizationId = OrganizationImpl.ANY_PARENT_ORGANIZATION_ID;

		LinkedHashMap organizationParams = new LinkedHashMap();

		if (tabs2.equals("current")) {
			organizationParams.put("organizationsGroups", new Long(group.getGroupId()));
		}

		int total = OrganizationLocalServiceUtil.searchCount(company.getCompanyId(), parentOrganizationId, searchTerms.getName(), !organizationsTab, searchTerms.getStreet(), searchTerms.getCity(), searchTerms.getZip(), searchTerms.getRegionIdObj(), searchTerms.getCountryIdObj(), organizationParams, searchTerms.isAndOperator());

		searchContainer.setTotal(total);

		List results = OrganizationLocalServiceUtil.search(company.getCompanyId(), parentOrganizationId, searchTerms.getName(), !organizationsTab, searchTerms.getStreet(), searchTerms.getCity(), searchTerms.getZip(), searchTerms.getRegionIdObj(), searchTerms.getCountryIdObj(), organizationParams, searchTerms.isAndOperator(), searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);
		%>

		<div class="separator"><!-- --></div>

		<input type="button" value="<liferay-ui:message key="update-associations" />" onClick="<portlet:namespace />updateGroupOrganizations('<%= portletURL.toString() %>&<portlet:namespace />cur=<%= cur %>');" />

		<br /><br />

		<%
		List headerNames = new ArrayList();

		headerNames.add("name");
		headerNames.add("parent-organization");
		headerNames.add("city");

		searchContainer.setHeaderNames(headerNames);

		if (!organizationsTab) {
			searchContainer.setEmptyResultsMessage(OrganizationSearch.EMPTY_RESULTS_MESSAGE_2);
		}

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			Organization organization = (Organization)results.get(i);

			ResultRow row = new ResultRow(organization, organization.getOrganizationId(), i);

			// Name

			row.addText(organization.getName());

			// Parent organization

			String parentOrganizationName = StringPool.BLANK;

			if (organization.getParentOrganizationId() > 0) {
				try {
					Organization parentOrganization = OrganizationLocalServiceUtil.getOrganization(organization.getParentOrganizationId());

					parentOrganizationName = parentOrganization.getName();
				}
				catch (Exception e) {
				}
			}

			row.addText(parentOrganizationName);

			// Address

			Address address = organization.getAddress();

			row.addText(address.getCity());

			// Add result row

			resultRows.add(row);
		}
		%>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

		<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
	</c:when>
	<c:when test='<%= tabs1.equals("user-groups") %>'>
		<input name="<portlet:namespace />addUserGroupIds" type="hidden" value="" />
		<input name="<portlet:namespace />removeUserGroupIds" type="hidden" value="" />

		<liferay-ui:tabs
			names="current,available"
			param="tabs2"
			url="<%= portletURL.toString() %>"
		/>

		<%
		UserGroupSearch searchContainer = new UserGroupSearch(renderRequest, portletURL);

		searchContainer.setRowChecker(new UserGroupGroupChecker(renderResponse, group));
		%>

		<liferay-ui:search-form
			page="/html/portlet/enterprise_admin/user_group_search.jsp"
			searchContainer="<%= searchContainer %>"
		/>

		<%
		UserGroupSearchTerms searchTerms = (UserGroupSearchTerms)searchContainer.getSearchTerms();

		LinkedHashMap userGroupParams = new LinkedHashMap();

		if (tabs2.equals("current")) {
			userGroupParams.put("userGroupsGroups", new Long(group.getGroupId()));
		}

		int total = UserGroupLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), userGroupParams);

		searchContainer.setTotal(total);

		List results = UserGroupLocalServiceUtil.search(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), userGroupParams, searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);
		%>

		<div class="separator"><!-- --></div>

		<input type="button" value="<liferay-ui:message key="update-associations" />" onClick="<portlet:namespace />updateGroupUserGroups('<%= portletURL.toString() %>&<portlet:namespace />cur=<%= cur %>');" />

		<br /><br />

		<%
		List headerNames = new ArrayList();

		headerNames.add("name");

		searchContainer.setHeaderNames(headerNames);

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			UserGroup userGroup = (UserGroup)results.get(i);

			ResultRow row = new ResultRow(userGroup, userGroup.getUserGroupId(), i);

			// Name

			row.addText(userGroup.getName());

			// Add result row

			resultRows.add(row);
		}
		%>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

		<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
	</c:when>
</c:choose>

</form>