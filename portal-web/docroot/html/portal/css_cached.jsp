<%
/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portal/init.jsp" %>

<%
response.setContentType(Constants.TEXT_CSS);
%>

/* ---------- Taglib discussion thread ---------- */

.taglib-discussion td img {
	vertical-align: middle;
}

/* ---------- Tree ---------- */

ul.gamma {
}

ul.gamma .expand-image {
}

ul.gamma a {
	text-decoration: none;
}

ul.gamma li {
	margin-bottom: 2px;
	padding-left: 0;
}

ul.gamma li ul {
}

ul.gamma li ul li, ul.gamma li.tree-item {
	padding-left: 0;
}

ul.gamma img {
	vertical-align: middle;
}

ul.gamma li.tree-item {
	padding-left: 5px;
}

ul.gamma li.tree-item a img {
	cursor: move;
}

ul.gamma li.tree-item li {
	padding-left: 20px;
}

ul.gamma li.tree-item ul {
	margin-left: 0;
	margin-top: 5px;
}

ul.gamma li.tree-item a, ul.gamma li.tree-item .expand-image {
	cursor: pointer;
}

ul.gamma .tree-item-hover {
	background: #7D93C1;
	padding: 5px;
}

.toggle-expand {
	padding-bottom: 10px;
}

.toggle-expand a {
	padding: 2px 0 2px 20px;
}

#lfr-expand {
	background: url(<%= themeDisplay.getPathThemeImages() %>/trees/expand_all.png) no-repeat 0 50%;
}

#lfr-collapse {
	background: url(<%= themeDisplay.getPathThemeImages() %>/trees/collapse_all.png) no-repeat 0 50%;
}

/* ---------- Portlets ---------- */

/* ---------- Alfresco content ---------- */
.portlet-alfresco-content.preview {
	border: 2px solid #FF0000; 
	padding: 8px;
}

/* ---------- Quick note ---------- */

.portlet-quick-note {
	margin: 2px;
	padding: 5px;
}

.portlet-quick-note textarea {
	min-height: 100px;
	padding: 3px;
	width: 95%;
}

.ie6 .portlet-quick-note textarea {
	height: expression(this.height < 100 ? '100px' : this.height);
}

.portlet-quick-note .note-color {
	border: 1px solid;
	cursor: pointer;
	float: left;
	font-size: 0;
	height: 10px;
	margin: 3px 5px;
	width: 10px;
}

.portlet-quick-note .note-color.yellow {
	background-color: #ffc;
	border-color: #fc0;
	margin-left: 0;
}

.portlet-quick-note .note-color.green {
	background-color: #cfc;
	border-color: #0c0;
}

.portlet-quick-note .note-color.blue {
	background-color: #ccf;
	border-color: #309;
}

.portlet-quick-note .note-color.red {
	background-color: #fcc;
	border-color: #f00;
}

.portlet-quick-note a.close-note {
	float: right;
}