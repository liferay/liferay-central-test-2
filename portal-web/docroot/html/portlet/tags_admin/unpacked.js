Liferay.Portlet.TagsAdmin = new Class({
	initialize: function(params) {
		var instance = this;

		//Liferay.ServiceAuth.setHeader("2", "test");

		instance._categoriesCount = 0;
		instance._entriesInCurCategoryCount = 0;
		instance._searchFilters = {};

		instance.params = params;

		var addCategoryNameInput = jQuery('#' + params.addCategoryNameInput);
		var addEntryButton = jQuery('#' + params.addEntryButton);
		var addEntryNameInput = jQuery('#' + params.addEntryNameInput);
		var addPropertyButton = jQuery('#' + params.addPropertyButton);
		var addVocabularyButton = jQuery('#' + params.addVocabularyButton);
		var addVocabularyNameInput = jQuery('#' + params.addVocabularyNameInput);
		var cancelEditEntryButton = jQuery('#' + params.cancelEditEntryButton);
		var cancelEditVocabularyButton = jQuery('#' + params.cancelEditVocabularyButton);
		var deleteEntryButton = jQuery('#' + params.deleteEntryButton);
		var deleteVocabularyButton = jQuery('#' + params.deleteVocabularyButton);
		var editEntryFields = jQuery('#' + params.editEntryFields);
		var editEntryNameInput = jQuery('#' + params.editEntryNameInput);
		var editVocabularyFields = jQuery('#' + params.editVocabularyFields);
		var form = jQuery('#' + params.form);
		var keywordsInput = jQuery('#' + params.keywordsInput);
		var updateEntryButton = jQuery('#' + params.updateEntryButton);
		var updateVocabularyButton = jQuery('#' + params.updateVocabularyButton);

		instance._form = form;

		// Show all entries

		instance._getEntries(instance);

		// Show all vocabularies

		instance._getVocabularies(instance);

		// Divs

		editEntryFields.hide();
		editVocabularyFields.hide();

		// Form

		form.submit(
			function() {
				return false;
			}
		);

		// Inputs

		addCategoryNameInput.val('');

		addCategoryNameInput.keypress(
			function(event) {
				if (event.keyCode == 13) {
					instance._addEntry(instance);
				}
			}
		);

		addEntryNameInput.keypress(
			function(event) {
				if (event.keyCode == 13) {
					instance._addEntry(instance);
				}
			}
		);

		addVocabularyNameInput.keypress(
			function(event) {
				if (event.keyCode == 13) {
					instance._addVocabulary(instance);
				}
			}			
		);

		editEntryNameInput.keypress(
			function(event) {
				if (event.keyCode == 13) {
					instance._updateEntry(instance);
				}
			}
		);

		keywordsInput.keyup(
			function(event) {

				// shift, ctrl, alt (option), end, home, left/up/right/down arrows

				if ([16, 17, 18, 35, 36, 37, 38, 39, 40].indexOf(event.which) == -1) {
					instance._searchEntries(instance);
				}
			}
		);

		keywordsInput.val('');
		//keywordsInput.focus();

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

		addVocabularyButton.click(
			function() {
				instance._addVocabulary(instance);
			}
		);

		cancelEditEntryButton.click(
			function() {
				editEntryFields.hide();
			}
		);

		cancelEditVocabularyButton.click(
			function() {
				editVocabularyFields.hide();
			}
		);
		

		deleteEntryButton.click(
			function() {
				instance._deleteEntry(instance, instance._entryId);
			}
		);

		deleteVocabularyButton.click(
			function() {
				instance._deleteVocabulary(instance, instance._vocabularyId);
			}
		);
		

		updateEntryButton.click(
			function() {
				instance._updateEntry(instance);
			}
		);
		
		updateVocabularyButton.click(
			function() {
				instance._updateVocabulary(instance);
			}
		);
		
		
	},

	deleteEntry: function(instance, entryId) {
		instance._deleteEntry(instance, entryId);
	},

	deleteVocabulary: function(instance, vocabularyId) {
		instance._deleteVocabulary(instance, vocabularyId);
	},

	editEntry: function(instance, entryId, name, vocabularyId, parentId) {
		var params = instance.params;

		instance._entryId = entryId;

		var editEntryFields = jQuery('#' + params.editEntryFields);
		var editEntryNameInput = jQuery('#' + params.editEntryNameInput);
		var editEntryVocabularyInput = jQuery('#' + params.editEntryVocabularyInput);
		var editEntryParentInput = jQuery('#' + params.editEntryParentInput);
		var editEntryParentDiv = jQuery('#' + params.editEntryParentDiv);
		var propertiesTable = jQuery('#' + params.propertiesTable);

		editEntryNameInput.val(name);

		editEntryParentInput.val('');
		editEntryVocabularyInput.val('');
		
		if (vocabularyId != '') {
			Liferay.Service.Tags.Vocabulary.getVocabulary(
				{
					vocabularyId: vocabularyId
				},
				function(vocabulary) {
					editEntryVocabularyInput.val(vocabulary.name);

					if (vocabulary.folksonomy) {
						editEntryParentDiv.hide();
					}
					else {
						editEntryParentDiv.show();
					}
				}
			);
		}

		if (parentId != '') {
			Liferay.Service.Tags.TagsEntry.getEntry(
				{
					entryId: parentId
				},
				function(parent) {
					editEntryParentInput.val(parent.name);
				}
			);
		}
		
		propertiesTable.find('tr').slice(0).remove();

		Liferay.Service.Tags.TagsProperty.getProperties(
			{
				entryId: entryId
			},
			function(properties) {
				instance._editProperties(instance, properties);
			}
		);

		editEntryFields.show();
	},

	editVocabulary: function(instance, vocabularyId, name, folksonomy) {
		var params = instance.params;

		instance._vocabularyId = vocabularyId;

		var editVocabularyFields = jQuery('#' + params.editVocabularyFields);
		var editVocabularyNameInput = jQuery('#' + params.editVocabularyNameInput);
		var editVocabularyFolksonomyCheck = jQuery('#' + params.editVocabularyFolksonomyCheck);

		editVocabularyNameInput.val(name);
		
		if (folksonomy) { 
			editVocabularyFolksonomyCheck.attr('checked', true);
		}
		else {
			editVocabularyFolksonomyCheck.attr('checked', false);
		}
		
		setTimeout(
			function() {
				instance._displayVocabularyEntries(instance, name);				
			},
			500
		);
				
		editVocabularyFields.show();
	},

	_addEntry: function(instance) {
		var params = instance.params;

		var instanceVar = params.instanceVar;
		var addEntryNameInput = jQuery('#' + params.addEntryNameInput);
		var addEntryVocabularyInput = jQuery('#' + params.addEntryVocabularyInput);

		var categorySel = jQuery('#' + instanceVar + 'categorySel');
		var filterSel = jQuery('#' + instanceVar + 'CategoryFilterSel');
		var category = categorySel.val();

		if (category == '[new]') {
			var addCategoryNameInput = jQuery('#' + params.addCategoryNameInput);

			category = jQuery.trim(addCategoryNameInput.val());

			if (category) {
				instance._searchFilters['category'] = category;

				addCategoryNameInput.hide();
			}
			else {
				category = 'no category';
			}
		}

		var properties = new Array('0:category:' + category);

		if (category == '[none]') {
			properties = null;
		}

		Liferay.Service.Tags.TagsEntry.addEntry(
			{
				name: addEntryNameInput.val(),
				vocabulary: addEntryVocabularyInput.val(),
				properties: properties
			},
			function(json) {
				if (!json.exception) {
					instance._getEntries(instance);
				}
				else {
					if (json.exception.indexOf('com.liferay.portlet.tags.DuplicateEntryException') > -1) {
						instance._sendMessage('error', 'that-tag-already-exists');
					}
					else if (json.exception.indexOf('com.liferay.portlet.tags.EntryNameException') > -1) {
						instance._sendMessage('error', 'one-of-your-fields-contain-invalid-characters');
					}
					else if (json.exception.indexOf('com.liferay.portlet.tags.NoSuchVocabularyException') > -1) {
						instance._sendMessage('error', 'that-vocabulary-does-not-exists');
					}

					jQuery('#' + params.addCategoryNameInput).show();
				}
			}
		);

		instance._resetFields(instance);
		instance._displayVocabularyEntries(instance, addEntryVocabularyInput.val());

		addEntryNameInput.focus();
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

				jQuery('input[@name=' + instanceVar + 'propertyValue]', row).keypress(
					function(event) {
						if (event.keyCode == 13) {
							instance._updateEntry(instance);
						}
					}
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

		html += ' name="' + instanceVar + 'propertyKey" size="10" type="text" value="' + key + '" />\n';
		html += '<input name="' + instanceVar + 'propertyValue" size="10" type="text" value="' + value + '" />\n';

		if (key != 'category') {
			html += '<input name="' + instanceVar + 'deletePropertyButton" type="button" value="Delete" />\n';
		}

		html += '</td></tr>';

		return html;
	},

	_addVocabulary: function(instance) {
		var params = instance.params;

		var instanceVar = params.instanceVar;
		var addVocabularyFolksonomyCheck = jQuery('#' + params.addVocabularyFolksonomyCheck);
		var addVocabularyNameInput = jQuery('#' + params.addVocabularyNameInput);
		
		Liferay.Service.Tags.Vocabulary.addVocabulary(
			{
				name: addVocabularyNameInput.val(),
				folksonomy: addVocabularyFolksonomyCheck.attr('checked')
			},
			function(json) {
				if (!json.exception) {
					instance._getVocabularies(instance);
				}
				else {
					if (json.exception.indexOf('com.liferay.portlet.tags.DuplicateVocabularyException') > -1) {
						instance._sendMessage('error', 'that-vocabulary-already-exists');
					}
					else if (json.exception.indexOf('com.liferay.portlet.tags.VocabularyNameException') > -1) {
						instance._sendMessage('error', 'one-of-your-fields-contain-invalid-characters');
					}
					else if (json.exception.indexOf('com.liferay.portlet.tags.NoSuchVocabularyException') > -1) {
						instance._sendMessage('error', 'that-parent-vocabulary-does-not-exist');
					}
					
				}
			}
		);

		instance._resetFields(instance);

		addVocabularyNameInput.focus();
	},

	_deleteEntry: function(instance, entryId) {
		var params = instance.params;

		if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this-tag'))) {
			var instanceVar = params.instanceVar;
			var editEntryFields = jQuery('#' + params.editEntryFields);
			var editVocabularyFields = jQuery('#' + params.editVocabularyFields);

			Liferay.Service.Tags.TagsEntry.deleteEntry(
				{
					entryId: entryId
				},
				function() {
					if (((instance._searchFilters['category'] != 'all') || (instance._categoriesCount == 1)) &&
						 (instance._entriesInCurCategoryCount == 1)) {

						instance._searchFilters['category'] = 'all';

						jQuery('#' + params.addCategoryNameInput).show();
					}

					instance._getEntries(instance);
				}
			);

			editEntryFields.hide();
			editVocabularyFields.hide();
		}
	},

	_deleteProperty: function() {
		jQuery(this).parents('tr').eq(0).remove();
	},

	_deleteVocabulary: function(instance, vocabularyId) {
		var params = instance.params;

		if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this-vocabulary-and-its-tags'))) {
			var instanceVar = params.instanceVar;
			var editVocabularyFields = jQuery('#' + params.editVocabularyFields);

			Liferay.Service.Tags.Vocabulary.deleteVocabulary(
				{
					vocabularyId: vocabularyId
				}
			);
			instance._getVocabularies(instance);
			editVocabularyFields.hide();
		}
	},

	_displayEntries: function(instance, properties, keywords) {
		var params = instance.params;

		var instanceVar = params.instanceVar;
		var searchResultsDiv = jQuery('#' + params.searchResultsDiv);
		var mergeConfirmation = Liferay.Language.get('are-you-sure-you-want-to-merge-x-into-x', ['{SOURCE}', '{DESTINATION}']);

		var html = '<br />';

		instance._categoriesCount = properties.length;

		searchResultsDiv.html('');

		jQuery.each(
			properties,
			function(i,category) {
				if (instance._searchFilters['category'] == null ||
					instance._searchFilters['category'] == 'all' ||
					instance._searchFilters['category'] == category.value) {

					Liferay.Service.Tags.TagsEntry.search(
						{
							companyId: themeDisplay.getCompanyId(),
							name: keywords,
							properties: 'category:' + category.value
						},
						function(entries) {
							if (category.value != '') {
								html += '<div class="tags-category"><b>' + category.value + '</b></div>';
							}

							html += '<div class="tags-container">';

							jQuery.each(
								entries,
								function(i, entry) {
									var hrefJS = instanceVar + '.editEntry(' + instanceVar + ', ' + entry.entryId + ', \'' + encodeURIComponent(entry.name) + '\');';

									var numEntries = entries.length;

									html += '<span class="ui-tag">';
									html += ' <a class="tag-name" href="javascript: ' + hrefJS + '" tagId="' + entry.entryId + '">' + entry.name + '</a>';
									html += ' <a class="ui-tag-delete" href="javascript: ' + instanceVar + '.deleteEntry(' + instanceVar + ', ' + entry.entryId + ')"><span>x</span></a>';
									html += '</span>';
								}
							);

							html += '</div>';

							if (entries.length == 0) {
								html += Liferay.Language.get('no-tags-found');
							}

							searchResultsDiv.html(html);

							var tags = searchResultsDiv.find('.ui-tag');

							tags.draggable(
								{
									ghosting: false,
									opacity: 0.7,
									revert: true,
									zIndex: 1000,
									start: function(event, ui) {
										searchResultsDiv.addClass('dragging');
									},
									stop: function(event, ui) {
										searchResultsDiv.removeClass('dragging');
									}
								}
							);

							tags.droppable(
								{
									accept : '.ui-tag',
									hoverClass:	'active-area',
									tolerance: 'pointer',
									drop: function (event, ui) {
										var draggable = ui.draggable;
										var droppable = jQuery(this);

										var from = draggable.find('a.tag-name');
										var to = droppable.find('a.tag-name');

										var fromId = from.attr('tagId');
										var toId = to.attr('tagId');

										var tagText = {
											SOURCE: from.text(),
											DESTINATION: to.text()
										};

										var mergeText = mergeConfirmation.replace(
											/\{(SOURCE|DESTINATION)\}/gm,
											function(completeMatch, match, index, str) {
												return tagText[match];
											}
										);

										if (confirm(mergeText)) {
											jQuery.data(draggable[0], 'draggable').options.revert = false;

											Liferay.Service.Tags.TagsEntry.mergeEntries(
												{
													fromEntryId: fromId,
													toEntryId: toId
												},
												function() {
													draggable.remove();
													jQuery('.ui-tag[@tagId=' + fromId + ']').remove();
												}
											);
										}
									}
								}
							);
						}
					);
				}
			}
		);
	},

	_displayVocabularyEntries: function(instance, vocabulary) {
		var params = instance.params;

		var instanceVar = params.instanceVar;
		var vocabularyTagsDiv = jQuery('#' + params.vocabularyTagsDiv);

		var html = '<br />';

		Liferay.Service.Tags.TagsEntry.getVocabularyEntries(
			{
				companyId: themeDisplay.getCompanyId(),
				name: vocabulary
			},
			function(entries) {
				html += '';

				jQuery.each(
					entries,
					function(i, entry) {
						var hrefJS = instanceVar + '.editEntry(' + instanceVar + ', ' + entry.entryId + ', \'';
						hrefJS += encodeURIComponent(entry.name) + '\', \'' + entry.vocabularyId + '\',\'';
						hrefJS += entry.parentEntryId + '\'';
						hrefJS +=');';

						var numEntries = entries.length;

						html += '<span class="ui-tag">';
						html += ' <a class="tag-name" href="javascript: ' + hrefJS + '" tagId="' + entry.entryId + '">' + entry.name + ' ('+entry.parentEntryId+')'+ '</a>';
						html += ' <a class="ui-tag-delete" href="javascript: ' + instanceVar + '.deleteEntry(' + instanceVar + ', ' + entry.entryId + ')"><span>x</span></a>';
						html += '</span>';
					}
				);

				html += '</div>';

				if (entries.length == 0) {
					html += Liferay.Language.get('no-vocabulary-tags-found');
				}

				vocabularyTagsDiv.html(html);
			}
		);
	},

	_displayFilters: function(instance, propertyKey, properties) {
		var params = instance.params;

		var instanceVar = params.instanceVar;
		var searchPropertiesSpan = jQuery('#' + params.searchPropertiesSpan);
		var addToCategorySpan = jQuery('#' + params.addToCategorySpan);

		var filterHtml = '';
		var selectHtml = '';

		jQuery.each(
			properties,
			function(i, property) {
				var selected = '';

				if (property.value == instance._searchFilters['category']) {
					selected = ' selected';
				}

				var html = '<option value="' + property.value + '"' + selected + '>' + property.value + '</option>';

				filterHtml += html;

				if (property.value != 'no category') {
					selectHtml += html;
				}
			}
		);

		filterHtml = '<select id="' + instanceVar + propertyKey + 'FilterSel"><option>all</option>' + filterHtml + '</select>';

		var selected = '';

		if (instance._searchFilters['category'] == 'no category') {
			selected = 'selected';
		}

		selectHtml = '<select id="' + instanceVar + propertyKey + 'Sel"><option value="[new]">(New)</option><option value="no category"' + selected + '>(None)</option>' + selectHtml + '</select>';

		searchPropertiesSpan.append('<span style="padding: 0px 5px 0px 10px;">' + propertyKey + '</span>');
		searchPropertiesSpan.append(filterHtml);

		addToCategorySpan.append('<span style="padding: 0px 5px 0px 10px;">' + selectHtml + '</span>');

		var addCategoryNameInput = jQuery('#' + params.addCategoryNameInput);
		var filterSel = jQuery('#' + instanceVar + propertyKey + 'FilterSel');
		var categorySel = jQuery('#' + instanceVar + 'categorySel');

		filterSel.change(
			function() {
				instance._searchFilters[propertyKey] = this.value;

				if (this.value == 'all') {
					categorySel.val('[new]');
					addCategoryNameInput.show();
				}
				else {
					categorySel.val(this.value);
					addCategoryNameInput.hide();
				}

				instance._searchEntries(instance);
			}
		);

		categorySel.change(
			function() {
				instance._searchFilters[propertyKey] = this.value;

				if (this.value == '[new]') {
					filterSel.val('[all]');
					addCategoryNameInput.show();
				}
				else {
					filterSel.val(this.value);
					addCategoryNameInput.hide();
				}

				instance._searchEntries(instance);
			}
		);
	},

	_displayVocabularies: function(instance) {
		var params = instance.params;

		var instanceVar = params.instanceVar;
		var listVocabulariesDiv = jQuery('#' + params.listVocabulariesDiv);

		var html = '<br />';

		Liferay.Service.Tags.Vocabulary.search(
			{
				companyId: themeDisplay.getCompanyId()
			},
			function(vocabularies) {
				html += '';

				jQuery.each(
					vocabularies,
					function(i, vocabulary) {
						var hrefJS = instanceVar + '.editVocabulary(' + instanceVar + ', ' + vocabulary.vocabularyId + ', \'' + encodeURIComponent(vocabulary.name) +  '\', ' + vocabulary.folksonomy + ');';

						var numVocabularies = vocabularies.length;

						html += '<span class="ui-tag">';
						html += ' <a class="tag-name" href="javascript: ' + hrefJS + '" vocabularyId="' + vocabulary.vocabularyId + '">' + vocabulary.name + '</a>';
						html += ' <a class="ui-tag-delete" href="javascript: ' + instanceVar + '.deleteVocabulary(' + instanceVar + ', ' + vocabulary.vocabularyId + ')"><span>x</span></a>';
						html += '</span>';
					}
				);


				if (vocabularies.length == 0) {
					html += Liferay.Language.get('no-vocabularies-found');
				}

				listVocabulariesDiv.html(html);

			}
		);
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

		var propertyKeys = new Array('category');

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

	_getVocabularies: function(instance) {
		instance._resetFields(instance);

		instance._displayVocabularies(instance,'%');
	},

	_resetFields: function(instance) {
		var params = instance.params;

		var addCategoryNameInput = jQuery('#' + params.addCategoryNameInput);
		var addEntryNameInput = jQuery('#' + params.addEntryNameInput);
		var addVocabularyNameInput = jQuery('#' + params.addVocabularyNameInput);
		var keywordsInput = jQuery('#' + params.keywordsInput);
		var addVocabularyFolksonomyCheck = jQuery('#' + params.editVocabularyFolksonomyCheck);
		var editVocabularyFolksonomyCheck = jQuery('#' + params.editVocabularyFolksonomyCheck);

		addCategoryNameInput.val('');
		addEntryNameInput.val('');
		addVocabularyNameInput.val('');
		addVocabularyFolksonomyCheck.attr('checked', false);
		editVocabularyFolksonomyCheck.attr('checked', false);
		keywordsInput.val('');
	},

	_searchEntries: function(instance) {
		var params = instance.params;

		if (instance.showTimer) {
			clearTimeout(instance.showTimer);
			instance.showTimer = 0;
		}

		instance.showTimer = setTimeout(
			function() {
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
			250
		);
	},

	_searchVocabularies: function(instance) {
		var params = instance.params;

		if (instance.showTimerV) {
			clearTimeout(instance.showTimerV);
			instance.showTimerV = 0;
		}

		instance.showTimerV = setTimeout(
			function() {
				instance._displayVocabularies(instance, keywords);				
			},
			250
		);
	},

	_sendMessage: function(type, key) {
		var instance = this;

		var msgType = 'portlet-msg-error';

		if (type == 'success') {
			msgType = 'portlet-msg-success';
		}

		var message = Liferay.Language.get(key);

		var currentMsg = jQuery('.lfr-message-response');

		if (currentMsg.length) {
			currentMsg.removeClass('portlet-msg-success').removeClass('portlet-msg-error');
			currentMsg.addClass(msgType);
			currentMsg.fadeIn('fast');
		}
		else {
			currentMsg = jQuery('<div class="' + msgType + ' lfr-message-response">' + message + '</div>');

			instance._form.prepend(currentMsg);
		}

		var fadeOutTimeout = setTimeout(
			function() {
				currentMsg.fadeOut('slow');
				clearTimeout(fadeOutTimeout);
			},
			7000
		);
	},

	_updateEntry: function(instance) {
		var params = instance.params;

		var instanceVar = params.instanceVar;
		var editEntryNameInput = jQuery('#' + params.editEntryNameInput);
		var editEntryFields = jQuery('#' + params.editEntryFields);
		var editEntryParentInput = jQuery('#' + params.editEntryParentInput);
		var editEntryVocabularyInput = jQuery('#' + params.editEntryVocabularyInput);
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
				parentCategoryName: editEntryParentInput.val(),
				properties: properties,
				vocabularyName: editEntryVocabularyInput.val()
			},
			function(json) {
				if (!json.exception) {
					instance._displayVocabularyEntries(instance, editEntryVocabularyInput.val());					
					editEntryFields.hide();
				}
				else {
					if (json.exception.indexOf('com.liferay.portlet.tags.NoSuchVocabularyException') > -1) {
						instance._sendMessage('error', 'that-vocabulary-does-not-exist');
					}					
					else if (json.exception.indexOf('com.liferay.portlet.tags.NoSuchEntryException') > -1) {
						instance._sendMessage('error', 'that-parent-category-does-not-exist');
					}					
					else if (json.exception.indexOf('Exception') > -1) {
						instance._sendMessage('error', 'one-of-your-fields-contain-invalid-characters');
					}
				}
			}
		);
	},
	
	_updateVocabulary: function(instance) {
		var params = instance.params;

		var instanceVar = params.instanceVar;
		var editVocabularyNameInput = jQuery('#' + params.editVocabularyNameInput);
		var editVocabularyFields = jQuery('#' + params.editVocabularyFields);
		var editVocabularyFolksonomyCheck = jQuery('#' + params.editVocabularyFolksonomyCheck);

		Liferay.Service.Tags.Vocabulary.updateVocabulary(
			{
				vocabularyId: instance._vocabularyId,
				name: editVocabularyNameInput.val(),
				folksonomy: editVocabularyFolksonomyCheck.attr('checked')
			},
			function(json) {
				if (!json.exception) {
					instance._getVocabularies(instance);
				}
				else {
					if (json.exception.indexOf('Exception') > -1) {
						instance._sendMessage('error', 'one-of-your-fields-contain-invalid-characters');
					}
				}
			}
		);

		editVocabularyFields.hide();
	}
});