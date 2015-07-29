AUI.add(
	'liferay-ddl-form-builder-definition-serializer',
	function(A) {
		var AArray = A.Array;
		var FieldTypes = Liferay.DDM.Renderer.FieldTypes;

		var DefinitionSerializer = A.Component.create(
			{
				ATTRS: {
					fieldHandler: {
						valueFn: '_valueFieldHandler'
					},

					fields: {
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
								availableLanguageIds: ['en_US'],
								defaultLanguageId: 'en_US',
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

						AArray.each(
							fieldType.get('settings').fields,
							function(item, index) {
								config[item.name] = field.get(item.name);
							}
						);

						instance.get('fields').push(
							A.merge(
								config,
								{
									dataType: 'string',
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