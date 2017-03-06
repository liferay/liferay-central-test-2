;(function() {
	AUI().applyConfig(
		{
			groups: {
				adaptivemedia: {
					base: MODULE_PATH + '/adaptive_media/js/',
					combine: Liferay.AUI.getCombine(),
					modules: {
						'liferay-adaptivemedia': {
							path: 'adaptivemedia.js',
							requires: [
								'aui-base',
								'aui-io-request',
								'liferay-portlet-base'
							]
						}
					},
					root: MODULE_PATH + '/adaptive_media/js/'
				}
			}
		}
	);
})();