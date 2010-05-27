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

.portlet-document-library h3.file-entry-title, .portlet-document-library h3.folder-title {
	border-bottom: 1px solid #000;
	font-size: 14px;
	font-weight: 700;
	margin-top: 0;
}

.portlet-document-library .file-entry-column-first .file-entry-column-content {
	margin-right: 4em;
}

.portlet-document-library .detail-column-last {
	background-color: #D7F1FF;
	overflow: visible;
}

.portlet-document-library .detail-column-last .detail-column-content {
	border: 1px solid #88C5D9;
	padding: 0.7em;
}

.portlet-document-library .custom-attributes label {
	display: block;
	font-weight: bold;
}

.portlet-document-library .custom-attributes {
	margin-bottom: 1em;
}

.portlet-document-library .file-entry-author {
	background: url(<%= themeImagesPath %>/portlet/edit_guest.png) no-repeat 0 50%;
	border-right: 1px solid #999;
	color: #999;
	float: left;
	margin-right: 10px;
	padding-left: 25px;
	padding-right: 10px;
}

.portlet-document-library .file-entry-categories .asset-category {
	color: #555;
}

.portlet-document-library .file-entry-categories {
	color: #7D7D7D;
}

.portlet-document-library .file-entry-date, .portlet-document-library .folder-date {
	background: url(<%= themeImagesPath %>/common/date.png) no-repeat 0 50%;
	border-right: 1px solid #999;
	color: #999;
	float: left;
	margin-right: 10px;
	overflow: hidden;
	padding-left: 25px;
	padding-right: 10px;
}

.portlet-document-library .file-entry-description, .portlet-document-library .folder-description {
	margin: 0 auto 2em;
}

.portlet-document-library .file-entry-download, .portlet-document-library .folder-icon {
	margin-bottom: 2em;
	overflow: hidden;
	text-align: center;
}

.portlet-document-library .file-entry-downloads {
	color: #999;
	overflow: hidden;
	padding-left: 10px;
}

.portlet-document-library .file-entry-field label, .portlet-document-library .folder-field label {
	display: block;
	font-weight: bold;
}

.portlet-document-library .file-entry-field, .portlet-document-library .folder-field {
	clear: left;
	margin: 1em auto;
}

.portlet-document-library .file-entry-ratings {
	margin: 2em 0;
}

.portlet-document-library .file-entry-panels {
	clear: both;
	padding-top: 1em;
}

.portlet-document-library .file-entry-panels .version-history {
	margin-bottom: 2em;
}

.portlet-document-library .file-entry-tags .tag {
	color: #555;
}

.portlet-document-library .file-entry-tags {
	color: #7D7D7D;
}

.portlet-document-library .folder-avatar img {
	margin: 0 auto;
	padding-right: 2em;
}

.portlet-document-library .folder-column #documentLibraryPanelContainer, .portlet-document-library .file-entry-panels #documentPanelContainer {
	border-width: 0;
}

.portlet-document-library .folder-column #subFoldersPanel, .portlet-document-library .folder-column #documentsPanel {
	clear: both;
	margin-bottom: 1.5em;
}

.portlet-document-library .folder-column-content {
	margin-right: 1em;
	padding: 0;
}

.portlet-document-library .folder-metadata {
	clear: both;
}

.portlet-document-library .folder-search {
	float: right;
	margin: 0 0 0.5em 0.5em;
}

.portlet-document-library .folder-file-entries {
	background: url(<%= themeImagesPath %>/common/page.png) no-repeat 0 50%;
	color: #999;
	overflow: hidden;
	padding-left: 25px;
}

.portlet-document-library .folder-subfolders {
	background: url(<%= themeImagesPath %>/common/folder.png) no-repeat 0 50%;
	border-right: 1px solid #999;
	color: #999;
	float: left;
	margin-right: 10px;
	padding-left: 25px;
	padding-right: 10px;
}

.portlet-document-library img.shortcut-icon {
	display: inline;
	margin-left: 10px;
	margin-top: 75px;
	position: absolute;
	z-index: 10;
}

.portlet-document-library img.locked-icon {
	display: inline;
	margin: 95px 0 0 130px;
	position: absolute;
	z-index: 10;
}

.portlet-document-library .taglib-webdav {
	margin-top: 3em;
}