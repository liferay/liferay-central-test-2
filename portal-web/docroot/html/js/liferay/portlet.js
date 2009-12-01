Liferay.Portlet = {
	list: [],

	add: function(options) {
		var instance = this;

		var plid = options.plid || themeDisplay.getPlid();
		var portletId = options.portletId;
		var doAsUserId = options.doAsUserId || themeDisplay.getDoAsUserIdEncoded();

		var placeHolder = options.placeHolder;

		if (!placeHolder) {
			placeHolder = AUI().Node.create('<div class="loading-animation" />');
		}
		else {
			placeHolder = AUI().one(placeHolder);
		}

		var positionOptions = options.positionOptions;
		var beforePortletLoaded = options.beforePortletLoaded;
		var onComplete = options.onComplete;

		var container = AUI().one('.lfr-portlet-column');

		if (!container) {
			return;
		}

		var portletPosition = 0;
		var currentColumnId = 'column-1';

		if (options.placeHolder) {
			var column = placeHolder.get('parentNode');

			placeHolder.addClass('portlet-boundary');

			portletPosition = column.all('.portlet-boundary').indexOf(placeHolder);

			currentColumnId = Liferay.Util.getColumnId(column.attr('id'));
		}

		var url = themeDisplay.getPathMain() + '/portal/update_layout';

		var data = {
			cmd: 'add',
			dataType: 'json',
			doAsUserId: doAsUserId,
			p_l_id: plid,
			p_p_col_id: currentColumnId,
			p_p_col_pos: portletPosition,
			p_p_id: portletId
		};

		var firstPortlet = container.one('.portlet-boundary');
		var hasStaticPortlet = (firstPortlet && firstPortlet.isStatic);

		if (!options.placeHolder && !options.plid) {
			if (!hasStaticPortlet) {
				container.prepend(placeHolder);
			}
			else {
				firstPortlet.placeAfter(placeHolder);
			}
		}

		if (themeDisplay.isFreeformLayout()) {
			container.prepend(placeHolder);
		}

		data.currentURL = Liferay.currentURL;

		return instance.addHTML(
			{
				beforePortletLoaded: beforePortletLoaded,
				data: data,
				onComplete: onComplete,
				placeHolder: placeHolder,
				url: url
			}
		);
	},

	addHTML: function(options) {
		var instance = this;

		var portletBoundary = null;

		var beforePortletLoaded = options.beforePortletLoaded;
		var data = options.data;
		var dataType = 'html';
		var onComplete = options.onComplete;
		var placeHolder = options.placeHolder;
		var url = options.url;

		if (data && data.dataType) {
			dataType = data.dataType;
		}

		var addPortletReturn = function(html) {
			var container = placeHolder.get('parentNode');

			var portletBound = AUI().Node.create('<div></div>');

			portletBound.set('innerHTML', html);
			portletBound = portletBound.get('firstChild');
			var id = portletBound.attr('id');

			var portletId = Liferay.Util.getPortletId(id);

			portletBound.portletId = portletId;

			placeHolder.hide();
			placeHolder.placeAfter(portletBound);
			placeHolder.remove();

			instance.refreshLayout(portletBound);

			Liferay.Util.addInputType(id);

			if (window.location.hash) {
				window.location.hash = 'p_' + portletId;
			}

			portletBoundary = portletBound;

			if (onComplete) {
				onComplete(portletBoundary, portletId);
			}

			container.removeClass('empty');

			return portletId;
		};

		if (beforePortletLoaded) {
			beforePortletLoaded(placeHolder);
		}

		AUI().use(
			'io',
			'json',
			function(A) {
				A.io(
					url,
					{
						data: A.toQueryString(data),
						method: 'POST',
						on: {
							success: function(id, obj) {
								var instance = this;

								var response = obj.responseText;

								if (dataType == 'html') {
									addPortletReturn(response);
								}
								else {
									response = A.JSON.parse(response);

									if (response.refresh) {
										location.reload();
									}
									else {
										addPortletReturn(response.portletHTML);
									}
								}
							}
						}
					}
				);
			}
		);
	},

	close: function(portlet, skipConfirm, options) {
		var instance = this;

		if (skipConfirm || confirm(Liferay.Language.get('are-you-sure-you-want-to-remove-this-component'))) {
			options = options || {};

			var plid = options.plid || themeDisplay.getPlid();
			var doAsUserId = options.doAsUserId || themeDisplay.getDoAsUserIdEncoded();
			var currentPortlet = AUI().one(portlet);

			if (currentPortlet) {
				var portletId = currentPortlet.portletId;
				var column = currentPortlet.ancestor('.lfr-portlet-column');

				currentPortlet.remove();

				var url = themeDisplay.getPathMain() + '/portal/update_layout';

				AUI().use(
					'io',
					function(A) {
						A.io(
							url,
							{
								data: A.toQueryString(
									{
										cmd: 'delete',
										doAsUserId: doAsUserId,
										p_l_id: plid,
										p_p_id: portletId
									}
								),
								method: 'POST'
							}
						);
					}
				);

				if (column) {
					var portletsLeft = column.one('.portlet-boundary');

					if (!portletsLeft) {
						column.addClass('empty');
					}
				}

				Liferay.fire(
					'closePortlet',
					 {
						plid: plid,
						portletId: portletId
					}
				);
			}
		}
		else {
			self.focus();
		}
	},

	isStatic: function(portletId) {
		var instance = this;

		var id = Liferay.Util.getPortletId(portletId.id || portletId);

		return (id in instance._staticPortlets);
	},

	minimize: function(portlet, el, options) {
		var instance = this;

		options = options || {};

		var plid = options.plid || themeDisplay.getPlid();
		var doAsUserId = options.doAsUserId || themeDisplay.getDoAsUserIdEncoded();

		portlet = AUI().one(portlet);

		if (portlet) {
			var content = portlet.one('.portlet-content-container');

			if (content) {
				var restore = content.hasClass('aui-helper-hidden');

				content.toggle();
				portlet.toggleClass('portlet-minimized');

				var link = AUI().one(el);

				if (link) {
					var img = link.one('img');

					if (img) {
						var title = (restore) ? Liferay.Language.get('minimize') : Liferay.Language.get('restore');

						var imgSrc = img.attr('src');

						if (restore) {
							imgSrc = imgSrc.replace(/restore.png$/, 'minimize.png');
						}
						else {
							imgSrc = imgSrc.replace(/minimize.png$/, 'restore.png');
						}

						img.attr('alt', title);
						img.attr('title', title);

						link.attr('title', title);
						img.attr('src', imgSrc);
					}
				}

				AUI().use(
					'io',
					function(A) {
						A.io(
							themeDisplay.getPathMain() + '/portal/update_layout',
							{
								data: A.toQueryString(
									{
										cmd: 'minimize',
										doAsUserId: doAsUserId,
										p_l_id: plid,
										p_p_id: portlet.portletId,
										p_p_restore: restore
									}
								),
								method: 'POST'
							}
						);
					}
				);
			}
		}
	},

	onLoad: function(options) {
		var instance = this;

		var canEditTitle = options.canEditTitle;
		var columnPos = options.columnPos;
		var isStatic = (options.isStatic == 'no') ? null : options.isStatic;
		var namespacedId = options.namespacedId;
		var portletId = options.portletId;
		var refreshURL = options.refreshURL;

		if (isStatic) {
			instance.registerStatic(portletId);
		}

		AUI().ready(
			function(A) {
				var portlet = A.one('#' + namespacedId);

				if (portlet && !portlet.portletProcessed) {
					portlet.portletProcessed = true;
					portlet.portletId = portletId;
					portlet.columnPos = columnPos;
					portlet.isStatic = isStatic;
					portlet.refreshURL = refreshURL;

					// Functions to run on portlet load

					if (canEditTitle) {
						Liferay.Util.portletTitleEdit(
							{
								obj: portlet,
								plid: themeDisplay.getPlid(),
								doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
								portletId: portletId
							}
						);
					}

					if (!themeDisplay.layoutMaximized) {
						var configurationLink = portlet.one('.portlet-configuration a');

						if (configurationLink) {
							configurationLink.on(
								'click',
								function(event) {
									location.href = event.currentTarget.attr('href') + '&previewWidth=' + portlet.offsetHeight;

									event.halt();
								}
							);
						}

						var minimizeLink = portlet.one('.portlet-minimize a');

						if (minimizeLink) {
							minimizeLink.on(
								'click',
								function(event) {
									instance.minimize(portlet, minimizeLink);

									event.halt();
								}
							);
						}

						var maximizeLink = portlet.one('.portlet-maximize a');

						if (maximizeLink) {
							maximizeLink.on(
								'click',
								function(event) {
									submitForm(document.hrefFm, event.currentTarget.attr('href'));

									event.halt();
								}
							);
						}

						var closeLink = portlet.one('.portlet-close a');

						if (closeLink) {
							closeLink.on(
								'click',
								function(event) {
									instance.close(portlet);

									event.halt();
								}
							);
						}

						var refreshLink = portlet.one('.portlet-refresh a');

						if (refreshLink) {
							refreshLink.on(
								'click',
								A.bind(instance.refresh, instance, portlet)
							);
						}

						var printLink = portlet.one('.portlet-print a');

						if (printLink) {
							printLink.on(
								'click',
								function(event) {
									location.href = event.currentTarget.attr('href');

									event.halt();
								}
							);
						}

						var portletCSSLink = portlet.one('.portlet-css a');

						if (portletCSSLink) {
							portletCSSLink.on(
								'click',
								function(event) {
									A.use(
										'liferay-look-and-feel',
										function() {
											Liferay.PortletCSS.init(portletId);
										}
									);
								}
							);
						}
					}

					Liferay.fire(
						'portletReady',
						{
							portlet: portlet,
							portletId: portletId
						}
					);

					var list = instance.list;

					var index = list.indexOf(portletId);

					if (index > -1) {
						list.splice(index, 1);
					}

					if (!list.length) {
						Liferay.fire(
							'allPortletsReady',
							{
								portletId: portletId
							}
						);
					}
				}
			}
		);
	},

	refresh: function(portlet) {
		var instance = this;

		portlet = AUI().one(portlet);

		if (portlet && portlet.refreshURL) {
			var url = portlet.refreshURL;
			var id = portlet.attr('portlet');

			var placeHolder = AUI().Node.create('<div class="loading-animation" id="p_load' + id + '" />');

			portlet.placeBefore(placeHolder);
			portlet.remove();

			instance.addHTML(
				{
					url: url,
					placeHolder: placeHolder,
					onComplete: function(portlet, portletId) {
						portlet.refreshURL = url;
					}
				}
			);
		}
	},

	refreshLayout: function(portletBound) {
	},

	registerStatic: function(portletId) {
		var instance = this;

		var Node = AUI().Node;

		if (Node && portletId instanceof Node) {
			portletId = portletId.attr('id');
		}
		else if (portletId.id) {
			portletId = portletId.id;
		}

		var id = Liferay.Util.getPortletId(portletId);

		instance._staticPortlets[id] = true;
	},

	_staticPortlets: {}
};

// Backwards compatability

Liferay.Portlet.ready = function(fn) {
	Liferay.on(
		'portletReady',
		function(event) {
			fn(event.portletId, event.portlet);
		}
	);
};