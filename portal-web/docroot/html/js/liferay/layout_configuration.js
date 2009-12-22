Liferay.LayoutConfiguration = {
	showTemplates: function() {
		var instance = this;

		var url = themeDisplay.getPathMain() + '/layout_configuration/templates';

		AUI().use(
			'dialog',
			function(A) {
				var dialog = new A.Dialog(
					{
						title: Liferay.Language.get('layout'),
						modal: true,
						width: 700,
						centered: true
					}
				)
				.render();

				dialog.plug(
					A.Plugin.IO,
					{
						data: {
							p_l_id: themeDisplay.getPlid(),
							doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
							redirect: Liferay.currentURL
						},
						uri: url
					}
				);
			}
		);
	},

	toggle: function(){}
};

AUI().add(
	'liferay-layout-configuration',
	function(A) {
		var DDM = A.DD.DDM;

		var LayoutConfiguration = {
			categories: [],
			portlets: [],
			showTimer: 0,

			init: function() {
				var instance = this;

				var menu = A.one('#portal_add_content');

				instance.menu = menu;

				if (menu) {
					instance.portlets = menu.all('.lfr-portlet-item');
					instance.categories = menu.all('.lfr-content-category');
					instance.categoryContainers = menu.all('.lfr-add-content');

					var data = function(node) {
						var value = node.attr('id');

						return Liferay.Util.uncamelize(value).toLowerCase();
					};

					var isVisible = function(item, index, collection) {
						return !item.hasClass('aui-helper-hidden');
					};

					new A.LiveSearch(
						{
							data: data,
							hide: function(node) {
								node.hide();
							},
							input: '#layout_configuration_content',
							nodes: '#portal_add_content .lfr-portlet-item',
							show: function(node) {
								node.show();

								var categoryParent = node.ancestor('.lfr-content-category');
								var contentParent = node.ancestor('.lfr-add-content');

								if (categoryParent) {
									categoryParent.show();
								}

								if (contentParent) {
									contentParent.replaceClass('collapsed', 'expanded');
									contentParent.show();
								}
							}
						}
					);

					new A.LiveSearch(
						{
							after: {
								search: function(event) {
									if (!this.query) {
										instance.categories.hide();
										instance.categoryContainers.replaceClass('expanded', 'collapsed');
										instance.categoryContainers.show();
									}

									if (this.query == '*') {
										instance.categories.show();
										instance.categoryContainers.replaceClass('collapsed', 'expanded');
										instance.categoryContainers.show();

										instance.portlets.show();
									}
								}
							},
							data: data,
							hide: function(node) {
								var instance = this;

								var children = node.all('.lfr-content-category > div');

								var action = 'hide';

								if (children.some(isVisible)) {
									action = 'show';
								}

								node[action]();
							},
							input: '#layout_configuration_content',
							nodes: '#portal_add_content .lfr-add-content'
						}
					);
				}
			},

			_addPortlet: function(portlet, options) {
				var instance = this;

				var portletMetaData = instance._getPortletMetaData(portlet);

				if (!portletMetaData.portletUsed) {
					var plid = portletMetaData.plid;
					var portletId = portletMetaData.portletId;
					var isInstanceable = portletMetaData.instanceable;

					if (!isInstanceable) {
						portlet.addClass('lfr-portlet-used');

						var node = A.get(portlet[0]);
						var draggable = DDM.getDrag(node);

						if (draggable) {
							var eventHandle = draggable.after(
								'drag:end',
								function(event) {
									draggable.set('lock', true);

									eventHandle.detach();
								}
							);
						}
					}

					var placeHolder = A.Node.create('<div class="loading-animation" />');
					var onComplete = null;
					var beforePortletLoaded = null;

					if (options) {
						var item = options.item;

						options.placeHolder = placeHolder;
						onComplete = options.onComplete;
						beforePortletLoaded = options.beforePortletLoaded;

						item.placeAfter(placeHolder);
						item.remove();
					}
					else {
						if (instance._dropZones && instance._dropZones.length) {
							A.one(instance._dropZones[0]).prepend(placeHolder);
						}
					}

					var portletOptions = {
						beforePortletLoaded: beforePortletLoaded,
						onComplete: onComplete,
						plid: plid,
						portletId: portletId,
						placeHolder: placeHolder
					}

					var portletPosition = Liferay.Portlet.add(portletOptions);

					instance._loadPortletFiles(portletMetaData);
				}
			},

			_getPortletMetaData: function(portlet) {
				var instance = this;

				var portletMetaData = portlet._LFR_portletMetaData;

				if (!portletMetaData) {
					var instanceable = (portlet.attr('instanceable') == 'true');
					var plid = portlet.attr('plid');
					var portletId = portlet.attr('portletId');
					var portletUsed = portlet.hasClass('lfr-portlet-used');
					var headerPortalCssPaths = (portlet.attr('headerPortalCssPaths') || '').split(',');
		            var headerPortletCssPaths = (portlet.attr('headerPortletCssPaths') || '').split(',');
					var footerPortalCssPaths = (portlet.attr('footerPortalCssPaths') || '').split(',');
					var footerPortletCssPaths = (portlet.attr('footerPortletCssPaths') || '').split(',');

					portletMetaData = {
						instanceable: instanceable,
						plid: plid,
						portletId: portletId,
						portletPaths: {
							footer: footerPortletCssPaths,
							header: headerPortletCssPaths
						},
						portalPaths: {
							footer: footerPortalCssPaths,
							header: headerPortalCssPaths
						},
						portletUsed: portletUsed
					}

					portlet._LFR_portletMetaData = portletMetaData;
				}

				return portletMetaData;
			},

			_loadContent: function() {
				var instance = this;

				instance.init();

				Liferay.Util.addInputType();

				Liferay.on('closePortlet', instance._onPortletClose, instance);

				instance._portletItems = instance._dialogBody.all('div.lfr-portlet-item');

				var portlets = instance._portletItems;

				instance._dialogBody.delegate(
					'click',
					function(event) {
						var link = event.currentTarget;
						var portlet = link.ancestor('.lfr-portlet-item');

						instance._addPortlet(portlet);
					},
					'a'
				);

				var portletItem = columnPortletItem;

				var layoutHandler = Liferay.Layout.layoutHandler;

				layoutHandler.on('drop:hit', instance._onPortletDrop);

				if (Liferay.Layout.isFreeForm) {
					portletItem = freeFormPortletItem;
				}
				else {
					instance._dropZones = layoutHandler.dropZones;
				}

				portlets.each(
					function(item, index, collection) {
						var draggablePortlet = new portletItem(
							{
								bubbles: layoutHandler,
								node: item,
								groups: 'portlets'
							}
						)
						.plug(
							A.Plugin.DDProxy,
							{
								borderStyle: '0',
								moveOnEnd: false
							}
						)
						.plug(A.Plugin.DDWinScroll);

						if (item.hasClass('lfr-portlet-used')) {
							draggablePortlet.set('lock', true);
						}
					}
				);

				if (Liferay.Browser.isIe()) {
					instance._dialogBody.delegate(
						'mouseenter',
						function(event) {
							event.currentTarget.addClass('over');
						},
						'.lfr-portlet-item'
					);

					instance._dialogBody.delegate(
						'mouseenter',
						function(event) {
							event.currentTarget.removeClass('over');
						},
						'.lfr-portlet-item'
					);
				}

				instance._dialogBody.delegate(
					'click',
					function(event) {
						var heading = event.currentTarget.get('parentNode');
						var category = heading.one('> .lfr-content-category');

						if (category) {
							category.toggle();
						}

						if (heading) {
							heading.toggleClass('collapsed').toggleClass('expanded');
						}
					},
					'.lfr-add-content > h2'
				);

				Liferay.Util.focusFormField('#layout_configuration_content');
			},

			_loadPortletFiles: function(portletMetaData) {
				var instance = this;

				var headerPortalCssPaths = portletMetaData.portalPaths.header;
				var footerPortalCssPaths = portletMetaData.portalPaths.footer;
				var headerPortletCssPaths = portletMetaData.portletPaths.header;
				var footerPortletCssPaths = portletMetaData.portletPaths.footer;

				var head = A.one('head');
				var body = A.getBody();

				var headerCSS = headerPortalCssPaths.concat(headerPortletCssPaths);
				var footerCSS = footerPortalCssPaths.concat(footerPortletCssPaths);

				A.each(
					headerCSS,
					function(item, index, collection) {
						var styleSheet = A.Node.create('<link href="' + item + '" rel="stylesheet" type="text/css" />');

						head.prepend(styleSheet);
					}
				);

				if (Liferay.Browser.isIe()) {
					A.all('body link').appendTo(head);

					A.all('link.lfr-css-file').each(
						function(item, index, collection) {
							document.createStyleSheet(item.href);
						}
					);
				}

				A.each(
					footerCSS,
					function(item, index, collection) {
						var styleSheet = A.Node.create('<link href="' + item + '" rel="stylesheet" type="text/css" />');

						body.append(styleSheet);
					}
				);
			},

			_onPortletClose: function(event) {
				var instance = this;

				var popup = A.one('#portal_add_content');

				if (popup) {
					var item = popup.one('.lfr-portlet-item[plid=' + event.plid + '][portletId=' + event.portletId + '][instanceable=false]');

					if (item && item.hasClass('lfr-portlet-used')) {
						item.removeClass('lfr-portlet-used');

						var draggable = DDM.getDrag(item);

						if (draggable) {
							draggable.set('lock', false);
						}
					}
				}
			},

			_onPortletDrop: function(event) {
				var instance = this;

				var drag = event.drag;

				if ((drag instanceof columnPortletItem)) {
					event.preventDefault();
				}
			}
		};

		var columnPortletItem = function() {
			columnPortletItem.superclass.constructor.apply(this, arguments);
		};

		columnPortletItem.ATTRS = {
			placeholder: {
				getter: function() {
					var instance = this;

					return instance._getPlaceholder();
				}
			}
		};

		A.extend(
			columnPortletItem,
			Liferay.Layout.DraggablePortlet,
			{
				initializer: function() {
					var instance = this;

					instance.on('drag:drophit', instance._onDropHitItem);
					instance.on('drag:end', instance._onDragEndItem);
					instance.on('drag:start', instance._onDragStartItem);
				},

				_getPlaceholder: function() {
					var instance = this;

					if (!instance._placeholder) {
						var node = instance.get('node');

						var placeholder = node.cloneNode();

						placeholder.setStyles(
							{
								height: '200px',
								visibility: 'hidden',
								width: '300px'
							}
						);

						placeholder.appendTo('body');

						instance._placeholder = placeholder;
					}

					return instance._placeholder;
				},

				_onDropHitItem: function(event) {
					var instance = this;

					var portlet = instance.get('node');
					var proxy = instance.get('dragNode');

					var placeholder = instance.get('placeholder');

					var options = {
						item: placeholder
					};

					LayoutConfiguration._addPortlet(portlet, options);

					placeholder.hide();
				},

				_onDragEndItem: function(event) {
					var instance = this;

					var placeholder = instance.get('placeholder')

					placeholder.appendTo('body');

					placeholder.addClass('aui-helper-hidden');
				},

				_onDragStartItem: function(event) {
					var instance = this;

					var placeholder = instance.get('placeholder');

					placeholder.addClass('not-intersecting');
					placeholder.removeClass('aui-helper-hidden');

					instance.get('node').removeClass('yui-dd-dragging');
				},

				_updateProxy: function() {
					var instance = this;

					var original = instance.get('node');
					var proxy = instance._getProxy();

					var titleHtml = original.attr('title');

					proxy.one('.portlet-title').html(titleHtml);

					proxy.setStyles(
						{
							height: '200px',
							width: '300px'
						}
					);
				}
			}
		);

		var freeFormPortletItem = function() {
			freeFormPortletItem.superclass.constructor.apply(this, arguments);
		};

		A.extend(
			freeFormPortletItem,
			columnPortletItem,
			{
				_getPlaceholder: function() {
					var instance = this;

					freeFormPortletItem.superclass._getPlaceholder.apply(instance, arguments);

					instance._placeholder.appendTo('#column-1');

					return instance._placeholder;
				},

				_onDragStartItem: function(event) {
					var instance = this;

					instance.get('dragNode').appendTo('#column-1');

					freeFormPortletItem.superclass._onDragStartItem.apply(instance, arguments);
				},

				_onDragEndItem: function(event) {
					var instance = this;

					var portlet = instance.get('node');
					var proxy = instance.get('dragNode');

					var proxyStyle = proxy.get('style');

					proxy = proxy.one('.aui-proxy');

					var proxyHeight = proxy.get('offsetHeight');
					var proxyWidth = proxy.get('offsetWidth');

					var position = {
						left: proxyStyle.left,
						top: proxyStyle.top
					};

					var dimensions = {
						height: proxyHeight + 'px',
						position: 'absolute',
						width: proxyWidth + 'px'
					};

					var options = {
						beforePortletLoaded: function(placeHolder) {
							placeHolder = A.one(placeHolder);

							placeHolder.setStyles(position);
							placeHolder.setStyles(dimensions);
						},
						item: proxy,
						onComplete: function(portlet, portletId) {
							portlet = A.one(portlet);

							portlet.setStyles(position);

							Liferay.Layout.layoutHandler._moveToTop(portlet);
							Liferay.Layout.layoutHandler._savePosition(portlet);
						}
					};

					LayoutConfiguration._addPortlet(portlet, options);
				},

				_onDropHitItem: function() {
				}
			}
		);

		A.mix(Liferay.LayoutConfiguration, LayoutConfiguration);
	},
	'',
	{
		requires: ['dd', 'liferay-layout', 'live-search'],
		use: []
	}
);