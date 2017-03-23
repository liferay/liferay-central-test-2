AUI.add(
	'liferay-ddm-form-renderer-field-repetition',
	function(A) {
		var AObject = A.Object;

		var Renderer = Liferay.DDM.Renderer;

		var FieldTypes = Renderer.FieldTypes;

		var SELECTOR_REPEAT_BUTTONS = '.lfr-ddm-form-field-repeatable-add-button, .lfr-ddm-form-field-repeatable-delete-button';

		var TPL_REPEATABLE_ADD = '<a class="icon-plus-sign lfr-ddm-form-field-repeatable-add-button" href="javascript:;"></a>';

		var TPL_REPEATABLE_DELETE = '<a class="hide icon-minus-sign lfr-ddm-form-field-repeatable-delete-button" href="javascript:;"></a>';

		var TPL_REPEATABLE_TOOLBAR = '<div class="lfr-ddm-form-field-repeatable-toolbar">' + TPL_REPEATABLE_DELETE + TPL_REPEATABLE_ADD + '</div>';

		var FieldRepetitionSupport = function() {
		};

		FieldRepetitionSupport.ATTRS = {
			repeatable: {
				setter: A.DataType.Boolean.parse,
				value: false
			},

			repeatedIndex: {
				state: true,
				value: 0
			},

			repetitions: {
				valueFn: '_valueRepetitions'
			}
		};

		FieldRepetitionSupport.prototype = {
			initializer: function() {
				var instance = this;

				if (instance.get('repeatable')) {
					instance._eventHandlers.push(
						instance.after('render', instance._afterRepeatableFieldRender)
					);
				}
			},

			destructor: function() {
				var instance = this;

				instance._removeCurrentFieldFromRepetitionList();

				instance._syncOtherRepeatableFields();
			},

			copy: function() {
				var instance = this;

				var config = instance.copyConfiguration();

				var fieldClass = instance.getFieldClass();

				return new fieldClass(config);
			},

			copyConfiguration: function() {
				var instance = this;

				var context = instance.get('context');

				var repetitions = instance.get('repetitions');

				var config = A.merge(
					context,
					{
						enableEvaluations: instance.get('enableEvaluations'),
						fieldName: instance.get('fieldName'),
						parent: instance.get('parent'),
						portletNamespace: instance.get('portletNamespace'),
						repeatable: instance.get('repeatable'),
						repeatedIndex: repetitions.length,
						repetitions: repetitions,
						type: instance.get('type'),
						visible: instance.get('visible')
					}
				);

				config.context = A.clone(context);

				delete config.context.name;
				delete config.context.value;
				delete config.name;
				delete config.value;

				return config;
			},

			getFieldClass: function() {
				var instance = this;

				var type = instance.get('type');

				var fieldType = FieldTypes.get(type);

				var fieldClassName = fieldType.get('className');

				return AObject.getValue(window, fieldClassName.split('.'));
			},

			getRepeatedSiblings: function() {
				var instance = this;

				return instance.get('repetitions');
			},

			remove: function() {
				var instance = this;

				instance.destroy();
			},

			renderRepeatable: function() {
				var instance = this;

				instance.renderRepeatableUI();

				instance.syncRepeatablelUI();
			},

			renderRepeatableUI: function() {
				var instance = this;

				var container = instance.get('container');

				if (!instance.get('readOnly')) {
					container.append(TPL_REPEATABLE_TOOLBAR);
				}
			},

			repeat: function() {
				var instance = this;

				var copiedField = instance.copy();

				var repetitions = instance.getRepeatedSiblings();

				var index = repetitions.indexOf(instance) + 1;

				copiedField.set('repeatedIndex', index);

				repetitions.splice(index, 0, copiedField);

				var container = instance.get('container');

				container.insert(copiedField.get('container'), 'after');

				copiedField.render();

				repetitions.filter(
					function(repetition, currentIndex) {
						return currentIndex > index;
					}
				).forEach(A.bind('_syncRepeatableField', instance));

				return copiedField;
			},

			syncRepeatablelUI: function() {
				var instance = this;

				if (!instance.get('readOnly')) {
					var container = instance.get('container');

					container.one('.lfr-ddm-form-field-repeatable-delete-button').toggle(instance.get('repeatedIndex') > 0);
				}
			},

			_afterRepeatableFieldRender: function() {
				var instance = this;

				var container = instance.get('container');

				instance.renderRepeatable();

				if (!instance.get('readOnly')) {
					(new A.EventHandle(instance._DOMEventHandlers)).detach();

					instance._DOMEventHandlers = [
						container.delegate('click', instance._handleToolbarClick, SELECTOR_REPEAT_BUTTONS, instance)
					];
				}
			},

			_handleToolbarClick: function(event) {
				var instance = this;

				var currentTarget = event.currentTarget;

				if (currentTarget.hasClass('lfr-ddm-form-field-repeatable-delete-button')) {
					instance.remove();
				}
				else {
					instance.repeat();
				}

				event.stopPropagation();
			},

			_removeCurrentFieldFromRepetitionList: function() {
				var instance = this;

				var repetitions = instance.get('repetitions');

				var index = repetitions.indexOf(instance);

				if (index > -1) {
					repetitions.splice(index, 1);
				}
			},

			_syncOtherRepeatableFields: function() {
				var instance = this;

				var repetitions = instance.get('repetitions');

				repetitions.forEach(A.bind('_syncRepeatableField', instance));
			},

			_syncRepeatableField: function(field) {
				var instance = this;

				var repeatedSiblings = instance.getRepeatedSiblings();

				var value = field.getValue();

				field.set('repeatedIndex', repeatedSiblings.indexOf(field));
				field.set('repetitions', repeatedSiblings);

				field.setValue(value);

				field.render();
			},

			_valueRepetitions: function() {
				var instance = this;

				return [instance];
			}
		};

		Liferay.namespace('DDM.Renderer').FieldRepetitionSupport = FieldRepetitionSupport;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-types', 'liferay-ddm-form-renderer-util']
	}
);