define("frontend-js-metal-web@1.0.4/metal-dropdown/src/Dropdown.soy", ['exports', 'metal-component/src/all/component', 'metal-soy/src/soy'], function (exports, _component, _soy) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });

  var _component2 = _interopRequireDefault(_component);

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

  var Templates = _soy.SoyTemplates.get();
  // This file was automatically generated from Dropdown.soy.
  // Please don't edit this file by hand.

  /**
   * @fileoverview Templates in namespace Templates.Dropdown.
   */

  if (typeof Templates.Dropdown == 'undefined') {
    Templates.Dropdown = {};
  }

  /**
   * @param {Object.<string, *>=} opt_data
   * @param {(null|undefined)=} opt_ignored
   * @param {Object.<string, *>=} opt_ijData
   * @return {!soydata.SanitizedHtml}
   * @suppress {checkTypes}
   */
  Templates.Dropdown.render = function (opt_data, opt_ignored, opt_ijData) {
    return soydata.VERY_UNSAFE.ordainSanitizedHtml('<div id="' + soy.$$escapeHtmlAttribute(opt_data.id) + '" class="dropdown component' + soy.$$escapeHtmlAttribute(opt_data.elementClasses ? ' ' + opt_data.elementClasses : '') + soy.$$escapeHtmlAttribute(opt_data.expanded ? ' open' : '') + '">' + (opt_data.header ? soy.$$escapeHtml(opt_data.header) : '') + Templates.Dropdown.body(opt_data, null, opt_ijData) + '</div>');
  };
  if (goog.DEBUG) {
    Templates.Dropdown.render.soyTemplateName = 'Templates.Dropdown.render';
  }

  /**
   * @param {Object.<string, *>=} opt_data
   * @param {(null|undefined)=} opt_ignored
   * @param {Object.<string, *>=} opt_ijData
   * @return {!soydata.SanitizedHtml}
   * @suppress {checkTypes}
   */
  Templates.Dropdown.body = function (opt_data, opt_ignored, opt_ijData) {
    return soydata.VERY_UNSAFE.ordainSanitizedHtml('<ul id="' + soy.$$escapeHtmlAttribute(opt_data.id) + '-body" class="dropdown-menu">' + (opt_data.body ? soy.$$escapeHtml(opt_data.body) : '') + '</ul>');
  };
  if (goog.DEBUG) {
    Templates.Dropdown.body.soyTemplateName = 'Templates.Dropdown.body';
  }

  Templates.Dropdown.render.params = ["header", "id"];
  Templates.Dropdown.body.params = ["body", "id"];

  var Dropdown = function (_Component) {
    _inherits(Dropdown, _Component);

    function Dropdown() {
      _classCallCheck(this, Dropdown);

      return _possibleConstructorReturn(this, _Component.apply(this, arguments));
    }

    return Dropdown;
  }(_component2.default);

  Dropdown.prototype.registerMetalComponent && Dropdown.prototype.registerMetalComponent(Dropdown, 'Dropdown')

  Dropdown.RENDERER = _soy.SoyRenderer;
  _soy.SoyAop.registerTemplates('Dropdown');
  exports.default = Dropdown;
});
//# sourceMappingURL=Dropdown.soy.js.map