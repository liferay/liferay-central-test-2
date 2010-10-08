<%--
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

long organizationId = GetterUtil.getLong(request.getParameter("organizationId"), OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID);
boolean saveOrganizationsListView = GetterUtil.getBoolean(request.getParameter("saveOrganizationsListView"));

if (saveOrganizationsListView) {
	portalPreferences.setValue(PortletKeys.ENTERPRISE_ADMIN_ORGANIZATIONS, "organizations-list-view", organizationsListView);
}

String viewOrganizationsRedirect = ParamUtil.getString(request, "viewOrganizationsRedirect");

if (Validator.isNotNull(viewOrganizationsRedirect)) {
	portletURL.setParameter("viewOrganizationsRedirect", viewOrganizationsRedirect);
}
%>

<liferay-ui:error exception="<%= RequiredOrganizationException.class %>" message="you-cannot-delete-organizations-that-have-suborganizations-or-users" />

<liferay-util:include page="/html/portlet/enterprise_admin/organization/toolbar.jsp">
	<liferay-util:param name="toolbarItem" value="view-all" />
</liferay-util:include>

<c:if test="<%= Validator.isNotNull(viewOrganizationsRedirect) %>">
	<aui:input name="viewOrganizationsRedirect" type="hidden" value="<%= viewOrganizationsRedirect %>" />
</c:if>

<c:choose>
	<c:when test="<%= organizationsListView.equals(OrganizationConstants.LIST_VIEW_FLAT) %>">
		<%@ include file="/html/portlet/enterprise_admin/organization/view_organizations_flat.jspf" %>
	</c:when>
	<c:otherwise>
		<%@ include file="/html/portlet/enterprise_admin/organization/view_organizations_tree.jspf" %>
	</c:otherwise>
</c:choose>