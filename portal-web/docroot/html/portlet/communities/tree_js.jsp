<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

boolean selectableTree = ParamUtil.getBoolean(request, "selectableTree");
String treeId = ParamUtil.getString(request, "treeId");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_pages.jsp-portletURL");
%>

<div class="lfr-tree-controls aui-helper-clearfix">
	<div class="lfr-tree-controls-item" id="<portlet:namespace />treeExpandAll">
		<div class="aui-icon lfr-tree-controls-expand"></div>

		<a href="javascript:;" class="lfr-tree-controls-label"><liferay-ui:message key="expand-all" /></a>
	</div>

	<div class="lfr-tree-controls-item" id="<portlet:namespace />treeCollapseAll">
		<div class="aui-icon lfr-tree-controls-collapse"></div>

		<a href="javascript:;" class="lfr-tree-controls-label"><liferay-ui:message key="collapse-all" /></a>
	</div>
</div>

<div class="lfr-tree-loading" id="<portlet:namespace />treeLoading">
	<span class="aui-icon aui-icon-loading lfr-tree-loading-icon"></span>
</div>

<div class="lfr-tree" id="<portlet:namespace /><%= HtmlUtil.escape(treeId) %>Output"></div>

<aui:script use="dataschema-xml,datatype-xml,io-request,tree-view">
	var TreeUtil = {
		DEFAULT_PARENT_LAYOUT_ID: <%= LayoutConstants.DEFAULT_PARENT_LAYOUT_ID %>,
		OPEN_NODES: '<%= SessionTreeJSClicks.getOpenNodes(request, treeId) %>'.split(','),
		SELECTED_NODES: '<%= SessionTreeJSClicks.getOpenNodes(request, treeId + "SelectedNode") %>'.split(','),
		PREFIX_LAYOUT_ID: '_layoutId_',
		PREFIX_PLID: '_plid_',

		afterRenderTree: function(event) {
			var treeInstance = event.target;

			var rootNode = treeInstance.item(0);
			var loadingEl = A.get('#<portlet:namespace />treeLoading');

			loadingEl.hide();

			TreeUtil.restoreNodeState(rootNode);
		},

		createId: function(layoutId, plid) {
			return '<%= HtmlUtil.escape(treeId) %>' + TreeUtil.PREFIX_LAYOUT_ID + layoutId + TreeUtil.PREFIX_PLID + plid;
		},

		createLink: function(label, plid) {
			return '<a href="<%= portletURL + StringPool.AMPERSAND + portletDisplay.getNamespace() + "selPlid=" %>'+ plid +'">'+ label +'</a>';
		},

		extractLayoutId: function(node) {
			return node.get('id').match(/\d+/g)[0];
		},

		extractPlid: function(node) {
			return node.get('id').match(/\d+/g)[1];
		},

		formatJSONResults: function(json) {
			var output = [];

			A.each(
				json,
				function(node) {
					var nameXML = A.DataType.XML.parse(node.name);

					var schema = {
						resultListLocator: 'root',
						resultFields: [
							{
								key: 'label',
								locator: 'name'
							}
						]
					};

					var nodeBase = A.DataSchema.XML.apply(schema, nameXML).results[0];

					var newNode = A.mix(
						nodeBase,
						{
							after: {
								check: function(event) {
									var plid = TreeUtil.extractPlid(event.target);

									TreeUtil.updateSessionTreeClick(plid, true, '<%= HtmlUtil.escape(treeId) %>SelectedNode');
								},
								uncheck: function(event) {
									var plid = TreeUtil.extractPlid(event.target);

									TreeUtil.updateSessionTreeClick(plid, false, '<%= HtmlUtil.escape(treeId) %>SelectedNode');
								}
							},
							alwaysShowHitArea: node.hasChildren,
							id: TreeUtil.createId(node.layoutId, node.plid),
							type: '<%= selectableTree ? "task" : "io" %>'
						}
					);

					if (!<%= selectableTree %>) {
						newNode.label = TreeUtil.createLink(newNode.label, node.plid);
					}

					output.push(newNode);
				}
			);

			return output;
		},

		restoreNodeState: function(node) {
			var instance = this;

			var id = node.get('id');
			var plid = TreeUtil.extractPlid(node);

			if (plid == '<%= selPlid %>') {
				node.select();
			}

			if (A.Array.indexOf(TreeUtil.OPEN_NODES, id) > -1) {
				node.expand();
			}

			if (A.Array.indexOf(TreeUtil.SELECTED_NODES, plid) > -1) {
				if (node.check) {
					node.check();
				}
			}
		},

		updateLayout: function(data) {
			var updateURL = themeDisplay.getPathMain() + '/layout_management/update_page';

			A.io.request(
				updateURL,
				{
					method: 'POST',
					data: data
				}
			);
		},

		updateLayoutParent: function(dragPlid, dropPlid) {
			TreeUtil.updateLayout(
				{
					cmd: 'parent_layout_id',
					parentPlid: dropPlid,
					plid: dragPlid
				}
			);
		},

		updateLayoutPriority: function(dragPlid, index) {
			TreeUtil.updateLayout(
				{
					cmd: 'priority',
					plid: dragPlid,
					priority: index
				}
			);
		},

		updateSessionTreeClick: function(id, open, treeId) {
			var sessionClickURL = themeDisplay.getPathMain() + '/portal/session_tree_js_click';

			var data = {
				nodeId: id,
				openNode: open || false,
				treeId: treeId
			};

			A.io.request(
				sessionClickURL,
				{
					method: 'POST',
					data: data
				}
			);
		}
	};

	var getLayoutsURL = themeDisplay.getPathMain() + '/layout_management/get_layouts';
	var rootId = TreeUtil.createId(TreeUtil.DEFAULT_PARENT_LAYOUT_ID, 0);
	var rootLabel = '<%= HtmlUtil.escape(rootNodeName) %>';
	var treeElId = '<portlet:namespace /><%= HtmlUtil.escape(treeId) %>Output';

	var RootNodeType = A.TreeNodeTask;
	var TreeViewType = A.TreeView;

	if (!<%= selectableTree %>) {
		RootNodeType = A.TreeNodeIO;
		TreeViewType = A.TreeViewDD;

		rootLabel = TreeUtil.createLink(rootLabel, TreeUtil.DEFAULT_PARENT_LAYOUT_ID);
	}

	var rootNode = new RootNodeType(
		{
			alwaysShowHitArea: true,
			draggable: false,
			id: rootId,
			label: rootLabel,
			leaf: false
		}
	);

	rootNode.get('contentBox').addClass('lfr-root-node');

	var treeview = new TreeViewType(
		{
			after: {
				collapse: function(event) {
					var id = event.tree.node.get('id');

					TreeUtil.updateSessionTreeClick(id, false, '<%= HtmlUtil.escape(treeId) %>');
				},
				expand: function(event) {
					var id = event.tree.node.get('id');

					TreeUtil.updateSessionTreeClick(id, true, '<%= HtmlUtil.escape(treeId) %>');
				},
				render: TreeUtil.afterRenderTree
			},
			boundingBox: '#' + treeElId,
			children: [rootNode],
			io: {
				cfg: {
					data: function(node) {
						var parentLayoutId = TreeUtil.extractLayoutId(node);

						return {
							groupId: <%= groupId %>,
							privateLayout: <%= privateLayout %>,
							parentLayoutId: parentLayoutId
						};
					},
					method: 'POST'
				},
				formatter: TreeUtil.formatJSONResults,
				url: getLayoutsURL
			},
			on: {
				append: function(event) {
					TreeUtil.restoreNodeState(event.tree.node);
				},
				drop: function(event) {
					var tree = event.tree;

					var index = tree.dragNode.get('parentNode').indexOf(tree.dragNode);

					TreeUtil.updateLayoutPriority(
						TreeUtil.extractPlid(tree.dragNode),
						index
					);
				},
				dropAppend: function(event) {
					var tree = event.tree;

					TreeUtil.updateLayoutParent(
						TreeUtil.extractPlid(tree.dragNode),
						TreeUtil.extractPlid(tree.dropNode)
					);
				},
				dropInsert: function(event) {
					var tree = event.tree;

					TreeUtil.updateLayoutParent(
						TreeUtil.extractPlid(tree.dragNode),
						TreeUtil.extractPlid(tree.dropNode.get('parentNode'))
					);
				}
			},
			type: 'pages'
		}
	)
	.render();

	A.on(
		'click',
		treeview.collapseAll,
		'#<portlet:namespace />treeCollapseAll',
		treeview
	);

	A.on(
		'click',
		treeview.expandAll,
		'#<portlet:namespace />treeExpandAll',
		treeview
	);
</aui:script>