Liferay.PortletSharing = {
	showNetvibesInfo: function(netvibesURL) {
		var portletURL = Liferay.PortletURL.createResourceURL();

		portletURL.setPortletId(133);

		portletURL.setParameter("netvibesURL",netvibesURL);

		new Expanse.Popup(
			{
				fixedcenter: true,
				header: Liferay.Language.get('add-to-netvibes'),
				modal: true,
				url: portletURL.toString(),
				width: 550
			}
		);
	},
	showWidgetInfo: function(widgetURL) {
		var portletURL = Liferay.PortletURL.createResourceURL();

		portletURL.setPortletId(133);

		portletURL.setParameter("widgetURL", widgetURL);

		new Expanse.Popup(
			{
				fixedcenter: true,
				header: Liferay.Language.get('add-to-any-website'),
				modal: true,
				url: portletURL.toString(),
				width: 550
			}
		);
	}
};