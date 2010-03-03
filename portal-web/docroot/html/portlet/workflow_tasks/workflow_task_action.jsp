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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

WorkflowTask workflowTask = (WorkflowTask)row.getParameter("workflowTask");
%>

<liferay-ui:icon-menu>
	<c:if test="<%= !workflowTask.isCompleted() && (workflowTask.getAssigneeUserId() == user.getUserId()) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="struts_action" value="/workflow_tasks/edit_workflow_task" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="workflowTaskId" value="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon image="edit" url="<%= editURL %>" />

		<%
		List<String> transitionNames = WorkflowTaskManagerUtil.getNextTransitionNames(company.getCompanyId(), user.getUserId(), workflowTask.getWorkflowTaskId());

		for (String transitionName : transitionNames) {
			String message = "proceed";

			if (Validator.isNotNull(transitionName)) {
				message = transitionName;
			}
		%>
			<portlet:actionURL var="editURL">
				<portlet:param name="struts_action" value="/workflow_tasks/edit_workflow_task" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SAVE %>" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="workflowTaskId" value="<%= StringUtil.valueOf(workflowTask.getWorkflowTaskId()) %>" />
				<portlet:param name="assigneeUserId" value="<%= StringUtil.valueOf(workflowTask.getAssigneeUserId()) %>" />

				<c:if test="<%= transitionName != null %>">
					<portlet:param name="transitionName" value="<%= transitionName %>" />
				</c:if>
			</portlet:actionURL>

			<liferay-ui:icon image="edit" message="<%= message %>" url="<%= editURL %>" />

		<%
		}
		%>

	</c:if>

	<c:if test="<%= PortletPermissionUtil.contains(permissionChecker, PortletKeys.WORKFLOW_TASKS, ActionKeys.ASSIGN_USER_TASKS) && !workflowTask.isCompleted() && (workflowTask.getAssigneeUserId() != user.getUserId()) %>">
		<portlet:actionURL var="editURL">
			<portlet:param name="struts_action" value="/workflow_tasks/edit_workflow_task" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ASSIGN %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="workflowTaskId" value="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>" />
			<portlet:param name="assigneeUserId" value="<%= String.valueOf(user.getUserId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon image="assign" message="assign-to-me" url="<%= editURL %>" />
	</c:if>
</liferay-ui:icon-menu>