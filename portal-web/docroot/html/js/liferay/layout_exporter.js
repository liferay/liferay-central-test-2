Liferay.LayoutExporter = {
	all: function(options) {
		options = options || {};

		var pane = options.pane;
		var obj = options.obj;
		var publish = options.publish;

		if (obj && obj.checked) {
			pane = AUI().one(pane);

			if (pane) {
				pane.hide();
			}

			if (!publish) {
				var publishBtn = AUI().one('#publishBtn');
				var selectBtn = AUI().one('#selectBtn');

				if (publishBtn) {
					publishBtn.show();
				}

				if (selectBtn) {
					selectBtn.hide();
				}
			}
			else {
				var changeBtn = AUI().one('#changeBtn');

				if (changeBtn) {
					changeBtn.hide();
				}
			}
		}
	},

	details: function(options) {
		options = options || {};

		var detail = AUI().one(options.detail);
		var img = AUI().one(toggle);

		if (detail && img) {
			var icon = Liferay.LayoutExporter.icons.plus;

			if (detail.hasClass('aui-helper-hidden')) {
				detail.show();
				icon = Liferay.LayoutExporter.icons.minus;
			}
			else {
				detail.hide();
			}

			img.attr('src', icon);
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

				var dialog = new A.Dialog(
					{
						centered: true,
						destroyOnClose: true,
						modal: true,
						title: title,
						width: 600,
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

				dialog.plug(
					A.Plugin.IO,
					{
						uri: url
					}
				);
			}
		);
	},

	selected: function(options) {
		options = options || {};

		var pane = options.pane;
		var obj = options.obj;
		var publish = options.publish;

		if (obj && obj.checked) {
			pane = AUI().one(pane);

			if (pane) {
				pane.show();
			}

			if (!publish) {
				var publishBtn = AUI().one('#publishBtn');
				var selectBtn = AUI().one('#selectBtn');

				if (publishBtn) {
					publishBtn.hide();
				}

				if (selectBtn) {
					selectBtn.show();
				}
			}
			else {
				var changeBtn = AUI().one('#changeBtn');

				if (changeBtn) {
					changeBtn.show();
				}
			}
		}
	}
};