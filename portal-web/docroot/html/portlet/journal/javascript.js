AUI().add(
	'liferay-portlet-journal',
	function(A) {
		var fieldsDataSet = new A.DataSet();

		var Journal = function(portletNamespace, articleId, instanceIdKey) {
			var instance = this;

			instance.articleId = articleId;
			instance.instanceIdKey = instanceIdKey;
			instance.messageDelay = {};
			instance.portletNamespace = portletNamespace;
			instance.uid = 0;

			var structureTreeId = instance._generateId(instance._structureTreeClass);
			var structureTree = A.one(structureTreeId);

			instance._helperCachedObjectId = instance._generateId('journalArticleHelper', instance.portletNamespace, '');

			instance._helperCachedObject = A.Node.create(
				'<div id="' + instance._helperCachedObjectId + '" class="journal-article-helper not-intersecting">' +
					'<div class="journal-component"></div>' +
					'<div class="forbidden-action"></div>' +
				'</div>'
			);

			instance._helperCachedObject.appendTo(document.body);

			instance.acceptChildren = true;

			var placeholder = A.Node.create('<div class="aui-tree-placeholder aui-tree-sub-placeholder"></div>');
			var fields = A.all(structureTreeId + instance._fieldRowsClass + '.structure-field');

			instance.nestedListOptions = {
				dd: {
					handles: [instance._handleMoveFiledClass]
				},
				dropCondition: function(event) {
					var dropNode = event.drop.get('node');

					return instance._canDropChildren(dropNode);
				},
				dropOn: 'span.folder > ul.folder-droppable',
				helper: instance._helperCachedObject,
				placeholder: placeholder,
				sortCondition: function(event) {
					var dropNode = event.drop.get('node');

					return dropNode.ancestor(structureTreeId);
				},
				sortOn: structureTreeId
			};

			instance.nestedListEvents = {
				'drag:start': function(event) {
					var helper = instance._helperCachedObject;

					helper.setStyle('height', '100px');
					helper.setStyle('width', '450px');

					instance._updateTextAreaFields('hidden');

					Liferay.Util.disableSelection(document.body);
				},

				'drag:end': function(event) {
					instance._dropField();

					instance._updateTextAreaFields('visible');

					Liferay.Util.enableSelection(document.body);
				},

				'drag:out': function(event) {
					if (!instance.acceptChildren) {
						instance._helperIntersecting();
						instance.acceptChildren = true;
					}
				},

				'drag:over': function(event) {
					var dropNode = event.drop.get('node');

					instance.acceptChildren = instance._canDropChildren(dropNode);

					if (instance.acceptChildren) {
						instance._helperIntersecting();
					}
					else {
						instance._helperNotIntersecting();
					}
				}
			};

			instance._createNestedList(
				fields,
				instance.nestedListOptions,
				instance.nestedListEvents
			);

			var journalComponentListId = instance._generateId(instance._journalComponentListClass);
			var componentFields = A.all(journalComponentListId + instance._componentFields);

			instance.componentFieldsOptions = {
				dropCondition: function(event) {
					var dropNode = event.drop.get('node');

					return instance._canDropChildren(dropNode);
				},
				dropOn: 'span.folder > ul.folder-droppable',
				helper: instance._helperCachedObject,
				placeholder: placeholder,
				sortCondition: function(event) {
					var dropNode = event.drop.get('node');

					return dropNode.ancestor(structureTreeId);
				}
			};

			instance.componentFieldsEvents = {
				'drag:start': function(event) {
					var drag = event.target;
					var proxy = drag.get('dragNode');
					var source = drag.get('node');
					var languageName = source.text();
					var componentType = instance._getComponentType(source);
					var className = 'journal-component-' + instance._stripComponentType(componentType);
					var helper = instance._helperCachedObject;
					var helperComponentIcon = instance._helperCachedObject.all('div.journal-component');

					helper.setStyle('height', '25px');
					helper.setStyle('width', '200px');

					if (helperComponentIcon) {
						helperComponentIcon.addClass(className).html(languageName);
					}

					proxy.addClass('component-dragging');

					instance._updateTextAreaFields('hidden');

					Liferay.Util.disableSelection(document.body);

					instance.clonedSource = source.cloneNode(true);

					source.placeBefore(instance.clonedSource);

					instance.clonedSource.attr('id', '');
					instance.clonedSource.guid();

					instance.clonedSource.show().setStyle('visibility', 'visible');
					instance.clonedSource.removeClass('aui-helper-hidden');
					instance.clonedSource.addClass('dragging');

					instance._createNestedList(
						instance.clonedSource,
						instance.componentFieldsOptions,
						instance.componentFieldsEvents,
						true
					);
				},

				'drag:end': function(event) {
					var drag = event.target;
					var source = drag.get('node');
					var proxy = drag.get('dragNode');

					var componentType = instance._getComponentType(source);
					var className = 'journal-component-' + instance._stripComponentType(componentType);
					var helperComponentIcon = instance._helperCachedObject.all('div.journal-component');

					proxy.removeClass('component-dragging');

					if (helperComponentIcon) {
						helperComponentIcon.removeClass(className).empty();
					}

					var addedComponent = structureTree.one('div.journal-component');

					if (addedComponent) {
						addedComponent.hide();

						var fieldInstance = instance._fieldInstanceFactory(componentType);
						var variableName = fieldInstance.get('variableName') + instance._getUID();

						if (fieldInstance.get('fieldType') == 'text_area') {
							var html = instance._buildWYSIWYGEditorHTML(fieldInstance);

							fieldInstance.set('innerHTML', html);
						}

						var htmlTemplate = instance._createFieldHTMLTemplate(fieldInstance);
						var newComponent = A.Node.create(htmlTemplate);

						addedComponent.placeBefore(newComponent);
						addedComponent.remove();

						fieldInstance.set('source', newComponent);

						var fieldLabel = fieldInstance.get('fieldLabel', variableName);

						instance._updateFieldVariableName(fieldInstance, variableName);
						instance._updateFieldLabelName(fieldInstance, fieldLabel);

						instance._createNestedList(
							newComponent,
							instance.nestedListOptions,
							instance.nestedListEvents
						);

						instance._attachEvents();

						var id = newComponent.get('id');

						fieldsDataSet.add(id, fieldInstance);

						instance._repositionEditFieldOptions();

						Liferay.Util.enableSelection(document.body);
					}
					else {
						source.remove();
					}

					instance._updateTextAreaFields('visible');

					if (instance.clonedSource) {
						var journalComponentList = instance._getById(instance._journalComponentListClass);

						instance.clonedSource.removeClass('dragging');

						if (journalComponentList.contains(source[0]) &&
							journalComponentList.contains(instance.clonedSource[0])) {

							source.remove();
						}
					}
				},

				'drag:out': instance.nestedListEvents['drag:out'],

				'drag:over': instance.nestedListEvents['drag:over']
			};

			instance._createNestedList(
				componentFields,
				instance.componentFieldsOptions,
				instance.componentFieldsEvents,
				true
			);

			var editContainerWrapper = instance._getById(instance._editFieldWrapperClass);

			editContainerWrapper.show();

			instance.editContainerContextPanel = new A.ContextPanel(
				{
					bodyContent: editContainerWrapper,
					align: {
						points: ['rc', 'lc']
					},
					trigger: 'input.edit-button'
				}
			)
			.render();

			A.ContextOverlayManager.remove(instance.editContainerContextPanel);

			instance._initializePageLoadFieldInstances();
			instance._attachEvents();
			instance._attachEditContainerEvents();
			instance._attachLiveQueryEvents();

			var currentStructureXSD = encodeURIComponent(instance._getStructureXSD());
			var structureXSDInput = instance._getInputByName(instance._getPrincipalForm(), 'structureXSD');

			structureXSDInput.val(currentStructureXSD);
		};

		Journal.prototype = {
			_getTextAreaFields: function() {
				var instance = this;

				var structureTreeId = instance._generateId(instance._structureTreeClass);

				return A.all(structureTreeId + instance._textAreaFields + ' div.journal-article-component-container');
			},

			_buildWYSIWYGEditorURL: function(fieldInstance) {
				var instance = this;

				var url = [];
				var initMethod = instance.portletNamespace + 'initEditor' + fieldInstance.get('variableName');
				var onChangeMethod = instance.portletNamespace + 'editorContentChanged';

				window[initMethod] = instance._emptyFunction;

				url.push(themeDisplay.getPathContext());
				url.push('/html/js/editor/editor.jsp?p_l_id=');
				url.push(themeDisplay.getPlid());
				url.push('&p_main_path=');
				url.push(encodeURIComponent(themeDisplay.getPathMain()));
				url.push('&doAsUserId=');
				url.push(Journal.PROXY.doAsUserId);
				url.push('&editorImpl=');
				url.push(Journal.PROXY.editorImpl);
				url.push('&toolbarSet=liferay-article');
				url.push('&initMethod=');
				url.push(initMethod);
				url.push('&onChangeMethod=');
				url.push(onChangeMethod);
				url.push('&cssPath=');
				url.push(Journal.PROXY.pathThemeCss);
				url.push('&cssClasses=portlet ');

				return url.join('');
			},

			_buildWYSIWYGEditorHTML: function(fieldInstance) {
				var instance = this;

				var name = instance.portletNamespace + 'structure_el_' + instance._getUID() + '_content';
				var url = instance._buildWYSIWYGEditorURL(fieldInstance);

				var iframeHTML = '<iframe frameborder="0" height="250" id="' + name + '" name="' + name + '" scrolling="no" src="' + url + '" width="600"></iframe>';

				return iframeHTML;
			},

			_canDropChildren: function(source) {
				var instance = this;

				var componentType = instance._getComponentType(source);

				var canDrop = true;

				if (componentType == 'multi-list' || componentType == 'list') {
					canDrop = false;
				}
				else if (source.hasClass('repeated-field')) {
					canDrop = false;
				}

				return canDrop;
			},

			_createNestedList: function(nodes, options, events, components) {
				var instance = this;

				var applyEvents = function(nestedList) {
					A.each(
						events,
						function(value, key) {
							if (key && A.Lang.isFunction(value)) {
								nestedList.on(key, value);
							}
						}
					);
				};

				var defaults = {
					dropOn: '.folder'
				};

				options = A.merge(defaults, options);

				if (!instance._nestedList) {
					instance._nestedList = new A.NestedList(options);

					applyEvents(instance._nestedList);
				}

				if (components && !instance._nestedListComponents) {
					instance._nestedListComponents = new A.NestedList(options);

					applyEvents(instance._nestedListComponents);
				}

				nodes.each(
					function() {
						var element = this;
						var nestedList = instance._nestedList;

						if (components) {
							nestedList = instance._nestedListComponents;
						}

						nestedList.add(element);
					}
				);
			},

			_attachEvents: function() {
				var instance = this;

				var portletContentWrapper = A.one(instance._portletContentClass);
				var closeButtons = instance._getCloseButtons();
				var editButtons = instance._getEditButtons();
				var repeatableButtons = instance._getRepeatableButtons();
				var defaultLanguageIdSelect = instance._getById('defaultLanguageIdSelect', portletContentWrapper);
				var downloadArticleContentBtn = instance._getById('downloadArticleContentBtn', portletContentWrapper);
				var languageIdSelect = instance._getById('languageIdSelect', portletContentWrapper);
				var previewArticleBtn = instance._getById('previewArticleBtn', portletContentWrapper);
				var saveArticleAndApproveBtn = instance._getById('saveArticleAndApproveBtn', portletContentWrapper);
				var saveArticleAndContinueBtn = instance._getById('saveArticleAndContinueBtn', portletContentWrapper);
				var saveArticleBtn = instance._getById('saveArticleBtn', portletContentWrapper);
				var variableName = instance._getById('variableName', portletContentWrapper);

				closeButtons.detach('click');
				defaultLanguageIdSelect.detach('change');
				editButtons.detach('click');
				languageIdSelect.detach('change');
				repeatableButtons.detach('click');
				saveArticleAndApproveBtn.detach('click');
				saveArticleAndContinueBtn.detach('click');
				saveArticleBtn.detach('click');

				editButtons.on(
					'click',
					function(event) {
						var source = event.target.ancestor('li').item(0);

						instance._renderEditFieldOptions(source);
					}
				);

				repeatableButtons.on(
					'click',
					function(event) {
						var source = event.target.ancestor('li').item(0);

						instance._repeatField(source);
					}
				);

				closeButtons.on(
					'click',
					function(event) {
						var source = event.target.ancestor('li').item(0);

						instance._closeField(source);
					}
				);

				saveArticleBtn.on(
					'click',
					function() {
						instance._saveArticle();
					}
				);

				saveArticleAndContinueBtn.on(
					'click',
					function() {
						var saveAndContinue = true;

						instance._saveArticle(null, saveAndContinue);
					}
				);

				saveArticleAndApproveBtn.on(
					'click',
					function() {
						instance._saveArticle('approve');
					}
				);

				if (downloadArticleContentBtn) {
					downloadArticleContentBtn.detach('click');

					downloadArticleContentBtn.on(
						'click',
						function() {
							instance._downloadArticleContent();
						}
					);
				}

				if (previewArticleBtn) {
					previewArticleBtn.detach('click');

					previewArticleBtn.on(
						'click',
						function() {
							instance._previewArticle();
						}
					);
				}

				languageIdSelect.on(
					'change',
					function() {
						instance._changeLanguageView();
					}
				);

				defaultLanguageIdSelect.on(
					'change',
					function() {
						instance._changeLanguageView();
					}
				);

				var changeStructureBtn = instance._getById('changeStructureBtn');
				var changeTemplateBtn = instance._getById('changeTemplateBtn');
				var editStructureBtn = instance._getById('editStructureBtn');
				var loadDefaultStructureBtn = instance._getById('loadDefaultStructure');
				var saveStructureBtn = instance._getById('saveStructureBtn');
				var saveStructureTriggers = A.one('.journal-save-structure-trigger');

				changeStructureBtn.detach('click');
				editStructureBtn.detach('click');
				saveStructureBtn.detach('click');
				saveStructureTriggers.detach('click');

				changeStructureBtn.on(
					'click',
					function(event) {
						if (confirm(Liferay.Language.get('selecting-a-new-structure-will-change-the-available-input-fields-and-available-templates'))) {
							var url = event.target.attr('dataChangeStructureUrl');
							instance._openPopupWindow(url, 'ChangeStructure');
						}
					}
				);

				if (changeTemplateBtn) {
					changeTemplateBtn.detach('click');

					changeTemplateBtn.on(
						'click',
						function(event) {
							if (confirm(Liferay.Language.get('selecting-a-template-will-change-the-structure,-available-input-fields,-and-available-templates'))) {
								var url = event.target.attr('dataChangeTemplateUrl');
								instance._openPopupWindow(url, 'ChangeTemplate');
							}
						}
					);
				}

				if (loadDefaultStructureBtn) {
					loadDefaultStructureBtn.detach('click');

					loadDefaultStructureBtn.on(
						'click',
						function() {
							instance._loadDefaultStructure();
						}
					);
				}

				saveStructureBtn.on(
					'click',
					function() {
						instance._openSaveStructureDialog();
					}
				);

				saveStructureTriggers.on(
					'click',
					function(event) {
						saveStructureBtn.simulate('click');

						return false;
					}
				);

				editStructureBtn.on(
					'click',
					function(event) {
						var fieldsContainer = instance._getById(instance._fieldsContainerClass);

						if (fieldsContainer.hasClass('journal-edit-mode')) {
							instance._disableEditMode();
						}
						else {
							instance._enableEditMode();
						}
					}
				);
			},

			_attachEditContainerEvents: function(attribute) {
				var instance = this;

				var editContainerWrapper = instance._getById(instance._editFieldWrapperClass);
				var editContainerCheckboxes = editContainerWrapper.all('input[type=checkbox]');
				var editContainerInputs = editContainerWrapper.all('input[type=text]');
				var editContainerTextareas = editContainerWrapper.all('textarea');
				var editFieldCancelButton = editContainerWrapper.one('.cancel-button');
				var editFieldCloseButton = editContainerWrapper.one('.close-button');
				var editFieldSaveButton = editContainerWrapper.one('.save-button');
				var languageIdSelect = instance._getById('languageIdSelect');
				var localizedCheckbox = instance._getById('localized', editContainerWrapper);

				editContainerCheckboxes.detach('click');
				editContainerInputs.detach('change');
				editContainerInputs.detach('keypress');
				editContainerTextareas.detach('change');
				editContainerTextareas.detach('keypress');
				editFieldCancelButton.detach('click');
				editFieldCloseButton.detach('click');
				editFieldSaveButton.detach('click');

				var editContainerSaveMode = function() {
					instance._editContainerSaveMode();
				};

				editContainerCheckboxes.on('click', editContainerSaveMode);
				editContainerInputs.on('change', editContainerSaveMode);
				editContainerInputs.on('keypress', editContainerSaveMode);
				editContainerTextareas.on('change', editContainerSaveMode);
				editContainerTextareas.on('keypress', editContainerSaveMode);

				editFieldSaveButton.on(
					'click',
					function() {
						var source = instance._getSelectedField();

						instance._saveEditFieldOptions(source);
					}
				);

				var closeEditField = function(event) {
					instance._closeEditFieldOptions();
				};

				editFieldCancelButton.on('click', closeEditField);
				editFieldCloseButton.on('click', closeEditField);

				localizedCheckbox.on(
					'click',
					function(event) {
						var source = instance._getSelectedField();
						var defaultLocale = instance._getDefaultLocale();
						var checkbox = event.target;
						var isLocalized = checkbox.test('input[type=checkbox]');
						var localizedValue = source.one('.journal-article-localized');

						if (languageIdSelect) {
							var selectedLocale = languageIdSelect.val();
						}

						if (localizedValue) {
							if (isLocalized) {
								localizedValue.val(selectedLocale || defaultLocale);
							}
							else if (!confirm(Liferay.Language.get('unchecking-this-field-will-remove-localized-data-for-languages-not-shown-in-this-view'))) {
								checkbox.attr('checked', 'checked');
								localizedValue.val(selectedLocale || defaultLocale);
							}
							else {
								localizedValue.val('false');
							}
						}
					}
				);
			},

			_attachLiveQueryEvents: function() {
				var instance = this;

				var journalArticleContainerId = instance._generateId(instance._fieldsContainerClass)

				var addListItem = function(event) {
					var icon = event.currentTarget;
					var iconParent = icon.get('parentNode');
					var select = iconParent.get('parentNode').one('select');
					var keyInput = iconParent.one('input.journal-list-key');
					var key = instance._formatOptionsKey(keyInput.val());
					var valueInput = iconParent.one('input.journal-list-value');
					var value = valueInput.val();

					if (key && value) {
						var options = select.all('option');

						options.each(
							function(option) {
								if (option.text().toLowerCase() == key.toLowerCase()) {
									option.remove();
								}
							}
						);

						var option = A.Node.create('<option></option>').val(value).text(key);

						select.append(option);
						option.attr('selected', 'selected');
						keyInput.val('').focus();
						valueInput.val('value');
					}
					else {
						keyInput.focus();
					}
				};

				var keyPressAddItem = function(event) {
					var btnScope = event.currentTarget.get('parentNode').one('span.journal-add-field');

					if (event.keyCode == 13) {
						event.currentTarget = btnScope;

						addListItem.apply(event.currentTarget, arguments);
					}
				};

				var removeListItem = function(event) {
					var icon = event.currentTarget;
					var select = icon.get('parentNode').one('select').focus();
					var options = select.all('option');

					options.each(
						function(node, i) {
							if (node.attr('selected')) {
								node.remove();
							}
						}
					);
				};

				var container = A.get(journalArticleContainerId);

				container.delegate(
					'click',
					function(event) {
						var currentTarget = event.currentTarget;
						var source = currentTarget.ancestor('li').item(0);

						instance._repeatField(source);
					},
					'.repeatable-field-image'
				);

				container.delegate(
					'mouseenter',
					function(event) {
						var source = event.currentTarget.ancestor('li');

						source.addClass('repeatable-border');
					},
					'.repeatable-field-image'
				);

				container.delegate(
					'mouseleave',
					function(event) {
						var source = event.currentTarget.ancestor('li');

						source.removeClass('repeatable-border');
					},
					'.repeatable-field-image'
				);

				container.delegate('keypress', keyPressAddItem, '.journal-list-key');
				container.delegate('keypress', keyPressAddItem, '.journal-list-value');

				container.delegate('click', addListItem, '.journal-add-field');
				container.delegate('click', removeListItem, '.journal-delete-field');

				container.delegate(
					'click',
					function(event) {
						var button = event.currentTarget;

						var imageWrapper = button.get('parentNode').one('.journal-image-wrapper');
						var imageDelete = button.get('parentNode').one('.journal-image-delete');

						if (imageDelete.val() == '') {
							imageDelete.val('delete');
							imageWrapper.hide();

							var buttonValue = Liferay.Language.get('cancel');

							button.val(buttonValue);
						}
						else {
							imageDelete.val('');
							imageWrapper.show();

							var buttonValue = Liferay.Language.get('delete');

							button.val(buttonValue);
						}
					},
					'.journal-image-delete-btn'
				);

				container.delegate(
					'click',
					function(event) {
						var link = event.currentTarget;
						var imagePreviewDiv = link.get('parentNode').get('parentNode').one('.journal-image-preview');

						var showLabel = link.one('.show-label').show();
						var hideLabel = link.one('.hide-label').show();

						var visible = imagePreviewDiv.hasClass('aui-helper-hidden');

						if (visible) {
							showLabel.hide();
							hideLabel.show();
						}
						else {
							showLabel.show();
							hideLabel.hide();
						}

						imagePreviewDiv.toggle();
					},
					'.journal-image-link'
				);

				var _attachButtonInputSelector = function(id, title, handlerName) {
					var buttonId = 'input.journal-' + id + '-button';
					var textId = 'input.journal-' + id + '-text';

					container.delegate(
						'click',
						function(event) {
							var button = event.currentTarget;
							var input = button.get('parentNode').one(textId);
							var imageGalleryUrl = button.attr('data' + id + 'Url');

							window[instance.portletNamespace + handlerName] = function(url) {
								input.val(url);
							};

							instance._openPopupWindow(imageGalleryUrl, title);
						},
						buttonId
					);
				};

				_attachButtonInputSelector('documentlibrary', 'DocumentLibrary', 'selectDocumentLibrary');
				_attachButtonInputSelector('imagegallery', 'ImageGallery', 'selectImageGallery');

				container.delegate(
					'mouseover',
					function(event) {
						var image = event.currentTarget;
						var source = image.ancestor('li').item(0);
						var id = source.get('id');
						var fieldInstance = fieldsDataSet.item(id);

						if (fieldInstance) {
							var instructions = fieldInstance.get('instructions');

							Liferay.Portal.ToolTip.show(this, instructions);
						}
					},
					'img.journal-article-instructions-container'
				);
			},

			_emptyFunction: function() {
				return '';
			},

			_openSaveStructureDialog: function() {
				var instance = this;

				var form = instance._getPrincipalForm();

				var structureIdInput = instance._getInputByName(form, 'structureId');
				var structureNameInput = instance._getInputByName(form, 'structureName');
				var structureDescriptionInput = instance._getInputByName(form, 'structureDescription');

				var structureId = structureIdInput.val();
				var structureName = structureNameInput.val();

				instance._getSaveDialog(
					function(dialog) {
						var dialogFields = dialog.fields;

						dialogFields.contentXSD = instance._getStructureXSD();

						dialogFields.dialogStructureName.val(structureNameInput.val());
						dialogFields.dialogDescription.val(structureDescriptionInput.val());
						dialogFields.dialogStructureId.attr('disabled', 'disabled').val(dialogFields.autoGenerateIdMessage);

						if (structureId) {
							dialogFields.saveStructureAutogenerateId.hide();
							dialogFields.dialogStructureId.val(structureIdInput.val());
						}

						dialog.show();
					}
				);
			},

			_openPopupWindow: function(url, title) {
				var popup = window.open(url, title, 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680');

				popup.focus();
			},

			_appendStructureChildren: function(source, buffer, generateArticleContent) {
				var instance = this;

				var selector = '> span.folder > ul > li';

				if (!generateArticleContent) {
					selector += '.structure-field:not(.repeated-field)';
				}

				var children = source.all(selector);

				A.each(
					children,
					function(sourceChild) {
						instance._appendStructureDynamicElementAndMetaData(sourceChild, buffer, generateArticleContent);
					}
				);
			},

			_appendStructureDynamicElementAndMetaData: function(source, buffer, generateArticleContent) {
				var instance = this;
				var id = source.get('id');
				var fieldInstance = fieldsDataSet.item(id);

				if (fieldInstance) {
					var dynamicElement;
					var type = fieldInstance.get('fieldType');
					var indexType = fieldInstance.get('indexType');

					if (generateArticleContent) {
						var instanceId = fieldInstance.get('instanceId');

						if (!instanceId) {
							instanceId = instance._generateInstanceId();
							fieldInstance.set('instanceId', instanceId);
						}

						dynamicElement = instance._createDynamicNode(
							'dynamic-element',
							{
								'instance-id': instanceId,
								name: encodeURI(fieldInstance.get('variableName')),
								type: type,
								'index-type': indexType
							}
						);
					}
					else {
						dynamicElement = instance._createDynamicNode(
							'dynamic-element',
							{
								name: encodeURI(fieldInstance.get('variableName')),
								type: type,
								'index-type': indexType,
								repeatable: fieldInstance.get('repeatable')
							}
						);
					}

					var dynConAttributes = null;

					if (fieldInstance.get('localized')) {
						dynConAttributes = {
							'language-id': fieldInstance.get('localizedValue')
						};
					}

					var dynamicContent = instance._createDynamicNode('dynamic-content', dynConAttributes);

					var metadata = instance._createDynamicNode('meta-data');

					var entryInstructions = instance._createDynamicNode(
						'entry',
						{
							name: 'instructions'
						}
					);

					var entryRequired = instance._createDynamicNode(
						'entry',
						{
							name: 'required'
						}
					);

					var displayAsTooltip = instance._createDynamicNode(
						'entry',
						{
							name: 'displayAsTooltip'
						}
					);

					var label = instance._createDynamicNode(
						'entry',
						{
							name: 'label'
						}
					);

					var predefinedValue = instance._createDynamicNode(
						'entry',
						{
							name: 'predefinedValue'
						}
					);

					buffer.push(dynamicElement.openTag);

					if (!generateArticleContent) {
						instance._appendStructureFieldOptionsBuffer(source, buffer);
					}

					instance._appendStructureChildren(source, buffer, generateArticleContent);

					if (!generateArticleContent) {
						buffer.push(metadata.openTag);
							var displayAsTooltipVal = instance.normalizeValue(
								fieldInstance.get('displayAsTooltip')
							);
							buffer.push(displayAsTooltip.openTag);
							buffer.push('<![CDATA[' + displayAsTooltipVal + ']]>');
							buffer.push(displayAsTooltip.closeTag);

							var requiredVal = instance.normalizeValue(
								fieldInstance.get('required')
							);
							buffer.push(entryRequired.openTag);
							buffer.push('<![CDATA[' + requiredVal + ']]>');
							buffer.push(entryRequired.closeTag);

							var instructionsVal = instance.normalizeValue(
								fieldInstance.get('instructions')
							);
							buffer.push(entryInstructions.openTag);
							buffer.push('<![CDATA[' + instructionsVal + ']]>');
							buffer.push(entryInstructions.closeTag);

							var fieldLabelVal = instance.normalizeValue(
								fieldInstance.get('fieldLabel')
							);
							buffer.push(label.openTag);
							buffer.push('<![CDATA[' + fieldLabelVal + ']]>');
							buffer.push(label.closeTag);

							var predefinedValueVal = instance.normalizeValue(
								fieldInstance.get('predefinedValue')
							);
							buffer.push(predefinedValue.openTag);
							buffer.push('<![CDATA[' + predefinedValueVal + ']]>');
							buffer.push(predefinedValue.closeTag);
							buffer.push(metadata.closeTag);
					}
					else if (generateArticleContent) {
						buffer.push(dynamicContent.openTag);

						var appendOptions = (type == 'multi-list' || type == 'list');

						if (appendOptions) {
							instance._appendStructureFieldOptionsBuffer(source, buffer, generateArticleContent);
						}
						else {
							var content = fieldInstance.getContent(source) || '';

							buffer.push('<![CDATA[' + content + ']]>')
						}

						buffer.push(dynamicContent.closeTag);
					}

					buffer.push(dynamicElement.closeTag);
				}
			},

			_formatOptionsKey: function(s) {
				return s.replace(/\W+/g, ' ').replace(/^\W+|\W+$/g, '').replace(/ /g, '_');
			},

			_appendStructureFieldOptionsBuffer: function(source, buffer, generateArticleContent) {
				var instance = this;
				var id = source.get('id');
				var fieldInstance = fieldsDataSet.item(id);
				var type = fieldInstance.get('fieldType');
				var optionsList = source.all('> .folder > .field-container > .journal-article-component-container > .journal-list-subfield option');

		 		if (optionsList) {
					A.each(
						optionsList,
						function(option) {
							var optionKey = instance._formatOptionsKey(option.text());
							var optionValue = option.val();

							if (!generateArticleContent) {
								var dynamicElementOption = instance._createDynamicNode(
									'dynamic-element',
									{
										name: optionKey,
										type: optionValue,
										'repeatable': fieldInstance.get('repeatable')
									}
								);

								buffer.push(dynamicElementOption.openTag + dynamicElementOption.closeTag);
							}
							else {
								if (option.get('selected')) {
									var multiList = (type == 'multi-list');
									var option = instance._createDynamicNode('option');

									if (multiList) {
										buffer.push(option.openTag);
									}

									buffer.push('<![CDATA[' + optionKey + ']]>');

									if (multiList) {
										buffer.push(option.closeTag);
									}
								}
							}
						}
					);
				}
			},

			_addStructure: function(structureId, autoStructureId, name, description, xsd, callback) {

				var groupId = themeDisplay.getScopeGroupId();
				var addCommunityPermissions = true;
				var addGuestPermissions = true;
				var parentStructureId = '';

				var serviceParameterTypes = [
					'long',
					'java.lang.String',
					'boolean',
					'java.lang.String',
					'java.lang.String',
					'java.lang.String',
					'java.lang.String',
					'com.liferay.portal.service.ServiceContext'
				];

				Liferay.Service.Journal.JournalStructure.addStructure(
					{
						groupId: groupId,
						structureId: structureId,
						autoStructureId: autoStructureId,
						parentStructureId: parentStructureId,
						name: name,
						description: description,
						xsd: xsd,
						serviceContext: jQuery.toJSON(
							{
								addCommunityPermissions: addCommunityPermissions,
								addGuestPermissions: addGuestPermissions,
								scopeGroupId: themeDisplay.getScopeGroupId()
							}
						),
						serviceParameterTypes: jQuery.toJSON(serviceParameterTypes)
					},
					function(message) {
						if (A.Lang.isFunction(callback)) {
							callback(message);
						}
					}
				);
			},

			_updateStructure: function(parentStructureId, structureId, name, description, xsd, callback) {

				var groupId = themeDisplay.getScopeGroupId();

				var serviceParameterTypes = [
					'long',
					'java.lang.String',
					'java.lang.String',
					'java.lang.String',
					'java.lang.String',
					'java.lang.String',
					'com.liferay.portal.service.ServiceContext'
				];

				Liferay.Service.Journal.JournalStructure.updateStructure(
					{
						groupId: groupId,
						structureId: structureId,
						parentStructureId: parentStructureId || '',
						name: name,
						description: description,
						xsd: xsd,
						serviceContext: jQuery.toJSON(
							{
								scopeGroupId: themeDisplay.getScopeGroupId()
							}
						),
						serviceParameterTypes: jQuery.toJSON(serviceParameterTypes)
					},
					function(message) {
						if (A.Lang.isFunction(callback)) {
							callback(message);
						}
					}
				);
			},

			_closeEditFieldOptions: function() {
				var instance = this;

				var fieldsContainer = instance._getById(instance._fieldsContainerClass);
				var editContainerWrapper = instance._getById(instance._editFieldWrapperClass);
				var structureTree = instance._getById(instance._structureTreeClass)

				instance.editContainerContextPanel.hide();

				instance._unselectFields();
			},

			_closeField: function(source) {
				var instance = this;
				var fields = instance._getFields();

				if (fields && fields.size() <= 1) {
					return;
				}

				if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this-field-and-all-its-children'))) {
					instance._closeRepeatedSiblings(source);
					instance._closeEditFieldOptions();

					if (source.inDoc()) {
						source.remove();
					}
				}
			},

			_closeRepeatedSiblings: function(source) {
				var instance = this;
				var id = source.get('id');
				var fieldInstance = fieldsDataSet.item(id);

				if (fieldInstance.get('repeatable')) {
					var repeatedFields = instance._getRepeatedSiblings(fieldInstance);

					if (repeatedFields) {
						repeatedFields.remove();
					}
				}
			},

			_downloadArticleContent: function() {
				var instance = this;

				var downloadAction = themeDisplay.getPathMain() + '/journal/get_article_content';
				var form = instance._getPrincipalForm();

				var articleContent = instance._getArticleContentXML();
				var xmlInput = instance._getInputByName(form, 'xml', true);

				if (instance._hasStructureChanged()) {
					if (confirm(Liferay.Language.get('you-should-save-the-structure-first'))) {
						instance._openSaveStructureDialog();
					}

					return;
				}

				form.attr('action', downloadAction);
				form.attr('target', '_self');

				xmlInput.val(articleContent);

				form.submit();
			},

			_editContainerSaveMode: function() {
				var instance = this;

				var editContainerWrapper = instance._getById(instance._editFieldWrapperClass);

				editContainerWrapper.addClass('save-mode');
				instance.editContainerModified = true;
			},

			_editContainerNormalMode: function() {
				var instance = this;

				var editContainerWrapper = instance._getById(instance._editFieldWrapperClass);

				editContainerWrapper.removeClass('save-mode');
				instance.editContainerModified = false;
			},

			_fieldInstanceFactory: function(options) {
				var instance = this;

				var type = 'text';

				if (YAHOO.lang.isString(options)) {
					type = options;
					options = null;
				}
				else {
					type = options.type;

					delete options.type;
				}

				options = options || {};

				var model = {
					'boolean': Journal.FieldModel.Boolean,
					'document_library': Journal.FieldModel.DocumentLibrary,
					'image': Journal.FieldModel.Image,
					'image_gallery': Journal.FieldModel.ImageGallery,
					'link_to_layout': Journal.FieldModel.LinkToPage,
					'list': Journal.FieldModel.List,
					'multi-list': Journal.FieldModel.MultiList,
					'selection_break': Journal.FieldModel.SelectionBreak,
					'text': Journal.FieldModel.Text,
					'text_area': Journal.FieldModel.TextArea,
					'text_box': Journal.FieldModel.TextBox
				};

				YAHOO.lang.augmentObject(options, model[type]);

				var fieldInstance = new Journal.StructureField(options, instance.portletNamespace);

				return fieldInstance;
			},

			_createFieldHTMLTemplate: function(field) {
				var instance = this;

				var fieldContainer = field.getFieldContainer();
				var fieldElementContainer = field.getFieldElementContainer();
				var innerHTML = field.get('innerHTML');
				var type = field.get('fieldType');

				fieldElementContainer.html(innerHTML);

				return fieldContainer.html();
			},

			_createDynamicNode: function(nodeName, attributeMap) {
				var attrs = [];
				var dynamicElement = [];

				if (!nodeName) {
					nodeName = 'dynamic-element';
				}

				var dynamicElementModel = ['<', nodeName, (attributeMap ? ' ' : ''), , '>', ,'</', nodeName, '>'];

				A.each(
					attributeMap || {},
					function(value, key) {
						if (value !== undefined) {
							attrs.push([key, '="', value, '" '].join(''));
						}
					}
				);

				dynamicElementModel[3] = attrs.join('').replace(/[\s]+$/g, '');
				dynamicElement = dynamicElementModel.join('').replace(/></, '>><<').split(/></);

				return {
					closeTag: dynamicElement[1],
					openTag: dynamicElement[0]
				};
			},

			_getSaveDialog: function(openCallback) {
				var instance = this;

				if (!instance._saveDialog) {
					var saveStructureTemplateDialog = instance._getById('saveStructureTemplateDialog');
					var htmlTemplate = saveStructureTemplateDialog.html();
					var title = Liferay.Language.get('editing-structure-details');

					var form = instance._getPrincipalForm();

					var structureIdInput = instance._getInputByName(form, 'structureId');
					var structureNameInput = instance._getInputByName(form, 'structureName');
					var structureDescriptionInput = instance._getInputByName(form, 'structureDescription');
					var storedStructureXSD = instance._getInputByName(form, 'structureXSD');

					var saveCallback = function() {
						var dialogFields = instance._saveDialog.fields;

						instance._showMessage(
							dialogFields.messageElement,
							'info',
							Liferay.Language.get('waiting-for-an-answer')
						);

						var form = instance._getPrincipalForm();
						var structureIdInput = instance._getInputByName(form, 'structureId');
						var structureId = structureIdInput.val();

						if (!structureId) {
							var autoGenerateId = dialogFields.saveStructureAutogenerateId.get('checked');

							instance._addStructure(
								dialogFields.dialogStructureId.val(),
								autoGenerateId,
								dialogFields.dialogStructureName.val(),
								dialogFields.dialogDescription.val(),
								dialogFields.contentXSD,
								serviceCallback
							);
						}
						else {
							instance._updateStructure(
								instance._getParentStructureId(),
								dialogFields.dialogStructureId.val(),
								dialogFields.dialogStructureName.val(),
								dialogFields.dialogDescription.val(),
								dialogFields.contentXSD,
								serviceCallback
							);
						}
					};

					instance._saveDialog = new A.Dialog(
						{
							bodyContent: htmlTemplate,
							centered: true,
							modal: true,
							title: title,
							width: 550,
							buttons: [
								{
									text: Liferay.Language.get('save'),
									handler: saveCallback
								},
								{
									text: Liferay.Language.get('cancel'),
									handler: function() {
										this.close();
									}
								}
							]
						}
					)
					.render();

					var dialogBody = A.Node.getDOMNode(instance._saveDialog.get('contentBox'));
					var dialog = A.get(dialogBody);

					instance._saveDialog.fields = {
						autoGenerateIdMessage: Liferay.Language.get('autogenerate-id'),
						contentXSD: '',
						dialogDescription: instance._getById('saveStructureStructureDescription', dialog),
						dialogStructureId: instance._getById('saveStructureStructureId', dialog),
						dialogStructureName: instance._getById('saveStructureStructureName', dialog),
						idInput: instance._getById('saveStructureStructureId', dialog),
						loadDefaultStructure: instance._getById('loadDefaultStructure'),
						messageElement: instance._getById('saveStructureMessage', dialog),
						saveStructureAutogenerateId: instance._getById('saveStructureAutogenerateId', dialog),
						showStructureIdContainer: instance._getById('showStructureIdContainer', dialog),
						structureIdContainer: instance._getById('structureIdContainer', dialog),
						structureNameLabel: instance._getById('structureNameLabel')
					};

					var dialogFields = instance._saveDialog.fields;

					var serviceCallback = function(message) {
						var exception = message.exception;

						if (!exception) {
							structureDescriptionInput.val(message.description);
							structureIdInput.val(message.structureId);
							structureNameInput.val(message.name);
							storedStructureXSD.val(encodeURIComponent(dialogFields.contentXSD));

							dialogFields.dialogStructureId.val(message.structureId);
							dialogFields.dialogStructureName.val(message.name);
							dialogFields.dialogDescription.val(message.description);
							dialogFields.structureNameLabel.html(message.name);

							dialogFields.saveStructureAutogenerateId.hide();

							if (dialogFields.loadDefaultStructure) {
								dialogFields.loadDefaultStructure.show();
							}

							dialogFields.dialogStructureId.attr('disabled', 'disabled');

							instance._showMessage(
								dialogFields.messageElement,
								'success',
								Liferay.Language.get('your-request-processed-successfully')
							);

							var structureMessage = instance._getById('structureMessage');

							structureMessage.hide();
						}
						else {
							var errorMessage = instance._translateErrorMessage(exception);

							instance._showMessage(
								dialogFields.messageElement,
								'error',
								errorMessage
							);
						}
					};

					dialogFields.saveStructureAutogenerateId.on(
						'click',
						function(event) {
							var checkbox = event.target;
							var isChecked = checkbox.attr('checked');

							if (isChecked) {
								dialogFields.dialogStructureId.attr('disabled', 'disabled').val(dialogFields.autoGenerateIdMessage);
							}
							else {
								dialogFields.dialogStructureId.attr('disabled', '').val('');
							}
						}
					);

					dialogFields.showStructureIdContainer.on(
						'click',
						function(event) {
							var isHidden = dialogFields.structureIdContainer.hasClass('aui-helper-hidden');

							if (!isHidden) {
								dialogFields.structureIdContainer.hide();
							}
							else {
								dialogFields.structureIdContainer.show();
							}

							event.halt();
						}
					);

					dialogFields.dialogStructureName.focus();
				}
				else {
					instance._saveDialog.show();
				}

				if (openCallback) {
					openCallback.apply(instance, [instance._saveDialog]);
				}
			},

			_changeLanguageView: function() {
				var instance = this;

				var form = instance._getPrincipalForm();
				var articleContent = instance._getArticleContentXML();

				var articleContent = instance._getArticleContentXML();
				var cmdInput = instance._getInputByName(form, 'cmd');
				var contentInput = instance._getInputByName(form, 'content');
				var defaultLocaleInput = instance._getInputByName(form, 'defaultLocale');
				var languageIdInput = instance._getInputByName(form, 'languageId');
				var redirectInput = instance._getInputByName(form, 'redirect');

				if (confirm(Liferay.Language.get('would-you-like-to-save-the-changes-made-to-this-language'))) {
					cmdInput.val('update');
				}
				else {
					if (!confirm(Liferay.Language.get('are-you-sure-you-want-to-switch-the-languages-view'))) {
						languageIdInput.one('option[value=' + defaultLocaleInput.val() + ']').attr('selected', 'selected');
						return;
					}
				}

				var languageId = languageIdInput.val();
				var getLanguageViewURL = window[instance.portletNamespace + 'getLanguageViewURL'];
				var languageViewURL = getLanguageViewURL(languageId);

				redirectInput.val(languageViewURL);
				contentInput.val(articleContent);

				form.submit();
			},

			_dropField: function() {
				var instance = this;

				instance._repositionEditFieldOptions();
				Liferay.Util.enableSelection(document.body);
			},

			_disableEditMode: function() {
				var instance = this;

				var articleHeaderEdit = instance._getById('articleHeaderEdit');
				var editStructureBtn = instance._getById('editStructureBtn');
				var fieldsContainer = instance._getById(instance._fieldsContainerClass);
				var journalComponentList = instance._getById(instance._journalComponentListClass);
				var saveStructureBtn = instance._getById('saveStructureBtn');

				instance._closeEditFieldOptions();

				articleHeaderEdit.show();
				saveStructureBtn.hide();
				journalComponentList.hide();

				fieldsContainer.removeClass('journal-edit-mode');

				var structureBtnText = Liferay.Language.get('edit');

				editStructureBtn.val(structureBtnText);

				A.all('input.journal-list-label').attr('disabled', 'disabled');

				if (instance._hasStructureChanged()) {
					var structureMessage = instance._getById('structureMessage');

					instance._showMessage(
						structureMessage,
						'alert',
						null,
						10000
					);
				}
			},

			_enableEditMode: function() {
				var instance = this;

				var articleHeaderEdit = instance._getById('articleHeaderEdit')
				var editStructureBtn = instance._getById('editStructureBtn');
				var fieldsContainer = instance._getById(instance._fieldsContainerClass);
				var journalComponentList = instance._getById(instance._journalComponentListClass);
				var saveStructureBtn = instance._getById('saveStructureBtn');

				instance._editContainerNormalMode();

				articleHeaderEdit.hide();
				saveStructureBtn.show();
				fieldsContainer.addClass('journal-edit-mode');
				journalComponentList.show();

				var structureTree = instance._getById(instance._structureTreeClass);
				var structureBtnText = Liferay.Language.get('stop-editing');

				editStructureBtn.val(structureBtnText);

				structureTree.all('.journal-list-label').attr('disabled', '');

				var structureMessage = instance._getById('structureMessage');
				instance._clearMessage(structureMessage);
			},

			_enableFields: function() {
				var instance = this;

				var fieldsContainer = instance._getById(instance._fieldsContainerClass);

				fieldsContainer.all('input:not(:button)').attr('disabled', '');
				fieldsContainer.all('textarea').attr('disabled', '');
				fieldsContainer.all('select').attr('disabled', '');
			},

			_disableFields: function() {
				var instance = this;

				var fieldsContainer = instance._getById(instance._fieldsContainerClass);

				fieldsContainer.all('input:not(:button)').attr('disabled', 'disabled');
				fieldsContainer.all('textarea').attr('disabled', 'disabled');
				fieldsContainer.all('select').attr('disabled', 'disabled');
			},

			_generateInstanceId: function() {
				var instance = this;

				var instanceId = '';

				var key = instance.instanceIdKey;

				for (var i = 0; i < 8; i++) {
					var pos = Math.floor(Math.random() * key.length);

					instanceId += key.substring(pos, pos + 1);
				}

				return instanceId;
			},

			_generateId: function(id, namespace, prefix) {
				var instance = this;

				if (typeof namespace != 'string') {
					namespace = instance.portletNamespace;
				}

				if (typeof prefix != 'string') {
					prefix = '#';
				}

				id = id.replace(/^#/, '');

				return prefix + namespace + id;
			},

			_getById: function(id) {
				var instance = this;
				var length = arguments.length;
				var namespace = null;
				var scope = null;

				if (length > 1) {
					scope = arguments[length - 1];
				}

				if (length == 3) {
					namespace = arguments[1];
				}

				return A.get(scope || document).one(instance._generateId(id, namespace));
			},

			_getComponentType: function(source) {
				return source.attr('dataType');
			},

			_getCloseButtons: function() {
				var instance = this;

				return A.all(instance._closeButtonsClass);
			},

			_getDefaultLocale: function() {
				var instance = this;

				return instance._getById('defaultLocale').val();
			},

			_getEditButtons: function() {
				var instance = this;
				var structureTreeId = instance._generateId(instance._structureTreeClass);

				return A.all(structureTreeId + instance._editButtonsClass);
			},

			_getRepeatableButtons: function() {
				var instance = this;
				var structureTreeId = instance._generateId(instance._structureTreeClass);

				return A.all(structureTreeId + instance._repeatableButtonsClass);
			},

			_getParentStructureId: function() {
				var instance = this;

				return instance._getById('parentStructureId').val();
			},

			_getEditButton: function(source) {
				var instance = this;

				return source.one('input.edit-button');
			},

			_getFields: function() {
				var instance = this;
				var structureTreeId = instance._generateId(instance._structureTreeClass);

				return A.all(structureTreeId + instance._fieldRowsClass);
			},

			_getRepeatedSiblings: function(fieldInstance) {
				var instance = this;
				var structureTreeId = instance._generateId(instance._structureTreeClass);
				var selector = structureTreeId + instance._fieldRowsClass + '[dataName=' + fieldInstance.get('variableName') + '].repeated-field';

				return A.all(selector);
			},

			_getSelectedField: function() {
				var instance = this;
				var selected = null;
				var fields = instance._getFields();

				if (fields) {
					selected = fields.filter('.selected');
				}

				return selected ? selected.item(0) : null;
			},

			_getStructureXSD: function() {
				var instance = this;

				var buffer = [];
				var structureTreeId = instance._generateId(instance._structureTreeClass);
				var sourceRoots = A.all(structureTreeId + ' > li.structure-field:not(.repeated-field)');

				var root = instance._createDynamicNode('root');

				buffer.push(root.openTag);

				A.each(
					sourceRoots,
					function(source) {
						instance._appendStructureDynamicElementAndMetaData(source, buffer);
					}
				);

				buffer.push(root.closeTag);

				return buffer.join('');
			},

			_getArticleContentXML: function() {
				var instance = this;

				var buffer = [];
				var structureTreeId = instance._generateId(instance._structureTreeClass);
				var sourceRoots = A.all(structureTreeId + ' > li');

				var attributes = null;
				var availableLocales = [];
				var stillLocalized = false;
				var availableLocalesElements = A.all('[name=' + instance.portletNamespace + 'available_locales]');
				var defaultLocale = instance._getById('defaultLocale').val();

				instance._getFields().each(
					function(field) {
						var id = field.get('id');
						var fieldInstance = fieldsDataSet.item(id);
						var isLocalized = fieldInstance.get('localized');

						if (isLocalized) {
							stillLocalized = true;
						}
					}
				);

				if (stillLocalized) {
					availableLocalesElements.each(
						function(field) {
							var locale = field.val();

							if (locale) {
								availableLocales.push(locale);
							}
						}
					);

					attributes = {
						'available-locales': availableLocales.join(','),
						'default-locale': defaultLocale
					};
				}

				var root = instance._createDynamicNode('root', attributes);

				buffer.push(root.openTag);

				sourceRoots.each(
					function(source) {
						instance._appendStructureDynamicElementAndMetaData(source, buffer, true);
					}
				);

				buffer.push(root.closeTag);

				return buffer.join('');
			},

			_getPrincipalForm: function(formName) {
				var instance = this;

				return A.one('form[name=' + instance.portletNamespace + (formName || 'fm1') + ']');
			},

			_getInputByName: function(currentForm, name, withoutNamespace) {
				var instance = this;

				var inputName = withoutNamespace ? name : instance.portletNamespace + name;

				return A.get(currentForm).one('[name=' + inputName + ']');
			},

			_getUID: function() {
				var instance = this;

				instance.uid = parseInt(Math.random() * Math.pow(10, 4), 10);

				return instance.uid;
			},

			_helperIntersecting: function() {
				var instance = this;

				instance._helperCachedObject.removeClass('not-intersecting');
			},

			_helperNotIntersecting: function(helper) {
				var instance = this;

				instance._helperCachedObject.addClass('not-intersecting');
			},

			_hideEditContainerMessage: function() {
				var instance = this;

				var selector = instance._generateId('journalMessage');

				A.one(selector).hide();

				clearTimeout(instance.messageDelay[selector]);
			},

			_hasStructureChanged: function(attribute) {
				var instance = this;

				var form = instance._getPrincipalForm();
				var storedStructureXSD = instance._getInputByName(form, 'structureXSD').val();

				var hasChanged = (storedStructureXSD != encodeURIComponent(instance._getStructureXSD()));

				return hasChanged;
			},

			_loadDefaultStructure: function() {
				var instance = this;

				var form = instance._getPrincipalForm();

				var structureIdInput = instance._getInputByName(form, 'structureId');
				var templateIdInput = instance._getInputByName(form, 'templateId');
				var contentInput = instance._getInputByName(form, 'content');

				structureIdInput.val('');
				templateIdInput.val('');
				contentInput.val('');

				form.submit();
			},

			_loadEditFieldOptions: function(source) {
				var instance = this;
				var id = source.get('id');
				var fieldInstance = fieldsDataSet.item(id);

				var check = function(checked) {
					return checked ? 'checked' : '';
				};

				if (fieldInstance) {
					var editContainerWrapper = instance._getById(instance._editFieldWrapperClass);

					var displayAsTooltip = instance._getById('displayAsTooltip', editContainerWrapper);
					var repeatable = instance._getById('repeatable', editContainerWrapper);
					var fieldType = instance._getById('fieldType', editContainerWrapper);
					var localizedCheckbox = instance._getById('localized', editContainerWrapper);
					var instructions = instance._getById('instructions', editContainerWrapper);
					var predefinedValue = instance._getById('predefinedValue', editContainerWrapper);
					var required = instance._getById('required', editContainerWrapper);
					var variableName = instance._getById('variableName', editContainerWrapper);
					var fieldLabel = instance._getById('fieldLabel', editContainerWrapper);

					displayAsTooltip.attr('checked', check(fieldInstance.get('displayAsTooltip')));
					repeatable.attr('checked', check(fieldInstance.get('repeatable')));
					fieldType.one('[value="' + fieldInstance.get('fieldType') + '"]').attr('selected', 'selected');
					localizedCheckbox.attr('checked', check(fieldInstance.get('localized')));
					instructions.val(fieldInstance.get('instructions'));
					predefinedValue.val(fieldInstance.get('predefinedValue'));
					required.attr('checked', check(fieldInstance.get('required')));
					variableName.val(fieldInstance.get('variableName'));
					fieldLabel.val(fieldInstance.get('fieldLabel'));

					var elements = editContainerWrapper.all('input[type=text], textarea, input[type=checkbox]');

					if (source.hasClass('repeated-field')) {
						elements.attr('disabled', 'disabled');

						if (localizedCheckbox) {
							localizedCheckbox.attr('disabled', '');
						}
					}
					else {
						elements.attr('disabled', '');
					}
				}
			},

			normalizeValue: function(value) {
				var instance = this;

				if (A.Lang.isUndefined(value)) {
					value = '';
				}

				return value;
			},

			_previewArticle: function() {
				var instance = this;

				var form = instance._getPrincipalForm();
				var auxForm = instance._getPrincipalForm('fm2');
				var articleContent = instance._getArticleContentXML();

				if (instance._hasStructureChanged()) {
					if (confirm(Liferay.Language.get('you-should-save-the-structure-first'))) {
						instance._openSaveStructureDialog();
					}

					return;
				}

				var languageIdInput = instance._getInputByName(form, 'languageId');
				var typeInput = instance._getInputByName(form, 'type');
				var versionInput = instance._getInputByName(form, 'version');
				var structureIdInput = instance._getInputByName(form, 'structureId');
				var templateIdInput = instance._getInputByName(form, 'templateId');

				var previewURL = themeDisplay.getPathMain() + '/journal/view_article_content?cmd=preview&groupId=' + themeDisplay.getScopeGroupId() + '&articleId=' + instance.articleId + '&version=' + versionInput.val() + '&languageId=' + languageIdInput.val() + '&type=' + typeInput.val() + '&structureId=' + structureIdInput.val() + '&templateId=' + templateIdInput.val();

				auxForm.attr('action', previewURL);
				auxForm.attr('target', '_blank');

				var titleInput = instance._getInputByName(form, 'title', true);
				var titleAuxFormInput = instance._getInputByName(auxForm, 'title', true);
				var xmlAuxFormInput = instance._getInputByName(auxForm, 'xml', true);

				titleAuxFormInput.val(titleInput.val());
				xmlAuxFormInput.val(articleContent);

				auxForm.submit();
			},

			_renderEditFieldOptions: function(source) {
				var instance = this;

				var editButtons = instance._getEditButtons();
				var editButton = instance._getEditButton(source);
				var structureTree = instance._getById(instance._structureTreeClass)

				instance._editContainerNormalMode();

				var fields = instance._getFields();
				var editContainerWrapper = instance._getById(instance._editFieldWrapperClass);
				var fieldsContainer = instance._getById(instance._fieldsContainerClass);
				var structureTree = instance._getById(instance._structureTreeClass)

				var editContainerHeight = editContainerWrapper.get('offsetHeight');
				var fieldsContainerWidth = fieldsContainer.get('offsetWidth');
				var fieldsContainerHeight = fieldsContainer.get('offsetHeight');

				fields.removeClass('selected');
				source.addClass('selected');

				if (instance._lastEditContainerTrigger != editButton) {
					instance.editContainerContextPanel.set('trigger', editButton);
					instance.editContainerContextPanel.show();

					instance._lastEditContainerTrigger = editButton;
				}

				instance._hideEditContainerMessage();
				instance._loadEditFieldOptions(source);

				instance.editContainerContextPanel.refreshAlign();
			},

			_repeatField: function(source) {
				var instance = this;
				var id = source.get('id');
				var fieldInstance = fieldsDataSet.item(id).clone();

				if (fieldInstance.get('fieldType') == 'text_area') {
					var html = instance._buildWYSIWYGEditorHTML(fieldInstance);

					fieldInstance.set('innerHTML', html);
				}

				var htmlTemplate = instance._createFieldHTMLTemplate(fieldInstance);
				var newComponent = A.Node.create(htmlTemplate);
				var newId = newComponent.guid();
				var instanceId = instance._generateInstanceId();

				fieldInstance.set('instanceId', instanceId);

				fieldsDataSet.add(newId, fieldInstance);

				fieldInstance.set('source', newComponent);
				source.placeAfter(newComponent);

				newComponent.addClass('repeated-field');

				instance._closeEditFieldOptions();

				instance._loadEditFieldOptions(newComponent);
				instance._saveEditFieldOptions(newComponent);

				instance._createNestedList(
					newComponent,
					instance.nestedListOptions,
					instance.nestedListEvents
				);

				instance._attachEvents();
			},

			_clearMessage: function(selector) {
				var instance = this;

				var journalMessage = A.get(selector);

				clearTimeout(instance.messageDelay[selector]);

				journalMessage.hide();
			},

			_repositionEditFieldOptions: function() {
				var instance = this;

				var editContainerWrapper = instance._getById(instance._editFieldWrapperClass);

				var isVisible = !editContainerWrapper.ancestor('.aui-contextpanel-hidden');

				if (isVisible) {
					setTimeout(
						function() {
							var lastSelectedField = instance._getSelectedField();
							instance._renderEditFieldOptions(lastSelectedField);
						},
						200
					);
				}
			},

			_saveArticle: function(cmd, saveAndContinue) {
				var instance = this;

				var form = instance._getPrincipalForm();

				if (instance._hasStructureChanged()) {
					if (confirm(Liferay.Language.get('you-should-save-the-structure-first'))) {
						instance._openSaveStructureDialog();
					}

					return;
				}

				if (!cmd) {
					cmd = instance.articleId ? 'update' : 'add';
				}

				var cmdInput = instance._getInputByName(form, 'cmd');
				var newArticleIdInput = instance._getInputByName(form, 'newArticleId');
				var articleIdInput = instance._getInputByName(form, 'articleId');
				var contentInput = instance._getInputByName(form, 'content');
				var approveInput = instance._getInputByName(form, 'approve');
				var saveAndContinueInput = instance._getInputByName(form, 'saveAndContinue');

				var canSubmmit = instance._validadeRequiredFields();

				if (canSubmmit) {
					if (cmd == 'approve') {
						approveInput.val(1);

						cmd = instance.articleId ? 'update' : 'add';
					}

					cmdInput.val(cmd);

					if (saveAndContinue) {
						saveAndContinueInput.val(1);
					}

					if (!instance.articleId) {
						articleIdInput.val(newArticleIdInput.val());
					}

					var content = instance._getArticleContentXML();

					contentInput.val(content);

					form.submit();
				}
			},

			_saveEditFieldOptions: function(source) {
				var instance = this;
				var id = source.get('id');
				var fieldInstance = fieldsDataSet.item(id);

				if (fieldInstance) {
					var editContainerWrapper = instance._getById(instance._editFieldWrapperClass);
					var displayAsTooltip = instance._getById('displayAsTooltip', editContainerWrapper);
					var repeatable = instance._getById('repeatable', editContainerWrapper);
					var fieldType = instance._getById('fieldType', editContainerWrapper);
					var localized = instance._getById('localized', editContainerWrapper);
					var instructions = instance._getById('instructions', editContainerWrapper);
					var predefinedValue = instance._getById('predefinedValue', editContainerWrapper);
					var required = instance._getById('required', editContainerWrapper);
					var variableName = instance._getById('variableName', editContainerWrapper);
					var fieldLabel = instance._getById('fieldLabel', editContainerWrapper);
					var localizedCheckbox = instance._getById('localized', editContainerWrapper);
					var localized = source.one('.journal-article-localized');

					if (localized) {
						var localizedValue = localized.val();
					}

					var variableNameValue = variableName.val();
					var canSave = true;
					var sourceRepeated = source.hasClass('repeated-field');

					instance._getFields().each(
						function(sourceValidation) {
							var id = sourceValidation.get('id');
							var fieldInstanceValidation = fieldsDataSet.item(id);

							if (!sourceValidation.hasClass('selected') &&
								fieldInstanceValidation &&
								!sourceRepeated &&
								!sourceValidation.hasClass('repeated-field')) {

								var validationVariableNameValue = fieldInstanceValidation.get('variableName');

								if (variableNameValue.toLowerCase() == validationVariableNameValue.toLowerCase()) {
									canSave = false;
								}
							}
						}
					);

					var journalMessage = instance._getById('journalMessage');

					if (canSave) {
						fieldInstance.set(
							{
								displayAsTooltip: displayAsTooltip.attr('checked'),
								fieldType: fieldType.val(),
								instructions: instructions.val(),
								localized: localizedCheckbox ? localizedCheckbox.attr('checked') : false,
								localizedValue: localizedValue,
								predefinedValue: predefinedValue.val(),
								repeatable: repeatable.attr('checked'),
								required: required.attr('checked')
							}
						);

						instance._updateFieldVariableName(fieldInstance, variableNameValue);
						instance._updateFieldLabelName(fieldInstance, fieldLabel.val());

						instance._showMessage(
							journalMessage,
							'success',
							Liferay.Language.get('your-request-processed-successfully')
						);

						instance._editContainerNormalMode();

						// instance._closeRepeatedSiblings(source);
					}
					else {
						variableName.focus();
						instance._showMessage(
							journalMessage,
							'error',
							Liferay.Language.get('duplicated-variable-name')
						);
					}
				}
			},

			_stripComponentType: function(type) {
				return type.toLowerCase().replace(/[^a-z]+/g, '');
			},

			_translateErrorMessage: function(exception) {
				var errorText = '';

				if (exception.indexOf('StructureXsdException') > -1) {
					errorText = Liferay.Language.get('please-enter-a-valid-xsd');
				}
				else if (exception.indexOf('DuplicateStructureIdException') > -1) {
					errorText = Liferay.Language.get('please-enter-a-unique-id');
				}
				else if (exception.indexOf('StructureDescriptionException') > -1) {
					errorText = Liferay.Language.get('please-enter-a-valid-description');
				}
				else if (exception.indexOf('StructureIdException') > -1) {
					errorText = Liferay.Language.get('please-enter-a-valid-id');
				}
				else if (exception.indexOf('StructureInheritanceException') > -1) {
					errorText = Liferay.Language.get('this-structure-is-already-within-the-inheritance-path-of-the-selected-parent-please-select-another-parent-structure');
				}
				else if (exception.indexOf('StructureNameException') > -1) {
					errorText = Liferay.Language.get('please-enter-a-valid-name');
				}
				else if (exception.indexOf('NoSuchStructureException') > -1) {
					errorText = Liferay.Language.get('please-enter-a-valid-id');
				}
				else if (exception.indexOf('ArticleContentException') > -1) {
					errorText = Liferay.Language.get('please-enter-valid-content');
				}
				else if (exception.indexOf('ArticleIdException') > -1) {
					errorText = Liferay.Language.get('please-enter-a-valid-id');
				}
				else if (exception.indexOf('ArticleTitleException') > -1) {
					errorText = Liferay.Language.get('please-enter-a-valid-name');
				}
				else if (exception.indexOf('DuplicateArticleIdException') > -1) {
					errorText = Liferay.Language.get('please-enter-a-unique-id');
				}

				return errorText;
			},

			_initializePageLoadFieldInstances: function() {
				var instance = this;

				var fields = instance._getFields();

				fields.each(
					function(field) {
						var id = field.get('id');
						var fieldInstance = fieldsDataSet.item(id);

						if (!fieldInstance) {
							var fieldLabel = field.attr('dataLabel');
							var componentName = field.attr('dataName');
							var componentType = field.attr('dataType');
							var indexType = field.attr('dataIndexType');
							var instructions = field.attr('dataInstructions');
							var required = field.attr('dataRequired');
							var displayAsTooltip = field.attr('dataDisplayAsTooltip');
							var parentValue = field.attr('dataParentStructureId');
							var repeatable = field.attr('dataRepeatable');
							var predefinedValue = field.attr('dataPredefinedValue');
							var instanceId = field.attr('dataInstanceId');

							// var localized = instance._getById('Localized', field);
							var localized = field.one('.journal-article-localized');

							if (localized) {
								var localizedValue = localized.val();
							}

							var isLocalized = (String(localizedValue) != 'false');

							fieldInstance = instance._fieldInstanceFactory(
								{
									displayAsTooltip: displayAsTooltip,
									fieldLabel: fieldLabel,
									instanceId: instanceId,
									instructions: instructions,
									localized: isLocalized,
									localizedValue: localizedValue,
									parentStructureId: parentValue,
									predefinedValue: predefinedValue,
									repeatable: repeatable,
									required: required,
									source: field,
									type: componentType,
									indexType: indexType,
									variableName: componentName
								}
							);
						}

						var id = field.get('id');

						fieldsDataSet.add(id, fieldInstance);
					}
				);
			},

			_showMessage: function(selector, type, message, delay) {
				var instance = this;

				var journalMessage = selector;

				if (!selector) {
					journalMessage = instance._generateId('journalMessage');
				}

				var journalMessage = A.one(selector);
				var className = 'portlet-msg-' + (type || 'success');

				journalMessage.removeClass().addClass(className);
				journalMessage.show();

				instance.editContainerContextPanel.refreshAlign();

				if (message) {
					journalMessage.html(message);
				}

				clearTimeout(instance.messageDelay[selector]);

				instance.messageDelay[selector] = setTimeout(
					function() {
						clearTimeout(instance.messageDelay[selector]);

						journalMessage.hide();
						instance.editContainerContextPanel.refreshAlign();
					},
					delay || 5000
				);
			},

			_updateTextAreaFields: function(visibility) {
				var instance = this;
				var textAreaFields = instance._getTextAreaFields();

				if (textAreaFields) {
					textAreaFields.setStyle('visibility', visibility);
				}
			},

			_updateFieldVariableName: function(fieldInstance, variableName) {
				var instance = this;

				var repeatedSiblings = instance._getRepeatedSiblings(fieldInstance);

				repeatedSiblings.each(
					function(field) {
						var newFieldInstance = fieldInstance.clone();

						newFieldInstance.set('source', field);
						newFieldInstance.set('variableName', variableName);
					}
				);

				fieldInstance.set('variableName', variableName);
			},

			_updateFieldLabelName: function(fieldInstance, fieldLabel) {
				var instance = this;

				var repeatedSiblings = instance._getRepeatedSiblings(fieldInstance);

				repeatedSiblings.each(
					function(field) {
						var newFieldInstance = fieldInstance.clone();

						newFieldInstance.set('source', field);
						newFieldInstance.set('fieldLabel', fieldLabel);
					}
				);

				fieldInstance.set('fieldLabel', fieldLabel);
			},

			_unselectFields: function() {
				var instance = this;
				var selected = instance._getSelectedField();

				if (selected) {
					selected.removeClass('selected');
				}
			},

			_validadeRequiredFields: function() {
				var instance = this;

				var canSubmmit = true;
				var structureTreeId = instance._generateId(instance._structureTreeClass);
				var fields = A.all(structureTreeId + instance._fieldRowsClass);
				var requiredFields = fields.filter('[dataRequired=true]');
				var fieldsConatainer = A.all(structureTreeId + instance._fieldRowsClass + ' .field-container');
				var firstEmptyField = null;

				fieldsConatainer.removeClass('required-field');

				A.each(
					requiredFields,
					function(source) {
						var id = source.get('id');
						var fieldInstance = fieldsDataSet.item(id);
						var content = fieldInstance.getContent(source);

						if (!content) {
							var fieldConatainer = source.one('.field-container');

							fieldConatainer.addClass('required-field');

							if (canSubmmit) {
								firstEmptyField = instance._getPrincipalFieldElement(source);
							}

							canSubmmit = false;
						}
					}
				);

				if (firstEmptyField) {
					firstEmptyField.focus();
				}

				return canSubmmit;
			},

			_getPrincipalFieldElement: function(source) {
				var instance = this;

				var componentContainer = source.one('div.journal-article-component-container');

				return componentContainer.one('.principal-field-element');
			},

			_closeButtonsClass: 'span.journal-article-close',
			_componentFields: ' .component-group .journal-component',
			_editButtonsClass: ' div.journal-article-buttons input.edit-button',
			_editFieldWrapperClass: '#journalArticleEditFieldWrapper',
			_fieldRowsClass: ' li',
			_fieldsContainerClass: '#journalArticleContainer',
			_handleMoveFiledClass: '.journal-article-move-handler',
			_helperCachedObject: null,
			_portletContentClass: '.portlet-journal .portlet-content-container',
			_repeatableButtonsClass: ' div.journal-article-buttons input.repeatable-button',
			_structureTreeClass: '#structureTree',
			_journalComponentListClass: '#journalComponentList',
			_textAreaFields: ' li[dataType=text_area]'
		};

		Journal.StructureField = function(options, portletNamespace) {
			var instance = this;

			Journal.StructureField.superclass.constructor.apply(instance, arguments);

			instance.portletNamespace = portletNamespace;

			options = options || {};

			var Lang = YAHOO.lang;

			instance.add(
				'source',
				{
					handler: instance.setSource,
					value: options.source
				}
			);

			instance.add(
				'content',
				{
					validator: Lang.isString,
					value: ''
				}
			);

			instance.add(
				'displayAsTooltip',
				{
					handler: instance._setDataAttribute,
					value: instance._getDataAttribute('displayAsTooltip', true)
				}
			);

			instance.add(
				'fieldType',
				{
					handler: instance._setDataAttribute,
					value: options.type || ''
				}
			);

			var localizedValue = instance.getLocalizedValue();
			var localized = (String(localizedValue) == 'true');

			instance.add(
				'localized',
				{
					value: localized
				}
			);

			instance.add(
				'localizedValue',
				{
					value: localizedValue
				}
			);

			instance.add(
				'fieldLabel',
				{
					handler: instance.setFieldLabel,
					value: instance._getDataAttribute('fieldLabel')
				}
			);

			instance.add(
				'innerHTML',
				{
					validator: Lang.isString,
					value: '<input class="principal-field-element lfr-input-text" type="text" value="" size="75"/>'
				}
			);

			instance.add(
				'instructions',
				{
					handler: instance.setInstructions,
					value: instance._getDataAttribute('instructions', '')
				}
			);

			instance.add(
				'instanceId',
				{
					handler: instance.setInstanceId,
					value: instance._getDataAttribute('instanceId', '')
				}
			);

			instance.add(
				'parentStructureId',
				{
					handler: instance._setDataAttribute,
					value: instance._getDataAttribute('parentStructureId', '')
				}
			);

			instance.add(
				'predefinedValue',
				{
					handler: instance._setDataAttribute,
					value: instance._getDataAttribute('predefinedValue', '')
				}
			);

			instance.add(
				'repeatable',
				{
					handler: instance.setRepeatable,
					value: instance._getDataAttribute('repeatable', false)
				}
			);

			instance.add(
				'required',
				{
					handler: instance._setDataAttribute,
					value: instance._getDataAttribute('required', true)
				}
			);

			instance.add(
				'selected',
				{
					handler: instance.setSelected,
					value: false
				}
			);

			instance.add(
				'variableName',
				{
					handler: instance.setVariableName,
					validator: Lang.isString,
					value: instance._getDataAttribute('name')
				}
			);

			instance.set(options);
		};

		A.extend(
			Journal.StructureField,
			Liferay.Observable,
			{
				clone: function(options) {
					var instance = this;

					var clone = new instance.constructor(options || {});

					var currentConfig = instance.cfg.getConfig();

					clone.set(currentConfig, true);

					return clone;
				},

				createInstructionsMessageContainer: function(value) {
					return A.Node.create('<div class="journal-article-instructions-container journal-article-instructions-message portlet-msg-info"></div>').html(value);
				},

				createTooltipImage: function() {
					return A.Node.create('<img align="top" class="journal-article-instructions-container" src="' + themeDisplay.getPathThemeImages() + '/portlet/help.png" />');
				},

				getContent: function(source) {
					var instance = this;

					var content;
					var type = instance.get('fieldType');
					var id = source.get('id');
					var fieldInstance = fieldsDataSet.item(id);
					var componentContainer = source.one('div.journal-article-component-container');

					var principalElement = componentContainer.one('.principal-field-element');

					if (type == 'boolean') {
						content = principalElement.attr('checked');
					}
					else if (type == 'text_area') {
						var editorName = source.one('iframe').attr('name');
						var editorReference = window[editorName];

						if (editorReference && A.Lang.isFunction(editorReference.getHTML)) {
							content = editorReference.getHTML();
						}
					}
					else if (type == 'multi-list') {
						var output = [];
						var options = principalElement.all('option');

						options.each(
							function(node) {
								if (node.get('selected')) {
									var value = node.val();

									output.push(value);
								}
							}
						);

						content = output.join(',');
					}
					else if (type == 'image') {
						var imageContent = componentContainer.one('.journal-image-content');
						var imageDelete = componentContainer.one('.journal-image-delete');

						if (imageDelete && imageDelete.val() == 'delete') {
							content = 'delete';
						}
						else {
							if (imageContent) {
								content = imageContent.val() || principalElement.val();
							}
						}
					}
					else {
						content = principalElement.val();
					}

					instance.set('content', content);

					return content;
				},

				getFieldContainer: function() {
					var instance = this;

					if (!instance.fieldContainer) {
						var htmlTemplate = [];
						var editOptionsLanguage = Liferay.Language.get('edit-options');
						var requiredFieldLanguage = Liferay.Language.get('this-field-is-required');
						var repeatableBtnTemplate = instance._getById('repeatableBtnTemplate');
						var repeatableBtnTemplateHTML = '';

						if (repeatableBtnTemplate) {
							repeatableBtnTemplateHTML = repeatableBtnTemplate.html();
						}

						htmlTemplate.push('<div><li class="structure-field">');
						htmlTemplate.push('<span class="journal-article-close"></span>');
						htmlTemplate.push('<span class="folder">');
						htmlTemplate.push('<div class="field-container">');

						htmlTemplate.push('<div class="journal-article-move-handler"></div>');
						htmlTemplate.push('<label for="" class="journal-article-field-label"><span>Field</span></label>');
						htmlTemplate.push('<div class="journal-article-component-container"></div>');
						htmlTemplate.push('<div class="journal-article-required-message portlet-msg-error">' + requiredFieldLanguage + '</div>');
						htmlTemplate.push('<div class="journal-article-buttons">');
						htmlTemplate.push('<input type="button" class="edit-button" value="' + editOptionsLanguage + '"/>');
						htmlTemplate.push(repeatableBtnTemplateHTML);
						htmlTemplate.push('</div>');

						htmlTemplate.push('</div>');
						htmlTemplate.push('<ul class="folder-droppable"></ul>');
						htmlTemplate.push('</span>');
						htmlTemplate.push('</li></div>');

						instance.fieldContainer = A.Node.create(htmlTemplate.join(''));
						var source = instance.fieldContainer.one('li');

						source.attr('dataName', instance.get('variableName'));
						source.attr('dataType', instance.get('fieldType'));
						source.attr('dataRequired', instance.get('required'));
					}

					return instance.fieldContainer;
				},

				getFieldElementContainer: function() {
					var instance = this;

					if (!instance.fieldElementContainer) {
						instance.fieldElementContainer = instance.getFieldContainer().one('div.journal-article-component-container');
					}

					return instance.fieldElementContainer;
				},

				getFieldLabelElement: function() {
					var instance = this;
					var source = instance.get('source', instance.getFieldContainer(), true);

					return source.one('> .folder > .field-container .journal-article-field-label');
				},

				getLocalizedValue: function() {
					var instance = this;

					var source = instance.get('source');

					if (source) {
						var input = source.one('.journal-article-localized');
					}

					return input ? input.val() : 'false';
				},

				setFieldLabel: function(key, args) {
					var instance = this;

					var value = args[0] || instance.get('variableName');

					var fieldLabel = instance.getFieldLabelElement();

					fieldLabel.one('span').html(value);

					instance._setDataAttribute(key, value);
				},

				setInstanceId: function(key, args) {
					var instance = this;

					var value = args[0];

					instance._setDataAttribute(key, value);

					var type = instance.get('fieldType');
					var source = instance.get('source');

					if ((type == 'image') && source) {
						var isLocalized = instance.get('localized');
						var inputFileName = 'structure_image_' + value + '_' + instance.get('variableName');
						var inputFile = source.one('.journal-article-component-container [type=file]');

						if (isLocalized) {
							inputFileName += '_' + instance.get('localizedValue');
						}

						inputFile.attr('name', inputFileName);
					}
				},

				setInstructions: function(key, args) {
					var instance = this;

					var value = args[0];
					var source = instance.get('source');
					var id = source.get('id');
					var fieldInstance = fieldsDataSet.item(id);

					instance._setDataAttribute(key, value);

					if (fieldInstance) {
						var fieldContainer = source.one('> .folder > .field-container');
						var label = fieldInstance.getFieldLabelElement();
						var tooltipIcon = label.one('.journal-article-instructions-container');
						var journalInstructionsMessage = fieldContainer.one('.journal-article-instructions-message');
						var displayAsTooltip = fieldInstance.get('displayAsTooltip');

						if (tooltipIcon) {
							tooltipIcon.remove();
						}

						if (journalInstructionsMessage) {
							journalInstructionsMessage.remove();
						}

						if (value) {
							if (!displayAsTooltip) {
								var instructionsMessage = fieldInstance.createInstructionsMessageContainer(value);
								var requiredMessage = fieldContainer.one('.journal-article-required-message');

								requiredMessage.placeAfter(instructionsMessage);
							}
							else {
								label.append(fieldInstance.createTooltipImage());
							}
						}
					}
				},

				setRepeatable: function(key, args) {
					var instance = this;

					var value = args[0];

					instance._setDataAttribute(key, value);

					var source = instance.get('source');

					var id = source.get('id');
					var fieldInstance = fieldsDataSet.item(id);
					var fieldContainer = source.one('> .folder > .field-container');
					var repeatableFieldImage = fieldContainer.one('.repeatable-field-image');
					var repeatableAddIcon = source.one('.journal-article-buttons .repeatable-button');

					if (repeatableFieldImage) {
						repeatableFieldImage.remove();
					}

					var repeatable = instance.get('repeatable');
					var parentStructureId = instance.get('parentStructureId');

					if (repeatable && !parentStructureId) {
						var repeatableFieldImageModel = A.Node.create(
							A.one('#repeatable-field-image-model').html()
						);

						fieldContainer.append(repeatableFieldImageModel);
						repeatableAddIcon.setStyle('display', 'inline-block').show();
					}
					else {
						repeatableAddIcon.hide();
					}
				},

				setSelected: function(key, args) {
					var instance = this;

					var value = args[0];
					var action = 'add';
					var element = instance.get('element');

					if (!value) {
						action = 'remove';
					}

					action += 'Class';

					element[action]('selected');
				},

				setSource: function(key, args) {
					var instance = this;
					var value = args[0];
					var id = value.get('id');

					instance.set('source', value, true);

					// fieldsDataSet.add(id, fieldInstance);
				},

				setVariableName: function(key, args) {
					var instance = this;

					var value = args[0];

					var fieldLabel = instance.getFieldLabelElement();

					fieldLabel.attr('for', value);
					fieldLabel.get('parentNode').one('input').attr('id', value);

					instance._setDataAttribute('name', value);
				},

				_getDataAttribute: function(key, defaultValue) {
					var instance = this;
					var value = undefined;
					var source = instance.get('source');

					if (source) {
						value = source.attr('data' + key);
					}

					if (YAHOO.lang.isUndefined(value) && !YAHOO.lang.isUndefined(defaultValue)) {
						value = defaultValue;
					}

					return value;
				},

				_setDataAttribute: function(key, args) {
					var instance = this;

					var value = args;

					if (YAHOO.lang.isArray(value)) {
						value = value[0];
					}

					instance.get('source').setAttribute('data' + key, value);
				},

				_generateId: Journal.prototype._generateId,

				_getById: Journal.prototype._getById
			}
		);

		Journal.FieldModel = {};

		var fieldModel = Journal.FieldModel;

		var createFieldModel = function(namespace, type, variableName) {
			var typeEl = A.one('#journalFieldModelContainer div[dataType="'+ type +'"]');

			if (typeEl) {
				var innerHTML = typeEl.html();
			}

			fieldModel[namespace] = {
				type: type,
				variableName: variableName,
				fieldLabel: variableName,
				innerHTML: innerHTML
			};
		};

		createFieldModel('Text', 'text', 'TextField');
		createFieldModel('TextArea', 'text_area', 'TextAreaField');
		createFieldModel('TextBox', 'text_box', 'TextField');
		createFieldModel('Image', 'image', 'ImageField');
		createFieldModel('ImageGallery', 'image_gallery', 'ImageGalleryField');
		createFieldModel('DocumentLibrary', 'document_library', 'DocumentLibraryField');
		createFieldModel('Boolean', 'boolean', 'BooleanField');
		createFieldModel('List', 'list', 'ListField');
		createFieldModel('MultiList', 'multi-list', 'MultiListField');
		createFieldModel('LinkToPage', 'link_to_layout', 'LinkToPageField');
		createFieldModel('SelectionBreak', 'selection_break', 'SelectionBreak');

		Liferay.Portlet.Journal = Journal;
	},
	'',
	{
		requires: ['aui-base', 'context-panel', 'dialog', 'liferay-observable', 'nested-list', 'collection', 'data-set']
	}
);