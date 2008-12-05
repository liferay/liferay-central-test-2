Liferay.PortletURL = new Class({
	initialize: function(lifecycle, params) {
		var instance = this;

		instance.params = params || {};

		instance.options = {
			copyCurrentRenderParameters: null,
			doAsUserId: null,
			encrypt: null,
			escapeXML: null,
			lifecycle: lifecycle,
			name: null,
			p_l_id: themeDisplay.getPlid(),
			portletConfiguration: false,
			portletId: null,
			portletMode: null,
			resourceId: null,
			secure: null,
			windowState: null
		};

		instance._parameterMap = {
			javaClass: 'java.util.HashMap',
			map: {}
		};
	},

	setCopyCurrentRenderParameters: function(copyCurrentRenderParameters) {
		var instance = this;

		instance.options.copyCurrentRenderParameters = copyCurrentRenderParameters;
	},

	setDoAsUserId: function(doAsUserId) {
		var instance = this;

		instance.options.doAsUserId = doAsUserId;
	},

	setEncrypt: function(encrypt) {
		var instance = this;

		instance.options.encrypt = encrypt;
	},

	setEscapeXML: function(escapeXML) {
		var instance = this;

		instance.options.escapeXML = escapeXML;
	},

	setLifecycle: function(lifecycle) {
		var instance = this;

		instance.options.lifecycle = lifecycle;
	},

	setName: function(name) {
		var instance = this;

		instance.options.name = name;
	},

	setParameter: function(key, value) {
		var instance = this;

		instance.params[key] = value;
	},

	setPlid: function(plid) {
		var instance = this;

		instance.options.p_l_id = plid;
	},

	setPortletConfiguration: function(portletConfiguration) {
		var instance = this;

		instance.options.portletConfiguration = portletConfiguration;
	},

	setPortletId: function(portletId) {
		var instance = this;

		instance.options.portletId = portletId;
	},

	setPortletMode: function(portletMode) {
		var instance = this;

		instance.options.portletMode = portletMode;
	},

	setResourceId: function(resourceId) {
		var instance = this;

		instance.options.resourceId = resourceId;
	},

	setSecure: function(secure) {
		var instance = this;

		instance.options.secure = secure;
	},

	setWindowState: function(windowState) {
		var instance = this;

		instance.options.windowState = windowState;
	},

	toString: function() {
		var instance = this;

		instance._forceStringValues(instance.params);
		instance._forceStringValues(instance.options);

		jQuery.extend(
			instance._parameterMap.map,
			instance.params
		);

		var xHR = jQuery.ajax(
			{
				async: false,
				data: instance._buildRequestData(),
				type: 'GET',
				url: themeDisplay.getPathContext() + '/c/portal/portlet_url'
			}
		);

		return xHR.responseText;
	},

	_buildRequestData: function() {
		var instance = this;

		var data = {};

		jQuery.each(
			instance.options,
			function (key, value) {
				if (value !== null) {
					data[key] = [value].join('');
				}
			}
		);

		data.parameterMap = jQuery.toJSON(instance._parameterMap);

		return data;
	},

	_forceStringValues: function(obj) {
		jQuery.each(
			obj,
			function (key, value) {
				if (value !== null) {
					obj[key] = [value].join('');
				}
			}
		);

		return obj;
	}
});

jQuery.extend(
	Liferay.PortletURL,
	{
		createActionURL: function() {
			return new Liferay.PortletURL('ACTION_PHASE');
		},

		createPermissionURL: function(portletResource, modelResource, modelResourceDescription, resourcePrimKey) {
			var redirect = location.href;

			var portletURL = Liferay.PortletURL.createRenderURL();

			portletURL.setPortletId(86);

			portletURL.setWindowState('MAXIMIZED');

			portletURL.setParameter('struts_action', '/portlet_configuration/edit_permissions');
			portletURL.setParameter('redirect', redirect);

			if (!themeDisplay.isStateMaximized()) {
				portletURL.setParameter('returnToFullPageURL', redirect);
			}

			portletURL.setParameter('portletResource', portletResource);
			portletURL.setParameter('modelResource', modelResource);
			portletURL.setParameter('modelResourceDescription', modelResourceDescription);
			portletURL.setParameter('resourcePrimKey', resourcePrimKey);

			return portletURL;
		},

		createRenderURL: function() {
			return new Liferay.PortletURL('RENDER_PHASE');
		},

		createResourceURL: function() {
			return new Liferay.PortletURL('RESOURCE_PHASE');
		}
	}
);