(function() {
	CKEDITOR.plugins.add(
	'bbcode',
		{
			init: function(editor) {
				var instance = this;

				var path = instance.path;

				var dependencies = [
					CKEDITOR.getUrl(path + 'bbcode_data_processor.js'),
					CKEDITOR.getUrl(path + 'bbcode_parser.js')
				];

				CKEDITOR.scriptLoader.load(
					dependencies,
					function() {
						var bbcodeDataProcessor = CKEDITOR.plugins.get('bbcode_data_processor');

						bbcodeDataProcessor.init(editor);
					}
				);

				var stylePre = new CKEDITOR.style(
					{
						element: 'pre'
					}
				);

				stylePre._.enterMode = editor.config.enterMode;

				editor.ui.addButton( 'Code',
				{
					label : 'Code',
					icon: CKEDITOR.config.imagesPath + 'code.png',
					click : function() {
						editor.focus();
						editor.fire( 'saveSnapshot' );

						var elementPath = new CKEDITOR.dom.elementPath( editor.getSelection().getStartElement() );

						stylePre[ stylePre.checkActive( elementPath ) ? 'remove' : 'apply' ]( editor.document );

						setTimeout(
							function()
							{
								editor.fire( 'saveSnapshot' );
							},
							0
						);
					}
				});
			}
		}
	);
})();
