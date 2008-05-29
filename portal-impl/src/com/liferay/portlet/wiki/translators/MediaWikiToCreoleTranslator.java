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

/**
 * <a href="MediaWikiToCreoleTranslator.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class MediaWikiToCreoleTranslator extends BaseTranslator {

	public static final String TABLE_OF_CONTENTS = "<<TableOfContents>>\n\n";

	public MediaWikiToCreoleTranslator() {
		initRegexps();
		initNowikiRegexps();
	}

	protected void initRegexps() {

		// Clean unnecessary header emphasis

		_regexps.put("= '''(.*)''' =", "= $1 =");
		_regexps.put("== '''(.*)''' ==", "== $1 ==");
		_regexps.put("== '''(.*)''' ===", "=== $1 ===");

		// Unscape angle brackets

		_regexps.put("&lt;", "<");
		_regexps.put("&gt;", ">");

		// Category removal

		_regexps.put("\\[\\[[Cc]ategory:(.*)\\]\\]", "");

		// Bold and italics

		_regexps.put(
			"''''((?s:.)*?)(''''|(\n\n|\r\r|\r\n\r\n))", "**//$1//**$3");

		// Bold

		_regexps.put("'''((?s:.)*?)('''|(\n\n|\r\r|\r\n\r\n))", "**$1**$3");

		// Italics

		_regexps.put("''((?s:.)*?)(''|(\n\n|\r\r|\r\n\r\n))", "//$1//$3");

		// Images

		_regexps.put("\\[{2}Image:([^\\]]*)\\]{2}", "{{SharedImages/$1}}");

		// Normalize URLs

		_regexps.put("\\[{2}((http|ftp)[^ ]*) (.*)\\]{2}", "[$1 $3]");

		// URL

		_regexps.put("\\[((http|ftp)[^ ]*)\\]", "[[$1]]");

		// URL with label

		_regexps.put("\\[((http|ftp)[^ ]*) (.*)\\]", "[[$1|$3]]");

		// Monospace

		_regexps.put("(^ (.+))(\\n (.+))*", "{{{\n$0\n}}}");

		// Term and definition

		_regexps.put("^\\t([\\w]+):\\t(.*)", "**$1**:\n$2");

		// Indented paragraph

		_regexps.put("^\\t:\\t(.*)", "$1");

		// No wiki

		_regexps.put("<nowiki>(.*)</nowiki>", "{{{$1}}}");

	}

	protected void initNowikiRegexps() {
		_nowikiRegexps.add(_PREFORMATTED_PROTECTED);
		_nowikiRegexps.add(_ESCAPE_PROTECTED);
	}

	protected String postProcess(String content) {
		return TABLE_OF_CONTENTS + super.postProcess(content);
	}

	private static final String _ESCAPE_PROTECTED =
		"~(\\*\\*|~|//|-|#|\\{\\{|}}|\\\\|~\\[~~[|]]|----|=|\\|)";

	private static final String _PREFORMATTED_PROTECTED =
		"(<nowiki>)(.*?)(</nowiki>)";

}