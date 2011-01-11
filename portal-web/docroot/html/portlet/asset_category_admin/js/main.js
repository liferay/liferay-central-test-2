AUI().add(
	'liferay-category-admin',
	function(A) {
		var ACTION_ADD = 0;

		var ACTION_EDIT = 1;

		var ACTION_VIEW = 2;

		var CATEGORY = 0;

		var CSS_VOCABULARY_EDIT_CATEGORY = 'vocabulary-content-edit-category';

		var JSON = A.JSON;

		var EXC_NO_SUCH_VOCABULARY = 'NoSuchVocabularyException';

		var EXC_PRINCIPAL = 'auth.PrincipalException';

		var EXC_VOCABULARY_NAME = 'VocabularyNameException';

		var MSG_TYPE_ERROR = 'error';

		var MSG_TYPE_SUCCESS = 'success';

		var Node = A.Node;

		var VOCABULARY = 1;

		var AssetCategoryAdmin = A.Component.create(
			{
				NAME: 'assetcategoryadmin',

				EXTENDS: A.Base,

				constructor: function() {
					AssetCategoryAdmin.superclass.constructor.apply(this, arguments);
				},

				prototype: {
					initializer: function(config) {
						var instance = this;

						var childrenContainer = A.one(instance._categoryContainerSelector);

						instance.portletId = config.portletId;
						instance._prefixedPortletId = ['_', config.portletId, '_'].join('');
						instance._container = A.one('.vocabulary-container');
						instance._vocabularyContent = A.one('.vocabulary-content');
						instance._categoryViewContainer = A.one('.category-view');
						instance._categoryViewDataContainer = A.one('.category-view-data');

						instance._portletMessageContainer = Node.create('<div class="aui-helper-hidden lfr-message-response" id="vocabulary-messages" />');
						instance._categoryMessageContainer = Node.create('<div class="aui-helper-hidden lfr-message-response" id="vocabulary-category-messages" />');

						instance._container.placeBefore(instance._portletMessageContainer);
						childrenContainer.placeBefore(instance._categoryMessageContainer);

						A.one('.category-view-close').on('click', instance._closeEditSection, instance);

						A.one('#vocabulary-select-search').on(
							'change',
							function(event) {
								var searchInput = A.one('#vocabulary-search-input');

								if (searchInput) {
									searchInput.focus();
								}

								instance._reloadSearch();
							}
						);

						instance._categoryViewContainer.on('click', instance._onCategoryViewContainerClick, instance);

						instance._hideMessageTask = new A.DelayedTask(
							function() {
								instance._portletMessageContainer.hide();
							}
						);

						A.one(instance._vocabularyContainerSelector).on('mousedown', instance._onVocabularyList, instance);
						A.one('#add-category-button').on('click', instance._onShowCategoryPanel, instance, ACTION_ADD);
						A.one('#add-vocabulary-button').on('click', instance._onShowVocabularyPanel, instance, ACTION_ADD);

						instance._loadData();

						instance.after('drop:hit', instance._afterDragDrop);
						instance.after('drop:enter', instance._afterDragEnter);
						instance.after('drop:exit', instance._afterDragExit);
					},

					_addCategory: function(form) {
						var instance = this;

						var uri = form.attr('action');

						var config = {
							form: form.getDOM(),
							dataType: 'json',
							on: {
								success: function(event, id, obj) {
									var response = this.get('responseData');

									instance._onCategoryAddSuccess(response);
								},
								failure: function(event, id, obj) {
									instance._onCategoryAddFailure(obj);
								}
							}
						};

						A.io.request(uri, config);
					},

					_addCategoryProperty: function(baseNode, key, value) {
						var instance = this;

						var baseCategoryProperty = A.one('div.vocabulary-property-row');

						if (baseCategoryProperty) {
							var newCategoryProperty = baseCategoryProperty.clone();

							var propertyKeyNode = newCategoryProperty.one('.category-property-key');
							var propertyValueNode = newCategoryProperty.one('.category-property-value');

							if (propertyKeyNode) {
								propertyKeyNode.val(key);
							}

							if (propertyValueNode) {
								propertyValueNode.val(value);
							}

							baseNode.placeAfter(newCategoryProperty);

							newCategoryProperty.show();

							if (!key && !value) {
								newCategoryProperty.one('input').focus();
							}

							instance._attachCategoryPropertyIconEvents(newCategoryProperty);
						}
					},

					_addVocabulary: function(form) {
						var instance = this;

						var uri = form.attr('action');

						var config = {
							form: form.getDOM(),
							dataType: 'json',
							on: {
								success: function(event, id, obj) {
									var response = this.get('responseData');

									instance._onVocabularyAddSuccess(response);
								},
								failure: function(event, id, obj) {
									instance._onVocabularyAddFailure(obj);
								}
							}
						};

						A.io.request(uri, config);
					},

					_afterDragDrop: function(event) {
						var instance = this;

						var dragNode = event.drag.get('node');
						var dropNode = event.drop.get('node');

						var node = A.Widget.getByNode(dragNode);

						var vocabularyId = dropNode.attr('data-vocabularyid');
						var fromCategoryId = instance._getCategoryId(node);
						var fromCategoryName = instance._getCategoryName(node);

						instance._merge(fromCategoryId, fromCategoryName, 0, vocabularyId);

						instance._selectVocabulary(vocabularyId);

						dropNode.removeClass('active-area');
					},

					_afterDragEnter: function(event) {
						var instance = this;

						var dropNode = event.drop.get('node');

						dropNode.addClass('active-area');
					},

					_afterDragExit: function(event) {
						var instance = this;

						var dropNode = event.target.get('node');

						dropNode.removeClass('active-area');
					},

					_alignPanel: function(panel){
						var instance = this;

						var vocabularyContent = instance._vocabularyContent;
						var vocabularyContentXY = vocabularyContent.getXY();

						panel.set("xy", vocabularyContentXY);
					},

					_alternateRows: function() {
						var instance = this;

						var categoriesScope = A.all(instance._categoryContainerSelector);

						var allRows = categoriesScope.all('li');

						allRows.removeClass('alt');
						allRows.odd().addClass('alt');
					},

					_attachCategoryPropertyIconEvents: function(categoryProperty) {
						var instance = this;

						var addProperty = categoryProperty.one('.add-category-property');
						var deleteProperty = categoryProperty.one('.delete-category-property');

						if (addProperty) {
							addProperty.on(
								'click',
								function() {
									instance._addCategoryProperty(categoryProperty, '', '');
								}
							);
						}

						if (deleteProperty) {
							deleteProperty.on(
								'click',
								function() {
									instance._removeCategoryProperty(categoryProperty);
								}
							);
						}
					},

					_bindCloseEvent: function(contextPanel) {
						var instance = this;

						contextPanel.get('boundingBox').on(
							'key',
							A.bind(contextPanel.hide, contextPanel),
							'up:27'
						);
					},

					_buildCategoryTreeview: function(categories, parentCategoryId) {
						var instance = this;

						var children = instance._filterCategory(categories, parentCategoryId);

						A.each(
							children,
							function(item, index, collection) {
								var categoryId = item.categoryId;
								var hasChild = instance._filterCategory(categories, categoryId).length;

								var node = new A.TreeNode(
									{
										alwaysShowHitArea: false,
										id: 'categoryNode' + item.categoryId,
										label: item.name,
										leaf: false,
										on: {
											select: function(event) {
												var nodeId = event.target.get('id');
												var categoryId = nodeId.replace('categoryNode', '');
												var viewContainer = instance._categoryViewContainer;
												var viewDataContainer = instance._categoryViewDataContainer;

												instance._selectCategory(categoryId);
												instance._showLoading(viewDataContainer);
												instance._vocabularyContent.addClass(CSS_VOCABULARY_EDIT_CATEGORY);
												instance._showSection(viewContainer);

												var categoryURL = instance._createURL(CATEGORY, ACTION_VIEW);

												var config = {
													dataType: 'html',
													on: {
														success: function(event, id, obj) {
															var response = this.get('responseData');

															instance._onCategoryViewSuccess(response);
														},
														failure: function(event, id, obj) {
															instance._onCategoryViewFailure(obj);
														}
													}
												};

												A.io.request(categoryURL.toString(), config);
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

						A.all('.vocabulary-property-row').each(
							function(item, index, collection) {
								if (!item.hasClass('aui-helper-hidden')) {
									var keyNode = item.one('input.category-property-key');
									var valueNode = item.one('input.category-property-value');

									if (keyNode && valueNode) {
										var rowValue = [keyNode.val(), ':', valueNode.val(), ','].join('');

										buffer.push(rowValue);
									}
								}
							}
						);

						return buffer.join('');
					},

					_closeEditSection: function() {
						var instance = this;

						instance._hideSection(instance._categoryViewContainer);

						instance._vocabularyContent.removeClass(CSS_VOCABULARY_EDIT_CATEGORY);
					},

					_createCategoryPanelAdd: function(){
						var instance = this;

						instance._categoryPanelAdd = new A.Dialog(
							{
								title: Liferay.Language.get('add-category'),
								resizable: false,
								width: '550px',
								zIndex: 1000
							}
						).render();

						/**
						 * visible: false in configuration properties does not work
						 */
						instance._hidePanel(instance._categoryPanelAdd);

						instance._bindCloseEvent(instance._categoryPanelAdd);

						instance._categoryPanelAdd.after(
							'visibleChange',
							function(event) {
								if (!event.newVal) {
									if (instance._categoryFormAdd){
										instance._categoryFormAdd.reset();
									}

									instance._hideFloatingPanels(event);
								}
							}
						);

						return instance._categoryPanelAdd;
					},

					_createCategoryTreeView: function(categories){
						var instance = this;

						var childrenList = A.one(instance._categoryContainerSelector);
						var boundingBox = Node.create('<div class="vocabulary-treeview-container" id="vocabularyTreeContainer"></div>');

						childrenList.empty();
						childrenList.append(boundingBox);

						instance.treeView = new VocabularyTree(
							{
								boundingBox: boundingBox,
								on: {
									dropAppend: function(event) {
										var tree = event.tree;
										var fromCategoryId = instance._getCategoryId(tree.dragNode);
										var fromCategoryName = instance._getCategoryName(tree.dragNode);
										var toCategoryId = instance._getCategoryId(tree.dropNode);
										var vocabularyId = instance._selectedVocabularyId;

										instance._merge(fromCategoryId, fromCategoryName, toCategoryId, vocabularyId);
									},
									dropInsert: function(event) {
										var tree = event.tree;
										var parentNode = tree.dropNode.get('parentNode');
										var fromCategoryId = instance._getCategoryId(tree.dragNode);
										var fromCategoryName = instance._getCategoryName(tree.dragNode);
										var toCategoryId = instance._getCategoryId(parentNode);
										var vocabularyId = instance._selectedVocabularyId;

										instance._merge(fromCategoryId, fromCategoryName, toCategoryId, vocabularyId);
									}
								},
								type: 'normal'
							}
						).render();

						instance._buildCategoryTreeview(categories, 0);
					},

					_createPermissionURL: function(modelResource, modelResourceDescription, resourcePrimKey) {
						var instance = this;

						var portletURL = Liferay.PortletURL.createPermissionURL(
							instance.portletId,
							modelResource,
							modelResourceDescription,
							resourcePrimKey
						);

						return portletURL;
					},

					_createVocabularyPanelAdd: function(){
						var instance = this;

						instance._vocabularyPanelAdd = new A.Dialog(
							{
								title: Liferay.Language.get('add-vocabulary'),
								resizable: false,
								width: '550px',
								zIndex: 1000
							}
						).render();

						/**
						 * visible: false in configuration properties does not work
						 */
						instance._hidePanel(instance._vocabularyPanelAdd);

						instance._bindCloseEvent(instance._vocabularyPanelAdd);

						instance._vocabularyPanelAdd.after(
							'visibleChange',
							function(event) {
								if (!event.newVal) {
									if (instance._vocabularyFormAdd){
										instance._vocabularyFormAdd.reset();
									}

									instance._hideFloatingPanels(event);
								}
							}
						);

						return instance._vocabularyPanelAdd;
					},

					_createPanelEdit: function(config){
						var instance = this;

						var defaultConfig = {
							resizable: false,
							width: '550px',
							zIndex: 1000
						};

						if(A.Lang.isObject(config)){
							config = A.merge(defaultConfig, config);
						}
						else {
							config = defaultConfig;
						}

						instance._panelEdit = new A.Dialog(config).render();

						/**
						 * visible: false in configuration properties does not work
						 */
						instance._hidePanel(instance._panelEdit);

						instance._bindCloseEvent(instance._panelEdit);

						instance._panelEdit.after(
							'visibleChange',
							function(event) {
								if (!event.newVal) {
									var body = instance._panelEdit.getStdModNode(A.WidgetStdMod.BODY);
									body.empty();

									instance._hideFloatingPanels(event);
								}
							}
						);

						return instance._panelEdit;
					},

					_createPanelPermissions: function(){
						var instance = this;

						var panelPermissionsChange = instance._panelPermissionsChange;

						if (!panelPermissionsChange){
							instance._permissionsPanelContentId = A.guid();
							instance._permissionsPanelMaskId = A.guid();

							panelPermissionsChange = new A.Dialog(
								{
									bodyContent: [
										'<div class="permissions-container">',
										'<div id="', instance._permissionsPanelMaskId, '" class="permissions-container-mask loading-animation"></div>',
										'<iframe frameborder="0" id="', instance._permissionsPanelContentId, '" ',
											'width="100%" height="100%" scrolling="', A.UA.ie > 0 ? 'auto' : 'no', '"></iframe>',
										'</div>'
									].join(''),
									cssClass: 'permissions-change',
									height: '400',
									title: Liferay.Language.get('edit-permissions'),
									xy: [-10000, -10000],
									width: '600'
								}
							).render();

							instance._panelPermissionsChange = panelPermissionsChange;

							instance._permissionsPanelContentEl = A.one('#' + instance._permissionsPanelContentId);
							instance._permissionsPanelContentEl.on('load', instance._onPermissionsPanelLoad, instance);

							instance._permissionsPanelMaskEl = A.one('#' + instance._permissionsPanelMaskId);
						}

						return panelPermissionsChange;
					},

					_createURL: function(type, action) {
						var instance = this;

						var path = '/asset_category_admin/';
						var url = Liferay.PortletURL.createRenderURL();

						url.setPortletId(instance.portletId);
						url.setWindowState('exclusive');

						if (type == VOCABULARY) {
							path += 'edit_vocabulary';

							if (action === ACTION_EDIT){
								url.setParameter('vocabularyId', instance._selectedVocabularyId);
							}
						}
						else if (type == CATEGORY) {
							url.setParameter('vocabularyId', instance._selectedVocabularyId);

							if (action === ACTION_ADD){
								path += 'edit_category';
							}
							else if (action === ACTION_EDIT){
								path += 'edit_category';
								url.setParameter('categoryId', instance._selectedCategoryId);
							}
							else if (action === ACTION_VIEW){
								path += 'view_category';
								url.setParameter('categoryId', instance._selectedCategoryId);
							}
						}

						url.setParameter('struts_action', path);

						return url;
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

					_displayVocabularyCategoriesImpl: function(categories, callback) {
						var instance = this;

						if (instance.treeView) {
							instance.treeView.destroy();
						}

						instance._createCategoryTreeView(categories);

						if (categories.length <= 0){
							var categoryMessages = A.one('#vocabulary-category-messages');

							categoryMessages.removeClass('portlet-msg-error').removeClass('portlet-msg-success');
							categoryMessages.addClass('portlet-msg-info');
							categoryMessages.html(Liferay.Language.get('there-are-no-categories'));
							categoryMessages.show();
						}

						instance._reloadSearch();

						var vocabularyContainer = A.one(instance._vocabularyContainerSelector);
						var listLinks = vocabularyContainer.all('li');

						listLinks.unplug(A.Plugin.Drop);

						listLinks.plug(
							A.Plugin.Drop,
							{
								bubbleTargets: [instance, instance.treeView]
							}
						);

						if (callback) {
							callback();
						}
					},

					_displayList: function(callback) {
						var instance = this;

						var buffer = [];
						var list = A.one(instance._vocabularyContainerSelector);

						instance._showLoading('.vocabulary-categories, .vocabulary-list');

						buffer.push('<ul>');

						instance._getVocabularies(
							function(vocabularies) {
								instance._vocabularies = vocabularies;

								A.each(
									vocabularies,
									function(item, index, collection) {
										buffer.push('<li');
										buffer.push(' class="vocabulary-category results-row');

										if (index == 0) {
											buffer.push(' selected ');
										}

										buffer.push('" data-vocabulary="');
										buffer.push(item.name);
										buffer.push('" data-vocabularyId="');
										buffer.push(item.vocabularyId);
										buffer.push('">');

										buffer.push('<div class="vocabulary-content-wrapper">');
										buffer.push('<div class="vocabulary-item-container">');
										buffer.push('<div class="vocabulary-item vocabulary-item-column">');
										buffer.push('<a href="javascript:;"');
										buffer.push('" data-vocabularyId="');
										buffer.push(item.vocabularyId);
										buffer.push('">');
										buffer.push(item.name);
										buffer.push('</a>');
										buffer.push('</div>');
										buffer.push('<div class="vocabulary-item-actions vocabulary-item-column">');
										buffer.push('<span class="vocabulary-item-actions-container">');
										buffer.push('<a href="javascript:;">');
										buffer.push('<span class="vocabulary-item-actions-trigger"');
										buffer.push('" data-vocabularyId="');
										buffer.push(item.vocabularyId);
										buffer.push('"></span>');
										buffer.push('</a>' )
										buffer.push('</span>');
										buffer.push('</div>');
										buffer.push('</div>');
										buffer.push('</div>');

									    
										buffer.push('</li>');
									}
								);

								buffer.push('</ul>');

								list.html(buffer.join(''));

								var firstVocabulary = A.one(instance._vocabularyItemSelector);
								var vocabularyName = instance._getVocabularyName(firstVocabulary);
								var vocabularyId = instance._getVocabularyId(firstVocabulary);

								instance._selectedVocabularyName = vocabularyName;
								instance._selectedVocabularyId = vocabularyId;
								
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
									categoryProperties = [{key: '', value: ''}];
								}

								var total = categoryProperties.length;
								var totalRendered = A.all('div.vocabulary-property-row').size();

								if (totalRendered > total) {
									return;
								}

								A.each(
									categoryProperties,
									function(item, index, collection) {
										var baseCategoryRows = A.all('div.vocabulary-property-row');
										var lastIndex = baseCategoryRows.size() - 1;
										var baseRow = baseCategoryRows.item(lastIndex);

										instance._addCategoryProperty(baseRow, item.key, item.value);
									}
								);
							}
						);
					},

					_displayVocabularyCategories: function(vocabularyId, callback) {
						var instance = this;

						var categoryMessages = A.one('#vocabulary-category-messages');

						if (categoryMessages) {
							categoryMessages.hide();
						}

						instance._getVocabularyCategories(
							vocabularyId,
							function(categories) {
								instance._displayVocabularyCategoriesImpl(categories, callback);
							}
						);
					},

					_initializeCategoryPanelAdd: function(callback) {
						var instance = this;

						var categoryFormAdd = instance._categoryPanelAdd.get('contentBox').one('form.update-category-form');

						categoryFormAdd.detach('submit');

						categoryFormAdd.on('submit', instance._onCategoryFormSubmit, instance, categoryFormAdd);

						var closeButton = categoryFormAdd.one('.aui-button-input-cancel');

						closeButton.on('click', instance._onCategoryAddButtonClose, instance);

						instance._categoryFormAdd = categoryFormAdd;

						if (callback){
							callback.call(instance);
						}
					},

					_initializeCategoryPanelEdit: function(){
						var instance = this;

						var categoryFormEdit = instance._panelEdit.get('contentBox').one('form.update-category-form');

						categoryFormEdit.detach('submit');

						categoryFormEdit.on('submit', instance._onCategoryFormSubmit, instance, categoryFormEdit);

						var closeButton = categoryFormEdit.one('.aui-button-input-cancel');

						closeButton.on('click', function(event, panel){
							instance._hidePanel(panel);
						}, instance, instance._panelEdit);

						var buttonDeleteCategory = categoryFormEdit.one('#category-delete-button');

						if (buttonDeleteCategory){
							buttonDeleteCategory.on(
								'click',
								instance._onCategoryDelete,
								instance
							);
						}

						var buttonChangeCategoryPermissions = categoryFormEdit.one('#category-change-permissions');

						if (buttonChangeCategoryPermissions){
							buttonChangeCategoryPermissions.on(
								'click',
								instance._onCategoryChangePermissions,
								instance
							);
						}

						var inputCategoryNameNode = categoryFormEdit.one('.category-name input');

						Liferay.Util.focusFormField(inputCategoryNameNode);
					},

					_initializeVocabularyPanelAdd: function(callback) {
						var instance = this;

						var vocabularyFormAdd = instance._vocabularyPanelAdd.get('contentBox').one('form.update-vocabulary-form');

						vocabularyFormAdd.detach('submit');

						vocabularyFormAdd.on('submit', instance._onVocabularyFormSubmit, instance, vocabularyFormAdd);

						var closeButton = vocabularyFormAdd.one('.aui-button-input-cancel');

						closeButton.on('click', function(event, panel){
							instance._hidePanel(panel);
						}, instance, instance._vocabularyPanelAdd);

						instance._vocabularyFormAdd = vocabularyFormAdd;

						if (callback){
							callback.call(instance);
						}
					},

					_initializeVocabularyPanelEdit: function(callback) {
						var instance = this;

						var vocabularyFormEdit = instance._panelEdit.get('contentBox').one('form.update-vocabulary-form');

						vocabularyFormEdit.detach('submit');

						vocabularyFormEdit.on('submit', instance._onVocabularyFormSubmit, instance, vocabularyFormEdit);

						var closeButton = vocabularyFormEdit.one('.aui-button-input-cancel');

						closeButton.on('click', function(event, panel){
							instance._hidePanel(panel);
						}, instance, instance._panelEdit);

						var buttonDeleteVocabulary = vocabularyFormEdit.one('#vocabulary-delete-button');

						if (buttonDeleteVocabulary){
							buttonDeleteVocabulary.on(
								'click',
								instance._onVocabularyDelete,
								instance
							);
						}
						
						var buttonChangeVocabularyPermissions = vocabularyFormEdit.one('#vocabulary-change-permissions');

						if (buttonChangeVocabularyPermissions){
							buttonChangeVocabularyPermissions.on(
								'click',
								instance._onVocabularyChangePermissions,
								instance
							);
						}

						var inputVocabularyEditNameNode = vocabularyFormEdit.one('.vocabulary-name input');

						Liferay.Util.focusFormField(inputVocabularyEditNameNode);
					},

					_feedVocabularySelect: function(vocabularies, selectedVocabularyId) {
						var instance = this;

						if (instance._categoryFormAdd){
							var selectEl = instance._categoryFormAdd.one('.vocabulary-select-list');

							if (selectEl) {
								selectedVocabularyId = parseInt(selectedVocabularyId, 10);

								selectEl.empty();

								A.each(
									vocabularies,
									function(item, index, collection) {
										var vocabularyEl = document.createElement('option');
										
										if( item.vocabularyId == selectedVocabularyId ){
											vocabularyEl.selected = true;
										}
										
										vocabularyEl.value = item.vocabularyId;

										var vocabularyTextEl = document.createTextNode(item.name);
										vocabularyEl.appendChild(vocabularyTextEl);

										selectEl.appendChild(vocabularyEl);
									}
									);
							}
						}
					},

					_filterCategory: function(categories, parentCategoryId) {
						var instance = this;

						return A.Array.filter(
							categories,
							function(item, index, collection) {
								return (item.parentCategoryId == parentCategoryId);
							}
						);
					},

					_focusCategoryPanelAdd: function(){
						var instance = this;

						var inputCategoryAddNameNode = instance._inputCategoryNameNode ||
							instance._categoryFormAdd.one('.category-name input');

						Liferay.Util.focusFormField(inputCategoryAddNameNode);
					},

					_focusVocabularyPanelAdd: function(){
						var instance = this;

						var inputVocabularyAddNameNode = instance._inputVocabularyAddNameNode ||
							instance._vocabularyFormAdd.one('.vocabulary-name input');

						Liferay.Util.focusFormField(inputVocabularyAddNameNode);
					},

					_getCategory: function(categoryId) {
						var instance = this;

						return A.Widget.getByNode('#categoryNode' + categoryId);
					},

					_getCategoryId: function(node) {
						var instance = this;

						var nodeId = node.get('id') || '';
						var categoryId = nodeId.replace('categoryNode', '');

						if (A.Lang.isGuid(categoryId)) {
							categoryId = '';
						}

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
						var instance = this;

						var buffer = [];
						var permissionsActions = A.one('.' + vocabularyType + '-permissions-actions');
						var permissions = permissionsActions.all('[name$=' + type + 'Permissions]');

						permissions.each(
							function(item, index, collection) {
								if (item.get('checked')) {
									buffer.push(item.val());
								}
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
								groupId: themeDisplay.getParentGroupId()
							},
							callback
						);
					},

					_getVocabulary: function(vocabularyId) {
						var instance = this;

						return A.one('li[data-vocabularyId="' + vocabularyId + '"]');
					},

					_getVocabularyCategories: function(vocabularyId, callback) {
						var instance = this;

						instance._showLoading(instance._categoryContainerSelector);

						Liferay.Service.Asset.AssetCategory.getVocabularyCategories(
							{
								vocabularyId: vocabularyId,
								start: -1,
								end: -1,
								obc: null
							},
							callback
						);
					},

					_getVocabularyId: function(exp) {
						var instance = this;

						return A.one(exp).attr('data-vocabularyId');
					},

					_getVocabularyName: function(exp) {
						var instance = this;

						return A.one(exp).attr('data-vocabulary');
					},

					_hideAllMessages: function() {
						var instance = this;

						instance._container.one('.lfr-message-response').hide();
					},

					_hideFloatingPanels: function(event) {
						var instance = this;

						var contextPanel = event.currentTarget;
						var boundingBox = contextPanel.get('boundingBox');
						var autoFieldsTriggers = boundingBox.all('.lfr-floating-trigger');

						autoFieldsTriggers.each(
							function(item, index, collection) {
								var autoFieldsInstance = item.getData('autoFieldsInstance');
								var panelInstance = item.getData('panelInstance');

								instance._resetInputLocalized(autoFieldsInstance, panelInstance);
							}
						);
					},

					_hideLoading: function(exp) {
						var instance = this;

						instance._container.one('div.loading-animation').remove();
					},

					_hideSection: function(exp) {
						var instance = this;

						var node = A.one(exp);

						if (node) {
							var parentNode = node.get('parentNode');

							if (parentNode) {
								parentNode.hide();
							}
						}
					},

					_hidePanel: function(panel) {
						if(panel && panel.get('visible')){
							panel.hide();
						}
					},

					_hidePanels: function() {
						var instance = this;

						instance._hidePanel(instance._categoryPanelAdd);
						instance._hidePanel(instance._vocabularyPanelAdd);
						instance._hidePanel(instance._panelEdit);
						instance._hidePanel(instance._panelPermissionsChange);
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

					_loadPermissions: function(url){
						var instance = this;

						var panelPermissionsChange = instance._panelPermissionsChange;

						if (!instance._panelPermissionsChange){
							panelPermissionsChange = instance._createPanelPermissions();
						}

						instance._permissionsPanelContentEl.set('src', url);
						instance._permissionsPanelContentEl.hide();

						instance._permissionsPanelMaskEl.show();

						panelPermissionsChange.show();

						instance._alignPanel(panelPermissionsChange);

						/**
						 * workaroung - without this code, permissions panel shows below edit panel
						 */
						panelPermissionsChange.set('zIndex', parseInt(instance._panelEdit.get('zIndex'), 10) + 2 );
					},

					_merge: function(fromCategoryId, fromCategoryName, toCategoryId, vocabularyId) {
						var instance = this;

						var categoryProperties = instance._buildCategoryProperties();

						vocabularyId = vocabularyId || instance._selectedVocabularyId;

						instance._updateCategory(fromCategoryId, vocabularyId, toCategoryId, fromCategoryName, categoryProperties);
					},

					_onCategoryAddFailure: function(response) {
						var instance = this;

						instance._sendMessage(MSG_TYPE_ERROR, Liferay.Language.get('your-request-failed-to-complete'));
					},

					_onCategoryAddSuccess: function(response) {
						var instance = this;

						var exception = response.exception;

						if (!exception && response.categoryId) {
							instance._sendMessage(MSG_TYPE_SUCCESS, Liferay.Language.get('your-request-processed-successfully'));

							instance._selectVocabulary(instance._selectedVocabularyId);

							instance._displayVocabularyCategories(
								instance._selectedVocabularyId,
								function() {
									instance._hideSection(instance._categoryViewContainer);
								}
							);

							instance._hidePanels();
						}
						else {
							var errorKey = '';

							if (exception.indexOf('DuplicateCategoryException') > -1) {
								errorKey = Liferay.Language.get('that-category-already-exists');
							}
							else if ((exception.indexOf('CategoryNameException') > -1) ||
									 (exception.indexOf('AssetCategoryException') > -1)) {
								errorKey = Liferay.Language.get('one-of-your-fields-contains-invalid-characters');
							}
							else if (exception.indexOf(EXC_NO_SUCH_VOCABULARY) > -1) {
								errorKey = Liferay.Language.get('that-vocabulary-does-not-exist');
							}
							else if (exception.indexOf(EXC_PRINCIPAL) > -1) {
								errorKey = Liferay.Language.get('you-do-not-have-permission-to-access-the-requested-resource');
							}
							else {
								errorKey = Liferay.Language.get('your-request-failed-to-complete');
							}

							instance._sendMessage(MSG_TYPE_ERROR, errorKey);
						}
					},

					_onCategoryAddButtonClose: function(event) {
						var instance = this;

						instance._hidePanel(instance._categoryPanelAdd);
					},

					_onCategoryChangePermissions: function(event){
						var instance = this;

						var buttonChangeCategoryPermissions = A.one('#category-change-permissions');
						var url = buttonChangeCategoryPermissions.getAttribute("data-url");

						instance._loadPermissions(url);
					},

					_onCategoryDelete: function(event){
						var instance = this;

						if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this-category'))) {
							instance._deleteCategory(
								instance._selectedCategoryId,
								function(message) {
									var errorMessage;
									var exception = message.exception;

									if (!exception) {
										instance._closeEditSection();
										instance._hidePanels();
										instance._displayVocabularyCategories(instance._selectedVocabularyId);
									}
									else {
										if (exception.indexOf(EXC_PRINCIPAL) > -1) {
											errorMessage = Liferay.Language.get('you-do-not-have-permission-to-access-the-requested-resource');
										}
										else {
											errorMessage = Liferay.Language.get('your-request-failed-to-complete');
										}

										instance._sendMessage(MSG_TYPE_ERROR, errorMessage);
									}
								}
							);
						}
					},

					_onCategoryFormSubmit: function(event, form) {
						var instance = this;

						event.halt();

						var vocabularySelectNode = A.one('.vocabulary-select-list');

						var vocabularyId = (vocabularySelectNode && vocabularySelectNode.val()) || instance._selectedVocabularyId;

						if(vocabularyId) {
							var vocabularyElId = ["#", instance._prefixedPortletId, 'vocabularyId'].join('');
							var parentCategoryElId = ["#", instance._prefixedPortletId, 'parentCategoryId'].join('');

							form.one(vocabularyElId).set("value", vocabularyId);
							form.one(parentCategoryElId).set("value", 0);

							instance._addCategory(form);
						}
					},

					_onCategoryViewContainerClick: function(event){
						var instance = this;
						
						var targetId = event.target.get('id');

						if (targetId === 'category-edit-button'){
							event.halt();

							instance._hidePanels();
							instance._showCategoryPanel(ACTION_EDIT);
						}
						else if(targetId === 'category-delete-button'){
							event.halt();

							instance._onCategoryDelete();
						}
					},

					_onCategoryViewFailure: function(response){
						var instance = this;

						instance._sendMessage(MSG_TYPE_ERROR, Liferay.Language.get('your-request-failed-to-complete'));
					},

					_onCategoryViewSuccess: function(response){
						var instance = this;

						instance._categoryViewDataContainer.html(response);
					},

					_onPermissionsPanelLoad: function(event){
						var instance = this;

						instance._tunePermissionsPanel();
					},

					_onShowCategoryPanel: function(event, action){
						var instance = this;

						instance._hidePanels();

						instance._showCategoryPanel(action);
					},

					_onShowVocabularyPanel: function(event, action){
						var instance = this;

						instance._hidePanels();

						instance._showVocabularyPanel(action);
					},

					_onVocabularyAddFailure: function(response) {
						var instance = this;

						instance._sendMessage(MSG_TYPE_ERROR, Liferay.Language.get('your-request-failed-to-complete'));
					},

					_onVocabularyAddSuccess: function(response) {
						var instance = this;

						instance._hideAllMessages();

						var exception = response.exception;

						if (!response.exception) {
							instance._sendMessage(MSG_TYPE_SUCCESS, Liferay.Language.get('your-request-processed-successfully'));

							instance._displayList(
								function() {
									var vocabulary = instance._selectVocabulary(response.vocabularyId);

									instance._displayVocabularyCategories(instance._selectedVocabularyId);

									if (vocabulary) {
										var scrollTop = vocabulary.get('region').top;

										A.one(instance._vocabularyContainerSelector).set('scrollTop', scrollTop);
									}
								}
							);

							instance._hidePanels();
						}
						else {
							var errorKey = '';

							if (exception.indexOf('DuplicateVocabularyException') > -1) {
								errorKey = Liferay.Language.get('that-vocabulary-already-exists');
							}
							else if (exception.indexOf(EXC_VOCABULARY_NAME) > -1) {
								errorKey = Liferay.Language.get('one-of-your-fields-contains-invalid-characters');
							}
							else if (exception.indexOf(EXC_NO_SUCH_VOCABULARY) > -1) {
								errorKey = Liferay.Language.get('that-parent-vocabulary-does-not-exist');
							}
							else if (exception.indexOf(EXC_PRINCIPAL) > -1) {
								errorKey = Liferay.Language.get('you-do-not-have-permission-to-access-the-requested-resource');
							}
							else {
								errorKey = Liferay.Language.get('your-request-failed-to-complete');
							}

							instance._sendMessage(MSG_TYPE_ERROR, errorKey);
						}
					},

					_onVocabularyChangePermissions: function(event){
						var instance = this;

						var buttonChangeVocabularyPermissions = A.one('#vocabulary-change-permissions');
						var url = buttonChangeVocabularyPermissions.getAttribute("data-url");

						instance._loadPermissions(url);
					},

					_onVocabularyDelete: function(){
						var instance = this;

						if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this-vocabulary'))) {
							instance._deleteVocabulary(
								instance._selectedVocabularyId,
								function(message) {
									var errorKey;
									var exception = message.exception;

									if (!exception) {
										instance._closeEditSection();
										instance._hidePanels();
										instance._loadData();
									}
									else {
										if (exception.indexOf(EXC_PRINCIPAL) > -1) {
											errorKey = Liferay.Language.get('you-do-not-have-permission-to-access-the-requested-resource');
										}
										else {
											errorKey = Liferay.Language.get('your-request-failed-to-complete');
										}

										instance._sendMessage(MSG_TYPE_ERROR, errorKey);
									}
								}
							);
						}
					},

					_onVocabularyFormSubmit: function(event, form) {
						var instance = this;

						event.halt();

						instance._addVocabulary(form);
					},

					_onVocabularyList: function(event){
						var instance = this;

						var target = event.target;
						var vocabularyId = instance._getVocabularyId(target);

						instance._selectVocabulary(vocabularyId);

						if (target.hasClass('vocabulary-item-actions-trigger')){
							instance._showVocabularyPanel(ACTION_EDIT);
						}
					},

					_tunePermissionsPanel: function(){
						var instance = this;

						var elHTML = A.Node.getDOMNode(instance._permissionsPanelContentEl).contentWindow.document.getElementsByTagName('html')[0];
						var elHTMLStyle = elHTML.style;

						elHTMLStyle.overflowX = 'auto';
						elHTMLStyle.overflowY = 'auto';

						instance._permissionsPanelContentEl.setAttribute('scrolling', 'auto');

						instance._permissionsPanelMaskEl.hide();
						instance._permissionsPanelContentEl.show();
					},

					_reloadSearch: function() {
						var	instance = this;

						var options = {
							input: '#vocabulary-search-input'
						};

						var vocabularySelectSearchNode = A.one('#vocabulary-select-search');
						var selected = (vocabularySelectSearchNode && vocabularySelectSearchNode.val()) || '';

						if (/vocabularies/.test(selected)) {
							A.mix(
								options,
								{
									data: function(node) {
										return node.one('a').html();
									},
									nodes: instance._vocabularyItemSelector
								}
							);
						}
						else {
							A.mix(
								options,
								{
									after: {
										search: function(event) {
											var results = event.liveSearch.results;

											A.each(
												results,
												function(item, index, collection) {
													var nodeWidget = A.Widget.getByNode(item.node);
													var nodeVisible = nodeWidget.get('boundingBox').hasClass('aui-helper-hidden');

													if (!nodeVisible) {
														nodeWidget.eachParent(
															function(parent) {
																parent.get('boundingBox').show();
															}
														);
													}
												}
											);
										}
									},
									data: function(node) {
										return node.one('.aui-tree-label').html();
									},
									nodes: instance._categoryItemSelector
								}
							);
						}

						if (instance.liveSearch) {
							instance.liveSearch.destroy();
						}

						instance.liveSearch = new A.LiveSearch(options);
					},

					_removeCategoryProperty: function(categoryProperty) {
						var instance = this;

						if (A.all('div.vocabulary-property-row').size() > 2) {
							categoryProperty.remove();
						}
					},

					_resetInputLocalized: function(autoFieldsInstance, panelInstance) {
						var instance = this;

						if (autoFieldsInstance) {
							autoFieldsInstance.reset();
						}

						if (panelInstance) {
							panelInstance.hide();
						}
					},

					_saveCategoryProperties: function() {
						var instance = this;

						var categoryId = instance._selectedCategoryId;
						var categoryNameNode = A.one('input.category-name');
						var categoryName = (categoryNameNode && categoryNameNode.val()) || instance._selectedCategoryName;
						var parentCategoryId = instance._selectedParentCategoryId;
						var categoryProperties = instance._buildCategoryProperties();
						var vocabularyId = instance._selectedVocabularyId;

						instance._updateCategory(categoryId, vocabularyId, parentCategoryId, categoryName, categoryProperties);
						instance._displayVocabularyCategories(vocabularyId);
					},

					_selectCategory: function(categoryId) {
						var instance = this;

						var category = instance._getCategory(categoryId);
						var categoryName = instance._getCategoryName(category);
						var parentCategoryId = instance._getParentCategoryId(category);

						categoryId = instance._getCategoryId(category);

						instance._selectedCategoryId = categoryId;
						instance._selectedCategoryName = categoryName;
						instance._selectedParentCategoryId = parentCategoryId || 0;

						if (!categoryId) {
							return category;
						}

						instance._selectedCategory = category;

						return category;
					},

					_selectCurrentVocabulary: function(value) {
						var instance = this;

						var option = A.one('select.vocabulary-select-list option[value="' + value + '"]');

						if (option) {
							option.set('selected', true);
						}
					},

					_selectVocabulary: function(vocabularyId) {
						var instance = this;

						var vocabulary = instance._getVocabulary(vocabularyId);

						if (vocabulary) {
							var vocabularyName = instance._getVocabularyName(vocabulary);

							if (vocabulary.hasClass('selected')) {
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
						}

						return vocabulary;
					},

					_sendMessage: function(type, message) {
						var instance = this;

						var output = instance._portletMessageContainer;
						var typeClass = 'portlet-msg-' + type;

						output.removeClass('portlet-msg-error').removeClass('portlet-msg-success');
						output.addClass(typeClass);
						output.html(message);
						output.show();

						instance._hideMessageTask.delay(7000);
					},

					_showCategoryPanel: function(action){
						var instance = this;

						switch (action){
							case ACTION_ADD:
								instance._showCategoryPanelAdd();
								break;
							case ACTION_EDIT:
								instance._showCategoryPanelEdit();
								break;
							default:
								throw 'Internal error. No default action specified.';
						}
					},

					_showCategoryPanelAdd: function(){
						var instance = this;

						var categoryPanelAdd = instance._categoryPanelAdd;

						if (!categoryPanelAdd){
							categoryPanelAdd = instance._createCategoryPanelAdd();

							var categoryURL = instance._createURL(CATEGORY, ACTION_ADD);

							categoryPanelAdd.plug(
								A.Plugin.IO,
								{
									uri: categoryURL.toString(),
									after: {
										success: A.bind(instance._initializeCategoryPanelAdd, instance, function(){
											instance._feedVocabularySelect(instance._vocabularies, instance._selectedVocabularyId);

											instance._focusCategoryPanelAdd();
										})
									}
								}
							);
								
							categoryPanelAdd.show();

							instance._alignPanel(categoryPanelAdd);
						}
						else {
							instance._feedVocabularySelect(instance._vocabularies, instance._selectedVocabularyId);

							categoryPanelAdd.show();

							instance._alignPanel(categoryPanelAdd);

							instance._focusCategoryPanelAdd();
						}						
					},

					_showCategoryPanelEdit: function(){
						var instance = this;

						var forceStart = false;
						var categoryPanelEdit = instance._panelEdit;

						if (!categoryPanelEdit){
							categoryPanelEdit = instance._createPanelEdit();
						} else {
							forceStart = true;

							instance._currentPanelEditInitHandler.detach();
						}

						categoryPanelEdit.set('title', Liferay.Language.get('edit-category'));

						var categoryEditURL = instance._createURL(CATEGORY, ACTION_EDIT);

						categoryPanelEdit.plug(
							A.Plugin.IO,
							{
								uri: categoryEditURL.toString(),
								after: {
									success: instance._currentPanelEditInitListener
								}
							}
						);

						instance._currentPanelEditInitHandler = categoryPanelEdit.io.after(
							'success', instance._initializeCategoryPanelEdit, instance);

						if (forceStart){
							categoryPanelEdit.io.start();
						}

						categoryPanelEdit.show();

						instance._alignPanel(categoryPanelEdit);
					},

					_showLoading: function(container) {
						var instance = this;

						A.all(container).html('<div class="loading-animation" />');
					},

					_showSection: function(exp) {
						var instance = this;

						var element = A.one(exp);

						if (element) {
							var parentNode = element.get('parentNode');

							if (parentNode) {
								parentNode.show();
								var firstInput = element.one('input');

								if (firstInput){
									firstInput.focus();
								}
							}
						}
					},

					_showVocabularyPanel: function(action){
						var instance = this;

						switch (action){
							case ACTION_ADD:
								instance._showVocabularyPanelAdd();
								break;
							case ACTION_EDIT:
								instance._showVocabularyPanelEdit();
								break;
							default:
								throw 'Internal error. No default action specified.';
						}
					},

					_showVocabularyPanelAdd: function(){
						var instance = this;

						var vocabularyPanelAdd = instance._vocabularyPanelAdd;

						if (!vocabularyPanelAdd){
							vocabularyPanelAdd = instance._createVocabularyPanelAdd();

							var vocabularyURL = instance._createURL(VOCABULARY, ACTION_ADD);

							vocabularyPanelAdd.plug(
								A.Plugin.IO,
								{
									uri: vocabularyURL.toString(),
									after: {
										success: A.bind(instance._initializeVocabularyPanelAdd, instance, function(){
											instance._focusVocabularyPanelAdd();
										})
									}
								}
							);

							vocabularyPanelAdd.show();

							instance._alignPanel(vocabularyPanelAdd);
						}
						else {
							vocabularyPanelAdd.show();

							instance._alignPanel(vocabularyPanelAdd);

							instance._focusVocabularyPanelAdd();
						}
					},

					_showVocabularyPanelEdit: function(){
						var instance = this;

						var forceStart = false;
						var vocabularyPanelEdit = instance._panelEdit;

						if (!vocabularyPanelEdit){
							vocabularyPanelEdit = instance._createPanelEdit();
						} else {
							forceStart = true;

							instance._currentPanelEditInitHandler.detach();
						}

						vocabularyPanelEdit.set('title', Liferay.Language.get('edit-vocabulary'));

						var vocabularyEditURL = instance._createURL(VOCABULARY, ACTION_EDIT);

						vocabularyPanelEdit.plug(
							A.Plugin.IO,
							{
								uri: vocabularyEditURL.toString()
							}
						);

						instance._currentPanelEditInitHandler = vocabularyPanelEdit.io.after(
							'success', instance._initializeVocabularyPanelEdit, instance);

						if (forceStart){
							vocabularyPanelEdit.io.start();
						}

						vocabularyPanelEdit.show();

						instance._alignPanel(vocabularyPanelEdit);
					},

					_unselectAllVocabularies: function() {
						var instance = this;

						A.all(instance._vocabularyItemSelector).removeClass('selected');
					},

					_updateCategory: function(categoryId, vocabularyId, parentCategoryId, name, categoryProperties, callback) {
						var instance = this;

						var descriptionMap = {};
						var titleMap = {};

						titleMap[themeDisplay.getDefaultLanguageId()] = name;

						Liferay.Service.Asset.AssetCategory.updateCategory(
							{
								categoryId: categoryId,
								parentCategoryId: parentCategoryId,
								titleMap: JSON.stringify(titleMap),
								descriptionMap: JSON.stringify(descriptionMap),
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
									var errorKey;

									if (exception.indexOf('AssetCategoryNameException') > -1) {
										errorKey = Liferay.Language.get('please-enter-a-valid-category-name');
									}
									else if (exception.indexOf('DuplicateCategoryException') > -1) {
										errorKey = Liferay.Language.get('there-is-another-category-with-the-same-name-and-the-same-parent');
									}
									else if (exception.indexOf(EXC_NO_SUCH_VOCABULARY) > -1) {
										errorKey = Liferay.Language.get('that-vocabulary-does-not-exist');
									}
									else if (exception.indexOf('NoSuchCategoryException') > -1) {
										errorKey = Liferay.Language.get('that-parent-category-does-not-exist');
									}
									else if (exception.indexOf(EXC_PRINCIPAL) > -1) {
										errorKey = Liferay.Language.get('you-do-not-have-permission-to-access-the-requested-resource');
									}
									else if (exception.indexOf('Exception') > -1) {
										errorKey = Liferay.Language.get('one-of-your-fields-contains-invalid-characters');
									}
									else {
										errorKey = Liferay.Language.get('your-request-failed-to-complete');
									}

									instance._sendMessage(MSG_TYPE_ERROR, errorKey);
								}

								if (callback) {
									callback(message);
								}
							}
						);
					},

					_updateVocabulary: function(vocabularyId, vocabularyName, callback) {
						var titleMap = {};

						titleMap[themeDisplay.getDefaultLanguageId()] = vocabularyName;

						Liferay.Service.Asset.AssetVocabulary.updateVocabulary(
							{
								vocabularyId: vocabularyId,
								title: '',
								titleMap: JSON.stringify(titleMap),
								description: '',
								settings: '',
								serviceContext: JSON.stringify(
									{
										scopeGroupId: themeDisplay.getParentGroupId()
									}
								)
							},
							callback
						);
					},

					_categoryItemSelector: '.vocabulary-categories .aui-tree-node',
					_categoryContainerSelector: '.vocabulary-categories',
					_selectedCategoryName: null,
					_selectedVocabulary: null,
					_selectedVocabularyId: null,
					_selectedVocabularyName: null,
					_vocabularies: null,
					_vocabularyItemSelector: '.vocabulary-list li',
					_vocabularyContainerSelector: '.vocabulary-list'
				}
			}
		);

		var VocabularyTree = A.Component.create(
			{
				NAME: 'VocabularyTree',

				EXTENDS: A.TreeViewDD,

				prototype: {
					_updateNodeState: function(event) {
						var instance = this;

						var dropNode = event.drop.get('node');

						if (dropNode && dropNode.hasClass('vocabulary-category')) {
							instance._appendState(dropNode);
						}
						else {
							VocabularyTree.superclass._updateNodeState.apply(instance, arguments);
						}
					}
				}
			}
		);

		Liferay.Portlet.AssetCategoryAdmin = AssetCategoryAdmin;
	},
	'',
	{
		requires: ['aui-live-search', 'aui-dialog', 'aui-tree-view', 'dd', 'json', 'liferay-portlet-url']
	}
);