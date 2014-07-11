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

<%@ include file="/html/taglib/ui/layouts_tree/init.jsp" %>

<%
boolean checkContentDisplayPage = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:layouts-tree:checkContentDisplayPage"));
boolean defaultStateChecked = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:layouts-tree:defaultStateChecked"));
boolean draggableTree = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:layouts-tree:draggableTree"));
boolean expandFirstNode = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:layouts-tree:expandFirstNode"));
long groupId = GetterUtil.getLong((String)request.getAttribute("liferay-ui:layouts-tree:groupId"));
PortletURL portletURL = (PortletURL)request.getAttribute("liferay-ui:layouts-tree:portletURL");
boolean privateLayout = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:layouts-tree:privateLayout"));
String rootNodeName = (String)request.getAttribute("liferay-ui:layouts-tree:rootNodeName");
boolean saveState = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:layouts-tree:saveState"));
boolean selectableTree = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:layouts-tree:selectableTree"));
String selectedLayoutIds = (String)request.getAttribute("liferay-ui:layouts-tree:selectedLayoutIds");
long selPlid = GetterUtil.getLong((String)request.getAttribute("liferay-ui:layouts-tree:selPlid"));
String treeId = (String)request.getAttribute("liferay-ui:layouts-tree:treeId");

String modules = "liferay-layouts-tree";

if (selectableTree) {
	modules += ",liferay-layouts-tree-selectable";
}

if (checkContentDisplayPage) {
	modules += ",liferay-layouts-tree-check-content-display-page";
}

if (saveState) {
	modules += ",liferay-layouts-tree-state";
}
%>

<aui:script use="<%= modules %>">
	var plugins = [];

	<c:if test="<%= selectableTree %>">
		plugins.push(
			{
				cfg: {
					default: <%= defaultStateChecked %>
				},
				fn: A.Plugin.LayoutsTreeSelectable
			}
		);
	</c:if>

	<c:if test="<%= checkContentDisplayPage %>">
		plugins.push(A.Plugin.LayoutsTreeCheckContentDisplayPage);
	</c:if>

	<c:if test="<%= saveState %>">

		<%
		JSONArray checkedNodesJSONArray = JSONFactoryUtil.createJSONArray();

		String checkedLayoutIds = GetterUtil.getString(selectedLayoutIds, SessionTreeJSClicks.getOpenNodes(request, treeId + "SelectedNode"));

		if (Validator.isNotNull(checkedLayoutIds)) {
			for (long checkedLayoutId : StringUtil.split(checkedLayoutIds, 0L)) {
				Layout checkedLayout = LayoutLocalServiceUtil.fetchLayout(groupId, privateLayout, checkedLayoutId);

				if (checkedLayout != null) {
					checkedNodesJSONArray.put(String.valueOf(checkedLayout.getPlid()));
				}
			}
		}
		%>

		plugins.push(
			{
				cfg: {
					checkedNodes: <%= checkedNodesJSONArray.toString() %>,
					rootNodeExpanded: <%= GetterUtil.getBoolean(SessionClicks.get(request, treeId + "RootNode", null), true) %>
				},
				fn: A.Plugin.LayoutsTreeState
			}
		);
	</c:if>

	<%
	long[] openNodes = StringUtil.split(SessionTreeJSClicks.getOpenNodes(request, treeId), 0L);

	JSONObject layoutsJSON = JSONFactoryUtil.createJSONObject(LayoutsTreeUtil.getLayoutsJSON(request, groupId, privateLayout, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, openNodes, true));
	%>

	var TreeViewType = Liferay.LayoutsTree;

	<c:choose>
		<c:when test="<%= draggableTree %>">
			TreeViewType = Liferay.LayoutsTreeDD;
		</c:when>
	</c:choose>

	var layoutsTree = new TreeViewType(
		{
			boundingBox: '#<portlet:namespace /><%= HtmlUtil.escape(treeId) %>Output',
			layouts: <%= layoutsJSON %>,
			layoutURL: '<%= portletURL + StringPool.AMPERSAND + portletDisplay.getNamespace() + "selPlid={selPlid}" + StringPool.AMPERSAND + portletDisplay.getNamespace() %>',
			maxChildren: <%= PropsValues.LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN %>,
			plugins: plugins,
			root: {
				defaultParentLayoutId: <%= LayoutConstants.DEFAULT_PARENT_LAYOUT_ID %>,
				expand: <%= expandFirstNode %>,
				groupId: <%= groupId %>,
				label: '<%= HtmlUtil.escapeJS(rootNodeName) %>',
				privateLayout: <%= privateLayout %>
			},
			selPlid: '<%= selPlid %>'
		}
	).render();
</aui:script>

<div class="lfr-tree" data-treeid="<%= HtmlUtil.escapeAttribute(treeId) %>" id="<portlet:namespace /><%= HtmlUtil.escape(treeId) %>Output"></div>