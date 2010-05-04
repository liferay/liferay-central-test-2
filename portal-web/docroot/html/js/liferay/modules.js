;(function(){
	var COMBINE = Liferay.AUI.getCombine();

	var COMBO_PATH = Liferay.AUI.getComboPath();

	var JAVASCRIPT_PATH = themeDisplay.getPathJavaScript();

	var LIFERAY_PATH = JAVASCRIPT_PATH + '/liferay/';

	var MISC_PATH = JAVASCRIPT_PATH + '/misc/';

	var GROUPS = AUI.defaults.groups;

	GROUPS.liferay = {
		base: LIFERAY_PATH,
		root: LIFERAY_PATH,
		combine: COMBINE,
		comboBase: COMBO_PATH,
		patterns: {
			'liferay-': {
				configFn: function(config) {
					var path = config.path;

					var nameRE = new RegExp(config.name + '/liferay-([A-Za-z0-9-]+)-min(\.js)');

					path = path.replace(nameRE, '$1$2');
					path = path.replace('-', '_');

					config.path = path;
				}
			}
		}
	};

	GROUPS.misc = {
		base: MISC_PATH,
		root: MISC_PATH,
		combine: COMBINE,
		comboBase: COMBO_PATH,
		modules: {
			swfupload: {
				path : 'swfupload/swfupload.js'
			},
			swfobject: {
				path: 'swfobject.js'
			}
		}
	};
})();