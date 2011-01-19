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

.portlet-asset-tag-admin .tags-admin-container {
	border-bottom: 1px solid #D3D7DB;
	width: 100%;
}

.portlet-asset-tag-admin .tags-admin-container .results-header {
	background: #AEB9BE;
	font-weight: bold;
	margin: 2px 0;
	padding: 5px 10px;
}

.ie6 .portlet-asset-tag-admin .tags-admin-container {
	width: 300px;
}

.portlet-tags-admin .vocabulary-entries-container .results-header {
	background: #AEB9BE;
}

.portlet-asset-tag-admin .tag-edit-container .results-header {
	background: #6F7D83;
	color: #fff;
	display: none;
}

.portlet-asset-tag-admin .tags-admin-content td {
	vertical-align: top;
}

.portlet-asset-tag-admin li.tag-item, .tag-item.portlet-tags-admin-helper {
	border-bottom: 1px solid #D3D7DB;
	font-weight: bold;
	list-style: none;
}

.portlet-asset-tag-admin .tag-item.alt {
	background: #F0F2F4;
}

.portlet-asset-tag-admin .tags-admin-container .results-row a, .tag-item.results-row a {
	padding: 8px 0 8px 10px;
}

.portlet-asset-tag-admin .tag-item a, .tag-item.portlet-tags-admin-helper a {
	display: block;
	padding-left: 20px;
}

.portlet-asset-tag-admin .tag-item.selected, .tag-item.portlet-tags-admin-helper {
	background: #aeb9be;
}

.portlet-asset-tag-admin .tag-item.selected a {
	color: #000;
	text-decoration: none;
}

.portlet-asset-tag-admin .tag-category-item.selected > span {
	font-weight: bold;
}

.portlet-asset-tag-admin .tags .active-area {
	background: #ffc;
}

.portlet-asset-tag-admin .tags .yui-dd-dragging {
	visibility: hidden;
}

.portlet-asset-tag-admin .tag-container {
	border-bottom: 1px #D3D7DB solid;
	border-left: 1px #D3D7DB solid;
	border-right: 1px #D3D7DB solid;
}

.portlet-asset-tag-admin .tags {
	height: 300px;
	overflow: auto;
	overflow-x: hidden;
}

.portlet-asset-tag-admin .tags a:hover {
	background: #D3DADD;
}

.portlet-asset-tag-admin .tags-admin-search-bar {
	float: left;
}

.portlet-asset-tag-admin .tags-admin-toolbar {
	background: #F6F8FB;
	border-bottom: 1px solid #dedede;
	overflow: hidden;
	padding: 5px 0;
}

.portlet-asset-tag-admin .tags-admin-actions {
	float: right;
}

.portlet-asset-tag-admin .tag-buttons .button {
	background: url(<%= themeImagesPath %>/common/page.png) no-repeat scroll 10px 50%;
	color: #9EA8AD;
	cursor: pointer;
	display: block;
	float: left;
	font-weight: bold;
	margin-right: 5px;
	min-width: 70px;
	padding: 5px 5px 5px 30px;
}

.portlet-asset-tag-admin .tag-buttons .selected {
	background-color: #CFD5D7;
	color: #0F0F0F;
}

.portlet-asset-tag-admin .tag-edit-container .tag-edit {
	border-right: 1px solid #D3D7DB;
	padding: 5px 5px 10px 10px;
}

.portlet-asset-tag-admin .tag-editing .tag-edit, .portlet-asset-tag-admin .tag-editing .results-header {
	display: block;
}

.portlet-asset-tag-admin div.tag-close {
	text-align: right;
}

.portlet-asset-tag-admin div.tag-close span {
	cursor: pointer;
}

.portlet-asset-tag-admin .tag-property-row {
	white-space: nowrap;
}

.portlet-asset-tag-admin .tag-footer {
	border-top: 1px solid #dedede;
	margin-top: 5px;
	padding: 10px 0 0;
}

.portlet-asset-tag-admin .view-tag {
	margin: 1em;
}

.portlet-asset-tag-admin .view-tag label {
	display: block;
	font-weight: bold;
}

.portlet-asset-tag-admin .view-tag .tag-field {
	clear: left;
	margin: 1em auto;
}

.add-tag-layer-wrapper {
	display: none;
}

.aui-widget-bd .add-tag-layer {
	padding: 10px;
	text-align: left;
}

.aui-widget-bd .add-tag-layer .aui-field {
	margin-bottom: 10px;
}

.aui-widget-bd .add-tag-layer label {
	display: block;
	font-weight: bold;
}

.aui-widget-bd .add-tag-layer .aui-field input, .aui-widget-bd .add-tag-layer .aui-field select {
	width: 200px;
}

.ie6 .add-tag-layer .aui-widget-bd .aui-field, .ie6 .add-tag-layer .aui-widget-bd .aui-field {
	width: 200px;
}

.aui-widget-bd .add-tag-layer .aui-button-holder {
	margin-top: 10px;
}

.portlet-asset-tag-admin #tag-messages {
	margin: 10px;
}

.portlet-asset-tag-admin .tags-admin-container .tag-name {
	width: 300px;
}

.portlet-asset-tag-admin #tags-admin-search-input {
	background-image: url(<%= themeImagesPath %>/common/search.png);
	background-repeat: no-repeat;
	background-position: 5px 50%;
	padding-left: 25px;
	width: 250px;
}