<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
String viewUsersRedirect = ParamUtil.getString(request, "viewUsersRedirect");
String backURL = ParamUtil.getString(request, "backURL", viewUsersRedirect);

PortletURL portletURL = (PortletURL)request.getAttribute("view.jsp-portletURL");

if (portletURL == null) {
	portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("struts_action", "/enterprise_admin/view_users");

	pageContext.setAttribute("portletURL", portletURL);
}

if (Validator.isNotNull(viewUsersRedirect)) {
	portletURL.setParameter("viewUsersRedirect", viewUsersRedirect);
}
%>

<liferay-ui:error exception="<%= RequiredOrganizationException.class %>" message="you-cannot-delete-organizations-that-have-suborganizations-or-users" />
<liferay-ui:error exception="<%= RequiredUserException.class %>" message="you-cannot-delete-or-deactivate-yourself" />

<liferay-util:include page="/html/portlet/enterprise_admin/user/toolbar.jsp">
	<liferay-util:param name="toolbarItem" value="view" />
</liferay-util:include>

<%
String usersListView = request.getParameter("usersListView");

PortalPreferences portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(renderRequest);

if (usersListView == null) {
	usersListView = portalPreferences.getValue(PortletKeys.ENTERPRISE_ADMIN_USERS, "users-list-view", PropsValues.USERS_LIST_VIEWS_DEFAULT);
}
else {
	boolean saveUsersListView = ParamUtil.getBoolean(request, "saveUsersListView");

	if (saveUsersListView && ArrayUtil.contains(PropsValues.USERS_LIST_VIEWS, usersListView)) {
		portalPreferences.setValue(PortletKeys.ENTERPRISE_ADMIN_USERS, "users-list-view", usersListView);
	}
}
%>

<c:choose>
	<c:when test="<%= usersListView.equals(UserConstants.LIST_VIEW_FLAT_ORGANIZATIONS) %>">
		<%@ include file="/html/portlet/enterprise_admin/view_users_flat_organizations.jspf" %>
	</c:when>
	<c:when test="<%= usersListView.equals(UserConstants.LIST_VIEW_FLAT_USER_GROUPS) %>">
		<%@ include file="/html/portlet/enterprise_admin/view_users_flat_user_groups.jsp" %>
	</c:when>
	<c:when test="<%= usersListView.equals(UserConstants.LIST_VIEW_FLAT_USERS) %>">
		<%@ include file="/html/portlet/enterprise_admin/view_users_flat_users.jspf" %>
	</c:when>
	<c:otherwise>
		<%@ include file="/html/portlet/enterprise_admin/view_users_tree.jspf" %>
	</c:otherwise>
</c:choose>