(function() {
	var Dom = Expanse.Dom;
	var Event = Expanse.Event;
	var Widget = YAHOO.widget;

	Expanse.OverlayManager = new Expanse.Class(Widget.OverlayManager);

	Expanse.Module = new Expanse.Widget(Widget.Module);
	Expanse.Overlay = new Expanse.Widget(Widget.Overlay);
	Expanse.Dialog = new Expanse.Widget(Widget.Dialog);
	Expanse.SimpleDialog = new Expanse.Widget(Widget.SimpleDialog);
	Expanse.Tooltip = new Expanse.Widget(Widget.Tooltip);
	Expanse.Panel = new Expanse.Widget(Widget.Panel);

	var baseContainerImpl = {
		initialize: function(el, options) {
			var instance = this;

			instance._super.apply(instance, arguments);

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

	Expanse.Module = Expanse.Module.extend(baseContainerImpl);
	Expanse.Overlay = Expanse.Overlay.extend(baseContainerImpl);
	Expanse.Dialog = Expanse.Dialog.extend(baseContainerImpl);
	Expanse.SimpleDialog = Expanse.SimpleDialog.extend(baseContainerImpl);
	Expanse.Panel = Expanse.Panel.extend(baseContainerImpl);

	Expanse.Overlay = Expanse.Overlay.extend(
		{
			initialize: function(el, options) {
				var instance = this;

				options.zIndex = options.zIndex || Expanse.zIndex.CONTAINER;

				instance._super(el, options);
			}
		}
	);

	Expanse.Tooltip = Expanse.Tooltip.extend(
		{
			initialize: function(el, options) {
				var instance = this;

				options.zIndex = options.zIndex || Expanse.zIndex.TOOLTIP;

				instance._super(el, options);
			}
		}
	);

	Expanse.Panel = Expanse.Panel.extend(
		{
			buildMask: function() {
				var instance = this;

				instance._super.apply(instance, arguments);

				Dom.addClass(instance.mask, 'exp-mask');
			}
		}
	);

	Expanse.extend(
		Widget.Module,
		{
			CSS_BODY: 'exp-body',
			CSS_FOOTER: 'exp-footer',
			CSS_HEADER: 'exp-header',
			CSS_MODULE: 'exp-module'
		}
	);

	Expanse.extend(
		Widget.Overlay,
		{
			CSS_OVERLAY: 'exp-overlay'
		}
	);

	Expanse.extend(
		Widget.Tooltip,
		{
			CSS_TOOLTIP: 'exp-tooltip'
		}
	);

	Expanse.extend(
		Widget.Panel,
		{
			CSS_PANEL: 'exp-panel',
			CSS_PANEL_CONTAINER: 'exp-panel-container'
		}
	);

	Expanse.extend(
		Widget.Dialog,
		{
			CSS_DIALOG: 'exp-dialog'
		}
	);

	Expanse.extend(
		Widget.SimpleDialog,
		{
			CSS_SIMPLEDIALOG: 'exp-simple-dialog',
			ICON_CSS_CLASSNAME: 'exp-icon'
		}
	);

	var IE_SYNC = (Liferay.Browser.isIe() && Liferay.Browser.getMajorVersion() < 7);

	/**
	* Popup is a subclass of Panel that behaves similarly, except that it has a richer
	* set of options. It automatically renders itself into the page (though this is configurable),
	* automatically registers itself with Expanse.Popup.Manager, and allows itself to be automatically
	* destroyed when it is closed.
	* 
	* Example:
	* <br /><a href="panel_example.html">Using the Expanse.Popup class</a>.
	* 
	* @namespace Expanse
	* @class Popup
	* @extends Expanse.Panel
	* @constructor
	* @param {Object} options The options that configure the instance of the Popup
	*/

	Expanse.Popup = Expanse.Panel.extend(
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

				options.zIndex = options.zIndex || Expanse.zIndex.CONTAINER;

				if (options.xy) {
					var position = options.xy;

					var x = position[0];
					var y = position[1];

					var centerValue = 'center';

					var win = Expanse.getWindow();

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

				var el = options.el || Expanse.generateId();

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

					instance.show();

					instance._onRender();
				}
				else {
					instance.renderEvent.subscribe(instance._onRender, instance, true);
				}

				Expanse.Popup.Manager.register(instance);

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

				if (options.resizable !== false && Expanse.Resize) {
					var resize = new Expanse.Resize(
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
	* @property Expanse.Popup.Manager
	* @static
	* @final
	* @type Object
	*/

	Expanse.Popup.Manager = new Expanse.OverlayManager();

	Expanse.extend(
		Expanse.Popup,
		{

			/**
			* Closes any instance of a popup
			* 
			* @method Expanse.Popup.close
			* @static
			* @param {HTMLElement} el The representing the Popup
			*/

			close: function(el) {
				var instance = this;

				var obj = el;

				if (!el.jquery) {
					obj = jQuery(el);
				}

				if (!obj.is('.exp-panel')) {
					obj = obj.parents('.exp-panel:first');
				}

				if (obj.length) {
					var id = obj[0].id;
					var popup = Expanse.Popup.Manager.find(id);

					if (popup) {
						popup.destroy();
					}
				}
			},

			/**
			* Updates the body of any Popup with the
			* result of an ajax request.
			* 
			* @method Expanse.Popup.update
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
})();