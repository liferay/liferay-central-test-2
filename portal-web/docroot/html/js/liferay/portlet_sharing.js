Liferay.PortletSharing = {
	showNetvibesInfo: function(netvibesURL) {
		var portletURL = Liferay.PortletURL.createResourceURL();

		portletURL.setPortletId(133);

		portletURL.setParameter("netvibesURL", netvibesURL);

		new Alloy.Popup(
			{
				body: {
					url: portletURL.toString()
				},
				fixedcenter: true,
				header: Liferay.Language.get('add-to-netvibes'),
				modal: true,
				width: 550
			}
		);
	},
	showWidgetInfo: function(widgetURL) {
		var portletURL = Liferay.PortletURL.createResourceURL();

		portletURL.setPortletId(133);

		portletURL.setParameter("widgetURL", widgetURL);

		new Alloy.Popup(
			{
				body: {
					url: portletURL.toString()
				},
				fixedcenter: true,
				header: Liferay.Language.get('add-to-any-website'),
				modal: true,
				width: 550
			}
		);
	}
};