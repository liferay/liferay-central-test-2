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
		<c:choose>
			<c:when test='<%= displayStyle.equals("menu") %>'>
				<div>
					<liferay-ui:message key="share" />
				</div>

				<span>

					<%
					for (int i = 0; i < typesArray.length; i++) {
					%>

						<liferay-ui:social-bookmark contentId="<%= contentId %>" displayStyle="<%= displayStyle %>" target="<%= target %>" title="<%= title %>" type="<%= typesArray[i] %>" url="<%= url %>" />

					<%
					}
					%>

				</span>

				<aui:script use="liferay-social-bookmarks">
					new Liferay.SocialBookmarks(
						{
							contentBox: '#<%= randomNamespace %>socialBookmarks'
						}
					);
				</aui:script>
			</c:when>
			<c:otherwise>
				<ul class="unstyled">

					<%
					for (int i = 0; i < typesArray.length; i++) {
						String styleClass = "taglib-social-bookmark-" + typesArray[i];
					%>

						<li class="<%= styleClass %>">
							<liferay-ui:social-bookmark contentId="<%= contentId %>" displayStyle="<%= displayStyle %>"  target="<%= target %>" title="<%= title %>" type="<%= typesArray[i] %>" url="<%= url %>" />
						</li>

					<%
					}
					%>

				</ul>
			</c:otherwise>
		</c:choose>
	</div>
</c:if>