import Ajax from 'metal-ajax';
import Component from 'metal-component';
import core from 'metal';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import ProgressBar from 'metal-progressbar';
import Soy from 'metal-soy';
import Tooltip from 'metal-tooltip';

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

		this.tooltipPosition = Tooltip.Align.Top;

		this.updateProgressBar_(this.optimizedImages, this.totalImages);
	}

	/**
	 * It starts checking the percentage of optimized images by
	 * doing ajax request continously.
	 *
	 * @param  {String} backgroundTaskUrl The background task
	 * that has to be invoked.
	 */
	startProgress(backgroundTaskUrl) {
		if (this.percentage >= 100 || this.totalImages === 0) {
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

				let optimizedImages = json.optimizedImages;

				let totalImages = json.totalImages;

				this.updateProgressBar_(optimizedImages, totalImages);

				if (this.percentage >= 100) {
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
	updateProgressBar_(optimizedImages, totalImages) {
		let percentage = Math.round(optimizedImages / totalImages * 100) || 0;

		this.progressBarClass = (percentage >= 100) ? 'progress-bar-success' : '';
		this.progressBarLabel = percentage + '%';
		this.progressBarValue = percentage;
		this.progressBarTooltip = this.tooltip ? this.tooltip : optimizedImages + "/" + totalImages;
		this.percentage = percentage;
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
	 * Number of optimized images in the platform.
	 *
	 * @instance
	 * @memberof AdaptiveMediaProgress
	 * @type {Number}
	 */
	optimizedImages: {
		validator: core.isNumber
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
	 * Number of images present in the platform.
	 *
	 * @instance
	 * @memberof AdaptiveMediaProgress
	 * @type {Number}
	 */
	totalImages: {
		validator: core.isNumber
	},

	/**
	 * The tooltip text to show in the progress bar.
	 *
	 * @instance
	 * @memberof AdaptiveMediaProgress
	 * @type {String}
	 */
	tooltip: {
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