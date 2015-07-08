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
<%@ include file="/html/taglib/init.jsp" %>

<%
String cssClass = (String)request.getAttribute("liferay-ui:icon-menu:css-class");
String direction = (String)request.getAttribute("liferay-ui:icon:direction");
String message = (String)request.getAttribute("liferay-ui:icon-menu:message");
%>

<div class="dropdown <%= cssClass %>">
	<a aria-expanded="false" class="dropdown-toggle" data-toggle="dropdown" href="#1" title="<%= message %>">
		<span class="icon-ellipsis-vertical icon-monospaced"></span>
	</a>
	<ul class="dropdown-menu dropdown-menu-<%= direction %>">