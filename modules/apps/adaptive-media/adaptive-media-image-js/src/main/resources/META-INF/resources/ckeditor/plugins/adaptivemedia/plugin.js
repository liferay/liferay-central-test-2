(function() {
	var Lang = AUI().Lang;

	var STR_ADAPTIVE_MEDIA_RETURN_TYPE = 'com.liferay.adaptative.media.item.selector.AdaptativeMediaURLItemSelectorReturnType';

	var STR_FILE_ENTRY_RETURN_TYPE = 'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType';

	var TPL_PICTURE_TAG = '<picture>{sources}<img src="{defaultSrc}"></picture>';

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
							var mediaText = source.attributes.reduce(
								function(previous, current) {
									var propertyName = Object.getOwnPropertyNames(current);

									var value = '(' + propertyName + ':' + current[propertyName] + ')';

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
							sources: sources
						}
					);

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