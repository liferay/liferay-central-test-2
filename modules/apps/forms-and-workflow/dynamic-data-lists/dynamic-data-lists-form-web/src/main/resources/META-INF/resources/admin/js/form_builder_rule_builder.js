AUI.add(
	'liferay-ddl-form-builder-rule-builder',
	function(A) {
		var SoyTemplateUtil = Liferay.DDM.SoyTemplateUtil;

		var MAP_ACTION_DESCRIPTIONS = {
			enable: 'enable-field',
			'jump-to-page': 'jump-from-page-to-page',
			require: 'require-field',
			show: 'show-field'
		};

		var FormBuilderRuleBuilder = A.Component.create(
			{
				ATTRS: {
					formBuilder: {
						value: null
					},

					rules: {
						value: []
					},

					strings: {
						value: {
							contains: Liferay.Language.get('contains'),
							delete: Liferay.Language.get('delete'),
							edit: Liferay.Language.get('edit'),
							emptyListText: Liferay.Language.get('there-are-no-rules-yet-click-on-plus-icon-bellow-to-add-the-first'),
							'enable-field': Liferay.Language.get('enable-x'),
							'equals-to': Liferay.Language.get('is-equal-to'),
							'jump-from-page-to-page': Liferay.Language.get('jump-from-x-to-x'),
							'not-contains': Liferay.Language.get('does-not-contain'),
							'not-equals-to': Liferay.Language.get('is-not-equal-to'),
							'require-field': Liferay.Language.get('require-x'),
							ruleBuilder: Liferay.Language.get('rule-builder'),
							'show-field': Liferay.Language.get('show-x')
						}
					}
				},

				NAME: 'liferay-ddl-form-builder-rule-builder',

				prototype: {
					bindUI: function() {
						var instance = this;

						var boundingBox = instance.get('boundingBox');

						instance.on('rulesChange', A.bind(instance._onRulesChange, instance));
						instance.on('*:saveRule', A.bind(instance._handleSaveRule, instance));
						instance.on('*:cancelRule', A.bind(instance._handleCancelRule, instance));

						instance._eventHandlers = [
							boundingBox.delegate('click', A.bind(instance._handleAddRuleClick, instance), '.form-builder-rule-builder-add-rule-button-icon'),
							boundingBox.delegate('click', A.bind(instance._handleEditCardClick, instance), '.rule-card-edit'),
							boundingBox.delegate('click', A.bind(instance._handleDeleteCardClick, instance), '.rule-card-delete')
						];
					},

					syncUI: function() {
						var instance = this;

						var ruleBuilderTemplateRenderer = SoyTemplateUtil.getTemplateRenderer('ddl.rule_builder');

						var rulesBuilder = ruleBuilderTemplateRenderer(
							{
								plusIcon: Liferay.Util.getLexiconIconTpl('plus', 'icon-monospaced'),
								strings: instance.get('strings')
							}
						);

						instance.get('contentBox').setHTML(rulesBuilder);

						var rules = instance.get('rules');

						rules.forEach(
							function(rule) {
								rule.conditions.forEach(
									function(condition) {
										condition.operands.forEach(
											function(operand) {
												operand.label = instance._getFieldLabel(operand.value);
											}
										);
									}
								);

								rule.actions.forEach(
									function(action) {
										action.label = instance._getFieldLabel(action.target);
									}
								);
							}
						);

						instance._renderCards(rules);
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandlers)).detach();
					},

					getFields: function() {
						var instance = this;

						var fields = [];

						instance.get('formBuilder').eachFields(
							function(field) {
								fields.push(
									{
										label: field.get('label') || field.get('fieldName'),
										options: field.get('options'),
										type: field.get('type'),
										value: field.get('fieldName')
									}
								);
							}
						);

						return fields;
					},

					getPages: function() {
						var instance = this;

						var pages;

						var formBuilder = instance.get('formBuilder');

						var pagesTitles = formBuilder.getPagesTitle();

						var pagesQuantity = formBuilder.get('layouts').length;

						pages = new Array(pagesQuantity);

						for (var i = 0; i < pagesQuantity; i++) {
							pages[i] = {
								label: pagesTitles[i] ? pagesTitles[i] : (i + 1).toString(),
								value: i.toString()
							};
						}

						return pages;
					},

					renderRule: function(rule) {
						var instance = this;

						if (!instance._ruleClasses) {
							instance._ruleClasses = new Liferay.DDL.FormBuilderRenderRule(
								{
									boundingBox: instance.get('boundingBox'),
									bubbleTargets: [instance],
									contentBox: instance.get('contentBox'),
									fields: instance.getFields(),
									pages: instance.getPages()
								}
							);
						}

						instance._ruleClasses.set('fields', instance.getFields());
						instance._ruleClasses.set('pages', instance.getPages());

						instance._ruleClasses.render(rule);
					},

					show: function() {
						var instance = this;

						FormBuilderRuleBuilder.superclass.show.apply(instance, arguments);

						instance.syncUI();
					},

					_getActionDescription: function(type, action) {
						var instance = this;

						var actionDescription = '';

						var strings = instance.get('strings');

						var badgeTemplate = SoyTemplateUtil.getTemplateRenderer('ddl.badge');

						var actionKey = MAP_ACTION_DESCRIPTIONS[type];

						var pages = instance.getPages();

						if (actionKey) {
							var data;

							if (type === 'jump-to-page') {
								data = [
									badgeTemplate(
										{
											content: pages[action.source].label
										}
									),
									badgeTemplate(
										{
											content: pages[action.target].label
										}
									)
								];
							}
							else {
								data = [
									badgeTemplate(
										{
											content: action.target
										}
									)
								];
							}

							actionDescription = A.Lang.sub(strings[actionKey], data);
						}

						return actionDescription;
					},

					_getActionsDescription: function(actions) {
						var instance = this;

						var actionsDescription = [];

						var actionDescription = '';

						for (var i = 0; i < actions.length; i++) {
							actionDescription = instance._getActionDescription(actions[i].action, actions[i]);

							actionsDescription.push(actionDescription);
						}

						return actionsDescription;
					},

					_getFieldLabel: function(fieldValue) {
						var instance = this;

						var fields = instance.getFields();

						var fieldLabel;

						for (var index in fields) {
							if (fields[index].value === fieldValue) {
								fieldLabel = fields[index].label;
							}
						}

						return fieldLabel;
					},

					_getRulesDescription: function(rules) {
						var instance = this;

						var rulesDescription = [];

						var ruleDescription = {};

						for (var i = 0; i < rules.length; i++) {
							ruleDescription.conditions = rules[i].conditions;

							ruleDescription.actions = instance._getActionsDescription(rules[i].actions);

							rulesDescription.push(ruleDescription);
						}

						return rulesDescription;
					},

					_handleAddRuleClick: function() {
						var instance = this;

						instance.renderRule();
					},

					_handleCancelRule: function() {
						var instance = this;

						instance.syncUI();
					},

					_handleDeleteCardClick: function(event) {
						var instance = this;

						var rules = instance.get('rules');

						rules.splice(event.currentTarget.getData('card-id'), 1);

						instance.set('rules', rules);
					},

					_handleEditCardClick: function(event) {
						var instance = this;

						var target = event.currentTarget;

						var ruleId = target.getData('card-id');

						instance._currentRuleId = ruleId;

						instance.renderRule(instance.get('rules')[ruleId]);
					},

					_handleSaveRule: function(event) {
						var instance = this;

						var rules = instance.get('rules');

						var rule = {
							actions: event.actions,
							conditions: event.condition,
							'logical-operator': event['logical-operator']
						};

						if (instance._currentRuleId) {
							rules[instance._currentRuleId] = rule;
						}
						else {
							rules.push(rule);
						}

						instance.syncUI();

						instance._currentRuleId = null;
					},

					_onRulesChange: function(val) {
						var instance = this;

						instance._renderCards(val.newVal);
					},

					_renderCards: function(rules) {
						var instance = this;

						var rulesList = instance.get('boundingBox').one('.liferay-ddl-form-rule-rules-list-container');

						var ruleListTemplateRenderer = SoyTemplateUtil.getTemplateRenderer('ddl.rule_list');

						var rulesDescription = instance._getRulesDescription(rules);

						rulesList.setHTML(
							ruleListTemplateRenderer(
								{
									kebab: Liferay.Util.getLexiconIconTpl('ellipsis-v', 'icon-monospaced'),
									rules: rulesDescription,
									strings: instance.get('strings')
								}
							)
						);
					}
				}
			}
		);

		Liferay.namespace('DDL').FormBuilderRuleBuilder = FormBuilderRuleBuilder;
	},
	'',
	{
		requires: ['aui-popover', 'event-outside', 'liferay-ddl-form-builder-rule']
	}
);