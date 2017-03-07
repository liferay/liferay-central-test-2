import core from 'metal';
import dom from 'metal-dom';
import { EventHandler } from 'metal-events';

import PortletBase from 'frontend-js-web/liferay/PortletBase.es';

/**
 * EditAdaptiveMediaConfig
 *
 * @abstract
 * @extends {PortletBase}
 */

class EditAdaptiveMediaConfig extends PortletBase {
	/**
	 * @inheritDoc
	 */
	created() {
		this.eventHandler_ = new EventHandler();
	}

	/**
	 * @inheritDoc
	 */
	attached() {
		let idOptions = this.one('#idOptions');

		if (idOptions) {
			this.eventHandler_.add(
				dom.delegate(
					idOptions,
					'change',
					'input[type="radio"]',
					(event) => this.onChangeUuidOptions_(event.delegateTarget)
				)
			);
		}

		let nameInput = this.one('#name');

		if (nameInput) {
			this.eventHandler_.add(nameInput.addEventListener('input', (event) => {
				this.updateUuid();
			}));
		}

		this.nameInput = nameInput;

		this.newUuidInput = this.one('#newUuid');
	}

	/**
	 * @inheritDoc
	 */
	detached() {
		super.detached();
		this.eventHandler_.removeAllListeners();
	}

	/**
	 * Updates the uuid identifier based on the "name" field
	 * if the "Automatic" option is selected
	 */
	updateUuid() {
		var newUuidInput = this.newUuidInput;

		var uuidEmpty = !newUuidInput.value;

		if (this.isAutomaticUuid_() && (uuidEmpty || this._originalUuidChanged)) {
			newUuidInput.value = Liferay.Util.normalizeFriendlyURL(this.nameInput.value);
		}

		this._originalUuidChanged = true;
	}

	/**
	 * Checks if the uuid identifier has a custom
	 * value or it has to be generated automatically.
	 *
	 * @protected
	 */
	onChangeUuidOptions_() {
		let newUuidInput = this.newUuidInput;

		if (this.isAutomaticUuid_()) {
			this._lastCustomUuuid = newUuidInput.value;

			this.updateUuid(this.nameInput.value);

			newUuidInput.setAttribute('disabled', true);
		}
		else {
			newUuidInput.value = this._lastCustomUuuid || newUuidInput.value;

			newUuidInput.removeAttribute('disabled');
		}
	}

	/**
	 * Returns if the "Automatic" check is selected
	 * @return {Boolean} whether the "Automatic" radiobutton is checked or not.
	 */
	isAutomaticUuid_() {
		return this.one('input:checked', '#idOptions').value === 'true';
	}
}

export default EditAdaptiveMediaConfig;