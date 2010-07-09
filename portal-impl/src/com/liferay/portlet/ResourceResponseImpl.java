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

package com.liferay.portlet;

import com.liferay.portal.kernel.portlet.LiferayPortletURL;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceURL;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class ResourceResponseImpl
	extends MimeResponseImpl implements ResourceResponse {

	public void addDateHeader(String name, long date) {
		_response.addDateHeader(name, date);
	}

	public void addHeader(String name, String value) {
		_response.addHeader(name, value);
	}

	public void addIntHeader(String name, int value) {
		_response.addIntHeader(name, value);
	}

	public void addProperty(Cookie cookie) {
		_response.addCookie(cookie);
	}

	public PortletURL createActionURL() {
		return super.createActionURL();
	}

	public LiferayPortletURL createLiferayPortletURL(
		String portletName, String lifecycle) {

		ResourceRequest resourceRequest = (ResourceRequest)getPortletRequest();

		String cacheability = resourceRequest.getCacheability();

		if (cacheability.equals(ResourceURL.PAGE)) {
		}
		else if (lifecycle.equals(PortletRequest.ACTION_PHASE)) {
			throw new IllegalStateException(
				"Unable to create an action URL from a resource response " +
					"when the cacheability is not set to PAGE");
		}
		else if (lifecycle.equals(PortletRequest.RENDER_PHASE)) {
			throw new IllegalStateException(
				"Unable to create a render URL from a resource response when " +
					"the cacheability is not set to PAGE");
		}

		return super.createLiferayPortletURL(portletName, lifecycle);
	}

	public PortletURL createRenderURL() {
		return super.createRenderURL();
	}

	public ResourceURL createResourceURL() {
		return super.createResourceURL();
	}

	public String getLifecycle() {
		return PortletRequest.RESOURCE_PHASE;
	}

	public void setCharacterEncoding(String charset) {
		_response.setCharacterEncoding(charset);
	}

	public void setLocale(Locale locale) {
		_response.setLocale(locale);
	}

	public void setContentLength(int length) {
		_response.setContentLength(length);
	}

	public void setDateHeader(String name, long date) {
		_response.setDateHeader(name, date);
	}

	public void setHeader(String name, String value) {
		_response.setHeader(name, value);
	}

	public void setIntHeader(String name, int value) {
		_response.setIntHeader(name, value);
	}

	protected void init(
		PortletRequestImpl portletRequestImpl, HttpServletResponse response,
		String portletName, long companyId, long plid) {

		super.init(portletRequestImpl, response, portletName, companyId, plid);

		_response = response;
	}

	private HttpServletResponse _response;

}