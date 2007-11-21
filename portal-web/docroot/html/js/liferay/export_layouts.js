Liferay.ExportLayouts = {
	icons: {
		minus: themeDisplay.getPathThemeImages() + '/arrows/01_minus.png',
		plus: themeDisplay.getPathThemeImages() + '/arrows/01_plus.png'
	},

	publishToLive: function(options) {
		options = options || {};
		
		var messageId = options.messageId;
		var url = options.url;
		
		if (!messageId) {
			messageId = Liferay.Language.get(messageId);
		}
		
		var exportLayoutsPopup = Liferay.Popup(
			{
				'title': messageId,
				modal: true,
				width: 600,
				height: 550,
				overflow: "auto",
				'messageId': messageId
			}
		);

		AjaxUtil.update(url, exportLayoutsPopup);
	},

	all: function(options) {
		options = options || {};
		
		var pane = options.pane;
		var obj = options.obj;
		var publish = options.publish;
		
		if (obj.checked) {
			jQuery(pane).hide();
			if (!publish) {
				jQuery('#publish_btn').show();
				jQuery('#select_btn').hide();
			}
			else {
				jQuery('#change_btn').hide();
			}
		}
	},

	selected: function(options) {
		window.status = "LOG: selected(" + options.toString() + ")";
		options = options || {};
		
		var pane = options.pane;
		var obj = options.obj;
		var publish = options.publish;
		
		if (obj.checked) {
			jQuery(pane).show();
			if (!publish) {
				jQuery('#publish_btn').hide();
				jQuery('#select_btn').show();
			}
			else {
				jQuery('#change_btn').show();
			}
		}
	},

	details: function(options) {
		options = options || {};
		
		var toggle = options.toggle;
		var detail = options.detail;
		
		var img = jQuery(toggle)[0];
		if (jQuery(detail).css('display') == 'none') {
			jQuery(detail).slideDown('normal');
			img.src = Liferay.ExportLayouts.icons.minus;
		}
		else {
			jQuery(detail).slideUp('normal');
			img.src = Liferay.ExportLayouts.icons.plus;
		}
	}
};