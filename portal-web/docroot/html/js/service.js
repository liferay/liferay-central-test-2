Liferay.Service = {
	url: themeDisplay.getPathMain() + "/portal/json_service",

	classNameSuffix: "ServiceJSON",

	getParameters: function(params) {
		var serviceParameters = "";

		for (var key in params) {
			serviceParameters += key + ",";
		}

		if (endsWith(serviceParameters, ",")) {
			serviceParameters = serviceParameters.substring(0, serviceParameters.length - 1);
		}

		return serviceParameters;
	}
};

Liferay.Service.Tags = {
	servicePackage: "com.liferay.portlet.tags.service.http."
};

Liferay.Service.Tags.TagsAsset = {
	serviceClassName: Liferay.Service.Tags.servicePackage + "TagsAsset" + Liferay.Service.classNameSuffix,

	deleteAsset: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteAsset";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getAsset: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getAsset";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Tags.TagsEntry = {
	serviceClassName: Liferay.Service.Tags.servicePackage + "TagsEntry" + Liferay.Service.classNameSuffix,

	addEntry: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addEntry";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteEntry: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteEntry";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	search: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "search";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	searchCount: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "searchCount";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateEntry: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateEntry";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};

Liferay.Service.Tags.TagsProperty = {
	serviceClassName: Liferay.Service.Tags.servicePackage + "TagsProperty" + Liferay.Service.classNameSuffix,

	addProperty: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "addProperty";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	deleteProperty: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "deleteProperty";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getProperties: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getProperties";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	getPropertyValues: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "getPropertyValues";

		_$J.getJSON(Liferay.Service.url, params, callback);
	},

	updateProperty: function(params, callback) {
		params.serviceParameters = Liferay.Service.getParameters(params);
		params.serviceClassName = this.serviceClassName;
		params.serviceMethodName = "updateProperty";

		_$J.getJSON(Liferay.Service.url, params, callback);
	}
};