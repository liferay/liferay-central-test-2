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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
boolean print = ParamUtil.getString(request, "viewMode").equals(Constants.PRINT);
%>

<c:if test="<%= !print && portletName.equals(PortletKeys.WIKI) %>">

	<%
	String strutsAction = ParamUtil.getString(request, "struts_action");

	WikiNode node = (WikiNode)request.getAttribute(WebKeys.WIKI_NODE);
	WikiPage wikiPage = (WikiPage)request.getAttribute(WebKeys.WIKI_PAGE);

	String keywords = ParamUtil.getString(request, "keywords");

	List nodes = WikiUtil.getNodes(scopeGroupId, visibleNodes, hiddenNodes, permissionChecker);

	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("nodeName", node.getName());
	%>

	<div class="top-links-container">
		<c:if test="<%= themeDisplay.isSignedIn() && (WikiPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_NODE) || GroupPermissionUtil.contains(permissionChecker, scopeGroupId, ActionKeys.PERMISSIONS)) %>">

			<%
			portletURL.setParameter("struts_action", "/wiki/view_nodes");
			%>

			<div class="top-links-configuration">
				<liferay-ui:icon cssClass="top-link" image="manage_nodes" message="manage-wikis" url="<%= portletURL.toString() %>" />
			</div>
		</c:if>

		<c:if test="<%= nodes.size() > 1 %>">
			<div class="top-links-nodes">

				<%
				for (int i = 0; i < nodes.size(); i++) {
					WikiNode curNode = (WikiNode)nodes.get(i);

					String cssClass = StringPool.BLANK;

					if (curNode.getNodeId() == node.getNodeId()) {
						cssClass = "node-current";
					}
				%>

					<portlet:renderURL var="viewPageURL">
						<portlet:param name="struts_action" value="/wiki/view" />
						<portlet:param name="nodeName" value="<%= curNode.getName() %>" />
						<portlet:param name="title" value="<%= WikiPageConstants.FRONT_PAGE %>" />
					</portlet:renderURL>

					<%= (i == 0) ? "" : "|" %> <aui:a cssClass="<%= cssClass %>" href="<%= viewPageURL %>" label="<%= curNode.getName() %>" />

				<%
				}
				%>

			</div>
		</c:if>

		<div class="top-links">
			<div class="top-links-navigation">

				<%
				PortletURL frontPageURL = PortletURLUtil.clone(portletURL, renderResponse);

				frontPageURL.setParameter("struts_action", "/wiki/view");
				frontPageURL.setParameter("title", WikiPageConstants.FRONT_PAGE);
				%>

				<liferay-ui:icon cssClass="top-link" image="../aui/home" label="<%= true %>" message="<%= WikiPageConstants.FRONT_PAGE %>" url='<%= wikiPage != null && (wikiPage.getTitle().equals(WikiPageConstants.FRONT_PAGE)) ? StringPool.BLANK : frontPageURL.toString() %>' />

				<%
				portletURL.setParameter("struts_action", "/wiki/view_recent_changes");
				%>

				<liferay-ui:icon cssClass="top-link" image="../aui/clock" label="<%= true %>" message="recent-changes" url='<%= strutsAction.equals("/wiki/view_recent_changes") ? StringPool.BLANK : portletURL.toString() %>' />

				<%
				portletURL.setParameter("struts_action", "/wiki/view_all_pages");
				%>

				<liferay-ui:icon cssClass="top-link" image="../aui/document" label="<%= true %>" message="all-pages" url='<%= strutsAction.equals("/wiki/view_all_pages") ? StringPool.BLANK : portletURL.toString() %>' />

				<%
				portletURL.setParameter("struts_action", "/wiki/view_orphan_pages");
				%>

				<liferay-ui:icon cssClass="top-link last" image="../aui/document-b" label="<%= true %>" message="orphan-pages" url='<%= strutsAction.equals("/wiki/view_orphan_pages") ? StringPool.BLANK : portletURL.toString() %>' />
			</div>

			<liferay-portlet:renderURL varImpl="searchURL">
				<portlet:param name="struts_action" value="/wiki/search" />
			</liferay-portlet:renderURL>

			<div class="page-search">
				<aui:form action="<%= searchURL %>" method="get" name="searchFm">
					<liferay-portlet:renderURLParams varImpl="searchURL" />
					<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
					<aui:input name="nodeId" type="hidden" value="<%= node.getNodeId() %>" />

					<aui:input inlineField="<%= true %>" label="" name="keywords" size="30" type="text" value="<%= keywords %>" />

					<aui:button type="submit" value="search" />
				</aui:form>
			</div>
		</div>
	</div>

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		<aui:script>
			Liferay.Util.focusFormField(document.<portlet:namespace />searchFm.<portlet:namespace />keywords);
		</aui:script>
	</c:if>
</c:if>