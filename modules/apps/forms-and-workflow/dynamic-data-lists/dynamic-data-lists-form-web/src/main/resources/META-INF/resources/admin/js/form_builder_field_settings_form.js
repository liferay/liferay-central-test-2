AUI.add(
	'liferay-ddl-form-builder-field-settings-form',
	function(A) {
		var TPL_SETTINGS_FORM = '<form action="javascript:;"></form>';

		var TPL_SETTINGS_TOGGLER = '<button class="btn settings-toggler" type="button"><span class="settings-toggle-label"></span><span class="settings-toggle-icon"></span></button>';

		var RendererUtil = Liferay.DDM.Renderer.Util;

		var FormBuilderSettingsForm = A.Component.create(
			{
				ATTRS: {
					editMode: {
						value: false
					},

					field: {
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Form,

				NAME: 'liferay-ddl-form-builder-field-settings-form',

				prototype: {
					initializer: function() {
						var instance = this;

						var evaluator = instance.get('evaluator');

						instance._eventHandlers.push(
							evaluator.after('evaluationStarted', A.bind('_saveSettings', instance)),
							instance.after('render', instance._afterSettingsFormRender),
							instance.on('*:addOption', instance._afterAddOption),
							instance.on('*:removeOption', instance.alignModal)
						);

						instance._fieldEventHandlers = [];
					},

					getEvaluationPayload: function() {
						var instance = this;

						var field = instance.get('field');

						return A.merge(
							FormBuilderSettingsForm.superclass.getEvaluationPayload.apply(instance, arguments),
							{
								type: field.get('type')
							}
						);
					},

					_afterAddOption: function(event) {
						var instance = this;

						var optionsField = event.target;

						var field = instance.get('field');

						var builder = field.get('builder');

						var definition = builder.get('definition');

						var searchResults = RendererUtil.searchFieldsByKey(definition, field.get('fieldName'), 'fieldName');

						if (searchResults.length) {
							var definitionOptions = searchResults[0].options || [];

							optionsField.eachOption(
								function(option) {
									var existingOption = definitionOptions.find(
										function(definitionOption) {
											return definitionOption.value === option.get('key');
										}
									);

									option.set('keyInputEnabled', !existingOption);
								}
							);
						}
					},

					_afterLabelFieldNormalizeKey: function(key) {
						var instance = this;

						return new A.Do.AlterReturn(null, instance._generateFieldName(A.Do.originalRetVal));
					},

					_afterSettingsFormRender: function() {
						var instance = this;

						var labelField = instance.getField('label');
						var nameField = instance.getField('name');

						(new A.EventHandle(instance._fieldEventHandlers)).detach();

						instance._fieldEventHandlers.push(
							labelField.on('keyChange', A.bind('_onLabelFieldKeyChange', instance)),
							labelField.after(A.bind('_afterLabelFieldNormalizeKey', instance), labelField, 'normalizeKey')
						);

						labelField.set('key', labelField.normalizeKey(nameField.getValue()));
						labelField.set('keyInputEnabled', instance.get('editMode'));
					},

					_createModeToggler: function() {
						var instance = this;

						var advancedSettingsNode = instance.getPageNode(2);

						var settingsTogglerNode = A.Node.create(TPL_SETTINGS_TOGGLER);

						advancedSettingsNode.placeBefore(settingsTogglerNode);

						settingsTogglerNode.on('click', A.bind('_onClickModeToggler', instance));

						instance.settingsTogglerNode = settingsTogglerNode;
					},

					_generateFieldName: function(key) {
						var instance = this;

						var counter = 0;

						var field = instance.get('field');

						var builder = field.get('builder');

						var existingField;

						if (!key) {
							key = field.get('type');
						}

						var name = key;

						if (name) {
							do {
								if (counter > 0) {
									name = key + counter;
								}

								existingField = builder.getField(name);

								counter++;
							}
							while (existingField !== undefined && existingField !== field);
						}

						return name;
					},

					_handleValidationResponse: function(hasErrors) {
						var instance = this;

						var field = instance.get('field');

						var builder = field.get('builder');

						var nameField = instance.getField('name');

						var sameNameField = builder.getField(nameField.getValue());

						if (!!sameNameField && sameNameField !== field) {
							nameField.showErrorMessage(Liferay.Language.get('field-name-is-already-in-use'));

							hasErrors = true;
						}

						return hasErrors;
					},

					_onClickModeToggler: function(event) {
						var instance = this;

						var advancedSettingsNode = instance.getPageNode(2);

						advancedSettingsNode.toggleClass('active');

						instance._syncModeToggler();
					},

					_onLabelFieldKeyChange: function(event) {
						var instance = this;

						var nameField = instance.getField('name');

						nameField.set('value', event.newVal);
					},

					_onNameChange: function(event) {
						var instance = this;

						var labelField = instance.getField('label');

						labelField.set('key', event.newVal);
					},

					_onSubmitForm: function(event) {
						var instance = this;

						event.preventDefault();
					},

					_saveSettings: function() {
						var instance = this;

						var field = instance.get('field');

						field.saveSettings(instance);
					},

					_syncModeToggler: function() {
						var instance = this;

						var advancedSettingsNode = instance.getPageNode(2);

						var settingsTogglerNode = instance.settingsTogglerNode;

						var settingsTogglerIconNode = settingsTogglerNode.one('.settings-toggle-icon');
						var settingsTogglerLabelNode = settingsTogglerNode.one('.settings-toggle-label');

						var active = advancedSettingsNode.hasClass('active');

						if (active) {
							settingsTogglerIconNode.html(Liferay.Util.getLexiconIconTpl('angle-up'));
							settingsTogglerLabelNode.html(Liferay.Language.get('hide-options'));
						}
						else {
							settingsTogglerIconNode.html(Liferay.Util.getLexiconIconTpl('angle-down'));
							settingsTogglerLabelNode.html(Liferay.Language.get('show-more-options'));
						}

						settingsTogglerNode.toggleClass('active', active);
					},

					_valueContainer: function() {
						var instance = this;

						return A.Node.create(TPL_SETTINGS_FORM);
					}
				}
			}
		);

		Liferay.namespace('DDL').FormBuilderSettingsForm = FormBuilderSettingsForm;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer', 'liferay-ddm-form-renderer-util', 'liferay-form']
	}
);