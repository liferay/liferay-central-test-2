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

.portlet-asset-category-admin .column,
.portlet-asset-category-admin .vocabulary-item-column {
	display: inline-block;
	letter-spacing: normal;
	vertical-align: top;
	word-spacing: normal;
	zoom: 1;
}

.ie .portlet-asset-category-admin .column,
.ie .portlet-asset-category-admin .vocabulary-item-column {
	*display: inline;
}

.portlet-asset-category-admin .vocabulary-categories-container,
.portlet-asset-category-admin .vocabulary-container,
.portlet-asset-category-admin .vocabulary-content-wrapper,
.vocabulary-item {
	width: 100%;
}

.portlet-asset-category-admin .vocabulary-container .results-header {
	background: #F0F5F7;
	font-weight: bold;
	margin: 2px 0;
	padding: 5px 10px;
	position: relative;
}

.portlet-asset-category-admin .vocabulary-content {
	letter-spacing: -0.31em;
	padding-left: 150px;
	padding-right: 0px;
	word-spacing: -0.43em;
}

.ie .portlet-asset-category-admin .vocabulary-content {
	*letter-spacing: normal;
}


.portlet-asset-category-admin .vocabulary-item-actions {
	margin-right: -20px;
	width: 20px;
	vertical-align: middle;
}

.portlet-asset-category-admin .vocabulary-item-actions-container {
	display: inline-block;
	padding-left: 2px
}

.portlet-asset-category-admin .vocabulary-item-actions-trigger {
	background-image: url(<%= themeImagesPath %>/common/edit.png);
	background-repeat: no-repeat;
	display: inline-block;
	height: 20px;
	width: 20px;
}

.ie .portlet-asset-category-admin .vocabulary-item-actions-trigger {
	*cursor: pointer;
}

.portlet-asset-category-admin .vocabulary-container .results-row .vocabulary-item-actions a {
	padding: 0 0 0 0;
}

.portlet-asset-category-admin .vocabulary-item-column {
	vertical-align: middle;
}

.portlet-asset-category-admin .vocabulary-item-container {
	letter-spacing: -0.31em;
	padding-right: 20px;
	padding-left: 0px;
	word-spacing: -0.43em;
}

.ie .portlet-asset-category-admin .vocabulary-item-container {
	*letter-spacing: normal;
}

.portlet-asset-category-admin .vocabulary-list-container {
	margin-left: -150px;
	width: 150px;
}

.portlet-asset-category-admin .vocabulary-list-container .results-header {
	background: #d3dadd;
}

.portlet-asset-category-admin .vocabulary-categories-container .results-header {
	background: #AEB9BE;
}

.portlet-asset-category-admin .vocabulary-edit-category .results-header {
	background: #6F7D83;
	color: #fff;
}

.portlet-asset-category-admin .vocabulary-content-edit-category {
	padding-right: 350px;
}

.portlet-asset-category-admin .vocabulary-content li.vocabulary-category {
	padding: 1px 0;
}

.portlet-asset-category-admin .vocabulary-content li.vocabulary-category,
.portlet-asset-category-admin li.vocabulary-item,
.vocabulary-item.portlet-asset-category-admin-helper {
	border-bottom: 1px solid #D3D7DB;
	font-weight: bold;
	list-style: none;
}

.portlet-asset-category-admin .vocabulary-item.alt {
	background: #F0F2F4;
}

.portlet-asset-category-admin .vocabulary-container .results-row a,
.vocabulary-item.results-row a {
	padding: 8px 0 8px 10px;
}

.portlet-asset-category-admin .vocabulary-list a {
	display: block;
	text-decoration: none;
}

.ie .portlet-asset-category-admin .vocabulary-list a {
	zoom: 1;
}

.portlet-asset-category-admin .vocabulary-item a,
.vocabulary-item.portlet-asset-category-admin-helper a {
	display: block;
	padding-left: 20px;
}

.portlet-asset-category-admin .vocabulary-item.selected,
.vocabulary-item.portlet-asset-category-admin-helper.selected {
	background: #aeb9be;
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

.portlet-asset-category-admin .vocabulary-category-item.selected > span {
	font-weight: bold;
}

.portlet-asset-category-admin .vocabulary-list .selected a,
.portlet-asset-category-admin .vocabulary-list .selected .vocabulary-content-wrapper,
.portlet-asset-category-admin .vocabulary-list .selected .vocabulary-content-wrapper:hover {
	background: #6F7D83;
	color: #fff;
}

.portlet-asset-category-admin .vocabulary-categories .active-area {
	background: #ffc;
}

.portlet-asset-category-admin .vocabulary-categories {
	border-bottom: 1px solid #D3D7DB;
	border-right: 1px solid #D3D7DB;
	height: 300px;
	overflow: auto;
}

.portlet-asset-category-admin .vocabulary-list {
	border-bottom: 1px #D3D7DB solid;
	border-left: 1px #D3D7DB solid;
	border-right: 1px #D3D7DB solid;
	height: 300px;
	overflow: auto;
	overflow-x: hidden;
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

.portlet-asset-category-admin .vocabulary-buttons {
	float: left;
	min-width: 220px;
	padding: 5px 0px 5px;
}

.portlet-asset-category-admin .vocabulary-actions {
	float: right;
}

.portlet-asset-category-admin .vocabulary-buttons .button {
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

.portlet-asset-category-admin .vocabulary-buttons .selected {
	background-color: #CFD5D7;
	color: #0F0F0F;
}

.portlet-asset-category-admin .vocabulary-edit-category {
	margin-right: -350px;
	width: 350px;
}

.portlet-asset-category-admin .vocabulary-edit-category .category-view {
	height: 300px;
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

.portlet-asset-category-admin .category-view-toolbar {
	padding-left: 0.5em;
}

.portlet-asset-category-admin .vocabulary-property-row {
	white-space: nowrap;
}

.portlet-asset-category-admin .vocabulary-footer {
	border-top: 1px solid #dedede;
	margin-top: 5px;
	padding: 10px 0 0;
}

.asset-category-layer-wrapper {
	display: none;
}

.aui-widget-bd .asset-category-layer {
	padding: 10px;
	text-align: left;
}

.aui-widget-bd .asset-category-layer .aui-field-content {
	margin-bottom: 10px;
}

.aui-widget-bd .asset-category-layer label {
	display: block;
	font-weight: bold;
}

.aui-widget-bd .asset-category-layer .aui-field input, .aui-widget-bd .asset-category-layer .aui-field select {
	width: 200px;
}

.ie6 .aui-widget-bd .asset-category-layer .aui-field, .ie6 .aui-widget-bd .asset-category-layer .aui-field {
	width: 200px;
}

.aui-widget-bd .asset-category-layer .button-holder {
	margin-top: 10px;
}

.aui-tree-node .aui-tree-node-selected .aui-tree-label {
    background-color: #6F7D83;
    color: #FFFFFF;
}

.asset-category-layer .aui-overlay {
	overflow: visible;
	width: 230px;
}

.portlet-asset-category-admin #vocabulary-category-messages {
	margin: 10px;
	position: absolute;
}

.portlet-asset-category-admin .aui-tree-node-selected .aui-tree-label {
	cursor: move;
}

.portlet-asset-category-admin .vocabulary-treeview-container {
	padding: 5px;
}

.portlet-asset-category-admin .vocabulary-container .category-name {
	width: 300px;
}

.portlet-asset-category-admin #vocabulary-search-input {
	background-image: url(<%= themeImagesPath %>/common/search.png);
	background-repeat: no-repeat;
	background-position: 5px 50%;
	padding-left: 25px;
	width: 250px;
}

.lfr-panel-container {
	background-color: none;
	border: none;
}

.lfr-position-helper {
	z-index: 10000;
}

.permissions-change .aui-dialog-bd {
	overflow: hidden;
}

.permissions-container {
	height: 100%;
	position: relative;
	width: 100%;
}

.permissions-container-mask {
	height: 100%;
	position: absolute;
	width: 100%
}