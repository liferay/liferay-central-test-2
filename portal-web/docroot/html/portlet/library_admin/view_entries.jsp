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
Folder folder = (Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long defaultFolderId = GetterUtil.getLong(preferences.getValue("rootFolderId", StringPool.BLANK), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

long repositoryId = GetterUtil.getLong((String)request.getAttribute("view.jsp-repositoryId"));

int status = WorkflowConstants.STATUS_APPROVED;

if (permissionChecker.isCompanyAdmin() || permissionChecker.isCommunityAdmin(scopeGroupId)) {
	status = WorkflowConstants.STATUS_ANY;
}

long categoryId = ParamUtil.getLong(request, "categoryId");
String tagName = ParamUtil.getString(request, "tag");

boolean useAssetEntryQuery = (categoryId > 0) || Validator.isNotNull(tagName);

LiferayPortletResponse liferayPortletResponse = null;

if (renderResponse != null) {
	liferayPortletResponse = (LiferayPortletResponse)renderResponse;
}
else {
	liferayPortletResponse = (LiferayPortletResponse)resourceResponse;
}

LiferayPortletRequest liferayPortletRequest = null;

if (renderRequest != null) {
	liferayPortletRequest = (LiferayPortletRequest)renderRequest;
}
else {
	liferayPortletRequest = (LiferayPortletRequest)resourceRequest;
}

PortalPreferences portalPreferences = null;

if (renderRequest != null) {
	portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(renderRequest);
}
else {
	portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(resourceRequest);
}

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(PortletKeys.LIBRARY_ADMIN, "display-style", "icon");
}

int targetPage = ParamUtil.getInteger(request, "targetPage");
int rowsPerPage = ParamUtil.getInteger(request, "rowsPerPage", SearchContainer.DEFAULT_DELTA);

PortletURL portletURL = liferayPortletResponse.createRenderURL();

portletURL.setParameter("struts_action", "/library_admin/view");
portletURL.setParameter("folderId", String.valueOf(folderId));
portletURL.setParameter("displayStyle", String.valueOf(displayStyle));

SearchContainer searchContainer = new SearchContainer(liferayPortletRequest, null, null, "cur2", fileEntriesPerPage, portletURL, null, null);

List<String> headerNames = new ArrayList<String>();

headerNames.add("name");
headerNames.add("description");
headerNames.add("size");
headerNames.add("create-date");
headerNames.add("modified-date");
headerNames.add("read-count");
headerNames.add(StringPool.BLANK);

searchContainer.setHeaderNames(headerNames);

searchContainer.setRowChecker(new RowChecker(liferayPortletResponse));

Map<String, String> orderableHeaders = new HashMap<String, String>();

orderableHeaders.put("name", "name");
orderableHeaders.put("size", "size");
orderableHeaders.put("creation-date", "creationDate");
orderableHeaders.put("modified-date", "modifiedDate");
orderableHeaders.put("read-count", "readCount");

String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");

OrderByComparator orderByComparator = DLUtil.getRepositoryModelOrderByComparator(orderByCol, orderByType);

searchContainer.setOrderableHeaders(orderableHeaders);
searchContainer.setOrderByCol(orderByCol);
searchContainer.setOrderByType(orderByType);
searchContainer.setOrderByComparator(orderByComparator);

searchContainer.setRowChecker(new RowChecker(liferayPortletResponse));

List results = null;
int total = 0;

if (useAssetEntryQuery) {
	long[] classNameIds = {PortalUtil.getClassNameId(DLFileEntryConstants.getClassName()), PortalUtil.getClassNameId(DLFileShortcut.class.getName())};

	AssetEntryQuery assetEntryQuery = new AssetEntryQuery(classNameIds, searchContainer);

	assetEntryQuery.setExcludeZeroViewCount(false);

	results = AssetEntryServiceUtil.getEntries(assetEntryQuery);
	total = AssetEntryServiceUtil.getEntriesCount(assetEntryQuery);
}
else {
	if (true) {
		results = DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcuts(repositoryId, folderId, status, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());
	}
	else {
		List folders = DLAppServiceUtil.getFolders(repositoryId, folderId, searchContainer.getStart(), searchContainer.getEnd(), DLUtil.getRepositoryModelOrderByComparator(orderByCol, orderByType));

		List fileEntries = DLAppServiceUtil.getFileEntries(repositoryId, folderId, searchContainer.getStart(), searchContainer.getEnd(), DLUtil.getRepositoryModelOrderByComparator(orderByCol, orderByType));

		results = ListUtil.copy(folders);

		results.addAll(fileEntries);
	}

	if (true) {
		total = DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(repositoryId, folderId, status);
	}
	else {
		total = DLAppServiceUtil.getFoldersCount(repositoryId, folderId);
		total += DLAppServiceUtil.getFileEntriesCount(repositoryId, folderId);
	}
}

searchContainer.setResults(results);
searchContainer.setTotal(total);
%>

<c:if test='<%= !displayStyle.equals("list") %>'>
	<c:choose>
		<c:when test="<%= results.isEmpty() %>">
			<div class="portlet-msg-info">
				<%= LanguageUtil.get(pageContext, "there-are-no-documents-in-this-folder") %>
			</div>
		</c:when>
		<c:otherwise>
			<div class="taglib-search-iterator-page-iterator-top">
				<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
			</div>
		</c:otherwise>
	</c:choose>
</c:if>

<%
for (int i = 0; i < results.size(); i++) {
	Object result = results.get(i);
%>

	<%@ include file="/html/portlet/document_library/cast_result.jspf" %>

	<c:choose>
		<c:when test='<%= !displayStyle.equals("list") %>'>
			<c:choose>
				<c:when test="<%= fileEntry != null %>">
					<c:choose>
						<c:when test="<%= DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.VIEW) %>">

							<%
							PortletURL tempRowURL = liferayPortletResponse.createRenderURL();

							tempRowURL.setParameter("struts_action", "/library_admin/view_file_entry");
							tempRowURL.setParameter("redirect", currentURL);
							tempRowURL.setParameter("fileEntryId", String.valueOf(fileEntry.getFileEntryId()));

							String src = themeDisplay.getPathThemeImages() + "/file_system/large/" + DLUtil.getGenericName(fileEntry.getExtension()) + ".png";

							if (PDFProcessorUtil.hasImages(fileEntry)) {
								src = themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + themeDisplay.getScopeGroupId() + StringPool.SLASH + fileEntry.getFolderId() + StringPool.SLASH + HttpUtil.encodeURL(HtmlUtil.unescape(fileEntry.getTitle())) + "?version=" + fileEntry.getVersion() + "&thumbnail=1";
							}

							request.setAttribute("view_entries.jsp-fileEntry", fileEntry);
							%>

							<c:choose>
								<c:when test='<%= displayStyle.equals("icon") %>'>
									<div class="document-display-style icon">
										<input class="overlay document-selector" name="<portlet:namespace /><%= RowChecker.ROW_IDS %>" type="checkbox" value="<%= fileEntry.getFileEntryId() %>" />

										<liferay-util:include page="/html/portlet/library_admin/file_entry_action.jsp" />

										<a class="document-link" data-isFolder="<%= Boolean.FALSE.toString() %>" href="<%= tempRowURL.toString() %>" title="<%= HtmlUtil.escape(fileEntry.getTitle()) + " - " + HtmlUtil.escape(fileEntry.getDescription()) %>">
											<c:if test="<%= fileEntry.isLocked() %>">
												<img alt="<%= LanguageUtil.get(pageContext, "locked") %>" class="locked-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/large/overlay_lock.png">
											</c:if>

											<img border="no" class="document-thumbnail" src="<%= src %>" style="height: <%= PropsValues.DL_FILE_ENTRY_THUMBNAIL_HEIGHT %>; width: <%= PropsValues.DL_FILE_ENTRY_THUMBNAIL_WIDTH %>;" />

											<span class="document-title">
												<%= HtmlUtil.escape(StringUtil.shorten(fileEntry.getTitle(), 60)) %>
											</span>
										</a>
									</div>
								</c:when>

								<c:otherwise>
									<div class="document-display-style descriptive">
										<a class="document-link" data-isFolder="<%= Boolean.FALSE.toString() %>" href="<%= tempRowURL.toString() %>" title="<%= HtmlUtil.escape(fileEntry.getTitle()) + " - " + HtmlUtil.escape(fileEntry.getDescription()) %>">
											<c:if test="<%= fileEntry.isLocked() %>">
												<img alt="<%= LanguageUtil.get(pageContext, "locked") %>" class="locked-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/large/overlay_lock.png">
											</c:if>

											<img border="no" class="document-thumbnail" src="<%= src %>" style="width: <%= PropsValues.DL_FILE_ENTRY_THUMBNAIL_WIDTH %>;" />

											<span class="document-title"><%= HtmlUtil.escape(fileEntry.getTitle()) %></span>

											<span class="document-description"><%= HtmlUtil.escape(fileEntry.getDescription()) %></span>
										</a>

										<liferay-util:include page="/html/portlet/library_admin/file_entry_action.jsp" />

										<input class="overlay document-selector" name="<portlet:namespace /><%= RowChecker.ROW_IDS %>" type="checkbox" value="<%= fileEntry.getFileEntryId() %>" />
									</div>
								</c:otherwise>
							</c:choose>
						</c:when>

						<c:otherwise>
							<div style="float: left; margin: 100px 10px 0px;">
								<img alt="<liferay-ui:message key="image" />" border="no" src="<%= themeDisplay.getPathThemeImages() %>/application/forbidden_action.png" />
							</div>
						</c:otherwise>
					</c:choose>
				</c:when>

				<c:when test="<%= (curFolder != null) %>">

					<%
					PortletURL tempRowURL = liferayPortletResponse.createRenderURL();

					tempRowURL.setParameter("struts_action", "/library_admin/view");
					tempRowURL.setParameter("redirect", currentURL);
					tempRowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));

					String src = themeDisplay.getPathThemeImages() + "/file_system/large/folder_full_document.png";

					request.setAttribute("view_entries.jsp-folder", curFolder);
					request.setAttribute("view_entries.jsp-folderId", String.valueOf(curFolder.getFolderId()));
					request.setAttribute("view_entries.jsp-repositoryId", String.valueOf(curFolder.getRepositoryId()));
					%>

					<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" varImpl="viewEntriesURL">
						<portlet:param name="struts_action" value="/library_admin/view" />
						<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
						<portlet:param name="showSiblings" value="<%= Boolean.TRUE.toString() %>" />
						<portlet:param name="viewAddButton" value="<%= Boolean.TRUE.toString() %>" />
						<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
						<portlet:param name="viewFolders" value="<%= Boolean.TRUE.toString() %>" />
					</liferay-portlet:resourceURL>

					<c:choose>
						<c:when test='<%= displayStyle.equals("icon") %>'>
							<div class="document-display-style icon">
								<input class="overlay document-selector" name="<portlet:namespace /><%= RowChecker.ROW_IDS %>" type="checkbox" value="<%= curFolder.getFolderId() %>" />

								<liferay-util:include page="/html/portlet/library_admin/folder_action.jsp" />

								<a class="document-link" data-isFolder="<%= Boolean.TRUE.toString() %>" data-resource-url="<%= viewEntriesURL.toString() %>" href="<%= tempRowURL.toString() %>" title="<%= HtmlUtil.escape(curFolder.getName()) + " - " + HtmlUtil.escape(curFolder.getDescription()) %>">
									<img border="no" class="document-thumbnail" src="<%= src %>" style="height: <%= PropsValues.DL_FILE_ENTRY_THUMBNAIL_HEIGHT %>; width: <%= PropsValues.DL_FILE_ENTRY_THUMBNAIL_WIDTH %>;" />

									<span class="document-title">
										<%= HtmlUtil.escape(StringUtil.shorten(curFolder.getName(), 60)) %>
									</span>
								</a>
							</div>
						</c:when>

						<c:otherwise>
							<div class="document-display-style descriptive">
								<a class="document-link" data-isFolder="<%= Boolean.TRUE.toString() %>" data-resource-url="<%= viewEntriesURL.toString() %>" href="<%= tempRowURL.toString() %>" title="<%= HtmlUtil.escape(curFolder.getName()) + " - " + HtmlUtil.escape(curFolder.getDescription()) %>">
									<img border="no" class="document-thumbnail" src="<%= src %>" style="width: <%= PropsValues.DL_FILE_ENTRY_THUMBNAIL_WIDTH %>;" />

									<span class="document-title"><%= HtmlUtil.escape(curFolder.getName()) %></span>

									<span class="document-description"><%= HtmlUtil.escape(curFolder.getDescription()) %></span>
								</a>

								<liferay-util:include page="/html/portlet/library_admin/folder_action.jsp" />

								<input class="overlay document-selector" name="<portlet:namespace /><%= RowChecker.ROW_IDS %>" type="checkbox" value="<%= curFolder.getFolderId() %>" />
							</div>
						</c:otherwise>
					</c:choose>
				</c:when>
			</c:choose>
		</c:when>

		<c:otherwise>
			<c:choose>
				<c:when test="<%= fileEntry != null %>">
					<liferay-util:buffer var="fileEntryTitle">
						<liferay-ui:icon
							image='<%= "../file_system/small/" + fileEntry.getExtension() %>'
							label="<%= true %>"
							message="<%= fileEntry.getTitle() %>"
						/>
					</liferay-util:buffer>

					<%
					List resultRows = searchContainer.getResultRows();

					ResultRow row = new ResultRow(fileEntry, fileEntry.getFileEntryId(), i);

					PortletURL rowURL = liferayPortletResponse.createRenderURL();

					rowURL.setParameter("struts_action", "/library_admin/view_file_entry");
					rowURL.setParameter("redirect", currentURL);
					rowURL.setParameter("fileEntryId", String.valueOf(fileEntry.getFileEntryId()));

					row.addText(fileEntryTitle, rowURL);

					row.addText(fileEntry.getDescription(), rowURL);

					row.addText(TextFormatter.formatKB(fileEntry.getSize(), locale) + "k");

					row.addText(dateFormatDateTime.format(fileEntry.getCreateDate()));

					row.addText(dateFormatDateTime.format(fileEntry.getModifiedDate()));

					row.addText(String.valueOf(fileEntry.getReadCount()));

					row.addJSP("/html/portlet/library_admin/file_entry_action.jsp");

					resultRows.add(row);
					%>

				</c:when>
				<c:when test="<%= curFolder != null %>">
					<liferay-util:buffer var="folderTitle">
						<liferay-ui:icon
							image="folder"
							label="<%= true %>"
							message="<%= curFolder.getName() %>"
						/>
					</liferay-util:buffer>

					<%
					List resultRows = searchContainer.getResultRows();

					ResultRow row = new ResultRow(curFolder, curFolder.getPrimaryKey(), i);

					PortletURL rowURL = liferayPortletResponse.createRenderURL();

					rowURL.setParameter("struts_action", "/library_admin/view");
					rowURL.setParameter("redirect", currentURL);
					rowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));

					row.addText(folderTitle, rowURL);

					row.addText(curFolder.getDescription(), rowURL);

					row.addText(String.valueOf(0) + "k");

					row.addText(dateFormatDateTime.format(curFolder.getCreateDate()));

					row.addText(dateFormatDateTime.format(curFolder.getModifiedDate()));

					row.addText(String.valueOf(0));

					row.addJSP("/html/portlet/library_admin/folder_action.jsp");

					resultRows.add(row);
					%>

				</c:when>
			</c:choose>
		</c:otherwise>
	</c:choose>

<%
}
%>

<c:if test='<%= displayStyle.equals("list") %>'>
	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
</c:if>

<c:if test='<%= !displayStyle.equals("list") && !results.isEmpty() %>'>
	<div class="taglib-search-iterator-page-iterator-top">
		<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
	</div>
</c:if>