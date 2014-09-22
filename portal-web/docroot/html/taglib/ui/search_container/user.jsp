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
Date date = GetterUtil.getDate(request.getAttribute("liferay-ui:search-container-column-user:date"), DateFormatFactoryUtil.getDate(locale), null);
long userId = GetterUtil.getLong(request.getAttribute("liferay-ui:search-container-column-user:userId"));

User user2 = UserLocalServiceUtil.fetchUser(userId);
%>

<div class="user-info">
	<div class="user-avatar">
		<img alt="<%= HtmlUtil.escapeAttribute((user2 != null) ? user2.getFullName() : LanguageUtil.get(request, "anonymous")) %>" class="avatar img-circle" src="<%= HtmlUtil.escape((user2 != null) ? user2.getPortraitURL(themeDisplay) : UserConstants.getPortraitURL(themeDisplay.getPathImage(), true, 0, StringPool.BLANK)) %>" />
	</div>

	<div class="user-details">
		<div class="row <%= (date == null) ? "line" : StringPool.BLANK %>">
			<span class="col-md-12 user-name"><%= HtmlUtil.escapeAttribute((user2 != null) ? user2.getFullName() : LanguageUtil.get(request, "anonymous")) %></span>
		</div>

		<c:if test="<%= date != null %>">
			<div class="row">
				<span class="col-md-12 date-info">
					<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - date.getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
				</span>
			</div>
		</c:if>
	</div>
</div>