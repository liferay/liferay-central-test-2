AUI.add(
	'liferay-store',
	function(A) {
		var Lang = A.Lang;

		var isObject = Lang.isObject;

		var TOKEN_SERIALIZE = 'serialize://';

		var Store = function(key, value) {
			var config = {
				after: {},
				data: {}
			};

			if (Lang.isFunction(value)) {
				var cmd = 'get';
				config.data.key = key;

				if (Lang.isArray(key)) {
					cmd = 'getAll';
					config.dataType = 'json';
				}

				config.data.cmd = cmd;

				var callback = value;

				config.after.success = function(event) {
					var responseData = this.get('responseData');

					if (Lang.isString(responseData) && responseData.indexOf(TOKEN_SERIALIZE) === 0) {
						try {
							responseData = A.JSON.parse(responseData.substring(TOKEN_SERIALIZE.length));
						}
						catch (e) {
						}
					}

					callback(responseData);
				};
			}
			else {
				if (isObject(key)) {
					config.data = key;
				}
				else {
					config.data[key] = value;
				}

				if (isObject(value)) {
					config.data[key] = TOKEN_SERIALIZE + A.JSON.stringify(value);
				}
			}

			A.io.request(
				themeDisplay.getPathMain() + '/portal/session_click',
				config
			);
		};

		Liferay.Store = Store;
	},
	'',
	{
		requires: ['aui-io-request']
	}
);