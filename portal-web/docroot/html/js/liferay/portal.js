Liferay.Portal = {};

Liferay.Portal.Tabs = {
	show: function() {}
};

AUI().ready(
	function(A) {
		Liferay.Portal.Tabs.show = function(namespace, names, id) {
			var tab = AUI().one('#' + namespace + id + 'TabsId');
			var panel = AUI().one('#' + namespace + id + 'TabsSection');

			if (tab) {
				tab.radioClass('aui-selected');
			}

			if (panel) {
				panel.show();
			}

			var index = A.Array.indexOf(names, id);

			names.splice(index, 1);

			for (var i = 0; i < names.length; i++) {
				el = AUI().one('#' + namespace + names[i] + 'TabsSection');

				if (el) {
					el.hide();
				}
			}
		};
	}
);

(function() {
	var elementsCache = {};

	AUI().ready(
		'tooltip',
		'simulate',
		function(A) {

			Liferay.Portal.ToolTip = {
				show: function(obj, text) {
					var cachedTooltip = Liferay.Portal.ToolTip.cached;

					if (cachedTooltip) {
						var trigger = cachedTooltip.get('trigger');
						var newElement = trigger.indexOf(obj) == -1;

						if (newElement) {
							cachedTooltip.set('trigger', obj);
							cachedTooltip.set('bodyContent', text);
							cachedTooltip.show();
						}

						cachedTooltip.refreshAlign();
					}
				}
			};

			Liferay.Portal.ToolTip.cached = new A.Tooltip({
				trigger: '.liferay-tooltip',
				zIndex: 10000
			})
			.render();
		}
	);
})();