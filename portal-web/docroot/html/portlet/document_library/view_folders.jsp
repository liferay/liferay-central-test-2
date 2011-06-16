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
long defaultFolderId = GetterUtil.getLong(preferences.getValue("rootFolderId", StringPool.BLANK), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

Folder folder = (Folder)request.getAttribute("view.jsp-folder");

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

long repositoryId = GetterUtil.getLong((String)request.getAttribute("view.jsp-repositoryId"));

long parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

Folder parentFolder = null;

boolean showRootFolder = ParamUtil.getBoolean(request, "showRootFolder", true);
boolean showSiblings = ParamUtil.getBoolean(request, "showSiblings");

if (showSiblings) {
	if (folder != null) {
		parentFolderId = folder.getParentFolderId();

		if (parentFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			try {
				parentFolder = DLAppLocalServiceUtil.getFolder(folderId);
			}
			catch (NoSuchFolderException nsfe) {
				parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
			}

			repositoryId = parentFolder.getRepositoryId();
		}
		else {
			repositoryId = scopeGroupId;
		}
	}
}
else {
	parentFolderId = folderId;
}

int start = ParamUtil.getInteger(request, "start");
int end = ParamUtil.getInteger(request, "end", SearchContainer.DEFAULT_DELTA);

List<Folder> folders = DLAppServiceUtil.getFolders(repositoryId, parentFolderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

List<Folder> ancestorFolders = new ArrayList();

if (folder != null) {
	ancestorFolders = folder.getAncestors();
}
%>

<div class="header-row">
	<div class="header-row-content" id="<portlet:namespace />parentFolderTitleContainer">
		<div class="parent-folder-title" id="<portlet:namespace />parentFolderTitle">
			<c:choose>
				<c:when test="<%= folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID && parentFolderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID %>">
					<span>
						<liferay-ui:message key="documents-home" />
					</span>
				</c:when>
				<c:when test="<%= parentFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID %>">

					<span>
						<%= DLAppLocalServiceUtil.getFolder(parentFolderId).getName() %>
					</span>
				</c:when>
			</c:choose>
		</div>
	</div>
</div>

<div class="body-row">
	<div id="<portlet:namespace />folderContainer">
		<ul>
			<c:choose>
				<c:when test="<%= (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) && (parentFolderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID && showRootFolder) %>">

					<%
					int foldersCount = DLAppServiceUtil.getFoldersCount(repositoryId, folderId);
					%>

					<liferay-portlet:renderURL varImpl="viewDocumentsHomeURL">
						<portlet:param name="struts_action" value="/document_library/view" />
						<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
					</liferay-portlet:renderURL>

					<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" varImpl="viewDocumentsHomeFoldersURL">
						<portlet:param name="struts_action" value="/document_library/view" />
						<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
						<portlet:param name="showRootFolder" value="<%= Boolean.FALSE.toString() %>" />
						<portlet:param name="showSiblings" value="<%= Boolean.TRUE.toString() %>" />
						<portlet:param name="viewFolders" value="<%= Boolean.TRUE.toString() %>" />
					</liferay-portlet:resourceURL>

					<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" varImpl="viewDocumentsHomeEntriesURL">
						<portlet:param name="struts_action" value="/document_library/view" />
						<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
						<portlet:param name="showSiblings" value="<%= Boolean.TRUE.toString() %>" />
						<portlet:param name="viewAddButton" value="<%= Boolean.TRUE.toString() %>" />
						<portlet:param name="viewBreadcrumb" value="<%= Boolean.TRUE.toString() %>" />
						<portlet:param name="viewDisplayStyleButtons" value="<%= Boolean.TRUE.toString() %>" />
						<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
						<portlet:param name="viewFileEntrySearch" value="<%= Boolean.TRUE.toString() %>" />
						<portlet:param name="viewFolders" value="<%= Boolean.TRUE.toString() %>" />
						<portlet:param name="start" value="0" />
						<portlet:param name="end" value="<%= String.valueOf(end - start) %>" />
					</liferay-portlet:resourceURL>

					<%
					String navigation = ParamUtil.getString(request, "navigation", "documents-home");
					%>

					<li class="folder <%= navigation.equals("documents-home") ? "selected" : StringPool.BLANK %>">
						<c:if test="<%= (foldersCount > 0) %>">
							<a href="<%= viewDocumentsHomeURL.toString() %>" data-refresh-folders="<%= Boolean.TRUE.toString() %>" data-resource-url="<%= viewDocumentsHomeFoldersURL.toString() %>" class="expand-folder">
								<liferay-ui:icon cssClass="expand-folder-arrow" image="../aui/carat-1-r" />
							</a>
						</c:if>

						<a href="<%= viewDocumentsHomeURL.toString() %>" data-navigation="documents-home" data-refresh-entries="<%= Boolean.TRUE.toString() %>" data-resource-url="<%= viewDocumentsHomeEntriesURL.toString() %>" data-show-siblings="<%= Boolean.TRUE.toString() %>" data-view-folders="<%= Boolean.TRUE.toString() %>">
							<liferay-ui:icon image="../aui/home" />

							<%= LanguageUtil.get(pageContext, "documents-home") %>
						</a>
					</li>

					<liferay-portlet:renderURL varImpl="viewRecentDocumentsURL">
						<portlet:param name="struts_action" value="/document_library/view" />
						<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
					</liferay-portlet:renderURL>

					<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" varImpl="viewRecentDocumentsEntriesURL">
						<portlet:param name="struts_action" value="/document_library/view" />
						<portlet:param name="navigation" value="recent-documents" />
						<portlet:param name="viewAddButton" value="<%= Boolean.TRUE.toString() %>" />
						<portlet:param name="viewBreadcrumb" value="<%= Boolean.TRUE.toString() %>" />
						<portlet:param name="viewDisplayStyleButtons" value="<%= Boolean.TRUE.toString() %>" />
						<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
						<portlet:param name="viewFileEntrySearch" value="<%= Boolean.TRUE.toString() %>" />
						<portlet:param name="start" value="0" />
						<portlet:param name="end" value="<%= String.valueOf(end - start) %>" />
					</liferay-portlet:resourceURL>

					<li class="folder <%= navigation.equals("recent-documents") ? "selected" : StringPool.BLANK %>">
						<a href="<%= viewRecentDocumentsURL.toString() %>" data-navigation="recent-documents" data-refresh-entries="<%= Boolean.TRUE.toString() %>" data-resource-url="<%= viewRecentDocumentsEntriesURL.toString() %>">
							<liferay-ui:icon image="../aui/clock" />

							<%= LanguageUtil.get(pageContext, "recent-documents") %>
						</a>
					</li>

					<liferay-portlet:renderURL varImpl="viewMyDocumentsURL">
						<portlet:param name="struts_action" value="/document_library/view" />
						<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
					</liferay-portlet:renderURL>

					<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" varImpl="viewMyDocumentsEntriesURL">
						<portlet:param name="struts_action" value="/document_library/view" />
						<portlet:param name="navigation" value="my-documents" />
						<portlet:param name="viewAddButton" value="<%= Boolean.TRUE.toString() %>" />
						<portlet:param name="viewBreadcrumb" value="<%= Boolean.TRUE.toString() %>" />
						<portlet:param name="viewDisplayStyleButtons" value="<%= Boolean.TRUE.toString() %>" />
						<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
						<portlet:param name="viewFileEntrySearch" value="<%= Boolean.TRUE.toString() %>" />
						<portlet:param name="start" value="0" />
						<portlet:param name="end" value="<%= String.valueOf(end - start) %>" />
					</liferay-portlet:resourceURL>

					<li class="folder <%= navigation.equals("my-documents") ? "selected" : StringPool.BLANK %>">
						<a href="<%= viewMyDocumentsURL.toString() %>" data-navigation="my-documents" data-refresh-entries="<%= Boolean.TRUE.toString() %>" data-resource-url="<%= viewMyDocumentsEntriesURL.toString() %>">
							<liferay-ui:icon image="../aui/person" />

							<%= LanguageUtil.get(pageContext, "my-documents") %>
						</a>
					</li>

					<%
					List<DLDocumentType> documentTypes = DLDocumentTypeServiceUtil.getDocumentTypes(scopeGroupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
					%>

					<c:if test="<%= !documentTypes.isEmpty() %>">
						<liferay-portlet:renderURL varImpl="viewBasicDocumentTypeURL">
							<portlet:param name="struts_action" value="/document_library/view" />
							<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
							<portlet:param name="documentTypeId" value="<%= String.valueOf(0) %>" />
						</liferay-portlet:renderURL>

						<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" varImpl="viewBasicDocumentTypeEntriesURL">
							<portlet:param name="struts_action" value="/document_library/view" />
							<portlet:param name="documentTypeId" value="<%= String.valueOf(0) %>" />
							<portlet:param name="viewAddButton" value="<%= Boolean.TRUE.toString() %>" />
							<portlet:param name="viewBreadcrumb" value="<%= Boolean.TRUE.toString() %>" />
							<portlet:param name="viewDisplayStyleButtons" value="<%= Boolean.TRUE.toString() %>" />
							<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
							<portlet:param name="viewFileEntrySearch" value="<%= Boolean.TRUE.toString() %>" />
							<portlet:param name="start" value="0" />
							<portlet:param name="end" value="<%= String.valueOf(end - start) %>" />
						</liferay-portlet:resourceURL>

						<li class="folder document-type">
							<a href="<%= viewBasicDocumentTypeURL.toString() %>" data-document-type-id="<%= String.valueOf(0) %>" data-refresh-entries="<%= Boolean.TRUE.toString() %>" data-resource-url="<%= viewBasicDocumentTypeEntriesURL.toString() %>"
								<liferay-ui:icon image="copy" />

								<%= LanguageUtil.get(pageContext, "basic-document") %>
							</a>
						</li>
					</c:if>

					<%
					for (DLDocumentType documentType : documentTypes) {
					%>

						<liferay-portlet:renderURL varImpl="viewDocumentTypeURL">
							<portlet:param name="struts_action" value="/document_library/view" />
							<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
							<portlet:param name="documentTypeId" value="<%= String.valueOf(documentType.getDocumentTypeId()) %>" />
						</liferay-portlet:renderURL>

						<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" varImpl="viewDocumentTypeEntriesURL">
							<portlet:param name="struts_action" value="/document_library/view" />
							<portlet:param name="documentTypeId" value="<%= String.valueOf(documentType.getDocumentTypeId()) %>" />
							<portlet:param name="viewAddButton" value="<%= Boolean.TRUE.toString() %>" />
							<portlet:param name="viewBreadcrumb" value="<%= Boolean.TRUE.toString() %>" />
							<portlet:param name="viewDisplayStyleButtons" value="<%= Boolean.TRUE.toString() %>" />
							<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
							<portlet:param name="viewFileEntrySearch" value="<%= Boolean.TRUE.toString() %>" />
							<portlet:param name="start" value="0" />
							<portlet:param name="end" value="<%= String.valueOf(end - start) %>" />
						</liferay-portlet:resourceURL>

						<li class="folder document-type">
							<a href="<%= viewDocumentTypeURL.toString() %>" data-document-type-id="<%= documentType.getDocumentTypeId()
							 %>" data-refresh-entries="<%= Boolean.TRUE.toString() %>" data-resource-url="<%= viewDocumentTypeEntriesURL.toString() %>"
								<liferay-ui:icon image="copy" />

								<%= documentType.getName() %>
							</a>
						</li>

					<%
					}
					%>

				</c:when>
				<c:otherwise>
					<liferay-portlet:renderURL varImpl="viewURL">
						<portlet:param name="struts_action" value="/document_library/view" />
						<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
					</liferay-portlet:renderURL>

					<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" varImpl="viewFoldersURL">
						<portlet:param name="struts_action" value="/document_library/view" />
						<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
						<portlet:param name="showSiblings" value="<%= Boolean.TRUE.toString() %>" />
						<portlet:param name="viewFolders" value="<%= Boolean.TRUE.toString() %>" />
					</liferay-portlet:resourceURL>

					<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" varImpl="viewEntriesURL">
						<portlet:param name="struts_action" value="/document_library/view" />
						<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
						<portlet:param name="showSiblings" value="<%= Boolean.TRUE.toString() %>" />
						<portlet:param name="viewAddButton" value="<%= Boolean.TRUE.toString() %>" />
						<portlet:param name="viewBreadcrumb" value="<%= Boolean.TRUE.toString() %>" />
						<portlet:param name="viewDisplayStyleButtons" value="<%= Boolean.TRUE.toString() %>" />
						<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
						<portlet:param name="viewFileEntrySearch" value="<%= Boolean.TRUE.toString() %>" />
						<portlet:param name="viewFolders" value="<%= Boolean.TRUE.toString() %>" />
						<portlet:param name="start" value="0" />
						<portlet:param name="end" value="<%= String.valueOf(end - start) %>" />
					</liferay-portlet:resourceURL>

					<li class="folder">
						<a data-direction-right="<%= Boolean.TRUE.toString() %>" data-refresh-folders="<%= Boolean.TRUE.toString() %>" data-resource-url="<%= viewFoldersURL.toString() %>" class="expand-folder" href="<%= viewURL.toString() %>">
							<liferay-ui:icon cssClass="expand-folder-arrow" image="../aui/carat-1-l" />
						</a>

						<a data-folder-id="<%= String.valueOf(parentFolderId) %>" data-refresh-entries="<%= Boolean.TRUE.toString() %>" data-refresh-folders="<%= Boolean.TRUE.toString() %>" data-resource-url="<%= viewEntriesURL.toString() %>" data-show-siblings="<%= Boolean.TRUE.toString() %>" data-view-folders="<%= Boolean.TRUE.toString() %>" href="<%= viewURL.toString() %>">
							<liferay-ui:icon src='<%= themeDisplay.getPathThemeImages() + "/arrows/01_up.png" %>' />

							<%= LanguageUtil.get(pageContext, "up") %>
						</a>
					</li>

					<%
					for (Folder curFolder : folders) {
						int foldersCount = DLAppServiceUtil.getFoldersCount(repositoryId, curFolder.getFolderId());
					%>

						<liferay-portlet:renderURL varImpl="viewURL">
							<portlet:param name="struts_action" value="/document_library/view" />
							<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
						</liferay-portlet:renderURL>

						<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" varImpl="viewFoldersURL">
							<portlet:param name="struts_action" value="/document_library/view" />
							<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
							<portlet:param name="viewFolders" value="<%= Boolean.TRUE.toString() %>" />
						</liferay-portlet:resourceURL>

						<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" varImpl="viewEntriesURL">
							<portlet:param name="struts_action" value="/document_library/view" />
							<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
							<portlet:param name="showSiblings" value="<%= Boolean.TRUE.toString() %>" />
							<portlet:param name="viewAddButton" value="<%= Boolean.TRUE.toString() %>" />
							<portlet:param name="viewBreadcrumb" value="<%= Boolean.TRUE.toString() %>" />
							<portlet:param name="viewDisplayStyleButtons" value="<%= Boolean.TRUE.toString() %>" />
							<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
							<portlet:param name="viewFileEntrySearch" value="<%= Boolean.TRUE.toString() %>" />
							<portlet:param name="start" value="0" />
							<portlet:param name="end" value="<%= String.valueOf(end - start) %>" />
						</liferay-portlet:resourceURL>

						<li class="folder <%= (curFolder.getFolderId() == folderId) ? "selected" : StringPool.BLANK %>">
							<c:if test="<%= foldersCount > 0 %>">
								<a data-refresh-folders="<%= Boolean.TRUE.toString() %>" data-resource-url="<%= viewFoldersURL.toString() %>" class="expand-folder" href="<%= viewURL.toString() %>">
									<liferay-ui:icon cssClass="expand-folder-arrow" image="../aui/carat-1-r" />
								</a>
							</c:if>

							<a data-folder-id="<%= String.valueOf(curFolder.getFolderId()) %>" data-refresh-entries="<%= Boolean.TRUE.toString() %>" data-resource-url="<%= viewEntriesURL.toString() %>" data-show-siblings="<%= Boolean.TRUE.toString() %>" data-view-folders="<%= Boolean.TRUE.toString() %>" href="<%= viewURL.toString() %>">
								<liferay-ui:icon image="folder" />

								<%= curFolder.getName() %>
							</a>
						</li>

					<%
					}
					%>

				</c:otherwise>
			</c:choose>
		</ul>
	</div>
</div>