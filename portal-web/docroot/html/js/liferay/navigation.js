Liferay.Navigation = new Class({

	/*
	params.layoutIds: an array of displayable layout ids
	params.navBlock: the selector for the navigation block
	*/
	initialize: function(params) {
		var instance = this;

		instance.params = params;

		instance._navBlock = jQuery(instance.params.navBlock);

		instance._isModifiable = instance._navBlock.is('.modify-pages');
		instance._isSortable = instance._navBlock.is('.sort-pages');
		instance._isUseHandle = instance._navBlock.is('.use-handle');

		instance._updateURL = themeDisplay.getPathMain() + '/layout_management/update_page';

		var items = instance._navBlock.find('> ul > li');

		items.each(
			function(i) {
				this._LFR_layoutId = instance.params.layoutIds[i];
			}
		);

		instance._makeAddable();
		instance._makeDeletable();
		instance._makeSortable();
		instance._makeEditable();

		Liferay.Publisher.register('navigation');

		Liferay.Publisher.subscribe('tree', instance._treeCallback, instance);
	},

	_addPage: function(event, obj) {
		var instance = this;

		var navItem = instance._navBlock;
		var addBlock = jQuery('<li>' + instance._enterPage + '</li>');

		var blockInput = addBlock.find('input');

		addBlock.find('.cancel-page').click(instance._cancelPage);

		addBlock.find('.save-page').click(
			function(event){
				instance._savePage(event, this, instance);
			}
		);

		addBlock.find('.enter-page input').keyup(
			function(event){
				instance._savePage(event, this, instance);
			}
		);

		navItem.find('ul:first').append(addBlock);

		blockInput[0].focus();
	},

	_cancelPage: function(event, obj, oldName) {
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
	},

	_deleteButton: function(obj) {
		var instance = this;

		obj.append('<span class="delete-tab">X</span>');

		var deleteTab = obj.find('.delete-tab');

		deleteTab.click(
			function(event) {
				instance._removePage(this, instance);

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

			instance._enterPage =
				'<div class="enter-page">' +
				'<input type="text" name="new_page" value="" class="text" />' +
				'<a class="cancel-page" href="javascript: ;"></a>' +
				'<a class="save-page" href="javascript: ;">Save</a>' +
				'</div>';

			navList.after(
				'<div id="add-page">' +
				'<a href="javascript:;">' +
				'<span>Add page</span>' +
				'</a>' +
				'</div>');

			var addPage = navList.parent().find('#add-page a');

			addPage.click(
				function(event){
					instance._addPage(event, this);
				}
			);
		}
	},

	_makeDeletable: function() {
		var instance = this;

		if (instance._isModifiable) {
			var navItems = instance._navBlock.find('li').not('.selected');

			instance._deleteButton(navItems);
		}
	},

	_makeEditable: function() {
		var instance = this;

		if (instance._isModifiable) {
			var currentItem = instance._navBlock.find('li.selected');
			var currentLink = currentItem.find('a');
			var currentSpan = currentLink.find('span');

			currentSpan.css('cursor', 'text');

			currentLink.click(
				function() {
					return false;
				}
			);

			currentSpan.click(
				function() {
					var span = jQuery(this);
					var text = span.text();

					span.parent().hide();
					span.parent().after(instance._enterPage);

					var enterPage = span.parent().next();

					var pageParents = enterPage.parents();

					var enterPageInput = enterPage.find('input');

					var pageBlur = function(event) {
						event.stopPropagation();

						if (!jQuery(this).is('li')) {
							cancelPage.trigger('click');
						}

						return false;
					};

					enterPageInput.val(text);

					enterPageInput.trigger('select');

					var savePage = enterPage.find('.save-page');

					savePage.click(
						function(event) {
							instance._savePage(event, this, instance, text);
							pageParents.unbind('blur', pageBlur);
						}
					);

					var cancelPage = enterPage.find('.cancel-page');

					cancelPage.hide();

					cancelPage.click(
						function(event) {
							instance._cancelPage(event, this, text);
							pageParents.unbind('blur', pageBlur);
						}
					);

					enterPageInput.keyup(
						function(event) {
							if (event.keyCode == 13) {
								savePage.trigger('click');
								pageParents.unbind('blur', pageBlur);
							}
							else if (event.keyCode == 27) {
								cancelPage.trigger('click');
								pageParents.unbind('blur', pageBlur);
							}
						}
					);

					pageParents.click(pageBlur);
				}
			);
		}
	},

	_makeSortable: function() {
		var instance = this;

		var navBlock = instance._navBlock;
		var navList = navBlock.find('ul:first');

		if (instance._isSortable) {
			var floatStyle = navList.find('> li').css('float');

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

			instance.sortable = navList.Sortable(
				{
					accept: 'sortable-item',
					helperclass: 'sort-helper',
					activeclass: 'sortableactive',
					hoverclass: 'sortablehover',
					handle: (instance._isUseHandle ? '.sort-handle' : 'a'),
					opacity: 0.8,
					revert:	true,
					floats:	(floatStyle == 'left' || floatStyle == 'right'),
					tolerance: 'pointer',
					onStop: function() {
						instance._saveSortables(this);

						Liferay.Publisher.deliver('navigation', this);
					}
				}
			);
		}
	},

	_removePage: function(obj, instance) {
		var tab = jQuery(obj).parents('li');
		var tabText = tab.find('a span').html();

		if (confirm('Are you sure you want to remove "' + tabText + '"?')) {
			var data = {
				cmd: 'delete',
				layoutId: tab[0]._LFR_layoutId,
				ownerId: themeDisplay.getOwnerId()
			};

			jQuery.ajax(
				{
					data: data,
					success: function() {
						Liferay.Publisher.deliver('navigation', tab, 'delete');

						tab.remove();
					},
					url: instance._updateURL
				}
			);
		}
	},

	_savePage: function(event, obj, instance, oldName) {
		if ((event.type == 'keyup') && (event.keyCode !== 13)) {
			return;
		}

		var data = null;
		var onSuccess = null;

		var newNavItem = jQuery(obj).parents('li');
		var name = newNavItem.find('input').val();
		var enterPage = newNavItem.find('.enter-page');

		if (oldName) {

			// Updating an existing page

			if (name != oldName) {

				data = {
					cmd: 'name',
					layoutId: themeDisplay.getLayoutId(),
					ownerId: themeDisplay.getOwnerId(),
					name: name,
					languageId: themeDisplay.getLanguageId()
				};

				onSuccess = function(data) {
					data = $J(data);

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

			data = {
				cmd: 'add',
				mainPath: themeDisplay.getPathMain(),
				doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
				groupId: themeDisplay.getGroupId(),
				privateLayout: themeDisplay.isPrivateLayout(),
				parentLayoutId: themeDisplay.getParentLayoutId(),
				name: name
			};

			onSuccess = function(data) {
				data = $J(data);

				var newTab = jQuery('<a href="' + data.url + '"><span>' + name + '</span></a>');

				if (instance._isUseHandle) {
					enterPage.before('<span class="sort-handle">+</span>');
				}
				else {
					newTab.css('cursor', 'move');
				}

				newNavItem[0]._LFR_layoutId = data.layoutId;

				enterPage.before(newTab);
				enterPage.remove();

				instance.sortable.SortableAddItem(newNavItem[0]);
				instance._deleteButton(newNavItem);

				Liferay.Publisher.deliver('navigation', newNavItem);
			}
		}

		jQuery.ajax(
			{
				data: data,
				success: onSuccess,
				url: instance._updateURL
			}
		);
	},

	_saveSortables: function(obj) {
		var instance = this;

		tabs = jQuery('li', instance._navBlock);

		var data = {
			cmd: 'priority',
			layoutId: obj._LFR_layoutId,
			ownerId: themeDisplay.getOwnerId(),
			parentLayoutId: themeDisplay.getParentLayoutId(),
			priority: tabs.index(obj)
		};

		jQuery.ajax(
			{
				data: data,
				url: instance._updateURL
			}
		);
	},

	_treeCallback: function(item, obj) {
		var instance = this;

		var navigation = instance._navBlock.find('> ul');

		if (instance._isSortable) {
			var liItems = navigation.find('> li');

			var droppedItem = jQuery(item);
			var tree = droppedItem.parent();
			var droppedName = droppedItem.find('span:first').text();
			var newParent = jQuery(obj).parents('li:first');

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

			var newIndex = treeItems.index(item);

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
				var newTab = liItems.lt(1).clone();

				newTab.removeClass('selected');
				newTab.find('.child-menu').remove();

				var newTabLink = newTab.find('a span');

				newTabLink.text(droppedName);
				newTabLink.css('cursor', 'pointer');

				liItems.parent().append(newTab);
			}
		}
	},

	_isSortable: false,
	_isModifiable: false,
	_isUseHandle: false,
	_enterPage: '',
	_updateURL: ''
});