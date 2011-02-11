var parentWindow = window.parent;

if (parentWindow) {
	Liferay = parentWindow.Liferay;
}

AUI().add(
	'liferay-portlet-forms',
	function(A) {
		var D = A.DataType;
		var Lang = A.Lang,
			isObject = Lang.isObject,

			isFormBuilderField = function(v) {
				return (v instanceof A.FormBuilderField);
			};

		var LiferayFormBuilder = A.Component.create({

			NAME: 'liferay-form-builder',

			EXTENDS: A.FormBuilder,

			ATTRS: {

				availableFields: {
					value: {
						'text': {
							fieldLabel: 'Text',
							iconClass: 'aui-form-builder-field-icon aui-form-builder-field-icon-text',
							label: Liferay.Language.get('text-box')
						},

						'textarea': {
							fieldLabel: 'Text Area',
							iconClass: 'aui-form-builder-field-icon aui-form-builder-field-icon-textarea',
							label: Liferay.Language.get('text-area')
						},

						'checkbox': {
							fieldLabel: 'Checkbox',
							iconClass: 'aui-form-builder-field-icon aui-form-builder-field-icon-checkbox',
							label: Liferay.Language.get('checkbox')
						},

						'button': {
							fieldLabel: 'Button',
							iconClass: 'aui-form-builder-field-icon aui-form-builder-field-icon-button',
							label: Liferay.Language.get('button')
						},

						'select': {
							fieldLabel: 'Select',
							iconClass: 'aui-form-builder-field-icon aui-form-builder-field-icon-select',
							label: Liferay.Language.get('select-option')
						},

						'radio': {
							fieldLabel: 'Radio',
							iconClass: 'aui-form-builder-field-icon aui-form-builder-field-icon-radio',
							label: Liferay.Language.get('radio-buttons')
						},

						'fieldset': {
							fieldLabel: 'Fieldset',
							iconClass: 'aui-form-builder-field-icon aui-form-builder-field-icon-fieldset',
							label: Liferay.Language.get('fieldset')
						},

						'fileupload': {
							fieldLabel: 'File Upload',
							iconClass: 'aui-form-builder-field-icon aui-form-builder-field-icon-fileupload',
							label: Liferay.Language.get('file-upload')
						}
					},
					validator: isObject
				},

				portletNamespace: {
					value: ''
				},

				portletResourceNamespace: {
					value: ''
				},

				strings: {
					value: {
						'stringEmptySelection': Liferay.Language.get('no-field-selected')
					}
				}
			},

			prototype: {

				initializer: function() {
					var instance = this;

					LiferayFormBuilder.superclass.initializer.apply(instance, arguments);

					instance.addTarget(Liferay.FormBuilderEvents);
				},

				getXSD: function() {
					var instance = this;
					var fields = instance.get('fields');
					var buffer = [];

					var root = instance._createDynamicNode('root');

					buffer.push(root.openTag);

					A.each(
						fields,
						function(item, index, collection) {
							instance._appendStructureTypeElementAndMetaData(item, buffer);
						}
					);

					buffer.push(root.closeTag);
					
					return buffer.join('');
				},

				getNodeTypeContent: function() {
					var instance = this;

					return 'dynamic-content';
				},

				normalizeValue: function(value) {
					var instance = this;

					if (A.Lang.isUndefined(value)) {
						value = '';
					}

					return value;
				},

				_appendStructureChildren: function(field, buffer, generateArticleContent) {
					var instance = this;
					var children = field.get('fields');

					A.each(
						children,
						function(item, index, collection) {
							instance._appendStructureTypeElementAndMetaData(item, buffer, generateArticleContent);
						}
					);
				},

				_appendStructureFieldOptionsBuffer: function(field, buffer, generateArticleContent) {
					var instance = this;

					var type = field.get('fieldType');
					var options = field.get('options');

					if (options) {
						A.each(
							options,
							function(item, index, collection) {
								var optionKey = instance._formatOptionsKey(item.name);
								var optionValue = item.value;
								
								var typeElementOption = instance._createDynamicNode(
									'dynamic-element',
									{
										name: optionKey,
										type: 'option',
										value: optionValue
									}
								);

								buffer.push(typeElementOption.openTag + typeElementOption.closeTag);
							}
						);
					}
				},

				_appendStructureTypeElementAndMetaData: function(field, buffer, generateArticleContent) {
					var instance = this;

					var type = field.get('type');

					var typeElement = instance._createDynamicNode(
						'dynamic-element',
						{
							name: encodeURI(field.get('name')),
							type: type
						}
					);

					var nodeTypeContent = instance.getNodeTypeContent();
					var typeContent = instance._createDynamicNode(nodeTypeContent);
					var metadata = instance._createDynamicNode('meta-data');

					var entryRequired = instance._createDynamicNode(
						'entry',
						{
							name: 'required'
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

					var showLabel = instance._createDynamicNode(
						'entry',
						{
							name: 'showLabel'
						}
					);

					buffer.push(typeElement.openTag);

					instance._appendStructureFieldOptionsBuffer(field, buffer);

					instance._appendStructureChildren(field, buffer, generateArticleContent);

					buffer.push(metadata.openTag);

					var requiredVal = instance.normalizeValue(
						field.get('required')
					);
					buffer.push(entryRequired.openTag);
					buffer.push('<![CDATA[' + requiredVal + ']]>');
					buffer.push(entryRequired.closeTag);

					var fieldLabelVal = instance.normalizeValue(
						field.get('label')
					);
					buffer.push(label.openTag);
					buffer.push('<![CDATA[' + fieldLabelVal + ']]>');
					buffer.push(label.closeTag);

					var predefinedValueVal = instance.normalizeValue(
						field.get('predefinedValue')
					);
					buffer.push(predefinedValue.openTag);
					buffer.push('<![CDATA[' + predefinedValueVal + ']]>');
					buffer.push(predefinedValue.closeTag);

					var showLabelVal = instance.normalizeValue(
						field.get('showLabel')
					);
					buffer.push(showLabel.openTag);
					buffer.push('<![CDATA[' + showLabelVal + ']]>');
					buffer.push(showLabel.closeTag);

					buffer.push(metadata.closeTag);

					buffer.push(typeElement.closeTag);
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

				_formatOptionsKey: function(s) {
					return s.replace(/\W+/g, ' ').replace(/^\W+|\W+$/g, '').replace(/ /g, '_');
				},

				_onClickFieldDelete: function(event) {
					var instance = this;
					var target = event.currentTarget;
					var fields = instance.get('fields');

					var fieldBB = target.ancestor('.aui-form-builder-field');
					var field = fieldBB.getData('field');

					if (field) {
						var parent = field.get('parent');

						if ((fields.length > 1) || isFormBuilderField(parent)) {
							var selectedField = instance.selectedField;

							if (field == selectedField ||
								field.contains(selectedField, true)) {

								instance._tabs.selectTab(0);
							}

							parent.removeField(field);

							field.set('selected', false);
						}
					}
				}
			}

		});

		Liferay.FormBuilder = LiferayFormBuilder;
	},
	'',
	{
		requires: ['aui-form-builder']
	}
);