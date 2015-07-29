/* global React, AlloyEditor */

(function() {
	'use strict';

	var Util = Liferay.Util;

	var ButtonImage = React.createClass(
		{
			displayName: 'ButtonImage',

			propTypes: {
				editor: React.PropTypes.object.isRequired,
				imageTPL: React.PropTypes.string
			},

			getDefaultProps: function() {
				return {
					imageTPL: new CKEDITOR.template('<img src="{src}" />')
				};
			},

			statics: {
				key: 'image'
			},

			render: function() {
				return React.createElement(
					'button',
					{
						className: 'alloy-editor-button',
						'data-type': 'button-image',
						onClick: this._handleClick,
						tabIndex: this.props.tabIndex
					},
					React.createElement(
						'span',
						{
							className: 'alloy-editor-icon-image'
						}
					)
				);
			},

			_destroyItemSelectorDialog: function() {
				var instance = this;

				if (instance._itemSelectorDialog) {
					setTimeout(
						function() {
							instance._itemSelectorDialog.destroy();
						},
						0
					);
				}
			},

			_handleClick: function() {
				var instance = this;

				var editor = this.props.editor.get('nativeEditor');

				var eventName = editor.name + 'selectDocument';

				if (instance._itemSelectorDialog) {
					instance._itemSelectorDialog.open();
				}
				else {
					AUI().use(
						'liferay-item-selector-dialog',
						function(A) {
							var itemSelectorDialog = new A.LiferayItemSelectorDialog(
								{
									after: {
										selectedItemChange: A.bind('_onSelectedItemChange', instance)
									},
									eventName: eventName,
									url: editor.config.filebrowserImageBrowseUrl
								}
							);

							itemSelectorDialog.open();

							instance._itemSelectorDialog = itemSelectorDialog;
						}
					);
				}
			},

			_onSelectedItemChange: function(event) {
				var instance = this;

				var editor = instance.props.editor.get('nativeEditor');

				var eventName = editor.name + 'selectDocument';

				var selectedItem = event.newVal;

				if (selectedItem) {
					Util.getWindow(eventName).onceAfter(
						'visibleChange',
						function() {
							var el = CKEDITOR.dom.element.createFromHtml(
								instance.props.imageTPL.output(
									{
										src: selectedItem.value
									}
								)
							);

							editor.insertElement(el);
						}
					);
				}

				instance._destroyItemSelectorDialog();
			}
		}
	);

	AlloyEditor.Buttons[ButtonImage.key] = AlloyEditor.ButtonImage = ButtonImage;
}());