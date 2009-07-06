(function() {
	var Dom = Alloy.Dom;
	var Event = Alloy.Event;

	Alloy.ColorPicker = new Alloy.Class(YAHOO.widget.ColorPicker);

	Alloy.ColorPicker.implement(
		{
			ID: {
				R: 'aui-picker-r',
				R_HEX: 'aui-picker-rhex',
				G: 'aui-picker-g',
				G_HEX: 'aui-picker-ghex',
				B: 'aui-picker-b',
				B_HEX: 'aui-picker-bhex',
				H: 'aui-picker-h',
				S: 'aui-picker-s',
				V: 'aui-picker-v',
				PICKER_BG: 'aui-picker-bg',
				PICKER_THUMB: 'aui-picker-thumb',
				HUE_BG: 'aui-picker-hue-bg',
				HUE_THUMB: 'aui-picker-hue-thumb',
				HEX: 'aui-picker-hex',
				SWATCH: 'aui-picker-swatch',
				WEBSAFE_SWATCH: 'aui-picker-websafe-swatch',
				CONTROLS: 'aui-picker-controls',
				RGB_CONTROLS: 'aui-picker-rgb-controls',
				HSV_CONTROLS: 'aui-picker-hsv-controls',
				HEX_CONTROLS: 'aui-picker-hex-controls',
				HEX_SUMMARY: 'aui-picker-hex-summary',
				CONTROLS_LABEL: 'aui-picker-controls-label'
			},

			IMAGE: {
				HUE_THUMB: themeDisplay.getPathThemeImages() + '/color_picker/color_indic.png',
				PICKER_THUMB: themeDisplay.getPathThemeImages() + '/color_picker/select.png'
			}
		}
	);

	Alloy.ColorPickerPanel = Alloy.Panel.extend(
		{
			initialize: function(options) {
				var instance = this;

				var defaults = {
					constraintoviewport: true,
					on: {},
					preventcontextoverlap: true,
					visible: false
				};

				options = Alloy.extend(defaults, options);

				instance.options = options;

				if (options.on.close) {
					options.on.hide = options.on.close;
				}

				instance._buttonContext = options.buttonContext || document.body;
				instance._hasImage =  options.hasImage || false;
				instance._item = jQuery(options.item || '.use-colorpicker', instance._buttonContext);

				instance.changeEvent = new Alloy.CustomEvent('change', instance);

				instance._insertImages();

				options.button = instance._button;

				options.zIndex = Alloy.zIndex.COLOR_PICKER;

				options.container = instance.panel;

				if (options.button && !options.context) {
					options.button = Alloy.getEl(options.button);

					options.context = [options.button, 'tl', 'bl', ['show']];
				}

				instance._super(options.el || Dom.generateId(), options);

				var pickerId = Dom.generateId();

				instance.setBody('<div id="' + pickerId + '"></div>');

				instance._pickerId = pickerId;
				instance.renderEvent.subscribe(instance.createPicker);

				Dom.addClass(instance.element, 'aui-picker-panel');

				instance.render(document.body);

				var inputs = instance.element.getElementsByTagName('input');

				if (inputs.length && !inputs[0].getAttribute('type')) {
					var input;

					for (var i = inputs.length - 1; i >= 0; i--) {
						input = inputs[i];

						input.setAttribute('type', input.type || 'text');
					}
				}

				Event.on(options.button, 'click', instance.toggle, instance, true);
				Event.on(instance.element, 'click', instance._swallowEvent, instance, true);

				instance.picker.on('rgbChange', instance.onColorChange, instance, true);
			},

			blur: function(event) {
				var instance = this;

				if (Event.getTarget(event) != instance._item[0]) {
					instance.hide();

					Event.un(document, 'click', arguments.callee);
				}
			},

			createPicker: function() {
				var instance = this;

				var options = instance.options;

				instance.picker = new Alloy.ColorPicker(instance._pickerId,
					{
						container: instance,
						showhexsummary: false,
						showhsvcontrols: false,
						showwebsafe: false
					}
				);
			},

			onColorChange: function(event, args) {
				var instance = this;

				var hexValue = instance.picker.get('hex');

				instance._currentInput.val('#' + hexValue);

				instance.changeEvent.fire();
			},

			show: function() {
				var instance = this;

				instance._super.apply(instance, arguments);

				Event.on(document, 'click', instance.blur, instance, true);
			},

			toggle: function(event) {
				var instance = this;

				var visible = instance.cfg.getProperty('visible');

				if (visible) {
					instance.hide();
				}
				else {
					instance.show();
				}

				if (event) {
					Event.stopEvent(event);
				}
			},

			_insertImages: function() {
				var instance = this;

				var buttonContext = instance._buttonContext;

				var items = instance._item;

				var colorPickerImgHTML = '<img alt="' + Liferay.Language.get('color-picker') + '" class="lfr-colorpicker-img" src="' + themeDisplay.getPathThemeImages() + '/color_picker/color_picker.png" title="' + Liferay.Language.get('color-picker') + '" />';

				var colorPickerImg;

				if (instance._hasImage) {
					colorPickerImg = items;
				}
				else {
					colorPickerImg = jQuery(colorPickerImgHTML);

					items.after(colorPickerImg);
				}

				instance._button = colorPickerImg;
				instance._currentInput = instance._button.prev();
			},

			_swallowEvent: function(event) {
				var instance = this;

				Event.stopEvent(event);
			}
		}
	);
})();