Liferay.PortletSharing = {
	showWidgetInfo: function(widgetURL) {
		var popup = Liferay.Popup(
			{
				width: 550,
				modal: true,
				title: Liferay.Language.get('add-to-any-website')
			}
		);

		var portletURL = Liferay.PortletURL.createResourceURL();

		portletURL.setPortletId(133);

		portletURL.setParameter("widgetURL", widgetURL);

		jQuery.ajax(
			{
				url: portletURL.toString(),
				success: function(message) {
					popup.html(message);
				}
			}
		);
	},
	showInvite: function(pId, pTitle) {
		var popup = Liferay.Popup(
			{
				width: 550,
				modal: true,
				title: Liferay.Language.get('share-this-application-with-friends')
			}
		);
		this.invitePopup = popup;

		var portletURL = Liferay.PortletURL.createResourceURL();

		portletURL.setPortletId(133);

		portletURL.setParameter("sharePortletId", pId);
		portletURL.setParameter("sharePortletTitle", pTitle);

		jQuery.ajax(
			{
				url: portletURL.toString(),
				success: function(message) {
					popup.html(message);
				}
			}
		);
	},
	sendInvite: function(pId,pTitle, form) {
		var instance = this;

		var portletURL = Liferay.PortletURL.createResourceURL();

		portletURL.setPortletId(133);
		portletURL.setParameter("sharePortletId", pId);
		portletURL.setParameter("sharePortletTitle", pTitle);
		portletURL.setParameter("ShareNOW", "doit");

		postData = "";
		for(i = 0; i < form.elements['checkedFriends'].length; i++) {
			if (form.elements['checkedFriends'][i].checked) {
				postData = postData + "checkedFriends=" + form.elements['checkedFriends'][i].value + "&";
			}
		}

		jQuery.ajax(
			{
				url: portletURL.toString(),
				type: "POST",
				data:   postData,
				success: function(message) {
					instance.invitePopup.html(message);
				}
			}
		); 
		return false;
	}

};
