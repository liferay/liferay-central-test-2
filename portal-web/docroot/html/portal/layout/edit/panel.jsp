<%@ page import="com.liferay.portal.util.TreeView" %>
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

<%@ include file="/html/portal/layout/edit/init.jsp" %>

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="description" />
	</td>
	<td>
		<textarea class="lfr-textarea" name="TypeSettingsProperties(description)" wrap="soft"><bean:write name="SEL_LAYOUT" property="typeSettingsProperties(description)" /></textarea>
	</td>
</tr>
</table>

<br />

<div class="portlet-msg-info">
	<liferay-ui:message key="select-the-applications-that-will-be-available-in-the-panel" />
</div>

<input id="<portlet:namespace />panelSelectedPortlets" name="TypeSettingsProperties(panelSelectedPortlets)" type="hidden" value="<bean:write name="SEL_LAYOUT" property="typeSettingsProperties(panelSelectedPortlets)" />" />

<%
String panelTreeKey = "panelSelectedPortletsPanelTree";
%>

<div class="lfr-tree-controls aui-helper-clearfix">
	<div class="lfr-tree-controls-item" id="<portlet:namespace />panelTreeExpandAll">
		<div class="aui-icon lfr-tree-controls-expand"></div>

		<a href="javascript:;" class="lfr-tree-controls-label"><liferay-ui:message key="expand-all" /></a>
	</div>

	<div class="lfr-tree-controls-item" id="<portlet:namespace />panelTreeCollapseAll">
		<div class="aui-icon lfr-tree-controls-collapse"></div>

		<a href="javascript:;" class="lfr-tree-controls-label"><liferay-ui:message key="collapse-all" /></a>
	</div>
</div>

<div id="<portlet:namespace />panelSelectPortletsOutput" style="margin: 4px;"></div>

<aui:script use="aui-tree-view">
	var panelSelectedPortletsEl = A.get('#<portlet:namespace />panelSelectedPortlets');
	var selectedPortlets = panelSelectedPortletsEl.val().split(',');

	var onCheck = function(event, plid) {
		var node = event.target;
		var add = A.Array.indexOf(selectedPortlets, plid) == -1;

		if (plid && add) {
			selectedPortlets.push(plid);

			panelSelectedPortletsEl.val(selectedPortlets.join(','));
		}
	};

	var onUncheck = function(event, plid) {
		var node = event.target;

		if (plid) {
			if (selectedPortlets.length) {
				A.Array.removeItem(selectedPortlets, plid);
			}

			panelSelectedPortletsEl.val( selectedPortlets.join(',') );
		}
	};

	var treeView = new A.TreeView(
		{
			boundingBox: '#<portlet:namespace />panelSelectPortletsOutput'
		}
	)
	.render();

	<%
	PortletLister portletLister = new PortletLister();

	portletLister.setIncludeInstanceablePortlets(false);

	TreeView treeView = portletLister.getTreeView(layoutTypePortlet, LanguageUtil.get(pageContext, "application"), user, application);

	Iterator itr = treeView.getList().iterator();

	for (int i = 0; itr.hasNext(); i++) {
		TreeNodeView treeNodeView = (TreeNodeView)itr.next();
	%>

		var parentNode<%= i %> = treeView.getNodeById('treePanel<%= treeNodeView.getParentId() %>') || treeView;
		var objId<%= i %> = '<%= treeNodeView.getObjId() %>';
		var checked<%= i %> = objId<%= i %> ? (A.Array.indexOf(selectedPortlets, objId<%= i %>) > -1) : false;

		parentNode<%= i %>.appendChild(
			new A.TreeNodeTask(
				{
					checked: checked<%= i %>,
					expanded: <%= treeNodeView.getDepth() == 0 %>,
					id: 'treePanel<%= treeNodeView.getId() %>',
					label: '<%= UnicodeFormatter.toString(treeNodeView.getName()) %>',
					leaf: <%= treeNodeView.getDepth() > 1 %>,
					on: {
						check: A.rbind(onCheck, window, objId<%= i %>),
						uncheck: A.rbind(onUncheck, window, objId<%= i %>)
					}
				}
			)
		);

	<%
	}
	%>

	A.on(
		'click',
		treeView.collapseAll,
		'#<portlet:namespace />panelTreeCollapseAll',
		treeView
	);

	A.on(
		'click',
		treeView.expandAll,
		'#<portlet:namespace />panelTreeExpandAll',
		treeView
	);
</aui:script>