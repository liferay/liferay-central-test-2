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
WikiNode node = (WikiNode)request.getAttribute(WebKeys.WIKI_NODE);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
%>

<c:if test="<%= WikiNodePermission.contains(permissionChecker, node.getNodeId(), ActionKeys.ADD_PAGE) %>">

	<%
	PortletURL addPageURL = renderResponse.createRenderURL();

	addPageURL.setParameter("struts_action", "/wiki/edit_page");
	addPageURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
	%>

	<script type="text/javascript">
		function <portlet:namespace />addPage() {
			var pageName = prompt('<liferay-ui:message key="page-name" />', '');

			if ((pageName != null) && (pageName.length > 0)) {
				window.location = Liferay.Util.addParams('<portlet:namespace />title=' + pageName, '<%= addPageURL.toString() %>');
			}
		}
	</script>
</c:if>

<div class="wiki-top-links">
	<table class="lfr-table">
	<tr>
		<td>

			<%
			PortletURL frontPageURL = PortletURLUtil.clone(portletURL, renderResponse);

			frontPageURL.setParameter("struts_action", "/wiki/view");
			frontPageURL.setParameter("title", WikiPageImpl.FRONT_PAGE);
			%>

			<liferay-ui:icon image="page" message="<%= WikiPageImpl.FRONT_PAGE %>" url="<%= frontPageURL.toString() %>" label="<%= true %>" />
		</td>
		<td>

			<%
			portletURL.setParameter("struts_action", "/wiki/view_recent_changes");
			%>

			<liferay-ui:icon image="recent_changes" message="recent-changes" url="<%= portletURL.toString() %>" label="<%= true %>" />
		</td>
		<td>

			<%
			portletURL.setParameter("struts_action", "/wiki/view_all_pages");
			%>

			<liferay-ui:icon image="all_pages" message="all-pages" url="<%= portletURL.toString() %>" label="<%= true %>" />
		</td>
		<td>

			<%
			portletURL.setParameter("struts_action", "/wiki/view_orphan_pages");
			%>

			<liferay-ui:icon image="orphan_pages" message="orphan-pages" url="<%= portletURL.toString() %>" label="<%= true %>" />
		</td>

		<c:if test="<%= WikiNodePermission.contains(permissionChecker, node.getNodeId(), ActionKeys.ADD_PAGE) %>">
			<td>
				<liferay-ui:icon image="add_article" message="add-page" url='<%= "javascript: " + renderResponse.getNamespace() + "addPage();" %>' label="<%= true %>" />
			</td>
		</c:if>

		<c:if test="<%= themeDisplay.isSignedIn() && PortletPermissionUtil.contains(permissionChecker, plid.longValue(), PortletKeys.WIKI, ActionKeys.ADD_NODE) %>">

			<%
			portletURL.setParameter("struts_action", "/wiki/view_nodes");
			%>

			<td>
				<liferay-ui:icon image="manage_nodes" message="manage-nodes" url="<%= portletURL.toString() %>" label="<%= true %>" />
			</td>
		</c:if>
	</tr>
	</table>
</div>