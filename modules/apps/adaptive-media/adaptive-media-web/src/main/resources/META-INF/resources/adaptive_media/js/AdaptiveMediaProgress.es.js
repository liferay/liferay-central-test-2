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
		let barClass = 'progress-bar';

		if (this.percentage == 100) {
			barClass += ' progress-bar-success';
		}

		this.progressbar = new ProgressBar(
			{
				barClass: barClass,
				label: this.percentage + '%',
				value: this.percentage
			},
			this.getElement_('ProgressBar')
		);
	}

	startProgress(backgroundTaskUrl) {
		Ajax.request(backgroundTaskUrl);

		this._intervalId = setInterval(this.getPercentage_(), 1000);

		this.showLoadingIndicator_();
	}

	getPercentage_() {
		Ajax.request(this.percentageUrl).then((xhr) => {
			let json = JSON.parse(xhr.response);

			let percentage = parseInt(json.percentage);

			this.updateProgressBar_(percentage);

			if (percentage === 100) {
				this.onProgressComplete_();
			}
		});
	}

	getElement_(elementId) {
		let finalId = this.id + elementId;
		return this.one('#' + finalId);
	}

	hideLoadingIndicator_() {
		this.getElement_('ProgressIndicator').classList.add('hide');
	}

	onProgressComplete_() {console.log('STOP!!!');
		clearInterval(this._intervalId);
		this.hideLoadingIndicator_();
	}

	showLoadingIndicator_() {
		this.getElement_('ProgressIndicator').classList.remove('hide');
	}

	updateProgressBar_(value) {
		this.progressbar.value = value;
	}
}

/**
 * AdaptiveMedia State definition.
 * @ignore
 * @static
 * @type {!Object}
 */
AdaptiveMediaProgress.STATE = {
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