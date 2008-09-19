Liferay.TagsSelector = new Class({

	/**
	 * OPTIONS
	 *
	 * Required
	 * instanceVar {string}: The instance variable for this class.
	 * hiddenInput {string}: The hidden input used to pass in the current tags.
	 * textInput {string}: The text input for users to add tags.
	 * summarySpan {string}: The summary span to show the current tags.
	 *
	 * Optional
	 * focus {boolean}: Whether the text input should be focused.
	 *
	 * Callbacks
	 * contentCallback {function}: Called to get suggested tags.
	 */

	initialize: function(options) {
		var instance = this;

		instance._curTags = [];

		instance.options = options;
		instance._ns = instance.options.instanceVar || '';
		instance._mainContainer = jQuery('<div class="lfr-tag-select-container"></div>');
		instance._container = jQuery('<div class="lfr-tag-container"></div>');
		instance._searchContainer = jQuery('<div class="lfr-tag-search-container"><input class="lfr-tag-search-input" type="text"/></div>');

		var hiddenInput = jQuery('#' + options.hiddenInput);

		hiddenInput.attr('name', hiddenInput.attr('id'));

		var textInput = jQuery('#' + options.textInput);

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

		var addTagButton = jQuery('#' + options.instanceVar + 'addTag');

		addTagButton.click(
			function() {
					var curTags = instance._curTags;
					var newTags = textInput.val().split(',');

					jQuery.each(
						newTags,
						function(i, n) {
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

		if (options.focus) {
			textInput.focus();
		}

		if (options.curTags != '') {
			instance._curTags = options.curTags.split(',');

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

		var options = instance.options;
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
		var searchContainer = instance._searchContainer;

		var saveBtn = jQuery('<input class="submit lfr-save-button" id="' + ns + 'saveButton" type="submit" value="' + Liferay.Language.get('save') + '" />');

		saveBtn.click(
			function() {
				instance._curTags = instance._curTags.length ? instance._curTags : [];

				container.find('input[@type=checkbox]').each(
					function() {
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

		mainContainer.append(searchContainer).append(container).append(saveBtn);

		if (!instance.selectTagPopup) {
			var popup = Liferay.Popup(
				{
					className: 'lfr-tag-selector',
					message: mainContainer[0],
					modal: false,
					position: 'center',
					resizable: false,
					width: 400,
					open: function() {
 						var inputSearch = jQuery('.lfr-tag-search-input');

						Liferay.Util.defaultValue(inputSearch, Liferay.Language.get('search'));
						inputSearch.trigger('focus');
					},
					onClose: function() {
						instance._popupVisible = false;
						instance.selectTagPopup = null;
					}
				}
			);
			instance.selectTagPopup = popup;
		}
		instance._popupVisible = true;

		if (Liferay.Browser.isIe()) {
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
				groupId: themeDisplay.getScopeGroupId(),
				name: "%" + term + "%",
				properties: "",
				begin: beginning,
				end: end
			}
		);

		return jQuery.map(
			data,
			function(row) {
				return {
					data: row.text,
					value: row.value,
					result: row.text
				}
			}
		);
	},

	_getVocabularies: function(folksonomy, callback) {
		var instance = this;

		Liferay.Service.Tags.TagsVocabulary.getGroupVocabularies(
			{
				groupId: themeDisplay.getScopeGroupId(),
				folksonomy: folksonomy
			},
			callback
		);
	},

	_getVocabularyEntries: function(vocabulary, callback) {
		var instance = this;

		Liferay.Service.Tags.TagsEntry.getGroupVocabularyEntries(
			{
				groupId: themeDisplay.getScopeGroupId(),
				name: vocabulary
			},
			callback
		);
	},

	_initializeSearch: function(container) {
		var data = function() {
			var value = jQuery(this).attr('title');

			return value.toLowerCase();
		};

		var inputSearch = jQuery('.lfr-tag-search-input');

		var options = {
			data: data,
			list: '.lfr-tag-container label',
			after: function() {
				jQuery('fieldset', container).each(
					function() {
						var fieldset = jQuery(this);

						var visibleEntries = fieldset.find('label:visible');

						if (visibleEntries.length == 0) {
							fieldset.addClass('no-matches');
						}
						else {
							fieldset.removeClass('no-matches');
						}
					}
				);
			}
		};

		inputSearch.liveSearch(options);
	},

	_setupSelectTags: function() {
		var instance = this;

		var options = instance.options;
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

		var options = instance.options;
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

		var options = instance.options;
		var ns = instance._ns;
		var mainContainer = instance._mainContainer;
		var container = instance._container;
		var searchMessage = Liferay.Language.get('no-tags-found');

		mainContainer.empty();
		container.empty().html('<div class="loading-animation" />');

		instance._getVocabularies(true,
			function(vocabularies) {
				var buffer = [];

				jQuery.each(
					vocabularies,
					function(i) {
						var tagset = this;
						var tagsetName = tagset.name;

						instance._getVocabularyEntries(
							tagsetName,
							function(entries) {

								buffer.push('<fieldset>');
								buffer.push('<legend class="lfr-tag-set-title">');
								buffer.push(tagsetName);
								buffer.push('</legend>');

								jQuery.each(
									entries,
									function(i) {
										var entry = this;
										var entryName = entry.name;
										var entryId = entry.entryId;
										var checked = (instance._curTags.indexOf(entryName) > -1) ? ' checked="checked" ' : '';
										buffer.push('<label title="');
										buffer.push(entryName);
										buffer.push('">');
										buffer.push('<input type="checkbox" value="');
										buffer.push(entryName);
										buffer.push('" ');
										buffer.push(checked);
										buffer.push('> ');
										buffer.push(entryName);
										buffer.push('</label>');
									}
								);

								buffer.push('<div class="lfr-tag-message">' + searchMessage + '</div>');
								buffer.push('</fieldset>');
								container.html(buffer.join(''));

								instance._initializeSearch(container);
							}
						);
					}
				);
			}
		);

		instance._createPopup();
	},

	_showSuggestionsPopup: function() {
		var instance = this;

		var options = instance.options;
		var ns = instance._ns;
		var mainContainer = instance._mainContainer;
		var container = instance._container;
		var searchMessage = Liferay.Language.get('no-tags-found');

		mainContainer.empty();
		container.empty().html('<div class="loading-animation" />');

		var context = '';

		if (options.contentCallback) {
			context = options.contentCallback();
		}

		var url =  "http://search.yahooapis.com/ContentAnalysisService/V1/termExtraction?appid=YahooDemo&output=json&context=" + escape(context);

		var buffer = [];

		jQuery.ajax(
			{
				url: themeDisplay.getPathMain() + "/portal/rest_proxy",
				data: {
					url: url
				},
				dataType: "json",
				success: function(obj) {
					buffer.push('<fieldset><legend>' + Liferay.Language.get('suggestions') + '</legend>');

					jQuery.each(
						obj.ResultSet.Result,
						function(i, tag) {
 							var checked = (instance._curTags.indexOf(tag) > -1) ? ' checked="checked" ' : '';
							var name = ns + 'input' + i;

							buffer.push('<label title="');
							buffer.push(tag);
							buffer.push('"><input');
							buffer.push(checked);
							buffer.push(' type="checkbox" name="');
							buffer.push(name);
							buffer.push('" id="');
							buffer.push(name);
							buffer.push('" value="');
							buffer.push(tag);
							buffer.push('" /> ');
							buffer.push(tag);
							buffer.push('</label>');
						}
					);

					buffer.push('<div class="lfr-tag-message">' + searchMessage + '</div>');
					buffer.push('</fieldset>');
 					container.html(buffer.join(''));

					if (!obj.ResultSet.Result.length) {
						container.find('fieldset:first').addClass('no-matches')
					}

					instance._initializeSearch(container);
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

		var options = instance.options;
		var curTags = instance._curTags;

		var hiddenInput = jQuery('#' + options.hiddenInput);

		hiddenInput.val(curTags.join(','));
	},

	_updateSummarySpan: function() {
		var instance = this;

		var options = instance.options;
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

		var tagsSummary = jQuery('#' + options.summarySpan);

		if (curTags.length) {
			tagsSummary.removeClass('empty');
		}
		else {
			tagsSummary.addClass('empty');
		}

		tagsSummary.html(html);
	}
});