/**
 * OPTIONS
 *
 * Required
 * message {string|object}: The default HTML/object to display.
 * width {number}: The starting width of the message box.
 *
 * Optional
 * className {string}: A class to add to the specific popup.
 * dragHelper {string|function}: A jQuery selector or a function that returns a DOM element.
 * handles {string}: A comma-separated list (n,ne,e,se,s,sw,w,nw) of the handles for resizing.
 * height {number}: The starting height of the message box.
 * messageId {string}: A unique ID to give to a popup's content.
 * modal {boolean}: Whether to show shaded background.
 * noCenter {boolean}: Whether to prevent re-centering.
 * stack {boolean}: Whether to automatically stack the popup on top of other ones.
 * resizeHelper {string}: A class that will be attached to resize proxy helper.
 *
 * Callbacks
 * dragStart {function}: Called when dragging of the dialog starts.
 * dragStop {function}: Called when dragging of the dialog starts.
 * onClose {function}: Called when a dialog is closed.
 */

Liferay.Popup = function(options) {
	var instance = this;

	var cacheDialogHelper = function(obj) {
		if (!obj.jquery) {
			obj = jQuery(obj);
		}

		var cache = obj.data('ui-helper-drag');

		if (!cache) {
			var cachedObj = obj.clone();

			cachedObj.find('.ui-dialog-content').empty();
			cachedObj.addClass('ui-proxy');

			cache = obj.data('ui-helper-drag', cachedObj);
		}

		return cache;
	};

	var checkExternalClick = function(element) {
		if (jQuery.datepicker) {
			jQuery.datepicker._checkExternalClick(
				{
					target: element
				}
			);
		}
	};

	options = options || {};

	if (options.dragHelper === null) {
		options.dragHelper = "original";
	}

	var defaults = {
		className: 'generic-dialog',
		draggable: true,
		handles: 'e,se,s,sw,w',
		resizeHelper: 'ui-resizable-proxy',
		message: '<div class="loading-animation"></div>',
		position: [5,5],
		height: 'auto',
		stack: false,
		dragHelper: function() {
			var dialog = jQuery(this);
			var cache = cacheDialogHelper(dialog);

			var height = dialog.height();
			var width = dialog.width();

			cache.css(
				{
					height: height,
					width: width
				}
			);

			return cache;
		},
		dragStart: function(e, ui) {
			if (!options.dragHelper) {
				var dialog = jQuery(this).parents('.ui-dialog:first');
				var target = jQuery(e.target);

				checkExternalClick(target);
				dialog.css('visibility', 'hidden');
			}
		},
		dragStop: function(e, ui) {
			if (!options.dragHelper) {
				var dialog = jQuery(this).parents('.ui-dialog:first');
				var helper = ui.helper;

				var left = helper.css('left');
				var top = helper.css('top');

				dialog.css(
					{
						left: left,
						top: top,
						visibility: 'visible'
					}
				);
			}
		},

		close: function() {
			var target = jQuery(this);

			checkExternalClick(target);
		},

		open: function(e, ui) {
			if (!options.dragHelper) {
				var dialog = jQuery(this).parents('.ui-dialog:first'), target = jQuery(this);
				
				dialog.click(function() {
					checkExternalClick(target);
				});
				
				cacheDialogHelper(dialog);
			}
		}
	};

	var config = jQuery.extend({}, defaults, options);

	var content = '';
	var message = config.message;

	if (typeof message == 'string') {
		content = jQuery('<div>' + config.message + '</div>');
	}
	else {
		content = jQuery('<div></div>').append(config.message);
	}

	var modal = config.modal;
	var draggable = config.draggable;
	var position = config.noCenter ? defaults.position : 'center';

	position = config.position || position;

	var top = config.top;
	var left = config.left;

	var className = config.className;
	var height = config.height;
	var dragHelper = config.dragHelper;
	var dragStart = config.dragStart;
	var dragStop = config.dragStop;
	var open = config.open;
	var close = config.close;
	var messageId = config.messageId;
	var resizable = config.resizable;
	var resizeHelper = config.resizeHelper;
	var stack = config.stack;
	var title = config.title;
	var width = config.width;

	if (resizable !== false) {
		resizable = config.handles;
	}

	if (Liferay.Util.isArray(position)) {
		var centering = position.indexOf('center');

		if (centering > -1) {
			var wnd = jQuery(window);
			var popupWidth = width || 0;
			var popupHeight = (typeof height == 'string') ? 0 : height;

			position[centering] = (centering == 0 ? (wnd.width() / 2) - (popupWidth / 2) : (wnd.height() / 2) - (popupHeight / 2));
		}
	}

	if (title) {
		className += ' has-title';
	}

	if (messageId) {
		content.attr('id', messageId);
	}

	content.appendTo('body');

	content.bind(
		'dialogclose',
		function(event) {
			if (config.onClose) {
				config.onClose();
			}

			jQuery(this).remove();
		}
	);

	return content.dialog(
		{
			autoResize: false,
			dialogClass: className,
			draggable: draggable,
			height: height,
			title: title,
			position: position,
			modal: modal,
			resizable: resizable,
			resizeHelper: resizeHelper,
			stack: stack,
			width: width,
			zIndex: Liferay.zIndex.ALERT, // compensate for UI's dialog
			dragHelper: dragHelper,
			dragStart: dragStart,
			dragStop: dragStop,
			open: open,
			close: close
		}
	);
};

jQuery.extend(
	Liferay.Popup,
	{
		close: function(el) {
			var instance = this;

			var obj = el;

			if (!el.jQuery) {
				obj = jQuery(el);
			}

			if (!obj.is('.ui-dialog-content')) {
				obj = obj.parents('.ui-dialog-content');
			}

			obj.trigger('dialogclose');
		},

		update: function(id, url) {
			var instance = this;

			var obj = jQuery(id);

			obj.html('<div class="loading-animation"></div>');
			obj.load(url);
		}
	}
);