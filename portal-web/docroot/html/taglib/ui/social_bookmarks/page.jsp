<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/ui/social_bookmarks/init.jsp" %>

<c:if test="<%= typesArray.length > 0 %>">

	<%
	String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_ui_social_bookmarks_page") + StringPool.UNDERLINE;
	%>

	<div class="taglib-social-bookmarks" id="<%= randomNamespace %>socialBookmarks">
		<liferay-ui:icon-menu icon="/html/themes/classic/images/common/share.png" message="share">

			<%
			for (int i = 0; i < typesArray.length; i++) {
			%>

				<liferay-ui:social-bookmark contentId="<%= contentId %>" target="<%= target %>" title="<%= title %>" type="<%= typesArray[i] %>" url="<%= url %>" />

			<%
			}
			%>

		</liferay-ui:icon-menu>
	</div>

	<aui:script use="liferay-social-bookmarks">
		var socialBookmarks = new Liferay.SocialBookmarks(
			{
				contentBox: '#<%= randomNamespace %>socialBookmarks'
			}
		).render();
	</aui:script>
</c:if>