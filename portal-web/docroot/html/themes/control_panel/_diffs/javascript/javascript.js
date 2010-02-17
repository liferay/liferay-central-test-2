Liferay.Util.portletTitleEdit = function() {
};

AUI().use(
	'aui-context-panel',
	'aui-io-request',
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
					align: {
						points: [ 'tl', 'br' ]
					},
					bodyContent: portletInformationEl,
					on: {
						hide: function() {
							A.io.request(
								themeDisplay.getPathMain() + '/portal/session_click',
								{
									data: sessionData
								}
							);
						}
					},
					trigger: portletInformationIcon,
					visible: false
				}
			).render();

			if (visible) {
				contextPanel.show();
			}
		}
	}
);