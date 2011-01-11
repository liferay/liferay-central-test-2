/**
 * Provides Categories portlet
 *
 * Categories portlet allows creation of vocabularies and categories.
 * One vocabulary might have multiple categories and categories might be organized in tree.
 *
 */

AUI().add(
	'liferay-category-admin',
	function(A) {
		/**
		 * Constants declaration
		 */

		var ACTION_ADD = 0;

		var ACTION_EDIT = 1;

		var ACTION_MOVE = 2;

		var ACTION_VIEW = 3;

		var CATEGORY = 0;

		var CSS_VOCABULARY_EDIT_CATEGORY = 'vocabulary-content-edit-category';

		var JSON = A.JSON;

		var EXC_NO_SUCH_VOCABULARY = 'NoSuchVocabularyException';

		var EXC_PRINCIPAL = 'auth.PrincipalException';

		var EXC_VOCABULARY_NAME = 'VocabularyNameException';

		var LIFECYCLE_RENDER = 0;

		var LIFECYCLE_PROCESS = 1;

		var MSG_TYPE_ERROR = 'error';

		var MSG_TYPE_SUCCESS = 'success';

		var Node = A.Node;

		var VOCABULARY = 1;

		/**
		 * AssetCategoryAdmin implementation.
		 *
		 * @class AssetCategoryAdmin
		 * @extends A.Base
		 */

		var AssetCategoryAdmin = A.Component.create(
			{
				NAME: 'assetcategoryadmin',

				EXTENDS: A.Base,

				/**
				 * Constructor function
				 *
				 * @param config {Object} Configuration properties
				 * <dl>
				 *		<dt>portletId</dt>
				 *			<dd>The ID of the portlet</dd>
				 * </dl>
				 */

				constructor: function() {
					AssetCategoryAdmin.superclass.constructor.apply(this, arguments);
				},

				prototype: {
					/**
					 * Initializer lifecycle implementation for AssetCategoryAdmin portlet
					 *
					 * Set up listeners, prepares searching by using A.LiveSearch,
					 * loads the Vocabularies from server
					 *
					 * @method initializer
					 * @protected
					 * @param config {Object} Contains configuration properties
					 */

					initializer: function(config) {
						var instance = this;

						var childrenContainer = A.one(instance._categoryContainerSelector);

						instance.portletId = config.portletId;
						instance._prefixedPortletId = ['_', config.portletId, '_'].join('');
						instance._container = A.one('.vocabulary-container');
						instance._vocabularyContent = A.one('.vocabulary-content');
						instance._categoryViewContainer = A.one('.category-view');

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

					/**
					 * Add or update category data, depending on the form content
					 * In order to update category, categoryId must be present
					 * in a hidden field. Otherwise, a new category will be created.
					 *
					 * @method _addCategory
					 * @protected
					 * @param form {A.Node} The data, extracted from this form will be send to server
					 */

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

					/**
					 * Add or update vocabulary data, depending on the form content.
					 * In order to update vocabulary, vocabularyId must be present in a hidden field.
					 * Otherwise, a new vocabulary will be created.
					 *
					 * @method _addVocabulary
					 * @protected
					 * @param form {A.Node} The data, extracted from this form will be send to server
					 */

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

					/**
					 * Moves a Category to another Vocabulary
					 *
					 * @method _afterDragDrop
					 * @protected
					 * @param event {Event} custom event
					 */

					_afterDragDrop: function(event) {
						var instance = this;

						var dragNode = event.drag.get('node');
						var dropNode = event.drop.get('node');

						var node = A.Widget.getByNode(dragNode);

						var vocabularyId = dropNode.attr('data-vocabularyid');
						var fromCategoryId = instance._getCategoryId(node);

						instance._merge(fromCategoryId, 0, vocabularyId);

						instance._selectVocabulary(vocabularyId);

						dropNode.removeClass('active-area');
					},

					/*
					 * Mark the current drop node as active
					 *
					 * @method _afterDragEnter
					 * @protected
					 * @param event {Event} custom event, contains the current drop node
					 */

					_afterDragEnter: function(event) {
						var instance = this;

						var dropNode = event.drop.get('node');

						dropNode.addClass('active-area');
					},

					/*
					 * Removes active status from the current drop node
					 *
					 * @method _afterDragExit
					 * @protected
					 * @param event {Event} custom event, contains the current drop node
					 */

					_afterDragExit: function(event) {
						var instance = this;

						var dropNode = event.target.get('node');

						dropNode.removeClass('active-area');
					},

					/*
					 * Aligns a panel to top/left position of main vocabulary content element
					 *
					 * @method _alignPanel
					 * @protected
					 * @param panel {A.Panel or an inheritor} The panel to be aligned
					 */

					_alignPanel: function(panel){
						var instance = this;

						var vocabularyContent = instance._vocabularyContent;
						var vocabularyContentXY = vocabularyContent.getXY();

						panel.set("xy", vocabularyContentXY);
					},

					/*
					 * Binds Escape key (ASCII code 27) to the bounding box of a panel.
					 * Pressing this key will hide the panel.
					 *
					 * @method _bindCloseEvent
					 * @protected
					 * @param contextPanel {A.Panel or an inheritor} The panel, to which Esc key should be bound
					 */

					_bindCloseEvent: function(contextPanel) {
						var instance = this;

						contextPanel.get('boundingBox').on(
							'key',
							A.bind(contextPanel.hide, contextPanel),
							'up:27'
						);
					},

					/**
					 * Builds tree structure of all available Categories. Each Category is one A.TreeNode instance.
					 * Sets listener to select event on each node in order to load category data.
					 *
					 * @method _buildCategoryTreeview
					 * @protected
					 * @param categories {Array} Array with categories
					 * @param parentCategoryId {Number} Parent category ID
					 * @return {Number} The number of children in the current category
					 */

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

												instance._selectCategory(categoryId);
												instance._showLoading(viewContainer);
												instance._vocabularyContent.addClass(CSS_VOCABULARY_EDIT_CATEGORY);
												instance._showSection(viewContainer);

												var categoryURL = instance._createURL(CATEGORY, ACTION_VIEW, LIFECYCLE_RENDER);

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

					/**
					 * Hides the section with Category data and updates the corresponding
					 * styles of the parent container.
					 * If there is selected category, this function unselects it.
					 *
					 * @method _closeEditSection
					 * @protected
					 */

					_closeEditSection: function() {
						var instance = this;

						instance._hideSection(instance._categoryViewContainer);

						instance._vocabularyContent.removeClass(CSS_VOCABULARY_EDIT_CATEGORY);

						if (instance._selectedCategory){
							instance._selectedCategory.unselect();
						}
					},

					/**
					 * Creates a panel (instance of A.Dialog) which allows adding a new Category.
					 *
					 * @method _createCategoryPanelAdd
					 * @protected
					 * @return {A.Dialog} The instance of the panel
					 */

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

						instance._categoryPanelAdd.on(
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

					/**
					 * Creates and populates a TreeView with list of categories
					 *
					 * @method _createCategoryTreeView
					 * @protected
					 * @param categories {Array} Array with categories
					 */

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
										var toCategoryId = instance._getCategoryId(tree.dropNode);
										var vocabularyId = instance._selectedVocabularyId;

										instance._merge(fromCategoryId, toCategoryId, vocabularyId);
									},
									dropInsert: function(event) {
										var tree = event.tree;
										var parentNode = tree.dropNode.get('parentNode');
										var fromCategoryId = instance._getCategoryId(tree.dragNode);
										var toCategoryId = instance._getCategoryId(parentNode);
										var vocabularyId = instance._selectedVocabularyId;

										instance._merge(fromCategoryId, toCategoryId, vocabularyId);
									}
								},
								type: 'normal'
							}
						).render();

						instance._buildCategoryTreeview(categories, 0);
					},

					/**
					 * Creates a panel (instance of A.Dialog) which allows adding a new Vocabulary.
					 *
					 * @method _createVocabularyPanelAdd
					 * @protected
					 * @return {A.Dialog} The instance of the panel
					 */

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

						instance._vocabularyPanelAdd.on(
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

					/**
					 * Creates a panel in order to edit Vocabulary or Category.
					 *
					 * @method _createPanelEdit
					 * @protected
					 * @param config {Object} Configuration properties as required by A.Dialog
					 * They will be merged with default properties (resizable: false, width: 550px, zIndex: 1000)
					 * @return {A.Dialog} The instance of the panel
					 */

					_createPanelEdit: function(config){
						var instance = this;

						var defaultConfig = {
							resizable: false,
							width: '550px',
							zIndex: 1000
						};

						if (A.Lang.isObject(config)){
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

					/**
					 * Creates a panel in order to edit permissions of Vocabulary or Category.
					 * The function will create a new panel only if there is no any created already.
					 * Otherwise, it returns the available panel instance.
					 *
					 * @method _createPanelPermissions
					 * @protected
					 * @return {A.Dialog} The newly created instance of the panel or the existing one
					 */

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

					/**
					 * Creates a URL depending on the type and action specified as arguments.
					 * The type can be VOCABULARY or CATEGORY and action can be:
					 * ACTION_ADD, ACTION_EDIT, ACTION_MOVE or ACTION_VIEW.
					 *
					 * @method _createURL
					 * @protected
					 * @param type {Number} The type of resource. Can be one the following values:
					 *  <dl>
					 *		<dt>CATEGORY or 0</dt>
					 *			<dd>Creates URL for Category</dd>
					 *		<dt>VOCABULARY or 1</dt>
					 *			<dd>Creates URL for Vocabulary</dd>
					 *  </dl>
					 *  @param action {Number} The type of action. Can be one the following values:
					 *  <dl>
					 *		<dt>ACTION_ADD or 0</dt>
					 *			<dd>Creates a URL to add the specified type of resource</dd>
					 *		<dt>ACTION_EDIT or 1</dt>
					 *			<dd>Creates a URL to edit the specified type of resource</dd>
					 *		<dt>ACTION_MOVE or 2</dt>
					 *			<dd>Creates a URL to move the specified type of resource</dd>
					 *		<dt>ACTION_VIEW or 3</dt>
					 *			<dd>Creates a URL to view the data of specified resource type</dd>
					 *  </dl>
					 *  @param lifecycle {Object} Portlet lifecycle. Can be one the following values:
					 *  <dl>
					 *		<dt>LIFECYCLE_RENDER or 0</dt>
					 *			<dd>Render lifecycle</dd>
					 *		<dt>LIFECYCLE_PROCESS or 1</dt>
					 *			<dd>Process lifecycle</dd>
					 *  @param params {Object} Map of additional parameters, which should be passed to Liferay.PortletURL
					 *  @return {PortletURL} The created URL
					 */

					_createURL: function(type, action, lifecycle, params) {
						var instance = this;

						var path = '/asset_category_admin/';

						var url;

						if (lifecycle === LIFECYCLE_RENDER){
							url = Liferay.PortletURL.createRenderURL();
						}
						else if (lifecycle === LIFECYCLE_PROCESS){
							url = Liferay.PortletURL.createActionURL();
						}
						else {
							throw 'Internal error. Unimplemented lifecycle.';
						}

						url.setPortletId(instance.portletId);
						url.setWindowState('exclusive');

						if (type == VOCABULARY) {
							path += 'edit_vocabulary';

							if (action === ACTION_EDIT){
								url.setParameter('vocabularyId', instance._selectedVocabularyId);
							}
						}
						else if (type == CATEGORY) {
							if (action === ACTION_ADD){
								path += 'edit_category';
								url.setParameter('vocabularyId', instance._selectedVocabularyId);
							}
							else if (action === ACTION_EDIT){
								path += 'edit_category';
								url.setParameter('categoryId', instance._selectedCategoryId);
								url.setParameter('vocabularyId', instance._selectedVocabularyId);
							}
							else if (action === ACTION_MOVE){
								path += 'edit_category';
								url.setParameter('categoryId', instance._selectedCategoryId);
								url.setParameter('cmd', 'move');
							}
							else if (action === ACTION_VIEW){
								path += 'view_category';
								url.setParameter('categoryId', instance._selectedCategoryId);
								url.setParameter('vocabularyId', instance._selectedVocabularyId);
							}
						}

						url.setParameter('struts_action', path);

						if (params){
							var hasOwnProperty = Object.prototype.hasOwnProperty;

							for (var key in params){
								if (hasOwnProperty.call(params, key)){
									url.setParameter(key, params[key]);
								}
							}
						}

						return url;
					},

					/**
					 * Deletes a Category.
					 *
					 * @method _deleteCategory
					 * @protected
					 * @param categoryId {Number} The ID of Category which have to be deleted.
					 * @param callback {Function} (optional) Callback function to be invoked once the process of deletion finishes.
					 */

					_deleteCategory: function(categoryId, callback) {
						var instance = this;

						Liferay.Service.Asset.AssetCategory.deleteCategory(
							{
								categoryId: categoryId
							},
							callback
						);
					},

					/**
					 * Deletes a Vocabulary.
					 *
					 * @method _deleteVocabulary
					 * @protected
					 * @param vocabularyId {Number} The ID of Vocabulary which have to be deleted.
					 * @param callback {Function} (optional) Callback function to be invoked once the process of deletion finishes.
					 */

					_deleteVocabulary: function(vocabularyId, callback) {
						var instance = this;

						Liferay.Service.Asset.AssetVocabulary.deleteVocabulary(
							{
								vocabularyId: vocabularyId
							},
							callback
						);
					},

					/**
					 * Renders all Categories of given vocabulary in a TreeView.
					 * Also, makes all items in Vocabulary container drop targets and
					 * loads A.LiveSearch with new data.
					 *
					 * @method _displayVocabularyCategoriesImpl
					 * @protected
					 * @param categories {Array} Array with available categories.
					 * @param callback {Function} (optional) Callback function to be invoked once the process finishes.
					 */

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

					/**
					 * Creates an unordered list with all Vocabularies and invokes an (optional) callback
					 *
					 * @method _displayList
					 * @protected
					 * @param callback {Function} (optional) Callback function to be invoked once the process finishes.
					 */

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

					/**
					 * Sends a request to the server in order to load all categories which belong to the vocabulary,
					 * specified by its ID in the first argument.
					 * Also, invokes an (optional) callback once the process finishes.
					 *
					 * @method _displayVocabularyCategories
					 * @protected
					 * @param vocabularyId {Number} The ID of vocabulary whose categories should be loaded.
					 * @param callback {Function} (optional) Callback function to be invoked once the process finishes.
					 */

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

					/**
					 * Initializes the panel for adding a new Category. The function detaches the default form action
					 * and replaces it with custom event.
					 * Also, invokes an (optional) callback function once the process finishes.
					 *
					 * @method _initializeCategoryPanelAdd
					 * @protected
					 * @param callback {Function} (optional) Callback function to be invoked once the process finishes.
					 */

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

					/**
					 * Initializes the panel for editing Category. The function detaches the default form action and
					 * replaces it with custom event. Adds listeners to some input elements like delete and permissions buttons.
					 * Also, invokes an (optional) callback function once the process finishes.
					 *
					 * @method _initializeCategoryPanelAdd
					 * @protected
					 */

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

					/**
					 * Initializes the panel for adding a new Vocabulary. The function detaches default form action
					 * and replaces it with custom event.
					 * Also, invokes an (optional) callback function once the process finishes.
					 *
					 * @method _initializeVocabularyPanelAdd
					 * @protected
					 * @param callback {Function} (optional) Callback function to be invoked once the process finishes.
					 */

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

					/**
					 * Initializes the panel for editing Vocabulary. The function detaches default form action and
					 * replaces it with custom event. Adds listeners to some input elements like delete and close buttons.
					 * Also, invokes an (optional) callback function once the process finishes.
					 *
					 * @method _initializeVocabularyPanelEdit
					 * @protected
					 */

					_initializeVocabularyPanelEdit: function() {
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

					/**
					 * Populates the vocabulary list in Category edit panel and selects the
					 * vocabulary to which the current Category belongs.
					 *
					 * @method _feedVocabularySelect
					 * @protected
					 * @method vocabularies {Array} Array with available vocabularies.
					 * @param selectedVocabularyId {Number} The ID of the currently selected Vocabulary.
					 */

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

										if ( item.vocabularyId == selectedVocabularyId ){
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

					/**
					 * Filters the categories, which belong to specific Category
					 *
					 * @method _filterCategory
					 * @protected
					 * @param categories {Array} Array with Categories
					 * @param parentCategoryId {Number} The ID of the Category whose children should be extracted
					 * @return {Array} New array with filtered Categories
					 */

					_filterCategory: function(categories, parentCategoryId) {
						var instance = this;

						return A.Array.filter(
							categories,
							function(item, index, collection) {
								return (item.parentCategoryId == parentCategoryId);
							}
						);
					},

					/**
					 * Set the name of the Category on focus.
					 *
					 * @method _focusCategoryPanelAdd
					 * @protected
					 */

					_focusCategoryPanelAdd: function(){
						var instance = this;

						var inputCategoryAddNameNode = instance._inputCategoryNameNode ||
							instance._categoryFormAdd.one('.category-name input');

						Liferay.Util.focusFormField(inputCategoryAddNameNode);
					},

					/**
					 * Set the name of Vocabulary on focus.
					 *
					 * @method _focusVocabularyPanelAdd
					 * @protected
					 */

					_focusVocabularyPanelAdd: function(){
						var instance = this;

						var inputVocabularyAddNameNode = instance._inputVocabularyAddNameNode ||
							instance._vocabularyFormAdd.one('.vocabulary-name input');

						Liferay.Util.focusFormField(inputVocabularyAddNameNode);
					},

					/**
					 * Returns a <code>Widget</code> instance, which belongs to the Category with ID, specified as first argument.
					 *
					 * @method _getCategory
					 * @protected
					 * @param categoryId {Number} The ID of the category, which should be returned
					 * @return {Widget} The <code>Widget</code> instance of the Category found.
					 */

					_getCategory: function(categoryId) {
						var instance = this;

						return A.Widget.getByNode('#categoryNode' + categoryId);
					},

					/**
					 * Returns the ID of Category. The function extracts it from the ID of some <code>A.Node</code> node
					 *
					 * @method _getCategoryId
					 * @protected
					 * @param node {A.Node} The node, which contains the ID of the Category.
					 * @return {String} The ID of the Category.
					 */

					_getCategoryId: function(node) {
						var instance = this;

						var nodeId = node.get('id') || '';
						var categoryId = nodeId.replace('categoryNode', '');

						if (A.Lang.isGuid(categoryId)) {
							categoryId = '';
						}

						return categoryId;
					},

					/**
					 * Returns the ID of parent Category.
					 * The function finds the parentNode of the node, specified as te first argument and
					 * invokes <code>_getCategoryId</code> function in order to extract the ID.
					 *
					 * @method _getParentCategoryId
					 * @protected
					 * @param node {A.Node} The node, where searching for the parent Category should begin.
					 * @return {String} The parent category ID.
					 */

					_getParentCategoryId: function(node) {
						var instance = this;

						var parentNode = node.get('parentNode');

						return instance._getCategoryId(parentNode);
					},

					/**
					 * Loads vocabularies from the server and invokes an (optional) callback function.
					 *
					 * @method _getVocabularies
					 * @protected
					 * @param callback {Function} (optional) Callback function to be invoked once the process finishes.
					 * @return {Array} Array with vocabularies.
					 */

					_getVocabularies: function(callback) {
						var instance = this;

						Liferay.Service.Asset.AssetVocabulary.getGroupVocabularies(
							{
								groupId: themeDisplay.getParentGroupId()
							},
							callback
						);
					},

					/**
					 * Returns an A.Node instance, which represents the vocabulary with ID, specified by the first argument.
					 *
					 * @method _getVocabulary
					 * @protected
					 * @param vocabularyId {String|Number} The ID of the vocabulary.
					 * @return {A.Node} The found vocabulary or null.
					 */

					_getVocabulary: function(vocabularyId) {
						var instance = this;

						return A.one('li[data-vocabularyId="' + vocabularyId + '"]');
					},

					/**
					 * Returns the categories, which belong to Vocabulary, specified by the first argument.
					 * Invokes an (optional) function once the process finishes.
					 *
					 * @method _getVocabularyCategories
					 * @protected
					 * @param vocabularyId {String} The ID if the vocabulary
					 * @param callback {Function} (optional) Callback function to be invoked once the process finishes.
					 * @return {Array} Array with categories
					 */

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

					/**
					 * Extract vocabulary ID from Node's data-* HTML5 attribute
					 *
					 * @method _getVocabularyId
					 * @protected
					 * @param exp {String} An expression (CSS selector), which will be used by <code>A.one</code> function.
					 * @return {String} The ID of the vocabulary or null.
					 */

					_getVocabularyId: function(exp) {
						var instance = this;

						return A.one(exp).attr('data-vocabularyId');
					},

					/**
					 * Extract vocabulary name from Node's data-* HTML5 attribute.
					 *
					 * @method _getVocabularyName
					 * @protected
					 * @param exp {String} An expression (CSS selector), which will be used by <code>A.one</code> function
					 * @return {String} The name of the vocabulary or null
					 */

					_getVocabularyName: function(exp) {
						var instance = this;

						return A.one(exp).attr('data-vocabulary');
					},

					/**
					 * Invokes hide() method of a node, which has class 'lfr-message-response'
					 *
					 * @method _hideAllMessages
					 * @protected
					 */

					_hideAllMessages: function() {
						var instance = this;

						instance._container.one('.lfr-message-response').hide();
					},

					/**
					 * Hides all 'floating' panels (instances of Liferay.PanelFloating).
					 *
					 * @method _hideFloatingPanels
					 * @protected
					 * @param event {Event} custom event
					 */

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

					/**
					 * Invokes hide() method of node's parentNode, specified as first argument.
					 *
					 * @method _hideSection
					 * @protected
					 * @param exp {String} An expression (CSS selector), which will be used by A.one function.
					 */

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

					/**
					 * Invokes hide() function of panel, specified by the first argument.
					 *
					 * @method _hidePanel
					 * @protected
					 * @param panel {Panel} The panel to hide.
					 */

					_hidePanel: function(panel) {
						if (panel && panel.get('visible')){
							panel.hide();
						}
					},

					/**
					 * Hides all panels, used in the portlet - for adding, editing and permissions changing.
					 *
					 * @method _hidePanels
					 * @protected
					 */

					_hidePanels: function() {
						var instance = this;

						instance._hidePanel(instance._categoryPanelAdd);
						instance._hidePanel(instance._vocabularyPanelAdd);
						instance._hidePanel(instance._panelEdit);
						instance._hidePanel(instance._panelPermissionsChange);
					},

					/**
					 * The main function, which loads the list with Vocabularies from server.
					 *
					 * @method _loadData
					 * @protected
					 */

					_loadData: function() {
						var instance = this;

						instance._closeEditSection();

						instance._displayList(
							function() {
								instance._displayVocabularyCategories(instance._selectedVocabularyId);
							}
						);
					},

					/**
					 * Sets the url, specified as first argument to the iframe, which is responsible for editing the permissions.
					 * Also, sets an animation during the process of loading,
					 * shows the permissions panel once the loading process finishes and
					 * updates its zIndex in order to set this panel on top of edit panel.
					 *
					 * @method _loadPermissions
					 * @protected
					 * @param url {String} The url of the page, which is responsible for editing the permissions.
					 */

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

						if (instance._panelEdit){
							/**
							 * workaroung - without this code, permissions panel shows below edit panel
							 */
							panelPermissionsChange.set('zIndex', parseInt(instance._panelEdit.get('zIndex'), 10) + 2 );
						}
					},

					/**
					 * Updates a Category's position in the tree with Categories.
					 *
					 * @method _merge
					 * @protected
					 * @param fromCategoryId {String} The ID of src node
					 * @param toCategoryId {String} The ID of destination category or an empty string
					 * @param vocabularyId {String} The ID of the vocabulary to which the category belongs
					 */

					_merge: function(fromCategoryId, toCategoryId, vocabularyId) {
						var instance = this;

						vocabularyId = vocabularyId || instance._selectedVocabularyId;

						instance._updateCategory(fromCategoryId, toCategoryId, vocabularyId);
					},

					/**
					 * Displays error message in case of error on adding Category
					 *
					 * @method _onCategoryAddFailure
					 * @protected
					 * @param response {Object} The server response
					 */

					_onCategoryAddFailure: function(response) {
						var instance = this;

						instance._sendMessage(MSG_TYPE_ERROR, Liferay.Language.get('your-request-failed-to-complete'));
					},

					/**
					 * The function checks the server respone and either displays a message in case of error
					 * or selects the current Vocabulary and loads its Categories.
					 *
					 * @metod _onCategoryAddSuccess
					 * @protected
					 * @param response {Object} The server response
					 */

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

					/**
					 * Closes Category add panel.
					 *
					 * @method _onCategoryAddButtonClose
					 * @protected
					 * @param event {Event} custom event
					 */

					_onCategoryAddButtonClose: function(event) {
						var instance = this;

						instance._hidePanel(instance._categoryPanelAdd);
					},

					/**
					 * Listener to change category permissions button.
					 * The function prepares an URL to the page with permissions.
					 *
					 * @method _onCategoryChangePermissions
					 * @protected
					 * @param event {Event} custom event
					 */

					_onCategoryChangePermissions: function(event){
						var instance = this;

						var buttonChangeCategoryPermissions = A.one('#category-change-permissions');
						var url = buttonChangeCategoryPermissions.getAttribute("data-url");

						instance._loadPermissions(url);
					},

					/**
					 * Listener to delete Category button.
					 * Invokes <code>_deleteCategory</code> function in order to delete the Category.
					 *
					 * @method _onCategoryDelete
					 * @protected
					 * @param event {Event} custom event
					 */

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

					/**
					 * Listener to Category submit button. This function either creates a new Category
					 * or updates the data of the existing one.
					 *
					 * @method _onCategoryFormSubmit
					 * @protected
					 * @param event {Event} custom event
					 * @param form {A.Node} The form, which data should be send to server
					 */

					_onCategoryFormSubmit: function(event, form) {
						var instance = this;

						event.halt();

						var vocabularySelectNode = A.one('.vocabulary-select-list');

						var vocabularyId = (vocabularySelectNode && vocabularySelectNode.val()) || instance._selectedVocabularyId;

						if (vocabularyId) {
							var vocabularyElId = ["#", instance._prefixedPortletId, 'vocabularyId'].join('');
							var parentCategoryElId = ["#", instance._prefixedPortletId, 'parentCategoryId'].join('');

							form.one(vocabularyElId).set("value", vocabularyId);
							form.one(parentCategoryElId).set("value", 0);

							Liferay.fire('saveAutoFields', {
								form: form
							}
							);

							instance._addCategory(form);
						}
					},

					_onCategoryMoveFailure: function(event){
						var instance = this;

						instance._sendMessage(MSG_TYPE_ERROR, Liferay.Language.get('your-request-failed-to-complete'));
					},

					_onCategoryMoveSuccess: function(response){
						var instance = this;

						var exception = response.exception;

						if (!exception) {
							instance._closeEditSection();
							instance._sendMessage(MSG_TYPE_SUCCESS, Liferay.Language.get('your-request-processed-successfully'));
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
					},

					/**
					 * Listener to click event on Category view container.
					 * This function displays edit panel, or deletes the current category.
					 *
					 * @method _onCategoryViewContainerClick
					 * @protected
					 * @param event {Event} custom event
					 */

					_onCategoryViewContainerClick: function(event){
						var instance = this;

						var targetId = event.target.get('id');

						if (targetId === 'category-edit-button'){
							event.halt();

							instance._hidePanels();
							instance._showCategoryPanel(ACTION_EDIT);
						}
						else if (targetId === 'category-delete-button'){
							event.halt();

							instance._onCategoryDelete();
						}
						else if (targetId === 'category-change-permissions'){
							event.halt();

							instance._onCategoryChangePermissions();
						}
					},

					/**
					 * Displays messsage in case of failure on viewing a Category.
					 *
					 * @method _onCategoryViewFailure
					 * @protected
					 * @param response {Object} The server response
					 */

					_onCategoryViewFailure: function(response){
						var instance = this;

						instance._sendMessage(MSG_TYPE_ERROR, Liferay.Language.get('your-request-failed-to-complete'));
					},

					/**
					 * Loads the data, returned from server to the view container.
					 *
					 * @method _onCategoryViewSuccess
					 * @protected
					 * @param response {Object} The server response
					 */

					_onCategoryViewSuccess: function(response){
						var instance = this;

						instance._categoryViewContainer.html(response);
					},

					/**
					 * Listener to load event of the iframe in permissions panel.
					 * This function invokes <code>_tunePermissionsPanel</code> method
					 *
					 * @method _onPermissionsPanelLoad
					 * @protected
					 * @param event {Event} custom event
					 */

					_onPermissionsPanelLoad: function(event){
						var instance = this;

						instance._tunePermissionsPanel();
					},

					/**
					 * Shows Category panel. Depending on the action, either the panel for
					 * adding or panel for editing will be opened.
					 *
					 * @method _onShowCategoryPanel
					 * @protected
					 * @param event {Event} custom event
					 * @param action {Number} Can be one of these:
					 * <dl>
					 *		<dt>ACTION_ADD or 0</dt>
					 *			<dd>Add a new category</dd>
					 *		<dt>ACTION_EDIT or 1</dt>
					 *			<dd>Edit an existing category</dd>
					 * </dl>
					 */

					_onShowCategoryPanel: function(event, action){
						var instance = this;

						instance._hidePanels();

						instance._showCategoryPanel(action);
					},

					/**
					 * Shows Vocabulary panel. Depending on the action, specified as second argument,
					 * either the panel for adding or panel for editing will be opened.
					 *
					 * @method _onShowVocabularyPanel
					 * @protected
					 * @param event {Event} custom event
					 * @param action {Number} Can be one of these:
					 * <dl>
					 *		<dt>ACTION_ADD or 0</dt>
					 *			<dd>Add a new category</dd>
					 *		<dt>ACTION_EDIT or 1</dt>
					 *			<dd>Edit an existing category</dd>
					 * </dl>
					 */

					_onShowVocabularyPanel: function(event, action){
						var instance = this;

						instance._hidePanels();

						instance._showVocabularyPanel(action);
					},

					/**
					 * Displays an error message in case of Vocabulary add failure
					 *
					 * @method _onVocabularyAddFailure
					 * @protected
					 * @param response {Object} The response from the server
					 */

					_onVocabularyAddFailure: function(response) {
						var instance = this;

						instance._sendMessage(MSG_TYPE_ERROR, Liferay.Language.get('your-request-failed-to-complete'));
					},

					/**
					 * The function checks the server respone and either displays a message
					 * in case of error, or loads the categories to the current Vocabulary.
					 *
					 * @metod _onVocabularyAddSuccess
					 * @protected
					 * @param response {Object} The server response
					 */

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

					/**
					 * Listener to change Vocabulary permissions button.
					 * The function prepares an URL to the page with permissions.
					 *
					 * @method _onVocabularyChangePermissions
					 * @protected
					 * @param event {Event} custom event
					 */

					_onVocabularyChangePermissions: function(event){
						var instance = this;

						var buttonChangeVocabularyPermissions = A.one('#vocabulary-change-permissions');
						var url = buttonChangeVocabularyPermissions.getAttribute("data-url");

						instance._loadPermissions(url);
					},

					/**
					 * Listener to delete Vocabulary button.
					 * Invokes <code>_deleteVocabulary</code> function in order to delete Vocabulary.
					 *
					 * @method _onVocabularyDelete
					 * @protected
					 */

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

					/**
					 * Listener to Vocabulary submit button. This function either
					 * creates a new Vocabulary, or updates the data of an existing one.
					 *
					 * @method _onVocabularyFormSubmit
					 * @protected
					 * @param event {Event} custom event
					 * @param form {A.Node} The form, which data should be send to server
					 */

					_onVocabularyFormSubmit: function(event, form) {
						var instance = this;

						event.halt();

						Liferay.fire('saveAutoFields', {
							form: form
						}
						);

						instance._addVocabulary(form);
					},

					/**
					 * Listener to user's action on Vocabulary panel.
					 * Selects a Vocabulary or shows edit panel, depending of the target node.
					 *
					 * @method _onVocabularyList
					 * @protected
					 * @param event {Event} custom event
					 */

					_onVocabularyList: function(event){
						var instance = this;

						var target = event.target;
						var vocabularyId = instance._getVocabularyId(target);

						instance._selectVocabulary(vocabularyId);

						if (target.hasClass('vocabulary-item-actions-trigger')){
							instance._showVocabularyPanel(ACTION_EDIT);
						}
					},

					/**
					 * Updates permissions panel in order to avoid unneeded vertical scrollbar.
					 * Also, hides the loading mask before to show the panel.
					 *
					 * @method _tunePermissionsPanel
					 * @protected
					 */

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

					/**
					 * Creates a new instance of A.LiveSearch and populates it with data.
					 *
					 * @method _reloadSearch
					 * @protected
					 */

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


					/**
					 * Resets the data in Liferay.AutoFields instance.
					 *
					 * @method _resetInputLocalized
					 * @protected
					 * @param autoFieldsInstance {Liferay.AutoFields} The AutoFields instance to reset
					 * @param panelInstance {Liferay.PanelFloating} The container in which <code>autoFieldsInstance</code> argument is rendered
					 */

					_resetInputLocalized: function(autoFieldsInstance, panelInstance) {
						var instance = this;

						if (autoFieldsInstance) {
							autoFieldsInstance.reset();
						}

						if (panelInstance) {
							panelInstance.hide();
						}
					},


					/**
					 * Marks a Category as selected and stores its ID, name and parentCategory ID
					 * to the current instance of the portlet.
					 *
					 * @method _selectCategory
					 * @protected
					 * @param categoryId {String} The ID of the category, which should be selected.
					 * @return {Widget} The Widget instance of the Category found.
					 */

					_selectCategory: function(categoryId) {
						var instance = this;

						var category = instance._getCategory(categoryId);
						var parentCategoryId = instance._getParentCategoryId(category);

						categoryId = instance._getCategoryId(category);

						instance._selectedCategoryId = categoryId;
						instance._selectedParentCategoryId = parentCategoryId || 0;

						if (!categoryId) {
							return category;
						}

						instance._selectedCategory = category;

						return category;
					},

					/**
					 * Marks Vocabulary as selected.
					 *
					 * @method _selectCurrentVocabulary
					 * @protected
					 * @param value {String} The value (ID) of the vocabulary, which should be selected
					 */

					_selectCurrentVocabulary: function(value) {
						var instance = this;

						var option = A.one('select.vocabulary-select-list option[value="' + value + '"]');

						if (option) {
							option.set('selected', true);
						}
					},

					/**
					 * Marks a Vocabulary as selected and loads its Categories.
					 *
					 * @method _selectVocabulary
					 * @protected
					 * @param vocabularyId {String} The ID of the vocabulary, which should be selected.
					 * @return {A.Node} The found Vocabulary or null
					 */

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

					/**
					 * Displays a message and sets A.DelayedTask in order to hide the message after some amount of time.
					 *
					 * @method _sendMessage
					 * @protected
					 * @param type {String} The type of the message. Can be one of the following:
					 * <dl>
					 *		<dt>MSG_TYPE_ERROR or 'error'</dt>
					 *			<dd>Message type error</dd>
					 *		<dt>MSG_TYPE_SUCCESS or 'success'</dt>
					 *			<dd>Message type success</dd>
					 * </dl>
					 * @param message {String} Message text
					 */

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

					/**
					 * Shows a panel in order to add a new Category or edit an existing one.
					 *
					 * @method _showCategoryPanel
					 * @protected
					 * @param action {Number} Can be one of these:
					 * <dl>
					 *		<dt>ACTION_ADD or 0</dt>
					 *			<dd>Add a new category</dd>
					 *		<dt>ACTION_EDIT or 1</dt>
					 *			<dd>Edit an existing category</dd>
					 * </dl>
					 */

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

					/**
					 * Shows a panel for adding Category.
					 * Creates a new panel if there is no existing one already.
					 *
					 * @method _showCategoryPanelAdd
					 * @protected
					 */

					_showCategoryPanelAdd: function(){
						var instance = this;

						var categoryPanelAdd = instance._categoryPanelAdd;

						if (!categoryPanelAdd){
							categoryPanelAdd = instance._createCategoryPanelAdd();

							var categoryURL = instance._createURL(CATEGORY, ACTION_ADD, LIFECYCLE_RENDER);

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

					/**
					 * Creates and shows a new panel for editing Category or reuses a previously created one.
					 *
					 * @method _showCategoryPanelEdit
					 * @protected
					 */

					_showCategoryPanelEdit: function(){
						var instance = this;

						var forceStart = false;
						var categoryPanelEdit = instance._panelEdit;

						if (!categoryPanelEdit){
							categoryPanelEdit = instance._createPanelEdit();
						}
						else {
							forceStart = true;

							instance._currentPanelEditInitHandler.detach();
						}

						categoryPanelEdit.set('title', Liferay.Language.get('edit-category'));

						var categoryEditURL = instance._createURL(CATEGORY, ACTION_EDIT, LIFECYCLE_RENDER);

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

					/**
					 * Replaces the content of a node(s) specified by the first argument with an animation.
					 *
					 * @method _showLoading
					 * @protected
					 * @param container {String} Selector CSS string.
					 */

					_showLoading: function(container) {
						var instance = this;

						A.all(container).html('<div class="loading-animation" />');
					},

					/**
					 * Shows a section specified by the CSS selector in the first argument.
					 *
					 * @method _showSection
					 * @protected
					 * @param exp {String} Selector CSS string.
					 */

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

					/**
					 * Shows a panel in order to add a new Vocabulary or edit an existing one.
					 *
					 * @method _showVocabularyPanel
					 * @protected
					 * @param action {Number} Can be one of these:
					 * <dl>
					 *		<dt>ACTION_ADD or 0</dt>
					 *			<dd>Add a new vocabulary</dd>
					 *		<dt>ACTION_EDIT or 1</dt>
					 *			<dd>Edit an existing vocabulary</dd>
					 * </dl>
					 */

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

					/**
					 * Shows a panel for adding Vocabulary.
					 * Creates a new panel if there is no existing one.
					 *
					 * @method _showVocabularyPanelAdd
					 * @protected
					 */

					_showVocabularyPanelAdd: function(){
						var instance = this;

						var vocabularyPanelAdd = instance._vocabularyPanelAdd;

						if (!vocabularyPanelAdd){
							vocabularyPanelAdd = instance._createVocabularyPanelAdd();

							var vocabularyURL = instance._createURL(VOCABULARY, ACTION_ADD, LIFECYCLE_RENDER);

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

					/**
					 * Shows a panel for editing Vocabulary.
					 * Creates a new panel if there is no existing one.
					 *
					 * @method _showVocabularyPanelEdit
					 * @protected
					 */

					_showVocabularyPanelEdit: function(){
						var instance = this;

						var forceStart = false;
						var vocabularyPanelEdit = instance._panelEdit;

						if (!vocabularyPanelEdit){
							vocabularyPanelEdit = instance._createPanelEdit();
						}
						else {
							forceStart = true;

							instance._currentPanelEditInitHandler.detach();
						}

						vocabularyPanelEdit.set('title', Liferay.Language.get('edit-vocabulary'));

						var vocabularyEditURL = instance._createURL(VOCABULARY, ACTION_EDIT, LIFECYCLE_RENDER);

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

					/**
					 * Removes 'selected' class from all vocabulary nodes
					 *
					 * @method _unselectAllVocabularies
					 * @protected
					 */

					_unselectAllVocabularies: function() {
						var instance = this;

						A.all(instance._vocabularyItemSelector).removeClass('selected');
					},

					/**
					 * Moves Category to another Vocabulary or makes it child of another Category
					 *
					 * @method _updateCategory
					 * @protected
					 * @param categoryId {String} The ID of Category
					 * @param parentCategoryId {String} The ID of Category to which source Category should be moved.
					 * @param vocabularyId {String} The ID of Vocabulary
					 */

					_updateCategory: function(categoryId, parentCategoryId, vocabularyId) {
						var instance = this;

						var moveURL = instance._createURL(CATEGORY, ACTION_MOVE, LIFECYCLE_PROCESS);

						var prefix = instance._prefixedPortletId;

						var data = [
							prefix, 'categoryId', '=', categoryId,
							'&', prefix, 'parentCategoryId', '=', parentCategoryId,
							'&', prefix, 'vocabularyId', '=', vocabularyId
						].join('');

						var config = {
							data: data,
							dataType: 'json',
							on: {
								success: function(event, id, obj) {
									var response = this.get('responseData');

									instance._onCategoryMoveSuccess(response);
								},
								failure: function(event, id, obj) {
									instance._onCategoryMoveFailure(obj);
								}
							}
						};

						A.io.request(moveURL.toString(), config);
					},

					_categoryItemSelector: '.vocabulary-categories .aui-tree-node',
					_categoryContainerSelector: '.vocabulary-categories',
					_selectedVocabulary: null,
					_selectedVocabularyId: null,
					_selectedVocabularyName: null,
					_vocabularies: null,
					_vocabularyItemSelector: '.vocabulary-list li',
					_vocabularyContainerSelector: '.vocabulary-list'
				}
			}
		);

		/**
		 * Provides VocabularyTree component.
		 *
		 * @extends TreeViewDD
		 */
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