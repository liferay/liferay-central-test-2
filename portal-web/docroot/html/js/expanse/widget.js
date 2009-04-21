(function() {
	Expanse.Widget = function(properties) {
		var Class = properties;

		if (!(properties instanceof Expanse.Class)) {
			Class = new Expanse.Class(properties);
		}

		Class.prototype.initialize = function(el, options) {
			this.options = options;

			this.constructor.apply(this, arguments);

			this.configureCallbacks();
		};

		Class.prototype.configureCallbacks = function() {
			var callbacks = this.options.on;

			if (callbacks) {
				var callback;
				var eventObj;

				for (var i in callbacks) {
					eventObj = this[i + 'Event'];
					callback = callbacks[i];

					if (eventObj) {
						eventObj.subscribe(callback);
					}
				}
			}
		};

		return Class;
	};

	Expanse.Widget.prototype = new Expanse.Class({});
})();