import Component from 'metal-component';
import core from 'metal/src/core';
import Soy from 'metal-soy';

import templates from './InputSearch.soy';

/**
 * InputSearch
 *
 */
class InputSearch extends Component {
	setSearchValue_(event) {
		this.searchValue = event.delegateTarget.value;
	}
}

InputSearch.STATE = {
	searchValue: {
		validator: core.isString
	}
};

Soy.register(InputSearch, templates);

export default InputSearch;