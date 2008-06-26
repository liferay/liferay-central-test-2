Liferay.Portlet = {
	fn: {},
	fnAll: [],
	fnLast: [],
	ajaxList: {},
	list: {},

	add: function(options) {
		var instance = this;

		var plid = options.plid || themeDisplay.getPlid();
		var portletId = options.portletId;
		var doAsUserId = options.doAsUserId || themeDisplay.getDoAsUserIdEncoded();
		var placeHolder = jQuery(options.placeHolder || '<div class="loading-animation" />');
		var positionOptions = options.positionOptions;
		var beforePortletLoaded = options.beforePortletLoaded;
		var onComplete = options.onComplete;

		var refreshPortletList = getRefreshPortletList();

		var container = jQuery('.lfr-portlet-column:first');

		if (!container.length) {
			return;
		}

		var portletPosition = 0;
		var currentColumnId = 'column-1';

		if (options.placeHolder) {
			var column = placeHolder.parent();

			placeHolder.addClass('portlet-boundary');

			portletPosition = column.find('.portlet-boundary').index(placeHolder[0]);

			currentColumnId = Liferay.Util.getColumnId(column[0].id);
		}

		var url = themeDisplay.getPathMain() + '/portal/update_layout';
		var data = {
			cmd: Liferay.Constants.ADD,
			doAsUserId: doAsUserId,
			p_l_id: plid,
			p_p_id: portletId,
			p_p_col_id: currentColumnId,
			p_p_col_pos: portletPosition
		};

		if (refreshPortletList["_" + portletId]) {
			data.referer = Liferay.currentURLEncoded;
			data.refresh = 1;

			if (plid) {
				location.href = url + '?' + jQuery.param(data);
			}
		}
		else {
			var firstPortlet = container.find('.portlet-boundary:first');
			var hasStaticPortlet = (firstPortlet.length && firstPortlet[0].isStatic);

			if (!options.placeHolder && !options.plid) {
				if (!hasStaticPortlet) {
					container.prepend(placeHolder);
				}
				else {
					firstPortlet.after(placeHolder);
				}
			}

			if (themeDisplay.isFreeformLayout()) {
				container.prepend(placeHolder);
			}

			data.currentURL = Liferay.currentURLEncoded;

			return instance.addHTML(
				{
					beforePortletLoaded: beforePortletLoaded,
					data: data,
					url: url,
					placeHolder: placeHolder[0],
					onComplete: onComplete
				}
			);
		}
	},

	addHTML: function(options) {
		var instance = this;

		var portletBoundary = null;

		var url = options.url;
		var data = options.data;
		var placeHolder = options.placeHolder;
		var beforePortletLoaded = options.beforePortletLoaded;
		var onComplete = options.onComplete;

		var addPortletReturn = function(html) {
			var container = placeHolder.parentNode;

			var portletBound = jQuery('<div></div>')[0];

			portletBound.innerHTML = html;
			portletBound = portletBound.firstChild;

			var portletId = Liferay.Util.getPortletId(portletBound.id);

			portletBound.portletId = portletId;

			instance.flagAjax(portletId);

			jQuery(placeHolder).hide().after(portletBound).remove();

			instance.refreshLayout(portletBound);

			if (Liferay.Browser.is_firefox) {
				setTimeout("Liferay.Portlet.process(\"" + portletId + "\")", 0);
			}
			else {
				instance.process(portletId);
			}

			Liferay.Util.addInputType(portletBound.id);

			if (window.location.hash) {
				window.location.hash = "p_" + portletId;
			}

			portletBoundary = portletBound;

			if (onComplete) {
				onComplete(portletBoundary, portletId);
			}

			var jContainer = jQuery(container);

			if (jContainer.is('.empty')) {
				jContainer.removeClass('empty');
			}

			return portletId;
		};

		if (beforePortletLoaded) {
			beforePortletLoaded(placeHolder);
		}

		jQuery.ajax(
			{
				url: url,
				data: data,
				complete: function(xHR) {
					addPortletReturn(xHR.responseText);
				}
			}
		);
	},

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
						cmd: Liferay.Constants.DELETE
					}
				}
			);

			var portletsLeft = column.find('.portlet-boundary').length;

			if (!portletsLeft) {
				column.addClass('empty');
			}

			instance.remove(portletId);

			Liferay.trigger('closePortlet', {plid: plid, portletId: portletId});
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
				jQuery(portlet)[action]('portlet-minimized');

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

					if (!instance.isAjax(portletId)) {
						instance.process(portletId);
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
						jQuery('.portlet-configuration:first a', portlet).click(
							function(event) {
								location.href = this.href + '&previewWidth=' + portlet.offsetHeight;
								return false;
							}
						);

						jQuery('.portlet-minimize:first a', portlet).click(
							function(event) {
								instance.minimize(portlet, this);
								return false;
							}
						);

						jQuery('.portlet-maximize:first a', portlet).click(
							function(event) {
								submitForm(document.hrefFm, this.href);
								return false;
							}
						);

						jQuery('.portlet-close:first a', portlet).click(
							function(event) {
								instance.close(portlet);
								return false;
							}
						);

						jQuery('.portlet-refresh:first a', portlet).click(
							function(event) {
								instance.refresh(portlet);
								return false;
							}
						);

						jQuery('.portlet-print:first a', portlet).click(
							function(event) {
								location.href = this.href;
								return false;
							}
						);

						jQuery('.portlet-css:first a', portlet).click(
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

			instance.addHTML(
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

	refreshLayout: function(portletBound) {
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