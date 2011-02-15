AUI().add(
	'liferay-portlet-forms',
	function(A) {
		var Lang = A.Lang;

		var DataType = A.DataType;

		var FormBuilderField = A.FormBuilderField;

		var instanceOf = A.instanceOf;

		var MAP_ATTR_LABEL = {
			name: 'label'
		};

		var MAP_ATTR_PREDEFINED_VALUE = {
			name: 'predefinedValue'
		};

		var MAP_ATTR_REQUIRED = {
			name: 'required'
		};

		var MAP_ATTR_SHOW_LABEL = {
			name: 'showLabel'
		};

		var MAP_ELEMENT_DATA = {
			attributeList: '',
			nodeName: ''
		};

		var STR_CDATA_CLOSE = ']]>';

		var STR_CDATA_OPEN = '<![CDATA[';

		var STR_BLANK = '';

		var STR_SPACE = ' ';

		var TPL_ELEMENT = '<{nodeName}{attributeList}></{nodeName}>';

		var LiferayFormBuilder = A.Component.create(
			{
				ATTRS: {
					availableFields: {
						value: {
							'text': {
								fieldLabel: Liferay.Language.get('text-box'),
								iconClass: 'aui-form-builder-field-icon aui-form-builder-field-icon-text',
								label: Liferay.Language.get('text-box')
							},

							'textarea': {
								fieldLabel: Liferay.Language.get('text-area'),
								iconClass: 'aui-form-builder-field-icon aui-form-builder-field-icon-textarea',
								label: Liferay.Language.get('text-area')
							},

							'checkbox': {
								fieldLabel: Liferay.Language.get('checkbox'),
								iconClass: 'aui-form-builder-field-icon aui-form-builder-field-icon-checkbox',
								label: Liferay.Language.get('checkbox')
							},

							'button': {
								fieldLabel: Liferay.Language.get('button'),
								iconClass: 'aui-form-builder-field-icon aui-form-builder-field-icon-button',
								label: Liferay.Language.get('button')
							},

							'select': {
								fieldLabel: Liferay.Language.get('select-option'),
								iconClass: 'aui-form-builder-field-icon aui-form-builder-field-icon-select',
								label: Liferay.Language.get('select-option')
							},

							'radio': {
								fieldLabel: Liferay.Language.get('radio-buttons'),
								iconClass: 'aui-form-builder-field-icon aui-form-builder-field-icon-radio',
								label: Liferay.Language.get('radio-buttons')
							},

							'fieldset': {
								fieldLabel: Liferay.Language.get('fieldset'),
								iconClass: 'aui-form-builder-field-icon aui-form-builder-field-icon-fieldset',
								label: Liferay.Language.get('fieldset')
							},

							'fileupload': {
								fieldLabel: Liferay.Language.get('file-upload'),
								iconClass: 'aui-form-builder-field-icon aui-form-builder-field-icon-fileupload',
								label: Liferay.Language.get('file-upload')
							}
						},
						validator: Lang.isObject
					},

					portletNamespace: {
						value: STR_BLANK
					},

					portletResourceNamespace: {
						value: STR_BLANK
					},

					strings: {
						value: {
							'stringEmptySelection': Liferay.Language.get('no-field-selected')
						}
					}
				},

				EXTENDS: A.FormBuilder,

				NAME: 'liferayformbuilder',

				prototype: {
					initializer: function() {
						var instance = this;

						instance.addTarget(window.parent.Liferay);
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

						return buffer.join(STR_BLANK);
					},

					normalizeValue: function(value) {
						var instance = this;

						if (Lang.isUndefined(value)) {
							value = STR_BLANK;
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

						var typeElement = instance._createDynamicNode(
							'dynamic-element',
							{
								name: encodeURI(field.get('name')),
								type: field.get('type')
							}
						);

						var metadata = instance._createDynamicNode('meta-data');

						var entryRequired = instance._createDynamicNode('entry', MAP_ATTR_REQUIRED);

						var label = instance._createDynamicNode('entry', MAP_ATTR_LABEL);

						var predefinedValue = instance._createDynamicNode('entry', MAP_ATTR_PREDEFINED_VALUE);

						var showLabel = instance._createDynamicNode('entry', MAP_ATTR_SHOW_LABEL);

						buffer.push(typeElement.openTag);

						instance._appendStructureFieldOptionsBuffer(field, buffer);

						instance._appendStructureChildren(field, buffer, generateArticleContent);

						buffer.push(metadata.openTag);

						var requiredVal = instance.normalizeValue(field.get('required'));

						buffer.push(
							entryRequired.openTag,
							STR_CDATA_OPEN + requiredVal + STR_CDATA_CLOSE,
							entryRequired.closeTag
						);

						var fieldLabelVal = instance.normalizeValue(field.get('label'));

						buffer.push(
							label.openTag,
							STR_CDATA_OPEN + fieldLabelVal + STR_CDATA_CLOSE,
							label.closeTag
						);

						var predefinedValueVal = instance.normalizeValue(field.get('predefinedValue'));

						buffer.push(
							predefinedValue.openTag,
							STR_CDATA_OPEN + predefinedValueVal + STR_CDATA_CLOSE,
							predefinedValue.closeTag
						);

						var showLabelVal = instance.normalizeValue(field.get('showLabel'));

						buffer.push(
							showLabel.openTag,
							STR_CDATA_OPEN + showLabelVal + STR_CDATA_CLOSE,
							showLabel.closeTag
						);

						buffer.push(metadata.closeTag, typeElement.closeTag);
					},

					_createDynamicNode: function(nodeName, attributeMap) {
						var instance = this;

						var attrs = [];
						var typeElement = [];

						if (!nodeName) {
							nodeName = 'dynamic-element';
						}

						MAP_ELEMENT_DATA.attributeList = STR_BLANK;
						MAP_ELEMENT_DATA.nodeName = nodeName;

						if (attributeMap) {
							A.each(
								attributeMap,
								function(item, index, collection) {
									if (item !== undefined) {
										attrs.push([index, '="', item, '" '].join(STR_BLANK));
									}
								}
							);

							MAP_ELEMENT_DATA.attributeList = STR_SPACE + attrs.join(STR_BLANK);
						}

						typeElement = Lang.sub(TPL_ELEMENT, MAP_ELEMENT_DATA);
						typeElement = typeElement.replace(/\s?(>)(<)/, '$1$1$2$2').split(/></);

						return {
							closeTag: typeElement[1],
							openTag: typeElement[0]
						};
					},

					_formatOptionsKey: function(s) {
						return s.replace(/\W+/g, STR_SPACE).replace(/^\W+|\W+$/g, STR_BLANK).replace(/ /g, '_');
					},

					_onClickFieldDelete: function(event) {
						var instance = this;

						var target = event.currentTarget;
						var fields = instance.get('fields');

						var fieldBB = target.ancestor('.aui-form-builder-field');
						var field = fieldBB.getData('field');

						if (field) {
							var parent = field.get('parent');

							if (fields.length > 1 || instanceOf(value, FormBuilderField)) {
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
			}
		);

		Liferay.FormBuilder = LiferayFormBuilder;
	},
	'',
	{
		requires: ['aui-form-builder']
	}
);