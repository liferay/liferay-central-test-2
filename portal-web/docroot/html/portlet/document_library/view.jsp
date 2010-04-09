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
String topLink = ParamUtil.getString(request, "topLink", "document-home");

DLFolder folder = (DLFolder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long defaultFolderId = GetterUtil.getLong(preferences.getValue("rootFolderId", StringPool.BLANK), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", defaultFolderId);

if ((folder == null) && (defaultFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {
	try {
		folder = DLFolderLocalServiceUtil.getFolder(folderId);
	}
	catch (NoSuchFolderException nsfe) {
		folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}
}

int status = StatusConstants.APPROVED;

if (permissionChecker.isCompanyAdmin() || permissionChecker.isCommunityAdmin(scopeGroupId)) {
	status = StatusConstants.ANY;
}

int foldersCount = DLFolderLocalServiceUtil.getFoldersCount(scopeGroupId, folderId);
int fileEntriesCount = DLFolderLocalServiceUtil.getFileEntriesAndFileShortcutsCount(scopeGroupId, folderId, status);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/document_library/view");
portletURL.setParameter("topLink", topLink);
portletURL.setParameter("folderId", String.valueOf(folderId));

request.setAttribute("view.jsp-folder", folder);

request.setAttribute("view.jsp-folderId", String.valueOf(folderId));

request.setAttribute("view.jsp-viewFolder", Boolean.TRUE.toString());
%>

<liferay-util:include page="/html/portlet/document_library/top_links.jsp" />

<c:choose>
	<c:when test='<%= topLink.equals("document-home") %>'>
		<aui:layout>
			<c:if test="<%= folder != null %>">
				<h3 class="folder-title"><%= folder.getName() %></h3>
			</c:if>

			<aui:column columnWidth="<%= showFolderMenu ? 75 : 100 %>" cssClass="folder-column folder-column-first" first="<%= true %>">
				<liferay-ui:panel-container extended="<%= false %>" id="documentLibraryPanelContainer" persistState="<%= true %>">
					<c:if test="<%= showSubfolders %>">
						<c:if test="<%= folder != null %>">
							<div class="folder-description">
								<%= folder.getDescription() %>
							</div>

							<div class="folder-metadata">
								<div class="folder-date">
									<%= LanguageUtil.format(pageContext, "last-updated-x", dateFormatDateTime.format(folder.getModifiedDate())) %>
								</div>

								<div class="folder-subfolders">
									<%= foldersCount %> <liferay-ui:message key='<%= (foldersCount == 1) ? "subfolder" : "subfolders" %>' />
								</div>

								<div class="folder-file-entries">
									<%= fileEntriesCount %> <liferay-ui:message key='<%= (imagesCount == 1) ? "document" : "documents" %>' />
								</div>
							</div>

							<div class="custom-attributes">
								<liferay-ui:custom-attributes-available className="<%= DLFolder.class.getName() %>">
									<liferay-ui:custom-attribute-list
										className="<%= DLFolder.class.getName() %>"
										classPK="<%= (folder != null) ? folder.getFolderId() : 0 %>"
										editable="<%= false %>"
										label="<%= true %>"
									/>
								</liferay-ui:custom-attributes-available>
							</div>
						</c:if>

						<c:if test="<%= foldersCount > 0 %>">
							<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="subFoldersPanel" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, (folder != null) ? "subfolders" : "folders") %>'>
								<liferay-ui:search-container
									curParam="cur1"
									delta="<%= foldersPerPage %>"
									deltaConfigurable="<%= false %>"
									headerNames="<%= StringUtil.merge(folderColumns) %>"
									iteratorURL="<%= portletURL %>"
								>
									<liferay-ui:search-container-results
										results="<%= DLFolderLocalServiceUtil.getFolders(scopeGroupId, folderId, searchContainer.getStart(), searchContainer.getEnd()) %>"
										total="<%= DLFolderLocalServiceUtil.getFoldersCount(scopeGroupId, folderId) %>"
									/>

									<liferay-ui:search-container-row
										className="com.liferay.portlet.documentlibrary.model.DLFolder"
										escapedModel="<%= true %>"
										keyProperty="folderId"
										modelVar="curFolder"
									>
										<liferay-portlet:renderURL varImpl="rowURL">
											<portlet:param name="struts_action" value="/document_library/view" />
											<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
										</liferay-portlet:renderURL>

										<%@ include file="/html/portlet/document_library/folder_columns.jspf" %>
									</liferay-ui:search-container-row>

									<liferay-ui:search-iterator />
								</liferay-ui:search-container>
							</liferay-ui:panel>
						</c:if>

						<c:choose>
							<c:when test="<%= showTabs %>">
								<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="documentsPanel" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "documents") %>'>
									<%@ include file="/html/portlet/document_library/view_file_entries.jspf" %>
								</liferay-ui:panel>
							</c:when>
							<c:otherwise>
								<%@ include file="/html/portlet/document_library/view_file_entries.jspf" %>
							</c:otherwise>
						</c:choose>
					</c:if>
				</liferay-ui:panel-container>
			</aui:column>

			<c:if test="<%= showFolderMenu %>">
				<aui:column columnWidth="<%= 25 %>" cssClass="detail-column detail-column-last" last="<%= true %>">
					<div class="folder-icon">
						<liferay-ui:icon
							cssClass="folder-avatar"
							image='<%= "../file_system/large/" + (((foldersCount + fileEntriesCount) > 0) ? "folder_full_document" : "folder_empty") %>'
							message='<%= folder != null ? folder.getName() : LanguageUtil.get(pageContext, "document-home") %>'
						/>

						<div class="folder-name">
							<h4><%= folder != null ? folder.getName() : LanguageUtil.get(pageContext, "document-home") %></h4>
						</div>
					</div>

					<%
					request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
					%>

					<liferay-util:include page="/html/portlet/document_library/folder_action.jsp" />
				</aui:column>
			</c:if>
		</aui:layout>

		<%
		if (folder != null) {
			DLUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);

			if (portletName.equals(PortletKeys.DOCUMENT_LIBRARY)) {
				PortalUtil.setPageSubtitle(folder.getName(), request);
				PortalUtil.setPageDescription(folder.getDescription(), request);
			}
		}
		%>

	</c:when>
	<c:when test='<%= topLink.equals("my-documents") || topLink.equals("recent-documents") %>'>
		<aui:layout>
			<h3 class="folder-title"><liferay-ui:message key="<%= topLink %>" /></h3>

			<%
			long groupFileEntriesUserId = 0;

			if (topLink.equals("my-documents") && themeDisplay.isSignedIn()) {
				groupFileEntriesUserId = user.getUserId();
			}
			%>

			<liferay-ui:search-container
				delta="<%= fileEntriesPerPage %>"
				deltaConfigurable="<%= false %>"
				emptyResultsMessage="there-are-no-documents"
				iteratorURL="<%= portletURL %>"
			>
				<liferay-ui:search-container-results
					results="<%= DLFileEntryLocalServiceUtil.getGroupFileEntries(scopeGroupId, groupFileEntriesUserId, searchContainer.getStart(), searchContainer.getEnd()) %>"
					total="<%= DLFileEntryLocalServiceUtil.getGroupFileEntriesCount(scopeGroupId, groupFileEntriesUserId) %>"
				/>

				<liferay-ui:search-container-row
					className="com.liferay.portlet.documentlibrary.model.DLFileEntry"
					escapedModel="<%= true %>"
					keyProperty="fileEntryId"
					modelVar="fileEntry"
				>

					<%
					String rowHREF = null;

					if (DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.VIEW)) {
						rowHREF = themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + themeDisplay.getScopeGroupId() + StringPool.SLASH + fileEntry.getFolderId() + StringPool.SLASH + HttpUtil.encodeURL(HtmlUtil.unescape(fileEntry.getTitle()));
					}
					%>

					<%@ include file="/html/portlet/document_library/file_entry_columns.jspf" %>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator />
			</liferay-ui:search-container>
		</aui:layout>

		<%
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, topLink), currentURL);

		PortalUtil.setPageSubtitle(LanguageUtil.get(pageContext, topLink), request);
		%>

	</c:when>
</c:choose>