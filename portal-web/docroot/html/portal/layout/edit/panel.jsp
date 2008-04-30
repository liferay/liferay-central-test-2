<%@ page import="com.liferay.portal.util.TreeView" %>
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

<%@ include file="/html/portal/layout/edit/init.jsp" %>

<liferay-theme:defineObjects/>

<div class="portlet-msg-info">
	<liferay-ui:message key="select-the-applications-that-will-be-available-in-the-panel" />
</div>

<input name="TypeSettingsProperties(selectedPortlets)" type="hidden" value="<bean:write name="SEL_LAYOUT" property="typeSettingsProperties(selectedPortlets)" />" />

<%
String panelTreeKey = "selectedPortletsPanelTree";
%>

<script src="<%= themeDisplay.getPathJavaScript() %>/liferay/tree.js" type="text/javascript"></script>

<div id="<portlet:namespace />panel-select-portlets-output" style="margin: 4px;"></div>

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

	var <portlet:namespace />panelPortletsArray = [];

	<%
	PortletLister lister = new PortletLister();

	TreeView treeView = lister.getTreeView(company, LanguageUtil.get(pageContext, "application"), user, layoutTypePortlet, application, locale);

	Iterator itr = treeView.getList().iterator();

	for (int i = 0; itr.hasNext(); i++) {
		TreeNodeView treeNodeView = (TreeNodeView)itr.next();

	%>

 		<portlet:namespace />panelPortletsArray[<%= i %>] = {
			depth: '<%= treeNodeView.getDepth() %>',
			id: '<%= treeNodeView.getId() %>',
			img: '<%= treeNodeView.getImg() %>',
			ls: '<%= treeNodeView.getLs() %>',
			href: '<%= treeNodeView.getHref() %>',
			parentId: '<%= treeNodeView.getParentId() %>',
			objId: '<%= treeNodeView.getObjId() %>',
			name: '<%= treeNodeView.getName() %>'
		};

	<%
	}
	%>
</script>

<script type="text/javascript">
	jQuery(
		function() {
			new Liferay.Tree(
				{
					className: "gamma",
					icons: <portlet:namespace />layoutIcons,
					nodes: <portlet:namespace />panelPortletsArray,
					openNodes: '<bean:write name="SEL_LAYOUT" property="typeSettingsProperties(selectedPortlets)" />',
					outputId: '#<portlet:namespace />panel-select-portlets-output',
					selectable: true,
					selectedNodes: '<bean:write name="SEL_LAYOUT" property="typeSettingsProperties(selectedPortlets)" />',
					treeId: '<%= panelTreeKey %>',
					onSelect: function(params) {
						var portletId = params.branchId;
						var selectedNode = params.selectedNode;

						var selectedPortletsEl = document.<portlet:namespace />fm['TypeSettingsProperties(selectedPortlets)'];
						console.debug(selectedPortletsEl);

						if (portletId) {
							if (selectedNode) {
								selectedPortletsEl.value += portletId + ',';
							}
							else {
								selectedPortletsEl.value = selectedPortletsEl.value.replace(portletId + ',', '');
							}
						}

						console.debug(selectedPortletsEl);

					}
				}
			);
		}
	);
</script>

<liferay-ui:message key="description" /><br />
<textarea name="TypeSettingsProperties(description)" cols="70"><bean:write name="SEL_LAYOUT" property="typeSettingsProperties(description)" /></textarea>

