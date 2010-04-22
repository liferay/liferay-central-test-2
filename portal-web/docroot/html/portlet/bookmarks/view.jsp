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

<%@ include file="/html/portlet/bookmarks/init.jsp" %>

<%
String topLink = ParamUtil.getString(request, "topLink", "bookmarks-home");

BookmarksFolder folder = (BookmarksFolder)request.getAttribute(WebKeys.BOOKMARKS_FOLDER);

long defaultFolderId = GetterUtil.getLong(preferences.getValue("rootFolderId", StringPool.BLANK), BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", defaultFolderId);

if ((folder == null) && (defaultFolderId != BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {
	try {
		folder = BookmarksFolderLocalServiceUtil.getFolder(folderId);
	}
	catch (NoSuchFolderException nsfe) {
		folderId = BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}
}

int foldersCount = BookmarksFolderLocalServiceUtil.getFoldersCount(scopeGroupId, folderId);
int entriesCount = BookmarksEntryLocalServiceUtil.getEntriesCount(scopeGroupId, folderId);

long categoryId = ParamUtil.getLong(request, "categoryId");
String tagName = ParamUtil.getString(request, "tag");

String categoryName = null;
String vocabularyName = null;

if (categoryId != 0) {
	AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getAssetCategory(categoryId);

	categoryName = assetCategory.getName();

	AssetVocabulary assetVocabulary = AssetVocabularyLocalServiceUtil.getAssetVocabulary(assetCategory.getVocabularyId());

	vocabularyName = assetVocabulary.getName();
}

boolean useAssetEntryQuery = (categoryId > 0)  || Validator.isNotNull(tagName);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/bookmarks/view");
portletURL.setParameter("topLink", topLink);
portletURL.setParameter("folderId", String.valueOf(folderId));

request.setAttribute("view.jsp-folder", folder);

request.setAttribute("view.jsp-folderId", String.valueOf(folderId));

request.setAttribute("view.jsp-viewFolder", Boolean.TRUE.toString());

request.setAttribute("view.jsp-useAssetEntryQuery", String.valueOf(useAssetEntryQuery));
%>

<liferay-util:include page="/html/portlet/bookmarks/top_links.jsp" />

<c:choose>
	<c:when test="<%= useAssetEntryQuery %>">
		<c:if test="<%= Validator.isNotNull(categoryName) %>">
			<h1 class="entry-title">
				<%= LanguageUtil.format(pageContext, "bookmarks-with-x-x", new String[] {vocabularyName, categoryName}) %>
			</h1>
		</c:if>

		<c:if test="<%= Validator.isNotNull(tagName) %>">
			<h1 class="entry-title">
				<%= LanguageUtil.format(pageContext, "bookmarks-with-tag-x", tagName) %>
			</h1>
		</c:if>

		<%@ include file="/html/portlet/bookmarks/view_entries.jspf" %>

		<%
		if (portletName.equals(PortletKeys.BOOKMARKS)) {
			PortalUtil.addPageKeywords(tagName, request);
			PortalUtil.addPageKeywords(categoryName, request);
		}
		%>

	</c:when>
	<c:when test='<%= topLink.equals("bookmarks-home") %>'>
		<aui:layout>
			<c:if test="<%= folder != null %>">
				<h3 class="folder-title"><%= folder.getName() %></h3>
			</c:if>

			<aui:column columnWidth="<%= 75 %>" cssClass="folder-column folder-column-first" first="<%= true %>">
				<liferay-ui:panel-container extended="<%= false %>" id="bookmarksPanelContainer" persistState="<%= true %>">
					<c:if test="<%= folder != null %>">
						<div class="folder-description">
							<%= folder.getDescription() %>
						</div>

						<div class="folder-metadata">
							<div class="folder-date">
								<%= LanguageUtil.format(pageContext, "last-updated-x", dateFormatDate.format(folder.getModifiedDate())) %>
							</div>

							<div class="folder-subfolders">
								<%= foldersCount %> <liferay-ui:message key='<%= (foldersCount == 1) ? "subfolder" : "subfolders" %>' />
							</div>

							<div class="folder-entries">
								<%= entriesCount %> <liferay-ui:message key='<%= (entriesCount == 1) ? "entry" : "entries" %>' />
							</div>
						</div>

						<div class="custom-attributes">
							<liferay-ui:custom-attributes-available className="<%= BookmarksFolder.class.getName() %>">
								<liferay-ui:custom-attribute-list
									className="<%= BookmarksFolder.class.getName() %>"
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
								headerNames="folder,num-of-folders,num-of-entries"
								iteratorURL="<%= portletURL %>"
							>
								<liferay-ui:search-container-results
									results="<%= BookmarksFolderLocalServiceUtil.getFolders(scopeGroupId, folderId, searchContainer.getStart(), searchContainer.getEnd()) %>"
									total="<%= BookmarksFolderLocalServiceUtil.getFoldersCount(scopeGroupId, folderId) %>"
								/>

								<liferay-ui:search-container-row
									className="com.liferay.portlet.bookmarks.model.BookmarksFolder"
									escapedModel="<%= true %>"
									keyProperty="folderId"
									modelVar="curFolder"
								>
									<liferay-portlet:renderURL varImpl="rowURL">
										<portlet:param name="struts_action" value="/bookmarks/view" />
										<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
									</liferay-portlet:renderURL>
									<%@ include file="/html/portlet/bookmarks/folder_columns.jspf" %>
								</liferay-ui:search-container-row>

								<liferay-ui:search-iterator />
							</liferay-ui:search-container>
						</liferay-ui:panel>
					</c:if>

					<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="entriesPanel" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "bookmarks") %>'>
						<%@ include file="/html/portlet/bookmarks/view_entries.jspf" %>
					</liferay-ui:panel>
				</liferay-ui:panel-container>
			</aui:column>

			<aui:column columnWidth="<%= 25 %>" cssClass="detail-column detail-column-last" last="<%= true %>">
				<div class="folder-icon">
					<liferay-ui:icon
						cssClass="folder-avatar"
						image='<%= "../file_system/large/" + (((foldersCount + entriesCount) > 0) ? "folder_full_bookmark" : "folder_empty") %>'
						message='<%= folder != null ? folder.getName() : LanguageUtil.get(pageContext, "bookmarks-home") %>'
					/>

					<div class="folder-name">
						<h4><%= folder != null ? folder.getName() : LanguageUtil.get(pageContext, "bookmarks-home") %></h4>
					</div>
				</div>

				<%
				request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
				%>

				<liferay-util:include page="/html/portlet/bookmarks/folder_action.jsp" />
			</aui:column>
		</aui:layout>

		<%
		if (folder != null) {
			BookmarksUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);

			if (portletName.equals(PortletKeys.BOOKMARKS)) {
				PortalUtil.setPageSubtitle(folder.getName(), request);
				PortalUtil.setPageDescription(folder.getDescription(), request);
			}
		}
		%>

	</c:when>
	<c:when test='<%= topLink.equals("my-entries") || topLink.equals("recent-entries") %>'>
		<aui:layout>
			<h3 class="folder-title"><liferay-ui:message key="<%= topLink %>" /></h3>

			<liferay-ui:search-container
				headerNames="folder,num-of-folders,num-of-entries"
				iteratorURL="<%= portletURL %>"
			>

				<%
				long groupEntriesUserId = 0;

				if (topLink.equals("my-entries") && themeDisplay.isSignedIn()) {
					groupEntriesUserId = user.getUserId();
				}
				%>

				<liferay-ui:search-container-results
					results="<%= BookmarksEntryLocalServiceUtil.getGroupEntries(scopeGroupId, groupEntriesUserId, searchContainer.getStart(), searchContainer.getEnd()) %>"
					total="<%= BookmarksEntryLocalServiceUtil.getGroupEntriesCount(scopeGroupId, groupEntriesUserId) %>"
				/>

				<liferay-ui:search-container-row
					className="com.liferay.portlet.bookmarks.model.BookmarksEntry"
					escapedModel="<%= true %>"
					keyProperty="entryId"
					modelVar="entry"
				>

					<%
					String rowHREF = themeDisplay.getPathMain().concat("/bookmarks/open_entry?entryId=").concat(String.valueOf(entry.getEntryId()));
					%>

					<liferay-ui:search-container-column-text
						href="<%= rowHREF %>"
						name="entry"
						property="name"
						target="_blank"
						title="<%= entry.getComments() %>"
					/>

					<liferay-ui:search-container-column-text
						href="<%= rowHREF %>"
						name="url"
						property="url"
						target="_blank"
						title="<%= entry.getComments() %>"
					/>

					<liferay-ui:search-container-column-text
						href="<%= rowHREF %>"
						name="visits"
						property="visits"
						target="_blank"
						title="<%= entry.getComments() %>"
					/>

					<liferay-ui:search-container-column-jsp
						align="right"
						path="/html/portlet/bookmarks/entry_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator />
			</liferay-ui:search-container>
		</aui:layout>

		<%
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, topLink), currentURL);

		PortalUtil.setPageSubtitle(LanguageUtil.get(pageContext, StringUtil.replace(topLink, StringPool.UNDERLINE, StringPool.DASH)), request);
		%>

	</c:when>
</c:choose>