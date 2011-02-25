(function() {
	CKEDITOR.plugins.add(
	'creole',
		{
			init: function(editor) {
				var instance = this;

				var dependencies = [
					CKEDITOR.getUrl(instance.path + 'creole_data_processor.js'),
					CKEDITOR.getUrl(instance.path + 'creole_parser.js')
				];

				CKEDITOR.scriptLoader.load(
					dependencies,
					function(){
						var creoleDataProcessor = CKEDITOR.plugins.get('creole_data_processor');

						creoleDataProcessor.init(editor);
					}
				);
			}
		}
	);
})();
