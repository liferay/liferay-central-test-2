import Component from 'metal-component';
import core from 'metal/src/core';
import Soy from 'metal-soy';

import CardsTreeView from './CardsTreeView.es';
import InputSearch from './InputSearch.es';
import templates from './SelectFolder.soy';

/**
 * SelectFolder
 *
 * This component shows a list of available folders to move content in and
 * allows to filter them by searching.
 */
class SelectFolder extends Component {

	/**
	 * Filters deep nested nodes based on a filtering value
	 *
	 * @type {Array.<Object>} nodes
	 * @type {String} filterVAlue
	 * @protected
	 */
	filterSiblingNodes_(nodes, filterValue) {
		let filteredNodes = [];

		nodes.filter(
			(node) => {
				if (node.name.toLowerCase().indexOf(filterValue) !== -1) {
					filteredNodes.push(node);
				}

				if (node.children) {
					filteredNodes = filteredNodes.concat(this.filterSiblingNodes_(node.children, filterValue));
				}
			}
		);

		return filteredNodes;
	}

	/**
	 * Searchs for nodes by name based on a filtering value
	 *
	 * @param {!Event} event
	 * @protected
	 */
	searchNodes_(event) {
		if (!this.originalNodes) {
			this.originalNodes = this.nodes;
		}
		else {
			this.nodes = this.originalNodes;
		}

		let filterValue = event.newVal.toLowerCase();

		if (filterValue !== '') {
			this.viewType = 'flat';
			this.nodes = this.filterSiblingNodes_(this.nodes, filterValue);
		}
		else {
			this.viewType = 'tree';
		}
	}

	/**
	 * Fires item selector save event on selected node change
	 *
	 * @param {!Event} event
	 * @protected
	 */
	selectedNodeChange_(event) {
		var node = event.newVal[0];

		if (node) {
			var data = {
				folderId: node.id,
				folderName: node.name
			};

			Liferay.Util.getOpener().Liferay.fire(
				this.itemSelectorSaveEvent,
				{
					data: data
				}
			);
		}
	}
}

SelectFolder.STATE = {
	/**
	 * Event name to fire on node selection
	 * @type {String}
	 */
	itemSelectorSaveEvent: {
		validator: core.isString
	},

	/**
	 * List of nodes
	 * @type {Array.<Object>}
	 */
	nodes: {
		validator: core.isArray
	},

	/**
	 * Type of view to render. Accepted values are 'tree' and 'flat'
	 * @type {String}
	 */
	viewType: {
		validator: core.isString,
		value: 'tree'
	}
};

Soy.register(SelectFolder, templates);

export default SelectFolder;