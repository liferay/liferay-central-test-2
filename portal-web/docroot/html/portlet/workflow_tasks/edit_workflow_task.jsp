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
String redirect = ParamUtil.getString(request, "redirect");

WorkflowTask workflowTask = (WorkflowTask)request.getAttribute(WebKeys.WORKFLOW_TASK);

Calendar dueDate = CalendarFactoryUtil.getCalendar(timeZone, locale);

if (workflowTask.getDueDate() != null) {
	dueDate.setTime(workflowTask.getDueDate());
}

WorkflowInstance workflowInstance = WorkflowInstanceManagerUtil.getWorkflowInstance(company.getCompanyId(), workflowTask.getWorkflowInstanceId());

Map<String, Serializable> workflowInstanceContext = workflowInstance.getContext();

long companyId = ((Number)workflowInstanceContext.get(ContextConstants.COMPANY_ID)).longValue();
long groupId = ((Number)workflowInstanceContext.get(ContextConstants.GROUP_ID)).longValue();
String className = (String)workflowInstanceContext.get(ContextConstants.ENTRY_CLASS_NAME);
long classPK = ((Number)workflowInstanceContext.get(ContextConstants.ENTRY_CLASS_PK)).longValue();
%>

<h3 class="task-title"><%= workflowTask.getName() %></h3>

<portlet:actionURL var="editWorkflowTaskURL">
	<portlet:param name="struts_action" value="/workflow_tasks/edit_workflow_task" />
</portlet:actionURL>

<aui:form action="<%= editWorkflowTaskURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="workflowTaskId" type="hidden" value="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>" />
	<aui:input name="transitionName" type="hidden" />
	<aui:input name="comment" type="hidden" />

	<aui:field-wrapper inlineLabel="left" label="name">
		<%= workflowTask.getName() %>
	</aui:field-wrapper>

	<aui:field-wrapper label="description">
		<%= GetterUtil.getString(workflowTask.getDescription()) %>
	</aui:field-wrapper>

	<aui:field-wrapper inlineLabel="left" label="assignee">
		<%= HtmlUtil.escape(PortalUtil.getUserName(workflowTask.getAssigneeUserId(), StringPool.BLANK)) %>
	</aui:field-wrapper>

	<%
	WorkflowHandler workflowHandler = WorkflowHandlerRegistryUtil.getWorkflowHandler(className);

	PortletURL editPortletURL = workflowHandler.getURLEdit(classPK, (LiferayPortletRequest)renderRequest, (LiferayPortletResponse)renderResponse);
	%>

	<c:if test="<%= editPortletURL != null %>">

		<%
		editPortletURL.setWindowState(WindowState.MAXIMIZED);
		editPortletURL.setPortletMode(PortletMode.VIEW);

		editPortletURL.setParameter("redirect", currentURL);
		%>

		<aui:field-wrapper inlineLabel="left" label="asset-title">
			<aui:a href="<%= editPortletURL.toString() %>"><%= workflowHandler.getTitle(classPK) %></aui:a>
		</aui:field-wrapper>
	</c:if>

	<aui:field-wrapper inlineLabel="left" label="state">
		<%= WorkflowInstanceLinkLocalServiceUtil.getState(companyId, groupId, className, classPK) %>
	</aui:field-wrapper>

	<aui:field-wrapper inlineLabel="left" label="create-date">
		<%= dateFormatDateTime.format(workflowTask.getCreateDate()) %>
	</aui:field-wrapper>

	<aui:field-wrapper label="due-date">
		<span class="aui-field-element ">
			<liferay-ui:input-date
				monthParam="dueDateMonth"
				monthValue="<%= dueDate.get(Calendar.MONTH) %>"
				dayParam="dueDateDay"
				dayValue="<%= dueDate.get(Calendar.DATE) %>"
				yearParam="dueDateYear"
				yearValue="<%= dueDate.get(Calendar.YEAR) %>"
				yearRangeStart="<%= dueDate.get(Calendar.YEAR) - 100 %>"
				yearRangeEnd="<%= dueDate.get(Calendar.YEAR) + 100 %>"
				firstDayOfWeek="<%= dueDate.getFirstDayOfWeek() - 1 %>"
				disabled="<%= true %>"
			/>

			&nbsp;

			<liferay-ui:input-time
				hourParam="dueDateHour"
				hourValue="<%= dueDate.get(Calendar.HOUR) %>"
				minuteParam="dueDateMinute"
				minuteValue="<%= dueDate.get(Calendar.MINUTE) %>"
				minuteInterval="<%= 1 %>"
				amPmParam="dueDateAmPm"
				amPmValue="<%= dueDate.get(Calendar.AM_PM) %>"
				disabled="<%= true %>"
			/>
		</span>
	</aui:field-wrapper>

	<%
	String taglibDueDateOnClick = renderResponse.getNamespace() + "disableInputDate('dueDate', !this.checked);";
	%>

	<aui:input inlineLabel="right" label="change-due-date" name="changeDueDate" onClick="<%= taglibDueDateOnClick %>" type="checkbox" />

	<c:if test="<%= PortletPermissionUtil.contains(permissionChecker, PortletKeys.WORKFLOW_TASKS, ActionKeys.ASSIGN_USER_TASKS) %>">

		<%
		long[] pooledActorsIds = WorkflowTaskManagerUtil.getPooledActorsIds(company.getCompanyId(), workflowTask.getWorkflowTaskId());
		%>

		<c:if test="<%= (pooledActorsIds != null) && (pooledActorsIds.length > 0) && !workflowTask.isCompleted() %>">
			<aui:select inlineLabel="left" label="assigned-to" name="assigneeUserId" showEmptyOption="<%= true %>">

				<%
				for (long pooledActorId : pooledActorsIds) {
				%>

					<aui:option label="<%= HtmlUtil.escape(PortalUtil.getUserName(pooledActorId, StringPool.BLANK)) %>" selected="<%= workflowTask.getAssigneeUserId() == pooledActorId %>" value="<%= String.valueOf(pooledActorId) %>" />

				<%
				}

				String taglibOnClick = renderResponse.getNamespace() + "updateWorkflowTask('"+ Constants.ASSIGN +"');";
				%>

				<input name="assignButton" onClick="<%= taglibOnClick %>" type="button" value="<%= LanguageUtil.get(locale, "assign") %>" />
			</aui:select>
		</c:if>
	</c:if>

	<br />

	<liferay-ui:panel defaultState="closed" title='<%= LanguageUtil.get(pageContext, "activities") %>'>

		<%
		List<WorkflowLog> workflowLogs = WorkflowLogManagerUtil.getWorkflowLogs(company.getCompanyId(), workflowTask.getWorkflowTaskId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS, WorkflowComparatorFactoryUtil.getLogCreateDateComparator(true));

		for (WorkflowLog workflowLog : workflowLogs) {
			Role curRole = null;
			User curUser = null;
			String actorName = null;

			if (workflowLog.getRoleId() != 0) {
				curRole = RoleLocalServiceUtil.getRole(workflowLog.getRoleId());
				actorName = curRole.getDescriptiveName();
			}
			else if (workflowLog.getUserId() != 0) {
				curUser = UserLocalServiceUtil.getUser(workflowLog.getUserId());
				actorName = curUser.getFullName();
			}
		%>

			<div class="activity">
				<div class="date">
					<%= dateFormatDateTime.format(workflowLog.getCreateDate()) %>
				</div>

				<c:choose>
					<c:when test="<%= workflowLog.getType() == WorkflowLog.TASK_UPDATE %>">
						<div>
							<%= LanguageUtil.format(pageContext, "x-updated-the-due-date", HtmlUtil.escape(actorName)) %>
						</div>
					</c:when>
					<c:when test="<%= workflowLog.getType() == WorkflowLog.TRANSITION %>">
						<div>
							<%= LanguageUtil.format(pageContext, "x-changed-the-state-from-x-to-x", new Object[] {HtmlUtil.escape(actorName), workflowLog.getPreviousState(), workflowLog.getState()}) %>
						</div>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="<%= (workflowLog.getPreviousUserId() == 0) && (curUser != null) %>">
								<div>
									<%= LanguageUtil.format(pageContext, curUser.isMale() ? "x-assigned-the-task-to-himself" : "x-assigned-the-task-to-herself", HtmlUtil.escape(curUser.getFullName())) %>
								</div>
							</c:when>
							<c:otherwise>

								<%
								String previousActorName = null;

								if (curRole == null) {
									previousActorName = PortalUtil.getUserName(workflowLog.getPreviousUserId(), StringPool.BLANK);
								}
								else {
									previousActorName = curRole.getDescriptiveName();
								}
								%>

								<div>
									<%= LanguageUtil.format(pageContext, "x-assigned-the-task-to-x", new Object[] {HtmlUtil.escape(previousActorName), actorName}) %>
								</div>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>

				<div>
					<%= workflowLog.getComment() %>
				</div>
			</div>

		<%
		}
		%>

	</liferay-ui:panel>

	<br />

	<aui:button-row>
		<c:if test="<%= !workflowTask.isCompleted() && _isWorkflowTaskAssignedToUser(workflowTask, user) %>">

			<%
			List<String> transitionNames = WorkflowTaskManagerUtil.getNextTransitionNames(company.getCompanyId(), user.getUserId(), workflowTask.getWorkflowTaskId());

			for (String transitionName : transitionNames) {
				String message = "proceed";

				if (Validator.isNotNull(transitionName)) {
					message = transitionName;
				}

				String taglibOnClick = renderResponse.getNamespace() + "updateWorkflowTask('"+ Constants.SAVE +"', '" + UnicodeFormatter.toString(message) + "');";
			%>

				<aui:button name='<%= message + "Button" %>' onClick="<%= taglibOnClick %>" type="button" value="<%= message %>" />

			<%
			}
			%>

		</c:if>

		<%
		String taglibOnClick = renderResponse.getNamespace() + "updateWorkflowTask('"+ Constants.UPDATE +"');";
		%>

		<aui:button name="updateButton" onClick="<%= taglibOnClick %>" disabled="<%= true %>" type="button" value="update-due-date" />

		<aui:button onClick="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<br />

<h3 class="comments"><liferay-ui:message key="comments" /> </h3>

<portlet:actionURL var="discussionURL">
	<portlet:param name="struts_action" value="/workflow_tasks/edit_workflow_task_discussion" />
</portlet:actionURL>

<liferay-ui:discussion
	className="<%= WorkflowTask.class.getName() %>"
	classPK="<%= workflowTask.getWorkflowTaskId() %>"
	formAction="<%= discussionURL %>"
	formName="fm1"
	ratingsEnabled="<%= true %>"
	redirect="<%= currentURL %>"
	subject="<%= workflowTask.getName() %>"
	userId="<%= user.getUserId() %>"
/>

<aui:script>
	function <portlet:namespace />disableInputDate(date, disabled) {
		document.<portlet:namespace />fm["<portlet:namespace />" + date + "Month"].disabled = disabled;
		document.<portlet:namespace />fm["<portlet:namespace />" + date + "Day"].disabled = disabled;
		document.<portlet:namespace />fm["<portlet:namespace />" + date + "Year"].disabled = disabled;
		document.<portlet:namespace />fm["<portlet:namespace />" + date + "Hour"].disabled = disabled;
		document.<portlet:namespace />fm["<portlet:namespace />" + date + "Minute"].disabled = disabled;
		document.<portlet:namespace />fm["<portlet:namespace />" + date + "AmPm"].disabled = disabled;

		document.<portlet:namespace />fm["<portlet:namespace />updateButton"].disabled = disabled;
	}

	function <portlet:namespace />updateWorkflowTask(cmd, transitionName) {
		AUI().use(
			'aui-dialog',
			function(A) {
				var dialog = new A.Dialog(
					{
						bodyContent: '<textarea id="<%= renderResponse.getNamespace() + "comment" %>" rows="10" cols="55"></textarea>',
						buttons: [
							{
								handler: function() {
									document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = cmd;
									document.<portlet:namespace />fm.<portlet:namespace />transitionName.value = transitionName;
									document.<portlet:namespace />fm.<portlet:namespace />comment.value = A.one('#<%= renderResponse.getNamespace() + "comment" %>').val();
									submitForm(document.<portlet:namespace />fm);
								},
								text: '<liferay-ui:message key="ok" />'
							},
							{
								handler: function() {
									this.close();
								},
								text: '<liferay-ui:message key="cancel" />'
							}
						],
						centered: true,
						modal: true,
						title: '<liferay-ui:message key="comments" />',
						width: 400
					}
				).render();
			}
		);
	}
</aui:script>