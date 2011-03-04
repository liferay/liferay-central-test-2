(function() {
	/*
	 * Based in part on the BBCode parse by Stone Steps Inc.
	 * http://www.stonesteps.ca/legal/bsd-license/
	 */

	var LIST = 'list';

	var REGEX_COLOR = /^(:?aqua|black|blue|fuchsia|gray|green|lime|maroon|navy|olive|purple|red|silver|teal|white|yellow|#(?:[0-9a-f]{3})?[0-9a-f]{3})$/i;

	var REGEX_MAIN = /((?:\r\n){2}|(?:\n\n)|\r\n|\n)|(?:\[((?:[a-z]|\*){1,16})(?:=([^\x00-\x1F"'\(\)<>\[\]]{1,256}))?\])|(?:\[\/([a-z]{1,16})\])/ig;

	var REGEX_NUMBER = /^[\\.0-9]{1,8}$/i;

	var REGEX_TAG_NAME = /^\/?(?:b|center|code|colou?r|i|img|justify|left|pre|q|quote|right|\*|s|samp|size|table|tr|th|td|list|font|u|url)$/;

	var REGEX_URI = /^[-;\/\?:@&=\+\$,_\.!~\*'\(\)%0-9a-z]{1,512}$/i;

	var STR_BBCODE_CLOSE = ']';

	var STR_BBCODE_OPEN = '[';

	var STR_BBCODE_END_OPEN = '[/';

	var STR_ATTR_TAG_CLOSE = '">';

	var STR_ATTR_TAG_OPEN = '<span style="';

	var STR_TAG_BR = '<br>';

	var STR_TAG_END_CLOSE = '>';

	var STR_TAG_END_OPEN = '</';

	var STR_TAG_P_OPEN = '<p>';

	var STR_TAG_OPEN = '<';

	var STR_TAG_SPAN_CLOSE = '</span>';

	var STR_TAG_SPAN_OPEN = '<span style="color: red">';

	var TPL_LIST_ITEM = STR_TAG_SPAN_OPEN + STR_BBCODE_OPEN + LIST + STR_BBCODE_CLOSE + STR_TAG_SPAN_CLOSE;

	var MAP_FONT_SIZE = {
		1: 10,
		2: 12,
		3: 16,
		4: 18,
		5: 24,
		6: 32,
		7: 48,
		defaultSize: 12
	};

	var MAP_LIST_STYLES = {
		1: 'list-style-type: decimal;',
		a: 'list-style-type: lower-alpha;'
	};

	var MAP_NEW_LINES = {
		'\r\n\r\n': STR_TAG_P_OPEN,
		'\n\n': STR_TAG_P_OPEN,
		'\r\n': STR_TAG_BR,
		'\n': STR_TAG_BR,
		'\r': STR_TAG_BR
	};

	var BBCodeParser = function(config) {
		var instance = this;

		instance._reset();

		instance._config = config;
	};

	BBCodeParser.prototype = {
		parse: function(data) {
			var instance = this;

			var endTags = instance._endTags;
			var openTags = instance._openTags;

			if (!openTags || openTags.length) {
				openTags = [];

				instance._openTags = openTags;
			}

			var result = data.replace(
				REGEX_MAIN,
				A.bind(instance._process, instance)
			);

			if (instance._noParse) {
				instance._noParse = false;
			}

			if (openTags.length) {
				var lastBBTag = openTags[openTags.length - 1].bbTag;

				if ( lastBBTag == 'url') {
					openTags.pop();

					endTags.push(STR_ATTR_TAG_CLOSE, data.substr(instance._urlStart, data.length - instance._urlStart), '</a>');
				}
				else if (lastBBTag == 'img') {
					openTags.pop();

					endTags.push(STR_ATTR_TAG_CLOSE);
				}

				while (openTags.length) {
					endTags.push(openTags.pop().endTag);
				}
			}

			instance._reset();

			if (endTags.length) {
				result =  result + endTags.join('');
			}

			return result;
		},

		_getFontSize: function(fontSize) {
			return MAP_FONT_SIZE[fontSize] || MAP_FONT_SIZE.defaultSize;
		},

		_handleCode: function(matchedStr, crlf, openTag, tagOption, closeTag, offset, str) {
			var instance = this;

			instance._openTags.push(
				{
					bbTag: openTag,
					endTag: '</pre>'
				}
			);

			instance._noParse = true;

			return '<pre>';
		},

		_handleColor: function(matchedStr, crlf, openTag, tagOption, closeTag, offset, str) {
			var instance = this;

			if (!tagOption || !REGEX_COLOR.test(tagOption)) {
				tagOption = 'inherit';
			}

			instance._openTags.push(
				{
					bbTag: openTag,
					endTag: STR_TAG_SPAN_CLOSE
				}
			);

			return STR_ATTR_TAG_OPEN + 'color: ' + tagOption + STR_ATTR_TAG_CLOSE;
		},

		_handleEm: function(matchedStr, crlf, openTag, tagOption, closeTag, offset, str) {
			var instance = this;

			instance._openTags.push(
				{
					bbTag: openTag,
					endTag: '</em>'
				}
			);

			return '<em>';
		},

		_handleFont: function(matchedStr, crlf, openTag, tagOption, closeTag, offset, str) {
			var instance = this;

			var result = STR_ATTR_TAG_OPEN + 'font-family: ' + tagOption + STR_ATTR_TAG_CLOSE;

			instance._openTags.push(
				{
					bbTag: openTag,
					endTag: STR_TAG_SPAN_CLOSE
				}
			);

			return result;
		},

		_handleImage: function(matchedStr, crlf, openTag, tagOption, closeTag, offset, str) {
			var instance = this;

			var result = '<img src="';

			instance._openTags.push(
				{
					bbTag: openTag,
					endTag: STR_ATTR_TAG_CLOSE
				}
			);

			return result;
		},

		_handleList: function(matchedStr, crlf, openTag, tagOption, closeTag, offset, str) {
			var instance = this;

			var tag = 'ul';
			var styleAttr;

			if (tagOption) {
				tag = 'ol';

				styleAttr = MAP_LIST_STYLES[tagOption];
			}

			instance._openTags.push(
				{
					bbTag: openTag,
					endTag: STR_TAG_END_OPEN + tag + STR_TAG_END_CLOSE
				}
			);

			var result = STR_TAG_OPEN + tag + STR_TAG_END_CLOSE;

			if (styleAttr) {
				result = STR_TAG_OPEN + tag + ' style="' + styleAttr + STR_ATTR_TAG_CLOSE;
			}

			return result;
		},

		_handleListItem: function(matchedStr, crlf, openTag, tagOption, closeTag, offset, str) {
			var instance = this;

			var result = null;

			if (!instance._hasOpenTag(LIST)) {
				result = TPL_LIST_ITEM;
			}
			else {
				if (instance._hasOpenTag(openTag)) {
					instance._processEndTag(matchedStr, crlf, openTag, tagOption, '*', offset, orig);
				}

				instance._openTags.push(
					{
						bbTag: openTag,
						endTag: '</li>'
					}
				);

				result = '<li>';
			}

			return result;
		},

		_handleQuote: function(matchedStr, crlf, openTag, tagOption, closeTag, offset, str) {
			var instance = this;

			instance._openTags.push(
				{
					bbTag: openTag,
					endTag: '</blockquote>'
				}
			);

			var result = '<blockquote>';

			if (tagOption && tagOption.length && REGEX_URI.test(tagOption)) {
				result = '<blockquote cite="' + tagOption + STR_ATTR_TAG_CLOSE;
			}

			return result;
		},

		_handleSimpleTags: function(matchedStr, crlf, openTag, tagOption, closeTag, offset, str) {
			var instance = this;

			instance._openTags.push(
				{
					bbTag: openTag,
					endTag: STR_TAG_END_OPEN + openTag + STR_TAG_END_CLOSE
				}
			);

			return STR_TAG_OPEN + openTag + STR_TAG_END_CLOSE;
		},

		_handleSize: function(matchedStr, crlf, openTag, tagOption, closeTag, offset, str) {
			var instance = this;

			if (!tagOption || !REGEX_NUMBER.test(tagOption)) {
				tagOption = '1';
			}

			instance._openTags.push(
				{
					bbTag: openTag,
					endTag: STR_TAG_SPAN_CLOSE
				}
			);

			return STR_ATTR_TAG_OPEN + 'font-size: ' + instance._getFontSize(tagOption) + 'px">';
		},

		_handleStrikeThrough: function(matchedStr, crlf, openTag, tagOption, closeTag, offset, str) {
			var instance = this;

			instance._openTags.push(
				{
					bbTag: openTag,
					endTag: '</strike>'
				}
			);

			return '<strike>';
		},

		_handleStrong: function(matchedStr, crlf, openTag, tagOption, closeTag, offset, str) {
			var instance = this;

			instance._openTags.push(
				{
					bbTag: openTag,
					endTag: '</strong>'
				}
			);

			return '<strong>';
		},

		_handleTable: function(matchedStr, crlf, openTag, tagOption, closeTag, offset, str) {
			var instance = this;

			instance._openTags.push(
				{
					bbTag: openTag,
					endTag: '</table>'
				}
			);

			return '<table>';
		},

		_handleTableCell: function(matchedStr, crlf, openTag, tagOption, closeTag, offset, str) {
			var instance = this;

			instance._openTags.push(
				{
					bbTag: openTag,
					endTag: '</td>'
				}
			);

			return '<td>';
		},

		_handleTableHeader: function(matchedStr, crlf, openTag, tagOption, closeTag, offset, str) {
			var instance = this;

			instance._openTags.push(
				{
					bbTag: openTag,
					endTag: '</th>'
				}
			);

			return '<th>';
		},

		_handleTableRow: function(matchedStr, crlf, openTag, tagOption, closeTag, offset, str) {
			var instance = this;

			instance._openTags.push(
				{
					bbTag: openTag,
					endTag: '</tr>'
				}
			);

			return '<tr>';
		},

		_handleTextAlignCenter: function(matchedStr, crlf, openTag, tagOption, closeTag, offset, str) {
			var instance = this;

			instance._openTags.push(
				{
					bbTag: openTag,
					endTag: '</p>'
				}
			);

			return '<p style="text-align: center">';
		},

		_handleTextAlignJustify: function(matchedStr, crlf, openTag, tagOption, closeTag, offset, str) {
			var instance = this;

			instance._openTags.push(
				{
					bbTag: openTag,
					endTag: '</p>'
				}
			);

			return '<p style="text-align: justify">';
		},

		_handleTextAlignLeft: function(matchedStr, crlf, openTag, tagOption, closeTag, offset, str) {
			var instance = this;

			instance._openTags.push(
				{
					bbTag: openTag,
					endTag: '</span>'
				}
			);

			return '<p style="text-align: left">';
		},

		_handleTextAlignRight: function(matchedStr, crlf, openTag, tagOption, closeTag, offset, str) {
			var instance = this;

			instance._openTags.push(
				{
					bbTag: openTag,
					endTag: '</p>'
				}
			);

			return '<p style="text-align: right">';
		},

		_handleURL: function(matchedStr, crlf, openTag, tagOption, closeTag, offset, str) {
			var instance = this;

			var result = null;

			instance._openTags.push(
				{
					bbTag: openTag,
					endTag: '</a>'
				}
			);

			if (tagOption && REGEX_URI.test(tagOption)) {
				instance._urlStart = -1;

				result = '<a href="' + tagOption + STR_ATTR_TAG_CLOSE;
			}
			else {
				instance._urlStart = matchedStr.length + offset;

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

		_isValidTag: function(str) {
			var valid = false;

			if (str && str.length) {
				valid = REGEX_TAG_NAME.test(str);
			}

			return valid;
		},

		_process: function(matchedStr, crlf, openTag, tagOption, closeTag, offset, str) {
			var instance = this;

			var result = null;

			if (crlf && crlf.length) {
				result = instance._processNewLines(matchedStr, crlf, openTag, tagOption, closeTag, offset, str);
			}
			else {
				result = instance._processStartTag(matchedStr, crlf, openTag, tagOption, closeTag, offset, str);

				if (result === null) {
					result = instance._processEndTag(matchedStr, crlf, openTag, tagOption, closeTag, offset, str);
				}
			}

			if (result === null) {
				result = matchedStr;
			}

			return result;
		},

		_processEndTag: function(matchedStr, crlf, openTag, tagOption, closeTag, offset, str) {
			var instance = this;

			var openTags = instance._openTags;
			var result = null;
			var tmp;

			if (instance._isValidTag(closeTag)) {
				if (instance._noParse) {
					if (closeTag == 'code') {
						instance._noParse = false;

						result = openTags.pop().endTag;
					}
					else {
						result = STR_BBCODE_END_OPEN + closeTag + STR_BBCODE_CLOSE;
					}
				}
				else {
					if (closeTag === LIST && instance._hasOpenTag('*')) {
						openTags.pop();

						tmp = '</li>';
					}

					if (!openTags.length || openTags[openTags.length - 1].bbTag != closeTag) {
						result = STR_TAG_SPAN_OPEN + STR_BBCODE_END_OPEN + closeTag + STR_BBCODE_CLOSE + STR_TAG_SPAN_CLOSE;
					}
					else if (closeTag == 'url') {
						if (instance._urlStart > 0) {
							result = STR_ATTR_TAG_CLOSE + orig.substr(instance._urlStart, offset - instance._urlStart) + openTags.pop().endTag;
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

		_processNewLines: function(matchedStr, crlf, openTag, tagOption, closeTag, offset, str) {
			var instance = this;

			var result = null;

			if (instance._noParse) {
				result = matchedStr;
			}
			else {
				result = MAP_NEW_LINES[crlf];

				if (!result) {
					throw 'Internal error. Invalid regular expression';
				}
			}

			return result;
		},

		_processStartTag: function(matchedStr, crlf, openTag, tagOption, closeTag, offset, str) {
			var instance = this;

			var openTags = instance._openTags;
			var result = null;

			if (instance._isValidTag(openTag)) {
				if (instance._noParse) {
					result = STR_BBCODE_OPEN + openTag + STR_BBCODE_CLOSE;
				}
				else if ((openTags.length && openTags[openTags.length - 1].bbTag == 'url' && instance._urlStart >= 0) ||
					(openTags.length && openTags[openTags.length - 1].bbTag == 'img')) {

					result = STR_BBCODE_OPEN + openTag + STR_BBCODE_CLOSE;
				}
				else {
					var handler = instance._handleSimpleTags;

					if (openTag == 'b') {
						handler = instance._handleStrong;
					}
					else if (openTag == 'center') {
						handler = instance._handleTextAlignCenter;
					}
					else if (openTag == 'code') {
						handler = instance._handleCode;
					}
					else if (openTag == 'color' || openTag == 'colour') {
						handler = instance._handleColor;
					}
					else if (openTag == 'font') {
						handler = instance._handleFont;
					}
					else if (openTag == 'i') {
						handler = instance._handleEm;
					}
					else if (openTag == 'img') {
						handler = instance._handleImage;
					}
					else if (openTag == 'justify') {
						handler = instance._handleTextAlignJustify;
					}
					else if (openTag == LIST) {
						handler = instance._handleList;
					}
					else if (openTag == 'left') {
						handler = instance._handleTextAlignLeft;
					}
					else if (openTag == 'right') {
						handler = instance._handleTextAlignRight;
					}
					else if (openTag == 'q' || openTag == 'quote') {
						handler = instance._handleQuote;
					}
					else if (openTag == '*') {
						handler = instance._handleListItem;
					}
					else if (openTag == 's') {
						handler = instance._handleStrikeThrough;
					}
					else if (openTag == 'size') {
						handler = instance._handleSize;
					}
					else if (openTag == 'table') {
						handler = instance._handleTable;
					}
					else if (openTag == 'td') {
						handler = instance._handleTableCell;
					}
					else if (openTag == 'th') {
						handler = instance._handleTableHeader;
					}
					else if (openTag == 'tr') {
						handler = instance._handleTableRow;
					}
					else if (openTag == 'url') {
						handler = instance._handleURL;
					}

					result = handler.apply(instance, arguments);
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
		}
	};

	CKEDITOR.BBCodeParser = BBCodeParser;
})();