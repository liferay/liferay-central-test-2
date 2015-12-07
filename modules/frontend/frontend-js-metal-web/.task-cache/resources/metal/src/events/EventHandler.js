'use strict';

function _typeof(obj) { return obj && typeof Symbol !== "undefined" && obj.constructor === Symbol ? "symbol" : typeof obj; }

define("frontend-js-metal-web@1.0.0/metal/src/events/EventHandler", ['exports', 'metal/src/disposable/Disposable'], function (exports, _Disposable2) {
	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _Disposable3 = _interopRequireDefault(_Disposable2);

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

		return call && ((typeof call === 'undefined' ? 'undefined' : _typeof(call)) === "object" || typeof call === "function") ? call : self;
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

	var EventHandler = (function (_Disposable) {
		_inherits(EventHandler, _Disposable);

		function EventHandler() {
			_classCallCheck(this, EventHandler);

			var _this = _possibleConstructorReturn(this, _Disposable.call(this));

			_this.eventHandles_ = [];
			return _this;
		}

		EventHandler.prototype.add = function add() {
			for (var i = 0; i < arguments.length; i++) {
				this.eventHandles_.push(arguments[i]);
			}
		};

		EventHandler.prototype.disposeInternal = function disposeInternal() {
			this.eventHandles_ = null;
		};

		EventHandler.prototype.removeAllListeners = function removeAllListeners() {
			for (var i = 0; i < this.eventHandles_.length; i++) {
				this.eventHandles_[i].removeListener();
			}

			this.eventHandles_ = [];
		};

		return EventHandler;
	})(_Disposable3.default);

	EventHandler.prototype.registerMetalComponent && EventHandler.prototype.registerMetalComponent(EventHandler, 'EventHandler')
	exports.default = EventHandler;
});
//# sourceMappingURL=EventHandler.js.map