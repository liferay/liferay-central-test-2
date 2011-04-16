AUI().add(
	'liferay-form-navigator',
	function(A) {
		var AUI_HELPER_HIDDEN_ACCESSIBLE = 'aui-helper-hidden-accessible';

		var FORM_SECTION = '.form-section';

		var LIST_ITEM_SELECTED = 'li.selected';

		var SECTION_ERROR = 'section-error';

		var SELECTED = 'selected';

		var FormNavigator = function(options) {
			var instance = this;

			instance._container = A.one(options.container);

			Liferay.on(
				'form:formRegistered',
				A.bind(instance._onFormRegistered, instance, options)
			);

			instance._navigation = instance._container.one('.form-navigator');
			instance._sections = instance._container.all(FORM_SECTION);

			if (instance._navigation) {
				instance._navigation.delegate(
					'click',
					A.bind(instance._onClick, instance),
					'li a'
				);
			}

			if (options.modifiedSections) {
				instance._modifiedSections = A.all('[name=' + options.modifiedSections+ ']');

				if (!instance._modifiedSections) {
					instance._modifiedSections = A.Node.create('<input name="' + options.modifiedSections + '" type="hidden" />');
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

			instance._revealSection(location.href);

			A.on(
				'formNavigator:trackChanges',
				A.bind(instance._trackChanges, instance)
			);

			var inputs = instance._container.all('input, select, textarea');

			if (inputs) {
				inputs.on(
					'change',
					function(event) {
						A.fire('formNavigator:trackChanges', event.target);
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
			_addModifiedSection: function (section) {
				var instance = this;

				if (A.Array.indexOf(section, instance._modifiedSectionsArray) == -1) {
					instance._modifiedSectionsArray.push(section);
				}
			},

			_getId: function(id) {
				var instance = this;

				id = id || '';

				if (id.indexOf('#') > -1) {
					id = id.split('#')[1] || '';

					id = id.replace(instance._hashKey, '');
				}
				else if (id.indexOf('historyKey=') > -1) {
					id = id.match(/historyKey=([^&#]+)/);
					id = id && id[1];
				}
				else {
					id = '';
				}

				return id;
			},

			_onClick: function(event) {
				var instance = this;

				event.preventDefault();

				var target = event.currentTarget;

				var li = target.get('parentNode');

				if (li && !li.test('.selected')) {
					instance._revealSection(target.attr('href'), li);

					var currentSection = target.attr('href').split('#');

					if (currentSection[1]) {
						A.later(0, instance, instance._updateHash, [currentSection[1]]);
					}
				}
			},

			_onFormRegistered: function(options, event) {
				var instance = this;

				if (event.formId === options.formId) {
					var formValidator = event.form.formValidator;

					instance._formValidator = formValidator;

					formValidator.on('errorField', function(event){
						instance._updateSectionStatus();
					});

					formValidator.on('validField', function(event){
						instance._updateSectionStatus();
					});
				}
			},

			_revealSection: function(id, currentNavItem) {
				var instance = this;

				id = instance._getId(id);

				if (id) {
					id = id.charAt(0) != '#' ? '#' + id : id;

					var li = currentNavItem || instance._navigation.one('[href$=' + id + ']').get('parentNode');

					id = id.split('#');

					if (!id[1]) {
						return;
					}

					Liferay.fire('enterpriseAdmin:reveal' + id[1]);

					id = '#' + id[1];

					var section = A.one(id);
					var selected = instance._navigation.one(LIST_ITEM_SELECTED);

					if (selected) {
						selected.removeClass(SELECTED);
					}

					li.addClass(SELECTED);

					instance._sections.removeClass(SELECTED).addClass(AUI_HELPER_HIDDEN_ACCESSIBLE);

					if (section) {
						section.addClass(SELECTED).removeClass(AUI_HELPER_HIDDEN_ACCESSIBLE);
					}
				}
			},

			_trackChanges: function(el) {
				var instance = this;

				var currentSection = A.one(el).ancestor(FORM_SECTION).attr('id');

				var currentSectionLink = A.one('#' + currentSection + 'Link');

				if (currentSectionLink) {
					currentSectionLink.get('parentNode').addClass('section-modified');
				}

				instance._addModifiedSection(currentSection);
			},

			_updateHash: function(section) {
				var instance = this;

				location.hash = instance._hashKey + section;
			},

			_updateSectionStatus: function() {
				var instance = this;

				var selectedSectionNode = instance._navigation.one(LIST_ITEM_SELECTED);

				if (selectedSectionNode) {
					if (instance._formValidator.hasErrors()) {
						var hasOwnProperty = Object.prototype.hasOwnProperty;

						var errors = instance._formValidator.errors;

						for (var item in errors) {
							if (hasOwnProperty.call(errors, item)) {
								var section = A.one('#' + item).ancestor(FORM_SECTION);

								if ( section && section.hasClass(SELECTED)) {
									selectedSectionNode.addClass(SECTION_ERROR);
									return;
								}

								selectedSectionNode.removeClass(SECTION_ERROR);
							}
						}
					}
					else {
						 selectedSectionNode.removeClass(SECTION_ERROR);
					}
				}
			},

			_hashKey: '_LFR_FN_'
		};

		Liferay.FormNavigator = FormNavigator;
	},
	'',
	{
		requires: ['aui-base']
	}
);