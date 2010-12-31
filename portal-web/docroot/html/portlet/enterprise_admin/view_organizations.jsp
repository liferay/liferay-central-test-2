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
PortletURL portletURL = (PortletURL)request.getAttribute("view.jsp-portletURL");
%>

<liferay-ui:error exception="<%= RequiredOrganizationException.class %>" message="you-cannot-delete-organizations-that-have-suborganizations-or-users" />

<liferay-util:include page="/html/portlet/enterprise_admin/organization/toolbar.jsp">
	<liferay-util:param name="toolbarItem" value="view-all" />
</liferay-util:include>

<%
String organizationsListView = request.getParameter("organizationsListView");

PortalPreferences portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(renderRequest);

if (organizationsListView == null) {
	organizationsListView = portalPreferences.getValue(PortletKeys.ENTERPRISE_ADMIN_ORGANIZATIONS, "organizations-list-view", PropsValues.ORGANIZATIONS_LIST_VIEWS_DEFAULT);
}
else {
	boolean saveOrganizationsListView = ParamUtil.getBoolean(request, "saveOrganizationsListView");

	if (saveOrganizationsListView && ArrayUtil.contains(PropsValues.ORGANIZATIONS_LIST_VIEWS, organizationsListView)) {
		portalPreferences.setValue(PortletKeys.ENTERPRISE_ADMIN_ORGANIZATIONS, "organizations-list-view", organizationsListView);
	}
}
%>

<c:choose>
	<c:when test="<%= organizationsListView.equals(OrganizationConstants.LIST_VIEW_FLAT) %>">
		<%@ include file="/html/portlet/enterprise_admin/view_organizations_flat.jspf" %>
	</c:when>
	<c:otherwise>
		<%@ include file="/html/portlet/enterprise_admin/view_organizations_tree.jspf" %>
	</c:otherwise>
</c:choose>