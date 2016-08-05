(function() {
	var STR_ADAPTIVE_MEDIA_RETURN_TYPE = 'com.liferay.item.selector.criteria.AdaptiveMediaItemSelectorReturnType';

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

							event.cancel();

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
				var pictureEl;

				try {
					var itemValue = JSON.parse(selectedItem.value);

					var pictureHtml = '<picture>';

					itemValue.sources.forEach(
						function(source) {
							var mediaText = '';

							source.media.forEach(
								function(media, index) {
									if (index > 0) {
										mediaText += ' and ';
									}
									mediaText += '(' + media.key + ':' + media.value + ')';
								}
							);

							pictureHtml += '<source srcset="' + source.src + '" media="' + mediaText + '">';
						}
					);

					if (itemValue.defaultSrc) {
						pictureHtml += '<img src="' + itemValue.defaultSrc + '">';
					}

					pictureHtml += '</picture>';

					pictureEl = CKEDITOR.dom.element.createFromHtml(pictureHtml);
				}
				catch (e) {
				}

				return pictureEl;
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