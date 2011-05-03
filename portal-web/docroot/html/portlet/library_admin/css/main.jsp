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

<%@ include file="/html/portlet/css_init.jsp" %>

.portlet-document-library .file-entry-list-description {
	font-style: italic;
	margin-left: 10px;
}

.portlet-document-library .file-entry-tags {
	margin-top: 5px;
}

.portlet-document-library .document-container, .portlet-document-library .document-entries-paginator {
	clear: both;
}

.portlet-document-library .select-documents input{
	left: 4px;
	position: absolute;
	top: 4px;
}

.portlet-document-library .select-documents {
	border-top-width: 3px;
	margin-left: 2em;
	min-height: 22px;
	min-width: 20px;
	position: relative;
}

.portlet-document-library .folder:hover {
	background-color: #ccecf9;
}

.portlet-document-library .folder {
	position: relative;
}

.portlet-document-library .expand-folder, .portlet-document-library .header-row-content .display-style {
	float: right;
}

.portlet-document-library .folder-search {
	float: right;
	margin: 0 0 0.5em 0.5em;
}

.portlet-document-library img.shortcut-icon {
	display: inline;
	margin-left: 10px;
	margin-top: 75px;
	position: absolute;
	z-index: 10;
}

.portlet-document-library .document-display-style.descriptive .document-title {
	display: block;
	font-weight: bold;
}

.portlet-document-library .document-display-style.descriptive .document-description {
	display: block;
}

.portlet-document-library .document-display-style.descriptive .document-thumbnail {
	float: left;
	margin: 5px 10px;
	text-align: center;
}

.portlet-document-library .document-display-style.icon .document-thumbnail {
	text-align: center;
}

.portlet-document-library .document-display-style.icon .document-action {
	overflow: hidden;
	position: absolute;
	right: 10px;
}

.portlet-document-library .document-display-style .document-action .direction-down{
	height: 20px;
}

.portlet-document-library .document-display-style.descriptive .document-action {
	height: 20px;
	overflow: hidden;
	position: absolute;
	right: 6px;
	top: 10px;
}

.portlet-document-library .document-display-style.descriptive {
	border-radius: 4px;
	display: block;
	height: 140px;
	margin: 5px;
	padding-bottom: 5px;
	padding-top: 5px;
	position: relative;
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
}

.portlet-document-library .document-display-style.descriptive .document-selector {
	bottom: 10px;
	margin-left: -20px;
	position: absolute;
	right: 10px;
}

.portlet-document-library .document-display-style.icon .document-selector {
	left: 10px;
	position: absolute;
	top: 10px;
}

.portlet-document-library .document-display-style.descriptive:hover .document-selector, .portlet-document-library .document-display-style.descriptive.selected .document-selector {
	clip: auto;
	margin-left: -20px;
	position: absolute;
}

.portlet-document-library .document-display-style.descriptive.selected, .portlet-document-library .document-display-style.descriptive.selected:hover, .portlet-document-library .body-row li.selected {
	background-color: #00a2ea;
	background-image: -moz-linear-gradient(top, #00a2ea, #0083bc);
	background-image: -webkit-gradient(linear,left top,left bottom,color-stop(0, #00a2ea),color-stop(1, #0083bc));
	filter: progid:DXImageTransform.Microsoft.gradient(startColorStr='#00a2ea', EndColorStr='#0083bc');
	-ms-filter: "progid:DXImageTransform.Microsoft.gradient(startColorStr='#00a2ea', EndColorStr='#0083bc')";
}

.portlet-document-library .document-display-style.selected a, .portlet-document-library .body-row li.selected a {
	color: #ffffff;
}

.portlet-document-library .document-display-style.icon.selected, .portlet-document-library .document-display-style.icon.selected:hover {
	background-color: #00a2ea;
	background-image: -moz-linear-gradient(top, #00a2ea, #0083bc);
	background-image: -webkit-gradient(linear,left top,left bottom,color-stop(0, #00a2ea),color-stop(1, #0083bc));
	filter: progid:DXImageTransform.Microsoft.gradient(startColorStr='#00a2ea', EndColorStr='#0083bc');
	-ms-filter: "progid:DXImageTransform.Microsoft.gradient(startColorStr='#00a2ea', EndColorStr='#0083bc')";
}

.portlet-document-library .document-display-style a {
	color: #333333;
}

.portlet-document-library .document-display-style.icon {
	border-radius: 4px;
	float: left;
	height: 170px;
	margin: 5px;
	padding-bottom: 5px;
	padding-top: 5px;
	position: relative;
	width: 200px;
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
}

.portlet-document-library .document-display-style.icon .document-link {
	display: block;
	min-height: 180px;
	text-align: center;
	text-decoration: none;
}

.portlet-document-library .header-row .aui-icon-display-icon .yui3-aui-icon {
	background: url(<%= themeImagesPath%>/library_admin/layouts.png) no-repeat -35px 0;
}

.portlet-document-library .header-row .aui-icon-edit .yui3-aui-icon {
	background: url(<%= themeImagesPath%>/common/edit.png) no-repeat 0 0;
}

.portlet-document-library .header-row .aui-icon-move .yui3-aui-icon {
	background: url(<%= themeImagesPath%>/common/submit.png) no-repeat 0 0;
}

.portlet-document-library .header-row .aui-icon-lock .yui3-aui-icon {
	background: url(<%= themeImagesPath%>/common/lock.png) no-repeat 0 0;
}

.portlet-document-library .header-row .aui-icon-unlock .yui3-aui-icon {
	background: url(<%= themeImagesPath%>/common/unlock.png) no-repeat 0 0;
}

.portlet-document-library .header-row .aui-icon-permissions .yui3-aui-icon {
	background: url(<%= themeImagesPath%>/common/permissions.png) no-repeat 0 0;
}

.portlet-document-library .header-row .aui-icon-display-descriptive .yui3-aui-icon {
	background: url(<%= themeImagesPath%>/library_admin/layouts.png) no-repeat -65px 0;
}

.portlet-document-library .header-row .aui-icon-display-list .yui3-aui-icon {
	background: url(<%= themeImagesPath%>/library_admin/layouts.png) no-repeat -4px 0;
}

.portlet-document-library .taglib-search-iterator .results-header input {
	display: none;
}

.portlet-document-library .document-display-style.descriptive .document-link {
	display: block;
	min-height: 150px;
	text-decoration: none;
}

.portlet-document-library .header-row-content .toolbar, .portlet-document-library .header-row-content .add-button, .portlet-document-library .body-row ul li a .icon {
	float: left;
}

.portlet-document-library .document-display-style .overlay.document-action a {
	display: block;
	float: right;
	min-height: 20px;
}

.portlet-document-library .document-display-style.icon .document-title {
	clear: both;
	display: block;
}

.portlet-document-library .document-display-style .overlay, .portlet-document-library .lfr-search-container .overlay {
	clip: rect(0 0 0 0);
}

.portlet-document-library .document-display-style:hover, .portlet-document-library .document-display-style.hover {
	background-color: #ccecf9;
}

.portlet-document-library .document-display-style.icon:hover .document-selector, .portlet-document-library .document-display-style.icon .document-selector, .portlet-document-library .document-display-style.icon.selected .document-selector {
	position: absolute;
}

.portlet-document-library .document-display-style:hover .overlay, .portlet-document-library .document-display-style.hover .overlay, .portlet-document-library .document-display-style.selected .document-selector {
	clip: auto;
}

.portlet-document-library .yui3-aui-button-holder.toolbar {
	display: inline;
	margin: 0;
}

.keywords {
	float: right;
	margin-left: 1em;
}

.portlet-document-library .body-row {
	height: 100%;
	overflow: hidden;
	position: relative;
	width: 100%;
}

.yui3-liferaylistview-data-container {
	background-color: #fafafa;
	height: 100%;
	position: absolute;
	width: 100%;
	z-index: 9999;
}

.yui3-liferaylistview-data-container-hidden {
	display: none;
}

.portlet-document-library .header-row {
	background-color: #DDD;
	margin-bottom: .5em;
	min-height: 34px;
}

.portlet-document-library .header-row-content {
	padding: 0.2em;
}

.portlet-document-library .view {
	border: 1px solid #CCC;
}

.portlet-document-library .view .view-content{
	background-color: #fafafa;
}

.portlet-document-library .context-pane {
	border-left: 1px solid #CCC;
}

.portlet-document-library .document-display-style.icon img.locked-icon {
	margin: 80px 45px;
	position: absolute;
}

.portlet-document-library .document-display-style.descriptive img.locked-icon {
	bottom: 10px;
	left: 90px;
	position: absolute;
}

.portlet-document-library .taglib-webdav {
	margin-top: 3em;
}

.portlet-document-library .taglib-workflow-status {
	margin-bottom: 5px;
}

.portlet-document-library .workflow-status-pending, .portlet-document-library .workflow-status-pending a {
	color: orange;
}

.portlet-document-library .body-row ul li a {
	color: #34404F;
	text-decoration: none;
}

.portlet-document-library .body-row ul .expand-folder {
	height: 10px;
	position: absolute;
	right: 2px;
	top: 5px;
	width: 16px;
}

.portlet-document-library .body-row ul .expand-folder .expand-folder-arrow {
	left: 2px;
	position: absolute;
	top: 1px;
}

.portlet-document-library .body-row ul .expand-folder:hover {
	background-color: #FFFFFF;
	border-radius: 20px;
	height: 10px;
	-moz-border-radius: 20px;
	-webkit-border-radius: 20px;
}

.portlet-document-library .body-row ul li.selected a {
	font-weight: bold;
}

.portlet-document-library .body-row ul, .portlet-document-library .body-row li {
	list-style: none outside none;
	margin: 0;
	padding: 0;
	width: 95%;
}

.portlet-document-library .body-row ul, .portlet-document-library .body-row li a {
	display: block;
	padding: 4px 0 4px 4px;
}

.portlet-document-library .body-row li.selected {
	background-color: #00a2ea;
	background-image: -moz-linear-gradient(top, #00a2ea, #0083bc);
	background-image: -webkit-gradient(linear,left top,left bottom,color-stop(0, #00a2ea),color-stop(1, #0083bc));
	filter: progid:DXImageTransform.Microsoft.gradient(startColorStr='#00a2ea', EndColorStr='#0083bc');
	-ms-filter: "progid:DXImageTransform.Microsoft.gradient(startColorStr='#00a2ea', EndColorStr='#0083bc')";
}

.portlet-document-library .body-row li {
	border-radius: 6px;
	-moz-border-radius: 6px;
	-webkit-border-radius: 6px;
}