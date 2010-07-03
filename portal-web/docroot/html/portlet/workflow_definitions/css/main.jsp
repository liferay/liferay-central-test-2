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

.asset-column-details .asset-title .asset-actions img {
	margin-left: 5px;
}

.asset-column-details .asset-actions {
	float: right;
	font-size: 11px;
	font-weight: normal;
	margin-bottom: 3px;
	margin-top: 0;
}

.asset-column-details .task-activity {
	padding: 5px 5px 5px 25px;
}

.asset-column-details .task-activity-date {
	font-weight: bold;
}

.asset-column-details .asset-assigned, .asset-column-details .asset-due-date, .asset-column-details .task-type-1, .asset-column-details .task-type-2, .asset-column-details .task-type-3, .asset-column-details .asset-date, .asset-column-details .asset-status {
	background: url() no-repeat 0 50%;
}

.asset-column-details .asset-assigned {
	background-image: url(<%= themeImagesPath %>/common/assign.png);
	margin-right: 10px;
	padding-left: 25px;
	padding-right: 10px;
}

.asset-column-details h3.task-content-title {
	margin-top: 0;
	border-bottom: 1px solid #ddd;
}

.asset-column-details .task-panel-container .lfr-panel-content {
	padding: 0.7em;
}

.asset-column-details .task-content-actions {
	float: right;
}

.asset-column-details .asset-date {
	background: url(<%= themeImagesPath %>/common/date.png) no-repeat 0 50%;
}

.asset-column-details .asset-due-date {
	background-image: url(<%= themeImagesPath %>/common/time.png);
	padding-left: 25px;
}

.asset-column-details .task-type-1 {
	background-image: url(<%= themeImagesPath %>/common/assign.png);
}

.asset-column-details .task-type-2 {
	background-image: url(<%= themeImagesPath %>/common/time.png);
}

.asset-column-details .task-type-3 {
	background-image: url(<%= themeImagesPath %>/common/recent_changes.png);
}

.asset-column-details .asset-date {
	margin-right: 10px;
	overflow: auto;
	padding-left: 25px;
	padding-right: 10px;
}

.asset-column-details .asset-status {
	background-image: url(<%= themeImagesPath %>/common/recent_changes.png);
	padding-left: 25px;
	padding-right: 10px;
}

.metadata-author {
	background: url(<%= themeImagesPath %>/portlet/edit_guest.png) no-repeat 0 0;
	float: left;
	font-weight: bold;
	margin-right: 10px;
	padding-left: 25px;
}

.metadata-entry {
	clear: both;
	color: #999;
	display: block;
}

.metadata-modified-date, .metadata-create-date, .metadata-publish-date, .metadata-expiration-date {
	background: url(<%= themeImagesPath %>/common/date.png) no-repeat 0 0;
	color: #999;
	margin-bottom: 1em;
	padding-left: 25px;
}

.metadata-priority {
	background: url(<%= themeImagesPath %>/common/top.png) no-repeat 0 20%;
	margin-right: 10px;
	padding-left: 25px;
}

.metadata-view-count {
	margin-right: 10px;
}