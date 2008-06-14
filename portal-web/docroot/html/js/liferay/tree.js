Liferay.Tree = new Class({
	initialize: function(params) {
		var instance = this;

		instance.className = params.className;
		instance.icons = params.icons;
		instance.nolinks = params.nolinks || false;
		instance.nodes = params.nodes;
		instance.openNodes = params.openNodes || '';
		instance.outputId = params.outputId || '';
		instance.tree = null;
		instance.treeHTML = '';
		instance.treeId = params.treeId;
		instance.selectable = params.selectable || false;
		instance.selectedNodes = params.selectedNodes || '';

		instance._updateURL = themeDisplay.getPathMain() + '/layout_management/update_page';

		instance._originalParentNode = null;
		instance._wasDropped = false;

		instance._dragOptions = {
			helper: 'clone',
			handle: 'a',
			items: '.tree-item',
			distance: 1,
			opacity: 0.5,
			revert: false,
			start: function(event, ui) {
				instance._originalParentNode = ui.item[0].parentNode;
			},
			update: function(event, ui) {
				instance._onUpdate(ui);
			}
		};

		instance._onSelect = params.onSelect || instance._onSelect;

		instance._expandText = Liferay.Language.get('expand-all');
		instance._collapseText = Liferay.Language.get('collapse-all');

		var icons = instance.icons;

		instance._plusImage = instance.generateImage(
			{
				src: icons.plus,
				className: 'expand-image'
			}
		);

		instance._minusImage = instance.generateImage(
			{
				src: icons.minus,
				className: 'expand-image'
			}
		);

		instance._spacerImage = instance.generateImage(
			{
				src: icons.spacer,
				className: 'expand-image'
			}
		);

		instance._spacerImage = instance.generateImage(
			{
				src: icons.spacer,
				className: 'expand-image'
			}
		);

		instance._pageImage = instance.generateImage(
			{
				src: icons.page
			}
		);

		instance._checkedPage = instance.generateImage(
			{
				src: icons.checked,
				className: 'select-state'
			}
		);

		instance._checkBoxPage = instance.generateImage(
			{
				src: icons.checkbox,
				className: 'select-state'
			}
		);

		Liferay.Publisher.register('tree');

		Liferay.Publisher.subscribe('navigation', instance._navigationCallback, instance);

		instance.create();
	},

	addNode: function(parentNode) {
		var instance = this;
		var icons = instance.icons;
		var src, leafNode, hidden, li, hasChildNode, ls, isNodeOpen, isNodeSelected, plid, image, openClass;
		var tree = instance.tree;

		for (var i = parentNode; i < instance.nodes.length; i++) {
			var node = instance.nodes[i];

			if (node.parentId == parentNode) {
				ls = (node.ls == 1);
				hasChildNode = instance.hasChildNode(node.id);
				isNodeOpen = instance.isNodeOpen(node.id);
				isNodeSelected = instance.isNodeSelected(node.objId);

				plid = node.objId;
				src = '';


				image = '';
				openClass = '';
				if (hasChildNode) {
					openClass = 'has-children';
					if (!isNodeOpen) {
						image = instance._plusImage;
					}
					else {
						image = instance._minusImage;
						openClass += ' node-open';
					}
				}
				else {
					image = instance._spacerImage;
				}

				instance.treeHTML += '<li branchId="' + plid + '" class="tree-item" id="_branchId_' + plid + '" nodeId="' + node.id + '">';
				instance.treeHTML += image;

				if (instance.selectable) {
					if (isNodeSelected) {
						instance.treeHTML += instance._checkedPage;
					}
					else {
						instance.treeHTML += instance._checkBoxPage;
					}
				}

				if (instance.nolinks) {
					instance.treeHTML += '<a name="' + node.id + '">';
					instance.treeHTML += instance._pageImage;
					instance.treeHTML += '<span>' + node.name + '</span>';
					instance.treeHTML += '</a>';
				}
				else {
					instance.treeHTML += '<a href="' + node.href + '">';
					instance.treeHTML += instance._pageImage;
					instance.treeHTML += '<span>' + node.name + '</span>';
					instance.treeHTML += '</a>';
				}

				// Recurse if node has children

				if (hasChildNode) {
					if (!isNodeOpen) {
						hidden = ' style="display: none;" ';
					}
					else {
						hidden = '';
					}

					instance.treeHTML += '<ul class="' + openClass + '" ' + hidden + 'id="' + instance.treeId + "div" + node.id + '">';

					instance.addNode(node.id);

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
		var selectedNodes = instance.selectedNodes;
		var outputEl = jQuery(instance.outputId);

		if (instance.nodes.length > 0) {
			if (openNodes != null) {
				instance.setOpenNodes();
			}

			if (instance.selectable && (selectedNodes != null)) {
				instance.setSelectedNodes();
			}

			var node = instance.nodes[0];

			var tree = jQuery('<ul class="lfr-component ' + instance.className + '"></ul>');
			var treeEl = tree[0];

			instance.tree = tree;

			// Output the tree

			outputEl.append(instance.tree);

			instance.addNode(1);

			var mainLi =
				'<li class="toggle-expand"><a href="javascript: ;" id="lfr-expand">' + instance._expandText + '</a> | <a href="javascript: ;" id="lfr-collapse">' + instance._collapseText + '</a></li>' +
					'<li class="root-container">';

			if (instance.nolinks) {
				mainLi += '<a name="' + node.id + '">';
			}
			else {
				mainLi += '<a href="' + node.href + '">';
			}

			mainLi +=
				instance.generateImage(icons.root) +
					'<span>&nbsp;' + node.name + '</span>' +
						'</a>' +
							'<ul class="node-open">' + instance.treeHTML + '</ul>' +
								'</li>';

			tree.append(mainLi);

			var treeBranches = jQuery('li.tree-item', treeEl);

			var nodeIdList = [];

			for (var i = instance.nodes.length - 1; i >= 0; i--) {
				if (i != 0 && instance.nodes[i].id) {
					nodeIdList[i] = instance.nodes[i].id;
				}
			}

			nodeIdList = nodeIdList.join(',');

			// Set toggling

			jQuery('img.expand-image', treeEl).click(
				function() {
					instance.toggle(this);
				}
			);

			// Enable selectable

			if (instance.selectable) {
				jQuery('img.select-state[@src*=checked]', treeEl).each(
					function() {
						instance._fixParentsOfSelected(this.parentNode);
					}
				);

				jQuery('img.select-state', treeEl).click(
					function() {
						instance.select(this);
					}
				);
			}

			// Set drop zones

			var droppableLinks = jQuery('li a', treeEl).not('#lfr-collapse, #lfr-expand');

			droppableLinks.droppable(
				{
					accept: '.tree-item',
					activeClass: '',
					hoverClass: 'tree-item-hover',
					tolerance: 'pointer',
					drop: function(event, ui) {
						instance._onDrop(ui.draggable[0], this);
					},
					over: function(event, ui) {
						instance._onHover(ui.draggable, this);
					},
					out: function(event, ui) {
						instance._onOut(this);
					}
				}
			);

			instance._droppables = droppableLinks;

			// Set draggable items

			instance.setDraggable(treeEl);

			var allDraggable = false;

			jQuery('#lfr-expand').click(
				function() {
					tree.find('.tree-item ul').show();
					tree.find('.tree-item img').each(
						function() {
								this.src = this.src.replace(/plus.png$/, 'minus.png');
						}
					);
					if (!allDraggable) {
						instance.setDraggable(treeEl, 'li.tree-item');
					}
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

			jQuery('#lfr-collapse').click(
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

	hasChildNode: function(parentNode) {
		var instance = this;

		return (parentNode < instance.nodes.length) && (instance.nodes[parentNode].parentId == parentNode);
	},

	isNodeOpen: function(node) {
		var instance = this;

		return (instance.openNodes.indexOf(',' + node + ',') > -1);
	},

	isNodeSelected: function(node) {
		var instance = this;

		for (var i = instance.selectedNodes.length - 1; i >= 0; i--) {
			if (instance.selectedNodes[i] == node) {
				return true;
			}
		}

		return false;
	},

	select: function(obj) {
		var instance = this;

		if (obj.src.indexOf('spacer') < 0) {
			var icons = instance.icons;
			var treeIdSelected = instance.treeId + "Selected";

			var selectedNode = false;

			var currentLi = obj.parentNode;

			var branchId = currentLi.getAttribute('branchId');

			if (instance._hasSelectedChildren(currentLi)) {
				if (obj.getAttribute("src") == icons.checked) {
					obj.src = icons.childChecked;
				}
				else {
					obj.src = icons.checked;
					selectedNode = true;
				}
			}
			else if (obj.getAttribute("src") == icons.checked) {
				obj.src = icons.checkbox;
			}
			else {
				obj.src = icons.checked;
				selectedNode = true;
			}

			instance._fixParentsOfSelected(currentLi);

			instance._onSelect(
				{
					branchId: branchId,
					openNode: selectedNode,
					selectedNode: selectedNode,
					treeId: treeIdSelected
				}
			);
		}
	},

	setDraggable: function(parentEl, selector) {
		var instance = this;

		if (!instance._initialSortable) {
			jQuery.extend(instance._dragOptions, {connectTo: 'ul.node-open'})

			instance._initialSortable = jQuery(parentEl).sortable(instance._dragOptions);
		}
		else {
			instance._initialSortable.sortable('refresh');
		}
	},

	setOpenNodes: function() {
		var instance = this;
		var openNodes = instance.openNodes;

		if (openNodes[openNodes.length - 1] != ',') {
			openNodes += ',';
		}

		if (openNodes[0] != ',') {
			openNodes = ',' + openNodes;
		}

		instance.openNodes = openNodes;
	},

	setSelectedNodes: function() {
		var instance = this;
		var selectedNodes = instance.selectedNodes;

		if (selectedNodes != null) {
			instance.selectedNodes = selectedNodes.split(',');
		}
	},

	toggle: function(obj) {
		var instance = this;

		if (obj.src.indexOf('spacer') < 0) {
			var icons = instance.icons;
			var treeId = instance.treeId;

			var openNode = false;

			var currentLi = obj.parentNode;

			var nodeId = currentLi.getAttribute('nodeId');

			var subBranch = jQuery('ul', currentLi).eq(0);

			currentLi.childrenDraggable = false;

			if (subBranch.is(':hidden')) {
				subBranch.show();
				obj.src = icons.minus;
				openNode = true;

				if (!currentLi.childrenDraggable) {
					instance.setDraggable(currentLi, '> ul > li.tree-item')
					currentLi.childrenDraggable = true;
				}
			}
			else {
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

	_fixParentsOfSelected: function(currentLi) {
		var instance = this;

		var parentLi = currentLi.parentNode.parentNode;

		if ((parentLi.nodeName == 'LI') && (parentLi.className != 'root-container')) {
			var icons = instance.icons;
			var img = jQuery("> img.select-state", parentLi)[0];

			if (instance._hasSelectedChildren(parentLi)) {
				if (img.getAttribute("src") == icons.checkbox) {
					img.src = icons.childChecked;
				}
			}
			else if (img.getAttribute("src") != icons.checked) {
				img.src = icons.checkbox;
			}

			instance._fixParentsOfSelected(parentLi);
		}
	},

	_hasSelectedChildren: function(currentLi) {
		var checkedChildren = jQuery('> ul > li > img.select-state[@src*=checked]', currentLi);
		var checkedCheckedChildren = jQuery('> ul > li > img.select-state[@src*=child_checked]', currentLi);

		if (checkedChildren.size() > 0 || checkedCheckedChildren.size() > 0) {
			return true;
		}

		return false;
	},

	_navigationCallback: function(obj, type) {
		var instance = this;

		type = (!type) ? 'update' : type;

		var tree = instance.tree;

		if (tree.length > 0) {
			if (type == 'update') {

				var droppedName = jQuery('span:eq(0)', obj).text();
				var li = tree.find('> li > ul > li');

				var liChild = li.find('span:first').filter(
					function() {
						return (jQuery(this).text() == droppedName);
					}
				);

				liChild = liChild.parents('li:first');

				var droppedIndex = jQuery(obj).parent().find('> li').index(obj);

				var newSibling = li.eq(droppedIndex);

				newSibling.after(liChild);

				var newIndex = li.index(liChild[0]);

				if (newIndex > droppedIndex || droppedIndex == 0) {
					newSibling = li.eq(droppedIndex);
					newSibling.before(liChild);
				}
			}
			else if (type == 'delete') {
				var tabLayoutId = obj[0]._LFR_layoutId;
				var treeBranch = tree.find('li[@nodeId=' + tabLayoutId + ']');

				treeBranch.remove();
			}
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
			function() {
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

		var newParentId = obj.parentNode.getAttribute('branchId');

		var currentId = item.getAttribute('branchId');

		instance._saveParentNode(
			{
				plid: currentId,
				parentPlid: newParentId
			}
		);

		Liferay.Publisher.deliver('tree', item, obj);

		instance._originalParentNode = null;
		instance._wasDropped = true;
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
					targetBranch.childrenDraggable = false;
					obj.expanderTime = window.setTimeout(
						function() {
							if (!targetBranch.childrenDraggable) {
								instance.setDraggable(targetBranch, '> li.tree-item');
								targetBranch.childrenDraggable = true;
							}

							jQuery(targetBranch).show();
							jQuery('img.expand-image', targetBranch.parentNode).eq(0).attr('src', icons.minus);
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

	_onSelect: function(params) {
		var instance = this;

		var branchId = params.branchId;
		var selectedNode = params.selectedNode;
		var treeIdSelected = params.treeId;

		jQuery.ajax(
			{
				url: themeDisplay.getPathMain() + '/portal/session_tree_js_click',
				data: {
					nodeId: branchId,
					openNode: selectedNode,
					treeId: treeIdSelected
				}
			}
		);
	},

	_onUpdate: function(ui) {
		var instance = this;

		var currentNode = ui.item[0];
		var parent = currentNode.parentNode;
		var parentNotUpdated = (instance._originalParentNode && instance._originalParentNode != parent && !instance._wasDropped);

		var currentId = currentNode.getAttribute('branchId');
		var parentId = parent.parentNode.getAttribute('branchId') || '';

		if (parentNotUpdated) {
			instance._saveParentNode(
				{
					plid: currentId,
					parentPlid: parentId
				}
			);
		}

		var index = jQuery('> li', parent).index(currentNode);

		var data = {
			cmd: 'priority',
			plid: currentId,
			priority: index
		};

		jQuery.ajax(
			{
				data: data,
				url: instance._updateURL
			}
		);

		instance._originalParentNode = null;
		instance._wasDropped = false;
	},

	_saveParentNode: function(options) {
		var instance = this;

		jQuery.ajax(
			{
				url: themeDisplay.getPathMain() + '/layout_management/update_page',
				data: {
					cmd: 'parent_layout_id',
					parentPlid: options.parentPlid,
					plid: options.plid
				}
			}
		);
	},

	_savePosition: function(options) {
		var instance = this;

		jQuery.ajax(
			{
				url: instance._updateURL,
				data: options
			}
		);
	}
});