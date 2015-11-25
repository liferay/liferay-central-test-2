'use strict';

define("frontend-js-metal-web@1.0.0/metal/src/object/object", ['exports', 'metal/src/core'], function (exports, _core) {
	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _core2 = _interopRequireDefault(_core);

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

	var object = (function () {
		function object() {
			_classCallCheck(this, object);
		}

		object.mixin = function mixin(target) {
			var key, source;

			for (var i = 1; i < arguments.length; i++) {
				source = arguments[i];

				for (key in source) {
					target[key] = source[key];
				}
			}

			return target;
		};

		object.getObjectByName = function getObjectByName(name, opt_obj) {
			var parts = name.split('.');
			var cur = opt_obj || window;
			var part;

			while (part = parts.shift()) {
				if (_core2.default.isDefAndNotNull(cur[part])) {
					cur = cur[part];
				} else {
					return null;
				}
			}

			return cur;
		};

		return object;
	})();

	exports.default = object;
});
//# sourceMappingURL=object.js.map