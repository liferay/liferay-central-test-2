// This file was automatically generated from field_options_toolbar.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddl.
 * @public
 */

if (typeof ddl == 'undefined') { var ddl = {}; }


ddl.field_settings_toolbar = function(opt_data, opt_ignored) {
  return '<div class="dropdown open"><a class="dropdown-toggle icon-monospaced" data-toggle="dropdown" href="javascript:;">' + soy.$$filterNoAutoescape(opt_data.toolbarButtonIcon) + '</a>' + ddl.field_settings_toolbar_list({options: opt_data.toolbarTemplateContext.options}) + '</div>';
};
if (goog.DEBUG) {
  ddl.field_settings_toolbar.soyTemplateName = 'ddl.field_settings_toolbar';
}


ddl.field_settings_toolbar_list = function(opt_data, opt_ignored) {
  var output = '<ul class="dropdown-menu dropdown-menu-right">';
  var optionList14 = opt_data.options;
  var optionListLen14 = optionList14.length;
  for (var optionIndex14 = 0; optionIndex14 < optionListLen14; optionIndex14++) {
    var optionData14 = optionList14[optionIndex14];
    output += ddl.field_settings_toolbar_item({option: optionData14});
  }
  output += '</ul>';
  return output;
};
if (goog.DEBUG) {
  ddl.field_settings_toolbar_list.soyTemplateName = 'ddl.field_settings_toolbar_list';
}


ddl.field_settings_toolbar_item = function(opt_data, opt_ignored) {
  return '<li><a class="' + soy.$$escapeHtmlAttribute(opt_data.option.buttonClass || '') + '" data-handler="' + soy.$$escapeHtmlAttribute(opt_data.option.handler) + '" href="javascript:;" title="' + soy.$$escapeHtmlAttribute(opt_data.option.label) + '">' + soy.$$escapeHtml(opt_data.option.label) + '</a></li>';
};
if (goog.DEBUG) {
  ddl.field_settings_toolbar_item.soyTemplateName = 'ddl.field_settings_toolbar_item';
}
