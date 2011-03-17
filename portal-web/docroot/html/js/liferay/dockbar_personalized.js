AUI().add(
	'liferay-dockbar-personalized',
	function(A) {
		var Dockbar = Liferay.Dockbar;

		Dockbar._initPersonalizedView = function() {
			var instance = this;

			var namespace = instance._namespace;

			var togglePersonalizedView = A.one('#' + namespace + 'togglePersonalizedView');

			if (togglePersonalizedView) {
				togglePersonalizedView.on(
					'click',
					function(event) {
						var disabled = togglePersonalizedView.hasClass('false');

						A.io.request(
							themeDisplay.getPathMain() + '/portal/update_layout',
							{
								data: {
									cmd: 'toggle_personalized_view',
									personalized_view: disabled
								},
								on: {
									success: function(event, id, obj) {
										window.location.reload();
									}
								}
							}
						);
					}
				);

				if (togglePersonalizedView.hasClass('has-submenu')) {
					instance.addMenu(
						{
							boundingBox: '#' + namespace + 'togglePersonalizedViewContainer',
							name: 'personalizedView',
							trigger: '#' + namespace + 'togglePersonalizedView'
						}
					);
				}
			}
		};

		A.after(Dockbar._initPersonalizedView, Dockbar, '_init');
	},
	'',
	{
		requires: ['liferay-dockbar']
	}
);