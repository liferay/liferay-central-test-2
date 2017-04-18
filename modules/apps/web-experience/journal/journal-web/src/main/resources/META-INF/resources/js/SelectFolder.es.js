import Component from 'metal-component';
import core from 'metal/src/core';
import Soy from 'metal-soy';

import CardsTreeView from './CardsTreeView.es';
import InputSearch from './InputSearch.es';
import templates from './SelectFolder.soy';

/**
 * SelectFolder
 *
 */
class SelectFolder extends Component {
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
	itemSelectorSaveEvent: {
		validator: core.isString
	},

	nodes: {
		validator: core.isArray
	},

	viewType: {
		validator: core.isString,
		value: 'tree'
	}
};

Soy.register(SelectFolder, templates);

export default SelectFolder;