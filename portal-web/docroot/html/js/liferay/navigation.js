Liferay.Navigation = new Alloy.Class(
	{

		/**
		 * OPTIONS
		 *
		 * Required
		 * hasPermission {boolean}: Whether the current user has permission to modify the navigation
		 * layoutIds {array}: The displayable layout ids.
		 * navBlock {string|object}: A jQuery selector or DOM element of the navigation.
		 */

		initialize: function(options) {
			var instance = this;

			instance.options = options;

			instance._navBlock = jQuery(instance.options.navBlock);

			instance._hasPermission = instance.options.hasPermission;
			instance._isModifiable = instance._navBlock.is('.modify-pages');
			instance._isSortable = instance._navBlock.is('.sort-pages') && instance._hasPermission;
			instance._isUseHandle = instance._navBlock.is('.use-handle');

			instance._updateURL = themeDisplay.getPathMain() + '/layout_management/update_page';

			var items = instance._navBlock.find('> ul > li');

			items.each(
				function(i) {
					this._LFR_layoutId = instance.options.layoutIds[i];
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

			Liferay.bind('tree', instance._treeCallback, instance);
		},

		_addPage: function(event, obj) {
			var instance = this;

			var navItem = instance._navBlock;
			var addBlock = jQuery('<li>' + instance._enterPage + '</li>');

			var blockInput = addBlock.find('input');

			navItem.find('ul:first').append(addBlock);

			var editComponent = addBlock.data('editComponent');

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

				editComponent._trigger = addBlock.find('.aui-options-trigger');

				editComponent._trigger.click(
					function(event) {
						var visible = editComponent.cfg.getProperty('visible');

						editComponent.cfg.setProperty('visible', !visible);

						jQuery(this).toggleClass('aui-trigger-selected');

						instance._optionsOpen = !visible;
					}
				);

				editComponent.render(document.body);

				Alloy.Dom.addClass(editComponent.element, 'lfr-menu-list lfr-component lfr-page-templates');
				Alloy.Dom.setStyle(editComponent.element, 'min-width', addBlock.outerWidth() + 'px');

				addBlock.data('editComponent', editComponent);
			}

			if (instance._optionsOpen) {
				editComponent.show();
			}
			else {
				editComponent.hide();
				editComponent._trigger.removeClass('aui-trigger-selected');
			}

			var currentInput = addBlock.find('.enter-page input');

			var savePage = addBlock.find('.save-page');

			var pageParents = jQuery(document);

			var cancelPage = function() {
				instance._cancelAddingPage(addBlock);

				Alloy.getDocument().unbind('click.liferay', pageBlur);

				editComponent.hide();
			}

			var pageBlur = function(internalEvent) {
				var currentEl = jQuery(internalEvent.target);
				var liParent = currentEl.parents('ul:eq(0)');

				if ((liParent.length == 0) && !currentEl.is('li') && !currentEl.parents('#add-page, #' + editComponent.id).length) {
					cancelPage();
				}
			};

			pageParents.bind('click.liferay', pageBlur);

			savePage.click(
				function(event) {
					instance._savePage(event, this);

					if (currentInput.val().length) {
						Alloy.getDocument().unbind('click.liferay', pageBlur);
					}
				}
			);

			currentInput.keypress(
				function(event) {
					if (event.keyCode == 13) {
						savePage.trigger('click');
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

			instance._navBlock.find('.enter-page').remove();

			if (previousName) {
				previousName.show();
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

			var navItem = null;

			if (oldName) {
				navItem = jQuery(obj).parents('li');

				var enterPage = navItem.find('.enter-page');

				enterPage.prev().show();
				enterPage.remove();
			}
			else {
				navItem = jQuery(this).parents('li');

				navItem.remove();

			}

			instance._stopAddEvent.fire();
		},

		_deleteButton: function(obj) {
			var instance = this;

			obj.append('<span class="delete-tab">X</span>');

			var deleteTab = obj.find('.delete-tab');

			deleteTab.click(
				function(event) {
					instance._removePage(this);
				}
			);

			deleteTab.hide();

			obj.hover(
				function() {
					jQuery(this).find('.delete-tab').fadeIn('fast');
				},
				function() {
					jQuery(this).find('.delete-tab').fadeOut('fast');
				}
			);
		},

		_makeAddable: function() {
			var instance = this;

			if (instance._isModifiable) {
				var navList = instance._navBlock.find('ul:first');

					var themeImages = themeDisplay.getPathThemeImages();

					instance._prototypeMenuTemplate = Alloy.get('#layoutPrototypeTemplate').html();

					instance._enterPage ='<span class="aui-form-field aui-form-text aui-form-options enter-page">' +
						'<span><input class="lfr-auto-focus text" id="" name="" type="text" /></span>' +
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
					var addPageButton = jQuery('#addPage');

					if (!addPageButton.length) {
						navList.after(
							'<div id="add-page">' +
							'<a href="javascript:;">' +
							'<span>' + Liferay.Language.get('add-page') + '</span>' +
							'</a>' +
							'</div>');

						addPageButton = navList.parent().find('#add-page a');
					}

					addPageButton.click(
						function(event) {
							if (!event.shiftKey) {
								Liferay.Dockbar.MenuManager.hideAll();
								Liferay.Dockbar.UnderlayManager.hideAll();
							}

							instance._addPage(event, this);

							return false;
						}
					);
				}
			}
		},

		_makeDeletable: function() {
			var instance = this;

			if (instance._isModifiable && instance._hasPermission) {
				var navItems = instance._navBlock.find('> ul > li').not('.selected');

				instance._deleteButton(navItems);
			}
		},

		_makeEditable: function() {
			var instance = this;

			if (instance._isModifiable) {
				var currentItem = instance._navBlock.find('li.selected');
				var currentLink = currentItem.find('a');
				var currentSpan = currentLink.find('span');

				currentLink.click(
					function(event) {
						if (event.shiftKey) {
							return false;
						}
					}
				);

				var resetCursor = function() {
					currentSpan.css('cursor', 'pointer');
				};

				currentLink.hover(
					function(event) {
						if (!themeDisplay.isStateMaximized() || event.shiftKey) {
							currentSpan.css('cursor', 'text');
						}
					},
					resetCursor
				);

				currentSpan.click(
					function(event) {
						if (themeDisplay.isStateMaximized() && !event.shiftKey) {
							return;
						}

						var span = jQuery(this);
						var text = span.text();

						span.parent().hide();
						span.parent().after(instance._enterPage);

						var enterPage = span.parent().next();

						enterPage.addClass('edit-page');

						var pageParents = enterPage.parents();

						var enterPageInput = enterPage.find('input');

						var pageBlur = function(event) {
							event.stopPropagation();

							if (!jQuery(this).is('li')) {
								cancelPage();
							}

							return false;
						};

						enterPageInput.val(text);

						enterPageInput.trigger('select');

						var savePage = enterPage.find('.save-page');

						savePage.click(
							function(event) {
								instance._savePage(event, this, text);

								if (enterPageInput.val().length) {
									pageParents.unbind('blur.liferay', pageBlur);
									pageParents.unbind('click.liferay', pageBlur);

									instance._stopEditEvent.fire();
								}
							}
						);

						var cancelPage = function() {
							instance._cancelPage(span, text);
							pageParents.unbind('blur.liferay', pageBlur);
							pageParents.unbind('click.liferay', pageBlur);

							instance._stopEditEvent.fire();
						};

						enterPageInput.keypress(
							function(event) {
								if (event.keyCode == 13) {
									savePage.trigger('click');
									pageParents.unbind('blur.liferay', pageBlur);
									pageParents.unbind('click.liferay', pageBlur);
								}
								else if (event.keyCode == 27) {
									cancelPage();
									pageParents.unbind('blur.liferay', pageBlur);
									pageParents.unbind('click.liferay', pageBlur);
								}
							}
						);

						pageParents.bind('click.liferay', pageBlur);

						resetCursor();

						instance._editEvent.fire();

						return false;
					}
				);
			}
		},

		_makeSortable: function() {
			var instance = this;

			var navBlock = instance._navBlock;
			var navList = navBlock.find('ul:first');

			if (instance._isSortable) {
				var items = navList.find('li');
				var anchors = items.find('a');

				if (instance._isUseHandle) {
					items.append('<span class="sort-handle">+</span>');
				}
				else {
					anchors.css('cursor', 'move');
					anchors.find('span').css('cursor', 'pointer');
				}

				items.addClass('sortable-item');

				new Alloy.Sortable(
					{
						container: '#navigation ul',
						items: 'li',
						helperClass: 'navigation-sort-helper',
						stop: function(event, sortableInstance) {
							var el = this.getEl();

							instance._saveSortables(el);

							Liferay.trigger(
								'navigation',
								{
									item: el,
									type: 'sort'
								}
							);
						}
					}
				);
			}
		},

		_removePage: function(obj) {
			var instance = this;

			var tab = jQuery(obj).parents('li:first');
			var tabText = tab.find('a span').html();

			if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this-page'))) {
				var data = {
					doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
					cmd: 'delete',
					groupId: themeDisplay.getScopeGroupId(),
					privateLayout: themeDisplay.isPrivateLayout(),
					layoutId: tab[0]._LFR_layoutId
				};

				jQuery.ajax(
					{
						data: data,
						success: function() {
							Liferay.trigger(
								'navigation',
								{
									item: tab,
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

			var newNavItem = jQuery(obj).parents('li');
			var name = newNavItem.find('input').val();
			var enterPage = newNavItem.find('.enter-page');

			name = jQuery.trim(name);

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
							var currentTab = enterPage.prev();
							var currentSpan = currentTab.find('span');

							currentSpan.text(name);
							currentTab.show();

							enterPage.remove();

							var oldTitle = jQuery(document).attr('title');

							var regex = new RegExp(oldName, 'g');

							newTitle = oldTitle.replace(regex, name);

							jQuery(document).attr('title', newTitle);
						}
					}
					else {

						// The new name is the same as the old one

						var currentTab = enterPage.prev();

						currentTab.show();
						enterPage.remove();

						return false;
					}
				}
				else {

					// Adding a new page

					var editComponent = newNavItem.data('editComponent');

					var layoutPrototypeId = jQuery('input:checked', editComponent.element).val() || null;

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
						var newTab = jQuery('<a href="' + data.url + '"><span>' + Liferay.Util.escapeHTML(name) + '</span></a>');

						if (instance._isUseHandle) {
							enterPage.before('<span class="sort-handle">+</span>');
						}
						else {
							newTab.css('cursor', 'move');
						}

						newNavItem[0]._LFR_layoutId = data.layoutId;

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

		_saveSortables: function(obj) {
			var instance = this;

			var tabs = jQuery('li', instance._navBlock);

			var data = {
				doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
				cmd: 'priority',
				groupId: themeDisplay.getScopeGroupId(),
				privateLayout: themeDisplay.isPrivateLayout(),
				layoutId: obj._LFR_layoutId,
				priority: tabs.index(obj)
			};

			jQuery.ajax(
				{
					data: data,
					url: instance._updateURL
				}
			);
		},

		_treeCallback: function(event, data) {
			var instance = this;

			var navigation = instance._navBlock.find('> ul');
			var droppedItem = jQuery(data.droppedItem);
			var dropTarget = jQuery(data.dropTarget);

			if (instance._isSortable) {
				var liItems = navigation.find('> li');

				var tree = droppedItem.parent();
				var droppedName = droppedItem.find('span:first').text();
				var newParent = dropTarget.parents('li:first');

				var liChild = liItems.find('span').not('.delete-tab');

				liChild = liChild.filter(
					function() {
						var currentItem = jQuery(this);

						if (currentItem.text() == droppedName) {
							return true;
						}
						else {
							return false;
						}
					}
				);

				var treeItems = tree.find('> li');

				var newIndex = treeItems.index(droppedItem);

				if (liChild.length > 0) {
					var newSibling = liItems.eq(newIndex);
					var parentLi = liChild.parents('li:first');

					if (!newParent.is('.tree-item')) {
						newSibling.after(parentLi);

						if (parentLi.is(':hidden')) {
							parentLi.show();
						}
					}
					else {

						//TODO: add parsing to move child elements around by their layoutId

						parentLi.hide();
					}
				}
				else if (!newParent.is('.tree-item')) {
					var newTab = liItems.slice(0, 1).clone();

					newTab.removeClass('selected');
					newTab.find('.child-menu').remove();

					var newTabLink = newTab.find('a span');

					newTabLink.text(droppedName);
					newTabLink.css('cursor', 'pointer');

					liItems.parent().append(newTab);
				}
			}
		},

		_enterPage: '',
		_optionsOpen: true,
		_updateURL: ''
	}
);