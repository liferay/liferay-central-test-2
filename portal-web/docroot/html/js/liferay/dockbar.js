AUI().use(
	'context-overlay',
	'io-stdmod',
	'overlay-manager',
	'tool-item',
	function(A) {
		Liferay.Dockbar = {
			init: function() {
				var instance = this;

				var dockBar = jQuery('#dockbar');

				if (dockBar.length) {
					dockBar.find('.pin-dockbar').click(
						function(event) {
							A.getBody().toggleClass('lfr-dockbar-pinned');

							jQuery.ajax(
								{
									url: themeDisplay.getPathMain() + '/portal/session_click',
									data: {
										'liferay_dockbar_pinned': A.getBody().hasClass('lfr-dockbar-pinned')
									}
								}
							);

							return false;
						}
					);

					instance.dockBar = dockBar;

					instance._namespace = dockBar.attr('rel');

					instance.MenuManager = new A.OverlayManager(
						{
							zIndexBase: 100000
						}
					);

					instance.UnderlayManager = new A.OverlayManager(
						{
							zIndexBase: 300
						}
					);

					instance._toolbarItems = {};

					instance.addUnderlay(
						{
							boundingBox: '#' + instance._namespace + 'dockbarMessages',
							align: {
								node: '#dockbar',
								points: ['tc', 'bc']
							},
							name: 'messages',
							visible: false,
							header: 'My messages'
						}
					);

					instance.messages.on(
						'visibleChange',
						function(event) {
							if (event.newVal) {
								A.getBody().addClass('showing-messages');

								Liferay.Dockbar.MenuManager.hideAll();
							}
							else {
								A.getBody().removeClass('showing-messages');
							}
						}
					);

					instance.messages.closeTool.on('click', instance.clearMessages, instance);

					instance.addMenu(
						{
							boundingBox: '#' + instance._namespace + 'addContentContainer',
							trigger: '#' + instance._namespace + 'addContent',
							name: 'addContent'
						}
					);

					instance.addMenu(
						{
							boundingBox: '#' + instance._namespace + 'manageContentContainer',
							trigger: '#' + instance._namespace + 'manageContent',
							name: 'manageContent'
						}
					);

					instance.addMenu(
						{
							boundingBox: '#' + instance._namespace + 'myPlacesContainer',
							trigger: '#' + instance._namespace + 'myPlaces',
							name: 'myPlaces'
						}
					);

					var userOptionsContainer = A.get('#' + instance._namespace + 'userOptionsContainer');

					if (userOptionsContainer) {
						instance.addMenu(
							{
								boundingBox: userOptionsContainer,
								trigger: '#' + instance._namespace + 'userAvatar',
								name: 'userOptions'
							}
						);
					}

					var addApplication = jQuery('#' + instance._namespace + 'addApplication');

					addApplication.click(
						function(event) {
							Liferay.Dockbar.addContent.hide();

							if (!Liferay.Dockbar.addApplication) {
								instance.addUnderlay(
									{
										width: '255px',
										name: 'addApplication',
										className: 'add-application',
										io: {
											uri: themeDisplay.getPathMain() + '/portal/render_portlet',
											cfg: {
												data: {
													p_l_id: themeDisplay.getPlid(),
													p_p_id: 87,
													p_p_state: 'exclusive',
													doAsUserId: themeDisplay.getDoAsUserIdEncoded()
												},
												on: {
													success: function(id, response) {
														AUI().use(
															'liferay-layout-configuration',
															function(A) {
																var contentBox = Liferay.Dockbar.addApplication.get('contentBox');

																Liferay.Dockbar.addApplication.set('bodyContent', response.responseText);

																Liferay.LayoutConfiguration._dialogBody = jQuery(contentBox.getDOM());

																Liferay.LayoutConfiguration._loadContent();
															}
														);
													}
												}
											}
										},
										on: {
											visibleChange: function(event) {
												var action = 'addClass';

												if (!event.newVal) {
													action = 'removeClass';
												}

												var body = A.getBody();

												body[action]('lfr-has-sidebar');
											}
										}
									}
								);
							}
							else {
								Liferay.Dockbar.addApplication.show();
							}

							Liferay.Dockbar.addApplication.focus();
						}
					);

					var pageTemplate = jQuery('#pageTemplate');

					pageTemplate.click(
						function(event) {
							Liferay.Dockbar.manageContent.hide();

							if (!Liferay.Dockbar.manageLayouts) {
								instance.addUnderlay(
									{
										io: {
											uri: themeDisplay.getPathMain() + '/layout_configuration/templates',
											cfg: {
												data: {
													p_l_id: themeDisplay.getPlid(),
													doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
													redirect: Liferay.currentURL
												}
											}
										},
										className: 'manage-layouts',
										name: 'manageLayouts',
										width: '700px'
									}
								);
							}
							else {
								Liferay.Dockbar.manageLayouts.show();
							}

							Liferay.Dockbar.manageLayouts.focus();
						}
					);

					var body = A.getBody();

					var isStaging = body.hasClass('staging');
					var isLiveView = body.hasClass('live-view');

					if (isStaging || isLiveView) {
						instance.addMenu(
							{
								boundingBox: '#' + instance._namespace + 'stagingContainer',
								trigger: '#' + instance._namespace + 'staging',
								name: 'staging'
							}
						);
					}

					instance.dockBar.find('.app-shortcut').click(
						function(event) {
							var portletId = this.getAttribute('rel');

							if ((/^\d+$/).test(portletId)) {
								Liferay.Portlet.add(
									{
										portletId: portletId
									}
								);
							}

							if (!event.shiftKey) {
								Liferay.Dockbar.MenuManager.hideAll();
							}

							return false;
						}
					);
				}
			},

			addItem: function(options) {
				var instance = this;

				if (options.url) {
					options.text = '<a href="' + options.url + '">' + options.text + '</a>';
				}

				var item = jQuery('<li class="' + (options.className || '') + '">' + options.text + '</li>');

				instance.dockBar.find('> ul').append(item);

				instance._toolbarItems[options.name] = item;

				return item;
			},

			addMenu: function(options) {
				var instance = this;

				if (options.name && A.get(options.trigger)) {
					var name = options.name;

					delete options.name;

					options.zIndex = instance.menuZIndex++;

					A.mix(
						options,
						{
							hideDelay: 500,
							hideOn: 'mouseleave',
							showOn: 'mouseenter'
						}
					);

					if (options.boundingBox && !('contentBox' in options)) {
						options.contentBox = options.boundingBox + '> .aui-menu-content';
					}

					var menu = new A.ContextOverlay(options);

					Liferay.Dockbar.MenuManager.register(menu);

					menu.on(
						'show',
						function(event) {
							var instance = this;

							Liferay.Dockbar.MenuManager.hideAll();

							instance.get('trigger').addClass('menu-button-active');
						}
					);

					menu.on(
						'hide',
						function(event) {
							var instance = this;

							instance.get('trigger').removeClass('menu-button-active');
						}
					);

					menu.render('#dockbar');

					instance[name] = menu;
				}
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

				if (options.name) {
					var name = options.name;

					autoShow = options.visible !== false;

					if (!instance[name]) {
						delete options.name;

						options.zIndex = instance.underlayZIndex++;

						options.align = options.align || {
							node: '#dockbar',
							points: ['tl', 'bl']
						};

						var underlay = new instance.Underlay(options);

						underlay.render('#dockbar');

						instance[name] = underlay;
					}

					if (autoShow && instance[name] && instance[name] instanceof A.Overlay) {
						instance[name].show();
					}

					return instance[name];
				}
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
			}
		};

		var Underlay = function() {
			Underlay.superclass.constructor.apply(this, arguments);
		};

		Underlay.NAME = 'underlay';

		Underlay.ATTRS = {
			className: {
				value: null,
				lazyAdd: false,
				setter: function(value) {
					var instance = this;

					instance.get('boundingBox').addClass(value);
				}
			},

			io: {
				value: null,
				lazyAdd: false,
				setter: function(value) {
					var instance = this;

					return instance._setIO(value);
				}
			}
		};

		A.extend(
			Underlay,
			A.Overlay,
			{
				initializer: function() {
					var instance = this;

					Liferay.Dockbar.UnderlayManager.register(instance);
				},

				renderUI: function() {
					var instance = this;

					var closeTool = new A.ToolItem('close');

					closeTool.render(instance.get('boundingBox'));

					closeTool.get('contentBox').addClass('aui-underlay-close');

					instance.set('headerContent', closeTool.get('boundingBox'));

					instance.closeTool = closeTool;
				},

				bindUI: function() {
					var instance = this;

					instance.closeTool.on('click', instance.hide, instance);
				},

				_setIO: function(value) {
					var instance = this;

					if (value && !instance.get('bodyContent')) {
						instance.set('bodyContent', '<div class="loading-animation"></div>');
					}

					if (value) {
						instance.unplug(A.Plugin.StdModIOPlugin);

						value.cfg = A.merge(
							{
								method: 'POST'
							},
							value.cfg
						);

						var data = value.cfg.data;

						if (A.Lang.isObject(data)) {
							value.cfg.data = A.toQueryString(data);
						}

						instance.plug(A.Plugin.StdModIOPlugin, value);

						if (instance.io) {
							instance.io.refresh();
						}
					}
					else {
						instance.unplug(A.Plugin.StdModIOPlugin);
					}

					return value;
				}
			}
		);

		Liferay.Dockbar.Underlay = Underlay;
	}
);

AUI().ready(
	function() {
		Liferay.Dockbar.init();
	}
);