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

<%@ include file="/html/taglib/ui/social_bookmark/init.jsp" %>

<%
String buzzDisplayStyle = "small-count";

if (displayStyle.equals("simple")) {
	buzzDisplayStyle = "small-button";
}
else if (displayStyle.equals("vertical")) {
	buzzDisplayStyle = "normal-count";
}
%>

<liferay-util:html-bottom outputKey="buzz">
	<script type="text/javascript" src="http://www.google.com/buzz/api/button.js"></script>
</liferay-util:html-bottom>

<a title="Publicar en Google Buzz" class="google-buzz-button <%= styleClass %>" href="http://www.google.com/buzz/post" data-button-style="<%= buzzDisplayStyle %>" data-locale="<%= locale.getLanguage() %>" data-url="<%= url %>"></a>