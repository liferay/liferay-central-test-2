Liferay.PortletSharing = {
	showWidgetInfo: function(widgetURL) {
		var portletURL = Liferay.PortletURL.createResourceURL();

		portletURL.setPortletId(133);

		portletURL.setParameter("widgetURL", widgetURL);

		new Expanse.Popup(
			{
				fixedcenter: true,
				modal: true,
				title: Liferay.Language.get('add-to-any-website'),
				url: portletURL.toString(),
				width: 550
			}
		);
	},
	showNetvibesInfo: function(netvibesURL) {
		var portletURL = Liferay.PortletURL.createResourceURL();

		portletURL.setPortletId(133);

		portletURL.setParameter("netvibesURL",netvibesURL);

		new Expanse.Popup(
			{
				fixedcenter: true,
				modal: true,
				title: Liferay.Language.get('add-to-netvibes'),
				url: portletURL.toString(),
				width: 550
			}
		);
	}
};