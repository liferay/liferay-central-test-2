CKEDITOR.dialog.add(
	'link',
	function(editor) {
		var LANG_COMMON = editor.lang.common;

		var LANG_LINK = editor.lang.link;

		var plugin = CKEDITOR.plugins.link;

		var parseLink = function(editor, element) {
			var instance = this;

			var href = (element  && (element.data('cke-saved-href') || element.getAttribute('href'))) || '';

			var data = {
				address: href
			};

			instance._.selectedElement = element;

			return data;
		};

		return {
			minWidth : 250,

			minHeight : 100,

			title : LANG_LINK.title,

			contents : [
				{
					id : 'info',

					label : LANG_LINK.info,

					title : LANG_LINK.info,

					elements : [
						{
							id : 'linkOptions',

							padding : 1,

							type :  'vbox',

							children : [
								{
									id : 'linkAddress',

									label : LANG_COMMON.url,

									required : true,

									type : 'text',

									commit : function(data) {
										var instance = this;

										if (!data) {
											data = {};
										}

										data.address = instance.getValue();
									},

									setup : function(data) {
										var instance = this;

										if (data) {
											instance.setValue(data.address);
										}

										var linkType = instance.getDialog().getContentElement('info', 'linkType');

										if (linkType && linkType.getValue()) {
											instance.select();
										}
									},

									validate : function() {
										var instance = this;

										var func = CKEDITOR.dialog.validate.notEmpty(LANG_LINK.noUrl);

										return func.apply(instance);
									}
								}
							]
						}
					]
				}
			],

			onShow : function() {
				var instance = this;

				instance.fakeObj = false;

				var editor = instance.getParentEditor();
				var selection = editor.getSelection();
				var element = null;

				// Fill in all the relevant fields if there's already one link selected.
				if ((element = plugin.getSelectedLink(editor))) {
					selection.selectElement(element);
				}
				else {
					element = null;
				}

				instance.setupContent(parseLink.apply(instance, [editor, element]));
			},

			onOk : function() {
				var instance = this;

				var attributes = {};
				var data = {};
				var editor = instance.getParentEditor();

				instance.commitContent(data);

				attributes['data-cke-saved-href'] = data.address;
				attributes.href = data.address;

				if (!instance._.selectedElement) {
					// Create element if current selection is collapsed.
					var selection = editor.getSelection(),
						ranges = selection.getRanges(true);

					if (ranges.length == 1 && ranges[0].collapsed)
					{
						var text = new CKEDITOR.dom.text(attributes['data-cke-saved-href'], editor.document);

						ranges[0].insertNode(text);
						ranges[0].selectNodeContents(text);
						selection.selectRanges(ranges);
					}

					// Apply style.
					var style = new CKEDITOR.style(
						{
							element : 'a',
							attributes : attributes
						}
					);

					style.type = CKEDITOR.STYLE_INLINE;		// need to override... dunno why.
					style.apply(editor.document);
				}
				else {
					var element = instance._.selectedElement;

					element.setAttributes(attributes);
				}
			},

			onLoad : function() {
			},

			// Inital focus on 'url' field if link is of type URL.
			onFocus : function() {
				var instance = this;

				var urlField = instance.getContentElement('info', 'linkAddress');

				urlField.select();
			}
		};
	}
);
