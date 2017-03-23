AUI.add(
	'liferay-ddm-form-field-options',
	function(A) {
		var AArray = A.Array;

		var Renderer = Liferay.DDM.Renderer;

		var Util = Renderer.Util;

		var TPL_DRAG_HANDLE = '<div class="drag-handle icon-reorder"><span aria-hidden="true"></span></div>';

		var TPL_DRAG_HELPER = '<div class="drag-helper"></div>';

		var TPL_DRAG_PLACEHOLDER = '<div class="drag-placeholder"></div>';

		var TPL_REMOVE_BUTTON = '<button class="close close-modal" type="button"><span aria-hidden="true">×</span></button>';

		var OptionsField = A.Component.create(
			{
				ATTRS: {

					allowEmptyOptions: {
						value: false
					},

					sortableList: {
						valueFn: '_valueSortableList'
					},

					strings: {
						value: {
							addOptionMessage: Liferay.Language.get('enter-an-option')
						}
					},

					type: {
						value: 'options'
					},

					value: {
						setter: '_setValue',
						validator: Array.isArray,
						value: []
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-options',

				prototype: {
					initializer: function() {
						var instance = this;

						var sortableList = instance.get('sortableList');

						instance._eventHandlers.push(
							instance.on('liferay-ddm-form-field-key-value:destroy', instance._onDestroyOption),
							instance.after('liferay-ddm-form-field-key-value:render', instance._afterRenderOption),
							instance.after('liferay-ddm-form-field-key-value:blur', instance._afterBlur),
							instance.after('liferay-ddm-form-field-key-value:valueChange', instance._afterOptionValueChange),
							sortableList.after('drag:end', A.bind('_afterSortableListDragEnd', instance)),
							sortableList.after('drag:start', A.bind('_afterSortableListDragStart', instance))
						);

						instance._createMainOption();
					},

					addOption: function() {
						var instance = this;

						var lastOption = instance.getLastOption();

						var repeatedOption = lastOption.repeat();

						instance._bindOptionUI(repeatedOption);
						instance._renderOptionUI(repeatedOption);
						instance._syncOptionUI(repeatedOption);
						instance._syncOptionUI(lastOption);

						repeatedOption.addTarget(instance);

						instance.fire('addOption');

						return repeatedOption;
					},

					clearValidationStatus: function() {
						var instance = this;

						instance.eachOption(
							function(option) {
								option.clearValidationStatus();
							}
						);

						OptionsField.superclass.clearValidationStatus.apply(instance, arguments);
					},

					eachOption: function(fn) {
						var instance = this;

						var mainOption = instance._mainOption;

						mainOption.getRepeatedSiblings().forEach(fn, instance);
					},

					empty: function() {
						var instance = this;

						var mainOption = instance._mainOption;

						var options = mainOption.getRepeatedSiblings();

						while (options.length > 1) {
							var option = options[options.length - 1];

							if (option !== mainOption) {
								option.remove();
							}
						}

						mainOption.set('key', '');
					},

					getLastOption: function() {
						var instance = this;

						var repetitions = instance._mainOption.getRepeatedSiblings();

						return instance.getOption(repetitions.length - 1);
					},

					getOption: function(index) {
						var instance = this;

						var repetitions = instance._mainOption.getRepeatedSiblings();

						return repetitions[index];
					},

					getValue: function() {
						var instance = this;

						var values = [];

						instance.eachOption(
							function(item) {
								var key = item.get('key');

								if (key) {
									values.push(
										{
											label: item.get('value'),
											value: key
										}
									);
								}
							}
						);

						return values;
					},

					hasErrors: function() {
						var instance = this;

						var hasErrors = false;

						if (instance.get('visible')) {
							instance.eachOption(
								function(option) {
									if (option.hasErrors()) {
										hasErrors = true;
									}
								}
							);
						}

						return hasErrors;
					},

					hideErrorMessage: function() {
						var instance = this;

						instance.eachOption(
							function(option) {
								option.hideErrorMessage();
							}
						);
					},

					moveOption: function(option, oldIndex, newIndex) {
						var instance = this;

						var repetitions = option.getRepeatedSiblings();

						var value = instance.get('value');

						instance._reorderOptions(repetitions, newIndex, oldIndex);

						repetitions.forEach(A.bind('_syncRepeatableField', option));

						instance._reorderOptions(value, newIndex, oldIndex);

						instance.set('value', value);
					},

					processEvaluationContext: function(context) {
						var instance = this;

						var value = instance.getValue();

						if (value.length === 0 && instance.get('required')) {
							context.valid = false;
							context.errorMessage = Liferay.Language.get('please-add-at-least-one-option');
						}

						return context;
					},

					removeOption: function(option) {
						var instance = this;

						var value = instance.getValue();

						if (value.length == 1) {
							option.set('value', '');
							option.setValue('');
						}
						else {
							var repetitions = option.getRepeatedSiblings();

							var index = repetitions.indexOf(option);

							value.splice(index, 1);

							instance.setValue(value);

							if (value.length > 0) {
								if (index > 0) {
									repetitions[index - 1].focus();
								}
								else {
									repetitions[index + 1].focus();
								}
							}
						}
					},

					render: function() {
						var instance = this;

						OptionsField.superclass.render.apply(instance, arguments);

						instance._renderOptions(instance.get('value'));

						return instance;
					},

					setValue: function(value) {
						var instance = this;

						if (!Util.compare(value, instance.get('value'))) {
							instance.set('value', value);
							instance._renderOptions(instance.get('value'));
						}
					},

					showErrorMessage: function() {
						var instance = this;

						var mainOption = instance._mainOption;

						mainOption.showErrorMessage();
					},

					updateContainer: function() {
						var instance = this;

						OptionsField.superclass.updateContainer.apply(instance, arguments);

						instance.eachOption(
							function(option) {
								option.updateContainer();
							}
						);
					},

					_afterBlur: function(event) {
						var instance = this;

						var value = instance.getValue();

						if (value.length === 0 || value.length === 1 && value[0].label === '') {
							instance.setValue([]);
						}
					},

					_afterErrorMessageChange: function(event) {
						var instance = this;

						var mainOption = instance._mainOption;

						mainOption.set('errorMessage', event.newVal);
					},

					_afterOptionNormalizeKey: function(key, option) {
						var instance = this;

						var name = key;

						if (key) {
							var valueInItem = function(value, item) {
								return item.value === value && item.value !== option.get('key');
							};

							var optionsValues = instance.getValue();

							var hasOptionWithName = function() {
								return optionsValues.filter(A.bind(valueInItem, null, name)).length > 0;
							};

							var counter = 0;

							do {
								if (counter > 0) {
									name = key + counter;
								}

								counter++;
							}
							while (hasOptionWithName());
						}

						return new A.Do.AlterReturn(null, name);
					},

					_afterOptionValueChange: function(event) {
						var instance = this;

						if (instance._skipOptionValueChange) {
							return;
						}

						var option = event.target;

						var repetitions = option.getRepeatedSiblings();

						if (option.get('repeatedIndex') === repetitions.length - 1) {
							instance.addOption();
						}

						var value = instance.getValue();

						if (value.length > 0 && instance.get('required')) {
							instance.set('errorMessage', '');
							instance.set('valid', true);
						}
					},

					_afterRenderOption: function(event) {
						var instance = this;

						var option = event.target;

						instance._bindListEvents();
						instance._renderOptionUI(option);
					},

					_afterSortableListDragEnd: function(event) {
						var instance = this;

						var dragNode = event.target.get('node');

						var dragEndIndex = instance._getNodeIndex(dragNode);

						var dragStartIndex = instance._dragStartIndex;

						if (dragEndIndex !== dragStartIndex) {
							var mainOption = instance._mainOption;

							var option = AArray.find(
								mainOption.getRepeatedSiblings(),
								function(item) {
									return item.get('container') === dragNode;
								}
							);

							instance.moveOption(option, dragStartIndex, dragEndIndex);
						}
					},

					_afterSortableListDragStart: function(event) {
						var instance = this;

						var dragNode = event.target.get('node');

						instance._dragStartIndex = instance._getNodeIndex(dragNode);

						var sortableList = instance.get('sortableList');

						var placeholderNode = sortableList.get('placeholder');

						placeholderNode.setContent(dragNode.clone().show());
					},

					_afterValidChange: function(event) {
						var instance = this;

						var mainOption = instance._mainOption;

						mainOption.set('valid', event.newVal);
					},

					_bindListEvents: function() {
						var instance = this;

						var optionsNode = instance._getOptionsNode();

						instance._eventHandlers.push(
							optionsNode.delegate('focus', A.bind('_onFocusOption', instance), '.last-option .field')
						);
					},

					_bindOptionUI: function(option) {
						var instance = this;

						option.after(A.rbind('_afterOptionNormalizeKey', instance, option), option, 'normalizeKey');

						option.bindContainerEvent('click', A.bind('_onOptionClickClose', instance, option), '.close');
					},

					_canSortNode: function(event) {
						var instance = this;

						var dragNode = event.drag.get('node');
						var dropNode = event.drop.get('node');

						var lastOption = instance.getLastOption();
						var lastOptionContainer = lastOption.get('container');

						return lastOptionContainer !== dropNode && lastOptionContainer !== dragNode;
					},

					_createMainOption: function() {
						var instance = this;

						var strings = instance.get('strings');

						var config = {
							enableEvaluations: false,
							fieldName: instance.get('fieldName') + 'Option',
							placeholder: strings.addOptionMessage,
							repeatable: true,
							repeatedIndex: 0,
							showLabel: false,
							value: '',
							visible: true
						};

						config.context = A.clone(config);

						instance._mainOption = new Liferay.DDM.Field.KeyValue(config);

						instance._mainOption.addTarget(instance);

						instance._bindOptionUI(instance._mainOption);
					},

					_getNodeIndex: function(node) {
						var instance = this;

						var optionsNode = instance._getOptionsNode();

						var siblings = optionsNode.all('> .lfr-ddm-form-field-container');

						return siblings.indexOf(node);
					},

					_getOptionsNode: function() {
						var instance = this;

						var container = instance.get('container');

						return container.one('.options');
					},

					_onDestroyOption: function(event) {
						var instance = this;

						var option = event.target;

						A.DD.DDM.getDrag(option.get('container')).destroy();
					},

					_onFocusOption: function(event) {
						event.target.scrollIntoView();
					},

					_onOptionClickClose: function(option) {
						var instance = this;

						instance.removeOption(option);
					},

					_renderOptions: function(optionsValues) {
						var instance = this;

						var container = instance.get('container');

						var mainOption = instance._mainOption;

						instance.empty();

						mainOption.render(container.one('.options'));

						instance._syncOptionUI(mainOption);

						optionsValues.forEach(
							function(optionValue, index) {
								if (index === 0) {
									instance._restoreOption(mainOption, optionValue);
								}
								else {
									var newOption = instance.addOption();

									instance._restoreOption(newOption, optionValue);
								}
							}
						);

						if (optionsValues.length && optionsValues[optionsValues.length - 1].value) {
							instance.addOption();
						}
					},

					_renderOptionUI: function(option) {
						var instance = this;

						var container = option.get('container');

						container.append(TPL_DRAG_HANDLE + TPL_REMOVE_BUTTON);
					},

					_reorderOptions: function(options, newIndex, oldIndex) {
						options.splice(newIndex, 0, options.splice(oldIndex, 1)[0]);
					},

					_restoreOption: function(option, contextValue) {
						var instance = this;

						instance._skipOptionValueChange = true;
						option.set('value', contextValue.label);
						option.set('key', contextValue.value);
						option.setValue(contextValue.label);
						instance._skipOptionValueChange = false;
					},

					_setValue: function(val) {
						var instance = this;

						if (!instance.get('allowEmptyOptions') && val.length === 0) {
							return [
								{
									label: 'Option',
									value: 'Option'
								}
							];
						}

						return val;
					},

					_syncOptionUI: function(option) {
						var instance = this;

						var addLastOptionClass = instance.getLastOption() === option;

						var container = option.get('container');

						container.toggleClass('last-option', addLastOptionClass);

						var sortableList = instance.get('sortableList');

						sortableList.add(container);
					},

					_valueSortableList: function() {
						var instance = this;

						return new A.SortableList(
							{
								dd: {
									handles: ['.drag-handle']
								},
								helper: A.Node.create(TPL_DRAG_HELPER),
								placeholder: A.Node.create(TPL_DRAG_PLACEHOLDER),
								sortCondition: A.bind('_canSortNode', instance)
							}
						);
					}
				}
			}
		);

		Liferay.namespace('DDM.Field').Options = OptionsField;
	},
	'',
	{
		requires: ['aui-sortable-list', 'liferay-ddm-form-field-key-value', 'liferay-ddm-form-renderer-field']
	}
);