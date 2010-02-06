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

package com.liferay.portal.kernel.captcha;

import java.io.IOException;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="CaptchaWrapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class CaptchaWrapper implements Captcha {

	public CaptchaWrapper(Captcha captcha) {
		_captcha = captcha;
	}

	public void check(HttpServletRequest request) throws CaptchaTextException {
		_captcha.check(request);
	}

	public void check(PortletRequest portletRequest)
		throws CaptchaTextException {

		_captcha.check(portletRequest);
	}

	public String getTaglibPath() {
		return _captcha.getTaglibPath();
	}

	public boolean isEnabled(HttpServletRequest request) {
		return _captcha.isEnabled(request);
	}

	public boolean isEnabled(PortletRequest portletRequest) {
		return _captcha.isEnabled(portletRequest);
	}

	public void serveImage(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		_captcha.serveImage(request, response);
	}

	public void serveImage(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws IOException {

		_captcha.serveImage(portletRequest, portletResponse);
	}

	private Captcha _captcha;

}