AUI.add(
	'liferay-layouts-tree-state',
	function(A) {
		var AArray = A.Array;

		var CURRENT_CHECKED_NODES = [];

		var CURRENT_UNCHECKED_NODES = [];

		var Lang = A.Lang;

		var STR_BOUNDING_BOX = 'boundingBox';

		var STR_CHECKED_NODES = 'checkedNodes';

		var STR_HOST = 'host';

		var LayoutsTreeState = A.Component.create(
			{
				ATTRS: {
					checkedNodes: {
						validator: Lang.isObject
					},

					rootNodeExpanded: {
						validator: Lang.isBoolean,
						value: true
					}
				},

				EXTENDS: A.Plugin.Base,

				NAME: 'layoutstreestate',

				NS: 'state',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._eventHandles = [
							instance.afterHostEvent('*:childrenChange', instance._onNodeChildrenChange, instance),
							instance.afterHostEvent('*:expandedChange', instance._onNodeExpandedChange, instance),
							instance.afterHostEvent('*:ioSuccess', instance._onNodeIOSuccess, instance),
							instance.afterHostEvent('checkContentDisplayTreeAppend', instance._onCheckContentDisplayTreeAppend, instance),
							instance.afterHostEvent('selectableNodeCheckedChange', instance._onSelectableNodeCheckedChange, instance),
							instance.afterHostEvent('selectableNodeChildrenChange', instance._onSelectableNodeChildrenChange, instance),
							instance.afterHostEvent('selectableTreeAppend', instance._onSelectableTreeAppend, instance),
							instance.afterHostEvent('selectableTreeRender', instance._onSelectableTreeRender, instance)
						];
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_invokeSessionClick: function(data, callback) {
						A.mix(
							data,
							{
								p_auth: Liferay.authToken,
								useHttpSession: true
							}
						);

						A.io.request(
							themeDisplay.getPathMain() + '/portal/session_click',
							{
								after: {
									success: function(event) {
										var instance = this;

										var responseData = instance.get('responseData');

										if (callback && responseData) {
											callback(responseData);
										}
									}
								},
								data: data
							}
						);
					},

					_onCheckContentDisplayTreeAppend: function(event) {
						var instance = this;

						var node = event.node;

						var host = instance.get(STR_HOST);

						host.restoreSelectedNode(node);

						node.eachChildren(A.bind(host.restoreSelectedNode, host));
					},

					_onNodeChildrenChange: function(event) {
						var instance = this;

						var host = instance.get(STR_HOST);

						var target = event.target;

						target.set('alwaysShowHitArea', event.newVal.length > 0);

						target.eachChildren(A.bind(host.restoreSelectedNode, host));
					},

					_onNodeExpandedChange: function(event) {
						var instance = this;

						var host = instance.get(STR_HOST);

						var treeId = host.get(STR_BOUNDING_BOX).attr('data-treeid');

						var expanded = event.newVal;

						var target = event.target;

						var plid = host.extractPlid(target);

						if (target === host.getChildren()[0]) {
							Liferay.Store(treeId + 'RootNode', expanded);
						}
						else {
							var layoutId = host.extractLayoutId(target);

							instance._updateSessionTreeOpenedState(treeId, layoutId, expanded);
						}

						if (CURRENT_CHECKED_NODES.indexOf(plid) > -1) {
							instance._updateCheckedNodes(target, true);
						}
						else if (CURRENT_UNCHECKED_NODES.indexOf(plid) > -1) {
							instance._updateCheckedNodes(target, false);
						}
					},

					_onNodeIOSuccess: function(event) {
						var instance = this;

						var host = instance.get(STR_HOST);

						var paginationMap = {};

						var updatePaginationMap = function(map, curNode) {
							if (A.instanceOf(curNode, A.TreeNodeIO)) {
								var paginationLimit = host.get('maxChildren');

								var layoutId = host.extractLayoutId(curNode);

								var children = curNode.get('children');

								map[layoutId] = Math.ceil(children.length / paginationLimit) * paginationLimit;
							}
						};

						var treeId = host.get(STR_BOUNDING_BOX).attr('data-treeid');

						var root = host.get('root');

						var key = treeId + ':' + root.groupId + ':' + root.privateLayout + ':Pagination';

						instance._invokeSessionClick(
							{
								cmd: 'get',
								key: key
							},
							function(responseData) {
								try {
									paginationMap = JSON.parse(responseData);
								}
								catch (e) {
								}

								updatePaginationMap(paginationMap, event.target);

								event.target.eachParent(
									function(parent) {
										updatePaginationMap(paginationMap, parent);
									}
								);

								var sessionClickData = {};

								sessionClickData[key] = JSON.stringify(paginationMap);

								instance._invokeSessionClick(sessionClickData);
							}
						);
					},

					_onSelectableNodeCheckedChange: function(event) {
						var instance = this;

						var host = instance.get(STR_HOST);

						var treeId = host.get(STR_BOUNDING_BOX).attr('data-treeid');

						var newVal = event.checked;
						var target = event.node;

						var plid = host.extractPlid(target);

						instance._updateSessionTreeCheckedState(treeId + 'SelectedNode', plid, newVal);

						instance._updateCheckedNodes(target, newVal);
					},

					_onSelectableNodeChildrenChange: function(event) {
						var instance = this;

						var node = event.node;

						if (node.get('checked')) {
							instance._updateCheckedNodes(node, true);
						}

						instance._restoreCheckedNode(node);
					},

					_onSelectableTreeAppend: function(event) {
						var instance = this;

						instance._restoreCheckedNode(event.node);
					},

					_onSelectableTreeRender: function(event) {
						var instance = this;

						var host = instance.get(STR_HOST);

						var rootNode = host.getChildren()[0];

						rootNode.set('checked', undefined);
						rootNode.set('expanded', instance.get('rootNodeExpanded'));

						instance._restoreCheckedNode(rootNode);
					},

					_restoreCheckedNode: function(node) {
						var instance = this;

						var plid = instance.get(STR_HOST).extractPlid(node);

						var tree = node.get('ownerTree');

						var treeNodeTaskSuperClass = A.TreeNodeTask.superclass;

						if (instance.get(STR_CHECKED_NODES).indexOf(plid) > -1) {
							treeNodeTaskSuperClass.check.call(node, tree);
						}
						else {
							treeNodeTaskSuperClass.uncheck.call(node, tree);
						}

						node.get('children').forEach(A.bind(instance._restoreCheckedNode, instance));
					},

					_updateCheckedNodes: function(node, state) {
						var instance = this;

						var children = node.get('children');

						var plid = instance.get(STR_HOST).extractPlid(node);

						var checkedNodes = instance.get(STR_CHECKED_NODES);
						var curCheckedNodes = CURRENT_CHECKED_NODES;
						var curUncheckedNodes = CURRENT_UNCHECKED_NODES;

						var checkedIndex = checkedNodes.indexOf(plid);
						var curCheckedIndex = curCheckedNodes.indexOf(plid);
						var curUncheckedIndex = curUncheckedNodes.indexOf(plid);

						if (state) {
							if (checkedIndex === -1) {
								checkedNodes.push(plid);
							}

							if (curCheckedIndex == -1) {
								curCheckedNodes.push(plid);
							}

							if (curUncheckedIndex > -1) {
								AArray.remove(curUncheckedNodes, curUncheckedIndex);
							}
						}
						else if (checkedIndex > -1) {
							AArray.remove(checkedNodes, checkedIndex);
							curUncheckedNodes.push(plid);

							if (curCheckedIndex > -1) {
								AArray.remove(curCheckedNodes, curCheckedIndex);
							}
						}

						node.set('checked', state)

						if (children.length) {
							A.each(
								children,
								function(child) {
									instance._updateCheckedNodes(child, state);
								}
							);
						}
					},

					_updateSessionTreeCheckedState: function(treeId, nodeId, state) {
						var instance = this;

						var data = {
							cmd: state ? 'layoutCheck' : 'layoutUncheck',
							plid: nodeId
						};

						instance._updateSessionTreeClick(treeId, data);
					},

					_updateSessionTreeClick: function(treeId, data) {
						var instance = this;

						var host = instance.get(STR_HOST);

						var root = host.get('root');

						data = A.merge(
							{
								groupId: root.groupId,
								privateLayout: root.privateLayout,
								recursive: true,
								treeId: treeId
							},
							data
						);

						A.io.request(
							themeDisplay.getPathMain() + '/portal/session_tree_js_click',
							{
								data: data
							}
						);
					},

					_updateSessionTreeOpenedState: function(treeId, nodeId, state) {
						var instance = this;

						var data = {
							nodeId: nodeId,
							openNode: state
						};

						instance._updateSessionTreeClick(treeId, data);
					}
				}
			}
		);

		A.Plugin.LayoutsTreeState = LayoutsTreeState;
	},
	'',
	{
		requires: ['aui-base', 'aui-io-request', 'liferay-store']
	}
);