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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
Folder folder = (Folder)request.getAttribute("view.jsp-folder");

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

long repositoryId = GetterUtil.getLong((String)request.getAttribute("view.jsp-repositoryId"));
%>

<liferay-ui:icon-menu align="left" icon="" direction="down" message="add" showExpanded="<%= false %>" showWhenSingleIcon="<%= false %>">
	<c:if test="<%= DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_FOLDER) %>">
		<portlet:renderURL var="addFolderURL">
			<portlet:param name="struts_action" value="/document_library/edit_folder" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
			<portlet:param name="parentFolderId" value="<%= String.valueOf(folderId) %>" />
		</portlet:renderURL>

		<liferay-ui:icon image="folder" message='<%= (folder != null) ? "subfolder" : "folder" %>' url="<%= addFolderURL %>" />
	</c:if>

	<c:if test="<%= DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_DOCUMENT) %>">
		<portlet:renderURL var="editFileEntryURL">
			<portlet:param name="struts_action" value="/document_library/edit_file_entry" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="backURL" value="<%= currentURL %>" />
			<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
			<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
		</portlet:renderURL>

		<liferay-ui:icon image="../document_library/add_document" message="document" url="<%= editFileEntryURL %>" />
	</c:if>

	<c:if test="<%= DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_SHORTCUT) %>">
		<portlet:renderURL var="editFileShortcutURL">
			<portlet:param name="struts_action" value="/document_library/edit_file_shortcut" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
			<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
		</portlet:renderURL>

		<liferay-ui:icon image="add_instance" message="shortcut" url="<%= editFileShortcutURL %>" />
	</c:if>

	<c:if test="<%= DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_FOLDER) %>">
		<portlet:renderURL var="addRepositoryURL">
			<portlet:param name="struts_action" value="/document_library/edit_repository" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
		</portlet:renderURL>

		<liferay-ui:icon image="add_drive" message="repository" url="<%= addRepositoryURL %>" />
	</c:if>

	<portlet:renderURL var="addDocumentTypeURL">
		<portlet:param name="struts_action" value="/document_library/edit_document_type" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</portlet:renderURL>

	<liferay-ui:icon image="copy" message="document-type" url="<%= addDocumentTypeURL %>" />

	<%
	List<DLDocumentType> documentTypes = DLDocumentTypeServiceUtil.getDocumentTypes(scopeGroupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

	for (DLDocumentType documentType : documentTypes) {
	%>

		<portlet:renderURL var="addDocumentTypeURL">
			<portlet:param name="struts_action" value="/document_library/edit_file_entry" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
			<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
			<portlet:param name="documentTypeId" value="<%= String.valueOf(documentType.getDocumentTypeId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon image="copy" message="<%= documentType.getName() %>" url="<%= addDocumentTypeURL %>" />

	<%
	}
	%>

</liferay-ui:icon-menu>