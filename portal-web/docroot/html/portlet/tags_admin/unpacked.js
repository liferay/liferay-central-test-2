Liferay.Portlet.TagsAdmin = new Class({
	
	VOCABULARY_SELECTED: "tag",
	
	EXP_ENTRY_SCOPE: ".ui-tags-vocabulary-entries",
	EXP_VOCABULARY_SCOPE: ".ui-tags-vocabulary-list",
	EXP_ENTRY_LIST: ".ui-tags-vocabulary-entries li",
	EXP_VOCABULARY_LIST: ".ui-tags-vocabulary-list li",
	
	selectedVocabularyId: null,
	selectedVocabularyName: null,
	selectedEntryName: null,
	selectedEntryId: null,
	messageTimeout: null,
	
	initialize: function(params) {
		var instance = this;
		
		/* Vocabulary edit section */
		var childrenContainer = jQuery(instance.EXP_ENTRY_SCOPE);
		
		jQuery('.ui-tags-close').click(function() {
			instance._unselectAllEntries();
			instance._closeEditSection();
		});
		
		jQuery('.ui-tags-save-properties').click(function() {
			instance._saveProperties();
		});
				
		/* Buttons Tags sets/Categories */
		var buttons = jQuery('.ui-tags-buttons'), toolbar = jQuery('.ui-tags-toolbar');
		
		var changeAddLabel = function(label) {
			toolbar.find('.ui-tags-label').html(label);
		},
		selectButton = function(button) {
			buttons.find('.button').removeClass('selected');
			jQuery(button).addClass('selected');
		};
		buttons.find('.tags-sets').click(function() {
			instance.VOCABULARY_SELECTED = 'tag';
			changeAddLabel('Add tag');
			selectButton(this);
			instance._loadData();
		});
		buttons.find('.categories').click(function() {
			instance.VOCABULARY_SELECTED = 'category';
			changeAddLabel('Add category');
			selectButton(this);
			instance._loadData();
		});
		
		jQuery('select.ui-tags-select-list').change(function() {
			var actionScope = jQuery('.ui-tags-actions'), 
				vocabularyId = jQuery(this).val(), 
				vocabularyName = jQuery(this).find('option:selected').text(),
				inputVocabularyName = actionScope.find('.ui-tags-vocabulary-name');
			
			if (vocabularyName == "(new)") {
				inputVocabularyName.show().focus();
			}else{
				instance._resetActionValues();
			}
		});
		
		var addEntry = function() {
			var actionScope = jQuery('.ui-tags-actions'), 
				entryName = actionScope.find('.ui-tags-entry-name').val(),
				vocabularyId = actionScope.find('.ui-tags-select-list').val(),
				vocabularyName = actionScope.find('.ui-tags-select-list option:selected').text(),
				inputVocabularyName = actionScope.find('.ui-tags-vocabulary-name');
			
			instance._hideAllMessages();
			
			var newVocabulary = inputVocabularyName.val();
			
			if (newVocabulary) { 
				instance._addVocabulary(newVocabulary, function() {
					instance._addEntry(entryName, newVocabulary);
				});
				return;
			}
			
			instance._addEntry(entryName, vocabularyName);
		};
		
		jQuery('input.ui-tags-save-entry').click(addEntry);
		jQuery('.ui-tags-actions input').keyup(function(event) {
			if (event.keyCode == 13) {
				addEntry();
				event.preventDefault();
			}
		});
		
		jQuery('input.ui-tags-delete-entries-button').click(function () {
			var wantToDelete = confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this-list'));
			
			if (wantToDelete) {
				instance._deleteEntry(instance.selectedEntryId, function() {
					instance._closeEditSection();
					instance.displayVocabularyEntries(instance.selectedVocabularyName);
				});
			}
		});
		
		jQuery('input.ui-tags-delete-list-button').click(function () {
			var wantToDelete = confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this-list'));
			
			if (wantToDelete) {
				instance._deleteVocabulary(instance.selectedVocabularyId, function() {
					instance._closeEditSection();
					instance._loadData();
					
				});
			}
		});
		
		this._loadData();
	},
	
	_loadData: function() {
		var instance = this, 
			selected = instance.VOCABULARY_SELECTED,
			method = /category/.test(selected) ? 'displayCategoriesList' : 'displayFolksonomiesList';
		
		this._closeEditSection();
		
		this[method](function() {
			instance.displayVocabularyEntries(instance.selectedVocabularyName, function() {
				var entryId = instance._getEntryId(instance.EXP_ENTRY_LIST + ':first');
				//instance._selectEntry(entryId);
			});
		});
	},
	
	getVocabularies: function(folksonomy, fn) {
		Liferay.Service.Tags.TagsVocabulary.getVocabularies({
			companyId: themeDisplay.getCompanyId(), folksonomy: folksonomy
		}, fn);
	},
	
	getFolksonomies: function(fn) {
		this.getVocabularies(true, fn);
	},
	
	getCategories: function(fn) {
		this.getVocabularies(false, fn);
	},
	
	displayFolksonomiesList: function(fn) {
		this.displayList(true, fn);
	},
	
	displayCategoriesList: function(fn) {
		this.displayList(false, fn);
	},
	
	displayCurrentList: function(fn) {
		var instance = this, selected = instance.VOCABULARY_SELECTED,
			method = /category/.test(selected) ? 'displayCategoriesList' : 'displayFolksonomiesList';
		instance[method](fn);
	},
	
	displayList: function(folksonomy, fn) {
		
		var instance = this, buffer = [], 
			list = jQuery(instance.EXP_VOCABULARY_SCOPE),
			dataMethodName = folksonomy ? 'getFolksonomies' : 'getCategories';
		
		instance._showLoading('.ui-tags-vocabulary-entries, .ui-tags-vocabulary-list');
		
		buffer.push("<ul>");
		
		this[dataMethodName](function(vocabularies) {
			jQuery.each(vocabularies, function(i) {
				buffer.push("<li");
				buffer.push(" class='ui-tags-vocabulary ");
				if (i == 0)	buffer.push(" selected ");
				buffer.push("' data-vocabulary='");
				buffer.push(this.name);
				buffer.push("' data-vocabularyId='");
				buffer.push(this.vocabularyId);
				buffer.push("'><a href='javascript:void(0);'>");
				buffer.push(this.name);
				buffer.push("</a></li>");
			});
			
			buffer.push("</ul>");
			
			list.html(buffer.join(''));
			
			/* Storing vocabulary useful data */
			var fisrtVocabulary = jQuery(instance.EXP_VOCABULARY_LIST + ':first'), 
				vocabularyName = instance._getVocabularyName(fisrtVocabulary),
				vocabularyId = instance._getVocabularyId(fisrtVocabulary);
			
			instance.selectedVocabularyName = vocabularyName;
			instance.selectedVocabularyId = vocabularyId;
			instance._feedVocabularySelect(vocabularies, vocabularyId);
			
			var listLinks = jQuery('li', list);
			
			listLinks.mousedown(function() {
				var vocabularyId = instance._getVocabularyId(this);
				instance._selectVocabulary(vocabularyId);
			});
			
			listLinks.droppable({
				accept: '.ui-tags-item',
				tolerance: 'pointer',
				hoverClass:	'active-area',
				scroll: 'auto',
				scope: 'ui-tags-item-scope',
				cssNamespace: false,
				drop: function(event, ui) {
					ui.droppable = jQuery(this);
					instance._merge(event, ui);
				}
			});
			
			jQuery('li a', list).editable(
				function(value, settings) {
					var vocabularyName = value, 
						vocabularyId = instance.selectedVocabularyId, 
						folksonomy = instance.VOCABULARY_SELECTED == "tag",
						li = jQuery(this).parents('li:first');
						
					li.attr('data-vocabulary', value);
					instance._updateVocabulary(vocabularyId, vocabularyName, folksonomy);
					return value;
				},
				{
					cssclass: 'ui-tags-edit-vocabulary',
					height: '15px',
					width: '200px',
					onblur: 'submit',
					select: true,
					type: 'text',
					event: 'dblclick'
				}
			);
			
			if (fn) fn();
		});
	},
	
	getVocabularyEntries: function(vocabulary, fn) {
		this._showLoading(this.EXP_ENTRY_SCOPE);
		
		Liferay.Service.Tags.TagsEntry.getVocabularyEntries({
			companyId: themeDisplay.getCompanyId(), name: vocabulary
		}, fn);
	},
	
	displayVocabularyEntries: function(vocabulary, fn) {
		var instance = this;
		
		this.getVocabularyEntries(vocabulary, function(entries) {
			if (!instance.VOCABULARY_SELECTED || instance.VOCABULARY_SELECTED == 'tag')
				instance.displayFolksonomiesVocabularyEntries(entries, fn);
			
			if (instance.VOCABULARY_SELECTED == 'category')
				instance.displayCategoriesVocabularyEntries(entries, fn);
		});
		
	},
	
	displayFolksonomiesVocabularyEntries: function(entries, fn) {
		var instance = this, buffer = [], childrenList = jQuery(instance.EXP_ENTRY_SCOPE);
		
		buffer.push("<ul>");
		
		jQuery.each(entries, function(i) {
			buffer.push("<li class='ui-tags-item' ");
			buffer.push("data-entry='");
			buffer.push(this.name);
			buffer.push("' data-entryId='");
			buffer.push(this.entryId);
			buffer.push("'><span><a href='javascript:void(0);'>");
			buffer.push(this.name);
			buffer.push("</a></span>");
			buffer.push("</li>");
		});
		buffer.push("</ul>");
		
		if (!entries.length) {
			buffer = [];
			instance._sendMessage('error', 'no-entries-were-found', '#ui-tags-entry-messages', true);
		}
		
		childrenList.html(buffer.join(''));
		
		var	entryList = jQuery(instance.EXP_ENTRY_LIST);
		
		entryList.mousedown(function() {
			var entryId = instance._getEntryId(this), 
				editContainer = jQuery('.ui-tags-vocabulary-edit');
			instance._selectEntry(entryId);
			instance._showSection(editContainer);
		});
		
		entryList.draggable({
			appendTo: 'body',
			helper: function(event, ui) {
				var drag = jQuery(this);
				return drag.clone().css({ width: drag.width() });
			},
			cursor: 'move',
			scroll: 'auto',
			distance: 3,
			ghosting: false,
			opacity: 0.7,
			zIndex: 1000,
			scope: 'ui-tags-item-scope',
			cssNamespace: false
		});
		
		entryList.droppable({
			accept: '.ui-tags-item',
			tolerance: 'pointer',
			hoverClass:	'active-area',
			cssNamespace: false,
			scope: 'ui-tags-item-scope',
			drop: function(event, ui) {
				ui.droppable = jQuery(this);
				instance._merge(event, ui);
			}
		});
			
		instance._paintOddLines();
		
		if (fn) fn();
	},
	
	displayCategoriesVocabularyEntries: function(entries, fn) {
		var instance = this, 
			buffer = [],
			childrenList = jQuery(instance.EXP_ENTRY_SCOPE);
			
		var treeOptions = {
			sortOn: "li",
			dropOn: "span.folder",
			dropHoverClass: "hover-folder",
			drop: function(event, ui) {
				ui.droppable = jQuery(jQuery(this).parent());
				instance._merge(event, ui);
				
				setTimeout(function() {
					jQuery("#ui-tags-treeview :not(span)").removeClass();
					jQuery("#ui-tags-treeview div").remove();
					jQuery("#ui-tags-treeview").removeData('toggler');
					jQuery('#ui-tags-treeview').treeview();
				}, 100);
			}
		};
			
		buffer.push('<div class="ui-tags-treeview-container"><ul id="ui-tags-treeview" class="filetree">');
		instance._buildCategoryTreeview(entries, buffer, 0);
		buffer.push('</ul></div>');
		
		childrenList.html(buffer.join(''));
		
		var	entryList = jQuery(instance.EXP_ENTRY_LIST);
		
		entryList.click(function(event) {
			var entryId = instance._getEntryId(this), 
				editContainer = jQuery('.ui-tags-vocabulary-edit');
			
			
			instance._selectEntry(entryId);
			instance._showSection(editContainer);
			event.stopPropagation(); 
		});
		
		jQuery("#ui-tags-treeview").treeview().tree(treeOptions);
		
		var list = jQuery(instance.EXP_VOCABULARY_SCOPE),
			listLinks = jQuery('li', list),
			treeScope= jQuery('#ui-tags-treeview').data('tree').identifier;
					
		listLinks.droppable({
			accept: '.ui-tags-category-item',
			tolerance: 'pointer',
			hoverClass:	'active-area',
			scope: treeScope,
			cssNamespace: false,
			drop: function(event, ui) {
				ui.droppable = jQuery(this);
				instance._merge(event, ui);
			}
		});
		
		if (fn) fn();
	},
	
	_buildCategoryTreeview: function(entries, buffer, parentId) {
		var instance = this, 
			children = instance._filterCategory(entries, parentId);
		
		jQuery.each(children, function(i) {
			var entryId = this.entryId, 
				name = this.name, 
				hasChild = instance._filterCategory(entries, entryId).length;
			
			buffer.push('<li');
			buffer.push(' class="ui-tags-category-item"');
			buffer.push(' data-entry="');
			buffer.push(this.name);
			buffer.push('" data-entryId="');
			buffer.push(this.entryId);
				
			buffer.push('"><span class="folder">');
			//buffer.push(hasChild ? 'folder' : 'file');
			buffer.push(name);
			buffer.push('</span>');
			
			if (hasChild) {
				buffer.push('<ul>');
				instance._buildCategoryTreeview(entries, buffer, entryId);
				buffer.push('</ul>');
			}
			
			buffer.push('</li>');
		});

		return children.length;
	},
	
	_filterCategory: function(entries, parentId) {
		var _entries = [];
		
		jQuery.each(entries, function(i) {
			if (this.parentEntryId == parentId) {
				_entries.push(this);
			}
		});
		
		return _entries;
	},
	
	getProperties: function(entryId, fn) {
		Liferay.Service.Tags.TagsProperty.getProperties({
			entryId: entryId
		}, fn);
	},
	
	displayProperties: function(entryId) {
		var instance = this;
		this.getProperties(entryId, function(properties) {
			if (!properties.length) properties = [{ key: '', value: '' }];
			
			var total = properties.length, 
				totalRendered = jQuery('div.ui-tags-property-line').size();

			if (totalRendered > total) return;
			
			jQuery.each(properties, function() {
				var baseProperty = jQuery('div.ui-tags-property-line:last');
				instance._addProperty(baseProperty, this.key, this.value);
			});
		});
	},
	
	_addProperty: function(baseNode, key, value) {
		var instance = this;
		var baseProperty = jQuery('div.ui-tags-property-line:last');
		var newProperty = baseProperty.clone(); 
		
		newProperty.find('.property-key').val(key);
		newProperty.find('.property-value').val(value);
		
		newProperty.insertAfter(baseNode).css('display', 'block');
		newProperty.find('input:first').focus();
		instance._attachPropertyIconEvents(newProperty);
	},
	
	_removeProperty: function(property) {
		if (jQuery('div.ui-tags-property-line').length > 2)
			property.remove();
	},
	
	_attachPropertyIconEvents: function(property) {
		var instance = this;
		
		jQuery(property).find('img[title=Add]').click(function() {
			instance._addProperty(property, '', '');
		});
		
		jQuery(property).find('img[title=Delete]').click(function() {
			instance._removeProperty(property);
		});
	},
	
	_showSection: function(exp) {
		var element = jQuery(exp);
		if (!element.is(':visible')) 
			element.fadeIn().find('input:first').focus();
	},

	_hideSection: function(exp) {
		jQuery(exp).hide();
	},
	
	_unselectAllEntries: function() {
		jQuery(this.EXP_ENTRY_LIST).removeClass('selected');
		jQuery('div.ui-tags-property-line:gt(0)').remove();
	},
	
	_unselectAllVocabularies: function() {
		jQuery(this.EXP_VOCABULARY_LIST).removeClass('selected');
	},
	
	_feedVocabularySelect: function(vocabularies, defaultValue) {
		var select = jQuery('select.ui-tags-select-list'), 
			buffer = ['<option value="0"></option>', '<option value="0">(new)</option>'];
		jQuery.each(vocabularies, function(i) {
			var selected = this.vocabularyId == defaultValue;
			buffer.push("<option");
			buffer.push(selected ? ' selected ' : '');
			buffer.push(" value='");
			buffer.push(this.vocabularyId);
			buffer.push("'>");
			buffer.push(this.name);
			buffer.push("</option>");
		});
		select.html(buffer.join(''));
	},
	
	_selectCurrentVocabulary: function(value) {
		var option = jQuery('select.ui-tags-select-list option[value="'+value+'"]');
		option.attr('selected', 'selected')
	},
	
	_updateVocabulary: function(vocabularyId, vocabularyName, folksonomy, fn) {
		Liferay.Service.Tags.TagsVocabulary.updateVocabulary({
			vocabularyId: vocabularyId,
			name: vocabularyName,
			folksonomy: folksonomy
		}, fn);
	},
	
	_updateEntry: function(entryId, name, parentEntryName, properties, vocabularyName) {
		var instance = this;

		Liferay.Service.Tags.TagsEntry.updateEntry({
				entryId: entryId,
				parentEntryName: parentEntryName,
				name: name,
				vocabularyName: vocabularyName,
				//parentCategoryName: parentCategoryName,
				properties: properties
			},
			function(json) {
				var exception = json.exception;
				
				if (!exception) {
					instance._closeEditSection();
					//instance.displayVocabularyEntries(instance.selectedVocabularyName);
				}
				else {
					if (/NoSuchVocabularyException/.test(exception)) {
						instance._sendMessage('error', 'that-vocabulary-does-not-exist');
					}					
					else if (/NoSuchEntryException/.test(exception)) {
						instance._sendMessage('error', 'that-parent-category-does-not-exist');
					}					
					else if (/Exception/.test(exception)) {
						instance._sendMessage('error', 'one-of-your-fields-contain-invalid-characters');
					}
				}
			}
		);
		
	},
	
	_addEntry: function(entryName, vocabulary, fn) {
		var instance = this, properties = ['0:category:category'];
		
		Liferay.Service.Tags.TagsEntry.addEntry(
				{
					name: entryName,
					vocabulary: vocabulary,
					properties: properties
				},
				function(json) {
					var exception = json.exception;

					if (!exception && json.entryId) {
						instance._sendMessage('success', 'your-request-processed-successfully');
						
						instance._selectVocabulary(json.vocabularyId);
						
						instance.displayVocabularyEntries(instance.selectedVocabularyName, function() {
							var entry = instance._selectEntry(json.entryId);
							if (entry.length) jQuery(instance.EXP_ENTRY_SCOPE).scrollTo(entry/*, 800*/);
							instance._showSection('.ui-tags-vocabulary-edit');
						});
						instance._resetActionValues();
						if (fn) fn(entryName, vocabulary);
					}
					else {
						var errorKey;
						if (/DuplicateEntryException/.test(exception)) {
							errorKey = 'that-tag-already-exists';
						}
						else if (/EntryNameException/.test(exception)) {
							errorKey = 'one-of-your-fields-contain-invalid-characters';
						}
						else if (/NoSuchVocabularyException/.test(exception)) {
							errorKey = 'that-vocabulary-does-not-exists';
						}
						if (errorKey) instance._sendMessage('error', errorKey);
					}
				}
			);
	},
	
	_addVocabulary: function(vocabulary, fn) {
		var instance = this, folksonomy = instance.VOCABULARY_SELECTED == "tag";

		Liferay.Service.Tags.TagsVocabulary.addVocabulary(
			{
				name: vocabulary,
				folksonomy: folksonomy
			},
			function(json) {
				var exception = json.exception;
				if (!json.exception) {
					instance._sendMessage('success', 'your-request-processed-successfully');
					
					var selected = instance.VOCABULARY_SELECTED,
						method = /category/.test(selected) ? 'displayCategoriesList' : 'displayFolksonomiesList';
					
					instance[method](function() {
						var vocabulary = instance._selectVocabulary(json.vocabularyId);
						instance.displayVocabularyEntries(instance.selectedVocabularyName);
						if (vocabulary.length) jQuery(instance.EXP_VOCABULARY_SCOPE).scrollTo(vocabulary/*, 800*/);
					});
					
					if (fn) fn(vocabulary);
				}
				else {
					var errorKey;
					if (/DuplicateVocabularyException/.test(exception)) {
						errorKey = 'that-vocabulary-already-exists';
					}
					else if (/VocabularyNameException/.test(exception)) {
						errorKey = 'one-of-your-fields-contain-invalid-characters';
					}
					else if (/NoSuchVocabularyException/.test(exception)) {
						errorKey = 'that-parent-vocabulary-does-not-exist';
					}
					if (errorKey) instance._sendMessage('error', errorKey);
				}
			}
		);
	},
	
	_deleteEntry: function(entryId, fn) {
		Liferay.Service.Tags.TagsEntry.deleteEntry({
			entryId: entryId
		}, fn);
	},
	
	_deleteVocabulary: function(vocabularyId, fn) {
		Liferay.Service.Tags.TagsVocabulary.deleteVocabulary({
			entryId: vocabularyId
		}, fn);
	},
	
	_selectVocabulary: function(vocabularyId) {
		var instance = this, vocabulary = instance._getVocabulary(vocabularyId), 
			vocabularyName = instance._getVocabularyName(vocabulary),
			vocabularyId = instance._getVocabularyId(vocabulary);
		
		if (vocabulary.is('.selected')) return vocabulary;
		
		instance._hideAllMessages();
		instance.selectedVocabularyName = vocabularyName;
		instance.selectedVocabularyId = vocabularyId;
		instance._selectCurrentVocabulary(vocabularyId);
		
		instance._unselectAllVocabularies();
		instance._closeEditSection();
		
		vocabulary.addClass('selected');
		instance.displayVocabularyEntries(instance.selectedVocabularyName);
		return vocabulary;
	},
	
	_selectEntry: function(entryId) {
		var instance = this, entry = instance._getEntry(entryId), 
			entryId = instance._getEntryId(entry),
			entryName = instance._getEntryName(entry);
		
			instance.selectedEntryId = entryId;
			instance.selectedEntryName = entryName;
			
		if (entry.is('.selected') || !entryId) return entry;
		
		instance._unselectAllEntries();
		entry.addClass('selected');
		
		var editContainer = jQuery('.ui-tags-vocabulary-edit'), 
			entryNameField = editContainer.find('input.entry-name');
		
		entryNameField.val(entryName);
		instance.displayProperties(entryId);
		return entry;
	},
	
	_mergeEntries: function(fromId, toId, fn) {
		Liferay.Service.Tags.TagsEntry.mergeEntries({
			fromEntryId: fromId,
			toEntryId: toId
		}, fn); 
	},
	
	_merge: function(event, ui) {
		var instance = this, 
			draggable = ui.draggable, 
			droppable = ui.droppable,
			fromEntryId = instance._getEntryId(draggable),
			fromEntryName = instance._getEntryName(draggable),
			toEntryId = instance._getEntryId(droppable),
			toEntryName = instance._getEntryName(droppable),
			vocabularyId = instance._getVocabularyId(droppable),
			vocabularyName = instance._getVocabularyName(droppable);
		
			
		var isChangingVocabulary = !!vocabularyName, 
			destination = isChangingVocabulary ? vocabularyName : toEntryName;
		
		var tagText = {
			SOURCE: instance._getEntryName(draggable), 
			DESTINATION: destination
		},
		
		mergeText = Liferay.Language.get('are-you-sure-you-want-to-merge-x-into-x', ['{SOURCE}', '{DESTINATION}']).replace(
			/\{(SOURCE|DESTINATION)\}/gm,
			function(completeMatch, match, index, str) { 
				return tagText[match];
			}
		);
		
		if (confirm(mergeText)) {
			if (this.VOCABULARY_SELECTED == "tag") {
					if (isChangingVocabulary) {
						var properties = instance._buildProperties();
						instance._updateEntry(fromEntryId, fromEntryName, null, properties, vocabularyName);
						instance.displayVocabularyEntries(instance.selectedVocabularyName);
					}
					else {
						instance._mergeEntries(fromEntryId, toEntryId, function() {
							draggable.remove();
							instance._selectEntry(toEntryId);
							instance._paintOddLines();
						});
					}
			}
			else if (this.VOCABULARY_SELECTED == "category") {
				var properties = instance._buildProperties();
				vocabularyName = vocabularyName || instance.selectedVocabularyName;
				parentEntryName = isChangingVocabulary ? null : toEntryName;
				instance._updateEntry(fromEntryId, fromEntryName, parentEntryName, properties, vocabularyName);
			}
		}
	},
	
	_resetActionValues: function() {
		jQuery('.ui-tags-actions input:text').val('');
		jQuery('.ui-tags-actions .ui-tags-vocabulary-name').hide();
	},
	
	_getVocabularyId: function(exp) {
		return jQuery(exp).attr('data-vocabularyId');
	},
	
	_getVocabularyName: function(exp) {
		return jQuery(exp).attr('data-vocabulary');
	},
	
	_getEntry: function(entryId) {
		return jQuery('li[data-entryId=' + entryId + ']')
	},
	
	_getVocabulary: function(vocabularyId) {
		return jQuery('li[data-vocabularyId=' + vocabularyId + ']')
	},
	
	_getEntryId: function(exp) {
		return jQuery(exp).attr('data-entryId');
	},
	
	_getEntryName: function(exp) {
		return jQuery(exp).attr('data-entry');
	},
	
	_saveProperties: function() {
		var instance = this, 
			entryId = instance.selectedEntryId, 
			entryName = jQuery('input.entry-name').val() || instance.selectedEntryName,
			parentCategoryName = null, 
			properties = instance._buildProperties(), 
			vocabularyName = instance.selectedVocabularyName;
			instance._updateEntry(entryId, entryName, parentCategoryName, properties, vocabularyName);
	},
	
	_buildProperties: function() {
		var buffer = [];
		jQuery('.ui-tags-property-line:visible').each(function(i, o) {
			var propertyLine = jQuery(this), 
				key = propertyLine.find('input.property-key').val(),
				value = propertyLine.find('input.property-value').val();
				buffer.push(['0', ':', key, ':', value, ','].join(''));
		});
		return buffer.join('');
	},
	
	_closeEditSection: function() {
		this._hideSection('.ui-tags-vocabulary-edit');
	},
	
	_hideAllMessages: function() {
		jQuery('#ui-tags-entry-messages, #ui-tags-messages').hide();
	},
	
	_showLoading: function(container) {
		jQuery(container).html("<div class='loading-animation'></div>");
	},
	
	_hideLoading: function(exp) {
		jQuery("div.loading-animation").remove();
	},
	
	_sendMessage: function(type, key, output, noAutoHide) {
		var instance = this, output = jQuery(output || '#ui-tags-messages'), 
			message = Liferay.Language.get(key),
			typeClass = 'portlet-msg-' + (type || 'error');
		
		clearTimeout(instance.messageTimeout);
		output.removeClass('portlet-msg-error portlet-msg-success');
		output.addClass(typeClass).html(message).fadeIn('fast');
		if (!noAutoHide) instance.messageTimeout = setTimeout(function() { output.fadeOut('slow'); }, 7000);
	},
	
	_paintOddLines: function() {
		var entriesScope = jQuery(this.EXP_ENTRY_SCOPE);
		jQuery('li', entriesScope).removeClass('odd');
		jQuery('li:odd', entriesScope).addClass('odd');
	}
	
});