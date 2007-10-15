Liferay.Menu = new Class({
	initialize: function(params) {
		var instance = this;

		instance._button = jQuery(params.button, params.context || document);
		instance._menu = instance._button.find('ul:first');
		instance._trigger = instance._button.find(params.trigger);

		if (instance._menu.length) {
			instance._run();
		}
	},

	_run: function() {
		var instance = this;
		
		var lastLi = instance._trigger.find('ul:first li:last-child');
		if (lastLi.length) {
			lastLi.addClass('last');
	
			instance._trigger.hover(
				function() {
					var trigger = jQuery(this);
					trigger.parent().addClass('visible');
				},
				function() {
					var trigger = jQuery(this);
					trigger.parent().removeClass('visible');
				}
			);
		} else {
			instance._button.hide();
		}
	}
});