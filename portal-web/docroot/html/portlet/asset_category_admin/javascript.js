AUI().add(
	'liferay-category-admin',
	function(A) {
		var Dom = Alloy.Dom;
		var Event = Alloy.Event;
		var DDM = Alloy.DragDrop;

		AssetCategoryAdmin = function(portletId) {
			var instance = this;

			var childrenContainer = jQuery(instance._categoryContainerSelector);

			instance.portletId = portletId;
			instance._container = jQuery('.vocabulary-container');

			jQuery('.vocabulary-close').click(
				function() {
					instance._closeEditSection();
				}
			);

			jQuery('.vocabulary-save-category-properties').click(
				function() {
					instance._saveCategoryProperties();
				}
			);

			instance._portletMessageContainer = jQuery('<div class="lfr-message-response" id="vocabulary-messages" />');
			instance._categoryMessageContainer = jQuery('<div class="lfr-message-response" id="vocabulary-category-messages" />');

			instance._portletMessageContainer.hide();
			instance._categoryMessageContainer.hide();

			instance._container.before(instance._portletMessageContainer);
			childrenContainer.before(instance._categoryMessageContainer);

			var buttons = jQuery('.vocabulary-buttons')
			var toolbar = jQuery('.vocabulary-toolbar');

			var addCategoryLayer = jQuery('.add-category-layer');
			var addVocabularyLayer = jQuery('.add-vocabulary-layer');

			var addCategoryButton = jQuery('.add-category-button');
			var addVocabularyButton = jQuery('.add-vocabulary-button');

			instance._toolbarCategoryPanel = new A.ContextPanel(
				{
					bodyContent: A.get('.add-category-layer'),
					trigger: '.add-category-button',
					align: {
						points: ['tr', 'br']
					}
				}
			)
			.render();

			instance._vocabularyCategoryPanel = new A.ContextPanel(
				{
					bodyContent: A.get('.add-vocabulary-layer'),
					trigger: '.add-vocabulary-button',
					align: {
						points: ['tr', 'br']
					}
				}
			)
			.render();

			var selectButton = function(button) {
				buttons.find('.button').removeClass('selected');
				jQuery(button).addClass('selected');
			};

			toolbar.find('.add-vocabulary-button').click(
				function (event) {
					instance._showToolBarVocabularySection();
				}
			);

			toolbar.find('.add-category-button').click(
				function (event) {
					instance._showToolBarCategorySection();
				}
			);

			jQuery('.permissions-categories-button').click(
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

			jQuery('.permissions-vocabulary-button').click(
				function() {
					var vocabularyName = instance._selectedVocabularyName;
					var vocabularyId = instance._selectedVocabularyId;

					if (vocabularyName && vocabularyId) {
						var portletURL = instance._createPermissionURL(
							'com.liferay.portlet.asset.model.AssetVocabulary',
							vocabularyName,
							vocabularyId);

						submitForm(document.hrefFm, portletURL.toString());
					}
					else {
						instance._showToolBarVocabularySection();
					}
				}
			);

			jQuery('#vocabulary-search-bar').change(
				function(event) {
					jQuery('#vocabulary-search-input').focus();
					instance._reloadSearch();
				}
			);

			var addCategory = function() {
				var categoryLayer = jQuery('.add-category-layer');
				var categoryName = categoryLayer.find('.vocabulary-category-name').val();
				var vocabularyId = categoryLayer.find('.vocabulary-select-list option:selected').attr('value');

				instance._hideAllMessages();
				instance._addCategory(categoryName, vocabularyId);
			};

			var addVocabulary = function() {
				var vocabularyLayer = jQuery('.add-vocabulary-layer');
				var inputVocabularyName = vocabularyLayer.find('.vocabulary-name');
				var newVocabularyName = inputVocabularyName.val();

				instance._hideAllMessages();
				instance._addVocabulary(newVocabularyName);
			};

			jQuery('input.category-save-button').click(addCategory);
			jQuery('input.vocabulary-save-button').click(addVocabulary);

			jQuery('.vocabulary-actions input').keyup(
				function(event) {
					if (event.keyCode == 13) {
						var input = jQuery(this);

						if (input.is('.vocabulary-category-name')) {
							addCategory();
						}
						else if (input.is('.vocabulary-name')) {
							addVocabulary();
						}

						return false;
					}
				}
			);

			jQuery('input.vocabulary-delete-categories-button').click(
				function() {
					if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this-category'))) {
						instance._deleteCategory(
							instance._selectedCategoryId,
							function(message) {
								var exception = message.exception;

								if (!exception) {
									instance._closeEditSection();
									instance._hideToolbarSections();
									instance._displayVocabularyCategories(instance._selectedVocabularyId);
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

			jQuery('input.vocabulary-delete-list-button').click(
				function() {
					if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this-list'))) {
						instance._deleteVocabulary(
							instance._selectedVocabularyId,
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

			jQuery('.aui-overlay input:text').keyup(
				function(event) {
					var ESC_KEY_CODE = 27;
					var keyCode = event.keyCode;

					if (keyCode == ESC_KEY_CODE) {
						instance._hideToolbarSections();
					}
				}
			);

			A.DD.DDM.on(
				'drop:enter',
				function(event) {
					var dropNode = event.drop.get('node');

					dropNode.addClass('active-area');
				}
			);

			A.DD.DDM.on(
				'drop:exit',
				function(event) {
					var dropNode = event.target.get('node');

					dropNode.removeClass('active-area');
				}
			);

			A.DD.DDM.on(
				'drop:hit',
				function(event) {
					var dragNode = event.drag.get('node');
					var dropNode = event.drop.get('node');

					var node = A.Widget.getByNode(dragNode);

					var vocabularyId = dropNode.attr('data-vocabularyid');
					var fromCategoryId = instance._getCategoryId(node);
					var fromCategoryName = instance._getCategoryName(node);

					instance._merge(fromCategoryId, fromCategoryName, 0, vocabularyId);

					instance._selectVocabulary(vocabularyId);

					dropNode.removeClass('active-area');
				}
			);

			instance._loadData();
		};

		AssetCategoryAdmin.prototype = {
			_createPermissionURL: function(modelResource, modelResourceDescription, resourcePrimKey) {
				var instance = this;

				var portletURL = Liferay.PortletURL.createPermissionURL(
					instance.portletId, modelResource, modelResourceDescription, resourcePrimKey);

				return portletURL;
			},

			_displayVocabularyCategoriesImpl: function(categories, callback) {
				var instance = this;

				var buffer = [];

				var childrenList = A.get(instance._categoryContainerSelector);
				var boundingBox = A.Node.create('<div class="vocabulary-treeview-container" id="vocabularyTreeContainer"></div>');

				childrenList.empty();
				childrenList.append(boundingBox);

				instance.treeView = new A.TreeViewDD(
					{
						boundingBox: boundingBox,
						on: {
							dropAppend: function(event) {
								var tree = event.tree;
								var fromCategoryId = instance._getCategoryId(tree.dragNode);
								var fromCategoryName = instance._getCategoryName(tree.dragNode);
								var toCategoryId = instance._getCategoryId(tree.dropNode);
								var toCategoryName = instance._getCategoryName(tree.dropNode);
								var vocabularyId = instance._selectedVocabularyId;

								instance._merge(fromCategoryId, fromCategoryName, toCategoryId, vocabularyId);
							},
							dropInsert: function(event) {
								var tree = event.tree;
								var parentNode = tree.dropNode.get('parentNode');
								var fromCategoryId = instance._getCategoryId(tree.dragNode);
								var fromCategoryName = instance._getCategoryName(tree.dragNode);
								var toCategoryId = instance._getCategoryId(parentNode);
								var toCategoryName = instance._getCategoryName(parentNode);
								var vocabularyId = instance._selectedVocabularyId;

								instance._merge(fromCategoryId, fromCategoryName, toCategoryId, vocabularyId);
							}
						},
						type: 'normal'
					}
				)
				.render();

				instance._buildCategoryTreeview(categories, 0);

				instance._reloadSearch();

				var vocabularyContainer = A.get(instance._vocabularyContainerSelector);
				var listLinks = vocabularyContainer.all('li');

				listLinks.plug(A.Plugin.Drop);

				if (callback) {
					callback();
				}
			},

			_displayList: function(callback) {
				var instance = this;

				var buffer = [];
				var list = jQuery(instance._vocabularyContainerSelector);

				instance._showLoading('.vocabulary-categories, .vocabulary-list');

				buffer.push('<ul>');

				instance._getVocabularies(
					function(vocabularies) {
						jQuery.each(
							vocabularies,
							function(i) {
								buffer.push('<li');
								buffer.push(' class="vocabulary-category results-row');

								if (i == 0) {
									buffer.push(' selected ');
								}

								buffer.push('" data-vocabulary="');
								buffer.push(this.name);
								buffer.push('" data-vocabularyId="');
								buffer.push(this.vocabularyId);
								buffer.push('"><span><a href="javascript:;">');
								buffer.push(this.name);
								buffer.push('</a></span>');
								buffer.push('</li>');
							}
						);

						buffer.push('</ul>');

						list.html(buffer.join(''));

						var firstVocabulary = jQuery(instance._vocabularyItemSelector + ':first');
						var vocabularyName = instance._getVocabularyName(firstVocabulary);
						var vocabularyId = instance._getVocabularyId(firstVocabulary);

						instance._selectedVocabularyName = vocabularyName;
						instance._selectedVocabularyId = vocabularyId;
						instance._feedVocabularySelect(vocabularies, vocabularyId);

						var listLinks = jQuery('li', list);

						listLinks.mousedown(
							function(event) {
								var vocabularyId = instance._getVocabularyId(this);

								instance._selectVocabulary(vocabularyId);
							}
						);

						var editableConfig = {
							eventType: 'dblclick',
							on: {
								contentTextChange: function(event) {
									if (!event.initial) {
										var vocabularyName = event.newVal;
										var vocabularyId = instance._selectedVocabularyId;

										var li = this.get('node').ancestor('li');

										li.setAttribute('data-vocabulary', event.newVal);

										instance._updateVocabulary(
											vocabularyId,
											vocabularyName,
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
															var vocabulary = instance._selectVocabulary(message.vocabularyId);

															instance._displayVocabularyCategories(instance._selectedVocabularyId);
														}
													);
												}
											}
										);
									}
								}
							}
						};

						AUI().ready(
							'editable',
							function(A) {
								var listEls = A.all('.vocabulary-list li span a');
								var listLength = listEls.size();

								for (var i = 0; i < listLength; i++) {
									editableConfig.node = listEls.item(i);

									new A.Editable(editableConfig);
								}
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
						var totalRendered = jQuery('div.vocabulary-property-row').length;

						if (totalRendered > total) {
							return;
						}

						jQuery.each(
							categoryProperties,
							function() {
								var baseCategoryProperty = jQuery('div.vocabulary-property-row:last');

								instance._addCategoryProperty(baseCategoryProperty, this.key, this.value);
							}
						);
					}
				);
			},

			_displayVocabularyCategories: function(vocabularyId, callback) {
				var instance = this;

				jQuery('#vocabulary-category-messages').hide();

				instance._getVocabularyCategories(
					vocabularyId,
					function(categories) {
						instance._displayVocabularyCategoriesImpl(categories, callback);
					}
				);
			},

			_addCategory: function(categoryName, vocabularyId, callback) {
				var instance = this;

				var communityPermission = instance._getPermissionsEnabled('category', 'community');
				var guestPermission = instance._getPermissionsEnabled('category', 'guest');

				var serviceParameterTypes = [
					'long',
					'java.lang.String',
					'long',
					'[Ljava.lang.String;',
					'com.liferay.portal.service.ServiceContext'
				];

				Liferay.Service.Asset.AssetCategory.addCategory(
					{
						parentCategoryId: 0,
						name: categoryName,
						vocabularyId: vocabularyId,
						properties: [],
						serviceContext: jQuery.toJSON(
							{
								communityPermissions: communityPermission,
								guestPermissions: guestPermission,
								scopeGroupId: themeDisplay.getScopeGroupId()
							}
						),
						serviceParameterTypes: jQuery.toJSON(serviceParameterTypes)
					},
					function(message) {
						var exception = message.exception;

						if (!exception && message.categoryId) {
							instance._sendMessage('success', 'your-request-processed-successfully');

							instance._selectVocabulary(vocabularyId);

							instance._displayVocabularyCategories(
								instance._selectedVocabularyId,
								function() {
									instance._hideSection('.vocabulary-edit');
								}
							);

							instance._resetActionValues();
							instance._hideToolbarSections();

							if (callback) {
								callback(categoryName, vocabularyId);
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
							else if (exception.indexOf('NoSuchVocabularyException') > -1) {
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

				var baseCategoryProperty = jQuery('div.vocabulary-property-row:last');
				var newCategoryProperty = baseCategoryProperty.clone();

				newCategoryProperty.find('.category-property-key').val(key);
				newCategoryProperty.find('.category-property-value').val(value);
				newCategoryProperty.insertAfter(baseNode);
				newCategoryProperty.show();

				if (!key && !value) {
					newCategoryProperty.find('input:first').addClass('lfr-auto-focus');
				}

				instance._attachCategoryPropertyIconEvents(newCategoryProperty);
			},

			_addVocabulary: function(vocabularyName, callback) {
				var instance = this;

				var communityPermission = instance._getPermissionsEnabled('vocabulary', 'community');
				var guestPermission = instance._getPermissionsEnabled('vocabulary', 'guest');

				Liferay.Service.Asset.AssetVocabulary.addVocabulary(
					{
						name: vocabularyName,
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
									var vocabulary = instance._selectVocabulary(message.vocabularyId);

									instance._displayVocabularyCategories(instance._selectedVocabularyId);

									if (vocabulary.length) {
										jQuery(instance._vocabularyContainerSelector).scrollTop(vocabulary.offset().top);
									}
								}
							);

							instance._resetActionValues();

							if (callback) {
								callback(vocabulary);
							}
						}
						else {
							var errorKey = '';

							if (exception.indexOf('DuplicateVocabularyException') > -1) {
								errorKey = 'that-vocabulary-already-exists';
							}
							else if (exception.indexOf('VocabularyNameException') > -1) {
								errorKey = 'one-of-your-fields-contains-invalid-characters';
							}
							else if (exception.indexOf('NoSuchVocabularyException') > -1) {
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

				var categoriesScope = jQuery(instance._categoryContainerSelector);

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

			_buildCategoryTreeview: function(categories, parentCategoryId) {
				var instance = this;

				var children = instance._filterCategory(categories, parentCategoryId);

				jQuery.each(
					children,
					function(i) {
						var categoryId = this.categoryId;
						var hasChild = instance._filterCategory(categories, categoryId).length;

						var node = new A.TreeNode(
							{
								alwaysShowHitArea: false,
								id: 'categoryNode' + this.categoryId,
								label: this.name,
								leaf: false,
								on: {
									select: function(event) {
										var nodeId = event.target.get('id');
										var categoryId = nodeId.replace('categoryNode', '');
										var editContainer = jQuery('.vocabulary-edit');

										instance._selectCategory(categoryId);
										instance._showSection(editContainer);
									}
								}
							}
						);

						var parentId = 'categoryNode' + parentCategoryId;
						var parentNode = instance.treeView.getNodeById(parentId) || instance.treeView;

						parentNode.appendChild(node);

						if (hasChild) {
							instance._buildCategoryTreeview(categories, categoryId);
						}
					}
				);

				return children.length;
			},

			_buildCategoryProperties: function() {
				var instance = this;

				var buffer = [];

				jQuery('.vocabulary-property-row:visible').each(
					function(i, o) {
						var categoryPropertyRow = jQuery(this);
						var key = categoryPropertyRow.find('input.category-property-key').val();
						var value = categoryPropertyRow.find('input.category-property-value').val();
						var rowValue = [key, ':', value, ','].join('');

						buffer.push(rowValue);
					}
				);

				return buffer.join('');
			},

			_closeEditSection: function() {
				var instance = this;

				instance._hideSection('.vocabulary-edit');

				jQuery(instance._categoryContainerCellsSelector).width('auto');
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

			_deleteVocabulary: function(vocabularyId, callback) {
				var instance = this;

				Liferay.Service.Asset.AssetVocabulary.deleteVocabulary(
					{
						vocabularyId: vocabularyId
					},
					callback
				);
			},

			_feedVocabularySelect: function(vocabularies, defaultValue) {
				var instance = this;

				var select = jQuery('select.vocabulary-select-list');
				var buffer = [];

				jQuery.each(
					vocabularies,
					function(i) {
						var selected = (this.vocabularyId == defaultValue);

						buffer.push('<option');
						buffer.push(selected ? ' selected ' : '');
						buffer.push(' value="');
						buffer.push(this.vocabularyId);
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

				return A.Widget.getByNode('#categoryNode' + categoryId);
			},

			_getCategoryId: function(node) {
				var instance = this;

				var nodeId = node.get('id') || '';
				var categoryId = nodeId.replace('categoryNode', '');

				return categoryId;
			},

			_getCategoryName: function(node) {
				var instance = this;

				return node.get('label');
			},

			_getParentCategoryId: function(node) {
				var instance = this;

				var parentNode = node.get('parentNode');

				return instance._getCategoryId(parentNode);
			},

			_getPermissionsEnabled: function(vocabularyType, type) {
				var buffer = [];
				var permissionsActions = jQuery('.'+vocabularyType+'-permissions-actions');
				var permissions = permissionsActions.find('[name$='+type+'Permissions]:checked');

				permissions.each(
					function(i, n) {
						buffer.push(this.value);
					}
				);

				return buffer.join(',');
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

			_getVocabularies: function(callback) {
				var instance = this;

				Liferay.Service.Asset.AssetVocabulary.getGroupVocabularies(
					{
						groupId: themeDisplay.getScopeGroupId()
					},
					callback
				);
			},

			_getVocabulary: function(vocabularyId) {
				var instance = this;

				return jQuery('li[data-vocabularyId=' + vocabularyId + ']')
			},

			_getVocabularyCategories: function(vocabularyId, callback) {
				var instance = this;

				instance._showLoading(instance._categoryContainerSelector);

				Liferay.Service.Asset.AssetCategory.getVocabularyCategories(
					{
						vocabularyId: vocabularyId
					},
					callback
				);
			},

			_getVocabularyId: function(exp) {
				var instance = this;

				return jQuery(exp).attr('data-vocabularyId');
			},

			_getVocabularyName: function(exp) {
				var instance = this;

				return jQuery(exp).attr('data-vocabulary');
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

				jQuery(exp).parent().removeClass('vocabulary-editing-tag');
			},

			_hideToolbarSections: function() {
				var instance = this;

				instance._toolbarCategoryPanel.hide();
				instance._vocabularyCategoryPanel.hide();
			},

			_loadData: function() {
				var instance = this;

				instance._closeEditSection();

				instance._displayList(
					function() {
						instance._displayVocabularyCategories(instance._selectedVocabularyId);
					}
				);
			},

			_merge: function(fromCategoryId, fromCategoryName, toCategoryId, vocabularyId) {
				var instance = this;

				var categoryProperties = instance._buildCategoryProperties();

				vocabularyId = vocabularyId || instance._selectedVocabularyId;

				instance._updateCategory(fromCategoryId, vocabularyId, toCategoryId, fromCategoryName, categoryProperties);
			},

			_reloadSearch: function() {
				var	instance = this;

				var options = {};
				var selected = jQuery('#vocabulary-select-search').val();
				var input = jQuery('#vocabulary-search-input');
				var categoryList = jQuery(instance._categoryItemSelector);
				var vocabularyList = jQuery(instance._vocabularyItemSelector);

				input.unbind('keyup');

				if (/vocabularies/.test(selected)) {
					options = {
						list: vocabularyList,
						filter: jQuery('a', vocabularyList)
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

				if (jQuery('div.vocabulary-property-row').length > 2) {
					categoryProperty.remove();
				}
			},

			_resetActionValues: function() {
				var instance = this;

				jQuery('.vocabulary-actions input:text').val('');

				instance._vocabularyCategoryPanel.hide();
			},

			_saveCategoryProperties: function() {
				var instance = this;

				var categoryId = instance._selectedCategoryId;
				var categoryName = jQuery('input.category-name').val() || instance._selectedCategoryName;
				var parentCategoryId = instance._selectedParentCategoryId;
				var categoryProperties = instance._buildCategoryProperties();
				var vocabularyId = instance._selectedVocabularyId;

				instance._updateCategory(categoryId, vocabularyId, parentCategoryId, categoryName, categoryProperties);
				instance._displayVocabularyCategories(vocabularyId);
			},

			_selectCurrentVocabulary: function(value) {
				var instance = this;

				var option = jQuery('select.vocabulary-select-list option[value="' + value + '"]');
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
				instance._selectedParentCategoryId = parentCategoryId || 0;

				if (!categoryId) {
					return category;
				}

				var properties = jQuery('div.vocabulary-property-row:gt(0)');
				var editContainer = jQuery('.vocabulary-edit');
				var categoryNameField = editContainer.find('input.category-name');

				properties.remove();

				categoryNameField.val(categoryName);
				instance._displayCategoryProperties(categoryId);

				instance._selectedCategory = category;

				return category;
			},

			_selectVocabulary: function(vocabularyId) {
				var instance = this;

				var vocabulary = instance._getVocabulary(vocabularyId);
				var vocabularyName = instance._getVocabularyName(vocabulary);

				if (vocabulary.is('.selected')) {
					return vocabulary;
				}

				instance._hideAllMessages();
				instance._selectedVocabularyName = vocabularyName;
				instance._selectedVocabularyId = vocabularyId;
				instance._selectCurrentVocabulary(vocabularyId);

				instance._unselectAllVocabularies();
				instance._closeEditSection();

				vocabulary.addClass('selected');
				instance._displayVocabularyCategories(instance._selectedVocabularyId);

				return vocabulary;
			},

			_sendMessage: function(type, key, output, noAutoHide) {
				var instance = this;

				var output = jQuery(output || '#vocabulary-messages');
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
									instance._toolbarCategoryPanel.refreshAlign();
									instance._vocabularyCategoryPanel.refreshAlign();
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
					element.parent().addClass('vocabulary-editing-tag');
					element.find('input:first').focus();
					jQuery(instance._categoryContainerCellsSelector).width('33%');
				}
			},

			_showToolBarCategorySection: function() {
				var instance = this;

				var toolbar = jQuery('.vocabulary-toolbar');

				var categoryPanel = instance._toolbarCategoryPanel;

				if (!instance._selectedVocabularyName) {
					instance._resetActionValues();

					categoryPanel.hide();

					instance._sendMessage('info', Liferay.Language.get('you-must-first-add-a-vocabulary'));

					instance._showToolBarVocabularySection();

					return;
				}

				categoryPanel.refreshAlign();

				instance._vocabularyCategoryPanel.hide();
			},

			_showToolBarVocabularySection: function() {
				var instance = this;

				var vocabularyCategoryPanel = instance._vocabularyCategoryPanel;

				vocabularyCategoryPanel.refreshAlign();

				instance._toolbarCategoryPanel.hide();
			},

			_unselectAllVocabularies: function() {
				var instance = this;

				jQuery(instance._vocabularyItemSelector).removeClass('selected');
			},

			_updateCategory: function(categoryId, vocabularyId, parentCategoryId, name, categoryProperties, callback) {
				var instance = this;

				Liferay.Service.Asset.AssetCategory.updateCategory(
					{
						categoryId: categoryId,
						parentCategoryId: parentCategoryId,
						name: name,
						vocabularyId: vocabularyId,
						properties: categoryProperties,
						serviceContext: null
					},
					function(message) {
						var exception = message.exception;

						if (!exception) {
							instance._selectedCategory.set('label', name);

							instance._closeEditSection();
						}
						else {
							if (exception.indexOf('DuplicateCategoryException') > -1) {
								instance._sendMessage('error', 'there-is-another-category-with-the-same-name-and-the-same-parent');
							}
							else if (exception.indexOf('NoSuchVocabularyException') > -1) {
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

			_updateVocabulary: function(vocabularyId, vocabularyName, callback) {
				Liferay.Service.Asset.AssetVocabulary.updateVocabulary(
					{
						vocabularyId: vocabularyId,
						name: vocabularyName,
						serviceContext: null
					},
					callback
				);
			},

			_categoryItemSelector: '.vocabulary-categories li',
			_categoryContainerCellsSelector: '.portlet-categories-admin .vocabulary-content td',
			_categoryContainerSelector: '.vocabulary-categories',
			_selectedCategoryName: null,
			_selectedVocabulary: null,
			_selectedVocabularyId: null,
			_selectedVocabularyName: null,
			_vocabularyItemSelector: '.vocabulary-list li',
			_vocabularyContainerSelector: '.vocabulary-list'
		};

		Liferay.Portlet.AssetCategoryAdmin = AssetCategoryAdmin;
	},
	'',
	{
		requires: ['context-panel', 'dd', 'tree-view']
	}
);