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

<c:choose>
	<c:when test="<%= tabs2.equals("organizations") %>">
		<div class="portlet-msg-info">
			<%= LanguageUtil.format(pageContext, "by-choosing-to-reassign-the-resource-all-actions-will-be-removed-from-the-role-and-the-default-actions-will-be-enabled-on-the-system-role-x", RoleConstants.ORGANIZATION_MEMBER) %>
		</div>

		<%
		List<ResourcePermission> resourcePermissions = new UniqueList<ResourcePermission>();

		List<Role> roles = RoleLocalServiceUtil.getRoles(RoleConstants.TYPE_COMMUNITY, "lfr-permission-algorithm-5");

		for (Iterator itr = roles.iterator(); itr.hasNext(); ) {
			Role role = (Role)itr.next();

			Group group = GroupLocalServiceUtil.getRoleGroups(role.getRoleId()).get(0);

			if (!group.isOrganization()) {
				continue;
			}

			List<ResourcePermission> resourcePermissions2 = ResourcePermissionLocalServiceUtil.getRoleResourcePermissions(role.getRoleId());

			for (ResourcePermission resourcePermission : resourcePermissions2) {
				try {
					if (resourcePermission.getScope() == ResourceConstants.SCOPE_INDIVIDUAL &&
						PortalUtil.getModel(resourcePermission) != null) {

						Role systemRole = null;

						// Find potential matching role

						long companyId = role.getCompanyId();

						List<Group> groups = GroupLocalServiceUtil.getRoleGroups(role.getRoleId());

						if (groups.size() == 1) {
							systemRole = RoleLocalServiceUtil.getRole(
								companyId, RoleConstants.ORGANIZATION_MEMBER);
						}

						if (systemRole != null) {
							resourcePermissions.add(resourcePermission);
						}
					}
				}
				catch (Exception e) {
				}
			}
		}
		%>

		<liferay-ui:search-container
			searchContainer="<%= new SearchContainer(renderRequest, portletURL, null, "no-generated-roles-to-reassign") %>"
		>
			<liferay-ui:search-container-results
				total="<%= resourcePermissions.size() %>"
				results="<%= ListUtil.subList(resourcePermissions, searchContainer.getStart(), searchContainer.getEnd()) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.model.ResourcePermission"
				escapedModel="<%= true %>"
				keyProperty="resourcePermissionId"
				modelVar="resourcePermission"
			>
				<%
				List<String> actionLabels = ResourceActionsUtil.getActionsNames(pageContext, resourcePermission.getName(), resourcePermission.getActionIds());

				Role role = RoleLocalServiceUtil.getRole(resourcePermission.getRoleId());
				Role systemRole = RoleLocalServiceUtil.getRole(role.getCompanyId(), RoleConstants.OWNER);
				List<String> systemActionLabels = ResourceActionsUtil.getActionsNames(pageContext, ResourceActionsUtil.getModelResourceCommunityDefaultActions(resourcePermission.getName()));

				Group group = GroupLocalServiceUtil.getRoleGroups(role.getRoleId()).get(0);
				Organization organization = OrganizationLocalServiceUtil.getOrganization(group.getClassPK());
				String entryName = organization.getName();

				ListUtil.sort(actionLabels);
				ListUtil.sort(systemActionLabels);
				%>

				<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editEntryURL">
					<portlet:param name="struts_action" value="/admin_server/edit_organization" />
					<portlet:param name="tabs1Names" value="organizations" />
					<portlet:param name="organizationId" value="<%= String.valueOf(organization.getOrganizationId()) %>" />
				</portlet:renderURL>

				<liferay-ui:search-container-column-text
					href="<%= editEntryURL %>"
					name="name"
					value="<%= entryName %>"
				/>

				<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editRoleURL">
					<portlet:param name="struts_action" value="/admin_server/edit_role_permissions" />
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.VIEW %>" />
					<portlet:param name="roleId" value="<%= String.valueOf(role.getRoleId()) %>" />
				</portlet:renderURL>

				<liferay-ui:search-container-column-text
					href="<%= editRoleURL %>"
					name="role"
					value="<%= role.getName() %>"
				/>

				<liferay-ui:search-container-column-jsp
					name="resource"
					path="/html/portlet/admin/view_model.jsp"
				/>

				<liferay-ui:search-container-column-text
					name="actions"
					value="<%= StringUtil.merge(actionLabels, "<br />") %>"
				/>

				<liferay-ui:search-container-column-text
					name="potential-actions"
					value="<%= StringUtil.merge(systemActionLabels, "<br />") %>"
				/>

				<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="reassignURL">
					<portlet:param name="struts_action" value="/admin_server/edit_permissions" />
					<portlet:param name="<%= Constants.CMD %>" value="reassign" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="toRoleId" value="<%= String.valueOf(systemRole.getRoleId()) %>" />
					<portlet:param name="resourcePermissionId" value="<%= String.valueOf(resourcePermission.getResourcePermissionId()) %>" />
				</portlet:actionURL>

				<liferay-ui:search-container-column-button
					align="right"
					href="<%= renderResponse.getNamespace() + "invoke('" + reassignURL + "')" %>"
					name="<%= LanguageUtil.get(pageContext, "reassign") %>"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>
	</c:when>
	<c:when test="<%= tabs2.equals("communities") %>">
		<div class="portlet-msg-info">
			<%= LanguageUtil.format(pageContext, "by-choosing-to-reassign-the-resource-all-actions-will-be-removed-from-the-role-and-the-default-actions-will-be-enabled-on-the-system-role-x", RoleConstants.COMMUNITY_MEMBER) %>
		</div>

		<%
		List<ResourcePermission> resourcePermissions = new UniqueList<ResourcePermission>();

		List<Role> roles = RoleLocalServiceUtil.getRoles(RoleConstants.TYPE_COMMUNITY, "lfr-permission-algorithm-5");

		for (Iterator itr = roles.iterator(); itr.hasNext(); ) {
			Role role = (Role)itr.next();

			Group group = GroupLocalServiceUtil.getRoleGroups(role.getRoleId()).get(0);

			if (!group.isCommunity()) {
				continue;
			}

			List<ResourcePermission> resourcePermissions2 = ResourcePermissionLocalServiceUtil.getRoleResourcePermissions(role.getRoleId());

			for (ResourcePermission resourcePermission : resourcePermissions2) {
				try {
					if (resourcePermission.getScope() == ResourceConstants.SCOPE_INDIVIDUAL &&
						PortalUtil.getModel(resourcePermission) != null) {

						Role systemRole = null;

						// Find potential matching role

						long companyId = role.getCompanyId();

						List<Group> groups = GroupLocalServiceUtil.getRoleGroups(role.getRoleId());

						if (groups.size() == 1) {
							systemRole = RoleLocalServiceUtil.getRole(
								companyId, RoleConstants.COMMUNITY_MEMBER);
						}

						if (systemRole != null) {
							resourcePermissions.add(resourcePermission);
						}
					}
				}
				catch (Exception e) {
				}
			}
		}
		%>

		<liferay-ui:search-container
			searchContainer="<%= new SearchContainer(renderRequest, portletURL, null, "no-generated-roles-to-reassign") %>"
		>
			<liferay-ui:search-container-results
				total="<%= resourcePermissions.size() %>"
				results="<%= ListUtil.subList(resourcePermissions, searchContainer.getStart(), searchContainer.getEnd()) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.model.ResourcePermission"
				escapedModel="<%= true %>"
				keyProperty="resourcePermissionId"
				modelVar="resourcePermission"
			>

				<%
				List<String> actionLabels = ResourceActionsUtil.getActionsNames(pageContext, resourcePermission.getName(), resourcePermission.getActionIds());

				Role role = RoleLocalServiceUtil.getRole(resourcePermission.getRoleId());
				Role systemRole = RoleLocalServiceUtil.getRole(role.getCompanyId(), RoleConstants.OWNER);
				List<String> systemActionLabels = ResourceActionsUtil.getActionsNames(pageContext, ResourceActionsUtil.getModelResourceCommunityDefaultActions(resourcePermission.getName()));

				Group group = GroupLocalServiceUtil.getRoleGroups(role.getRoleId()).get(0);
				String entryName = group.getName();

				ListUtil.sort(actionLabels);
				ListUtil.sort(systemActionLabels);
				%>

				<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editEntryURL">
					<portlet:param name="struts_action" value="/admin_server/edit_community" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
				</portlet:renderURL>

				<liferay-ui:search-container-column-text
					href="<%= editEntryURL %>"
					name="name"
					value="<%= entryName %>"
				/>

				<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editRoleURL">
					<portlet:param name="struts_action" value="/admin_server/edit_role_permissions" />
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.VIEW %>" />
					<portlet:param name="roleId" value="<%= String.valueOf(role.getRoleId()) %>" />
				</portlet:renderURL>

				<liferay-ui:search-container-column-text
					href="<%= editRoleURL %>"
					name="role"
					value="<%= role.getName() %>"
				/>

				<liferay-ui:search-container-column-jsp
					name="resource"
					path="/html/portlet/admin/view_model.jsp"
				/>

				<liferay-ui:search-container-column-text
					name="actions"
					value="<%= StringUtil.merge(actionLabels, "<br />") %>"
				/>

				<liferay-ui:search-container-column-text
					name="potential-actions"
					value="<%= StringUtil.merge(systemActionLabels, "<br />") %>"
				/>

				<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="reassignURL">
					<portlet:param name="struts_action" value="/admin_server/edit_permissions" />
					<portlet:param name="<%= Constants.CMD %>" value="reassign" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="toRoleId" value="<%= String.valueOf(systemRole.getRoleId()) %>" />
					<portlet:param name="resourcePermissionId" value="<%= String.valueOf(resourcePermission.getResourcePermissionId()) %>" />
				</portlet:actionURL>

				<liferay-ui:search-container-column-button
					align="right"
					href="<%= renderResponse.getNamespace() + "invoke('" + reassignURL + "')" %>"
					name="<%= LanguageUtil.get(pageContext, "reassign") %>"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>
	</c:when>
	<c:otherwise>
		<div class="portlet-msg-info">
			<%= LanguageUtil.format(pageContext, "by-choosing-to-reassign-the-resource-all-actions-will-be-removed-from-the-role-and-the-default-actions-will-be-enabled-on-the-system-role-x", RoleConstants.OWNER) %>
		</div>

		<%
		List<ResourcePermission> resourcePermissions = new UniqueList<ResourcePermission>();

		List<Role> roles = RoleLocalServiceUtil.getRoles(RoleConstants.TYPE_REGULAR, "lfr-permission-algorithm-5");

		for (Iterator itr = roles.iterator(); itr.hasNext(); ) {
			Role role = (Role)itr.next();

			List<ResourcePermission> resourcePermissions2 = ResourcePermissionLocalServiceUtil.getRoleResourcePermissions(role.getRoleId());

			for (ResourcePermission resourcePermission : resourcePermissions2) {
				try {
					if (resourcePermission.getScope() == ResourceConstants.SCOPE_INDIVIDUAL &&
						PortalUtil.getModel(resourcePermission) != null) {

						Role systemRole = null;

						// Find potential matching role

						long companyId = role.getCompanyId();

						List<User> users =
							UserLocalServiceUtil.getRoleUsers(role.getRoleId());

						if (users.size() == 1) {
							try {
								BaseModel model = PortalUtil.getModel(resourcePermission);

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

						if (systemRole != null) {
							resourcePermissions.add(resourcePermission);
						}
					}
				}
				catch (Exception e) {
				}
			}
		}
		%>

		<liferay-ui:search-container
			emptyResultsMessage="no-generated-roles-to-reassign"
			searchContainer="<%= new SearchContainer(renderRequest, portletURL, null, "no-generated-roles-to-reassign") %>"
		>
			<liferay-ui:search-container-results
				total="<%= resourcePermissions.size() %>"
				results="<%= ListUtil.subList(resourcePermissions, searchContainer.getStart(), searchContainer.getEnd()) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.model.ResourcePermission"
				escapedModel="<%= true %>"
				keyProperty="resourcePermissionId"
				modelVar="resourcePermission"
			>

				<%
				List<String> actionLabels = ResourceActionsUtil.getActionsNames(pageContext, resourcePermission.getName(), resourcePermission.getActionIds());

				Role role = RoleLocalServiceUtil.getRole(resourcePermission.getRoleId());
				Role systemRole = RoleLocalServiceUtil.getRole(role.getCompanyId(), RoleConstants.OWNER);
				List<String> systemActionLabels = ResourceActionsUtil.getActionsNames(pageContext, ResourceActionsUtil.getModelResourceActions(resourcePermission.getName()));

				User user2 = UserLocalServiceUtil.getRoleUsers(role.getRoleId()).get(0);
				String entryName = user2.getScreenName();

				ListUtil.sort(actionLabels);
				ListUtil.sort(systemActionLabels);
				%>

				<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editEntryURL">
					<portlet:param name="struts_action" value="/admin_server/edit_user" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="tabs1Names" value="users" />
					<portlet:param name="p_u_i_d" value="<%= String.valueOf(user2.getUserId()) %>" />
				</portlet:renderURL>

				<liferay-ui:search-container-column-text
					href="<%= editEntryURL %>"
					name="name"
					value="<%= entryName %>"
				/>

				<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editRoleURL">
					<portlet:param name="struts_action" value="/admin_server/edit_role_permissions" />
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.VIEW %>" />
					<portlet:param name="roleId" value="<%= String.valueOf(role.getRoleId()) %>" />
				</portlet:renderURL>

				<liferay-ui:search-container-column-text
					href="<%= editRoleURL %>"
					name="role"
					value="<%= role.getName() %>"
				/>

				<liferay-ui:search-container-column-jsp
					name="resource"
					path="/html/portlet/admin/view_model.jsp"
				/>

				<liferay-ui:search-container-column-text
					name="actions"
					value="<%= StringUtil.merge(actionLabels, "<br />") %>"
				/>

				<liferay-ui:search-container-column-text
					name="potential-actions"
					value="<%= StringUtil.merge(systemActionLabels, "<br />") %>"
				/>

				<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="reassignURL">
					<portlet:param name="struts_action" value="/admin_server/edit_permissions" />
					<portlet:param name="<%= Constants.CMD %>" value="reassign" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="toRoleId" value="<%= String.valueOf(systemRole.getRoleId()) %>" />
					<portlet:param name="resourcePermissionId" value="<%= String.valueOf(resourcePermission.getResourcePermissionId()) %>" />
				</portlet:actionURL>

				<liferay-ui:search-container-column-button
					align="right"
					href="<%= renderResponse.getNamespace() + "invoke('" + reassignURL + "')" %>"
					name="<%= LanguageUtil.get(pageContext, "reassign") %>"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>
	</c:otherwise>
</c:choose>

<style type="text/css">
.model-details {
	background: none;
	border: 0px;
	color: none;
}
</style>