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

<%@ include file="/html/taglib/ui/social_bookmark/init.jsp" %>

<%
String diggDisplayStyle = "DiggCompact";

if (displayStyle.equals("simple")) {
	diggDisplayStyle = "DiggIcon";
}
else if (displayStyle.equals("vertical")) {
	diggDisplayStyle = "DiggMedium";
}
%>

<liferay-util:html-bottom outputKey="taglib_ui_social_bookmark_digg">
	<script src="<%= HttpUtil.getProtocol(request) %>://widgets.digg.com/buttons.js" type="text/javascript"></script>
</liferay-util:html-bottom>

<a
	class="DiggThisButton <%= diggDisplayStyle %>"
	href="<%= HttpUtil.getProtocol(request) %>://digg.com/submit?url=<%= HttpUtil.encodeURL(url) %>&amp;title=<%= HttpUtil.encodeURL(title) %>"
>
</a>