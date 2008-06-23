Liferay.Portlet = {
	fn: {},
	fnAll: [],
	fnLast: [],
	ajaxList: {},
	list: {},

	close: function(portlet, skipConfirm, options) {
		var instance = this;

		if (skipConfirm || confirm(Liferay.Language.get('are-you-sure-you-want-to-remove-this-component'))) {
			options = options || {};

			var plid = options.plid || themeDisplay.getPlid();
			var doAsUserId = options.doAsUserId || themeDisplay.getDoAsUserIdEncoded();

			var portletId = portlet.portletId;
			var currentPortlet = jQuery(portlet);
			var column = currentPortlet.parents('.lfr-portlet-column:first');

			currentPortlet.remove();
			jQuery('#' + portletId).remove();

			if (LayoutConfiguration) {
				LayoutConfiguration.initialized = false;
			}

			var url = themeDisplay.getPathMain() + '/portal/update_layout';

			jQuery.ajax(
				{
					url: url,
					data: {
						p_l_id: plid,
						p_p_id: portletId,
						doAsUserId: doAsUserId,
						cmd: 'delete'
					}
				}
			);

			var portletsLeft = column.find('.portlet-boundary').length;

			if (!portletsLeft) {
				column.addClass('empty');
			}

			instance.remove(portletId);

			Liferay.Publisher.register('closePortlet');
			Liferay.Publisher.deliver('closePortlet', {plid: plid, portletId: portletId});
		}
		else {
			self.focus();
		}
	},

	isAjax: function(id) {
		return (this.ajaxList[id] == 1);
	},

	flagAjax: function(id) {
		this.ajaxList[id] = 1;
	},

	minimize: function(portlet, el, options) {
		var instance = this;

		options = options || {};

		var plid = options.plid || themeDisplay.getPlid();
		var doAsUserId = options.doAsUserId || themeDisplay.getDoAsUserIdEncoded();

		var content = jQuery('.portlet-content-container', portlet);
		var restore = content.is(':hidden');

		content.toggle(
			'blind',
			{
				direction: 'vertical'
			},
			'fast',
			function() {
				var action = (restore) ? 'removeClass' : 'addClass';
				jQuery('.portlet', portlet)[action]('portlet-minimized');

				if (el) {
					var minimizeKey = Liferay.Language.get('minimize');
					var restoreKey = Liferay.Language.get('restore');
					var title = (restore) ? minimizeKey : restoreKey;

					var link = jQuery(el);
					var img = link.find('img');

					var imgSrc = img.attr('src');
					if (restore) {
						imgSrc = imgSrc.replace(/restore.png$/, 'minimize.png');
					}
					else {
						imgSrc = imgSrc.replace(/minimize.png$/, 'restore.png');
					}

					link.attr('title', title);
					img.attr('src', imgSrc);
				}
			}
		);

		jQuery.ajax(
			{
				url: themeDisplay.getPathMain() + '/portal/update_layout',
				data: {
					p_l_id: plid,
					p_p_id: portlet.portletId,
					p_p_restore: restore,
					doAsUserId: doAsUserId,
					cmd: 'minimize'
				}
			}
		);
	},

	onLoad: function(options) {
		var instance = this;

		var canEditTitle = options.canEditTitle;
		var columnPos = options.columnPos;
		var isStatic = (options.isStatic == 'no') ? null : options.isStatic;
		var namespacedId = options.namespacedId;
		var portletId = options.portletId;

		jQuery(
			function () {
				var jPortlet = jQuery('#' + namespacedId);
				var portlet = jPortlet[0];

				if (!portlet.portletProcessed) {
					portlet.portletProcessed = true;
					portlet.portletId = portletId;
					portlet.columnPos = columnPos;
					portlet.isStatic = isStatic;

					if (!Liferay.Portlet.isAjax(portletId)) {
						Liferay.Portlet.process(portletId);
					}

					// Functions to run on portlet load

					if (canEditTitle) {
						Liferay.Util.portletTitleEdit(
							{
								obj: jPortlet,
								plid: themeDisplay.getPlid(),
								doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
								portletId: portletId
							}
						);
					}

					if (!themeDisplay.layoutMaximized) {
						jQuery('.portlet-configuration-icon:first a', portlet).click(
							function(event) {
								location.href = this.href + '&previewWidth=' + portlet.offsetHeight;
								return false;
							}
						);

						jQuery('.portlet-minimize-icon:first a', portlet).click(
							function(event) {
								instance.minimize(portlet, this);
								return false;
							}
						);

						jQuery('.portlet-maximize-icon:first a', portlet).click(
							function(event) {
								submitForm(document.hrefFm, this.href);
								return false;
							}
						);

						jQuery('.portlet-close-icon:first a', portlet).click(
							function(event) {
								instance.close(portlet);
								return false;
							}
						);

						jQuery('.portlet-refresh-icon:first a', portlet).click(
							function(event) {
								instance.refresh(portlet);
								return false;
							}
						);

						jQuery('.portlet-print-icon:first a', portlet).click(
							function(event) {
								location.href = this.href;
								return false;
							}
						);

						jQuery('.portlet-css-icon:first a', portlet).click(
							function(event) {
								Liferay.PortletCSS.init(portlet.portletId);
							}
						)
					}
				}
			}
		);
	},

	process: function(id) {
		var status = this.list[id];
		var count = 0;

		this.list[id] = 0;

		if (status == 1) {
			this.processPortlet(id);
			this.processAll(id);
		}
		else if (status == 0) {

			// Already processed. Do nothing.

		}
		else {

			// New portlet. Process and mark.

			this.processPortlet(id);
		}

		for (var i in this.list) {
			count += this.list[i];
		}

		if (count == 0) {
			this.processLast(id);
		}
	},

	processAll: function(id) {
		for (var i = 0; i < this.fnAll.length; i++) {
			this.fnAll[i](id, jQuery("#p_p_id_" + id + "_"));
		}
	},

	processPortlet: function(id) {
		if (this.fn[id]) {
			for (var i = 0; i < this.fn[id].length; i++) {
				this.fn[id][i](id, jQuery("#p_p_id_" + id + "_"));
			}
			this.fn[id] = [];
		}
	},

	processLast: function(id) {
		for (var i = 0; i < this.fnLast.length; i++) {
			this.fnLast[i](id);
		}
		this.fnLast = [];
	},

	ready: function(arg1, arg2) {
		if (typeof arg1 == "function") {
			this.fnAll.push(arg1);
		}
		else if (typeof arg1 == "string" && typeof arg2 == "function") {
			if (!this.fn[arg1]) {
				this.fn[arg1] = [];
			}

			this.fn[arg1].push(arg2);
		}
	},

	refresh: function(portlet) {
		var instance = this;

		if (portlet.refreshURL) {
			var url = portlet.refreshURL;
			var id = portlet.id;

			portlet = jQuery(portlet);

			var placeHolder = jQuery('<div class="loading-animation" id="p_load' + id + '" />');

			portlet.before(placeHolder);
			portlet.remove();

			addPortletHTML(
				{
					url: url,
					placeHolder: placeHolder[0],
					onComplete: function(portlet, portletId) {
						portlet.refreshURL = url;
					}
				}
			);
		}
	},

	remove: function(id) {
		this.ajaxList[id] = 0;
		this.list[id] = 1;
		this.fn[id] = [];
	},

	last: function(arg1) {
		this.fnLast.push(arg1);
	}
};

jQuery.fn.last = function(fn) {
	Liferay.Portlet.last(fn);
};