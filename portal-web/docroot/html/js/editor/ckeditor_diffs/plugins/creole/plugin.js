(function() {
	CKEDITOR.plugins.add( 'creole',
	{
		init: function(editor) {
			var dependencies = [
				CKEDITOR.getUrl(this.path + 'creole_data_processor.js'),
				CKEDITOR.getUrl(this.path + 'creole_parser.js')
			];

			CKEDITOR.scriptLoader.load(dependencies, function(){
				var creoleDataProcessor = CKEDITOR.plugins.get('creole_data_processor');

				creoleDataProcessor.init(editor);
			});
		}
	});
}());
