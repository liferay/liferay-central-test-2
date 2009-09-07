Liferay.AssetTagsSelector = new Alloy.Class(
	{

		/**
		 * OPTIONS
		 *
		 * Required
		 * curTags (string): The current tags.
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
			instance._seed = instance.options.seed || '';
			instance._ns = instance.options.instanceVar || '';
			instance._mainContainer = jQuery('<div class="lfr-tag-select-container"></div>');
			instance._container = jQuery('<div class="lfr-tag-container"></div>');
			instance._searchContainer = jQuery('<div class="lfr-tag-search-container"><input class="lfr-tag-search-input" type="text"/></div>');
			instance._summarySpan = jQuery('#' + options.summarySpan + instance._seed);

			instance._summarySpan.html('');

			var hiddenInput = jQuery('#' + options.hiddenInput + instance._seed);

			hiddenInput.attr('name', hiddenInput.attr('id'));

			var textInput = jQuery('#' + options.textInput + instance._seed);

			AUI().use(
				'autocomplete',
				function(A) {
					var autoComplete = new A.AutoComplete(
						{
							dataSource: instance._searchTags,
							schema: {
								resultFields: ['text', 'value']
							},
							delimChar: ',',
							input: textInput[0],
							contentBox: '#' + instance._ns + 'assetTagsSelector'
						}
					);

					autoComplete.render();

					instance._autoComplete = autoComplete;

					instance._popupVisible = false;

					instance._setupSelectTags();
					instance._setupSuggestions();

					var addTagButton = jQuery('#' + instance._namespace('addTag'));

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
													var contentBox = instance.selectTagPopup.get('contentBox');
													var contextNode = contentBox._node;

													jQuery('input[type=checkbox][value$=' + n + ']', contextNode).attr('checked', true);
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

					textInput.keyup(
						function() {
							addTagButton.attr('disabled', !this.value.length);
						}
					);

					textInput.keypress(
						function(event) {
							if (event.keyCode == 13) {
								if (!autoComplete.get('visible')) {
									addTagButton.trigger('click');
								}

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

					instance._summarySpan.unbind('click');

					instance._summarySpan.click(
						function(event) {
							var target = jQuery(event.target);

							if (!target.hasClass('ui-tag-delete')) {
								target = target.parent();
							}

							if (target.hasClass('ui-tag-delete')) {
								var id = target.attr('data-tagIndex');

								instance.deleteTag(id);
							}
						}
					);
				}
			);
		},

		deleteTag: function(id) {
			var instance = this;

			var options = instance.options;
			var curTags = instance._curTags;

			jQuery('#' + instance._namespace('CurTags' + id + '_')).remove();
			var value = curTags.splice(id, 1);

			if (instance._popupVisible) {
				var contentBox = instance.selectTagPopup.get('contentBox');
				var contextNode = contentBox._node;

				jQuery('input[type=checkbox][value$=' + value + ']', contextNode).attr('checked', false);
			}

			instance._update();
		},

		_createPopup: function() {
			var instance = this;

			var container = instance._container;
			var mainContainer = instance._mainContainer;
			var searchContainer = instance._searchContainer;

			mainContainer.append(searchContainer).append(container);

			if (!instance.selectTagPopup) {
				AUI().use(
					'dialog',
					function(A) {
						var titleLabel = Liferay.Language.get('tags');
						var saveLabel = Liferay.Language.get('save');
						var bodyContent = new A.Node(mainContainer[0]);

						var saveFn = function() {
							instance._curTags = instance._curTags.length ? instance._curTags : [];

							container.find('input[type=checkbox]').each(
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
							instance.selectTagPopup.hide();
						};

						var popup = new A.Dialog(
							{
								bodyContent: bodyContent,
								centered: true,
								draggable: true,
								height: 400,
								stack: true,
								title: titleLabel,
								width: 320,
								zIndex: 1000,
								after: {
									render: function() {
										var inputSearch = jQuery('.lfr-tag-search-input');

										instance._initializeSearch(instance._container);
									},
									close: function() {
										instance._popupVisible = false;
									}
								},
								buttons: [
									{
										text: saveLabel,
										handler: saveFn
									}
								]
							}
						)
						.render();

						popup.get('boundingBox').addClass('lfr-tag-selector');

						instance.selectTagPopup = popup;

						if (Liferay.Browser.isIe()) {
							jQuery('.lfr-label-text', popup).click(
								function() {
									var input = jQuery(this.previousSibling);
									var checkedState = !input.is(':checked');
									input.attr('checked', checkedState);
								}
							);
						}
				});
			}
			else {
				instance.selectTagPopup.show();
			}

			instance._popupVisible = true;
		},

		_getTags: function(callback) {
			var instance = this;

			Liferay.Service.Asset.AssetTag.getGroupTags(
				{
					groupId: themeDisplay.getScopeGroupId()
				},
				callback
			);
		},

		_initializeSearch: function(container) {
			var data = function() {
				var value = jQuery(this).attr('title');

				return value.toLowerCase();
			};

			var inputSearch = jQuery('.lfr-tag-search-input').val('');

			Liferay.Util.defaultValue(inputSearch, Liferay.Language.get('search'));

			var options = {
				data: data,
				list: '.lfr-tag-container label',
				after: function() {
					jQuery('fieldset', container).each(
						function() {
							var fieldset = jQuery(this);

							var visibleTags = fieldset.find('label:visible');

							if (visibleTags.length == 0) {
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

		_namespace: function(name) {
			var instance = this;

			return instance._ns + name + instance._seed;
		},

		_searchTags: function(term) {
			var beginning = 0;
			var end = 20;

			if (term == '*') {
				term = '';
			}

			return Liferay.Service.Asset.AssetTag.search(
				{
					groupId: themeDisplay.getScopeGroupId(),
					name: '%' + term + '%',
					properties: '',
					begin: beginning,
					end: end
				}
			);
		},

		_setupSelectTags: function() {
			var instance = this;

			var options = instance.options;

			var input = jQuery('#' + instance._namespace('selectTag'));

			input.unbind('click');

			input.click(
				function() {
					instance._showSelectPopup();
				}
			);
		},

		_setupSuggestions: function() {
			var instance = this;

			var options = instance.options;

			var input = jQuery('#' + instance._namespace('suggestions'));

			input.click(
				function() {
					instance._showSuggestionsPopup();
				}
			);
		},

		_showSelectPopup: function() {
			var instance = this;

			var options = instance.options;
			var mainContainer = instance._mainContainer;
			var container = instance._container;
			var searchMessage = Liferay.Language.get('no-tags-found');

			mainContainer.empty();

			container.empty().html('<div class="loading-animation" />');

			instance._createPopup();

			instance._getTags(
				function(tags) {
					var buffer = [];

					buffer.push('<fieldset>');

					jQuery.each(
						tags,
						function(i) {
							var tag = this;
							var tagName = tag.name;
							var tagId = tag.tagId;
							var checked = (instance._curTags.indexOf(tagName) > -1) ? ' checked="checked" ' : '';

							buffer.push('<label title="');
							buffer.push(tagName);
							buffer.push('">');
							buffer.push('<input type="checkbox" value="');
							buffer.push(tagName);
							buffer.push('" ');
							buffer.push(checked);
							buffer.push('> ');
							buffer.push(tagName);
							buffer.push('</label>');
						}
					);

					buffer.push('<div class="lfr-tag-message">' + searchMessage + '</div>');
					buffer.push('</fieldset>');

					container.html(buffer.join(''));

					if (tags.length == 0) {
						container.addClass('no-matches');
					}
					else {
						container.removeClass('no-matches');
					}

					instance._initializeSearch(container);
				}
			);
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

			var url = 'http://search.yahooapis.com/ContentAnalysisService/V1/termExtraction?appid=YahooDemo&output=json&context=' + escape(context);

			var buffer = [];

			jQuery.ajax(
				{
					url: themeDisplay.getPathMain() + '/portal/rest_proxy',
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

			var hiddenInput = jQuery('#' + options.hiddenInput + instance._seed);

			hiddenInput.val(curTags.join(','));
		},

		_updateSummarySpan: function() {
			var instance = this;

			var options = instance.options;
			var curTags = instance._curTags;

			var html = '';

			jQuery(curTags).each(
				function(i, curTag) {
					html += '<span class="ui-tag" id="' + instance._namespace('CurTags' + i + '_') + '">';
					html += curTag;
					html += '<a class="ui-tag-delete" href="javascript:;" data-tagIndex="' + i + '"><span>x</span></a>';
					html += '</span>';
				}
			);

			var tagsSummary = instance._summarySpan;

			if (curTags.length) {
				tagsSummary.removeClass('empty');
			}
			else {
				tagsSummary.addClass('empty');
			}

			tagsSummary.html(html);
		}
	}
);