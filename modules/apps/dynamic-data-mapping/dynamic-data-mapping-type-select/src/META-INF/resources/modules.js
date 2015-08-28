;(function() {
	var PATH_DDM_TYPE_SELECT = Liferay.ThemeDisplay.getPathContext() + '/o/ddm-type-select';

	AUI().applyConfig(
		{
			groups: {
				'field-select': {
					base: PATH_DDM_TYPE_SELECT + '/',
					modules: {
						'liferay-ddm-form-field-select': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'select_field.js',
							requires: [
								'liferay-ddm-form-renderer-field'
							]
						},
						'liferay-ddm-form-field-select-template': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'select.soy.js',
							requires: [
								'soyutils'
							]
						}
					},
					root: PATH_DDM_TYPE_SELECT + '/'
				}
			}
		}
	);
})();