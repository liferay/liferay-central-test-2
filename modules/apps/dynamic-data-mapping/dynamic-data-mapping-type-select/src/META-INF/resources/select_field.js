AUI.add(
	'liferay-ddm-form-field-select',
	function(A) {
		var SelectField = A.Component.create(
			{
				ATTRS: {
					options: {
						validator: Array.isArray,
						value: []
					},

					type: {
						value: 'select'
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-select',

				prototype: {
					getOptions: function() {
						var instance = this;

						var value = instance.get('value');

						if (instance.get('localizable')) {
							value = value[instance.get('locale')];
						}

						return A.map(
							instance.get('options'),
							function(item) {
								return {
									label: item.label[instance.get('locale')],
									status: value.indexOf(item.value) > -1 ? 'selected' : '',
									value: item.value
								};
							}
						);
					},

					getTemplateContext: function() {
						var instance = this;

						return A.merge(
								SelectField.superclass.getTemplateContext.apply(instance, arguments),
							{
								options: instance.getOptions()
							}
						);
					}
				}
			}
		);

		Liferay.namespace('DDM.Field').Select = SelectField;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-field']
	}
);