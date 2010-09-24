;(function(A, Liferay) {
	var Tabs = Liferay.namespace('Portal.Tabs');
	var ToolTip = Liferay.namespace('Portal.ToolTip');

	var arrayIndexOf = A.Array.indexOf;

	Liferay.provide(
		Tabs,
		'show',
		function(namespace, names, id) {
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

			var index = arrayIndexOf(names, id);

			names.splice(index, 1);

			for (var i = 0; i < names.length; i++) {
				el = A.one('#' + namespace + names[i] + 'TabsSection');

				if (el) {
					el.hide();
				}
			}
		},
		['aui-base']
	);

	Liferay.provide(
		ToolTip,
		'show',
		function(obj, text) {
			var instance = this;

			if (!instance._cached) {
				instance._cached = new A.Tooltip(
					{
						trigger: '.liferay-tooltip',
						zIndex: 10000
					}
				).render();
			}

			var cached = instance._cached;

			var trigger = cached.get('trigger');
			var newElement = (trigger.indexOf(obj) == -1);
			var bodyContent = cached.get('bodyContent');

			if (newElement || (bodyContent != text)) {
				cached.set('trigger', obj);
				cached.set('bodyContent', text);
				cached.show();
			}

			cached.refreshAlign();
		},
		['aui-tooltip']
	);
})(AUI(), Liferay);