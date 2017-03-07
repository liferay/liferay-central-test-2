import core from 'metal';
import Component from 'metal-component';
import Soy from 'metal-soy';
import Ajax from 'metal-ajax';
import ProgressBar from 'metal-progressbar';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import templates from './AdaptiveMediaProgress.soy';

/**
 * AdaptiveMediaProgress
 *
 * @abstract
 * @extends {Component}
 */

class AdaptiveMediaProgress extends PortletBase {
	/**
	 * @inheritDoc
	 */
	created() {
		this.id = 'OptimizeRemaining' + this.uuid +'Progress';
	}

	/**
	 * @inheritDoc
	 */
	attached() {
		this.progressbar = new ProgressBar(
			{
				barClass: this.getProgressBarClass_(this.percentage),
				label: this.percentage + '%',
				value: this.percentage
			},
			this.getElement_('ProgressBar')
		);
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

		this.showLoadingIndicator_();
	}

	clearInterval_() {
		if (this.intervalId) {
			clearInterval(this.intervalId);
		}
	}

	getElement_(elementId) {
		return this.one('#' + this.id + elementId);
	}

	getOptimizedImagesPercentage_() {
		Ajax.request(this.percentageUrl).then((xhr) => {
			try {
				let json = JSON.parse(xhr.response);

				let percentage = parseInt(json.percentage);

				this.updateProgressBar_(percentage);

				if (percentage >= 100) {
					this.onProgressComplete_();
				}
			} catch(e) {
				clearInterval(this._intervalId);
			}
		});
	}

	getProgressBarClass_(percentage) {
		return (percentage >= 100) ? 'progress-bar-success' : '';
	}

	hideLoadingIndicator_() {
		this.getElement_('ProgressIndicator').classList.add('hide');
	}

	onProgressComplete_() {
		this.clearInterval_();
		this.hideLoadingIndicator_();
	}

	showLoadingIndicator_() {
		this.getElement_('ProgressIndicator').classList.remove('hide');
	}

	updateProgressBar_(progress) {
		this.progressbar.barClass = this.getProgressBarClass_(progress),
		this.progressbar.label = progress + '%';
		this.progressbar.value = progress;
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