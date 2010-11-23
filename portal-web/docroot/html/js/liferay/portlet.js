;(function(A, Liferay) {
	var Util = Liferay.Util;

	var arrayIndexOf = A.Array.indexOf;

	var Portlet = {
		list: [],

		isStatic: function(portletId) {
			var instance = this;

			var id = Util.getPortletId(portletId.id || portletId);

			return (id in instance._staticPortlets);
		},

		refreshLayout: function(portletBoundary){
		},

		_defCloseFn: function(event) {
			var instance = this;

			event.portlet.remove(true);

			A.io.request(
				themeDisplay.getPathMain() + '/portal/update_layout',
				{
					data: {
						cmd: 'delete',
						doAsUserId: event.doAsUserId,
						p_l_id: event.plid,
						p_p_id: event.portletId
					}
				}
			);
		},

		_loadPortletFiles: function(response) {
			var headerCssPaths = response.headerCssPaths || [];
			var footerCssPaths = response.footerCssPaths || [];

			var headerJavaScriptPaths = response.headerJavaScriptPaths || [];
			var footerJavaScriptPaths = response.footerJavaScriptPaths || [];

			var head = A.one('head');
			var body = A.getBody();

			if (headerCssPaths.length) {
				A.Get.css(
					headerCssPaths,
					{
						insertBefore: head.get('firstChild').getDOM(),
						onSuccess: function(event) {
							if (Liferay.Browser.isIe()) {
								A.all('body link').appendTo(head);

								A.all('link.lfr-css-file').each(
									function(item, index, collection) {
										document.createStyleSheet(item.get('href'));
									}
								);
							}
						}
					}
				);
			}

			var lastChild = body.get('lastChild').getDOM();

			if (footerCssPaths.length) {
				A.Get.css(
					footerCssPaths,
					{
						insertBefore: lastChild
					}
				);
			}

			if (headerJavaScriptPaths.length) {
				A.Get.script(headerJavaScriptPaths);
			}

			if (footerJavaScriptPaths.length) {
				A.Get.script(
					footerJavaScriptPaths,
					{
						insertBefore: lastChild
					}
				);
			}
		},

		_staticPortlets: {}
	};

	Liferay.provide(
		Portlet,
		'add',
		function(options) {
			var instance = this;

			Liferay.fire('initLayout');

			var plid = options.plid || themeDisplay.getPlid();
			var portletId = options.portletId;
			var doAsUserId = options.doAsUserId || themeDisplay.getDoAsUserIdEncoded();

			var placeHolder = options.placeHolder;

			if (!placeHolder) {
				placeHolder = A.Node.create('<div class="loading-animation" />');
			}
			else {
				placeHolder = A.one(placeHolder);
			}

			var positionOptions = options.positionOptions;
			var beforePortletLoaded = options.beforePortletLoaded;
			var onComplete = options.onComplete;

			var container = A.one(Liferay.Layout.options.dropContainer);

			if (!container) {
				return;
			}

			var portletPosition = 0;
			var currentColumnId = 'column-1';

			if (options.placeHolder) {
				var column = placeHolder.get('parentNode');

				placeHolder.addClass('portlet-boundary');

				portletPosition = column.all('.portlet-boundary').indexOf(placeHolder);

				currentColumnId = Util.getColumnId(column.attr('id'));
			}

			var url = themeDisplay.getPathMain() + '/portal/update_layout';

			var data = {
				cmd: 'add',
				dataType: 'json',
				doAsUserId: doAsUserId,
				p_l_id: plid,
				p_p_col_id: currentColumnId,
				p_p_col_pos: portletPosition,
				p_p_id: portletId,
				p_p_isolated: true
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
		['aui-base']
	);

	Liferay.provide(
		Portlet,
		'addHTML',
		function(options) {
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

				var portletBound = A.Node.create('<div></div>');

				portletBound.plug(A.Plugin.ParseContent);

				portletBound.setContent(html);
				portletBound = portletBound.get('firstChild');

				var id = portletBound.attr('id');

				var portletId = Util.getPortletId(id);

				portletBound.portletId = portletId;

				placeHolder.hide();
				placeHolder.placeAfter(portletBound);

				placeHolder.remove();

				instance.refreshLayout(portletBound);

				Util.addInputType(portletBound);

				if (window.location.hash) {
					window.location.hash = 'p_' + portletId;
				}

				portletBoundary = portletBound;

				if (Liferay.Layout) {
					Liferay.Layout.updateCurrentPortletInfo(portletBoundary);

					if (container) {
						Liferay.Layout.syncEmptyColumnClassUI(container);
					}
				}

				if (onComplete) {
					onComplete(portletBoundary, portletId);
				}

				return portletId;
			};

			if (beforePortletLoaded) {
				beforePortletLoaded(placeHolder);
			}

			A.io.request(
				url,
				{
					data: data,
					dataType: dataType,
					on: {
						success: function(event, id, obj) {
							var instance = this;

							var response = instance.get('responseData');

							if (dataType == 'html') {
								addPortletReturn(response);
							}
							else {
								if (response.refresh) {
									location.reload();
								}
								else {
									addPortletReturn(response.portletHTML);

									Portlet._loadPortletFiles(response);
								}
							}
						}
					}
				}
			);
		},
		['aui-io-request', 'aui-parse-content']
	);

	Liferay.provide(
		Portlet,
		'close',
		function(portlet, skipConfirm, options) {
			var instance = this;

			portlet = A.one(portlet);

			if (portlet && (skipConfirm || confirm(Liferay.Language.get('are-you-sure-you-want-to-remove-this-component')))) {
				options = options || {};

				options.plid = options.plid || themeDisplay.getPlid();
				options.doAsUserId = options.doAsUserId || themeDisplay.getDoAsUserIdEncoded();
				options.portlet = portlet;
				options.portletId = portlet.portletId;

				Liferay.fire('closePortlet', options);
			}
			else {
				self.focus();
			}
		},
		['aui-io-request']
	);

	Liferay.provide(
		Portlet,
		'minimize',
		function(portlet, el, options) {
			var instance = this;

			options = options || {};

			var plid = options.plid || themeDisplay.getPlid();
			var doAsUserId = options.doAsUserId || themeDisplay.getDoAsUserIdEncoded();

			portlet = A.one(portlet);

			if (portlet) {
				var content = portlet.one('.portlet-content-container');

				if (content) {
					var restore = content.hasClass('aui-helper-hidden');

					content.toggle();
					portlet.toggleClass('portlet-minimized');

					var link = A.one(el);

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

					var html = '';
					var portletBody = content.one('.portlet-body');

					if (portletBody) {
						html = portletBody.html();
					}

					var hasBodyContent = !!(A.Lang.trim(html));

					if (hasBodyContent) {
						content.unplug(A.Plugin.IO);
					}
					else {
						content.plug(
							A.Plugin.IO,
							{
								autoLoad: false,
								data: {
									doAsUserId: doAsUserId,
									p_l_id: plid,
									p_p_id: portlet.portletId,
									p_p_state: 'exclusive'
								},
								showLoading: false,
								uri: themeDisplay.getPathMain() + '/portal/render_portlet'
							}
						);
					}

					A.io.request(
						themeDisplay.getPathMain() + '/portal/update_layout',
						{
							after: {
								success: function() {
									if (restore && content.io) {
										content.io.start();
									}
								}
							},
							data: {
								cmd: 'minimize',
								doAsUserId: doAsUserId,
								p_l_id: plid,
								p_p_id: portlet.portletId,
								p_p_restore: restore
							}
						}
					);
				}
			}
		},
		['aui-io']
	);

	Liferay.provide(
		Portlet,
		'onLoad',
		function(options) {
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

			var portlet = A.one('#' + namespacedId);

			if (portlet && !portlet.portletProcessed) {
				portlet.portletProcessed = true;
				portlet.portletId = portletId;
				portlet.columnPos = columnPos;
				portlet.isStatic = isStatic;
				portlet.refreshURL = refreshURL;

				// Functions to run on portlet load

				if (canEditTitle) {
					Util.portletTitleEdit(
						{
							doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
							obj: portlet,
							plid: themeDisplay.getPlid(),
							portletId: portletId
						}
					);
				}

				if (!themeDisplay.layoutMaximized) {
					var configurationLink = portlet.all('.portlet-configuration a');

					configurationLink.on(
						'click',
						function(event) {
							var configurationURL = event.currentTarget.attr('href');

							instance.openConfiguration(portlet, portletId, configurationURL, namespacedId);

							event.preventDefault();
						}
					);

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
								instance._loadCSSEditor(portletId);
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

				var index = arrayIndexOf(list, portletId);

				if (index > -1) {
					list.splice(index, 1);

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
		},
		['aui-base']
	);

	Liferay.provide(
		Portlet,
		'refresh',
		function(portlet) {
			var instance = this;

			portlet = A.one(portlet);

			if (portlet && portlet.refreshURL) {
				var url = portlet.refreshURL;
				var id = portlet.attr('portlet');

				var placeHolder = A.Node.create('<div class="loading-animation" id="p_load' + id + '" />');

				portlet.placeBefore(placeHolder);
				portlet.remove(true);

				instance.addHTML(
					{
						data: {
							p_p_state: 'normal'
						},
						onComplete: function(portlet, portletId) {
							portlet.refreshURL = url;
						},
						placeHolder: placeHolder,
						url: url
					}
				);
			}
		},
		['aui-base']
	);

	Liferay.provide(
		Portlet,
		'registerStatic',
		function(portletId) {
			var instance = this;

			var Node = A.Node;

			if (Node && portletId instanceof Node) {
				portletId = portletId.attr('id');
			}
			else if (portletId.id) {
				portletId = portletId.id;
			}

			var id = Util.getPortletId(portletId);

			instance._staticPortlets[id] = true;
		},
		['aui-base']
	);

	Liferay.provide(
		Portlet,
		'openConfiguration',
		function(portlet, portletId, configurationURL, namespacedId) {
			var instance = this;

			portlet = A.one(portlet);

			if (portlet && configurationURL) {
				var title = portlet.one('.portlet-title') || portlet.one('.portlet-title-default');

				var iframeId = namespacedId + 'configurationIframe';

				var iframeTPL = '<iframe class="configuration-frame" frameborder="0" id="' + iframeId + '" name="' + iframeId + '" src="' + configurationURL + '"></iframe>';
				var iframe = A.Node.create(iframeTPL);

				var bodyContent = A.Node.create('<div></div>');

				bodyContent.append(iframe);

				var fixSize = function(number) {
					return ((parseInt(number, 10) || 0) - 5) + 'px';
				};

				var updateIframeSize = function(event) {
					setTimeout(
						function() {
							var bodyHeight = bodyNode.getStyle('height');

							iframe.setStyle('height', fixSize(bodyHeight));

							bodyNode.loadingmask.refreshMask();
						},
						50
					);
				};

				var dialog = new A.Dialog(
					{
						after: {
							heightChange: updateIframeSize,
							widthChange: updateIframeSize
						},
						align: {
							node: null,
							points: ['tc', 'tc']
						},
						bodyContent: bodyContent,
						destroyOnClose: true,
						draggable: true,
						title: title.html() + ' - ' + Liferay.Language.get('configuration'),
						width: 820
					}
				).render();

				dialog.move(dialog.get('x'), dialog.get('y') + 100);

				var bodyNode = dialog.bodyNode;

				bodyNode.plug(A.LoadingMask).loadingmask.show();

				iframe.on(
					'load',
					function(event) {
						var iframeDoc = iframe.get('contentWindow.document');

						iframeDoc.get('documentElement').setStyle('overflow', 'visible');

						var iframeBody = iframeDoc.get('body');

						iframeBody.addClass('configuration-popup');

						iframe.set('height', iframeBody.get('scrollHeight'));

						A.on(
							'key',
							function(event) {
								dialog.close();
							},
							[iframeBody],
							'down:27'
						);

						var closeButton = iframeBody.one('.aui-button-input-cancel');

						if (closeButton) {
							closeButton.on('click', dialog.close, dialog);
						}

						var rolesSearchContainer = iframeBody.one('#rolesSearchContainerSearchContainer');

						if (rolesSearchContainer) {
							rolesSearchContainer.delegate(
								'click',
								function(event){
									event.preventDefault();

									submitForm(document.hrefFm, event.currentTarget.attr('href'));
								},
								'a'
							);
						}

						bodyNode.loadingmask.hide();
					}
				);
			}
		},
		['aui-dialog', 'aui-loading-mask']
	);

	Liferay.provide(
		Portlet,
		'_loadCSSEditor',
		function(portletId) {
			Liferay.PortletCSS.init(portletId);
		},
		['liferay-look-and-feel']
	);

	Liferay.publish(
		'closePortlet',
		{
			defaultFn: Portlet._defCloseFn
		}
	);

	Liferay.publish(
		'allPortletsReady',
		{
			fireOnce: true
		}
	);

	// Backwards compatability

	Portlet.ready = function(fn) {
		Liferay.on(
			'portletReady',
			function(event) {
				fn(event.portletId, event.portlet);
			}
		);
	};

	Liferay.Portlet = Portlet;
})(AUI(), Liferay);