Liferay.PortletSharing = {
	showNetvibesInfo: function(netvibesURL) {
		AUI().use(
			'aui-dialog',
			'liferay-portlet-url',
			function(A) {
				var portletURL = Liferay.PortletURL.createResourceURL();

				portletURL.setPortletId(133);

				portletURL.setParameter("netvibesURL", netvibesURL);

				var dialog = new A.Dialog(
					{
						centered: true,
						destroyOnClose: true,
						modal: true,
						title: Liferay.Language.get('add-to-netvibes'),
						width: 550
					}
				)
				.render();

				dialog.plug(
					A.Plugin.IO,
					{
						uri: portletURL.toString()
					}
				);
			}
		);
	},
	showWidgetInfo: function(widgetURL) {
		AUI().use(
			'aui-dialog',
			'liferay-portlet-url',
			function(A) {
				var portletURL = Liferay.PortletURL.createResourceURL();

				portletURL.setPortletId(133);

				portletURL.setParameter("widgetURL", widgetURL);

				var dialog = new A.Dialog(
					{
						centered: true,
						destroyOnClose: true,
						modal: true,
						title: Liferay.Language.get('add-to-any-website'),
						width: 550
					}
				)
				.render();

				dialog.plug(
					A.Plugin.IO,
					{
						uri: portletURL.toString()
					}
				);
			}
		);
	}
};