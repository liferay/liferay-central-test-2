/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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