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

package com.liferay.portal.kernel.util;

/**
 * <a href="HtmlUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Clarence Shen
 * @author Harry Mark
 * @author Samuel Kong
 */
public class HtmlUtil {

	public static String escape(String html) {
		return getHtml().escape(html);
	}

	public static String escape(String html, int mode) {
		return getHtml().escape(html, mode);
	}

	public static String escapeAttribute(String attribute) {
		return getHtml().escapeAttribute(attribute);
	}

	public static String escapeCSS(String css) {
		return getHtml().escapeCSS(css);
	}

	public static String escapeJS(String js) {
		return getHtml().escapeJS(js);
	}

	public static String escapeURL(String url) {
		return getHtml().escapeURL(url);
	}

	public static String extractText(String html) {
		return getHtml().extractText(html);
	}

	public static String fromInputSafe(String html) {
		return getHtml().fromInputSafe(html);
	}

	public static Html getHtml() {
		return _html;
	}

	public static String replaceMsWordCharacters(String html) {
		return getHtml().replaceMsWordCharacters(html);
	}

	public static String stripBetween(String html, String tag) {
		return getHtml().stripBetween(html, tag);
	}

	public static String stripComments(String html) {
		return getHtml().stripComments(html);
	}

	public static String stripHtml(String html) {
		return getHtml().stripHtml(html);
	}

	public static String toInputSafe(String html) {
		return getHtml().toInputSafe(html);
	}

	public static String unescape(String html) {
		return getHtml().unescape(html);
	}

	public void setHtml(Html html) {
		_html = html;
	}

	private static Html _html;

}