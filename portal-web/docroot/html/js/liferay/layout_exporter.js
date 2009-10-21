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
				"</form>" +
			"</div>";

		AUI().use(
			'dialog',
			function(A) {
				new A.Dialog(
					{
						bodyContent: contents,
						centered: true,
						destroyOnClose: true,
						modal: true,
						title: title,
						width: 300,
						buttons: [
							{
								text: Liferay.Language.get('close'),
								handler: function() {
									this.close();
								}
							}
						]
					}
				)
				.render();
			}
		);
	},

	publishToLive: function(options) {
		AUI().use(
			'dialog',
			function(A) {
				options = options || {};

				var url = options.url;
				var title = options.title;

				new A.Dialog(
					{
						centered: true,
						destroyOnClose: true,
						modal: true,
						title: title,
						width: 600,
						io: {
							url: url
						},
						buttons: [
							{
								text: Liferay.Language.get('close'),
								handler: function() {
									this.close();
								}
							}
						]
					}
				)
				.render();
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