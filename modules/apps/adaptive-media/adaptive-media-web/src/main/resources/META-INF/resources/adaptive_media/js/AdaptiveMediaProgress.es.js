import Ajax from 'metal-ajax';
import Component from 'metal-component';
import core from 'metal';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import ProgressBar from 'metal-progressbar';
import Soy from 'metal-soy';

import templates from './AdaptiveMediaProgress.soy';

/**
 * AdaptiveMediaProgress
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

	startProgress(backgroundTaskUrl) {
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

	clearInterval_() {
		if (this.intervalId) {
			clearInterval(this.intervalId);
		}
	}

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

	onProgressBarComplete_() {
		this.clearInterval_();
		this.showLoadingIndicator = false;
	}

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
	intervalSpeed: {
		validator: core.isNumber,
		value: 1000
	},

	percentage: {
		validator: core.isNumber,
		value: 0
	},

	percentageUrl: {
		validator: core.isString
	},

	uuid: {
		validator: core.isString
	}
};

// Register component
Soy.register(AdaptiveMediaProgress, templates);

export default AdaptiveMediaProgress;