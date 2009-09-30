// For details about this file see: LPS-2155

// To be removed in Liferay 5.4

// LPS-2129

// NOTE: the "dragHelper" option from the previous Liferay.Popup has been
// completely removed because keeping it would add excessive overhead.
// This should have little to no effect for most users.

(function() {
	var Dom = Alloy.Dom;
	var Event = Alloy.Event;

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

		options.on = {};
		options.on.close = options.close;
		options.on.open = options.open;

		if (options.url) {
			options.body = {
				url: options.url,
				data: options.urlData,
				complete: options.urlComplete,
				error: options.urlError,
				success: options.urlSuccess
			};
		}

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

		var dialog = new Alloy.Popup(options);

		Dom.addClass(dialog.innerElement, options.className || 'generic-dialog');

		if (options.stack === false) {
			dialog.subscribe(
				'focus',
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

		// LPS-2166

		Dom.addClass(dialog.element, 'ui-dialog');
		Dom.addClass(dialog.innerElement, 'ui-dialog-container');
		Dom.addClass(dialog.header, 'ui-dialog-title');
		Dom.addClass(dialog.body, 'ui-dialog-content');
		Dom.addClass(dialog.close, 'ui-dialog-close');

		if (options.modal) {
			Dom.addClass(dialog.mask, 'ui-dialog-overlay');
		}

		return jQuery(dialog.body);
	};

	Liferay.Popup.close = Alloy.Popup.close;
	Liferay.Popup.update = Alloy.Popup.update;
})();

// LPS-2166

(function() {
	var Resize = Alloy.Resize.prototype;

	Resize.CSS_RESIZE += ' ui-resizable';
	Resize.CSS_DRAG += ' ui-draggable';
    Resize.CSS_HOVER += ' ui-resizable-hover';
    Resize.CSS_PROXY += ' ui-resizable-proxy';
    Resize.CSS_WRAP += ' ui-resizable-wrap';
    Resize.CSS_KNOB += ' ui-resizable-knob';
    Resize.CSS_HIDDEN += ' ui-resizable-hidden';
    Resize.CSS_STATUS += ' ui-resizable-status';
    Resize.CSS_GHOST += ' ui-resizable-ghost';
    Resize.CSS_RESIZING += ' ui-resizable-resizing';

    Resize.CSS_HANDLE = 'ui-resizable-handle ' + Resize.CSS_HANDLE;
})();

(function() {
	var tabShow = Liferay.Portal.Tabs.show;

	Liferay.Portal.Tabs = {
		show: function(namespace, names, id) {
			var instance = this;

			tabShow.apply(instance, arguments);

			var tab = jQuery('#' + namespace + id + 'TabsId');
			var panel = jQuery('#' + namespace + id + 'TabsSection');

			tab.siblings().removeClass('current');
			tab.addClass('current');
		}
	};
})();