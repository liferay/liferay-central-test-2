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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
WikiNode node = (WikiNode)request.getAttribute(WikiWebKeys.WIKI_NODE);
%>

<liferay-util:include page="/html/portlet/wiki/top_links.jsp" servletContext="<%= application %>" />

<liferay-ui:header
	title="recent-changes"
/>

<liferay-util:include page="/html/portlet/wiki/page_iterator.jsp" servletContext="<%= application %>">
	<liferay-util:param name="type" value="recent_changes" />
</liferay-util:include>

<br />

<c:if test="<%= wikiPortletInstanceSettings.enableRss() %>">
	<liferay-ui:rss
		delta="<%= GetterUtil.getInteger(wikiPortletInstanceSettings.rssDelta()) %>"
		displayStyle="<%= wikiPortletInstanceSettings.rssDisplayStyle() %>"
		feedType="<%= wikiPortletInstanceSettings.rssFeedType() %>"
		url='<%= themeDisplay.getPathMain() + "/wiki/rss?p_l_id=" + plid + "&nodeId=" + node.getNodeId() %>'
	/>
</c:if>