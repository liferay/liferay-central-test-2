Liferay.Portal = {};

Liferay.Portal.Tabs = {
	show: function(namespace, names, id) {
		var callback = function(A) {
			var tab = A.one('#' + namespace + id + 'TabsId');
			var panel = A.one('#' + namespace + id + 'TabsSection');

			if (tab) {
				tab.radioClass('aui-selected');
				tab.radioClass('aui-state-active');
				tab.radioClass('aui-tab-active');
				tab.radioClass('current');
			}

			if (panel) {
				panel.show();
			}

			var index = A.Array.indexOf(names, id);

			names.splice(index, 1);

			for (var i = 0; i < names.length; i++) {
				el = A.one('#' + namespace + names[i] + 'TabsSection');

				if (el) {
					el.hide();
				}
			}
		};

		var A = AUI();

		if (A.one) {
			callback(A);
		}
		else {
			AUI().use('node', callback);
		}
	}
};

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