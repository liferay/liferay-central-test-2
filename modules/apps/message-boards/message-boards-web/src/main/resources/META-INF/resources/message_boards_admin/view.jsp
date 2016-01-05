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

<%@ include file="/message_boards/init.jsp" %>

<%
String topLink = ParamUtil.getString(request, "topLink", "message-boards-home");

MBCategory category = (MBCategory)request.getAttribute(WebKeys.MESSAGE_BOARDS_CATEGORY);

long categoryId = MBUtil.getCategoryId(request, category);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/message_boards/view");
portletURL.setParameter("topLink", topLink);
portletURL.setParameter("mbCategoryId", String.valueOf(categoryId));

request.setAttribute("view.jsp-categoryId", categoryId);
request.setAttribute("view.jsp-viewCategory", Boolean.TRUE.toString());
request.setAttribute("view.jsp-portletURL", portletURL);
%>

<portlet:actionURL name="/message_boards/edit_category" var="restoreTrashEntriesURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
</portlet:actionURL>

<liferay-trash:undo
	portletURL="<%= restoreTrashEntriesURL %>"
/>

<%
PortletURL navigationURL = renderResponse.createRenderURL();

navigationURL.setParameter("mvcRenderCommandName", "/message_boards/view");
%>

<aui:nav-bar cssClass='<%= topLink.equals("message-boards-home") ? "collapse-basic-search" : StringPool.BLANK %>' markupView="lexicon">
	<aui:nav cssClass="navbar-nav">

		<%
		navigationURL.setParameter("top-link", "message-boards-home");
		navigationURL.setParameter("tag", StringPool.BLANK);
		%>

		<aui:nav-item
			href="<%= navigationURL.toString() %>"
			label="message-boards-home"
			selected='<%= topLink.equals("message-boards-home") %>'
		/>

		<%
		navigationURL.setParameter("topLink", "statistics");
		%>

		<aui:nav-item
			href="<%= navigationURL.toString() %>"
			label="statistics"
			selected='<%= topLink.equals("statistics") %>'
		/>

		<%
		navigationURL.setParameter("topLink", "banned-users");
		%>

		<aui:nav-item
			href="<%= navigationURL.toString() %>"
			label="banned-users"
			selected='<%= topLink.equals("banned-users") %>'
		/>
	</aui:nav>

	<c:if test='<%= topLink.equals("message-boards-home") %>'>
		<liferay-portlet:renderURL varImpl="searchURL">
			<portlet:param name="mvcRenderCommandName" value="/message_boards/search" />
		</liferay-portlet:renderURL>

		<aui:nav-bar-search>
			<aui:form action="<%= searchURL %>" name="searchFm">
				<liferay-portlet:renderURLParams varImpl="searchURL" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
				<aui:input name="breadcrumbsCategoryId" type="hidden" value="<%= categoryId %>" />
				<aui:input name="searchCategoryId" type="hidden" value="<%= categoryId %>" />

				<liferay-ui:input-search markupView="lexicon" />
			</aui:form>
		</aui:nav-bar-search>
	</c:if>
</aui:nav-bar>

<c:choose>
	<c:when test='<%= topLink.equals("message-boards-home") %>'>
		<liferay-util:include page="/message_boards_admin/view_entries.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test='<%= topLink.equals("statistics") %>'>
		<liferay-util:include page="/message_boards_admin/statistics.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test='<%= topLink.equals("banned-users") %>'>
		<liferay-util:include page="/message_boards_admin/banned_users.jsp" servletContext="<%= application %>" />
	</c:when>
</c:choose>