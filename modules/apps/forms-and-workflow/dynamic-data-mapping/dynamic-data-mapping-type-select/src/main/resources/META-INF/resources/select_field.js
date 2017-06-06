AUI.add(
	'liferay-ddm-form-field-select',
	function(A) {
		var CSS_ACTIVE = A.getClassName('active');

		var CSS_DROP_CHOSEN = A.getClassName('drop', 'chosen');

		var CSS_FORM_FIELD_CONTAINER = A.getClassName('lfr', 'ddm', 'form', 'field', 'container');

		var CSS_HELP_BLOCK = A.getClassName('help', 'block');

		var CSS_HIDE = A.getClassName('hide');

		var CSS_INPUT_SELECT_WRAPPER = A.getClassName('input', 'select', 'wrapper');

		var CSS_SEARCH_CHOSEN = A.getClassName('search', 'chosen');

		var CSS_SELECT_ARROW_DOWN = A.getClassName('select', 'arrow', 'down', 'container');

		var CSS_SELECT_BADGE_ITEM_CLOSE = A.getClassName('trigger', 'badge', 'item', 'close');

		var CSS_SELECT_OPTION_ITEM = A.getClassName('select', 'option', 'item');

		var CSS_SELECT_TRIGGER_ACTION = A.getClassName('select', 'field', 'trigger');

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
							dynamicallyLoadedData: Liferay.Language.get('dynamically-loaded-data'),
							emptyList: Liferay.Language.get('empty-list')
						}
					},

					triggers: {
						value: []
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

						instance._open = false;

						instance._createBadgeTooltip();

						instance._eventHandlers.push(
							A.one('doc').after('click', A.bind(instance._afterClickOutside, instance)),
							instance.bindContainerEvent('click', instance._handleContainerClick, '.' + CSS_FORM_FIELD_CONTAINER)
						);
					},

					destructor: function() {
						var instance = this;

						if (instance._tooltip) {
							instance._tooltip.destroy();
						}
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

							container.one('.' + CSS_DROP_CHOSEN).addClass(CSS_HIDE);

							container.one('.' + CSS_SELECT_TRIGGER_ACTION).removeClass(CSS_ACTIVE);

							instance._open = false;

							instance.fire('closeList');
						}
					},

					focus: function() {
						var instance = this;

						var container = instance.get('container');

						var arrowSelect = container.one('.' + CSS_SELECT_ARROW_DOWN);

						arrowSelect.focus();
					},

					getTemplateContext: function() {
						var instance = this;

						return A.merge(
							SelectField.superclass.getTemplateContext.apply(instance, arguments),
							{
								badgeCloseIcon: Liferay.Util.getLexiconIconTpl('times', 'icon-monospaced'),
								open: instance._open,
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

						var value = instance.get('value');

						if (!Lang.isArray(value)) {
							value = [value];
						}

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

						instance._getSelectTriggerAction().addClass(CSS_ACTIVE);

						instance.get('container').one('.form-group').removeClass(CSS_HIDE);
						instance.get('container').one('.' + CSS_DROP_CHOSEN).removeClass(CSS_HIDE);

						instance._open = true;
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

						var inputGroup = container.one('.' + CSS_INPUT_SELECT_WRAPPER);

						inputGroup.insert(container.one('.' + CSS_HELP_BLOCK), 'after');
					},

					toggleList: function() {
						var instance = this;

						if (instance._isListOpen()) {
							instance.closeList();
						}
						else {
							instance.openList();
						}
					},

					_afterClickOutside: function(event) {
						var instance = this;

						if (!instance._preventDocumentClick && instance._isClickingOutSide(event)) {
							instance.closeList();
						}

						instance._preventDocumentClick = false;
					},

					_createBadgeTooltip: function() {
						var instance = this;

						instance._tooltip = new A.TooltipDelegate(
							{
								position: 'bottom',
								trigger: '.multiple-badge-list .multiple-badge',
								triggerHideEvent: ['blur', 'mouseleave'],
								triggerShowEvent: ['focus', 'mouseover'],
								visible: false
							}
						);
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

					_handleBadgeItemCloseClick: function(target) {
						var instance = this;

						var value = target.getAttribute('data-badge-value');

						var values = instance._removeBadge(value);

						instance.setValue(values);
					},

					_handleContainerClick: function(event) {
						var instance = this;

						var target = event.target;

						var closeIconNode = target.ancestor('.' + CSS_SELECT_BADGE_ITEM_CLOSE, true);

						var optionNode = target.ancestor('.' + CSS_SELECT_OPTION_ITEM, true);

						if (closeIconNode) {
							instance._handleBadgeItemCloseClick(closeIconNode);
						}
						else if (optionNode) {
							instance._handleItemClick(optionNode);
						}
						else {
							instance._handleSelectTriggerClick(event);
						}

						instance._preventDocumentClick = true;
					},

					_handleItemClick: function(target) {
						var instance = this;

						var value;

						var currentTarget = target;

						if (instance.get('multiple')) {
							value = instance.get('value').slice();

							instance._open = true;

							var itemValue = currentTarget.getAttribute('data-option-value');

							if (currentTarget.getAttribute('data-option-selected')) {
								value = instance._removeBadge(itemValue);
							}
							else {
								value.push(itemValue);
							}
						}
						else {
							value = currentTarget.getAttribute('data-option-value');

							instance._open = false;
						}

						instance.setValue(value);

						instance.focus();
					},

					_handleSelectTriggerClick: function(event) {
						var instance = this;

						if (!instance.get('readOnly')) {
							var target = event.target;

							if (target.ancestor('.' + CSS_SEARCH_CHOSEN)) {
								return;
							}

							instance.toggleList();
						}
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

						var triggers = instance.get('triggers');

						if (triggers.length) {
							for (var i = 0; i < triggers.length; i++) {
								if (triggers[i].contains(event.target)) {

									return false;
								}
							}
						}

						return !container.contains(event.target);
					},

					_isListOpen: function() {
						var instance = this;

						var container = instance.get('container');

						var dropChosen = container.one('.' + CSS_DROP_CHOSEN);

						if (dropChosen) {
							var openList = dropChosen.hasClass(CSS_HIDE);

							return !openList;
						}

						return false;
					},

					_removeBadge: function(value) {
						var instance = this;

						var values = instance.get('value');

						var index = values.indexOf(value);

						if (index >= 0) {
							values.splice(index, 1);
						}

						return values;
					},

					_selectDOMOption: function(optionNode, value) {
						var selected = false;

						if (Lang.isArray(value)) {
							value = value[0];
						}

						if (value) {
							if (optionNode.val()) {
								selected = value.indexOf(optionNode.val()) > -1;
							}

							if (selected) {
								optionNode.attr('selected', selected);
							}
							else {
								optionNode.removeAttribute('selected');
							}
						}
					},

					_setSelectNodeOptions: function(optionNode, value) {
						var instance = this;

						if (instance.get('multiple')) {
							for (var i = 0; i < value.length; i++) {
								instance._selectDOMOption(optionNode, value[i]);
							}
						}
						else {
							instance._selectDOMOption(optionNode, value);
						}
					}
				}
			}
		);

		Liferay.namespace('DDM.Field').Select = SelectField;
	},
	'',
	{
		requires: ['aui-tooltip', 'liferay-ddm-form-field-select', 'liferay-ddm-form-field-select-search-support', 'liferay-ddm-form-renderer-field']
	}
);