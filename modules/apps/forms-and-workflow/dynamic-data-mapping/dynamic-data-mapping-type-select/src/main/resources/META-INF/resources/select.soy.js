// This file was automatically generated from select.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddm.
 * @hassoydeltemplate {ddm.field}
 * @public
 */

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.__deltemplate_s2_2dbfb377 = function(opt_data, opt_ignored) {
  return '' + ddm.select(opt_data);
};
if (goog.DEBUG) {
  ddm.__deltemplate_s2_2dbfb377.soyTemplateName = 'ddm.__deltemplate_s2_2dbfb377';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field'), 'select', 0, ddm.__deltemplate_s2_2dbfb377);


ddm.select = function(opt_data, opt_ignored) {
  var output = '<div class="form-group' + soy.$$escapeHtmlAttribute(opt_data.visible ? '' : ' hide') + '" data-fieldname="' + soy.$$escapeHtmlAttribute(opt_data.name) + '"><div class="input-select-wrapper">' + ((opt_data.showLabel) ? ddm.select_label(opt_data) : '') + '<div class="form-builder-select-field input-group-container">' + ((! opt_data.readOnly) ? ddm.hidden_select(opt_data) : '') + '<div class="form-control select-field-trigger" dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" id="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + ((opt_data.selectCaretDoubleIcon) ? '<a class="select-arrow-down-container" href="javascript:;">' + soy.$$filterNoAutoescape(opt_data.selectCaretDoubleIcon) + '</a>' : '');
  if (opt_data.readOnly) {
    output += '<span class="option-selected option-selected-placeholder">' + soy.$$escapeHtml(opt_data.multiple ? opt_data.strings.chooseOptions : opt_data.strings.chooseAnOption) + '</span>';
  } else {
    if (opt_data.multiple) {
      if (opt_data.value.length == 0) {
        output += '<span class="option-selected option-selected-placeholder">' + soy.$$escapeHtml(opt_data.strings.chooseOptions) + '</span>';
      } else {
        output += '<ul class="multiple-badge-list">';
        var currentValueList49 = opt_data.value;
        var currentValueListLen49 = currentValueList49.length;
        for (var currentValueIndex49 = 0; currentValueIndex49 < currentValueListLen49; currentValueIndex49++) {
          var currentValueData49 = currentValueList49[currentValueIndex49];
          output += ddm.badge_item({badgeCloseIcon: opt_data.badgeCloseIcon, value: currentValueData49.value, label: currentValueData49.label});
        }
        output += '</ul>';
      }
    } else {
      if (opt_data.value && opt_data.value.length > 0) {
        var currentValueList60 = opt_data.value;
        var currentValueListLen60 = currentValueList60.length;
        for (var currentValueIndex60 = 0; currentValueIndex60 < currentValueListLen60; currentValueIndex60++) {
          var currentValueData60 = currentValueList60[currentValueIndex60];
          output += (currentValueData60 && currentValueData60.label) ? '<span class="option-selected">' + soy.$$escapeHtml(currentValueData60.label) + '</span>' : '';
        }
      } else {
        output += '<span class="option-selected option-selected-placeholder">' + soy.$$escapeHtml(opt_data.strings.chooseAnOption) + '</span>';
      }
    }
  }
  output += '</div>' + ((! opt_data.readOnly) ? '<div class="drop-chosen ' + soy.$$escapeHtmlAttribute(opt_data.multiple && opt_data.value.length > 0 ? '' : 'hide') + '"><div class="search-chosen"><div class="select-search-container">' + ((opt_data.selectSearchIcon) ? '<a class="" href="javascript:;">' + soy.$$filterNoAutoescape(opt_data.selectSearchIcon) + '</a>' : '') + '</div><input autocomplete="off" class="drop-chosen-search" placeholder="Search" type="text"></div><ul class="results-chosen">' + ddm.select_options(opt_data) + '</ul></div>' : '') + '</div>' + ((opt_data.childElementsHTML) ? soy.$$filterNoAutoescape(opt_data.childElementsHTML) : '') + '</div></div>';
  return output;
};
if (goog.DEBUG) {
  ddm.select.soyTemplateName = 'ddm.select';
}


ddm.badge_item = function(opt_data, opt_ignored) {
  return '<li><span class="badge badge-default badge-sm multiple-badge">' + soy.$$escapeHtml(opt_data.label) + '<a class="trigger-badge-item" data-badge-value="' + soy.$$escapeHtmlAttribute(opt_data.value) + '" href="javascript:void(0)">' + soy.$$filterNoAutoescape(opt_data.badgeCloseIcon) + '</a></span></li>';
};
if (goog.DEBUG) {
  ddm.badge_item.soyTemplateName = 'ddm.badge_item';
}


ddm.select_label = function(opt_data, opt_ignored) {
  return '<label class="control-label" for="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + soy.$$escapeHtml(opt_data.label) + ((opt_data.required) ? '<span class="icon-asterisk text-warning"></span>' : '') + '</label>' + ((opt_data.tip) ? '<p class="liferay-ddm-form-field-tip">' + soy.$$escapeHtml(opt_data.tip) + '</p>' : '');
};
if (goog.DEBUG) {
  ddm.select_label.soyTemplateName = 'ddm.select_label';
}


ddm.hidden_select = function(opt_data, opt_ignored) {
  var output = '<select class="form-control hide" dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" id="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" ' + ((opt_data.multiple) ? 'multiple size="' + soy.$$escapeHtmlAttribute(opt_data.options.length) + '"' : '') + '>' + ((! opt_data.readOnly) ? '<option dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" disabled ' + ((opt_data.value.length == 0) ? 'selected' : '') + ' value="">' + soy.$$escapeHtml(opt_data.strings.chooseAnOption) + '</option>' : '');
  var optionList146 = opt_data.options;
  var optionListLen146 = optionList146.length;
  for (var optionIndex146 = 0; optionIndex146 < optionListLen146; optionIndex146++) {
    var optionData146 = optionList146[optionIndex146];
    output += (opt_data.multiple) ? ddm.select_multiple_selection({dir: opt_data.dir, option: optionData146, values: opt_data.value}) : ddm.select_single_selection({dir: opt_data.dir, option: optionData146, value: opt_data.value});
  }
  output += '</select>';
  return output;
};
if (goog.DEBUG) {
  ddm.hidden_select.soyTemplateName = 'ddm.hidden_select';
}


ddm.select_single_selection = function(opt_data, opt_ignored) {
  var output = '';
  var selected__soy150 = '';
  if (opt_data.value) {
    var currentValueList156 = opt_data.value;
    var currentValueListLen156 = currentValueList156.length;
    for (var currentValueIndex156 = 0; currentValueIndex156 < currentValueListLen156; currentValueIndex156++) {
      var currentValueData156 = currentValueList156[currentValueIndex156];
      selected__soy150 += (currentValueData156.value == opt_data.option.value) ? 'selected' : '';
    }
  }
  output += '<option dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" ' + soy.$$filterHtmlAttributes(selected__soy150) + ' value="' + soy.$$escapeHtmlAttribute(opt_data.option.value) + '">' + soy.$$escapeHtml(opt_data.option.label) + '</option>';
  return output;
};
if (goog.DEBUG) {
  ddm.select_single_selection.soyTemplateName = 'ddm.select_single_selection';
}


ddm.select_multiple_selection = function(opt_data, opt_ignored) {
  var output = '';
  var selected__soy168 = '';
  if (opt_data.values) {
    var currentValueList174 = opt_data.values;
    var currentValueListLen174 = currentValueList174.length;
    for (var currentValueIndex174 = 0; currentValueIndex174 < currentValueListLen174; currentValueIndex174++) {
      var currentValueData174 = currentValueList174[currentValueIndex174];
      selected__soy168 += (currentValueData174.value == opt_data.option.value) ? 'selected' : '';
    }
  }
  output += '<option dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" ' + soy.$$filterHtmlAttributes(selected__soy168) + ' value="' + soy.$$escapeHtmlAttribute(opt_data.option.value) + '">' + soy.$$escapeHtml(opt_data.option.label) + '</option>';
  return output;
};
if (goog.DEBUG) {
  ddm.select_multiple_selection.soyTemplateName = 'ddm.select_multiple_selection';
}


ddm.select_options = function(opt_data, opt_ignored) {
  var output = '';
  var optionList223 = opt_data.options;
  var optionListLen223 = optionList223.length;
  for (var optionIndex223 = 0; optionIndex223 < optionListLen223; optionIndex223++) {
    var optionData223 = optionList223[optionIndex223];
    var selected__soy186 = '';
    if (opt_data.value) {
      var currentValueList192 = opt_data.value;
      var currentValueListLen192 = currentValueList192.length;
      for (var currentValueIndex192 = 0; currentValueIndex192 < currentValueListLen192; currentValueIndex192++) {
        var currentValueData192 = currentValueList192[currentValueIndex192];
        selected__soy186 += (currentValueData192.value == optionData223.value) ? 'selected' : '';
      }
    }
    output += '<li class="select-option-item ' + ((selected__soy186) ? 'option-selected' : '') + '" data-option-index="' + soy.$$escapeHtmlAttribute(optionIndex223) + '" data-option-selected="' + ((selected__soy186) ? 'true' : '') + '" data-option-value="' + soy.$$escapeHtmlAttribute(optionData223.value) + '">' + ((opt_data.multiple) ? '<input type="checkbox" value="" ' + ((selected__soy186) ? 'checked' : '') + '>' : '') + '<span data-option-selected="' + ((selected__soy186) ? 'true' : '') + '" data-option-value="' + soy.$$escapeHtmlAttribute(optionData223.value) + '">' + soy.$$escapeHtml(optionData223.label) + '</span></li>';
  }
  return output;
};
if (goog.DEBUG) {
  ddm.select_options.soyTemplateName = 'ddm.select_options';
}
