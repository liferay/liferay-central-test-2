AUI().ready(
	'event-delegate', 'liferay-hudcrumbs', 'node-focusmanager',
	function(A) {
		var ACTIVE_DESCENDANT = 'activeDescendant';

		var ARROW_LEFT = 37;

		var ARROW_RIGHT = 39;

		var CHILD_MENU_VISIBLE = 'child-menu-visible';

		var DIRECTION_LEFT = 0;

		var DIRECTION_RIGHT = 1;

		var ESCAPE = 27;

		var TAB = 9;

		if (Liferay.Browser.isIe() && Liferay.Browser.getMajorVersion() < 7) {
			var navigation = A.one('#navigation > ul');

			if (navigation) {
				navigation.delegate(
					'mouseenter',
					function(event) {
						event.currentTarget.addClass('hover');
					},
					'> li'
				);

				navigation.delegate(
					'mouseleave',
					function(event) {
						event.currentTarget.removeClass('hover');
					},
					'> li'
				);
			}
		}

		var siteBreadcrumbs = A.one('.site-breadcrumbs');

		if (siteBreadcrumbs) {
			siteBreadcrumbs.plug(A.Hudcrumbs);
		}

		var currentMenuEl = null;
		var toolbar = A.one('#navigation');

		toolbar.plug(
			A.Plugin.NodeFocusManager,
			{
				descendants: 'a',
				focusClass: 'active',
				keys: {
					next: 'down:40',
					previous: 'down:38'
				}
			}
		);

		toolbar.focusManager.after('activeDescendantChange', showMenu);
		toolbar.focusManager.after('focusedChange', showMenu);

		A.delegate('keydown', handleKeyDown, '#navigation', 'a');

		function handleExit(event) {
			toolbar.focusManager.set(ACTIVE_DESCENDANT, 0);
			toolbar.focusManager.blur();

			if (currentMenuEl) {
				currentMenuEl.removeClass(CHILD_MENU_VISIBLE);
			}
		}

		function handleKey(event, direction) {
			var target = event.target;

			var parent = target.ancestors('#navigation > ul > li').item(0);

			var item;

			if (direction === DIRECTION_LEFT) {
				item = parent.previous() || parent.next();
			}
			else {
				item = parent.next() || parent.previous();
			}

			toolbar.focusManager.focus(item.one('a'));
		}

		function handleKeyDown(event) {
			var charCode = event.charCode;

			switch (charCode) {
				case ARROW_LEFT:
					handleLeft(event);
					break;
				case ARROW_RIGHT:
					handleRight(event);
					break;
				case TAB:
				case ESCAPE:
					handleExit(event);
					break;
			}
		}

		function handleLeft(event) {
			handleKey(event, DIRECTION_LEFT );
		}

		function handleRight(event) {
			handleKey(event, DIRECTION_RIGHT );
		}

		function showMenu(event) {
			event.halt();

			var activeDescendant = toolbar.focusManager.get(ACTIVE_DESCENDANT);
			var descendants = toolbar.focusManager.get('descendants');

			var currentEl = descendants.item(activeDescendant);

			var menuEl = currentEl.ancestors('ul.child-menu').item(0);

			if (!menuEl) {
				var parent = currentEl.ancestors('#navigation > ul > li').item(0);
				menuEl = parent.one('ul.child-menu');
			}

			if (currentMenuEl && currentMenuEl != menuEl) {
				currentMenuEl.removeClass(CHILD_MENU_VISIBLE);
				currentMenuEl = null;
			}

			if (menuEl) {
				menuEl.addClass(CHILD_MENU_VISIBLE);
				currentMenuEl = menuEl;
			}
		}
	}
);