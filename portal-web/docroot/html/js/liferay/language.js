Liferay.Language = {

	get: function(key, extraParams) {
		var instance = this;

		var url = '/language/' + themeDisplay.getLanguageId() + '/' + key + '/';
		
		var theKey = '';

		if (extraParams) {
			if (typeof extraParams == 'string') {
				url += extraParams;
			} else if (Liferay.Util.isArray(extraParams)) {
				url += extraParams.join('/');
			}
		}

		if (instance._cache[url] != null) {
			return instance._cache[url];
		}

		var xHR = jQuery.ajax(
			{
				url: url,
				async: false
			}
		);

		theKey = xHR.responseText;

		instance._cache[url] = theKey;

		return theKey;
	},

	_cache: {}

};