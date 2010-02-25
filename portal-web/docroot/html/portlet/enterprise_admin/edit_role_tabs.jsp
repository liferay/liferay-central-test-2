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
String cmd = ParamUtil.getString(request, Constants.CMD);

tabs1 = ParamUtil.getString(request, "tabs1");

String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL", redirect);

Role role = (Role)request.getAttribute(WebKeys.ROLE);

String portletResource = ParamUtil.getString(request, "portletResource");

String portletResourceLabel = null;

if (Validator.isNotNull(portletResource)) {
	Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletResource);

	portletResourceLabel = PortalUtil.getPortletTitle(portlet, application, locale);
}

// Edit

PortletURL editRoleURL = renderResponse.createRenderURL();

editRoleURL.setParameter("struts_action", "/enterprise_admin/edit_role");
editRoleURL.setParameter("redirect", backURL);
editRoleURL.setParameter(Constants.CMD, Constants.VIEW);
editRoleURL.setParameter("roleId", String.valueOf(role.getRoleId()));

// Define permissions

PortletURL definePermissionsURL = renderResponse.createRenderURL();

definePermissionsURL.setParameter("struts_action", "/enterprise_admin/edit_role_permissions");
definePermissionsURL.setParameter("redirect", backURL);
definePermissionsURL.setParameter(Constants.CMD, Constants.VIEW);
definePermissionsURL.setParameter("roleId", String.valueOf(role.getRoleId()));

// Assign members

PortletURL assignMembersURL = renderResponse.createRenderURL();

assignMembersURL.setParameter("struts_action", "/enterprise_admin/edit_role_assignments");
assignMembersURL.setParameter("redirect", backURL);
assignMembersURL.setParameter("roleId", String.valueOf(role.getRoleId()));

int pos = 0;

String tabs1Names = StringPool.BLANK;

if (RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.UPDATE)) {
	tabs1Names += ",edit";

	request.setAttribute("liferay-ui:tabs:url" + pos++, editRoleURL.toString());
}

String name = role.getName();

if (!name.equals(RoleConstants.ADMINISTRATOR) && !name.equals(RoleConstants.COMMUNITY_ADMINISTRATOR) && !name.equals(RoleConstants.COMMUNITY_OWNER) && !name.equals(RoleConstants.ORGANIZATION_ADMINISTRATOR) && !name.equals(RoleConstants.ORGANIZATION_OWNER) && !name.equals(RoleConstants.OWNER) && RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.DEFINE_PERMISSIONS)) {
	tabs1Names += ",define-permissions";

	request.setAttribute("liferay-ui:tabs:url" + pos++, definePermissionsURL.toString());
}

boolean unassignableRole = false;

if (name.equals(RoleConstants.GUEST) || name.equals(RoleConstants.OWNER) || name.equals(RoleConstants.USER)) {
	unassignableRole = true;
}

if (!unassignableRole && (role.getType() == RoleConstants.TYPE_REGULAR) && RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.ASSIGN_MEMBERS)) {
	tabs1Names += ",assign-members";

	request.setAttribute("liferay-ui:tabs:url" + pos++, assignMembersURL.toString());
}

if (tabs1Names.startsWith(",")) {
	tabs1Names = tabs1Names.substring(1);
}

// Breadcrumbs

PortalUtil.addPortletBreadcrumbEntry(request, role.getTitle(locale), null);

request.setAttribute("edit_role_permissions.jsp-role", role);

request.setAttribute("edit_role_permissions.jsp-portletResource", portletResource);
%>

<h3><%= role.getTitle(locale) %></h3>

<liferay-ui:tabs names="<%= tabs1Names %>" />