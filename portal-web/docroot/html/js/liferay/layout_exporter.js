Liferay.LayoutExporter = {
	all: function(options) {
		options = options || {};

		var pane = options.pane;
		var obj = options.obj;
		var publish = options.publish;

		if (obj && obj.checked) {
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
				"<form action='" + url + "' method='post'>";

		if (reviewers.length > 0) {
			contents +=
				"<textarea name='" + namespace + "description' style='height: 100px; width: 284px;'></textarea><br /><br />" +
				Liferay.Language.get('reviewer') + " <select name='" + namespace + "reviewUserId'>";

			for (var i = 0; i < reviewers.length; i++) {
				contents += "<option value='" + reviewers[i].userId + "'>" + reviewers[i].fullName + "</option>";
			}

			contents +=
				"</select><br /><br />" +
				"<input type='submit' value='" + Liferay.Language.get('proceed') + "' />";
		}
		else {
			contents +=
				Liferay.Language.get('no-reviewers-were-found') + "<br />" +
				Liferay.Language.get('please-contact-the-administrator-to-assign-reviewers') + "<br /><br />";
		}

		contents +=
					"<input type='button' value='" + Liferay.Language.get('cancel') + "' onClick='Alloy.Popup.close(this);' />" +
				"</form>" +
			"</div>";

		new Alloy.Popup(
			{
				body: contents,
				fixedcenter: true,
				header: title,
				modal: true,
				width: 300
			}
		);
	},

	publishToLive: function(options) {
		options = options || {};

		var messageId = options.messageId;
		var url = options.url;
		var title = options.title;

		if (!title) {
			title = Liferay.Language.get(messageId);
		}

		var exportLayoutsPopup = new Alloy.Popup(
			{
				body: {
					url: url
				},
				fixedcenter: true,
				header: title,
				modal: true,
				messageId: messageId,
				width: 600
			}
		);
	},

	selected: function(options) {
		options = options || {};

		var pane = options.pane;
		var obj = options.obj;
		var publish = options.publish;

		if (obj && obj.checked) {
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