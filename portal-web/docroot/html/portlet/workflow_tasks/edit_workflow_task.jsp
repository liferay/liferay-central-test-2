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

long workflowTaskId = BeanParamUtil.getLong(workflowTask, request, "workflowTaskId");
long assigneeUserId = workflowTask.getAssigneeUserId();
String description = BeanPropertiesUtil.getString(workflowTask.getDescription(), StringPool.BLANK);
Date createDate = workflowTask.getCreateDate();
Date dueDate = workflowTask.getDueDate();

WorkflowInstance workflowInstance = WorkflowInstanceManagerUtil.getWorkflowInstance(workflowTask.getWorkflowInstanceId());

Map<String, Object> workflowInstanceContext = workflowInstance.getContext();

String className = (String)workflowInstanceContext.get(ContextConstants.ENTRY_CLASS_NAME);
long classPK = (Long)workflowInstanceContext.get(ContextConstants.ENTRY_CLASS_PK);
long companyId = (Long)workflowInstanceContext.get(ContextConstants.COMPANY_ID);
long groupId = (Long)workflowInstanceContext.get(ContextConstants.GROUP_ID);

String state = WorkflowInstanceLinkLocalServiceUtil.getState(companyId, groupId, className, classPK);

WorkflowHandler workflowHandler = WorkflowHandlerRegistryUtil.getWorkflowHandler(className);

PortletURL assetURLEdit =  workflowHandler.getURLEdit(classPK, (LiferayPortletRequest)renderRequest, (LiferayPortletResponse)renderResponse);

if (assetURLEdit != null) {
	assetURLEdit.setWindowState(WindowState.MAXIMIZED);
	assetURLEdit.setPortletMode(PortletMode.VIEW);

	assetURLEdit.setParameter("redirect", currentURL);
}

List<String> transitionNames = WorkflowTaskManagerUtil.getNextTransitionNames(user.getUserId(), workflowTask.getWorkflowTaskId());
%>

<script type="text/javascript">
	function <portlet:namespace />saveTask(transitionName) {
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
	<aui:input name="workflowTaskId" type="hidden" value="<%= String.valueOf(workflowTaskId) %>" />
	<aui:input name="assigneeUserId" type="hidden" value="<%= String.valueOf(assigneeUserId) %>" />
	<aui:input name="transitionName" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<aui:fieldset>
		<aui:field-wrapper label="name" inlineLabel="true">
			<%= workflowTask.getName() %>
		</aui:field-wrapper>

		<aui:field-wrapper label="description">
			<%= description %>
		</aui:field-wrapper>

		<aui:field-wrapper label="asset-title" inlineLabel="true">
			<aui:a href="<%= assetURLEdit.toString() %>"><%= workflowHandler.getTitle(classPK) %></aui:a>
		</aui:field-wrapper>

		<aui:field-wrapper label="state" inlineLabel="true">
			<%= state %>
		</aui:field-wrapper>

		<aui:field-wrapper label="create-date" inlineLabel="true">
			<%= dateFormatDateTime.format(createDate) %>
		</aui:field-wrapper>

		<aui:field-wrapper label="due-date" inlineLabel="true">
			<%= (dueDate == null) ? LanguageUtil.get(pageContext, "never") : dateFormatDateTime.format(dueDate) %>
		</aui:field-wrapper>

		<br />

		<aui:button-row>
			<% for (String transitionName : transitionNames) {
					String message = "proceed";

					if (Validator.isNotNull(transitionName)) {
						message = transitionName;
					}

					String clickHandler = renderResponse.getNamespace() + "saveTask('"+ message +"')";
			%>
					<aui:button name='<%= message + \"Button\" %>' onClick="<%= clickHandler %>" type="button" value="<%= message %>" />
			<% } %>

			<aui:button name="cancelButton" onClick="<%= redirect %>" type="button" value="cancel" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>