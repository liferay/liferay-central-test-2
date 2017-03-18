;(function() {
	AUI().applyConfig(
		{
			groups: {
				'field-numeric': {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-field-numeric': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'numeric_field.js',
							requires: [
								'aui-autosize-deprecated',
								'aui-tooltip',
								'liferay-ddm-form-renderer-field'
							]
						},
						'liferay-ddm-form-field-numeric-template': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'numeric.soy.js',
							requires: [
								'soyutils'
							]
						}
					},
					root: MODULE_PATH + '/'
				}
			}
		}
	);
})();