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
						centered: true,
						io: {
							url: url,
							cfg: {
								data: {
									p_l_id: themeDisplay.getPlid(),
									doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
									redirect: Liferay.currentURL
								}
							}
						}
					}
				)
				.render();
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

				var menu = jQuery('#portal_add_content');

				instance.menu = menu;

				if (menu.length) {
					instance.portlets = menu.find('.lfr-portlet-item');
					instance.categories = menu.find('.lfr-content-category');
					instance.categoryContainers = menu.find('.lfr-add-content');

					var data = function(node) {
						var value = node.attr('id');

						return Liferay.Util.uncamelize(value).toLowerCase();
					};

					var searchField = jQuery('#layout_configuration_content');

					new A.LiveSearch(
						{
							data: data,
							hide: function(node) {
								var portlet = jQuery(node.getDOM());

								portlet.hide();
							},
							input: '#layout_configuration_content',
							nodes: '#portal_add_content .lfr-portlet-item',
							show: function(node) {
								var portlet = jQuery(node.getDOM());

								portlet.show();
								portlet.parents('.lfr-content-category').addClass('visible').removeClass('hidden').show();
								portlet.parents('.lfr-add-content').addClass('expanded').removeClass('collapsed').show();
							}
						}
					);

					new A.LiveSearch(
						{
							after: {
								search: function(event) {
									if (!this.query) {
										instance.categories.addClass('hidden').removeClass('visible').removeClass('aui-helper-hidden');
										instance.categoryContainers.addClass('collapsed').removeClass('expanded').removeClass('aui-helper-hidden');
										instance.portlets.css('display', '');
									}

									if (this.query == "*") {
										instance.categories.addClass('visible').removeClass('hidden').removeClass('aui-helper-hidden');
										instance.categoryContainers.addClass('expanded').removeClass('collapsed').removeClass('aui-helper-hidden');
										instance.portlets.show();
									}
								}
							},
							data: data,
							hide: function(node) {
								var instance = this;

								var categoryContent = jQuery('.lfr-content-category', node.getDOM());
								var totalVisibleChildren = categoryContent.find('> div:visible').length;

								if (totalVisibleChildren <= 0) {
									node.hide();
								}
								else {
									node.show();
								}
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

					var placeHolder = jQuery('<div class="loading-animation" />');
					var onComplete = null;
					var beforePortletLoaded = null;

					if (options) {
						var item = options.item;

						options.placeHolder = placeHolder[0];
						onComplete = options.onComplete;
						beforePortletLoaded = options.beforePortletLoaded;

						item.after(placeHolder);
						item.remove();
					}
					else {
						if (instance._dropZones && instance._dropZones.length) {
							jQuery(instance._dropZones[0]).prepend(placeHolder);
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
					var portletUsed = portlet.is('.lfr-portlet-used');
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

				instance._portletItems = instance._dialogBody.find('div.lfr-portlet-item');

				var portlets = instance._portletItems;

				portlets.find('a').click(
					function(event) {
						var link = jQuery(this);
						var portlet = link.parents('.lfr-portlet-item:first');

						instance._addPortlet(portlet);
					}
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

				for (var i = instance._portletItems.length - 1; i >= 0; i--){
					var node = A.get(instance._portletItems[i]);

					var draggablePortlet = new portletItem(
						{
							bubbles: layoutHandler,
							node: node,
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

					if (node.hasClass('lfr-portlet-used')) {
						draggablePortlet.set('lock', true);
					}
				}

				if (Liferay.Browser.isIe()) {
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

			_loadPortletFiles: function(portletMetaData) {
				var instance = this;

				var headerPortalCssPaths = portletMetaData.portalPaths.header;
				var footerPortalCssPaths = portletMetaData.portalPaths.footer;
				var headerPortletCssPaths = portletMetaData.portletPaths.header;
				var footerPortletCssPaths = portletMetaData.portletPaths.footer;

				var head = jQuery('head');
				var docBody = jQuery(document.body);

				var headerCSS = headerPortalCssPaths.concat(headerPortletCssPaths);
				var footerCSS = footerPortalCssPaths.concat(footerPortletCssPaths);

				jQuery.each(
					headerCSS,
					function(i, n) {
						head.prepend('<link href="' + this + '" rel="stylesheet" type="text/css" />');
					}
				);

				if (Liferay.Browser.isIe()) {
					jQuery('body link').appendTo('head');

					jQuery('link.lfr-css-file').each(
						function(i) {
							document.createStyleSheet(this.href);
						}
					);
				}

				jQuery.each(
					footerCSS,
					function(i, n) {
						docBody.append('<link href="' + this + '" rel="stylesheet" type="text/css" />');
					}
				);
			},

			_onPortletClose: function(event) {
				var instance = this;

				var popup = jQuery('#portal_add_content');
				var item = popup.find('.lfr-portlet-item[plid=' + event.plid + '][portletId=' + event.portletId + '][instanceable=false]');

				if (item.is('.lfr-portlet-used')) {
					item.removeClass('lfr-portlet-used');

					var node = A.get(item[0]);
					var draggable = DDM.getDrag(node);

					if (draggable) {
						draggable.set('lock', false);
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

					placeholder = jQuery(placeholder.getDOM());
					portlet = jQuery(portlet.getDOM());

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

					portlet = jQuery(portlet.getDOM());
					proxy = jQuery(proxy.get('parentNode').getDOM());

					var options = {
						beforePortletLoaded: function(placeHolder) {
							placeHolder = jQuery(placeHolder);

							placeHolder.css(position);
							placeHolder.css(dimensions);
						},
						item: proxy,
						onComplete: function(portlet, portletId) {
							portlet = A.get(portlet);

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