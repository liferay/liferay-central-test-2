(function() {
	Alloy.Class = function(properties) {
		var instance = this;

		var superclass = instance;

		if (typeof properties == 'function') {
			var initialize = properties;

			properties = properties.prototype;

			properties.initialize = initialize;

			superclass = initialize.superclass || superclass;
		}

		if (!properties.implement) {
			properties.implement = function(options) {
				var instance = this;

				var args = Array.prototype.slice.call(arguments, 0);

				args.unshift(instance);
				Alloy.extend.apply(instance, args);

				return instance;
			};
		}

		var Class = function(args) {
			if (this instanceof arguments.callee) {
				var instance = this;

				if (typeof properties == 'function') {
					var initialize = properties;

					properties = properties.prototype;

					properties.initialize = initialize;
				}

				var formalArguments = arguments;
				var firstArgument = arguments[0];

				if (args && args.callee) {
					formalArguments = args;

					if (formalArguments[0]) {
						firstArgument = formalArguments[0];
					}
				}

				if (firstArgument != 'noinit' && instance.initialize) {
					var returnVal;

					if (instance._beforeconstructor_) {
						formalArguments = instance._beforeconstructor_.apply(instance, formalArguments) || formalArguments;
					}

					returnVal = instance.initialize.apply(instance, formalArguments);

					if (instance._afterconstructor_) {
						returnVal = instance._afterconstructor_.apply(instance, formalArguments) || returnVal;
					}

					return returnVal;
				}
			}
			else {
				return new arguments.callee(arguments);
			}
		};

		Class.extend = this.extend;
		Class.implement = this.implement;
		Class.mixin = this.mixin;
		Class.prototype = properties;

		Class.prototype.superclass = superclass;
		Class.superclass = superclass;

		var currentConstructor = Class.prototype.constructor;

		Class.prototype.constructor = (currentConstructor == Object.prototype.constructor) ? Class : currentConstructor;
		Class.constructor = Class;

		return Class;
	};

	Alloy.Class.prototype = {
		extend: function(properties) {
			var instance = this;

			var proto = new instance('noinit');

			for (var property in properties) {
				var previous = proto[property];
				var current = properties[property];

				if (previous && typeof previous == 'function' && previous != current) {
					current = Alloy.Class.createSuper(previous, current) || current;
				}

				proto[property] = current;
			}

			var Class = new Alloy.Class(proto);

			Class.prototype.superclass = instance.constructor;
			Class.superclass = instance.constructor;

			var currentConstructor = Class.prototype.constructor;

			Class.prototype.constructor = Class;
			Class.constructor = Class;

			return Class;
		},

		implement: function(properties) {
			var instance = this;

			return YAHOO.lang.augmentObject(instance.prototype, properties, true);
		},

		mixin: function(module, override) {
			var instance = this;

			return YAHOO.lang.augmentProto(instance, module, override);
		}
	};

	Alloy.Class.createSuper = function(previous, current) {
		return function() {
			this._super = previous;

			return current.apply(this, arguments);
		}
	};
})();