(function() {
	var Dom = Alloy.Dom;
	var Event = Alloy.Event;

	Liferay.Dockbar = {
		init: function() {
			var instance = this;

			var dockBar = jQuery('#dockbar');

			if (dockBar.length) {
				dockBar.find('.pin-dockbar').click(
					function(event) {
						Alloy.getBody().toggleClass('lfr-dockbar-pinned');

						jQuery.ajax(
							{
								url: themeDisplay.getPathMain() + '/portal/session_click',
								data: {
									'liferay_dockbar_pinned': Alloy.getBody().hasClass('lfr-dockbar-pinned')
								}
							}
						);

						return false;
					}
				);

				instance.dockBar = dockBar;

				instance._namespace = dockBar.attr('rel');

				instance.MenuManager = new Alloy.OverlayManager();
				instance.UnderlayManager = new Alloy.OverlayManager();

				instance._toolbarItems = {};

				instance.underlayZIndex = 300;
				instance.menuZIndex = 100000;

				instance.addUnderlay(
					{
						name: 'messages',
						el: instance._namespace + 'dockbarMessages',
						visible: false,
						header: 'My messages'
					}
				);

				Dom.addClass(instance.messages.element, 'dockbar-messages-container');

				instance.messages.beforeShowEvent.subscribe(
					function(event) {
						Alloy.getBody().addClass('showing-messages');

						Liferay.Dockbar.MenuManager.hideAll();
					}
				);

				instance.messages.hideEvent.subscribe(
					function(event) {
						Alloy.getBody().removeClass('showing-messages');
					}
				);

				Event.on(instance.messages.close, 'click', instance.clearMessages, instance, true);

				instance.addMenu(
					{
						el: instance._namespace + 'addContentContainer',
						trigger: instance._namespace + 'addContent',
						name: 'addContent'
					}
				);

				instance.addMenu(
					{
						el: instance._namespace + 'manageContentContainer',
						trigger: instance._namespace + 'manageContent',
						name: 'manageContent'
					}
				);

				instance.addMenu(
					{
						el: instance._namespace + 'myPlacesContainer',
						trigger: instance._namespace + 'myPlaces',
						name: 'myPlaces'
					}
				);

				instance.addMenu(
					{
						el: instance._namespace + 'userOptionsContainer',
						trigger: instance._namespace + 'userAvatar',
						name: 'userOptions'
					}
				);

				var addApplication = jQuery('#' + instance._namespace + 'addApplication');

				addApplication.click(
					function(event) {
						Liferay.Dockbar.addContent.hide();

						if (!Liferay.Dockbar.addApplication) {
							instance.addUnderlay(
								{
									width: '235px',
									name: 'addApplication',
									className: 'add-application',
									body: {
										url: themeDisplay.getPathMain() + '/portal/render_portlet',
										data: {
											p_l_id: themeDisplay.getPlid(),
											p_p_id: 87,
											p_p_state: 'exclusive',
											doAsUserId: themeDisplay.getDoAsUserIdEncoded()
										},
										success: function(message) {
											Liferay.Dockbar.addApplication.setBody(message);

											Liferay.LayoutConfiguration._dialogBody = jQuery(Liferay.Dockbar.addApplication.body);

											Liferay.LayoutConfiguration._loadContent();
										}
									},
									on: {
										show: function() {
											Alloy.getBody().addClass('lfr-has-sidebar');
										},
										hide: function() {
											Alloy.getBody().removeClass('lfr-has-sidebar');
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
									body: {
										url: themeDisplay.getPathMain() + '/layout_configuration/templates',
										data: {
											p_l_id: themeDisplay.getPlid(),
											doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
											redirect: Liferay.currentURL
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

				var body = Alloy.getBody();

				var isStaging = body.hasClass('staging');
				var isLiveView = body.hasClass('live-view');

				if (isStaging || isLiveView) {
					instance.addMenu(
						{
							el: instance._namespace + 'stagingContainer',
							trigger: instance._namespace + 'staging',
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

			if (options.name) {
				var name = options.name;

				delete options.name;

				options.zIndex = instance.menuZIndex++;

				instance[name] = new instance.Menu(options);
			}
		},

		addMessage: function(message, messageId) {
			var instance = this;

			if (!instance.messageList) {
				instance.messageList = [];
				instance.messageIdList = [];
			}

			instance.messages.show();

			if (!messageId) {
				messageId = Dom.generateId();
			}

			instance.messageList.push(message);
			instance.messageIdList.push(messageId);

			var currentBody = instance.messages.body.innerHTML;

			message = instance._createMessage(message, messageId);

			instance.messages.setBody(currentBody + message);

			if (instance.messageList.length > 1) {
				Dom.addClass(instance.messages.element, 'multiple-messages');
			}
			else {
				Dom.removeClass(instance.messages.element, 'multiple-messages');
			}

			return messageId;
		},

		addUnderlay: function(options) {
			var instance = this;

			if (options.name) {
				var name = options.name;

				if (!instance[name]) {
					delete options.name;

					options.zIndex = instance.underlayZIndex++;

					instance[name] = new instance.Underlay(options);
				}
				else if (instance[name] && instance[name] instanceof Alloy.Overlay) {
					instance[name].show();
				}

				return instance[name];
			}
		},

		clearMessages: function(event) {
			var instance = this;

			instance.messages.setBody('');

			instance.messageList = [];
			instance.messageIdList = [];
		},

		setMessage: function(message, messageId) {
			var instance = this;

			if (!messageId) {
				messageId = Dom.generateId();
			}

			instance.messageList = [message];
			instance.messageIdList = [messageId];

			instance.messages.show();

			message = instance._createMessage(message, messageId);

			instance.messages.setBody(message);

			Dom.removeClass(instance.messages.element, 'multiple-messages');

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

	Liferay.Dockbar.Menu = Alloy.ContextOverlay.extend(
		{
			initialize: function(options) {
				var instance = this;

				options.renderTo = options.renderTo || 'dockbar';
				options.interaction = 'mouse';

				instance._super.apply(instance, arguments);

				this.cfg.setProperty('width', this.element.offsetWidth + 'px');

				Liferay.Dockbar.MenuManager.register(this);
			},

			_createInteraction: function() {
				var instance = this;

				instance._super.apply(instance, arguments);

				instance.beforeShowEvent.subscribe(instance._hideAll, instance, true);
			},

			_hideAll: function() {
				var instance = this;

				Liferay.Dockbar.MenuManager.hideAll();

				instance.cfg.setProperty('visible', true, true);
			}
		}
	);

	Liferay.Dockbar.Underlay = Alloy.Panel.extend(
		{
			initialize: function(options) {
				var instance = this;

				options.draggable = false;

				instance._super(options.el || Dom.generateId(), options);

				Liferay.Dockbar.UnderlayManager.register(instance);

				instance.setBody(options.body || '');

				if (options.header) {
					instance.setHeader(options.header);
				}

				if (options.footer) {
					instance.setFooter(options.footer);
				}

				instance.options = options;

				instance.beforeShowEvent.subscribe(instance._beforeShow);
				instance.showEvent.subscribe(instance.focus);

				instance.render(options.renderTo || 'dockbar');

				Liferay.Dockbar.UnderlayManager.bringToTop(instance);

				Dom.addClass(instance.element, 'aui-underlay-container');
				Dom.addClass(instance.innerElement, 'aui-underlay');

				if (options.className) {
					Dom.addClass(instance.element, options.className);
				}

				instance.blurEvent.subscribe(
					function(event) {
						jQuery(instance.element).one(
							'click',
							function(event) {
								return false;
							}
						);
					}
				);
			},

			toggle: function(event) {
				var instance = this;

				var visible = instance.cfg.getProperty('visible');

				if (event) {
					Liferay.Dockbar.UnderlayManager.hideAll();

					Event.stopEvent(event);
				}

				if (visible) {
					instance.hide();
				}
				else {
					instance.show();
				}
			},

			_beforeShow: function() {
				var instance = this;

				Liferay.Dockbar.MenuManager.hideAll();
			}
		}
	);
})();

AUI().ready(
	function() {
		Liferay.Dockbar.init();
	}
);