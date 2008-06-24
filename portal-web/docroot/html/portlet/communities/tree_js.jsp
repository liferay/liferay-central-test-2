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

List layoutList = (List)request.getAttribute("edit_pages.jsp-layoutList");

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

	<%
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

</script>

<%
int[] openNodes = StringUtil.split(SessionTreeJSClicks.getOpenNodes(request, "layoutsTree"), 0);

Arrays.sort(openNodes);

StringMaker sm = new StringMaker();

_buildLayoutsTreeHTML(groupId, privateLayout, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, new IntegerWrapper(1), openNodes, portletURL, themeDisplay, sm);
%>

<div style="display: none;">
	<%= sm %>
</div>

<%!
private void _buildLayoutsTreeHTML(long groupId, boolean privateLayout, long parentLayoutId, IntegerWrapper nodeId, int[] openNodes, PortletURL portletURL, ThemeDisplay themeDisplay, StringMaker sm) throws Exception {
	PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

	List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(groupId, privateLayout, parentLayoutId);

	sm.append("<ul ");

	sm.append("class=\"");

	if (layouts.size() > 0) {
		sm.append("has-children ");
	}

	if (Arrays.binarySearch(openNodes, nodeId.getValue()) >= 0) {
		sm.append("node-open");
	}

	sm.append("\"");

	sm.append(">");

	for (Layout layout : layouts) {
		nodeId.increment();

		sm.append("<li branchid=\"");
		sm.append(layout.getPlid());
		sm.append("\" nodeid=\"");
		sm.append(nodeId.getValue());
		sm.append("\">");
		sm.append("<span><a href=\"");
		sm.append(portletURL.toString());
		sm.append("&");
		sm.append(portletDisplay.getNamespace());
		sm.append("selPlid=");
		sm.append(layout.getPlid());
		sm.append("\">");
		sm.append(layout.getName(themeDisplay.getLocale()));
		sm.append("</span></a>");

		_buildLayoutsTreeHTML(groupId, privateLayout, layout.getLayoutId(), nodeId, openNodes, portletURL, themeDisplay, sm);

		sm.append("</li>");
	}

	sm.append("</ul>");
}
%>