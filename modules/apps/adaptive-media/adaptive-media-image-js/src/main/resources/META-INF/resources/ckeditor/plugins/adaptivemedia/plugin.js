(function() {
	var STR_ADAPTIVE_MEDIA_RETURN_TYPE = 'com.liferay.item.selector.criteria.AdaptativeMediaURLItemSelectorReturnType';

	var STR_FILE_ENTRY_RETURN_TYPE = 'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType';

	CKEDITOR.plugins.add(
		'adaptivemedia',
		{
			init: function(editor) {
				var instance = this;

				instance._bindEvent(editor);
			},

			_bindEvent: function(editor) {
				var instance = this;

				editor.on(
					'beforeCommandExec',
					function(event) {
						if (event.data.name === 'imageselector') {
							event.removeListener();

							event.stop();

							var onSelectedImageChangeFn = AUI._.bind(
								instance._onSelectedImageChange,
								instance,
								editor
							);

							editor.execCommand('imageselector', onSelectedImageChangeFn);

							instance._bindEvent(editor);
						}
					}
				);
			},

			_getPictureElement: function(selectedItem) {
				var itemValue = JSON.parse(selectedItem.value);

				return CKEDITOR.dom.element.createFromHtml('<picture><img src="' + itemValue.url + '"></picture>');
			},

			_onSelectedImageChange: function(editor, imageSrc, selectedItem) {
				var instance = this;

				var el;

				if (selectedItem.returnType === STR_ADAPTIVE_MEDIA_RETURN_TYPE) {
					el = instance._getPictureElement(selectedItem);
				}
				else {
					el = CKEDITOR.dom.element.createFromHtml('<img src="' + imageSrc + '">');
				}

				editor.insertElement(el);

				editor.setData(editor.getData());
			}
		}
	);
})();