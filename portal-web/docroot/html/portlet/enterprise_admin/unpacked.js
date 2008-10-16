Liferay.EnterpriseAdmin = {
}

Liferay.EnterpriseAdmin.FormNavigator = new Class({
	initialize: function(options) {
		var instance = this;

		instance._container = jQuery(options.container);

		instance._navigation = instance._container.find('.form-navigation');
		instance._sections = instance._container.find('.form-section');

		instance._navigation.find('li a').click(
			function(event) {
				var li = jQuery(this.parentNode);

				if (!li.is('.selected')) {
					instance._revealSection(this.href, li);

					var currentSection = this.href.split('#');

					if (currentSection[1]) {
						location.hash = currentSection[1];
					}
				}

				return false;
			}
		);

		instance._revealSection(location.hash);

		instance._container.find('input, select, textarea').change(
			function(event) {
				instance._trackChanges(this);
			}
		);
	},

	_revealSection: function(id, currentNavItem) {
		var instance = this;

		var li = currentNavItem || instance._navigation.find('[@href$=' + id + ']').parent();

		id = id.split('#');

		if (!id[1]) {
			return;
		}

		id = '#' + id[1];

		var section = jQuery(id);

		instance._navigation.find('.selected').removeClass('selected');
		instance._sections.removeClass('selected');

		section.addClass('selected');
		li.addClass('selected');
	},

	_trackChanges: function(el) {
		var instance = this;

		var currentSection = jQuery(el).parents('.form-section:first').attr('id');

		var currentSectionLink = jQuery('#' + currentSection + 'Link');
		currentSectionLink.parent().addClass('section-modified');
	}
});