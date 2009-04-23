(function() {
	Expanse.Widget = function(properties) {
		var Class = properties;

		if (!(properties instanceof Expanse.Class)) {
			Class = new Expanse.Class(properties);
		}

		var widgetConstructor = function(el, options) {
			this.options = options || {};

			if (this.initEvents) {
				this.initEvents();
			}

			this.configureCallbacks();

			this._super.apply(this, arguments);
		};

		if (Class.prototype.initialize) {
			widgetConstructor = Expanse.Class.createSuper(Class.prototype.initialize, widgetConstructor);
		}

		Class.prototype.initialize = widgetConstructor;

		Class.prototype.configureCallbacks = function() {
			var callbacks = this.options.on;

			if (callbacks) {
				var callback;
				var eventObj;

				for (var i in callbacks) {
					eventObj = this[i + 'Event'];
					callback = callbacks[i];

					if (eventObj && callback) {
						eventObj.subscribe(callback);
					}
				}
			}
		};

		return Class;
	};

	Expanse.Widget.prototype = new Expanse.Class({});
})();