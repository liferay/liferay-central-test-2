import Component from 'metal-component/src/Component';
import Soy from 'metal-soy/src/Soy';

import core from 'metal/src/core';
import dom from 'metal-dom/src/dom';
import { CancellablePromise } from 'metal-promise/src/promise/Promise';
import Dropdown from 'metal-dropdown/src/Dropdown';

import ImageEditorHistoryEntry from './ImageEditorHistoryEntry.es';
import ImageEditorLoading from './ImageEditorLoading.es';

import templates from './ImageEditor.soy';

/**
 * ImageEditor
 *
 * This class bootstraps all the necessary parts of an image editor. It only controls
 * the state and history of the editing process, orchestrating how the different parts
 * of the application work.
 *
 * All image processing is delegated to the different image editor capability implementations. The
 * editor provides:
 * - A common way of exposing the functionality.
 * - Some registration points which can be used by the image editor capability implementors
 * to provide UI controls.
 */
class ImageEditor extends Component {
	/**
	 * @inheritDoc
	 */
	constructor(opt_config) {
		super(opt_config);

		/**
	 	 * This index points to the current state in the history.
	 	 *
		 * @type {Number}
		 * @protected
		 */
		this.historyIndex_ = 0;

		/**
		 * History of the different image states during edition. Every
		 * entry entry represents a change to the image on top of the
		 * previous one.
		 * - History entries are objects with
		 *     - url (optional): the url representing the image
		 *     - data: the ImageData object of the image
	 	 *
		 * @type {Array.<Object>}
		 * @protected
		 */
		this.history_ = [
			new ImageEditorHistoryEntry(
				{
					url: this.image
				}
			)
		];

		// Polyfill svg usage for lexicon icons
		svg4everybody(
			{
				polyfill: true
			}
		);

		// Load the first entry imageData and render it on the app.
		this.history_[0].getImageData()
			.then((imageData) => this.syncImageData_(imageData));
	}

	/**
	 * Accepts the current changes applied by the active control and creates
	 * a new entry in the history stack. Doing this will wipe out any
	 * stale redo states.
	 */
	accept() {
		let selectedControl = this.components[this.id + '_selected_control_' + this.selectedControl.variant];

		this.history_[this.historyIndex_].getImageData()
			.then((imageData) => selectedControl.process(imageData))
			.then((imageData) => this.createHistoryEntry_(imageData))
			.then(() => this.syncHistory_())
			.then(() => {
				this.selectedControl = null;
				this.selectedTool = null;
			});
	}

	/**
	 * Creates a new history entry state.
	 *
	 * @param  {ImageData} imageData The ImageData of the new image.
	 * @protected
	 */
	createHistoryEntry_(imageData) {
		// Push new state and discard stale redo states
		this.historyIndex_++;
		this.history_.length = this.historyIndex_ + 1;
		this.history_[this.historyIndex_] = new ImageEditorHistoryEntry({data: imageData});

		return CancellablePromise.resolve();
	}

	/**
	 * Discards the current changes applied by the active control and reverts
	 * the image to its state before the control activation.
	 */
	discard() {
		this.selectedControl = null;
		this.selectedTool = null;
		this.syncHistory_();
	}

	/**
	 * Retrieves the editor canvas DOM node.
	 *
	 * @return {Element} The canvas element.
	 */
	getEditorCanvas() {
		return this.element.querySelector('.lfr-image-editor-image-container canvas');
	}

	/**
	 * Retrieves the ImageData representation of the current image.
	 *
	 * @return {CancellablePromise} A promise that will resolve with the image data.
	 */
	getEditorImageData() {
		return this.history_[this.historyIndex_].getImageData();
	}

	/**
	 * Updates the image back to a previously undone state in the history.
	 * Redoing an action recovers the undone image changes and enables the
	 * undo stack in case the user wants to undo the changes again.
	 */
	redo() {
		this.historyIndex_++;
		this.syncHistory_();
	}

	/**
	 * Selects a control and starts the edition phase for it.
	 *
	 * @param  {MouseEvent} event
	 */
	requestEditorEdit(event) {
		let controls = this.imageEditorCapabilities.tools.reduce(
			(prev, curr) => prev.concat(curr.controls), []);

		let target = event.delegateTarget || event.currentTarget;
		let targetControl = target.getAttribute('data-control');
		let targetTool = target.getAttribute('data-tool');

		this.syncHistory_()
			.then(() => {
				this.selectedControl = controls.filter(tool => tool.variant === targetControl)[0];
				this.selectedTool = targetTool;
			});
	}

	/**
	 * Queues a request for a preview process of the current image by the
	 * currently selected control.
	 */
	requestEditorPreview() {
		let selectedControl = this.components[this.id + '_selected_control_' + this.selectedControl.variant];

		this.history_[this.historyIndex_].getImageData()
			.then((imageData) => selectedControl.preview(imageData))
			.then((imageData) => this.syncImageData_(imageData));

		this.components.loading.show = true;
	}

	/**
	 * Discards all changes and restores the original state of the image.
	 * Unlike the undo/redo methods, reset will wipe out all the history.
	 */
	reset() {
		this.historyIndex_ = 0;
		this.history_.length = 1;
		this.syncHistory_();
	}

	/**
	 * Syncs the image and history values after changes to the
	 * history stack.
	 *
	 * @protected
	 */
	syncHistory_() {
		return new CancellablePromise((resolve, reject) => {
			this.history_[this.historyIndex_].getImageData()
				.then((imageData) => {
					this.syncImageData_(imageData);

					this.history = {
						canRedo: this.historyIndex_ < this.history_.length - 1,
						canReset: this.history_.length > 1,
						canUndo: this.historyIndex_ > 0
					};

					resolve();
				});
			});
	}

	/**
	 * Updates the image data showed in the editable area
	 *
	 * @protected
	 * @param  {ImageData} imageData The new ImageData value to show on the editor
	 */
	syncImageData_(imageData) {
		let width = imageData.width;
		let height = imageData.height;

		let aspectRatio = width / height;

		let offscreenCanvas = document.createElement('canvas');
		offscreenCanvas.width = width;
		offscreenCanvas.height = height;

		let offscreenContext = offscreenCanvas.getContext('2d');
		offscreenContext.clearRect(0, 0, width, height);
		offscreenContext.putImageData(imageData, 0, 0);

		let canvas = this.element.querySelector('.lfr-image-editor-image-container canvas');

		let boundingBox = dom.closest(this.element, '#main-content');
		let availableWidth = boundingBox.offsetWidth;
		let availableHeight = boundingBox.offsetHeight - 142 - 40;
		let availableAspectRatio = availableWidth / availableHeight;

		if (availableAspectRatio > 1) {
			canvas.height = availableHeight;
			canvas.width = aspectRatio * availableHeight;
		} else {
			canvas.width = availableWidth;
			canvas.height = availableWidth / aspectRatio;
		}

		let context = canvas.getContext('2d');
		context.clearRect(0, 0, canvas.width, canvas.height);
		context.drawImage(offscreenCanvas, 0, 0, width, height, 0, 0, canvas.width, canvas.height);

		canvas.style.width = canvas.width + 'px';
		canvas.style.height = canvas.height + 'px';

		this.components.loading.show = false;
	}

	/**
	 * Reverts the image to the previous state in the history. Undoing
	 * an action brings back the previous version of the image and enables
	 * the redo stack in case the user wants to reapply the change again.
	 */
	undo() {
		this.historyIndex_--;
		this.syncHistory_();
	}
}

// Register component
Soy.register(ImageEditor, templates);

export default ImageEditor;