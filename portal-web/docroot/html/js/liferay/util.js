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
			'aui-base',
			function(A) {
				var handleFocus = function(event) {
					var target = event.target;

					var tagName = target.get('tagName');

					if (tagName) {
						tagName = tagName.toLowerCase();
					}

					var nodeType = target.get('type');

					if (((tagName == 'input') && (/text|password/).test(nodeType)) ||
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

		var A = AUI();

		instance.addInputType = function() {};

		if (Liferay.Browser.isIe() && Liferay.Browser.getMajorVersion() < 7) {
			instance.addInputType = function(el) {
				var item;

				if (el) {
					el = A.one(el);
				}
				else {
					el = A.one(document.body);
				}

				var defaultType = 'text';

				el.all('input').each(
					function(item, index, collection) {
						var type = item.get('type') || defaultType;

						item.addClass(type);
					}
				);
			};
		}

		return instance.addInputType(el);
	},

	addParams: function(params, url) {
		var instance = this;

		var A = AUI().use('querystring-stringify-simple');

		if (A.Lang.isObject(params)) {
			params = A.QueryString.stringify(params);
		}
		else {
			params = A.Lang.trim(params);
		}

		if (params) {
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
		AUI().use(
			'node',
			function(A) {
				var checkbox = A.one(form[name]);

				if (checkbox) {
					checkbox.set('checked', checked);
				}
			}
		);
	},

	checkAll: function(form, name, allBox) {
		AUI().use(
			'node',
			function(A) {
				var selector;

				if (Liferay.Util.isArray(name)) {
					selector = 'input[name='+ name.join('], input[name=') + ']';
				}
				else {
					selector = 'input[name=' + name + ']';
				}

				form = A.one(form);

				form.all(selector).set('checked', A.one(allBox).get('checked'));
			}
		);
	},

	checkAllBox: function(form, name, allBox) {
		var A = AUI();

		var totalBoxes = 0;
		var totalOn = 0;
		var inputs = A.one(form).all('input[type=checkbox]');

		allBox = A.one(allBox);

		if (!A.Lang.isArray(name)) {
			name = [name];
		}

		inputs.each(
			function(item, index, collection) {
				if (!item.compareTo(allBox)) {
					if (A.Array.indexOf(name, item.getAttribute('name')) > -1) {
						totalBoxes++;
					}

					if (item.get('checked')) {
						totalOn++;
					}
				}
			}
		);

		allBox.set('checked', (totalBoxes == totalOn));
	},

	checkTab: function(box) {
		if ((document.all) && (event.keyCode == 9)) {
			box.selection = document.selection.createRange();

			setTimeout(
				function() {
					Liferay.Util.processTab(box.id);
				},
				0
			);
		}
	},

	createFlyouts: function(options) {
		AUI().use(
			'aui-delayed-task',
			'event',
			'node',
			function(A) {
				options = options || {};

				var flyout = A.one(options.container);
				var containers = [];

				if (flyout) {
					var lis = flyout.all('li');

					lis.each(
						function(item, index, collection) {
							var childUL = item.one('ul');

							if (childUL) {
								childUL.hide();

								item.addClass('lfr-flyout');
								item.addClass('has-children lfr-flyout-has-children');
							}
						}
					);

					var hideTask = new A.DelayedTask(
						function(event) {
							showTask.cancel();

							var li = event.currentTarget;

							if (li.hasClass('has-children')) {
								var childUL = event.currentTarget.one('> ul');

								if (childUL) {
									childUL.hide();

									if (options.mouseOut) {
										options.mouseOut.apply(event.currentTarget, [event]);
									}
								}
							}
						}
					);

					var showTask = new A.DelayedTask(
						function(event) {
							hideTask.cancel();

							var li = event.currentTarget;

							if (li.hasClass('has-children')) {
								var childUL = event.currentTarget.one('> ul');

								if (childUL) {
									childUL.show();

									if (options.mouseOver) {
										options.mouseOver.apply(event.currentTarget, [event]);
									}
								}
							}
						}
					);

					lis.on(
						'mouseenter',
						A.bind(showTask.delay, showTask, 0, null, null),
						'li'
					);

					lis.on(
						'mouseleave',
						A.bind(hideTask.delay, hideTask, 300, null, null),
						'li'
					);
				}
			}
		)
	},

	disableElements: function(obj) {
		AUI().use(
			'event',
			'node',
			function(A) {
				var el = A.one(obj);

				if (el) {
					el = el.getDOM();

					var children = el.getElementsByTagName('*');

					var emptyFn = function() { return false; };
					var Event = A.Event;

					for (var i = children.length - 1; i >= 0; i--) {
						var item = children[i];

						item.style.cursor = 'default';

						el.onclick = emptyFn;
						el.onmouseover = emptyFn;
						el.onmouseout = emptyFn;
						el.onmouseenter = emptyFn;
						el.onmouseleave = emptyFn;

						Event.purgeElement(el, false);

						item.href = 'javascript:;';
						item.disabled = true;
						item.action = '';
						item.onsubmit = emptyFn;
					}
				}
			}
		);
	},

	disableEsc: function() {
		if ((document.all) && (event.keyCode == 27)) {
			event.returnValue = false;
		}
	},

	disableFormButtons: function(inputs, form) {
		var instance = this;

		instance._submitLocked = AUI().later(
			10000,
			instance,
			instance.enableFormButtons,
			[inputs, form]
		);

		inputs.set('disabled', true);
		inputs.setStyle('opacity', 0.5);
	},

	disableTextareaTabs: function(textarea) {
		AUI().use(
			'event',
			'node',
			function(A) {
				textarea = A.one(textarea);

				if (textarea && textarea.attr('textareatabs') != 'enabled') {
					textarea.attr('textareatabs', 'disabled');

					textarea.detach('keydown', Liferay.Util.textareaTabs);
				}
			}
		);
	},

	disableToggleBoxes: function(checkBoxId, toggleBoxId, checkDisabled) {
		AUI().use(
			'node',
			function(A) {
				var checkBox = A.one('#' + checkBoxId);
				var toggleBox = A.one('#' + toggleBoxId);

				if (checkBox && toggleBox) {
					if (checkBox.get('checked') && checkDisabled) {
						toggleBox.set('disabled', true);
					}
					else {
						toggleBox.set('disabled', false);
					}

					checkBox.on(
						'click',
						function() {
							toggleBox.set('disabled', !toggleBox.get('disabled'));
						}
					);
				}
			}
		);
	},

	enableFormButtons: function(inputs, form) {
		var instance = this;

		instance._submitLocked = null;

		document.body.style.cursor = 'auto';

		inputs.set('disabled', false);
		inputs.setStyle('opacity', 1);
	},

	enableTextareaTabs: function(textarea) {
		var instance = this;
		AUI().use(
			'event',
			'node',
			function(A) {
				textarea = A.one(textarea);

				if (textarea && textarea.attr('textareatabs') != 'enabled') {
					textarea.attr('textareatabs', 'disabled');

					textarea.on('keydown', Liferay.Util.textareaTabs);
				}
			}
		);
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
			'aui-base',
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
					el = A.one(el);

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
		AUI().use(
			'node',
			function(A) {
				link = A.one(link);

				if (link) {
					var url = link.attr('href');

					submitForm(document.hrefFm, url);
				}
			}
		);
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

		AUI().ready(
			'aui-dialog',
			'aui-io-plugin',
			function(A) {
				if (options.url && options.button) {
					var url = options.url;
					var button = options.button;
					var width = options.width || 680;
					var height = options.height || 640;
					var textarea = options.textarea;
					var clicked = false;

					var editorButton = A.one(button);
					var popup = null;

					if (editorButton) {
						editorButton.on(
							'click',
							function(event) {
								if (!clicked) {
									popup = new A.Dialog(
										{
											height: 640,
											title: Liferay.Language.get('editor'),
											width: 680
										}
									).render();

									popup.plug(
										A.Plugin.IO,
										{
											uri: url + '&rt=' + Liferay.Util.randomInt()
										}
									);

									clicked = true;
								}
								else {
									popup.show();
									popup.io.start();
								}
							}
						);
					}
				}
			}
		);
	},

	isArray: function(object) {
		return !!(window.Array && object.constructor == window.Array);
	},

	listChecked: function(form) {
		var s = [];

		form = AUI().one(form);

		if (form) {
			form.all('input[type=checkbox]').each(
				function(item, index, collection) {
					var val = item.val();

					if (val && item.get('checked')) {
						s.push(val);
					}
				}
			);
		}

		return s.join(',');
	},

	listCheckedExcept: function(form, except) {
		var s = [];

		form = AUI().one(form);

		if (form) {
			form.all('input[type=checkbox]').each(
				function(item, index, collection) {
					var val = item.val();

					if (val && item.get('name') != except && item.get('checked')) {
						s.push(val);
					}
				}
			);
		}

		return s.join(',');
	},

	listSelect: function(box, delimeter) {
		var s = [];

		delimeter = delimeter || ',';

		if (box == null) {
			return '';
		}

		var select = AUI().one(box);

		if (select) {
			var options = select.all('option');

			options.each(
				function(item, index, collection) {
					var val = item.val();

					if (val) {
						s.push(val);
					}
				}
			);
		}

		if (s[0] == '.none') {
			return '';
		}
		else {
			return s.join(delimeter);
		}
	},

	listUncheckedExcept: function(form, except) {
		var s = [];

		form = AUI().one(form);

		if (form) {
			form.all('input[type=checkbox]').each(
				function(item, index, collection) {
					var val = item.val();

					if (val && item.get('name') != except && !item.get('checked')) {
						s.push(val);
					}
				}
			);
		}

		return s.join(',');
	},

	moveItem: function(fromBox, toBox, sort) {
		var A = AUI();

		fromBox = A.one(fromBox);
		toBox = A.one(toBox);

		var selectedIndex = fromBox.get('selectedIndex');

		var selectedOption;

		if (selectedIndex >= 0) {
			var options = fromBox.all('option');

			selectedOption = options.item(selectedIndex);

			options.each(
				function(item, index, collection) {
					if (item.get('selected')) {
						toBox.append(item);
					}
				}
			);
		}

		if (selectedOption && selectedOption.text() != '' && sort == true) {
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

		var title = obj.one('.portlet-title-text');

		var re = new RegExp('<\/?[^>]+>|\n|\r|\t', 'gim');

		if (title && !title.hasClass('not-editable')) {
			AUI().use(
				'aui-editable',
				'event',
				function(A) {
					var editableTitle = new A.Editable(
						{
							after: {
								contentTextChange: function(event) {
									var instance = this;

									if (!event.initial) {
										Liferay.Util.savePortletTitle(
											{
												doAsUserId: doAsUserId,
												plid: plid,
												portletId: portletId,
												title: event.newVal
											}
										);
									}
								},
								startEditing: function(event) {
									var instance = this;

									if (Liferay.Layout) {
										instance._dragListener = Liferay.Layout.layoutHandler.on(
											'drag:start',
											function(event) {
												instance.fire('save');
											}
										);
									}
								},
								stopEditing: function(event) {
									var instance = this;

									if (instance._dragListener) {
										instance._dragListener.detach();
									}
								}
							},
							cssClass: 'lfr-portlet-title-editable',
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
		box = AUI().one(box);

		var selectedIndex =  box.get('selectedIndex');

		if (!value) {
			box.all('option').item(selectedIndex).remove();
		}
		else {
			box.all('option[value=' + value + ']').item(selectedIndex).remove();
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
		AUI().ready(
			function(A) {
				var el = A.one('#' + elString);

				if (!el) {
					el = A.one('textarea[name=' + elString + ']');
				}

				if (el) {
					var pageBody;

					if (resizeToInlinePopup) {
						pageBody = el.ancestor('.aui-dialog-bd');
					}
					else {
						pageBody = A.getBody();
					}

					var resize = function() {
						var pageBodyHeight = pageBody.get('offsetHeight');

						if (usingRichEditor) {
							try {
								if (el.get('nodeName').toLowerCase() != 'iframe') {
									el = window[elString];
								}
							}
							catch (e) {
							}
						}

						var diff = 170;

						if (!resizeToInlinePopup) {
							diff = 100;
						}

						el = A.one(el);

						var styles = {
							height: (pageBodyHeight - diff) + 'px',
							width: '98%'
						};

						if (usingRichEditor) {
							if (!el || !A.DOM.inDoc(el)) {
								A.on(
									'available',
									function(event) {
										el = A.one(window[elString]);

										if (el) {
											el.setStyles(styles);
										}
									},
									'#' + elString + '_cp'
								);

								return;
							}
						}

						if (el) {
							el.setStyles(styles);
						}
					};

					resize();

					if (resizeToInlinePopup) {
						A.on('popupResize', resize);
					}
					else {
						A.getWin().on('resize', resize);
					}
				}
			}
		);
	},

	savePortletTitle: function(params) {
		AUI().use(
			'io',
			function(A) {
				A.mix(
					params,
					{
						doAsUserId: 0,
						plid: 0,
						portletId: 0,
						title: '',
						url: themeDisplay.getPathMain() + '/portlet_configuration/update_title'
					}
				);

				A.io.request(
					params.url,
					{
						data: {
							doAsUserId: params.doAsUserId,
							p_l_id: params.plid,
							portletId: params.portletId,
							title: params.title
						},
						method: 'POST'
					}
				);
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
		var option = AUI().one(col).one('option[value=' + value + ']');

		if (option) {
			option.set('selected', true);
		}
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
		AUI().use(
			'node',
			function(A) {
				var newBox = [];

				var options = box.all('option');

				for (var i = 0; i < options.size(); i++) {
					newBox[i] = [options.item(i).val(), options.item(i).text()];
				}

				newBox.sort(Liferay.Util.sortByAscending);

				var boxObj = A.one(box);

				boxObj.all('option').remove();

				A.each(
					newBox,
					function(item, index, collection) {
						boxObj.append('<option value="' + item[0] + '">' + item[1] + '</option>');
					}
				);

				if (Liferay.Browser.isIe()) {
					var currentWidth = boxObj.getStyle('width');

					if (currentWidth == 'auto') {
						boxObj.setStyle('width', 'auto');
					}
				}
			}
		);
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
	 * popup {string|object}: A selector or DOM element of the popup that contains the editor.
	 * textarea {string}: the name of the textarea to auto-resize.
	 * url {string}: The url to open that sets the editor.
	 */

	switchEditor: function(options) {
		if (options.url && options.popup) {
			AUI().use(
				'aui-io-plugin',
				function(A) {
					var url = options.url;
					var popup = A.one(options.popup);
					var textarea = options.textarea;

					if (popup) {
						if (!popup.io) {
							popup.plug(
								A.Plugin.IO,
								{
									uri: url
								}
							);
						}
						else {
							popup.io.set('uri', url);

							popup.io.start();
						}
					}
				}
			);
		}
	},

	textareaTabs: function(event) {
		var el = event.currentTarget.getDOM();
		var pressedKey = event.keyCode;

		if(pressedKey == 9) {
			event.halt();

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

	toggleBoxes: function(checkBoxId, toggleBoxId) {
		AUI().use(
			'node',
			function(A) {
				var checkBox = A.one('#' + checkBoxId);
				var toggleBox = A.one('#' + toggleBoxId);

				if (checkBox && toggleBox) {
					if (!checkBox.get('checked')) {
						toggleBox.hide();
					}

					checkBox.on(
						'click',
						function() {
							toggleBox.toggle();
						}
					);
				}
			}
		);
	},

	toggleControls: function() {
		AUI().use(
			'aui-io-request',
			'event',
			'node',
			function(A) {
				var trigger = A.one('.toggle-controls');

				if (trigger) {
					var docBody = A.getBody();
					var hiddenClass = 'controls-hidden';
					var visibleClass = 'controls-visible';
					var currentClass = visibleClass;

					if (Liferay._editControlsState != 'visible') {
						currentClass = hiddenClass;
					}

					docBody.addClass(currentClass);

					trigger.on(
						'click',
						function(event) {
							docBody.toggleClass(visibleClass).toggleClass(hiddenClass);

							Liferay._editControlsState = (docBody.hasClass(visibleClass) ? 'visible' : 'hidden');

							A.io.request(
								themeDisplay.getPathMain() + '/portal/session_click',
								{
									data: {
										'liferay_toggle_controls': Liferay._editControlsState
									},
									method: 'POST'
								}
							);
						}
					);
				}
			}
		);
	},

	toggleSelectBox: function(selectBoxId, value, toggleBoxId) {
		AUI().use(
			'event',
			'node',
			function(A) {
				var selectBox = A.one('#' + selectBoxId);
				var toggleBox = A.one('#' + toggleBoxId);

				if (selectBox && toggleBox) {
					var toggle = function() {
						var action = 'show';

						if (selectBox.val() != value) {
							action = 'hide';
						}

						toggleBox[action]();
					};

					toggle();

					selectBox.on('change', toggle);
				}
			}
		);
	},

	uncamelize: function(value, separator) {
		separator = separator || ' ';

		value = value.replace(/([a-zA-Z][a-zA-Z])([A-Z])([a-z])/g, '$1' + separator + '$2$3');
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
	}
};

function submitForm(form, action) {
	if (!Liferay.Util._submitLocked) {
		AUI().use(
			'event',
			'node',
			function(A) {
				form = A.one(form);

				var inputs = form.all('input[type=button], input[type=reset], input[type=submit]');

				Liferay.Util.disableFormButtons(inputs, form);

				if (action != null) {
					form.attr('action', action);
				}

				Liferay.fire(
					'submitForm',
					{
						form: form
					}
				);

				form.submit();
			}
		);
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