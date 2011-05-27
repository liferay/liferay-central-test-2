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
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view");
%>

<div class="lfr-portlet-toolbar">
	<portlet:renderURL var="viewUsersURL">
		<portlet:param name="struts_action" value="/enterprise_admin/view" />
	</portlet:renderURL>

	<span class="lfr-toolbar-button view-button <%= toolbarItem.equals("view") ? "current" : StringPool.BLANK %>">
		<a href="<%= viewUsersURL %>"><liferay-ui:message key="view" /></a>
	</span>

	<c:if test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_USER) %>">
		<portlet:renderURL var="addUserURL">
			<portlet:param name="struts_action" value="/enterprise_admin/edit_user" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</portlet:renderURL>

		<span class="lfr-toolbar-button add-button <%= toolbarItem.equals("add-user") ? "current" : StringPool.BLANK %>"><a href="<%= addUserURL %>"><liferay-ui:message key="add-user" /></a></span>
	</c:if>

	<c:if test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_ORGANIZATION) %>">
		<portlet:renderURL var="addOrganizationURL">
			<portlet:param name="struts_action" value="/enterprise_admin/edit_organization" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</portlet:renderURL>

		<span class="lfr-toolbar-button add-button <%= toolbarItem.equals("add-organization") ? "current" : StringPool.BLANK %>"><a href="<%= addOrganizationURL %>"><liferay-ui:message key="add-organization" /></a></span>
	</c:if>

	<c:if test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.EXPORT_USER) %>">
		<portlet:renderURL var="addUserURL">
			<portlet:param name="struts_action" value="/enterprise_admin/edit_user" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</portlet:renderURL>

		<span class="lfr-toolbar-button export-button"><a href="javascript:<portlet:namespace />exportUsers();"><liferay-ui:message key="export-users" /></a></span>
	</c:if>
</div>