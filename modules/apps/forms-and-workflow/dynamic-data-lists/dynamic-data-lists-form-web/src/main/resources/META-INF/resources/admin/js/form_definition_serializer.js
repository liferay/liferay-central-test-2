AUI.add(
	'liferay-ddl-form-builder-definition-serializer',
	function(A) {
		var FieldTypes = Liferay.DDM.Renderer.FieldTypes;

		var DefinitionSerializer = A.Component.create(
			{
				ATTRS: {
					availableLanguageIds: {
						value: [
							themeDisplay.getDefaultLanguageId()
						]
					},

					defaultLanguageId: {
						value: themeDisplay.getDefaultLanguageId()
					},

					fieldHandler: {
						valueFn: '_valueFieldHandler'
					},

					fields: {
						validator: Array.isArray,
						value: []
					}
				},

				EXTENDS: Liferay.DDL.LayoutVisitor,

				NAME: 'liferay-ddl-form-builder-definition-serializer',

				prototype: {
					serialize: function() {
						var instance = this;

						instance.visit();

						var definition = A.JSON.stringify(
							{
								availableLanguageIds: instance.get('availableLanguageIds'),
								defaultLanguageId: instance.get('defaultLanguageId'),
								fields: instance.get('fields')
							}
						);

						instance.set('fields', []);

						return definition;
					},

					_serializeField: function(field) {
						var instance = this;

						var config = {};

						var fieldType = FieldTypes.get(field.get('type'));

						fieldType.get('settings').fields.forEach(
							function(item, index) {
								config[item.name] = field.get(item.name);
							}
						);

						instance.get('fields').push(
							A.merge(
								config,
								{
									dataType: field.get('dataType'),
									readOnly: false,
									type: field.get('type')
								}
							)
						);
					},

					_valueFieldHandler: function() {
						var instance = this;

						return instance._serializeField;
					}
				}
			}
		);

		Liferay.namespace('DDL').DefinitionSerializer = DefinitionSerializer;
	},
	'',
	{
		requires: ['json', 'liferay-ddl-form-builder-layout-visitor', 'liferay-ddm-form-field-types']
	}
);