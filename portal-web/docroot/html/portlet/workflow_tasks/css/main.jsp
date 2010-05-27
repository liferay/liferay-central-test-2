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

.portlet-workflow-tasks .activity {
	padding: 5px;
}

.portlet-workflow-tasks .date {
	font-weight: bold;
}

.portlet-workflow-tasks h3.task-title, .portlet-workflow-tasks h3.comments {
	border-bottom: 1px solid #000;
	font-size: 14px;
	font-weight: 700;
	margin-top: 0;
}

.portlet-workflow-tasks .lfr-portlet-toolbar .lfr-toolbar-button.completed-button a {
	background-image: url(<%= themeImagesPath %>/common/checked.png);
}

.portlet-workflow-tasks .lfr-portlet-toolbar .lfr-toolbar-button.assigned-to-me a {
	background-image: url(<%= themeImagesPath %>/common/assign.png);
}

.portlet-workflow-tasks .lfr-portlet-toolbar .lfr-toolbar-button.assigned-to-my-role a {
	background-image: url(<%= themeImagesPath %>/common/assign_user_roles.png);
}

.portlet-workflow-tasks .user-avatar {
	vertical-align: middle;
	width: 25px;
}