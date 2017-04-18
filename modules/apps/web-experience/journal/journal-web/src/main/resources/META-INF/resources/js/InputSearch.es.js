import Component from 'metal-component';
import core from 'metal/src/core';
import Soy from 'metal-soy';

import templates from './InputSearch.soy';

/**
 * InputSearch
 *
 * This component renders an specific input to do searches. When input value
 * changes component searchValue state changes, so you can suscribe to that
 * change event to listen to user inputs.
 *
 */
class InputSearch extends Component {

	/**
	 * Sets input value to searchValue state property
	 *
	 * @param {!Event} event
	 * @protected
	 */
	setSearchValue_(event) {
		this.searchValue = event.delegateTarget.value;
	}
}

InputSearch.STATE = {
	/**
	 * Portlet Namespace
	 * @type {String}
	 */
	portletNamespace: {
		validator: core.isString
	},

	/**
	 * Search input value
	 * @type {String}
	 */
	searchValue: {
		validator: core.isString
	}
};

Soy.register(InputSearch, templates);

export default InputSearch;