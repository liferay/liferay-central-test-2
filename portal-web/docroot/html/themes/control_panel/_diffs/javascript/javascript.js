Liferay.Util.portletTitleEdit = function() {
};

AUI().use(
	'context-panel',
	'io-request',
	function(A) {
		var portletInformationEl = A.get('#cpContextPanelTemplate');
		var portletInformationIcon = A.get('#cpPortletTitleHelpIcon');

		if (portletInformationEl && portletInformationIcon) {
			var portletId = portletInformationEl.attr('data-portlet-id');
			var visible = (portletInformationEl.attr('data-visible-panel') == "true");
			var sessionData = {};
			var sessionKey = 'show-portlet-description-' + portletId;

			sessionData[sessionKey] = false;

			portletInformationEl.removeClass('aui-contextpanel-hidden');

			var contextPanel = new A.ContextPanel(
				{
					trigger: portletInformationIcon,
					bodyContent: portletInformationEl,
					align: { points: [ 'tl', 'br' ] },
					visible: false,
					on: {
						hide: function() {
							A.io.request(
								themeDisplay.getPathMain() + '/portal/session_click',
								{
									data: sessionData
								}
							);
						}
					}
				}
			).render();

			if (visible) {
				contextPanel.show();
			}
		}
	}
);