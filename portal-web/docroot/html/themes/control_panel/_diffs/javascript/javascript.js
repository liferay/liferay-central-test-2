Liferay.Util.portletTitleEdit = function() {
};

AUI().ready(
	function() {
		var portletInformationEl = jQuery('#cpContextPanelTemplate');
		var portletInformationIcon = jQuery('#cpPortletTitleHelpIcon');

		var portletId = portletInformationEl.attr('data-portlet-id');
		var visible = (portletInformationEl.attr('data-visible-panel') == "true");

		var sessionData = {};
		var sessionKey = 'show-portlet-description-' + portletId;

		sessionData[sessionKey] = false;

		var cpContextPanel = new Alloy.ContextPanel(
			{
				el: portletInformationEl[0],
				context: [ portletInformationIcon[0], 'tl', 'bl', ['render', 'beforeShow', 'windowResize']],
				visible: visible,
				draggable: false,
				on: {
					hide: function() {
						jQuery.ajax(
							{
								url: themeDisplay.getPathMain() + '/portal/session_click',
								data: sessionData
							}
						);
					}
				}
			}
		);

		cpContextPanel.render(document.body);

		portletInformationIcon.click(
			function() {
				cpContextPanel.toggle();
			}
		);
	}
);