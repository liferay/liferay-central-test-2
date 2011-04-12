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

<%@ include file="/html/portlet/dynamic_data_lists/init.jsp" %>

<%
SearchContainer searchContainer = (SearchContainer)request.getAttribute("liferay-ui:search:searchContainer");

String redirect = searchContainer.getIteratorURL().toString();

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DDLEntry entry = (DDLEntry)row.getObject();
%>

<liferay-ui:icon-menu>
	<c:if test="<%= DDLEntryPermission.contains(permissionChecker, entry, ActionKeys.VIEW) %>">
		<portlet:renderURL var="viewEntryURL">
			<portlet:param name="struts_action" value="/dynamic_data_lists/view_entry" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.VIEW %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			image="view"
			url="<%= viewEntryURL %>"
		/>
	</c:if>

	<c:if test="<%= DDLEntryPermission.contains(permissionChecker, entry, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editEntryURL">
			<portlet:param name="struts_action" value="/dynamic_data_lists/edit_entry" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UPDATE %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			image="edit"
			url="<%= editEntryURL %>"
		/>
	</c:if>

	<c:if test="<%= DDLEntryPermission.contains(permissionChecker, entry, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= DDLEntry.class.getName() %>"
			modelResourceDescription="<%= entry.getName() %>"
			resourcePrimKey="<%= String.valueOf(entry.getEntryId()) %>"
			var="permissionsEntryURL"
		/>

		<liferay-ui:icon
			image="permissions"
			url="<%= permissionsEntryURL %>"
		/>
	</c:if>

	<c:if test="<%= DDLEntryPermission.contains(permissionChecker, entry, ActionKeys.DELETE) %>">
		<portlet:actionURL var="deleteEntryURL">
			<portlet:param name="struts_action" value="/dynamic_data_lists/edit_entry" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete url="<%= deleteEntryURL %>" />
	</c:if>
</liferay-ui:icon-menu>