AUI.add(
	'liferay-ddm-form-field-grid',
	function(A) {
		var Lang = A.Lang;

		var TPL_QUERY_INPUT_CHECKED = 'input[name="{name}"]:checked';

		var GridField = A.Component.create(
			{
				ATTRS: {
					columns: {
						setter: '_setColumns',
						state: true,
						validator: Array.isArray,
						value: []
					},

					rows: {
						setter: '_setRows',
						state: true,
						validator: Array.isArray,
						value: []
					},

					type: {
						value: 'grid'
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-grid',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._eventHandlers.push(
							instance.bindContainerEvent('change', instance._onCheckItem, '.form-builder-grid-field')
						);
					},

					getTemplateContext: function() {
						var instance = this;

						return A.merge(
							GridField.superclass.getTemplateContext.apply(instance, arguments),
							{
								columns: instance.get('columns'),
								focusTarget: instance._getFocusTarget(),
								rows: instance.get('rows')
							}
						);
					},

					getValue: function() {
						var instance = this;

						var gridRowsNode = instance._getGridRowsNode();

						var value = {};

						for (var i = 0; i < gridRowsNode.size(); i++) {
							var gridRow = gridRowsNode.item(i);

							var rowName = gridRow.attr('name');

							var queryChecked = Lang.sub(
								TPL_QUERY_INPUT_CHECKED,
								{
									name: rowName
								}
							);

							var inputChecked = gridRow.one(queryChecked);

							if (inputChecked) {
								value[rowName] = inputChecked.val();
							}
						}

						return value;
					},

					setValue: function(value) {
						var instance = this;

						instance.set('value', value);

						instance.render();
					},

					showErrorMessage: function() {
						var instance = this;

						var container = instance.get('container');

						GridField.superclass.showErrorMessage.apply(instance, arguments);

						container.all('.help-block').appendTo(container.one('.form-group'));
					},

					_getFocusTarget: function() {
						var instance = this;

						return instance.focusTarget;
					},

					_getGridRowsNode: function() {
						var instance = this;

						var container = instance.get('container');

						var gridRowsNode = container.all('tbody tr');

						return gridRowsNode;
					},

					_getLocalizedLabel: function(option) {
						var defaultLanguageId = themeDisplay.getDefaultLanguageId();

						return option.label[defaultLanguageId] ? option.label[defaultLanguageId] : option.label;
					},

					_mapItemsLabels: function(items) {
						var instance = this;

						items.forEach(
							function(item) {
								item.label = instance._getLocalizedLabel(item);
							}
						);
					},

					_onCheckItem: function(event) {
						var instance = this;

						var target = event.currentTarget;

						var value = instance.get('value');

						if (!value) {
							value = {};
						}

						value[target.attr('name')] = target.attr('value');

						instance._setFocusTarget(target);

						instance.setValue(value);
					},

					_setColumns: function(columns) {
						var instance = this;

						instance._mapItemsLabels(columns);
					},

					_setFocusTarget: function(target) {
						var instance = this;

						var focusTarget = {};

						focusTarget.row = target.attr('name');

						focusTarget.index = target.attr('data-row-index');

						instance.focusTarget = focusTarget;
					},

					_setRows: function(rows) {
						var instance = this;

						instance._mapItemsLabels(rows);
					}
				}
			}
		);

		Liferay.namespace('DDM.Field').Grid = GridField;
	},
	'',
	{
		requires: ['liferay-ddm-form-field-grid', 'liferay-ddm-form-renderer-field']
	}
);