import Ajax from 'metal-ajax';
import Component from 'metal-component';
import core from 'metal';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import ProgressBar from 'metal-progressbar';
import Soy from 'metal-soy';

import templates from './AdaptiveMediaProgress.soy';

/**
 * Handles the actions of the configuration entry's progressbar.
 *
 * @abstract
 * @extends {PortletBase}
 */

class AdaptiveMediaProgress extends PortletBase {
	/**
	 * @inheritDoc
	 */
	created() {
		this.id = this.namespace + 'OptimizeRemaining' + this.uuid + 'Progress';

		this.updateProgressBar_(this.percentage);
	}

	/**
	 * It starts checking the percentage of optimized images by
	 * doing ajax request continously.
	 *
	 * @param  {String} backgroundTaskUrl The background task
	 * that has to be invoked.
	 */
	startProgress(backgroundTaskUrl) {
		if (this.percentage >= 100) {
			return;
		}

		if (backgroundTaskUrl) {
			Ajax.request(backgroundTaskUrl);
		}

		this.clearInterval_();

		this.intervalId = setInterval(
			this.getOptimizedImagesPercentage_.bind(this),
			this.intervalSpeed
		);

		this.showLoadingIndicator = true;
	}

	/**
	 * Clears the interval to stop sending ajax requests.
	 *
	 * @protected
	 */
	clearInterval_() {
		if (this.intervalId) {
			clearInterval(this.intervalId);
		}
	}

	/**
	 * Sends an ajax request to obtain the percentage of
	 * optimized images and updates the progressbar.
	 *
	 * @protected
	 */
	getOptimizedImagesPercentage_() {
		Ajax.request(this.percentageUrl).then((xhr) => {
			try {
				let json = JSON.parse(xhr.response);

				let percentage = parseInt(json.percentage);

				this.updateProgressBar_(percentage);

				if (percentage >= 100) {
					this.onProgressBarComplete_();
				}
			} catch(e) {
				clearInterval(this._intervalId);
			}
		});
	}

	/**
	 * Stops sending ajax request and hides the loading icon.
	 *
	 * @protected
	 */
	onProgressBarComplete_() {
		this.clearInterval_();
		this.showLoadingIndicator = false;
	}

	/**
	 * Updates the progressbar
	 *
	 * @param  {Number} progress progressbar value
	 * @protected
	 */
	updateProgressBar_(progress) {
		this.progressBarClass = (progress >= 100) ? 'progress-bar-success' : '';
		this.progressBarLabel = progress + '%';
		this.progressBarValue = progress;
	}
}

/**
 * AdaptiveMedia State definition.
 * @ignore
 * @static
 * @type {!Object}
 */
AdaptiveMediaProgress.STATE = {
	/**
	 * The interval (in milliseconds) on how often
	 * we will check the percentage of optimized images.
	 *
	 * @instance
	 * @memberof AdaptiveMediaProgress
	 * @type {Number}
	 */
	intervalSpeed: {
		validator: core.isNumber,
		value: 1000
	},

	/**
	 * Current percentage of optimized images.
	 *
	 * @instance
	 * @memberof AdaptiveMediaProgress
	 * @type {Number}
	 */
	percentage: {
		validator: core.isNumber,
		value: 0
	},

	/**
	 * Url to the action that returns the percentage
	 * of optimized images.
	 *
	 * @instance
	 * @memberof AdaptiveMediaProgress
	 * @type {String}
	 */
	percentageUrl: {
		validator: core.isString
	},

	/**
	 * Configuration entry's uuid.
	 *
	 * @instance
	 * @memberof AdaptiveMediaProgress
	 * @type {String}
	 */
	uuid: {
		validator: core.isString
	}
};

// Register component
Soy.register(AdaptiveMediaProgress, templates);

export default AdaptiveMediaProgress;