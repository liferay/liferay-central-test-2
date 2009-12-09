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

<%@ include file="/html/portlet/workflow_tasks/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

WorkflowTask workflowTask = (WorkflowTask)row.getParameter("workflowTask");
%>

<liferay-ui:icon-menu>
	<c:if test="<%= !workflowTask.isCompleted() && (workflowTask.getAssigneeUserId() == user.getUserId()) %>">
		<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editURL">
			<portlet:param name="struts_action" value="/workflow_tasks/edit_workflow_task" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="workflowTaskId" value="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon image="edit" url="<%= editURL %>" />

		<%
		List<String> transitionNames = WorkflowTaskManagerUtil.getNextTransitionNames(user.getUserId(), workflowTask.getWorkflowTaskId());

		for (String transitionName : transitionNames) {
			String message = "proceed";

			if (Validator.isNotNull(transitionName)) {
				message = transitionName;
			}
		%>
			<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editURL">
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
		<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editURL">
			<portlet:param name="struts_action" value="/workflow_tasks/edit_workflow_task" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ASSIGN %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="workflowTaskId" value="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>" />
			<portlet:param name="assigneeUserId" value="<%= String.valueOf(user.getUserId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon image="assign" message="assign-to-me" url="<%= editURL %>" />
	</c:if>
</liferay-ui:icon-menu>