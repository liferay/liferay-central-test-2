AUI.add(
	'liferay-dockbar-add-content',
	function(A) {
		var Dockbar = Liferay.Dockbar;
		var Layout = Liferay.Layout;

		var SELECTOR_ADD_CONTENT_ITEM = '.add-content-item';

		var STR_CLICK = 'click';

		var STR_RESPONSE_DATA = 'responseData';

		var AddContent = A.Component.create(
			{
				AUGMENTS: [Dockbar.AddContentPreview, Dockbar.AddContentSearch, Liferay.PortletBase],

				EXTENDS: Dockbar.AddBase,

				NAME: 'addcontent',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._config = config;
						instance._displayStyle = config.displayStyle;

						instance._addContentForm = instance.byId('addContentForm');
						instance._entriesPanel = instance.byId('entriesContainer');
						instance._numItems = instance.byId('numItems');

						instance._bindUI();
					},

					_afterSuccess: function(event) {
						var instance = this;

						instance._entriesPanel.setContent(event.currentTarget.get(STR_RESPONSE_DATA));
					},

					_bindUI: function() {
						var instance = this;

						instance._eventHandles.push(
							instance._numItems.on('change', instance._onChangeNumItems, instance),
							instance._entriesPanel.delegate(STR_CLICK, instance._addContent, SELECTOR_ADD_CONTENT_ITEM, instance),
							Liferay.on('AddContent:changeDisplayStyle', instance._onChangeDisplayStyle, instance),
							Liferay.on('AddContent:refreshContentList', instance._refreshContentList, instance),
							Liferay.on('showTab', instance._onShowTab, instance),
							Liferay.on(
								'AddContent:addPortlet',
								function(event) {
									instance.addPortlet(event.node, event.options);
								}
							)
						);
					},

					_onChangeDisplayStyle: function(event) {
						var instance = this;

						var displayStyle = event.displayStyle;

						instance._displayStyle = displayStyle;

						Liferay.Store('liferay_addpanel_displaystyle', displayStyle);

						instance._refreshContentList(event);
					},

					_onChangeNumItems: function(event) {
						var instance = this;

						Liferay.Store('liferay_addpanel_numitems', instance._numItems.val());

						instance._refreshContentList(event);
					},

					_onShowTab: function(event) {
						var instance = this;

						if (event.namespace.indexOf(instance.get('namespace')) === 0) {
							var index = event.selectedIndex;

							Liferay.Store('liferay_addpanel_tab', event.names[index]);
						}
					},

					_refreshContentList: function(event) {
						var instance = this;

						A.io.request(
							instance._addContentForm.getAttribute('action'),
							{
								after: {
									success: A.bind('_afterSuccess', instance)
								},
								data: instance.ns(
									{
										delta: instance._numItems.val(),
										displayStyle: instance._displayStyle,
										keywords: instance.get('inputNode').val(),
										viewEntries: true,
										viewPreview: false
									}
								)
							}
						);
					}
				}
			}
		);

		Dockbar.AddContent = AddContent;
	},
	'',
	{
		requires: ['aui-io-request', 'liferay-dockbar', 'liferay-dockbar-add-base', 'liferay-dockbar-add-content-preview', 'liferay-dockbar-add-content-search']
	}
);