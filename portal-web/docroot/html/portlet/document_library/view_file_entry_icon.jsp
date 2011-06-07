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
FileEntry fileEntry = (FileEntry)request.getAttribute("view_entries.jsp-fileEntry");

PortletURL tempRowURL = (PortletURL)request.getAttribute("view_entries.jsp-tempRowURL");

String thumbnailSrc = themeDisplay.getPathThemeImages() + "/file_system/large/" + DLUtil.getGenericName(fileEntry.getExtension()) + ".png";

if (PDFProcessorUtil.hasImages(fileEntry)) {
	thumbnailSrc = themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + themeDisplay.getScopeGroupId() + StringPool.SLASH + fileEntry.getFolderId() + StringPool.SLASH + HttpUtil.encodeURL(HtmlUtil.unescape(fileEntry.getTitle())) + "?version=" + fileEntry.getVersion() + "&thumbnail=1";
}
%>

<div class="document-display-style icon">
	<c:if test="<%= DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.DELETE) || DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE) %>">
		<aui:input inputCssClass="overlay document-selector" label="" name="<%= RowChecker.ROW_IDS %>" type="checkbox" value="<%= fileEntry.getFileEntryId() %>" />
	</c:if>

	<liferay-util:include page="/html/portlet/document_library/file_entry_action.jsp" />

	<a class="document-link" data-folder="<%= Boolean.FALSE.toString() %>" href="<%= tempRowURL.toString() %>" title="<%= HtmlUtil.escape(fileEntry.getTitle()) + " - " + HtmlUtil.escape(fileEntry.getDescription()) %>">
		<c:if test="<%= fileEntry.isLocked() %>">
			<img alt="<%= LanguageUtil.get(pageContext, "locked") %>" class="locked-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/large/overlay_lock.png">
		</c:if>

		<img border="no" class="document-thumbnail" src="<%= thumbnailSrc %>" style="height: <%= PropsValues.DL_FILE_ENTRY_THUMBNAIL_HEIGHT %>; width: <%= PropsValues.DL_FILE_ENTRY_THUMBNAIL_WIDTH %>;" />

		<span class="document-title">
			<%= HtmlUtil.escape(StringUtil.shorten(fileEntry.getTitle(), 60)) %>
		</span>
	</a>
</div>