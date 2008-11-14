Liferay.ControlPanel = {
	init: function() {
		var instance = this;

		instance._panelMenu = jQuery('#p_p_id_87_');
		instance._createTogglers();
	},

	_createTogglers: function() {
		var instance = this;

		var panelCategories = jQuery('.panel-page-category li h2');

		panelCategories.each(
			function(i) {
				var heading = jQuery(this);

				heading.prepend('<a class="toggle-menu" href="javascript: ;"></a>');
			}
		);

		var toggleButtons = panelCategories.find('.toggle-menu');

		toggleButtons.parent().click(
			function(event) {
				var data = {};

				var panel = jQuery(this).parents('.panel-page-category:first');
				var panelId = panel.attr('id');
				var state = 'open';

				panel.toggleClass('open').toggleClass('closed');

				if (panel.is('.closed')) {
					state = 'closed';
				}

				data[panelId] = state;

				jQuery.ajax(
					{
						url: themeDisplay.getPathMain() + '/portal/session_click',
						data: data
					}
				);
			}
		);
	}
};

Liferay.Util.portletTitleEdit = function() {
};

jQuery(
	function () {
		Liferay.ControlPanel.init();
	}
);