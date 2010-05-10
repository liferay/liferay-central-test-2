<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
%>

<%@ include file="/html/portlet/css_init.jsp" %>

.portlet-wiki .wiki-body pre {
	background : #fff;
	border: 1px dashed #2f6fab;
	margin: 5px 0;
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
 	margin-top: 0;
}
.portlet-wiki .toc li.toclevel-1 {
	list-style-type: none;
	margin-left: 0;
}

.portlet-wiki .toc li.toclevel-2 {
	list-style-type: none;
	margin-left: 15px;
}

.portlet-wiki .toc li.toclevel-3 {
	list-style-type: none;
	margin-left: 30px;
}

.portlet-wiki .page-categorization {
	margin: -8px 0 10px;
}

.portlet-wiki .page-categories {
	color: #7d7d7d;
}

.portlet-wiki .taglib-discussion {
	margin-top: 18px;
}

.portlet-wiki .page-tags {
	color: #7d7d7d;
}

.portlet-wiki .portlet-body h1, .portlet-wiki .portlet-body h2, .portlet-wiki .portlet-body h3 {
	border-bottom: 1px solid #aaa;
	margin-bottom: 0.5em;
	padding-bottom: 5px;
}

.portlet-wiki .portlet-body h1.page-title {
	clear: both;
	margin: 0 0 10px;
}

.portlet-wiki .portlet-body h1.page-title .return-to-page {
	background: url(<%= themeImagesPath %>/wiki/return_to_page.png) no-repeat 0 50%;
	padding-left: 20px;
	text-decoration: none;
}

.portlet-wiki .portlet-body h3 {
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
	padding: 0 5px;
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
	margin: -1em 0 1.4em 0;
	width: auto;
}

.portlet-wiki .page-redirect {
	color: #7d7d7d;
	cursor: pointer;
	line-height: 1.2em;
	margin: -1em 0 1.4em 0;
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

.portlet-wiki .top-links-configuration {
	float: left;
}

.portlet-wiki .subscription-info tr td {
	border: none;
	padding: 0.1em 10px 0.1em 0;
}

.portlet-wiki .wiki-page-status {
	background: url(<%= themeImagesPath %>/common/recent_changes.png) no-repeat 0 50%;
	color: #999;
	margin-right: 5px;
	padding: 2px 0 2px 20px;
}

.portlet-wiki .wiki-page-status-approved {
	color: green;
}

.portlet-wiki .wiki-page-status-draft {
	color: blue;
}

.portlet-wiki .wiki-page-status-expired {
	color: red;
}

.portlet-wiki .wiki-page-status-pending {
	color: orange;
}

.portlet-wiki .wiki-page-version {
	background: url(<%= themeImagesPath %>/common/pages.png) no-repeat 0 50%;
	color: #999;
	margin-right: 5px;
	padding: 2px 20px;
}