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
List<UserGroupRole> organizationRoles = (List<UserGroupRole>)request.getAttribute("user.organizationRoles");
List<UserGroupRole> communityRoles = (List<UserGroupRole>)request.getAttribute("user.communityRoles");
List<Organization> organizations = (List<Organization>)request.getAttribute("user.organizations");
List<Group> groups = (List<Group>)request.getAttribute("user.groups");

List <UserGroupRole> userGroupRoles = new ArrayList<UserGroupRole>();

userGroupRoles.addAll(organizationRoles);
userGroupRoles.addAll(communityRoles);
%>

<liferay-util:buffer var="removeRoleIcon">
	<liferay-ui:icon image="unlink" message="remove" label="<%= true %>" />
</liferay-util:buffer>

<script type="text/javascript">
	var <portlet:namespace />groupRolesRoleIds = ['<%= ListUtil.toString(userGroupRoles, "roleId", "', '") %>'];
	var <portlet:namespace />groupRolesGroupIds = ['<%= ListUtil.toString(userGroupRoles, "groupId", "', '") %>'];

	function <portlet:namespace />openRegularRoleSelector() {
		<portlet:namespace />openRoleSelector('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/select_role" /></portlet:renderURL>');
	}

	function <portlet:namespace />openOrganizationRoleSelector() {
		<portlet:namespace />openRoleSelector('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/select_organization_role" /><portlet:param name="step" value="1" /><portlet:param name="userId" value="<%= String.valueOf(selUser.getUserId()) %>" /></portlet:renderURL>');
	}

	function <portlet:namespace />openCommunityRoleSelector() {
		<portlet:namespace />openRoleSelector('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/select_community_role" /><portlet:param name="step" value="1" /><portlet:param name="userId" value="<%= String.valueOf(selUser.getUserId()) %>" /></portlet:renderURL>');
	}

	function <portlet:namespace />openRoleSelector(url) {
		var roleWindow = window.open(url, 'role', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680');

		roleWindow.focus();
	}

	function <portlet:namespace />selectRole(roleId, name, searchContainer, groupName, groupId) {
		var searchContainerName = '<portlet:namespace />' + searchContainer + 'SearchContainer';
		var searchContainer = Liferay.SearchContainer.get(searchContainerName);

		var rowColumns = [];

		rowColumns.push(name);

		if (groupName) {
			rowColumns.push(groupName);
		}

		if (groupId) {
			rowColumns.push(<portlet:namespace />createURL('javascript: ;', '<%= UnicodeFormatter.toString(removeRoleIcon) %>', 'Liferay.SearchContainer.get(\'+searchContainerName+\').deleteRow(this, ' + roleId + '); <portlet:namespace />deleteGroupRole(' + roleId + ', ' + groupId + ')'));

			<portlet:namespace />groupRolesRoleIds.push(roleId);
			<portlet:namespace />groupRolesGroupIds.push(groupId);

			document.<portlet:namespace />fm.<portlet:namespace />groupRolesRoleIds.value = <portlet:namespace />groupRolesRoleIds.join(',');
			document.<portlet:namespace />fm.<portlet:namespace />groupRolesGroupIds.value = <portlet:namespace />groupRolesGroupIds.join(',');
		}
		else {
			rowColumns.push(<portlet:namespace />createURL('javascript: ;', '<%= UnicodeFormatter.toString(removeRoleIcon) %>', 'Liferay.SearchContainer.get(\'+searchContainerName+\').deleteRow(this, ' + roleId + ')'));
		}
		console.log(name);
		searchContainer.addRow(rowColumns, roleId);
		searchContainer.updateDataStore();

		jQuery('.selected .modify-link').trigger('change');
	}

	function <portlet:namespace />deleteGroupRole(roleId, groupId) {
		for (var i = 0; i < <portlet:namespace />groupRolesRoleIds.length; i++) {
			if ((<portlet:namespace />groupRolesRoleIds[i] == roleId) && (<portlet:namespace />groupRolesGroupIds[i] == groupId)) {
				 <portlet:namespace />groupRolesRoleIds.splice(i, 1);
				 <portlet:namespace />groupRolesGroupIds.splice(i, 1);

				break;
			}
		}

		document.<portlet:namespace />fm.<portlet:namespace />groupRolesRoleIds.value = <portlet:namespace />groupRolesRoleIds.join(',');
		document.<portlet:namespace />fm.<portlet:namespace />groupRolesGroupIds.value = <portlet:namespace />groupRolesGroupIds.join(',');
	}
</script>

<input name="<portlet:namespace />groupRolesRoleIds" type="hidden" value="<%= ListUtil.toString(userGroupRoles, "roleId") %>" />
<input name="<portlet:namespace />groupRolesGroupIds" type="hidden" value="<%= ListUtil.toString(userGroupRoles, "groupId") %>" />

<h3><liferay-ui:message key="regular-roles" /></h3>

<liferay-ui:search-container
	id='<%= renderResponse.getNamespace() + "regularRolesSearchContainer" %>'
	headerNames="title"
>
	<liferay-ui:search-container-results
		results="<%= regularRoles %>"
		total="<%= regularRoles.size() %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.Role"
		keyProperty="roleId"
		modelVar="regularRole"
	>
		<liferay-util:param name="className" value="<%= EnterpriseAdminUtil.getCssClassName(regularRole) %>" />
		<liferay-util:param name="classHoverName" value="<%= EnterpriseAdminUtil.getCssClassName(regularRole) %>" />

		<liferay-ui:search-container-column-text
			name="title"
			value="<%= regularRole.getTitle(locale) %>"
		/>

		<c:if test="<%= !portletName.equals(PortletKeys.MY_ACCOUNT) %>">
			<liferay-ui:search-container-column-text>
				<a class="modify-link" href="javascript: ;" onclick="jQuery(this).trigger('change'); Liferay.SearchContainer.get('<portlet:namespace />regularRolesSearchContainer').deleteRow(this, <%= regularRole.getRoleId() %>);"><%= removeRoleIcon %></a>
			</liferay-ui:search-container-column-text>
		</c:if>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>

<c:if test="<%= !portletName.equals(PortletKeys.MY_ACCOUNT) %>">
	<liferay-ui:icon
		image="add"
		message="select"
		url='<%= "javascript: " + renderResponse.getNamespace() + "openRegularRoleSelector();" %>'
		label="<%= true %>"
		cssClass="modify-link"
	/>
</c:if>

<br />
<br />

<h3><liferay-ui:message key="organization-roles" /></h3>

<c:choose>
	<c:when test="<%= organizations.isEmpty() %>">
		<liferay-ui:message key="this-user-does-not-belong-to-any-organization-to-which-an-organization-role-can-be-assigned" />
	</c:when>
	<c:otherwise>
		<liferay-ui:search-container
			id='<%= renderResponse.getNamespace() + "organizationRolesSearchContainer" %>'
			headerNames="title,organization,"
		>
			<liferay-ui:search-container-results
				results="<%= organizationRoles %>"
				total="<%= organizationRoles.size() %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.model.UserGroupRole"
				keyProperty="roleId"
				modelVar="organizationRole"
			>
				<liferay-util:param name="className" value="<%= EnterpriseAdminUtil.getCssClassName(organizationRole.getRole()) %>" />
				<liferay-util:param name="classHoverName" value="<%= EnterpriseAdminUtil.getCssClassName(organizationRole.getRole()) %>" />

				<liferay-ui:search-container-column-text
					name="title"
					value="<%= organizationRole.getRole().getTitle(locale) %>"
				/>

				<liferay-ui:search-container-column-text
					name="organization"
					value="<%= organizationRole.getGroup().getDescriptiveName() %>"
				/>

				<c:if test="<%= !portletName.equals(PortletKeys.MY_ACCOUNT) %>">
					<liferay-ui:search-container-column-text>
						<a class="modify-link" href="javascript: ;" onclick="jQuery(this).trigger('change'); Liferay.SearchContainer.get('<portlet:namespace />organizationRolesSearchContainer').deleteRow(this, '<%= organizationRole.getRoleId() %>'); <portlet:namespace />deleteGroupRole('<%= organizationRole.getRoleId() %>', '<%= organizationRole.getGroupId() %>')"><%= removeRoleIcon %></a>
					</liferay-ui:search-container-column-text>
				</c:if>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>

		<c:if test="<%= !portletName.equals(PortletKeys.MY_ACCOUNT) %>">
			<liferay-ui:icon
				image="add"
				message="select"
				url='<%= "javascript: " + renderResponse.getNamespace() + "openOrganizationRoleSelector();" %>'
				label="<%= true %>"
				cssClass="modify-link"
			/>
		</c:if>
	</c:otherwise>
</c:choose>

<br />
<br />

<h3><liferay-ui:message key="community-roles" /></h3>

<c:choose>
	<c:when test="<%= groups.isEmpty() %>">
		<liferay-ui:message key="this-user-does-not-belong-to-any-community-to-which-a-community-role-can-be-assigned" />
	</c:when>
	<c:otherwise>
		<liferay-ui:search-container
			id='<%= renderResponse.getNamespace() + "communityRolesSearchContainer" %>'
			headerNames="title,community,"
		>
			<liferay-ui:search-container-results
				results="<%= communityRoles %>"
				total="<%= communityRoles.size() %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.model.UserGroupRole"
				keyProperty="roleId"
				modelVar="communityRole"
			>
				<liferay-util:param name="className" value="<%= EnterpriseAdminUtil.getCssClassName(communityRole.getRole()) %>" />
				<liferay-util:param name="classHoverName" value="<%= EnterpriseAdminUtil.getCssClassName(communityRole.getRole()) %>" />

				<liferay-ui:search-container-column-text
					name="title"
					value="<%= communityRole.getRole().getTitle(locale) %>"
				/>

				<liferay-ui:search-container-column-text
					name="community"
					value="<%= communityRole.getGroup().getDescriptiveName() %>"
				/>

				<c:if test="<%= !portletName.equals(PortletKeys.MY_ACCOUNT) %>">
					<liferay-ui:search-container-column-text>
						<a class="modify-link" href="javascript: ;" onclick="jQuery(this).trigger('change'); Liferay.SearchContainer.get('<portlet:namespace />communityRolesSearchContainer').deleteRow(this, '<%= communityRole.getRoleId() %>'); <portlet:namespace />deleteGroupRole('<%= communityRole.getRoleId() %>', '<%= communityRole.getGroupId() %>')"><%= removeRoleIcon %></a>
					</liferay-ui:search-container-column-text>
				</c:if>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>

		<c:if test="<%= !portletName.equals(PortletKeys.MY_ACCOUNT) %>">
			<liferay-ui:icon
				image="add"
				message="select"
				url='<%= "javascript: " + renderResponse.getNamespace() + "openCommunityRoleSelector();" %>'
				label="<%= true %>"
				cssClass="modify-link"
			/>
		</c:if>
	</c:otherwise>
</c:choose>