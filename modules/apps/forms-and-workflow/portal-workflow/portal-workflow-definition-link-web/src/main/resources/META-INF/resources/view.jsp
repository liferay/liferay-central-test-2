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

<%@ include file="/init.jsp" %>

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view.jsp");
portletURL.setParameter("tabs1", "default-configuration");

WorkflowDefinitionLinkSearch workflowDefinitionLinkSearch = new WorkflowDefinitionLinkSearch(renderRequest, portletURL);
%>

<liferay-util:include page="/search_bar.jsp" servletContext="<%= application %>" />

<liferay-frontend:management-bar>
	<liferay-frontend:management-bar-filters>
		<liferay-util:include page="/sort_buttons.jsp" servletContext="<%= application %>" />
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid-1280 workflow-definition-link-container" id="<portlet:namespace />Container">
	<liferay-ui:search-container
		id="searchContainer"
		searchContainer="<%= workflowDefinitionLinkSearch %>"
	>

		<liferay-ui:search-container-results
			results="<%= workflowDefinitionLinkDisplayContext.getSearchContainerResults(searchContainer) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.workflow.definition.link.web.search.WorkflowDefinitionLinkSearchEntry"
			modelVar="workflowDefinitionLinkSearchEntry"
		>

			<liferay-ui:search-container-row-parameter
				name="workflowDefinitionLinkSearchEntry"
				value="<%= workflowDefinitionLinkSearchEntry %>"
			/>

			<liferay-ui:search-container-column-text
				name="resource"
				value="<%= workflowDefinitionLinkSearchEntry.getResource() %>"
			/>

			<liferay-ui:search-container-column-text
				name="workflow"
				value="<%= workflowDefinitionLinkSearchEntry.getWorkflowDefinitionLabel() %>"
			/>

			<liferay-ui:search-container-column-jsp
				align="right"
				cssClass="entry-action"
				path="/workflow_definition_link_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="list" markupView="lexicon" />
	</liferay-ui:search-container>
</div>