Liferay.TagsSelector = new Class({

	/*
	params.instanceVar: the instance variable for this class
	params.hiddenInput: the hidden input used to pass in the current tags
	params.textInput: the text input for users to add tags
	params.summarySpan: the summary span tos how the current tags
	params.curTags: comma delimited string of current tags
	params.focus: true if the text input should be focused
	params.contentCallback: the callback method to get content used to get suggestible tags
	*/
	initialize: function(params) {
		var instance = this;

		instance._curTags = [];

		instance.params = params;
		instance._ns = instance.params.instanceVar || '';
		instance._mainContainer = jQuery('<div class="lfr-tag-select-container"></div>');
		instance._container = jQuery('<div class="lfr-tag-container"></div>');

		var hiddenInput = jQuery('#' + params.hiddenInput);

		hiddenInput.attr('name', hiddenInput.attr('id'));

		var textInput = jQuery('#' + params.textInput);

		textInput.autocomplete(
			{
				source: instance._getTags,
				width: textInput.width() + 20,
				formatItem: function(row, i, max, term) {
					return row;
				},
				dataType: 'json',
				delay: 0,
				multiple: true,
				mutipleSeparator: ',',
				minChars: 1,
				hide: function(event, ui) {
					jQuery(this).removeClass('showing-list');
				},
				show: function(event, ui) {
					jQuery(this).addClass('showing-list');
					this._LFR_listShowing = true;
				},
				result: function(event, ui) {
					var caretPos = this.value.length;

					if (this.createTextRange) {
						var textRange = this.createTextRange();

						textRange.moveStart('character', caretPos);
						textRange.select();
					}
					else if (this.selectionStart) {
						this.selectionStart = caretPos;
						this.selectionEnd = caretPos;
					}
				}
			}
		);

		instance._popupVisible = false;

		instance._setupSelectTags();
		instance._setupSuggestions();

		var addTagButton = jQuery('#' + params.instanceVar + 'addTag');

		addTagButton.click(
			function() {
					var curTags = instance._curTags;
					var newTags = textInput.val().split(',');

					jQuery.each(
						newTags,
						function (i, n) {
							n = jQuery.trim(n);

							if (curTags.indexOf(n) == -1) {
								if (n != '') {
									curTags.push(n);

									if (instance._popupVisible) {
										jQuery('input[@type=checkbox][@value$=' + n + ']', instance.selectTagPopup).attr('checked', true);
									}
								}
							}
						}
					);

					curTags = curTags.sort();
					textInput.val('');

					instance._update();
				}
		);

		textInput.keypress(
			function(event) {
				if (event.keyCode == 13) {
					if (!this._LFR_listShowing) {
						addTagButton.trigger('click');
					}

					this._LFR_listShowing = null;
					return false;
				}
			}
		);

		if (params.focus) {
			textInput.focus();
		}

		if (params.curTags != '') {
			instance._curTags = params.curTags.split(',');

			instance._update();
		}

		Liferay.Util.actsAsAspect(window);

		window.before(
			'submitForm',
			function() {
				var val = jQuery.trim(textInput.val());

				if (val.length) {
					addTagButton.trigger('click');
				}
			}
		);
	},

	deleteTag: function(id) {
		var instance = this;

		var params = instance.params;
		var curTags = instance._curTags;

		jQuery('#' + instance._ns + 'CurTags' + id).remove();

		var value = curTags.splice(id, 1);

		if (instance._popupVisible) {
			jQuery('input[@type=checkbox][@value$=' + value + ']', instance.selectTagPopup).attr('checked', false);
		}

		instance._update();
	},

	_createPopup: function() {
		var instance = this;

		var ns = instance._ns;
		var container = instance._container;
		var mainContainer = instance._mainContainer;

		var saveBtn = jQuery('<input class="submit lfr-save-button" id="' + ns + 'saveButton" type="submit" value="' + Liferay.Language.get('save') + '" />');

		saveBtn.click(
			function() {
				instance._curTags = instance._curTags.length ? instance._curTags : [];

				container.find('input[@type=checkbox]').each(
					function(){
						var currentIndex = instance._curTags.indexOf(this.value);
						if (this.checked) {
							if (currentIndex == -1) {
								instance._curTags.push(this.value);
							}
						}
						else {
							if (currentIndex > -1) {
								instance._curTags.splice(currentIndex, 1);
							}
						}
					}
				);

				instance._update();
				Liferay.Popup.close(instance.selectTagPopup);
			}
		);

		mainContainer.append(container).append(saveBtn);

		if (!instance.selectTagPopup) {
			var popup = Liferay.Popup(
				{
					modal: false,
					position: 'center',
					width: 400,
					message: mainContainer[0],
					onClose: function() {
						instance._popupVisible = false;
						instance.selectTagPopup = null;
					}
				}
			);
			instance.selectTagPopup = popup;
		}
		instance._popupVisible = true;

		if (Liferay.Browser.is_ie) {
			jQuery('.lfr-label-text', popup).click(
				function() {
					var input = jQuery(this.previousSibling);
					var checkedState = !input.is(':checked');
					input.attr('checked', checkedState);
				}
			);
		}
	},

	_getTags: function(term) {
		var beginning = 0;
		var end = 20;

		var data = Liferay.Service.Tags.TagsEntry.searchAutocomplete(
			{
				companyId: themeDisplay.getCompanyId(),
				name: "%" + term + "%",
				properties: "",
				begin: beginning,
				end: end
			}
		);

		return jQuery.map(data, 
			function(row) {
				return {
					data: row.text,
					value: row.value,
					result: row.text
				}
			}
		);
	},

	_setupSelectTags: function() {
		var instance = this;

		var params = instance.params;
		var ns = instance._ns;

		var input = jQuery('#' + ns + 'selectTag');

		input.click(
			function() {
				instance._showSelectPopup();
			}
		);
	},

	_setupSuggestions: function() {
		var instance = this;

		var params = instance.params;
		var ns = instance._ns;

		var input = jQuery('#' + ns + 'suggestions');

		input.click(
			function() {
				instance._showSuggestionsPopup();
			}
		);
	},

	_showSelectPopup: function() {
		var instance = this;

		var params = instance.params;
		var ns = instance._ns;
		var mainContainer = instance._mainContainer;
		var container = instance._container;

		mainContainer.empty();
		container.empty();

		var categories = Liferay.Service.Tags.TagsProperty.getPropertyValues(
			{
				companyId: themeDisplay.getCompanyId(),
				key: "category"
			}
		);

		jQuery.each(
			categories,
			function(i, category) {
				var tags = Liferay.Service.Tags.TagsEntry.search(
					{
						companyId: themeDisplay.getCompanyId(),
						name: '%',
						properties: 'category:' + category.value
					}
				);

				var label = '';

				jQuery.each(
					tags,
					function(j, tag) {
						if (j == 0) {
							if (i > 0) {
								label += '</fieldset>';
							}
							label += '<fieldset><legend>' + category.value + '</legend>';
						}

						var checked = (instance._curTags.indexOf(tag.name) > -1) ? ' checked="checked"' : '';

						label +=
							'<label title="' + tag.name + '">' +
								'<input' + checked + ' type="checkbox" name="' + ns + 'input' + j + '" id="' + ns + 'input' + j + '" value="' + tag.name + '" />' +
								'<a class="lfr-label-text" href="javascript: ;">' + tag.name + '</a>' +
							'</label>';

					}
				);

				container.append(label);
			}
		);

		instance._createPopup();
	},

	_showSuggestionsPopup: function() {
		var instance = this;

		var params = instance.params;
		var ns = instance._ns;
		var mainContainer = instance._mainContainer;
		var container = instance._container;

		mainContainer.empty();
		container.empty();

		var context = '';

		if (params.contentCallback) {
			context = params.contentCallback();
		}

		var url =  "http://search.yahooapis.com/ContentAnalysisService/V1/termExtraction?appid=YahooDemo&output=json&context=" + escape(context);

		var label = '';

		jQuery.ajax(
			{
				url: themeDisplay.getPathMain() + "/portal/rest_proxy",
				data: {
					url: url
				},
				dataType: "json",
				success: function(obj) {
					label += '<fieldset><legend>' + Liferay.Language.get('suggestions') + '</legend>';

					jQuery.each(
						obj.ResultSet.Result,
						function(i, tag) {
							var checked = (instance._curTags.indexOf(tag) > -1) ? ' checked="checked"' : '';

							label +=
								'<label title="' + tag + '">' +
									'<input' + checked + ' type="checkbox" name="' + ns + 'input' + i + '" id="' + ns + 'input' + i + '" value="' + tag + '" />' +
									'<a class="lfr-label-text" href="javascript: ;">' + tag + '</a>' +
								'</label>';
						}
					)

					label += '</fieldset>';

					container.append(label);
				}
			}
		);

		instance._createPopup();
	},

	_update: function() {
		var instance = this;

		instance._updateHiddenInput();
		instance._updateSummarySpan();
	},

	_updateHiddenInput: function() {
		var instance = this;

		var params = instance.params;
		var curTags = instance._curTags;

		var hiddenInput = jQuery('#' + params.hiddenInput);

		hiddenInput.val(curTags.join(','));
	},

	_updateSummarySpan: function() {
		var instance = this;

		var params = instance.params;
		var curTags = instance._curTags;

		var html = '';

		jQuery(curTags).each(
			function(i, curTag) {
				html += '<span class="ui-tag" id="' + instance._ns + 'CurTags' + i + '">';
				html += curTag;
				html += '<a class="ui-tag-delete" href="javascript: ' + instance._ns + '.deleteTag(' + i + ');"><span>x</span></a>';

				html += '</span>';
			}
		);

		var tagsSummary = jQuery('#' + params.summarySpan);

		tagsSummary.html(html);
	}
});