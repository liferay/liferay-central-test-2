(function() {
	var Lang = AUI().Lang;

	var STR_ADAPTIVE_MEDIA_URL_RETURN_TYPE = 'com.liferay.adaptive.media.image.item.selector.AdaptiveMediaImageURLItemSelectorReturnType';

	var STR_ADAPTIVE_MEDIA_FILE_ENTRY_RETURN_TYPE = 'com.liferay.adaptive.media.image.item.selector.AdaptiveMediaImageFileEntryItemSelectorReturnType';

	var TPL_PICTURE_TAG = '<picture data-fileEntryId="{fileEntryId}">{sources}<img src="{defaultSrc}"></picture>';

	var TPL_SOURCE_TAG = '<source srcset="{srcset}" media="{media}">';

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

					var sources = '';

					itemValue.sources.forEach(
						function(source) {
							var propertyNames = Object.getOwnPropertyNames(source.attributes);

							var mediaText = propertyNames.reduce(
								function(previous, current) {
									var value = '(' + current + ':' + source.attributes[current] + ')';

									return previous ? previous + ' and ' + value : value;
								},
								''
							);

							sources += Lang.sub(
								TPL_SOURCE_TAG,
								{
									media: mediaText,
									srcset: source.src
								}
							);
						}
					);

					var pictureHtml = Lang.sub(
						TPL_PICTURE_TAG,
						{
							defaultSrc: itemValue.defaultSource,
							fileEntryId: itemValue.fileEntryId,
							sources: sources
						}
					);

					pictureEl = CKEDITOR.dom.element.createFromHtml(pictureHtml);
				}
				catch (e) {
				}

				return pictureEl;
			},

			_getImgElement: function(imageSrc, selectedItem) {
				var imgEl = CKEDITOR.dom.element.createFromHtml('<img>');

				if (selectedItem.returnType === STR_ADAPTIVE_MEDIA_FILE_ENTRY_RETURN_TYPE) {
					var itemValue = JSON.parse(selectedItem.value);

					imgEl.setAttribute('src', itemValue.url);
					imgEl.setAttribute('data-fileEntryId', itemValue.fileEntryId);
				}
				else {
					imgEl.setAttribute('src', imageSrc);
				}

				return imgEl;
			},

			_onSelectedImageChange: function(editor, imageSrc, selectedItem) {
				var el;
				var instance = this;

				if (selectedItem.returnType === STR_ADAPTIVE_MEDIA_URL_RETURN_TYPE) {
					el = instance._getPictureElement(selectedItem);
				}
				else {
					el = instance._getImgElement(imageSrc, selectedItem);
				}

				editor.insertElement(el);

				editor.setData(editor.getData());
			}
		}
	);
})();