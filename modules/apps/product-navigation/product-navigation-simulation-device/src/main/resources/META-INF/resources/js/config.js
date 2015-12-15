;(function() {
	var PATH_NAVIGATION_SIMULATION_DEVICE = Liferay.ThemeDisplay.getPathContext() + '/o/product-navigation-simulation-device';

	AUI().applyConfig(
		{
			groups: {
				'navigation-simulation-device': {
					base: PATH_NAVIGATION_SIMULATION_DEVICE + '/js/',
					modules: {
						'liferay-control-menu-device-preview': {
							path: 'control_menu_device_preview.js',
							requires: [
								'aui-dialog-iframe-deprecated',
								'aui-event-input',
								'aui-modal',
								'liferay-portlet-base',
								'liferay-util-window',
								'liferay-widget-size-animation-plugin'
							]
						}
					},
					root: PATH_NAVIGATION_SIMULATION_DEVICE + '/js/'
				}
			}
		}
	);
})();