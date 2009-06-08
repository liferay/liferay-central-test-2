Liferay.TagsEntriesSelector = new Expanse.Class(
	{

		/**
		 * OPTIONS
		 *
		 * Required
		 * curTagsEntries (string): The current tag entries.
		 * instanceVar {string}: The instance variable for this class.
		 * hiddenInput {string}: The hidden input used to pass in the current tag entries.
		 * textInput {string}: The text input for users to add tag entries.
		 * summarySpan {string}: The summary span to show the current tag entries.
		 *
		 * Optional
		 * focus {boolean}: Whether the text input should be focused.
		 *
		 * Callbacks
		 * contentCallback {function}: Called to get suggested tag entries.
		 */

		initialize: function(options) {
			var instance = this;

			instance._curTagsEntries = [];

			instance.options = options;
			instance._ns = instance.options.instanceVar || '';
			instance._mainContainer = jQuery('<div class="lfr-tag-select-container"></div>');
			instance._container = jQuery('<div class="lfr-tag-container"></div>');
			instance._searchContainer = jQuery('<div class="lfr-tag-search-container"><input class="lfr-tag-search-input" type="text"/></div>');

			var hiddenInput = jQuery('#' + options.hiddenInput);

			hiddenInput.attr('name', hiddenInput.attr('id'));

			var textInput = jQuery('#' + options.textInput);

			var autoComplete = new Expanse.AutoComplete(
				{
					dataSource: {
						source: instance._getTagsEntries,
						responseSchema: {
							fields: ['text', 'value']
						}
					},
					delimChar: ',',
					input: options.textInput
				}
			);

			autoComplete.containerExpandEvent.subscribe(
				function(event) {
					textInput.addClass('showing-list');
					this._LFR_listShowing = true;
				}
			);

			autoComplete.containerCollapseEvent.subscribe(
				function(event) {
					textInput.removeClass('showing-list');
				}
			);

			instance._popupVisible = false;

			instance._setupSelectTagsEntries();
			instance._setupSuggestions();

			var addTagEntryButton = jQuery('#' + options.instanceVar + 'addTag');

			addTagEntryButton.click(
				function() {
						var curTagsEntries = instance._curTagsEntries;
						var newTagsEntries = textInput.val().split(',');

						jQuery.each(
							newTagsEntries,
							function(i, n) {
								n = jQuery.trim(n);

								if (curTagsEntries.indexOf(n) == -1) {
									if (n != '') {
										curTagsEntries.push(n);

										if (instance._popupVisible) {
											jQuery('input[type=checkbox][value$=' + n + ']', instance.selectTagEntryPopup.body).attr('checked', true);
										}
									}
								}
							}
						);

						curTagsEntries = curTagsEntries.sort();
						textInput.val('');

						instance._update();
					}
			);

			textInput.keypress(
				function(event) {
					if (event.keyCode == 13) {
						if (!autoComplete._LFR_listShowing) {
							addTagEntryButton.trigger('click');
						}

						autoComplete._LFR_listShowing = null;

						return false;
					}
				}
			);

			if (options.focus) {
				textInput.focus();
			}

			if (options.curTagsEntries != '') {
				instance._curTagsEntries = options.curTagsEntries.split(',');

				instance._update();
			}

			Liferay.Util.actsAsAspect(window);

			window.before(
				'submitForm',
				function() {
					var val = jQuery.trim(textInput.val());

					if (val.length) {
						addTagEntryButton.trigger('click');
					}
				}
			);
		},

		deleteTagEntry: function(id) {
			var instance = this;

			var options = instance.options;
			var curTagsEntries = instance._curTagsEntries;

			jQuery('#' + instance._ns + 'CurTags' + id).remove();

			var value = curTagsEntries.splice(id, 1);

			if (instance._popupVisible) {
				jQuery('input[type=checkbox][value$=' + value + ']', instance.selectTagEntryPopup.body).attr('checked', false);
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
					instance._curTagsEntries = instance._curTagsEntries.length ? instance._curTagsEntries : [];

					container.find('input[type=checkbox]').each(
						function() {
							var currentIndex = instance._curTagsEntries.indexOf(this.value);
							if (this.checked) {
								if (currentIndex == -1) {
									instance._curTagsEntries.push(this.value);
								}
							}
							else {
								if (currentIndex > -1) {
									instance._curTagsEntries.splice(currentIndex, 1);
								}
							}
						}
					);

					instance._update();
					instance.selectTagEntryPopup.closePopup();
				}
			);

			mainContainer.append(searchContainer).append(container).append(saveBtn);

			if (!instance.selectTagEntryPopup) {
				var popup = new Expanse.Popup(
					{
						body: mainContainer[0],
						className: 'lfr-tag-selector',
						fixedcenter: true,
						modal: false,
						header: Liferay.Language.get('tags'),
						on: {
							render: function() {
		 						var inputSearch = jQuery('.lfr-tag-search-input');

								Liferay.Util.defaultValue(inputSearch, Liferay.Language.get('search'));
							},
							close: function() {
								instance._popupVisible = false;
								instance.selectTagEntryPopup = null;
							}
						},
						resizable: false,
						width: 400
					}
				);

				instance.selectTagEntryPopup = popup;
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

		_getTagsEntries: function(term) {
			var beginning = 0;
			var end = 20;

			return Liferay.Service.Tags.TagsEntry.search(
				{
					groupId: themeDisplay.getScopeGroupId(),
					name: "%" + term + "%",
					properties: "",
					begin: beginning,
					end: end
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

		_setupSelectTagsEntries: function() {
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

					if (vocabularies.length == 0) {
						buffer.push('<fieldset class="no-matches"><legend>' + Liferay.Language.get('tag-sets') + '</legend>');
						buffer.push('<div class="lfr-tag-message">' + searchMessage + '</div>');
						buffer.push('</fieldset>');

						container.html(buffer.join(''));
					}
					else {
						jQuery.each(
							vocabularies,
							function(i) {
								var tagEntrySet = this;
								var tagEntrySetName = tagEntrySet.name;

								instance._getVocabularyEntries(
									tagEntrySetName,
									function(entries) {
										buffer.push('<fieldset>');
										buffer.push('<legend class="lfr-tag-set-title">');
										buffer.push(tagEntrySetName);
										buffer.push('</legend>');

										jQuery.each(
											entries,
											function(i) {
												var entry = this;
												var entryName = entry.name;
												var entryId = entry.entryId;
												var checked = (instance._curTagsEntries.indexOf(entryName) > -1) ? ' checked="checked" ' : '';
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
							function(i, tagEntry) {
	 							var checked = (instance._curTagsEntries.indexOf(tagEntry) > -1) ? ' checked="checked" ' : '';
								var name = ns + 'input' + i;

								buffer.push('<label title="');
								buffer.push(tagEntry);
								buffer.push('"><input');
								buffer.push(checked);
								buffer.push(' type="checkbox" name="');
								buffer.push(name);
								buffer.push('" id="');
								buffer.push(name);
								buffer.push('" value="');
								buffer.push(tagEntry);
								buffer.push('" /> ');
								buffer.push(tagEntry);
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
			var curTagsEntries = instance._curTagsEntries;

			var hiddenInput = jQuery('#' + options.hiddenInput);

			hiddenInput.val(curTagsEntries.join(','));
		},

		_updateSummarySpan: function() {
			var instance = this;

			var options = instance.options;
			var curTagsEntries = instance._curTagsEntries;

			var html = '';

			jQuery(curTagsEntries).each(
				function(i, curTagEntry) {
					html += '<span class="ui-tag" id="' + instance._ns + 'CurTags' + i + '">';
					html += curTagEntry;
					html += '<a class="ui-tag-delete" href="javascript:' + instance._ns + '.deleteTagEntry(' + i + ');"><span>x</span></a>';
					html += '</span>';
				}
			);

			var tagsEntriesSummary = jQuery('#' + options.summarySpan);

			if (curTagsEntries.length) {
				tagsEntriesSummary.removeClass('empty');
			}
			else {
				tagsEntriesSummary.addClass('empty');
			}

			tagsEntriesSummary.html(html);
		}
	}
);