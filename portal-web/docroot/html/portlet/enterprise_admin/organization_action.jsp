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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
OrganizationSearch searchContainer = (OrganizationSearch)request.getAttribute("liferay-ui:search:searchContainer");

String redirect = searchContainer.getIteratorURL().toString();

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Organization organization = (Organization)row.getObject();

long organizationId = organization.getOrganizationId();
%>

<liferay-ui:icon-menu>
	<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editOrganizationURL">
		<portlet:param name="struts_action" value="/enterprise_admin/edit_organization" />
		<portlet:param name="organizationId" value="<%= String.valueOf(organizationId) %>" />
		<portlet:param name="redirect" value="<%= redirect %>" />
	</portlet:renderURL>

	<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="deleteOrganizationURL">
		<portlet:param name="struts_action" value="/enterprise_admin/edit_organization" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
		<portlet:param name="deleteOrganizationIds" value="<%= String.valueOf(organizationId) %>" />
		<portlet:param name="redirect" value="<%= redirect %>" />
	</portlet:actionURL>

	<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="pagesURL">
		<portlet:param name="struts_action" value="/enterprise_admin/edit_pages" />
		<portlet:param name="redirect" value="<%= redirect %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(organization.getGroup().getGroupId()) %>" />
	</portlet:renderURL>

	<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="addUserURL">
		<portlet:param name="struts_action" value="/enterprise_admin/edit_user" />
		<portlet:param name="organizationIds" value="<%= String.valueOf(organizationId) %>" />
		<portlet:param name="organizationName" value="<%= organization.getName() %>" />
	</portlet:renderURL>

	<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="viewUsersURL">
		<portlet:param name="struts_action" value="/enterprise_admin/view" />
		<portlet:param name="tabs1" value="users" />
		<portlet:param name="organizationId" value="<%= String.valueOf(organizationId) %>" />
	</portlet:renderURL>

	<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="viewSuborganizationsURL">
		<portlet:param name="struts_action" value="/enterprise_admin/view" />
		<portlet:param name="tabs1" value="organizations" />
		<portlet:param name="parentOrganizationId" value="<%= String.valueOf(organizationId) %>" />
	</portlet:renderURL>

	<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="addSuborganizationURL">
		<portlet:param name="struts_action" value="/enterprise_admin/edit_organization" />
		<portlet:param name="parentOrganizationId" value="<%= String.valueOf(organizationId) %>" />
		<portlet:param name="parentOrganizationName" value="<%= organization.getName() %>" />
	</portlet:renderURL>

	<c:if test="<%= portletName.equals(PortletKeys.ENTERPRISE_ADMIN) || portletName.equals(PortletKeys.ORGANIZATION_ADMIN) %>">
		<c:if test="<%= OrganizationPermissionUtil.contains(permissionChecker, organizationId, ActionKeys.UPDATE) %>">
			<liferay-ui:icon image="edit" url="<%= editOrganizationURL %>" />
		</c:if>

		<c:if test="<%= OrganizationPermissionUtil.contains(permissionChecker, organizationId, ActionKeys.PERMISSIONS) %>">
			<liferay-security:permissionsURL
				modelResource="<%= Organization.class.getName() %>"
				modelResourceDescription="<%= organization.getName() %>"
				resourcePrimKey="<%= String.valueOf(organization.getOrganizationId()) %>"
				var="editOrganizationPermissionsURL"
			/>

			<liferay-ui:icon image="permissions" url="<%= editOrganizationPermissionsURL %>" />
		</c:if>

		<c:if test="<%= OrganizationPermissionUtil.contains(permissionChecker, organizationId, ActionKeys.UPDATE) %>">
			<liferay-ui:icon image="pages" message="configure-pages" url="<%= pagesURL %>" />
		</c:if>

		<c:if test="<%= OrganizationPermissionUtil.contains(permissionChecker, organizationId, ActionKeys.ADD_USER) %>">
			<liferay-ui:icon image="add_user" message="add-user" url="<%= addUserURL %>" />

			<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="assignUserRolesURL">
				<portlet:param name="struts_action" value="/enterprise_admin/edit_user_roles" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(organization.getGroup().getGroupId()) %>" />
			</portlet:renderURL>

			<liferay-ui:icon image="assign_user_roles" url="<%= assignUserRolesURL %>" />
		</c:if>
	</c:if>

	<liferay-ui:icon image="view_users" message="view-users" url="<%= viewUsersURL %>" />

	<c:if test="<%= organization.isRegular() %>">
		<c:if test="<%= (portletName.equals(PortletKeys.ENTERPRISE_ADMIN) || portletName.equals(PortletKeys.ORGANIZATION_ADMIN)) && OrganizationPermissionUtil.contains(permissionChecker, organizationId, ActionKeys.ADD_LOCATION) %>">
			<liferay-ui:icon image="add_location" message="add-suborganization" url="<%= addSuborganizationURL %>" />
		</c:if>

		<liferay-ui:icon image="view_locations" message="view-suborganizations" url="<%= viewSuborganizationsURL %>" />
	</c:if>

	<c:if test="<%= portletName.equals(PortletKeys.ENTERPRISE_ADMIN) && OrganizationPermissionUtil.contains(permissionChecker, organizationId, ActionKeys.DELETE) %>">
		<liferay-ui:icon-delete url="<%= deleteOrganizationURL %>" />
	</c:if>
</liferay-ui:icon-menu>