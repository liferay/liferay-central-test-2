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

.portlet-asset-tags-admin .tags-admin-container {
	border-bottom: 1px solid #D3D7DB;
	width: 100%;
}

.portlet-asset-tags-admin .tags-admin-container .results-header {
	background: #AEB9BE;
	font-weight: bold;
	margin: 2px 0;
	padding: 5px 10px;
}

.ie6 .portlet-asset-tags-admin .tags-admin-container {
	width: 300px;
}

.portlet-tags-admin .vocabulary-entries-container .results-header {
	background: #AEB9BE;
}

.portlet-asset-tags-admin .tag-edit-container .results-header {
	background: #6F7D83;
	color: #fff;
	display: none;
}

.portlet-asset-tags-admin .tags-admin-content td {
	vertical-align: top;
}

.portlet-asset-tags-admin li.tag-item, .tag-item.portlet-tags-admin-helper {
	border-bottom: 1px solid #D3D7DB;
	font-weight: bold;
	list-style: none;
}

.portlet-asset-tags-admin .tag-item.alt {
	background: #F0F2F4;
}

.portlet-asset-tags-admin .tags-admin-container .results-row a, .tag-item.results-row a {
	padding: 8px 0 8px 10px;
}

.portlet-asset-tags-admin .tag-item a, .tag-item.portlet-tags-admin-helper a {
	display: block;
	padding-left: 20px;
}

.portlet-asset-tags-admin .tag-item.selected, .tag-item.portlet-tags-admin-helper {
	background: #aeb9be;
}

.portlet-asset-tags-admin .tag-item.selected a {
	color: #000;
	text-decoration: none;
}

.portlet-asset-tags-admin .tag-category-item.selected > span {
	font-weight: bold;
}

.portlet-asset-tags-admin .tags .active-area {
	background: #ffc;
}

.portlet-asset-tags-admin .tags .yui-dd-dragging {
	visibility: hidden;
}

.portlet-asset-tags-admin .tag-container {
	border-bottom: 1px #D3D7DB solid;
	border-left: 1px #D3D7DB solid;
	border-right: 1px #D3D7DB solid;
}

.portlet-asset-tags-admin .tags {
	height: 300px;
	overflow: auto;
	overflow-x: hidden;
}

.portlet-asset-tags-admin .tags a:hover {
	background: #D3DADD;
}

.portlet-asset-tags-admin .ui-tags .nowrap {
	empty-cells: show;
	overflow: hidden;
	white-space: nowrap;
}

.portlet-asset-tags-admin .tags-admin-search-bar {
	background: #F0F2F4;
	border: 1px solid #D3D7DB;
	border-left: 0;
	border-right: 0;
	padding: 10px;
}

.portlet-asset-tags-admin .tags-admin-toolbar {
	background: #F6F8FB;
	border-bottom: 1px solid #dedede;
}

.portlet-asset-tags-admin .tags-admin-actions {
	padding: 5px;
	text-align: right;
}

.portlet-asset-tags-admin .tag-buttons .button {
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

.portlet-asset-tags-admin .tag-buttons .selected {
	background-color: #CFD5D7;
	color: #0F0F0F;
}

.portlet-asset-tags-admin .tag-edit-container .tag-edit {
	border-right: 1px solid #D3D7DB;
	padding: 5px 5px 10px 10px;
}

.portlet-asset-tags-admin .tag-editing .tag-edit, .portlet-asset-tags-admin .tag-editing .results-header {
	display: block;
}

.portlet-asset-tags-admin div.tag-close {
	text-align: right;
}

.portlet-asset-tags-admin div.tag-close span {
	cursor: pointer;
}

.portlet-asset-tags-admin .tag-property-row {
	white-space: nowrap;
}

.portlet-asset-tags-admin .tag-footer {
	border-top: 1px solid #dedede;
	margin-top: 5px;
	padding: 10px 0 0;
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

.portlet-asset-tags-admin #tag-messages {
	margin: 10px;
}

.portlet-asset-tags-admin .tags-admin-container .tag-name {
	width: 300px;
}

.portlet-asset-tags-admin #tags-admin-search-input {
	width: 250px;
}