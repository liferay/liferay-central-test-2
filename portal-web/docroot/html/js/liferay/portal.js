Liferay.Portal = {};

Liferay.Portal.Tabs = {
	show: function(namespace, names, id) {
		var tab = jQuery('#' + namespace + id + 'TabsId');
		var panel = jQuery('#' + namespace + id + 'TabsSection');

		tab.siblings().removeClass('aui-selected');
		tab.addClass('aui-selected');

		panel.show();

		var index = names.indexOf(id);
		names.splice(index, 1);

		for (var i = 0; i < names.length; i++) {
			el = jQuery('#' + namespace + names[i] + 'TabsSection');
			el.hide();
		}
	}
};

(function() {
	var Dom = Alloy.Dom;
	var Event = Alloy.Event;

	var elementsCache = {};

	Liferay.Portal.ToolTip = {
		show: function(event, obj, text) {
			var instance = this;

			var itemId = Dom.generateId(obj);

			var container = instance._container;

			if (!elementsCache[itemId]) {
				if (!container) {
					container = new Alloy.Tooltip(
						{
							autodismissdelay: 10000,
							context: obj,
							hidedelay: 0,
							showdelay: 0
						}
					);

					container.render(document.body);

					Dom.addClass(container.element, 'portal-tool-tip');

					instance._container = container;
				}

				var context = container.cfg.getProperty('context') || [];

				context.push(obj);

				container.cfg.setProperty('context', context);
				container.doShow(event, obj);

				elementsCache[itemId] = obj;
			}

			container.cfg.setProperty('text', text, true);
		}
	};
})();