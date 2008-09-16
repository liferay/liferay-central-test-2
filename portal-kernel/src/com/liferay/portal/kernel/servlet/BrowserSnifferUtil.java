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

package com.liferay.portal.kernel.servlet;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="BrowserSnifferUtil.java.html"><b><i>View Source</i></b></a>
 *
 * See http://www.zytrax.com/tech/web/browser_ids.htm for examples.
 *
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 *
 */
public class BrowserSnifferUtil {

	public static boolean acceptsGzip(HttpServletRequest request) {
		return getBrowserSniffer().acceptsGzip(request);
	}

	public static boolean isAir(HttpServletRequest request) {
		return getBrowserSniffer().isAir(request);
	}

	public static boolean isChrome(HttpServletRequest request) {
		return getBrowserSniffer().isChrome(request);
	}

	public static boolean isFirefox(HttpServletRequest request) {
		return getBrowserSniffer().isFirefox(request);
	}

	public static boolean isGecko(HttpServletRequest request) {
		return getBrowserSniffer().isGecko(request);
	}

	public static boolean isIE(HttpServletRequest request) {
		return getBrowserSniffer().isIE(request);
	}

	public static boolean isIPhone(HttpServletRequest request) {
		return getBrowserSniffer().isIPhone(request);
	}

	public static boolean isLinux(HttpServletRequest request) {
		return getBrowserSniffer().isLinux(request);
	}

	public static boolean isMobile(HttpServletRequest request) {
		return getBrowserSniffer().isMobile(request);
	}

	public static boolean isMozilla(HttpServletRequest request) {
		return getBrowserSniffer().isMozilla(request);
	}

	public static boolean isMac(HttpServletRequest request) {
		return getBrowserSniffer().isMac(request);
	}

	public static boolean isRTF(HttpServletRequest request) {
		return getBrowserSniffer().isRTF(request);
	}

	public static boolean isOpera(HttpServletRequest request) {
		return getBrowserSniffer().isOpera(request);
	}

	public static boolean isSafari(HttpServletRequest request) {
		return getBrowserSniffer().isSafari(request);
	}

	public static boolean isSun(HttpServletRequest request) {
		return getBrowserSniffer().isSun(request);
	}

	public static boolean isWebkit(HttpServletRequest request) {
		return getBrowserSniffer().isWebkit(request);
	}

	public static boolean isWap(HttpServletRequest request) {
		return getBrowserSniffer().isWap(request);
	}

	public static boolean isWapXHTML(HttpServletRequest request) {
		return getBrowserSniffer().isWapXHTML(request);
	}

	public static boolean isWindows(HttpServletRequest request) {
		return getBrowserSniffer().isWindows(request);
	}

	public static boolean isWML(HttpServletRequest request) {
		return getBrowserSniffer().isWML(request);
	}

	public static float majorVersion(HttpServletRequest request) {
		return getBrowserSniffer().majorVersion(request);
	}

	public static String revision(HttpServletRequest request) {
		return getBrowserSniffer().revision(request);
	}

	public static String version(HttpServletRequest request) {
		return getBrowserSniffer().version(request);
	}

	public static BrowserSniffer getBrowserSniffer() {
		return _browserSniffer;
	}

	public void setBrowserSniffer(BrowserSniffer browserSniffer) {
		_browserSniffer = browserSniffer;
	}

	private static BrowserSniffer _browserSniffer;

}