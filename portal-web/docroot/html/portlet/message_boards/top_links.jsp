<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
%>

<%@ include file="/html/portlet/message_boards/init.jsp" %>

<%
String topLink = ParamUtil.getString(request, "topLink", "message-boards-home");

MBCategory category = (MBCategory)request.getAttribute(WebKeys.MESSAGE_BOARDS_CATEGORY);

long categoryId = BeanParamUtil.getLong(category, request, "mbCategoryId", MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

boolean viewCategory = GetterUtil.getBoolean((String)request.getAttribute("view.jsp-viewCategory"));

PortletURL portletURL = renderResponse.createRenderURL();
%>

<div class="top-links-container">
	<div class="top-links">
		<div class="top-links-navigation">

			<%
			portletURL.setParameter("topLink", "message-boards-home");
			%>

			<liferay-ui:icon cssClass="top-link" image="../aui/home" message="message-boards-home" label="<%= true %>" url='<%= (topLink.equals("message-boards-home") && categoryId == 0 && viewCategory) ? StringPool.BLANK : portletURL.toString() %>' />

			<%
			portletURL.setParameter("topLink", "recent-posts");
			%>

			<liferay-ui:icon cssClass="top-link" image="../aui/clock" message="recent-posts" label="<%= true %>" url='<%= topLink.equals("recent-posts") ? StringPool.BLANK : portletURL.toString() %>'/>

			<c:if test="<%= themeDisplay.isSignedIn() %>">

				<%
				portletURL.setParameter("topLink", "my-posts");
				%>

				<liferay-ui:icon cssClass="top-link" image="../aui/person" message="my-posts" label="<%= true %>" url='<%= topLink.equals("my-posts") ? StringPool.BLANK : portletURL.toString() %>'/>

				<%
				portletURL.setParameter("topLink", "my-subscriptions");
				%>

				<liferay-ui:icon cssClass="top-link" image="../aui/signal-diag" message="my-subscriptions" label="<%= true %>" url='<%= topLink.equals("my-subscriptions") ? StringPool.BLANK : portletURL.toString() %>'/>
			</c:if>

			<%
			portletURL.setParameter("topLink", "statistics");
			%>

			<liferay-ui:icon cssClass='<%= "top-link" + (MBPermission.contains(permissionChecker, scopeGroupId, ActionKeys.BAN_USER) ? StringPool.BLANK : " last") %>' image="../aui/clipboard" message="statistics" label="<%= true %>" url='<%= topLink.equals("statistics") ? StringPool.BLANK : portletURL.toString() %>'/>

			<c:if test="<%= MBPermission.contains(permissionChecker, scopeGroupId, ActionKeys.BAN_USER) %>">

				<%
				portletURL.setParameter("topLink", "banned-users");
				%>

				<liferay-ui:icon cssClass="top-link last" image="../aui/alert" message="banned-users" label="<%= true %>" url='<%= topLink.equals("banned-users") ? StringPool.BLANK : portletURL.toString() %>'/>
			</c:if>
		</div>

		<c:if test="<%= showSearch %>">
			<liferay-portlet:renderURL varImpl="searchURL">
				<portlet:param name="struts_action" value="/message_boards/search" />
			</liferay-portlet:renderURL>

			<div class="category-search">
				<aui:form action="<%= searchURL %>" method="get" name="searchFm">
					<liferay-portlet:renderURLParams varImpl="searchURL" />
					<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
					<aui:input name="breadcrumbsCategoryId" type="hidden" value="<%= categoryId %>" />
					<aui:input name="searchCategoryId" type="hidden" value="<%= categoryId %>" />

					<aui:input id="keywords1" inlineField="<%= true %>" label="" name="keywords" size="30" type="text" />

					<aui:button type="submit" value="search" />
				</aui:form>
			</div>

			<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) && !themeDisplay.isFacebook() %>">
				<aui:script>
					Liferay.Util.focusFormField(document.<portlet:namespace />searchFm.<portlet:namespace />keywords);
				</aui:script>
			</c:if>
		</c:if>
	</div>
</div>