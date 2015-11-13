;(function() {
	var PATH_DDM_TYPE_TEXT = Liferay.ThemeDisplay.getPathContext() + '/o/dynamic-data-mapping-type-text';

	AUI().applyConfig(
		{
			groups: {
				'field-text': {
					base: PATH_DDM_TYPE_TEXT + '/',
					modules: {
						'liferay-ddm-form-field-text': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'text_field.js',
							requires: [
								'aui-tooltip',
								'liferay-ddm-form-renderer-field'
							]
						},
						'liferay-ddm-form-field-text-template': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'text.soy.js',
							requires: [
								'soyutils'
							]
						}
					},
					root: PATH_DDM_TYPE_TEXT + '/'
				}
			}
		}
	);
})();