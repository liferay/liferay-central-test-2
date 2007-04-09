Liferay.TagsSelector = new Class({

	/*
	params.instanceVar: the instance variable for this class
	params.hiddenInput: the hidden input used to pass in the current tags
	params.textInput: the text input for users to add tags
	params.summarySpan: the summary span tos how the current tags
	params.curTags: comma delimited string of current tags
	params.focus: true if the text input should be focused
	*/
	initialize: function(params) {
		var instance = this;

		instance._curTags = [];

		instance.params = params;

		var hiddenInput = jQuery('#' + params.hiddenInput);

		hiddenInput.attr('name', hiddenInput.attr('id'));

		var textInput = jQuery('#' + params.textInput);

		textInput.Autocomplete(
			{
				source: mainPath + '/portal/autocomplete_tags_entries',
				delay: 0,
				fx: {
					type: 'slide',
					duration: 400
				},
				autofill: true,
				dataSourceType: 'json',
				helperClass: 'autocomplete-box',
				selectClass: 'autocomplete-selected',
				minchars: 1,
				onSelect: function(option) {
					textInput.val('');

					var curTags = instance._curTags;
					var selTag = option.text;

					if (curTags.indexOf(selTag) == -1) {
						curTags.push(selTag);
					}

					curTags = curTags.sort();

					instance._update(instance);
				},
				onShow: function() {},
				onHide: function() {}
			}
		);

		if (params.focus) {
			textInput.focus();
		}

		if (params.curTags != '') {
			instance._curTags = params.curTags.split(',');

			instance._update(instance);
		}
	},

	deleteTag: function(instance, id) {
		var params = instance.params;
		var curTags = instance._curTags;

		jQuery('#' + params.instanceVar + 'CurTags' + id).remove();

		curTags.splice(id, 1);

		instance._update(instance);
	},

	_update: function(instance) {
		instance._updateHiddenInput(instance);
		instance._updateSummarySpan(instance);
	},

	_updateHiddenInput: function(instance) {
		var params = instance.params;
		var curTags = instance._curTags;

		var hiddenInput = jQuery('#' + params.hiddenInput);

		hiddenInput.val(curTags.join(','));
	},

	_updateSummarySpan: function(instance) {
		var params = instance.params;
		var curTags = instance._curTags;

		var html = '';

		jQuery(curTags).each(
			function(i, curTag) {
				html += '<span id="' + params.instanceVar + 'CurTags' + i + '">';
				html += curTag + ' ';
				html += '[<a href="javascript: ' + params.instanceVar + '.deleteTag(' + params.instanceVar + ', ' + i + ');">x</a>]';

				if ((i + 1) < curTags.length) {
					html += ', ';
				}

				html += '</span>';
			}
		);

		var tagsSummary = jQuery('#' + params.summarySpan);

		tagsSummary.html(html);
	}
});