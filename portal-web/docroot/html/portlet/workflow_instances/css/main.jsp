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

<%@ page import="com.liferay.portal.kernel.util.StringUtil" %>

<liferay-util:buffer var="html">
	<liferay-util:include page="/html/portlet/workflow_tasks/css/main.jsp" />
</liferay-util:buffer>

<%
html = StringUtil.replace(html, "workflowTaskPanelContainer", "workflowInstancePanelContainer");
html = StringUtil.replace(html, "task", "instance");
html = StringUtil.replace(html, "portlet-workflow-tasks", "portlet-workflow-instances");
%>

<%= html %>