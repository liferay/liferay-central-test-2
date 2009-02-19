// For details about this file see: LPS-2155

// To be removed in Liferay 5.4

// LPS-2135

window.Class = Expanse.Class;

Expanse.Class.createSuper = function(previous, current) {
	return function() {
		this.parent = previous;
		this._super = previous;

		return current.apply(this, arguments);
	};
};

// LPS-2129

// NOTE: the "dragHelper" option from the previous Liferay.Popup has been
// completely removed because keeping it would add excessive overhead.
// This should have little to no effect for most users.

(function() {
	var Dom = Expanse.Dom;
	var Event = Expanse.Event;

	var handlesMap = {
		e: 'r',
		n: 't',
		s: 'b',
		w: 'l',
		ne: 'tr',
		nw: 'tl',
		se: 'br',
		sw: 'bl'
	};

	Liferay.Popup = function(options) {
		var instance = this;

		options.body = options.message;
		options.header = options.title;
		options.fixedcenter = !options.noCenter;

		if (options.position) {
			options.xy = options.position;
		}

		if (options.handles) {
			var handles = options.handles;

			if (typeof handles == 'string') {
				handles = handles.split(',');
			}

			var handleLocation;
			var newHandles = [];

			for (var i = handles.length - 1; i >= 0; i--) {
				handleLocation = handles[i];

				newHandles[i] = handlesMap[handleLocation];
			}
		}

		var dialog = new Expanse.Popup(options);

		Dom.addClass(dialog.innerElement, options.className || 'generic-dialog');

		if (options.stack === false) {
			dialog.subscribe('focus',
				function(event) {
					this.cfg.resetProperty('zindex');
				}
			);
		}

		if (options.resizeHelper) {
			var proxyEl = dialog.resizable.getProxyEl();

			Dom.addClass(proxyEl, options.resizeHelper);
		}

		if (options.dragStart) {
			dialog.dd.subscribe('startDragEvent', options.dragStart);
		}

		if (options.dragStop) {
			dialog.dd.subscribe('endDragEvent', options.dragStop);
		}

		if (options.zIndex) {
			dialog.cfg.setProperty('zindex', options.zIndex);
		}

		Dom.addClass(dialog.element, 'ui-dialog');
		Dom.addClass(dialog.innerElement, 'ui-dialog-container');
		Dom.addClass(dialog.header, 'ui-dialog-title');
		Dom.addClass(dialog.body, 'ui-dialog-content');
		Dom.addClass(dialog.close, 'ui-dialog-close');

		return jQuery(dialog.body);
	};

	Liferay.Popup.close = Expanse.Popup.close;
	Liferay.Popup.update = Expanse.Popup.update;
})();