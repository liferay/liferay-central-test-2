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

<%@ include file="/html/portlet/communities/init.jsp" %>

<%
long groupId = (Long)request.getAttribute(WebKeys.TREE_GROUP_ID);
boolean privateLayout = (Boolean)request.getAttribute(WebKeys.TREE_PRIVATE_LAYOUT);
long parentLayoutId = (Long)request.getAttribute(WebKeys.TREE_PARENT_LAYOUT_ID);
long nodeId = (Long)request.getAttribute(WebKeys.TREE_NODE_ID);
long[] openNodes = (long[])request.getAttribute(WebKeys.TREE_OPEN_NODES);
boolean selectableTree = (Boolean)request.getAttribute(WebKeys.TREE_SELECTABLE_TREE);
long[] selectedNodes = (long[])request.getAttribute(WebKeys.TREE_SELECTED_NODES);
String portletURL = (String)request.getAttribute(WebKeys.TREE_PORTLET_URL);
boolean renderChildrenOnly = (Boolean)request.getAttribute(WebKeys.TREE_RENDER_CHILDREN_ONLY);

List<Layout> treeLayouts = LayoutLocalServiceUtil.getLayouts(groupId, privateLayout, parentLayoutId);

boolean nodeOpen = false;
%>

<c:if test="<%= treeLayouts.size() > 0 %>">
	<c:if test="<%= !renderChildrenOnly %>">

		<%
		String cssClass = "has-children";

		if ((Arrays.binarySearch(openNodes, nodeId) >= 0) || (nodeId == 1)) {
			nodeOpen = true;

			cssClass += " node-open";
		}
		%>

		<ul class="<%= cssClass %>">
	</c:if>

	<%
	for (Layout treeLayout : treeLayouts) {
		nodeId++;

		List<Layout> childLayouts = treeLayout.getChildren();

		String image = "spacer.png";
		String cssClass = "tree-item";

		if (nodeOpen || renderChildrenOnly) {
			if (childLayouts.size() > 0) {
				image = "plus.png";
				cssClass += " has-children";

				if (Arrays.binarySearch(openNodes, nodeId) >= 0) {
					image = "minus.png";
					cssClass += " node-open";
				}
			}
		%>

			<li branchid="<%= treeLayout.getPlid() %>" class="<%= cssClass %>" layoutid="<%= treeLayout.getLayoutId() %>" nodeid="<%= nodeId %>" privateLayout="<%= treeLayout.getPrivateLayout() %>">
				<img alt="" class="expand-image" src="<%= themeDisplay.getPathThemeImages() + "/trees/" + image %>" />

				<%
				if (selectableTree && Validator.isNotNull(selectedNodes)) {
					String selectableImagePath = "/trees/checkbox.png";

					if (Arrays.binarySearch(selectedNodes, treeLayout.getPlid()) >= 0) {
						selectableImagePath = "/trees/checked.png";
					}
				%>

					<img alt="" class="select-state" src="<%= themeDisplay.getPathThemeImages() + selectableImagePath %>" />

				<%
				}
				%>

				<a href="<%= portletURL + StringPool.AMPERSAND + portletDisplay.getNamespace() + "selPlid=" + treeLayout.getPlid() %>">
					<img alt="" src="<%= themeDisplay.getPathThemeImages() + "/trees/page.png" %>"  />
					<span><%= HtmlUtil.escape(treeLayout.getName(themeDisplay.getLocale())) %></span>
				</a>

				<%
				request.setAttribute(WebKeys.TREE_GROUP_ID, groupId);
				request.setAttribute(WebKeys.TREE_PRIVATE_LAYOUT, privateLayout);
				request.setAttribute(WebKeys.TREE_PARENT_LAYOUT_ID, treeLayout.getLayoutId());
				request.setAttribute(WebKeys.TREE_NODE_ID, nodeId);
				request.setAttribute(WebKeys.TREE_OPEN_NODES, openNodes);
				request.setAttribute(WebKeys.TREE_SELECTABLE_TREE, selectableTree);
				request.setAttribute(WebKeys.TREE_SELECTED_NODES, selectedNodes);
				request.setAttribute(WebKeys.TREE_PORTLET_URL, portletURL);
				request.setAttribute(WebKeys.TREE_RENDER_CHILDREN_ONLY, false);

				pageContext.include("/html/portlet/communities/tree_js_node.jsp");
				%>

			</li>

	<%
		}
	}
	%>

	<c:if test="<%= !renderChildrenOnly %>">
		</ul>
	</c:if>
</c:if>