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
Folder folder = (Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long folderId = ParamUtil.getLong(request, "folderId", DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

if ((folder == null) && (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {
	try {
		folder = DLAppLocalServiceUtil.getFolder(folderId);
	}
	catch (NoSuchFolderException nsfe) {
		folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}
}

long repositoryId = scopeGroupId;

if (folder != null) {
	repositoryId = folder.getRepositoryId();
}

boolean viewFolders = ParamUtil.getBoolean(request, "viewFolders");
boolean viewEntries = ParamUtil.getBoolean(request, "viewEntries");
boolean viewAddButton = ParamUtil.getBoolean(request, "viewAddButton");
boolean viewDisplayStyleButtons = ParamUtil.getBoolean(request, "viewDisplayStyleButtons");

request.setAttribute("view.jsp-folder", folder);

request.setAttribute("view.jsp-folderId", String.valueOf(folderId));

request.setAttribute("view.jsp-repositoryId", String.valueOf(repositoryId));
%>

<c:if test="<%= viewFolders %>">
	<div id="<portlet:namespace />folders">
		<liferay-util:include page="/html/portlet/document_library/view_folders.jsp" />
	</div>
</c:if>

<c:if test="<%= viewEntries %>">
	<div id="<portlet:namespace />entries">
		<liferay-util:include page="/html/portlet/document_library/view_entries.jsp" />
	</div>
</c:if>

<c:if test="<%= viewAddButton %>">
	<span id="<portlet:namespace />addButton">
		<liferay-util:include page="/html/portlet/document_library/add_button.jsp" />
	</span>
</c:if>

<c:if test="<%= viewDisplayStyleButtons %>">
	<span id="<portlet:namespace />displayStyleButtons">
		<liferay-util:include page="/html/portlet/document_library/display_style_buttons_resources.jsp" />
	</span>
</c:if>