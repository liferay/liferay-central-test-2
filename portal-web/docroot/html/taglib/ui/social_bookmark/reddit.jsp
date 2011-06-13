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
String redditDisplayStyle = "button1";

if (displayStyle.equals("vertical")) {
	redditDisplayStyle = "button2";
}
%>

<c:choose>
	<c:when test='<%= displayStyle.equals("simple") %>'>
		<a href="http://www.reddit.com/submit" onclick="window.location = 'http://www.reddit.com/submit?url=' + encodeURIComponent('<%= url %>'); return false" title="<liferay-ui:message key="submit-to-reddit" />"> <img src="http://www.reddit.com/static/spreddit7.gif" alt='<liferay-ui:message key="submit-to-reddit" />' border="0" /> </a>
	</c:when>
	<c:otherwise>
		<script type="text/javascript">
			reddit_newwindow='1'
			reddit_title = "<%= title %>";
			reddit_url = "<%= url %>";
		</script>

		<script type="text/javascript" src="http://www.reddit.com/static/button/<%= redditDisplayStyle %>.js"></script>
	</c:otherwise>
</c:choose>