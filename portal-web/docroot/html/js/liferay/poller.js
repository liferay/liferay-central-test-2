(function() {
	var _browserKey = Liferay.Util.randomInt();
	var _enabled = false;
	var _supportsComet = false;
	var _encryptedUserId = null;

	var _getEncryptedUserId = function() {
		return _encryptedUserId;
	};

	var _metaData = {
		startPolling: true,
		browserKey: _browserKey,
		companyId: themeDisplay.getCompanyId()
	};

	var _portlets = {};
	var _registeredPortlets = [];
	var _requestData = [_metaData];
	var _requestDelay = 5000;
	var _suspended = false;
	var _timerId = null;
	var _url = '/poller';

	var _cancelRequestTimer = function() {
		clearTimeout(_timerId);

		_timerId = null;
	};

	var _createRequestTimer = function() {
		_cancelRequestTimer();

		if (_enabled) {
			_timerId = setTimeout(_send, Poller.getDelay());
		}
	};

	var _getUrl = function() {
		return _url;
	};

	var _processResponse = function(response) {
		if (Liferay.Util.isArray(response)) {
			var meta = response.shift();

			var chunk;

			var portletId;
			var portlet;

			for (var i = 0, length = response.length; i < length; i++) {
				chunk = response[i];

				portletId = chunk.portletId;
				portlet = _portlets[portletId];

				if (portlet) {
					portlet.listener.call(portlet.scope || Poller, chunk.data, chunk.chunkId);
				}
			}

			if ('startPolling' in _metaData) {
				delete _metaData.startPolling;
			}

			if (!meta.suspendPolling) {
				_createRequestTimer();
			}
		}
	};

	var _send = function() {
		if (!_suspended) {
			_metaData.userId = _getEncryptedUserId();
			_metaData.timestamp = (new Date()).getTime();
			_metaData.portletIds = _registeredPortlets.join(',');

			var requestStr = jQuery.toJSON(_requestData);

			_requestData = [_metaData];

			jQuery.ajax(
				{
					cache: false,
					type: 'POST',
					url: _getUrl(),
					data: {
						pollerRequest: requestStr
					},
					dataType: 'json',
					success: _processResponse
				}
			);
		}
	};

	var Poller = {
		init: function(options) {
			var instance = this;

			instance.setEncryptedUserId(options.encryptedUserId);
			instance.setSupportsComet(options.supportsComet);
		},

		url: _url,

		addListener: function(key, listener, scope) {
			if (!_enabled) {
				_enabled = true;

				_send();
			}

			_portlets[key] = {
				listener: listener,
				scope: scope
			};

			if (jQuery.inArray(key, _registeredPortlets) == -1) {
				_registeredPortlets.push(key);
			}
		},

		getDelay: function() {
			return _requestDelay;
		},

		getUrl: _getUrl,

		isSupportsComet: function() {
			return _supportsComet;
		},

		processResponse: _processResponse,

		resume: function() {
			_suspended = false;

			_createRequestTimer();
		},

		setDelay: function(delay) {
			_requestDelay = delay;
		},

		setEncryptedUserId: function(encryptedUserId) {
			_encryptedUserId = encryptedUserId;
		},

		setSupportsComet: function(supportsComet) {
			_supportsComet = supportsComet;
		},

		setUrl: function(url) {
			_url = url;
		},

		submitRequest: function(key, data, chunkId) {
			var requestData = {
				portletId: key,
				data: data
			};

			if (chunkId) {
				requestData.chunkId = chunkId;
			}

			_requestData.push(requestData);
		},

		suspend: function() {
			_cancelRequestTimer();

			_suspended = true;
		}
	};

	jQuery(document).bind(
		'focus focusin',
		function(event) {
			_metaData.startPolling = true;

			_createRequestTimer();
		}
	);

	Liferay.Poller = Poller;
})();