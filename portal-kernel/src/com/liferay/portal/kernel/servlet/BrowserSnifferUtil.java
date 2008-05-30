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

import com.liferay.portal.kernel.bean.BeanLocatorUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="BrowserSnifferUtil.java.html"><b><i>View Source</i></b></a>
 *
 * See http://www.zytrax.com/tech/web/browser_ids.htm for examples.
 *
 * @author Brian Wing Shun Chan
 *
 */
public class BrowserSnifferUtil {

	public static boolean acceptsGzip(HttpServletRequest req) {
		return getBrowserSniffer().acceptsGzip(req);
	}

	public static BrowserSniffer getBrowserSniffer() {
		return _getUtil()._browserSniffer;
	}

	public static boolean is_ie(HttpServletRequest req) {
		return getBrowserSniffer().is_ie(req);
	}

	public static boolean is_ie_4(HttpServletRequest req) {
		return getBrowserSniffer().is_ie_4(req);
	}

	public static boolean is_ie_5(HttpServletRequest req) {
		return getBrowserSniffer().is_ie_5(req);
	}

	public static boolean is_ie_5_5(HttpServletRequest req) {
		return getBrowserSniffer().is_ie_5_5(req);
	}

	public static boolean is_ie_5_5_up(HttpServletRequest req) {
		return getBrowserSniffer().is_ie_5_5_up(req);
	}

	public static boolean is_ie_6(HttpServletRequest req) {
		return getBrowserSniffer().is_ie_6(req);
	}

	public static boolean is_ie_7(HttpServletRequest req) {
		return getBrowserSniffer().is_ie_7(req);
	}

	public static boolean is_linux(HttpServletRequest req) {
		return getBrowserSniffer().is_linux(req);
	}

	public static boolean is_mozilla(HttpServletRequest req) {
		return getBrowserSniffer().is_mozilla(req);
	}

	public static boolean is_mozilla_1_3_up(HttpServletRequest req) {
		return getBrowserSniffer().is_mozilla_1_3_up(req);
	}

	public static boolean is_ns_4(HttpServletRequest req) {
		return getBrowserSniffer().is_ns_4(req);
	}

	public static boolean is_rtf(HttpServletRequest req) {
		return getBrowserSniffer().is_rtf(req);
	}

	public static boolean is_safari(HttpServletRequest req) {
		return getBrowserSniffer().is_safari(req);
	}

	public static boolean is_safari_3(HttpServletRequest req) {
		return getBrowserSniffer().is_safari_3(req);
	}

	public static boolean is_safari_mobile(HttpServletRequest req) {
		return getBrowserSniffer().is_safari_mobile(req);
	}

	public static boolean is_wap(HttpServletRequest req) {
		return getBrowserSniffer().is_wap(req);
	}

	public static boolean is_wap_xhtml(HttpServletRequest req) {
		return getBrowserSniffer().is_wap_xhtml(req);
	}

	public static boolean is_wml(HttpServletRequest req) {
		return getBrowserSniffer().is_wml(req);
	}

	public void setBrowserSniffer(BrowserSniffer browserSniffer) {
		_browserSniffer = browserSniffer;
	}

	private static BrowserSnifferUtil _getUtil() {
		if (_util == null) {
			_util = (BrowserSnifferUtil)BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static final String _UTIL = BrowserSnifferUtil.class.getName();

	private static BrowserSnifferUtil _util;

	private BrowserSniffer _browserSniffer;

}