AUI().add(
	'liferay-navigation',
	function(A) {
		var Lang = A.Lang;

		var TPL_DELETE_BUTTON = '<span class="delete-tab aui-helper-hidden">X</span>';

		var TPL_LIST_ITEM = '<li></li>';

		var TPL_TAB_LINK = '<a href="{url}"><span>{pageTitle}</span></a>';

		/**
		 * OPTIONS
		 *
		 * Required
		 * hasPermission {boolean}: Whether the current user has permission to modify the navigation
		 * layoutIds {array}: The displayable layout ids.
		 * navBlock {string|object}: A selector or DOM element of the navigation.
		 */

		var Navigation = function(config) {
			Navigation.superclass.constructor.apply(this, arguments);
		};

		Navigation.NAME = 'navigation';

		Navigation.ATTRS = {
			hasPermission: {
				value: false
			},

			isModifiable: {
				value: false,
				getter: function(value) {
					var instance = this;

					return instance.get('hasPermission') &&
							instance.get('navBlock').hasClass('modify-pages');
				}
			},

			isSortable: {
				value: false,
				getter: function(value) {
					var instance = this;

					return instance.get('hasPermission') &&
							instance.get('navBlock').hasClass('sort-pages');
				}
			},

			layoutIds: {
				value: []
			},

			navBlock: {
				lazyAdd: false,
				value: null,
				setter: function(value) {
					var instance = this;

					value = A.get(value);

					if (!value) {
						value = A.Attribute.INVALID_VALUE;
					}

					return value;
				}
			}
		};

		A.extend(
			Navigation,
			A.Base,
			{
				initializer: function(config) {
					var instance = this;

					var navBlock = instance.get('navBlock');

					if (navBlock) {
						instance._updateURL = themeDisplay.getPathMain() + '/layout_management/update_page';

						var items = navBlock.queryAll('> ul > li');
						var layoutIds = instance.get('layoutIds');

						items.each(
							function(item, index, collection) {
								item._LFR_layoutId = layoutIds[index];
							}
						);

						instance._makeAddable();
						instance._makeDeletable();
						instance._makeSortable();
						instance._makeEditable();

						instance.on('savePage', instance._savePage);
						instance.on('cancelPage', instance._cancelPage);

						navBlock.delegate('keypress', A.bind(instance._onKeypress, instance), 'input');
					}
				},

				_addPage: function(event) {
					var instance = this;

					if (!event.shiftKey) {
						Liferay.Dockbar.MenuManager.hideAll();
						Liferay.Dockbar.UnderlayManager.hideAll();
					}

					var navBlock = instance.get('navBlock');
					var addBlock = A.Node.create(TPL_LIST_ITEM);

					navBlock.one('ul').append(addBlock);

					instance._createEditor(
						addBlock,
						{
							prevVal: ''
						}
					);
				},

				_createEditor: function(listItem, options) {
					var instance = this;

					var prototypeTemplate = instance._prototypeMenuTemplate || '';

					prototypeTemplate = prototypeTemplate.replace(/name=\"template\"/g, 'name="' + A.guid() + '_template"');

					var prevVal = options.prevVal;

					if (options.actionNode) {
						options.actionNode.hide();
					}

					var docClick = A.getDoc().on(
						'click',
						function(event) {
							docClick.detach();

							instance.fire('cancelPage', options);
						}
					);

					var relayEvent = function(event) {
						docClick.detach();

						var eventName = event.type.split(':');

						eventName = eventName[1] || eventName[0];

						instance.fire(eventName, options);
					};

					var tools = [
						{
							icon: 'check',
							id: 'save',
							handler: function(event) {
								comboBox.fire('savePage', options);
							}
						}
					];

					if (prototypeTemplate && !prevVal) {
						tools.unshift(
							{
								activeState: true,
								icon: 'gear',
								id: 'options',
								handler: function(event) {
									var toolItem = this;

									event.halt();

									var action = 'show';

									if (toolItem.StateInteraction.get('active')) {
										action = 'hide';
									}

									comboBox._optionsOverlay[action]();
								}
							}
						);
					}

					var optionsOverlay = new A.Overlay(
						{
							bodyContent: prototypeTemplate,
							align: {
								node: listItem,
								points: ['tl', 'bl']
							},
							on: {
								align: function(event) {
									var instance = this;

									instance._syncUIPosExtras();
								},
								destroy: function(event) {
									var instance = this;

									if (instance.get('rendered')) {
										instance.get('boundingBox').remove();
									}

									instance.detachAll();
								},
								visibleChange: function(event) {
									var instance = this;

									if (event.newVal) {
										if (!instance.get('rendered')) {
											instance.set('align.node', comboBox.get('contentBox'));

											instance.render();
										}
									}
								}
							},
							zIndex: 200
						}
					);

					var comboBox = new A.Combobox(
						{
							after: {
								destroy: function(event) {
									instance.fire('stopEditing');
								},
								render: function(event) {
									instance.fire('startEditing');
								}
							},
							field: {
								value: prevVal
							},
							on: {
								destroy: function(event) {
									var instance = this;

									instance.get('boundingBox').remove();

									optionsOverlay.destroy();
								}
							},
							tools: tools
						}
					).render(listItem);

					if (prototypeTemplate && instance._optionsOpen && !prevVal) {
						var optionItem = comboBox.toolset.tools.item('options');

						optionItem.StateInteraction.set('active', true);
						optionsOverlay.show();
					}

					var comboField = comboBox._field;

					var comboContentBox = comboBox.get('contentBox');

					var overlayBoundingBox = optionsOverlay.get('boundingBox');
					var overlayContentBox = optionsOverlay.get('contentBox');

					options.listItem = listItem;
					options.comboBox = comboBox;
					options.field = comboField;
					options.optionsOverlay = optionsOverlay;

					comboBox.on('savePage', relayEvent);
					comboBox.on('cancelPage', relayEvent);

					comboBox._optionsOverlay = optionsOverlay;

					listItem._comboBox = comboBox;

					overlayBoundingBox.setStyle('minWidth', listItem.get('offsetWidth') + 'px');
					overlayContentBox.addClass('lfr-menu-list lfr-component lfr-page-templates');

					comboContentBox.swallowEvent('click');
					overlayContentBox.swallowEvent('click');

					comboField.get('node').focus();

					var realign = A.bind(optionsOverlay.fire, optionsOverlay, 'align');

					optionsOverlay.on('visibleChange', realign);

					instance.on('stopEditing', realign);
					instance.on('startEditing', realign);

					if (prevVal) {
						instance.fire('editPage');
					}
				},

				_cancelPage: function(event) {
					var instance = this;

					var listItem = event.listItem;
					var comboBox = event.comboBox;
					var field = event.field;

					var actionNode = event.actionNode;

					if (actionNode) {
						actionNode.show();

						field.resetValue();
					}
					else {
						listItem.remove();
					}

					comboBox.destroy();
				},

				_deleteButton: function(obj) {
					var instance = this;

					obj.append(TPL_DELETE_BUTTON);
				},

				_makeAddable: function() {
					var instance = this;

					if (instance.get('isModifiable')) {
						var navList = instance.get('navBlock').one('ul');

						var themeImages = themeDisplay.getPathThemeImages();

						var prototypeMenuNode = A.get('#layoutPrototypeTemplate');

						if (prototypeMenuNode) {
							instance._prototypeMenuTemplate = prototypeMenuNode.html();
						}

						if (instance.get('hasPermission')) {
							var addPageButton = A.get('#addPage');

							if (addPageButton) {
								addPageButton.on('click', instance._addPage, instance);
							}
						}
					}
				},

				_makeDeletable: function() {
					var instance = this;

					if (instance.get('isModifiable')) {
						var navBlock = instance.get('navBlock');

						var navItems = navBlock.queryAll('> ul > li').filter(':not(.selected)');

						navBlock.delegate(
							'click',
							A.bind(instance._removePage, instance),
							'.delete-tab'
						);

						navBlock.delegate(
							'mouseenter',
							A.rbind(instance._toggleDeleteButton, instance, 'removeClass'),
							'li'
						);

						navBlock.delegate(
							'mouseleave',
							A.rbind(instance._toggleDeleteButton, instance, 'addClass'),
							'li'
						);

						instance._deleteButton(navItems);
					}
				},

				_makeEditable: function() {
					var instance = this;

					if (instance.get('isModifiable')) {
						var currentItem = instance.get('navBlock').one('li.selected');

						if (currentItem) {
							var currentLink = currentItem.one('a');

							if (currentLink) {
								var currentSpan = currentLink.one('span');

								if (currentSpan) {
									currentLink.on(
										'click',
										function(event) {
											if (event.shiftKey) {
												event.halt();
											}
										}
									);

									currentLink.on(
										'mouseenter',
										function(event) {
											if (!themeDisplay.isStateMaximized() || event.shiftKey) {
												currentSpan.setStyle('cursor', 'text');
											}
										}
									);

									currentLink.on('mouseleave', A.bind(currentSpan.setStyle, currentSpan, 'cursor', 'pointer'));

									currentSpan.on(
										'click',
										function(event) {
											if (themeDisplay.isStateMaximized() && !event.shiftKey) {
												return;
											}

											event.halt();

											var textNode = event.currentTarget;

											var actionNode = textNode.get('parentNode');
											var currentText = textNode.text();

											instance._createEditor(
												currentItem,
												{
													actionNode: actionNode,
													prevVal: currentText,
													textNode: textNode
												}
											);
										}
									);
								}
							}
						}
					}
				},

				_makeSortable: function() {
					var instance = this;

					var navBlock = instance.get('navBlock');
					var navList = navBlock.one('ul');

					if (instance.get('isSortable')) {
						var items = navList.queryAll('li');
						var anchors = navList.queryAll('a');

						anchors.setStyle('cursor', 'move');

						anchors.each(
							function(item, index, collection) {
								var span = item.one('span');

								if (span) {
									span.setStyle('cursor', 'pointer');
								}
							}
						);

						instance._sortable = new A.Sortable(
							{
								nodes: items,
								on: {
									'drag:end': function(event) {
										var dragNode = event.target.get('node');

										instance._saveSortables(dragNode);

										Liferay.fire(
											'navigation',
											{
												item: dragNode.getDOM(),
												type: 'sort'
											}
										);
									}
								}
							}
						);
					}
				},

				_onKeypress: function(event) {
					var instance = this;

					var keyCode = event.keyCode;

					if (keyCode == 13 || keyCode == 27) {
						var listItem = event.currentTarget.ancestor('li');
						var eventType = 'savePage';

						if (keyCode == 27) {
							eventType = 'cancelPage';
						}

						var comboBox = listItem._comboBox;

						comboBox.fire(eventType);
					}
				},

				_removePage: function(event) {
					var instance = this;

					var tab = event.currentTarget.ancestor('li');

					if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this-page'))) {
						var data = {
							doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
							cmd: 'delete',
							groupId: themeDisplay.getScopeGroupId(),
							privateLayout: themeDisplay.isPrivateLayout(),
							layoutId: tab._LFR_layoutId
						};

						jQuery.ajax(
							{
								data: data,
								success: function() {
									Liferay.fire(
										'navigation',
										{
											item: tab.getDOM(),
											type: 'delete'
										}
									);

									tab.remove();
								},
								url: instance._updateURL
							}
						);
					}
				},

				_savePage: function(event, obj, oldName) {
					var instance = this;

					var listItem = event.listItem;
					var comboBox = event.comboBox;
					var field = event.field;
					var actionNode = event.actionNode;
					var textNode = event.textNode;

					var pageTitle = field.get('value');

					pageTitle = Lang.trim(pageTitle);

					var data = null;
					var onSuccess = null;

					if (pageTitle) {
						if (actionNode) {
							if (field.isDirty()) {
								data = {
									doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
									cmd: 'name',
									groupId: themeDisplay.getScopeGroupId(),
									privateLayout: themeDisplay.isPrivateLayout(),
									layoutId: themeDisplay.getLayoutId(),
									name: pageTitle,
									languageId: themeDisplay.getLanguageId()
								};

								onSuccess = function(id, obj) {
									var data = A.JSON.parse(obj.responseText);

									var doc = A.getDoc();

									textNode.text(pageTitle);
									actionNode.show();

									comboBox.destroy();

									var oldTitle = doc.get('title');

									var regex = new RegExp(field.get('prevVal'), 'g');

									newTitle = oldTitle.replace(regex, pageTitle);

									doc.set('title', newTitle);
								};
							}
							else {
								// The new name is the same as the old one

								comboBox.fire('cancelPage');
							}
						}
						else {
							var optionsOverlay = comboBox._optionsOverlay;
							var selectedInput = optionsOverlay.get('contentBox').one('input:checked');
							var layoutPrototypeId = selectedInput && selectedInput.val();

							data = {
								mainPath: themeDisplay.getPathMain(),
								doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
								cmd: 'add',
								groupId: themeDisplay.getScopeGroupId(),
								privateLayout: themeDisplay.isPrivateLayout(),
								parentLayoutId: themeDisplay.getParentLayoutId(),
								name: pageTitle,
								layoutPrototypeId: layoutPrototypeId
							};

							onSuccess = function(id, obj) {
								var data = A.JSON.parse(obj.responseText);

								var tabHtml = A.substitute(
									TPL_TAB_LINK,
									{
										url: data.url,
										pageTitle: pageTitle
									}
								);

								var newTab = A.Node.create(tabHtml);

								newTab.setStyle('cursor', 'move');

								listItem._LFR_layoutId = data.layoutId;

								listItem.append(newTab);

								comboBox.destroy();

								listItem.addClass('sortable-item');
								instance._sortable.add(listItem);

								instance._deleteButton(listItem);

								Liferay.fire(
									'navigation',
									{
										item: listItem,
										type: 'add'
									}
								);
							}
						}

						if (data) {
							A.io(
								instance._updateURL,
								{
									data: A.toQueryString(data),
									method: 'POST',
									on: {
										success: onSuccess
									}
								}
							);
						}
					}
				},

				_saveSortables: function(node) {
					var instance = this;

					var priority = instance.get('navBlock').queryAll('li').indexOf(node);

					var data = {
						doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
						cmd: 'priority',
						groupId: themeDisplay.getScopeGroupId(),
						privateLayout: themeDisplay.isPrivateLayout(),
						layoutId: node._LFR_layoutId,
						priority: priority
					};

					A.io(
						instance._updateURL,
						{
							method: 'POST',
							data: A.toQueryString(data)
						}
					);
				},

				_toggleDeleteButton: function(event, action) {
					var instance = this;

					var deleteTab = event.currentTarget.one('.delete-tab');

					if (deleteTab) {
						deleteTab[action]('aui-helper-hidden');
					}
				},

				_optionsOpen: true,
				_updateURL: ''
			}
		);

		Liferay.Navigation = Navigation;
	},
	'',
	{
		requires: ['overlay', 'selector-css3', 'sortable', 'node-event-simulate', 'combobox', 'io', 'substitute', 'json-parse']
	}
);