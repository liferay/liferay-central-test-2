<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

.portlet-wiki .wiki-body pre {
	background : #fff;
	border: 1px dashed #2f6fab;
	margin: 5px 0px 5px 0px;
	padding: 0.5em;
}

.portlet-wiki a.createpage {
	color: #f00;
}

.portlet-wiki .toc {
	border: 1px solid #aaa;
	background-color: #f9f9f9;
	padding: 10px;
}

.portlet-wiki .toc h4 {
	margin-bottom: 0.7em;
}

.portlet-wiki .toc ul {
 	margin-top: 0px;
}
.portlet-wiki .toc li.toclevel-1 {
	list-style-type: none;
	margin-left: 0px;
}

.portlet-wiki .toc li.toclevel-2 {
	list-style-type: none;
	margin-left: 15px;
}

.portlet-wiki .toc li.toclevel-3 {
	list-style-type: none;
	margin-left: 30px;
}

.portlet-wiki .page-categories {
	margin: -10px 0px 10px 0px;
	color: #7d7d7d;
}

.portlet-wiki .taglib-discussion {
	margin-top: 18px;
}

.portlet-wiki .page-tags {
	margin: -7px 0px 10px 0px;
	color: #7d7d7d;
}

.portlet-wiki h1, .portlet-wiki h2, .portlet-wiki h3 {
	border-bottom: 1px solid #aaa;
	margin-bottom: 0.5em;
	padding-bottom: 5px;
}

.portlet-wiki h1.page-title {
	clear: both;
	margin: 0 0 10px 0;
}

.portlet-wiki h1.page-title .return-to-page {
	background: url(<%= themeImagesPath %>/wiki/return_to_page.png) no-repeat 0 50%;
	padding-left: 20px;
	text-decoration: none;
}

.portlet-wiki h3 {
	border-bottom: 1px dotted #aaa;
}

.portlet-wiki .preview {
	background: #ffc;
	border: 1px dotted gray;
	padding: 3px;
}

.portlet-wiki .custom-attributes {
	margin-bottom: 1em;
}

.portlet-wiki .custom-attributes label {
	display: block;
	font-weight: bold;
}

.portlet-wiki .child-pages h3 {
	font-size: 1.2em;
	margin-bottom: 0.3em;
}

.ie .portlet-wiki .child-pages h3 {
	margin-bottom: 0.2em;
}

.portlet-wiki .child-pages ul {
	margin-top: 0;
}

.portlet-wiki .child-pages li {
	font-weight: bold;
	font-size: 1.1em;
}

.portlet-wiki .content-body .wiki-code {
	background: #fff;
	border: 1px solid #777;
	font-family: monospace;
	white-space: pre;
}

.portlet-wiki .content-body .code-lines {
	border-right: 1px solid #ccc;
	color: #000;
	margin-right: 5px;
	padding: 0px 5px 0px 5px;
}

.portlet-wiki .content-body a.external-link {
	background: transparent url(<%= themeImagesPath %>/wiki/external.png) right top no-repeat;
	text-decoration: none;
	padding-right: 10px;
}

.portlet-wiki .content-body a.external-link:hover {
	background: transparent url(<%= themeImagesPath %>/wiki/external.png) right top no-repeat;
	text-decoration: underline;
	padding-right: 11px;
}

.portlet-wiki .history-navigation .central-info a.change-mode {
	font-weight: bold;
}

.portlet-wiki .history-navigation .central-info span.change-mode {
	color: #999;
	font-weight: bold;
}

.portlet-wiki .history-navigation .central-info {
	float: left;
	padding-left: 10px;
	padding-right: 10px;
	text-align: center;
	width: 66%;
}

.portlet-wiki .history-navigation .central-title {
	font-weight: bold;
	padding-right: 20px;
}

.portlet-wiki .history-navigation .central-username {
	font-weight: bold;
}

.portlet-wiki .history-navigation .next {
	background-image: url(<%= themeImagesPath %>/arrows/paging_next.png);
	background-position: 100% 0;
	float: right;
	padding-right: 15px;
}

.portlet-wiki .history-navigation .previous {
	background-image: url(<%= themeImagesPath %>/arrows/paging_previous.png);
	float: left;
	padding-left: 15px;
}

.portlet-wiki .history-navigation a, .portlet-wiki .history-navigation span {
	background: url() no-repeat;
}

.portlet-wiki .history-navigation span.next {
	background-position: 100% 100%;
}

.portlet-wiki .history-navigation span.previous {
	background-position: 0 100%;
}

.portlet-wiki .history-navigation {
	background: #eee;
	border-top: 1px solid #ccc;
	margin: 15px 0 20px;
	overflow: hidden;
	padding: 5px;
}

.portlet-wiki .node-current {
	text-decoration: none;
	font-weight: bold;
}

.portlet-wiki .page-actions {
	margin-top: 1.5em;
}

.portlet-wiki .page-actions .article-actions {
	border-right: 1px solid #999;
	float: left;
	margin-right: 10px;
	padding-right: 10px;
}

.portlet-wiki .page-actions .stats {
	color: #999;
}

.portlet-wiki .page-title .page-actions {
	float: right;
	margin-top: 0;
}

.portlet-wiki .page-title .page-actions a {
	text-decoration: none;
}

.portlet-wiki .page-actions a:hover {
	text-decoration: underline;
}

.portlet-wiki .page-title .page-actions {
	font-size: 11px;
	font-weight: normal;
}

.portlet-wiki .page-title .page-actions img {
	margin-left: 5px;
}

.portlet-wiki .page-info {
	width: 100%;
}

.portlet-wiki .page-info tr th, .portlet-wiki .page-info tr td {
	border: 1px solid #ccc;
	border-left: none;
	border-right: none;
	padding: 0.3em 1em;
}

.portlet-wiki .page-old-version a {
	color: #f93;
}

.portlet-wiki .page-old-version {
	color: #f93;
	line-height: 1.2em;
	margin: -1em 0pt 1.4em 0em;
	width: auto;
}

.portlet-wiki .page-redirect {
	color: #7d7d7d;
	cursor: pointer;
	line-height: 1.2em;
	margin: -1em 0pt 1.4em 0em;
	width: auto;
}

.portlet-wiki .page-redirect:hover {
	text-decoration: underline;
}

.portlet-wiki .popup-print {
	float: right;
}

.portlet-wiki .syntax-help {
	border: 1px dotted gray;
	padding-left: 10px;
}

.portlet-wiki .syntax-help h4 {
	margin-bottom: 0.5em;
}
.ie .portlet-wiki .syntax-help h4 {
	margin-bottom: 0.3em;
}

.portlet-wiki .syntax-help pre {
	margin-left: 1em;
	margin-bottom: 1em;
}

.portlet-wiki .top-links {
	padding-bottom: 10px;
}

.portlet-wiki .top-links {
	float: right;
}

.portlet-wiki .top-links .top-link {
	padding-right: 0.5em;
	border-right: 1px solid #bbb;
}

.portlet-wiki .top-links-container {
	clear: both;
	overflow: hidden;
}

.portlet-wiki .top-links-nodes {
	border: 1px solid #ddd;
	float: left;
	margin: 0 0.5em 0.5em;
	padding: 0.2em 0.5em;
}

.portlet-wiki .top-links .page-search {
	float: right;
	margin: 0 0 0.5em 0.5em;
}


.portlet-wiki .top-links .top-link.last {
	border: none;
}

.portlet-wiki .top-links .top-links-navigation, .portlet-wiki .top-links-configuration {
	float: left;
}

.portlet-wiki .subscription-info tr td {
	border: none;
	padding: 0.1em 10px 0.1em 0;
}