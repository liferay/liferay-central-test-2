<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/message_boards/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Object[] objArray = (Object[])row.getObject();

MBMessage message = (MBMessage)objArray[0];

MBThread thread = message.getThread();

Date now = new Date();

long lastPostAgo = now.getTime() - thread.getLastPostDate().getTime();

String lastPostByUserName = HtmlUtil.escape(PortalUtil.getUserName(thread.getLastPostByUserId(), StringPool.BLANK));

User userDisplay = UserLocalServiceUtil.getUserById(thread.getLastPostByUserId());

String portraitURL = userDisplay.getPortraitURL(themeDisplay);
String displayURL = userDisplay.getDisplayURL(themeDisplay);

String portraitAlt = (userDisplay != null) ? HtmlUtil.escapeAttribute(userDisplay.getFullName()) : LanguageUtil.get(pageContext, "generic-portrait");
%>

<div class="user-info">
	<div class="portrait">
		<a href="<%= displayURL %>"><img class="avatar" src=" <%= portraitURL %>" alt="<%= portraitAlt %>" width="60" /></a>
	</div>

	<div class="username">
		<a href="<%= displayURL %>"><%= lastPostByUserName %></a>
	</div>

	<div class="time">
		<liferay-ui:icon
			image="../aui/clock"
			label="<%= true %>"
			message='<%= LanguageUtil.format(pageContext, "x-ago", LanguageUtil.getTimeDescription(pageContext, lastPostAgo, true)) %>'
		/>
	</div>
</div>
