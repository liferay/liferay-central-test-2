<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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
--%>

<%@ include file="/init.jsp" %>

<%
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectFolder");

Map<String, Object> context = new HashMap<>();

context.put("itemSelectorSaveEvent", eventName);
context.put("namespace", liferayPortletResponse.getNamespace());
context.put("nodes", journalDisplayContext.getFoldersJSONArray());
context.put("pathThemeImages", themeDisplay.getPathThemeImages());
%>

<soy:template-renderer
	context="<%= context %>"
	module="journal-web/js/SelectFolder.es"
	templateNamespace="SelectFolder.render"
/>