AUI.add(
	'liferay-ddm-soy-template-util',
	function(A) {
		var AObject = A.Object;

		var SoyTemplateUtil = {
			getTemplateRenderer: function(templateNamespace) {
				var instance = this;

				var renderer = AObject.getValue(window, templateNamespace.split('.'));

				if (!renderer) {
					throw new Error('Form template renderer is not defined: "' + templateNamespace);
				}

				return renderer;
			}
		};

		Liferay.namespace('DDM').SoyTemplateUtil = SoyTemplateUtil;
	},
	'',
	{
		requires: []
	}
);