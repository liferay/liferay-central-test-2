Liferay.PortletSharing = {
	showNetvibesInfo: function(netvibesURL) {
		var portletURL = Liferay.PortletURL.createResourceURL();

		portletURL.setPortletId(133);

		portletURL.setParameter("netvibesURL", netvibesURL);

		AUI().use(
			'dialog',
			function(A) {
				var dialog = new A.Dialog(
					{
						centered: true,
						destroyOnClose: true,
						modal: true,
						title: Liferay.Language.get('add-to-netvibes'),
						width: 550,
						io: {
							url: portletURL.toString()
						}
					}
				)
				.render();
			}
		);
	},
	showWidgetInfo: function(widgetURL) {
		var portletURL = Liferay.PortletURL.createResourceURL();

		portletURL.setPortletId(133);

		portletURL.setParameter("widgetURL", widgetURL);

		AUI().use(
			'dialog',
			function(A) {
				var dialog = new A.Dialog(
					{
						centered: true,
						destroyOnClose: true,
						modal: true,
						title: Liferay.Language.get('add-to-any-website'),
						width: 550,
						io: {
							url: portletURL.toString()
						}
					}
				)
				.render();
			}
		);
	}
};