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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
Role role = (Role)request.getAttribute(WebKeys.ROLE);

tabs1 = ParamUtil.getString(request, "tabs1");
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL", redirect);

String cmd = ParamUtil.getString(request, Constants.CMD);
String portletResource = ParamUtil.getString(request, "portletResource");

String portletResourceLabel = null;

if (Validator.isNotNull(portletResource)) {
	Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletResource);

	portletResourceLabel = PortalUtil.getPortletTitle(portlet, application, locale);
}

// Edit

PortletURL editRoleURL = renderResponse.createRenderURL();

editRoleURL.setWindowState(WindowState.MAXIMIZED);

editRoleURL.setParameter("struts_action", "/enterprise_admin/edit_role");
editRoleURL.setParameter("redirect", backURL);
editRoleURL.setParameter(Constants.CMD, Constants.VIEW);
editRoleURL.setParameter("roleId", String.valueOf(role.getRoleId()));

// Supported clients

PortletURL definePermissionsURL = renderResponse.createRenderURL();

definePermissionsURL.setWindowState(WindowState.MAXIMIZED);

definePermissionsURL.setParameter("struts_action", "/enterprise_admin/edit_role_permissions");
definePermissionsURL.setParameter("redirect", backURL);
definePermissionsURL.setParameter(Constants.CMD, Constants.VIEW);
definePermissionsURL.setParameter("roleId", String.valueOf(role.getRoleId()));

// Assign Members

PortletURL assignMembersURL = renderResponse.createRenderURL();

assignMembersURL.setWindowState(WindowState.MAXIMIZED);

assignMembersURL.setParameter("struts_action", "/enterprise_admin/edit_role_assignments");
assignMembersURL.setParameter("redirect", backURL);
assignMembersURL.setParameter("roleId", String.valueOf(role.getRoleId()));

int pos = 0;

String tabNames = StringPool.BLANK;

if (RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.UPDATE)) {
	tabNames += ",edit";

	request.setAttribute("liferay-ui:tabs:url" + pos++, editRoleURL.toString());
}

String name = role.getName();

if (!name.equals(RoleConstants.ADMINISTRATOR) && !name.equals(RoleConstants.COMMUNITY_ADMINISTRATOR) && !name.equals(RoleConstants.COMMUNITY_OWNER) && !name.equals(RoleConstants.ORGANIZATION_ADMINISTRATOR) && !name.equals(RoleConstants.ORGANIZATION_OWNER) && !name.equals(RoleConstants.OWNER) && RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.DEFINE_PERMISSIONS)) {
	tabNames += ",define-permissions";

	request.setAttribute("liferay-ui:tabs:url" + pos++, definePermissionsURL.toString());
}

boolean unassignableRole = false;

if (name.equals(RoleConstants.GUEST) || name.equals(RoleConstants.OWNER) || name.equals(RoleConstants.USER)) {
	unassignableRole = true;
}

if (!unassignableRole && (role.getType() == RoleConstants.TYPE_REGULAR) && RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.ASSIGN_MEMBERS)) {
	tabNames += ",assign-members";

	request.setAttribute("liferay-ui:tabs:url" + pos++, assignMembersURL.toString());
}

if (tabNames.startsWith(",")) {
	tabNames = tabNames.substring(1);
}

// Breadcrumbs

PortletURL breadcrumbsURL = renderResponse.createRenderURL();

breadcrumbsURL.setWindowState(WindowState.MAXIMIZED);

breadcrumbsURL.setParameter("struts_action", "/enterprise_admin/view");
breadcrumbsURL.setParameter("tabs1", tabs1);

String breadcrumbs = "<a href=\"" + breadcrumbsURL.toString() + "\">" + LanguageUtil.get(pageContext, "roles") + "</a> &raquo; ";

breadcrumbsURL.setParameter("struts_action", "/enterprise_admin/edit_role");
breadcrumbsURL.setParameter(Constants.CMD, Constants.VIEW);

breadcrumbs += "<strong>" + HtmlUtil.escape(role.getTitle(locale)) + "</strong>";

breadcrumbsURL.setParameter(Constants.CMD, Constants.EDIT);

if (Validator.isNotNull(tabs1) && tabs1.equals("define-permissions")) {
	breadcrumbs += " &raquo; <a href=\"" + definePermissionsURL.toString() + "\">" + LanguageUtil.get(pageContext, "define-permissions") + "</a>";
}
else if (Validator.isNotNull(tabs1) && tabs1.equals("assign-members")) {
	breadcrumbs += " &raquo; <a href=\"" + assignMembersURL.toString() + "\">" + LanguageUtil.get(pageContext, "define-permissions") + "</a>";
}

if (!cmd.equals(Constants.VIEW) && Validator.isNotNull(portletResource)) {
	breadcrumbsURL.setParameter("portletResource", portletResource);

	breadcrumbs += " &raquo; <a href=\"" + breadcrumbsURL.toString() + "\">" + portletResourceLabel + "</a>";
}

request.setAttribute("edit_role_permissions.jsp-role", role);

request.setAttribute("edit_role_permissions.jsp-portletResource", portletResource);
%>

<div class="breadcrumbs">
	<%= breadcrumbs %>
</div>

<liferay-ui:tabs names="<%= tabNames %>" />