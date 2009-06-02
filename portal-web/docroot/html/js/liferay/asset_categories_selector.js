Liferay.AssetCategoriesSelector = new Expanse.Class(
	{

		/**
		 * OPTIONS
		 *
		 * Required
		 * curAssetCategoryIds (string): The ids of the current categories.
		 * curAssetCategoryNames (string): The names of the current categories.
		 * instanceVar {string}: The instance variable for this class.
		 * hiddenInput {string}: The hidden input used to pass in the current categories.
		 * summarySpan {string}: The summary span to show the current categories.
		 */

		initialize: function(options) {
			var instance = this;

			instance._curAssetCategoryIds = [];

			instance.options = options;
			instance._ns = instance.options.instanceVar || '';
			instance._mainContainer = jQuery('<div class="lfr-asset-category-select-container"></div>');
			instance._container = jQuery('<div class="lfr-asset-category-container"></div>');
			instance._searchContainer = jQuery('<div class="lfr-asset-category-search-container"><input class="lfr-asset-category-search-input" type="text"/></div>');

			var hiddenInput = jQuery('#' + options.hiddenInput);

			hiddenInput.attr('name', hiddenInput.attr('id'));

			instance._popupVisible = false;

			instance._setupSelectAssetCategories();

			if (options.curAssetCategoryIds != '') {
				instance._curAssetCategoryIds = options.curAssetCategoryIds.split(',');
				instance._curAssetCategoryNames = options.curAssetCategoryNames.split(',');

				instance._update();
			}
		},

		deleteAssetCategory: function(id) {
			var instance = this;

			var options = instance.options;
			var curAssetCategoryIds = instance._curAssetCategoryIds;

			jQuery('#' + instance._ns + 'CurAssetCategoryIds' + id).remove();

			var value = curAssetCategoryIds.splice(id, 1);

			if (instance._popupVisible) {
				jQuery('input[type=checkbox][value$=' + value + ']', instance.selectAssetCategoryPopup.body).attr('checked', false);
			}

			instance._update();
		},

		_assetCategoryIterator: function(categories, buffer, counter) {
			var instance = this;

			buffer.push('<ul>');

			jQuery.each(
				categories,
				function(i) {
					var category = this;
					var categoryName = category.name;
					var categoryId = category.categoryId;
					var checked = (instance._curAssetCategoryIds.indexOf(categoryId.toString()) > -1) ? ' checked="checked" ' : '';

					buffer.push('<li><label title="');
					buffer.push(categoryName);
					buffer.push('"><input type="checkbox" value="');
					buffer.push(categoryId);
					buffer.push('" ');
					buffer.push(checked);
					buffer.push(' data-title="');
					buffer.push(categoryName);
					buffer.push('"> ');
					buffer.push(categoryName);
					buffer.push('</label>');

					var childCategories = Liferay.Service.Asset.AssetCategory.getChildCategories(
						{
							vocabularyId: categoryId
						},
						false
					);

					if (childCategories.length > 0) {
						instance._assetCategoryIterator(childCategories, buffer, counter + 1);
					}

					buffer.push('</li>');
				}
			);

			buffer.push('</ul>');

			counter = counter - 1;
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
					instance._curAssetCategoryIds = instance._curAssetCategoryIds.length ? instance._curAssetCategoryIds : [];

					container.find('input[type=checkbox]').each(
						function() {
							var currentIndex = instance._curAssetCategoryIds.indexOf(this.value);
							if (this.checked) {
								if (currentIndex == -1) {
									instance._curAssetCategoryIds.push(this.value);
									instance._curAssetCategoryNames.push(jQuery(this).attr('data-title'));
								}
							}
							else {
								if (currentIndex > -1) {
									instance._curAssetCategoryIds.splice(currentIndex, 1);
									instance._curAssetCategoryNames.splice(currentIndex, 1);
								}
							}
						}
					);

					instance._update();
					instance.selectAssetCategoryPopup.closePopup();
				}
			);

			mainContainer.append(searchContainer).append(container).append(saveBtn);

			if (!instance.selectAssetCategoryPopup) {
				var popup = new Expanse.Popup(
					{
						body: mainContainer[0],
						className: 'lfr-asset-category-selector',
						fixedcenter: true,
						header: Liferay.Language.get('categories'),
						modal: false,
						on: {
							render: function() {
		 						var inputSearch = jQuery('.lfr-asset-category-search-input');

								Liferay.Util.defaultValue(inputSearch, Liferay.Language.get('search'));
							},
							close: function() {
								instance._popupVisible = false;
								instance.selectAssetCategoryPopup = null;
							}
						},
						resizable: false,
						width: 400
					}
				);

				instance.selectAssetCategoryPopup = popup;
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

		_initializeSearch: function(container) {
			var data = function() {
				var value = jQuery(this).attr('title');

				return value.toLowerCase();
			};

			var inputSearch = jQuery('.lfr-asset-category-search-input');

			var options = {
				data: data,
				list: '.lfr-asset-category-container label',
				after: function() {
					jQuery('fieldset', container).each(
						function() {
							var fieldset = jQuery(this);

							var visibleCategories = fieldset.find('label:visible');

							if (visibleCategories.length == 0) {
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

		_setupSelectAssetCategories: function() {
			var instance = this;

			var options = instance.options;
			var ns = instance._ns;

			var input = jQuery('#' + ns + 'selectAssetCategories');

			input.click(
				function() {
					instance._showSelectPopup();
				}
			);
		},

		_showSelectPopup: function() {
			var instance = this;

			var options = instance.options;
			var ns = instance._ns;
			var mainContainer = instance._mainContainer;
			var container = instance._container;
			var searchMessage = Liferay.Language.get('no-categories-found');

			mainContainer.empty();

			container.empty().html('<div class="loading-animation" />');

			Liferay.Service.Asset.AssetCategoryVocabulary.getGroupCategoryVocabularies(
				{
					groupId: themeDisplay.getScopeGroupId()
				},
				function(vocabularies) {
					var buffer = [];
					if (vocabularies.length == 0) {
						buffer.push('<fieldset class="no-matches"><legend>' + Liferay.Language.get('category-sets') + '</legend>');
						buffer.push('<div class="lfr-asset-category-message">' + searchMessage + '</div>');
						buffer.push('</fieldset>');

						container.html(buffer.join(''));
					}
					else {
						jQuery.each(
							vocabularies,
							function(i) {
								var categoryVocabulary = this;
								var categoryVocabularyName = categoryVocabulary.name;
								var categoryVocabularyId = categoryVocabulary.categoryVocabularyId;

								Liferay.Service.Asset.AssetCategory.getVocabularyRootCategories(
									{
										assetCategoryVocabularyId: categoryVocabularyId
									},
									function(categories) {
										buffer.push('<fieldset>');
										buffer.push('<legend class="lfr-asset-category-set-title">');
										buffer.push(categoryVocabularyName);
										buffer.push('</legend><div class="treeview">');

										instance._assetCategoryIterator(categories, buffer, 0);

										buffer.push('</div><div class="lfr-asset-category-message">' + searchMessage + '</div>');
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

		_update: function() {
			var instance = this;

			instance._updateHiddenInput();
			instance._updateSummarySpan();
		},

		_updateHiddenInput: function() {
			var instance = this;

			var options = instance.options;
			var curAssetCategoryIds = instance._curAssetCategoryIds;

			var hiddenInput = jQuery('#' + options.hiddenInput);

			hiddenInput.val(curAssetCategoryIds.join(','));
		},

		_updateSummarySpan: function() {
			var instance = this;

			var options = instance.options;
			var curAssetCategoryIds = instance._curAssetCategoryIds;
			var curAssetCategoryNames = instance._curAssetCategoryNames;

			var html = '';

			jQuery(curAssetCategoryIds).each(
				function(i, curAssetCategoryId) {
					html += '<span class="ui-asset-category" id="' + instance._ns + 'CurAssetCategoryIds' + i + '">';
					html += curAssetCategoryNames[i];
					html += '<a class="ui-asset-category-delete" href="javascript:' + instance._ns + '.deleteAssetCategory(' + i + ');"><span>x</span></a>';
					html += '</span>';
				}
			);

			var assetCategoriesSummary = jQuery('#' + options.summarySpan);

			if (curAssetCategoryIds.length) {
				assetCategoriesSummary.removeClass('empty');
			}
			else {
				assetCategoriesSummary.addClass('empty');
			}

			assetCategoriesSummary.html(html);
		}
	}
);