Liferay.Tree = new Expanse.Class(
	{

		/**
		 * OPTIONS
		 *
		 * Required
		 * className {string}: A class to add to the tree.
		 * icons {object}: An object containing the icons to use in the tree.
		 * nodes {array}: An array of objects containing the tree nodes.
		 * openNodes {string}: A comma-separated list of which node ids default to open.
		 * outputId {string}: A jQuery selector that specifies where the tree should be inserted.
		 * treeId {string}: A unique id to apply to the tree.
		 *
		 * Optional
		 * nodeIds {string}: A comma-separated list of the existing node ids.
		 * nolinks {boolean}: Whether to include hrefs on the nodes.
		 * preRendered {boolean}: Whether to use a pre-rendered html list as the tree
		 * selectable {boolean}: Whether the tree nodes can be selected with checkboxes.
		 * selectedNodes {string}: A comma-separated list of which node ids default to open.
		 * url {string}: URL base for load the node contents using ajax - should retun a valid Tree HTML.
		 *
		 * Callbacks
		 * onSelect {string}: Called when a node is selected (if selectable is true).
		 */

		initialize: function(options) {
			var instance = this;

			instance.className = options.className || 'lfr-tree';
			instance.icons = options.icons;
			instance.nodes = options.nodes;
			instance.nodeIds = options.nodeIds;
			instance.nolinks = options.nolinks || false;
			instance.openNodes = options.openNodes || '';
			instance.outputId = options.outputId || '';
			instance.preRendered = options.preRendered;
			instance.tree = null;
			instance.treeHTML = [];
			instance.treeId = options.treeId;
			instance.selectable = options.selectable || false;
			instance.selectedNodes = options.selectedNodes || '';
			instance.url = options.url;

			instance._updateURL = themeDisplay.getPathMain() + '/layout_management/update_page';

			instance._originalParentNode = null;
			instance._wasDropped = false;

			instance._dragOptions = {
				cssNamespace: false,
				helper: 'clone',
				handle: '> a',
				distance: 1,
				opacity: 0.5,
				revert: false,
				scope: 'liferayTree',
				start: function(event, ui) {
					instance._originalParentNode = this.parentNode;
				},
				update: function(event, ui) {
					instance._onUpdate(ui);
				}
			};

			instance._dropOptions = {
				cssNamespace: false,
				accept: '.tree-item',
				activeClass: '',
				hoverClass: 'tree-item-hover',
				scope: 'liferayTree',
				tolerance: 'pointer',
				drop: function(event, ui) {
					ui.helper.remove();
					instance._onDrop(ui.draggable[0], this);
				},
				over: function(event, ui) {
					instance._onHover(event, ui, this);
				},
				out: function(event, ui) {
					instance._onOut(this);
				}
			};

			instance._onSelect = options.onSelect || instance._onSelect;

			instance._expandText = Liferay.Language.get('expand-all');
			instance._collapseText = Liferay.Language.get('collapse-all');

			if (!instance.preRendered) {
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
			}

			Liferay.bind('navigation', instance._navigationCallback, instance);

			instance.create();

			instance._attachEventDelegation();
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

					instance.treeHTML.push('<li branchId="' + plid + '" class="tree-item" id="_branchId_' + plid + '" nodeId="' + node.id + '">');
					instance.treeHTML.push(image);

					if (instance.selectable) {
						if (isNodeSelected) {
							instance.treeHTML.push(instance._checkedPage);
						}
						else {
							instance.treeHTML.push(instance._checkBoxPage);
						}
					}

					if (instance.nolinks) {
						instance.treeHTML.push('<a id="' + node.id + '">');
						instance.treeHTML.push(instance._pageImage);
						instance.treeHTML.push('<span>' + node.name + '</span>');
						instance.treeHTML.push('</a>');
					}
					else {
						instance.treeHTML.push('<a href="' + node.href + '">');
						instance.treeHTML.push(instance._pageImage);
						instance.treeHTML.push('<span>' + node.name + '</span>');
						instance.treeHTML.push('</a>');
					}

					// Recurse if node has children

					if (hasChildNode) {
						if (!isNodeOpen) {
							hidden = ' style="display: none;"';
						}
						else {
							hidden = '';
						}

						instance.treeHTML.push('<ul class="' + openClass + '" ' + hidden + 'id="' + instance.treeId + "div" + node.id + '">');

						instance.addNode(node.id);

						instance.treeHTML.push('</ul>');
					}

					instance.treeHTML.push('</li>');
				}
			}
		},

		create: function() {
			var instance = this;

			var outputEl = jQuery(instance.outputId);
			var mainLi = '<li class="toggle-expand"><a class="lfr-expand" href="javascript:;" id="lfrExpand">' + instance._expandText + '</a> | <a class="lfr-collapse" href="javascript:;" id="lfrCollapse">' + instance._collapseText + '</a></li>';

			if (!instance.preRendered) {
				var icons = instance.icons;
				var openNodes = instance.openNodes;
				var selectedNodes = instance.selectedNodes;

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

					mainLi += '<li class="root-container">';

					if (instance.nolinks) {
						mainLi += '<a id="' + node.id + '">';
					}
					else {
						mainLi += '<a href="' + node.href + '">';
					}

					mainLi +=
						instance.generateImage(icons.root) +
							'<span>&nbsp;' + node.name + '</span>' +
								'</a>' +
									'<ul class="node-open">' + instance.treeHTML.join('') + '</ul>' +
										'</li>';

					tree.append(mainLi);

					var treeBranches = jQuery('li.tree-item', treeEl);
				}
			}
			else {
				var tree = outputEl.find('> ul');
				var treeEl = tree[0];

				tree.prepend(mainLi);

				instance.tree = tree;
			}

			if (!instance.nodeIds) {
				var nodeIdList = [];

				for (var i = instance.nodes.length - 1; i >= 0; i--) {
					if (i != 0 && instance.nodes[i].id) {
						nodeIdList[i] = instance.nodes[i].id;
					}
				}

				instance.nodeIds = nodeIdList.join(',');
			}

			// Set draggables and droppables

			instance.setInteraction(treeEl);

			var allDraggable = false;

			jQuery('#lfrExpand').click(
				function() {
					tree.find('.tree-item ul:has(.tree-item)').show();
					tree.find('.tree-item:has(.tree-item) > img').each(
						function() {
								this.src = this.src.replace(/plus.png$/, 'minus.png');
						}
					);

					if (!allDraggable) {
						instance.setInteraction(treeEl, 'li.tree-item');
						allDraggable = true;
					}

					jQuery.ajax(
						{
							url: themeDisplay.getPathMain() + '/portal/session_tree_js_click',
							data: {
								cmd: 'expand',
								nodeIds: instance.nodeIds,
								treeId: instance.treeId
							}
						}
					);
				}
			);

			jQuery('#lfrCollapse').click(
				function() {
					tree.find('.tree-item ul:has(.tree-item)').hide();
					tree.find('.tree-item:has(.tree-item) > img').each(
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
		},

		generateImage: function(options) {
			var instance = this;

			var border = ' border="' + (options.border || '0') + '"';
			var className = ' class="' + (options.className || '') + '"';
			var height = ' height="' + (options.height || '20') + '"';
			var hspace = ' hspace="' + (options.hspace || '0') + '"';
			var id = ' id="' + (options.id || '') + '"';
			var src = ' src="' + (options.src || options) + '"';
			var vspace = ' vspace="' + (options.vspace || '0') + '"';
			var width = ' width="' + (options.width || '19') + '"';

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

			if (obj.tagName.toLowerCase() == 'a') {
				obj = jQuery('> img.select-state', obj.parentNode)[0];
			}

			var src = obj.src;

			if (obj.src.indexOf('spacer') < 0) {
				var icons = instance.icons;
				var treeIdSelected = instance.treeId + 'Selected';

				var selectedNode = false;

				var currentLi = obj.parentNode;

				var branchId = currentLi.getAttribute('branchId');

				if (instance._hasSelectedChildren(currentLi)) {
					if (src.indexOf(icons.checked) > -1) {
						obj.src = icons.childChecked;
					}
					else {
						obj.src = icons.checked;
						selectedNode = true;
					}
				}
				else if (src.indexOf(icons.checked) > -1) {
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

		setInteraction: function(parentEl, selector) {
			var instance = this;

			var treeEl = instance.tree[0];

			if (!instance.selectable) {
				selector = selector || 'ul.node-open > li';
				parentEl = parentEl || treeEl;

				var draggables = jQuery(selector, parentEl);
				draggables.draggable(instance._dragOptions);

				draggables.find('> a').droppable(instance._dropOptions);

				if (!instance.droppablesSet) {
					instance.tree.find('a.community').droppable(instance._dropOptions);
					instance.droppablesSet = true;
				}
			}
			else {
				jQuery('img.select-state[src*=checked]', treeEl).each(
					function(event) {
						instance._fixParentsOfSelected(this.parentNode);
					}
				);

				jQuery('img.select-state, a', treeEl).unbind('click').click(
					function(event) {
						instance.select(this);
						return false;
					}
				);
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

				var nodeId = jQuery(currentLi).attr('nodeId');

				var parentLayoutId = jQuery(currentLi).attr('layoutId');

				var privateLayout = jQuery(currentLi).attr('privateLayout');

				var subBranch = jQuery('ul', currentLi).eq(0);

				var empty = subBranch.find("li").length == 0;

				currentLi.childrenDraggable = false;

				var branchInteraction = function() {
					if (subBranch.is(':hidden')) {
						subBranch.show();
						obj.src = icons.minus;
						openNode = true;

						if (!currentLi.childrenDraggable) {
							subBranch.addClass('node-open');
							instance.setInteraction(currentLi);
							currentLi.childrenDraggable = true;
						}
					}
					else {
						subBranch.hide();
						obj.src = icons.plus;
					}
				};

				var	sessionClick = function() {
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
				};

				branchInteraction();

				if (instance.url && empty) {
					var url = instance.url + '&nodeId=' + nodeId + '&parentLayoutId=' + parentLayoutId + '&privateLayout=' + privateLayout;

					var loadingGif = themeDisplay.getPathContext() + '/html/themes/classic/images/application/loading_indicator.gif';
					var pageImg = jQuery(currentLi).find('a img:first');
					var pageImgSrc = pageImg.attr('src');

					pageImg.attr('src', loadingGif);

					jQuery.get(
						url,
						function(html) {
							subBranch.html(html);
							branchInteraction();
							pageImg.attr('src', pageImgSrc);
							sessionClick();
						}
					);
				}
				else {
					sessionClick();
				}
			}
		},

		_attachEventDelegation: function() {
			var instance = this;
			var treeEl = instance.tree;

			treeEl.mouseup(
				function(event) {
					var target = event.target;
					var nodeName = target.nodeName || '';

					if (nodeName.toLowerCase() == 'img' && target.className) {
						if (target.className.indexOf('expand-image') != -1) {
							instance.toggle(target);
						}
					}
				}
			);
		},

		_fixParentsOfSelected: function(currentLi) {
			var instance = this;

			var parentLi = currentLi.parentNode.parentNode;

			if ((parentLi.nodeName == 'LI') && (parentLi.className != 'root-container')) {
				var icons = instance.icons;
				var img = jQuery("> img.select-state", parentLi)[0];
				var src = img.src;

				if (instance._hasSelectedChildren(parentLi)) {
					if (src.indexOf(icons.checkbox) > -1) {
						img.src = icons.childChecked;
					}
				}
				else if (src.indexOf(icons.childChecked) > -1) {
					img.src = icons.checkbox;
				}

				instance._fixParentsOfSelected(parentLi);
			}
		},

		_hasSelectedChildren: function(currentLi) {
			var checkedChildren = jQuery('> ul > li > img.select-state[src*=checked]', currentLi);
			var checkedCheckedChildren = jQuery('> ul > li > img.select-state[src*=child_checked]', currentLi);

			if (checkedChildren.size() > 0 || checkedCheckedChildren.size() > 0) {
				return true;
			}

			return false;
		},

		_navigationCallback: function(event, data) {
			var instance = this;

			var type = (!data.type || data.type != 'delete') ? 'update' : data.type;
			var item = data.item;

			var tree = instance.tree;

			if (tree.length > 0) {
				if (type == 'update') {
					var droppedName = jQuery('span:eq(0)', item).text();
					var li = tree.find('> li > ul > li');

					var liChild = li.find('span:first').filter(
						function() {
							return (jQuery(this).text() == droppedName);
						}
					);

					liChild = liChild.parents('li:first');

					var droppedIndex = jQuery(item).parent().find('> li').index(item);

					var newSibling = li.eq(droppedIndex);

					newSibling.after(liChild);

					var newIndex = li.index(liChild[0]);

					if (newIndex > droppedIndex || droppedIndex == 0) {
						newSibling = li.eq(droppedIndex);
						newSibling.before(liChild);
					}
				}
				else if (type == 'delete') {
					var tabLayoutId = item[0]._LFR_layoutId;
					var treeBranch = tree.find('li[nodeId=' + tabLayoutId + ']');

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

			var oldBranches = jQuery('> li', oldParent);

			if (oldBranches.length < 1) {
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

			var newParentId = obj.parentNode.getAttribute('branchId');

			var currentId = item.getAttribute('branchId');

			instance._saveParentNode(
				{
					plid: currentId,
					parentPlid: newParentId
				}
			);

			Liferay.trigger(
				'tree',
				{
					droppedItem: item,
					dropTarget: obj
				}
			);

			instance._originalParentNode = null;
			instance._wasDropped = true;
		},

		_onHover: function(event, ui, obj) {
			var instance = this;

			var item = ui.draggable;

			var icons = instance.icons;

			if (!obj.expanded) {
				var subBranches = jQuery('ul', obj.parentNode);

				if (subBranches.length > 0) {
					var subBranch = subBranches.eq(0);
					var draggable = jQuery(item).data('draggable');
					var droppable = jQuery(obj).data('droppable');
					obj.expanded = true;

					if (subBranch.is(':hidden')) {
						var targetBranch = subBranch.get(0);
						targetBranch.childrenDraggable = false;

						obj.expanderTime = window.setTimeout(
							function() {
								var branch = subBranch.eq(0);

								if (!targetBranch.childrenDraggable) {
									branch.addClass('node-open');
									instance.setInteraction(targetBranch.parentNode);
									targetBranch.childrenDraggable = true;
								}

								branch.show();

								jQuery.ui.ddmanager.prepareOffsets(draggable, event);
								jQuery('img.expand-image:first', targetBranch.parentNode).attr('src', icons.minus);
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

		_onSelect: function(options) {
			var instance = this;

			var branchId = options.branchId;
			var selectedNode = options.selectedNode;
			var treeIdSelected = options.treeId;

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
		}
	}
);