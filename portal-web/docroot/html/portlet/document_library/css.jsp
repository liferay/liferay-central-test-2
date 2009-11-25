<%
/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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

.portlet-document-library .file-entry-avatar img {
	left: -45px;
	position: relative;
	top: -25px;
	width: 200px;
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
	overflow: auto;
	padding-left: 25px;
	padding-right: 10px;
}

.portlet-document-library .file-entry-description, .portlet-document-library .folder-description {
	margin: 0 auto 2em;
}

.portlet-document-library .file-entry-download .file-entry-name {
	position: relative;
	top: -30px;
}

.portlet-document-library .file-entry-download, .portlet-document-library .folder-icon {
	margin-bottom: 2em;
	text-align: center;
}

.portlet-document-library .file-entry-downloads {
	color: #999;
	overflow: auto;
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
	margin-bottom: 3em;
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

.portlet-document-library .folder-column #documentLibraryPanels, .portlet-document-library .file-entry-panels #documentPanels {
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
	overflow: auto;
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
	margin-top: 100px;
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

.portlet-document-library .top-links-container {
	clear: both;
	overflow: auto;
}

.portlet-document-library .top-links {
	float: right;
}

.portlet-document-library .top-links .top-link {
	border-right: 1px solid #bbb;
	padding-right: 0.5em;
}

.portlet-document-library .top-links .top-link.last {
	border-width: 0;
}

.portlet-document-library .top-links .top-links-navigation {
	float: left;
}