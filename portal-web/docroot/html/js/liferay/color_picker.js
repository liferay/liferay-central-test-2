(function() {
	var Dom = Alloy.Dom;
	var Event = Alloy.Event;

	Liferay.ColorPicker = Alloy.ColorPickerPanel.extend(
		{

			/**
			 * OPTIONS
			 *
			 * Required
			 * item {string|object}: A jQuery selector or DOM element that specifies which field to insert the selected value into.
			 *
			 * Optional
			 * context {object}: A DOM element which specifies the context in which to search for the item.
			 * hasImage {boolean}: Whether an image is provided in the DOM or options object (via the item option).
			 *
			 * Callbacks
			 * onChange {function}: Called whenever the color changes.
			 * onClose {function}: Called when the color picker is closed.
			 */

			initialize: function(options) {
				var instance = this;

				instance._onClose = options.onClose;
				instance._onChange = options.onChange;
				instance._context = options.context || document.body;
				instance._hasImage =  options.hasImage || false;
				instance._item = jQuery(options.item || '.use-colorpicker', instance._context);

				instance._insertImages();

				options.button = instance._button;

				delete options.context;

				options.zIndex = Liferay.zIndex.ALERT + 10000;

				instance._super(options);

				instance.picker.on('rgbChange', instance.onColorChange, instance, true);

				if (instance._onClose) {
					instance.hideEvent.subscribe(instance._onClose, instance, true);
				}
			},

			blur: function(event) {
				var instance = this;

				if (Event.getTarget(event) != instance._item[0]) {
					instance._super.apply(instance, arguments);
				}
			},

			onColorChange: function(event, args) {
				var instance = this;

				var hexValue = instance.picker.get('hex');

				instance._currentInput.val('#' + hexValue);

				if (instance._onChange) {
					instance._onChange(args);
				}
			},

			_insertImages: function() {
				var instance = this;

				var context = instance._context;

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
			}
		}
	);
})();