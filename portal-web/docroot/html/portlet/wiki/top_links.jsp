<%
/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
boolean print = ParamUtil.getBoolean(request, Constants.PRINT);
%>

<c:if test="<%= !print && portletName.equals(PortletKeys.WIKI) %>">

	<%
	WikiNode node = (WikiNode)request.getAttribute(WebKeys.WIKI_NODE);

	String keywords = ParamUtil.getString(request, "keywords");

	List nodes = WikiNodeLocalServiceUtil.getNodes(portletGroupId.longValue());

	Iterator itr = nodes.iterator();

	while (itr.hasNext()) {
		WikiNode curNode = (WikiNode)itr.next();

		if (!WikiNodePermission.contains(permissionChecker, curNode.getNodeId(), ActionKeys.VIEW)) {
			itr.remove();
		}
	}

	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
	%>

	<div class="wiki-top-links">
		<table class="lfr-table">
		<tr>
			<td valign="top" width="16">
				<c:if test="<%= themeDisplay.isSignedIn() && PortletPermissionUtil.contains(permissionChecker, plid.longValue(), PortletKeys.WIKI, ActionKeys.ADD_NODE) %>">

					<%
					portletURL.setParameter("struts_action", "/wiki/view_nodes");
					%>

					<liferay-ui:icon image="manage_nodes" message="manage-wikis" url="<%= portletURL.toString() %>" />
				</c:if>
			</td>
			<td valign="top">

				<c:if test="<%= nodes.size() > 1 %>">

					<%
					for (int i = 0; i < nodes.size(); i++) {
						WikiNode curNode = (WikiNode)nodes.get(i);
					%>

						<%= (i == 0) ? "" : "|" %> <a <%= (curNode.getNodeId() == node.getNodeId()) ? "class=\"wiki-node-current\"" : "" %> href="<portlet:renderURL><portlet:param name="struts_action" value="/wiki/view" /><portlet:param name="nodeId" value="<%= String.valueOf(curNode.getNodeId()) %>" /></portlet:renderURL>"><nobr><%= curNode.getName() %></nobr></a>

					<%
					}
					%>

				</c:if>
			</td>
			<td align="right" valign="top">
				<liferay-portlet:renderURL varImpl="searchURL"><portlet:param name="struts_action" value="/wiki/search" /></liferay-portlet:renderURL>

				<form action="<%= searchURL %>" method="get" name="<portlet:namespace />fmSearch" onSubmit="submitForm(this); return false;">
				<liferay-portlet:renderURLParams varImpl="searchURL" />
				<input name="<portlet:namespace />redirect" type="hidden" value="<%= currentURL %>" />
				<input name="<portlet:namespace />nodeId" type="hidden" value="<%= node.getNodeId() %>" />

				<%
				PortletURL frontPageURL = PortletURLUtil.clone(portletURL, renderResponse);

				frontPageURL.setParameter("struts_action", "/wiki/view");
				frontPageURL.setParameter("title", WikiPageImpl.FRONT_PAGE);
				%>

				<a href="<%= frontPageURL.toString() %>"><nobr><%= WikiPageImpl.FRONT_PAGE %></nobr></a>

				<%
				portletURL.setParameter("struts_action", "/wiki/view_recent_changes");
				%>

				| <a href="<%= portletURL.toString() %>"><nobr><liferay-ui:message key="recent-changes" /></nobr></a>

				<%
				portletURL.setParameter("struts_action", "/wiki/view_all_pages");
				%>

				| <a href="<%= portletURL.toString() %>"><nobr><liferay-ui:message key="all-pages" /></nobr></a>

				<%
				portletURL.setParameter("struts_action", "/wiki/view_orphan_pages");
				%>

				| <a href="<%= portletURL.toString() %>"><nobr><liferay-ui:message key="orphan-pages" /></nobr></a>

				&nbsp;

				<nobr>
					<input name="<portlet:namespace />keywords" size="30" type="text" value="<%= Html.escape(keywords) %>" />

					<input type="submit" value="<liferay-ui:message key="search" />" />
				</nobr>

				</form>
			</td>
		</tr>
		</table>
	</div>
</c:if>