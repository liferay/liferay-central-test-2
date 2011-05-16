AUI().add(
	'liferay-menu',
	function(A) {
		var ATTR_CLASS_NAME = 'className';

		var ALIGN_AUTO = 'auto';

		var ALIGN_LEFT = 'left';

		var ALIGN_RIGHT = 'right';

		var ARIA_ATTR_ROLE = 'role';

		var CSS_STATE_ACTIVE = 'yui3-aui-state-active';

		var CSS_HELPER_HIDDEN = 'yui3-aui-helper-hidden-accessible';

		var CSS_EXTENDED = 'lfr-extended';

		var DIRECTION_AUTO = 'auto';

		var DIRECTION_DOWN = 'down';

		var DIRECTION_LEFT = 'left';

		var DIRECTION_RIGHT = 'right';

		var EVENT_CLICK = 'click';

		var ID = 'id';

		var LIST_ITEM = 'li';

		var OFFSET_HEIGHT = 'offsetHeight';

		var SELECTOR_SEARCH_CONTAINER = '.lfr-menu-list-search-container';

		var SELECTOR_SEARCH_INPUT = '.lfr-menu-list-search';

		var REGEX_MAX_VISIBLE_ITEMS = /max-display-items-(\d+)/;

		var TPL_MENU = '<div id="{menuId}" class="lfr-component lfr-menu-list" />';

		var TPL_SEARCH_BOX =
			'<div class="lfr-menu-list-search-container">' +
				'<input autocomplete="off" aria-autocomplete="list" aria-expanded="true" aria-labelledby="{searchLabeledBy}" aria-owns="{searchOwns}" class="lfr-menu-list-search" id="{searchId}" role="combobox">' +
			'</div>';

		var Menu = function() {
			var instance = this;

			if (!arguments.callee._hasRun) {
				arguments.callee._hasRun = true;

				instance._body = A.getBody();
				instance._document = A.getDoc();
				instance._window = A.getWin();

				instance._active = {
					menu: null,
					trigger: null
				};

				var Layout = Liferay.Layout;

				if (Layout) {
					Layout.on('drag:start', instance._closeActiveMenu, instance);
				}

				instance._window.on(
					'resize',
					function(event){
						var instance = this;

						var menu = instance._active.menu;

						if (menu) {
							if (!menu.hasClass(CSS_HELPER_HIDDEN)) {
								instance._positionActiveMenu();
							}
						}
					},
					instance
				);

				instance._body.delegate(
					EVENT_CLICK,
					function(event) {
						var trigger = event.currentTarget;
						var menu = trigger._AUI_MENU;

						if (!menu) {
							menu = instance._createMenu(trigger);
						}

						if (instance._active.menu && !instance._active.menu.compareTo(menu)) {
							instance._closeActiveMenu();
						}

						if (!menu.hasClass(CSS_HELPER_HIDDEN)) {
							instance._closeActiveMenu();
						}
						else {
							instance._active.menu = menu;
							instance._active.trigger = trigger;

							if (!menu.focusManager) {
								instance._plugMenu(menu, trigger);
							}

							instance._positionActiveMenu();
						}

						event.halt();

						var anchor = trigger.one('a');

						if (anchor) {
							anchor.setAttrs(
								{
									'aria-haspopup': true,
									role: 'button'
								}
							);
						}
					},
					'.lfr-actions'
				);

				instance._document.on(EVENT_CLICK, instance._onDocumentClick, instance);
			}
		};

		Menu.prototype = {
			_createLiveSearch: function(trigger, menu) {
				var instance = this;

				var idSearch = A.guid();

				var listNode = menu.one('ul');

				var searchLabelNode = trigger.one('a') || trigger;

				var searchBoxContent = A.substitute(
					TPL_SEARCH_BOX,
					{
						searchId: idSearch,
						searchLabeledBy: searchLabelNode.get(ID),
						searchOwns: listNode.get(ID)
					}
				);

				var inputSearch = A.Node.create(searchBoxContent);

				menu.prepend(inputSearch);

				var options = {
					data: function(node) {
						return A.Lang.trim(node.one('a').text());
					},
					input: '#' + idSearch,
					nodes: '#' + menu.get(ID) + ' > ul > li'
				};

				if (instance._liveSearch) {
					instance._liveSearch.destroy();
				}

				instance._liveSearch = new A.LiveSearch(options);

				instance._liveSearch.after(
					'search',
					function(event) {
						menu.focusManager.refresh();
					},
					instance
				);

				return instance._liveSearch;
			},

			_createMenu: function(trigger) {
				var instance = this;

				var list = trigger.one('ul');

				var listItems = list.all(LIST_ITEM);

				listItems.last().addClass('last');

				var menuContent = A.substitute(
					TPL_MENU,
					{
						menuId: A.guid()
					}
				);

				var menu = A.Node.create(menuContent);

				menu._hideClass = CSS_HELPER_HIDDEN;

				menu.appendChild(list);

				instance._body.prepend(menu);

				instance._setMenuHeight(trigger, menu, listItems);

				instance._setARIARoles(trigger, menu);

				menu.hide();

				Liferay.Util.createFlyouts(
					{
						container: menu.getDOM()
					}
				);

				trigger._AUI_MENU = menu;

				return menu;
			},

			_closeActiveMenu: function() {
				var instance = this;

				if (instance._active.menu) {
					instance._active.menu.hide();
					instance._active.menu = null;

					var cssClass = instance._active.trigger.attr(ATTR_CLASS_NAME);

					var extended = (cssClass.indexOf(CSS_EXTENDED) > -1);

					if (extended) {
						instance._active.trigger.removeClass(CSS_STATE_ACTIVE);
					}

					instance._active.trigger = null;
				}
			},

			_onDocumentClick: function(event) {
				var instance = this;

				var menu = instance._active.menu;

				if (menu) {
					var target = event.target;

					var insideMenu = target.ancestor(
						function(parent){
							return parent === menu;
						}
					);

					if (!insideMenu ) {
						instance._closeActiveMenu();
					}
				}
			},

			_plugMenu: function(menu, trigger) {
				var instance = this;

				menu.plug(
					A.Plugin.NodeFocusManager,
					{
						circular: true,
						descendants: 'li:not(.yui3-aui-helper-hidden) a,input',
						focusClass: 'yui3-aui-focus',
						keys: {
							next: 'down:40',
							previous: 'down:38'
						}
					 }
				);

				var anchor = trigger.one('a');

				menu.on(
					'key',
					function(event) {
						instance._closeActiveMenu();

						if (anchor) {
							anchor.focus();
						}
					},
					'down:27,9'
				);

				var focusManager = menu.focusManager;

				menu.delegate(
					'mouseenter',
					function (event) {
						if (focusManager.get('focused')) {
							focusManager.focus(event.currentTarget.one('a'));
						}
					},
					LIST_ITEM
				);

				focusManager.after(
					'activeDescendantChange',
					function(event) {
						var descendants = focusManager.get('descendants');

						var selectedItemIndex = event.newVal;

						var selectedItem = descendants.item(selectedItemIndex);

						menu.one('ul').setAttribute('aria-activedescendant', selectedItem.get(ID));
					}
				);
			},

			_positionActiveMenu: function() {
				var instance = this;

				var menu = instance._active.menu;
				var trigger = instance._active.trigger;

				if (menu) {
					var triggerRegion = trigger.get('region');

					var cssClass = trigger.attr(ATTR_CLASS_NAME);

					var align = ALIGN_AUTO;
					var direction = DIRECTION_AUTO;
					var win = instance._window;

					if (cssClass.indexOf('align-right') > -1) {
						align = ALIGN_RIGHT;
					}
					else if (cssClass.indexOf('align-left') > -1) {
						align = ALIGN_LEFT;
					}

					if (cssClass.indexOf('direction-right') > -1) {
						direction = DIRECTION_RIGHT;
					}
					else if (cssClass.indexOf('direction-left') > -1) {
						direction = DIRECTION_LEFT;
					}
					else if (cssClass.indexOf('direction-down') > -1) {
						direction = DIRECTION_DOWN;
					}

					var menuHeight = menu.get(OFFSET_HEIGHT);
					var menuWidth = menu.get('offsetWidth');

					var triggerHeight = triggerRegion.height;
					var triggerWidth = triggerRegion.width;

					var menuTop = menuHeight + triggerRegion.top;
					var menuLeft = menuWidth + triggerRegion.left;

					var scrollTop = win.get('scrollTop');
					var scrollLeft = win.get('scrollLeft');

					var windowRegion = trigger.get('viewportRegion');

					var windowHeight = windowRegion.height + scrollTop;
					var windowWidth = windowRegion.width + scrollLeft;

					if (align == ALIGN_AUTO) {
						if (direction == DIRECTION_DOWN) {
							triggerRegion.top += triggerHeight;
						}

						if (menuTop > windowHeight
							&& !((triggerRegion.top - menuHeight) < 0)) {

							triggerRegion.top -= menuHeight;
						}

						if ((menuLeft > windowWidth || ((menuWidth/2) + triggerRegion.left) > windowWidth/2)
							&& !((triggerRegion.left - menuWidth) < 0)) {

							triggerRegion.left -= (menuWidth - triggerWidth);
						}
						else {
							if (direction == DIRECTION_LEFT) {
								triggerRegion.left -= menuWidth;
							}
							else {
								triggerRegion.left += triggerWidth;
							}
						}
					}
					else {
						if (direction == DIRECTION_LEFT) {
							triggerRegion.left -= (menuWidth - 2);
						}
						else if (direction == DIRECTION_RIGHT) {
							triggerRegion.left += (triggerWidth + 2);
						}

						if (direction == DIRECTION_DOWN) {
							triggerRegion.top += triggerHeight;
						}
						else {
							triggerRegion.top -= (menuHeight - triggerHeight);
						}
					}

					menu.setStyle('position', 'absolute');

					menu.setXY([triggerRegion.left, triggerRegion.top]);

					menu.show();

					if (A.UA.ie === 6 || A.UA.ie === 7) {
						var searchContainer = menu.one(SELECTOR_SEARCH_CONTAINER);

						if (searchContainer) {
							searchContainer.setStyle('width', menu.get('clientWidth') + 'px');
							menu.one(SELECTOR_SEARCH_INPUT).setStyle('width', '100%');
						}
					}

					var extended = (cssClass.indexOf(CSS_EXTENDED) > -1);

					if (extended) {
						trigger.addClass(CSS_STATE_ACTIVE);
					}

					instance._active = {
						menu: menu,
						trigger: trigger
					};

					menu.focusManager.focus(0);
				}
			},

			_setARIARoles: function(trigger, menu) {
				var links = menu.all('a');

				var searchContainer = menu.one(SELECTOR_SEARCH_CONTAINER);

				var listNode = menu.one('ul');

				if (!searchContainer) {
					listNode.setAttribute(ARIA_ATTR_ROLE, 'menu');
					links.set(ARIA_ATTR_ROLE, 'menuitem');
				}
				else {
					listNode.setAttribute(ARIA_ATTR_ROLE, 'listbox');
					links.set(ARIA_ATTR_ROLE, 'option');
				}

				var anchor = trigger.one('a');

				if (anchor) {
					listNode.setAttribute('aria-labelledby', anchor.get(ID));
				}
			},

			_setMenuHeight: function(trigger, menu, listItems) {
				var instance = this;

				var cssClass = trigger.attr(ATTR_CLASS_NAME);

				var expanded = (cssClass.indexOf('lfr-menu-expanded') > -1);

				if (!expanded) {
					var params = REGEX_MAX_VISIBLE_ITEMS.exec(cssClass);

					if (params && params.length === 2) {
						var maxDisplayItems = parseInt(params[1], 10);

						if (maxDisplayItems && listItems.size() > maxDisplayItems) {
							instance._createLiveSearch(trigger, menu);

							var totalHeight = instance._liveSearch.get('input').get(OFFSET_HEIGHT);

							A.some(
								listItems,
								function(item, index) {
									totalHeight += item.get(OFFSET_HEIGHT);

									return index === (maxDisplayItems - 1);
								}
							);

							menu.setStyle('height', totalHeight + 'px');
						}
					}
				}
			}
		};

		Menu.handleFocus = function(id) {
			var node = A.one(id);

			if (node) {
				node.delegate('mouseenter', A.rbind(Menu._targetLink, node, 'focus'), LIST_ITEM);
				node.delegate('mouseleave', A.rbind(Menu._targetLink, node, 'blur'), LIST_ITEM);
			}
		};

		Menu._targetLink = function(event, action) {
			var anchor = event.currentTarget.one('a');

			if (anchor) {
				anchor[action]();
			}
		};

		Liferay.Menu = Menu;
	},
	'',
	{
		requires: ['aui-live-search', 'node-focusmanager', 'selector-css3']
	}
);