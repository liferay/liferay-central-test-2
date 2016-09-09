define("frontend-js-spa-web@1.0.11/liferay/app/App.es", ['exports', 'senna/src/app/App', 'metal/src/core', 'metal-dom/src/dom', '../util/Utils.es', '../surface/Surface.es'], function (exports, _App2, _core, _dom, _Utils, _Surface) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _App3 = _interopRequireDefault(_App2);

	var _core2 = _interopRequireDefault(_core);

	var _dom2 = _interopRequireDefault(_dom);

	var _Utils2 = _interopRequireDefault(_Utils);

	var _Surface2 = _interopRequireDefault(_Surface);

	function _interopRequireDefault(obj) {
		return obj && obj.__esModule ? obj : {
			default: obj
		};
	}

	function _classCallCheck(instance, Constructor) {
		if (!(instance instanceof Constructor)) {
			throw new TypeError("Cannot call a class as a function");
		}
	}

	function _possibleConstructorReturn(self, call) {
		if (!self) {
			throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
		}

		return call && (typeof call === "object" || typeof call === "function") ? call : self;
	}

	function _inherits(subClass, superClass) {
		if (typeof superClass !== "function" && superClass !== null) {
			throw new TypeError("Super expression must either be null or a function, not " + typeof superClass);
		}

		subClass.prototype = Object.create(superClass && superClass.prototype, {
			constructor: {
				value: subClass,
				enumerable: false,
				writable: true,
				configurable: true
			}
		});
		if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
	}

	var LiferayApp = function (_App) {
		_inherits(LiferayApp, _App);

		function LiferayApp() {
			_classCallCheck(this, LiferayApp);

			var _this = _possibleConstructorReturn(this, _App.call(this));

			_this.portletsBlacklist = {};
			_this.validStatusCodes = [];

			_this.setShouldUseFacade(true);

			_this.timeout = Math.max(Liferay.SPA.requestTimeout, 0) || _Utils2.default.getMaxTimeout();
			_this.timeoutAlert = null;

			var exceptionsSelector = ':not([target="_blank"]):not([data-senna-off]):not([data-resource-href])';

			_this.setFormSelector('form' + exceptionsSelector);
			_this.setLinkSelector('a' + exceptionsSelector);
			_this.setLoadingCssClass('lfr-spa-loading');

			_this.on('beforeNavigate', _this.onBeforeNavigate);
			_this.on('endNavigate', _this.onEndNavigate);
			_this.on('startNavigate', _this.onStartNavigate);

			Liferay.on('io:complete', _this.onLiferayIOComplete, _this);

			var body = document.body;

			if (!body.id) {
				body.id = 'senna_surface' + _core2.default.getUid();
			}

			_this.addSurfaces(new _Surface2.default(body.id));

			_dom2.default.append(body, '<div class="lfr-spa-loading-bar"></div>');
			return _this;
		}

		LiferayApp.prototype.getCacheExpirationTime = function getCacheExpirationTime() {
			return Liferay.SPA.cacheExpirationTime;
		};

		LiferayApp.prototype.getValidStatusCodes = function getValidStatusCodes() {
			return this.validStatusCodes;
		};

		LiferayApp.prototype.isCacheEnabled = function isCacheEnabled() {
			return this.getCacheExpirationTime() > -1;
		};

		LiferayApp.prototype.isInPortletBlacklist = function isInPortletBlacklist(element) {
			return Object.keys(this.portletsBlacklist).some(function (portletId) {
				var boundaryId = _Utils2.default.getPortletBoundaryId(portletId);

				var portlets = document.querySelectorAll('[id^="' + boundaryId + '"]');

				return Array.prototype.slice.call(portlets).some(function (portlet) {
					return _dom2.default.contains(portlet, element);
				});
			});
		};

		LiferayApp.prototype.isScreenCacheExpired = function isScreenCacheExpired(screen) {
			if (this.getCacheExpirationTime() === 0) {
				return false;
			}

			var lastModifiedInterval = new Date().getTime() - screen.getCacheLastModified();

			return lastModifiedInterval > this.getCacheExpirationTime();
		};

		LiferayApp.prototype.onBeforeNavigate = function onBeforeNavigate(data, event) {
			if (Liferay.SPA.clearScreensCache || data.form) {
				this.clearScreensCache();
			}

			Liferay.fire('beforeNavigate', {
				originalEvent: event,
				app: this,
				path: data.path
			});
		};

		LiferayApp.prototype.onDocClickDelegate_ = function onDocClickDelegate_(event) {
			if (this.isInPortletBlacklist(event.delegateTarget)) {
				return;
			}

			_App.prototype.onDocClickDelegate_.call(this, event);
		};

		LiferayApp.prototype.onDocSubmitDelegate_ = function onDocSubmitDelegate_(event) {
			if (this.isInPortletBlacklist(event.delegateTarget)) {
				return;
			}

			_App.prototype.onDocSubmitDelegate_.call(this, event);
		};

		LiferayApp.prototype.onEndNavigate = function onEndNavigate(event) {
			Liferay.fire('endNavigate', {
				app: this,
				error: event.error,
				path: event.path
			});

			if (!this.pendingNavigate) {
				this._clearRequestTimer();
				this._hideTimeoutAlert();
			}

			if (event.error) {
				if (event.error.invalidStatus || event.error.requestError || event.error.timeout) {
					if (event.form) {
						event.form.submit();
					} else {
						window.location.href = event.path;
					}
				}
			} else if (Liferay.Layout && Liferay.Data.layoutConfig) {
				Liferay.Layout.init();
			}

			AUI().Get._insertCache = {};

			Liferay.DOMTaskRunner.reset();
		};

		LiferayApp.prototype.onLiferayIOComplete = function onLiferayIOComplete() {
			this.clearScreensCache();
		};

		LiferayApp.prototype.onStartNavigate = function onStartNavigate(event) {
			Liferay.fire('startNavigate', {
				app: this,
				path: event.path
			});

			this._startRequestTimer(event.path);
		};

		LiferayApp.prototype.setPortletsBlacklist = function setPortletsBlacklist(portletsBlacklist) {
			this.portletsBlacklist = portletsBlacklist;
		};

		LiferayApp.prototype.setValidStatusCodes = function setValidStatusCodes(validStatusCodes) {
			this.validStatusCodes = validStatusCodes;
		};

		LiferayApp.prototype._clearRequestTimer = function _clearRequestTimer() {
			if (this.requestTimer) {
				clearTimeout(this.requestTimer);
			}
		};

		LiferayApp.prototype._createTimeoutNotification = function _createTimeoutNotification() {
			var instance = this;

			AUI().use('liferay-notification', function () {
				instance.timeoutAlert = new Liferay.Notification({
					closeable: true,
					delay: {
						hide: 0,
						show: 0
					},
					duration: 500,
					message: Liferay.SPA.userNotification.message,
					title: Liferay.SPA.userNotification.title,
					type: 'warning'
				}).render('body');
			});
		};

		LiferayApp.prototype._hideTimeoutAlert = function _hideTimeoutAlert() {
			if (this.timeoutAlert) {
				this.timeoutAlert.hide();
			}
		};

		LiferayApp.prototype._startRequestTimer = function _startRequestTimer(path) {
			var _this2 = this;

			this._clearRequestTimer();

			if (Liferay.SPA.userNotification.timeout > 0) {
				this.requestTimer = setTimeout(function () {
					Liferay.fire('spaRequestTimeout', {
						path: path
					});

					if (!_this2.timeoutAlert) {
						_this2._createTimeoutNotification();
					} else {
						_this2.timeoutAlert.show();
					}
				}, Liferay.SPA.userNotification.timeout);
			}
		};

		return LiferayApp;
	}(_App3.default);

	exports.default = LiferayApp;
});
//# sourceMappingURL=App.es.js.map