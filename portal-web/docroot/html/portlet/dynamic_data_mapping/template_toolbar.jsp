<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/dynamic_data_mapping/init.jsp" %>

<%
String backURL = ParamUtil.getString(request, "backURL");

String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-all");

String structureKey = ParamUtil.getString(request, "structureKey");
%>

<div class="lfr-portlet-toolbar">
	<portlet:renderURL var="viewEntriesURL">
		<portlet:param name="struts_action" value="/dynamic_data_mapping/view_template" />
		<portlet:param name="backURL" value="<%= backURL %>" />
		<portlet:param name="structureKey" value="<%= structureKey %>" />
	</portlet:renderURL>

	<span class="lfr-toolbar-button view-button <%= toolbarItem.equals("view-all") ? "current" : StringPool.BLANK %>">
		<a href="<%= viewEntriesURL %>"><liferay-ui:message key="view-all" /></a>
	</span>

	<c:if test="<%= DDMPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_STRUCTURE) %>">
		<portlet:renderURL var="addEntryURL">
			<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_template" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="backURL" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
			<portlet:param name="structureKey" value="<%= structureKey %>" />
			<portlet:param name="structureAvailableFields" value='<%= renderResponse.getNamespace() + "structureAvailableFields" %>' />
		</portlet:renderURL>

		<span class="lfr-toolbar-button add-template <%= toolbarItem.equals("add-detail-template") ? "current" : StringPool.BLANK %>"><a href="<%= addEntryURL %>"><liferay-ui:message key="add-detail-template" /></a></span>
	</c:if>

	<c:if test="<%= DDMPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_STRUCTURE) %>">
		<portlet:renderURL var="addEntryURL">
			<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_template" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="backURL" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
			<portlet:param name="type" value="list" />
			<portlet:param name="structureKey" value="<%= structureKey %>" />
		</portlet:renderURL>

		<span class="lfr-toolbar-button view-templates <%= toolbarItem.equals("add-list-template") ? "current" : StringPool.BLANK %>"><a href="<%= addEntryURL %>"><liferay-ui:message key="add-list-template" /></a></span>
	</c:if>
</div>