// This file was automatically generated from rule.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddl.rule.
 * @public
 */

if (typeof ddl == 'undefined') { var ddl = {}; }
if (typeof ddl.rule == 'undefined') { ddl.rule = {}; }


ddl.rule.settings = function(opt_data, opt_ignored) {
  var output = '<h2 class="form-builder-section-title text-default">' + soy.$$escapeHtml(opt_data.strings.title) + '</h2><h4 class="text-default">' + soy.$$escapeHtml(opt_data.strings.description) + '</h4><div class="ddl-form-body-content"><ul class="liferay-ddl-form-builder-rule-condition-list liferay-ddl-form-rule-builder-timeline timeline ' + soy.$$escapeHtmlAttribute(opt_data.conditions.length > 1 ? 'can-remove-item' : '') + '">' + ddl.rule.rulesHeader({extraContent: soydata.VERY_UNSAFE.$$ordainSanitizedHtmlForInternalBlocks('' + ddl.rule.logicOperatorDropDown(opt_data)), logicalOperator: opt_data.logicalOperator, title: 'Condition'});
  var conditionList79 = opt_data.conditions;
  var conditionListLen79 = conditionList79.length;
  if (conditionListLen79 > 0) {
    for (var conditionIndex79 = 0; conditionIndex79 < conditionListLen79; conditionIndex79++) {
      var conditionData79 = conditionList79[conditionIndex79];
      output += ddl.rule.condition({deleteIcon: opt_data.deleteIcon, if: opt_data.strings['if'], index: conditionIndex79, logicOperator: opt_data.logicalOperator});
    }
  } else {
    output += ddl.rule.condition({deleteIcon: opt_data.deleteIcon, if: opt_data.strings['if'], index: 0, logicOperator: opt_data.logicalOperator});
  }
  output += '</ul>' + ddl.rule.btnAddNewTimelineItem({cssClass: 'form-builder-rule-add-condition', plusIcon: opt_data.plusIcon}) + '<ul class="action-list liferay-ddl-form-builder-rule-action-list liferay-ddl-form-rule-builder-timeline timeline ' + soy.$$escapeHtmlAttribute(opt_data.actions.length > 1 ? 'can-remove-item' : '') + '">' + ddl.rule.rulesHeader({logicalOperator: opt_data.logicalOperator, title: 'Actions'});
  var actionList100 = opt_data.actions;
  var actionListLen100 = actionList100.length;
  if (actionListLen100 > 0) {
    for (var actionIndex100 = 0; actionIndex100 < actionListLen100; actionIndex100++) {
      var actionData100 = actionList100[actionIndex100];
      output += ddl.rule.action({deleteIcon: opt_data.deleteIcon, do: opt_data.strings['do'], index: actionIndex100});
    }
  } else {
    output += ddl.rule.action({deleteIcon: opt_data.deleteIcon, do: opt_data.strings['do'], index: 0});
  }
  output += '</ul>' + ddl.rule.btnAddNewTimelineItem({cssClass: 'form-builder-rule-add-action', plusIcon: opt_data.plusIcon}) + '<div class="liferay-ddl-form-rule-builder-footer"><button class="btn btn-default btn-lg btn-primary ddl-button form-builder-rule-settings-save"  ' + ((opt_data.invalid) ? 'disabled' : '') + ' type="button"><span class="form-builder-rule-settings-save-label">' + soy.$$escapeHtml(opt_data.strings.save) + '</span></button><button class="btn btn-cancel btn-default btn-lg btn-link form-builder-rule-settings-cancel" type="button"><span class="lfr-btn-label">' + soy.$$escapeHtml(opt_data.strings.cancel) + '</span></button></div></div>';
  return output;
};
if (goog.DEBUG) {
  ddl.rule.settings.soyTemplateName = 'ddl.rule.settings';
}


ddl.rule.condition = function(opt_data, opt_ignored) {
  return '<li class="form-builder-rule-condition-container-' + soy.$$escapeHtmlAttribute(opt_data.index) + ' timeline-item"><div class="panel panel-default"><div class="flex-container panel-body"><h4>' + soy.$$escapeHtml(opt_data['if']) + '</h4><div class="condition-if-' + soy.$$escapeHtmlAttribute(opt_data.index) + ' form-group"></div><div class="condition-operator-' + soy.$$escapeHtmlAttribute(opt_data.index) + ' form-group"></div><div class="condition-the-' + soy.$$escapeHtmlAttribute(opt_data.index) + ' form-group"></div><div class="condition-type-value-' + soy.$$escapeHtmlAttribute(opt_data.index) + ' form-group"></div><div class="condition-type-value-options-' + soy.$$escapeHtmlAttribute(opt_data.index) + ' form-group"></div><div class="timeline-increment-icon"><span class="timeline-icon"></span></div></div></div><div class="operator panel panel-default panel-inline"><div class="panel-body text-uppercase">' + soy.$$escapeHtml(opt_data.logicOperator) + '</div></div><div class="container-trash"><button class="btn btn-link condition-card-delete icon-monospaced" data-card-id="' + soy.$$escapeHtmlAttribute(opt_data.index) + '" href="javascript:;" type="button">' + soy.$$filterNoAutoescape(opt_data.deleteIcon) + '</button></div></li>';
};
if (goog.DEBUG) {
  ddl.rule.condition.soyTemplateName = 'ddl.rule.condition';
}


ddl.rule.btnAddNewTimelineItem = function(opt_data, opt_ignored) {
  return '<div class="addbutton-timeline-item"><div class="add-condition timeline-increment-icon"><a aria-role="button" class="btn btn-primary btn-xs form-builder-timeline-add-item ' + soy.$$escapeHtmlAttribute(opt_data.cssClass || '') + '" href="javascript:;">' + soy.$$filterNoAutoescape(opt_data.plusIcon) + '</a></div></div>';
};
if (goog.DEBUG) {
  ddl.rule.btnAddNewTimelineItem.soyTemplateName = 'ddl.rule.btnAddNewTimelineItem';
}


ddl.rule.rulesHeader = function(opt_data, opt_ignored) {
  return '<li class="timeline-item"><div class="panel panel-default"><div class="flex-container panel-body"><div class="h4 panel-title">' + soy.$$escapeHtml(opt_data.title) + '</div>' + ((opt_data.extraContent) ? soy.$$filterNoAutoescape(opt_data.extraContent) : '') + '<div class="timeline-increment-icon"><span class="timeline-icon"></span></div></div></div></li>';
};
if (goog.DEBUG) {
  ddl.rule.rulesHeader.soyTemplateName = 'ddl.rule.rulesHeader';
}


ddl.rule.logicOperatorDropDown = function(opt_data, opt_ignored) {
  opt_data = opt_data || {};
  return soydata.VERY_UNSAFE.ordainSanitizedHtml('<div class="btn-group dropdown" style="block"><button class="btn btn-default dropdown-toggle dropdown-toggle-operator text-uppercase" data-toggle="dropdown" type="button"><span class="dropdown-toggle-selected-value">' + soy.$$escapeHtml(opt_data.logicalOperator) + '</span> <span class="caret"></span></button><ul class="dropdown-menu"><li class="logic-operator text-uppercase"><a href="#">or</a></li><li class="divider"></li><li class="logic-operator text-uppercase"><a href="#">and</a></li></ul></div>');
};
if (goog.DEBUG) {
  ddl.rule.logicOperatorDropDown.soyTemplateName = 'ddl.rule.logicOperatorDropDown';
}


ddl.rule.action = function(opt_data, opt_ignored) {
  return '<li class="form-builder-rule-action-container-' + soy.$$escapeHtmlAttribute(opt_data.index) + ' timeline-item"><div class="panel panel-default"><div class="no-padding-bottom panel-body"><div class="row"><div class="col-md-12 flex-container "><h4>' + soy.$$escapeHtml(opt_data['do']) + '</h4><div class="action-' + soy.$$escapeHtmlAttribute(opt_data.index) + ' form-group"></div><div class="container-target-action form-group target-' + soy.$$escapeHtmlAttribute(opt_data.index) + '"></div></div></div><div class="action-rule-data-provider row"><div class="col-md-12 no-padding"><div class="additional-info-' + soy.$$escapeHtmlAttribute(opt_data.index) + '"></div></div></div><div class="timeline-increment-icon"><span class="timeline-icon"></span></div></div></div><div class="container-trash"><button class="btn btn-link action-card-delete icon-monospaced" data-card-id="' + soy.$$escapeHtmlAttribute(opt_data.index) + '" href="javascript:;" type="button">' + soy.$$filterNoAutoescape(opt_data.deleteIcon) + '</button></div></li>';
};
if (goog.DEBUG) {
  ddl.rule.action.soyTemplateName = 'ddl.rule.action';
}
