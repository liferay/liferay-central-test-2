Liferay.EnterpriseAdmin = {
}

Liferay.EnterpriseAdmin.Addresses = {
	getCountries: function(callback) {
		Liferay.Service.Portal.Country.getCountries(
			{
				active: true
			},
			callback
		);
	},

	getRegions: function(callback, selectKey) {
		Liferay.Service.Portal.Region.getRegions(
			{
				countryId: Number(selectKey),
				active: true
			},
			callback
		);
	}
};

Liferay.EnterpriseAdmin.FormNavigator = new Expanse.Class(
	{
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

			if (options.modifiedSections) {
				instance._modifiedSections = jQuery('[name=' + options.modifiedSections+ ']');

				if (!instance._modifiedSections.length) {
					instance._modifiedSections = jQuery('<input name="' + options.modifiedSections+ '" type="hidden" />')
					instance._container.append(instance._modifiedSections);
				}
			}
			else {
				instance._modifiedSections = jQuery([]);
			}

			if (options.defaultModifiedSections) {
				instance._modifiedSectionsArray = options.defaultModifiedSections;
			}
			else {
				instance._modifiedSectionsArray = [];
			}

			instance._revealSection(location.hash);

			instance._container.find('input, select, textarea, .modify-link').change(
				function(event) {
					instance._trackChanges(this);
				}
			);

			Liferay.bind(
				'submitForm',
				function(event, data) {
					var form = jQuery(data.form);

					instance._modifiedSections.val(instance._modifiedSectionsArray.join(','));
				}
			);
		},

		_revealSection: function(id, currentNavItem) {
			var instance = this;

			var li = currentNavItem || instance._navigation.find('[href$=' + id + ']').parent();

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

			instance._addModifiedSection(currentSection);
		},

		_addModifiedSection: function (section) {
			var instance = this;

			if (jQuery.inArray(section, instance._modifiedSectionsArray) == -1) {
				instance._modifiedSectionsArray.push(section);
			}
		}
	}
);