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
String redirect = ParamUtil.getString(request, "redirect");

WorkflowTask workflowTask = (WorkflowTask)request.getAttribute(WebKeys.WORKFLOW_TASK);

WorkflowInstance workflowInstance = WorkflowInstanceManagerUtil.getWorkflowInstance(workflowTask.getWorkflowInstanceId());

Map<String, Object> workflowInstanceContext = workflowInstance.getContext();

long companyId = (Long)workflowInstanceContext.get(ContextConstants.COMPANY_ID);
long groupId = (Long)workflowInstanceContext.get(ContextConstants.GROUP_ID);
String className = (String)workflowInstanceContext.get(ContextConstants.ENTRY_CLASS_NAME);
long classPK = (Long)workflowInstanceContext.get(ContextConstants.ENTRY_CLASS_PK);
%>

<aui:script>
	function <portlet:namespace />updateWorkflowTask(cmd, transitionName) {
		AUI().use(
			'dialog',
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
		<%= PortalUtil.getUserName(workflowTask.getAssigneeUserId(), StringPool.BLANK) %>
	</aui:field-wrapper>

	<%
	WorkflowHandler workflowHandler = WorkflowHandlerRegistryUtil.getWorkflowHandler(className);

	PortletURL editPortletURL =  workflowHandler.getURLEdit(classPK, (LiferayPortletRequest)renderRequest, (LiferayPortletResponse)renderResponse);
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

	<aui:field-wrapper inlineLabel="left" label="due-date">
		<%= (workflowTask.getDueDate() == null) ? LanguageUtil.get(pageContext, "never") : dateFormatDateTime.format(workflowTask.getDueDate()) %>
	</aui:field-wrapper>

	<c:if test="<%= PortletPermissionUtil.contains(permissionChecker, PortletKeys.WORKFLOW_TASKS, ActionKeys.ASSIGN_USER_TASKS) %>">

		<%
		long[] pooledActorsIds = WorkflowTaskManagerUtil.getPooledActorsIds(workflowTask.getWorkflowTaskId());
		%>

		<c:if test="<%= (pooledActorsIds != null) && (pooledActorsIds.length > 0) && !workflowTask.isCompleted() %>">
			<aui:select inlineLabel="left" label="assigned-to" name="assigneeUserId" showEmptyOption="<%= true %>">

				<%
				for (long pooledActorId : pooledActorsIds) {
				%>

					<aui:option label="<%= PortalUtil.getUserName(pooledActorId, StringPool.BLANK) %>" selected="<%= workflowTask.getAssigneeUserId() == pooledActorId %>" value="<%= String.valueOf(pooledActorId) %>" />

				<%
				}

				String taglibOnClick = renderResponse.getNamespace() + "updateWorkflowTask('"+ Constants.ASSIGN +"');";
				%>

				<input name="assignButton" onClick="<%= taglibOnClick %>" type="button" value="<%= LanguageUtil.get(locale, "assign") %>" />
			</aui:select>
		</c:if>
	</c:if>

	<br />

	<liferay-ui:panel defaultState="closed" id='<%= renderResponse.getNamespace() + "activitiesPanel" %>'  title='<%= LanguageUtil.get(pageContext, "activities") %>'>

		<%
		List<WorkflowLog> workflowLogs =  WorkflowLogManagerUtil.getWorkflowLogs(workflowTask.getWorkflowTaskId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS, new WorkflowLogCreateDateComparator(true));

		for (WorkflowLog workflowLog : workflowLogs) {
			User curUser = UserLocalServiceUtil.getUser(workflowLog.getUserId());
		%>

			<div class="activity">
				<div class="date">
					<%= dateFormatDateTime.format(workflowLog.getCreateDate()) %>
				</div>

				<c:choose>
					<c:when test="<%= workflowLog.getType() == WorkflowLog.TRANSITION %>">
						<div>
							<%= LanguageUtil.format(pageContext, "x-changed-the-state-from-x-to-x", new Object[] {curUser.getFullName(), workflowLog.getPreviousState(), workflowLog.getState()}) %>
						</div>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="<%= workflowLog.getPreviousUserId() == 0 %>">
								<div>
									<%= LanguageUtil.format(pageContext, curUser.isMale() ? "x-assigned-the-task-to-himself" : "x-assigned-the-task-to-herself", curUser.getFullName()) %>
								</div>
							</c:when>
							<c:otherwise>
								<div>
									<%= LanguageUtil.format(pageContext, "x-assigned-the-task-to-x", new Object[] {PortalUtil.getUserName(workflowLog.getPreviousUserId(), StringPool.BLANK), curUser.getFullName()}) %>
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
		<c:if test="<%= !workflowTask.isCompleted() && (workflowTask.getAssigneeUserId() == user.getUserId()) %>">

			<%
			List<String> transitionNames = WorkflowTaskManagerUtil.getNextTransitionNames(user.getUserId(), workflowTask.getWorkflowTaskId());

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

		<aui:button onClick="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<br />

<h3 class="comments"><liferay-ui:message key="comments" /> </h3>

<portlet:actionURL var="discussionURL">
	<portlet:param name="struts_action" value="/workflow_tasks/edit_workflow_task_discussion" />
</portlet:actionURL>

<liferay-ui:discussion
	formName="fm1"
	formAction="<%= discussionURL %>"
	className="<%= WorkflowTask.class.getName() %>"
	classPK="<%= workflowTask.getWorkflowTaskId() %>"
	userId="<%= user.getUserId() %>"
	subject="<%= workflowTask.getName() %>"
	redirect="<%= currentURL %>"
	ratingsEnabled="<%= true %>"
/>