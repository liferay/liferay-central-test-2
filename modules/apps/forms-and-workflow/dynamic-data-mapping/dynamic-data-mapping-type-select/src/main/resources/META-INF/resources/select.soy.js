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
  output += '</div>' + ((! opt_data.readOnly) ? '<div class="drop-chosen ' + soy.$$escapeHtmlAttribute(opt_data.open ? '' : 'hide') + '"><div class="search-chosen"><div class="select-search-container">' + ((opt_data.selectSearchIcon) ? '<a class="" href="javascript:;">' + soy.$$filterNoAutoescape(opt_data.selectSearchIcon) + '</a>' : '') + '</div><input autocomplete="off" class="drop-chosen-search" placeholder="Search" type="text"></div><ul class="results-chosen">' + ddm.select_options(opt_data) + '</ul></div>' : '') + '</div>' + ((opt_data.childElementsHTML) ? soy.$$filterNoAutoescape(opt_data.childElementsHTML) : '') + '</div></div>';
  return output;
};
if (goog.DEBUG) {
  ddm.select.soyTemplateName = 'ddm.select';
}


ddm.badge_item = function(opt_data, opt_ignored) {
  return '<li><span class="badge badge-default badge-sm multiple-badge">' + soy.$$escapeHtml(opt_data.label) + '<a class="trigger-badge-item-close" data-badge-value="' + soy.$$escapeHtmlAttribute(opt_data.value) + '" href="javascript:void(0)">' + soy.$$filterNoAutoescape(opt_data.badgeCloseIcon) + '</a></span></li>';
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
  var optionList139 = opt_data.options;
  var optionListLen139 = optionList139.length;
  for (var optionIndex139 = 0; optionIndex139 < optionListLen139; optionIndex139++) {
    var optionData139 = optionList139[optionIndex139];
    output += ddm.select_hidden_options({dir: opt_data.dir, option: optionData139, values: opt_data.value});
  }
  output += '</select>';
  return output;
};
if (goog.DEBUG) {
  ddm.hidden_select.soyTemplateName = 'ddm.hidden_select';
}


ddm.select_hidden_options = function(opt_data, opt_ignored) {
  var output = '';
  var selected__soy143 = '';
  if (opt_data.values) {
    var currentValueList149 = opt_data.values;
    var currentValueListLen149 = currentValueList149.length;
    for (var currentValueIndex149 = 0; currentValueIndex149 < currentValueListLen149; currentValueIndex149++) {
      var currentValueData149 = currentValueList149[currentValueIndex149];
      selected__soy143 += (currentValueData149.value == opt_data.option.value) ? 'selected' : '';
    }
  }
  output += '<option dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" ' + soy.$$filterHtmlAttributes(selected__soy143) + ' value="' + soy.$$escapeHtmlAttribute(opt_data.option.value) + '">' + soy.$$escapeHtml(opt_data.option.label) + '</option>';
  return output;
};
if (goog.DEBUG) {
  ddm.select_hidden_options.soyTemplateName = 'ddm.select_hidden_options';
}


ddm.select_options = function(opt_data, opt_ignored) {
  var output = '';
  var optionList192 = opt_data.options;
  var optionListLen192 = optionList192.length;
  for (var optionIndex192 = 0; optionIndex192 < optionListLen192; optionIndex192++) {
    var optionData192 = optionList192[optionIndex192];
    var selected__soy161 = '';
    if (opt_data.value) {
      var currentValueList167 = opt_data.value;
      var currentValueListLen167 = currentValueList167.length;
      for (var currentValueIndex167 = 0; currentValueIndex167 < currentValueListLen167; currentValueIndex167++) {
        var currentValueData167 = currentValueList167[currentValueIndex167];
        selected__soy161 += (currentValueData167.value == optionData192.value) ? 'selected' : '';
      }
    }
    output += '<li class="select-option-item ' + ((selected__soy161) ? 'option-selected' : '') + '" data-option-index="' + soy.$$escapeHtmlAttribute(optionIndex192) + '" data-option-selected="' + ((selected__soy161) ? 'true' : '') + '" data-option-value="' + soy.$$escapeHtmlAttribute(optionData192.value) + '">' + ((opt_data.multiple) ? '<input type="checkbox" value="" ' + ((selected__soy161) ? 'checked' : '') + '>' : '') + '<span>' + soy.$$escapeHtml(optionData192.label) + '</span></li>';
  }
  return output;
};
if (goog.DEBUG) {
  ddm.select_options.soyTemplateName = 'ddm.select_options';
}
