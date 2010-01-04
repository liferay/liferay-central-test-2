AUI().add(
	'liferay-layout',
	function(A) {
		var DOM = A.DOM;
		var DDM = A.DD.DDM;

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

		var PortalLayout = function() {
			PortalLayout.superclass.constructor.apply(this, arguments);
		};

		PortalLayout.NAME = 'portallayout';

		A.extend(
			PortalLayout,
			A.Base,
			{
				initializer: function(options) {
					var instance = this;

					instance._boxSelector = options.boxSelector;
					instance._columnSelector = options.columnSelector;
					instance._handleSelector = options.handleSelector;
				},

				add: function() {},

				findIndex: function(node) {
					var instance = this;

					var parentNode = node.get('parentNode');

					return parentNode.queryAll('> .portlet-boundary').indexOf(node);
				},

				initializeColumns: function() {},

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
				}
			}
		);

		var ColumnLayout = function() {
			ColumnLayout.superclass.constructor.apply(this, arguments);
		};

		ColumnLayout.NAME = 'columns';

		A.extend(
			ColumnLayout,
			PortalLayout,
			{
				initializer: function(options) {
					var instance = this;

					instance._syncShims = A.bind(DDM.syncActiveShims, DDM, true);

					instance.after('drag:end', instance._onDragEnd);
					instance.after('drag:exit', instance._onDragExit);
					instance.after('drag:over', instance._onDragOver);
					instance.after('drag:start', instance._onDragStart);

					instance.after('drop:enter', instance._onDropEnter);
					instance.after('drop:exit', instance._onDropExit);
					instance.after('drop:hit', instance._onDropHit);

					instance._grid = A.all(options.grid);

					instance._gridColumns = '.lfr-column';

					instance.dropZones = [];

					var columns = A.all(instance._gridColumns);

					columns.each(instance.initializeColumns, instance);

					instance._columns = columns;
				},

				add: function(portlet) {
					var instance = this;

					portlet = A.get(portlet);

					if (!portlet._registeredDraggable) {
						var draggablePortlet = new DraggablePortlet(
							{
								bubbles: instance,
								groups: 'portlets',
								handles: [instance._handleSelector],
								node: portlet,
								target: true
							}
						)
						.plug(
							A.Plugin.DDProxy,
							{
								borderStyle: '0',
								moveOnEnd: false
							}
						)
						.plug(A.Plugin.DDWinScroll);

						var handle = portlet.one(instance._handleSelector);

						if (handle) {
							handle.setStyle('cursor', 'move');
						}

						portlet._registeredDraggable = true;
					}
				},

				getCurrentSection: function(event) {
					var instance = this;

					var drag = event.drag;
					var drop = event.drop;

					var y = drag.mouseXY[1];

					var region = drop.region;

					var bottomEnd = region.bottom;
					var topStart = region.top;

					var height = (bottomEnd - topStart);

					var middle = topStart + (height / 2);

					var topEnd = middle;
					var bottomStart = middle;

					if (drop.get('node').get('id').indexOf('118_INSTANCE') > -1) {
						var range = 40;

						topEnd = topStart + Math.min(middle, range);
						bottomStart = bottomEnd - Math.min(middle, range);
					}

					var section = 0;

					if (y > topStart && y < topEnd) {
						section = 1;
					}
					else if (y > bottomStart && y < bottomEnd) {
						section = 2;
					}

					return section;
				},

				hasMoved: function(node) {
					var instance = this;

					var columnOrderChanged = (node.get('previousSibling') != instance._originalPreviousSibling);
					var columnContainerChanged = (node.get('parentNode') != instance._originalParent);

					return columnOrderChanged || columnContainerChanged;
				},

				initializeColumns: function(node, i, nodeList) {
					var instance = this;

					new A.DD.Drop(
						{
							node: node,
							bubbles: instance,
							groups: 'portlets'
						}
					);

					var portlets = node.queryAll('.portlet-boundary');

					portlets.each(instance.add, instance);

					instance.dropZones.push(node._node);
				},

				isValidDropZone: function(node) {
					var instance = this;

					return node && node.hasClass('lfr-column');
				},

				refresh: function(portlet) {
					var instance = this;

					portlet = A.get(portlet);

					if (portlet.get('id').indexOf('p_p_id_118_') === 0) {
						var columns = portlet.queryAll(instance._gridColumns);

						columns.each(instance.initializeColumns, instance);
					}

					ColumnLayout.superclass.refresh.apply(instance, arguments);
				},

				setInsideNested: function(node) {
					var instance = this;

					instance._insideNested = (node.get('id').indexOf('118_INSTANCE') > -1 &&
											!node.hasClass('portlet-boundary'));
				},

				startDragging: function() {
					var instance = this;

					instance._grid.addClass('dragging');
				},

				stopDragging: function() {
					var instance = this;

					instance._grid.removeClass('dragging');

					var dropZones = instance.dropZones;

					for (var i = dropZones.length - 1; i >= 0; i--) {
						DOM.removeClass(dropZones[i], 'drop-area');
						DOM.removeClass(dropZones[i], 'active-area');
					}
				},

				_onDragEnd: function(event) {
					var instance = this;

					var drag = event.target;

					var original = drag.get('placeholder');

					original.setStyle('visibility', '');

					instance.stopDragging();
				},

				_onDragExit: function(event) {
					var instance = this;

					var drag = event.target;
					var drop = event.drop;

					var previousDropNode = drop.get('node');
					var currentDropNode = DDM.activeDrop.get('node');

					if (instance.isValidDropZone(previousDropNode)) {
						previousDropNode.removeClass('active-area');
					}

					var invalidDrop = (!currentDropNode.hasClass('portlet-boundary') &&
					 					!instance.isValidDropZone(currentDropNode));

					var sameNode = (previousDropNode.compareTo(currentDropNode));

					var proxy = drag._getProxy();
					var action = 'removeClass';

					if (invalidDrop || sameNode) {
						action = 'addClass';
					}

					instance.setInsideNested(currentDropNode);

					proxy[action]('not-intersecting');
				},

				_onDragOver: function(event) {
					var instance = this;

					var drag = event.drag;
					var drop = event.drop;

					var dragNode = drag.get('placeholder');
					var dropNode = drop.get('node');

					if (dropNode.get('className').indexOf('portlet-boundary') > -1 && !instance._insideNested) {
						var currentSection = instance.getCurrentSection(event);

						if (currentSection == 1) {
							dropNode.placeBefore(dragNode);
						} else if (currentSection == 2) {
							dropNode.placeAfter(dragNode);
						}

						DDM.syncActiveShims();
					}
				},

				_onDragStart: function(event) {
					var instance = this;

					var drag = event.target;

					var proxy = drag.get('dragNode');
					var original = drag.get('placeholder');

					drag._updateProxy();

					instance.startDragging();

					instance._originalPreviousSibling = original.get('previousSibling');
					instance._originalParent = original.get('parentNode');

					var dropZones = instance.dropZones;

					for (var i = dropZones.length - 1; i >= 0; i--) {
						DOM.addClass(dropZones[i], 'drop-area');
					}
				},

				_onDropEnter: function(event) {
					var instance = this;

					var drag = event.drag;
					var drop = event.drop;

					var dragNode = drag.get('placeholder');
					var dropNode = drop.get('node');

					instance.setInsideNested(dropNode);

					if (instance.isValidDropZone(dropNode)) {
						dropNode.addClass('active-area');

						drag._getProxy().removeClass('not-intersecting');

						if (!dropNode.contains(dragNode)) {
							try {
								var portletColumn = dropNode.one(instance._columnSelector);

								if (portletColumn) {
									portletColumn.appendChild(dragNode);
								}
							}
							catch (e) {}
						}
					}

					A.Lang.later(50, A, instance._syncShims);
				},

				_onDropExit: function(event) {
					var instance = this;

					var drop = event.target;
					var dropNode = drop.get('node');

					instance.setInsideNested(dropNode);
				},

				_onDropHit: function(event) {
					var instance = this;

					var drag = event.drag;
					var dragNode = drag.get('placeholder');
					var drop = event.drop;
					var dropNode = drop.get('node');

					var portletColumn;

					 if (dropNode.hasClass('portlet-boundary')) {
						portletColumn = dropNode.get('parentNode');
						dropNode = portletColumn.get('parentNode');
					} else if (instance.isValidDropZone(dropNode)) {
						portletColumn = dropNode.one(instance._columnSelector);

						if (portletColumn != instance._originalParent) {
							if (!instance._originalParent.one('.portlet-boundary')) {
								instance._originalParent.addClass('empty');
							}
						}
					}

					if (portletColumn) {
						portletColumn.removeClass('empty');
					}

					if (instance.hasMoved(dragNode)) {
						var position = instance.findIndex(dragNode);
						var currentColumnId = Liferay.Util.getColumnId(dragNode.get('parentNode').get('id'));
						var portletId = Liferay.Util.getPortletId(dragNode.get('id'));

						instance.saveLayout(
							{
								cmd: 'move',
								p_p_col_id: currentColumnId,
								p_p_col_pos: position,
								p_p_id: portletId
							}
						);
					}
				}
			}
		);

		var FreeFormLayout = function() {
			FreeFormLayout.superclass.constructor.apply(this, arguments);
		};

		FreeFormLayout.NAME = 'freeform';

		A.extend(
			FreeFormLayout,
			PortalLayout,
			{
				initializer: function(options) {
					var instance = this;

					var columns = A.all(instance._columnSelector);

					instance.after('drag:end', instance._onDragEnd);
					instance.after('drag:start', instance._onDragStart);

					columns.each(instance.initializeColumns, instance);
				},

				add: function(portlet) {
					var instance = this;

					portlet = A.get(portlet);

					if (!portlet._registeredDraggable) {
						var handle = portlet.one(instance._handleSelector);

						if (handle) {
							handle.setStyle('cursor', 'move');
						}

						portlet.setStyle('position', 'absolute');

						var helperZIndex = instance._maxZIndex + 10;

						var draggablePortlet = new DraggablePortlet(
							{
								bubbles: instance,
								groups: 'portlets',
								handles: [instance._handleSelector],
								node: portlet
							}
						)
						.plug(
							A.Plugin.DDProxy,
							{
								borderStyle: '0'
							}
						)
						.plug(A.Plugin.DDWinScroll);

						portlet.on('mousedown', instance._onMouseDown, instance);

						var resize = new A.Resize(
							{
								node: portlet,
								proxy: true,
								setSize: false
							}
						);

						var resizeBox = portlet.one('.portlet-content-container, .portlet-borderless-container');

						var delta = (portlet.get('offsetHeight') || 0) - (resizeBox.get('offsetHeight') || 0);

						resize.on(
							'resize',
							function(event) {
								var rBoxHeight = parseInt(resizeBox.getStyle('height'), 10);

								var portletHeight = event.height;

								var newHeight = event.height - delta;
								var newWidth = event.width;

								resizeBox.setStyle('height', newHeight + 'px');

								portlet.setStyles(
									{
										height: 'auto',
										width: newWidth + 'px'
									}
								);

								instance._savePosition(portlet);
							}
						);

						if ((parseInt(portlet.getStyle('top'), 10) + parseInt(portlet.getStyle('left'), 10)) == 0) {
							portlet.setStyles(
								{
									left: (20 * portlet.columnPos || instance._portletCounter) + 'px',
									top: (20 * portlet.columnPos || instance._portletCounter) + 'px'
								}
							);

							instance._portletCounter++;
						}

						portlet._registeredDraggable = true;

						instance._current = portlet;
					}
				},

				initializeColumns: function(node, i, nodeList) {
					var instance = this;

					var portlets = node.queryAll('.portlet-boundary');

					portlets.each(instance.add, instance);
				},

				refresh: function(portlet) {
					var instance = this;

					if (portlet) {
						instance.add(portlet);
					}
				},

				_moveToTop: function(portlet) {
					var instance = this;

					var container = portlet.get('parentNode');

					portlet.oldPosition = instance.findIndex(portlet);

					container.appendChild(portlet);
				},

				_onDragEnd: function(event) {
					var instance = this;

					var drag = event.target;

					var portlet = drag.get('placeholder');
					var proxy = drag.get('dragNode');

					var left = parseInt(portlet.getStyle('left'), 10);
					var top = parseInt(portlet.getStyle('top'), 10);

					instance._savePosition(portlet);
				},

				_onDragStart: function(event) {
					var instance = this;

					var drag = event.target;

					var original = drag.get('placeholder');

					drag._updateProxy();

					instance._moveToTop(original);
				},

				_onMouseDown: function(event) {
					var instance = this;

					var portlet = event.currentTarget;

					if (instance._current != portlet) {
						instance._moveToTop(portlet);
						instance._savePosition(portlet, true);
						instance._current = portlet;

						portlet.setStyle('zIndex', instance._maxZIndex);

						event.halt();
					}
				},

				_savePosition: function(portlet, wasClicked) {
					var instance = this;

					var portletColumn = portlet.get('parentNode');

					var resizeBox = portlet.one('.portlet-content-container, .portlet-borderless-container');
					var position = instance.findIndex(portlet);
					var portletId = Liferay.Util.getPortletId(portlet.get('id'));
					var changedIndex = (position != portlet.oldPosition);
					var changedPosition = (resizeBox && !wasClicked);

					if (changedIndex || changedPosition) {
						if (changedIndex) {
							var currentColumnId = Liferay.Util.getColumnId(portletColumn.get('id'));

							instance.saveLayout(
								{
									cmd: 'move',
									p_p_col_id: currentColumnId,
									p_p_col_pos: position,
									p_p_id: portletId
								}
							);
						}

						if (changedPosition) {
							instance.saveLayout(
								{
									cmd: 'drag',
									height: resizeBox.getStyle('height'),
									left: portlet.getStyle('left'),
									p_p_id: portletId,
									top: portlet.getStyle('top'),
									width: portlet.getStyle('width')
								}
							);
						}
					}
				},

				_portletCounter: 1,

				_maxZIndex: 99
			}
		);

		var DraggablePortlet = function() {
			DraggablePortlet.superclass.constructor.apply(this, arguments);
		};

		DraggablePortlet.ATTRS = {
			placeholder: {
				getter: function() {
					var instance = this;

					return instance.get('node');
				}
			}
		};

		A.extend(
			DraggablePortlet,
			A.DD.Drag,
			{
				_createProxy: function() {
					var instance = this;

					var proxy = A.Node.create(Liferay.Template.PORTLET);

					proxy.addClass('aui-proxy');

					return proxy;
				},

				_getProxy: function() {
					var instance = this;

					if (!instance._proxy) {
						var proxy = instance.get('dragNode');

						var proxyDisplay = proxy.one('.aui-proxy');

						if (!proxyDisplay) {
							proxyDisplay = instance._createProxy();

							proxy.append(proxyDisplay);
						}

						instance._proxy = proxyDisplay;
					}

					return instance._proxy;
				},

				_updateProxy: function() {
					var instance = this;

					var original = instance.get('placeholder');
					var proxy = instance.get('dragNode');

					var width = original.get('offsetWidth');
					var height = original.get('offsetHeight');

					var proxyDisplay = instance._getProxy();

					var titleHtml = original.one('.portlet-title, .portlet-title-default').html();

					proxyDisplay.one('.portlet-title').html(titleHtml);

					proxyDisplay.setStyles(
						{
							height: height + 'px',
							width: width + 'px',
							zIndex: Liferay.zIndex.DRAG_ITEM
						}
					);

					proxy.setStyles(
						{
							height: 'auto',
							width: 'auto'
						}
					);
				}
			}
		);

		Layout.PortalLayout = PortalLayout;
		Layout.ColumnLayout = ColumnLayout;
		Layout.FreeFormLayout = FreeFormLayout;

		Layout.DraggablePortlet = DraggablePortlet;

		Liferay.Layout = Layout;
	},
	'',
	{
		requires: ['dd', 'io-request', 'resize'],
		use: []
	}
);