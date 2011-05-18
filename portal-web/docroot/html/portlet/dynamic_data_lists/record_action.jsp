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

DDLRecord record = (DDLRecord)row.getObject();
%>

<liferay-ui:icon-menu>
	<portlet:renderURL var="editRecordURL">
		<portlet:param name="struts_action" value="/dynamic_data_lists/edit_record" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UPDATE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="recordId" value="<%= String.valueOf(record.getRecordId()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		image="edit"
		url="<%= editRecordURL %>"
	/>

	<portlet:actionURL var="deleteRecordURL">
		<portlet:param name="struts_action" value="/dynamic_data_mapping_list/edit_record" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="recordId" value="<%= String.valueOf(record.getRecordId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete url="<%= deleteRecordURL %>" />
</liferay-ui:icon-menu>