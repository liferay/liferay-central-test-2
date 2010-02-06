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
 * <a href="ResourceResponseImpl.java.html"><b><i>View Source</i></b></a>
 *
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