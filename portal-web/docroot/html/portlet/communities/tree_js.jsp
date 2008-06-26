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

<%@ include file="/html/portlet/communities/init.jsp" %>

<%
long groupId = ((Long)request.getAttribute("edit_pages.jsp-groupId")).longValue();
long selPlid = ((Long)request.getAttribute("edit_pages.jsp-selPlid")).longValue();
boolean privateLayout = ((Boolean)request.getAttribute("edit_pages.jsp-privateLayout")).booleanValue();

String rootNodeName = (String)request.getAttribute("edit_pages.jsp-rootNodeName");

List layoutList = (List)request.getAttribute("edit_pages.jsp-layoutList");

boolean selectableTree = ParamUtil.getBoolean(request, "selectableTree");
String treeId = ParamUtil.getString(request, "treeId");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_pages.jsp-portletURL");
%>

<script src="<%= themeDisplay.getPathJavaScript() %>/liferay/tree.js" type="text/javascript"></script>

<script type="text/javascript">
	var <portlet:namespace />layoutIcons = {
		checkbox: '<%= themeDisplay.getPathThemeImages() %>/trees/checkbox.png',
		checked: '<%= themeDisplay.getPathThemeImages() %>/trees/checked.png',
		childChecked: '<%= themeDisplay.getPathThemeImages() %>/trees/child_checked.png',
		minus: '<%= themeDisplay.getPathThemeImages() %>/trees/minus.png',
		page: '<%= themeDisplay.getPathThemeImages() %>/trees/page.png',
		plus: '<%= themeDisplay.getPathThemeImages() %>/trees/plus.png',
		root: '<%= themeDisplay.getPathThemeImages() %>/trees/root.png',
		spacer: '<%= themeDisplay.getPathThemeImages() %>/trees/spacer.png'
	};

	var <portlet:namespace />layoutArray = [];
	var <portlet:namespace />nodeIds = '';

	<%
	StringBuilder nodeIds = new StringBuilder();

	for (int i = 0; i < layoutList.size(); i++) {
		String layoutDesc = (String)layoutList.get(i);

		String[] nodeValues = StringUtil.split(layoutDesc, "|");

		long objId = GetterUtil.getLong(nodeValues[3]);
		String name = UnicodeFormatter.toString(nodeValues[4]);

		// Set a better name and remove depth because href should be in the 5th
		// position

		if (selPlid == objId) {
			name = "<strong>" + name + "</strong>";
		}

		nodeValues[4] = name;

		int depth = 0;

		if (i != 0) {
			depth = GetterUtil.getInteger(nodeValues[6]);
			nodeValues[6] = "";
		}

		layoutDesc = StringUtil.merge(nodeValues, "|");

		if (i != 0) {
			layoutDesc = layoutDesc.substring(0, layoutDesc.length() - 1);
		}

		if (nodeValues[0] != "0") {
			nodeIds.append(nodeValues[0]);
			nodeIds.append(StringPool.COMMA);
		}
	%>

		<portlet:namespace />layoutArray[<%= i %>] = {
			depth: '<%= depth %>',
			id: '<%= nodeValues[0]  %>',
			img: '<%= nodeValues[4] %>',
			ls: '<%= nodeValues[2] %>',
			href: 'javascript: location.href = \'<%= HttpUtil.encodeURL(portletURL.toString()) %>&<portlet:namespace />selPlid=<%= objId %>\';',
			parentId: '<%= nodeValues[1] %>',
			objId: '<%= nodeValues[3] %>',
			name: '<%= nodeValues[4] %>'
		};

	<%
	}
	%>

	var <portlet:namespace />nodeIds = '<%= nodeIds %>';
</script>

<%
int[] openNodes = StringUtil.split(SessionTreeJSClicks.getOpenNodes(request, treeId), 0);

Arrays.sort(openNodes);

int[] selectedNodes = StringUtil.split(SessionTreeJSClicks.getOpenNodes(request, treeId + "Selected"), 0);

Arrays.sort(selectedNodes);

StringMaker sm = new StringMaker();

_buildLayoutsTreeHTML(groupId, privateLayout, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, new IntegerWrapper(1), openNodes, selectableTree, selectedNodes, portletURL, themeDisplay, sm);
%>

<div class="lfr-tree" id="<portlet:namespace /><%= treeId %>_tree-output">
	<ul class="lfr-component">
		<li class="root-container">
			<a class="community" href="<%= portletURL.toString() %>&<portlet:namespace />selPlid=<%= LayoutConstants.DEFAULT_PARENT_LAYOUT_ID %>"><img height="20" src="<%= themeDisplay.getPathThemeImages() %>/trees/root.png" width="19" /><span><%= rootNodeName %></span></a>

			<%= sm %>
		</li>
	</ul>
</div>

<script type="text/javascript">
	jQuery(
		function() {
			new Liferay.Tree(
				{
					icons: <portlet:namespace />layoutIcons,
					nodes: <portlet:namespace />layoutArray,
					nodeIds: <portlet:namespace />nodeIds,
					openNodes: '<%= SessionTreeJSClicks.getOpenNodes(request, treeId) %>',
					outputId: '#<portlet:namespace /><%= treeId %>_tree-output',
					preRendered: true,
					<c:if test="<%= selectableTree %>">
						selectable: true,
					</c:if>
					treeId: '<%= treeId %>'
				}
			);
		}
	);
</script>

<%!
private void _buildLayoutsTreeHTML(long groupId, boolean privateLayout, long parentLayoutId, IntegerWrapper nodeId, int[] openNodes, boolean selectableTree, int[] selectedNodes, PortletURL portletURL, ThemeDisplay themeDisplay, StringMaker sm) throws Exception {
	PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

	List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(groupId, privateLayout, parentLayoutId);

	if (layouts.size() == 0) {
		return;
	}

	boolean nodeOpen = false;

	sm.append("<ul class=\"has-children ");

	if ((Arrays.binarySearch(openNodes, nodeId.getValue()) >= 0) || nodeId.getValue() == 1) {
		nodeOpen = true;

		sm.append("node-open ");
	}

	sm.append("\" ");

	if (!nodeOpen) {
		sm.append("style=\"display: none\" ");
	}

	sm.append(">");

	for (Layout layout : layouts) {
		nodeId.increment();

		List<Layout> childLayouts = layout.getChildren();

		String image = "spacer.png";

		sm.append("<li branchid=\"");
		sm.append(layout.getPlid());
		sm.append("\" class=\"tree-item ");

		if (childLayouts.size() > 0) {
			image = "plus.png";

			sm.append("has-children ");

			if (Arrays.binarySearch(openNodes, nodeId.getValue()) >= 0) {
				image = "minus.png";

				sm.append("node-open ");
			}
		}

		sm.append("\" nodeid=\"");
		sm.append(nodeId.getValue());
		sm.append("\"><img class=\"expand-image\" height=\"20\" src=\"");
		sm.append(themeDisplay.getPathThemeImages() + "/trees/" + image);
		sm.append("\" width=\"19\" />");

		if (selectableTree && Validator.isNotNull(selectedNodes)) {
			sm.append("<img class=\"select-state\" height=\"20\" src=\"");

			if (Arrays.binarySearch(selectedNodes, (int)layout.getPlid()) >= 0) {
				sm.append(themeDisplay.getPathThemeImages() + "/trees/checked.png");
			}
			else {
				sm.append(themeDisplay.getPathThemeImages() + "/trees/checkbox.png");
			}

			sm.append("\" width=\"19\" />");
		}

		sm.append("<a href=\"");
		sm.append(portletURL.toString());
		sm.append(StringPool.AMPERSAND);
		sm.append(portletDisplay.getNamespace());
		sm.append("selPlid=");
		sm.append(layout.getPlid());
		sm.append("\"><img height=\"20\" src=\"");
		sm.append(themeDisplay.getPathThemeImages() + "/trees/page.png");
		sm.append("\" width=\"19\" /><span>");
		sm.append(layout.getName(themeDisplay.getLocale()));
		sm.append("</span></a>");

		_buildLayoutsTreeHTML(groupId, privateLayout, layout.getLayoutId(), nodeId, openNodes, selectableTree, selectedNodes, portletURL, themeDisplay, sm);

		sm.append("</li>");
	}

	sm.append("</ul>");
}
%>