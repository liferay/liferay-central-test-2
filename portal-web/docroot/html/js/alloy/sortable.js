(function() {
	var Dom = Alloy.Dom;
	var Event = Alloy.Event;
	var DDM = Alloy.DragDrop;

	var counter = 0;

	Alloy.Sortable = new Alloy.Class(
		{
			initialize: function(options) {
				var instance = this;

				counter += 1;

				instance.options = options;

				instance._containers = jQuery(options.container);
				instance._items = options.items;
				instance._placeHolder = options.placeHolder;
				instance._handle = jQuery(options.handle);
				instance._group = options.group || 'sortGroup' + counter;

				if (typeof options.items == 'string') {
					instance._items = instance._containers.find(options.items);
				}

				var constraint = null;

				if (options.axis) {
					var axis = options.axis.toUpperCase();

					if (axis == 'X' || axis == 'Y') {
						constraint = 'set';

						if (axis == 'X') {
							constraint += 'Y';
						}
						else {
							constraint += 'X';
						}

						constraint += 'Constraint';
					}

					if (!instance.getSortableItem().prototype[constraint]) {
						constraint = null;
					}
				}

				options.constraint = constraint;

				var config = {
					dragElId: 'sortProxy' + counter
				};

				if (jQuery.isFunction(options.helper)) {
					config.helper = options.helper;
				}

				if (options.helperClass) {
					config.helperClass = options.helperClass;
				}

				instance.config = config;

				for (var i = instance._containers.length - 1; i >= 0; i--) {
					instance.addContainer(instance._containers[i]);
				}

				var handle;
				var id;

				for (var i = instance._items.length - 1; i >= 0; i--) {
					instance.add(instance._items[i]);
				}
			},

			add: function(item) {
				var instance = this;

				var options = instance.options;
				var config = instance.config;

				var sortableItem = instance.getSortableItem();

				var draggable = new sortableItem(item, instance._group, config);

				if (options.stop) {
					draggable.on('endDragEvent', options.stop, instance);
				}

				if (options.constraint) {
					draggable[options.constraint](0, 0);

					draggable.on('b4StartDragEvent', instance._flushCache);
				}

				if (options.handle) {
					handle = jQuery(options.handle, item);

					id = Alloy.generateId(handle[0]);

					draggable.setHandleElId(id);
				}
			},

			addContainer: function(container) {
				var instance = this;

				var sortableTarget = instance.getSortableTarget();

				new sortableTarget(container, instance._group);
			},

			getSortableItem: function() {
				var instance = this;

				return Alloy.SortableItem;
			},

			getSortableTarget: function() {
				var instance = this;

				return Alloy.SortableTarget;
			},

			_flushCache: function() {
				var draggable = this;

				draggable.resetConstraints();

				draggable.unsubscribe('b4StartDragEvent', arguments.callee);
			}
		}
	);

	Alloy.SortableItem = Alloy.DragProxy.extend(
		{
			initialize: function(el, group, options) {
				var instance = this;

				instance._super.apply(instance, arguments);

				var defaults = {
					forcePlaceholderSize: true
				};

				instance.options = Alloy.extend({}, defaults, options);

				instance.goingUp = false;
				var el = instance.getEl();

				el = jQuery(el);

				var floatValue = el.css('float');

				instance._isFloat = floatValue != 'none';

				instance.invalidHandleTypes = {
					INPUT: 'INPUT',
					BUTTON: 'BUTTON',
					TEXTAREA: 'TEXTAREA'
				};

				var placeholderOption = instance.options.placeholder;

				if (placeholderOption) {
					instance.placeholder = jQuery(placeholderOption);

					instance.on(
						'b4StartDragEvent',
						function(event) {
							if (instance.options.placeholder) {
								var srcEl = jQuery(instance.getEl());

								instance.originalElWidth = srcEl.width();
								instance.originalElHeight = srcEl.height();

								instance._updatePlaceholder();
							}
						}
					);

					instance.on(
						'endDragEvent',
						function(event) {
							if (instance.options.placeholder) {
								instance._removePlaceholder();
							}
						}
					);
				}

				instance._updateProxy();
			},

			endDrag: function(event) {
				var instance = this;

				var portlet = instance.getEl();
				var proxy = instance.getDragEl();

				Dom.setStyle(proxy, 'visibility', 'hidden');
				Dom.setStyle(portlet, 'visibility', '');

				Dom.setStyle(proxy, 'top', 0);
				Dom.setStyle(proxy, 'left', 0);
			},

			onDrag: function(event) {
				var instance = this;

				var y = Event.getPageY(event);
				var x = Event.getPageX(event);

				var tolerance = 5;

				var value = y;
				var lastValue = instance.lastY;

				if (instance._isFloat) {
					value = x;
					lastValue = instance.lastX;
				}

		        if (value < lastValue && (lastValue - value) >= tolerance) {
					instance.goingUp = true;
				} else if (value > lastValue && (value - lastValue) >= tolerance) {
					instance.goingUp = false;
		        }

				instance.lastY = y;
				instance.lastX = x;
		    },

			onDragDrop: function(e, id) {
				var instance = this;

				if (DDM.interactionInfo.drop.length === 1) {
					var point = DDM.interactionInfo.point;
					var region = DDM.interactionInfo.sourceRegion;

					if (!region.intersect(point)) {
						var target = Dom.get(id);
						var targetDroppable = DDM.getDDById(id);
						var source = instance.getEl();

						target.appendChild(source);

						targetDroppable.isEmpty = false;

						DDM.refreshCache();
					}
				}
		    },

		    onDragOver: function(event, id) {
				var instance = this;

				var portlet = instance.getEl();
				var target = Dom.get(id);

				var targetInstance = DDM.getDDById(id);

				if (!instance._isDroppable(targetInstance)) {
					var destClass = (target.className || '').toLowerCase();

					instance._insert(portlet, target, true);
				}

				if (instance.options.placeholder) {
					instance._updatePlaceholder();
				}

		        DDM.refreshCache();
		    },

			startDrag: function(x, y) {
				var instance = this;

				instance._super.apply(instance, arguments);

				var proxy = instance.getDragEl();
				var original = instance.getEl();

				Dom.setStyle(original, 'visibility', 'hidden');
			},

			_createPlaceHolder: function() {
				var instance = this;

				var srcEl = jQuery(instance.getEl());
				var placeholderOption = instance.options.placeholder;
				var placeholder = null;

				if(placeholderOption && (typeof placeholderOption == 'string')) {
					var className = placeholderOption;

					placeholder = jQuery(document.createElement(srcEl[0].nodeName));
					placeholder.addClass(className);
				}

				return placeholder;
			},

			_insert: function(srcEl, destEl, checkContains) {
				if (!checkContains || !Alloy.Dom.contains(srcEl, destEl)) {
					destEl.parentNode.insertBefore(srcEl, this.goingUp ? destEl : destEl.nextSibling);
	            	DDM.refreshCache();
				}
			},

			_isDroppable: function(target) {
				return (target instanceof Alloy.Droppable);
			},

			_updateProxy: function() {
				var instance = this;

				var proxy = instance.getDragEl();
				var src = instance.getEl();

				var options = instance.options;

				if (options.helper || options.helperClass) {
					Dom.setStyle(proxy, 'border-width', 0);
				}

				if (options.helper) {
					proxy = instance.options.helper.call(instance, src, proxy);
				}
				else if (options.helperClass) {
					Dom.addClass(proxy, options.helperClass);
				}

				return proxy;
			},

			_updatePlaceholder: function(cancelResize) {
				var instance = this;

				var srcEl = jQuery(instance.getEl());
				var placeholderEl = instance.placeholderEl;

				if (!placeholderEl) {
					instance.placeholderEl = instance._createPlaceHolder();
					placeholderEl = instance.placeholderEl;
				}

				if (!cancelResize && instance.options.forcePlaceholderSize) {
					var height = srcEl.height();

					placeholderEl.height(height);
				}

				srcEl.hide();
				instance._insert(placeholderEl[0], srcEl[0]);
			},

			_removePlaceholder: function() {
				var instance = this;

				var srcEl = jQuery(instance.getEl());
				var placeholderEl = instance.placeholderEl;

				if (placeholderEl && placeholderEl.length) {
					var placeholderEl = instance.placeholderEl[0];

					srcEl.show();

					if (placeholderEl.parentNode) {
						placeholderEl.parentNode.removeChild(placeholderEl);
					}
				}
			}
		}
	);

	Alloy.SortableTarget = Alloy.Droppable;

	Alloy.NestedList = Alloy.SortableItem.extend(
		{
			initialize: function(el, group, options) {
				var instance = this;

				var defaults = {
					dropOn: 'ul',
					centerFrame: true
				};

				instance.options = Alloy.extend({}, defaults, options);

				instance._super.apply(instance, [el, group, instance.options]);

				var handleOption = instance.options.handle;

				if (handleOption) {
					var handleEl = jQuery(el).find(handleOption)[0];

					if (handleEl) {
						var handleId = Alloy.Dom.generateId(handleEl);

						instance.setHandleElId(handleId);
					}
				}
			},

			onDragDrop: Alloy.emptyFunction,

			onDragOver: function(event, id) {
				var instance = this;

		        var srcEl = this.getEl();
		        var destEl = Dom.get(id);

				var dropConditionOption = this.options.dropCondition;

				if (instance._sortOn(destEl)) {
					var container = jQuery(destEl).find('> ' + instance.options.dropOn).get(0);

					if (container) {
						if (jQuery.isFunction(dropConditionOption) && (dropConditionOption.apply(destEl, [event, id]) == false)) {
							instance._super(event, id);

							return false;
						}

						if (!Alloy.Dom.contains(container, srcEl) &&
							!Alloy.Dom.contains(srcEl, container)) {

							container.appendChild(srcEl);

							if (instance.options.placeholder) {
								instance._updatePlaceholder(true);
							}
						}
					}
					else {
						instance._super(event, id);
					}
				}
		    },

			onDragOut: function(event, id) {
				var instance = this;

				var destEl = Dom.get(id);

				if (instance._sortOn(destEl)) {
			        var srcEl = this.getEl();
			        var destEl = Dom.get(id);

		            instance._insert(srcEl, destEl);

					if (instance.options.placeholder) {
						instance._updatePlaceholder(true);
					}
				}
			},

			_sortOn: function(destEl) {
				var instance = this;

				var srcEl = this.getEl();
				var sortOnOption = this.options.sortOn;

				if (sortOnOption) {
					var sortContainer = jQuery(sortOnOption);

					if (sortContainer.length && (Alloy.Dom.contains(sortContainer[0], destEl) == false)) {
						return false;
					}
				}

				return true;
			}
		}
	);
})();