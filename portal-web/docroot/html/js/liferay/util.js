Liferay.Util = {
	submitCountdown: 0,

	actsAsAspect: function(object) {
		object.yield = null;
		object.rv = {};

		object.before = function(method, f) {
			var original = eval('this.' + method);

			this[method] = function() {
				f.apply(this, arguments);

				return original.apply(this, arguments);
			};
		};

		object.after = function(method, f) {
			var original = eval('this.' + method);

			this[method] = function() {
				this.rv[method] = original.apply(this, arguments);

				return f.apply(this, arguments);
			};
		};

		object.around = function(method, f) {
			var original = eval('this.' + method);

			this[method] = function() {
				this.yield = original;

				return f.apply(this, arguments);
			};
		};
	},

	addInputFocus: function() {
		var instance = this;

		AUI().use(
			'node',
			function(A) {
				var handleFocus = function(event) {
					var target = event.target;

					var tagName = target.get('tagName');

					if (tagName) {
						tagName = tagName.toLowerCase();
					}

					var nodeType = target.get('type');

					if (((tagName == 'input') && /text|password/.test(nodeType)) ||
						(tagName == 'textarea')) {

						var action = 'addClass';

						if (/blur|focusout/.test(event.type)) {
							action = 'removeClass';
						}

						target[action]('focus');
					}
				};

				A.on('focus', handleFocus, document);
				A.on('blur', handleFocus, document);
			}
		);

		instance.addInputFocus = function(){};
	},

	addInputType: function(el) {
		var instance = this;

		instance.addInputType = function() {};

		if (Liferay.Browser.isIe() && Liferay.Browser.getMajorVersion() < 7) {
			instance.addInputType = function(el) {
				var item;

				if (el) {
					if (typeof el == 'object') {
						item = jQuery(el);
					}
					else {
						item = jQuery('#' + el);
					}
				}
				else {
					item = document.body;
				}

				jQuery('input', item).each(function() {
					var current = jQuery(this);
					var type = this.type || 'text';

					current.addClass(type);
				});
			};
		}

		return instance.addInputType(el);
	},

	addParams: function(params, url) {
		var instance = this;

		if (typeof params == 'object') {
			params = jQuery.param(params);
		}
		else {
			params = jQuery.trim(params);
		}

		if (params != '') {
			var loc = url || location.href;
			var anchorHash, finalUrl;

			if (loc.indexOf('#') > -1) {
				var locationPieces = loc.split('#');
				loc = locationPieces[0];
				anchorHash = locationPieces[1];
			}

			if (loc.indexOf('?') == -1) {
				params = '?' + params;
			}
			else {
				params = '&' + params;
			}

			if (loc.indexOf(params) == -1) {
				finalUrl = loc + params;

				if (anchorHash) {
					finalUrl += '#' + anchorHash;
				}
				if (!url) {
					location.href = finalUrl;
				}
				return finalUrl;
			}
		}
	},

	check: function(form, name, checked) {
		jQuery('input[name=' + name + ']:checkbox',form).attr('checked', checked);
	},

	checkAll: function(form, name, allBox) {
		var inputs;

		if (Liferay.Util.isArray(name)) {
			var names = 'input[name='+ name.join(']:checkbox,input[name=') + ']:checkbox';

			inputs = jQuery(names, form);
		}
		else {
			inputs = jQuery('input[name=' + name + ']:checkbox', form);
		}

		inputs.attr('checked', allBox.checked);
	},

	checkAllBox: function(form, name, allBox) {
		var totalBoxes = 0;
		var totalOn = 0;
		var inputs;

		if (Liferay.Util.isArray(name)) {
			var names = 'input[name='+ name.join(']:checkbox,input[name=') + ']:checkbox';

			inputs = jQuery(names, form);
		}
		else {
			inputs = jQuery('input[name=' + name + ']:checkbox', form);
		}

		inputs = inputs.not(allBox);

		totalBoxes = inputs.length;
		totalOn = inputs.filter(':checked').length;

		allBox.checked = (totalBoxes == totalOn);
	},

	checkTab: function(box) {
		if ((document.all) && (event.keyCode == 9)) {
			box.selection = document.selection.createRange();
			setTimeout('Liferay.Util.processTab("' + box.id + '")', 0);
		}
	},

	createFlyouts: function(options) {
		var instance = this;

		AUI().use(
			'delayed-task',
			function(A) {
				options = options || {};

				var flyout, containers;

				var containerFilter = function() {
					return (jQuery('ul', this).length != 0);
				};

				if (!options.container) {
					flyout = jQuery('.lfr-flyout');
					containers = flyout.find('li').filter(containerFilter);
				}
				else {
					flyout = jQuery('li', options.container);
					containers = flyout.filter(containerFilter);
				}

				containers.addClass('lfr-flyout');
				containers.addClass('has-children lfr-flyout-has-children');

				if (!options.container) {
					containers = containers.add(flyout);
				}

				var hideTask = new A.DelayedTask(
					function() {
						showTask.cancel();

						containers.find('> ul').hide();

						if (options.mouseOut) {
							options.mouseOut.apply(event.target, [event]);
						}
					}
				);

				var showTask = new A.DelayedTask(
					function() {
						hideTask.cancel();

						containers.find('> ul').show();

						if (options.mouseOver) {
							options.mouseOver.apply(event.target, [event]);
						}
					}
				);

				containers.mouseover(
					function(event) {
						showTask.delay(0, null, null, event);
					}
				);

				containers.mouseout(
					function(event) {
						hideTask.delay(300, null, null, event);
					}
				);
			}
		)
	},

	defaultValue: function(obj, defaultValue) {
		var inputs = jQuery(obj);

		inputs.each(function() {
			var input = jQuery(this);

			input.unbind('.lfrDefaultValue');

			if (!input.val().length) {
				input.val(defaultValue);
			}

			input.bind(
				'focus.lfrDefaultValue',
				function() {
					if (this.value == defaultValue) {
						this.value = '';
					}
				}
			);

			input.bind(
				'blur.lfrDefaultValue',
				function() {
					if (!this.value) {
						this.value = defaultValue;
					}
				}
			);
		});
	},

	disableElements: function(obj) {
		var el = jQuery(obj);
		var children = el.find('*');

		var emptyFn = function() { return false; };

		var defaultEvents = function(el) {
			el.onclick = emptyFn;
			el.onmouseover = emptyFn;
			el.onmouseout = emptyFn;
			jQuery.event.remove(el);
		};

		var ieEvents = function(el) {
			el.onmouseenter = emptyFn;
			el.onmouseleave = emptyFn;
		};

		var removeEvents = defaultEvents;

		if (Liferay.Browser.isIe()) {
			removeEvents = function(el) {
				defaultEvents(el);
				ieEvents(el);
			};
		}

		for (var i = children.length - 1; i >= 0; i--) {
			var item = children[i];
			var nodeName = item.nodeName.toLowerCase();

			item.style.cursor = 'default';

			removeEvents(item);

			if (nodeName == 'a') {
				item.href = 'javascript:;';
			}
			else if (nodeName == 'input' || nodeName == 'select' || nodeName == 'script') {
				item.disabled = true;
			}
			else if (nodeName == 'form') {
				item.action = '';
				item.onsubmit = emptyFn;
			}
		};
	},

	disableEsc: function() {
		if ((document.all) && (event.keyCode == 27)) {
			event.returnValue = false;
		}
	},

	disableMatchedKeys: function(input, pattern, allowedKeyCodes) {
		input = jQuery(input);

		var blockChars = function(event) {
			var charCode = event.charCode;
			var keyCode = event.keyCode;

			if (event.shiftKey) {
				return false;
			}

			if (allowedKeyCodes && jQuery.inArray(keyCode, allowedKeyCodes) != -1) {
				return true;
			}

			var typed = String.fromCharCode(charCode || keyCode);
			var regex = new RegExp(pattern);

			return !(regex.test(typed));
		};

		input.unbind('.disableMatchedKeys');
		input.bind('keypress.disableMatchedKeys', blockChars);
	},

	disableSelection: function(el) {
		jQuery(el).attr('unselectable', 'on').css('MozUserSelect', 'none');
	},

	disableTextareaTabs: function(textarea) {
		var instance = this;

		if (!textarea.jquery) {
			textarea = jQuery(textarea);
		}

		if (textarea.attr('textareatabs') != 'enabled') {
			textarea.attr('textareatabs', 'disabled');
			textarea.unbind('keydown.liferay', Liferay.Util.textareaTabs);
		}
	},

	enableSelection: function(el) {
		jQuery(el).attr('unselectable', 'off').css('MozUserSelect', '');
	},

	enableTextareaTabs: function(textarea) {
		var instance = this;

		if (!textarea.jquery) {
			textarea = jQuery(textarea);
		}

		if (textarea.attr('textareatabs') != 'enabled') {
			textarea.attr('textareatabs', 'enabled');
			textarea.bind('keydown.liferay', Liferay.Util.textareaTabs);
		}
	},

	endsWith: function(str, x) {
		return (str.lastIndexOf(x) === (str.length - x.length));
	},

	escapeHTML: function(str) {
		return str.replace(
			/<|>|&/gi,
			function(match) {
				var str = '';

				if (match == '<') {
					str = '&lt;';
				}
				else if (match == '>') {
					str = '&gt;';
				}
				else if (match == '&') {
					str = '&amp;';
				}
				else if (match == '\"') {
					str = '&#034;';
				}
				else if (match == '\'') {
					str = '&#039;';
				}

				return str;
			}
		);
	},

	focusFormField: function(el, caretPosition) {
		var instance = this;

		instance.addInputFocus();

		AUI().ready(
			'event',
			'node',
			function(A) {
				var interacting = false;

				var clickHandle = A.getDoc().on(
					'click',
					function(event) {
						interacting = true;

						clickHandle.detach();
					}
				);

				if (!interacting) {
					el = A.get(el);

					try {
						el.focus();
					}
					catch (e) {
					}
				}
			}
		);
	},

	forcePost: function(link) {
		var instance = this;

		if (link) {
			var url = jQuery(link).attr('href');

			submitForm(document.hrefFm, url);
		}
	},

	getColumnId: function(str) {
		var columnId = str.replace(/layout-column_/, '');

		return columnId;
	},

	getPortletId: function(portletId) {
		portletId = portletId.replace(/^p_p_id_/i, '');
		portletId = portletId.replace(/_$/, '');

		return portletId;
	},

	getSelectedRadioValue: function(col) {
		return jQuery(col).filter(':checked').val() || '';
	},

	getURLWithSessionId: function(url) {
		if (document.cookie && (document.cookie.length > 0)) {
			return url;
		}

		// LEP-4787

		var x = url.indexOf(';');

		if (x > -1) {
			return url;
		}

		var sessionId = ';jsessionid=' + themeDisplay.getSessionId();

		x = url.indexOf('?');

		if (x > -1) {
			return url.substring(0, x) + sessionId + url.substring(x);
		}

		// In IE6, http://www.abc.com;jsessionid=XYZ does not work, but
		// http://www.abc.com/;jsessionid=XYZ does work.

		x = url.indexOf('//');

		if (x > -1) {
			var y = url.lastIndexOf('/');

			if (x + 1 == y) {
				return url + '/' + sessionId;
			}
		}

		return url + sessionId;
	},

	/**
	 * OPTIONS
	 *
	 * Required
	 * button {string|object}: The button that opens the popup when clicked.
	 * height {number}: The height to set the popup to.
	 * textarea {string}: the name of the textarea to auto-resize.
	 * url {string}: The url to open that sets the editor.
	 * width {number}: The width to set the popup to.
	 */

	inlineEditor: function(options) {
		var instance = this;

		if (options.url && options.button) {
			var url = options.url;
			var button = options.button;
			var width = options.width || 680;
			var height = options.height || 640;
			var textarea = options.textarea;
			var clicked = false;

			var editorButton = jQuery(button);
			var popup = null;

			editorButton.click(
				function(event) {
					if (!clicked) {
						var form = jQuery([]);

						AUI().use(
							'dialog',
							function(A) {
								popup = new A.Dialog(
									{
										title: Liferay.Language.get('editor'),
										height: 640,
										width: 680,
										io: {
											url: url + '&rt=' + Liferay.Util.randomInt()
										}
									}
								)
								.render();
							}
						);

						clicked = true;
					}
					else {
						popup.show();
						popup.io.refresh();
					}
				}
			);
		}
	},

	isArray: function(object) {
		return !!(window.Array && object.constructor == window.Array);
	},

	listChecked: function(form) {
		var s = [];
		var inputs = jQuery('input[value!=]:checked:checkbox', form);

		inputs.each(
			function() {
				s.push(this.value);
			}
		);

		return s.join(',');
	},

	listCheckedExcept: function(form, except) {
		var s = [];
		var inputs = jQuery('input[value!=][name!="' + except + '"]:checked:checkbox', form);

		inputs.each(
			function() {
				s.push(this.value);
			}
		);

		return s.join(',');
	},

	listSelect: function(box, delimeter) {
		var s = [];

		delimeter = delimeter || ',';

		if (box == null) {
			return '';
		}

		var opts = jQuery(box).find('option[value!=]');

		opts.each(
			function() {
				s.push(this.value);
			}
		);

		if (s[0] == '.none') {
			return '';
		}
		else {
			return s.join(delimeter);
		}
	},

	listUncheckedExcept: function(form, except) {
		var s = [];
		var inputs = jQuery('input[value!=][name!="' + except + '"]:checkbox:not(:checked)', form);

		inputs.each(
			function() {
				s.push(this.value);
			}
		);

		return s.join(',');
	},

	moveItem: function(fromBox, toBox, sort) {
		if (fromBox.selectedIndex >= 0) {
			var toSelect = jQuery(toBox);
			var selectedOption = jQuery(fromBox).find('option:selected');

			toSelect.append(selectedOption);
		}

		if (selectedOption.text() != '' && sort == true) {
			Liferay.Util.sortBox(toBox);
		}
	},

	portletTitleEdit: function(options) {
		var instance = this;

		var obj = options.obj;
		var plid = options.plid;
		var doAsUserId = options.doAsUserId;
		var portletId = options.portletId;
		var url = options.url;

		var title = obj.one('.portlet-title');

		var re = new RegExp('<\/?[^>]+>|\n|\r|\t', 'gim');

		if (title && !title.hasClass('not-editable')) {
			AUI().use(
				'editable',
				function(A) {
					var editableTitle = new A.Editable(
						{
							after: {
								contentTextChange: function(event) {
									var instance = this;

									if (!event.initial) {
										Liferay.Util.savePortletTitle(
											{
												plid: plid,
												doAsUserId: doAsUserId,
												portletId: portletId,
												title: event.newVal
											}
										);
									}
								},

								startEditing: function(event) {
									var instance = this;

									var value = instance.get('node').get('innerHTML');

									instance._LFR_cruft = value.match(re);
								}
							},
							cssClass: 'lfr-portlet-title-editable',
							formatOutput: function(value) {
								var instance = this;

								value = instance._toHTML(value);

								var cruft = instance._LFR_cruft || [];

								cruft = cruft.join('');

								return cruft + value;
							},
							node: title
						}
					);
				}
			);
		}
	},

	processTab: function(id) {
		document.all[id].selection.text = String.fromCharCode(9);
		document.all[id].focus();
	},

	randomInt: function() {
		return (Math.ceil(Math.random() * (new Date).getTime()));
	},

	randomMinMax: function(min, max) {
		return (Math.round(Math.random() * (max - min))) + min;
	},

	removeItem: function(box, value) {
		var selectEl = jQuery(box);

		if (!value) {
			selectEl.find('option:selected').remove();
		}
		else {
			selectEl.find('option[value=' + value + ']:selected').remove();
		}
	},

	reorder: function(box, down) {
		var si = box.selectedIndex;

		if (si == -1) {
			box.selectedIndex = 0;
		}
		else {
			sText = box.options[si].text;
			sValue = box.options[si].value;

			if ((box.options[si].value > '') && (si > 0) && (down == 0)) {
				box.options[si].text = box.options[si - 1].text;
				box.options[si].value = box.options[si - 1].value;
				box.options[si - 1].text = sText;
				box.options[si - 1].value = sValue;
				box.selectedIndex--;
			}
			else if ((si < box.length - 1) && (box.options[si + 1].value > '') && (down == 1)) {
				box.options[si].text = box.options[si + 1].text;
				box.options[si].value = box.options[si + 1].value;
				box.options[si + 1].text = sText;
				box.options[si + 1].value = sValue;
				box.selectedIndex++;
			}
			else if (si == 0) {
				for (var i = 0; i < (box.length - 1); i++) {
					box.options[i].text = box.options[i + 1].text;
					box.options[i].value = box.options[i + 1].value;
				}

				box.options[box.length - 1].text = sText;
				box.options[box.length - 1].value = sValue;

				box.selectedIndex = box.length - 1;
			}
			else if (si == (box.length - 1)) {
				for (var j = (box.length - 1); j > 0; j--) {
					box.options[j].text = box.options[j - 1].text;
					box.options[j].value = box.options[j - 1].value;
				}

				box.options[0].text = sText;
				box.options[0].value = sValue;

				box.selectedIndex = 0;
			}
		}
	},

	resizeTextarea: function(elString, usingRichEditor, resizeToInlinePopup) {
		var init = function() {
			var el = jQuery('#' + elString);

			if (!el.length) {
				el = jQuery('textarea[name=' + elString + ']');
			}

			if (el.length) {
				var pageBody;

				if (resizeToInlinePopup) {
					pageBody = el.parents('.aui-body:first');
				}
				else {
					pageBody = jQuery('body');
				}

				var resize = function() {
					var pageBodyHeight = pageBody.height();

					if (usingRichEditor) {
						try {
							if (!el.is('iframe')) {
								el = eval(elString);

								if (!el.jquery) {
									el = jQuery(el);
								}
							}
						}
						catch (e) {
						}
					}

					var diff = 170;

					if (!resizeToInlinePopup) {
						diff = 100;
					}

					el.css(
						{
							height: (pageBodyHeight - diff) + 'px',
							width: '98%'
						}
					);
				};

				resize();

				if (resizeToInlinePopup) {
					jQuery(document).bind('popupResize.liferay', resize);
				}
				else {
					jQuery(window).resize(resize);
				}
			}
		};

		AUI().ready(init);
	},

	resubmitCountdown: function(formName) {
		if (Liferay.Util.submitCountdown > 0) {
			Liferay.Util.submitCountdown--;

			setTimeout('Liferay.Util.resubmitCountdown("' + formName + '")', 1000);
		}
		else {
			Liferay.Util.submitCountdown = 0;

			if (!Liferay.Browser.isMozilla()) {
				document.body.style.cursor = 'auto';
			}

			var form = document.forms[formName];

			for (var i = 0; i < form.length; i++) {
				var e = form.elements[i];

				if (e.type && (e.type.toLowerCase() == 'button' || e.type.toLowerCase() == 'reset' || e.type.toLowerCase() == 'submit')) {
					e.disabled = false;
				}
			}
		}
	},

	savePortletTitle: function(params) {
		var defaultParams = {
			plid: 0,
			doAsUserId: 0,
			portletId: 0,
			title: '',
			url: themeDisplay.getPathMain() + '/portlet_configuration/update_title'
		};

		var settings = jQuery.extend(defaultParams, params);

		jQuery.ajax(
			{
				url: settings.url,
				data: {
					p_l_id: settings.plid,
					doAsUserId: settings.doAsUserId,
					portletId: settings.portletId,
					title: settings.title
				}
			}
		);
	},

	selectAndCopy: function(el) {
		el.focus();
		el.select();

		if (document.all) {
			var textRange = el.createTextRange();

			textRange.execCommand('copy');
		}
	},

	setBox: function(oldBox, newBox) {
		for (var i = oldBox.length - 1; i > -1; i--) {
			oldBox.options[i] = null;
		}

		for (var i = 0; i < newBox.length; i++) {
			oldBox.options[i] = new Option(newBox[i].value, i);
		}

		oldBox.options[0].selected = true;
	},

	setSelectedValue: function(col, value) {
		jQuery('option[value=' + value + ']', col).attr('selected', true);
	},

	showCapsLock: function(event, span) {
		var keyCode = event.keyCode ? event.keyCode : event.which;
		var shiftKey = event.shiftKey ? event.shiftKey : ((keyCode == 16) ? true : false);

		if (((keyCode >= 65 && keyCode <= 90) && !shiftKey) ||
			((keyCode >= 97 && keyCode <= 122) && shiftKey)) {

			document.getElementById(span).style.display = '';
		}
		else {
			document.getElementById(span).style.display = 'none';
		}
	},

	sortBox: function(box) {
		var newBox = [];

		for (var i = 0; i < box.length; i++) {
			newBox[i] = [box[i].value, box[i].text];
		}

		newBox.sort(Liferay.Util.sortByAscending);

		var boxObj = jQuery(box);

		boxObj.find('option').remove();

		jQuery.each(
			newBox,
			function(key, value) {
				boxObj.append('<option value="' + value[0] + '">' + value[1] + '</option>');
			}
		);

		if (Liferay.Browser.isIe()) {
			var currentWidth = boxObj.css('width');

			if (currentWidth == 'auto') {
				boxObj.css('width', 'auto');
			}
		}
	},

	sortByAscending: function(a, b) {
		a = a[1].toLowerCase();
		b = b[1].toLowerCase();

		if (a > b) {
			return 1;
		}

		if (a < b) {
			return -1;
		}

		return 0;
	},

	startsWith: function(str, x) {
		return (str.indexOf(x) === 0);
	},

	/**
	 * OPTIONS
	 *
	 * Required
	 * popup {string|object}: A jQuery selector or DOM element of the popup that contains the editor.
	 * textarea {string}: the name of the textarea to auto-resize.
	 * url {string}: The url to open that sets the editor.
	 */

	switchEditor: function(options) {
		var instance = this;

		if (options.url && options.popup) {
			var url = options.url;
			var popup = options.popup;
			var textarea = options.textarea;

			if (!popup.jquery) {
				popup = jQuery(popup);
			}

			var popupMessage = popup;

			jQuery.ajax(
				{
					url: url,
					beforeSend: function() {
						popupMessage.empty();
						popupMessage.append('<div class="loading-animation"></div>');
					},
				  	success: function(message) {
						popupMessage.empty();
						popupMessage.append(message);
				 	}
				}
			);
		}
	},

	textareaTabs: function(event) {
		var el = this;
		var pressedKey = event.which;

		if(pressedKey == 9 || (Liferay.Browser.isSafari() && pressedKey == 25)) {
			event.preventDefault();
			event.stopPropagation();

			var oldscroll = el.scrollTop;

			if (el.setSelectionRange) {
				var caretPos = el.selectionStart + 1;
				var elValue = el.value;

				el.value = elValue.substring(0, el.selectionStart) + '\t' + elValue.substring(el.selectionEnd, elValue.length);

				setTimeout(
					function() {
						el.focus();
						el.setSelectionRange(caretPos, caretPos);
					}, 0);

			}
			else {
				document.selection.createRange().text='\t';
			}

	        el.scrollTop = oldscroll;

			return false;
	    }
	},

	toggleByIdSpan: function(obj, id) {
		jQuery('#' + id).toggle();

		var spans = jQuery(obj).find('span');

		spans.toggle();
	},

	toggle: function(obj, returnState, displayType) {
		if (typeof obj == 'string') {
			obj = '#' + obj;
		}

		var el = jQuery(obj);
		var hidden = el.toggle().is(':visible');

		if (displayType) {
			el.css('display', displayType);
			hidden = el.is(':visible');
		}

		if (returnState) {
			return hidden;
		}
	},

	toggleBoxes: function(checkBoxId, toggleBoxId) {
		var checkBox = jQuery('#' + checkBoxId);
		var toggleBox = jQuery('#' + toggleBoxId);

		if (!checkBox.is(':checked')) {
			toggleBox.hide();
		}

		checkBox.click(
			function() {
				toggleBox.toggle();
			}
		);
	},

	toggleControls: function() {
		var instance = this;

		var trigger = jQuery('.toggle-controls');
		var docBody = jQuery(document.body);
		var hiddenClass = 'controls-hidden';
		var visibleClass = 'controls-visible';
		var currentClass = visibleClass;

		if (Liferay._editControlsState != 'visible') {
			currentClass = hiddenClass;
		}

		docBody.addClass(currentClass);

		trigger.click(
			function(event) {
				docBody.toggleClass(visibleClass).toggleClass(hiddenClass);

				Liferay._editControlsState = (docBody.is('.' + visibleClass) ? 'visible' : 'hidden');

				jQuery.ajax(
					{
						url: themeDisplay.getPathMain() + '/portal/session_click',
						data: {
							'liferay_toggle_controls': Liferay._editControlsState
						}
					}
				);
			}
		);
	},

	toggleSelectBox: function(selectBoxId, value, toggleBoxId) {
		var selectBox = jQuery('#' + selectBoxId);
		var toggleBox = jQuery('#' + toggleBoxId);

		if (selectBox.val() != value) {
			toggleBox.hide();
		}
		else {
			toggleBox.show();
		}

		selectBox.change(
			function(event) {
				if (selectBox.val() != value) {
					toggleBox.hide();
				}
				else {
					toggleBox.show();
				}
			}
		);
	},

	uncamelize: function(value, separator) {
		separator = separator || ' ';

		value = value.replace(/([a-z])([A-Z])([a-z])/g, '$1' + separator + '$2$3');
		value = value.replace(/([a-z])([A-Z])/g, '$1' + separator + '$2');

		return value;
	},

	unescapeHTML: function(str) {
		return str.replace(
			/&lt;|&gt;|&amp;|&#034;|&#039;/gi,
			function(match) {
				var str = '';

				if (match == '&lt;') {
					str = '<';
				}
				else if (match == '&gt;') {
					str = '>';
				}
				else if (match == '&amp;') {
					str = '&';
				}
				else if (match == '&#034;') {
					str = '\"';
				}
				else if (match == '&#039;') {
					str = '\'';
				}

				return str;
			}
		);
	},

	viewport: {
		frame: function() {
			var instance = this;
			var viewport = jQuery(window);

			var x = viewport.width();
			var y = viewport.height();

			return {x: x, y: y};
		},
		page: function() {
			var instance = this;
			var viewport = jQuery(document);

			var x = viewport.width();
			var y = viewport.height();

			return {x: x, y: y};
		},
		scroll: function() {
			var instance = this;
			var viewport = jQuery(window);

			var x = viewport.scrollLeft();
			var y = viewport.scrollTop();

			return {x: x, y: y};
		}
	}
};

function submitForm(form, action, singleSubmit) {
	if (Liferay.Util.submitCountdown == 0) {
		Liferay.Util.submitCountdown = 10;

		setTimeout('Liferay.Util.resubmitCountdown("' + form.name + '")', 1000);

		if ((singleSubmit == null) || singleSubmit) {
			Liferay.Util.submitCountdown++;

			var inputs = jQuery('input[type=button], input[type=reset], input[type=submit]', form);

			inputs.attr('disabled', true);
			inputs.fadeTo(50, 0.5);
		}

		if (action != null) {
			form.action = action;
		}

		if (!Liferay.Browser.isMozilla()) {
			document.body.style.cursor = 'wait';
		}

		Liferay.fire('submitForm', {form: form});

		form.submit();
	}
}

// 0-200: Theme Developer
// 200-400: Portlet Developer
// 400+: Liferay

Liferay.zIndex = {
	DOCK:			10,
	DOCK_PARENT:	20,
	ALERT:			430,
	DROP_AREA:		440,
	DROP_POSITION:	450,
	DRAG_ITEM:		460,
	TOOLTIP:		470
};