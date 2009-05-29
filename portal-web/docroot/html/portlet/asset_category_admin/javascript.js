(function() {
	var Dom = Expanse.Dom;
	var Event = Expanse.Event;
	var DDM = Expanse.DragDrop;

	var AssetCategoryAdmin = new Expanse.Class(
		{
			initialize: function(portletId) {
				var instance = this;

				var childrenContainer = jQuery(instance._categoryScopeClass);

				instance.portletId = portletId;
				instance._container = jQuery('.category-vocabulary-container');

				jQuery('.category-vocabulary-close').click(
					function() {
						instance._unselectAllCategories();
						instance._closeEditSection();
					}
				);

				jQuery('.category-vocabulary-save-category-properties').click(
					function() {
						instance._saveCategoryProperties();
					}
				);

				instance._portletMessageContainer = jQuery('<div class="lfr-message-response" id="category-vocabulary-messages" />');
				instance._categoryMessageContainer = jQuery('<div class="lfr-message-response" id="category-vocabulary-category-messages" />');

				instance._portletMessageContainer.hide();
				instance._categoryMessageContainer.hide();

				instance._container.before(instance._portletMessageContainer);
				childrenContainer.before(instance._categoryMessageContainer);

				var buttons = jQuery('.category-vocabulary-buttons')
				var toolbar = jQuery('.category-vocabulary-toolbar');

				var categoryToolbarSection = jQuery('.category-toolbar-section');
				var categoryVocabularyToolbarSection = jQuery('.category-vocabulary-toolbar-section');

				var addCategoryButton = jQuery('.add-category-btn');
				var addCategoryVocabularyButton = jQuery('.add-category-vocabulary-btn');

				instance._toolbarCategoryPanel = new Expanse.Overlay(
					categoryToolbarSection[0],
					{
						context: [addCategoryButton[0], 'tr', 'br'],
						preventcontextoverlap: true,
						visible: false
					}
				);

				instance._categoryVocabularyCategoryPanel = new Expanse.Overlay(
					categoryVocabularyToolbarSection[0],
					{
						context: [addCategoryVocabularyButton[0], 'tr', 'br'],
						preventcontextoverlap: true,
						visible: false
					}
				);

				var selectButton = function(button) {
					buttons.find('.button').removeClass('selected');
					jQuery(button).addClass('selected');
				};

				toolbar.find('.add-category-vocabulary-btn').click(
					function (event) {
						instance._showToolBarCategoryVocabularySection();
					}
				);

				toolbar.find('.add-category-btn').click(
					function (event) {
						instance._showToolBarCategorySection();
					}
				);

				jQuery('.permissions-categories-btn').click(
					function() {
						var categoryName = instance._selectedCategoryName;
						var categoryId = instance._selectedCategoryId;

						if (categoryName && categoryId) {
							var portletURL = instance._createPermissionURL(
								'com.liferay.portlet.asset.model.AssetCategory',
								categoryName,
								categoryId);

							submitForm(document.hrefFm, portletURL.toString());
						}
						else {
							instance._showToolBarCategorySection();
						}
					}
				);

				jQuery('.permissions-category-vocabulary-btn').click(
					function() {
						var categoryVocabularyName = instance._selectedCategoryVocabularyName;
						var categoryVocabularyId = instance._selectedCategoryVocabularyId;

						if (categoryVocabularyName && categoryVocabularyId) {
							var portletURL = instance._createPermissionURL(
								'com.liferay.portlet.asset.model.AssetCategoryVocabulary',
								categoryVocabularyName,
								categoryVocabularyId);

							submitForm(document.hrefFm, portletURL.toString());
						}
						else {
							instance._showToolBarCategoryVocabularySection();
						}
					}
				);

				jQuery('#category-vocabulary-search-bar').change(
					function(event) {
						jQuery('#category-vocabulary-search-input').focus();
						instance._reloadSearch();
					}
				);

				var addCategory = function() {
					var actionScope = jQuery('.category-vocabulary-actions');
					var categoryName = actionScope.find('.category-vocabulary-category-name').val();
					var categoryVocabularyId = actionScope.find('.category-vocabulary-select-list option:selected').attr('value');

					instance._hideAllMessages();
					instance._addCategory(categoryName, categoryVocabularyId);
				};

				var addCategoryVocabulary = function() {
					var actionScope = jQuery('.category-vocabulary-actions');
					var inputCategoryVocabularyName = actionScope.find('.category-vocabulary-name');
					var newCategoryVocabularyName = inputCategoryVocabularyName.val();

					instance._hideAllMessages();
					instance._addCategoryVocabulary(newCategoryVocabularyName);
				};

				jQuery('input.category-save-button').click(addCategory);
				jQuery('input.category-vocabulary-save-button').click(addCategoryVocabulary);

				jQuery('.category-vocabulary-actions input').keyup(
					function(event) {
						if (event.keyCode == 13) {
							var input = jQuery(this);

							if (input.is('.category-vocabulary-category-name')) {
								addCategory();
							}
							else if (input.is('.category-vocabulary-name')) {
								addCategoryVocabulary();
							}

							return false;
						}
					}
				);

				jQuery('input.category-vocabulary-delete-categories-button').click(
					function() {
						if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this-category'))) {
							instance._deleteCategory(
								instance._selectedCategoryId,
								function(message) {
									var exception = message.exception;

									if (!exception) {
										instance._closeEditSection();
										instance._hideToolbarSections();
										instance._displayCategoryVocabularyCategories(instance._selectedCategoryVocabularyId);
									}
									else {
										if (exception.indexOf('auth.PrincipalException') > -1) {
											instance._sendMessage('error', 'you-do-not-have-permission-to-access-the-requested-resource');
										}
									}
								}
							);
						}
					}
				);

				jQuery('input.category-vocabulary-delete-list-button').click(
					function() {
						if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this-list'))) {
							instance._deleteCategoryVocabulary(
								instance._selectedCategoryVocabularyId,
								function(message) {
									var exception = message.exception;
									if (!exception) {
										instance._closeEditSection();
										instance._hideToolbarSections();
										instance._loadData();
									}
									else {
										if (exception.indexOf('auth.PrincipalException') > -1) {
											instance._sendMessage('error', 'you-do-not-have-permission-to-access-the-requested-resource');
										}
									}
								}
							);
						}
					}
				);

				jQuery('.close-panel').click(
					function() {
						instance._hideToolbarSections();
					}
				);

				jQuery('.exp-overlay input:text').keyup(
					function(event) {
						var ESC_KEY_CODE = 27;
						var keyCode = event.keyCode;

						if (keyCode == ESC_KEY_CODE) {
							instance._hideToolbarSections();
						}
					}
				);

				instance._loadData();
			},

			_createPermissionURL: function(modelResource, modelResourceDescription, resourcePrimKey) {
				var instance = this;

				var portletURL = Liferay.PortletURL.createPermissionURL(
					instance.portletId, modelResource, modelResourceDescription, resourcePrimKey);

				return portletURL;
			},

			_displayCategoryVocabularyCategoriesImpl: function(categories, callback) {
				var instance = this;

				var buffer = [];
				var childrenList = jQuery(instance._categoryScopeClass);

				var treeOptions = {
					sortOn: 'li',
					distance: 10,
					dropOn: 'span.folder',
					dropHoverClass: 'hover-folder',
					drop: function(event, ui) {
						ui.droppable = jQuery(this).parent();
						instance._merge(event, ui);

						var categoryTree = jQuery('#category-vocabulary-treeview');

						setTimeout(
							function() {
								categoryTree.find(':not(span)').removeClass();
								categoryTree.find('div').remove();
								categoryTree.removeData('toggler');
								categoryTree.treeview();
						}, 100);
					}
				};

				buffer.push('<div class="category-vocabulary-treeview-container lfr-component"><ul id="category-vocabulary-treeview" class="filetree">');
				instance._buildCategoryTreeview(categories, buffer, 0);
				buffer.push('</ul></div>');

				childrenList.html(buffer.join(''));

				instance._reloadSearch();

				var categoryTree = jQuery('#category-vocabulary-treeview');
				var	categoryList = jQuery(instance._categoryListClass);

				categoryList.click(
					function(event) {
						var categoryId = instance._getCategoryId(this);
						var editContainer = jQuery('.category-vocabulary-edit');

						instance._selectCategory(categoryId);
						instance._showSection(editContainer);

						event.stopPropagation();
					}
				);

				categoryTree.treeview().tree(treeOptions);

				var list = jQuery(instance._categoryVocabularyScopeClass);
				var listLinks = jQuery('li', list);
				var treeScope = categoryTree.data('tree').identifier;

				for (var i = listLinks.length - 1; i >= 0; i--) {
					new droppableTag(listLinks[i], 'tags');
				}

				if (callback) {
					callback();
				}
			},

			_displayList: function(callback) {
				var instance = this;

				var buffer = [];
				var list = jQuery(instance._categoryVocabularyScopeClass);

				instance._showLoading('.category-vocabulary-categories, .category-vocabulary-list');

				buffer.push('<ul>');

				instance._getCategoryVocabularies(
					function(categoryVocabularies) {
						jQuery.each(
							categoryVocabularies,
							function(i) {
								buffer.push('<li');
								buffer.push(' class="category-vocabulary-category results-row');

								if (i == 0) {
									buffer.push(' selected ');
								}

								buffer.push('" data-categoryVocabulary="');
								buffer.push(this.name);
								buffer.push('" data-categoryVocabularyId="');
								buffer.push(this.categoryVocabularyId);
								buffer.push('"><span><a href="javascript:;">');
								buffer.push(this.name);
								buffer.push('</a></span>');
								buffer.push('</li>');
							}
						);

						buffer.push('</ul>');

						list.html(buffer.join(''));

						var firstCategoryVocabulary = jQuery(instance._categoryVocabularyListClass + ':first');
						var categoryVocabularyName = instance._getCategoryVocabularyName(firstCategoryVocabulary);
						var categoryVocabularyId = instance._getCategoryVocabularyId(firstCategoryVocabulary);

						instance._selectedCategoryVocabularyName = categoryVocabularyName;
						instance._selectedCategoryVocabularyId = categoryVocabularyId;
						instance._feedCategoryVocabularySelect(categoryVocabularies, categoryVocabularyId);

						var listLinks = jQuery('li', list);

						listLinks.mousedown(
							function(event) {
								var categoryVocabularyId = instance._getCategoryVocabularyId(this);

								instance._selectCategoryVocabulary(categoryVocabularyId);
							}
						);

						for (var i = listLinks.length - 1; i >= 0; i--) {
							new droppableTag(listLinks[i], 'tags');
						}

						jQuery('li span a', list).editable(
							function(value, settings) {

								var categoryVocabularyName = value;
								var categoryVocabularyId = instance._selectedCategoryVocabularyId;
								var li = jQuery(this).parents('li:first');

								li.attr('data-categoryVocabulary', value);

								instance._updateCategoryVocabulary(
									categoryVocabularyId,
									categoryVocabularyName,
									function(message) {
										var exception = message.exception;
										if (exception) {
											if (exception.indexOf('auth.PrincipalException') > -1) {
												instance._sendMessage('error', 'you-do-not-have-permission-to-access-the-requested-resource');
											}
										}
										else {
											instance._displayList(
												function() {
													var categoryVocabulary = instance._selectCategoryVocabulary(message.categoryVocabularyId);

													instance._displayCategoryVocabularyCategories(instance._selectedCategoryVocabularyId);
												}
											);
										}
									}
								);

								return value;
							},
							{
								cssclass: 'category-vocabulary-edit-vocabulary',
								data: function(value, settings) {
									return value;
								},

								height: '15px',
								width: '200px',
								onblur: 'ignore',
								submit: Liferay.Language.get('save'),
								select: false,
								type: 'text',
								event: 'dblclick'
							}
						);

						if (callback) {
							callback();
						}
					}
				);
			},

			_displayCategoryProperties: function(categoryId) {
				var instance = this;

				instance._getCategoryProperties(
					categoryId,
					function(categoryProperties) {
						if (!categoryProperties.length) {
							categoryProperties = [{ key: '', value: '' }];
						}

						var total = categoryProperties.length;
						var totalRendered = jQuery('div.category-vocabulary-property-row').length;

						if (totalRendered > total) {
							return;
						}

						jQuery.each(
							categoryProperties,
							function() {
								var baseCategoryProperty = jQuery('div.category-vocabulary-property-row:last');

								instance._addCategoryProperty(baseCategoryProperty, this.key, this.value);
							}
						);
					}
				);
			},

			_displayCategoryVocabularyCategories: function(categoryVocabularyId, callback) {
				var instance = this;

				jQuery('#category-vocabulary-category-messages').hide();

				instance._getCategoryVocabularyCategories(
					categoryVocabularyId,
					function(categories) {
						instance._displayCategoryVocabularyCategoriesImpl(categories, callback);
					}
				);
			},

			_addCategory: function(categoryName, categoryVocabularyId, callback) {
				var instance = this;
				var communityPermission = instance._getPermissionsEnabled('category', 'community');
				var guestPermission = instance._getPermissionsEnabled('category', 'guest');

				var serviceParameterTypes = [
					'long',
					'java.lang.String',
					'long',
					'[Ljava.lang.String;',
					'com.liferay.portal.service.ServiceContext'
				].join(',');

				Liferay.Service.Asset.AssetCategory.addCategory(
					{
						parentCategoryId: 0,
						name: categoryName,
						categoryVocabularyId: categoryVocabularyId,
						properties: [],
						serviceContext: jQuery.toJSON(
							{
								communityPermissions: communityPermission,
								guestPermissions: guestPermission,
								scopeGroupId: themeDisplay.getScopeGroupId()
							}
						),
						serviceParameterTypes: serviceParameterTypes
					},
					function(message) {
						var exception = message.exception;

						if (!exception && message.categoryId) {
							instance._sendMessage('success', 'your-request-processed-successfully');

							instance._selectCategoryVocabulary(categoryVocabularyId);

							instance._displayCategoryVocabularyCategories(
								instance._selectedCategoryVocabularyId,
								function() {
									var category = instance._selectCategory(message.categoryId);

									if (category.length) {
										jQuery(instance._categoryScopeClass).scrollTo(category);
									}

									instance._showSection('.category-vocabulary-edit');
								}
							);

							instance._resetActionValues();
							instance._hideToolbarSections();

							if (callback) {
								callback(categoryName, categoryVocabularyId);
							}
						}
						else {
							var errorKey = '';

							if (exception.indexOf('DuplicateCategoryException') > -1) {
								errorKey = 'that-category-already-exists';
							}
							else if ((exception.indexOf('CategoryNameException') > -1) ||
									 (exception.indexOf('AssetCategoryException') > -1)) {
								errorKey = 'one-of-your-fields-contains-invalid-characters';
							}
							else if (exception.indexOf('NoSuchCategoryVocabularyException') > -1) {
								errorKey = 'that-vocabulary-does-not-exist';
							}
							else if (exception.indexOf('auth.PrincipalException') > -1) {
								errorKey = 'you-do-not-have-permission-to-access-the-requested-resource';
							}
							if (errorKey) {
								instance._sendMessage('error', errorKey);
							}
						}
					}
				);
			},

			_addCategoryProperty: function(baseNode, key, value) {
				var instance = this;

				var baseCategoryProperty = jQuery('div.category-vocabulary-property-row:last');
				var newCategoryProperty = baseCategoryProperty.clone();

				newCategoryProperty.find('.cateogory-property-key').val(key);
				newCategoryProperty.find('.category-property-value').val(value);
				newCategoryProperty.insertAfter(baseNode);
				newCategoryProperty.show();

				if (!key && !value) {
					newCategoryProperty.find('input:first').addClass('lfr-auto-focus');
				}

				instance._attachCategoryPropertyIconEvents(newCategoryProperty);
			},

			_addCategoryVocabulary: function(categoryVocabularyName, callback) {
				var instance = this;

				var communityPermission = instance._getPermissionsEnabled('category-vocabulary', 'community');
				var guestPermission = instance._getPermissionsEnabled('category-vocabulary', 'guest');

				Liferay.Service.Asset.AssetCategoryVocabulary.addCategoryVocabulary(
					{
						name: categoryVocabularyName,
						serviceContext: jQuery.toJSON(
							{
								communityPermissions: communityPermission,
								guestPermissions: guestPermission,
								scopeGroupId: themeDisplay.getScopeGroupId()
							}
						)
					},
					function(message) {
						var exception = message.exception;

						if (!message.exception) {
							instance._sendMessage('success', 'your-request-processed-successfully');

							instance._displayList(
								function() {
									var categoryVocabulary = instance._selectCategoryVocabulary(message.categoryVocabularyId);

									instance._displayCategoryVocabularyCategories(instance._selectedCategoryVocabularyId);

									if (categoryVocabulary.length) {
										jQuery(instance._categoryVocabularyScopeClass).scrollTo(categoryVocabulary);
									}
								}
							);

							instance._resetActionValues();

							if (callback) {
								callback(categoryVocabulary);
							}
						}
						else {
							var errorKey = '';

							if (exception.indexOf('DuplicateCategoryVocabularyException') > -1) {
								errorKey = 'that-vocabulary-already-exists';
							}
							else if (exception.indexOf('CategoryVocabularyNameException') > -1) {
								errorKey = 'one-of-your-fields-contains-invalid-characters';
							}
							else if (exception.indexOf('NoSuchCategoryVocabularyException') > -1) {
								errorKey = 'that-parent-vocabulary-does-not-exist';
							}
							else if (exception.indexOf('auth.PrincipalException') > -1) {
								errorKey = 'you-do-not-have-permission-to-access-the-requested-resource';
							}

							if (errorKey) {
								instance._sendMessage('error', errorKey);
							}
						}
					}
				);
			},

			_alternateRows: function() {
				var instance = this;

				var categoriesScope = jQuery(instance._categoryScopeClass);

				jQuery('li', categoriesScope).removeClass('alt');
				jQuery('li:odd', categoriesScope).addClass('alt');
			},

			_attachCategoryPropertyIconEvents: function(categoryProperty) {
				var instance = this;

				var row = jQuery(categoryProperty);

				row.find('.add-category-property').click(
					function() {
						instance._addCategoryProperty(categoryProperty, '', '');
					}
				);

				row.find('.delete-category-property').click(
					function() {
						instance._removeCategoryProperty(categoryProperty);
					}
				);
			},

			_buildCategoryTreeview: function(categories, buffer, parentCategoryId) {
				var instance = this;

				var children = instance._filterCategory(categories, parentCategoryId);

				jQuery.each(
					children,
					function(i) {
						var categoryId = this.categoryId;
						var name = this.name;
						var hasChild = instance._filterCategory(categories, categoryId).length;

						buffer.push('<li');
						buffer.push(' class="category-vocabulary-category-item"');
						buffer.push(' data-category="');
						buffer.push(this.name);
						buffer.push('" data-categoryId="');
						buffer.push(this.categoryId);
						buffer.push('" data-parentCategoryId="');
						buffer.push(parentCategoryId);

						buffer.push('"><span class="folder">');
						buffer.push(name);
						buffer.push('</span>');

						if (hasChild) {
							buffer.push('<ul>');

							instance._buildCategoryTreeview(categories, buffer, categoryId);

							buffer.push('</ul>');
						}

						buffer.push('</li>');
					}
				);

				return children.length;
			},

			_buildCategoryProperties: function() {
				var instance = this;

				var buffer = [];

				jQuery('.category-vocabulary-property-row:visible').each(
					function(i, o) {
						var categoryPropertyRow = jQuery(this);
						var key = categoryPropertyRow.find('input.category-property-key').val();
						var value = categoryPropertyRow.find('input.category-property-value').val();
						var rowValue = ['0', ':', key, ':', value, ','].join('');

						buffer.push(rowValue);
					}
				);

				return buffer.join('');
			},

			_closeEditSection: function() {
				var instance = this;

				instance._hideSection('.category-vocabulary-edit');
				jQuery(instance._layoutContainerCells).width('auto');
			},

			_deleteCategory: function(categoryId, callback) {
				var instance = this;

				Liferay.Service.Asset.AssetCategory.deleteCategory(
					{
						categoryId: categoryId
					},
					callback
				);
			},

			_deleteCategoryVocabulary: function(categoryVocabularyId, callback) {
				var instance = this;

				Liferay.Service.Asset.AssetCategoryVocabulary.deleteCategoryVocabulary(
					{
						categoryVocabularyId: categoryVocabularyId
					},
					callback
				);
			},

			_feedCategoryVocabularySelect: function(categoryVocabularies, defaultValue) {
				var instance = this;

				var select = jQuery('select.category-vocabulary-select-list');
				var buffer = [];

				jQuery.each(
					categoryVocabularies,
					function(i) {
						var selected = (this.categoryVocabularyId == defaultValue);

						buffer.push('<option');
						buffer.push(selected ? ' selected ' : '');
						buffer.push(' value="');
						buffer.push(this.categoryVocabularyId);
						buffer.push('">');
						buffer.push(this.name);
						buffer.push('</option>');
					}
				);

				select.html(buffer.join(''));
			},

			_filterCategory: function(categories, parentCategoryId) {
				var instance = this;

				return jQuery.grep(
					categories,
					function(item, i) {
						return (item.parentCategoryId == parentCategoryId);
					}
				);
			},

			_getCategory: function(categoryId) {
				var instance = this;

				return jQuery('li[data-categoryId=' + categoryId + ']')
			},

			_getCategoryId: function(exp) {
				var instance = this;

				return jQuery(exp).attr('data-categoryId');
			},

			_getCategoryName: function(exp) {
				var instance = this;

				return jQuery(exp).attr('data-category');
			},

			_getParentCategoryId: function(exp) {
				var instance = this;

				return jQuery(exp).attr('data-parentCategoryId');
			},

			_getPermissionsEnabled: function(vocabularyType, type) {
				var buffer = [];
				var permissionsActions = jQuery('.'+vocabularyType+'-permissions-actions');
				var permissions = permissionsActions.find('[name$='+type+'Permissions]:checked');

				buffer = permissions.fieldValue().join(',');

				return buffer;
			},

			_getCategoryProperties: function(categoryId, callback) {
				var instance = this;

				Liferay.Service.Asset.AssetCategoryProperty.getCategoryProperties(
					{
						categoryId: categoryId
					},
					callback
				);
			},

			_getCategoryVocabularies: function(callback) {
				var instance = this;

				Liferay.Service.Asset.AssetCategoryVocabulary.getGroupCategoryVocabularies(
					{
						groupId: themeDisplay.getScopeGroupId()
					},
					callback
				);
			},

			_getCategoryVocabulary: function(categoryVocabularyId) {
				var instance = this;

				return jQuery('li[data-categoryVocabularyId=' + categoryVocabularyId + ']')
			},

			_getCategoryVocabularyCategories: function(categoryVocabularyId, callback) {
				var instance = this;

				instance._showLoading(instance._categoryScopeClass);

				Liferay.Service.Asset.AssetCategory.getVocabularyCategories(
					{
						categoryVocabularyId: categoryVocabularyId
					},
					callback
				);
			},

			_getCategoryVocabularyId: function(exp) {
				var instance = this;

				return jQuery(exp).attr('data-categoryVocabularyId');
			},

			_getCategoryVocabularyName: function(exp) {
				var instance = this;

				return jQuery(exp).attr('data-categoryVocabulary');
			},

			_hideAllMessages: function() {
				var instance = this;

				instance._container.find('.lfr-message-response').hide();
			},

			_hideLoading: function(exp) {
				var instance = this;

				instance._container.find('div.loading-animation').remove();
			},

			_hideSection: function(exp) {
				var instance = this;

				jQuery(exp).parent().removeClass('category-vocabulary-editing-tag');
			},

			_hideToolbarSections: function() {
				var instance = this;

				instance._toolbarCategoryPanel.hide();
				instance._categoryVocabularyCategoryPanel.hide();
			},

			_loadData: function() {
				var instance = this;

				instance._closeEditSection();

				instance._displayList(
					function() {
						instance._displayCategoryVocabularyCategories(
							instance._selectedCategoryVocabularyId,
							function() {
								var categoryId = instance._getCategoryId(instance._categoryListClass + ':first');
							}
						);
					}
				);
			},

			_merge: function(event, ui) {
				var instance = this;

				var draggable = ui.draggable;
				var droppable = ui.droppable;
				var fromCategoryId = instance._getCategoryId(draggable);
				var fromCategoryName = instance._getCategoryName(draggable);
				var toCategoryId = instance._getCategoryId(droppable);
				var toCategoryName = instance._getCategoryName(droppable);
				var categoryVocabularyId = instance._getCategoryVocabularyId(droppable);
				var categoryVocabularyName = instance._getCategoryVocabularyName(droppable);

				var isChangingCategoryVocabulary = !!categoryVocabularyName;
				var destination = isChangingCategoryVocabulary ? categoryVocabularyName : toCategoryName;

				var tagText = {
					SOURCE: instance._getCategoryName(draggable),
					DESTINATION: destination
				};

				var mergeText = Liferay.Language.get('are-you-sure-you-want-to-merge-x-into-x', ['[$SOURCE$]', '[$DESTINATION$]']).replace(
					/\[\$(SOURCE|DESTINATION)\$\]/gm,
					function(completeMatch, match, index, str) {
						return tagText[match];
					}
				);

				// Move category

				var categoryProperties = instance._buildCategoryProperties();

				categoryVocabularyId = categoryVocabularyId || instance._selectedCategoryVocabularyId;
				parentCategoryId = isChangingCategoryVocabulary ? null : toCategoryId;

				instance._updateCategory(fromCategoryId, categoryVocabularyId, parentCategoryId, fromCategoryName, categoryProperties);
			},

			_onDragDrop: function(event, instance) {
				var draggable = this;

				var target = Dom.get(event.info);
				var src = draggable.getEl();

				if (DDM.interactionInfo.validDrop && target != src) {
					instance._merge(event,
						{
							draggable: src,
							droppable: target
						}
					);
				}

				Dom.removeClass(target, 'active-area');
			},

			_reloadSearch: function() {
				var	instance = this;
				var options = {};
				var selected = jQuery('#category-vocabulary-select-search').val();
				var input = jQuery('#category-vocabulary-search-input');
				var categoryList = jQuery(instance._categoryListClass);
				var categoryVocabularyList = jQuery(instance._categoryVocabularyListClass);

				input.unbind('keyup');

				if (/vocabularies/.test(selected)) {
					options = {
						list: categoryVocabularyList,
						filter: jQuery('a', categoryVocabularyList)
					};
				}
				else {
					var filter = 'span';

					options = {
						list: categoryList,
						filter: jQuery(filter, categoryList)
					};
				}

				input.liveSearch(options);
			},

			_removeCategoryProperty: function(categoryProperty) {
				var instance = this;

				if (jQuery('div.category-vocabulary-property-row').length > 2) {
					categoryProperty.remove();
				}
			},

			_resetActionValues: function() {
				var instance = this;

				jQuery('.category-vocabulary-actions input:text').val('');

				instance._categoryVocabularyCategoryPanel.hide();
			},

			_saveCategoryProperties: function() {
				var instance = this;

				var categoryId = instance._selectedCategoryId;
				var categoryName = jQuery('input.category-name').val() || instance._selectedCategoryName;
				var parentCategoryId = instance._selectedParentCategoryId;
				var categoryProperties = instance._buildCategoryProperties();
				var categoryVocabularyId = instance._selectedCategoryVocabularyId;

				instance._updateCategory(categoryId, categoryVocabularyId, parentCategoryId, categoryName, categoryProperties);
				instance._displayCategoryVocabularyCategories(categoryVocabularyId);
			},

			_selectCurrentCategoryVocabulary: function(value) {
				var instance = this;

				var option = jQuery('select.category-vocabulary-select-list option[value="' + value + '"]');
				option.attr('selected', 'selected');
			},

			_selectCategory: function(categoryId) {
				var instance = this;

				var category = instance._getCategory(categoryId);
				var categoryId = instance._getCategoryId(category);
				var categoryName = instance._getCategoryName(category);
				var parentCategoryId = instance._getParentCategoryId(category);

				instance._selectedCategoryId = categoryId;
				instance._selectedCategoryName = categoryName;
				instance._selectedParentCategoryId = parentCategoryId;

				if (category.is('.selected') || !categoryId) {
					return category;
				}

				instance._unselectAllCategories();
				category.addClass('selected');

				var editContainer = jQuery('.category-vocabulary-edit');
				var categoryNameField = editContainer.find('input.category-name');

				categoryNameField.val(categoryName);
				instance._displayCategoryProperties(categoryId);

				instance._selectedCategory = category;

				return category;
			},

			_selectCategoryVocabulary: function(categoryVocabularyId) {
				var instance = this;

				var categoryVocabulary = instance._getCategoryVocabulary(categoryVocabularyId);
				var categoryVocabularyName = instance._getCategoryVocabularyName(categoryVocabulary);

				if (categoryVocabulary.is('.selected')) {
					return categoryVocabulary;
				}

				instance._hideAllMessages();
				instance._selectedCategoryVocabularyName = categoryVocabularyName;
				instance._selectedCategoryVocabularyId = categoryVocabularyId;
				instance._selectCurrentCategoryVocabulary(categoryVocabularyId);

				instance._unselectAllCategoryVocabularies();
				instance._closeEditSection();

				categoryVocabulary.addClass('selected');
				instance._displayCategoryVocabularyCategories(instance._selectedCategoryVocabularyId);

				return categoryVocabulary;
			},

			_sendMessage: function(type, key, output, noAutoHide) {
				var instance = this;

				var output = jQuery(output || '#category-vocabulary-messages');
				var message = Liferay.Language.get(key);
				var typeClass = 'portlet-msg-' + type;

				clearTimeout(instance._messageTimeout);

				output.removeClass('portlet-msg-error portlet-msg-success');
				output.addClass(typeClass).html(message).fadeIn('fast');

				if (!noAutoHide) {
					instance._messageTimeout = setTimeout(
						function() {
							output.fadeOut('slow',
								function(event) {
									instance._toolbarCategoryPanel.align();
									instance._categoryVocabularyCategoryPanel.align();
								}
							);
						}, 7000);
				}
			},

			_showLoading: function(container) {
				var instance = this;

				jQuery(container).html('<div class="loading-animation" />');
			},

			_showSection: function(exp) {
				var instance = this;

				var element = jQuery(exp);

				if (!element.is(':visible')) {
					element.parent().addClass('category-vocabulary-editing-tag');
					element.find('input:first').focus();
					jQuery(instance._layoutContainerCells).width('33%');
				}
			},

			_showToolBarCategorySection: function() {
				var instance = this;

				var toolbar = jQuery('.category-vocabulary-toolbar');

				var categoryPanel = instance._toolbarCategoryPanel;

				if (!instance._selectedCategoryVocabularyName) {
					instance._resetActionValues();

					categoryPanel.hide();

					instance._sendMessage('info', Liferay.Language.get('you-must-first-add-a-vocabulary'));

					instance._showToolBarCategoryVocabularySection();

					return;
				}

				categoryPanel.show();
				categoryPanel.align();

				jQuery('.category-vocabulary-category-name', categoryPanel.body).focus();

				instance._categoryVocabularyCategoryPanel.hide();
			},

			_showToolBarCategoryVocabularySection: function() {
				var instance = this;

				var categoryVocabularyCategoryPanel = instance._categoryVocabularyCategoryPanel;

				categoryVocabularyCategoryPanel.show();
				categoryVocabularyCategoryPanel.align();

				jQuery('.category-vocabulary-name', categoryVocabularyCategoryPanel.body).focus();

				instance._toolbarCategoryPanel.hide();
			},

			_unselectAllCategories: function() {
				var instance = this;

				jQuery(instance._categoryListClass).removeClass('selected');
				jQuery('div.category-vocabulary-property-row:gt(0)').remove();
			},

			_unselectAllCategoryVocabularies: function() {
				var instance = this;

				jQuery(instance._categoryVocabularyListClass).removeClass('selected');
			},

			_updateCategory: function(categoryId, categoryVocabularyId, parentCategoryId, name, categoryProperties, callback) {
				var instance = this;

				Liferay.Service.Asset.AssetCategory.updateCategory(
					{
						categoryId: categoryId,
						parentCategoryId: parentCategoryId,
						name: name,
						categoryVocabularyId: categoryVocabularyId,
						properties: categoryProperties
					},
					function(message) {
						var exception = message.exception;

						if (!exception) {
							var selectedText = instance._selectedCategory.find('> span > a');

							if (!selectedText.length) {
								selectedText.find('> span');
							}

							instance._selectedCategory.attr('data-category', name);
							selectedText.text(name);

							instance._closeEditSection();
						}
						else {
							if (exception.indexOf('DuplicateCategoryException') > -1) {
								instance._sendMessage('error', 'there-is-another-category-with-the-same-name-and-the-same-parent');
							}
							else if (exception.indexOf('NoSuchCategoryVocabularyException') > -1) {
								instance._sendMessage('error', 'that-vocabulary-does-not-exist');
							}
							else if (exception.indexOf('NoSuchCategoryException') > -1) {
								instance._sendMessage('error', 'that-parent-category-does-not-exist');
							}
							else if (exception.indexOf('auth.PrincipalException') > -1) {
								instance._sendMessage('error', 'you-do-not-have-permission-to-access-the-requested-resource');
							}
							else if (exception.indexOf('Exception') > -1) {
								instance._sendMessage('error', 'one-of-your-fields-contains-invalid-characters');
							}
						}

						if (callback) {
							callback(message);
						}
					}
				);
			},

			_updateCategoryVocabulary: function(categoryVocabularyId, categoryVocabularyName, callback) {
				Liferay.Service.Asset.AssetCategoryVocabulary.updateCategoryVocabulary(
					{
						categoryVocabularyId: categoryVocabularyId,
						name: categoryVocabularyName
					},
					callback
				);
			},

			_categoryListClass: '.category-vocabulary-categories li',
			_categoryScopeClass: '.category-vocabulary-categories',
			_layoutContainerCells: '.portlet-categories-admin .category-vocabulary-content td',
			_selectedCategoryName: null,
			_selectedCategoryVocabulary: null,
			_selectedCategoryVocabularyId: null,
			_selectedCategoryVocabularyName: null,
			_categoryVocabularyListClass: '.category-vocabulary-list li',
			_categoryVocabularyScopeClass: '.category-vocabulary-list'
		}
	);

	var droppableTag = Expanse.Droppable;

	var scrollParent = jQuery('.category-vocabulary-categories')[0];

	var draggableTag = Expanse.DragProxy.extend(
		{
			initialize: function() {
				var instance = this;

				instance._super.apply(instance, arguments);

				instance.removeInvalidHandleType('a');

				instance.goingUp = false;
				instance.lastY = 0;

				instance._scrollParent = scrollParent;

	            instance._scrollHeight = scrollParent.scrollHeight;
	            instance._clientHeight = scrollParent.clientHeight;
	            instance._xy = Dom.getXY(scrollParent);
			},

			endDrag: function(event) {
				var instance = this;

				var proxy = instance.getDragEl();

				Dom.setStyle(proxy, 'top', 0);
				Dom.setStyle(proxy, 'left', 0);

				instance._removeScrollInterval();
			},

			onDrag: function(event) {
				var instance = this;

				instance._super.apply(instance, arguments);

				var y = Event.getPageY(event);

				if (y < instance.lastY) {
					instance.goingUp = true;
				}
				else if (y > instance.lastY) {
					instance.goingUp = false;
				}

				instance.lastY = y;

				var pageY = Event.getPageY(event);
				var clientHeight = instance.getEl().clientHeight;
				var scrollTop = false;

				instance._scrollBy = (clientHeight * 2) + instance._overflow;

				if (instance.goingUp) {
					var deltaTop = instance._xy[1] + (clientHeight + instance._overflow);

					if (pageY < deltaTop) {
						scrollTop = instance._scrollParent.scrollTop - instance._scrollBy;
					}
				}
				else {
					var deltaBottom = instance._clientHeight + instance._xy[1] - (clientHeight + instance._overflow);

					if (pageY > deltaBottom) {
						scrollTop = instance._scrollParent.scrollTop + instance._scrollBy;
					}
				}

				instance._scrollTo(scrollTop);
			},

			onDragDrop: function() {
				var instance = this;

				instance._super.apply(this, arguments);

				instance._removeScrollInterval();
			},

			onDragEnter: function(event, id) {
				var instance = this;

				var target = Dom.get(id);
				var src = instance.getEl();

				if (target != src) {
					Dom.addClass(target, 'active-area');
				}
			},

			onDragOut: function(event, id) {
				var instance = this;

				var target = Dom.get(id);
				var src = instance.getEl();

				if (target != src) {
					Dom.removeClass(target, 'active-area');
				}
			},

			startDrag: function(x, y) {
				var instance = this;

				var proxy = instance.getDragEl();
				var src = instance.getEl();

				proxy.innerHTML = '';

				var clone = src.cloneNode(true);
				clone.id = '';

				proxy.appendChild(clone);

				Dom.setStyle(proxy, 'border-width', 0);
				Dom.addClass(clone, 'portlet-categories-admin-helper');
			},

			_removeScrollInterval: function() {
				var instance = this;

				if (instance._scrollInterval) {
					clearInterval(instance._scrollInterval);
				}
			},

			_scrollTo: function(scrollTop) {
				var instance = this;

				instance._currentScrollTop = scrollTop;

				instance._removeScrollInterval();

				if (scrollTop) {
					instance._scrollInterval = setInterval(
						function() {
							if ((instance._currentScrollTop < 0) || (instance._currentScrollTop > instance._scrollHeight)) {
								instance._removeScrollInterval();
							}

							instance._scrollParent.scrollTop = instance._currentScrollTop;

							DDM.refreshCache();

							if (instance.goingUp) {
								instance._currentScrollTop -= instance._scrollBy;
							}
							else {
								instance._currentScrollTop += instance._scrollBy;
							}
						},
						10
					);
				}
			},

			_overflow: 5,
			_scrollBy: 0,
			_scrollInterval: null
		}
	);

	Liferay.Portlet.AssetCategoryAdmin = AssetCategoryAdmin;
})();