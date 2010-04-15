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

<%@ include file="/html/portlet/css_init.jsp" %>

<%@ page import="com.liferay.portal.kernel.util.StringUtil" %>

<liferay-util:buffer var="html">
	<liferay-util:include page="/html/portlet/document_library/css.jsp" />
</liferay-util:buffer>

<%
html = StringUtil.replace(html, "documentLibraryPanelContainer", "imageGalleryPanelContainer");
html = StringUtil.replace(html, "file-entries", "images");
html = StringUtil.replace(html, "file-entry", "image");
html = StringUtil.replace(html, "portlet-document-library", "portlet-image-gallery");
%>

<%= html %>

.lfr-image-gallery-actions {
	font-size: 11px;
	text-align: right;
}

.portlet-image-gallery .image-score {
	display: block;
	margin: 0 0 5px 35px;
	padding-top: 3px;
}

.portlet-image-gallery .image-thumbnail {
	border: 2px solid #eee;
	float: left;
	margin: 20px 4px 0;
	padding: 5px 5px 0;
	text-align: center;
	text-decoration: none;
}

.portlet-image-gallery .image-thumbnail:hover {
	border-color: #ccc;
}

.portlet-image-gallery .image-date {
	border-width: 0;
	float: none;
}

.portlet-image-gallery .image-download .image-name {
	top: 0;
}

.portlet-image-gallery .image-title {
	display: block;
}