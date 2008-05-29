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

import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.util.DigesterImpl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <a href="BaseTranslator.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public abstract class BaseTranslator {

	public String translate(String content) {
		_protectionMap.clear();

		content = preProcess(content);

		content = runRegexps(content);

		content = postProcess(content);

		return content;
	}

	protected String postProcess(String content) {
		return unprotectNowikiText(content);
	}

	protected String preProcess(String content) {
		content = _normalizeLineBreaks(content);

		for (String regexp: _nowikiRegexps) {
			content = protectText(content, regexp);
		}

		return content;
	}

	protected String protectText(String content, String markupRegex) {

	    Matcher matcher = Pattern.compile(
		    markupRegex, Pattern.MULTILINE | Pattern.DOTALL).matcher(content);

	    StringBuffer result = new StringBuffer();

	    while (matcher.find()) {
	        String protectedText = matcher.group();

		    String hash = _digester.digest(protectedText);

		    matcher.appendReplacement(result, "$1" + hash + "$3");

		    _protectionMap.put(hash, matcher.group(2));
	    }

	    matcher.appendTail(result);

	    return result.toString();
	}

	protected String runRegexps(String content) {
		for (String regexp: _regexps.keySet()) {
			String replacement = _regexps.get(regexp);

			content = runRegexp(content, regexp, replacement);
		}

		return content;
	}

	protected String runRegexp(
		String content, String regexp, String replacement) {

		Matcher matcher = Pattern.compile(
			regexp, Pattern.MULTILINE).matcher(content);

		StringBuffer sb = new StringBuffer();

		while (matcher.find()) {
			matcher.appendReplacement(sb, replacement);
		}

		matcher.appendTail(sb);

		return sb.toString();
	}

	protected String unprotectNowikiText(String content) {
		List<String> hashList = new ArrayList<String>(_protectionMap.keySet());

	    for (int i = hashList.size() - 1; i >= 0; i--) {
	        String hash = hashList.get(i);

	        String protectedMarkup = _protectionMap.get(hash);

		    content = content.replace(hash, protectedMarkup);
	    }

	    return content;
	}

	private String _normalizeLineBreaks(String content) {
		content = content.replace("\r\n", "\n");
		content = content.replace("\r", "\n");

		return content;
	}

	protected List<String> _nowikiRegexps = new LinkedList<String>();

	protected Map<String, String> _regexps =
		new LinkedHashMap<String, String>();

	private Digester _digester = new DigesterImpl();

	private Map<String, String> _protectionMap = new LinkedHashMap();

}