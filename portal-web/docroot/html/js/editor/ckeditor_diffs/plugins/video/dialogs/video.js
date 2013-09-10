CKEDITOR.dialog.add(
	'video',
	function(editor) {
		var TPL_SCRIPT_PREFIX = 'AUI().use(' +
								'	"aui-base", "aui-video",' +
								'	function(A) {' +
								'		var videoId = A.guid();' +
								'		var videoDivNode = A.one(".ckvideo-no-id");' +
								'		videoDivNode.attr("id", videoId);' +
								'		videoDivNode.removeClass("ckvideo-no-id");' +
								'		var videoConfig = {';

		var TPL_SCRIPT =		'			boundingBox: "#" + videoId,' +
								'			height: {height},' +
								'			ogvUrl: "{ogvUrl}",' +
								'			poster: "{poster}",' +
								'			url: "{url}",' +
								'			width: {width}';

		var TPL_SCRIPT_SUFFIX = '		};' +
								'		new A.Video(videoConfig).render();' +
								'	}' +
								');';

		function commitValue(videoNode, extraStyles) {
			var instance = this;

			var id = instance.id;
			var value = instance.getValue();

			var scriptNode = videoNode.getChild(1);

			var scriptTPL = null;
			var textScript = null;

			var videoHeight = videoNode.getAttribute('data-height');
			var videoOgvUrl = videoNode.getAttribute('data-video-ogv-url');
			var videoPoster = videoNode.getAttribute('data-poster');
			var videoUrl = videoNode.getAttribute('data-video-url');
			var videoWidth = videoNode.getAttribute('data-width');

			if (id === 'poster') {
				videoNode.setAttribute('data-document-url', value);

				videoUrl = Liferay.Util.addParams('videoPreview=1&type=mp4', value);

				videoNode.setAttribute('data-video-url', videoUrl);

				videoOgvUrl = Liferay.Util.addParams('videoPreview=1&type=ogv', value);

				videoNode.setAttribute('data-video-ogv-url', videoOgvUrl);

				value = Liferay.Util.addParams('videoThumbnail=1', value);

				videoNode.setAttribute('data-poster', value);

				scriptTPL = new CKEDITOR.template(TPL_SCRIPT);

				textScript = scriptTPL.output(
					{
						height: videoHeight,
						ogvUrl: videoOgvUrl,
						poster: value,
						url: videoUrl,
						width: videoWidth
					}
				);

				scriptNode.setText(TPL_SCRIPT_PREFIX + textScript + TPL_SCRIPT_SUFFIX);
			}

			if (value) {
				if (id === 'poster') {
					extraStyles.backgroundImage = 'url(' + value + ')';
				}
				else if (id === 'height' || id === 'width') {
					var height = videoHeight;
					var width = videoWidth;

					if (id === 'height') {
						height = value;
					}
					else {
						width = value;
					}

					extraStyles[id] = value + 'px';

					videoNode.setAttribute('data-' + id, value);

					if (scriptNode && scriptNode.getText()) {
						scriptTPL = new CKEDITOR.template(TPL_SCRIPT);

						textScript = scriptTPL.output(
							{
								height: height,
								ogvUrl: videoOgvUrl,
								poster: videoPoster,
								url: videoUrl,
								width: width
							}
						);

						scriptNode.setText(TPL_SCRIPT_PREFIX + textScript + TPL_SCRIPT_SUFFIX);
					}
				}
			}
		}

		function loadValue(videoNode) {
			var instance = this;

			var id = instance.id;

			if (videoNode) {
				var value = null;

				if (id === 'poster') {
					value = videoNode.getAttribute('data-document-url');
				}
				else if (id === 'height') {
					value = videoNode.getAttribute('data-height');
				}
				else if (id === 'width') {
					value = videoNode.getAttribute('data-width');
				}

				if (value !== null) {
					instance.setValue(value);
				}
			}
		}

		return {
			minHeight: 200,
			minWidth: 400,

			contents: [
				{
					elements:
					[
						{
							children: [
								{
									commit: commitValue,
									id: 'poster',
									label: Liferay.Language.get('video'),
									setup: loadValue,
									type: 'text'
								},
								{
									filebrowser:
									{
										action: 'Browse',
										target: 'info:poster',
										url: editor.config.filebrowserBrowseUrl + '&Type=Video'
									},
									hidden: 'true',
									id: 'browse',
									label: editor.lang.common.browseServer,
									style: 'display:inline-block;margin-top:10px;',
									type: 'button'
								}
							],
							type: 'hbox',
							widths: [ '', '100px']
						},
						{
							children: [
								{
									commit: commitValue,
									'default': 400,
									id: 'width',
									label: editor.lang.common.width,
									setup: loadValue,
									type: 'text',
									validate: CKEDITOR.dialog.validate.notEmpty(Liferay.Language.get('width-field-cannot-be-empty'))
								},
								{
									commit: commitValue,
									'default': 300,
									id: 'height',
									label: editor.lang.common.height,
									setup: loadValue,
									type: 'text',
									validate: CKEDITOR.dialog.validate.notEmpty(Liferay.Language.get('height-field-cannot-be-empty'))
								}
							],
							type: 'hbox',
							widths: [ '50%', '50%']
						}
					],
					id: 'info'
				}
			],

			title: Liferay.Language.get('video-properties'),

			onShow: function() {
				var instance = this;

				instance.fakeImage = null;
				instance.videoNode = null;

				var fakeImage = instance.getSelectedElement();

				if (fakeImage && fakeImage.data('cke-real-element-type') && fakeImage.data('cke-real-element-type') === 'video') {
					instance.fakeImage = fakeImage;

					var videoNode = editor.restoreRealElement(fakeImage);

					instance.videoNode = videoNode;

					instance.setupContent(videoNode);
				}
				else {
					instance.setupContent(null);
				}
			},
			onOk: function() {
				var instance = this;

				var STR_DIV = 'div';

				var extraStyles = {};

				var divNode = editor.document.createElement(STR_DIV);

				divNode.setAttribute('class', 'liferayckevideo video-container');

				var boundingBoxTmp = editor.document.createElement(STR_DIV);

				boundingBoxTmp.setAttribute('class', 'ckvideo-no-id');

				var scriptTmp = editor.document.createElement('script');

				scriptTmp.setAttribute('type', 'text/javascript');

				divNode.append(boundingBoxTmp);
				divNode.append(scriptTmp);

				instance.commitContent(divNode, extraStyles);

				var newFakeImage = editor.createFakeElement(divNode, 'liferay_cke_video', 'video', false);

				newFakeImage.setStyles(extraStyles);

				if (instance.fakeImage) {
					newFakeImage.replace(instance.fakeImage);

					editor.getSelection().selectElement(newFakeImage);
				}
				else {
					editor.insertElement(newFakeImage);
				}
			}
		};
	}
);