AUI.add(
	'liferay-ddl-form-builder-layout-deserializer',
	function(A) {
		var AArray = A.Array;
		var Lang = A.Lang;

		var FieldTypes = Liferay.DDM.Renderer.FieldTypes;
		var FormBuilderUtil = Liferay.DDL.FormBuilderUtil;
		var RendererUtil = Liferay.DDM.Renderer.Util;

		var LayoutDeserializer = A.Component.create(
			{
				ATTRS: {
					definition: {
						validator: function(val) {
							return Lang.isObject(val);
						}
					},

					descriptions: {
						value: []
					},

					layouts: {
						value: []
					},

					titles: {
						value: []
					}
				},

				EXTENDS: A.Base,

				NAME: 'liferay-ddl-form-builder-layout-deserializer',

				prototype: {
					deserialize: function() {
						var instance = this;

						var layouts = instance.get('layouts');

						return instance.deserializePages(layouts);
					},

					deserializeColumn: function(column) {
						var instance = this;

						var deserializedColumn = new A.LayoutCol(
							{
								size: column.size
							}
						);

						if (column.fieldNames && column.fieldNames.length > 0) {
							var fieldsList = new A.FormBuilderFieldList(
								{
									fields: instance.deserializeFields(deserializedColumn, column.fieldNames)
								}
							);

							deserializedColumn.set('value', fieldsList);
						}

						return deserializedColumn;
					},

					deserializeColumns: function(columns) {
						var instance = this;

						return AArray.map(columns, A.bind('deserializeColumn', instance));
					},

					deserializeField: function(deserializedColumn, fieldName) {
						var instance = this;

						var definition = instance.get('definition');

						var fieldDefinition = RendererUtil.searchFieldData(definition, 'name', fieldName);

						var fieldType = FieldTypes.get(fieldDefinition.type);

						var settings = fieldType.get('settings');

						var fieldConfig = {};

						AArray.each(
							settings.fields,
							function(item, index, collection) {
								var value = fieldDefinition[item.name];

								if (value) {
									fieldConfig[item.name] = value;
								}
							}
						);

						deserializedColumn.set('container', deserializedColumn.get('node'));

						fieldConfig.definition = fieldDefinition;
						fieldConfig.fieldType = fieldDefinition.type;
						fieldConfig.form = instance;
						fieldConfig.parent = deserializedColumn;
						fieldConfig.value = '';

						var fieldClassName = fieldType.get('className');

						var fieldClass = FormBuilderUtil.getFieldClass(fieldClassName);

						return (new fieldClass(fieldConfig)).render();
					},

					deserializeFields: function(deserializedColumn, fieldNames) {
						var instance = this;

						return AArray.map(fieldNames, A.bind('deserializeField', instance, deserializedColumn));
					},

					deserializePage: function(page) {
						var instance = this;

						var description = page.description && page.description[themeDisplay.getLanguageId()];

						instance.get('descriptions').push(description);

						var title = page.title && page.title[themeDisplay.getLanguageId()];

						instance.get('titles').push(title);

						return new A.Layout(
							{
								rows: instance.deserializeRows(page.rows)
							}
						);
					},

					deserializePages: function(pages) {
						var instance = this;

						var deserializedPages;

						if (pages.length) {
							deserializedPages = AArray.map(pages, A.bind('deserializePage', instance));
						}
						else {
							deserializedPages = [
								new A.Layout(
									{
										rows: [
											new A.LayoutRow()
										]
									}
								)
							];
						}

						return deserializedPages;
					},

					deserializeRow: function(row) {
						var instance = this;

						return new A.LayoutRow(
							{
								cols: instance.deserializeColumns(row.columns)
							}
						);
					},

					deserializeRows: function(rows) {
						var instance = this;

						return AArray.map(rows, A.bind('deserializeRow', instance));
					}
				}
			}
		);

		Liferay.namespace('DDL').LayoutDeserializer = LayoutDeserializer;
	},
	'',
	{
		requires: ['aui-layout', 'aui-form-builder-field-list', 'liferay-ddl-form-builder-field', 'liferay-ddl-form-builder-util', 'liferay-ddm-form-field-types', 'liferay-ddm-form-renderer-util']
	}
);