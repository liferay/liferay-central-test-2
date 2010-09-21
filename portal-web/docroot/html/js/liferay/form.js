AUI().add(
	'liferay-form',
	function(A) {
		var Form = A.Component.create(
			{
				ATTRS: {
					id: {},
					namespace: {},
					onSubmit: {
						valueFn: function() {
							var instance = this;

							return instance._onSubmit;
						}
					}
				},

				EXTENDS: A.Base,

				prototype: {
					initializer: function() {
						var instance = this;

						var id = instance.get('id');

						var form = document[id];
						var formNode = A.one(form);

						instance.form = form;
						instance.formNode = formNode;

						if (formNode) {
							instance._bindForm();
						}
					},

					_bindForm: function() {
						var instance = this;

						var formNode = instance.formNode;

						var onSubmit = instance.get('onSubmit');

						formNode.on('submit', onSubmit, instance);

						formNode.delegate('blur', instance._onFieldFocusChange, 'button,input,select,textarea', instance);
						formNode.delegate('focus', instance._onFieldFocusChange, 'button,input,select,textarea', instance);
					},

					_defaultSubmitFn: function(event) {
						var instance = this;

						if (!event.stopped) {
							submitForm(instance.form);
						}
					},

					_onFieldFocusChange: function(event) {
						var instance = this;

						var row = event.currentTarget.ancestor('.aui-field');

						if (row) {
							row.toggleClass('aui-field-focused', (event.type == 'focus'));
						}
					},

					_onSubmit: function(event) {
						var instance = this;

						event.preventDefault();

						setTimeout(
							function() {
								instance._defaultSubmitFn.call(instance, event);
							},
							0
						);
					}
				},

				register: function(config) {
					var instance = this;

					var form = new Liferay.Form(config);

					instance._INSTANCES[config.id || config.namespace] = form;

					return form;
				},

				_INSTANCES: {}
			}
		);

		Liferay.Form = Form;
	},
	'',
	{
		requires: ['aui-base'],
		use: []
	}
);