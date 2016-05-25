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
long groupId = ParamUtil.getLong(request, "groupId", scopeGroupId);
long classPK = ParamUtil.getLong(request, "classPK");
String eventName = ParamUtil.getString(request, "eventName", "selectStructure");
%>

<liferay-portlet:renderURL varImpl="portletURL">
	<portlet:param name="mvcPath" value="/select_structure.jsp" />
	<portlet:param name="classPK" value="<%= String.valueOf(classPK) %>" />
	<portlet:param name="eventName" value="<%= eventName %>" />
</liferay-portlet:renderURL>

<%
SearchContainer structureSearch = new StructureSearch(renderRequest, portletURL, WorkflowConstants.STATUS_APPROVED);

request.setAttribute(WebKeys.SEARCH_CONTAINER, structureSearch);
%>

<liferay-util:include page="/structure_toolbar.jsp" servletContext="<%= application %>" />

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="selectStructureFm">
	<liferay-ui:search-container
		searchContainer="<%= structureSearch %>"
	>
		<liferay-ui:search-container-results>
			<%@ include file="/structure_search_results.jspf" %>
		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.dynamic.data.mapping.model.DDMStructure"
			keyProperty="structureId"
			modelVar="structure"
		>
			<liferay-ui:search-container-column-text
				cssClass="content-column name-column title-column"
				name="name"
				truncate="<%= true %>"
				value="<%= HtmlUtil.escape(structure.getName(locale)) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="content-column description-column"
				name="description"
				truncate="<%= true %>"
				value="<%= HtmlUtil.escape(structure.getDescription(locale)) %>"
			/>

			<liferay-ui:search-container-column-date
				cssClass="modified-date-column text-column"
				name="modified-date"
				value="<%= structure.getModifiedDate() %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="id-column text-column"
				name="id"
				value="<%= String.valueOf(structure.getStructureId()) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="entry-action-column"
			>
				<c:if test="<%= (structure.getStructureId() != classPK) && ((classPK == 0) || (structure.getParentStructureId() == 0) || (structure.getParentStructureId() != classPK)) %>">

					<%
					Map<String, Object> data = new HashMap<String, Object>();

					if (ddmDisplay.isShowConfirmSelectStructure()) {
						data.put("confirm-selection", Boolean.TRUE.toString());
						data.put("confirm-selection-message", ddmDisplay.getConfirmSelectStructureMessage(locale));
					}

					data.put("ddmstructureid", structure.getStructureId());
					data.put("ddmstructurekey", structure.getStructureKey());
					data.put("name", structure.getName(locale));
					%>

					<aui:button cssClass="selector-button" data="<%= data %>" value="choose" />
				</c:if>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<c:if test="<%= ddmDisplay.isShowAddStructureButton() && DDMStructurePermission.containsAddStruturePermission(permissionChecker, groupId, scopeClassNameId) %>">
	<portlet:renderURL var="viewStructureURL">
		<portlet:param name="mvcPath" value="/select_structure.jsp" />
		<portlet:param name="classPK" value="<%= String.valueOf(classPK) %>" />
		<portlet:param name="eventName" value="<%= eventName %>" />
	</portlet:renderURL>

	<portlet:renderURL var="addStructureURL">
		<portlet:param name="mvcPath" value="/edit_structure.jsp" />
		<portlet:param name="redirect" value="<%= viewStructureURL %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add") %>' url="<%= addStructureURL %>" />
	</liferay-frontend:add-menu>
</c:if>

<aui:script>
	Liferay.Util.focusFormField(document.<portlet:namespace />searchForm.<portlet:namespace />keywords);

	Liferay.Util.selectEntityHandler('#<portlet:namespace />selectStructureFm', '<%= HtmlUtil.escapeJS(eventName) %>');
</aui:script>