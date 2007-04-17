var Tree = new Class({
	initialize: function(params) {
		var instance = this;

		instance.className = params.className;
		instance.icons = params.icons;
		instance.nodes = params.nodes;
		instance.openNodes = params.openNodes || '';
		instance.outputId = params.outputId || '';
		instance.tree = null;
		instance.treeHTML = '';
		instance.treeId = params.treeId;

		instance.create();
	},

	addNode: function(parentNode, recursedNodes) {
		var instance = this;
		var icons = instance.icons;
		var src, leafNode, hidden, li;
		var tree = instance.tree;

		for (var i = parentNode; i < instance.nodes.length; i++) {
			var node = instance.nodes[i];

			if (node.parentId == parentNode) {
				var ls = (node.ls == 1) ? true : false;
				var hasChildNode = instance.hasChildNode(node.id);
				var isNodeOpen = instance.isNodeOpen(node.id);

				var layoutId = Liferay.Layout.getLayoutId(node.objId);

				instance.treeHTML += '<li class="tree-item" id="_branchId_' + layoutId + '" rel="_nodeId_' + node.id + '">';
				instance.treeHTML += '<a href="' + node.href + '">';
				instance.treeHTML += instance.generateImage(icons.page);
				instance.treeHTML += '<span>' + node.name + '</span>';
				instance.treeHTML += '</a>';

				// Recurse if node has children

				if (hasChildNode) {
					if (!isNodeOpen) {
						hidden = ' style="display: none;" ';
					}
					else {
						hidden = '';
					}

					instance.treeHTML += '<ul ' + hidden + 'id="' + instance.treeId + "div" + node.id + '">';

					instance.addNode(node.id, recursedNodes);

					instance.treeHTML += '</ul>';
				}

				instance.treeHTML += '</li>';
			}
		}
	},

	create: function() {
		var instance = this;
		var icons = instance.icons;
		var openNodes = instance.openNodes;
		var outputEl = jQuery(instance.outputId);

		var recursedNodes = [];

		if (instance.nodes.length > 0) {
			if (openNodes != null) {
				instance.setOpenNodes();
			}

			var node = instance.nodes[0];

			var tree = jQuery('<ul class="' + instance.className + '"></ul>');
			var treeEl = tree.get(0);

			var mainLi  = jQuery(
				'<li class="root-container">' +
					'<a href="' + node.href + '">' + 
						instance.generateImage(icons.root) + 
						'<span>&nbsp;' + node.name + '</span>' +
					'</a>' +
				'</li>'
			);

			instance.addNode(1, recursedNodes);

			mainLi.append('<ul>' + instance.treeHTML + '</ul>');

			tree.append(mainLi);

			var treeBranches = jQuery('li.tree-item', treeEl);

			tree.prepend('<li class="toggle-expand"><a href="javascript: ;" id="lfr-expand">' + Liferay.Language.get('expand-all') + '</a> | <a href="javascript: ;" id="lfr-collapse">' + Liferay.Language.get('collapse-all') + '</a></li>');

			var branches = tree.find('li[@rel^=_nodeId_]');
			var nodeIdList = [];

			branches.each(
				function(i) {
					nodeIdList[i] = this.getAttribute('rel').replace(/_nodeId_/, '');
				}
			);

			nodeIdList = nodeIdList.join(',');

			tree.find('#lfr-expand').click(
				function() {
					tree.find('.tree-item ul').show();
					tree.find('.tree-item img').each(
						function() {
								this.src = this.src.replace(/plus.png$/, 'minus.png');
						}
					);

					jQuery.ajax(
						{
							url: themeDisplay.getPathMain() + '/portal/session_tree_js_click',
							data: {
								cmd: 'expand',
								nodeIds: nodeIdList,
								treeId: instance.treeId
							}
						}
					);
				}
			);

			tree.find('#lfr-collapse').click(
				function() {
					tree.find('.tree-item ul').hide();
					tree.find('.tree-item img').each(
						function() {
							this.src = this.src.replace(/minus.png$/, 'plus.png');
						}
					);

					jQuery.ajax(
						{
							url: themeDisplay.getPathMain() + '/portal/session_tree_js_click',
							data: {
								cmd: 'collapse',
								treeId: instance.treeId
							}
						}
					);
				}
			);

			// Prepend images

			treeBranches.each(
				function() {
					var subBranch = jQuery('ul', this);
					var currentLi = jQuery(this);
					var src;

					if (subBranch.size() > 0) {
						if (subBranch.eq(0).css('display') == 'none') {
							src = icons.plus;
						}
						else {
							src = icons.minus;
						}
					}
					else {
						src = icons.spacer;
					}

					var image = instance.generateImage(
						{
							src: src,
							className: 'expand-image'
						}
					);

					currentLi.prepend(image);
				}
			);

			// Set toggling

			jQuery('img.expand-image', treeEl).click(
				function() {
					instance.toggle(this);
				}
			);

			// Set drop zones

			var droppableLinks = jQuery('li a', treeEl).not('#lfr-collapse, #lfr-expand');

			droppableLinks.Droppable(
				{
					accept: 'tree-item',
					activeclass: '',
					hoverclass: 'tree-item-hover',
					tolerance: 'pointer',

					ondrop: function(item) {
						instance._onDrop(item, this);
					},

					onhover: function(item) {
						instance._onHover(item, this);
					},

					onout: function() {
						instance._onOut(this);
					}
				}
			);

			// Set draggable items

			jQuery('li.tree-item', treeEl).Draggable(
				{
					autoSize: true,
					ghosting: true,
					handle: 'a',
					revert: true
				}
			);

			instance.tree = tree;

			// Output the tree

			outputEl.append(instance.tree);
		}
	},

	generateImage: function(params) {
		var instance = this;

		var border = params.border || '0';
		var className = params.className || '';
		var height = params.height || '20';
		var hspace = params.hspace || '0';
		var id = params.id || '';
		var src = params.src || params;
		var vspace =  params.vspace || '0';
		var width = params.width || '19';

		border = ' border="' + border + '"';
		className = ' class="' + className + '"';
		height = ' height="' + height + '"';
		hspace = ' hspace="' + hspace + '"';
		id = ' id="' + id + '"';
		src = ' src="' + src + '"';
		vspace = ' vspace="' + vspace + '"';
		width = ' width="' + width + '"';

		return '<img' + border + className + height + hspace + id + src + vspace + width + ' />';
	},

	getHTML: function() {
		var instance = this;

		return instance.treeHTML;
	},

	hasChildNode: function(parentNode) {
		var instance = this;
		var node = instance.nodes[parentNode];

		return (parentNode < instance.nodes.length &&
				node.parentId == parentNode);
	},

	isNodeOpen: function(node) {
		var instance = this;

		for (i = 0; i < instance.openNodes.length; i++) {
			if (instance.openNodes[i] == node) {
				return true;
			}
		}

		return false;
	},

	setOpenNodes: function() {
		var instance = this;
		var openNodes = instance.openNodes;

		if (openNodes != null) {
			instance.openNodes = openNodes.split(',');
		}
	},

	toggle: function(obj) {
		var instance = this;

		if (obj.src.indexOf('spacer') < 0) {
			var icons = instance.icons;
			var treeId = instance.treeId;

			var openNode = false;

			var currentLi = obj.parentNode;

			var nodeId = currentLi.getAttribute('rel').replace(/_nodeId_/, '');

			var subBranch = jQuery('ul', currentLi).eq(0);

			if (subBranch.is(':hidden')) {
				subBranch.show();
				obj.src = icons.minus;
				openNode = true;
			} else {
				subBranch.hide();
				obj.src = icons.plus;
			}

			jQuery.ajax(
				{
					url: themeDisplay.getPathMain() + '/portal/session_tree_js_click',
					data: {
						nodeId: nodeId,
						openNode: openNode,
						treeId: treeId
					}
				}
			);
		}
	},

	_onDrop: function(item, obj) {
		var instance = this;

		var icons = instance.icons;
		var isChild = false;

		var droppedLink = jQuery(obj);

		// Look to see if the dropped item is being dropped on one of its own
		// descendents

		droppedLink.parents('li.tree-item').each(
			function () {
				if (this == item) {
					isChild = true;
					return false;
				}
			}
		);

		if (isChild == true) {
			return;
		}

		if (obj.expanderTime) {
			window.clearTimeout(obj.expanderTime);
			obj.expanded = false;
		}

		var currentBranch = droppedLink.parent();

		var subBranch = jQuery('ul', obj.parentNode);

		if (subBranch.length == 0) {
			jQuery(obj).after('<ul></ul>');
			subBranch = jQuery('ul', obj.parentNode);
		}

		var oldParent = item.parentNode;

		subBranch.eq(0).append(item);

		var oldBranches = jQuery('li', oldParent);

		if (oldBranches.length == 0) {
			jQuery('img.expand-image', oldParent.parentNode).attr('src', icons.spacer);
			jQuery(oldParent).remove();
		}

		if (currentBranch.is('.tree-item')) {
			var expander = jQuery('img.expand-image', obj.parentNode).filter(':first');
			var expanderSrc = expander.attr('src');

			if (expanderSrc.indexOf('spacer') > -1) {
				expander.attr('src', icons.minus);
			}
		}

		var idRegEx = /_branchId_/;

		var newParentId = obj.parentNode.id.replace(idRegEx, '');

		var currentId = item.id.replace(idRegEx, '');

		jQuery.ajax(
			{
				url: themeDisplay.getPathMain() + '/layout_management/update_page',
				data: {
					cmd: 'parent_layout_id',
					layoutId: currentId,
					ownerId: themeDisplay.getOwnerId(),
					parentLayoutId: newParentId
				}
			}
		);

		instance._updateNavigation(item, obj);
	},

	_onHover: function(item, obj) {
		var instance = this;

		var icons = instance.icons;

		if (!obj.expanded) {
			var subBranches = jQuery('ul', obj.parentNode);

			if (subBranches.length > 0) {
				var subBranch = subBranches.eq(0);

				obj.expanded = true;

				if (subBranch.is(':hidden')) {
					var targetBranch = subBranch.get(0);

					obj.expanderTime = window.setTimeout(
						function() {
							jQuery(targetBranch).show();
							jQuery('img.expand-image', targetBranch.parentNode).eq(0).attr('src', icons.minus);
							jQuery.recallDroppables();
						},
						500
					);
				}
			}
		}
	},

	_onOut: function(obj) {
		var instance = this;

		if (obj.expanderTime) {
			window.clearTimeout(obj.expanderTime);
			obj.expanded = false;
		}
	},

	_updateNavigation: function(item, obj) {
		var navigation = jQuery('#navigation > ul');

		if (navigation.length && navigation.parent().is('.sort-pages')) {
			var liItems = navigation.find('> li');

			var droppedItem = jQuery(item);
			var tree = droppedItem.parent();
			var droppedName = droppedItem.find('span:first').text();
			var newParent = jQuery(obj).parents('li:first');

			var liChild = liItems.find('span').not('.delete-tab');

			liChild = liChild.filter(
				function() {
					var currentItem = jQuery(this);
					return (currentItem.text() == droppedName);
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
		}
	}
});