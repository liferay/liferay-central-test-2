AUI().add(
	'liferay-categories-selector',
	function(A) {

		/**
		 * OPTIONS
		 *
		 * Required
		 * curCategoryIds (string): The ids of the current categories.
		 * curCategoryNames (string): The names of the current categories.
		 * instanceVar {string}: The instance variable for this class.
		 * hiddenInput {string}: The hidden input used to pass in the current categories.
		 * summarySpan {string}: The summary span to show the current categories.
		 */

		var AssetCategoriesSelector = function(options) {
			var instance = this;

			instance._curCategoryIds = [];

			instance.options = options;
			instance._seed = instance.options.seed || '';
			instance._ns = instance.options.instanceVar || '';
			instance._mainContainer = jQuery('<div class="lfr-asset-category-select-container"></div>');
			instance._container = jQuery('<div class="lfr-asset-category-container"></div>');
			instance._searchContainer = jQuery('<div class="lfr-asset-category-search-container"><input class="lfr-asset-category-search-input" type="text"/></div>');
			instance._summarySpan = jQuery('#' + options.summarySpan + instance._seed);

			instance._summarySpan.html('');

			var hiddenInput = jQuery('#' + options.hiddenInput + instance._seed);

			hiddenInput.attr('name', hiddenInput.attr('id'));

			instance._popupVisible = false;

			instance._setupSelectAssetCategories();

			if (options.curCategoryIds) {
				instance._curCategoryIds = options.curCategoryIds.split(',');
				instance._curCategoryNames = options.curCategoryNames.split(',');

				instance._update();
			}
			else {
				instance._curCategoryIds = [];
				instance._curCategoryNames = [];
			}

			instance._summarySpan.unbind('click');

			instance._summarySpan.click(
				function(event) {
					var target = jQuery(event.target);

					if (!target.hasClass('ui-asset-category-delete')) {
						target = target.parent();
					}

					if (target.hasClass('ui-asset-category-delete')) {
						var id = target.attr('data-tagIndex');

						instance.deleteCategory(id);
					}
				}
			);
		};

		AssetCategoriesSelector.prototype = {
			deleteCategory: function(id) {
				var instance = this;

				var options = instance.options;
				var curCategoryIds = instance._curCategoryIds;
				var curCategoryNames = instance._curCategoryNames;

				jQuery('#' + instance._namespace('CurCategoryIds' + id + '_')).remove();

				var value = curCategoryIds.splice(id, 1);

				curCategoryNames.splice(id, 1);

				if (instance._popupVisible) {
					var contentBox = instance.selectCategoryPopup.get('contentBox');
					var contextNode = contentBox._node;

					jQuery('input[type=checkbox][value$=' + value + ']', contextNode).attr('checked', false);
				}

				instance._update();
			},

			_categoryIterator: function(categories, buffer, counter) {
				var instance = this;

				buffer.push('<ul>');

				jQuery.each(
					categories,
					function(i) {
						var category = this;
						var categoryName = category.name;
						var categoryId = category.categoryId;
						var checked = (instance._curCategoryIds.indexOf(categoryId.toString()) > -1) ? ' checked="checked" ' : '';

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
							instance._categoryIterator(childCategories, buffer, counter + 1);
						}

						buffer.push('</li>');
					}
				);

				buffer.push('</ul>');

				counter = counter - 1;
			},

			_createPopup: function() {
				var instance = this;

				var container = instance._container;
				var mainContainer = instance._mainContainer;
				var searchContainer = instance._searchContainer;

				mainContainer.append(searchContainer).append(container);

				if (!instance.selectCategoryPopup) {
					var titleLabel = Liferay.Language.get('categories');
					var saveLabel = Liferay.Language.get('save');
					var bodyContent = new A.Node(mainContainer[0]);

					var saveFn = function() {
						instance._curCategoryIds = instance._curCategoryIds.length ? instance._curCategoryIds : [];

						container.find('input[type=checkbox]').each(
							function() {
								var currentIndex = instance._curCategoryIds.indexOf(this.value);
								if (this.checked) {
									if (currentIndex == -1) {
										instance._curCategoryIds.push(this.value);
										instance._curCategoryNames.push(jQuery(this).attr('data-title'));
									}
								}
								else {
									if (currentIndex > -1) {
										instance._curCategoryIds.splice(currentIndex, 1);
										instance._curCategoryNames.splice(currentIndex, 1);
									}
								}
							}
						);

						instance._update();
						instance.selectCategoryPopup.hide();
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
									var inputSearch = jQuery('.lfr-asset-category-search-input');

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

					popup.get('boundingBox').addClass('lfr-asset-category-selector');

					instance.selectCategoryPopup = popup;

					if (Liferay.Browser.isIe()) {
						jQuery('.lfr-label-text', popup).click(
							function() {
								var input = jQuery(this.previousSibling);
								var checkedState = !input.is(':checked');
								input.attr('checked', checkedState);
							}
						);
					}
				}
				else {
					instance.selectCategoryPopup.show();
				}

				instance._popupVisible = true;
			},

			_initializeSearch: function(container) {
				var data = function(node) {
					var value = node.attr('title');

					return value.toLowerCase();
				};

				var inputSearch = jQuery('.lfr-asset-category-search-input').val('');

				Liferay.Util.defaultValue(inputSearch, Liferay.Language.get('search'));

				var options = {
					after: {
						search: function() {
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
					},
					data: data,
					input: '.lfr-asset-category-search-input',
					nodes: '.lfr-asset-category-container label'
				};

				new A.LiveSearch(options);
			},

			_namespace: function(name) {
				var instance = this;

				return instance._ns + name + instance._seed;
			},

			_setupSelectAssetCategories: function() {
				var instance = this;

				var options = instance.options;

				var input = jQuery('#' + instance._namespace('selectAssetCategories'));

				input.unbind('click');

				input.click(
					function() {
						instance._showSelectPopup();
					}
				);
			},

			_showSelectPopup: function() {
				var instance = this;

				var options = instance.options;
				var mainContainer = instance._mainContainer;
				var container = instance._container;
				var globalMessage = Liferay.Language.get('global');
				var searchMessage = Liferay.Language.get('no-categories-found');

				mainContainer.empty();

				container.empty().html('<div class="loading-animation" />');

				instance._createPopup();

				Liferay.Service.Asset.AssetVocabulary.getGroupsVocabularies(
					{
						groupIds: [themeDisplay.getScopeGroupId(), themeDisplay.getCompanyGroupId()]
					},
					function(vocabularies) {
						var buffer = [];

						jQuery.each(
							vocabularies,
							function(i) {
								var vocabulary = this;
								var vocabularyName = vocabulary.name;
								var vocabularyId = vocabulary.vocabularyId;

								var categories = Liferay.Service.Asset.AssetCategory.getVocabularyRootCategories(
									{
										assetVocabularyId: vocabularyId
									}
								);

								var noMatchesClass = '';

								if (categories.length == 0) {
									if (vocabulary.groupId == themeDisplay.getCompanyGroupId()) {
										return;
									}

									noMatchesClass = 'no-matches';
								}

								buffer.push('<fieldset class="lfr-asset-vocabulary-container ' + noMatchesClass + '">');
								buffer.push('<legend class="lfr-asset-category-set-title">');
								buffer.push(vocabularyName);

								if (vocabulary.groupId == themeDisplay.getCompanyGroupId()) {
									buffer.push(' (' + globalMessage + ')');
								}

								buffer.push('</legend><div class="lfr-asset-category-list">');

								instance._categoryIterator(categories, buffer, 0);

								buffer.push('</div><div class="lfr-asset-category-message">' + searchMessage + '</div>');
								buffer.push('</fieldset>');

								container.html(buffer.join(''));

								instance._initializeSearch(container);
							}
						);
					}
				);
			},

			_update: function() {
				var instance = this;

				instance._updateHiddenInput();
				instance._updateSummarySpan();
			},

			_updateHiddenInput: function() {
				var instance = this;

				var options = instance.options;
				var curCategoryIds = instance._curCategoryIds;

				var hiddenInput = jQuery('#' + options.hiddenInput + instance._seed);

				hiddenInput.val(curCategoryIds.join(','));
			},

			_updateSummarySpan: function() {
				var instance = this;

				var options = instance.options;
				var curCategoryIds = instance._curCategoryIds;
				var curCategoryNames = instance._curCategoryNames;

				var html = '';

				jQuery(curCategoryIds).each(
					function(i, curCategoryId) {
						html += '<span class="ui-asset-category" id="' + instance._namespace('CurCategoryIds' + i + '_') + '">';
						html += curCategoryNames[i];
						html += '<a class="ui-asset-category-delete" href="javascript:;" data-tagIndex="' + i + '"><span>x</span></a>';
						html += '</span>';
					}
				);

				var assetCategoriesSummary = instance._summarySpan;

				if (curCategoryIds.length) {
					assetCategoriesSummary.removeClass('empty');
				}
				else {
					assetCategoriesSummary.addClass('empty');
				}

				assetCategoriesSummary.html(html);
			}
		};

		Liferay.AssetCategoriesSelector = AssetCategoriesSelector;
	},
	'',
	{
		requires: ['dialog', 'live-search']
	}
);