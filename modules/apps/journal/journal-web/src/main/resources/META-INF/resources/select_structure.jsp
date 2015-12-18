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
String eventName = ParamUtil.getString(request, "eventName", renderResponse.getNamespace() + "selectStructure");

String ddmStructureKey = ParamUtil.getString(request, "ddmStructureKey");

long[] groupIds = PortalUtil.getCurrentAndAncestorSiteGroupIds(scopeGroupId);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/select_structure.jsp");
portletURL.setParameter("eventName", eventName);
%>

<aui:nav-bar markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item label="structures" selected="<%= true %>" />
	</aui:nav>
</aui:nav-bar>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="selectStructureFm">
	<liferay-ui:search-container
		iteratorURL="<%= portletURL %>"
	>
		<liferay-ui:search-container-results
			results="<%= DDMStructureServiceUtil.getStructures(company.getCompanyId(), groupIds, PortalUtil.getClassNameId(JournalArticle.class), WorkflowConstants.STATUS_APPROVED) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.dynamic.data.mapping.model.DDMStructure"
			cssClass="select-action"
			modelVar="ddmStructure"
		>

			<%
			if (ddmStructureKey.equals(ddmStructure.getStructureKey())) {
				row.setCssClass("select-action active");
			}

			Map<String, Object> rowData = new HashMap<String, Object>();

			rowData.put("ddmStructureKey", ddmStructure.getStructureKey());

			row.setData(rowData);
			%>

			<liferay-ui:search-container-column-icon
				icon="edit-layout"
			/>

			<liferay-ui:search-container-column-text
				colspan="<%= 2 %>"
			>
				<h5><%= HtmlUtil.escape(ddmStructure.getName(locale)) %></h5>

				<h6 class="text-default">
					<span><%= ddmStructure.getDescription(locale) %></span>
				</h6>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="descriptive" markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<aui:script use="aui-base">
	var form = A.one('#<portlet:namespace />selectStructureFm');

	form.delegate(
		'click',
		function(event) {
			event.preventDefault();

			var currentTarget = event.currentTarget;

			A.all('.select-action').removeClass('active');

			currentTarget.addClass('active');

			Liferay.Util.getOpener().Liferay.fire(
				'<%= HtmlUtil.escapeJS(eventName) %>',
				{
					data: currentTarget.attr('data-ddmStructureKey')
				}
			);
		},
		'.select-action'
	);
</aui:script>