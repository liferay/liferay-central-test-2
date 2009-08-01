Liferay.Util.portletTitleEdit = function() {
};

jQuery(
	function() {
		var cpPortletTitle = jQuery('#cpPortletTitle');
		var portletInformationEl = jQuery('#cpContextPanelTemplate');
		var portletId = portletInformationEl.attr('data-portlet-id');
		var visible = (portletInformationEl.attr('data-visible-panel') == "true");

		var sessionData = {};
		var sessionKey = 'show-portlet-description-' + portletId;

		sessionData[sessionKey] = false;

		var cpContextPanel = new Alloy.ContextPanel(
			{
				el: 'cpContextPanelTemplate',
				context: [ 'cpPortletTitleHelpIcon', 'tl', 'bl', ['render', 'beforeShow', 'windowResize']],
				visible: visible,
				draggable: false
			}
		);

		cpContextPanel.render(document.body);

		cpContextPanel.hideEvent.subscribe(
			function() {
				jQuery.ajax(
					{
						url: themeDisplay.getPathMain() + '/portal/session_click',
						data: sessionData
					}
				);
			}
		);

		jQuery('#cpPortletTitleHelpIcon').click(
			function() {
				cpContextPanel.toggle();
			}
		);
	}
);