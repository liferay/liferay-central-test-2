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
long groupId = ParamUtil.getLong(request, "groupId");
long liveGroupId = ParamUtil.getLong(request, "liveGroupId");
boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");
String rootNodeName = ParamUtil.getString(request, "rootNodeName");
%>

<liferay-portlet:renderURL varImpl="portletURL">
	<portlet:param name="mvcRenderCommandName" value="exportLayouts" />
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EXPORT %>" />
	<portlet:param name="tabs2" value="new-export-process" />
	<portlet:param name="exportConfigurationButtons" value="saved" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<portlet:param name="liveGroupId" value="<%= String.valueOf(liveGroupId) %>" />
	<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
	<portlet:param name="rootNodeName" value="<%= rootNodeName %>" />
</liferay-portlet:renderURL>

<aui:form action="<%= portletURL %>">
	<liferay-ui:search-container
		displayTerms="<%= new ExportImportConfigurationDisplayTerms(renderRequest) %>"
		emptyResultsMessage="there-are-no-saved-export-templates"
		iteratorURL="<%= portletURL %>"
		orderByCol="name"
		orderByType="asc"
		searchTerms="<%= new ExportImportConfigurationSearchTerms(renderRequest) %>"
	>
		<aui:nav-bar>
			<aui:nav cssClass="navbar-nav" searchContainer="<%= searchContainer %>">
				<portlet:renderURL var="addExportConfigurationURL">
					<portlet:param name="mvcRenderCommandName" value="exportLayouts" />
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
					<portlet:param name="liveGroupId" value="<%= String.valueOf(liveGroupId) %>" />
					<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
					<portlet:param name="rootNodeName" value="<%= rootNodeName %>" />
				</portlet:renderURL>

				<aui:nav-item href="<%= addExportConfigurationURL %>" iconCssClass="icon-plus" label="new" />
			</aui:nav>

			<aui:nav-bar-search searchContainer="<%= searchContainer %>">

				<%
				request.setAttribute("liferay-ui:search:searchContainer", searchContainer);
				%>

				<liferay-util:include page="/export_import_configuration_search.jsp" servletContext="<%= application %>" />
			</aui:nav-bar-search>
		</aui:nav-bar>

		<liferay-ui:search-container-results>

			<%
			int exportImportConfigurationType = ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT;
			%>

			<%@ include file="/export_import_configuration_search_results.jspf" %>
		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.portlet.exportimport.model.ExportImportConfiguration"
			keyProperty="exportImportConfigurationId"
			modelVar="exportImportConfiguration"
		>
			<liferay-ui:search-container-column-text
				cssClass="export-configuration-user-column"
				name="user"
			>
				<liferay-ui:user-display
					displayStyle="3"
					showUserDetails="<%= false %>"
					showUserName="<%= false %>"
					userId="<%= exportImportConfiguration.getUserId() %>"
				/>
			</liferay-ui:search-container-column-text>

			<liferay-portlet:renderURL varImpl="rowURL">
				<portlet:param name="mvcRenderCommandName" value="exportLayouts" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UPDATE %>" />
				<portlet:param name="redirect" value="<%= searchContainer.getIteratorURL().toString() %>" />
				<portlet:param name="exportImportConfigurationId" value="<%= String.valueOf(exportImportConfiguration.getExportImportConfigurationId()) %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
				<portlet:param name="liveGroupId" value="<%= String.valueOf(liveGroupId) %>" />
				<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
				<portlet:param name="rootNodeName" value="<%= rootNodeName %>" />
			</liferay-portlet:renderURL>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="name"
				value="<%= HtmlUtil.escape(exportImportConfiguration.getName()) %>"
			/>

			<liferay-ui:search-container-column-text
				name="description"
				value="<%= HtmlUtil.escape(exportImportConfiguration.getDescription()) %>"
			/>

			<liferay-ui:search-container-column-date
				name="create-date"
				value="<%= exportImportConfiguration.getCreateDate() %>"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action"
				path="/export_configuration_actions.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>