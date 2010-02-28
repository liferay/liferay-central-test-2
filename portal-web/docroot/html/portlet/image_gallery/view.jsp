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

<%@ include file="/html/portlet/image_gallery/init.jsp" %>

<%
String topLink = ParamUtil.getString(request, "topLink", "image-home");

IGFolder folder = (IGFolder)request.getAttribute(WebKeys.IMAGE_GALLERY_FOLDER);

long defaultFolderId = GetterUtil.getLong(preferences.getValue("rootFolderId", StringPool.BLANK), IGFolderConstants.DEFAULT_PARENT_FOLDER_ID);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", defaultFolderId);

if ((folder == null) && (defaultFolderId != IGFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {
	try {
		folder = IGFolderLocalServiceUtil.getFolder(folderId);
	}
	catch (NoSuchFolderException nsfe) {
		folderId = IGFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}
}

int foldersCount = IGFolderLocalServiceUtil.getFoldersCount(scopeGroupId, folderId);
int imagesCount = IGImageLocalServiceUtil.getImagesCount(scopeGroupId, folderId);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/image_gallery/view");
portletURL.setParameter("topLink", topLink);
portletURL.setParameter("folderId", String.valueOf(folderId));

request.setAttribute("view.jsp-folder", folder);

request.setAttribute("view.jsp-folderId", String.valueOf(folderId));

request.setAttribute("view.jsp-portletURL", portletURL);

request.setAttribute("view.jsp-viewFolder", Boolean.TRUE.toString());
%>

<liferay-util:include page="/html/portlet/image_gallery/top_links.jsp" />

<c:choose>
	<c:when test='<%= topLink.equals("image-home") %>'>
		<aui:layout>
			<c:if test="<%= folder != null %>">
				<h3 class="folder-title"><%= folder.getName() %></h3>
			</c:if>

			<aui:column columnWidth="<%= 75 %>" cssClass="folder-column folder-column-first" first="<%= true %>">
				<liferay-ui:panel-container extended="<%= false %>" id="imageGalleryPanelContainer" persistState="<%= true %>">
					<c:if test="<%= folder != null %>">
						<div class="folder-description">
							<%= folder.getDescription() %>
						</div>

						<div class="folder-metadata">
							<div class="folder-date">
								<%= LanguageUtil.format(pageContext, "last-updated-x", dateFormatDate.format(folder.getModifiedDate())) %>
							</div>

							<div class="folder-subfolders">
								<%= foldersCount %> <liferay-ui:message key="subfolders" />
							</div>

							<div class="folder-images">
								<%= imagesCount %> <liferay-ui:message key="images" />
							</div>
						</div>

						<div class="custom-attributes">
							<liferay-ui:custom-attributes-available className="<%= IGFolder.class.getName() %>">
								<liferay-ui:custom-attribute-list
									className="<%= IGFolder.class.getName() %>"
									classPK="<%= (folder != null) ? folder.getFolderId() : 0 %>"
									editable="<%= false %>"
									label="<%= true %>"
								/>
							</liferay-ui:custom-attributes-available>
						</div>
					</c:if>

					<c:if test="<%= foldersCount > 0 %>">
						<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="subFoldersPanel" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, folder != null ? "subfolders" : "folders") %>'>
							<liferay-util:include page="/html/portlet/image_gallery/view_folders.jsp" />
						</liferay-ui:panel>
					</c:if>

					<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="entriesPanel" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "images") %>'>

						<%
						SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "cur2", SearchContainer.DEFAULT_DELTA, portletURL, null, null);

						int total = IGImageLocalServiceUtil.getImagesCount(scopeGroupId, folderId);

						searchContainer.setTotal(total);

						List results = IGImageLocalServiceUtil.getImages(scopeGroupId, folderId, searchContainer.getStart(), searchContainer.getEnd());

						searchContainer.setResults(results);

						List scores = null;
						%>

						<%@ include file="/html/portlet/image_gallery/view_images.jspf" %>

					</liferay-ui:panel>
				</liferay-ui:panel-container>
			</aui:column>

			<aui:column columnWidth="<%= 25 %>" cssClass="detail-column detail-column-last" last="<%= true %>">
				<div class="folder-icon">
					<liferay-ui:icon
						cssClass="folder-avatar"
						image='<%= "../file_system/large/" + (((foldersCount + imagesCount) > 0) ? "folder_full_image" : "folder_empty") %>'
						message='<%= folder != null ? folder.getName() : LanguageUtil.get(pageContext, "image-home") %>'
					/>

					<div class="folder-name">
						<h4><%= folder != null ? folder.getName() : LanguageUtil.get(pageContext, "image-home") %></h4>
					</div>
				</div>

				<%
				request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
				%>

				<liferay-util:include page="/html/portlet/image_gallery/folder_action.jsp" />
			</aui:column>
		</aui:layout>

		<%
		if (folder != null) {
			IGUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);

			if (portletName.equals(PortletKeys.IMAGE_GALLERY)) {
				PortalUtil.setPageSubtitle(folder.getName(), request);
				PortalUtil.setPageDescription(folder.getDescription(), request);
			}
		}
		%>

	</c:when>
	<c:when test='<%= topLink.equals("my-images") || topLink.equals("recent-images") %>'>

		<%
		long groupImagesUserId = 0;

		if (topLink.equals("my-images") && themeDisplay.isSignedIn()) {
			groupImagesUserId = user.getUserId();
		}

		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, null, null);

		int total = IGImageLocalServiceUtil.getGroupImagesCount(scopeGroupId, groupImagesUserId);

		searchContainer.setTotal(total);

		List results = IGImageLocalServiceUtil.getGroupImages(scopeGroupId, groupImagesUserId, searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);
		%>

		<aui:layout>
			<h3 class="folder-title"><liferay-ui:message key="<%= topLink %>" /></h3>

			<%
			List scores = null;
			%>

			<%@ include file="/html/portlet/image_gallery/view_images.jspf" %>
		</aui:layout>

		<%
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, topLink), currentURL);

		PortalUtil.setPageSubtitle(LanguageUtil.get(pageContext, topLink), request);
		%>

	</c:when>
</c:choose>