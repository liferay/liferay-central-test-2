Liferay.DynamicSelect = new Class({

	/**
	 * OPTIONS
	 *
	 * Required
	 * array {array}: An array of options.
	 * array[i].select {string}: An id of a select box.
	 * array[i].selectId {string}: A JSON object field name for an option value.
	 * array[i].selectDesc {string}: A JSON object field name for an option description.
	 * array[i].selectVal {string}: The value that is displayed in an option field.
	 *
	 * Callbacks
	 * array[i].selectData {function}: Returns a JSON array to populate the next select box.
	 */

	initialize: function(array) {
		var instance = this;

		instance.array = array;

		jQuery.each(
			array,
			function(i, options) {
				var select = jQuery('#' + options.select);
				var selectData = options.selectData;

				var prevSelectVal = null;

				if (i > 0) {
					prevSelectVal = array[i - 1].selectVal;
				}

				selectData(
					function(list) {
						instance._updateSelect(instance, i, list);
					},
					prevSelectVal
				);

				select.attr('name', select.attr('id'));

				select.bind(
					'change',
					function() {
						instance._callSelectData(instance, i);
					}
				);
			}
		);
	},

	_callSelectData: function(instance, i) {
		var array = instance.array;

		if ((i + 1) < array.length) {
			var curSelect = jQuery('#' + array[i].select);
			var nextSelectData = array[i + 1].selectData;

			nextSelectData(
				function(list) {
					instance._updateSelect(instance, i + 1, list);
				},
				curSelect.val()
			);
		}
	},

	_updateSelect: function(instance, i, list) {
		var options = instance.array[i];

		var select = jQuery('#' + options.select);
		var selectId = options.selectId;
		var selectDesc = options.selectDesc;
		var selectVal = options.selectVal;
		var selectNullable = options.selectNullable || true;

		var options = '';

		if (selectNullable) {
			options += '<option value="0"></option>';
		}

		jQuery.each(
			list,
			function(i, obj) {
				eval('var key = obj.' + selectId + ';');
				eval('var value = obj.' + selectDesc + ';');

				options += '<option value="' + key + '">' + value + '</option>';
			}
		);

		select.html(options);
		select.find('option[@value=' + selectVal + ']').attr('selected', 'selected');

		if (Liferay.Browser.is_ie) {
			select.css('width', 'auto');
		}
	}
});