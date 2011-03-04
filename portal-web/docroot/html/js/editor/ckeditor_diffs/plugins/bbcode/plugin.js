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
			}
		}
	);
})();
