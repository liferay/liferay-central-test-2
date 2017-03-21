'use strict';

var A = AUI();

var createOptionsField = function(config) {
	return new Liferay.DDM.Field.Options(
		A.merge(
			{
				context: {
					name: 'optionsField'
				}
			},
			config || {}
		)
	).render(document.body);
};

describe(
	'Liferay.DDM.Field.Options',
	function() {
		var optionsField;

		afterEach(
			function(done) {
				optionsField.destroy();

				done();
			}
		);

		before(
			function(done) {
				AUI().use(
					'liferay-ddm-form-field-options-template',
					'liferay-ddm-form-field-options',
					function(A) {
						Liferay.DDM.Renderer.FieldTypes.register(
							[
								{
									'javaScriptClass': 'Liferay.DDM.Field.Options',
									'name': 'options',
									'templateNamespace': 'ddm.options'
								},
								{
									'javaScriptClass': 'Liferay.DDM.Field.Text',
									'name': 'text',
									'templateNamespace': 'ddm.text'
								},
								{
									'javaScriptClass': 'Liferay.DDM.Field.KeyValue',
									'name': 'key_value',
									'templateNamespace': 'ddm.key_value'
								}
							]
						);

						done();
					}
				);
			}
		);

		describe(
			'LPS-70570',
			function() {
				it(
					'should not allow the same name for different options',
					function(done) {
						optionsField = createOptionsField(
							{
								allowEmptyOptions: true
							}
						);

						optionsField.getLastOption().set('value', 'One');
						optionsField.getLastOption().set('value', 'One');
						optionsField.getLastOption().set('value', 'One');

						var value = optionsField.getValue();

						assert.strictEqual('One', value[0].value);
						assert.strictEqual('One1', value[1].value);
						assert.strictEqual('One2', value[2].value);

						done();
					}
				);
			}
		);
	}
);