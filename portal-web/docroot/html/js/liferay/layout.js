(function() {
	var Dom = Expanse.Dom;
	var Event = Expanse.Event;
	var DDM = Expanse.DragDrop;

	var Layout = {
		init: function(options) {
			var instance = this;

			instance.isFreeForm = options.freeForm;
			instance._draggables = {};

			var layoutHandler;

			if (!options.freeForm) {
				layoutHandler = instance.Columns;
			}
			else {
				layoutHandler = instance.FreeForm;
			}

			instance._useCloneProxy = options.clonePortlet;

			layoutHandler.init(options);

			instance.layoutHandler = layoutHandler;
		},

		bind: function(event, fn) {
			var instance = this;

			var draggables = instance._draggables;

			if (event.indexOf('Event') < 0) {
				event += 'Event';
			}

			for (var i in draggables) {
				draggables[i].on(event, fn);
			}

			return instance;
		},

		refresh: function(portletBound) {
			var instance = this;

			instance.layoutHandler.refresh(portletBound);
		},

		showTemplates: function() {
			var instance = this;

			var url = themeDisplay.getPathMain() + '/layout_configuration/templates';

			new Expanse.Popup(
				{
					body: {
						url: url,
						data: {
							p_l_id: themeDisplay.getPlid(),
							doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
							redirect: Liferay.currentURL
						}
					},
					header: Liferay.Language.get('layout'),
					modal: true,
					width: 700,
					xy: ['center', 100]
				}
			);
		},

		trigger: function(event) {
			var instance = this;

			var draggables = instance._draggables;

			if (event.indexOf('Event') < 0) {
				event += 'Event';
			}

			for (var i in draggables) {
				draggables[i].fireEvent(event);
			}
		},

		_findIndex: function(portlet, parentNode) {
			var instance = this;

			parentNode = parentNode || portlet.parentNode;

			return jQuery('> .portlet-boundary', parentNode).index(portlet);
		},

		_saveLayout: function(options) {
			var instance = this;

			var data = {
				doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
				p_l_id: themeDisplay.getPlid()
			};

			jQuery.extend(data, options);

			jQuery.ajax(
				{
					url: themeDisplay.getPathMain() + '/portal/update_layout',
					data: data
				}
			);
		}
	};

	var Columns = {
		init: function(options) {
			var instance = this;

			instance._columns = options.columnSelector;
			instance._portlets = options.boxSelector;

			instance._grid = jQuery(options.grid);

			instance._handleSelector = options.handleSelector;
			instance._boxSelector = options.boxSelector;
			instance._onCompleteCallback = options.onComplete;

			instance._gridColumns = '.lfr-column';

			var columns = jQuery(instance._gridColumns);

			instance.dropZones = [];

			for (var i = columns.length - 1; i >= 0; i--) {
				new Expanse.Droppable(columns[i], 'portlets');

				instance.dropZones[i] = columns[i];
			}

			var portlets = columns.children(0).find('> ' + instance._boxSelector);

			for (var i = portlets.length - 1; i >= 0; i--) {
				instance.add(portlets[i]);
			}
		},

		add: function(portlet) {
			var instance = this;

			var draggable = new columnPortlet(portlet, 'portlets');
			var handle = jQuery(instance._handleSelector, portlet);

			var id = Expanse.generateId(handle[0]);

			draggable.setHandleElId(id);

			handle.css('cursor', 'move');

			Layout._draggables[portlet.id] = draggable;
		},

		getGrid: function() {
			var instance = this;

			return instance._grid || jQuery([]);
		},

		refresh: function(portlet) {
			var instance = this;

			if (portlet.id.indexOf('p_p_id_118_') === 0) {
				var jPortlet = jQuery(portlet);

				var columns = jPortlet.find(instance._gridColumns);

				for (var i = columns.length - 1; i >= 0; i--){
					new Expanse.Droppable(columns[i], 'portlets');

					instance.dropZones.push(columns[i]);
				}
			}

			instance.add(portlet);
		},

		startDragging: function() {
			var instance = this;

			instance._grid.addClass('dragging');
		},

		stopDragging: function() {
			var instance = this;

			instance._grid.removeClass('dragging');

			Dom.removeClass(instance.dropZones, 'drop-area');
			Dom.removeClass(instance.dropZones, 'active-area');
		}
	};

	var FreeForm = {
		init: function(options) {
			var instance = this;

			instance._columns = jQuery(options.columnSelector);
			var portlets = jQuery(options.boxSelector);

			for (var i = portlets.length - 1; i >= 0; i--) {
				instance.add(portlets[i]);
			}

			instance._portlets = portlets;
		},

		add: function(portlet) {
			var instance = this;

			var handle = jQuery('.portlet-header-bar, .portlet-title-default, .portlet-topper', portlet);

			handle.css('cursor', 'move');

			var jPortlet = jQuery(portlet);

			jPortlet.css('position', 'absolute');

			var helperZIndex = instance._maxZIndex + 10;

			var draggable = new freeFormPortlet(portlet, 'portlets');

			var id = handle.generateId();

			draggable.setHandleElId(id);

			Layout._draggables[portlet.id] = draggable;

			jPortlet.mousedown(
				function(event) {
					if (instance._current != this) {
						instance._moveToTop(this);
						instance._savePosition(this, true);
						instance._current = this;
						this.style.zIndex = instance._maxZIndex;
					}
				}
			);

			var resize = new Expanse.Resize(portlet, {
					proxy: true,
					setSize: false
				}
			);

			var resizeBox = jQuery('.portlet-content-container, .portlet-borderless-container', portlet);

			var delta = (jPortlet.height() || 0) - (resizeBox.height() || 0);

			resize.on(
				'resize',
				function(data) {
					var rBoxHeight = parseInt(resizeBox[0].style.height, 10);

					var portletHeight = data.height;

					var newHeight = data.height - delta;
					var newWidth = data.width;

					resizeBox.css('height', newHeight);

					jPortlet.css(
						{
							height: 'auto',
							width: newWidth
						}
					);

					instance._savePosition(portlet);
				}
			);

			if ((parseInt(portlet.style.top, 10) + parseInt(portlet.style.left, 10)) == 0) {
				portlet.style.top = (20 * portlet.columnPos || instance._portletCounter) + 'px';
				portlet.style.left = (20 * portlet.columnPos || instance._portletCounter) + 'px';

				instance._portletCounter++;
			}

			instance._current = portlet;
		},

		refresh: function(portletBound) {
			var instance = this;

			if (portletBound) {
				instance.add(portletBound);
			}
		},

		_moveToTop: function(portlet, temporary) {
			var instance = this;

			var container = portlet.parentNode;
			portlet.oldPosition = Layout._findIndex(portlet);

			if (!temporary) {
				container.appendChild(portlet);
			}
			else {
				portlet.style.zIndex = instance._maxZIndex + 5;

				jQuery(portlet).one(
					'click',
					function(event) {
						instance._moveToTop(this);
					}
				);
			}
		},

		_savePosition: function(portlet, wasClicked) {
			var instance = this;

			var resizeBox = jQuery(portlet).find('.portlet-content-container, .portlet-borderless-container')[0];
			var position = Layout._findIndex(portlet);
			var portletId = Liferay.Util.getPortletId(portlet.id);
			var changedIndex = (position != portlet.oldPosition);
			var changedPosition = (resizeBox && !wasClicked);

			if (changedIndex || changedPosition) {
				if (changedIndex) {
					var currentColumnId = Liferay.Util.getColumnId(portlet.parentNode.id);

					Layout._saveLayout(
						{
							cmd: 'move',
							p_p_col_id: currentColumnId,
							p_p_col_pos: position,
							p_p_id: portletId
						}
					);
				}

				if (changedPosition) {
					Layout._saveLayout(
						{
							cmd: 'drag',
							height: resizeBox.style.height,
							left: portlet.style.left,
							p_p_id: portletId,
							top: portlet.style.top,
							width: portlet.style.width
						}
					);
				}
			}
		},

		_portletCounter: 1,

		_maxZIndex: 99
	};

	var draggablePortlet = Expanse.DragProxy.extend(
		{
			initialize: function() {
				var instance = this;

				instance._super.apply(instance, arguments);

				var el = instance.getDragEl();

				Dom.setStyle(el, 'opacity', 0.67);

				instance.invalidHandleTypes = {
					A: 'A',
					INPUT: 'INPUT',
					BUTTON: 'BUTTON',
					TEXTAREA: 'TEXTAREA'
				};
			},

			startDrag: function(x, y) {
				var instance = this;

				instance._super.apply(instance, arguments);

				var proxy = instance.getDragEl();
				var original = instance.getEl();

				instance._updateProxy();

				Dom.setStyle(original, 'visibility', 'hidden');
			},

			_createProxy: function() {
				var instance = this;

				var proxy = jQuery(Liferay.Template.PORTLET);

				proxy.addClass('exp-proxy');

				return proxy;
			},

			_getPlaceholder: function() {
				var instance = this;

				return instance.getEl();
			},

			_getProxy: function() {
				var instance = this;

				if (!instance._proxy) {
					var proxy = instance.getDragEl();

					var proxyDisplay = jQuery('.exp-proxy', proxy);

					instance._proxy = proxyDisplay;
				}

				return instance._proxy;
			},

			_updateProxy: function() {
				var instance = this;

				var original = instance.getEl();
				var proxy = instance.getDragEl();

				var obj = jQuery(original);

				var width = original.offsetWidth;
				var height = original.offsetHeight;
				var div = [];

				proxy = jQuery(proxy);

				var proxyDisplay = proxy.find('.exp-proxy');

				if (!proxyDisplay.length) {
					proxyDisplay = instance._createProxy();

					proxy.prepend(proxyDisplay);
				}

				proxy.css('border-width', 0);

				var titleHtml = obj.find('.portlet-title, .portlet-title-default').html();

				proxyDisplay.find('.portlet-title').html(titleHtml);

				proxyDisplay.css(
					{
						height: height,
						width: width,
						zIndex: Liferay.zIndex.DRAG_ITEM
					}
				);

				proxy.css(
					{
						height: 'auto',
						width: 'auto'
					}
				);

				return proxy;
			}
		}
	);

	var columnPortlet = draggablePortlet.extend(
		{
			initialize: function() {
				var instance = this;

				instance._super.apply(this, arguments);

				instance.goingUp = false;

				instance.lastY = 0;
			},

			endDrag: function(event) {
				var instance = this;

				var portlet = instance.getEl();
				var proxy = instance.getDragEl();

				Dom.setStyle(proxy, 'visibility', 'hidden');
				Dom.setStyle(portlet, 'visibility', '');

				if (portlet.parentNode != instance._originalParent) {
					if (!Dom.getElementsByClassName('portlet', 'div', instance._originalParent).length) {
						Dom.addClass(instance._originalParent, 'empty');
					}
				}

				Columns.stopDragging();

				Dom.removeClass(Columns.dropZones, 'drop-area');
				Dom.removeClass(Columns.dropZones, 'active-area');

				Dom.setStyle(proxy, 'top', 0);
				Dom.setStyle(proxy, 'left', 0);
			},

		    onDrag: function(event) {
				var instance = this;

				var y = Event.getPageY(event);

				var tolerance = 5;

		        if (y < instance.lastY && (instance.lastY - y) >= tolerance) {
					instance.goingUp = true;
				} else if (y > instance.lastY && (y - instance.lastY) >= tolerance) {
					instance.goingUp = false;
		        }

				instance.lastY = y;
		    },

			onDragDrop: function(event, id) {
				var instance = this;

				var target = Dom.get(id);
				var portlet = instance.getEl();

				if (target.tagName.toLowerCase() == 'td') {
					jQuery('> .empty', target).removeClass('empty');
				}

				if (DDM.interactionInfo.validDrop) {
					var inNested = (id.indexOf('118_INSTANCE') === 0);

					if ((inNested || !instance._eventProcessed(event)) && instance._hasMoved(portlet) && !event.stopProcessing) {
						var position = Layout._findIndex(portlet, Dom.getFirstChild(target));
						var currentColumnId = Liferay.Util.getColumnId(portlet.parentNode.id);
						var portletId = Liferay.Util.getPortletId(portlet.id);

						var viewport = Liferay.Util.viewport.scroll();
						var portletOffset = jQuery(portlet).offset();

						Layout._saveLayout(
							{
								cmd: 'move',
								p_p_col_id: currentColumnId,
								p_p_col_pos: position,
								p_p_id: portletId
							}
						);

						if (viewport.y > portletOffset.top) {
							window.scrollTo(portletOffset.left, portletOffset.top - 10);
						}

						event.eventProcessed = true;
						event.stopProcessing = true;
					}
				}
			},

			onDragEnter: function(event, id) {
				var instance = this;

				var target = Dom.get(id);

				if (target.tagName.toLowerCase() == 'td') {
					Dom.addClass(target, 'active-area');

					var divColumn = Dom.getFirstChild(target);
					var portlet = instance.getEl();

					if (divColumn != instance._originalParent || (portlet.parentNode == divColumn)) {

						try {
							divColumn.appendChild(instance._getPlaceholder());
						}
						catch (e) {}
					}

					instance._getProxy().removeClass('not-intersecting');
				}
			},

			onDragOut: function(event, id) {
				var instance = this;

				var portlet = instance._getPlaceholder();
		        var target = Dom.get(id);

				if (target.tagName.toLowerCase() == 'td') {

					Dom.removeClass(target, 'active-area');

					instance._getProxy().addClass('not-intersecting');
				}
				else if (id.indexOf('p_p_id_118_') === 0) {
					instance.insideNested = false;
				}

				DDM.refreshCache();
			},

		    onDragOver: function(event, id) {
				var instance = this;

				var portlet = instance._getPlaceholder();
				var target = Dom.get(id);
				var destClass = (target.className || '').toLowerCase();

				if (Dom.hasClass(target, 'portlet-boundary')) {
					var parent = target.parentNode;

					instance.insideNested = (id.indexOf('p_p_id_118_') === 0);

					if (!instance.insideNested) {
						try {
							if (instance.goingUp) {
								parent.insertBefore(portlet, target)
							} else {
								parent.insertBefore(portlet, target.nextSibling);
				            }
						}
						catch (e) {}
					}

		            DDM.refreshCache();
		        }
				else if (target.tagName.toLowerCase() == 'td') {
					var column = target;

					var divColumn = Dom.getFirstChild(target);

					instance._getProxy().removeClass('not-intersecting');

					if (Dom.hasClass(column, 'active-area')) {
						if (Dom.hasClass(divColumn, 'empty')) {
							try {
								Dom.getFirstChild(target).appendChild(portlet);
							}
							catch (e) {}
						}
					}
				}
		    },

			startDrag: function(x, y) {
				var instance = this;

				instance._super.apply(instance, arguments);

				var proxy = instance.getDragEl();
				var original = instance.getEl();

				Columns.startDragging();

				Dom.addClass(Columns.dropZones, 'drop-area');

				instance._originalParent = original.parentNode;
				instance._originalPreviousSibling = Dom.getPreviousSibling(original);
			},

			_eventProcessed: function(event) {
				var instance = this;

				var alreadyProcessed = true;

				if (!event.eventProcessed) {
					alreadyProcessed = false;

					event.eventProcessed = true;
				}

				return alreadyProcessed;
			},

			_hasMoved: function(portlet) {
				var instance = this;

				var columnOrderChanged = (Dom.getPreviousSibling(portlet) != instance._originalPreviousSibling);
				var columnContainerChanged = (portlet.parentNode != instance._originalParent);

				return columnOrderChanged || columnContainerChanged;
			}
		}
	);

	var freeFormPortlet = draggablePortlet.extend(
		{
			endDrag: function(event) {
				var instance = this;

				instance._super.apply(instance, arguments);

				var portlet = instance.getEl();
				var proxy = instance.getDragEl();

				var left = parseInt(portlet.style.left, 10);
				var top = parseInt(portlet.style.top, 10);

				FreeForm._savePosition(portlet);

				Dom.setStyle(proxy, 'visibility', 'hidden');
				Dom.setStyle(portlet, 'visibility', '');

				Dom.setStyle(proxy, 'top', 0);
				Dom.setStyle(proxy, 'left', 0);
			},

			startDrag: function(x, y) {
				var instance = this;

				instance._super.apply(instance, arguments);

				var original = instance.getEl();

				FreeForm._moveToTop(original);
			}
		}
	);

	Layout.Columns = Columns;
	Layout.FreeForm = FreeForm;

	Layout.Portlet = draggablePortlet;
	Layout.columnPortlet = columnPortlet;
	Layout.freeFormPortlet = freeFormPortlet;

	Liferay.Layout = Layout;
})();