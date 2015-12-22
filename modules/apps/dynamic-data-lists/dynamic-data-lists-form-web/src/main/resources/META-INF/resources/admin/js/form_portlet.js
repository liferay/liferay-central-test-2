AUI.add(
	'liferay-ddl-portlet',
	function(A) {
		var DefinitionSerializer = Liferay.DDL.DefinitionSerializer;
		var LayoutSerializer = Liferay.DDL.LayoutSerializer;

		var TPL_BUTTON_SPINNER = '<span><span class="icon-spinner"></span></span>';

		var DDLPortlet = A.Component.create(
			{
				ATTRS: {
					dataProviders: {
					},

					definition: {
					},

					editForm: {
					},

					formBuilder: {
						valueFn: '_valueFormBuilder'
					},

					layout: {
					},

					publishRecordSetURL: {
					},

					recordSetId: {
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'liferay-ddl-portlet',

				prototype: {
					initializer: function() {
						var instance = this;

						instance.definitionSerializer = new DefinitionSerializer();

						instance.layoutSerializer = new LayoutSerializer(
							{
								builder: instance.get('formBuilder')
							}
						);

						instance.renderUI();

						instance.bindUI();
					},

					renderUI: function() {
						var instance = this;

						instance.one('#loader').remove();

						instance.get('formBuilder').render(instance.one('#formBuilder'));

						instance.enableButtons();
					},

					bindUI: function() {
						var instance = this;

						var editForm = instance.get('editForm');

						editForm.set('onSubmit', A.bind('_onSubmitEditForm', instance));

						var rootNode = instance.get('rootNode');

						instance._eventHandlers = [
							instance.one('#publishCheckbox').on('change', A.bind('_onChangePublishCheckbox', instance)),
							Liferay.on('destroyPortlet', A.bind('_onDestroyPortlet', instance))
						];
					},

					destructor: function() {
						var instance = this;

						instance.get('formBuilder').destroy();

						(new A.EventHandle(instance._eventHandlers)).detach();
					},

					enableButtons: function() {
						var instance = this;

						var buttons = instance.all('.ddl-button');

						Liferay.Util.toggleDisabled(buttons, false);
					},

					openPublishModal: function() {
						var instance = this;

						Liferay.Util.openWindow(
							{
								dialog: {
									height: 325,
									resizable: false,
									width: 720
								},
								id: instance.ns('publishModal'),
								title: Liferay.Language.get('publish')
							},
							function(dialogWindow) {
								var bodyNode = dialogWindow.bodyNode;

								var publishNode = instance.one('#publishModal');

								if (publishNode) {
									publishNode.show();

									bodyNode.append(publishNode);
								}
							}
						);
					},

					serializeFormBuilder: function() {
						var instance = this;

						var description = window[instance.ns('descriptionEditor')].getHTML();

						instance.one('#description').val(description);

						var formBuilder = instance.get('formBuilder');

						var pages = formBuilder.get('layouts');

						var definitionInput = instance.one('#definition');

						instance.definitionSerializer.set('pages', pages);

						definitionInput.val(instance.definitionSerializer.serialize());

						var layoutInput = instance.one('#layout');

						instance.layoutSerializer.set('pages', pages);

						layoutInput.val(instance.layoutSerializer.serialize());

						var name = window[instance.ns('nameEditor')].getHTML();

						instance.one('#name').val(name);
					},

					submitForm: function() {
						var instance = this;

						instance.serializeFormBuilder();

						var submitButton = instance.one('#submit');

						submitButton.html(Liferay.Language.get('saving'));

						submitButton.append(TPL_BUTTON_SPINNER);

						var editForm = instance.get('editForm');

						submitForm(editForm.form);
					},

					_onChangePublishCheckbox: function(event) {
						var instance = this;

						var publishCheckbox = event.currentTarget;

						var payload = instance.ns(
							{
								recordSetId: instance.get('recordSetId'),
								published: publishCheckbox.attr('checked')
							}
						);

						A.io.request(
							instance.get('publishRecordSetURL'),
							{
								data: payload,
								dataType: 'JSON',
								method: 'POST'
							}
						);
					},

					_onDestroyPortlet: function(event) {
						var instance = this;

						instance.destroy();
					},

					_onSubmitEditForm: function(event) {
						var instance = this;

						event.preventDefault();

						instance.serializeFormBuilder();

						instance.submitForm();
					},

					_valueFormBuilder: function() {
						var instance = this;

						var layout = instance.get('layout');

						return new Liferay.DDL.FormBuilder(
							{
								dataProviders: instance.get('dataProviders'),
								definition: instance.get('definition'),
								pagesJSON: layout.pages,
								portletNamespace: instance.get('namespace')
							}
						);
					}
				}
			}
		);

		Liferay.namespace('DDL').Portlet = DDLPortlet;
	},
	'',
	{
		requires: ['liferay-ddl-form-builder', 'liferay-ddl-form-builder-definition-serializer', 'liferay-ddl-form-builder-layout-serializer', 'liferay-portlet-base']
	}
);