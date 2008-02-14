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

.portlet-wiki a.createpage {
	color: red;
}

.portlet-wiki .toc {
	border: 1px solid #aaa;
	background-color: #f9f9f9;
	padding: 5px;
}

.portlet-wiki .toc ul {
 	margin-top: 0px;
}
.portlet-wiki .toc li.toclevel-1 {
	margin-left: 0px;
	list-style-type: decimal;
}

.portlet-wiki .toc li.toclevel-2 {
	margin-left: 15px;
}

.portlet-wiki .toc li.toclevel-3 {
	margin-left: 30px;
}

.portlet-wiki .taglib-tags-summary {
	margin: -10px 0px 10px 0px;
	color: #7d7d7d;
}

.portlet-wiki h1.wiki-page-title {
	border-bottom: 1px solid #AAAAAA;
	margin: 0px;
	padding-bottom: 5px;
}

.portlet-wiki .preview {
	background: #ffc;
	border: 1px dotted gray;
	padding: 3px;
}

.portlet-wiki .wiki-body .wiki-code {
	background: #fff;
	border: 1px solid #777;
	font-family: monospace;
	white-space: pre;
}

.portlet-wiki .wiki-body .wiki-code-lines {
	border-right: 1px solid #ccc;
	color: #000;
	margin-right: 5px;
	padding: 0px 5px 0px 5px;
}

.portlet-wiki .wiki-body a.wiki-external-link {
	background: transparent url(<%= themeImagesPath %>/wiki/external.png) right top no-repeat;
	text-decoration: none;
	padding-right: 10px;
}

.portlet-wiki .wiki-body a.wiki-external-link:hover {
	background: transparent url(<%= themeImagesPath %>/wiki/external.png) right top no-repeat;
	text-decoration: underline;
	padding-right: 11px;
}

.portlet-wiki .wiki-node-current {
	text-decoration: none;
	font-weight: bold;
}

.portlet-wiki .wiki-page-actions {
	float: right;
}

.portlet-wiki .wiki-page-actions a {
	text-decoration: none;
}

.portlet-wiki .wiki-page-actions a:hover {
	text-decoration: underline;
}

.portlet-wiki .wiki-page-info tr th, .portlet-wiki .wiki-page-info tr td {
	border: 1px solid #ccc;
	border-left: none;
	border-right: none;
	padding: 5px;
}

.portlet-wiki .wiki-page-old-version a {
	color: #ff9933;
}

.portlet-wiki .wiki-page-old-version {
	color: #ff9933;
	line-height: 1.2em;
	margin: -1em 0pt 1.4em 0em;
	width: auto;
}

.portlet-wiki .wiki-page-redirect {
	color: #7d7d7d;
	line-height: 1.2em;
	margin: -1em 0pt 1.4em 0em;
	width: auto;
}

.portlet-wiki .wiki-popup-print {
	float: right;
}

.portlet-wiki .wiki-syntax-help {
	border: 1px dotted gray;
	padding-left: 10px;
}

.portlet-wiki .wiki-top-links {
	padding-bottom: 10px;
}

.portlet-wiki .wiki-top-links table {
	width: 100%;
}