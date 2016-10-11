<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1");

String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

long roleId = ParamUtil.getLong(request, "roleId");

Role role = RoleServiceUtil.fetchRole(roleId);

String[] tabs1Names = new String[0];
String[] tabs1URLs = new String[0];

String portletResource = ParamUtil.getString(request, "portletResource");
String portletURL = ParamUtil.getString(request, "portletURL");

if (role != null) {

	// Edit

	PortletURL editRoleURL = renderResponse.createRenderURL();

	editRoleURL.setParameter("mvcPath", "/edit_role.jsp");
	editRoleURL.setParameter("redirect", backURL);
	editRoleURL.setParameter("roleId", String.valueOf(role.getRoleId()));
	editRoleURL.setParameter("tabs1", "details");

	// Define permissions

	PortletURL definePermissionsURL = renderResponse.createRenderURL();

	definePermissionsURL.setParameter("mvcPath", "/edit_role_permissions.jsp");
	definePermissionsURL.setParameter("redirect", backURL);
	definePermissionsURL.setParameter(Constants.CMD, Constants.VIEW);
	definePermissionsURL.setParameter("roleId", String.valueOf(role.getRoleId()));
	definePermissionsURL.setParameter("tabs1", "define-permissions");

	// Assign members

	PortletURL assignMembersURL = renderResponse.createRenderURL();

	assignMembersURL.setParameter("mvcPath", "/edit_role_assignments.jsp");
	assignMembersURL.setParameter("redirect", backURL);
	assignMembersURL.setParameter("roleId", String.valueOf(role.getRoleId()));
	assignMembersURL.setParameter("tabs1", "assign-members");

	if (RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.UPDATE)) {
		tabs1Names = ArrayUtil.append(tabs1Names, "details");

		tabs1URLs = ArrayUtil.append(tabs1URLs, editRoleURL.toString());
	}

	String name = role.getName();

	if (!name.equals(RoleConstants.ADMINISTRATOR) && !name.equals(RoleConstants.ORGANIZATION_ADMINISTRATOR) && !name.equals(RoleConstants.ORGANIZATION_OWNER) && !name.equals(RoleConstants.OWNER) && !name.equals(RoleConstants.SITE_ADMINISTRATOR) && !name.equals(RoleConstants.SITE_OWNER) && RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.DEFINE_PERMISSIONS)) {
		tabs1Names = ArrayUtil.append(tabs1Names, "define-permissions");

		tabs1URLs = ArrayUtil.append(tabs1URLs, definePermissionsURL.toString());
	}

	boolean unassignableRole = false;

	if (name.equals(RoleConstants.GUEST) || name.equals(RoleConstants.OWNER) || name.equals(RoleConstants.USER)) {
		unassignableRole = true;
	}

	if (!unassignableRole && (role.getType() == RoleConstants.TYPE_REGULAR) && RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.ASSIGN_MEMBERS)) {
		tabs1Names = ArrayUtil.append(tabs1Names, "assign-members");

		tabs1URLs = ArrayUtil.append(tabs1URLs, assignMembersURL.toString());
	}

	// Breadcrumbs

	PortalUtil.addPortletBreadcrumbEntry(request, role.getTitle(locale), null);

	request.setAttribute("edit_role_permissions.jsp-role", role);

	request.setAttribute("edit_role_permissions.jsp-portletResource", portletResource);
}
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<c:choose>
			<c:when test="<%= role != null %>">

				<%
				for (int i = 0; i < tabs1URLs.length; i++) {
				%>

					<aui:nav-item href="<%= tabs1URLs[i] %>" label="<%= tabs1Names[i] %>" selected="<%= tabs1Names[i].equals(tabs1) %>" />

				<%
				}
				%>

			</c:when>
			<c:otherwise>
				<aui:nav-item href="<%= currentURL %>" label="details" selected="<%= true %>" />
				<aui:nav-item cssClass="disabled" label="define-permissions" selected="<%= false %>" />

				<%
				int type = ParamUtil.getInteger(request, "type", RoleConstants.TYPE_REGULAR);
				%>

				<c:if test="<%= type == RoleConstants.TYPE_REGULAR %>">
					<aui:nav-item cssClass="disabled" label="assign-members" selected="<%= false %>" />
				</c:if>
			</c:otherwise>
		</c:choose>
	</aui:nav>

	<c:if test='<%= tabs1.equals("assign-members") %>'>
		<aui:nav-bar-search>
			<aui:form action="<%= portletURL %>" name="searchFm">
				<liferay-ui:input-search autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" markupView="lexicon" />
			</aui:form>
		</aui:nav-bar-search>
	</c:if>
</aui:nav-bar>