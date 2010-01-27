AUI().add(
	'liferay-layout',
	function(A) {
		var Lang = A.Lang;
		var isBoolean = Lang.isBoolean;
		var isString = Lang.isString;

		var DOM = A.DOM;
		var DDM = A.DD.DDM;

		var hasClass = A.cached(
			function(id, className) {
				return A.one('#' + id).hasClass(className);
			}
		);

		var Layout = {
			init: function(options) {
				var instance = this;

				instance.isFreeForm = options.freeForm;

				var handler = ColumnLayout;

				if (options.freeForm) {
					handler = FreeFormLayout;
				}

				instance.layoutHandler = new handler(options);
			},

			fire: function() {
				var instance = this;

				var layoutHandler = instance.layoutHandler;

				if (layoutHandler) {
					return layoutHandler.fire.apply(layoutHandler, arguments);
				}
			},

			on: function() {
				var instance = this;

				var layoutHandler = instance.layoutHandler;

				if (layoutHandler) {
					return layoutHandler.on.apply(layoutHandler, arguments);
				}
			},

			refresh: function() {
				var instance = this;

				var layoutHandler = instance.layoutHandler;

				if (layoutHandler) {
					return layoutHandler.refresh.apply(layoutHandler, arguments);
				}
			}
		};

		var ColumnLayout = function() {
			ColumnLayout.superclass.constructor.apply(this, arguments);
		};

		ColumnLayout.NAME = 'ColumnLayout';

		ColumnLayout.ATTRS = {
			columnSelector: {
				value: '.lfr-portlet-column'
			},

			contentWrapperSelector: {
				value: '#content-wrapper'
			},

			dropZones: {
				valueFn: function() {
					var instance = this;

					var gridColumnSelector = instance.get('gridColumnSelector');

					return A.all(gridColumnSelector);
				}
			},

			gridColumnSelector: {
				value: '.lfr-column'
			},

			gridSelector: {
				value: '#layout-grid,.lfr-grid'
			},

			portletHandler: {
				validator: function(val) {
					return (val instanceof A.NestedList);
				}
			},

			portletSelector: {
				value: '.portlet-boundary'
			}
		};

		A.extend(
			ColumnLayout,
			A.Base,
			{
				curPortletInfo: null,

				initializer: function(options) {
					var instance = this;

					instance.bindUI();
				},

				add: function(portletNode) {
					var instance = this;

					instance.get('portletHandler').registerPortlet(portletNode);
				},

				bindUI: function() {
					var instance = this;

					instance._bindDropColumns();
					instance._bindDragPortlets();
					instance._bindDragDropListeners();
				},

				findIndex: function(node) {
					var instance = this;

					var parentNode = node.get('parentNode');

					return parentNode.all('> .portlet-boundary:not(.portlet-dd-placeholder)').indexOf(node);
				},

				getPortlets: function() {
					var instance = this;

					var contentWrapperSelector = instance.get('contentWrapperSelector');
					var portletSelector = instance.get('portletSelector');
					var portletsSelector = [contentWrapperSelector, portletSelector].join(' ');

					return A.all(portletsSelector);
				},

				refresh: function(portlet) {
					var instance = this;

					portlet = A.get(portlet);

					if (portlet) {
						instance.add(portlet);
					}
				},

				saveLayout: function(options) {
					var instance = this;

					var data = {
						doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
						p_l_id: themeDisplay.getPlid()
					};

					A.mix(data, options);

					A.io.request(
						themeDisplay.getPathMain() + '/portal/update_layout',
						{
							data: data,
							method: 'POST'
						}
					);
				},

				saveIndex: function(portletNode, columnNode) {
					var instance = this;

					var currentColumnId = Liferay.Util.getColumnId(columnNode.get('id'));
					var portletId = Liferay.Util.getPortletId(portletNode.get('id'));
					var position = instance.findIndex(portletNode);

					if (instance.hasMoved(portletNode)) {
						instance.saveLayout(
							{
								cmd: 'move',
								p_p_col_id: currentColumnId,
								p_p_col_pos: position,
								p_p_id: portletId
							}
						);
					}
				},

				hasMoved: function(dragNode) {
					var instance = this;

					var moved = false;
					var curPortletInfo = instance.curPortletInfo;

					if (curPortletInfo) {
						var currentIndex = instance.findIndex(dragNode);
						var currentParent = dragNode.get('parentNode');

						if ((curPortletInfo.originalParent != currentParent) ||
							(curPortletInfo.originalIndex != currentIndex)) {
							moved = true;
						}
					}

					return moved;
				},

				hasPortlets: function(columnNode) {
					var instance = this;

					var portletSelector = instance.get('portletSelector');

					return !!columnNode.one(portletSelector);
				},

				_activateColumnUI: function(columnNode) {
					var instance = this;

					columnNode.addClass('active-area');
				},

				_bindDragDropListeners: function() {
					var instance = this;

					var portletHandler = instance.get('portletHandler');

					portletHandler.on('drag:start', A.bind(instance._onPortletDragStart, instance));
					portletHandler.on('drag:end', A.bind(instance._onPortletDragEnd, instance));

					instance.on('drag:end', instance._onPortletDragEnd);
					instance.on('drop:enter', instance._onColumnDropEnter);
					instance.on('drop:exit', instance._onColumnDropExit);
				},

				_bindDragPortlets: function() {
					var instance = this;

					var portletHandler = new Layout.ColumnPortlet(
						{
							nodes: instance.getPortlets()
						}
					);

					instance.set('portletHandler', portletHandler);
				},

				_bindDropColumns: function() {
					var instance = this;

					var dropZones = instance.get('dropZones');

					dropZones.each(instance._setupDroppable, instance);
				},

				_deactivateColumnUI: function(columnNode) {
					var instance = this;

					columnNode.removeClass('active-area');
				},

				_onColumnDropEnter: function(event) {
					var instance = this;

					var portletNode = event.drag.get('node');
					var dropNode = event.target.get('node');

					instance._activateColumnUI(dropNode);

					var portletHandler = instance.get('portletHandler');
					var placeholder = portletHandler.get('placeholder');

					var columnSelector = instance.get('columnSelector');
					var portletColumn = dropNode.one(columnSelector);

					if (portletNode.get('parentNode') != portletColumn) {
						portletColumn.append(placeholder);
					}

					event.stopImmediatePropagation();
				},

				_onColumnDropExit: function(event) {
					var instance = this;
					var dropNode = event.target.get('node');

					instance._deactivateColumnUI(dropNode);

					event.stopImmediatePropagation();
				},

				_onPortletDragEnd: function(event) {
					var instance = this;

					var drag = event.target;
					var portletNode = drag.get('node');
					var columnNode = portletNode.get('parentNode');

					instance.saveIndex(portletNode, columnNode);

					instance._setEmptyClassUI(columnNode);

					instance._setDropZonesUI(false);
				},

				_onPortletDragStart: function(event) {
					var instance = this;

					instance._updatePortletInfo(event);

					instance._setDropZonesUI(true);
				},

				_setDropZonesUI: function(dragging) {
					var instance = this;

					var gridSelector = instance.get('gridSelector');
					var layoutGrid = A.one(gridSelector);
					var dropZones = instance.get('dropZones');

					if (dragging) {
						if (layoutGrid) {
							layoutGrid.addClass('dragging');
						}

						dropZones.addClass('drop-area');
					}
					else {
						if (layoutGrid) {
							layoutGrid.removeClass('dragging');
						}

						dropZones.removeClass('drop-area');
						dropZones.removeClass('active-area');
					}
				},

				_setEmptyClassUI: function(columnNode) {
					var instance = this;

					var curPortletInfo = instance.curPortletInfo;

					if (curPortletInfo) {
						var originalParent = curPortletInfo.originalParent;
						var columnHasPortlets = instance.hasPortlets(columnNode);
						var originalColumnHasPortlets = instance.hasPortlets(originalParent);

						columnNode.toggleClass('empty', !columnHasPortlets);
						originalParent.toggleClass('empty', !originalColumnHasPortlets);
					}
				},

				_setupDroppable: function(node) {
					var instance = this;

					new A.DD.Drop(
						{
							node: node,
							bubbles: instance,
							groups: ['portlets']
						}
					);
				},

				_updatePortletInfo: function(event) {
					var instance = this;

					var drag = event.target;
					var portletNode = drag.get('node');

					instance.curPortletInfo = {
						node: portletNode,
						originalIndex: instance.findIndex(portletNode),
						originalParent: portletNode.get('parentNode')
					};
				}
			}
		);

		var FreeFormLayout = function() {
			FreeFormLayout.superclass.constructor.apply(this, arguments);
		};

		FreeFormLayout.NAME = 'FreeFormLayout';

		A.extend(
			FreeFormLayout,
			ColumnLayout,
			{
				portletZIndex: 100,

				alignPortlet: function(portletNode, referenceNode) {
					var instance = this;

					portletNode.setXY(referenceNode.getXY());

					instance.savePosition(portletNode);
				},

				bindUI: function() {
					var instance = this;

					FreeFormLayout.superclass.bindUI.apply(this, arguments);

					instance.getPortlets().each(
						function(item, index, collection) {
							instance._setupNodeResize(item);
							instance._setupNodeStack(item);
						}
					);

					var portletHandler = instance.get('portletHandler');

					portletHandler.after('drag:start', instance._afterPortletDragStart);
				},

				savePosition: function(portletNode) {
					var instance = this;

					var portletId = Liferay.Util.getPortletId(portletNode.get('id'));

					instance.saveLayout(
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

				_activateColumnUI: function() {},

				_afterPortletDragStart: function(event) {
					var instance = this;

					Layout.placeholder.hide();
				},

				_bindDragPortlets: function() {
					var instance = this;

					var portletHandler = new Layout.FreeFormPortlet(
						{
							nodes: instance.getPortlets()
						}
					);

					instance.set('portletHandler', portletHandler);
				},

				_deactivateColumnUI: function() {},

				_onPortletMouseDown: function(event) {
					var instance = this;

					var portlet = event.currentTarget;

					portlet.setStyle('zIndex', instance.portletZIndex++);
				},

				_setDropZonesUI: function() {},

				_setupNodeResize: function(node) {
					var instance = this;

					var resizable = node.hasClass('aui-resize');

					if (!resizable) {
						new A.Resize(
							{
								after: {
									end: function(event) {
										var info = event.info;

										var portletNode = this.get('node');
										var internalNode = portletNode.one('.portlet');

										if (internalNode) {
											internalNode.set('offsetHeight', info.height)
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
				}
			}
		);

		var ColumnPortlet = function() {
			ColumnPortlet.superclass.constructor.apply(this, arguments);
		};

		Layout.placeholder = A.Node.create('<div class="portlet-boundary portlet-dd-placeholder"></div>');

		ColumnPortlet.NAME = 'ColumnPortlet';

		ColumnPortlet.ATTRS = {
			currentPortletNode: {
				value: null
			},

			dd: {
				setter: function(val) {
					return A.merge(
						{
							groups: ['portlets'],
							handles: ['.portlet-title,.portlet-title-default'],
							plugins: [
								{
									fn: A.Plugin.DDWinScroll,
									cfg: {
										horizontal: false,
										scrollDelay: 150
									}
								}
							]
						},
						val || {}
					);
				}
			},

			dragging: {
				value: false,
				validator: isBoolean
			},

			dropContainer: {
				value: function(event) {
					var instance = this;
					var drop = event.drop;
					var dropNode = drop.get('node');
					var dropOn = instance.get('dropOn');

					var container = dropNode.one(dropOn);
					var dropActive = dropNode.one('.yui-dd-drop-over');

					if (dropActive) {
						container = dropActive.one(dropOn);
					}

					return container;
				}
			},

			dropOn: {
				value: '.lfr-portlet-column'
			},

			helper: {
				valueFn: function() {
					var helper = A.Node.create(Liferay.Template.PORTLET);

					helper.addClass('aui-proxy');

					return helper;
				}
			},

			placeholder: {
				value: Layout.placeholder
			},

			sortCondition: {
				value: function(event) {
					var dropNode = event.drop.get('node');

					return hasClass(dropNode.get('id'), 'portlet-boundary');
				}
			}
		};

		A.extend(
			ColumnPortlet,
			A.NestedList,
			{
				getCurrentPortletTitle: function() {
					var instance = this;

					var title = null;
					var currentPortletNode = instance.get('currentPortletNode');

					if (currentPortletNode) {
						title = currentPortletNode.one('.portlet-title,.portlet-title-default').html();
					}

					return title;
				},

				registerPortlet: function(portletNode) {
					var instance = this;

					instance.add(portletNode);
				},

				registerPortlets: function(portletNodeList) {
					var instance = this;

					instance.addAll(portletNodeList);
				},

				_onDragEnd: function(event) {
					var instance = this;

					instance.set('dragging', false);

					ColumnPortlet.superclass._onDragEnd.apply(this, arguments);

					Liferay.Layout.layoutHandler._setDropZonesUI(false);
				},

				_onDragExit: function(event) {
					var instance = this;

					var drag = event.target;
					var portletNode = drag.get('node');

					var hasActiveAncestor = portletNode.ancestor('.lfr-column.active-area');

					if (hasActiveAncestor) {
						ColumnPortlet.superclass._onDragExit.apply(this, arguments);
					}
				},

				_onDragStart: function(event) {
					var instance = this;

					var drag = event.target;
					var portletNode = drag.get('node');
					var helper = instance.get('helper');

					instance.set('dragging', true);
					instance.set('currentDrag', drag);
					instance.set('currentPortletNode', portletNode);

					ColumnPortlet.superclass._onDragStart.apply(this, arguments);

					instance._syncHelperSizeUI();
					instance._syncHelperTitleUI();
					instance._syncHelperZIndexUI();

					Liferay.Layout.layoutHandler._setDropZonesUI(true);
				},

				_syncHelperSizeUI: function() {
					var instance = this;

					var helper = instance.get('helper');
					var placeholder = instance.get('placeholder');
					var offsetWidth = placeholder.get('offsetWidth');
					var offsetHeight = placeholder.get('offsetHeight');

					helper.set('offsetWidth', offsetWidth);
					helper.set('offsetHeight', offsetHeight);
				},

				_syncHelperTitleUI: function() {
					var instance = this;

					var helper = instance.get('helper');
					var title = instance.getCurrentPortletTitle();

					helper.one('.portlet-title').html(title);
				},

				_syncHelperZIndexUI: function() {
					var instance = this;

					var helper = instance.get('helper');

					helper.setStyle('zIndex', Liferay.zIndex.DRAG_ITEM);
				}
			}
		);

		var FreeFormPortlet = function() {
			ColumnPortlet.superclass.constructor.apply(this, arguments);
		};

		FreeFormPortlet.NAME = 'FreeFormPortlet';

		FreeFormPortlet.ATTRS = {
			proxy: {
				value: {
					hideOnEnd: false
				}
			},

			sortCondition: {
				value: function(event) {
					var dropNode = event.drop.get('node');

					return hasClass(dropNode.get('id'), 'portlet-nested-portlets');
				}
			}
		};

		A.extend(
			FreeFormPortlet,
			ColumnPortlet,
			{
				_onDragEnd: function(event) {
					var instance = this;

					var portletNode = event.target.get('node');
					var helper = event.target.get('dragNode');

					portletNode.show();

					Liferay.Layout.layoutHandler.alignPortlet(portletNode, helper);

					helper.hide();

					FreeFormPortlet.superclass._onDragEnd.apply(this, arguments);
				},

				_syncHelperSizeUI: function() {
					var instance = this;

					var helper = instance.get('helper');
					var currentPortletNode = instance.get('currentPortletNode');

					currentPortletNode.show();

					var offsetWidth = currentPortletNode.get('offsetWidth');
					var offsetHeight = currentPortletNode.get('offsetHeight');

					helper.set('offsetWidth', offsetWidth);
					helper.set('offsetHeight', offsetHeight);

					currentPortletNode.hide();
				}
			}
		);

		Layout.ColumnLayout = ColumnLayout;
		Layout.FreeFormPortlet = FreeFormPortlet;
		Layout.ColumnPortlet = ColumnPortlet;

		Liferay.Layout = Layout;
	},
	'',
	{
		requires: ['dd', 'io-request', 'nested-list', 'resize'],
		use: []
	}
);