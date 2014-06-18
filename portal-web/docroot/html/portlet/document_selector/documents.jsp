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

<%@ include file="/html/portlet/document_selector/init.jsp" %>

<%
long groupId = ParamUtil.getLong(request, "groupId");

Folder folder = (Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

if ((folder != null) && (folder.getGroupId() != groupId)) {
	folder = null;

	folderId = 0;
}

if (folderId > 0) {
	folder = DLAppServiceUtil.getFolder(folderId);
}

String ckEditorFuncNum = DocumentSelectorUtil.getCKEditorFuncNum(request);
String eventName = ParamUtil.getString(request, "eventName");
boolean showGroupsSelector = ParamUtil.getBoolean(request, "showGroupsSelector");

long repositoryId = groupId;

if (folder != null) {
	repositoryId = folder.getRepositoryId();

	PortletURL breadcrumbURL = renderResponse.createRenderURL();

	breadcrumbURL.setParameter("struts_action", "/document_selector/view");
	breadcrumbURL.setParameter("groupId", String.valueOf(groupId));
	breadcrumbURL.setParameter("folderId", String.valueOf(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID));
	breadcrumbURL.setParameter("ckEditorFuncNum", ckEditorFuncNum);
	breadcrumbURL.setParameter("eventName", eventName);
	breadcrumbURL.setParameter("showGroupsSelector", String.valueOf(showGroupsSelector));

	PortalUtil.addPortletBreadcrumbEntry(request, themeDisplay.translate("home"), breadcrumbURL.toString());

	breadcrumbURL.setParameter("folderId", String.valueOf(folderId));

	DLUtil.addPortletBreadcrumbEntries(folder, request, breadcrumbURL);
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/document_selector/view");
portletURL.setParameter("groupId", String.valueOf(groupId));
portletURL.setParameter("folderId", String.valueOf(folderId));
portletURL.setParameter("ckEditorFuncNum", ckEditorFuncNum);
portletURL.setParameter("eventName", eventName);
portletURL.setParameter("showGroupsSelector", String.valueOf(showGroupsSelector));
%>

<c:if test="<%= showGroupsSelector %>">
	<liferay-util:include page="/html/portlet/document_selector/group_selector.jsp" />
</c:if>

<aui:form method="post" name="selectDocumentFm">

	<%
	FileEntrySearch fileEntrySearchContainer = new FileEntrySearch(renderRequest, portletURL);

	FileEntryDisplayTerms displayTerms = (FileEntryDisplayTerms)fileEntrySearchContainer.getDisplayTerms();

	displayTerms.setSelectedGroupId(groupId);
	%>

	<aui:nav-bar>
		<aui:nav cssClass="navbar-nav">
			<aui:nav-item
				dropdown="<%= true %>"
				iconCssClass="icon-plus"
				label="add"
			>
				<c:if test="<%= DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_FOLDER) %>">
					<liferay-portlet:renderURL portletName="<%= PortletKeys.DOCUMENT_LIBRARY %>" var="addFolderURL">
						<portlet:param name="struts_action" value="/document_library/edit_folder" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
						<portlet:param name="parentFolderId" value="<%= String.valueOf(folderId) %>" />
					</liferay-portlet:renderURL>

					<%
					AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(DLFolder.class.getName());
					%>

					<aui:nav-item
						href="<%= addFolderURL %>"
						iconCssClass="<%= assetRendererFactory.getIconCssClass() %>"
						label='<%= (folder != null) ? "subfolder" : "folder" %>'
					/>
				</c:if>

				<c:if test="<%= DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_DOCUMENT) %>">

					<%
					List<DLFileEntryType> fileEntryTypes = Collections.emptyList();

					if ((folder == null) || folder.isSupportsMetadata()) {
						fileEntryTypes = DLFileEntryTypeLocalServiceUtil.getFolderFileEntryTypes(PortalUtil.getCurrentAndAncestorSiteGroupIds(scopeGroupId), folderId, true);
					}
					%>

					<c:if test="<%= fileEntryTypes.isEmpty() %>">
						<liferay-portlet:renderURL portletName="<%= PortletKeys.DOCUMENT_LIBRARY %>" var="editFileEntryURL">
							<portlet:param name="struts_action" value="/document_library/edit_file_entry" />
							<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="backURL" value="<%= currentURL %>" />
							<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
							<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
						</liferay-portlet:renderURL>

						<%
						AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(DLFileEntry.class.getName());
						%>

						<aui:nav-item
							href="<%= editFileEntryURL %>"
							iconCssClass="<%= assetRendererFactory.getIconCssClass() %>"
							label="basic-document"
						/>
					</c:if>

					<%
					for (DLFileEntryType fileEntryType : fileEntryTypes) {
					%>

						<liferay-portlet:renderURL portletName="<%= PortletKeys.DOCUMENT_LIBRARY %>" var="addFileEntryTypeURL">
							<portlet:param name="struts_action" value="/document_library/edit_file_entry" />
							<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
							<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
							<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(fileEntryType.getFileEntryTypeId()) %>" />
						</liferay-portlet:renderURL>

						<%
						AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(DLFileEntry.class.getName());
						%>

						<aui:nav-item
							href="<%= addFileEntryTypeURL %>"
							iconCssClass="<%= assetRendererFactory.getIconCssClass() %>"
							label="<%= HtmlUtil.escape(fileEntryType.getName(locale)) %>"
						/>

					<%
					}
					%>

				</c:if>
			</aui:nav-item>
		</aui:nav>

		<aui:nav-bar-search cssClass="pull-right"
			file="/html/portlet/document_selector/search.jsp"
			searchContainer="<%= fileEntrySearchContainer %>"
		/>
	</aui:nav-bar>

	<c:if test="<%= Validator.isNull(displayTerms.getKeywords()) %>">
		<liferay-ui:header
			title="folders"
		/>

		<liferay-ui:breadcrumb showGuestGroup="<%= false %>" showLayout="<%= false %>" showParentGroups="<%= false %>" />

		<liferay-ui:search-container
			emptyResultsMessage="there-are-no-folders"
			iteratorURL="<%= portletURL %>"
		>
			<liferay-ui:search-container-results
				results="<%= DLAppServiceUtil.getFolders(repositoryId, folderId, searchContainer.getStart(), searchContainer.getEnd()) %>"
				total="<%= DLAppServiceUtil.getFoldersCount(groupId, folderId) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.kernel.repository.model.Folder"
				keyProperty="folderId"
				modelVar="curFolder"
			>
				<portlet:renderURL var="rowURL">
					<portlet:param name="struts_action" value="/document_selector/view" />
					<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
					<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
					<portlet:param name="ckEditorFuncNum" value="<%= ckEditorFuncNum %>" />
					<portlet:param name="eventName" value="<%= eventName %>" />
					<portlet:param name="showGroupsSelector" value="<%= String.valueOf(showGroupsSelector) %>" />
				</portlet:renderURL>

				<liferay-ui:search-container-column-text
					href="<%= rowURL %>"
					name="folder"
				>

					<%
					AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(DLFolder.class.getName());

					AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(curFolder.getFolderId());
					%>

					<liferay-ui:icon
						iconCssClass="<%= assetRenderer.getIconCssClass() %>"
						label="<%= true %>"
						message="<%= HtmlUtil.escape(curFolder.getName()) %>"
					/>
				</liferay-ui:search-container-column-text>

				<%
				List<Long> subfolderIds = DLAppServiceUtil.getSubfolderIds(curFolder.getRepositoryId(), curFolder.getFolderId(), false);

				int foldersCount = subfolderIds.size();

				subfolderIds.clear();
				subfolderIds.add(curFolder.getFolderId());

				int fileEntriesCount = DLAppServiceUtil.getFoldersFileEntriesCount(curFolder.getRepositoryId(), subfolderIds, WorkflowConstants.STATUS_APPROVED);
				%>

				<liferay-ui:search-container-column-text
					name="num-of-folders"
					value="<%= String.valueOf(foldersCount) %>"
				/>

				<liferay-ui:search-container-column-text
					name="num-of-documents"
					value="<%= String.valueOf(fileEntriesCount) %>"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
		</liferay-ui:search-container>
	</c:if>

	<%
	PortletURL backURL = renderResponse.createRenderURL();

	backURL.setParameter("struts_action", "/document_selector/view");
	backURL.setParameter("groupId", String.valueOf(groupId));
	backURL.setParameter("ckEditorFuncNum", ckEditorFuncNum);
	backURL.setParameter("eventName", eventName);
	backURL.setParameter("showGroupsSelector", String.valueOf(showGroupsSelector));
	%>

	<liferay-ui:header
		backURL="<%= backURL.toString() %>"
		showBackURL="<%= Validator.isNotNull(displayTerms.getKeywords()) %>"
		title="documents"
	/>

	<%
	PortletURL iteratorURL = renderResponse.createRenderURL();

	iteratorURL.setParameter("struts_action", "/document_selector/view");
	iteratorURL.setParameter("groupId", String.valueOf(groupId));
	iteratorURL.setParameter("folderId", String.valueOf(folderId));
	iteratorURL.setParameter("ckEditorFuncNum", ckEditorFuncNum);
	iteratorURL.setParameter("eventName", eventName);
	iteratorURL.setParameter("showGroupsSelector", String.valueOf(showGroupsSelector));
	%>

	<liferay-ui:search-container
		emptyResultsMessage="there-are-no-documents-in-this-folder"
		iteratorURL="<%= iteratorURL %>"
	>

		<%
		SearchContext searchContext = SearchContextFactory.getInstance(request);

		searchContext.setAttribute("groupId", groupId);
		searchContext.setAttribute("mimeTypes", DocumentSelectorUtil.getMimeTypes(request));
		searchContext.setAttribute("paginationType", "regular");

		int entryEnd = ParamUtil.getInteger(request, "entryEnd", PropsValues.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA);

		searchContext.setEnd(entryEnd);

		searchContext.setFolderIds(new long[]{folderId});
		searchContext.setGroupIds(new long[] {groupId});
		searchContext.setIncludeFolders(false);

		String keywords = ParamUtil.getString(request, "keywords");

		searchContext.setKeywords(keywords);

		searchContext.setScopeStrict(false);

		int entryStart = ParamUtil.getInteger(request, "entryStart");

		searchContext.setStart(entryStart);

		Hits hits = DLAppServiceUtil.search(repositoryId, searchContext);
		%>

		<liferay-ui:search-container-results
			results="<%= DLUtil.getFileEntries(hits) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.repository.model.FileEntry"
			keyProperty="fileEntryId"
			modelVar="fileEntry"
		>
			<liferay-ui:search-container-column-text
				name="document"
			>
				<img align="left" alt="" src="<%= DLUtil.getThumbnailSrc(fileEntry, null, themeDisplay) %>" style="<%= DLUtil.getThumbnailStyle() %>" />
				<%= HtmlUtil.escape(fileEntry.getTitle()) %>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="size"
				value="<%= TextFormatter.formatStorageSize(fileEntry.getSize(), locale) %>"
			/>

			<c:if test="<%= PropsValues.DL_FILE_ENTRY_BUFFERED_INCREMENT_ENABLED %>">
				<liferay-ui:search-container-column-text
					name="downloads"
					value="<%= String.valueOf(fileEntry.getReadCount()) %>"
				/>
			</c:if>

			<liferay-ui:search-container-column-text
				name="locked"
				value='<%= fileEntry.isCheckedOut() ? "yes" : "no" %>'
			/>

			<liferay-ui:search-container-column-text>

				<%
				Map<String, Object> data = new HashMap<String, Object>();

				data.put("groupid", fileEntry.getGroupId());
				data.put("title", fileEntry.getTitle());
				data.put("url", DLUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), themeDisplay, StringPool.BLANK, false, false));
				data.put("uuid", fileEntry.getUuid());
				data.put("version", fileEntry.getVersion());
				%>

				<aui:button cssClass="selector-button" data="<%= data %>" value="choose" />
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<aui:script use="aui-base">
	Liferay.Util.selectEntityHandler('#<portlet:namespace />selectDocumentFm', '<%= HtmlUtil.escapeJS(eventName) %>');
</aui:script>