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

<script type="text/javascript">
	function <portlet:namespace />assignTask() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "assign_task";

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />saveTask(transitionName) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.SAVE %>";

		if (transitionName) {
			document.<portlet:namespace />fm.<portlet:namespace />transitionName.value = transitionName;
		}

		submitForm(document.<portlet:namespace />fm);
	}
</script>

<liferay-ui:tabs
	names="workflow-task"
/>

<portlet:actionURL var="editWorkflowTaskURL">
	<portlet:param name="struts_action" value="/workflow_tasks/edit_workflow_task" />
</portlet:actionURL>

<aui:form action="<%= editWorkflowTaskURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="workflowTaskId" type="hidden" value="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>" />
	<aui:input name="transitionName" type="hidden" />

	<aui:fieldset>
		<aui:field-wrapper inlineLabel="left" label="name">
			<%= workflowTask.getName() %>
		</aui:field-wrapper>

		<aui:field-wrapper label="description">
			<%= (workflowTask.getDescription() == null) ? StringPool.BLANK : workflowTask.getDescription() %>
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

		<%
		long[] pooledActorsIds = WorkflowTaskManagerUtil.getPooledActorsIds(workflowTask.getWorkflowTaskId());
		%>

		<c:if test="<%= pooledActorsIds != null %>">
			<aui:select inlineLabel="left" label="owner" name="assigneeUserId">
				<%
				for (long pooledActorId : pooledActorsIds) {
				%>

					<aui:option label="<%= PortalUtil.getUserName(pooledActorId, StringPool.BLANK) %>" selected="<%= workflowTask.getAssigneeUserId() == pooledActorId %>" value="<%= String.valueOf(pooledActorId) %>" />

				<%
				}
				%>

				<aui:button name="assignButton" onClick='<%= renderResponse.getNamespace() + "assignTask();" %>' type="button" value="assign" />
			</aui:select>
		</c:if>

		<br />

		<aui:button-row>

			<%
			List<String> transitionNames = WorkflowTaskManagerUtil.getNextTransitionNames(user.getUserId(), workflowTask.getWorkflowTaskId());

			for (String transitionName : transitionNames) {
				String message = "proceed";

				if (Validator.isNotNull(transitionName)) {
					message = transitionName;
				}

				String taglibOnClick = renderResponse.getNamespace() + "saveTask('" + message + "');";
			%>

				<aui:button name='<%= message + "Button" %>' onClick="<%= taglibOnClick %>" type="button" value="<%= message %>" />

			<%
			}
			%>

			<aui:button name="cancelButton" onClick="<%= redirect %>" type="button" value="cancel" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>