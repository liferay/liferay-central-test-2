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

<%@ include file="/html/portlet/library_admin/init.jsp" %>

<%
long defaultFolderId = GetterUtil.getLong(preferences.getValue("rootFolderId", StringPool.BLANK), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

Folder folder = (Folder)request.getAttribute("view.jsp-folder");

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

long repositoryId = GetterUtil.getLong((String)request.getAttribute("view.jsp-repositoryId"));

long parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

Folder parentFolder = null;

boolean showSiblings = ParamUtil.getBoolean(request, "showSiblings");

if (showSiblings) {
	if (folder != null) {
		parentFolderId = folder.getParentFolderId();

		try {
			parentFolder = DLAppLocalServiceUtil.getFolder(folderId);
		}
		catch (NoSuchFolderException nsfe) {
			parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		}

		repositoryId = parentFolder.getRepositoryId();
	}
}
else {
	parentFolderId = folderId;
}

int start = ParamUtil.getInteger(request, "start");
int end = ParamUtil.getInteger(request, "end", SearchContainer.DEFAULT_DELTA);

String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");

List<Folder> folders = DLAppServiceUtil.getFolders(repositoryId, parentFolderId, start, end, DLUtil.getEntryOrderByComparator(orderByCol, orderByType));
%>

<ul>
	<%
	for (Folder curFolder : folders) {

		int childrenFolderCount = DLAppServiceUtil.getFoldersCount(repositoryId, curFolder.getFolderId());
	%>

	<liferay-portlet:renderURL varImpl="viewURL">
		<portlet:param name="struts_action" value="/library_admin/view" />
		<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
	</liferay-portlet:renderURL>

	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" varImpl="viewFoldersURL">
		<portlet:param name="struts_action" value="/library_admin/view" />
		<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
		<portlet:param name="viewFolders" value="<%= Boolean.TRUE.toString() %>" />
	</liferay-portlet:resourceURL>

	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" varImpl="viewEntriesURL">
		<portlet:param name="struts_action" value="/library_admin/view" />
		<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
		<portlet:param name="showSiblings" value="<%= Boolean.TRUE.toString() %>" />
		<portlet:param name="viewAddButton" value="<%= Boolean.TRUE.toString() %>" />
		<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
	</liferay-portlet:resourceURL>

		<li class="folder <%= (curFolder.getFolderId() == folderId) ? "selected" : StringPool.BLANK %>">
			<c:if test="<%= (childrenFolderCount > 0) %>">
				<a href="<%= viewURL.toString() %>" data-expand="<%= Boolean.TRUE.toString() %>" data-resource-url="<%= viewFoldersURL.toString() %>" class="expand-folder">
					<liferay-ui:icon cssClass="expand-folder-arrow" image="../aui/carat-1-r" />
				</a>
			</c:if>

			<a href="<%= viewURL.toString() %>" data-resource-url="<%= viewEntriesURL.toString() %>">
				<liferay-ui:icon image="folder" />

				<%= curFolder.getName() %>
			</a>
		</li>

	<%
	}
	%>

</ul>