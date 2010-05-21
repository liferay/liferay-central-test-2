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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

int numVersions = GetterUtil.getInteger((String)request.getAttribute("numVersions"));

Object[] objArray = (Object[])row.getObject();

DLFileEntry fileEntry = (DLFileEntry)objArray[0];
DLFileVersion fileVersion = (DLFileVersion)objArray[1];
String[] conversions = (String[])objArray[2];
Boolean isLocked = (Boolean)objArray[3];
Boolean hasLock = (Boolean)objArray[4];
%>

<liferay-ui:icon-menu>
	<c:if test="<%= showDeleteFileEntryButton && DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.DELETE) && (!isLocked.booleanValue() || hasLock.booleanValue()) %>">
		<portlet:renderURL var="parentFolderURL">
			<portlet:param name="struts_action" value="/document_library/view" />
			<portlet:param name="folderId" value="<%= String.valueOf(fileVersion.getFolderId()) %>" />
		</portlet:renderURL>

		<portlet:actionURL var="portletURL">
			<portlet:param name="struts_action" value="/document_library/edit_file_entry" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= (numVersions == 1) ? parentFolderURL : currentURL %>" />
			<portlet:param name="folderId" value="<%= String.valueOf(fileVersion.getFolderId()) %>" />
			<portlet:param name="name" value="<%= fileVersion.getName() %>" />
			<portlet:param name="version" value="<%= String.valueOf(fileVersion.getVersion()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete url="<%= portletURL %>" />
	</c:if>
</liferay-ui:icon-menu>