/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.wiki.importers.mediawiki.MediaWikiImporter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <a href="MediaWikiToCreoleTranslator.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class MediaWikiToCreoleTranslator extends BaseTranslator {

	public static final String TABLE_OF_CONTENTS = "<<TableOfContents>>\n\n";

	public MediaWikiToCreoleTranslator() {
		initRegexps();
		initNowikiRegexps();
	}

	protected void initNowikiRegexps() {

		// Preformat protected

		nowikiRegexps.add("(<nowiki>)(.*?)(</nowiki>)");
		nowikiRegexps.add("(<pre>)(.*?)(</pre>)");

		// Escape protected

		nowikiRegexps.add(
			"~(\\*\\*|~|//|-|#|\\{\\{|}}|\\\\|~\\[~~[|]]|----|=|\\|)");
	}

	protected void initRegexps() {

		// Clean unnecessary header emphasis

		regexps.put("= '''([^=]+)''' =", "= $1 =");
		regexps.put("== '''([^=]+)''' ==", "== $1 ==");
		regexps.put("== '''([^=]+)''' ===", "=== $1 ===");

		// Unscape angle brackets

		regexps.put("&lt;", "<");
		regexps.put("&gt;", ">");

		// Remove categories

		regexps.put("\\[\\[[Cc]ategory:([^\\]]*)\\]\\][\\n]*", "");

		// Remove disambiguations

		regexps.put("\\{{2}OtherTopics\\|([^\\}]*)\\}{2}", StringPool.BLANK);

		// Remove work in progress

		regexps.put("\\{{2}Work in progress\\}{2}", StringPool.BLANK);

		// Bold and italics

		regexps.put(
			"''''((?s:.)*?)(''''|(\n\n|\r\r|\r\n\r\n))", "**//$1//**$3");

		// Bold

		regexps.put("'''((?s:.)*?)('''|(\n\n|\r\r|\r\n\r\n))", "**$1**$3");

		// Italics

		regexps.put("''((?s:.)*?)(''|(\n\n|\r\r|\r\n\r\n))", "//$1//$3");

		// Normalize URLs

		regexps.put("\\[{2}((http|ftp)[^ ]*) ([^\\]]*)\\]{2}", "[$1 $3]");

		// URL

		regexps.put("\\[((http|ftp)[^ ]*)\\]", "[[$1]]");

		// URL with label

		regexps.put("\\[((http|ftp)[^ ]*) ([^\\]]*)\\]", "[[$1|$3]]");

		// Term and definition

		regexps.put("^\\t([\\w]+):\\t(.*)", "**$1**:\n$2");

		// Indented paragraph

		regexps.put("^\\t:\\t(.*)", "$1");

		// Monospace

		regexps.put("(^ (.+))(\\n (.+))*", "{{{\n$0\n}}}");

		// No wiki

		regexps.put("<nowiki>([^<]*)</nowiki>", "{{{$1}}}");

		// HTML PRE

		regexps.put("<pre>([^<]*)</pre>", "{{{$1}}}");

		// User reference

		regexps.put("[-]*\\[{2}User:([^\\]]*)\\]{2}", "$1");
	}

	protected String postProcess(String content) {

		// LEP-6118

		Matcher matcher = Pattern.compile(
			"^=([^=]+)=", Pattern.MULTILINE).matcher(content);

		if (matcher.find()) {
			content = runRegexp(content, "^===([^=]+)===", "====$1====");
			content = runRegexp(content, "^==([^=]+)==", "===$1===");
			content = runRegexp(content, "^=([^=]+)=", "==$1==");
		}

		// Remove HTML tags

		for (int i = 0; i < _HTML_TAGS.length; i++) {
			content = content.replaceAll(_HTML_TAGS[i], StringPool.BLANK);
		}

		// Images

		matcher = Pattern.compile(
			"\\[{2}Image:([^\\]]*)\\]{2}", Pattern.DOTALL).matcher(content);

		StringBuffer sb = new StringBuffer(content);

		int offset = 0;

		while (matcher.find()) {
			String image =
				"{{" + MediaWikiImporter.SHARED_IMAGES_TITLE + "/" +
					matcher.group(1).toLowerCase() + "}}";

			sb.replace(
				matcher.start(0) + offset, matcher.end(0) + offset, image);

			offset += MediaWikiImporter.SHARED_IMAGES_TITLE.length() - 5;
		}

		content = sb.toString();

		// Remove underscores from links

		matcher = Pattern.compile(
			"\\[{2}([^\\]]*)\\]{2}", Pattern.DOTALL).matcher(content);

		sb = new StringBuffer(content);

		while (matcher.find()) {
			String link = matcher.group(1).replace(
				StringPool.UNDERLINE, StringPool.SPACE);

			sb.replace(matcher.start(1), matcher.end(1), link);
		}

		return TABLE_OF_CONTENTS + super.postProcess(sb.toString());
	}

	private static final String[] _HTML_TAGS = {
		"<blockquote>", "</blockquote>", "<br>", "<br/>", "<br />",  "<center>",
		"</center>", "<cite>", "</cite>","<code>", "</code>", "<div[^>]*>",
		"</div>", "<font[^>]*>", "</font>", "<hr>", "<hr/>", "<hr />", "<p>",
		"</p>", "<tt>", "</tt>", "<var>", "</var>"};

}