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
 * <a href="Captcha.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public interface Captcha {

	public void check(HttpServletRequest request) throws CaptchaTextException;

	public void check(PortletRequest portletRequest)
		throws CaptchaTextException;

	public String getTaglibPath();

	public boolean isEnabled(HttpServletRequest request);

	public boolean isEnabled(PortletRequest portletRequest);

	public void serveImage(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException;

	public void serveImage(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws IOException;

}