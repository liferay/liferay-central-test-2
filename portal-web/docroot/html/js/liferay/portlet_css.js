Liferay.PortletCSS = {
	init: function(str) {
		var instance = this;
		var obj = jQuery('#' + str);
		var currentId = str;
		var tabTrigger = 1;

		instance._currentPortlet = obj.find('.portlet');
		instance._newPanel = jQuery('#portlet-set-properties');
		instance._currentPortletId = instance._currentPortlet.attr('id');

		var newPanel = instance._newPanel;

		if (instance._currentPortlet.length) {
			if (!instance._newPanel.is('.instantiated')) {

				// Language keys

				instance._lang = {};

				instance._lang.refreshText = Liferay.Language.get('update-the-styles-on-this-page');
				instance._lang.refreshTextSafari = Liferay.Language.get('please-press-the-save-button-to-view-your-changes');
				instance._lang.addId = Liferay.Language.get('add-a-css-rule-for-just-this-portlet');
				instance._lang.addClass = Liferay.Language.get('add-a-css-rule-for-all-portlets-like-this-one');
				instance._lang.updateOnType = Liferay.Language.get('update-my-styles-as-i-type');
				instance._lang.portletId = Liferay.Language.get('portlet-id');
				instance._lang.portletClasses = Liferay.Language.get('portlet-classes');
				instance._lang.currentPortletInfo = Liferay.Language.get('your-current-portlet-information-is-as-follows');

				instance._newPanel.addClass('instantiated');

				instance._portletId = jQuery('#portlet-id');

				// Portlet config

				instance._customTitleInput = jQuery('#custom-title');
				instance._defaultPortletTitle = instance._currentPortlet.find('.portlet-title').text();
				instance._customTitleCheckbox = jQuery('#use-custom-title-checkbox');
				instance._showBorders = jQuery('#show-borders');
				instance._borderNote = jQuery('#border-note');
				instance._portletLanguage = jQuery('#lfr-portlet-language');
				instance._portletLinksTarget = jQuery('#lfr-point-links');

				// Text

				instance._fontFamily = jQuery('#lfr-font-family');
				instance._fontWeight = jQuery('#lfr-font-bold');
				instance._fontStyle = jQuery('#lfr-font-italic');
				instance._fontSize = jQuery('#lfr-font-size');
				instance._fontColor = jQuery('#lfr-font-color');
				instance._textAlign = jQuery('#lfr-font-align');
				instance._textDecoration = jQuery('#lfr-font-decoration');
				instance._wordSpacing = jQuery('#lfr-font-space');
				instance._leading = jQuery('#lfr-font-leading');
				instance._tracking = jQuery('#lfr-font-tracking');

				// Background

				instance._backgroundColor = jQuery('#lfr-bg-color');

				instance._useBgImage = jQuery('#lfr-use-bg-image');
				instance._bgImageProperties = jQuery('.lfr-bg-image-properties');

				instance._bgRepeating = jQuery('#lfr-bg-repeat');

				instance._bgPosTop = jQuery('#lfr-bg-top-int');
				instance._bgPosTopUnit = jQuery('#lfr-bg-top-unit');
				instance._bgPosLeft = jQuery('#lfr-bg-left-int');
				instance._bgPosLeftUnit = jQuery('#lfr-bg-left-unit');

				// Border

				instance._ufaBorderWidth = jQuery('#lfr-use-for-all-width');
				instance._ufaBorderStyle = jQuery('#lfr-use-for-all-style');
				instance._ufaBorderColor = jQuery('#lfr-use-for-all-color');

				instance._borderTopInt = jQuery('#lfr-border-width-top');
				instance._borderTopUnit = jQuery('#lfr-border-width-top-unit');
				instance._borderRightInt = jQuery('#lfr-border-width-right');
				instance._borderRightUnit = jQuery('#lfr-border-width-right-unit');
				instance._borderBottomInt = jQuery('#lfr-border-width-bottom');
				instance._borderBottomUnit = jQuery('#lfr-border-width-bottom-unit');
				instance._borderLeftInt = jQuery('#lfr-border-width-left');
				instance._borderLeftUnit = jQuery('#lfr-border-width-left-unit');

				instance._borderTopStyle = jQuery('#lfr-border-style-top');
				instance._borderRightStyle = jQuery('#lfr-border-style-right');
				instance._borderBottomStyle = jQuery('#lfr-border-style-bottom');
				instance._borderLeftStyle = jQuery('#lfr-border-style-left');

				instance._borderTopColor = jQuery('#lfr-border-color-top');
				instance._borderRightColor = jQuery('#lfr-border-color-right');
				instance._borderBottomColor = jQuery('#lfr-border-color-bottom');
				instance._borderLeftColor = jQuery('#lfr-border-color-left');

				// Spacing

				instance._ufaPadding = jQuery('#lfr-use-for-all-padding');
				instance._ufaMargin = jQuery('#lfr-use-for-all-margin');

				instance._paddingTopInt = jQuery('#lfr-padding-top');
				instance._paddingTopUnit = jQuery('#lfr-padding-top-unit');
				instance._paddingRightInt = jQuery('#lfr-padding-right');
				instance._paddingRightUnit = jQuery('#lfr-padding-right-unit');
				instance._paddingBottomInt = jQuery('#lfr-padding-bottom');
				instance._paddingBottomUnit = jQuery('#lfr-padding-bottom-unit');
				instance._paddingLeftInt = jQuery('#lfr-padding-left');
				instance._paddingLeftUnit = jQuery('#lfr-padding-left-unit');

				instance._marginTopInt = jQuery('#lfr-margin-top');
				instance._marginTopUnit = jQuery('#lfr-margin-top-unit');
				instance._marginRightInt = jQuery('#lfr-margin-right');
				instance._marginRightUnit = jQuery('#lfr-margin-right-unit');
				instance._marginBottomInt = jQuery('#lfr-margin-bottom');
				instance._marginBottomUnit = jQuery('#lfr-margin-bottom-unit');
				instance._marginLeftInt = jQuery('#lfr-margin-left');
				instance._marginLeftUnit = jQuery('#lfr-margin-left-unit');

				// Advanced CSS

				instance._customCSS = jQuery('#lfr-custom-css');

				instance._saveButton = jQuery('#lfr-lookfeel-save');
				instance._resetButton = jQuery('#lfr-lookfeel-reset');

				newPanel.show();

				newPanel.tabs({
					tabStruct: 'form>fieldset',
					selectedClass: 'current'
				});

				instance._currentPopup = Liferay.Popup({
					width: 700,
					message: newPanel[0],
					modal: false,
					noCenter: true,
					onClose: function() {
						instance._newPanel.removeClass('instantiated');
						jQuery(newPanel[0]).hide().appendTo('body');
					}
				});

			}

			newPanel.find('.lfr-color-picker-img').remove();

			var defaultData = {
				advancedData: {
					customCSS: ''
				},
				bgData: {
					backgroundColor: '',
					backgroundImage: '',
					useBgImage: false,
					backgroundRepeat: 'repeat',
					backgroundPosition: {
						left: '',
						leftUnit: 'px',
						top: '',
						topUnit: 'px'
					}
				},

				borderData: {
					borderWidth: {
						bottom: '',
						bottomUnit: 'px',
						left: '',
						leftUnit: 'px',
						right: '',
						rightUnit: 'px',
						top: '',
						topUnit: 'px',
						sameForAll: true
					},

					borderStyle: {
						bottom: 'solid',
						left: 'solid',
						right: 'solid',
						top: 'solid',
						sameForAll: true
					},

					borderColor: {
						bottom: '',
						left: '',
						right: '',
						top: '',
						sameForAll: true
					}
				},

				portletData: {
					language: 'en_US',
					portletLinksTarget: '',
					showBorders: true,
					title: '',
					useCustomTitle: false
				},
				spacingData: {
					margin: {
						bottom: '',
						bottomUnit: 'px',
						left: '',
						leftUnit: 'px',
						right: '',
						rightUnit: '%',
						top: '',
						topUnit: 'px',
						sameForAll: true
					},
					padding: {
						bottom: '',
						bottomUnit: 'px',
						left: '',
						leftUnit: 'px',
						right: '',
						rightUnit: 'px',
						top: '',
						topUnit: 'px',
						sameForAll: true
					}

				},
				textData: {
					textAlign: 'left',
					color: '',
					fontFamily: 'Arial',
					fontSize: '1em',
					fontStyle: false,
					fontWeight: false,
					letterSpacing: '0',
					lineHeight: '1.2em',
					textDecoration: 'none',
					wordSpacing: 'normal'
				}
			};

			instance._objData = defaultData;

			instance._assignColorPickers();

			instance._portletId.val(currentId);

			instance._setDefaults();

			instance._portletConfig();
			instance._textStyles();
			instance._backgroundStyles();
			instance._borderStyles();
			instance._spacingStyles();
			instance._cssStyles();

			newPanel.triggerTab(tabTrigger);

			var useForAll = newPanel.find('.lfr-use-for-all');

			var handleForms = function() {
				var checkBox = jQuery(this);
				var otherHolders = checkBox.parents('fieldset:first').find('.ctrl-holder:gt(1)');
				var otherForms = otherHolders.find('input, select');
				var colorPickerImages = otherHolders.find('.lfr-color-picker-img');

				if (this.checked) {
					otherHolders.fadeTo('fast', 0.3);
					otherForms.attr('disabled', true);
					colorPickerImages.hide();
				}
				else {
					otherHolders.fadeTo('fast', 1);
					otherForms.attr('disabled', false);
					colorPickerImages.show();
				}
			};

			useForAll.unbind().click(handleForms);
			useForAll.each(handleForms);

			instance._saveButton.unbind().click(
				function() {
						jQuery.ajax(
							{
								data: {
									doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
									cmd: 'update_portlet_css',
									portletId: instance._portletId,
									objData: instance._objData
								},
								url: themeDisplay.getPathContext() + '/update_portlet_css'
							}
						);
				}
			);

			instance._resetButton.unbind().click(
				function() {
					instance._currentPortlet.attr('style', '');
					jQuery('#lfr-custom-css-block-' + instance._currentPortletId).remove();
					instance._objData = defaultData;
					instance._setDefaults();
				}
			);

			Liferay.Util.addInputFocus(instance._newPanel[0]);
		}
	},

	_assignColorPickers: function() {
		var instance = this;
		instance._newPanel.find('.use-color-picker').each(
			function() {
				new Liferay.ColorPicker(
					{
						context: jQuery('#portlet-set-properties')[0],
						item: this
					}
				);
			}
		);

	},

	_backgroundStyles: function() {
		var instance = this;

		var bgData = instance._objData.bgData;

		var portlet = instance._currentPortlet;

		// Background color
	
		var backgroundColor = instance._backgroundColor;

		var setColor = function(obj){
			var color = obj.value;
			if (color != '') {
				portlet.css('background-color', color);

				bgData.backgroundColor = color;
			}
		};

		var colorPicker = new Liferay.ColorPicker(
			{
				context: instance._newPanel[0],
				item: backgroundColor[0],
				onChange: function() {
					setColor(backgroundColor[0]);
				}
			}
		);

		backgroundColor.unbind().blur(
			function() {
				setColor(this);
			}
		);

		// Background image

		var useBgImage = instance._useBgImage;
		var useBg = useBgImage.is(':checked');
		var bgImageProperties = instance._bgImageProperties;

		bgData.useBgImage = useBg;

		if (useBg) {
			bgImageProperties.show();
		}

		useBgImage.unbind().click(
			function() {
				bgImageProperties.toggle();
			}
		);

		// Background repeating

		var bgRepeating = instance._bgRepeating;

		bgRepeating.unbind().change(
			function() {
				var backgroundRepeat = this.options[this.selectedIndex].value;
				portlet.css('background-repeat', backgroundRepeat);

				bgData.backgroundRepeat = backgroundRepeat;
			}
		);

		// Background position

		var bgPosTop = instance._bgPosTop;
		var bgPosTopUnit = instance._bgPosTopUnit;

		var bgPosLeft = instance._bgPosLeft;
		var bgPosLeftUnit = instance._bgPosLeftUnit;

		var updatePos = function() {
			var topPos = instance._getCombo(bgPosTop, bgPosTopUnit);
			var leftPos = instance._getCombo(bgPosLeft, bgPosLeftUnit);

			portlet.css('background-position', leftPos.both + ' ' + topPos.both);

			bgData.backgroundPosition.top = topPos.input;
			bgData.backgroundPosition.topUnit = topPos.selectBox;

			bgData.backgroundPosition.left = leftPos.input;
			bgData.backgroundPosition.leftUnit = leftPos.selectBox;
		};

		bgPosTop.unbind().blur(updatePos);
		bgPosLeft.unbind().blur(updatePos);
		bgPosTop.unbind().keyup(updatePos);
		bgPosLeft.unbind().keyup(updatePos);

		bgPosTopUnit.unbind().change(updatePos);
		bgPosLeftUnit.unbind().change(updatePos);
	},

	_borderStyles: function() {
		var instance = this;

		var portlet = instance._currentPortlet;

		var ufaWidth = instance._ufaBorderWidth;
		var ufaStyle = instance._ufaBorderStyle;
		var ufaColor = instance._ufaBorderColor;

		var borderData = instance._objData.borderData;

		// Border width

		var wTopInt = instance._borderTopInt;
		var wTopUnit = instance._borderTopUnit;
		var wRightInt = instance._borderRightInt;
		var wRightUnit = instance._borderRightUnit;
		var wBottomInt = instance._borderBottomInt;
		var wBottomUnit = instance._borderBottomUnit;
		var wLeftInt = instance._borderLeftInt;
		var wLeftUnit = instance._borderLeftUnit;

		var changeWidth = function() {
			var styling = {};
			var borderWidth = {};

			borderWidth = instance._getCombo(wTopInt, wTopUnit);
			styling = {borderWidth: borderWidth.both};

			var ufa = ufaWidth.is(':checked');

			borderData.borderWidth.top = borderWidth.input;
			borderData.borderWidth.topUnit = borderWidth.selectBox;
			borderData.borderWidth.sameForAll = ufa;

			if (!ufa) {
				var extStyling = {};

				extStyling.borderTopWidth = styling.borderWidth;

				var right = instance._getCombo(wRightInt, wRightUnit);
				var bottom = instance._getCombo(wBottomInt, wBottomUnit);
				var left = instance._getCombo(wLeftInt, wLeftUnit);

				extStyling.borderRightWidth = right.both;
				extStyling.borderBottomWidth = bottom.both;
				extStyling.borderLeftWidth = left.both;

				styling = extStyling;

				borderData.borderWidth.right = right.input;
				borderData.borderWidth.rightUnit = right.selectBox;

				borderData.borderWidth.bottom = bottom.input;
				borderData.borderWidth.bottomUnit = bottom.selectBox;

				borderData.borderWidth.left = left.input;
				borderData.borderWidth.leftUnit = left.selectBox;
			}

			portlet.css(styling);

			changeStyle();
			changeColor();
		};

		wTopInt.unbind().blur(changeWidth);
		wTopInt.unbind().keyup(changeWidth);

		wRightInt.unbind().blur(changeWidth);
		wRightInt.unbind().keyup(changeWidth);

		wBottomInt.unbind().blur(changeWidth);
		wBottomInt.unbind().keyup(changeWidth);

		wLeftInt.unbind().blur(changeWidth);
		wLeftInt.unbind().keyup(changeWidth);

		wTopUnit.unbind().change(changeWidth);
		wRightUnit.unbind().change(changeWidth);
		wBottomUnit.unbind().change(changeWidth);
		wLeftUnit.unbind().change(changeWidth);

		ufaWidth.unbind().click(changeWidth);

		// Border style

		var sTopStyle = instance._borderTopStyle;
		var sRightStyle = instance._borderRightStyle;
		var sBottomStyle = instance._borderBottomStyle;
		var sLeftStyle = instance._borderLeftStyle;

		var changeStyle = function() {
			var styling = {};
			var borderStyle = {};

			borderStyle = sTopStyle.find('option:selected').val();
			styling = {borderStyle: borderStyle};
			var ufa = ufaStyle.is(':checked');

			borderData.borderStyle.top = borderStyle;
			borderData.borderStyle.sameForAll = ufa;

			if (!ufa) {
				var extStyling = {};

				extStyling.borderTopStyle = styling.borderStyle;

				var right = sRightStyle.find('option:selected').val();
				var bottom = sBottomStyle.find('option:selected').val();
				var left = sLeftStyle.find('option:selected').val();

				extStyling.borderRightStyle = right;
				extStyling.borderBottomStyle = bottom;
				extStyling.borderLeftStyle = left;

				styling = extStyling;

				borderData.borderStyle.right = right;

				borderData.borderStyle.bottom = bottom;

				borderData.borderStyle.left = left;
			}

			portlet.css(styling);
		};

		sTopStyle.unbind().change(changeStyle);
		sRightStyle.unbind().change(changeStyle);
		sBottomStyle.unbind().change(changeStyle);
		sLeftStyle.unbind().change(changeStyle);

		ufaStyle.unbind().click(changeStyle);

		// Border color

		var cTopColor = instance._borderTopColor;
		var cRightColor = instance._borderRightColor;
		var cBottomColor = instance._borderBottomColor;
		var cLeftColor = instance._borderLeftColor;

		var changeColor = function() {
			var styling = {};
			var borderColor = {};

			borderColor = cTopColor.val();
			styling = {borderColor: borderColor};

			var ufa = ufaColor.is(':checked');

			borderData.borderColor.top = borderColor;
			borderData.borderColor.sameForAll = ufa;

			if (!ufa) {
				var extStyling = {};

				extStyling.borderTopColor = styling.borderColor;

				var right = cRightColor.val();
				var bottom = cBottomColor.val();
				var left = cLeftColor.val();

				extStyling.borderRightColor = right;
				extStyling.borderBottomColor = bottom;
				extStyling.borderLeftColor = left;

				styling = extStyling;

				borderData.borderColor.right = right;

				borderData.borderColor.bottom = bottom;

				borderData.borderColor.left = left;
			}

			portlet.css(styling);
		};

		var colorPickerTop = new Liferay.ColorPicker(
			{
				context: jQuery('#portlet-set-properties')[0],
				item: cTopColor[0],
				onChange: changeColor
			}
		);

		var colorPickerRight = new Liferay.ColorPicker(
			{
				context: jQuery('#portlet-set-properties')[0],
				item: cRightColor[0],
				onChange: changeColor
			}
		);

		var colorPickerBottom = new Liferay.ColorPicker(
			{
				context: jQuery('#portlet-set-properties')[0],
				item: cBottomColor[0],
				onChange: changeColor
			}
		);

		var colorPickerLeft = new Liferay.ColorPicker(
			{
				context: jQuery('#portlet-set-properties')[0],
				item: cLeftColor[0],
				onChange: changeColor
			}
		);

		cTopColor.unbind().blur(changeColor);
		cRightColor.unbind().blur(changeColor);
		cBottomColor.unbind().blur(changeColor);
		cLeftColor.unbind().blur(changeColor);

		cTopColor.unbind().keyup(changeColor);
		cRightColor.unbind().keyup(changeColor);
		cBottomColor.unbind().keyup(changeColor);
		cLeftColor.unbind().keyup(changeColor);

		ufaColor.unbind().click(changeColor);

	},

	_cssStyles: function() {
		var instance = this;

		var portlet = instance._currentPortlet;

		var customCSS = jQuery('#lfr-custom-css');
		var customCSSContainer = customCSS.parents('.ctrl-holder');
		var customPortletNoteHTML = '<p class="portlet-msg-info form-hint"></p>';
		var customPortletNote = jQuery('#lfr-portlet-info');
		var refreshText = '';

		var portletId = instance._currentPortletId;
		var portletClasses = portlet.attr('class');

		portletClasses = jQuery.trim(portletClasses).replace(/(\s)/g, '$1.');

		var portletInfoText =
			instance._lang.currentPortletInfo + ':<br />' +
				instance._lang.portletId + ': <strong>#' + portletId + '</strong><br />' +
					instance._lang.portletClasses + ': <strong>.' + portletClasses + '</strong>';

		var customNote = jQuery('#lfr-refresh-styles');

		if (!customNote.length) {
			customNote = jQuery(customPortletNoteHTML);
			customNote.attr(
				{
					'class': '',
					id: 'lfr-refresh-styles'
				}
			);
		}

		if (!customPortletNote.length) {
			customPortletNote = jQuery(customPortletNoteHTML);
			customCSSContainer.before(customPortletNote);

			customPortletNote.attr(
				{
					id: 'lfr-portlet-info'
				}
			);
		}

		customPortletNote.html(portletInfoText);

		customCSS.EnableTabs();

		if (!jQuery.browser.safari) {

			refreshText = instance._lang.refreshText;
			var refreshLink = jQuery('<a href="javascript:;">' + refreshText + '</a>');

			var customStyleBlock = jQuery('#lfr-custom-css-block-' + portletId);

			if (!customStyleBlock.length) {
				//  Do not modify. This is a workaround for an IE bug.
				var styleEl = document.createElement('style');
				styleEl.id = 'lfr-custom-css-block-' + portletId;
				styleEl.className = 'lfr-custom-css-block';
				styleEl.setAttribute('type', 'text/css');

				document.getElementsByTagName('head')[0].appendChild(styleEl);
			}
			else {
				styleEl = customStyleBlock[0];
			}

			var refreshStyles = function() {
				var customStyles = customCSS.val();

				customStyles = customStyles.replace(/<script[^>]*>([\u0001-\uFFFF]*?)<\/script>/gim, '');
				customStyles = customStyles.replace(/<\/?[^>]+>/gi, '');

				if (styleEl.styleSheet) { // for IE only
					if (customStyles == '') {

						// Do not modify. This is a workaround for an IE bug.

						customStyles = '<!---->';
					}
					styleEl.styleSheet.cssText = customStyles;
				}
				else {
					jQuery(styleEl).html(customStyles)
				}
			};

			refreshLink.unbind().click(refreshStyles);

			customNote.empty().append(refreshLink);
		}
		else {
			refreshText = instance._lang.refreshTextSafari;

			customNote.empty().text(refreshText);
		}

		var insertContainer = jQuery('#lfr-add-rule-container');
		var addIdLink = jQuery('#lfr-add-id');
		var addClassLink = jQuery('#lfr-add-class');
		var updateOnType = jQuery('#lfr-update-on-type');

		if (!insertContainer.length) {
			insertContainer = jQuery('<div id="lfr-add-rule-container"></div>');
			addIdLink = jQuery('<a href="javascript:;" id="lfr-add-id">' + instance._lang.addId + '</a>');
			addClassLink = jQuery('<a href="javascript:;" id="lfr-add-class">' + instance._lang.addClass + '</a>');

			var updateOnTypeHolder = jQuery('<div class="ctrl-holder"></div>');
			var updateOnTypeLabel = jQuery('<label>' + instance._lang.updateOnType + ' </label>');

			updateOnType = jQuery('<input id="lfr-update-on-type" type="checkbox" />');

			updateOnTypeLabel.append(updateOnType);
			updateOnTypeHolder.append(updateOnTypeLabel);

			customCSSContainer.after(insertContainer);

			insertContainer.append(addIdLink);
			insertContainer.append('<br />');
			insertContainer.append(addClassLink);
			insertContainer.append(updateOnTypeHolder);

			insertContainer.after(customNote);
		}

		updateOnType.click(
			function() {
				if (this.checked) {
					customNote.hide();
					customCSS.keyup(refreshStyles);
				}
				else {
					customNote.show();
					customCSS.unbind('keyup', refreshStyles);
				}
			}
		);

		addIdLink.unbind().click(
			function() {
				customCSS[0].value += '\n#' + portletId + '{\n\t\n}\n';
			}
		);

		addClassLink.unbind().click(
			function() {
				customCSS[0].value += '\n.' + portletClasses.replace(/\s/g, '') + '{\n\t\n}\n';
			}
		);
	},

	_getCombo: function(input, selectBox) {
		var instance = this;

		var inputVal = input.val();
		var selectVal = selectBox.find('option:selected').val();

		inputVal = instance._getSafeInteger(inputVal);

		return {input: inputVal, selectBox: selectVal, both: inputVal + selectVal};
	},

	_getSafeInteger: function(input) {
		var instance = this;

		var output = parseInt(input);

		if (output == '' || isNaN(output)) {
			output = 0;
		}

		return output;
	},

	_portletConfig: function() {
		var instance = this;

		var portletData = instance._objData.portletData;
		var customTitleInput = instance._customTitleInput;
		var customTitleCheckbox = instance._customTitleCheckbox;
		var showBorders = instance._showBorders;
		var language = instance._portletLanguage;
		var borderNote = instance._borderNote;

		// Use custom title

		customTitleCheckbox.unbind().click(
			function() {
				var title;

				if (this.checked) {
					title = customTitleInput.val();
					portletData.useCustomTitle = this.checked;
					portletData.title = title;
				}
				else {
					title = instance._defaultPortletTitle;
				}

				instance._currentPortlet.find('.portlet-title').text(title);
			}
		);

		customTitleInput.unbind().keyup(
			function() {
				if (!customTitleCheckbox.is(':checked')) {
					return;
				}
				instance._currentPortlet.find('.portlet-title').text(this.value);
				portletData.title = this.value;
			}
		);

		// Show borders

		showBorders.unbind().click(
			function() {
				borderNote.toggle();
				portletData.showBorders = this.checked;
			}
		);

		language.change(
			function() {
				portletData.language = this.options[this.selectedIndex].value;
			}
		);
	},

	_setCheckbox: function(obj, value) {
		var instance = this;

		obj.attr('checked', value);
	},

	_setDefaults: function() {
		var instance = this;

		var objData = instance._objData;

		var portletData = objData.portletData;
		var textData = objData.textData;
		var bgData = objData.bgData;
		var borderData = objData.borderData;
		var spacingData = objData.spacingData;

		// Portlet config

		instance._setInput(instance._customTitleInput, portletData.title);
		instance._setCheckbox(instance._customTitleCheckbox, portletData.useCustomTitle);
		instance._setCheckbox(instance._showBorders, portletData.showBorders);
		instance._setSelect(instance._portletLanguage, portletData.language);
		instance._setSelect(instance._portletLinksTarget, portletData.portletLinksTarget);

		// Text

		instance._setSelect(instance._fontFamily, textData.fontFamily);
		instance._setCheckbox(instance._fontWeight, textData.fontWeight);
		instance._setCheckbox(instance._fontStyle, textData.fontStyle);
		instance._setSelect(instance._fontSize, textData.fontSize);
		instance._setInput(instance._fontColor, textData.color);
		instance._setSelect(instance._textAlign, textData.textAlign);
		instance._setSelect(instance._textDecoration, textData.textDecoration);
		instance._setSelect(instance._wordSpacing, textData.wordSpacing);
		instance._setSelect(instance._leading, textData.lineHeight);
		instance._setSelect(instance._tracking, textData.letterSpacing);

		// Background

		instance._setInput(instance._backgroundColor, bgData.backgroundColor);
		instance._setCheckbox(instance._useBgImage, bgData.useBgImage);
		instance._setSelect(instance._bgRepeating, bgData.backgroundRepeat);
		instance._setInput(instance._bgPosTop, bgData.backgroundPosition.top);
		instance._setSelect(instance._bgPosTopUnit, bgData.backgroundPosition.topUnit);
		instance._setInput(instance._bgPosLeft, bgData.backgroundPosition.left);
		instance._setSelect(instance._bgPosLeftUnit, bgData.backgroundPosition.leftUnit);

		// Border

		instance._setCheckbox(instance._ufaBorderWidth, borderData.borderWidth.sameForAll);
		instance._setCheckbox(instance._ufaBorderStyle, borderData.borderStyle.sameForAll);
		instance._setCheckbox(instance._ufaBorderColor, borderData.borderColor.sameForAll);

		instance._setInput(instance._borderTopInt, borderData.borderWidth.top);
		instance._setSelect(instance._borderTopUnit, borderData.borderWidth.topUnit);
		instance._setInput(instance._borderRightInt, borderData.borderWidth.right);
		instance._setSelect(instance._borderRightUnit, borderData.borderWidth.rightUnit);
		instance._setInput(instance._borderBottomInt, borderData.borderWidth.bottom);
		instance._setSelect(instance._borderBottomUnit, borderData.borderWidth.bottomUnit);
		instance._setInput(instance._borderLeftInt, borderData.borderWidth.left);
		instance._setSelect(instance._borderLeftUnit, borderData.borderWidth.leftUnit);

		instance._setSelect(instance._borderTopStyle, borderData.borderStyle.top);
		instance._setSelect(instance._borderRightStyle, borderData.borderStyle.right);
		instance._setSelect(instance._borderBottomStyle, borderData.borderStyle.bottom);
		instance._setSelect(instance._borderLeftStyle, borderData.borderStyle.left);

		instance._setInput(instance._borderTopColor, borderData.borderColor.top);
		instance._setInput(instance._borderRightColor, borderData.borderColor.right);
		instance._setInput(instance._borderBottomColor, borderData.borderColor.bottom);
		instance._setInput(instance._borderLeftColor, borderData.borderColor.left);

		// Spacing

		instance._setCheckbox(instance._ufaPadding, spacingData.padding.sameForAll);
		instance._setCheckbox(instance._ufaMargin, spacingData.margin.sameForAll);

		instance._setInput(instance._paddingTopInt, spacingData.padding.top);
		instance._setSelect(instance._paddingTopUnit, spacingData.padding.topUnit);
		instance._setInput(instance._paddingRightInt, spacingData.padding.right);
		instance._setSelect(instance._paddingRightUnit, spacingData.padding.rightUnit);
		instance._setInput(instance._paddingBottomInt, spacingData.padding.bottom);
		instance._setSelect(instance._paddingBottomUnit, spacingData.padding.bottomUnit);
		instance._setInput(instance._paddingLeftInt, spacingData.padding.left);
		instance._setSelect(instance._paddingLeftUnit, spacingData.padding.leftUnit);

		instance._setInput(instance._marginTopInt, spacingData.margin.top);
		instance._setSelect(instance._marginTopUnit, spacingData.margin.topUnit);
		instance._setInput(instance._marginRightInt, spacingData.margin.right);
		instance._setSelect(instance._marginRightUnit, spacingData.margin.rightUnit);
		instance._setInput(instance._marginBottomInt, spacingData.margin.bottom);
		instance._setSelect(instance._marginBottomUnit, spacingData.margin.bottomUnit);
		instance._setInput(instance._marginLeftInt, spacingData.margin.left);
		instance._setSelect(instance._marginLeftUnit, spacingData.margin.leftUnit);

		// Advanced CSS

		var customStyleBlock = jQuery('#lfr-custom-css-block-' + instance._currentPortletId);

		var customStyles = customStyleBlock.html();

		if (customStyles == '' || customStyles == null) {
			customStyles = objData.advancedData.customCSS;
		}

		instance._setTextarea(instance._customCSS, customStyles);
	},

	_setInput: function(obj, value) {
		var instance = this;

		obj.val(value);
	},

	_setSelect: function(obj, value) {
		var instance = this;

		if (value != '') {
			obj.find('option[@value=' + value + ']').attr('selected', 'selected');
		}
	},

	_setTextarea: function(obj, value) {
		var instance = this;

		instance._setInput(obj, value);
	},

	_spacingStyles: function() {
		var instance = this;

		var portlet = instance._currentPortlet;

		var ufaPadding = instance._ufaPadding;
		var ufaMargin = instance._ufaMargin;

		var spacingData = instance._objData.spacingData;

		// Padding

		var pTop = instance._paddingTopInt;
		var pTopUnit = instance._paddingTopUnit;
		var pRight = instance._paddingRightInt;
		var pRightUnit = instance._paddingRightUnit;
		var pBottom = instance._paddingBottomInt;
		var pBottomUnit = instance._paddingBottomUnit;
		var pLeft = instance._paddingLeftInt;
		var pLeftUnit = instance._paddingLeftUnit;

		var changePadding = function() {
			var styling = {};

			var padding = instance._getCombo(pTop, pTopUnit);

			styling = {padding: padding.both};

			var ufa = ufaPadding.is(':checked');

			spacingData.padding.top = top.input;
			spacingData.padding.topUnit = top.selectBox;

			spacingData.padding.sameForAll = ufa;

			if (!ufa) {
				var extStyling = {};

				extStyling.paddingTop = styling.padding;

				var right = instance._getCombo(pRight, pRightUnit);
				var bottom = instance._getCombo(pBottom, pBottomUnit);
				var left = instance._getCombo(pLeft, pLeftUnit);

				extStyling.paddingRight = right.both;
				extStyling.paddingBottom = bottom.both;
				extStyling.paddingLeft = left.both;

				styling = extStyling;

				spacingData.padding.right = right.input;
				spacingData.padding.rightUnit = right.selectBox;

				spacingData.padding.bottom = bottom.input;
				spacingData.padding.bottomUnit = bottom.selectBox;

				spacingData.padding.left = left.input;
				spacingData.padding.leftUnit = left.selectBox;
			}

			portlet.css(styling);
		};

		pTop.unbind().blur(changePadding);
		pRight.unbind().blur(changePadding);
		pBottom.unbind().blur(changePadding);
		pLeft.unbind().blur(changePadding);

		pTop.unbind().keyup(changePadding);
		pRight.unbind().keyup(changePadding);
		pBottom.unbind().keyup(changePadding);
		pLeft.unbind().keyup(changePadding);

		pTopUnit.unbind().change(changePadding);
		pRightUnit.unbind().change(changePadding);
		pBottomUnit.unbind().change(changePadding);
		pLeftUnit.unbind().change(changePadding);

		ufaPadding.unbind().click(changePadding);

		// Margin

		var mTop = instance._marginTopInt;
		var mTopUnit = instance._marginTopUnit;
		var mRight = instance._marginRightInt;
		var mRightUnit = instance._marginRightUnit;
		var mBottom = instance._marginBottomInt;
		var mBottomUnit = instance._marginBottomUnit;
		var mLeft = instance._marginLeftInt;
		var mLeftUnit = instance._marginLeftUnit;

		var changeMargin = function() {
			var styling = {};

			var margin = instance._getCombo(mTop, mTopUnit);

			styling = {margin: margin.both};

			var ufa = ufaMargin.is(':checked');

			spacingData.margin.top = margin.input;
			spacingData.margin.topUnit = margin.selectBox;

			spacingData.margin.sameForAll = ufa;

			if (!ufa) {
				var extStyling = {};

				extStyling.marginTop = styling.margin;

				var right = instance._getCombo(mRight, mRightUnit);
				var bottom = instance._getCombo(mBottom, mBottomUnit);
				var left = instance._getCombo(mLeft, mLeftUnit);

				extStyling.marginRight = right.both;
				extStyling.marginBottom = bottom.both;
				extStyling.marginLeft = left.both;

				styling = extStyling;

				spacingData.margin.right = right.input;
				spacingData.margin.rightUnit = right.selectBox;

				spacingData.margin.bottom = bottom.input;
				spacingData.margin.bottomUnit = bottom.selectBox;

				spacingData.margin.left = left.input;
				spacingData.margin.leftUnit = left.selectBox;
			}

			portlet.css(styling);
		};

		mTop.unbind().blur(changeMargin);
		mRight.unbind().blur(changeMargin);
		mBottom.unbind().blur(changeMargin);
		mLeft.unbind().blur(changeMargin);

		mTop.unbind().keyup(changeMargin);
		mRight.unbind().keyup(changeMargin);
		mBottom.unbind().keyup(changeMargin);
		mLeft.unbind().keyup(changeMargin);

		mTopUnit.unbind().change(changeMargin);
		mRightUnit.unbind().change(changeMargin);
		mBottomUnit.unbind().change(changeMargin);
		mLeftUnit.unbind().change(changeMargin);

		ufaMargin.unbind().click(changeMargin);
	},

	_textStyles: function() {
		var instance = this;

		var portlet = instance._currentPortlet;
		var fontFamily = instance._fontFamily;
		var fontBold = instance._fontWeight;
		var fontItalic = instance._fontStyle;
		var fontSize = instance._fontSize;
		var fontColor = instance._fontColor;
		var textAlign = instance._textAlign;
		var textDecoration = instance._textDecoration;
		var wordSpacing = instance._wordSpacing;
		var leading = instance._leading;
		var tracking = instance._tracking;

		var textData = instance._objData.textData;

		// Font family

		fontFamily.unbind().change(
			function(){
				var fontFamily = this.options[this.selectedIndex].value;

				portlet.css('font-family', fontFamily);

				textData.fontFamily = fontFamily;
			}
		);

		// Font style

		fontBold.unbind().click(
			function(){
			var style = 'normal';
				if (this.checked){
					style = 'bold';
				}

				portlet.css('font-weight', style);

				textData.fontWeight = style;
			}
		);

		fontItalic.unbind().click(
			function(){
				var style = 'normal';

				if (this.checked){
					style = 'italic';
				}

				portlet.css('font-style', style);

				textData.fontStyle = style;
			}
		);

		// Font size

		fontSize.unbind().change(
			function(){
				var fontSize = this.options[this.selectedIndex].value;

				portlet.css('font-size', fontSize)
			}
		);

		// Font color

		var setColor = function(obj){
			var color = obj.value;

			if (color != '') {
				portlet.css('color', color);

				textData.color = color;
			}
		};

		var colorPicker = new Liferay.ColorPicker(
			{
				context: jQuery('#portlet-set-properties')[0],
				item: fontColor[0],
				onChange: function() {
					setColor(fontColor[0]);
				}
			}
		);

		fontColor.unbind().blur(
			function() {
				setColor(this);
			}
		);

		// Text alignment

		textAlign.unbind().change(
			function(){
				var textAlign = this.options[this.selectedIndex].value;

				portlet.css('text-align', textAlign);

				textData.textAlign = textAlign;
			}
		);

		// Text decoration

		textDecoration.unbind().change(
			function(){
				var decoration = this.options[this.selectedIndex].value;

				portlet.css('text-decoration', decoration);

				textData.textDecoration = decoration;
			}
		);

		// Word spacing

		wordSpacing.unbind().change(
			function(){
				var spacing = this.options[this.selectedIndex].value;

				portlet.css('word-spacing', spacing);

				textDecoration.wordSpacing = spacing;
			}
		);

		// Line height

		leading.unbind().change(
			function(){
				var leading = this.options[this.selectedIndex].value;

				portlet.css('line-height', leading);

				textDecoration.lineHeight = leading;
			}
		);

		// Letter spacing

		tracking.unbind().change(
			function(){
				var tracking = this.options[this.selectedIndex].value;

				portlet.css('letter-spacing', tracking);

				textDecoration.letterSpacing = tracking;
			}
		);
	}
};