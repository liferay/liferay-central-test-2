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

<%@ include file="/html/portlet/workflow_admin/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "workflow-definitions");

String redirect = ParamUtil.getString(request, "redirect");

WorkflowDefinition workflowDefinition = (WorkflowDefinition)request.getAttribute(WebKeys.WORKFLOW_DEFINITION);

String name = StringPool.BLANK;
String version = StringPool.BLANK;

if (workflowDefinition != null) {
	name = workflowDefinition.getName();
	version = String.valueOf(workflowDefinition.getVersion());
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/workflow_admin/view");
portletURL.setParameter("tabs1", tabs1);
%>

<liferay-ui:tabs
	names="resources,workflow-definitions"
	url="<%= portletURL.toString() %>"
/>

<liferay-util:include page="/html/portlet/workflow_admin/toolbar.jsp">
	<liferay-util:param name="toolbarItem" value="add" />
</liferay-util:include>

<portlet:actionURL var="editWorkflowDefinitionURL">
	<portlet:param name="struts_action" value="/workflow_admin/edit_workflow_definition" />
	<portlet:param name="redirect" value="<%= redirect %>" />
</portlet:actionURL>

<aui:form action="<%= editWorkflowDefinitionURL %>" enctype="multipart/form-data" method="post">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= workflowDefinition == null ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="tabs1" type="hidden" value="workflow-definitions" />

	<aui:fieldset>
		<aui:input name="name" value="<%= name %>" />

		<aui:input name="file" type="file" />

		<aui:button-row>
			<aui:button type="submit" />

			<aui:button onClick="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>