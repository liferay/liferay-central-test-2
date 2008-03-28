/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.wiki.translators;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <a href="ClassicToCreoleTranslator.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class ClassicToCreoleTranslator {

	public ClassicToCreoleTranslator() {
		_initRegexps();
	}

	public String translate(String src) {
		String result = _normalize(src);

		for (String regexp: _regexps.keySet()) {
			String replacement = _regexps.get(regexp);

			result = _runRegexp(result, regexp, replacement);
		}

		return result;
	}

	private String _runRegexp(
		String content, String regexp, String replacement) {

		Matcher matcher =
			Pattern.compile(regexp, Pattern.MULTILINE).matcher(content);

		StringBuffer sb = new StringBuffer();

		while (matcher.find()) {
			matcher.appendReplacement(sb, replacement);
		}

		matcher.appendTail(sb);

		return sb.toString();
	}

	private void _initRegexps() {

		// Bold and italics
		_regexps.put(
			"'''''((?s:.)*?)('''''|(\n\n|\r\r|\r\n\r\n))", "**//$1//**$3");
		// Bold
		_regexps.put("'''((?s:.)*?)('''|(\n\n|\r\r|\r\n\r\n))", "**$1**$3");

		// Italics
		_regexps.put("''((?s:.)*?)(''|(\n\n|\r\r|\r\n\r\n))", "//$1//$3");

		// Link
		_regexps.put("\\[([^ ]*)\\]", "[[$1]]");

		// Link with label
		_regexps.put("\\[([^ ]+) (.*)\\]", "[[$1|$2]]");

		// Monospaced
		_regexps.put("(^ (.+))(\\n (.+))*", "{{{\n$0\n}}}");

		// List item
		_regexps.put("^\\t[\\*] (.*)", "* $1");

		// List subitem
		_regexps.put("^\\t\\t[\\*] (.*)", "** $1");

		// List subsubitem
		_regexps.put("^\\t\\t\\t[\\*] (.*)", "*** $1");

		// List subsubsubitem
		_regexps.put("^\\t\\t\\t\\t[\\*] (.*)", "**** $1");

		// Ordered list item
		_regexps.put("^\\t1 (.*)", "# $1");

		// Ordered list subitem
		_regexps.put("^\\t\\t1 (.*)", "## $1");

		// Ordered list subsubitem
		_regexps.put("^\\t\\t\\t1 (.*)", "### $1");

		// Ordered list subsubsubitem
		_regexps.put("^\\t\\t\\t\\t1 (.*)", "#### $1");

		// Term and definition
		_regexps.put("^\\t([\\w]+):\\t(.*)", "**$1**:\n$2");

		// Indented paragraph
		_regexps.put("^\\t:\\t(.*)", "$1");

		// CamelCase
		_regexps.put(
			"(^|\\p{Punct}|\\p{Space})((\\p{Lu}\\p{Ll}+){2,})" +
				"(\\z|\\n|\\p{Punct}|\\p{Space})", " [[$2]] ");

	}

	private String _normalize(String src) {
		String result = src;

		result = result.replace("\r\n", "\n");
		result = result.replace("\r", "\n");

		return result;
	}

	private Map<String, String> _regexps = new LinkedHashMap<String, String>();
}