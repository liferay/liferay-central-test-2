AUI().add(
	'liferay-portlet-dynamic-data-lists',
	function(A) {
		var Lang = A.Lang,

		getObjectKeys = A.Object.keys,

		_COMMA = ',',
		_EMPTY = '',
		_EMPTY_FN = function() {};

		var LiferaySpreadSheet = A.Component.create(
			{
				ATTRS: {
					portletNamespace: {
						value: _EMPTY,
						validator: Lang.isString
					},

					recordsetId: {
						value: 0,
						validator: Lang.isNumber
					}
				},

				EXTENDS: A.DataTable.Base,

				NAME: 'liferayspreadsheet',

				prototype: {
					initializer: function() {
						var instance = this;

						var recordset = instance.get('recordset');

						recordset.on('update', A.bind(instance._onRecordUpdate, instance));
					},

					addEmptyRows: function(num) {
						var instance = this;

						var columnset = instance.get('columnset');

						var recordset = instance.get('recordset');

						var emptyRows = LiferaySpreadSheet.buildEmptyRecords(num, getObjectKeys(columnset.keyHash));

						recordset.add(emptyRows);

						instance._uiSetRecordset(recordset);
						
						instance._fixPluginsUI();
					},

					addRecord: function(displayIndex, fieldsMap, callback) {
						var instance = this;

						var recordsetId = instance.get('recordsetId');

						Liferay.Service.DDL.DDLRecord.addRecord(
							{
								recordSetId: recordsetId,
							    fieldsMap: A.JSON.stringify(fieldsMap),
							    displayIndex: displayIndex,
							    serviceContext: A.JSON.stringify(
							        {
							            scopeGroupId: themeDisplay.getScopeGroupId(),
							            userId: themeDisplay.getUserId()
							        }
							    )
							},
							A.bind(callback || _EMPTY_FN, instance)
						);
					},

					updateMinDisplayRows: function(minDisplayRows, callback) {
						var instance = this;

						var recordsetId = instance.get('recordsetId');

						Liferay.Service.DDL.DDLRecordSet.updateMinDisplayRows(
							{
								recordSetId: recordsetId,
							    minDisplayRows: minDisplayRows,
							    serviceContext: A.JSON.stringify(
							        {
							            scopeGroupId: themeDisplay.getScopeGroupId(),
							            userId: themeDisplay.getUserId()
							        }
							    )
							},
							A.bind(callback || _EMPTY_FN, instance)
						);
					},

					updateRecord: function(recordId, displayIndex, fieldsMap, merge, callback) {
						var instance = this;

						Liferay.Service.DDL.DDLRecord.updateRecord(
							{
								recordId: recordId,
							    fieldsMap: A.JSON.stringify(fieldsMap),
							    displayIndex: displayIndex,
							    merge: merge,
							    serviceContext: A.JSON.stringify(
							        {
							            scopeGroupId: themeDisplay.getScopeGroupId(),
							            userId: themeDisplay.getUserId()
							        }
							    )
							},
							A.bind(callback || _EMPTY_FN, instance)
						);
					},

					_normalizeRecordData: function(data) {
						var instance = this;

						var normalized = {};

						A.each(
							data,
							function(value, key) {
								normalized[key] = instance._normalizeValue(value);
							}
						);

						delete normalized.displayIndex;
						delete normalized.recordId;

						return normalized;
					},

					_normalizeValue: function(value) {
						var instance = this;

						if (Lang.isArray(value)) {
							value = value.join(_COMMA);
						}

						return String(value);
					},

					_onRecordUpdate: function(event) {
						var instance = this;

						var recordIndex = event.index;

						A.Array.each(
							event.updated,
							function(record) {
								var data = record.get('data');
								var fieldsMap = instance._normalizeRecordData(data);

								if (data.recordId > 0) {
									instance.updateRecord(data.recordId, recordIndex, fieldsMap, true);
								}
								else {
									instance.addRecord(
										recordIndex,
										fieldsMap,
										function(json) {
											if (json.recordId > 0) {
												data.recordId = json.recordId;
											}
										}
									);
								}
							}
						);
					}
				}
			}
		);

		A.mix(
			LiferaySpreadSheet,
			{
				buildDataTableColumnset: function(columnset, structure) {
					var instance = this;

					A.Array.each(
						columnset,
						function(column) {
							column.key = column.name;

							if (column.editable) {
								var dataType = column.dataType;
								var label = column.label;
								var required = column.required;
								var type = column.type;

								var EditorClass = instance.TYPE_EDITOR[type] || A.TextCellEditor;

								var config = {
									validator: {
										rules: {}
									}
								};

								var elementName = 'value';

								if (type === 'checkbox') {
									elementName = label;

									config.options = [ label ];
								}
								else if ((type === 'radio') || (type === 'select')) {
									var structureField = instance.findStructureFieldByKey(structure, column.key);

									config.options = instance.getCellEditorOptions(structureField.options);
								}

								var validatorRules = config.validator.rules;
								var validatorRuleName = instance.DATATYPE_VALIDATOR[dataType];

								validatorRules[elementName] = {
									required: required
								};

								if (validatorRuleName) {
									validatorRules[elementName][validatorRuleName] = true;
								}

								column.editor = new EditorClass(config);
							}
						}
					);

					return columnset;
				},

				buildEmptyRecords: function(num, keys) {
					var instance = this;

					var emptyRows = [];

					for (var i = 0; i < num; i++) {
						emptyRows.push(instance.getRecordModel(keys));
					}

					return emptyRows;
				},

				findStructureFieldByKey: function(structure, key) {
					var found = null;

					A.Array.some(
						structure,
						function(field) {
							found = field;

							return (found.key === key);
						}
					);

					return found;
				},

				getCellEditorOptions: function(options) {
					var normalized = {};

					A.Array.each(
						options,
						function(option) {
							normalized[option.name] = option.value;
						}
					);

					return normalized;
				},

				getRecordModel: function(keys) {
					var instance = this;

					var recordModel = {};

					A.Array.each(
						keys,
						function(key) {
							recordModel[key] = _EMPTY;
						}
					);

					return recordModel;
				},

				DATATYPE_VALIDATOR: {
					'double': 'number',
					'integer': 'digits',
					'long': 'digits'
				},

				TYPE_EDITOR: {
					'checkbox': A.CheckboxCellEditor,
					'ddm-date': A.DateCellEditor,
					'ddm-decimal': A.TextCellEditor,
					'ddm-integer': A.TextCellEditor,
					'ddm-number': A.TextCellEditor,
					'radio': A.RadioCellEditor,
					'select': A.DropDownCellEditor,
					'text': A.TextCellEditor,
					'textarea': A.TextAreaCellEditor
				}
			}
		);

		Liferay.LiferaySpreadSheet = LiferaySpreadSheet;
	},
	'',
	{
		requires: ['aui-datatable']
	}
);