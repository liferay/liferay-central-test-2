AUI().add(
	'liferay-portlet-journal',
	function(A) {
		var Lang = A.Lang;

		var TPL_FIELD_CONTAINER = '<div><li class="structure-field">' +
				'<span class="journal-article-close"></span>' +
				'<span class="folder">' +
					'<div class="field-container">' +
						'<div class="journal-article-move-handler"></div>' +
						'<label for="" class="journal-article-field-label"><span>{fieldLabel}</span></label>' +
						'<div class="journal-article-component-container"></div>' +
						'<div class="journal-article-required-message portlet-msg-error">{requiredFieldLanguage}</div>' +
						'<div class="journal-article-buttons">' +
							'<input type="button" class="edit-button" value="{editOptionsLanguage}"/>' +
							'{repeatableBtnTemplateHTML}' +
						'</div>' +
					'</div>' +
					'<ul class="folder-droppable"></ul>' +
				'</span>' +
			'</li></div>';

		var TPL_HELPER = '<div id="{0}" class="journal-article-helper not-intersecting">' +
			'<div class="journal-component"></div>' +
			'<div class="forbidden-action"></div>' +
		'</div>';

		var TPL_IFRAME = '<iframe frameborder="0" height="250" id="{name}" name="{name}" scrolling="no" src="{url}" width="500"></iframe>';

		var TPL_INSTRUCTIONS_CONTAINER = '<div class="journal-article-instructions-container journal-article-instructions-message portlet-msg-info"></div>';

		var TPL_OPTION = '<option></option>';

		var TPL_PLACEHOLDER = '<div class="aui-tree-placeholder aui-tree-sub-placeholder"></div>';

		var TPL_STRUCTURE_FIELD_INPUT = '<input class="principal-field-element lfr-input-text" type="text" value="" size="40"/>';

		var TPL_TOOLTIP_IMAGE = '<img align="top" class="journal-article-instructions-container" src="' + themeDisplay.getPathThemeImages() + '/portlet/help.png" />';

		var fieldsDataSet = new A.DataSet();

		var Journal = function(portletNamespace, articleId, instanceIdKey) {
			var instance = this;

			instance.articleId = articleId;
			instance.instanceIdKey = instanceIdKey;
			instance.timers = {};
			instance.portletNamespace = portletNamespace;

			var structureTreeId = instance._guid('#structureTree');
			var structureTree = A.one(structureTreeId);

			instance._helperId = instance._guid('journalArticleHelper', instance.portletNamespace, '');

			var helperHTML = A.substitute(TPL_HELPER, [instance._helperId]);

			instance._helper = A.Node.create(helperHTML);

			instance._helper.appendTo(document.body);

			instance.acceptChildren = true;

			var placeholder = A.Node.create(TPL_PLACEHOLDER);

			var fields = A.all(structureTreeId + ' li.structure-field');

			instance.nestedListOptions = {
				dd: {
					handles: ['.journal-article-move-handler']
				},
				dropCondition: function(event) {
					var dropNode = event.drop.get('node');

					return instance.canDrop(dropNode);
				},
				dropOn: 'span.folder > ul.folder-droppable',
				helper: instance._helper,
				placeholder: placeholder,
				sortCondition: function(event) {
					var dropNode = event.drop.get('node');

					return dropNode.ancestor(structureTreeId);
				},
				sortOn: structureTreeId
			};

			instance.nestedListEvents = {
				'drag:start': function(event) {
					var helper = instance._helper;

					helper.setStyle('height', '100px');
					helper.setStyle('width', '450px');

					instance.updateTextAreaVisibility('hidden');
				},

				'drag:end': function(event) {
					instance._dropField();

					instance.updateTextAreaVisibility('visible');
				},

				'drag:out': function(event) {
					if (!instance.acceptChildren) {
						instance.helperIntersecting();
						instance.acceptChildren = true;
					}
				},

				'drag:over': function(event) {
					var dropNode = event.drop.get('node');

					instance.acceptChildren = instance.canDrop(dropNode);

					if (instance.acceptChildren) {
						instance.helperIntersecting();
					}
					else {
						instance.helperNotIntersecting();
					}
				}
			};

			instance.createNestedList(
				fields,
				instance.nestedListOptions,
				instance.nestedListEvents
			);

			var journalComponentListId = instance._guid('#journalComponentList');
			var componentFields = A.all(journalComponentListId + ' .component-group .journal-component');

			instance.componentFieldsOptions = {
				dropCondition: function(event) {
					var dropNode = event.drop.get('node');

					return instance.canDrop(dropNode);
				},
				dropOn: 'span.folder > ul.folder-droppable',
				helper: instance._helper,
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
					var componentType = instance.getComponentType(source);
					var className = 'journal-component-' + instance._stripComponentType(componentType);
					var helper = instance._helper;
					var helperComponentIcon = instance._helper.all('div.journal-component');

					helper.setStyle('height', '25px');
					helper.setStyle('width', '200px');

					if (helperComponentIcon) {
						helperComponentIcon.addClass(className).html(languageName);
					}

					proxy.addClass('component-dragging');

					instance.updateTextAreaVisibility('hidden');

					instance.clonedSource = source.cloneNode(true);

					source.placeBefore(instance.clonedSource);

					instance.clonedSource.attr('id', '');
					instance.clonedSource.guid();

					instance.clonedSource.show().setStyle('visibility', 'visible');
					instance.clonedSource.removeClass('aui-helper-hidden');
					instance.clonedSource.addClass('dragging');

					instance.createNestedList(
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

					var componentType = instance.getComponentType(source);
					var className = 'journal-component-' + instance._stripComponentType(componentType);
					var helperComponentIcon = instance._helper.all('div.journal-component');

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
							var html = instance.buildHTMLEditor(fieldInstance);

							fieldInstance.set('innerHTML', html);
						}

						var htmlTemplate = instance._createFieldHTMLTemplate(fieldInstance);
						var newComponent = A.Node.create(htmlTemplate);

						addedComponent.placeBefore(newComponent);
						addedComponent.remove();

						fieldInstance.set('source', newComponent);
						fieldInstance.set('variableName', variableName);

						instance.createNestedList(
							newComponent,
							instance.nestedListOptions,
							instance.nestedListEvents
						);

						instance._attachEvents();

						var id = newComponent.get('id');

						fieldsDataSet.add(id, fieldInstance);

						instance.repositionEditFieldOptions();
					}
					else {
						source.remove();
					}

					instance.updateTextAreaVisibility('visible');

					if (instance.clonedSource) {
						var journalComponentList = instance.getById('#journalComponentList');

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

			instance.createNestedList(
				componentFields,
				instance.componentFieldsOptions,
				instance.componentFieldsEvents,
				true
			);

			var fieldLabel = instance.getById('fieldLabel');
			var editContainerWrapper = instance.getById('#journalArticleEditFieldWrapper');

			editContainerWrapper.show();

			instance.editContainerContextPanel = new A.ContextPanel(
				{
					after: {
						hide: A.bind(instance.closeEditFieldOptions, instance),
						show: function() {
							A.later(
								0,
								instance,
								function() {
									fieldLabel.focus();
								}
							);
						}
					},
					align: {
						points: ['rc', 'lc']
					},
					bodyContent: editContainerWrapper,
					trigger: 'input.edit-button'
				}
			)
			.render();

			A.ContextOverlayManager.remove(instance.editContainerContextPanel);

			instance._initializePageLoadFieldInstances();
			instance._attachEvents();
			instance._attachEditContainerEvents();
			instance._attachDelegatedEvents();

			instance._updateOriginalContent();
			instance._updateOriginalStructureXSD();
		};

		Journal.prototype = {
			addStructure: function(structureId, autoStructureId, name, description, xsd, callback) {

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
						serviceContext: A.JSON.stringify(
							{
								addCommunityPermissions: addCommunityPermissions,
								addGuestPermissions: addGuestPermissions,
								scopeGroupId: themeDisplay.getScopeGroupId()
							}
						),
						serviceParameterTypes: A.JSON.stringify(serviceParameterTypes)
					},
					function(message) {
						if (Lang.isFunction(callback)) {
							callback(message);
						}
					}
				);
			},

			articleChange: function(attribute) {
				var instance = this;

				var form = instance.getPrincipalForm();
				var content = instance.getByName(form, 'originalContent').val();

				var hasChanged = (content != encodeURIComponent(instance.getArticleContentXML()));

				return hasChanged;
			},

			buildHTMLEditor: function(fieldInstance) {
				var instance = this;

				var name = instance.portletNamespace + 'structure_el_' + instance._getUID() + '_content';
				var url = instance.buildHTMLEditorURL(fieldInstance);

				var iframeHTML = A.substitute(
					TPL_IFRAME,
					{
						name: name,
						url: url
					}
				);

				return iframeHTML;
			},

			buildHTMLEditorURL: function(fieldInstance) {
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

			canDrop: function(source) {
				var instance = this;

				var componentType = instance.getComponentType(source);

				var canDrop = true;

				if ((componentType == 'multi-list') || (componentType == 'list')) {
					canDrop = false;
				}
				else if (source.hasClass('repeated-field')) {
					canDrop = false;
				}

				return canDrop;
			},

			changeLanguageView: function() {
				var instance = this;

				var form = instance.getPrincipalForm();

				var articleContent = instance.getArticleContentXML();
				var cmdInput = instance.getByName(form, 'cmd');
				var defaultLocaleInput = instance.getByName(form, 'defaultLocale');
				var languageIdInput = instance.getByName(form, 'languageId');
				var redirectInput = instance.getByName(form, 'redirect');

				if (instance.articleChange() && confirm(Liferay.Language.get('would-you-like-to-save-the-changes-made-to-this-language'))) {
					var contentInput = instance.getByName(form, 'content');

					cmdInput.val('update');
					contentInput.val(articleContent);
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

				form.submit();
			},

			clearMessage: function(selector) {
				var instance = this;

				var journalMessage = A.get(selector);

				var timer = instance.timers[selector];

				if (timer) {
					timer.cancel();
				}

				journalMessage.hide();
			},

			closeEditFieldOptions: function() {
				var instance = this;

				var fieldsContainer = instance.getById('#journalArticleContainer');
				var editContainerWrapper = instance.getById('#journalArticleEditFieldWrapper');
				var structureTree = instance.getById('#structureTree')

				instance.editContainerContextPanel.hide();

				instance.unselectFields();
			},

			closeField: function(source) {
				var instance = this;

				var fields = instance.getFields();

				if (fields && fields.size() > 1) {
					if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this-field-and-all-its-children'))) {
						instance.closeRepeatedSiblings(source);
						instance.closeEditFieldOptions();

						if (source.inDoc()) {
							source.remove();
						}
					}
				}
			},

			closeRepeatedSiblings: function(source) {
				var instance = this;

				var id = source.get('id');
				var fieldInstance = fieldsDataSet.item(id);

				if (fieldInstance.get('repeatable')) {
					var repeatedFields = instance.getRepeatedSiblings(fieldInstance);

					if (repeatedFields) {
						repeatedFields.remove();
					}
				}
			},

			createNestedList: function(nodes, options, events, components) {
				var instance = this;

				var applyEvents = function(nestedList) {
					A.each(
						events,
						function(item, index, collection) {
							if (index && Lang.isFunction(item)) {
								nestedList.on(index, item);
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
					function(item, index, collection) {
						var nestedList = instance._nestedList;

						if (components) {
							nestedList = instance._nestedListComponents;
						}

						nestedList.add(item);
					}
				);
			},

			disableEditMode: function() {
				var instance = this;

				var articleHeaderEdit = instance.getById('articleHeaderEdit');
				var editStructureBtn = instance.getById('editStructureBtn');
				var fieldsContainer = instance.getById('#journalArticleContainer');
				var journalComponentList = instance.getById('#journalComponentList');
				var saveStructureBtn = instance.getById('saveStructureBtn');

				instance.closeEditFieldOptions();

				articleHeaderEdit.show();
				saveStructureBtn.hide();
				journalComponentList.hide();

				fieldsContainer.removeClass('journal-edit-mode');

				var structureBtnText = Liferay.Language.get('edit');

				editStructureBtn.val(structureBtnText);

				A.all('input.journal-list-label').attr('disabled', 'disabled');

				if (instance.structureChange()) {
					var structureMessage = instance.getById('structureMessage');

					instance.showMessage(
						structureMessage,
						'alert',
						null,
						30000
					);
				}

				var columnFirst = A.one('.aui-column-first');
				var columnLast = A.one('.aui-column-last');

				columnFirst.show();
				columnLast.setStyle('float', '');
				columnLast.replaceClass('aui-w100', 'aui-w75');
			},

			disableFields: function() {
				var instance = this;

				var fieldsContainer = instance.getById('#journalArticleContainer');

				fieldsContainer.all('input:not(:button)').attr('disabled', 'disabled');
				fieldsContainer.all('textarea, select').attr('disabled', 'disabled');
			},

			downloadArticleContent: function() {
				var instance = this;

				var downloadAction = themeDisplay.getPathMain() + '/journal/get_article_content';
				var auxForm = instance.getPrincipalForm('fm2');
				var form = instance.getPrincipalForm();

				var articleContent = instance.getArticleContentXML();
				var xmlInput = instance.getByName(auxForm, 'xml', true);

				if (instance.structureChange()) {
					if (confirm(Liferay.Language.get('you-should-save-the-structure-first'))) {
						instance.openSaveStructureDialog();
					}
				}
				else {
					auxForm.attr('action', downloadAction);
					auxForm.attr('target', '_self');

					xmlInput.val(articleContent);

					auxForm.submit();
				}
			},

			editContainerNormalMode: function() {
				var instance = this;

				var editContainerWrapper = instance.getById('#journalArticleEditFieldWrapper');

				editContainerWrapper.removeClass('save-mode');
				instance.editContainerModified = false;
			},

			editContainerSaveMode: function() {
				var instance = this;

				var editContainerWrapper = instance.getById('#journalArticleEditFieldWrapper');

				editContainerWrapper.addClass('save-mode');
				instance.editContainerModified = true;
			},

			enableEditMode: function() {
				var instance = this;

				var articleHeaderEdit = instance.getById('articleHeaderEdit')
				var editStructureBtn = instance.getById('editStructureBtn');
				var fieldsContainer = instance.getById('#journalArticleContainer');
				var journalComponentList = instance.getById('#journalComponentList');
				var saveStructureBtn = instance.getById('saveStructureBtn');

				instance.editContainerNormalMode();

				articleHeaderEdit.hide();
				saveStructureBtn.show();
				fieldsContainer.addClass('journal-edit-mode');
				journalComponentList.show();

				var structureTree = instance.getById('#structureTree');
				var structureBtnText = Liferay.Language.get('stop-editing');

				editStructureBtn.val(structureBtnText);

				structureTree.all('.journal-list-label').attr('disabled', '');

				var structureMessage = instance.getById('structureMessage');
				instance.clearMessage(structureMessage);

				var columnFirst = A.one('.aui-column-first');
				var columnLast = A.one('.aui-column-last');

				columnFirst.hide();
				columnLast.setStyle('float', 'left');
				columnLast.replaceClass('aui-w75', 'aui-w100');
			},

			enableFields: function() {
				var instance = this;

				var fieldsContainer = instance.getById('#journalArticleContainer');

				fieldsContainer.all('input:not(:button)').attr('disabled', '');
				fieldsContainer.all('textarea, select').attr('disabled', '');
			},

			generateInstanceId: function() {
				var instance = this;

				var instanceId = '';

				var key = instance.instanceIdKey;

				for (var i = 0; i < 8; i++) {
					var pos = Math.floor(Math.random() * key.length);

					instanceId += key.substring(pos, pos + 1);
				}

				return instanceId;
			},

			getArticleContentXML: function() {
				var instance = this;

				var buffer = [];
				var structureTreeId = instance._guid('#structureTree');
				var sourceRoots = A.all(structureTreeId + ' > li');
				var hasStructure = instance.hasStructure();

				var content;

				if (!hasStructure) {
					var item = sourceRoots.item(0);

					if (item) {
						var id = item.get('id');
						var fieldInstance = fieldsDataSet.item(id);

						content = fieldInstance.getContent(item);
					}
				}
				else {
					var attributes = null;
					var availableLocales = [];
					var stillLocalized = false;
					var availableLocalesElements = A.all('[name=' + instance.portletNamespace + 'available_locales]');
					var defaultLocale = instance.getById('defaultLocale').val();

					instance.getFields().each(
						function(item, index, collection) {
							var id = item.get('id');
							var fieldInstance = fieldsDataSet.item(id);
							var isLocalized = fieldInstance.get('localized');

							if (isLocalized) {
								stillLocalized = true;
							}
						}
					);

					if (stillLocalized) {
						availableLocalesElements.each(
							function(item, index, collection) {
								var locale = item.val();

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
						function(item, index, collection) {
							instance._appendStructureTypeElementAndMetaData(item, buffer, true);
						}
					);

					buffer.push(root.closeTag);

					content = buffer.join('');
				}

				return content;
			},

			getById: function(id, namespace) {
				var instance = this;

				return A.one(
					instance._guid(id, namespace)
				);
			},

			getCloseButtons: function() {
				var instance = this;

				return A.all('span.journal-article-close');
			},

			getComponentType: function(source) {
				return source.attr('dataType');
			},

			getDefaultLocale: function() {
				var instance = this;

				return instance.getById('defaultLocale').val();
			},

			getEditButton: function(source) {
				var instance = this;

				return source.one('input.edit-button');
			},

			getEditButtons: function() {
				var instance = this;

				var structureTreeId = instance._guid('#structureTree');

				return A.all(structureTreeId + ' div.journal-article-buttons input.edit-button');
			},

			getFields: function() {
				var instance = this;

				var structureTreeId = instance._guid('#structureTree');

				return A.all(structureTreeId + ' li');
			},

			getParentStructureId: function() {
				var instance = this;

				return instance.getById('parentStructureId').val();
			},

			getRepeatableButtons: function() {
				var instance = this;

				var structureTreeId = instance._guid('#structureTree');

				return A.all(structureTreeId + ' div.journal-article-buttons input.repeatable-button');
			},

			getRepeatedSiblings: function(fieldInstance) {
				var instance = this;

				var structureTreeId = instance._guid('#structureTree');
				var selector = structureTreeId + ' li[dataName=' + fieldInstance.get('variableName') + '].repeated-field';

				return A.all(selector);
			},

			getSaveDialog: function(openCallback) {
				var instance = this;

				if (!instance._saveDialog) {
					var saveStructureTemplateDialog = instance.getById('saveStructureTemplateDialog');
					var htmlTemplate = saveStructureTemplateDialog.html();
					var title = Liferay.Language.get('editing-structure-details');

					var form = instance.getPrincipalForm();

					var structureIdInput = instance.getByName(form, 'structureId');
					var structureNameInput = instance.getByName(form, 'structureName');
					var structureDescriptionInput = instance.getByName(form, 'structureDescription');
					var storedStructureXSD = instance.getByName(form, 'structureXSD');

					var saveCallback = function() {
						var dialogFields = instance._saveDialog.fields;

						instance.showMessage(
							dialogFields.messageElement,
							'info',
							Liferay.Language.get('waiting-for-an-answer')
						);

						var form = instance.getPrincipalForm();
						var structureIdInput = instance.getByName(form, 'structureId');
						var structureId = structureIdInput.val();

						if (!structureId) {
							var autoGenerateId = dialogFields.saveStructureAutogenerateId.get('checked');

							instance.addStructure(
								dialogFields.dialogStructureId.val(),
								autoGenerateId,
								dialogFields.dialogStructureName.val(),
								dialogFields.dialogDescription.val(),
								dialogFields.contentXSD,
								serviceCallback
							);
						}
						else {
							instance.updateStructure(
								instance.getParentStructureId(),
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

					instance._saveDialog.fields = {
						autoGenerateIdMessage: Liferay.Language.get('autogenerate-id'),
						contentXSD: '',
						dialogDescription: instance.getById('saveStructureStructureDescription'),
						dialogStructureId: instance.getById('saveStructureStructureId'),
						dialogStructureName: instance.getById('saveStructureStructureName'),
						idInput: instance.getById('saveStructureStructureId'),
						loadDefaultStructure: instance.getById('loadDefaultStructure'),
						messageElement: instance.getById('saveStructureMessage'),
						saveStructureAutogenerateId: instance.getById('saveStructureAutogenerateId'),
						showStructureIdContainer: instance.getById('showStructureIdContainer'),
						structureIdContainer: instance.getById('structureIdContainer'),
						structureNameLabel: instance.getById('structureNameLabel')
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

							instance.showMessage(
								dialogFields.messageElement,
								'success',
								Liferay.Language.get('your-request-processed-successfully')
							);

							var structureMessage = instance.getById('structureMessage');

							structureMessage.hide();
						}
						else {
							var errorMessage = instance._translateErrorMessage(exception);

							instance.showMessage(
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

			getSelectedField: function() {
				var instance = this;

				var selected = null;
				var fields = instance.getFields();

				if (fields) {
					selected = fields.filter('.selected');
				}

				return selected ? selected.item(0) : null;
			},

			getStructureXSD: function() {
				var instance = this;

				var buffer = [];
				var structureTreeId = instance._guid('#structureTree');
				var sourceRoots = A.all(structureTreeId + ' > li.structure-field:not(.repeated-field)');

				var root = instance._createDynamicNode('root');

				buffer.push(root.openTag);

				A.each(
					sourceRoots,
					function(item, index, collection) {
						instance._appendStructureTypeElementAndMetaData(item, buffer);
					}
				);

				buffer.push(root.closeTag);

				return buffer.join('');
			},

			getTextAreaFields: function() {
				var instance = this;

				var structureTreeId = instance._guid('#structureTree');

				return A.all(structureTreeId + ' li[dataType=text_area] div.journal-article-component-container');
			},

			getPrincipalFieldElement: function(source) {
				var instance = this;

				var componentContainer = source.one('div.journal-article-component-container');

				return componentContainer.one('.principal-field-element');
			},

			getPrincipalForm: function(formName) {
				var instance = this;

				return A.one('form[name=' + instance.portletNamespace + (formName || 'fm1') + ']');
			},

			getByName: function(currentForm, name, withoutNamespace) {
				var instance = this;

				var inputName = withoutNamespace ? name : instance.portletNamespace + name;

				return A.get(currentForm).one('[name=' + inputName + ']');
			},

			getNodeTypeContent: function() {
				var instance = this;

				return instance.hasStructure() ? 'dynamic-content' : 'static-content';
			},

			hasStructure: function() {
				var instance = this;

				return !!instance.getById('structureId').val();
			},

			hasTemplate: function() {
				var instance = this;
				var form = instance.getPrincipalForm();

				return !!instance.getByName(form, 'templateId').val();
			},

			helperIntersecting: function() {
				var instance = this;

				instance._helper.removeClass('not-intersecting');
			},

			helperNotIntersecting: function(helper) {
				var instance = this;

				instance._helper.addClass('not-intersecting');
			},

			hideEditContainerMessage: function() {
				var instance = this;

				var selector = instance._guid('journalMessage');

				A.one(selector).hide();
			},

			loadDefaultStructure: function() {
				var instance = this;

				var form = instance.getPrincipalForm();

				var structureIdInput = instance.getByName(form, 'structureId');
				var templateIdInput = instance.getByName(form, 'templateId');
				var contentInput = instance.getByName(form, 'content');

				structureIdInput.val('');
				templateIdInput.val('');
				contentInput.val('');

				form.submit();
			},

			loadEditFieldOptions: function(source) {
				var instance = this;

				var id = source.get('id');
				var fieldInstance = fieldsDataSet.item(id);

				var check = function(checked) {
					return checked ? 'checked' : '';
				};

				if (fieldInstance) {
					var editContainerWrapper = instance.getById('#journalArticleEditFieldWrapper');
					var displayAsTooltip = instance.getById('displayAsTooltip');
					var repeatable = instance.getById('repeatable');
					var fieldType = instance.getById('fieldType');
					var localizedCheckbox = instance.getById('localized');
					var instructions = instance.getById('instructions');
					var predefinedValue = instance.getById('predefinedValue');
					var required = instance.getById('required');
					var variableName = instance.getById('variableName');
					var fieldLabel = instance.getById('fieldLabel');

					var fieldTypeEl = fieldType.one('[value="' + fieldInstance.get('fieldType') + '"]');

					if (fieldTypeEl) {
						fieldTypeEl.attr('selected', 'selected');
					}

					displayAsTooltip.attr('checked', check(fieldInstance.get('displayAsTooltip')));
					repeatable.attr('checked', check(fieldInstance.get('repeatable')));
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

				if (Lang.isUndefined(value)) {
					value = '';
				}

				return value;
			},

			openPopupWindow: function(url, title) {
				var popup = window.open(url, title, 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680');

				popup.focus();
			},

			openSaveStructureDialog: function() {
				var instance = this;

				var form = instance.getPrincipalForm();

				var structureIdInput = instance.getByName(form, 'structureId');
				var structureNameInput = instance.getByName(form, 'structureName');
				var structureDescriptionInput = instance.getByName(form, 'structureDescription');

				var structureId = structureIdInput.val();
				var structureName = structureNameInput.val();

				instance.getSaveDialog(
					function(dialog) {
						var dialogFields = dialog.fields;

						dialogFields.contentXSD = instance.getStructureXSD();

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

			previewArticle: function() {
				var instance = this;

				var form = instance.getPrincipalForm();
				var auxForm = instance.getPrincipalForm('fm2');
				var articleContent = instance.getArticleContentXML();

				if (instance.structureChange()) {
					if (confirm(Liferay.Language.get('you-should-save-the-structure-first'))) {
						instance.openSaveStructureDialog();
					}
				}
				else {
					var languageIdInput = instance.getByName(form, 'languageId');
					var typeInput = instance.getByName(form, 'type');
					var versionInput = instance.getByName(form, 'version');
					var structureIdInput = instance.getByName(form, 'structureId');
					var templateIdInput = instance.getByName(form, 'templateId');

					var previewURL = themeDisplay.getPathMain() + '/journal/view_article_content?cmd=preview&groupId=' + themeDisplay.getScopeGroupId() + '&articleId=' + instance.articleId + '&version=' + versionInput.val() + '&languageId=' + languageIdInput.val() + '&type=' + typeInput.val() + '&structureId=' + structureIdInput.val() + '&templateId=' + templateIdInput.val();

					auxForm.attr('action', previewURL);
					auxForm.attr('target', '_blank');

					var titleInput = instance.getByName(form, 'title');
					var titleAuxFormInput = instance.getByName(auxForm, 'title', true);
					var xmlAuxFormInput = instance.getByName(auxForm, 'xml', true);

					titleAuxFormInput.val(titleInput.val());
					xmlAuxFormInput.val(articleContent);

					auxForm.submit();
				}
			},

			renderEditFieldOptions: function(source) {
				var instance = this;

				var editButton = instance.getEditButton(source);
				var fields = instance.getFields();

				instance.editContainerNormalMode();

				fields.removeClass('selected');
				source.addClass('selected');

				if (instance._lastEditContainerTrigger != editButton) {
					instance.editContainerContextPanel.set('trigger', editButton);
					instance.editContainerContextPanel.show();

					instance._lastEditContainerTrigger = editButton;
				}

				instance.hideEditContainerMessage();
				instance.loadEditFieldOptions(source);

				instance.editContainerContextPanel.refreshAlign();
			},

			repeatField: function(source) {
				var instance = this;

				var id = source.get('id');
				var fieldInstance = fieldsDataSet.item(id).clone();

				if (fieldInstance.get('fieldType') == 'text_area') {
					var html = instance.buildHTMLEditor(fieldInstance);

					fieldInstance.set('innerHTML', html);
				}

				var htmlTemplate = instance._createFieldHTMLTemplate(fieldInstance);
				var newComponent = A.Node.create(htmlTemplate);
				var newId = newComponent.guid();
				var instanceId = instance.generateInstanceId();

				fieldInstance.set('instanceId', instanceId);

				fieldsDataSet.add(newId, fieldInstance);

				fieldInstance.set('source', newComponent);
				source.placeAfter(newComponent);

				newComponent.addClass('repeated-field');

				instance.closeEditFieldOptions();

				instance.loadEditFieldOptions(newComponent);
				instance.saveEditFieldOptions(newComponent);

				instance.createNestedList(
					newComponent,
					instance.nestedListOptions,
					instance.nestedListEvents
				);

				instance._attachEvents();
			},

			repositionEditFieldOptions: function() {
				var instance = this;

				var editContainerWrapper = instance.getById('#journalArticleEditFieldWrapper');

				var isVisible = !editContainerWrapper.ancestor('.aui-contextpanel-hidden');

				if (isVisible) {
					setTimeout(
						function() {
							var lastSelectedField = instance.getSelectedField();
							instance.renderEditFieldOptions(lastSelectedField);
						},
						200
					);
				}
			},

			saveArticle: function(cmd, saveAndContinue) {
				var instance = this;

				var form = instance.getPrincipalForm();

				if (instance.structureChange()) {
					if (confirm(Liferay.Language.get('you-should-save-the-structure-first'))) {
						instance.openSaveStructureDialog();
					}
				}
				else if (instance.hasStructure() && !instance.hasTemplate()) {
					var templateMessage = Liferay.Language.get('please-add-a-template-to-render-this-structure');

					alert(templateMessage);

					instance.showMessage(
						'#selectTemplateMessage',
						'info',
						templateMessage,
						30000
					);

					A.one('#selectTemplateBtn').focus();
				}
				else {
					if (!cmd) {
						cmd = instance.articleId ? 'update' : 'add';
					}

					var cmdInput = instance.getByName(form, 'cmd');
					var newArticleIdInput = instance.getByName(form, 'newArticleId');
					var articleIdInput = instance.getByName(form, 'articleId');
					var contentInput = instance.getByName(form, 'content');
					var approveInput = instance.getByName(form, 'approve');
					var saveAndContinueInput = instance.getByName(form, 'saveAndContinue');

					var canSubmit = instance.validadeRequiredFields();

					if (canSubmit) {
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

						var content = instance.getArticleContentXML();

						contentInput.val(content);

						form.submit();
					}
				}
			},

			saveEditFieldOptions: function(source) {
				var instance = this;

				var id = source.get('id');
				var fieldInstance = fieldsDataSet.item(id);

				if (fieldInstance) {
					var displayAsTooltip = instance.getById('displayAsTooltip');
					var repeatable = instance.getById('repeatable');
					var fieldType = instance.getById('fieldType');
					var localized = instance.getById('localized');
					var instructions = instance.getById('instructions');
					var predefinedValue = instance.getById('predefinedValue');
					var required = instance.getById('required');
					var variableName = instance.getById('variableName');
					var fieldLabel = instance.getById('fieldLabel');
					var localizedCheckbox = instance.getById('localized');
					var localized = source.one('.journal-article-localized');

					if (localized) {
						var localizedValue = localized.val();
					}

					var variableNameValue = variableName.val();
					var canSave = true;
					var sourceRepeated = source.hasClass('repeated-field');

					instance.getFields().each(
						function(item, index, collection) {
							var id = item.get('id');
							var fieldInstanceValidation = fieldsDataSet.item(id);

							if (!item.hasClass('selected') &&
								fieldInstanceValidation &&
								!sourceRepeated &&
								!item.hasClass('repeated-field')) {

								var validationVariableNameValue = fieldInstanceValidation.get('variableName');

								if (variableNameValue.toLowerCase() == validationVariableNameValue.toLowerCase()) {
									canSave = false;
								}
							}
						}
					);

					var journalMessage = instance.getById('journalMessage');

					if (canSave) {
						A.each(
							{
								displayAsTooltip: displayAsTooltip.attr('checked'),
								fieldType: fieldType.val(),
								instructions: instructions.val(),
								localized: localizedCheckbox ? localizedCheckbox.attr('checked') : false,
								localizedValue: localizedValue,
								predefinedValue: predefinedValue.val(),
								repeatable: repeatable.attr('checked'),
								required: required.attr('checked')
							},
							function(item, index, collection) {
								fieldInstance.set(index, item);
							}
						);

						instance.updateFieldVariableName(fieldInstance, variableNameValue);
						instance.updateFieldLabelName(fieldInstance, fieldLabel.val());

						instance.showMessage(
							journalMessage,
							'success',
							Liferay.Language.get('your-request-processed-successfully')
						);

						instance.editContainerNormalMode();
					}
					else {
						variableName.focus();
						instance.showMessage(
							journalMessage,
							'error',
							Liferay.Language.get('duplicated-variable-name')
						);
					}
				}
			},

			showMessage: function(selector, type, message, delay) {
				var instance = this;

				var journalMessage = selector;

				if (!selector) {
					journalMessage = instance._guid('journalMessage');
				}

				var journalMessage = A.one(selector);
				var className = 'portlet-msg-' + (type || 'success');

				journalMessage.removeClass().addClass(className);
				journalMessage.show();

				instance.editContainerContextPanel.refreshAlign();

				if (message) {
					journalMessage.html(message);
				}

				instance.timers[selector] = A.later(
					delay || 5000,
					instance,
					function() {
						journalMessage.hide();

						instance.editContainerContextPanel.refreshAlign();
					}
				);
			},

			structureChange: function(attribute) {
				var instance = this;

				var form = instance.getPrincipalForm();
				var storedStructureXSD = instance.getByName(form, 'structureXSD').val();

				var hasChanged = (storedStructureXSD != encodeURIComponent(instance.getStructureXSD()));

				return hasChanged;
			},

			unselectFields: function() {
				var instance = this;

				var selected = instance.getSelectedField();

				if (selected) {
					selected.removeClass('selected');
				}
			},

			updateFieldLabelName: function(fieldInstance, fieldLabel) {
				var instance = this;

				var repeatedSiblings = instance.getRepeatedSiblings(fieldInstance);

				repeatedSiblings.each(
					function(item, index, collection) {
						var id = item.get('id');
						var repeatedFieldInstance = fieldsDataSet.item(id);

						repeatedFieldInstance.set('fieldLabel', fieldLabel);
					}
				);

				fieldInstance.set('fieldLabel', fieldLabel);
			},

			updateFieldVariableName: function(fieldInstance, variableName) {
				var instance = this;

				var repeatedSiblings = instance.getRepeatedSiblings(fieldInstance);

				repeatedSiblings.each(
					function(item, index, collection) {
						var id = item.get('id');
						var repeatedFieldInstance = fieldsDataSet.item(id);

						repeatedFieldInstance.set('variableName', variableName);
					}
				);

				fieldInstance.set('variableName', variableName);
			},

			updateStructure: function(parentStructureId, structureId, name, description, xsd, callback) {
				var instance = this;

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
						serviceContext: A.JSON.stringify(
							{
								scopeGroupId: themeDisplay.getScopeGroupId()
							}
						),
						serviceParameterTypes: A.JSON.stringify(serviceParameterTypes)
					},
					function(message) {
						if (Lang.isFunction(callback)) {
							callback(message);
						}
					}
				);
			},

			updateTextAreaVisibility: function(visibility) {
				var instance = this;

				var textAreaFields = instance.getTextAreaFields();

				if (textAreaFields) {
					textAreaFields.setStyle('visibility', visibility);
				}
			},

			validadeRequiredFields: function() {
				var instance = this;

				var canSubmit = true;
				var structureTreeId = instance._guid('#structureTree');
				var fields = A.all(structureTreeId + ' li');
				var requiredFields = fields.filter('[dataRequired=true]');
				var fieldsConatainer = A.all(structureTreeId + ' li .field-container');
				var firstEmptyField = null;

				fieldsConatainer.removeClass('required-field');

				A.each(
					requiredFields,
					function(item, index, collection) {
						var id = item.get('id');
						var fieldInstance = fieldsDataSet.item(id);
						var content = fieldInstance.getContent(item);

						if (!content) {
							var fieldConatainer = item.one('.field-container');

							fieldConatainer.addClass('required-field');

							if (canSubmit) {
								firstEmptyField = instance.getPrincipalFieldElement(item);
							}

							canSubmit = false;
						}
					}
				);

				if (firstEmptyField) {
					firstEmptyField.focus();
				}

				return canSubmit;
			},

			_attachEvents: function() {
				var instance = this;

				var closeButtons = instance.getCloseButtons();
				var editButtons = instance.getEditButtons();
				var repeatableButtons = instance.getRepeatableButtons();
				var defaultLanguageIdSelect = instance.getById('defaultLanguageIdSelect');
				var downloadArticleContentBtn = instance.getById('downloadArticleContentBtn');
				var languageIdSelect = instance.getById('languageIdSelect');
				var previewArticleBtn = instance.getById('previewArticleBtn');
				var saveArticleAndApproveBtn = instance.getById('saveArticleAndApproveBtn');
				var saveArticleAndContinueBtn = instance.getById('saveArticleAndContinueBtn');
				var saveArticleBtn = instance.getById('saveArticleBtn');
				var variableName = instance.getById('variableName');

				closeButtons.detach('click');
				defaultLanguageIdSelect.detach('change');
				editButtons.detach('click');
				languageIdSelect.detach('change');
				repeatableButtons.detach('click');

				if (saveArticleAndApproveBtn) {
					saveArticleAndApproveBtn.detach('click');
				}

				saveArticleAndContinueBtn.detach('click');
				saveArticleBtn.detach('click');

				editButtons.on(
					'click',
					function(event) {
						var source = event.target.ancestor('li').item(0);

						instance.renderEditFieldOptions(source);
					}
				);

				repeatableButtons.on(
					'click',
					function(event) {
						var source = event.target.ancestor('li').item(0);

						instance.repeatField(source);
					}
				);

				closeButtons.on(
					'click',
					function(event) {
						var source = event.target.ancestor('li').item(0);

						instance.closeField(source);
					}
				);

				saveArticleBtn.on(
					'click',
					function() {
						instance.saveArticle();
					}
				);

				if (saveArticleAndApproveBtn) {
					saveArticleAndApproveBtn.on(
						'click',
						function() {
							instance.saveArticle('approve');
						}
					);
				}

				saveArticleAndContinueBtn.on(
					'click',
					function() {
						var saveAndContinue = true;

						instance.saveArticle(null, saveAndContinue);
					}
				);

				if (downloadArticleContentBtn) {
					downloadArticleContentBtn.detach('click');

					downloadArticleContentBtn.on(
						'click',
						function() {
							instance.downloadArticleContent();
						}
					);
				}

				if (previewArticleBtn) {
					previewArticleBtn.detach('click');

					previewArticleBtn.on(
						'click',
						function() {
							instance.previewArticle();
						}
					);
				}

				languageIdSelect.on(
					'change',
					function() {
						instance.changeLanguageView();
					}
				);

				defaultLanguageIdSelect.on(
					'change',
					function() {
						instance.changeLanguageView();
					}
				);

				var changeStructureBtn = instance.getById('changeStructureBtn');
				var changeTemplateBtn = instance.getById('changeTemplateBtn');
				var editStructureBtn = instance.getById('editStructureBtn');
				var loadDefaultStructureBtn = instance.getById('loadDefaultStructure');
				var saveStructureBtn = instance.getById('saveStructureBtn');
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
							instance.openPopupWindow(url, 'ChangeStructure');
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
								instance.openPopupWindow(url, 'ChangeTemplate');
							}
						}
					);
				}

				if (loadDefaultStructureBtn) {
					loadDefaultStructureBtn.detach('click');

					loadDefaultStructureBtn.on(
						'click',
						function() {
							instance.loadDefaultStructure();
						}
					);
				}

				saveStructureBtn.on(
					'click',
					function() {
						instance.openSaveStructureDialog();
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
						var fieldsContainer = instance.getById('#journalArticleContainer');

						if (fieldsContainer.hasClass('journal-edit-mode')) {
							instance.disableEditMode();
						}
						else {
							instance.enableEditMode();
						}
					}
				);
			},

			_attachEditContainerEvents: function(attribute) {
				var instance = this;

				var editContainerWrapper = instance.getById('#journalArticleEditFieldWrapper');
				var editContainerCheckboxes = editContainerWrapper.all('input[type=checkbox]');
				var editContainerInputs = editContainerWrapper.all('input[type=text]');
				var editContainerTextareas = editContainerWrapper.all('textarea');
				var editFieldCancelButton = editContainerWrapper.one('.cancel-button');
				var editFieldCloseButton = editContainerWrapper.one('.close-button');
				var editFieldSaveButton = editContainerWrapper.one('.save-button');
				var languageIdSelect = instance.getById('languageIdSelect');
				var localizedCheckbox = instance.getById('localized');

				editContainerCheckboxes.detach('click');
				editContainerInputs.detach('change');
				editContainerInputs.detach('keypress');
				editContainerTextareas.detach('change');
				editContainerTextareas.detach('keypress');
				editFieldCancelButton.detach('click');
				editFieldCloseButton.detach('click');
				editFieldSaveButton.detach('click');

				var editContainerSaveMode = instance.editContainerSaveMode;

				editContainerCheckboxes.on('click', editContainerSaveMode, instance);
				editContainerInputs.on('change', editContainerSaveMode, instance);
				editContainerInputs.on('keypress', editContainerSaveMode, instance);
				editContainerTextareas.on('change', editContainerSaveMode, instance);
				editContainerTextareas.on('keypress', editContainerSaveMode, instance);

				editFieldSaveButton.on(
					'click',
					function() {
						var source = instance.getSelectedField();

						instance.saveEditFieldOptions(source);
					}
				);

				var closeEditField = instance.closeEditFieldOptions;

				editFieldCancelButton.on('click', closeEditField, instance);
				editFieldCloseButton.on('click', closeEditField, instance);

				localizedCheckbox.on(
					'click',
					function(event) {
						var source = instance.getSelectedField();
						var defaultLocale = instance.getDefaultLocale();
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

			_attachDelegatedEvents: function() {
				var instance = this;

				var journalArticleContainerId = instance._guid('#journalArticleContainer')

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
							function(item, index, collection) {
								if (item.text().toLowerCase() == key.toLowerCase()) {
									item.remove();
								}
							}
						);

						var option = A.Node.create(TPL_OPTION).val(value).text(key);

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
						function(item, index, collection) {
							if (item.attr('selected')) {
								item.remove();
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

						instance.repeatField(source);
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

							instance.openPopupWindow(imageGalleryUrl, title);
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

			_appendStructureChildren: function(source, buffer, generateArticleContent) {
				var instance = this;

				var selector = '> span.folder > ul > li';

				if (!generateArticleContent) {
					selector += '.structure-field:not(.repeated-field)';
				}

				var children = source.all(selector);

				A.each(
					children,
					function(item, index, collection) {
						instance._appendStructureTypeElementAndMetaData(item, buffer, generateArticleContent);
					}
				);
			},

			_appendStructureTypeElementAndMetaData: function(source, buffer, generateArticleContent) {
				var instance = this;

				var id = source.get('id');
				var fieldInstance = fieldsDataSet.item(id);

				if (fieldInstance) {
					var typeElement;
					var type = fieldInstance.get('fieldType');
					var indexType = fieldInstance.get('indexType');

					if (generateArticleContent) {
						var instanceId = fieldInstance.get('instanceId');

						if (!instanceId) {
							instanceId = instance.generateInstanceId();
							fieldInstance.set('instanceId', instanceId);
						}

						typeElement = instance._createDynamicNode(
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
						typeElement = instance._createDynamicNode(
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

					var nodeTypeContent = instance.getNodeTypeContent();
					var typeContent = instance._createDynamicNode(nodeTypeContent, dynConAttributes);
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

					buffer.push(typeElement.openTag);

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
						buffer.push(typeContent.openTag);

						var appendOptions = (type == 'multi-list' || type == 'list');

						if (appendOptions) {
							instance._appendStructureFieldOptionsBuffer(source, buffer, generateArticleContent);
						}
						else {
							var content = fieldInstance.getContent(source) || '';

							buffer.push('<![CDATA[' + content + ']]>')
						}

						buffer.push(typeContent.closeTag);
					}

					buffer.push(typeElement.closeTag);
				}
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
						function(item, index, collection) {
							var optionKey = instance._formatOptionsKey(item.text());
							var optionValue = item.val();

							if (!generateArticleContent) {
								var typeElementOption = instance._createDynamicNode(
									'dynamic-element',
									{
										name: optionKey,
										type: optionValue,
										'repeatable': fieldInstance.get('repeatable')
									}
								);

								buffer.push(typeElementOption.openTag + typeElementOption.closeTag);
							}
							else {
								if (item.get('selected')) {
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

			_createDynamicNode: function(nodeName, attributeMap) {
				var instance = this;

				var attrs = [];
				var typeElement = [];

				if (!nodeName) {
					nodeName = 'dynamic-element';
				}

				var typeElementModel = ['<', nodeName, (attributeMap ? ' ' : ''), , '>', ,'</', nodeName, '>'];

				A.each(
					attributeMap || {},
					function(item, index, collection) {
						if (item !== undefined) {
							attrs.push([index, '="', item, '" '].join(''));
						}
					}
				);

				typeElementModel[3] = attrs.join('').replace(/[\s]+$/g, '');
				typeElement = typeElementModel.join('').replace(/></, '>><<').replace(/ +>/, '>').split(/></);

				return {
					closeTag: typeElement[1],
					openTag: typeElement[0]
				};
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

			_dropField: function() {
				var instance = this;

				instance.repositionEditFieldOptions();
			},

			_emptyFunction: function() {
				return '';
			},

			_fieldInstanceFactory: function(options) {
				var instance = this;

				var type;

				if (Lang.isString(options)) {
					type = options;
					options = null;
				}
				else {
					type = options.fieldType;
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

				options = A.merge(model[type], options);

				var fieldInstance = new Journal.StructureField(
					options,
					instance.portletNamespace
				);

				fieldInstance.get('fieldLabel');

				return fieldInstance;
			},

			_formatOptionsKey: function(s) {
				return s.replace(/\W+/g, ' ').replace(/^\W+|\W+$/g, '').replace(/ /g, '_');
			},

			_getUID: function() {
				var instance = this;

				return parseInt(Math.random() * Math.pow(10, 4), 10);
			},

			_guid: function(id, namespace, prefix) {
				var instance = this;

				if (!Lang.isString(namespace)) {
					namespace = instance.portletNamespace;
				}

				if (!Lang.isString(prefix)) {
					prefix = '#';
				}

				id = id.replace(/^#/, '');

				return prefix + namespace + id;
			},

			_initializePageLoadFieldInstances: function() {
				var instance = this;

				var fields = instance.getFields();

				fields.each(
					function(item, index, collection) {
						var id = item.get('id');
						var fieldInstance = fieldsDataSet.item(id);

						if (!fieldInstance) {
							var componentName = item.attr('dataName');
							var componentType = item.attr('dataType');
							var displayAsTooltip = item.attr('dataDisplayAsTooltip');
							var fieldLabel = item.attr('dataLabel');
							var indexType = item.attr('dataIndexType');
							var instanceId = item.attr('dataInstanceId');
							var instructions = item.attr('dataInstructions');
							var localized = item.one('.journal-article-localized');
							var parentValue = item.attr('dataParentStructureId');
							var predefinedValue = item.attr('dataPredefinedValue');
							var repeatable = item.attr('dataRepeatable');
							var required = item.attr('dataRequired');

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
									source: item,
									fieldType: componentType,
									indexType: indexType,
									variableName: componentName
								}
							);
						}

						var id = item.get('id');

						fieldsDataSet.add(id, fieldInstance);
					}
				);
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

			_updateOriginalContent: function() {
				var instance = this;
				var form = instance.getPrincipalForm();

				var originalContent = encodeURIComponent(instance.getArticleContentXML());
				var originalContentInput = instance.getByName(form, 'originalContent');

				originalContentInput.val(originalContent);
			},

			_updateOriginalStructureXSD: function() {
				var instance = this;
				var form = instance.getPrincipalForm();

				var currentXSD = encodeURIComponent(instance.getStructureXSD());
				var structureXSDInput = instance.getByName(form, 'structureXSD');

				structureXSDInput.val(currentXSD);
			}
		};

		var	STRUCTURE_FIELD = 'structurefield';

		function StructureField(config, portletNamespace) {
			var instance = this;

			instance._lazyAddAttrs = false;

			instance.portletNamespace = portletNamespace;

			StructureField.superclass.constructor.apply(this, arguments);
		}

		A.mix(
			StructureField,
			{
				NAME: STRUCTURE_FIELD,

				ATTRS: {
					source: {
						value: null
					},

					content: {
						validator: Lang.isString,
						value: ''
					},

					displayAsTooltip: {
						setter: function(v) {
							var instance = this;

							return instance.setAttribute('displayAsTooltip', v);
						},
						valueFn: function() {
							var instance = this;

							return instance.getAttribute('displayAsTooltip', true);
						}
					},

					fieldLabel: {
						setter: function(v) {
							var instance = this;

							return instance.setFieldLabel(v);
						},
						valueFn: function() {
							var instance = this;

							return instance.getAttribute('fieldLabel', '');
						}
					},

					fieldType: {
						setter: function(v) {
							var instance = this;

							return instance.setAttribute('fieldType', v);
						},
						validator: Lang.isString,
						value: ''
					},

					localized: {
						valueFn: function() {
							var instance = this;

							var localizedValue = instance.getLocalizedValue();

							return (String(localizedValue) == 'true');
						}
					},

					localizedValue: {
						valueFn: function() {
							var instance = this;

							return instance.getLocalizedValue();
						}
					},

					innerHTML: {
						validator: Lang.isString,
						value: TPL_STRUCTURE_FIELD_INPUT
					},

					instructions: {
						setter: function(v) {
							var instance = this;

							return instance.setInstructions(v);
						},
						valueFn: function() {
							var instance = this;

							return instance.getAttribute('instructions', '');
						}
					},

					instanceId: {
						setter: function(v) {
							var instance = this;

							return instance.setInstanceId(v);
						},
						valueFn: function() {
							var instance = this;

							return instance.getAttribute('instanceId', '');
						}
					},

					parentStructureId: {
						setter: function(v) {
							var instance = this;

							return instance.setAttribute('parentStructureId', v);
						},
						valueFn: function() {
							var instance = this;

							return instance.getAttribute('parentStructureId', '');
						}
					},

					predefinedValue: {
						setter: function(v) {
							var instance = this;

							return instance.setAttribute('predefinedValue', v);
						},
						valueFn: function() {
							var instance = this;

							return instance.getAttribute('predefinedValue', '');
						}
					},

					repeatable: {
						setter: function(v) {
							var instance = this;

							return instance.setRepeatable(v);
						},
						valueFn: function() {
							var instance = this;

							return instance.getAttribute('repeatable', false);
						}
					},

					required: {
						setter: function(v) {
							var instance = this;

							return instance.setAttribute('required', v);
						},
						valueFn: function() {
							var instance = this;

							return instance.getAttribute('required', false);
						}
					},

					variableName: {
						setter: function(v) {
							var instance = this;

							return instance.setVariableName(v);
						},
						validator: Lang.isString,
						valueFn: function() {
							var instance = this;

							return instance.getAttribute('name');
						}
					}
				}
			}
		);

		A.extend(
			StructureField,
			A.Base,
			{
				cloneableAttrs: [
					'displayAsTooltip',
					'fieldLabel',
					'fieldType',
					'innerHTML',
					'instructions',
					'localized',
					'localizedValue',
					'predefinedValue',
					'repeatable',
					'required',
					'variableName'
				],

				initializer: function() {
					var instance = this;

					var propagateAttr = instance.propagateAttr;

					A.each(
						instance.cloneableAttrs,
						function(item, index, collection) {
							instance.after(item + 'Change', propagateAttr);
						}
					);
				},

				clone: function() {
					var instance = this;

					var options = {};
					var portletNamespace = instance.portletNamespace;

					A.each(
						instance.cloneableAttrs,
						function(item, index, collection) {
							options[item] = instance.get(item);
						}
					);

					options.source = null;

					return new StructureField(options, portletNamespace);
				},

				createInstructionsContainer: function(value) {
					return A.Node.create(TPL_INSTRUCTIONS_CONTAINER).html(value);
				},

				createTooltipImage: function() {
					return A.Node.create(TPL_TOOLTIP_IMAGE);
				},

				getAttribute: function(key, defaultValue) {
					var instance = this;

					var value = undefined;
					var source = instance.get('source');

					if (source) {
						value = source.attr('data' + key);
					}

					if (Lang.isUndefined(value) && !Lang.isUndefined(defaultValue)) {
						value = defaultValue;
					}

					return value;
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

						if (editorReference && Lang.isFunction(editorReference.getHTML)) {
							content = editorReference.getHTML();
						}
					}
					else if (type == 'multi-list') {
						var output = [];
						var options = principalElement.all('option');

						options.each(
							function(item, index, collection) {
								if (item.get('selected')) {
									var value = item.val();

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
						var fieldLabel = Liferay.Language.get('field');
						var editOptionsLanguage = Liferay.Language.get('edit-options');
						var requiredFieldLanguage = Liferay.Language.get('this-field-is-required');

						var repeatableBtnTemplate = instance.getById('repeatableBtnTemplate');
						var repeatableBtnTemplateHTML = '';

						if (repeatableBtnTemplate) {
							repeatableBtnTemplateHTML = repeatableBtnTemplate.html();
						}

						htmlTemplate = A.substitute(
							TPL_FIELD_CONTAINER,
							{
								requiredFieldLanguage: requiredFieldLanguage,
								editOptionsLanguage: editOptionsLanguage,
								fieldLabel: fieldLabel,
								repeatableBtnTemplateHTML: repeatableBtnTemplateHTML
							}
						);

						instance.fieldContainer = A.Node.create(htmlTemplate);

						var source = instance.fieldContainer.one('li');
						var fieldType = instance.get('fieldType');
						var required = instance.get('required');
						var variableName = instance.get('variableName');

						source.attr('dataName', variableName);
						source.attr('dataRequired', required);
						source.attr('dataType', fieldType);
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

					var source = instance.get('source');

					if (!source) {
						source = instance.getFieldContainer().one('li');
					}

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

				getRepeatedSiblings: function() {
					var instance = this;

					return Journal.prototype.getRepeatedSiblings.apply(instance, [instance]);
				},

				propagateAttr: function(event) {
					var instance = this;

					var siblings = instance.getRepeatedSiblings();

					if (siblings) {
						siblings.each(
							function(item, index, collection) {
								var id = item.get('id');
								var fieldInstance = fieldsDataSet.item(id);

								if (fieldInstance) {
									fieldInstance.set(event.attrName, event.newVal);
								}
							}
						);
					}
				},

				setFieldLabel: function(value) {
					var instance = this;

					var fieldLabel = instance.getFieldLabelElement();

					if (!value) {
						value = instance.get('variableName');
					}

					fieldLabel.one('span').html(value);

					instance.setAttribute('fieldLabel', value);
				},

				setInstanceId: function(value) {
					var instance = this;

					instance.setAttribute('instanceId', value);

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

				setInstructions: function(value) {
					var instance = this;

					var source = instance.get('source');

					if (source) {
						var id = source.get('id');
						var fieldInstance = fieldsDataSet.item(id);

						instance.setAttribute('instructions', value);

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
									var instructionsMessage = fieldInstance.createInstructionsContainer(value);
									var requiredMessage = fieldContainer.one('.journal-article-required-message');

									requiredMessage.placeAfter(instructionsMessage);
								}
								else {
									label.append(fieldInstance.createTooltipImage());
								}
							}
						}
					}
				},

				setRepeatable: function(value) {
					var instance = this;

					var source = instance.get('source');

					instance.setAttribute('repeatable', value);

					if (source) {
						var id = source.get('id');
						var fieldInstance = fieldsDataSet.item(id);
						var fieldContainer = source.one('> .folder > .field-container');
						var repeatableFieldImage = fieldContainer.one('.repeatable-field-image');
						var repeatableAddIcon = source.one('.journal-article-buttons .repeatable-button');

						if (repeatableFieldImage) {
							repeatableFieldImage.remove();
						}

						var parentStructureId = instance.get('parentStructureId');

						if (value && !parentStructureId) {
							var repeatableFieldImageModel = A.Node.create(
								A.one('#repeatable-field-image-model').html()
							);

							fieldContainer.append(repeatableFieldImageModel);

							if (repeatableAddIcon) {
								repeatableAddIcon.setStyle('display', 'inline-block').show();
							}
						}
						else {
							if (repeatableAddIcon) {
								repeatableAddIcon.hide();
							}
						}
					}
				},

				setVariableName: function(value) {
					var instance = this;

					var fieldLabel = instance.getFieldLabelElement();

					fieldLabel.attr('for', value);
					fieldLabel.get('parentNode').one('input').attr('id', value);

					instance.setAttribute('name', value);
				},

				setAttribute: function(key, value) {
					var instance = this;

					var source = instance.get('source');

					if (Lang.isArray(value)) {
						value = value[0];
					}

					if (source) {
						source.setAttribute('data' + key, value);
					}

					return value;
				},

				_guid: Journal.prototype._guid,

				getById: Journal.prototype.getById
			}
		);

		Journal.StructureField = StructureField;

		Journal.FieldModel = {};

		var fieldModel = Journal.FieldModel;

		var registerFieldModel = function(namespace, type, variableName) {
			var instance = this;

			var typeEl = A.one('#journalFieldModelContainer div[dataType="'+ type +'"]');

			if (typeEl) {
				var innerHTML = typeEl.html();
			}

			fieldModel[namespace] = {
				fieldType: type,
				variableName: variableName,
				fieldLabel: variableName,
				innerHTML: innerHTML
			};
		};

		registerFieldModel('Text', 'text', 'TextField');
		registerFieldModel('TextArea', 'text_area', 'TextAreaField');
		registerFieldModel('TextBox', 'text_box', 'TextBoxField');
		registerFieldModel('Image', 'image', 'ImageField');
		registerFieldModel('ImageGallery', 'image_gallery', 'ImageGalleryField');
		registerFieldModel('DocumentLibrary', 'document_library', 'DocumentLibraryField');
		registerFieldModel('Boolean', 'boolean', 'BooleanField');
		registerFieldModel('List', 'list', 'ListField');
		registerFieldModel('MultiList', 'multi-list', 'MultiListField');
		registerFieldModel('LinkToPage', 'link_to_layout', 'LinkToPageField');
		registerFieldModel('SelectionBreak', 'selection_break', 'SelectionBreakField');

		Liferay.Portlet.Journal = Journal;
	},
	'',
	{
		requires: [ 'aui-base', 'context-panel', 'data-set', 'dialog', 'json', 'nested-list', 'substitute' ]
	}
);