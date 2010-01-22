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

.portlet-asset-publisher .asset-abstract .asset-content p {
	margin-bottom: 0;
}

.portlet-asset-publisher .asset-abstract .asset-description {
	clear: left;
}

.portlet-asset-publisher .asset-content .asset-description {
	clear: left;
	font-style: italic;
}

.portlet-asset-publisher .asset-content {
	clear: right;
	margin-bottom: 10px;
	margin-left: 25px;
	margin-right: 10px;
}

.portlet-asset-publisher .asset-flag {
	margin-top: 1.8em;
}

.portlet-asset-publisher .asset-full-content .asset-content {
	margin-right: 25px;
}

.portlet-asset-publisher .asset-full-content.show-asset-title .asset-content {
	margin-right: 10px;
}

.portlet-asset-publisher .asset-full-content.no-title .asset-user-actions{
	padding-bottom: 2em;
}

.portlet-asset-publisher .asset-edit {
	float: right;
	margin-left: 1em;
}

.portlet-asset-publisher .asset-metadata span {
	float: left;
}

.portlet-asset-publisher .asset-metadata .metadata-categories span {
	float: none;
}

.portlet-asset-publisher .asset-metadata {
	clear: both;
	margin-left: 25px;
	overflow: hidden;
}

.ie .portlet-asset-publisher .asset-metadata {
	height: 1%;
}

.portlet-asset-publisher .asset-more {
	clear: left;
}

.portlet-asset-publisher .asset-entries-group-label {
	background-color: #EEE;
	clear: both;
	padding: 3px;
}

.portlet-asset-publisher .asset-ratings {
	float: left;
}

.portlet-asset-publisher .asset-small-image {
	float: right;
	padding-left: 0.5em;
}

.portlet-asset-publisher .asset-title .asset-actions img {
	margin-left: 5px;
}

.portlet-asset-publisher .asset-actions {
	float: right;
	font-size: 11px;
	font-weight: normal;
	margin-bottom: 3px;
	margin-top: 0;
}

.portlet-asset-publisher .asset-title a {
	text-decoration: none;
}

.portlet-asset-publisher .asset-title a:hover {
	text-decoration: underline;
}

.portlet-asset-publisher .asset-title {
	background-image: url(<%= themeImagesPath %>/common/page.png);
	background-position: 0 50%;
	background-repeat: no-repeat;
	border-bottom: 1px solid #DDD;
	margin-bottom: 0.7em;
	margin-right: 8px;
	margin-top: 2em;
	padding-bottom: 1px;
	padding-left: 25px;
}

.portlet-asset-publisher .asset-user-actions .export-actions, .portlet-asset-publisher .asset-user-actions .print-action, .portlet-asset-publisher .asset-user-actions .locale-actions {
	float: right;
}

.portlet-asset-publisher .asset-user-actions .locale-separator {
	border-right: 1px solid #CCC;
	float: right;
	margin-right: 1em;
	padding: 0.8em 0.5em;
}

.portlet-asset-publisher .asset-user-actions .print-action {
	margin: 0 1em;
}

.portlet-asset-publisher .blog {
	background-image: url(<%= PortalUtil.getPathContext() %>/html/icons/blogs.png);
}

.portlet-asset-publisher .bookmark {
	background-image: url(<%= themeImagesPath %>/ratings/star_hover.png);
}

.portlet-asset-publisher .content {
	background-image: url(<%= themeImagesPath %>/common/history.png);
}

.portlet-asset-publisher .dl-file-icon {
	margin: 0 5px 0 0;
	padding-bottom: 0.5em;
}

.portlet-asset-publisher .document {
	background-image: url(<%= themeImagesPath %>/common/clip.png);
}

.portlet-asset-publisher .edit-controls {
	margin-bottom: 20px;
}

.portlet-asset-publisher .final-separator {
	border: 0;
	margin-bottom: 30px;
}

.portlet-asset-publisher .image {
	background-image: url(<%= PortalUtil.getPathContext() %>/html/icons/image_gallery.png);
}

.portlet-asset-publisher .lfr-meta-actions {
	margin-right: 1em;
	padding-top: 0;
}

.portlet-asset-publisher .metadata-author {
	background: url(<%= themeImagesPath %>/portlet/edit_guest.png) no-repeat 0 0;
	float: left;
	font-weight: bold;
	margin-right: 10px;
	padding-left: 25px;
}

.portlet-asset-publisher .metadata-entry {
	color: #999;
}

.portlet-asset-publisher .metadata-modified-date, .portlet-asset-publisher .metadata-create-date, .portlet-asset-publisher .metadata-publish-date, .portlet-asset-publisher .metadata-expiration-date {
	background: url(<%= themeImagesPath %>/common/date.png) no-repeat 0 0;
	color: #999;
	margin-bottom: 1em;
	padding-left: 25px;
}

.portlet-asset-publisher .metadata-priority {
	background: url(<%= themeImagesPath %>/common/top.png) no-repeat 0 20%;
	margin-right: 10px;
	padding-left: 25px;
}

.portlet-asset-publisher .metadata-view-count {
	margin-right: 10px;
}

.portlet-asset-publisher .separator {
	border-right: 1px solid #999;
	clear: both;
	margin: 25px 25px;
}

.portlet-asset-publisher .taglib-asset-categories-summary {
	float: left;
}

.portlet-asset-publisher .taglib-asset-tags-summary {
	float: left;
}

.portlet-asset-publisher .message {
	background-image: url(<%= PortalUtil.getPathContext() %>/html/icons/message_boards.png);
}

.portlet-asset-publisher .title-list .asset-actions {
	left: 10px;
	position: relative;
}

.portlet-asset-publisher .title-list .asset-metadata {
	padding: 0;
}

.portlet-asset-publisher .title-list a {
	float: left;
}

.portlet-asset-publisher .vertical-separator {
	border-right: 1px solid #999;
	float: left;
	margin: 0 10px;
	padding: 7px 0;
}

.portlet-asset-publisher .wiki {
	background-image: url(<%= themeImagesPath %>/common/pages.png);
}

.portlet-asset-publisher li.title-list {
	background-position: 0 0;
	background-repeat: no-repeat;
	clear: both;
	list-style: none;
	margin-bottom: 0.15em;
	margin-right: 8px;
	padding-bottom: 1px;
	padding-left: 25px;
}

.portlet-asset-publisher ul.title-list {
	margin-left: 1em;
}