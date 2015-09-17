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

<%@ include file="/html/taglib/ui/search_container/init.jsp" %>

<%
String href = (String)request.getAttribute("liferay-ui:search-container-column-icon:href");
String src = (String)request.getAttribute("liferay-ui:search-container-column-icon:src");
boolean toggleRowChecker = GetterUtil.getBoolean(request.getAttribute("liferay-ui:search-container-column-icon:toggleRowChecker"));
%>

<c:if test="<%= Validator.isNotNull(src) %>">
	<div class="user-icon user-icon-xl user-icon-square <%= toggleRowChecker ? "click-selector" : StringPool.BLANK %>">
		<img alt="thumbnail" class="img-responsive img-rounded" src="<%= HtmlUtil.escapeAttribute(src) %>" />
	</div>
</c:if>