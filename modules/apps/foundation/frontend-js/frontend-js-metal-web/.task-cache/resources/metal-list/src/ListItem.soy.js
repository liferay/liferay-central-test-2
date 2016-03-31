define("frontend-js-metal-web@1.0.6/metal-list/src/ListItem.soy", ['exports', 'metal-component/src/all/component', 'metal-soy/src/soy'], function (exports, _component, _soy) {
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
  // This file was automatically generated from ListItem.soy.
  // Please don't edit this file by hand.

  /**
   * @fileoverview Templates in namespace Templates.ListItem.
   */

  if (typeof Templates.ListItem == 'undefined') {
    Templates.ListItem = {};
  }

  /**
   * @param {Object.<string, *>=} opt_data
   * @param {(null|undefined)=} opt_ignored
   * @param {Object.<string, *>=} opt_ijData
   * @return {!soydata.SanitizedHtml}
   * @suppress {checkTypes}
   */
  Templates.ListItem.render = function (opt_data, opt_ignored, opt_ijData) {
    return soydata.VERY_UNSAFE.ordainSanitizedHtml('<li id="' + soy.$$escapeHtmlAttribute(opt_data.id) + '" class="listitem list-group-item component' + soy.$$escapeHtmlAttribute(opt_data.elementClasses ? ' ' + opt_data.elementClasses : '') + ' clearfix" data-index="' + soy.$$escapeHtmlAttribute(opt_data.index) + '">' + Templates.ListItem.item(opt_data, null, opt_ijData) + '</li>');
  };
  if (goog.DEBUG) {
    Templates.ListItem.render.soyTemplateName = 'Templates.ListItem.render';
  }

  /**
   * @param {Object.<string, *>=} opt_data
   * @param {(null|undefined)=} opt_ignored
   * @param {Object.<string, *>=} opt_ijData
   * @return {!soydata.SanitizedHtml}
   * @suppress {checkTypes}
   */
  Templates.ListItem.item = function (opt_data, opt_ignored, opt_ijData) {
    var output = (opt_data.item.avatar ? '<span class="list-image pull-left ' + soy.$$escapeHtmlAttribute(opt_data.item.avatar['class']) + '">' + soy.$$escapeHtml(opt_data.item.avatar.content) + '</span>' : '') + '<div class="list-main-content pull-left"><div class="list-text-primary">' + soy.$$escapeHtml(opt_data.item.textPrimary) + '</div>' + (opt_data.item.textSecondary ? '<div class="list-text-secondary">' + soy.$$escapeHtml(opt_data.item.textSecondary) + '</div>' : '') + '</div>';
    if (opt_data.item.icons) {
      var iconList55 = opt_data.item.icons;
      var iconListLen55 = iconList55.length;
      for (var iconIndex55 = 0; iconIndex55 < iconListLen55; iconIndex55++) {
        var iconData55 = iconList55[iconIndex55];
        output += '<span class="btn-icon ' + soy.$$escapeHtmlAttribute(iconData55) + ' pull-right"></span>';
      }
    }
    if (opt_data.item.iconsHtml) {
      output += '<div class="pull-right">';
      var iconHtmlList63 = opt_data.item.iconsHtml;
      var iconHtmlListLen63 = iconHtmlList63.length;
      for (var iconHtmlIndex63 = 0; iconHtmlIndex63 < iconHtmlListLen63; iconHtmlIndex63++) {
        var iconHtmlData63 = iconHtmlList63[iconHtmlIndex63];
        output += soy.$$escapeHtml(iconHtmlData63);
      }
      output += '</div>';
    }
    output += opt_data.item.label ? '<span class="label list-label pull-right ' + soy.$$escapeHtmlAttribute(opt_data.item.label['class']) + '">' + soy.$$escapeHtml(opt_data.item.label.content) + '</span>' : '';
    return soydata.VERY_UNSAFE.ordainSanitizedHtml(output);
  };
  if (goog.DEBUG) {
    Templates.ListItem.item.soyTemplateName = 'Templates.ListItem.item';
  }

  Templates.ListItem.render.params = ["id", "index", "item"];
  Templates.ListItem.item.params = ["item"];

  var ListItem = function (_Component) {
    _inherits(ListItem, _Component);

    function ListItem() {
      _classCallCheck(this, ListItem);

      return _possibleConstructorReturn(this, _Component.apply(this, arguments));
    }

    return ListItem;
  }(_component2.default);

  ListItem.prototype.registerMetalComponent && ListItem.prototype.registerMetalComponent(ListItem, 'ListItem')

  ListItem.RENDERER = _soy.SoyRenderer;
  _soy.SoyAop.registerTemplates('ListItem');
  exports.default = ListItem;
});
//# sourceMappingURL=ListItem.soy.js.map