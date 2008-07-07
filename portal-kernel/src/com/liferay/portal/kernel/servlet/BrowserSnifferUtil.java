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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

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

	public static boolean acceptsGzip(HttpServletRequest request) {
		return getBrowserSniffer().acceptsGzip(request);
	}

	public static BrowserSniffer getBrowserSniffer() {
		return _getUtil()._browserSniffer;
	}

	public static boolean is_ie(HttpServletRequest request) {
		return getBrowserSniffer().is_ie(request);
	}

	public static boolean is_ie_4(HttpServletRequest request) {
		return getBrowserSniffer().is_ie_4(request);
	}

	public static boolean is_ie_5(HttpServletRequest request) {
		return getBrowserSniffer().is_ie_5(request);
	}

	public static boolean is_ie_5_5(HttpServletRequest request) {
		return getBrowserSniffer().is_ie_5_5(request);
	}

	public static boolean is_ie_5_5_up(HttpServletRequest request) {
		return getBrowserSniffer().is_ie_5_5_up(request);
	}

	public static boolean is_ie_6(HttpServletRequest request) {
		return getBrowserSniffer().is_ie_6(request);
	}

	public static boolean is_ie_7(HttpServletRequest request) {
		return getBrowserSniffer().is_ie_7(request);
	}

	public static boolean is_linux(HttpServletRequest request) {
		return getBrowserSniffer().is_linux(request);
	}

	public static boolean is_mozilla(HttpServletRequest request) {
		return getBrowserSniffer().is_mozilla(request);
	}

	public static boolean is_mozilla_1_3_up(HttpServletRequest request) {
		return getBrowserSniffer().is_mozilla_1_3_up(request);
	}

	public static boolean is_ns_4(HttpServletRequest request) {
		return getBrowserSniffer().is_ns_4(request);
	}

	public static boolean is_rtf(HttpServletRequest request) {
		return getBrowserSniffer().is_rtf(request);
	}

	public static boolean is_safari(HttpServletRequest request) {
		return getBrowserSniffer().is_safari(request);
	}

	public static boolean is_safari_3(HttpServletRequest request) {
		return getBrowserSniffer().is_safari_3(request);
	}

	public static boolean is_safari_mobile(HttpServletRequest request) {
		return getBrowserSniffer().is_safari_mobile(request);
	}

	public static boolean is_wap(HttpServletRequest request) {
		return getBrowserSniffer().is_wap(request);
	}

	public static boolean is_wap_xhtml(HttpServletRequest request) {
		return getBrowserSniffer().is_wap_xhtml(request);
	}

	public static boolean is_wml(HttpServletRequest request) {
		return getBrowserSniffer().is_wml(request);
	}

	public void setBrowserSniffer(BrowserSniffer browserSniffer) {
		_browserSniffer = browserSniffer;
	}

	private static BrowserSnifferUtil _getUtil() {
		if (_util == null) {
			_util = (BrowserSnifferUtil)PortalBeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static final String _UTIL = BrowserSnifferUtil.class.getName();

	private static BrowserSnifferUtil _util;

	private BrowserSniffer _browserSniffer;

}