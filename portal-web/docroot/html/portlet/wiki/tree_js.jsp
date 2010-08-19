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

<%!

private static String printAllChildren(WikiPage parent, boolean isLast) {
	String root = "{ label: '" + HtmlUtil.escapeJS(parent.getTitle()) + "', leaf: false";
	List<WikiPage> childPages = parent.getChildPages();
	if (childPages.size() != 0) {
		root += ", children: [ ";
		for (int i = 0; i < childPages.size(); i++) {
			WikiPage child = childPages.get(i);
			if (i == childPages.size() -1 ) {
				root += printAllChildren(child, true);
			}
			else {
				root += printAllChildren(child, false);
			}
		}
		if (isLast) {
			root += "]";
		}
		else {
			root += "]";
		}
	}
	if (isLast) {
		root += " } ";
	}
	else {
		root += " }, ";
	}
	return root;
}

public static String getTreeString(List<WikiPage> pages) {
	String treeString = "[\n";
	for (int i = 0; i < pages.size(); i++) {
		WikiPage curPage = pages.get(i);
		if (i == pages.size() - 1) {
			treeString += "\t" + printAllChildren(curPage, true) + "\n]";
		}
		else {
			treeString += "\t" + printAllChildren(curPage, false) + "\n";
		}
	}
	return treeString;
}
%>

<%
Portlet portlet = (Portlet)request.getAttribute(WebKeys.RENDER_PORTLET);

String redirect = ParamUtil.getString(request, "redirect");

WikiNode node = (WikiNode)request.getAttribute(WebKeys.WIKI_NODE);

long nodeId = BeanParamUtil.getLong(node, request, "nodeId");

List<WikiPage> childPages = WikiPageLocalServiceUtil.getChildren(node.getNodeId(), true, StringPool.BLANK);

String children = getTreeString(childPages);
%>

<label class="aui-field-label" for="<portlet:namespace />treeview"> Categories Tree View </label>
<div id="tree"></div>

<aui:script use="aui-base,aui-io-request,aui-tree-view,dataschema-xml,datatype-xml,liferay-portlet-url">
	var tree = new A.TreeViewDD({
		width: 400,
		type: 'normal',
		boundingBox: '#tree',
		children: <%= children %>,
		on: {
			drop: function(event) {
				var tree = event.tree;

				var childNodeId = tree.dragNode.get('parentNode').get('id');

				var childNodeTitle = decodeURI(tree.dragNode.get('label'));

				var newParentTitle = tree.dragNode.get('parentNode').get('label');

				var movePageURL = Liferay.PortletURL.createActionURL();

				movePageURL.setParameter('struts_action', '/wiki_admin/move_page');
				movePageURL.setParameter('nodeId', '<%= nodeId %>');
				movePageURL.setParameter('title', childNodeTitle);
				movePageURL.setParameter('newParentTitle', newParentTitle);
				movePageURL.setPortletId(<%= portlet.getPortletId() %>);

				A.io.request(movePageURL.toString(), {
					data: {
						'<portlet:namespace /><%= Constants.CMD %>': 'changeParent'
					}
				});
			}
		}
	}).render();

	function updateNode(cmd, data) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = cmd;
		submitForm(document.<portlet:namespace />fm, data);
	}
</aui:script>