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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

LayoutSetBranch branch = (LayoutSetBranch)row.getObject();
%>

<liferay-ui:icon-menu>
	<c:if test="<%= LayoutSetBranchPermissionUtil.contains(permissionChecker, branch, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= LayoutSetBranch.class.getName() %>"
			modelResourceDescription="<%= branch.getName() %>"
			resourcePrimKey="<%= String.valueOf(branch.getLayoutSetBranchId()) %>"
			var="permissionsURL"
		/>

		<liferay-ui:icon
			image="permissions"
			url="<%= permissionsURL %>"
		/>
	</c:if>

	<c:if test="<%= !branch.isMaster() && LayoutSetBranchPermissionUtil.contains(permissionChecker, branch, ActionKeys.DELETE) %>">
		<portlet:actionURL var="deleteURL">
			<portlet:param name="struts_action" value="/layouts_admin/edit_layout_set_branch" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(branch.getGroupId()) %>" />
			<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(branch.getLayoutSetBranchId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>

	<c:if test="<%= LayoutSetBranchPermissionUtil.contains(permissionChecker, branch, ActionKeys.MERGE) %>">
		<liferay-ui:icon
			image="edit"
			message="merge"
			url='<%= "javascript:Liferay.Staging.Branching.mergeBranch({groupId:" + branch.getGroupId() + ",layoutSetBranchId:" + branch.getLayoutSetBranchId() + ",privateLayout:" + branch.getPrivateLayout() + "});" %>'
		/>
	</c:if>
</liferay-ui:icon-menu>