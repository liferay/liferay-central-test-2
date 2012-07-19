<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%@ include file="/html/portlet/layouts_admin/init_attributes.jspf" %>

<%
boolean incomplete = ParamUtil.getBoolean(request, "incomplete", true);

String treeLoading = PortalUtil.generateRandomKey(request, "treeLoading");

String treeId = ParamUtil.getString(request, "treeId");
boolean checkContentDisplayPage = ParamUtil.getBoolean(request, "checkContentDisplayPage", false);
boolean expandFirstNode = ParamUtil.getBoolean(request, "expandFirstNode", true);
boolean saveState = ParamUtil.getBoolean(request, "saveState", true);
boolean selectableTree = ParamUtil.getBoolean(request, "selectableTree");

String modules = "aui-io-request,aui-tree-view,dataschema-xml,datatype-xml";

if (!selectableTree) {
	modules += ",liferay-history-manager";
}
%>

<aui:script use="<%= modules %>">
	var Lang = A.Lang;

	var Util = Liferay.Util;

	var LAYOUT_URL = '<%= portletURL + StringPool.AMPERSAND + portletDisplay.getNamespace() + "selPlid={selPlid}" + StringPool.AMPERSAND + portletDisplay.getNamespace() + "historyKey={historyKey}" %>';

	var TreeUtil = {
		DEFAULT_PARENT_LAYOUT_ID: <%= LayoutConstants.DEFAULT_PARENT_LAYOUT_ID %>,

		<%
		String openNodes = SessionTreeJSClicks.getOpenNodes(request, treeId);
		%>

		OPEN_NODES: '<%= openNodes %>'.split(','),
		PAGINATION_LIMIT: <%= PropsValues.LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN %>,
		PREFIX_GROUP_ID: '_groupId_',
		PREFIX_LAYOUT: '_layout_',
		PREFIX_LAYOUT_ID: '_layoutId_',
		PREFIX_PLID: '_plid_',

		<%
		JSONArray selectedNodesJSONArray = JSONFactoryUtil.createJSONArray();

		String selectedLayoutIds = SessionTreeJSClicks.getOpenNodes(request, treeId + "SelectedNode");

		if (selectedLayoutIds != null) {
			for (long selectedLayoutId : StringUtil.split(selectedLayoutIds, 0L)) {
				try {
					Layout selectedLayout = LayoutLocalServiceUtil.getLayout(groupId, privateLayout, selectedLayoutId);

					selectedNodesJSONArray.put(String.valueOf(selectedLayout.getPlid()));
				}
				catch (NoSuchLayoutException nsle) {
				}
			}
		}
		%>

		SELECTED_NODES: <%= selectedNodesJSONArray.toString() %>,

		afterRenderTree: function(event) {
			var treeInstance = event.target;

			var rootNode = treeInstance.item(0);

			var loadingEl = A.one('#<portlet:namespace />treeLoading<%= treeLoading %>');

			loadingEl.hide();

			<c:choose>
				<c:when test="<%= saveState %>">
					TreeUtil.restoreNodeState(rootNode);
				</c:when>
				<c:when test="<%= expandFirstNode %>">
					rootNode.expand();
				</c:when>
			</c:choose>
		},

		createListItemId: function(groupId, layoutId, plid) {
			return '<%= HtmlUtil.escape(treeId) %>' + TreeUtil.PREFIX_LAYOUT_ID + layoutId + TreeUtil.PREFIX_PLID + plid + TreeUtil.PREFIX_GROUP_ID + groupId;
		},

		createLinkId: function(friendlyURL) {
			return '<%= HtmlUtil.escape(treeId) %>' + TreeUtil.PREFIX_LAYOUT + friendlyURL.substring(1);
		},

		createLink: function(data) {
			var className = 'layout-tree';

			if (data.cssClass) {
				className += ' ' + data.cssClass;
			}

			if (<%= checkContentDisplayPage %> && !data.contentDisplayPage) {
				className += ' layout-page-invalid';
			}

			var href = Lang.sub(
				LAYOUT_URL,
				{
					historyKey: data.historyKey,
					selPlid: data.plid
				}
			);

			return '<a class="' + className + '" data-uuid="' + data.uuid + '" href="' + href + '" id="' + data.id + '" title="' + data.title + '">' + data.label + '</a>';
		},

		extractGroupId: function(node) {
			return node.get('id').match(/groupId_(\d+)/)[1];
		},

		extractLayoutId: function(node) {
			return node.get('id').match(/layoutId_(\d+)/)[1];
		},

		extractPlid: function(node) {
			return node.get('id').match(/plid_(\d+)/)[1];
		},

		formatJSONResults: function(json) {
			var output = [];

			A.each(
				json.layouts,
				function(node) {
					var childLayouts = [];
					var total = 0;

					var nodeChildren = node.children;

					if (nodeChildren) {
						childLayouts = nodeChildren.layouts;
						total = nodeChildren.total;
					}

					var expanded = (total > 0);

					var newNode = {
						<c:if test="<%= saveState %>">
							after: {
								checkedChange: function(event) {
									if (this === event.originalTarget) {
										var plid = TreeUtil.extractPlid(event.target);

										TreeUtil.updateSessionTreeCheckedState('<%= HtmlUtil.escape(treeId) %>SelectedNode', plid, event.newVal);
									}
								},
								expandedChange: function(event) {
									var layoutId = TreeUtil.extractLayoutId(event.target);

									TreeUtil.updateSessionTreeOpenedState('<%= HtmlUtil.escape(treeId) %>', layoutId, event.newVal);
								}
							},
						</c:if>
						alwaysShowHitArea: node.hasChildren,
						draggable: node.updateable,
						expanded: expanded,
						id: TreeUtil.createListItemId(node.groupId, node.layoutId, node.plid),
						paginator: {
							limit: TreeUtil.PAGINATION_LIMIT,
							offsetParam: 'start',
							start: Math.max(childLayouts.length - TreeUtil.PAGINATION_LIMIT, 0),
							total: total
						},
						type: '<%= selectableTree ? "task" : "io" %>'
					};

					if (nodeChildren && expanded) {
						newNode.children = TreeUtil.formatJSONResults(nodeChildren);
					}

					var cssClass = '';

					var title = '';

					newNode.label = Util.escapeHTML(node.name);

					if (node.layoutRevisionId) {
						if (node.layoutBranchName) {
							node.layoutBranchName = Util.escapeHTML(node.layoutBranchName);

							newNode.label += Lang.sub(' <span class="layout-branch-name" title="<%= UnicodeLanguageUtil.get(pageContext, "this-is-the-page-variation-that-is-marked-as-ready-for-publication") %>">[{layoutBranchName}]</span>', node);
						}

						if (node.incomplete) {
							cssClass = 'incomplete-layout';

							title = '<%= UnicodeLanguageUtil.get(pageContext, "this-page-is-not-enabled-in-this-site-pages-variation,-but-is-available-in-other-variations") %>';
						}
					}

					if (!node.updateable) {
						newNode.cssClass = 'lfr-page-locked';
					}

					if (!<%= selectableTree %>) {
						newNode.label = TreeUtil.createLink(
							{
								contentDisplayPage: node.contentDisplayPage,
								cssClass: cssClass,
								id: TreeUtil.createLinkId(node.friendlyURL),
								label: newNode.label,
								plid: node.plid,
								title: title,
								uuid: node.uuid
							}
						);
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

			if (A.Array.indexOf(TreeUtil.SELECTED_NODES, plid) > -1) {
				if (node.check) {
					var tree = node.get('ownerTree');

					node.check(tree);
				}
			}

			A.Array.each(node.get('children'), TreeUtil.restoreNodeState);
		},

		updateLayout: function(data) {
			var updateURL = themeDisplay.getPathMain() + '/layouts_admin/update_page';

			A.io.request(
				updateURL,
				{
					data: A.mix(
						data,
						{
							p_auth: Liferay.authToken
						}
					)
				}
			);
		},

		updateLayoutParent: function(dragPlid, dropPlid, index) {
			TreeUtil.updateLayout(
				{
					cmd: 'parent_layout_id',
					parentPlid: dropPlid,
					plid: dragPlid,
					priority: index
				}
			);
		}

		<c:if test="<%= saveState %>">
			, invokeSessionClick: function(data, callback) {
				A.mix(
					data,
					{
						useHttpSession: true
					}
				);

				A.io.request(
					themeDisplay.getPathMain() + '/portal/session_click',
					{
						after: {
							success: function(event) {
								var responseData = this.get('responseData');

								if (callback && responseData) {
									callback(Liferay.Util.unescapeHTML(responseData));
								}
							}
						},
						data: data
					}
				);
			},

			updatePagination: function(node) {
				var paginationMap = {};

				var updatePaginationMap = function(map, curNode) {
					if (A.instanceOf(curNode, A.TreeNodeIO)) {
						var paginationLimit = TreeUtil.PAGINATION_LIMIT;

						var layoutId = TreeUtil.extractLayoutId(curNode);

						var children = curNode.get('children');

						map[layoutId] = Math.ceil(children.length / paginationLimit) * paginationLimit;
					}
				}

				TreeUtil.invokeSessionClick(
					{
						cmd: 'get',
						key: '<%= HtmlUtil.escape(treeId) %>:<%= groupId %>:<%= privateLayout %>:Pagination'
					},
					function(responseData) {
						try {
							paginationMap = A.JSON.parse(responseData);
						}
						catch(e) {
						}

						updatePaginationMap(paginationMap, node)

						node.eachParent(
							function(parent) {
								updatePaginationMap(paginationMap, parent);
							}
						);

						TreeUtil.invokeSessionClick(
							{
								'<%= HtmlUtil.escape(treeId) %>:<%= groupId %>:<%= privateLayout %>:Pagination': A.JSON.stringify(paginationMap)
							}
						);
					}
				);
			},

			updateSessionTreeCheckedState: function(treeId, nodeId, state) {
				var data = {
					cmd: state ? 'layoutCheck' : 'layoutUncheck',
					plid: nodeId
				};

				TreeUtil.updateSessionTreeClick(treeId, data);
			},

			updateSessionTreeClick: function(treeId, data) {
				var sessionClickURL = themeDisplay.getPathMain() + '/portal/session_tree_js_click';

				data = A.merge(
					{
						groupId: <%= groupId %>,
						privateLayout: <%= privateLayout %>,
						treeId: treeId
					},
					data
				);

				A.io.request(
					sessionClickURL,
					{
						data: data
					}
				);
			},

			updateSessionTreeOpenedState: function(treeId, nodeId, state) {
				var data = {
					nodeId: nodeId,
					openNode: state
				};

				TreeUtil.updateSessionTreeClick(treeId, data);
			}
		</c:if>
	};

	var getLayoutsURL = themeDisplay.getPathMain() + '/layouts_admin/get_layouts';
	var rootId = TreeUtil.createListItemId(<%= groupId %>, TreeUtil.DEFAULT_PARENT_LAYOUT_ID, 0);
	var rootLabel = '<%= HtmlUtil.escapeJS(rootNodeName) %>';
	var treeElId = '<portlet:namespace /><%= HtmlUtil.escape(treeId) %>Output';

	var RootNodeType = A.TreeNodeTask;
	var TreeViewType = A.TreeView;

	<c:if test="<%= !selectableTree %>">
		RootNodeType = A.TreeNodeIO;
		TreeViewType = A.TreeViewDD;

		<c:if test="<%= !checkContentDisplayPage %>">
		rootLabel = TreeUtil.createLink(
			{
				label: Util.escapeHTML(rootLabel),
				plid: TreeUtil.DEFAULT_PARENT_LAYOUT_ID
			}
		);
		</c:if>
	</c:if>

	var rootNode = new RootNodeType(
		{
			after: {
				checkedChange: function(event) {
					TreeUtil.updateSessionTreeCheckedState('<%= HtmlUtil.escape(treeId) %>SelectedNode', <%= LayoutConstants.DEFAULT_PLID %>, event.newVal);
				},
				expandedChange: function(event) {
					var sessionClickURL = themeDisplay.getPathMain() + '/portal/session_click';

					A.io.request(
						sessionClickURL,
						{
							data: {
								'<%= HtmlUtil.escape(treeId) %>RootNode': event.newVal
							}
						}
					);
				}
			},

			alwaysShowHitArea: true,

			<%
			JSONObject layoutsJSON = JSONFactoryUtil.createJSONObject(LayoutsTreeUtil.getLayoutsJSON(request, groupId, privateLayout, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, StringUtil.split(openNodes, 0L), true));
			%>

			children: TreeUtil.formatJSONResults(<%= layoutsJSON %>),
			draggable: false,

			<%
			boolean rootNodeExpanded = GetterUtil.getBoolean(SessionClicks.get(request, treeId + "RootNode", null), true);
			%>

			expanded: <%= rootNodeExpanded %>,
			id: rootId,
			label: rootLabel,
			leaf: false,
			paginator: {
				limit: TreeUtil.PAGINATION_LIMIT,
				offsetParam: 'start',
				start: Math.max(<%= layoutsJSON.getJSONArray("layouts").length() %> - TreeUtil.PAGINATION_LIMIT, 0),
				total: <%= layoutsJSON.getInt("total") %>
			}
		}
	);

	rootNode.get('contentBox').addClass('lfr-root-node');

	var treeview = new TreeViewType(
		{
			after: {
				render: TreeUtil.afterRenderTree
			},
			boundingBox: '#' + treeElId,
			children: [rootNode],
			io: {
				cfg: {
					data: function(node) {
						var groupId = TreeUtil.extractGroupId(node);
						var parentLayoutId = TreeUtil.extractLayoutId(node);

						return {
							groupId: groupId,
							incomplete: <%= incomplete %>,
							p_auth: Liferay.authToken,
							parentLayoutId: parentLayoutId,
							privateLayout: <%= privateLayout %>,
							selPlid: '<%= selPlid %>'
						};
					},
					method: AUI.defaults.io.method,
					on: {
						success: function(event, id, xhr) {
							var instance = this;

							var paginator = instance.get('paginator');
							var response;

							try {
								response = A.JSON.parse(xhr.responseText);
							}
							catch(e) {}

							if (response) {
								paginator.total = response.total;

								instance.syncUI();
							}

							TreeUtil.updatePagination(instance);
						}
					}
				},
				formatter: TreeUtil.formatJSONResults,
				url: getLayoutsURL
			},
			on: {
				<c:if test="<%= saveState %>">
				append: function(event) {
					TreeUtil.restoreNodeState(event.tree.node);
				},
				</c:if>
				dropAppend: function(event) {
					var tree = event.tree;

					var index = tree.dragNode.get('parentNode').indexOf(tree.dragNode);

					TreeUtil.updateLayoutParent(
						TreeUtil.extractPlid(tree.dragNode),
						TreeUtil.extractPlid(tree.dropNode),
						index
					);
				},
				dropInsert: function(event) {
					var tree = event.tree;

					var index = tree.dragNode.get('parentNode').indexOf(tree.dragNode);

					TreeUtil.updateLayoutParent(
						TreeUtil.extractPlid(tree.dragNode),
						TreeUtil.extractPlid(tree.dropNode.get('parentNode')),
						index
					);
				}
			},
			type: 'pages'
		}
	).render();

	<c:if test="<%= !saveState && checkContentDisplayPage %>">
		treeview.on(
			'append',
			function(event) {
				var node = event.tree.node;

				var plid = TreeUtil.extractPlid(node);

				if (plid == '<%= selPlid %>') {
					node.select();
				}
			}
		);
	</c:if>

	A.one('#' + treeElId).setData('treeInstance', treeview);

	<c:if test="<%= !selectableTree %>">
		var History = Liferay.HistoryManager;

		var DEFAULT_PLID = '0';

		var HISTORY_SELECTED_PLID = '<portlet:namespace />selPlid';

		var layoutsContainer = A.one('#<portlet:namespace />layoutsContainer');

		treeview.after(
			'lastSelectedChange',
			function(event) {
				var node = event.newVal;

				var plid = TreeUtil.extractPlid(node);

				var currentValue = History.get(HISTORY_SELECTED_PLID);

				if (plid != currentValue) {
					if ((plid == DEFAULT_PLID) && Lang.isValue(currentValue)) {
						plid = null;
					}

					History.add(
						{
							'<portlet:namespace />selPlid': plid
						}
					);
				}
			}
		);

		function compareItemId(item, id) {
			return (TreeUtil.extractPlid(item) == id);
		}

		function findNodeByPlid(node, plid) {
			var foundItem = null;

			if (node) {
				if (compareItemId(node, plid)) {
					foundItem = node;
				}
			}

			if (!foundItem) {
				var children = (node || treeview).get('children');

				var length = children.length;

				for (var i = 0; i < length; i++) {
					var item = children[i];

					if (item.isLeaf()) {
						if (compareItemId(item, plid)) {
							foundItem = item;
						}
					}
					else {
						foundItem = findNodeByPlid(item, plid);
					}

					if (foundItem) {
						break;
					}
				}
			}

			return foundItem;
		}

		History.after(
			'stateChange',
			function(event) {
				var nodePlid = event.newVal[HISTORY_SELECTED_PLID];

				if (Lang.isValue(nodePlid)) {
					var node = findNodeByPlid(null, nodePlid);

					if (node) {
						var lastSelected = treeview.get('lastSelected');

						if (lastSelected) {
							lastSelected.unselect();
						}

						node.select();

						var io = layoutsContainer.io;

						var uri = Lang.sub(
							LAYOUT_URL,
							{
								historyKey: '',
								selPlid: nodePlid
							}
						);

						io.set('uri', uri);

						io.start();
					}
				}
			}
		);
	</c:if>
</aui:script>

<div class="lfr-tree-loading" id="<portlet:namespace />treeLoading<%= treeLoading %>">
	<span class="aui-icon aui-icon-loading lfr-tree-loading-icon"></span>
</div>

<div class="lfr-tree" id="<portlet:namespace /><%= HtmlUtil.escape(treeId) %>Output"></div>