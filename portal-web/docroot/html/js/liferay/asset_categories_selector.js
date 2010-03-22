AUI().add(
	'liferay-categories-selector',
	function(A) {
		var Lang = A.Lang;

		var	getClassName = A.ClassNameManager.getClassName;

		var NAME = 'categoriesselector';

		var CSS_HELPER_RESET = getClassName('helper', 'reset');

		var CSS_NO_MATCHES = 'no-matches';

		var TPL_CHECKED = ' checked="checked" ';

		var TPL_INPUT = '<label title="{name}"><input {checked} data-title="{name}" type="checkbox" value="{categoryId}" />{name}</label>';

		var TPL_LIST_CLOSE = '</ul>';

		var TPL_LIST_OPEN = '<ul>';

		var TPL_LIST_ITEM_CLOSE = '</li>';

		var TPL_LIST_ITEM_OPEN = '<li>';

		var TPL_MESSAGE = '<div class="lfr-tag-message">{0}</div>';

		/**
		 * OPTIONS
		 *
		 * Required
		 * curEntryIds (string): The ids of the current categories.
		 * curEntries (string): The names of the current categories.
		 * instanceVar {string}: The instance variable for this class.
		 * hiddenInput {string}: The hidden input used to pass in the current categories.
		 */

		var AssetCategoriesSelector = function() {
			AssetCategoriesSelector.superclass.constructor.apply(this, arguments);
		};

		AssetCategoriesSelector.NAME = NAME;

		AssetCategoriesSelector.ATTRS = {
			curEntryIds: {
				setter: function(value) {
					var instance = this;

					if (Lang.isString(value)) {
						value = value.split(',');
					}

					return value;
				},
				value: ''
			}
		};

		A.extend(
			AssetCategoriesSelector,
			Liferay.AssetTagsSelector,
			{
				renderUI: function() {
					var instance = this;

					AssetCategoriesSelector.superclass.constructor.superclass.renderUI.apply(instance, arguments);

					instance._renderToolset();

					instance.inputContainer.hide('aui-helper-hidden-accessible');
				},

				syncUI: function() {
					var instance = this;

					AssetCategoriesSelector.superclass.constructor.superclass.syncUI.apply(instance, arguments);

					var matchKey = instance.get('matchKey');

					instance.entries.getKey = function(obj) {
						return obj.categoryId;
					};

					var curEntries = instance.get('curEntries');
					var curEntryIds = instance.get('curEntryIds');

					A.each(
						curEntryIds,
						function(item, index, collection) {
							var entry = {
								categoryId: item
							};

							entry[matchKey] = curEntries[index];

							instance.entries.add(entry);
						}
					);
				},

				_afterTBLFocusedChange: function() {
				},

				_entriesIterator: function(item, index, collection) {
					var instance = this;

					var buffer = instance._buffer;

					if (index == 0) {
						buffer.push(TPL_LIST_OPEN);
					}

					item.checked = instance.entries.findIndexBy('categoryId', item.categoryId) > -1 ? TPL_CHECKED : '';

					buffer.push(TPL_LIST_ITEM_OPEN);

					instance._formatEntry(item);

					var childCategories = Liferay.Service.Asset.AssetCategory.getChildCategories(
						{
							vocabularyId: item.categoryId
						},
						false
					);

					A.each(childCategories, instance._entriesIterator, instance);

					buffer.push(TPL_LIST_ITEM_CLOSE);

					if (index == collection.length - 1) {
						buffer.push(TPL_LIST_CLOSE);
					}
				},

				_formatEntry: function(item) {
					var instance = this;

					var input = A.substitute(TPL_INPUT, item);

					instance._buffer.push(input);
				},

				_getEntries: function(callback) {
					var instance = this;

					Liferay.Service.Asset.AssetVocabulary.getGroupsVocabularies(
						{
							groupIds: [themeDisplay.getParentGroupId(), themeDisplay.getCompanyGroupId()]
						},
						callback
					);
				},

				_onCheckboxClick: function(event) {
					var instance = this;

					var checkbox = event.currentTarget;
					var checked = checkbox.get('checked');
					var value = checkbox.val();
					var entryName = checkbox.attr('data-title');

					if (checked) {
						var matchKey = instance.get('matchKey');

						var entry = {
							categoryId: value
						};

						entry[matchKey] = entryName;

						instance.entries.add(entry);
					}
					else {
						instance.entries.removeKey(value);
					}
				},

				_renderToolset: function() {
					var instance = this;

					var contentBox = instance.get('contentBox');

					instance.toolset = new A.Toolbar(
						{
							children: [
								{
									icon: 'search',
									id: 'select',
									handler: {
										context: instance,
										fn: instance._showSelectPopup
									}
								}
							]
						}
					).render(contentBox);

					var toolsetBoundingBox = instance.toolset.get('boundingBox');

					instance.entryHolder.placeAfter(toolsetBoundingBox);
				},

				_showSelectPopup: function(event) {
					var instance = this;

					instance._showPopup(event);

					var popup = instance._popup;

					popup.set('title', Liferay.Language.get('categories'));

					instance._getEntries(
						function(entries) {
							instance._buffer = [];

							A.each(entries, instance._vocabulariesIterator, instance);

							popup.entriesNode.html(instance._buffer.join(''));

							popup.liveSearch.get('nodes').refresh();
							popup.liveSearch.refreshIndex();
						}
					);
				},

				_updateHiddenInput: function(event) {
					var instance = this;

					var hiddenInput = instance.get('hiddenInput');

					hiddenInput.val(instance.entries.keys.join());

					var popup = instance._popup;

					if (popup && popup.get('visible')) {
						var checkbox = popup.bodyNode.one('input[value=' + event.attrName + ']');

						if (checkbox) {
							var checked = false;

							if (event.type == 'dataset:add') {
								checked = true;
							}

							checkbox.set('checked', checked);
						}
					}
				},

				_updateSelectList: function(data, iterator, name) {
					var instance = this;

					var popup = instance._popup;

					popup.searchField.resetValue();

					var buffer = instance._buffer;

					instance._buffer.push('<fieldset class="' + (!data || !data.length ? CSS_NO_MATCHES : '') + '">');

					if (name) {
						buffer.push('<legend>');
						buffer.push(name);
						buffer.push('</legend>');
					}

					A.each(data, iterator, instance);

					var message = A.substitute(TPL_MESSAGE, [Liferay.Language.get('no-tags-found')]);

					buffer.push(message);
					buffer.push('</fieldset>');
				},

				_vocabulariesIterator: function(item, index, collection) {
					var instance = this;

					var vocabularyName = item.name;
					var vocabularyId = item.vocabularyId;

					if (item.groupId == themeDisplay.getCompanyGroupId()) {
						vocabularyName += ' (' + Liferay.Language.get('global') + ')';
					}

					var categories = Liferay.Service.Asset.AssetCategory.getVocabularyRootCategories(
						{
							assetVocabularyId: vocabularyId
						}
					);

					instance._updateSelectList(categories, instance._entriesIterator, vocabularyName);
				},

				_buffer: []
			}
		);

		Liferay.AssetCategoriesSelector = AssetCategoriesSelector;
	},
	'',
	{
		requires: ['liferay-tags-selector']
	}
);