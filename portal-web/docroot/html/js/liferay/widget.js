window.Liferay = window.Liferay || {};

Liferay.Widget = function(options) {
	options = options || {};
	
	var id = options.id || '_Liferay_widget' + (Math.ceil(Math.random() * (new Date).getTime()));
	var height = options.height || '100%';
	var url = options.url || 'http://liferay.com/';
	var width = options.width || '100%';
	
	var html = '<iframe border="0" height="' + height + '" src="' + url + '" width="' + width + '"></iframe>';
	
	document.write(html);
}