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

.portlet-asset-category-admin .vocabulary-list-container-content, .portlet-asset-category-admin .vocabulary-categories-container-content, .portlet-asset-category-admin .vocabulary-edit-category-content {
	padding: 0;
}

.portlet-asset-category-admin .vocabulary-container .results-header {
	background: #F0F5F7;
	font-weight: bold;
	margin: 2px 0;
	padding: 5px 10px;
	position: relative;
}

.portlet-asset-category-admin .vocabulary-content-wrapper {
	position: relative;
}

.portlet-asset-category-admin .vocabulary-item a {
	padding: 8px 20px 8px 10px;
}

.portlet-asset-category-admin .vocabulary-content-wrapper, .portlet-asset-category-admin .header-title, .portlet-asset-category-admin .aui-tree-node-content .aui-tree-label  {
	word-wrap: break-word;
}

.portlet-asset-category-admin .vocabulary-item-actions-trigger {
	background-image: url(<%= themeImagesPath %>/common/edit.png);
	background-repeat: no-repeat;
	display: none;
	height: 16px;
	margin-top: -8px;
	padding: 0;
	position: absolute;
	right: 0;
	top: 50%;
	width: 16px;
}

.ie6 .portlet-asset-category-admin .vocabulary-item-actions-trigger {
	display: inline-block;
}

.portlet-asset-category-admin .vocabulary-category:hover .vocabulary-item-actions-trigger {
	display: inline-block;
}

.ie6 .portlet-asset-category-admin .vocabulary-item-actions-trigger, .ie7 .portlet-asset-category-admin .vocabulary-item-actions-trigger {
	cursor: pointer;
}

.portlet-asset-category-admin .vocabulary-container .results-row .vocabulary-item-actions a {
	padding: 0;
}

.portlet-asset-category-admin .vocabulary-list-container .results-header {
	background: #D3DADD;
}

.portlet-asset-category-admin .vocabulary-categories-container .results-header {
	background: #AEB9BE;
}

.portlet-asset-category-admin .vocabulary-edit-category .results-header {
	background: #6F7D83;
	color: #FFF;
}

.portlet-asset-category-admin .vocabulary-content li.vocabulary-category {
	padding: 1px 0;
}

.portlet-asset-category-admin .vocabulary-content li.vocabulary-category, .portlet-asset-category-admin li.vocabulary-item {
	font-weight: bold;
	list-style: none;
}

.portlet-asset-category-admin .vocabulary-item.alt {
	background: #F0F2F4;
}

.portlet-asset-category-admin .vocabulary-treeview-container {
	padding: 5px;
}

.portlet-asset-category-admin .vocabulary-item a {
	display: block;
	text-decoration: none;
}

.ie .portlet-asset-category-admin .vocabulary-item a {
	zoom: 1;
}

.portlet-asset-category-admin .vocabulary-list li {
	border: 1px solid transparent;
}

.portlet-asset-category-admin .vocabulary-item.selected {
	background: #AEB9BE;
}

.ie6 .portlet-asset-category-admin .vocabulary-treeview-container .vocabulary-item.selected {
	background: none;
}

.portlet-asset-category-admin .vocabulary-item.selected a {
	color: #000;
	text-decoration: none;
}

.portlet-asset-category-admin .vocabulary-list .selected a:hover {
	color: #FFB683;
}

.portlet-asset-category-admin .vocabulary-list .selected a, .portlet-asset-category-admin .vocabulary-list .selected .vocabulary-content-wrapper, .portlet-asset-category-admin .vocabulary-list .selected .vocabulary-content-wrapper:hover {
	background-color: #6F7D83;
	color: #FFF;
}

.portlet-asset-category-admin .vocabulary-categories {
	overflow: auto;
}

.portlet-asset-category-admin .vocabulary-list {
	overflow: auto;
	overflow-x: hidden;
}

.portlet-asset-category-admin .vocabulary-list .active-area {
	border: 1px solid #008000;
}

.portlet-asset-category-admin .vocabulary-list .active-area a {
	background-color: #90EE90;
}

.portlet-asset-category-admin .vocabulary-list .vocabulary-content-wrapper {
	background-color: #F5F5F5;
}

.portlet-asset-category-admin .vocabulary-list .vocabulary-content-wrapper:hover {
	background: #D3DADD;
}

.portlet-asset-category-admin .vocabulary-search-bar {
	float: left;
}

.portlet-asset-category-admin .vocabulary-toolbar {
	background: #F6F8FB;
	border-bottom: 1px solid #dedede;
	overflow: hidden;
	padding: 5px 0;
}

.portlet-asset-category-admin .vocabulary-actions {
	clear: none;
	float: right;
	margin: 0;
}

.portlet-asset-category-admin .vocabulary-edit-category .category-view {
	padding: 0 5px 0 0px;
}

.portlet-asset-category-admin .category-view-close {
	position: absolute;
	right: 2px;
	text-align: right;
	top: 4px;
}

.portlet-asset-category-admin .category-view-close span {
	cursor: pointer;
}

.portlet-asset-category-admin #vocabulary-category-messages {
	margin: 10px;
}

.portlet-asset-category-admin .vocabulary-treeview-container .aui-tree-label {
	cursor: pointer;
}

.portlet-asset-category-admin .vocabulary-treeview-container .aui-tree-label:hover {
	color: #06c;
}

.portlet-asset-category-admin .vocabulary-treeview-container .aui-tree-node-selected .aui-tree-label {
	background-color: #6F7D83;
	color: #FFF;
	cursor: move;
}

.portlet-asset-category-admin .vocabulary-container .category-name {
	width: 300px;
}

.portlet-asset-category-admin .vocabulary-search .aui-field-content, .portlet-asset-category-admin .vocabulary-select-search .aui-field-content {
	display: inline-block;
}

.portlet-asset-category-admin .vocabulary-search .aui-field-input {
	background-image: url(<%= themeImagesPath %>/common/search.png);
	background-repeat: no-repeat;
	background-position: 5px 50%;
	padding-left: 25px;
	width: 250px;
}

.portlet-asset-category-admin .view-category {
	margin: 1em;
}

.portlet-asset-category-admin .view-category label {
	display: block;
	font-weight: bold;
}

.portlet-asset-category-admin .view-category .category-field {
	clear: left;
	margin: 1em auto;
}

.portlet-asset-categories-admin-dialog .aui-fieldset {
	margin-bottom: 0;
}

.portlet-asset-categories-admin-dialog .lfr-panel-container {
	background-color: transparent;
	border-width: 0;
}

.portlet-asset-categories-admin-dialog .asset-category-layer .aui-overlay {
	overflow: visible;
	width: 230px;
}

.portlet-asset-categories-admin-dialog .aui-widget-bd .asset-category-layer {
	padding: 0 10px;
}

.portlet-asset-categories-admin-dialog .aui-widget-bd .asset-category-layer .aui-field-content {
	margin-bottom: 10px;
}

.portlet-asset-categories-admin-dialog .aui-widget-bd .asset-category-layer label {
	display: block;
	font-weight: bold;
}

.portlet-asset-categories-admin-dialog .aui-widget-bd .asset-category-layer .aui-field input, .portlet-asset-categories-admin-dialog .aui-widget-bd .asset-category-layer .aui-field select {
	width: 200px;
}

.portlet-asset-categories-admin-dialog .aui-widget-bd .asset-category-layer .button-holder {
	margin-top: 10px;
}

.ie6 .portlet-asset-categories-admin-dialog .aui-widget-bd .asset-category-layer .aui-field, .ie6 .portlet-asset-categories-admin-dialog .aui-widget-bd .asset-category-layer .aui-field {
	width: 200px;
}

.lfr-position-helper {
	z-index: 10000;
}