AUI().add(
	'liferay-navigation',
	function(A) {

		/**
		 * OPTIONS
		 *
		 * Required
		 * hasPermission {boolean}: Whether the current user has permission to modify the navigation
		 * layoutIds {array}: The displayable layout ids.
		 * navBlock {string|object}: A jQuery selector or DOM element of the navigation.
		 */

		var Navigation = function(options) {
			var instance = this;

			instance.options = options;

			instance._navBlock = A.get(instance.options.navBlock);

			if (instance._navBlock) {
				instance._hasPermission = instance.options.hasPermission;
				instance._isModifiable = instance._navBlock.hasClass('modify-pages');
				instance._isSortable = instance._navBlock.hasClass('sort-pages') && instance._hasPermission;
				instance._isUseHandle = instance._navBlock.hasClass('use-handle');

				instance._updateURL = themeDisplay.getPathMain() + '/layout_management/update_page';

				var items = instance._navBlock.queryAll('> ul > li');

				items.each(
					function(item, index, collection) {
						item._LFR_layoutId = instance.options.layoutIds[index];
					}
				);

				instance._addEvent = new Alloy.CustomEvent('add', instance);
				instance._stopAddEvent = new Alloy.CustomEvent('stopAdd', instance);
				instance._editEvent = new Alloy.CustomEvent('edit', instance);
				instance._stopEditEvent = new Alloy.CustomEvent('stopEdit', instance);

				instance._makeAddable();
				instance._makeDeletable();
				instance._makeSortable();
				instance._makeEditable();
			}
		};

		Navigation.prototype = {
			_addPage: function(event, obj) {
				var instance = this;

				var navItem = instance._navBlock;
				var addBlock = A.Node.create('<li>' + instance._enterPage + '</li>');

				var blockInput = addBlock.one('input');

				navItem.one('ul').append(addBlock);

				blockInput.focus();

				var editComponent = addBlock._editComponent;

				if (!editComponent) {
					var prototypeTemplate = instance._prototypeMenuTemplate || '';

					prototypeTemplate = prototypeTemplate.replace(/name=\"template\"/g, 'name="' + Alloy.generateId() + '_template"');

					var editComponent = new Alloy.Overlay(
						{
							body: prototypeTemplate,
							context: [addBlock[0], 'tl', 'bl', ['beforeShow', instance._addEvent, instance._stopAddEvent, instance._editEvent, instance._stopEditEvent]],
							zIndex: 200
						}
					);

					editComponent._trigger = addBlock.one('.aui-options-trigger');

					if (editComponent._trigger) {
						editComponent._trigger.on(
							'click',
							function(event) {
								var visible = editComponent.cfg.getProperty('visible');

								editComponent.cfg.setProperty('visible', !visible);

								this.toggleClass('aui-trigger-selected');

								instance._optionsOpen = !visible;
							}
						);
					}

					editComponent.render(document.body);

					Alloy.Dom.addClass(editComponent.element, 'lfr-menu-list lfr-component lfr-page-templates');
					Alloy.Dom.setStyle(editComponent.element, 'min-width', addBlock.get('offsetWidth') + 'px');

					addBlock._editComponent = editComponent;
				}

				if (instance._optionsOpen) {
					editComponent.show();
				}
				else {
					editComponent.hide();
					editComponent._trigger.removeClass('aui-trigger-selected');
				}

				var currentInput = addBlock.one('.enter-page input');

				var savePage = addBlock.one('.save-page');

				var pageParents = A.get(document);

				var cancelPage = function() {
					instance._cancelAddingPage(addBlock);

					A.detach('click', pageBlur, pageParents);

					editComponent.hide();
				}

				var pageBlur = function(internalEvent) {
					var currentEl = internalEvent.target;
					var liParent = currentEl.ancestor('ul');

					if (!liParent &&
						currentEl.get('tagName') != 'li' &&
						!currentEl.ancestor('#add-page, #' + editComponent.id)
						) {
						cancelPage();
					}
				};

				pageParents.on('click', pageBlur);

				savePage.on(
					'click',
					function(event) {
						instance._savePage(event, this);

						if (currentInput.val().length) {
							A.detach('click', pageBlur, pageParents);
						}
					}
				);

				currentInput.on(
					'keypress',
					function(event) {
						if (event.keyCode == 13) {
							savePage.simulate('click');
						}
						else if (event.keyCode == 27) {
							cancelPage();
						}
						else {
							return;
						}
					}
				);
			},

			_stopEditing: function(previousName) {
				var instance = this;

				instance._navBlock.one('.enter-page').remove();

				if (previousName) {
					previousName.removeClass('aui-helper-hidden');
				}

				instance._stopEditEvent.fire();
			},

			_cancelAddingPage: function(obj) {
				var instance = this;

				obj.remove();

				instance._stopAddEvent.fire();
			},

			_cancelPage: function(obj, oldName) {
				var instance = this;

				var navItem = obj.ancestor('li');

				if (oldName) {
					var enterPage = navItem.one('.enter-page');

					enterPage.previous().removeClass('aui-helper-hidden');
					enterPage.remove();
				}
				else {
					navItem.remove();
				}

				instance._stopAddEvent.fire();
			},

			_deleteButton: function(obj) {
				var instance = this;

				var deleteTab = A.Node.create('<span class="delete-tab">X</span>');

				obj.append('<span class="delete-tab aui-helper-hidden">X</span>');
			},

			_makeAddable: function() {
				var instance = this;

				if (instance._isModifiable) {
					var navList = instance._navBlock.one('ul');

						var themeImages = themeDisplay.getPathThemeImages();

						var prototypeMenuTemplate = '';
						var prototypeMenuNode = A.get('#layoutPrototypeTemplate');

						if (prototypeMenuNode) {
							prototypeMenuTemplate = prototypeMenuNode.html();
						}

						instance._prototypeMenuTemplate = prototypeMenuTemplate;

						instance._enterPage ='<span class="aui-form-field aui-form-text aui-form-options enter-page">' +
							'<span><input class="text" id="" name="" type="text" /></span>' +
							'<span class="aui-form-triggers">' +
								(instance._prototypeMenuTemplate ?
									'<a class="aui-form-trigger aui-options-trigger ' + (instance._optionsOpen ? 'aui-trigger-selected' : '') + '" href="javascript:;">' +
										'<img src="' + themeImages + '/spacer.png" />' +
										'</a>' : '') +
								'<a class="aui-form-trigger aui-save-trigger save-page" href="javascript:;">' +
									'<img src="' + themeImages + '/spacer.png" />' +
								'</a>' +
							'</span>' +
						'</span>';

					if (instance._hasPermission) {
						var addPageButton = A.get('#addPage');

						if (!addPageButton) {
							var addPageButton = A.Node.create('<div id="add-page">' +
							'<a href="javascript:;">' +
							'<span>' + Liferay.Language.get('add-page') + '</span>' +
							'</a>' +
							'</div>');

							navList.placeAfter(addPageButton);
						}

						addPageButton.on(
							'click',
							function(event) {
								if (!event.shiftKey) {
									Liferay.Dockbar.MenuManager.hideAll();
									Liferay.Dockbar.UnderlayManager.hideAll();
								}

								instance._addPage(event, this);
							}
						);
					}
				}
			},

			_makeDeletable: function() {
				var instance = this;

				if (instance._isModifiable && instance._hasPermission) {
					var navItems = instance._navBlock.queryAll('> ul > li').filter(':not(.selected)');

					instance._navBlock.delegate(
						'click',
						function(event) {
							instance._removePage(event.currentTarget);
						},
						'.delete-tab'
					);

					instance._navBlock.delegate(
						'mouseenter',
						function(event) {
							var deleteTab = event.currentTarget.one('.delete-tab');

							if (deleteTab) {
								deleteTab.removeClass('aui-helper-hidden');
							}
						},
						'li'
					);

					instance._navBlock.delegate(
						'mouseleave',
						function(event) {
							var deleteTab = event.currentTarget.one('.delete-tab');

							if (deleteTab) {
								deleteTab.addClass('aui-helper-hidden');
							}
						},
						'li'
					);

					instance._deleteButton(navItems);
				}
			},

			_makeEditable: function() {
				var instance = this;

				if (instance._isModifiable) {
					var currentItem = instance._navBlock.one('li.selected');
					var currentLink = currentItem.one('a');
					var currentSpan = currentLink.one('span');

					var swallowEvent = function(event) {
						event.stopPropagation();
					};

					currentLink.on(
						'click',
						function(event) {
							if (event.shiftKey) {
								event.halt();
							}
						}
					);

					var resetCursor = function() {
						currentSpan.setStyle('cursor', 'pointer');
					};

					currentLink.on(
						'mouseenter',
						function(event) {
							if (!themeDisplay.isStateMaximized() || event.shiftKey) {
								currentSpan.setStyle('cursor', 'text');
							}
						}
					);

					currentLink.on('mouseleave', resetCursor);

					currentSpan.on(
						'click',
						function(event) {
							if (themeDisplay.isStateMaximized() && !event.shiftKey) {
								return;
							}

							event.halt();

							currentItem.on('click', swallowEvent);

							var span = this;
							var text = span.text();

							var spanParent = span.get('parentNode');

							var enterPage = A.Node.create(instance._enterPage);

							spanParent.addClass('aui-helper-hidden');
							spanParent.placeAfter(enterPage);

							enterPage.addClass('edit-page');

							var pageParents = A.get(document);

							var enterPageInput = enterPage.one('input');

							var pageBlur = function(event) {
								event.stopPropagation();

								if (event.target.get('tagName').toLowerCase() != 'li') {
									cancelPage();
								}
							};

							enterPageInput.val(text);

							enterPageInput.invoke('select');

							var savePage = enterPage.one('.save-page');

							savePage.on(
								'click',
								function(event) {
									instance._savePage(event, this, text);

									if (enterPageInput.val().length) {
										A.detach('blur', pageBlur, pageParents);
										A.detach('click', pageBlur, pageParents);

										A.detach('click', swallowEvent, currentItem);

										instance._stopEditEvent.fire();
									}
								}
							);

							var cancelPage = function() {
								instance._cancelPage(span, text);

								A.detach('blur', pageBlur, pageParents);
								A.detach('click', pageBlur, pageParents);

								A.detach('click', swallowEvent, currentItem);

								instance._stopEditEvent.fire();
							};

							enterPageInput.on(
								'keypress',
								function(event) {
									if (event.keyCode == 13) {
										savePage.simulate('click');
										A.detach('blur', pageBlur, pageParents);
										A.detach('click', pageBlur, pageParents);
									}
									else if (event.keyCode == 27) {
										cancelPage();
										A.detach('blur', pageBlur, pageParents);
										A.detach('click', pageBlur, pageParents);
									}
								}
							);

							pageParents.on('click', pageBlur);

							resetCursor();

							instance._editEvent.fire();
						}
					);
				}
			},

			_makeSortable: function() {
				var instance = this;

				var navBlock = instance._navBlock;
				var navList = navBlock.one('ul');

				if (instance._isSortable) {
					var items = navList.queryAll('li');
					var anchors = navList.queryAll('a');

					if (instance._isUseHandle) {
						var handle = A.Node.create('<span class="sort-handle">+</span>');

						items.append(handle);
					}
					else {
						anchors.setStyle('cursor', 'move');

						anchors.each(
							function(item, index, collection) {
								item.one('span').setStyle('cursor', 'pointer');
							}
						);
					}

					var sortable = new A.Sortable(
						{
							nodes: items,
							on: {
								'drag:end': function(event) {
									var dragNode = event.target.get('node');

									instance._saveSortables(dragNode);

									Liferay.trigger(
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

			_removePage: function(obj) {
				var instance = this;

				var tab = obj.ancestor('li');

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
								Liferay.trigger(
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

				if ((event.type == 'keypress') && (event.keyCode !== 13)) {
					return;
				}

				var data = null;
				var onSuccess = null;

				var newNavItem = obj.ancestor('li');
				var name = newNavItem.one('input').val();
				var enterPage = newNavItem.one('.enter-page');

				name = A.Lang.trim(name);

				if (name) {
					if (oldName) {

						// Updating an existing page

						if (name != oldName) {
							data = {
								doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
								cmd: 'name',
								groupId: themeDisplay.getScopeGroupId(),
								privateLayout: themeDisplay.isPrivateLayout(),
								layoutId: themeDisplay.getLayoutId(),
								name: name,
								languageId: themeDisplay.getLanguageId()
							};

							onSuccess = function(data) {
								var currentTab = enterPage.previous();
								var currentSpan = currentTab.one('span');

								var doc = A.get(document);

								currentSpan.text(name);
								currentTab.removeClass('aui-helper-hidden');

								enterPage.remove();

								var oldTitle = doc.attr('title');

								var regex = new RegExp(oldName, 'g');

								newTitle = oldTitle.replace(regex, name);

								doc.attr('title', newTitle);
							}
						}
						else {

							// The new name is the same as the old one

							var currentTab = enterPage.previous();

							currentTab.removeClass('aui-helper-hidden');
							enterPage.remove();

							event.halt();
						}
					}
					else {

						// Adding a new page

						var editComponent = newNavItem._editComponent;

						var layoutPrototypeId = null;

						if (editComponent.element) {
							var selectedInput = A.get(editComponent.element).one('input:checked');

							if (selectedInput) {
								layoutPrototypeId = selectedInput.val();
							}
						}

						data = {
							mainPath: themeDisplay.getPathMain(),
							doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
							cmd: 'add',
							groupId: themeDisplay.getScopeGroupId(),
							privateLayout: themeDisplay.isPrivateLayout(),
							parentLayoutId: themeDisplay.getParentLayoutId(),
							name: name,
							layoutPrototypeId: layoutPrototypeId
						};

						onSuccess = function(data) {
							var newTab = A.Node.create('<a href="' + data.url + '"><span>' + Liferay.Util.escapeHTML(name) + '</span></a>');

							if (instance._isUseHandle) {
								enterPage.before('<span class="sort-handle">+</span>');
							}
							else {
								newTab.setStyle('cursor', 'move');
							}

							newNavItem._LFR_layoutId = data.layoutId;

							enterPage.before(newTab);
							enterPage.remove();

							newNavItem.addClass('sortable-item');

							instance._deleteButton(newNavItem);

							Liferay.trigger(
								'navigation',
								{
									item: newNavItem,
									type: 'add'
								}
							);

							editComponent.hide();
						}
					}

					jQuery.ajax(
						{
							data: data,
							dataType: 'json',
							success: onSuccess,
							url: instance._updateURL
						}
					);
				}
			},

			_saveSortables: function(node) {
				var instance = this;

				var priority = instance._navBlock.queryAll('li').indexOf(node);

				var data = {
					doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
					cmd: 'priority',
					groupId: themeDisplay.getScopeGroupId(),
					privateLayout: themeDisplay.isPrivateLayout(),
					layoutId: node._LFR_layoutId,
					priority: priority
				};

				jQuery.ajax(
					{
						data: data,
						url: instance._updateURL
					}
				);
			},

			_enterPage: '',
			_optionsOpen: true,
			_updateURL: ''
		};

		Liferay.Navigation = Navigation;
	},
	'',
	{
		requires: ['overlay', 'selector-css3', 'sortable', 'node-event-simulate', 'editable']
	}
);