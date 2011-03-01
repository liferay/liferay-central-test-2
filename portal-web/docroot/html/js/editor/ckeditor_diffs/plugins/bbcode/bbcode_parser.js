(function() {
	/*
	 * Copyright (c) 2011 Liferay Inc.
	 * Liferay's implementation extends and improves Stone Steps's parser.
	 *
	 * Copyright (c) 2008, Stone Steps Inc.
	 * All rights reserved
	 * http://www.stonesteps.ca/legal/bsd-license/
	 */

	var LIST = 'list';

	var REGEX_COLOR = /^(:?aqua|black|blue|fuchsia|gray|green|lime|maroon|navy|olive|purple|red|silver|teal|white|yellow|#(?:[0-9a-f]{3})?[0-9a-f]{3})$/i;

	var REGEX_MAIN = /((?:\r\n){2}|(?:\n\n)|\r\n|\n)|(?:\[((?:[a-z]|\*){1,16})(?:=([^\x00-\x1F"'\(\)<>\[\]]{1,256}))?\])|(?:\[\/([a-z]{1,16})\])/ig;

	var REGEX_NUMBER = /^[\\.0-9]{1,8}$/i;

	var REGEX_TAG_NAME = /^\/?(?:b|center|code|colou?r|i|img|justify|left|pre|q|quote|right|\*|s|samp|size|table|tr|th|td|list|font|u|url)$/;

	var REGEX_URI = /^[-;\/\?:@&=\+\$,_\.!~\*'\(\)%0-9a-z]{1,512}$/i;

	var SPAN_CLOSE = '</span>';

	var BBCodeParser = function(config) {
		this._config = config;
	};

	BBCodeParser.prototype = {
		constructor: BBCodeParser,

		parse: function(data) {
			var instance = this;

			var endTags = instance._endTags;
			var openTags = instance._openTags;

			if (openTags == null || openTags.length) {
				openTags = instance._openTags = [];
			}

			var result = data.replace(
				REGEX_MAIN,
				function(pstr, p1, p2, p3, p4, offset, orig) {
					return instance._process(pstr, p1, p2, p3, p4, offset, orig);
				}
			);

			if (instance._noParse) {
				instance._noParse = false;
			}

			if (openTags.length) {
				var lastBBTag = openTags[openTags.length - 1].bbTag;

				if ( lastBBTag == 'url') {
					openTags.pop();
					endTags.push('">', data.substr(instance._urlStart, data.length - instance._urlStart), '</a>');
				}
				else if (lastBBTag == 'img') {
					openTags.pop();
					endTags.push('">');
				}

				while (openTags.length) {
					endTags.push(openTags.pop().endTag);
				}
			}

			this._reset();

			if (endTags.length) {
				result =  result + endTags.join('');
			}

			return result;
		},

		_getFontSize: function(fontSize) {
			switch (fontSize) {
				case '1':
					return 10;
					break;
				case '2':
					return 12;
					break;
				case '3':
					return 16;
					break;
				case '4':
					return 18;
					break;
				case '5':
					return 24;
					break;
				case '6':
					return 32;
					break;
				case '7':
					return 48;
					break;
				default:
					return 12;
			}
		},

		_isValidTag: function(str) {
			if (!str || !str.length) {
				return false;
			}

			return REGEX_TAG_NAME.test(str);
		},

		_handleCode: function(pstr, p1, p2, p3, p4, offset, orig) {
			var instance = this;

			instance._openTags.push(
				{
					bbTag: p2,
					endTag: '</pre>'
				}
			);

			instance._noParse = true;

			return '<pre>';
		},

		_handleColor: function(pstr, p1, p2, p3, p4, offset, orig) {
			var instance = this;

			if (!p3 || !REGEX_COLOR.test(p3)) {
				p3 = 'inherit';
			}

			instance._openTags.push(
				{
					bbTag: p2,
					endTag: SPAN_CLOSE
				}
			);

			return '<span style="color: ' + p3 + '">';
		},

		_handleEm: function(pstr, p1, p2, p3, p4, offset, orig) {
			var instance = this;

			instance._openTags.push(
				{
					bbTag: p2,
					endTag: '</em>'
				}
			);

			return '<em>';
		},

		_handleImage: function(pstr, p1, p2, p3, p4, offset, orig) {
			var instance = this;

			var result = null;

			instance._openTags.push(
				{
					bbTag: p2,
					endTag: '">'
				}
			);

			result = '<img src="';

			return result;
		},

		_handleFont: function(pstr, p1, p2, p3, p4, offset, orig) {
			var instance = this;

			var result = null;

			instance._openTags.push(
				{
					bbTag: p2,
					endTag: SPAN_CLOSE
				}
			);

			result = '<span style="font-family: ' + p3 + '">';

			return result;
		},

		_handleList: function(pstr, p1, p2, p3, p4, offset, orig) {
			var instance = this;

			var tag = 'ul';
			var styleAttr;

			if (p3) {
				tag = 'ol';

				switch (p3) {
					case '1':
						styleAttr = 'list-style-type: decimal;';

						break;
					case 'a':
						styleAttr = 'list-style-type: lower-alpha;';

						break;
				}
			}

			instance._openTags.push(
				{
					bbTag: p2,
					endTag: '</' + tag + '>'
				}
			);

			var result;

			if (styleAttr) {
				result = '<' + tag + ' style="' + styleAttr + '">';
			}
			else {
				result = '<' + tag + '>';
			}

			return result;
		},

		_handleListItem: function(pstr, p1, p2, p3, p4, offset, orig) {
			var instance = this;

			var result;

			if (!instance._hasOpenTag(LIST)) {
				result = '<span style="color: red">[list]</span>';
			}
			else {
				if (instance._hasOpenTag(p2)) {
					instance._processEndTag(pstr, p1, p2, p3, '*', offset, orig);
				}

				instance._openTags.push(
					{
						bbTag: p2,
						endTag: '</li>'
					}
				);

				result = '<li>';
			}

			return result;
		},

		_handleQuote: function(pstr, p1, p2, p3, p4, offset, orig) {
			var instance = this;

			instance._openTags.push(
				{
					bbTag: p2,
					endTag: '</blockquote>'
				}
			);

			var result;

			if (p3 && p3.length && REGEX_URI.test(p3)) {
				result = '<blockquote cite="' + p3 + '">';
			} else {
				result = '<blockquote>';
			}

			return result;
		},

		_handleSize: function(pstr, p1, p2, p3, p4, offset, orig) {
			var instance = this;

			if (!p3 || !REGEX_NUMBER.test(p3)) {
				p3 = '1';
			}

			instance._openTags.push(
				{
					bbTag: p2,
					endTag: SPAN_CLOSE
				}
			);

			return '<span style="font-size: ' + instance._getFontSize(p3) + 'px">';
		},

		_handleSimpleTags: function(pstr, p1, p2, p3, p4, offset, orig) {
			var instance = this;

			instance._openTags.push(
				{
					bbTag: p2,
					endTag: '</' + p2 + '>'
				}
			);

			return '<' + p2 + '>';
		},

		_handleStrikeThrough: function(pstr, p1, p2, p3, p4, offset, orig) {
			var instance = this;

			instance._openTags.push(
				{
					bbTag: p2,
					endTag: '</strike>'
				}
			);

			return '<strike>';
		},

		_handleStrong: function(pstr, p1, p2, p3, p4, offset, orig) {
			var instance = this;

			instance._openTags.push(
				{
					bbTag: p2,
					endTag: '</strong>'
				}
			);

			return '<strong>';
		},

		_handleTable: function(pstr, p1, p2, p3, p4, offset, orig) {
			var instance = this;

			instance._openTags.push(
				{
					bbTag: p2,
					endTag: '</table>'
				}
			);

			return '<table>';
		},

		_handleTableCell: function(pstr, p1, p2, p3, p4, offset, orig) {
			var instance = this;

			instance._openTags.push(
				{
					bbTag: p2,
					endTag: '</td>'
				}
			);

			return '<td>';
		},

		_handleTableHeader: function(pstr, p1, p2, p3, p4, offset, orig) {
			var instance = this;

			instance._openTags.push(
				{
					bbTag: p2,
					endTag: '</th>'
				}
			);

			return '<th>';
		},

		_handleTableRow: function(pstr, p1, p2, p3, p4, offset, orig) {
			var instance = this;

			instance._openTags.push(
				{
					bbTag: p2,
					endTag: '</tr>'
				}
			);

			return '<tr>';
		},

		_handleTextAlignCenter: function(pstr, p1, p2, p3, p4, offset, orig) {
			var instance = this;

			instance._openTags.push(
				{
					bbTag: p2,
					endTag: '</p>'
				}
			);

			return '<p style="text-align: center">';
		},

		_handleTextAlignJustify: function(pstr, p1, p2, p3, p4, offset, orig) {
			var instance = this;

			instance._openTags.push(
				{
					bbTag: p2,
					endTag: '</p>'
				}
			);

			return '<p style="text-align: justify">';
		},

		_handleTextAlignLeft: function(pstr, p1, p2, p3, p4, offset, orig) {
			var instance = this;

			instance._openTags.push(
				{
					bbTag: p2,
					endTag: '</span>'
				}
			);

			return '<p style="text-align: left">';
		},

		_handleTextAlignRight: function(pstr, p1, p2, p3, p4, offset, orig) {
			var instance = this;

			instance._openTags.push(
				{
					bbTag: p2,
					endTag: '</p>'
				}
			);

			return '<p style="text-align: right">';
		},

		_handleURL: function(pstr, p1, p2, p3, p4, offset, orig) {
			var instance = this;

			var result = null;

			instance._openTags.push(
				{
					bbTag: p2,
					endTag: '</a>'
				}
			);

			if (p3 && REGEX_URI.test(p3)) {
				instance._urlStart = -1;

				result = '<a href="' + p3 + '">';
			}
			else {
				instance._urlStart = pstr.length + offset;

				result = '<a href="';
			}

			return result;
		},

		_hasOpenTag: function(tagName) {
			var instance = this;

			var openTags = instance._openTags;

			if (!openTags) {
				return false;
			}

			for (var i = openTags.length - 1; i >= 0; i--) {
				if (openTags[i].bbTag === tagName) {
					return true;
				}
			}

			return false;
		},

		_process: function(pstr, p1, p2, p3, p4, offset, orig) {
			var instance = this;

			var res;

			if (p1 && p1.length) {
				res = instance._processNewLines(pstr, p1, p2, p3, p4, offset, orig);
			}
			else {
				res = instance._processStartTag(pstr, p1, p2, p3, p4, offset, orig);

				if (res === null) {
					res = instance._processEndTag(pstr, p1, p2, p3, p4, offset, orig);
				}
			}

			if (res === null) {
				res = pstr;
			}

			return res;
		},

		_processEndTag: function(pstr, p1, p2, p3, p4, offset, orig) {
			var instance = this;

			var openTags = instance._openTags;
			var result = null, tmp = null;

			if (instance._isValidTag(p4)) {
				if (instance._noParse) {
					if (p4 == 'code') {
						instance._noParse = false;

						result = openTags.pop().endTag;
					}
					else {
						result = '[/' + p4 + ']';
					}
				}
				else {
					if (p4 === LIST && instance._hasOpenTag('*')) {
						openTags.pop();
						tmp = '</li>';
					}

					if (!openTags.length || openTags[openTags.length - 1].bbTag != p4) {
						result = '<span style="color: red">[/' + p4 + ']</span>';
					}
					else if (p4 == 'url') {
						if (instance._urlStart > 0) {
							result = '">' + orig.substr(instance._urlStart, offset - instance._urlStart) + openTags.pop().endTag;
						}
						else {
							result = openTags.pop().endTag;
						}
					}
					else {
						result = openTags.pop().endTag;
					}
				}
			}

			if (tmp) {
				result = tmp + result;
			}

			return result;
		},

		_processNewLines: function(pstr, p1, p2, p3, p4, offset, orig) {
			var instance = this;

			var res = null;

			if (instance._noParse) {
				res = pstr;
			}
			else {
				switch (p1) {
					case '\r\n\r\n':
					case '\n\n':
						res = '<p>';

						break;
					case '\r\n':
					case '\n':
					case '\r':
						res = '<br>';

						break;
					default:
						throw 'Internal error. Invalid regular expression';
				}
			}

			return res;
		},

		_processStartTag: function(pstr, p1, p2, p3, p4, offset, orig) {
			var instance = this;

			var openTags = instance._openTags;
			var handler, result = null;

			if (instance._isValidTag(p2)) {
				if (instance._noParse) {
					result = '[' + p2 + ']';
				}
				else if ((openTags.length && openTags[openTags.length - 1].bbTag == 'url' && instance._urlStart >= 0) ||
					(openTags.length && openTags[openTags.length - 1].bbTag == 'img')) {
					result = '[' + p2 + ']';
				}
				else {
					switch (p2) {
						case 'b':
							handler = instance._handleStrong;

							break;
						case 'center':
							handler = instance._handleTextAlignCenter;

							break;
						case 'code':
							handler = instance._handleCode;

							break;
						case 'color':
						case 'colour':
							handler = instance._handleColor;

							break;
						case 'i':
							handler = instance._handleEm;

							break;
						case 'img':
							handler = instance._handleImage;

							break;
						case 'justify':
							handler = instance._handleTextAlignJustify;

							break;
						case 'font':
							handler = instance._handleFont;

							break;
						case LIST:
							handler = instance._handleList;

							break;
						case 'left':
							handler = instance._handleTextAlignLeft;

							break;
						case 'right':
							handler = instance._handleTextAlignRight;

							break;
						case 'q':
						case 'quote':
							handler = instance._handleQuote;

							break;
						case '*':
							handler = instance._handleListItem;

							break;
						case 's':
							handler = instance._handleStrikeThrough;

							break;
						case 'size':
							handler = instance._handleSize;

							break;
						case 'table':
							handler = instance._handleTable;

							break;
						case 'td':
							handler = instance._handleTableCell;

							break;
						case 'th':
							handler = instance._handleTableHeader;

							break;
						case 'tr':
							handler = instance._handleTableRow;

							break;
						case 'url':
							handler = instance._handleURL;

							break;
						default:
							handler = instance._handleSimpleTags;

							break;
					}

					result = handler.call(instance, pstr, p1, p2, p3, p4, offset, orig);
				}
			}

			return result;
		},

		_reset: function() {
			var instance = this;

			instance._endTags = [];
			instance._fontStart = -1;
			instance._noParse = false;
			instance._openTags = null;
			instance._urlStart = -1;
		},

		_endTags: [],

		_fontStart: -1,

		_noParse: false,

		_openTags: null,

		_urlStart: -1
	};
	
	CKEDITOR.BBCodeParser = BBCodeParser;
})();
