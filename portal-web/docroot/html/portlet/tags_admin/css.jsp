<%
/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

.portlet-tags-admin .vocabulary-container {
	width: 100%;
}

.portlet-tags-admin .vocabulary-container .results-grid {
	padding: 0;
}

.portlet-tags-admin .vocabulary-container .results-header {
	font-weight: bold;
	margin: 2px 0;
	padding: 5px 10px;
}

.portlet-tags-admin .vocabulary-list-container {
	min-width: 180px;
}



.portlet-tags-admin .vocabulary-edit-entry .results-header {
	color: #fff;
	display: none;
}

.portlet-tags-admin .vocabulary-content td {
	vertical-align: top;
}

.portlet-tags-admin .vocabulary-content li.vocabulary-category {
	padding: 1px 0;
}

.portlet-tags-admin .vocabulary-content li.vocabulary-category, .portlet-tags-admin li.vocabulary-item {
	border-bottom: 1px solid #ccc;
	font-weight: bold;
	list-style: none;
}

.portlet-tags-admin .vocabulary-container .results-row a {
	padding: 8px 0 8px 10px;
}

.portlet-tags-admin .vocabulary-list a {
	display: block;
	text-decoration: none;
}

.ie .portlet-tags-admin .vocabulary-list a {
	zoom: 1;
}

.portlet-tags-admin .vocabulary-item a {
	display: block;
	padding-left: 20px;
}

.portlet-tags-admin .vocabulary-item.selected a {
	text-decoration: none;
}

.portlet-tags-admin .vocabulary-category-item.selected > span {
	font-weight: bold;
}

.portlet-tags-admin .vocabulary-entries {
	border-right: 1px solid #ccc;
	height: 300px;
	min-width: 200px;
	overflow: auto;
}

.portlet-tags-admin .vocabulary-list {
	border-left: 1px solid #ccc;
	border-right: 1px solid #ccc;
	height: 300px;
	overflow: auto;
	overflow-x: hidden;
}

.portlet-tags-admin .vocabulary-properties {
	width: 300px;
}

.portlet-tags-admin .ui-tags .nowrap {
	empty-cells: show;
	overflow: hidden;
	white-space: nowrap;
}

.portlet-tags-admin .vocabulary-search-bar {
	border: 1px solid #ccc;
	border-left: 0;
	border-right: 0;
	padding: 10px;
}

.portlet-tags-admin .vocabulary-toolbar {
	border-bottom: 1px solid #ccc;
}

.portlet-tags-admin .vocabulary-buttons {
	float: left;
	min-width: 220px;
	padding: 5px 0px 5px;
}

.portlet-tags-admin .vocabulary-actions {
	padding: 5px;
	text-align: right;
}

.portlet-tags-admin .vocabulary-actions input, .portlet-tags-admin .vocabulary-actions div, .portlet-tags-admin .vocabulary-actions select {
	margin-left: 5px;
}

.portlet-tags-admin .vocabulary-buttons .button {
	background: url(<%= themeImagesPath %>/common/page.png) no-repeat scroll 10px 50%;
	cursor: pointer;
	display: block;
	float: left;
	font-weight: bold;
	margin-right: 5px;
	min-width: 70px;
	padding: 5px 5px 5px 30px;
}

.portlet-tags-admin .vocabulary-buttons .selected {
	background-color: #ccc;
}

.portlet-tags-admin .vocabulary-edit-entry .vocabulary-edit {
	display: none;
	padding: 5px 5px 10px 10px;
}

.portlet-tags-admin .vocabulary-editing-tag .vocabulary-edit, .portlet-tags-admin .vocabulary-editing-tag .results-header {
	display: block;
}

.portlet-tags-admin div.vocabulary-close {
	text-align: right;
}

.portlet-tags-admin div.vocabulary-close span {
	cursor: pointer;
}

.portlet-tags-admin .vocabulary-property-row {
	display: none;
	white-space: nowrap;
}

.portlet-tags-admin .vocabulary-footer {
	border-top: 1px solid #ccc;
	margin-top: 5px;
	padding: 10px 0 0;
}

.portlet-tags-admin .vocabulary-name {
	display: none;
}

.portlet-tags-admin #vocabulary-entry-messages {
	margin: 10px;
}

.portlet-tags-admin .vocabulary-treeview-container {
	padding: 5px;
}

.portlet-tags-admin .vocabulary-container .entry-name {
	width: 100%;
}

.portlet-tags-admin #vocabulary-search-input {
	width: 250px;
}