<%
/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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

				<liferay-ui:icon cssClass="top-link" image="../aui/home" message="<%= WikiPageConstants.FRONT_PAGE %>" label="<%= true %>" url='<%= wikiPage != null && (wikiPage.getTitle().equals(WikiPageConstants.FRONT_PAGE)) ? StringPool.BLANK : frontPageURL.toString() %>' />

				<%
				portletURL.setParameter("struts_action", "/wiki/view_recent_changes");
				%>

				<liferay-ui:icon cssClass="top-link" image="../aui/clock" message="recent-changes" label="<%= true %>" url='<%= strutsAction.equals("/wiki/view_recent_changes") ? StringPool.BLANK : portletURL.toString() %>' />

				<%
				portletURL.setParameter("struts_action", "/wiki/view_all_pages");
				%>

				<liferay-ui:icon cssClass="top-link" image="../aui/document" message="all-pages" label="<%= true %>" url='<%= strutsAction.equals("/wiki/view_all_pages") ? StringPool.BLANK : portletURL.toString() %>' />

				<%
				portletURL.setParameter("struts_action", "/wiki/view_orphan_pages");
				%>

				<liferay-ui:icon cssClass="top-link last" image="../aui/document-b" message="orphan-pages" label="<%= true %>" url='<%= strutsAction.equals("/wiki/view_orphan_pages") ? StringPool.BLANK : portletURL.toString() %>' />
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

			<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
				<aui:script>
					Liferay.Util.focusFormField(document.<portlet:namespace />searchFm.<portlet:namespace />keywords);
				</aui:script>
			</c:if>
		</div>
	</div>
</c:if>