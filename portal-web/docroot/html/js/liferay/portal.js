Liferay.Portal = {};

Liferay.Portal.Tabs = {
	show: function(namespace, names, id) {
		var tab = jQuery('#' + namespace + id + 'TabsId');
		var panel = jQuery('#' + namespace + id + 'TabsSection');

		tab.siblings().removeClass('aui-selected');
		tab.addClass('aui-selected');

		panel.show();
		panel.removeClass('aui-helper-hidden');

		var index = names.indexOf(id);
		names.splice(index, 1);

		for (var i = 0; i < names.length; i++) {
			el = jQuery('#' + namespace + names[i] + 'TabsSection');
			el.addClass('aui-helper-hidden');
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