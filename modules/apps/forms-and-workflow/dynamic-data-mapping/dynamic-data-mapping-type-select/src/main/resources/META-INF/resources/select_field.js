AUI.add(
	'liferay-ddm-form-field-select',
	function(A) {
		var CSS_SELECT_BADGE_ITEM = A.getClassName('trigger', 'badge', 'item');

		var CSS_SELECT_OPTION_ITEM = A.getClassName('select', 'option', 'item');

		var CSS_SELECT_TRIGGER_ACTION = A.getClassName('form', 'builder', 'select', 'field');

		var Lang = A.Lang;

		var TPL_OPTION = '<option>{label}</option>';

		var SelectField = A.Component.create(
			{
				ATTRS: {
					dataSourceType: {
						getter: '_getDataSourceType',
						value: 'manual'
					},

					multiple: {
						state: true,
						value: false
					},

					options: {
						getter: '_getOptions',
						state: true,
						validator: Array.isArray,
						value: []
					},

					strings: {
						value: {
							chooseAnOption: Liferay.Language.get('choose-an-option'),
							chooseOptions: Liferay.Language.get('choose-options'),
							dynamicallyLoadedData: Liferay.Language.get('dynamically-loaded-data')
						}
					},

					type: {
						value: 'select'
					},

					value: {
						value: []
					}
				},

				AUGMENTS: [
					Liferay.DDM.Field.SelectFieldSearchSupport
				],

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-select',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._eventHandlers.push(
							A.one('doc').after('click', A.bind(instance._afterClickOutside, instance)),
							instance.bindContainerEvent('click', instance._afterClickSelectTrigger, '.' + CSS_SELECT_TRIGGER_ACTION),
							instance.bindContainerEvent('click', instance._onClickItem, '.' + CSS_SELECT_OPTION_ITEM),
							instance.bindContainerEvent('click', instance._onClickBadgeItem, '.' + CSS_SELECT_BADGE_ITEM)
						);
					},

					cleanSelect: function() {
						var instance = this;

						var inputNode = instance.getInputNode();

						inputNode.setAttribute('selected', false);

						instance.set('value', []);
					},

					closeList: function() {
						var instance = this;

						if (!instance.get('readOnly') && instance._isListOpen()) {
							var container = instance.get('container');

							container.one('.drop-chosen').addClass('hide');

							container.one('.form-builder-select-field').removeClass('active');

							instance.fire('closeList');
						}
					},

					getTemplateContext: function() {
						var instance = this;

						return A.merge(
							SelectField.superclass.getTemplateContext.apply(instance, arguments),
							{
								badgeCloseIcon: Liferay.Util.getLexiconIconTpl('times', 'icon-monospaced'),
								options: instance.get('options'),
								selectCaretDoubleIcon: Liferay.Util.getLexiconIconTpl('caret-double-l', 'icon-monospaced'),
								selectSearchIcon: Liferay.Util.getLexiconIconTpl('search', 'icon-monospaced'),
								strings: instance.get('strings'),
								value: instance.getValueSelected()
							}
						);
					},

					getValue: function() {
						var instance = this;

						var inputNode = instance.getInputNode();

						var value = [];

						inputNode.all('option').each(
							function(optionNode) {
								if (optionNode.attr('selected')) {
									value.push(optionNode.val());
								}
							}
						);

						value = value.join();

						if (!value) {
							var contextValue = instance._getContextValue();

							var hasOption = instance._hasOption(contextValue);

							if (contextValue && !hasOption) {
								value = contextValue;
							}
						}

						return value;
					},

					getValueSelected: function() {
						var instance = this;

						var value = instance.get('value');

						if (!Lang.isArray(value)) {
							value = [value];
						}

						var values = instance._getOptionsSelected(value);

						return values;
					},

					openList: function() {
						var instance = this;

						instance._getSelectTriggerAction().addClass('active');

						instance.get('container').one('.drop-chosen').toggleClass('hide');
					},

					render: function() {
						var instance = this;

						var dataSourceType = instance.get('dataSourceType');

						SelectField.superclass.render.apply(instance, arguments);

						if (dataSourceType !== 'manual' && instance.get('builder')) {
							var inputNode = instance.getInputNode();

							var strings = instance.get('strings');

							inputNode.attr('disabled', true);

							inputNode.html(
								Lang.sub(
									TPL_OPTION,
									{
										label: strings.dynamicallyLoadedData
									}
								)
							);
						}

						return instance;
					},

					setValue: function(value) {
						var instance = this;

						if (!Lang.isArray(value)) {
							value = [value];
						}

						instance.set('value', value);

						instance.render();
					},

					showErrorMessage: function() {
						var instance = this;

						SelectField.superclass.showErrorMessage.apply(instance, arguments);

						var container = instance.get('container');

						var inputGroup = container.one('.input-select-wrapper');

						inputGroup.insert(container.one('.help-block'), 'after');
					},

					_afterClickOutside: function(event) {
						var instance = this;

						if (instance._isClickingOutSide(event)) {
							instance.closeList();
						}
					},

					_afterClickSelectTrigger: function(event) {
						event.preventDefault();

						var instance = this;

						if (!instance.get('readOnly')) {
							var target = event.target;

							if (target.ancestor('.search-chosen') || target.ancestor('.trigger-badge-item')) {
								return;
							}

							instance.openList();
						}
					},

					_getContextValue: function() {
						var instance = this;

						var contextValue = instance.get('value');

						if (Lang.isArray(contextValue)) {
							contextValue = contextValue[0];
						}

						return contextValue;
					},

					_getDataSourceType: function(value) {
						if (Lang.isString(value)) {
							try {
								value = JSON.parse(value);
							}
							catch (e) {
							}
						}

						if (Lang.isArray(value)) {
							value = value[0];
						}

						return value;
					},

					_getOptions: function(options) {
						return options || [];
					},

					_getOptionsSelected: function(value) {
						var instance = this;

						var options = instance.get('options');

						var optionsSelected = [];

						if (Lang.isArray(value)) {
							value.forEach(
								function(value, index) {
									options.forEach(
										function(option, index) {
											if (value && option.value === value) {
												optionsSelected.push(option);
											}
										}
									);
								}
							);
						}

						return optionsSelected;
					},

					_getSelectTriggerAction: function() {
						var instance = this;

						return instance.get('container').one('.' + CSS_SELECT_TRIGGER_ACTION);
					},

					_hasOption: function(value) {
						var instance = this;

						var hasOption = false;

						var inputNode = instance.getInputNode();

						inputNode.all('option').each(
							function(optionNode) {
								if (optionNode.val() === value) {
									hasOption = true;
								}
							}
						);

						return hasOption;
					},

					_isClickingOutSide: function(event) {
						var instance = this;

						var container = instance.get('container');

						if (container.contains(event.target)) {
							return false;
						}

						return true;
					},

					_isListOpen: function() {
						var instance = this;

						var container = instance.get('container');

						if (!container.one('.drop-chosen')) {
							return false;
						}

						var openList = container.one('.drop-chosen').hasClass('hide');

						return !openList;
					},

					_onClickBadgeItem: function(event) {
						event.stopPropagation();

						var instance = this;

						var values = instance.get('value');

						var value = event.currentTarget.getAttribute('data-badge-value');

						var index = values.indexOf(value);

						if (index >= 0) {
							values.splice(index, 1);
						}

						instance.setValue(values);
					},

					_onClickItem: function(event) {
						event.stopPropagation();

						var instance = this;

						var value;

						if (instance.get('multiple')) {
							value = instance.get('value');

							if (!event.target.getAttribute('data-option-selected')) {
								value.push(event.target.getAttribute('data-option-value'));
							}
						}
						else {
							value = event.target.getAttribute('data-option-value');
						}

						instance.setValue(value);
					}
				}
			}
		);

		Liferay.namespace('DDM.Field').Select = SelectField;
	},
	'',
	{
		requires: ['liferay-ddm-form-field-select', 'liferay-ddm-form-field-select-search-support', 'liferay-ddm-form-renderer-field']
	}
);