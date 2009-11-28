AUI().add(
	'liferay-enterprise-admin',
	function(A) {
		var Addresses = {
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

		var FormNavigator = function(options) {
			var instance = this;

			instance._container = A.one(options.container);

			instance._navigation = instance._container.all('.form-navigation');
			instance._sections = instance._container.all('.form-section');

			if (instance._navigation) {
				instance._navigation.all('li a').on(
					'click',
					function(event) {
						var target = event.target;
						var li = target.get('parentNode');

						if (li && !li.test('.selected')) {
							instance._revealSection(target.attr('href'), li);

							var currentSection = target.attr('href').split('#');

							if (currentSection[1]) {
								location.hash = instance._hashKey + currentSection[1];
							}
						}

						return false;
					}
				);
			}

			if (options.modifiedSections) {
				instance._modifiedSections = A.all('[name=' + options.modifiedSections+ ']');

				if (!instance._modifiedSections) {
					instance._modifiedSections = A.Node.create('<input name="' + options.modifiedSections+ '" type="hidden" />')
					instance._container.append(instance._modifiedSections);
				}
			}
			else {
				instance._modifiedSections = null;
			}

			if (options.defaultModifiedSections) {
				instance._modifiedSectionsArray = options.defaultModifiedSections;
			}
			else {
				instance._modifiedSectionsArray = [];
			}

			instance._revealSection(location.hash);

		    A.on(
				'enterpriseAdmin:trackChanges',
				function(element) {
		        	instance._trackChanges(element);
		    	}
			);

			var inputs = instance._container.all('input, select, textarea');

			if (inputs) {
				inputs.on(
					'change',
					function(event) {
						A.fire('enterpriseAdmin:trackChanges', event.target);
					}
				);
			}

			Liferay.on(
				'submitForm',
				function(event, data) {
					if (instance._modifiedSections) {
						instance._modifiedSections.val(instance._modifiedSectionsArray.join(','));
					}
				}
			);
		};

		FormNavigator.prototype = {
			_revealSection: function(id, currentNavItem) {
				var instance = this;

				id = id.replace(instance._hashKey, '');

				var li = currentNavItem || instance._navigation.one('[href$=' + id + ']').get('parentNode');

				id = id.split('#');

				if (!id[1]) {
					return;
				}

				id = '#' + id[1];

				var section = A.one(id);
				var selected = instance._navigation.all('.selected');

				if (selected) {
					selected.removeClass('selected');
				}

				instance._sections.removeClass('selected');

				if (section) {
					section.addClass('selected');
				}

				li.addClass('selected');
			},

			_trackChanges: function(el) {
				var instance = this;

				var currentSection = A.get(el).ancestor('.form-section').attr('id');

				var currentSectionLink = A.one('#' + currentSection + 'Link');

				if (currentSectionLink) {
					currentSectionLink.get('parentNode').addClass('section-modified');
				}

				instance._addModifiedSection(currentSection);
			},

			_addModifiedSection: function (section) {
				var instance = this;

				if (A.Array.indexOf(section, instance._modifiedSectionsArray) == -1) {
					instance._modifiedSectionsArray.push(section);
				}
			},

			_hashKey: '_LFR_FN_'
		};

		Liferay.EnterpriseAdmin = {
			Addresses: Addresses,
			FormNavigator: FormNavigator
		};
	}
);