Liferay.LayoutExporter = {
	all: function(options) {
		options = options || {};

		var pane = options.pane;
		var obj = options.obj;
		var publish = options.publish;

		if (obj.checked) {
			jQuery(pane).hide();

			if (!publish) {
				jQuery('#publishBtn').show();
				jQuery('#selectBtn').hide();
			}
			else {
				jQuery('#changeBtn').hide();
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
			img.src = Liferay.LayoutExporter.icons.minus;
		}
		else {
			jQuery(detail).slideUp('normal');
			img.src = Liferay.LayoutExporter.icons.plus;
		}
	},

	icons: {
		minus: themeDisplay.getPathThemeImages() + '/arrows/01_minus.png',
		plus: themeDisplay.getPathThemeImages() + '/arrows/01_plus.png'
	},

	proposeLayout: function(options) {
		options = options || {};

		var url = options.url;
		var namespace = options.namespace;
		var reviewers = options.reviewers;
		var title = options.title;

		var contents =
			"<div>" +
				"<form action='" + url + "' method='post'>" +
					"<textarea name='" + namespace + "description' style='height: 100px; width: 284px;'></textarea><br /><br />";

		if (reviewers.length > 0) {
			contents += Liferay.Language.get('reviewer') + " <select name='" + namespace + "reviewUserId'>";

			for (var i = 0; i < reviewers.length; i++) {
				contents += "<option value='" + reviewers[i].userId + "'>" + reviewers[i].fullName + "</option>";
			}

			contents += "</select><br /><br />";
		}

		contents +=
					"<input type='submit' value='" + Liferay.Language.get('proceed') + "' />" +
					"<input type='button' value='" + Liferay.Language.get('cancel') + "' onClick='Liferay.Popup.close(this);' />" +
				"</form>" +
			"</div>";

		Liferay.Popup({
			'title': title,
			message: contents,
			noCenter: false,
			modal: true,
			width: 300
		});
	},

	publishToLive: function(options) {
		options = options || {};

		var messageId = options.messageId;
		var url = options.url;
		var title = options.title;

		if (!title) {
			title = Liferay.Language.get(messageId);
		}

		var exportLayoutsPopup = Liferay.Popup(
			{
				'title': title,
				modal: true,
				width: 600,
				height: 550,
				overflow: "auto",
				'messageId': messageId
			}
		);

		jQuery.ajax(
			{
				url: url,
				success: function(response) {
					jQuery(exportLayoutsPopup).html(response);

					if (Liferay.Browser.is_ie) {
						Liferay.Util.evalScripts(exportLayoutsPopup);
					}
				}
			}
		);
	},

	selected: function(options) {
		options = options || {};

		var pane = options.pane;
		var obj = options.obj;
		var publish = options.publish;

		if (obj.checked) {
			jQuery(pane).show();

			if (!publish) {
				jQuery('#publishBtn').hide();
				jQuery('#selectBtn').show();
			}
			else {
				jQuery('#changeBtn').show();
			}
		}
	}
};