Expanse.extend(Function.prototype,
	{
		parentize: function(current) {
			var instance = this;

			return function() {
				this.parent = instance;

				return current.apply(this, arguments);
			}
		}
	}
);

Expanse.Class = function(properties) {
	var instance = this;

	if (typeof properties == 'function') {
		var initialize = properties;

		properties = properties.prototype;

		properties.initialize = initialize;
	}

	if (!properties.implement) {
		properties.implement = function(options) {
			var instance = this;

			var args = Array.prototype.slice.call(arguments, 0);

			args.unshift(instance);
			Expanse.extend.apply(instance, args);

			return instance;
		};
	}

	var Class = function(args) {
		var instance = this;

		if (typeof properties == 'function') {
			var initialize = properties;

			properties = properties.prototype;

			properties.initialize = initialize;
		}

		for (var p in instance) {
			if (instance[p] && typeof instance[p] == 'function') {
				instance[p]._proto_ = instance;
			}
		}

		if (this instanceof arguments.callee) {
			var formalArguments = arguments;
			var firstArgument = arguments[0];

			if (args && args.callee) {
				formalArguments = args;

				if (formalArguments[0]) {
					firstArgument = formalArguments[0];
				}
			}

			if (firstArgument != 'noinit' && instance.initialize) {
				return instance.initialize.apply(instance, formalArguments);
			}
		}
		else {
			return new arguments.callee(arguments);
		}
	};

	Class.extend = this.extend;
	Class.implement = this.implement;
	Class.prototype = properties;

	Class.prototype.superclass = instance;
	Class.superclass = instance;

	Class.prototype.constructor = Class;
	Class.constructor = Class;

	return Class;
};

Expanse.Class.prototype = {
	extend: function(properties) {
		var instance = this;

		var proto = new instance('noinit');

		for (var property in properties) {
			var previous = proto[property];
			var current = properties[property];

			if (typeof previous == 'function' && previous && previous != current) {
				current = previous.parentize(current) || current;
			}

			proto[property] = current;
		}

		var Class = new Expanse.Class(proto);

		Class.prototype.superclass = instance;
		Class.superclass = instance;

		Class.prototype.constructor = Class;
		Class.constructor = Class;

		return Class;
	},

	implement: function(properties) {
		var instance = this;

		for (var property in properties) {
			instance.prototype[property] = properties[property];
		}
	}
};