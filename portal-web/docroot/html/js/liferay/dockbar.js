AUI().add(
	'liferay-dockbar',
	function(A) {
		var LayoutConfiguration = Liferay.LayoutConfiguration;
		var Portlet = Liferay.Portlet;
		var Util = Liferay.Util;

		var BODY = A.getBody();

		var Dockbar = {
			init: function() {
				var instance = this;

				var dockBar = A.one('#dockbar');

				if (dockBar) {
					instance.dockBar = dockBar;

					instance._namespace = dockBar.attr('data-namespace');

					Liferay.once('initDockbar', instance._init, instance);

					var eventHandle = dockBar.on(
						['focus', 'mousemove'],
						function(event) {
							Liferay.fire('initDockbar');

							eventHandle.detach();
						}
					);
				}
			},

			addItem: function(options) {
				var instance = this;

				if (options.url) {
					options.text = '<a href="' + options.url + '">' + options.text + '</a>';
				}

				var item = A.Node.create('<li class="' + (options.className || '') + '">' + options.text + '</li>');

				instance.dockBar.one('> ul').appendChild(item);

				instance._toolbarItems[options.name] = item;

				return item;
			},

			addMenu: function(options) {
				var instance = this;

				var menu;
				var name = options.name;

				if (name && A.one(options.trigger)) {

					delete options.name;

					options.zIndex = instance.menuZIndex++;

					A.mix(
						options,
						{
							hideDelay: 500,
							hideOn: 'mouseleave',
							showOn: 'mouseover'
						}
					);

					var boundingBox = options.boundingBox;

					if (boundingBox && !('contentBox' in options)) {
						options.contentBox = boundingBox + '> .aui-menu-content';
					}

					menu = new A.OverlayContext(options);

					var contentBox = menu.get('contentBox');

					contentBox.plug(
						A.Plugin.NodeFocusManager,
						{
							circular: false,
							descendants: 'a',
							focusClass: 'aui-focus',
							keys: {
								next: 'down:40',
								previous: 'down:38'
							}
						 }
					);

					var focusManager = contentBox.focusManager;

					contentBox.all('li').addClass('aui-menu-item');

					contentBox.delegate(
						'mouseenter',
						function (event) {
							focusManager.focus(event.currentTarget.one('a'));
						},
						'.aui-menu-item'
					);

					contentBox.delegate(
						'mouseleave',
						function (event) {
							focusManager.blur(event.currentTarget.one('a'));
						},
						'.aui-menu-item'
					);

					var MenuManager = Dockbar.MenuManager;

					var dockBar = instance.dockBar;

					var trigger = menu.get('trigger').item(0);
					var button = trigger.one('a');

					MenuManager.register(menu);

					menu.on(
						'visibleChange',
						function(event) {
							var visible = event.newVal;

							if (visible) {
								MenuManager.hideAll();
							}

							trigger.toggleClass('menu-button-active', visible);
						}
					);

					button.on(
						'focus',
						function(event) {
							menu.show();
						}
					);

					button.on(
						'keydown',
						function(event) {
							if (event.keyCode == 40) {
								focusManager.focus(0);
							}
						}
					);

					menu.on(
						'keydown',
						function(event) {
							if (focusManager.get('activeDescendant') == -1) {
								button.focus();
							}
							else {
								instance._updateMenu(event.domEvent, button);
							}
						}
					);

					instance[name] = menu.render(instance.dockBar);
				}

				return menu;
			},

			addMessage: function(message, messageId) {
				var instance = this;

				var messages = instance.messages;

				if (!instance.messageList) {
					instance.messageList = [];
					instance.messageIdList = [];
				}

				messages.show();

				if (!messageId) {
					messageId = A.guid();
				}

				instance.messageList.push(message);
				instance.messageIdList.push(messageId);

				var currentBody = messages.get('bodyContent');

				message = instance._createMessage(message, messageId);

				messages.setStdModContent('body', message, 'after');

				var messagesContainer = messages.get('boundingBox');

				var action = 'removeClass';

				if (instance.messageList.length > 1) {
					action = 'addClass';
				}

				messagesContainer[action]('multiple-messages');

				return messageId;
			},

			addUnderlay: function(options) {
				var instance = this;

				var autoShow = true;

				var underlay;
				var name = options.name;

				if (name) {
					autoShow = options.visible !== false;

					underlay = instance[name];

					if (!underlay) {
						delete options.name;

						options.zIndex = instance.underlayZIndex++;

						options.align = options.align || {
							node: instance.dockBar,
							points: ['tl', 'bl']
						};

						underlay = new instance.Underlay(options);

						underlay.render(instance.dockBar);

						var ioOptions = options.io;

						if (ioOptions) {
							ioOptions.loadingMask = {
								background: 'transparent'
							};

							underlay.plug(A.Plugin.IO, ioOptions);
						}

						instance[name] = underlay;
					}

					if (autoShow && underlay && underlay instanceof A.Overlay) {
						underlay.show();
					}
				}

				return underlay;
			},

			clearMessages: function(event) {
				var instance = this;

				instance.messages.set('bodyContent', ' ');

				instance.messageList = [];
				instance.messageIdList = [];
			},

			setMessage: function(message, messageId) {
				var instance = this;

				var messages = instance.messages;

				if (!messageId) {
					messageId = A.guid();
				}

				instance.messageList = [message];
				instance.messageIdList = [messageId];

				messages.show();

				message = instance._createMessage(message, messageId);

				messages.set('bodyContent', message);

				var messagesContainer = messages.get('boundingBox');

				messagesContainer.removeClass('multiple-messages');

				return messageId;
			},

			_createMessage: function(message, messageId) {
				var instance = this;

				var cssClass = '';

				if (instance.messageList.length == 1) {
					cssClass = 'first';
				}

				return '<div class="dockbar-message ' + cssClass + '" id="' + messageId + '">' + message + '</div>';
			},

			_init: function() {
				var instance = this;

				var dockBar = instance.dockBar;
				var namespace = instance._namespace;

				dockBar.one('.pin-dockbar').on(
					'click',
					function(event) {
						BODY.toggleClass('lfr-dockbar-pinned');

						var pinned = BODY.hasClass('lfr-dockbar-pinned');

						A.io.request(
							themeDisplay.getPathMain() + '/portal/session_click',
							{
								data: {
									'liferay_dockbar_pinned': pinned
								}
							}
						);

						event.halt();

						Liferay.fire(
							'dockbar:pinned',
							{
								pinned: pinned
							}
						);
					}
				);

				var MenuManager = new A.OverlayManager(
					{
						zIndexBase: 100000
					}
				);

				var UnderlayManager = new A.OverlayManager(
					{
						zIndexBase: 300
					}
				);

				Dockbar.MenuManager = MenuManager;
				Dockbar.UnderlayManager = UnderlayManager;

				instance._toolbarItems = {};

				var messages = instance.addUnderlay(
					{
						align: {
							node: instance.dockBar,
							points: ['tc', 'bc']
						},
						bodyContent: '',
						boundingBox: '#' + namespace + 'dockbarMessages',
						header: 'My messages',
						name: 'messages',
						visible: false
					}
				);

				messages.on(
					'visibleChange',
					function(event) {
						if (event.newVal) {
							A.getBody().addClass('showing-messages');

							MenuManager.hideAll();
						}
						else {
							A.getBody().removeClass('showing-messages');
						}
					}
				);

				messages.closeTool.on('click', instance.clearMessages, instance);

				var addContent = instance.addMenu(
					{
						boundingBox: '#' + namespace + 'addContentContainer',
						name: 'addContent',
						trigger: '#' + namespace + 'addContent'
					}
				);

				if (addContent) {
					addContent.on(
						'show',
						function() {
							Liferay.fire('initLayout');
							Liferay.fire('initNavigation');
						}
					);

					var addContentBoundingBox = addContent.get('boundingBox');

					var commonItems = addContentBoundingBox.one('.common-items');

					if (commonItems) {
						commonItems.removeClass('aui-menu-item');
					}

					addContentBoundingBox.delegate(
						'click',
						function(event) {
							var item = event.currentTarget;

							var portletId = item.attr('data-portlet-id');

							if ((/^\d+$/).test(portletId)) {
								Portlet.add(
									{
										portletId: portletId
									}
								);
							}

							if (!event.shiftKey) {
								MenuManager.hideAll();
							}

							event.halt();
						},
						'.app-shortcut'
					);
				}

				var manageContent = instance.addMenu(
					{
						boundingBox: '#' + namespace + 'manageContentContainer',
						name: 'manageContent',
						trigger: '#' + namespace + 'manageContent'
					}
				);

				instance.addMenu(
					{
						boundingBox: '#' + namespace + 'myPlacesContainer',
						name: 'myPlaces',
						trigger: '#' + namespace + 'myPlaces'
					}
				);

				var userOptionsContainer = A.one('#' + namespace + 'userOptionsContainer');

				if (userOptionsContainer) {
					instance.addMenu(
						{
							boundingBox: userOptionsContainer,
							name: 'userOptions',
							trigger: '#' + namespace + 'userAvatar'
						}
					);
				}

				var isStaging = BODY.hasClass('staging') || BODY.hasClass('remote-staging');
				var isLiveView = BODY.hasClass('live-view');

				if (isStaging || isLiveView) {
					instance.addMenu(
						{
							boundingBox: '#' + namespace + 'stagingContainer',
							name: 'staging',
							trigger: '#' + namespace + 'staging'
						}
					);
				}

				var addApplicationLink = A.one('#' + namespace + 'addApplication');

				if (addApplicationLink) {
					addApplicationLink.on(
						'click',
						function(event) {
							addContent.hide();

							var addApplication = Dockbar.addApplication;

							if (!addApplication) {
								var setAddApplicationUI = function(visible) {
									BODY.toggleClass('lfr-has-sidebar', visible);
								};

								addApplication = instance.addUnderlay(
									{
										after: {
											render: function(event) {
												setAddApplicationUI(true);
											}
										},
										className: 'add-application',
										io: {
											after: {
												success: Dockbar._loadAddApplications
											},
											data: {
												doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
												p_l_id: themeDisplay.getPlid(),
												p_p_id: 87,
												p_p_state: 'exclusive'
											},
											uri: themeDisplay.getPathMain() + '/portal/render_portlet'
										},
										name: 'addApplication',
										width: '255px'
									}
								);

								addApplication.after(
									'visibleChange',
									function(event) {
										if (event.newVal) {
											Util.focusFormField('#layout_configuration_content');
										}

										setAddApplicationUI(event.newVal);
									}
								);
							}
							else {
								addApplication.show();
							}

							addApplication.focus();
						}
					);
				}

				var pageTemplate = A.one('#pageTemplate');

				if (pageTemplate) {
					pageTemplate.on(
						'click',
						function(event) {
							manageContent.hide();

							var manageLayouts = Dockbar.manageLayouts;

							if (!manageLayouts) {
								manageLayouts = instance.addUnderlay(
									{
										className: 'manage-layouts',
										io: {
											data: {
												doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
												p_l_id: themeDisplay.getPlid(),
												redirect: Liferay.currentURL
											},
											uri: themeDisplay.getPathMain() + '/layout_configuration/templates'
										},
										name: 'manageLayouts',
										width: '670px'
									}
								);
							}
							else {
								manageLayouts.show();
							}

							manageLayouts.focus();
						}
					);
				}

				dockBar._menuButtons = dockBar.all('ul.aui-toolbar > li > a, .user-links a, .sign-out a');

				dockBar.delegate(
					'keydown',
					function(event) {
						instance._updateMenu(event, event.currentTarget);
					},
					'.aui-toolbar a'
				);
			},

			_updateMenu: function(event, item) {
				var instance = this;

				var menuButtons = instance.dockBar._menuButtons;
				var lastButtonIndex = menuButtons.size();
				var index = menuButtons.indexOf(item);

				if (index > -1) {
					var button;

					var keyCode = event.keyCode;

					if (keyCode == 37 && index > 0) {
						button = menuButtons.item(--index);
					}
					else if (keyCode == 39 && (index < lastButtonIndex)) {
						button = menuButtons.item(++index);
					}

					if (button) {
						if (keyCode >= 37 && keyCode <= 40) {
							event.halt();
						}

						var MenuManager = Dockbar.MenuManager;

						MenuManager.hideAll();

						button.focus();
					}
				}
			}
		};

		var Underlay = A.Component.create(
			{
				ATTRS: {
					bodyContent: {
						value: A.Node.create('<div style="height: 100px"></div>')
					},
					className: {
						lazyAdd: false,
						setter: function(value) {
							var instance = this;

							instance.get('boundingBox').addClass(value);
						},
						value: null
					}
				},

				EXTENDS: A.OverlayBase,

				NAME: 'underlay',

				prototype: {
					initializer: function() {
						var instance = this;

						Dockbar.UnderlayManager.register(instance);
					},

					renderUI: function() {
						var instance = this;

						Underlay.superclass.renderUI.apply(instance, arguments);

						var closeTool = new A.ButtonItem('close');

						closeTool.render(instance.get('boundingBox'));

						closeTool.get('contentBox').addClass('aui-underlay-close');

						instance.set('headerContent', closeTool.get('boundingBox'));

						instance.closeTool = closeTool;
					},

					bindUI: function() {
						var instance = this;

						Underlay.superclass.bindUI.apply(instance, arguments);

						instance.closeTool.on('click', instance.hide, instance);
					}
				}
			}
		);

		Dockbar.Underlay = Underlay;

		Liferay.provide(
			Dockbar,
			'_loadAddApplications',
			function(event, id, obj) {
				var contentBox = Dockbar.addApplication.get('contentBox');

				LayoutConfiguration._dialogBody = contentBox;

				LayoutConfiguration._loadContent();
			},
			['liferay-layout-configuration']
		);

		Liferay.Dockbar = Dockbar;
	},
	'',
	{
		requires: ['aui-button-item', 'aui-io-plugin', 'aui-io-request', 'aui-overlay-context', 'aui-overlay-manager', 'node-focusmanager']
	}
);