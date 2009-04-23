<%
/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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
String tabs1 = ParamUtil.getString(request, "tabs1", "merge-redundant-roles");
String tabs2 = ParamUtil.getString(request, "tabs2", "organizations");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/admin_server/edit_permissions_algorithm_5");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("tabs2", tabs2);

MultiValueMap mvp = new MultiValueMap();
%>

<script type="text/javascript">
	function <portlet:namespace />invoke(link) {
		submitForm(document.<portlet:namespace />fm, link);
	}
</script>

<form method="post" name="<portlet:namespace />fm">

<liferay-ui:tabs
	names="merge-redundant-roles,reassign-to-system-role"
	param="tabs1"
	url="<%= portletURL.toString() %>"
/>

<c:choose>
	<c:when test='<%= tabs1.equals("merge-redundant-roles") %>'>
		<liferay-ui:tabs
			names="organizations,communities,users"
			param="tabs2"
			url="<%= portletURL.toString() %>"
		/>

		<%
		List<Role> roles = RoleLocalServiceUtil.getRoles(tabs2.equals("users") ? RoleConstants.TYPE_REGULAR : RoleConstants.TYPE_COMMUNITY, "lfr-permission-algorithm-5");

		for (Iterator itr = roles.iterator(); itr.hasNext(); ) {
			Role role = (Role)itr.next();

			List entries = null;

			if (tabs2.equals("users")) {
				entries = UserLocalServiceUtil.getRoleUsers(role.getRoleId());
			}
			else {
				entries = GroupLocalServiceUtil.getRoleGroups(role.getRoleId());
			}

			if (entries.size() > 1) {
				continue;
			}

			Object entry = entries.get(0);

			if (entry instanceof Group) {
				Group group = (Group)entry;

				if (tabs2.equals("organizations") && !group.isOrganization()) {
					continue;
				}
				else if (tabs2.equals("communities") && !group.isCommunity()) {
					continue;
				}
			}

			mvp.put(entry, role);
		}

		for (Iterator itr = mvp.keySet().iterator(); itr.hasNext(); ) {
			Object key = itr.next();

			if (mvp.size(key) == 1) {
				itr.remove();
			}
		}

		List entries = new ArrayList(mvp.keySet());

		List<String> headerNames = new ArrayList<String>();

		headerNames.add("name");
		headerNames.add("role");
		headerNames.add(StringPool.BLANK);

		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, "no-generated-roles-to-merge");

		searchContainer.setTotal(entries.size());

		List results = ListUtil.subList(entries, searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			Object entry = results.get(i);
			Collection<Role> roles2 = mvp.getCollection(entry);

			List<Long> roleIds = new ArrayList<Long>();

			for (Role role : roles2) {
				roleIds.add(role.getRoleId());
			}

			ResultRow row = null;

			PortletURL editEntryURL = renderResponse.createRenderURL();

			editEntryURL.setWindowState(WindowState.MAXIMIZED);
			editEntryURL.setParameter("redirect", currentURL);

			if (tabs2.equals("users")) {
				User user2 = (User)entry;

				row = new ResultRow(new Object[] {user2, roleIds}, user2.getUserId(), i);

				editEntryURL.setParameter("struts_action", "/admin_server/edit_user");
				editEntryURL.setParameter("tabs1Names", "users");
				editEntryURL.setParameter("p_u_i_d", String.valueOf(user2.getUserId()));

				row.addText(user2.getScreenName(), editEntryURL);
			}
			else {
				Group group = (Group)entry;

				row = new ResultRow(new Object[] {group, roleIds}, group.getGroupId(), i);

				if (group.isCommunity()) {
					editEntryURL.setParameter("struts_action", "/admin_server/edit_community");
					editEntryURL.setParameter("groupId", String.valueOf(group.getGroupId()));

					row.addText(group.getName(), editEntryURL);
				}
				else if (group.isOrganization()) {
					Organization organization = OrganizationLocalServiceUtil.getOrganization(group.getClassPK());

					editEntryURL.setParameter("struts_action", "/admin_server/edit_organization");
					editEntryURL.setParameter("tabs1Names", "organizations");
					editEntryURL.setParameter("organizationId", String.valueOf(organization.getOrganizationId()));

					row.addText(organization.getName(), editEntryURL);
				}
			}

			StringBuffer sb = new StringBuffer();

			PortletURL editRoleURL = renderResponse.createRenderURL();

			editRoleURL.setWindowState(WindowState.MAXIMIZED);

			editRoleURL.setParameter("struts_action", "/admin_server/edit_role_permissions");
			editRoleURL.setParameter(Constants.CMD, Constants.VIEW);
			editRoleURL.setParameter("redirect", currentURL);

			for (Role role : roles2) {
				editRoleURL.setParameter("roleId", String.valueOf(role.getRoleId()));

				sb.append("<a href=\"" + editRoleURL.toString() + "\">" + role.getName() + "</a><br />");
			}

			row.addText(sb.toString());

			// Action

			PortletURL mergeURL = renderResponse.createActionURL();

			mergeURL.setWindowState(WindowState.MAXIMIZED);

			mergeURL.setParameter("struts_action", "/admin_server/edit_permissions_algorithm_5");
			mergeURL.setParameter(Constants.CMD, "merge");
			mergeURL.setParameter("redirect", currentURL);
			mergeURL.setParameter("roleIds", StringUtil.merge(roleIds));

			row.addButton("right", SearchEntry.DEFAULT_VALIGN, LanguageUtil.get(pageContext, "merge"), renderResponse.getNamespace() + "invoke('" + mergeURL + "');");

			resultRows.add(row);
		}
		%>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
	</c:when>
	<c:otherwise>
		<liferay-ui:tabs
			names="organizations,communities,users"
			param="tabs2"
			url="<%= portletURL.toString() %>"
		/>

		<%
		String possibleRole = RoleConstants.OWNER;

		if (tabs2.equals("organizations")) {
			possibleRole = RoleConstants.ORGANIZATION_MEMBER;
		}
		else if (tabs2.equals("communities")) {
			possibleRole = RoleConstants.COMMUNITY_MEMBER;
		}
		%>

		<div class="portlet-msg-info">
			<%= LanguageUtil.format(pageContext, "by-choosing-to-reassign-the-resource-all-actions-will-be-removed-from-the-role-and-the-default-actions-will-be-enabled-on-the-system-role-x", possibleRole) %>
		</div>

		<%
		List<Role> roles = RoleLocalServiceUtil.getRoles(tabs2.equals("users") ? RoleConstants.TYPE_REGULAR : RoleConstants.TYPE_COMMUNITY, "lfr-permission-algorithm-5");

		for (Iterator itr = roles.iterator(); itr.hasNext(); ) {
			Role role = (Role)itr.next();

			if (!tabs2.equals("users")) {
				Group group = GroupLocalServiceUtil.getRoleGroups(role.getRoleId()).get(0);

				if (tabs2.equals("organizations") && !group.isOrganization()) {
					continue;
				}
				else if (tabs2.equals("communities") && !group.isCommunity()) {
					continue;
				}
			}

			List<Permission> permissions = PermissionLocalServiceUtil.getRolePermissions(role.getRoleId());

			for (Permission permission : permissions) {
				Resource resource = ResourceLocalServiceUtil.getResource(permission.getResourceId());

				ResourceCode resourceCode = ResourceCodeLocalServiceUtil.getResourceCode(resource.getCodeId());

				try {
					if (resourceCode.getScope() == ResourceConstants.SCOPE_INDIVIDUAL &&
						ResourceLocalServiceUtil.getModel(resource) != null) {

						Role systemRole = null;

						// Find potential matching role

						long companyId = role.getCompanyId();

						if (role.getType() == RoleConstants.TYPE_COMMUNITY) {
							List<Group> groups = GroupLocalServiceUtil.getRoleGroups(role.getRoleId());

							if (groups.size() == 1) {
								Group group = groups.get(0);

								if (group.isOrganization()) {
									systemRole = RoleLocalServiceUtil.getRole(
										companyId, RoleConstants.ORGANIZATION_MEMBER);
								}
								else if (group.isCommunity()) {
									systemRole = RoleLocalServiceUtil.getRole(
										companyId, RoleConstants.COMMUNITY_MEMBER);
								}
							}
						}
						else if (role.getType() == RoleConstants.TYPE_REGULAR) {
							List<User> users =
								UserLocalServiceUtil.getRoleUsers(role.getRoleId());

							if (users.size() == 1) {
								try {
									Object model = ResourceLocalServiceUtil.getModel(resource);

									if (model != null) {
										java.lang.reflect.Method method = model.getClass().getMethod(
											"getUserId", (Class[])null);

										Long userId = (Long)method.invoke(model, null);

										if (users.get(0).getUserId() == userId.longValue()) {
											systemRole = RoleLocalServiceUtil.getRole(
												companyId, RoleConstants.OWNER);
										}
									}
								}
								catch (Exception e) {
								}
							}
						}

						if (systemRole != null) {
							mvp.put(role.getRoleId() + "," + resource.getResourceId() + "," + systemRole.getRoleId(), permission);
						}
					}
				}
				catch (Exception e) {
				}
			}
		}

		List entries = new ArrayList(mvp.keySet());

		List<String> headerNames = new ArrayList<String>();

		headerNames.add("name");
		headerNames.add("role");
		headerNames.add("resource");
		headerNames.add("actions");
		headerNames.add("potential-actions");
		headerNames.add(StringPool.BLANK);

		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, "no-generated-roles-to-reassign");

		searchContainer.setTotal(entries.size());

		List results = ListUtil.subList(entries, searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);

		List resultRows = searchContainer.getResultRows();

		PortletURL editRoleURL = renderResponse.createRenderURL();

		editRoleURL.setWindowState(WindowState.MAXIMIZED);

		editRoleURL.setParameter("struts_action", "/admin_server/edit_role_permissions");
		editRoleURL.setParameter(Constants.CMD, Constants.VIEW);
		editRoleURL.setParameter("redirect", currentURL);

		PortletURL editEntryURL = renderResponse.createRenderURL();

		editEntryURL.setWindowState(WindowState.MAXIMIZED);
		editEntryURL.setParameter("redirect", currentURL);

		for (int i = 0; i < results.size(); i++) {
			String key = (String)results.get(i);
			List<Permission> permissions = new ArrayList<Permission>(mvp.getCollection(key));
			List<Long> permissionIds = new ArrayList<Long>();
			List<String> actionLabels = new ArrayList<String>();

			for (Permission permission : permissions) {
				permissionIds.add(permission.getPermissionId());
				actionLabels.add(ResourceActionsUtil.getAction(pageContext, permission.getActionId()));
			}

			long[] keyParts = StringUtil.split(key, 0L);
			long roleId = keyParts[0];
			long resourceId = keyParts[1];
			long systemRoleId = keyParts[2];

			Role role = RoleLocalServiceUtil.getRole(roleId);
			Resource resource = ResourceLocalServiceUtil.getResource(resourceId);
			ResourceCode resourceCode = ResourceCodeLocalServiceUtil.getResourceCode(resource.getCodeId());
			Object model = ResourceLocalServiceUtil.getModel(resource);
			Role systemRole = RoleLocalServiceUtil.getRole(systemRoleId);
			List<String> systemActionLabels = null;

			String entryName = "";

			if (role.getType() == RoleConstants.TYPE_REGULAR) {
				systemActionLabels = ResourceActionsUtil.getActionsNames(pageContext, ResourceActionsUtil.getModelResourceActions(resourceCode.getName()));

				User user2 = UserLocalServiceUtil.getRoleUsers(roleId).get(0);
				entryName = user2.getScreenName();

				editEntryURL.setParameter("struts_action", "/admin_server/edit_user");
				editEntryURL.setParameter("tabs1Names", "users");
				editEntryURL.setParameter("p_u_i_d", String.valueOf(user2.getUserId()));
			}
			else {
				systemActionLabels = ResourceActionsUtil.getActionsNames(pageContext, ResourceActionsUtil.getModelResourceCommunityDefaultActions(resourceCode.getName()));

				Group group = GroupLocalServiceUtil.getRoleGroups(roleId).get(0);

				if (group.isCommunity()) {
					entryName = group.getName();

					editEntryURL.setParameter("struts_action", "/admin_server/edit_community");
					editEntryURL.setParameter("groupId", String.valueOf(group.getGroupId()));
				}
				else  {
					Organization organization = OrganizationLocalServiceUtil.getOrganization(group.getClassPK());

					entryName = organization.getName();

					editEntryURL.setParameter("struts_action", "/admin_server/edit_organization");
					editEntryURL.setParameter("tabs1Names", "organizations");
					editEntryURL.setParameter("organizationId", String.valueOf(organization.getOrganizationId()));
				}
			}

			ListUtil.sort(actionLabels);
			ListUtil.sort(systemActionLabels);

			ResultRow row = new ResultRow(new Object[] {role, resource}, roleId + "," + resourceId, i);

			editRoleURL.setParameter("roleId", String.valueOf(roleId));

			row.addText(entryName, editEntryURL);
			row.addText(role.getName(), editRoleURL);
			row.addJSP("/html/portlet/admin/view_model.jsp");
			row.addText(StringUtil.merge(actionLabels, "<br />"));
			row.addText(StringUtil.merge(systemActionLabels, "<br />"));

			// Action

			PortletURL reassignURL = renderResponse.createActionURL();

			reassignURL.setWindowState(WindowState.MAXIMIZED);

			reassignURL.setParameter("struts_action", "/admin_server/edit_permissions_algorithm_5");
			reassignURL.setParameter(Constants.CMD, "reassign");
			reassignURL.setParameter("redirect", currentURL);
			reassignURL.setParameter("fromRoleId", String.valueOf(role.getRoleId()));
			reassignURL.setParameter("toRoleId", String.valueOf(systemRole.getRoleId()));
			reassignURL.setParameter("resourceId", String.valueOf(resource.getResourceId()));
			reassignURL.setParameter("permissionIds", StringUtil.merge(permissionIds));

			row.addButton(LanguageUtil.get(pageContext, "reassign"), renderResponse.getNamespace() + "invoke('" + reassignURL + "');");

			resultRows.add(row);
		}
		%>

		<style type="text/css">
		.model-details {
			background: none;
			border: 0px;
			color: none;
		}
		</style>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
	</c:otherwise>
</c:choose>

</form>