YAHOO.util.DDProxy.dragElId = 'EXP_PROXY';

Expanse.DragDrop = YAHOO.util.DragDropMgr;

Expanse.Draggable = new Expanse.Class(YAHOO.util.DD);
Expanse.DragProxy = new Expanse.Class(YAHOO.util.DDProxy);
Expanse.Droppable = new Expanse.Class(YAHOO.util.DDTarget);

Expanse.Resize = new Expanse.Class(YAHOO.util.Resize);

Expanse.Resize.implement({
    CSS_RESIZE: 'exp-resizable',
    CSS_DRAG: 'exp-draggable',
    CSS_HOVER: 'exp-resizable-hover',
    CSS_PROXY: 'exp-resizable-proxy',
    CSS_WRAP: 'exp-resizable-wrap',
    CSS_KNOB: 'exp-resizable-knob',
    CSS_HIDDEN: 'exp-resizable-hidden',
    CSS_HANDLE: 'exp-resizable-handle',
    CSS_STATUS: 'exp-resizable-status',
    CSS_GHOST: 'exp-resizable-ghost',
    CSS_RESIZING: 'exp-resizable-resizing'
});

(function() {
	var Dom = Expanse.Dom;
	var Event = Expanse.Event;
	var DDM = Expanse.DragDrop;

	var _counter = 0;

	Expanse.Sortable = new Expanse.Class({
		initialize: function(options) {
			var instance = this;

			_counter += 1;

			instance.options = options;

			instance._containers = jQuery(options.container);
			instance._items = options.items;
			instance._placeHolder = options.placeHolder;
			instance._handle = jQuery(options.handle);
			instance._group = options.group || 'sortGroup' + _counter;

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
				dragElId: 'sortProxy' + _counter
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

				id = Expanse.generateId(handle[0]);

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

			return Expanse.SortableItem;
		},

		getSortableTarget: function() {
			var instance = this;

			return Expanse.SortableTarget;
		},

		_flushCache: function() {
			var draggable = this;

			draggable.resetConstraints();

			draggable.unsubscribe('b4StartDragEvent', arguments.callee);
		}
	});

	Expanse.SortableItem = Expanse.DragProxy.extend({
		initialize: function(el, group, options) {
			var instance = this;

			instance._super.apply(instance, arguments);

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

			instance.options = options;

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

				var parent = target.parentNode;

				if (instance.goingUp) {
					parent.insertBefore(portlet, target)
				} else {
					parent.insertBefore(portlet, target.nextSibling);
		        }
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

		_isDroppable: function(target) {
			return (target instanceof Expanse.Droppable);
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
		}
	});

	Expanse.SortableTarget = Expanse.Droppable;
})();