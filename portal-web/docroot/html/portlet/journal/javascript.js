;(function() {
	Liferay.Portlet.Journal = new Alloy.Class(
		{
			initialize: function(portletNamespace, articleId, instanceIdKey) {
				var instance = this;

				instance.articleId = articleId;
				instance.instanceIdKey = instanceIdKey;
				instance.messageDelay = {};
				instance.portletNamespace = portletNamespace;
				instance.uid = 0;

				var structureTreeId = instance._generateId(instance._structureTreeClass);
				var structureTree = jQuery(structureTreeId);

				instance._helperCachedObjectId = instance._generateId('journalArticleHelper', instance.portletNamespace, '');

				instance._helperCachedObject = jQuery(
					'<div id="' + instance._helperCachedObjectId + '" class="journal-article-helper not-intersecting">' +
						'<div class="journal-component"></div>' +
						'<div class="forbidden-action"></div>' +
					'</div>'
				);

				instance._helperCachedObject.appendTo(document.body);

				instance.acceptChildren = true;

				var fields = jQuery(structureTreeId + instance._fieldRowsClass + '.structure-field');

				instance.nestedListOptions = {
					centerFrame: false,
					dropOn: 'span.folder > ul.folder-droppable',
					forcePlaceholderSize: false,
					handle: instance._handleMoveFiledClass,
					placeholder: 'aui-tree-placeholder aui-tree-sub-placeholder',
					resizeFrame: true,
					sortOn: structureTree,
					dropCondition: function() {
						var destEl = jQuery(this);

						return instance._canDropChildren(destEl);
					}
				};

				instance.nestedListEvents = {
					b4StartDragEvent: function(event) {
						var placeholderEl = this.placeholderEl;

						var placeholderWidth = placeholderEl.outerWidth();

						instance._helperCachedObject.css(
							{
								width: placeholderWidth
							}
						);

						instance._getTextAreaFields().css('visibility', 'hidden');

						Liferay.Util.disableSelection(document.body);
					},

					endDragEvent: function() {
						instance._dropField();

						instance._getTextAreaFields().css('visibility', 'visible');

						Liferay.Util.enableSelection(document.body);
					},

					dragOutEvent: function(event, id) {
						if (!instance.acceptChildren) {
							instance._helperIntersecting();
							instance.acceptChildren = true;
						}
					},

					dragOverEvent: function(event, id) {
						var destId = event.info;
						var destEl = jQuery('#' + destId);

						instance.acceptChildren = instance._canDropChildren(destEl);

						if (instance.acceptChildren) {
							instance._helperIntersecting();
						}
						else {
							instance._helperNotIntersecting();
						}
					}
				};

				instance._createNestedList(fields, instance.nestedListOptions, instance.nestedListEvents);

				var journalComponentListId = instance._generateId(instance._journalComponentListClass);
				var componentFields = jQuery(journalComponentListId + instance._componentFields);

				instance.componentFieldsOptions = {
					centerFrame: false,
					dropOn: 'span.folder > ul.folder-droppable',
					forcePlaceholderSize: false,
					placeholder: 'aui-tree-placeholder aui-tree-sub-placeholder',
					resizeFrame: true,
					sortOn: structureTree,
					dropCondition: function() {
						var destEl = jQuery(this);

						return instance._canDropChildren(destEl);
					}
				};

				instance.componentFieldsEvents = {
					startDragEvent: function() {
						var source = jQuery(this.getEl());
						var proxy = jQuery(this.getDragEl());

						var languageName = source.text();
						var componentType = instance._getComponentType(source);
						var className = 'journal-component-' + instance._stripComponentType(componentType);

						var helperComponentIcon = instance._helperCachedObject.find('div.journal-component');

						helperComponentIcon.addClass(className).html(languageName);

						proxy.addClass('component-dragging');

						instance._getTextAreaFields().css('visibility', 'hidden');

						Liferay.Util.disableSelection(document.body);

						instance.clonedSource = source.clone();

						source[0].parentNode.insertBefore(instance.clonedSource[0], source[0]);

						instance.clonedSource.removeAttr('id');
						instance.clonedSource.generateId();

						instance.clonedSource.css('visibility', 'visible').show();
						instance.clonedSource.addClass('dragging');

						instance._createNestedList(instance.clonedSource, instance.componentFieldsOptions, instance.componentFieldsEvents);
					},

					endDragEvent: function() {
						var source = jQuery(this.getEl());
						var proxy = jQuery(this.getDragEl());

						proxy.removeClass('component-dragging');

						var componentType = instance._getComponentType(source);
						var className = 'journal-component-' + instance._stripComponentType(componentType);
						var helperComponentIcon = instance._helperCachedObject.find('div.journal-component');

						helperComponentIcon.removeClass(className).empty();

						var addedComponent = structureTree.find('div.journal-component');

						addedComponent.hide();

						if (addedComponent.length) {
							var fieldInstance = instance._fieldInstanceFactory(componentType);
							var variableName = fieldInstance.get('variableName') + instance._getUID();

							if (fieldInstance.get('fieldType') == 'text_area') {
								var html = instance._buildWYSIWYGEditorHTML(fieldInstance);

								fieldInstance.set('innerHTML', html);
							}

							var htmlTemplate = instance._createFieldHTMLTemplate(fieldInstance);
							var newComponent = jQuery(htmlTemplate);

							newComponent.insertBefore(addedComponent);
							addedComponent.remove();

							fieldInstance.set('source', newComponent);

							var fieldLabel = fieldInstance.get('fieldLabel', variableName);

							instance._updateFieldVariableName(fieldInstance, variableName);
							instance._updateFieldLabelName(fieldInstance, fieldLabel);

							instance._createNestedList(newComponent, instance.nestedListOptions, instance.nestedListEvents);

							instance._attachEvents();

							newComponent.data('fieldInstance', fieldInstance);

							instance._repositionEditFieldOptions();

							Liferay.Util.enableSelection(document.body);
						}

						instance._getTextAreaFields().css('visibility', 'visible');

						if (instance.clonedSource) {
							var journalComponentList = instance._getById(instance._journalComponentListClass);

							instance.clonedSource.removeClass('dragging');

							if (Alloy.Dom.contains(journalComponentList[0], source[0]) && Alloy.Dom.contains(journalComponentList[0], instance.clonedSource[0])) {
								source.remove();
							}
						}
					},

					dragOutEvent: instance.nestedListEvents.dragOutEvent,

					dragOverEvent: instance.nestedListEvents.dragOverEvent
				};

				instance._createNestedList(componentFields, instance.componentFieldsOptions, instance.componentFieldsEvents);

				var editContainerWrapper = instance._getById(instance._editFieldWrapperClass);

				AUI().use(
					'context-panel',
					function(A) {
						var wrapper = A.get(editContainerWrapper[0]);

						wrapper.setStyle('display', 'block');

						instance.editContainerContextPanel = new A.ContextPanel(
							{
								bodyContent: wrapper,
								align: {
									points: ['rc', 'lc']
								},
								trigger: 'input.edit-button'
							}
						)
						.render();
					}
				);

				instance._initializePageLoadFieldInstances();
				instance._attachEvents();
				instance._attachEditContainerEvents();
				instance._attachLiveQueryEvents();

				var currentStructureXSD = encodeURIComponent(instance._getStructureXSD());
				var structureXSDInput = instance._getInputByName(instance._getPrincipalForm(), 'structureXSD');

				structureXSDInput.val(currentStructureXSD);
			},

			_getTextAreaFields: function() {
				var instance = this;
				var structureTreeId = instance._generateId(instance._structureTreeClass);

				return jQuery(structureTreeId + instance._textAreaFields).find('div.journal-article-component-container:first');
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
				url.push(Liferay.Portlet.Journal.PROXY.doAsUserId);
				url.push('&editorImpl=');
				url.push(Liferay.Portlet.Journal.PROXY.editorImpl);
				url.push('&toolbarSet=liferay-article');
				url.push('&initMethod=');
				url.push(initMethod);
				url.push('&onChangeMethod=');
				url.push(onChangeMethod);
				url.push('&cssPath=');
				url.push(Liferay.Portlet.Journal.PROXY.pathThemeCss);
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
				else if (source.is('.repeated-field')) {
					canDrop = false;
				}

				return canDrop;
			},

			_createNestedList: function(selector, options, events) {
				var instance = this;

				jQuery(selector).each(
					function() {
						var element = this;

						var defaults = {
							centerFrame: false,
							dropOn: '.folder'
						};

						options = jQuery.extend(defaults, options);

						var nestedList = new Alloy.NestedList(element, null, options);
						nestedList.setDragElId(instance._helperCachedObjectId);

						jQuery.each(
							events,
							function(key, value) {
								if (key && jQuery.isFunction(value)) {
									nestedList.on(key, value);
								}
							}
						);
					}
				);
			},

			_attachEvents: function() {
				var instance = this;

				var portletContentWrapper = jQuery(instance._portletContentClass);

				var closeButtons = instance._getCloseButtons();
				var fields = instance._getFields();
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

				closeButtons.unbind('click');
				defaultLanguageIdSelect.unbind('change');
				downloadArticleContentBtn.unbind('click');
				editButtons.unbind('click');
				languageIdSelect.unbind('change');
				previewArticleBtn.unbind('click');
				repeatableButtons.unbind('click');
				saveArticleAndApproveBtn.unbind('click');
				saveArticleAndContinueBtn.unbind('click');
				saveArticleBtn.unbind('click');

				editButtons.click(
					function(event) {
						var source = jQuery(this).parents('li:first');

						instance._renderEditFieldOptions(source);
					}
				);

				repeatableButtons.click(
					function(event) {
						var source = jQuery(this).parents('li:first');

						instance._repeatField(source, true);
					}
				);

				closeButtons.click(
					function(event) {
						var source = jQuery(this).parents('li:first');

						instance._closeField(source);
					}
				);

				saveArticleBtn.click(
					function() {
						instance._saveArticle();
					}
				);

				saveArticleAndContinueBtn.click(
					function() {
						var saveAndContinue = true;

						instance._saveArticle(null, saveAndContinue);
					}
				);

				saveArticleAndApproveBtn.click(
					function() {
						instance._saveArticle('approve');
					}
				);

				downloadArticleContentBtn.click(
					function() {
						instance._downloadArticleContent();
					}
				);

				previewArticleBtn.click(
					function() {
						instance._previewArticle();
					}
				);

				languageIdSelect.change(
					function() {
						instance._changeLanguageView();
					}
				);

				defaultLanguageIdSelect.change(
					function() {
						instance._changeLanguageView();
					}
				);

				var changeStructureBtn = instance._getById('changeStructureBtn');
				var changeTemplateBtn = instance._getById('changeTemplateBtn');
				var saveStructureBtn = instance._getById('saveStructureBtn');
				var editStructureBtn = instance._getById('editStructureBtn');
				var loadDefaultStructureBtn = instance._getById('loadDefaultStructure');
				var saveStructureTriggers = jQuery('.journal-save-structure-trigger');

				changeStructureBtn.unbind('click');
				changeTemplateBtn.unbind('click');
				saveStructureBtn.unbind('click');
				editStructureBtn.unbind('click');
				loadDefaultStructureBtn.unbind('click');
				saveStructureTriggers.unbind('click');

				changeStructureBtn.click(
					function() {
						if (confirm(Liferay.Language.get('selecting-a-new-structure-will-change-the-available-input-fields-and-available-templates'))) {
							var url = jQuery(this).attr('data-changeStructureUrl');
							instance._openPopupWindow(url, 'ChangeStructure');
						}
					}
				);

				changeTemplateBtn.click(
					function() {
						if (confirm(Liferay.Language.get('selecting-a-template-will-change-the-structure,-available-input-fields,-and-available-templates'))) {
							var url = jQuery(this).attr('data-changeTemplateUrl');
							instance._openPopupWindow(url, 'ChangeTemplate');
						}
					}
				);

				loadDefaultStructureBtn.click(
					function() {
						instance._loadDefaultStructure();
					}
				);

				saveStructureBtn.click(
					function() {
						instance._openSaveStructureDialog();
					}
				);

				saveStructureTriggers.click(
					function(event) {
						saveStructureBtn.trigger('click');

						return false;
					}
				);

				editStructureBtn.toggle(
					function() {
						instance._enableEditMode();
					},
					function() {
						instance._disableEditMode();
					}
				);
			},

			_attachEditContainerEvents: function(attribute) {
				var instance = this;

				var editContainerWrapper = instance._getById(instance._editFieldWrapperClass);
				var editContainerCheckboxes = editContainerWrapper.find(':checkbox');
				var editContainerInputs = editContainerWrapper.find(':text');
				var editContainerTextareas = editContainerWrapper.find('textarea');
				var editFieldCancelButton = editContainerWrapper.find('.cancel-button');
				var editFieldCloseButton = editContainerWrapper.find('.close-button');
				var editFieldSaveButton = editContainerWrapper.find('.save-button');
				var localized = instance._getById('localized', editContainerWrapper);

				editContainerCheckboxes.unbind('click');
				editContainerInputs.unbind('change');
				editContainerInputs.unbind('keypress');
				editContainerTextareas.unbind('change');
				editContainerTextareas.unbind('keypress');
				editFieldCancelButton.unbind('click');
				editFieldCloseButton.unbind('click');
				editFieldSaveButton.unbind('click');

				var editContainerSaveMode = function() {
					instance._editContainerSaveMode();
				};

				editContainerCheckboxes.click(editContainerSaveMode);
				editContainerInputs.change(editContainerSaveMode);
				editContainerInputs.keypress(editContainerSaveMode);
				editContainerTextareas.change(editContainerSaveMode);
				editContainerTextareas.keypress(editContainerSaveMode);

				editFieldSaveButton.click(
					function() {
						var source = instance._getSelectedField();

						instance._saveEditFieldOptions(source);
					}
				);

				var closeEditField = function(event) {
					instance._closeEditFieldOptions();
				};

				editFieldCancelButton.click(closeEditField);

				editFieldCloseButton.click(closeEditField);

				localized.click(
					function() {
						var source = instance._getSelectedField();

						var defaultLocale = instance._getDefaultLocale();
						var checkbox = jQuery(this);
						var isLocalized = checkbox.is(':checked');
						var localizedValue = instance._getById('Localized', source);

						if (isLocalized) {
							localizedValue.val(defaultLocale);
						}
						else if (!confirm(Liferay.Language.get('unchecking-this-field-will-remove-localized-data-for-languages-not-shown-in-this-view'))) {
							checkbox.attr('checked', 'checked');
							localizedValue.val(defaultLocale);
						}
						else {
							localizedValue.val('false');
						}
					}
				);
			},

			_attachLiveQueryEvents: function() {
				var instance = this;

				var journalArticleContainerId = instance._generateId(instance._fieldsContainerClass)

				AUI().use(
					'aui-base',
					function(A) {
						var addListItem = function(event) {
							var icon = jQuery(event.currentTarget.getDOM());
							var iconParent = icon.parent();

							var select = iconParent.siblings('select:first');

							var keyInput = icon.siblings('input.journal-list-key');
							var key = instance._formatOptionsKey(keyInput.val());

							var valueInput = icon.siblings('input.journal-list-value');
							var value = valueInput.val();

							if (key && value) {
								var options = select.find('option');

								jQuery.grep(
									options,
									function(option) {
										option = jQuery(option);

										if (option.text().toLowerCase() == key.toLowerCase()) {
											option.remove();
										}
									}
								);

								var option = jQuery('<option></option>').val(value).text(key);

								select.append(option);
								option.select();
								keyInput.val('').focus();

								valueInput.val('value');
							}
							else {
								keyInput.focus();
							}
						};

						var keyPressAddItem = function(event) {
							var btnScope = jQuery(event.currentTarget.getDOM()).siblings('span.journal-add-field');
							var scope = btnScope[0];

							if (event.keyCode == 13) {
								event.currentTarget = A.get(scope);

								addListItem.apply(event.currentTarget, arguments);
							}
						};

						var removeListItem = function(event) {
							var icon = jQuery(event.currentTarget.getDOM());
							var select = icon.siblings('select:first').focus();

							jQuery('option:selected', select).remove();
						};

						var container = A.get(journalArticleContainerId);

						container.delegate(
							'click',
							function(event) {
								var currentTarget = event.currentTarget;

								var source = jQuery(currentTarget.getDOM()).parents('li:first');

								instance._repeatField(source, false);
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
								var button = jQuery(event.currentTarget.getDOM());

								var imageWrapper = button.siblings('.journal-image-wrapper');
								var imageDelete = button.siblings('.journal-image-delete');

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
								var link = jQuery(event.currentTarget.getDOM());
								var imagePreviewDiv = link.parent().siblings('.journal-image-preview');

								imagePreviewDiv.toggle();

								var visible = imagePreviewDiv.is(':visible');
								var labelSelector = '.show-label';

								if (visible) {
									labelSelector = '.hide-label'
								}

								link.find('span').hide();
								link.find(labelSelector).show();
							},
							'.journal-image-link'
						);

						var _attachButtonInputSelector = function(id, title, handlerName) {
							var buttonId = 'input.journal-' + id + '-button';
							var textId = 'input.journal-' + id + '-text';

							container.delegate(
								'click',
								function(event) {
									var button = jQuery(event.currentTarget.getDOM());
									var input = button.siblings(textId);
									var imageGalleryUrl = button.attr('data-' + id + '-url');

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
								var image = jQuery(event.currentTarget.getDOM());
								var source = image.parents('li:first');
								var fieldInstance = source.data('fieldInstance');

								if (fieldInstance) {
									var instructions = fieldInstance.get('instructions');

									Liferay.Portal.ToolTip.show(this, instructions);
								}
							},
							'img.journal-article-instructions-container'
						);
					}
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

				var children = source.find(selector);

				jQuery(children).each(
					function() {
						var sourceChild = jQuery(this);

						instance._appendStructureDynamicElementAndMetaData(sourceChild, buffer, generateArticleContent);
					}
				);
			},

			_appendStructureDynamicElementAndMetaData: function(source, buffer, generateArticleContent) {
				var instance = this;

				var fieldInstance = source.data('fieldInstance');

				if (fieldInstance) {
					var dynamicElement;
					var type = fieldInstance.get('fieldType');

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
								type: type
							}
						);
					}
					else {
						dynamicElement = instance._createDynamicNode(
							'dynamic-element',
							{
								name: encodeURI(fieldInstance.get('variableName')),
								type: type,
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
							buffer.push(displayAsTooltip.openTag);
							buffer.push('<![CDATA[' + fieldInstance.get('displayAsTooltip') + ']]>');
							buffer.push(displayAsTooltip.closeTag);

							buffer.push(entryRequired.openTag);
							buffer.push('<![CDATA[' + fieldInstance.get('required') + ']]>');
							buffer.push(entryRequired.closeTag);

							buffer.push(entryInstructions.openTag);
							buffer.push('<![CDATA[' + fieldInstance.get('instructions') + ']]>');
							buffer.push(entryInstructions.closeTag);

							buffer.push(label.openTag);
							buffer.push('<![CDATA[' + fieldInstance.get('fieldLabel') + ']]>');
							buffer.push(label.closeTag);

							buffer.push(predefinedValue.openTag);
							buffer.push('<![CDATA[' + fieldInstance.get('predefinedValue') + ']]>');
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

				var fieldInstance = source.data('fieldInstance');
				var type = fieldInstance.get('fieldType');
				var optionsList = source.find('> .folder > .field-container > .journal-article-component-container > .journal-list-subfield option');

		 		if (optionsList.length) {
					jQuery(optionsList).each(
						function() {
							var option = jQuery(this);
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
								if (option.is(':selected')) {
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
								communityPermissions: addCommunityPermissions,
								guestPermissions: addGuestPermissions,
								scopeGroupId: themeDisplay.getScopeGroupId()
							}
						),
						serviceParameterTypes: jQuery.toJSON(serviceParameterTypes)
					},
					function(message) {
						if (jQuery.isFunction(callback)) {
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
						if (jQuery.isFunction(callback)) {
							callback(message);
						}
					}
				);
			},

			_closeEditFieldOptions: function() {
				var instance = this;

				var source = instance._getSelectedField();
				var editButton = instance._getEditButton(source);
				var fieldsContainer = instance._getById(instance._fieldsContainerClass);
				var editContainerWrapper = instance._getById(instance._editFieldWrapperClass);
				var structureTree = instance._getById(instance._structureTreeClass)

				if (structureTree.data('revertHeight')) {
					structureTree.height('auto');
					structureTree.data('revertHeight', false);
				}

				instance.editContainerContextPanel.hide();

				instance._unselectFields();
			},

			_closeField: function(source) {
				var instance = this;

				if (instance._getFields().length <= 1) {
					return;
				}

				if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this-field-and-all-its-children'))) {
					source.fadeOut(
						'fast',
						function() {
							source.remove();

							if (source.is('.selected')) {
								instance._closeEditFieldOptions();
							}
							else {
								instance._repositionEditFieldOptions();
							}
						}
					);
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
					'boolean': Liferay.Portlet.Journal.FieldModel.Boolean,
					'document_library': Liferay.Portlet.Journal.FieldModel.DocumentLibrary,
					'image': Liferay.Portlet.Journal.FieldModel.Image,
					'image_gallery': Liferay.Portlet.Journal.FieldModel.ImageGallery,
					'link_to_layout': Liferay.Portlet.Journal.FieldModel.LinkToPage,
					'list': Liferay.Portlet.Journal.FieldModel.List,
					'multi-list': Liferay.Portlet.Journal.FieldModel.MultiList,
					'selection_break': Liferay.Portlet.Journal.FieldModel.SelectionBreak,
					'text': Liferay.Portlet.Journal.FieldModel.Text,
					'text_area': Liferay.Portlet.Journal.FieldModel.TextArea,
					'text_box': Liferay.Portlet.Journal.FieldModel.TextBox
				};

				YAHOO.lang.augmentObject(options, model[type]);

				var fieldInstance = new Liferay.Portlet.Journal.StructureField(options, instance.portletNamespace);

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

				jQuery.each(
					attributeMap || {},
					function(key, value) {
						if (value !== undefined) {
							attrs.push([key, '="',  value, '" '].join(''));
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

				AUI().use(
					'dialog',
					function(A) {
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

								instance._showMessage(dialogFields.messageElement, 'info', 'waiting-for-an-answer');

								var form = instance._getPrincipalForm();
								var structureIdInput = instance._getInputByName(form, 'structureId');
								var structureId = structureIdInput.val();

								if (!structureId) {
									var autoGenerateId = dialogFields.saveStructureAutogenerateId.is(':checked');

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
							var dialog = jQuery(dialogBody);

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
									dialogFields.loadDefaultStructure.show();
									dialogFields.dialogStructureId.attr('disabled', 'disabled');

									instance._showMessage(dialogFields.messageElement, 'success', 'your-request-processed-successfully');

									var structureMessage = instance._getById('structureMessage');

									structureMessage.hide();
								}
								else {
									var errorMessage = instance._translateErrorMessage(exception);

									instance._showMessage(dialogFields.messageElement, 'error', errorMessage);
								}
							};

							dialogFields.saveStructureAutogenerateId.click(
								function() {
									var checkbox = jQuery(this);
									var isChecked = checkbox.is(':checked');

									if (isChecked) {
										dialogFields.dialogStructureId.attr('disabled', 'disabled').val(dialogFields.autoGenerateIdMessage);
									}
									else {
										dialogFields.dialogStructureId.attr('disabled', '').val('');
									}
								}
							);

							dialogFields.showStructureIdContainer.toggle(
								function() {
									var newLabel = dialogFields.showStructureIdContainer.html().replace('&raquo;', '&laquo;');

									dialogFields.showStructureIdContainer.html(newLabel);
									dialogFields.structureIdContainer.show();

									return false;
								},
								function() {
									var newLabel = dialogFields.showStructureIdContainer.html().replace('&laquo;', '&raquo;');

									dialogFields.showStructureIdContainer.html(newLabel);
									dialogFields.structureIdContainer.hide();

									return false;
								}
							);

							dialogFields.dialogStructureName.focus();
						}
						else {
							instance._saveDialog.show();
						}

						if (openCallback) {
							openCallback.apply(instance, [ instance._saveDialog ]);
						}
					}
				);
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
						languageIdInput.find('option[value=' + defaultLocaleInput.val() + ']').select();
						return;
					}
				}

				var redirectInput = instance._getInputByName(form, 'redirect');
				var portletURL = Liferay.PortletURL.createRenderURL();

				portletURL.setParameter('struts_action', '/journal/edit_article');
				portletURL.setParameter('redirect', redirectInput.val());
				portletURL.setParameter('groupId', themeDisplay.getScopeGroupId());
				portletURL.setParameter('articleId', 'articleId');
				portletURL.setParameter('version', 'version');
				portletURL.setWindowState('maximized');

				var url = portletURL.toString() + '&' + instance.portletNamespace + 'languageId=' + languageIdInput.val();

				redirectInput.val(url);
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

				jQuery('input.journal-list-label').attr('disabled', 'disabled');

				if (instance._hasStructureChanged()) {
					var structureMessage = instance._getById('structureMessage');
					instance._showMessage(structureMessage, 'alert', null, 10000);
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

				structureTree.find('.journal-list-label').removeAttr('disabled');

				var structureMessage = instance._getById('structureMessage');
				instance._clearMessage(structureMessage);
			},

			_enableFields: function() {
				var instance = this;

				var fieldsContainer = instance._getById(instance._fieldsContainerClass);

				fieldsContainer.find(':input:not(:button)').removeAttr('disabled');
			},

			_disableFields: function() {
				var instance = this;

				var fieldsContainer = instance._getById(instance._fieldsContainerClass);

				fieldsContainer.find(':input:not(:button)').attr('disabled', 'disabled');
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
					scope = arguments[length];
				}

				if (length == 3) {
					namespace = arguments[1];
				}

				return jQuery(scope || document).find( instance._generateId(id, namespace) );
			},

			_getComponentType: function(source) {
				return jQuery(source).attr('data-component-type');
			},

			_getCloseButtons: function() {
				var instance = this;

				return jQuery(instance._closeButtonsClass);
			},

			_getDefaultLocale: function() {
				var instance = this;

				return instance._getById('defaultLocale').val();
			},

			_getEditButtons: function() {
				var instance = this;
				var structureTreeId = instance._generateId(instance._structureTreeClass);

				return jQuery(structureTreeId + instance._editButtonsClass);
			},

			_getRepeatableButtons: function() {
				var instance = this;
				var structureTreeId = instance._generateId(instance._structureTreeClass);

				return jQuery(structureTreeId + instance._repeatableButtonsClass);
			},

			_getParentStructureId: function() {
				var instance = this;

				return instance._getById('parentStructureId').val();
			},

			_getEditButton: function(source) {
				var instance = this;

				return source.find('input.edit-button:first');
			},

			_getFields: function() {
				var instance = this;
				var structureTreeId = instance._generateId(instance._structureTreeClass);

				return jQuery(structureTreeId + instance._fieldRowsClass);
			},

			_getRepeatedSiblings: function(fieldInstance) {
				var instance = this;
				var structureTreeId = instance._generateId(instance._structureTreeClass);

				return jQuery(structureTreeId + instance._fieldRowsClass + '[data-component-name=' + fieldInstance.get('variableName') + '].repeated-field');
			},

			_getSelectedField: function() {
				var instance = this;

				return instance._getFields().filter('.selected:first');
			},

			_getStructureXSD: function() {
				var instance = this;

				var buffer = [];
				var structureTreeId = instance._generateId(instance._structureTreeClass);
				var sourceRoots = jQuery(structureTreeId + ' > li.structure-field:not(.repeated-field)');

				var root = instance._createDynamicNode('root');

				buffer.push(root.openTag);

				jQuery(sourceRoots).each(
					function() {
						var source = jQuery(this);

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
				var sourceRoots = jQuery(structureTreeId + ' > li');

				var attributes = null;
				var availableLocales = [];
				var stillLocalized = false;
				var availableLocalesElements = jQuery('[name$=available_locales]');
				var defaultLocale = jQuery('[name$=defaultLocale]:first').val();

				instance._getFields().each(
					function() {
						var field = jQuery(this);
						var fieldInstance = field.data('fieldInstance');
						var isLocalized = fieldInstance.get('localized');

						if (isLocalized) {
							stillLocalized = true;
						}
					}
				);

				if (stillLocalized) {
					availableLocalesElements.each(
						function() {
							var locale = jQuery(this).val();

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

				jQuery(sourceRoots).each(
					function() {
						var source = jQuery(this);

						instance._appendStructureDynamicElementAndMetaData(source, buffer, true);
					}
				);

				buffer.push(root.closeTag);

				return buffer.join('');
			},

			_getPrincipalForm: function(formName) {
				var instance = this;

				return jQuery('form[name=' + instance.portletNamespace + (formName || 'fm1') + ']');
			},

			_getInputByName: function(currentForm, name, withoutNamespace) {
				var instance = this;

				var inputName = withoutNamespace ? name : instance.portletNamespace + name;

				return jQuery(currentForm).find('[name=' + inputName + ']');
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

				jQuery(selector).hide();

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

				var fieldInstance = source.data('fieldInstance');

				var check = function(checked) {
					return checked ? 'checked' : '';
				};

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

					displayAsTooltip.attr('checked', check(fieldInstance.get('displayAsTooltip')));
					repeatable.attr('checked', check(fieldInstance.get('repeatable')));
					fieldType.find('[value="' + fieldInstance.get('fieldType') + '"]').select();
					localized.attr('checked', check(fieldInstance.get('localized')));
					instructions.val(fieldInstance.get('instructions'));
					predefinedValue.val(fieldInstance.get('predefinedValue'));
					required.attr('checked', check(fieldInstance.get('required')));
					variableName.val(fieldInstance.get('variableName'));
					fieldLabel.val(fieldInstance.get('fieldLabel'));

					var elements = editContainerWrapper.find(':text, textarea, :checkbox:not(#localized)');

					if (source.is('.repeated-field')) {
						elements.attr('disabled', 'disabled');
					}
					else {
						elements.removeAttr('disabled');
					}
				}
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

				titleAuxFormInput.val( titleInput.val() );
				xmlAuxFormInput.val(articleContent);

				auxForm.submit();
			},

			_renderEditFieldOptions: function(source) {
				var instance = this;

				var editButtons = instance._getEditButtons();
				var editButton = instance._getEditButton(source)[0];
				var structureTree = instance._getById(instance._structureTreeClass)

				if (structureTree.height() < 550) {
					structureTree.height(550);
					structureTree.data('revertHeight', true);
				}

				instance._editContainerNormalMode();

				var row = jQuery(source);
				var offset = row.offset();

				var fields = instance._getFields();
				var editContainerWrapper = instance._getById(instance._editFieldWrapperClass);
				var fieldsContainer = instance._getById(instance._fieldsContainerClass);
				var structureTree = instance._getById(instance._structureTreeClass)

				var editContainerHeight = editContainerWrapper.height();
				var fieldsContainerWidth = fieldsContainer.width();
				var fieldsContainerHeight = fieldsContainer.height();
				var fieldsContainerOffset = fieldsContainer.offset();
				var isVisible = editContainerWrapper.is(':visible');

				fields.removeClass('selected');
				row.addClass('selected');

				if (instance._lastEditContainerTrigger != editButton) {
					instance.editContainerContextPanel.set('trigger', editButton);
					instance.editContainerContextPanel.show();

					instance._lastEditContainerTrigger = editButton;
				}

				instance._hideEditContainerMessage();
				instance._loadEditFieldOptions(source);

				instance.editContainerContextPanel.refreshAlign();
			},

			_repeatField: function(source, editMode) {
				var instance = this;

				var fieldInstance = source.data('fieldInstance').clone();

				if (fieldInstance.get('fieldType') == 'text_area') {
					var html = instance._buildWYSIWYGEditorHTML(fieldInstance);

					fieldInstance.set('innerHTML', html);
				}

				var htmlTemplate = instance._createFieldHTMLTemplate(fieldInstance);
				var newComponent = jQuery(htmlTemplate);

				fieldInstance.set('source', newComponent);

				newComponent.hide();
				newComponent.insertAfter(source);

				newComponent.addClass('repeated-field');

				newComponent.fadeIn('slow');

				instance._loadEditFieldOptions(newComponent);
				instance._saveEditFieldOptions(newComponent);

				if (editMode) {
					instance._renderEditFieldOptions(newComponent);
				}

				instance._createNestedList(newComponent, instance.nestedListOptions, instance.nestedListEvents);

				instance._attachEvents();
			},

			_clearMessage: function(selector) {
				var instance = this;

				var journalMessage = jQuery(selector);

				clearTimeout(instance.messageDelay[selector]);

				journalMessage.hide();
			},

			_repositionEditFieldOptions: function() {
				var instance = this;

				var editContainerWrapper = instance._getById(instance._editFieldWrapperClass);
				var isVisible = editContainerWrapper.parent().is(':visible');

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
					cmd =  instance.articleId ? 'update' : 'add';
				}

				var cmdInput = instance._getInputByName(form, 'cmd');
				var newArticleIdInput = instance._getInputByName(form, 'newArticleId');
				var articleIdInput = instance._getInputByName(form, 'articleId');
				var contentInput = instance._getInputByName(form, 'content');
				var approveInput = instance._getInputByName(form, 'approve');
				var saveAndContinueInput = instance._getInputByName(form, 'saveAndContinue');

				var canSubmmit = instance._validadeRequiredFields();

				if (canSubmmit) {
					cmdInput.val(cmd);

					if (cmd == 'approve') {
						approveInput.val(1);
					}

					if (saveAndContinue) {
						saveAndContinueInput.val(1);
					}

					if (!instance.articleId) {
						articleIdInput.val( newArticleIdInput.val() );
					}

					var content = instance._getArticleContentXML();

					contentInput.val(content);

					form.submit();
				}
			},

			_saveEditFieldOptions: function(source) {
				var instance = this;

				var fieldInstance = source.data('fieldInstance');

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
					var localizedValue = instance._getById('Localized', source).val();

					var variableNameValue = variableName.val();
					var canSave = true;
					var sourceRepeated = source.is('.repeated-field');

					instance._getFields().each(
						function() {
							var sourceValidation = jQuery(this);
							var fieldInstanceValidation = sourceValidation.data('fieldInstance');

							if (!sourceValidation.is('.selected') &&
								fieldInstanceValidation &&
								!sourceRepeated &&
								!sourceValidation.is('.repeated-field')) {

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
								displayAsTooltip: displayAsTooltip.is(':checked'),
								fieldType: fieldType.find('option:selected').val(),
								instructions: instructions.val(),
								localized: localized.is(':checked'),
								localizedValue: localizedValue,
								predefinedValue: predefinedValue.val(),
								repeatable: repeatable.is(':checked'),
								required: required.is(':checked')
							}
						);

						instance._updateFieldVariableName(fieldInstance, variableNameValue);
						instance._updateFieldLabelName(fieldInstance, fieldLabel.val());

						instance._showMessage(journalMessage, 'success', 'your-request-processed-successfully');

						instance._editContainerNormalMode();

						if (!fieldInstance.get('repeatable')) {
							instance._getRepeatedSiblings(fieldInstance).remove();
						}
					}
					else {
						variableName.focus();
						instance._showMessage(journalMessage, 'error', 'duplicated-variable-name');
					}
				}
			},

			_stripComponentType: function(type) {
				return type.toLowerCase().replace(/[^a-z]+/g, '');
			},

			_translateErrorMessage: function(exception) {
				var errorKey = '';

				if (exception.indexOf('StructureXsdException') > -1) {
					errorKey = 'please-enter-a-valid-xsd';
				}
				else if (exception.indexOf('DuplicateStructureIdException') > -1) {
					errorKey = 'please-enter-a-unique-id';
				}
				else if (exception.indexOf('StructureDescriptionException') > -1) {
					errorKey = 'please-enter-a-valid-description';
				}
				else if (exception.indexOf('StructureIdException') > -1) {
					errorKey = 'please-enter-a-valid-id';
				}
				else if (exception.indexOf('StructureInheritanceException') > -1) {
					errorKey = 'this-structure-is-already-within-the-inheritance-path-of-the-selected-parent-please-select-another-parent-structure';
				}
				else if (exception.indexOf('StructureNameException') > -1) {
					errorKey = 'please-enter-a-valid-name';
				}
				else if (exception.indexOf('NoSuchStructureException') > -1) {
					errorKey = 'please-enter-a-valid-id';
				}
				else if (exception.indexOf('ArticleContentException') > -1) {
					errorKey = 'please-enter-valid-content';
				}
				else if (exception.indexOf('ArticleIdException') > -1) {
					errorKey = 'please-enter-a-valid-id';
				}
				else if (exception.indexOf('ArticleTitleException') > -1) {
					errorKey = 'please-enter-a-valid-name';
				}
				else if (exception.indexOf('DuplicateArticleIdException') > -1) {
					errorKey = 'please-enter-a-unique-id';
				}

				return Liferay.Language.get(errorKey);
			},

			_initializePageLoadFieldInstances: function() {
				var instance = this;

				var fields = instance._getFields();

				fields.each(
					function(i) {
						var field = jQuery(this);
						var fieldInstance = field.data('fieldInstance');

						if (!fieldInstance) {
							var fieldLabel = field.attr('data-component-label');
							var componentName = field.attr('data-component-name');
							var componentType = field.attr('data-component-type');
							var instructions = field.attr('data-component-instructions');
							var required = field.attr('data-component-required');
							var displayAsTooltip = field.attr('data-component-displayAsTooltip');
							var parentValue = field.attr('data-component-parentStructureId');
							var repeatable = field.attr('data-component-repeatable');
							var predefinedValue = field.attr('data-component-predefinedValue');
							var instanceId = field.attr('data-component-instanceId');

							var localizedValue = instance._getById('Localized', field).val();
							var isLocalized = localizedValue != 'false';

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
									variableName: componentName
								}
							);
						}
					}
				);
			},

			_showMessage: function(selector, type, message, delay) {
				var instance = this;
				var journalMessage = selector;

				if (!selector) {
					journalMessage = instance._generateId('journalMessage');
				}

				var journalMessage = jQuery(selector);
				var className = 'portlet-msg-' + (type || 'success');

				journalMessage.removeClass().addClass(className);
				journalMessage.show();

				instance.editContainerContextPanel.refreshAlign();

				if (message) {
					var messageLang = Liferay.Language.get(message);
					journalMessage.html(messageLang);
				}

				clearTimeout(instance.messageDelay[selector]);

				instance.messageDelay[selector] = setTimeout(
					function() {
						clearTimeout(instance.messageDelay[selector]);

						journalMessage.fadeOut(
							function () {
								jQuery(this).hide();
								instance.editContainerContextPanel.refreshAlign();
							}
						);
					},
					delay || 5000
				);
			},

			_updateFieldVariableName: function(fieldInstance, variableName) {
				var instance = this;

				var repeatedSiblings = instance._getRepeatedSiblings(fieldInstance);

				repeatedSiblings.each(
					function() {
						var field = jQuery(this);

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
					function() {
						var field = jQuery(this);
						var newFieldInstance = fieldInstance.clone();

						newFieldInstance.set('source', field);
						newFieldInstance.set('fieldLabel', fieldLabel);
					}
				);

				fieldInstance.set('fieldLabel', fieldLabel);
			},

			_unselectFields: function() {
				var instance = this;

				instance._getSelectedField().removeClass('selected');
			},

			_validadeRequiredFields: function() {
				var instance = this;

				var canSubmmit = true;
				var structureTreeId = instance._generateId(instance._structureTreeClass);
				var fields = jQuery(structureTreeId + instance._fieldRowsClass);
				var requiredFields = fields.filter('[data-component-required=true]');
				var fieldsConatainer = fields.find('.field-container');
				var firstEmptyField = null;

				fieldsConatainer.removeClass('required-field');

				jQuery.each(
					requiredFields,
					function() {
						var source = jQuery(this);
						var fieldInstance = source.data('fieldInstance');
						var content = fieldInstance.getContent(source);

						if (!content) {
							var fieldConatainer = source.find('.field-container:first');

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

				var componentContainer = source.find('div.journal-article-component-container');

				return componentContainer.find('.principal-field-element:first');
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
			_textAreaFields: ' li[data-component-type=text_area]'
		}
	);

	Liferay.Portlet.Journal.StructureField = Alloy.Observable.extend(
		{
			initialize: function(options, portletNamespace) {
				var instance = this;

				instance.portletNamespace = portletNamespace;

				options = options || {};

				var Lang = YAHOO.lang;

				instance.add(
					'source',
					{
						handler: instance.setSource,
						value: options.source || jQuery([])
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
			},

			clone: function(options) {
				var instance = this;

				var clone = new instance.constructor(options || {});

				var currentConfig = instance.cfg.getConfig();

				clone.set(currentConfig, true);

				return clone;
			},

			createInstructionsMessageContainer: function(value) {
				return jQuery('<div class="journal-article-instructions-container journal-article-instructions-message portlet-msg-info"></div>').html(value);
			},

			createTooltipImage: function() {
				return jQuery('<img align="top" class="journal-article-instructions-container" src="' + themeDisplay.getPathThemeImages() + '/portlet/help.png" />');
			},

			getContent: function(source) {
				var instance = this;

				var content;
				var type = instance.get('fieldType');
				var fieldInstance = source.data('fieldInstance');
				var componentContainer = source.find('div.journal-article-component-container');

				var principalElement = componentContainer.find('.principal-field-element:first');

				if (type == 'boolean') {
					content = principalElement.is(':checked');
				}
				else if (type == 'text_area') {
					var editorName = source.find('iframe:first').attr('name');
					var editorReference = window[editorName];

					if (editorReference && jQuery.isFunction(editorReference.getHTML)) {
						content = editorReference.getHTML();
					}
				}
				else if (type == 'multi-list') {
					content = principalElement.val() || [];
					content = content.join(',');
				}
				else if (type == 'image') {
					var imageContent = componentContainer.find('.journal-image-content');
					var imageDelete = componentContainer.find('.journal-image-delete');

					if (imageDelete.val() == 'delete') {
						content = 'delete';
					}
					else {
						content = imageContent.val() || principalElement.val();
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
					var repeatableBtnTemplateHTML = repeatableBtnTemplate.html();

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

					instance.fieldContainer = jQuery(htmlTemplate.join(''));

					var source = instance.fieldContainer.find('li:first');

					source.attr('data-component-name', instance.get('variableName'));
					source.attr('data-component-type', instance.get('fieldType'));
					source.attr('data-component-required', instance.get('required'));
				}

				return instance.fieldContainer;
			},

			getFieldElementContainer: function() {
				var instance = this;

				if (!instance.fieldElementContainer) {
					instance.fieldElementContainer = instance.getFieldContainer().find('div.journal-article-component-container');
				}

				return instance.fieldElementContainer;
			},

			getFieldLabelElement: function() {
				var instance = this;
				var source = instance.get('source', instance.getFieldContainer(), true);

				return source.find('> .folder > .field-container .journal-article-field-label');
			},

			getLocalizedValue: function() {
				var instance = this;

				return instance.get('source').find('input.journal-article-localized').val();
			},

			setFieldLabel: function(key, args) {
				var instance = this;

				var value = args[0] || instance.get('variableName');

				var fieldLabel = instance.getFieldLabelElement();

				fieldLabel.find('span:first').html(value);

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
					var inputFile = source.find('.journal-article-component-container :file');

					if (isLocalized) {
						inputFileName += '_' + instance.get('localizedValue');
					}

					inputFile.attr('name', inputFileName);
				}
			},

			setInstructions: function(key, args) {
				var instance = this;

				var value = args[0];

				instance._setDataAttribute(key, value);

				var source = instance.get('source');
				var fieldInstance = source.data('fieldInstance');

				if (fieldInstance) {
					var fieldContainer = source.find('> .folder > .field-container');
					var label = fieldInstance.getFieldLabelElement().eq(0);
					var tooltipIcon = label.find('.journal-article-instructions-container');
					var journalInstructionsMessage = fieldContainer.find('.journal-article-instructions-message');
					var displayAsTooltip = fieldInstance.get('displayAsTooltip');

					tooltipIcon.remove();
					journalInstructionsMessage.remove();

					if (value) {
						if (!displayAsTooltip) {
							var instructionsMessage = fieldInstance.createInstructionsMessageContainer(value);
							var requiredMessage = fieldContainer.find('.journal-article-required-message');

							instructionsMessage.insertAfter(requiredMessage);
						}
						else {
							label.append( fieldInstance.createTooltipImage() );
						}
					}
				}
			},

			setRepeatable: function(key, args) {
				var instance = this;

				var value = args[0];

				instance._setDataAttribute(key, value);

				var source = instance.get('source');

				var fieldInstance = source.data('fieldInstance');
				var fieldContainer = source.find('> .folder > .field-container');
				var repeatableFieldImage = fieldContainer.find('.repeatable-field-image');
				var repeatableAddIcon = source.find('.journal-article-buttons .repeatable-button');

				repeatableFieldImage.remove();

				var repeatable = instance.get('repeatable');
				var parentStructureId = instance.get('parentStructureId');

				if (repeatable && !parentStructureId) {
					var repeatableFieldImageModel = jQuery('#repeatable-field-image-model').html();

					fieldContainer.append(repeatableFieldImageModel);
					repeatableAddIcon.show();
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

				if (!value) {
					value = [];
				}

				if (!value.jquery || !value.length) {
					value = jQuery(value);

					instance.set('source', value, true);
				}

				value.data('fieldInstance', instance);
			},

			setVariableName: function(key, args) {
				var instance = this;

				var value = args[0];

				var fieldLabel = instance.getFieldLabelElement();

				fieldLabel.attr('for', value);
				fieldLabel.siblings('input:first').attr('id', value);

				instance._setDataAttribute('name', value);
			},

			_getDataAttribute: function(key, defaultValue) {
				var instance = this;

				var value = instance.get('source').attr('data-component-' + key);

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

				instance.get('source').attr('data-component-' + key, value);
			},

			_generateId: Liferay.Portlet.Journal.prototype._generateId,

			_getById: Liferay.Portlet.Journal.prototype._getById
		}
	);

	Liferay.Portlet.Journal.FieldModel = {};

	AUI().ready(
		function() {
			var fieldModel = Liferay.Portlet.Journal.FieldModel;

			var fieldModelsContainer = jQuery('#journalFieldModelContainer');

			var createFieldModel = function(namespace, type, variableName) {
				var innerHTML = fieldModelsContainer.find('[data-component-type="' + type + '"]').html();

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
		}
	);
})();