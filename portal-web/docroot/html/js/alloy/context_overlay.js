(function() {
	var Dom = Alloy.Dom;
	var Event = Alloy.Event;

	/**
	* ContextOverlay is a subclass of Overlay that is automatically configured to display
	* at a specified corner of an element. It also defines an interaction that triggers
	* it's showing and hiding.
	* 
	* @class ContextOverlay
	* @module container
	* @extends Alloy.Overlay
	* @constructor
	* @param {Object} options The options that configure the instance of the ContextOverlay
	*/

	Alloy.ContextOverlay = Alloy.Overlay.extend(
		{
			initialize: function(options) {
				var instance = this;

				if (options.visible !== false) {
					options.visible = false;
				}

				if (options.preventcontextoverlap !== false) {
					options.preventcontextoverlap = true;
				}

				if (options.constraintoviewport !== false) {
					options.constraintoviewport = true;
				}

				if (options.trigger && !YAHOO.lang.isArray(options.context)) {
					var contextEl = Alloy.getEl(options.trigger);

					var overlayCorner = options.overlayCorner || 'tl';
					var contextCorner = options.contextCorner || 'bl';

					options.context = [contextEl, overlayCorner, contextCorner, ['beforeShow', 'windowResize']];
				}

				if (options.context) {
					instance._super(options);

					if (!options.lazyload) {
						instance._renderOverlay();
					}
					else {
						Event.on(options.context[0], 'click', instance._renderOverlay, instance, true);
					}

					instance.options = options;

					instance.hideTask = new Alloy.DelayedTask(this.hide, instance);

					instance._createInteraction();

					if (options.cssClass) {
						Dom.addClass(instance.element, options.cssClass);
					}
				}
			},

			blur: function(event) {
				var instance = this;

				instance.hide();

				Event.un(document, 'click', arguments.callee);
			},

			hide: function() {
				var instance = this;

				instance._super.apply(instance, arguments);

				Dom.removeClass(instance.contextEl, 'selected');
			},

			show: function() {
				var instance = this;

				instance._super.apply(instance, arguments);

				Dom.addClass(instance.contextEl, 'selected');

				Event.on(document, 'click', instance.blur, instance, true);
			},

			toggle: function(event) {
				var instance = this;

				var visible = instance.cfg.getProperty('visible');

				if (event) {
					Event.stopEvent(event);
				}

				if (visible) {
					instance.hide();
				}
				else {
					instance.show();
				}
			},

			_createInteraction: function() {
				var instance = this;

				var options = instance.options;

				var context = instance.cfg.getProperty('context');

				if (context && context[0]) {
					var contextEl = context[0];

					instance.contextEl = contextEl;

					if (!options.interaction || options.interaction == 'click') {
						instance._createClickInteraction();
					}
					else if (options.interaction == 'mouse') {
						instance._createMouseInteraction();
					}
					else if (options.interaction == 'both') {
						instance._createClickInteraction();
						instance._createMouseInteraction();
					}
				}

				Event.on(instance.element, 'click', instance._swallowEvent);
			},

			_createClickInteraction: function() {
				var instance = this;

				Event.on(instance.contextEl, 'click', instance.toggle, instance, true);
			},

			_createMouseInteraction: function() {
				var instance = this;

				var contextObj = jQuery(instance.contextEl);
				var elementObj = jQuery(instance.element);

				contextObj.bind(
					'mouseenter',
					function(event) {
						instance.hideTask.cancel();
						instance.show();
					}
				);

				contextObj.bind(
					'mouseleave',
					function(event) {
						instance.hideTask.delay(500);
					}
				);

				elementObj.bind(
					'mouseenter',
					function(event) {
						instance.hideTask.cancel();
					}
				);

				elementObj.bind(
					'mouseleave',
					function(event) {
						instance.hideTask.delay(1000);
					}
				);
			},

			_renderOverlay: function() {
				var instance = this;

				instance.render(instance.options.renderTo || document.body);
			},

			_swallowEvent: function(event) {
				var instance = this;

				Event.stopPropagation(event);
			}
		}
	);
})();