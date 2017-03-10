import Soy from 'metal-soy/src/Soy';

import core from 'metal/src/core';
import dom from 'metal-dom/src/dom';
import Treeview from 'metal-treeview';

import templates from './CardsTreeView.soy';

/**
 * CardsTreeView
 *
 * This is an extension of the default TreeView component that adds
 * the following features:
 *
 * - Node selection management, both single and multiple
 * - Custom tree node template using Lexicon Horizontal Cards
 * - Improved accessibility for keyboard navigation following common tree widget patterns
 */
class CardsTreeview extends Treeview {

	created() {
		this.nodes.forEach(
			function(node) {
				if (node.children) {
					node.expanded = this.expandParentNodes_(node.children);
				}
			},
			this
		);
	}

	attached() {
		this.addSelectedNodes_(this.nodes);
	}

	/**
	 * Adds nodes with selected attribute to selectedNodes list in case when
	 * they are still not there.
	 *
	 * @param nodes Nodes to check and add to selectedNodes list.
	 * @protected
	 */
	addSelectedNodes_(nodes) {
		nodes.forEach(
			function(node) {
				if (node.children) {
					this.addSelectedNodes_(node.children);
				}

				if (node.selected) {
					this.selectNode_(node.id);
				}
			},
			this
		);
	}

	/**
	 * Deselects all selected tree nodes
	 *
	 * @param nodes List of nodes to deselect the elements in.
	 * @protected
	 */
	deselectAll_(nodes) {
		nodes.forEach(
			function(node) {
				node.selected = false;

				if (node.children) {
					this.deselectAll_(node.children);
				}
			},
			this
		);
	}

	/**
	 * Expands all parent nodes of expanded children.
	 *
	 * @param nodes List of nodes to expand all parent nodes of expanded children.
	 * @protected
	 */
	expandParentNodes_(nodes) {
		let expanded = false;

		for (let node of nodes) {
			if (node.expanded) {
				return true;
			}

			if (node.children) {
				node.expanded = this.expandParentNodes_(node.children);
			}
		}

		return false;
	}

	/**
	 * Focus the given tree node.
	 * @param {!Object} nodeObj
	 * @protected
	 */
	focus_(nodeObj) {

		let focusedNode = this.element.querySelector('.focused');

		if (focusedNode) {
			focusedNode.classList.remove('focused');
		}

		if (!nodeObj.id) {
			return;
		}

		if (nodeObj) {
			this.element.querySelector('[data-treeitemid="' + nodeObj.id + '"]').classList.add('focused');
			this.element.querySelector('[data-treeitemid="' + nodeObj.id + '"] .card').focus();
		}
	}

	/**
	 * Focus the next tree node of given tree node.
	 * @param {!Element} node
	 * @protected
	 */
	focusNextNode_(node) {
		let path = node.getAttribute('data-treeview-path').split('-');

		let nodeObj = this.getNodeObj(path);

		let nextNodeObj;

		if (nodeObj.children && nodeObj.expanded) {
			path.push(0);

			nextNodeObj = this.getNodeObj(path);
		}
		else {
			while (!nextNodeObj && path.length > 0) {
				path[path.length - 1]++;

				nextNodeObj = this.getNodeObj(path);

				path.pop();
			}
		}

		this.focus_(nextNodeObj);
	}

	/**
	 * Focus the previous tree node of given tree node.
	 * @param {!Element} node
	 * @protected
	 */
	focusPrevNode_(node) {
		let path = node.getAttribute('data-treeview-path').split('-');

		let prevNodeObj;

		if (path[path.length - 1] === '0') {
			path.pop();

			prevNodeObj = this.getNodeObj(path);
		}
		else {
			path[path.length - 1]--;

			prevNodeObj = this.getNodeObj(path);

			while (prevNodeObj.children && prevNodeObj.expanded) {
				prevNodeObj = prevNodeObj.children[prevNodeObj.children.length - 1];
			}
		}

		this.focus_(prevNodeObj);
	}

	/**
	 * This is called when one of this tree view's nodes is clicked.
	 * @param {!Event} event
	 * @protected
	 */
	handleNodeClicked_(event) {
		let currentTarget = event.delegateTarget.parentNode;

		let currentTargetId = currentTarget.getAttribute('data-treeitemid');

		if (!currentTargetId) {
			return;
		}

		this.focus_(
			{
				id: currentTargetId
			}
		);

		if (currentTarget.classList.contains('disabled')) {
			return;
		}

		this.selectNode_(currentTargetId);
	}

	/**
	 * Selects specific node.
	 *
	 * @param nodeId ID of node to select.
	 * @protected
	 */
	selectNode_(nodeId) {
		if (this.multiSelection) {
			if (this.selectedNodes.indexOf(nodeId + ',') !== -1) {
				this.selectedNodes = this.selectedNodes.replace(nodeId + ',', '');
			}
			else {
				this.selectedNodes += nodeId + ',';
			}
		}
		else {
			this.deselectAll_(this.nodes);

			this.selectedNodes = ',' + nodeId + ',';
		}
	}

	/**
	 * This is called when one of this tree view's nodes toggler is clicked.
	 * @param {!Event} event
	 * @protected
	 */
	handleNodeTogglerClicked_(event) {
		this.toggleExpandedState_(event.delegateTarget.parentNode.parentNode.parentNode);
	}

	/**
	 * This is called when one of this tree view's nodes receives a keypress.
	 * Depending on the pressed key, the tree will:
	 * - ENTER or SPACE: Select the current node
	 * - DOWN ARROW: Focus the next node
	 * - UP ARROW: Focus the previous node
	 * - LEFT ARROW: Collapse the current node
	 * - RIGHT ARROW: Expand the current node
	 * @param {!Event} event
	 * @protected
	 */
	handleNodeKeyUp_(event) {
		let node = event.delegateTarget.parentNode.parentNode.parentNode;

		if (event.keyCode === 37) {
			this.setNodeExpandedState_(node, {expanded: false});
		}
		else if (event.keyCode === 38) {
			this.focusPrevNode_(node);
		}
		else if (event.keyCode === 39) {
			this.setNodeExpandedState_(node, {expanded: true});
		}
		else if (event.keyCode === 40) {
			this.focusNextNode_(node);
		}
		else if (event.keyCode === 13 || event.keyCode === 32) {
			this.handleNodeClicked_(event);
		}
	}

	/**
	 * Sets the expanded state of a node
	 * @param {!Element} node The tree node we want to change the expanded state to
	 * @param {!Object} state A state object with the new value of the expanded state
	 * @protected
	 */
	setNodeExpandedState_(node, state) {
		let path = node.getAttribute('data-treeview-path').split('-');

		let nodeObj = this.getNodeObj(path);

		nodeObj.expanded = state.expanded;

		this.nodes = this.nodes;
	}
}

/**
 * CardsTreeview state definition.
 * @type {!Object}
 * @static
 */
CardsTreeview.STATE = {
	/**
	 * Enables multiple selection of tree elements
	 * @type {boolean}
	 */
	multiSelection: {
		validator: core.isBoolean,
		value: false
	}
};

Soy.register(CardsTreeview, templates);

export default CardsTreeview;