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
int abstractLength = (Integer)request.getAttribute(WebKeys.ASSET_PUBLISHER_ABSTRACT_LENGTH);
AssetRenderer assetRenderer = (AssetRenderer)request.getAttribute(WebKeys.ASSET_RENDERER);

DLFileEntry fileEntry = (DLFileEntry)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY);
%>

<div class="asset-resource-info">
	<a href="<%= assetRenderer.getURLViewInContext((LiferayPortletRequest)renderRequest, (LiferayPortletResponse)renderResponse, StringPool.BLANK) %>">
		<img align="left" alt="<liferay-ui:message key="<%= assetRenderer.getViewInContextMessage() %>" />" class="dl-file-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/small/<%= DLUtil.getFileIcon(fileEntry.getTitle()) %>.png">

		<%= fileEntry.getTitle() %>
	</a>
</div>

<p class="asset-description"><%= StringUtil.shorten(fileEntry.getDescription(), abstractLength) %></p>