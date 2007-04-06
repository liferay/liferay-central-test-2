Liferay.Portlet.TagsAdmin = new Class({
	initialize: function(params) {
		var instance = this;

		instance._searchFilters = {};

		instance.params = params;

		var addCategoryNameInput = jQuery('#' + params.addCategoryNameInput);
		var addEntryButton = jQuery('#' + params.addEntryButton);
		var addEntryNameInput = jQuery('#' + params.addEntryNameInput);
		var addPropertyButton = jQuery('#' + params.addPropertyButton);
		var addToCategorySpan = jQuery('#' + params.addToCategorySpan);
		var cancelEditEntryButton = jQuery('#' + params.cancelEditEntryButton);
		var deleteEntryButton = jQuery('#' + params.deleteEntryButton);
		var editEntryFields = jQuery('#' + params.editEntryFields);
		var form = jQuery('#' + params.form);
		var keywordsInput = jQuery('#' + params.keywordsInput);
		var updateEntryButton = jQuery('#' + params.updateEntryButton);

		// Show all entries

		instance._getEntries(instance);

		// Divs

		editEntryFields.css('display', 'none');

		// Form

		form.submit(
			function() {
				return false;
			}
		);

		// Inputs

		addCategoryNameInput.val('');

		addEntryNameInput.keypress(
			function(event) {
				if (event.keyCode == 13) {
					instance._addEntry(instance);
				}
			}
		);

		keywordsInput.keyup(
			function(event) {
				instance._searchEntries(instance);
			}
		);

		keywordsInput.val('');
		keywordsInput.focus();

		// Buttons

		addEntryButton.click(
			function() {
				instance._addEntry(instance);
			}
		);

		addPropertyButton.click(
			function() {
				var html = instance._addProperty(instance, '0', '', '');

				instance._addProperties(instance, html);
			}
		);

		cancelEditEntryButton.click(
			function() {
				editEntryFields.css('display', 'none');
			}
		);

		deleteEntryButton.click(
			function() {
				instance._deleteEntry(instance);
			}
		);

		updateEntryButton.click(
			function() {
				instance._updateEntry(instance);
			}
		);
	},

	editEntry: function(instance, entryId, name) {
		var params = instance.params;

		instance._entryId = entryId;

		var editEntryFields = jQuery('#' + params.editEntryFields);
		var editEntryNameInput = jQuery('#' + params.editEntryNameInput);
		var propertiesTable = jQuery('#' + params.propertiesTable);

		editEntryNameInput.val(name);

		propertiesTable.find('tr').gt(-1).remove();

		Liferay.Service.Tags.TagsProperty.getProperties(
			{
				entryId: entryId
			},
			function(properties) {
				instance._editProperties(instance, properties);
			}
		);

		editEntryFields.css('display', '');
	},

	_addEntry: function(instance) {
		var params = instance.params;

		var instanceVar = params.instanceVar;
		var addEntryNameInput = jQuery('#' + params.addEntryNameInput);

		var category = jQuery('#' + instanceVar + 'CategorySel').val();

		if (category == '[new]') {
			var addCategoryNameInput = jQuery('#' + params.addCategoryNameInput);

			category = addCategoryNameInput.val();
		}

		var properties = new Array('0:category:' + category);

		Liferay.Service.Tags.TagsEntry.addEntry(
			{
				name: addEntryNameInput.val(),
				properties: properties
			},
			function() {
				instance._getEntries(instance);
			}
		);

		instance._resetFields(instance);
	},

	_addProperties: function(instance, html) {
		var params = instance.params;

		var instanceVar = params.instanceVar;
		var propertiesTable = jQuery('#' + params.propertiesTable);

		propertiesTable.append(html);

		propertiesTable.find('tr').each(
			function(i, row) {
				jQuery('input[@name=' + instanceVar + 'deletePropertyButton]', row).click(
					instance._deleteProperty
				);
			}
		);
	},

	_addProperty: function(instance, propertyId, key, value) {
		var params = instance.params;

		var instanceVar = params.instanceVar;

		var html = '';

		html += '<tr><td>';
		html += '<input name="' + instanceVar + 'propertyId" type="hidden" value="' + propertyId + '" />\n';
		html += '<input';

		if (key == 'category') {
			html += ' disabled';
		}

		html += ' name="' + instanceVar + 'propertyKey" type="text" value="' + key + '" />\n';
		html += '<input name="' + instanceVar + 'propertyValue" type="text" value="' + value + '" />\n';

		if (key != 'category') {
			html += '<input name="' + instanceVar + 'deletePropertyButton" type="button" value="Delete" />\n';
		}

		html += '</td></tr>';

		return html;
	},

	_deleteEntry: function(instance) {
		var params = instance.params;

		var instanceVar = params.instanceVar;
		var editEntryFields = jQuery('#' + params.editEntryFields);

		Liferay.Service.Tags.TagsEntry.deleteEntry(
			{
				entryId: instance._entryId
			},
			function() {
				instance._getEntries(instance);
			}
		);

		editEntryFields.css('display', 'none');
	},

	_deleteProperty: function() {
		jQuery(this).parents('tr').eq(0).remove();
	},

	_displayEntries: function(instance, properties, keywords) {
		var params = instance.params;

		var instanceVar = params.instanceVar;
		var searchResultsDiv = jQuery('#' + params.searchResultsDiv);

		var html = '<br />';

		jQuery.each(
			properties,
			function(i,category) {
				if (instance._searchFilters['Category'] == null || 
					instance._searchFilters['Category'] == 'All' || 
					instance._searchFilters['Category'] == category.value) {

					Liferay.Service.Tags.TagsEntry.search(
						{
							companyId: themeDisplay.getCompanyId(),
							name: keywords,
							properties: 'category:' + category.value
						},
						function(entries) {
							if (category.value != '') {
								html += '<div style="clear: both;"></div><b>' + category.value + '</b><br />';
							}

							html += '<div style="float: left; width: 25%;">';

							jQuery.each(
								entries,
								function(i, entry) {
									var hrefJS = instanceVar + '.editEntry(' + instanceVar + ', ' + entry.entryId + ', \'' + encodeURIComponent(entry.name) + '\');';

									var numEntries = entries.length;

									var modulo = i % (Math.floor(numEntries / 4));

									if (modulo == 0) {
										html += '</div><div style="float: left; width: 25%;">';
									}

									html += '<a href="javascript: ' + hrefJS + '">' + entry.name + '</a>';

									if ((i + 1) < entries.length) {
										html += '<br />';
									}

								}
							);

							html += '</div>';

							if (entries.length == 0) {
								html += 'no-tags-found';
							}

							searchResultsDiv.html(html);
						}
					);
				}
			}
		);
	},

	_displayFilters: function(instance, propertyKey, properties) {
		var params = instance.params;

		var instanceVar = params.instanceVar;
		var searchPropertiesSpan = jQuery('#' + params.searchPropertiesSpan);
		var addToCategorySpan = jQuery('#' + params.addToCategorySpan);

		var filterHtml = '';

		jQuery.each(
			properties,
			function(i, property) {
				filterHtml += '<option value="' + property.value + '">' + property.value + '</option>';
			}
		);

		var selectHtml = filterHtml;

		if (properties.length > 0) {
			filterHtml = '<select id="' + instanceVar + propertyKey + 'FilterSel"><option>All</option>' + filterHtml + '</select>';
			selectHtml = '<select id="' + instanceVar + propertyKey + 'Sel"><option value="[new]">(New)</option>' + selectHtml + '</select>';

			searchPropertiesSpan.append('<span style="padding: 0px 5px 0px 10px;">' + propertyKey + '</span>');
			searchPropertiesSpan.append(filterHtml);
			addToCategorySpan.append('<span style="padding: 0px 5px 0px 10px;">' + selectHtml + '</span>');

			var filterSel = jQuery('#' + instanceVar + propertyKey + 'FilterSel');

			filterSel.change(
				function() {
					instance._searchFilters[propertyKey] = this.value;

					instance._searchEntries(instance);
				}
			);

			var categorySel = jQuery('#' + instanceVar + 'CategorySel');

			categorySel.change(
				function() {
					var addCategoryNameInput = jQuery('#' + params.addCategoryNameInput);

					if (this.value == '[new]') {
						addCategoryNameInput.css('display', '')
					}
					else {
						addCategoryNameInput.css('display', 'none')
					}

					instance._searchFilters[propertyKey] = this.value;
				}
			);
		}
	},

	_editProperties: function(instance, properties) {
		var html = '';

		jQuery.each(
			properties,
			function(i, property) {
				html += instance._addProperty(instance, property.propertyId, property.key, property.value);
			}
		);

		if (properties.length == 0) {
			html += instance._addProperty('0', '', '');
		}

		instance._addProperties(instance, html);
	},

	_getEntries: function(instance) {
		instance._resetFields(instance);

		Liferay.Service.Tags.TagsProperty.getPropertyValues(
			{
				companyId: themeDisplay.getCompanyId(),
				key: "category"
			},
			function(properties) {
				instance._displayEntries(instance, properties, '%');
			}
		);

		instance._getFilters(instance);
	},

	_getFilters: function(instance) {
		var params = instance.params;

		var instanceVar = params.instanceVar;

		var searchPropertiesSpan = jQuery('#' + params.searchPropertiesSpan);
		var addToCategorySpan = jQuery('#' + params.addToCategorySpan);

		var propertyKeys = new Array('Category');

		if (propertyKeys.length > 0) {
			searchPropertiesSpan.html('Filter By: ');
			addToCategorySpan.html('Add To: ');
		}

		jQuery.each(
			propertyKeys,
			function(i, propertyKey) {
				Liferay.Service.Tags.TagsProperty.getPropertyValues(
					{
						companyId: themeDisplay.getCompanyId(),
						key: propertyKey
					},
					function(properties) {
						instance._displayFilters(instance, propertyKey, properties);
					}
				);
			}
		);
	},

	_resetFields: function(instance) {
		var params = instance.params;

		var addCategoryNameInput = jQuery('#' + params.addCategoryNameInput);
		var addEntryNameInput = jQuery('#' + params.addEntryNameInput);
		var keywordsInput = jQuery('#' + params.keywordsInput);

		addCategoryNameInput.val('');
		addEntryNameInput.val('');
		keywordsInput.val('');
	},

	_searchEntries: function(instance) {
		var params = instance.params;

		var keywordsInput = jQuery('#' + params.keywordsInput);
		var keywords = '%' + keywordsInput.val() + '%';

		var searchResultsDiv = jQuery('#' + params.searchResultsDiv);

		searchResultsDiv.html('');

		Liferay.Service.Tags.TagsProperty.getPropertyValues(
			{
				companyId: themeDisplay.getCompanyId(),
				key: "category"
			},
			function(properties) {
				instance._displayEntries(instance, properties, keywords);
			}
		);
	},

	_updateEntry: function(instance) {
		var params = instance.params;

		var instanceVar = params.instanceVar;
		var editEntryNameInput = jQuery('#' + params.editEntryNameInput);
		var editEntryFields = jQuery('#' + params.editEntryFields);
		var propertiesTable = jQuery('#' + params.propertiesTable);

		var properties = '';

		var rows = propertiesTable.find('tr');

		rows.each(
			function(i, row) {
				var propertyId = jQuery('input[@name=' + instanceVar + 'propertyId]', row).val();
				var propertyKey = jQuery('input[@name=' + instanceVar + 'propertyKey]', row).val();
				var propertyValue = jQuery('input[@name=' + instanceVar + 'propertyValue]', row).val();

				properties += propertyId + ':' + propertyKey + ':' + propertyValue;

				if ((i + 1) < rows.length) {
					properties += ',';
				}
			}
		);

		Liferay.Service.Tags.TagsEntry.updateEntry(
			{
				entryId: instance._entryId,
				name: editEntryNameInput.val(),
				properties: properties
			},
			function() {
				instance._getEntries(instance);
			}
		);

		editEntryFields.css('display', 'none');
	}
});