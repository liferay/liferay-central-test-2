<%
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
%>

<%@ include file="/html/portlet/workflow_tasks/init.jsp" %>

<%
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "all");
String backURL = ParamUtil.getString(request, "backURL");
%>

<div class="lfr-portlet-toolbar">
	<c:if test="<%= PortletPermissionUtil.contains(permissionChecker, PortletKeys.WORKFLOW_TASKS, ActionKeys.VIEW_ALL_TASKS) %>">
		<portlet:renderURL var="viewAllURL">
			<portlet:param name="struts_action" value="/workflow_tasks/view" />
			<portlet:param name="toolbarItem" value="all" />
		</portlet:renderURL>

		<span class="lfr-toolbar-button view-button <%= toolbarItem.equals("all") ? "current" : StringPool.BLANK %>">
			<a href="<%= viewAllURL %>"><liferay-ui:message key="all" /></a>
		</span>
	</c:if>

	<portlet:renderURL var="assignedToMeURL">
		<portlet:param name="struts_action" value="/workflow_tasks/view" />
		<portlet:param name="toolbarItem" value="assigned-to-me" />
	</portlet:renderURL>

	<span class="lfr-toolbar-button assigned-to-me <%= toolbarItem.equals("assigned-to-me") ? "current" : StringPool.BLANK %>">
		<a href="<%= assignedToMeURL %>"><liferay-ui:message key="assigned-to-me" /></a>
	</span>

	<portlet:renderURL var="assignedToMyRolesURL">
		<portlet:param name="struts_action" value="/workflow_tasks/view" />
		<portlet:param name="toolbarItem" value="assigned-to-my-roles" />
	</portlet:renderURL>

	<span class="lfr-toolbar-button assigned-to-my-role <%= toolbarItem.equals("assigned-to-my-roles") ? "current" : StringPool.BLANK %>">
		<a href="<%= assignedToMyRolesURL %>"><liferay-ui:message key="assigned-to-my-roles" /></a>
	</span>

	<portlet:renderURL var="completedURL">
		<portlet:param name="struts_action" value="/workflow_tasks/view" />
		<portlet:param name="toolbarItem" value="my-completed-tasks" />
	</portlet:renderURL>

	<span class="lfr-toolbar-button completed-button <%= toolbarItem.equals("my-completed-tasks") ? "current" : StringPool.BLANK %>">
		<a href="<%= completedURL %>"><liferay-ui:message key="my-completed-tasks" /></a>
	</span>
</div>