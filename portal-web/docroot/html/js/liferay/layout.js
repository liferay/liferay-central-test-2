AUI().add(
	'liferay-layout',
	function(A) {
		var DDM = A.DD.DDM;

		var getTitle = A.cached(
			function(id) {
				var portletBoundary = A.one('#' + id);

				var portletTitle = portletBoundary.one('.portlet-title');

				if (!portletTitle) {
					portletTitle = Liferay.Layout.PROXY_NODE_ITEM.one('.portlet-title');

					var title = portletBoundary.one('.portlet-title-default');

					var titleText = '';

					if (title) {
						titleText = title.html();
					}

					portletTitle.html(titleText);
				}

				return portletTitle.outerHTML();
			}
		);

		var Layout = {
			EMPTY_COLUMNS: {},

			OVER_NESTED_PORTLET: false,

			PROXY_NODE: A.Node.create('<div class="lfr-portlet-proxy aui-portal-layout-proxy"></div>'),

			PROXY_NODE_ITEM: A.Node.create(
				'<div class="lfr-portlet-proxy aui-portal-layout-proxy">' +
					'<div class="portlet-topper">' +
						'<span class="portlet-title"></span>' +
					'</div>' +
				'</div>'
			),

			PORTLET_TOPPER: A.Node.create('<div class="portlet-topper"></div>'),

			init: function(options) {
				Layout.options = options;
				Layout.isFreeForm = options.freeForm;

				Layout.PROXY_NODE.append(Layout.PORTLET_TOPPER);

				Layout.DEFAULT_LAYOUT_OPTIONS = {
					dd: {
						clickPixelThresh: 0,
						clickTimeThresh: 250,
						handles: [options.handles],
						plugins: [
							{
								cfg: {
									horizontal: false,
									scrollDelay: 30
								},
								fn: A.Plugin.DDWinScroll
							}
						]
					},
					dragNodes: options.dragNodes,
					dropContainer: function(dropNode) {
						return dropNode.one(options.dropContainer);
					},
					dropNodes: options.dropNodes,
					lazyStart: true,
					proxy: {
						resizeFrame: false
					}
				};

				if (options.freeForm) {
					Layout.initFreeFormLayoutHandler();
				}
				else {
					Layout.initColumnLayoutHandler();
				}

				Layout.bindDragDropListeners();

				Layout.updateEmptyColumnsInfo();
			},

			bindDragDropListeners: function() {
				var layoutHandler = Layout.layoutHandler;

				layoutHandler.on('drag:end', A.bind(Layout._onPortletDragEnd, Layout));
				layoutHandler.on('drag:start', A.bind(Layout._onPortletDragStart, Layout));
			},

			findIndex: function(node) {
				var options = Layout.options;
				var parentNode = node.get('parentNode');

				return parentNode.all('> ' + options.portletBoundary).indexOf(node);
			},

			fire: function() {
				var layoutHandler = Layout.layoutHandler;

				if (layoutHandler) {
					return layoutHandler.fire.apply(layoutHandler, arguments);
				}
			},

			getPortlets: function() {
				var options = Layout.options;

				return A.all(options.dragNodes);
			},

			hasMoved: function(dragNode) {
				var moved = false;
				var curPortletInfo = Layout.curPortletInfo;

				if (curPortletInfo) {
					var currentIndex = Layout.findIndex(dragNode);
					var currentParent = dragNode.get('parentNode');

					if ((curPortletInfo.originalParent != currentParent) ||
						(curPortletInfo.originalIndex != currentIndex)) {
						moved = true;
					}
				}

				return moved;
			},

			hasPortlets: function(columnNode) {
				var options = Layout.options;

				return !!columnNode.one(options.portletBoundary);
			},

			initColumnLayoutHandler: function() {
				var columnLayoutDefaults = A.merge(
					Layout.DEFAULT_LAYOUT_OPTIONS,
					{
						after: {
							'drag:start': function(event) {
								var node = DDM.activeDrag.get('node');
								var nodeId = node.get('id');

								Layout.PORTLET_TOPPER.html(getTitle(nodeId));
							}
						},
						on: {
							'drop:enter': function(event) {
								Layout.updateOverNestedPortletInfo();
							},

							'drop:exit': function(event) {
								Layout.updateOverNestedPortletInfo();
							},

							placeholderAlign: function(event) {
								var portalLayout = event.currentTarget;
								var activeDrop = portalLayout.activeDrop;
								var lastActiveDrop = portalLayout.lastActiveDrop;

								if (lastActiveDrop) {
									var activeDropNode = activeDrop.get('node');
									var lastActiveDropNode = lastActiveDrop.get('node');

									var isOverColumn = !activeDropNode.dd;

									if (!Layout.OVER_NESTED_PORTLET && isOverColumn) {
										var activeDropNodeId = activeDropNode.get('id');
										var emptyColumn = Layout.EMPTY_COLUMNS[activeDropNodeId];

										if (!emptyColumn) {
											if (activeDropNode != lastActiveDropNode) {
												var portlets = activeDropNode.all(Layout.options.portletBoundary);
												var lastIndex = portlets.size() - 1;
												var lastPortlet = portlets.item(lastIndex);

												var drop = DDM.getDrop(lastPortlet);

												if (drop) {
													portalLayout.quadrant = 4;
													portalLayout.activeDrop = drop;
													portalLayout.lastAlignDrop = drop;
												}

												portalLayout._syncPlaceholderUI();
											}

											event.halt();
										}
									}

									if (Layout.OVER_NESTED_PORTLET && (activeDropNode == lastActiveDropNode)) {
										event.halt();
									}
								}
							}
						}
					}
				);

				Layout.layoutHandler = new Layout.ColumnLayout(columnLayoutDefaults);
			},

			initFreeFormLayoutHandler: function() {
				var freeformLayoutDefaults = A.merge(
					Layout.DEFAULT_LAYOUT_OPTIONS,
					{
						after: {
							'drag:start': function(event) {
								var instance = this;

								var proxyNode = instance.get('proxyNode');
								var node = DDM.activeDrag.get('node');
								var nodeId = node.get('id');

								proxyNode.one('.portlet-topper').html(getTitle(nodeId));
							}
						},
						dd: {
							startCentered: false
						},
						lazyStart: false
					}
				);

				freeformLayoutDefaults.dd.plugins = null;

				Layout.layoutHandler = new Layout.FreeFormLayout(freeformLayoutDefaults);
			},

			on: function() {
				var layoutHandler = Layout.layoutHandler;

				if (layoutHandler) {
					return layoutHandler.on.apply(layoutHandler, arguments);
				}
			},

			refresh: function(portlet) {
				var layoutHandler = Layout.layoutHandler;

				portlet = A.get(portlet);

				if (portlet) {
					layoutHandler.addDragTarget(portlet);
				}
			},

			saveIndex: function(portletNode, columnNode) {
				var currentColumnId = Liferay.Util.getColumnId(columnNode.get('id'));
				var portletId = Liferay.Util.getPortletId(portletNode.get('id'));
				var position = Layout.findIndex(portletNode);

				if (Layout.hasMoved(portletNode)) {
					Layout.saveLayout(
						{
							cmd: 'move',
							p_p_col_id: currentColumnId,
							p_p_col_pos: position,
							p_p_id: portletId
						}
					);
				}
			},

			saveLayout: function(options) {
				var data = {
					doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
					p_l_id: themeDisplay.getPlid()
				};

				A.mix(data, options);

				A.io(
					themeDisplay.getPathMain() + '/portal/update_layout',
					{
						data: data,
						method: 'POST'
					}
				);
			},

			updateEmptyColumnsInfo: function() {
				var options = Layout.options;

				A.all(options.dropNodes).each(
					function(item) {
						var columnId = item.get('id');

						Layout.EMPTY_COLUMNS[columnId] = !Layout.hasPortlets(item);
					}
				);
			},

			updateOverNestedPortletInfo: function() {
				var activeDrop = DDM.activeDrop;
				var nestedPortletId = Layout.options.nestedPortletId;

				if (activeDrop) {
					var activeDropNode = activeDrop.get('node');
					var activeDropNodeId = activeDropNode.get('id');

					Layout.OVER_NESTED_PORTLET = (activeDropNodeId.indexOf(nestedPortletId) > -1);
				}
			},

			updatePortletDropZones: function(portletBoundary) {
				var options = Layout.options;
				var portletDropNodes = portletBoundary.all(options.dropNodes);

				portletDropNodes.each(
					function(item) {
						Layout.layoutHandler.addDropNode(item);
					}
				);
			},

			_onPortletDragEnd: function(event) {
				var dragNode = event.target.get('node');

				var columnNode = dragNode.get('parentNode');

				Layout.saveIndex(dragNode, columnNode);

				Layout._syncEmptyClassUI(columnNode);
			},

			_onPortletDragStart: function(event) {
				var dragNode = event.target.get('node');

				Layout._updatePortletInfo(dragNode);
			},

			_syncEmptyClassUI: function(columnNode) {
				var options = Layout.options;
				var curPortletInfo = Layout.curPortletInfo;

				if (curPortletInfo) {
					var emptyColumnClass = options.emptyColumnClass;
					var originalParent = curPortletInfo.originalParent;
					var columnHasPortlets = Layout.hasPortlets(columnNode);
					var originalColumnHasPortlets = Layout.hasPortlets(originalParent);

					var dropZoneId = columnNode.ancestor(Layout.options.dropNodes).get('id');
					var originalDropZoneId = originalParent.ancestor(Layout.options.dropNodes).get('id');

					Layout.EMPTY_COLUMNS[dropZoneId] = !columnHasPortlets;
					Layout.EMPTY_COLUMNS[originalDropZoneId] = !originalColumnHasPortlets;

					columnNode.toggleClass(emptyColumnClass, !columnHasPortlets);
					originalParent.toggleClass(emptyColumnClass, !originalColumnHasPortlets);
				}
			},

			_updatePortletInfo: function(dragNode) {
				var options = Layout.options;

				Layout.curPortletInfo = {
					node: dragNode,
					originalIndex: Layout.findIndex(dragNode),
					originalParent: dragNode.get('parentNode'),
					siblingsPortlets: dragNode.siblings(options.portletBoundary)
				};
			}
		};

		var ColumnLayout = function() {
			ColumnLayout.superclass.constructor.apply(this, arguments);
		};

		ColumnLayout.NAME = 'ColumnLayout';

		ColumnLayout.ATTRS = {
			proxyNode: {
				value: Layout.PROXY_NODE
			}
		};

		A.extend(
			ColumnLayout,
			A.PortalLayout,
			{
				dragItem: 0,

				addDragTarget: function(node) {
					var instance = this;

					var delay = 30;
					var dragDelay = (delay * instance.dragItem++);

					setTimeout(
						function() {
							ColumnLayout.superclass.addDragTarget.apply(instance, [node]);
						},
						dragDelay
					);
				},

				_syncProxyNodeSize: function() {
					var instance = this;

					var dragNode = DDM.activeDrag.get('dragNode');
					var proxyNode = instance.get('proxyNode');

					if (proxyNode && dragNode) {
						dragNode.set('offsetHeight', 30);
						dragNode.set('offsetWidth', 200);

						proxyNode.set('offsetHeight', 30);
						proxyNode.set('offsetWidth', 200);
					}
				}
			}
		);

		var FreeFormLayout = function() {
			FreeFormLayout.superclass.constructor.apply(this, arguments);
		};

		FreeFormLayout.NAME = 'FreeFormLayout';

		FreeFormLayout.ATTRS = {
			proxyNode: {
				value: Liferay.Template.PORTLET
			}
		};

		A.extend(
			FreeFormLayout,
			ColumnLayout,
			{
				portletZIndex: 100,

				initializer: function() {
					var instance = this;

					var placeholder = instance.get('placeholder');

					if (placeholder) {
						placeholder.addClass(Layout.options.freeformPlaceholderClass);
					}

					Layout.getPortlets().each(
						function(item, index, collection) {
							instance._setupNodeResize(item);
							instance._setupNodeStack(item);
						}
					);
				},

				alignPortlet: function(portletNode, referenceNode) {
					var instance = this;

					portletNode.setXY(referenceNode.getXY());

					instance.savePosition(portletNode);
				},

				savePosition: function(portletNode) {
					var portletId = Liferay.Util.getPortletId(portletNode.get('id'));

					Layout.saveLayout(
						{
							cmd: 'drag',
							height: portletNode.getStyle('height'),
							left: portletNode.getStyle('left'),
							p_p_id: portletId,
							top: portletNode.getStyle('top'),
							width: portletNode.getStyle('width')
						}
					);
				},

				_onPortletMouseDown: function(event) {
					var instance = this;

					var portlet = event.currentTarget;

					portlet.setStyle('zIndex', instance.portletZIndex++);
				},

				_positionNode: function(event) {
					var instance = this;

					var activeDrag = DDM.activeDrag;
					var dragNode = activeDrag.get('dragNode');
					var portletNode = activeDrag.get('node');

					var activeDrop = instance.activeDrop;

					if (activeDrop) {
						FreeFormLayout.superclass._positionNode.apply(this, arguments);
					}

					dragNode.setStyle('display', 'block');

					instance.alignPortlet(portletNode, dragNode);

					dragNode.setStyle('display', 'none');
				},

				_setupNodeResize: function(node) {
					var instance = this;

					var resizable = node.hasClass('aui-resize');

					if (!resizable) {
						var resize = new A.Resize(
							{
								after: {
									end: function(event) {
										var info = event.info;

										var portletNode = this.get('node');
										var internalNode = portletNode.one('.portlet');

										if (internalNode) {
											internalNode.set('offsetHeight', info.height);
										}

										instance.savePosition(portletNode);
									}
								},
								handles: 'r,br,b',
								node: node,
								proxy: true
							}
						);
					}
				},

				_setupNodeStack: function(node) {
					var instance = this;

					node.on('mousedown', A.bind(instance._onPortletMouseDown, instance));
				},

				_syncProxyNodeSize: function() {
					var instance = this;

					var node = DDM.activeDrag.get('node');
					var proxyNode = instance.get('proxyNode');

					if (proxyNode) {
						var offsetHeight = node.get('offsetHeight');
						var offsetWidth = node.get('offsetWidth');

						proxyNode.set('offsetHeight', offsetHeight);
						proxyNode.set('offsetWidth', offsetWidth);
					}
				}
			}
		);

		Layout.ColumnLayout = ColumnLayout;
		Layout.FreeFormLayout = FreeFormLayout;
		Liferay.Layout = Layout;
	},
	'',
	{
		requires: ['aui-io-request', 'aui-portal-layout', 'aui-resize', 'dd'],
		use: []
	}
);