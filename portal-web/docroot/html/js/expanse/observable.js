Expanse.Observable = new Expanse.Class(
	{
		initialize: function() {
			var instance = this;

			instance._events = {};
		},

		add: function(key, options) {
			var instance = this;

			instance._checkConfig(key, options);
		},

		bind: function(event, handler, scope) {
			var instance = this;

			if (handler && event) {
				var customEvent = instance._getEventObject(event);

				customEvent.subscribe(handler, scope, !!scope);
			}
		},

		get: function(key, defaultValue, setToDefault) {
			var instance = this;

			instance._checkConfig(key);

			var value = instance.cfg.getProperty(key);

			if (YAHOO.lang.isFunction(value)) {
				value = value.call(instance, key, defaultValue);
			}

			if (YAHOO.lang.isUndefined(value) && !YAHOO.lang.isUndefined(defaultValue)) {
				value = defaultValue;

				if (setToDefault) {
					instance.set(key, value, true);
				}
			}

			return instance.cfg.getProperty(key);
		},

		listen: function(key, handler) {
			var instance = this;

			instance._checkConfig(key);

			return instance.cfg.subscribeToConfigEvent.apply(instance.cfg, arguments);
		},

		resumeEvents: function() {
			var instance = this;

			instance._eventsSuspended = false;
		},

		set: function(key, value, silent, options) {
			var instance = this;

			var propertySet = false;

			if (YAHOO.lang.isObject(key) && arguments.length <= 2) {
				silent = value;

				for (var i in key) {
					propertySet = arguments.callee.call(instance, i, key[i], silent);
				}
			}
			else {
				instance._checkConfig(key, options);

				propertySet = instance.cfg.setProperty(key, value, silent);
			}

			return propertySet;
		},

		suspendEvents: function() {
			var instance = this;

			instance._eventsSuspended = true;
		},

		trigger: function(event, data) {
			var instance = this;

			var returnValue = false;

			if (instance._eventsSuspended == false) {
				var customEvent = instance._getEventObject(event);
				var args = Array.prototype.slice.call(arguments, 1);

				returnValue = customEvent.fire.apply(customEvent, args);
			}

			return returnValue;
		},

		unbind: function(event, handler, scope) {
			var instance = this;

			if (event) {
				var customEvent = instance._getEventObject(event);

				if (handler) {
					customEvent.unsubscribe(handler, scope);
				}
				else {
					customEvent.unsubscribeAll();
				}
			}
		},

		unlisten: function(key, handler) {
			var instance = this;

			instance._checkConfig(key);

			if (key && !handler) {
				var config = instance.cfg.config;

				var propertyName = key.toLowerCase();
				var property = config[propertyName];

				if (property && property.event) {
					property.event.unsubscribeAll()
				}
			}
			else {
				instance.cfg.unsubscribeFromConfigEvent.apply(instance.cfg, arguments);
			}
		},

		_beforeconstructor_: function() {
			var instance = this;

			instance.cfg = new YAHOO.util.Config(instance);
		},

		_checkConfig: function(key, options) {
			var instance = this;

			var config = instance.cfg.getConfig();

			if (!(key.toLowerCase() in config)) {
				if (!options) {
					options = {};
				}
				else if(YAHOO.lang.isFunction(options)) {
					var handler = options;

					options = {
						handler: handler
					};
				}

				instance.cfg.addProperty(key, options);
			}
		},

		_createEventObj: function() {
			var instance = this;

			if (!instance._events) {
				instance._events = {};
			}
		},

		_getEventObject: function(event) {
			var instance = this;

			instance._createEventObj();

			if (!instance._events[event]) {
				instance._events[event] = new Expanse.CustomEvent(event, instance, true);
			}

			return instance._events[event];
		},

		_eventsSuspended: false
	}
);

Expanse.Observable.prototype.on = Expanse.Observable.prototype.bind;
Expanse.Observable.prototype.un = Expanse.Observable.prototype.unbind;