$ = null;

Function.prototype.extendNativeFunctionObject = jQuery.extend;

jQuery.getOne = function(s, context) {
	var rt;

	if (typeof s == 'object') {
		rt = s;
	}
	else if (typeof s == 'string') {
		if (s.search(/^[#.]/) == -1) {
			s = '#' + s;
		}

		if (context == null) {
			rt = jQuery(s);
		}
		else {
			rt = jQuery(s, context);
		}

		if (rt.length > 0) {
			rt = rt.get(0);
		}
		else {
			rt = null;
		}
	}

	return rt;
};

jQuery.fn.getOne = function(s) {
	return jQuery.getOne(s, this);
};

Liferay = function() {
	var $ = jQuery;

	return {};
}();

Liferay.Service = {
	url: themeDisplay.getPathMain() + "/portal/json_service",

	classNameSuffix: "ServiceJSON",

	ajax: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);

		if (callback) {
			jQuery.ajax(
				{
					type: 'GET',
					url: Liferay.Service.url,
					data: params,
					dataType: 'json',
					beforeSend: function(xHR) {
						if (Liferay.ServiceAuth.header) {
							xHR.setRequestHeader('Authorization', Liferay.ServiceAuth.header);
						}
					},
					success: callback
				}
			);
		}
		else {
			var xHR = jQuery.ajax(
				{
					url: Liferay.Service.url,
					data: params,
					dataType: 'json',
					async: false
				}
			);

			return eval("(" + xHR.responseText + ")");
		}
	},

	getParameters: function(params) {
		var serviceParameters = "";

		for (var key in params) {
			if ((key != "serviceClassName") && (key != "serviceMethodName")) {
				serviceParameters += key + ",";
			}
		}

		if (Liferay.Util.endsWith(serviceParameters, ",")) {
			serviceParameters = serviceParameters.substring(0, serviceParameters.length - 1);
		}

		return serviceParameters;
	}
};

Liferay.ServiceAuth = {
	header: null,

	setHeader: function(userId, password) {
		header = "Basic " + Base64.encode(userId + ':' + password);
	}
};

jQuery.fn.exactHeight = jQuery.fn.height;
jQuery.fn.exactWidth = jQuery.fn.width;

jQuery.each( [ 'height', 'width' ], function(i,n){
	jQuery.fn[ n ] = function(h) {
		return h == undefined ?
			( this.length ? (n == 'height' ? this[0].offsetHeight : this[0].offsetWidth) : null ) :
			this.css( n, h.constructor == String ? h : h + "px" );
	};
});