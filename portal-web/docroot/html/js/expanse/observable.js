Expanse.Observable = new Expanse.Class(
	{
		initialize: function() {
			var instance = this;

			instance._events = {};
		},

		bind: function(event, handler, scope) {
			var instance = this;

			if (handler && event) {
				var customEvent = instance._getEventObject(event);

				customEvent.subscribe(handler, scope, !!scope);
			}

		},

		get: function(key, defaultValue) {
			var instance = this;

			instance._checkConfig(key);

			var value = instance.cfg.getProperty(key);

			if (value === undefined && defaultValue) {
				value = defaultValue;
			}

			return value;
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

		set: function(key, value, silent) {
			var instance = this;

			instance._checkConfig(key);

			instance.cfg.setProperty(key, value, silent);
		},

		suspendEvents: function() {
			var instance = this;

			instance._eventsSuspended = true;
		},

		trigger: function(event, data) {
			var instance = this;

			if (instance._eventsSuspended == false) {
				var customEvent = instance._getEventObject(event);
				var args = Array.prototype.slice.call(arguments, 1);

				customEvent.fire.apply(customEvent, args);
			}
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

		_checkConfig: function(key) {
			var instance = this;

			if (!instance.cfg) {
				instance.cfg = new YAHOO.util.Config(instance);
			}

			var config = instance.cfg.getConfig();

			if (!config[key.toLowerCase()]) {
				instance.cfg.addProperty(key, {});
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