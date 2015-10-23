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

<%@ include file="/html/portlet/software_catalog/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

SCFrameworkVersion frameworkVersion = (SCFrameworkVersion)row.getObject();

String frameworkVersionId = String.valueOf(frameworkVersion.getFrameworkVersionId());
%>

<liferay-ui:icon-menu icon="<%= StringPool.BLANK %>" message="<%= StringPool.BLANK %>">
	<c:if test="<%= SCFrameworkVersionPermission.contains(permissionChecker, frameworkVersion, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcRenderCommandName" value="/software_catalog/edit_framework_version" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="frameworkVersionId" value="<%= frameworkVersionId %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			iconCssClass="icon-edit"
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= SCFrameworkVersionPermission.contains(permissionChecker, frameworkVersion, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= SCFrameworkVersion.class.getName() %>"
			modelResourceDescription="<%= frameworkVersion.getName() %>"
			resourcePrimKey="<%= frameworkVersionId %>"
			var="permissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			iconCssClass="icon-lock"
			message="permissions"
			method="get"
			url="<%= permissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= SCFrameworkVersionPermission.contains(permissionChecker, frameworkVersion, ActionKeys.DELETE) %>">
		<portlet:actionURL name="/software_catalog/edit_framework_version" var="deleteURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="frameworkVersionId" value="<%= frameworkVersionId %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>