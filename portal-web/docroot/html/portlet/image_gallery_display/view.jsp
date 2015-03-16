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

<%@ include file="/html/portlet/image_gallery_display/init.jsp" %>

<%
Folder folder = (Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long defaultFolderId = dlPortletInstanceSettings.getDefaultFolderId();

long folderId = BeanParamUtil.getLong(folder, request, "folderId", defaultFolderId);

boolean defaultFolderView = false;

if ((folder == null) && (defaultFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {
	defaultFolderView = true;
}

if (defaultFolderView) {
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

int status = WorkflowConstants.STATUS_APPROVED;

if (permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId)) {
	status = WorkflowConstants.STATUS_ANY;
}

Map<String, Object> contextObjects = new HashMap<String, Object>();

contextObjects.put("dlPortletInstanceSettings", dlPortletInstanceSettings);

DLPortletInstanceSettingsHelper dlPortletInstanceSettingsHelper = new DLPortletInstanceSettingsHelper(igRequestHelper);

String[] mediaGalleryMimeTypes = dlPortletInstanceSettings.getMimeTypes();

List fileEntries = DLAppServiceUtil.getGroupFileEntries(scopeGroupId, 0, folderId, mediaGalleryMimeTypes, status, 0, SearchContainer.MAX_DELTA, null);
%>

<liferay-ui:ddm-template-renderer className="<%= FileEntry.class.getName() %>" contextObjects="<%= contextObjects %>" displayStyle="<%= displayStyle %>" displayStyleGroupId="<%= displayStyleGroupId %>" entries="<%= fileEntries %>">

	<%
	String topLink = ParamUtil.getString(request, "topLink", "home");

	long assetCategoryId = ParamUtil.getLong(request, "categoryId");
	String assetTagName = ParamUtil.getString(request, "tag");

	boolean useAssetEntryQuery = (assetCategoryId > 0) || Validator.isNotNull(assetTagName);

	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("struts_action", "/image_gallery_display/view");
	portletURL.setParameter("topLink", topLink);
	portletURL.setParameter("folderId", String.valueOf(folderId));

	request.setAttribute("view.jsp-folder", folder);

	request.setAttribute("view.jsp-defaultFolderId", String.valueOf(defaultFolderId));

	request.setAttribute("view.jsp-folderId", String.valueOf(folderId));

	request.setAttribute("view.jsp-repositoryId", String.valueOf(repositoryId));

	request.setAttribute("view.jsp-viewFolder", Boolean.TRUE.toString());

	request.setAttribute("view.jsp-useAssetEntryQuery", String.valueOf(useAssetEntryQuery));

	request.setAttribute("view.jsp-portletURL", portletURL);
	%>

	<portlet:actionURL var="restoreTrashEntriesURL">
		<portlet:param name="struts_action" value="/image_gallery_display/edit_entry" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
	</portlet:actionURL>

	<liferay-ui:trash-undo
		portletURL="<%= restoreTrashEntriesURL %>"
	/>

	<liferay-util:include page="/html/portlet/document_library/top_links.jsp" />

	<c:choose>
		<c:when test="<%= useAssetEntryQuery %>">
			<liferay-ui:categorization-filter
				assetType="images"
				portletURL="<%= portletURL %>"
			/>

			<%
			SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "cur2", SearchContainer.DEFAULT_DELTA, portletURL, null, null);

			long[] classNameIds = {PortalUtil.getClassNameId(DLFileEntryConstants.getClassName()), PortalUtil.getClassNameId(DLFileShortcut.class.getName())};

			AssetEntryQuery assetEntryQuery = new AssetEntryQuery(classNameIds, searchContainer);

			assetEntryQuery.setExcludeZeroViewCount(false);

			int total = AssetEntryServiceUtil.getEntriesCount(assetEntryQuery);

			searchContainer.setTotal(total);

			List results = AssetEntryServiceUtil.getEntries(assetEntryQuery);

			searchContainer.setResults(results);

			mediaGalleryMimeTypes = null;

			request.setAttribute("view.jsp-mediaGalleryMimeTypes", mediaGalleryMimeTypes);
			request.setAttribute("view.jsp-searchContainer", searchContainer);
			%>

			<liferay-util:include page="/html/portlet/image_gallery_display/view_images.jsp" />
		</c:when>
		<c:when test='<%= topLink.equals("home") %>'>
			<aui:row>
				<c:if test="<%= folder != null %>">
					<aui:col width="<%= 100 %>">
						<liferay-ui:header
							localizeTitle="<%= false %>"
							title="<%= folder.getName() %>"
						/>
					</aui:col>
				</c:if>

				<%
				SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "cur2", SearchContainer.DEFAULT_DELTA, portletURL, null, null);

				int foldersCount = DLAppServiceUtil.getFoldersCount(repositoryId, folderId, true);

				int total = DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(repositoryId, folderId, status, mediaGalleryMimeTypes, true);

				int imagesCount = total - foldersCount;

				searchContainer.setTotal(total);

				List results = DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcuts(repositoryId, folderId, status, mediaGalleryMimeTypes, true, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

				searchContainer.setResults(results);

				request.setAttribute("view.jsp-mediaGalleryMimeTypes", mediaGalleryMimeTypes);
				request.setAttribute("view.jsp-searchContainer", searchContainer);
				%>

				<aui:col cssClass="lfr-asset-column lfr-asset-column-details" width="<%= dlPortletInstanceSettingsHelper.isFolderMenuVisible() ? 75 : 100 %>">
					<div id="<portlet:namespace />imageGalleryAssetInfo">
						<c:if test="<%= folder != null %>">
							<div class="lfr-asset-description">
								<%= HtmlUtil.escape(folder.getDescription()) %>
							</div>

							<div class="lfr-asset-metadata">
								<div class="icon-calendar lfr-asset-icon">
									<%= LanguageUtil.format(request, "last-updated-x", dateFormatDate.format(folder.getModifiedDate()), false) %>
								</div>

								<%
								AssetRendererFactory dlFolderAssetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(DLFolder.class.getName());
								%>

								<div class="<%= dlFolderAssetRendererFactory.getIconCssClass() %> lfr-asset-icon">
									<%= foldersCount %> <liferay-ui:message key='<%= (foldersCount == 1) ? "subfolder" : "subfolders" %>' />
								</div>

								<%
								AssetRendererFactory dlFileEntryAssetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(DLFileEntry.class.getName());
								%>

								<div class="<%= dlFileEntryAssetRendererFactory.getIconCssClass() %> last lfr-asset-icon">
									<%= imagesCount %> <liferay-ui:message key='<%= (imagesCount == 1) ? "image" : "images" %>' />
								</div>
							</div>

							<liferay-ui:custom-attributes-available className="<%= DLFolderConstants.getClassName() %>">
								<liferay-ui:custom-attribute-list
									className="<%= DLFolderConstants.getClassName() %>"
									classPK="<%= (folder != null) ? folder.getFolderId() : 0 %>"
									editable="<%= false %>"
									label="<%= true %>"
								/>
							</liferay-ui:custom-attributes-available>
						</c:if>

						<liferay-util:include page="/html/portlet/image_gallery_display/view_images.jsp" />
					</div>
				</aui:col>

				<c:if test="<%= dlPortletInstanceSettingsHelper.isFolderMenuVisible() %>">
					<aui:col cssClass="lfr-asset-column lfr-asset-column-actions" last="<%= true %>" width="<%= 25 %>">
						<div class="lfr-asset-summary">
							<liferay-ui:icon
								cssClass="lfr-asset-avatar"
								image='<%= "../file_system/large/" + ((total > 0) ? "folder_full_image" : "folder_empty") %>'
								message='<%= (folder != null) ? HtmlUtil.escape(folder.getName()) : LanguageUtil.get(request, "home") %>'
							/>

							<div class="lfr-asset-name">
								<h4><%= (folder != null) ? HtmlUtil.escape(folder.getName()) : LanguageUtil.get(request, "home") %></h4>
							</div>
						</div>

						<%
						request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
						%>

						<liferay-util:include page="/html/portlet/document_library/folder_action.jsp" />
					</aui:col>
				</c:if>
			</aui:row>

			<%
			if (folder != null) {
				IGUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);

				if (!defaultFolderView && portletName.equals(PortletKeys.MEDIA_GALLERY_DISPLAY)) {
					PortalUtil.setPageSubtitle(folder.getName(), request);
					PortalUtil.setPageDescription(folder.getDescription(), request);
				}
			}
			%>

		</c:when>
		<c:when test='<%= topLink.equals("mine") || topLink.equals("recent") %>'>

			<%
			long groupImagesUserId = 0;

			if (topLink.equals("mine") && themeDisplay.isSignedIn()) {
				groupImagesUserId = user.getUserId();
			}

			SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, null, null);

			int total = DLAppServiceUtil.getGroupFileEntriesCount(repositoryId, groupImagesUserId, defaultFolderId, mediaGalleryMimeTypes, status);

			searchContainer.setTotal(total);

			List results = DLAppServiceUtil.getGroupFileEntries(repositoryId, groupImagesUserId, defaultFolderId, mediaGalleryMimeTypes, status, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

			searchContainer.setResults(results);

			request.setAttribute("view.jsp-mediaGalleryMimeTypes", mediaGalleryMimeTypes);
			request.setAttribute("view.jsp-searchContainer", searchContainer);
			%>

			<aui:row>
				<liferay-ui:header
					title="<%= topLink %>"
				/>

				<liferay-util:include page="/html/portlet/image_gallery_display/view_images.jsp" />
			</aui:row>

			<%
			PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, topLink), currentURL);

			PortalUtil.setPageSubtitle(LanguageUtil.get(request, topLink), request);
			%>

		</c:when>
	</c:choose>
</liferay-ui:ddm-template-renderer>