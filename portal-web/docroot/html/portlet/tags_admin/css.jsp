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

.portlet-tags-admin .ui-tag {
	float: left;
	margin-bottom: 2px;
	width: 20%;
}

.portlet-tags-admin .tags-container {
	clear: both;
	float: left;
	width: 100%;
}

.portlet-tags-admin .dragging .ui-tag {
	background: #D3DADD;
}

.portlet-tags-admin .dragging .active-area {
	background: #FFC;
	border-color: #fc0;
}

.portlet-tags-admin .tag {
	cursor: move;
}

.portlet-tags-admin .tags-category {
	clear: both;
}

/* ---------- Treeview ---------- */

.portlet-tags-admin .treeview {
	cursor: pointer;
	font-size: 13px;
}

.portlet-tags-admin .treeview .hitarea {
	cursor: pointer;
	float: left;
	height: 1px;
	margin-left: -16px;
	width: 16px;
}

.portlet-tags-admin .treeview .hover {
	cursor: pointer;
}

.portlet-tags-admin .treeview li.lastCollapsable, .treeview li.lastExpandable {
	background-image: none;
}

.portlet-tags-admin .treeview .placeholder {
	background: url(<%= themeImagesPath %>/trees/ajax_loader.gif) 0 0 no-repeat;
	display: block;
	height: 16px;
	width: 16px;
}

.portlet-tags-admin .filetree li {
	padding: 4px 0 5px 16px;
}

.portlet-tags-admin .filetree span.folder {
	background: url(<%= themeImagesPath %>/trees/folder.png) 0 0 no-repeat;
}

.portlet-tags-admin .filetree li.expandable span.folder {
	background: url(<%= themeImagesPath %>/trees/folder_closed.png) 0 0 no-repeat;
}

.portlet-tags-admin .filetree span.file {
	background: url(<%= themeImagesPath %>/trees/file.gif) 0 0 no-repeat;
}

.portlet-tags-admin .treeview .hover-up {
	border-bottom: 2px solid black;
}

.portlet-tags-admin .treeview .hover-down {
	border-top: 2px solid black;
}

.portlet-tags-admin .treeview .hover-folder {
	background: #FFC url(<%= themeImagesPath %>/trees/folder.png) no-repeat scroll 0 0 !important;
}