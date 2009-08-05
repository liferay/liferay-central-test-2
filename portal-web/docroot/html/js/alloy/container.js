(function() {
	var Dom = Alloy.Dom;
	var Event = Alloy.Event;
	var Widget = YAHOO.widget;

	Alloy.OverlayManager = new Alloy.Class(Widget.OverlayManager);

	Alloy.Module = new Alloy.Widget(Widget.Module);
	Alloy.Overlay = new Alloy.Widget(Widget.Overlay);
	Alloy.Dialog = new Alloy.Widget(Widget.Dialog);
	Alloy.SimpleDialog = new Alloy.Widget(Widget.SimpleDialog);
	Alloy.Tooltip = new Alloy.Widget(Widget.Tooltip);
	Alloy.Panel = new Alloy.Widget(Widget.Panel);

	var normalizeArguments = function(el, options) {
		if (!options) {
			options = {};

			if (el) {
				if (!YAHOO.lang.isString(el) && !el.tagName) {
					options = el;

					el = options.el || Dom.generateId();
				}
			}
		}

		return [el, options];
	};

	var baseContainerImpl = {
		initialize: function(el, options) {
			var instance = this;

			var args = normalizeArguments(el, options);

			el = args[0];
			options = args[1];

			if (instance._defaults) {
				YAHOO.lang.augmentObject(options, instance._defaults);
			}

			instance._super.call(instance, el, options);

			if (options.body) {
				if (options.body.url) {
					instance._ajaxConfig = options.body;

					options.body = '<div class="loading-animation" />';

					instance.renderEvent.subscribe(instance._loadBody);
				}

				instance.setBody(options.body);
			}
		},

		refresh: function() {
			var instance = this;

			if (instance._ajaxConfig) {
				instance.setBody('<div class="loading-animation" />');
				instance._loadBody();
			}
		},

		toggle: function() {
			var instance = this;

			var visible = instance.cfg.getProperty('visible');

			if (visible) {
				instance.hide();
			}
			else {
				instance.show();
			}
		},

		_loadBody: function() {
			var instance = this;

			var ajaxConfig = instance._ajaxConfig;

			ajaxConfig.url = ajaxConfig.url.replace(/p_p_state=(maximized|pop_up)/g, 'p_p_state=exclusive');

			if (!ajaxConfig.success) {
				ajaxConfig.success = function(message) {
					instance.setBody('');

					jQuery(instance.body).html(message);

					if (!instance.cfg.getProperty('width')) {
						instance.cfg.setProperty('width', 'auto');
					}

					instance.changeContentEvent.fire();
				};
			}

			jQuery.ajax(ajaxConfig);
		}
	};

	Alloy.Module = Alloy.Module.extend(baseContainerImpl);
	Alloy.Overlay = Alloy.Overlay.extend(baseContainerImpl);
	Alloy.Dialog = Alloy.Dialog.extend(baseContainerImpl);
	Alloy.SimpleDialog = Alloy.SimpleDialog.extend(baseContainerImpl);
	Alloy.Tooltip = Alloy.Tooltip.extend(baseContainerImpl);
	Alloy.Panel = Alloy.Panel.extend(baseContainerImpl);

	Alloy.Overlay = Alloy.Overlay.extend(
		{
			_defaults: {
				zIndex: Alloy.zIndex.CONTAINER
			}
		}
	);

	Alloy.Tooltip = Alloy.Tooltip.extend(
		{
			_defaults: {
				zIndex: Alloy.zIndex.TOOLTIP
			}
		}
	);

	Alloy.Panel = Alloy.Panel.extend(
		{
			buildMask: function() {
				var instance = this;

				instance._super.apply(instance, arguments);

				Dom.addClass(instance.mask, 'aui-mask');
			}
		}
	);

	Alloy.extend(
		Widget.Module,
		{
			CSS_BODY: 'aui-body',
			CSS_FOOTER: 'aui-footer',
			CSS_HEADER: 'aui-header',
			CSS_MODULE: 'aui-module'
		}
	);

	Alloy.extend(
		Widget.Overlay,
		{
			CSS_OVERLAY: 'aui-overlay'
		}
	);

	Alloy.extend(
		Widget.Tooltip,
		{
			CSS_TOOLTIP: 'aui-tooltip'
		}
	);

	Alloy.extend(
		Widget.Panel,
		{
			CSS_PANEL: 'aui-panel',
			CSS_PANEL_CONTAINER: 'aui-panel-container'
		}
	);

	Alloy.extend(
		Widget.Dialog,
		{
			CSS_DIALOG: 'aui-dialog'
		}
	);

	Alloy.extend(
		Widget.SimpleDialog,
		{
			CSS_SIMPLEDIALOG: 'aui-simple-dialog',
			ICON_CSS_CLASSNAME: 'aui-icon'
		}
	);

	var IE_SYNC = (Liferay.Browser.isIe() && Liferay.Browser.getMajorVersion() < 7);

	/**
	* Popup is a subclass of Panel that behaves similarly, except that it has a richer
	* set of options. It automatically renders itself into the page (though this is configurable),
	* automatically registers itself with Alloy.Popup.Manager, and allows itself to be automatically
	* destroyed when it is closed.
	*
	* Example:
	* <br /><a href="panel_example.html">Using the Alloy.Popup class</a>.
	*
	* @namespace Alloy
	* @class Popup
	* @extends Alloy.Panel
	* @constructor
	* @param {Object} options The options that configure the instance of the Popup
	*/

	Alloy.Popup = Alloy.Panel.extend(
		{
			initialize: function(options) {
				var instance = this;

				options.on = options.on || {};

				instance._destroyOnClose = options.destroyOnClose;

				if (options.on.close) {
					var closeEvent = 'destroy';

					if (options.destroyOnClose === false) {
						closeEvent = 'hide';
					}

					options.on[closeEvent] = options.on.close;
				}

				if (options.width && (options.width != 'auto')) {
					options.width = (parseInt(options.width, 10) || 300) + 'px';
				}

				if (options.height && (options.height != 'auto')) {
					options.height = (parseInt(options.height, 10) || 300) + 'px';
				}

				options.zIndex = options.zIndex || Alloy.zIndex.CONTAINER;

				if (options.xy) {
					var position = options.xy;

					var x = position[0];
					var y = position[1];

					var centerValue = 'center';

					var win = Alloy.getWindow();

					if (x == centerValue || y == centerValue) {
						var width = parseInt(options.width, 10) || 0;
						var height = parseInt(options.width, 10) || 0;

						if (x == centerValue) {
							x = (win.width() / 2 - width / 2);
						}
						else if (y == centerValue) {
							y = (win.height() / 2 - height / 2);
						}
					}

					if (options.absoluteXY !== true) {
						var scrollLeft = win.scrollLeft();
						var scrollTop = win.scrollTop();

						x += scrollLeft;
						y += scrollTop;
					}

					options.xy = [x, y];
				}

				var el = options.el || Alloy.generateId();

				instance._super(el, options);

				if (options.header) {
					instance.setHeader(options.header);
				}

				if (options.className) {
					Dom.addClass(instance.element, options.className);
				}

				if (options.footer) {
					instance.setFooter(options.footer);
				}

				instance.options._el = el;

				if (!options.deferRender) {
					instance.render(options.renderTo || document.body);

					if (options.visible !== false) {
						instance.show();
					}

					instance._onRender();
				}
				else {
					instance.renderEvent.subscribe(instance._onRender, instance, true);
				}

				Alloy.Popup.Manager.register(instance);

				return instance;
			},

			/**
			* Closes the current popup. If it has been configured to be destroyed when
			* it's closed, then the destroy method will be called, otherwise it
			* will just be hidden.
			*
			* @method closePopup
			*/

			closePopup: function() {
				var instance = this;

				if (instance._destroyOnClose !== false) {
					instance.destroy();
				}
				else {
					instance.hide();
				}
			},

			_onRender: function() {
				var instance = this;

				var options = instance.options;
				var el = options._el;

				options.on = options.on || {};

				if (instance._destroyOnClose !== false) {
					Event.un(instance.close, 'click', instance._doClose);
					Event.on(instance.close, 'click', instance.destroy, instance, true);
				}

				if (options.messageId) {
					instance.body.id = options.messageId;
				}

				if (options.resizable !== false && Alloy.Resize) {
					var resize = new Alloy.Resize(
						el,
						{
							handles: options.handles || ['r', 'b', 'br'],
							height: options.height || 'auto',
							proxy: true,
							width: options.width
						}
					);

					var paddingTop = Dom.getStyle(instance.body, 'padding-top');
					var paddingBottom = Dom.getStyle(instance.body, 'padding-bottom');

					paddingTop = parseInt(paddingTop, 10) || 10;
					paddingBottom = parseInt(paddingBottom, 10) || 10;

					instance._panelPaddingVertical = paddingTop + paddingBottom;

					resize.on('resize', instance._resizeBody, instance, true);

					if (options.on.resize) {
						resize.on('resize', options.on.resize, instance, true);
					}

					instance.resizable = resize;
				}

				if (options.draggable !== false) {
					var el = Dom.get(instance.id);

					Dom.setStyle(el, 'position', 'relative');
				}

				if (options.center || options.fixedcenter) {
					instance.center();
				}
			},

			_resizeBody: function(options) {
				var instance = this;

				var panelHeight = options.height;

				var headerHeight = instance.header.offsetHeight;
				var footerHeight = 0;

				if (instance.footer) {
					footerHeight = instance.footer.offsetHeight;
				}

				var bodyHeight = panelHeight - headerHeight - footerHeight;

				Dom.setStyle(instance.body, 'height', (bodyHeight - instance._panelPaddingVertical) + 'px');

				if (IE_SYNC) {
					instance.sizeUnderlay();
					instance.syncIframe();
				}
			}
		}
	);

	/**
	* The OverlayManager that keeps track of the Popups added to the page
	*
	* @property Alloy.Popup.Manager
	* @static
	* @final
	* @type Object
	*/

	Alloy.Popup.Manager = new Alloy.OverlayManager();

	Alloy.extend(
		Alloy.Popup,
		{

			/**
			* Closes any instance of a popup
			*
			* @method Alloy.Popup.close
			* @static
			* @param {HTMLElement} el The representing the Popup
			*/

			close: function(el) {
				var instance = this;

				var obj = el;

				if (!el.jquery) {
					obj = jQuery(el);
				}

				if (!obj.is('.aui-panel')) {
					obj = obj.parents('.aui-panel:first');
				}

				if (obj.length) {
					var id = obj[0].id;
					var popup = Alloy.Popup.Manager.find(id);

					if (popup) {
						popup.destroy();
					}
				}
			},

			/**
			* Updates the body of any Popup with the
			* result of an ajax request.
			*
			* @method Alloy.Popup.update
			* @static
			* @param {String} id The id of the popup
			* @param {String} url The url to load via ajax
			*/

			update: function(id, url) {
				var instance = this;

				var obj = jQuery(id);

				obj.html('<div class="loading-animation"></div>');
				obj.load(url);
			}
		}
	);

	Alloy.ContextPanel = Alloy.Panel.extend(
		{
			initialize: function(options) {

				var arrowPosition = 'tl';

				if (options && options.arrowPosition) {
					arrowPosition = options.arrowPosition;
					delete options.arrowPosition;
				}

				var arrowEl = jQuery('<div class="aui-context-panel-pointer aui-context-panel-container"><div class="aui-context-panel-pointer-inner"></div></div>');

				this._super.apply(this, arguments);

				var innerClass = 'aui-context-panel aui-context-panel-arrow-' + arrowPosition + ' aui-context-panel-container';

				Dom.addClass(this.innerElement, innerClass);

				jQuery(this.innerElement).append(arrowEl);
			}
		}
	);
})();