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

<%@ include file="/admin/init.jsp" %>

<%
DDLRecordSet recordSet = ddlFormAdminDisplayContext.getRecordSet();

long recordSetId = BeanParamUtil.getLong(recordSet, request, "recordSetId");
long groupId = BeanParamUtil.getLong(recordSet, request, "groupId", scopeGroupId);
%>

<portlet:actionURL name="updateRecordSetSettings" var="updateRecordSetSettingsURL">
	<portlet:param name="mvcPath" value="/admin/record_set_settings.jsp" />
</portlet:actionURL>

<div class="container-fluid-1280">
	<aui:form action="<%= updateRecordSetSettingsURL %>" method="post" name="fm">
		<aui:input name="recordSetId" type="hidden" value="<%= recordSetId %>" />
		<aui:input name="groupId" type="hidden" value="<%= groupId %>" />

		<c:if test="<%= ddlFormAdminDisplayContext.isDDLRecordWorkflowHandlerDeployed() %>">
			<aui:select label="workflow" name="workflowDefinition">

				<%
				WorkflowDefinitionLink workflowDefinitionLink = null;

				try {
					workflowDefinitionLink = WorkflowDefinitionLinkLocalServiceUtil.getWorkflowDefinitionLink(company.getCompanyId(), themeDisplay.getScopeGroupId(), DDLRecordSet.class.getName(), recordSetId, 0, true);
				}
				catch (NoSuchWorkflowDefinitionLinkException nswdle) {
				}
				%>

				<aui:option><%= LanguageUtil.get(request, "no-workflow") %></aui:option>

				<%
				List<WorkflowDefinition> workflowDefinitions = WorkflowDefinitionManagerUtil.getActiveWorkflowDefinitions(company.getCompanyId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

				for (WorkflowDefinition workflowDefinition : workflowDefinitions) {
					boolean selected = false;

					if ((workflowDefinitionLink != null) && workflowDefinitionLink.getWorkflowDefinitionName().equals(workflowDefinition.getName()) && (workflowDefinitionLink.getWorkflowDefinitionVersion() == workflowDefinition.getVersion())) {
						selected = true;
					}
				%>

					<aui:option label='<%= HtmlUtil.escape(workflowDefinition.getName()) + " (" + LanguageUtil.format(locale, "version-x", workflowDefinition.getVersion(), false) + ")" %>' selected="<%= selected %>" useModelValue="<%= false %>" value="<%= HtmlUtil.escapeAttribute(workflowDefinition.getName()) + StringPool.AT + workflowDefinition.getVersion() %>" />

				<%
				}
				%>

			</aui:select>
		</c:if>

		<aui:button-row cssClass="ddl-form-builder-buttons">
			<aui:button cssClass="btn-lg" id="submit" label="save" primary="<%= true %>" type="submit" />
		</aui:button-row>
	</aui:form>
</div>