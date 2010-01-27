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

						instance._lockDrag(portlet);
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
						var firstColumn = A.one('.lfr-portlet-column');

						if (firstColumn) {
							firstColumn.prepend(placeHolder);
						}
					}

					var portletOptions = {
						beforePortletLoaded: beforePortletLoaded,
						onComplete: function(portletBoundary) {
							var portletHandler = Liferay.Layout.layoutHandler.get('portletHandler');

							if (portletHandler) {
								portletHandler.registerPortlet(portletBoundary);
							}

							if (onComplete) {
								onComplete.apply(this, arguments);
							}
						},
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
				var usedPortlets = portlets.filter('.lfr-portlet-used');

				instance._dialogBody.delegate(
					'click',
					function(event) {
						var link = event.currentTarget;
						var portlet = link.ancestor('.lfr-portlet-item');

						instance._addPortlet(portlet);
					},
					'a'
				);

				var portletItem = null;
				var layoutHandler = Liferay.Layout.layoutHandler;

				var portletItemOptions = {
					dd: {
						handles: null
					},
					nodes: portlets
				};

				if (Liferay.Layout.isFreeForm) {
					portletItem = new FreeFormPortletItem(portletItemOptions);
				}
				else {
					portletItem = new ColumnPortletItem(portletItemOptions);
				}

				usedPortlets.each(
					function(node, index) {
						instance._lockDrag(node);
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

			_lockDrag: function(node) {
				var instance = this;

				var draggable = DDM.getDrag(node);

				if (draggable) {
					draggable.set('lock', true);
				}
			},

			_onPortletClose: function(event) {
				var instance = this;

				var popup = A.one('#portal_add_content');

				if (popup) {
					var item = popup.one('.lfr-portlet-item[plid=' + event.plid + '][portletId=' + event.portletId + '][instanceable=false]');

					if (item && item.hasClass('lfr-portlet-used')) {
						item.removeClass('lfr-portlet-used');

						instance._unlockDrag(item);
					}
				}
			},

			_onPortletDrop: function(event) {
				var instance = this;

				var drag = event.drag;

				if ((drag instanceof columnPortletItem)) {
					event.preventDefault();
				}
			},

			_unlockDrag: function(node) {
				var instance = this;

				var draggable = DDM.getDrag(node);

				if (draggable) {
					draggable.set('lock', false);
				}
			}
		};

		var ColumnPortletItem = function() {
			ColumnPortletItem.superclass.constructor.apply(this, arguments);
		};

		ColumnPortletItem.NAME = 'ColumnPortletItem';

		A.extend(
			ColumnPortletItem,
			Liferay.Layout.ColumnPortlet,
			{
				getCurrentPortletTitle: function() {
					var instance = this;

					return instance.get('currentPortletNode').attr('title');
				},

				_addPortlet: function(portletNode, options) {
					var instance = this;

					options = A.merge(
						{
							item: instance.get('placeholder')
						},
						options
					);

					LayoutConfiguration._addPortlet(portletNode, options);
				},

				_onDragEnd: function(event) {
					var instance = this;

					var drag = event.target;
					var portletNode = drag.get('node');
					var placeholder = instance.get('placeholder');

					instance.set('dragging', false);

					if (placeholder) {
						placeholder.hide();
					}

					var isColumn = placeholder.get('parentNode').hasClass('lfr-portlet-column');

					if (isColumn) {
						instance._addPortlet(portletNode);
					}

					Liferay.Layout.layoutHandler._onPortletDragEnd(event);
				},

				_onDragStart: function(event) {
					var instance = this;

					var drag = event.target;
					var portletNode = drag.get('node');

					ColumnPortletItem.superclass._onDragStart.apply(this, arguments);

					portletNode.show();
				},

				_syncHelperSizeUI: function(drag) {
					var instance = this;

					var helper = instance.get('helper');

					instance._updateDefHeight(helper);
					instance._updateDefWidth(helper);
				},

				_updateDefHeight: function(elem) {
					var instance = this;

					elem.set('offsetHeight', 100);
				},

				_updateDefWidth: function(elem) {
					var instance = this;

					elem.set('offsetWidth', 350);
				},

				_updatePlaceholder: function(event, cancelAppend) {
					var instance = this;

					var placeholder = instance.get('placeholder');

					instance._updateDefHeight(placeholder);

					ColumnPortletItem.superclass._updatePlaceholder.apply(this, arguments);
				}
			}
		);

		var FreeFormPortletItem = function() {
			FreeFormPortletItem.superclass.constructor.apply(this, arguments);
		};

		FreeFormPortletItem.NAME = 'FreeFormPortletItem';

		A.extend(
			FreeFormPortletItem,
			ColumnPortletItem,
			{
				initializer: function() {
					var instance = this;

					instance.after('drag:start', instance._afterDragStart);
				},

				_addPortlet: function(portletNode, options) {
					var instance = this;

					options = {
						onComplete: A.bind(instance._onAddPortlet, instance)
					};

					FreeFormPortletItem.superclass._addPortlet.apply(this, [portletNode, options]);
				},

				_afterDragStart: function(event) {
					var instance = this;

					var placeholder = instance.get('placeholder');

					var column = Liferay.Layout.layoutHandler.get('dropZones').item(0);

					if (column) {
						column.append(placeholder);
					}

					Liferay.Layout.placeholder.hide();
				},

				_onAddPortlet: function(portletNode, portletId) {
					var instance = this;

					var layoutHandler = Liferay.Layout.layoutHandler;
					var currentDrag = instance.get('currentDrag');
					var helper = currentDrag.get('dragNode');

					if (helper) {
						helper.setStyle('display', 'block');

						layoutHandler.alignPortlet(portletNode, helper);

						helper.setStyle('display', 'none');
					}

					layoutHandler._setupNodeResize(portletNode);
					layoutHandler._setupNodeStack(portletNode);
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