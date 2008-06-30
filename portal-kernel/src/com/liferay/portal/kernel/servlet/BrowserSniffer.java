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
 * <a href="BrowserSniffer.java.html"><b><i>View Source</i></b></a>
 *
 * See http://www.zytrax.com/tech/web/browser_ids.htm for examples.
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface BrowserSniffer {

	public boolean acceptsGzip(HttpServletRequest request);

	public boolean is_ie(HttpServletRequest request);

	public boolean is_ie_4(HttpServletRequest request);

	public boolean is_ie_5(HttpServletRequest request);

	public boolean is_ie_5_5(HttpServletRequest request);

	public boolean is_ie_5_5_up(HttpServletRequest request);

	public boolean is_ie_6(HttpServletRequest request);

	public boolean is_ie_7(HttpServletRequest request);

	public boolean is_linux(HttpServletRequest request);

	public boolean is_mozilla(HttpServletRequest request);

	public boolean is_mozilla_1_3_up(HttpServletRequest request);

	public boolean is_ns_4(HttpServletRequest request);

	public boolean is_rtf(HttpServletRequest request);

	public boolean is_safari(HttpServletRequest request);

	public boolean is_safari_3(HttpServletRequest request);

	public boolean is_safari_mobile(HttpServletRequest request);

	public boolean is_wap(HttpServletRequest request);

	public boolean is_wap_xhtml(HttpServletRequest request);

	public boolean is_wml(HttpServletRequest request);

}