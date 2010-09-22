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

String viewOrganizationsRedirect = ParamUtil.getString(request, "viewOrganizationsRedirect");

if (Validator.isNotNull(viewOrganizationsRedirect)) {
	portletURL.setParameter("viewOrganizationsRedirect", viewOrganizationsRedirect);
}

long organizationId = GetterUtil.getLong(request.getParameter("organizationId"));
long defaultOrganizationId = GetterUtil.getLong(preferences.getValue("rootOrganizationId", StringPool.BLANK), OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID);

request.setAttribute("view_organizations.jsp-organizationId", organizationId);
request.setAttribute("view_organizations.jsp-defaultOrganizationId", defaultOrganizationId);
request.setAttribute("view_organizations.jsp-organizationView", organizationView);
request.setAttribute("view_organizations.jsp-portletURL", portletURL);
request.setAttribute("view_organizations.jsp-viewOrganizationsRedirect", viewOrganizationsRedirect);
%>

<liferay-ui:error exception="<%= RequiredOrganizationException.class %>" message="you-cannot-delete-organizations-that-have-suborganizations-or-users" />

<liferay-util:include page="/html/portlet/enterprise_admin/organization/toolbar.jsp">
	<liferay-util:param name="toolbarItem" value="view-all" />
</liferay-util:include>

<c:if test="<%= Validator.isNotNull(viewOrganizationsRedirect) %>">
	<aui:input name="viewOrganizationsRedirect" type="hidden" value="<%= viewOrganizationsRedirect %>" />
</c:if>

<c:choose>
	<c:when test="<%= organizationView.equals(OrganizationConstants.ORGANIZATION_VIEW_FLAT) %>">
		<liferay-util:include page="/html/portlet/enterprise_admin/organization/view_organizations_flat.jsp" />
	</c:when>
	<c:when test="<%= organizationView.equals(OrganizationConstants.ORGANIZATION_VIEW_TREE) %>">
		<liferay-util:include page="/html/portlet/enterprise_admin/organization/view_organizations_tree.jsp" />
	</c:when>
</c:choose>