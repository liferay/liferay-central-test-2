import core from 'metal';
import dom from 'metal-dom';
import { EventHandler } from 'metal-events';

import PortletBase from 'frontend-js-web/liferay/PortletBase.es';

/**
 * Enables/disables the actions of the configuration entry's while
 * it is being optimized.
 *
 * @abstract
 * @extends {PortletBase}
 */

class AdaptiveMediaOptionsHandler extends PortletBase {
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
		let progressBarComponent = this.getProgressBarComponent_();

		this.eventHandler_.add(
			progressBarComponent.on('start_optimizing', () => this.onStartOptimizing_())
		);

		this.eventHandler_.add(
			progressBarComponent.on('finish_optimizing', () => this.onFinishOptimizing_())
		);

		this.disableIcon = this.one('#icon-disable-' + this.uuid, 'body');
		this.optimizeIcon = this.one('#icon-optimize-' + this.uuid, 'body');
	}

	/**
	 * @inheritDoc
	 */
	detached() {
		super.detached();
		this.eventHandler_.removeAllListeners();
	}

	/**
	 * Get the progress bar component associated to
	 * the configuration entry.
	 *
	 * @protected
	 * @return {AdaptiveMediaProgress} progressbar component
	 */
	getProgressBarComponent_() {
		if (!this.progressBarComponent_) {
			this.progressBarComponent_ = Liferay.component(this.ns('OptimizeRemaining' + this.uuid));
		}

		return this.progressBarComponent_;
	}

	/**
	 * Disables the "Disable" and "Optimize Remaining" icons.
	 *
	 * @protected
	 */
	onStartOptimizing_() {
		this.disableIcon_(this.optimizeIcon);
		this.disableIcon_(this.disableIcon);
	}

	/**
	 * Enable the "Disable" icon.
	 *
	 * @protected
	 */
	onFinishOptimizing_() {
		this.enableIcon_(this.disableIcon);
	}

	/**
	 * Disables a configuration entry icon.
	 *
	 * @protected
	 */
	disableIcon_(element) {
		if (!element) {
			return;
		}

		dom.addClasses(element.parentElement, 'disabled');

		element.setAttribute('data-href', element.getAttribute('href'));
		element.setAttribute('data-onclick', element.getAttribute('onclick'));
		element.removeAttribute('href');
		element.removeAttribute('onclick');
	}

	/**
	 * Enables a configuration entry icon.
	 *
	 * @protected
	 */
	enableIcon_(element) {
		if (!element) {
			return;
		}

		dom.removeClasses(element.parentElement, 'disabled');

		element.setAttribute('href', element.getAttribute('data-href'));
		element.setAttribute('onclick', element.getAttribute('data-onclick'));

		element.removeAttribute('data-href');
		element.removeAttribute('data-onclick');
	}
}

/**
 * AdaptiveMediaOptionsHandler State definition.
 * @ignore
 * @static
 * @type {!Object}
 */
AdaptiveMediaOptionsHandler.STATE = {
	/**
	 * Configuration entry's uuid.
	 *
	 * @instance
	 * @memberof AdaptiveMediaOptionsHandler
	 * @type {String}
	 */
	uuid: {
		validator: core.isString
	}
}

export default AdaptiveMediaOptionsHandler;