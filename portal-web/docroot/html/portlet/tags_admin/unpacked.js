Liferay.Portlet.TagsAdmin = new Class({
	initialize: function(params) {
		var instance = this;

		instance._searchFilters = {};

		instance.params = params;

		var addEntryButton = jQuery('#' + params.addEntryButton);
		var addEntryNameInput = jQuery('#' + params.addEntryNameInput);
		var addPropertyButton = jQuery('#' + params.addPropertyButton);
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

		var addEntryNameInput = jQuery('#' + params.addEntryNameInput);

		Liferay.Service.Tags.TagsEntry.addEntry(
			{
				name: addEntryNameInput.val()
			},
			function() {
				instance._getEntries(instance);
			}
		);

		addEntryNameInput.val('');
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
		html += '<input class="form-text" name="' + instanceVar + 'propertyKey" type="text" value="' + key + '" />\n';
		html += '<input class="form-text" name="' + instanceVar + 'propertyValue" type="text" value="' + value + '" />\n';
		html += '<input class="portlet-form-button" name="' + instanceVar + 'deletePropertyButton" type="button" value="Delete" />\n';
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

	_displayEntries: function(instance, entries) {
		var params = instance.params;

		var instanceVar = params.instanceVar;
		var searchResultsDiv = jQuery('#' + params.searchResultsDiv);

		var html = '<br />';

		jQuery.each(
			entries,
			function(i, entry) {
				var hrefJS = instanceVar + '.editEntry(' + instanceVar + ', ' + entry.entryId + ', \'' + encodeURIComponent(entry.name) + '\');';

				html += '<a href="javascript: ' + hrefJS + '">' + entry.name + '</a>';

				if ((i + 1) < entries.length) {
					html += ', ';
				}
			}
		);

		if (entries.length == 0) {
			html += 'no-tags-found';
		}

		searchResultsDiv.html(html);
	},

	_displayFilters: function(instance, propertyKey, properties) {
		var params = instance.params;

		var instanceVar = params.instanceVar;
		var searchPropertiesSpan = jQuery('#' + params.searchPropertiesSpan);

		var html = '';

		jQuery.each(
			properties,
			function(i, property) {
				html += '<option value="' + property.value + '">' + property.value + '</option>';
			}
		);

		if (properties.length > 0) {
			html = '<select id="' + instanceVar + propertyKey + 'FilterSel"><option></option>' + html + '</select>';

			searchPropertiesSpan.append('<span style="padding: 0px 5px 0px 10px;">' + propertyKey + '</span>');
			searchPropertiesSpan.append(html);

			var filterSel = jQuery('#' + instanceVar + propertyKey + 'FilterSel');

			filterSel.change(
				function() {
					instance._searchFilters[propertyKey] = this.value;

					instance._searchEntries(instance);
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
		Liferay.Service.Tags.TagsEntry.search(
			{
				companyId: themeDisplay.getCompanyId(),
				name: '%',
				properties: ''
			},
			function(entries) {
				instance._displayEntries(instance, entries);
			}
		);

		instance._getFilters(instance);
	},

	_getFilters: function(instance) {
		var params = instance.params;

		var instanceVar = params.instanceVar;
		var searchPropertiesSpan = jQuery('#' + params.searchPropertiesSpan);

		var propertyKeys = new Array('Category', 'Country');

		if (propertyKeys.length > 0) {
			searchPropertiesSpan.html('Filter By: ');
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

	_searchEntries: function(instance) {
		var params = instance.params;

		var keywordsInput = jQuery('#' + params.keywordsInput);

		var keywords = '%' + keywordsInput.val() + '%';

		var properties = '';

		jQuery.each(
			instance._searchFilters,
			function(propertyKey, propertyValue) {

				if (propertyValue != '') {
					if (properties != '') {
						properties += ',';
					}

					properties += propertyKey + ':' + propertyValue;
				}
			}
		);

		Liferay.Service.Tags.TagsEntry.search(
			{
				companyId: themeDisplay.getCompanyId(),
				name: keywords,
				properties: properties
			},
			function(entries) {
				instance._displayEntries(instance, entries);
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