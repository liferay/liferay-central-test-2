var LayoutConfiguration = {
	categories : [],
	initialized : false,
	loadingImage : null,
	menu : null,
	menuDiv : null,
	menuIframe : null,
	portlets : [],
	showTimer : 0,

	init : function () {
		var instance = this;

		var menu = jQuery('#portal_add_content');

		instance.menu = menu;

		if (menu.length) {
			var list = menu.childNodes;

			instance.menuDiv = menu.find('.portal-add-content');
			instance.menuIframe = menu.find('iframe');

			instance.portlets = menu.find('.lfr-portlet-item');
			instance.categories = menu.find('.lfr-content-category');
			instance.categoryContainers = menu.find('.lfr-add-content');

			instance.initialized = true;

			jQuery('#layout_configuration_content').trigger('focus').addClass('focus');
			jQuery('#layout_configuration_content').keyup(
				function(event) {
					instance.startShowTimer(event, this);
				}
			);
		}
	},

	toggle : function (plid, ppid, doAsUserId) {
		var instance = this;

		if (!instance.menu) {
			var url = themeDisplay.getPathMain() + "/portal/render_portlet?p_l_id=" + plid + "&p_p_id=" + ppid + "&doAsUserId=" + doAsUserId + "&p_p_state=exclusive";

			var popup = Liferay.Popup({
				width: 250,
				noCenter: true,
				title: Liferay.Language.get("add-content"),
				onClose: function() {
					instance.menu = null;
				}
			});

			jQuery(popup).parents('.popup:first').css({top: 10, left: 10});

			AjaxUtil.update(url, popup,
				{
					onComplete: function() {
						instance._loadContent();
					}
				}
			);
		}
	},

	searchField: function(event, obj) {
		var instance = this;

		var word = jQuery.trim(obj.value).toLowerCase();
		var portlets = instance.portlets;
		var categories = instance.categories;
		var categoryContainers = instance.categoryContainers;

		if (word != '*' && word.length) {
			word = word.match(/[a-zA-Z0-9]*/g).join("");
			portlets.hide();
			categories.hide();
			categoryContainers.hide();
			portlets.each(
				function(i) {
					var name = this.id.toLowerCase();
					if (name.indexOf(word) > -1) {
						var portlet = jQuery(this);
						portlet.show();
						portlet.parents('.lfr-content-category').addClass('visible').removeClass('hidden').show();
						portlet.parents('.lfr-add-content').addClass('expanded').removeClass('collapsed').show();
					}
				}
			);
		}
		else {
			if (!word.length) {
				categories.addClass('hidden').removeClass('visible').css('display', '');
				categoryContainers.addClass('collapsed').removeClass('expanded').css('display', '');
				portlets.css('display', '');
			}
			else if (word == '*') {
				categories.addClass('visible').removeClass('hidden');
				categoryContainers.addClass('expanded').removeClass('collapsed');
				portlets.show();
			}
		}
	},

	startShowTimer : function (event, obj) {
		var instance = this;

		if (instance.showTimer) {
			clearTimeout(instance.showTimer);
			instance.showTimer = 0;
		}

		instance.showTimer = setTimeout(
			function() {
				instance.searchField(event, obj);
			},
			250
		);
	},

	_loadContent: function() {
		var instance = this;

		instance.init();

		Liferay.Util.addInputType();
		Liferay.Util.addInputFocus();

		Liferay.Publisher.subscribe('closePortlet', instance._onPortletClose, instance);

		var portlets = jQuery('.lfr-portlet-item');

		var options = {
			threshold: 10,
			onMove: function(s) {
				Liferay.Columns._onMove(s);
			},
			onComplete: function(s) {
				var container = s.container;

				var plid = container.getAttribute('plid');
				var portletId = container.getAttribute('portletId');

				if (plid && portletId) {
					var portlet = jQuery(s.container);
					var isInstanceable = (container.getAttribute('instanceable') == 'true');
					var doAsUserId = themeDisplay.getDoAsUserIdEncoded();
					var portletBound = addPortlet(plid, portletId, doAsUserId, true);

					if (!isInstanceable) {
						if (portletBound) {
							portlet.addClass('lfr-portlet-used');
							portlet.unbind('mousedown');
						}
					}
					else {
						Liferay.Columns.add(portlet, options);
					}

					portlet.css(
						{
							top: 0,
							left: 0
						}
					);

					s.container = portletBound;

					var completed = Liferay.Columns._onComplete(s);

					if (completed) {
						portlet.Highlight(750, '#ffe98f');
					}
					else {
						if (isInstanceable) {
							portletId = portletBound.id;
							portletId = portletId.replace(/^p_p_id_(.*)_$/, '$1');
						}

						closePortlet(plid, portletId, doAsUserId, true);
					}
				}
			}
		};

		instance._layoutOptions = options;

		portlets.each(
			function() {
				if (this.className.indexOf('lfr-portlet-used') == -1) {
					Liferay.Columns.add(this, options);
				}
			}
		);

		if (Liferay.Browser.is_ie) {
			portlets.hover(
				function() {
					this.className += ' over';
				},
				function() {
					this.className = this.className.replace('over', '');
				}
			);
		}

		jQuery('.lfr-add-content > h2').click(
			function() {
				var heading = jQuery(this).parent();
				var category = heading.find('> .lfr-content-category');

				category.toggleClass('hidden').toggleClass('visible');
				heading.toggleClass('collapsed').toggleClass('expanded');
			}
		);
	},

	_onPortletClose: function(portletData) {
		var instance = this;

		var popup = jQuery('#portal_add_content');
		var item = popup.find('.lfr-portlet-item[@plid=' + portletData.plid + '][@portletId=' + portletData.portletId + '][@instanceable=false]');

		if (item.is('.lfr-portlet-used')) {
			item.removeClass('lfr-portlet-used')
			Liferay.Columns.add(item[0], instance._layoutOptions);
		}
	}
};