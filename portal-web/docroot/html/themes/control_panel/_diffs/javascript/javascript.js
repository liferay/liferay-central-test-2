Liferay.ControlPanel = {
	init: function() {
		var instance = this;

		instance._panelMenu = jQuery('#p_p_id_87_');
		instance._createTogglers();
		instance._createSortables();
	},

	_createTogglers: function() {
		var instance = this;

		var panelCategories = jQuery('.panel-category li h2');

		panelCategories.each(
			function(i) {
				var heading = jQuery(this);

				heading.prepend('<a class="toggle-menu" href="javascript: ;"></a>');
				heading.append('<a class="drag-handle" href="javascript: ;"></a>');
			}
		);

		var toggleButtons = panelCategories.find('.toggle-menu');

		toggleButtons.click(
			function(event) {
				var data = {};

				var panel = jQuery(this).parents('.panel-category:first');
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
	},

	_createSortables: function() {
		var instance = this;

		var panelMenu = instance._panelMenu;
		var panelCats = panelMenu.find('.portal-add-content');

		panelCats.sortable(
			{
				handle: '.drag-handle',
				items: '.panel-category',
				axis: 'y'
			}
		);
	}
};

jQuery(
	function () {
		Liferay.ControlPanel.init();
	}
);