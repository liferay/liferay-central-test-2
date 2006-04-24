/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portal.servlet;

import com.liferay.portal.shared.util.PortalClassLoaderUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.CollectionFactory;
import com.liferay.util.servlet.DynamicServletRequest;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="NamespaceServletRequest.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Myunghun Kim
 *
 */
public class NamespaceServletRequest extends DynamicServletRequest {

	static Set reservedParams = CollectionFactory.getHashSet();

	static {
		reservedParams.add(WebKeys.JAVAX_PORTLET_CONFIG);
		reservedParams.add(WebKeys.JAVAX_PORTLET_PORTLET);
		reservedParams.add(WebKeys.JAVAX_PORTLET_REQUEST);
		reservedParams.add(WebKeys.JAVAX_PORTLET_RESPONSE);

		// provide servlet transparency to dispatcher includes
		reservedParams.add(WebKeys.JAVAX_SERVLET_INCLUDE_CONTEXT_PATH);
		reservedParams.add(WebKeys.JAVAX_SERVLET_INCLUDE_PATH_INFO);
		reservedParams.add(WebKeys.JAVAX_SERVLET_INCLUDE_QUERY_STRING);
		reservedParams.add(WebKeys.JAVAX_SERVLET_INCLUDE_REQUEST_URI);
		reservedParams.add(WebKeys.JAVAX_SERVLET_INCLUDE_SERVLET_PATH);
	}

	public NamespaceServletRequest(HttpServletRequest req, String portletName) {
		this(req, portletName, true);
	}

	public NamespaceServletRequest(HttpServletRequest req, String portletName,
								   boolean inherit) {

		super(req, inherit);

		_portletName = portletName;
		_namespace = PortalUtil.getPortletNamespace(_portletName);
	}

	public Object getAttribute(String name) {
		Object value  = null;
		
		if (reservedParams.contains(name) || 
				PortalClassLoaderUtil.isPortalClassLoader()) {
			value = super.getAttribute(name);
		}

		if (value == null) {
			value = super.getAttribute(_namespace + name);
		}

		return value;
	}

	public String getParameter(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		String value = super.getParameter(name);

		if (value == null) {
			value = super.getParameter(_namespace + name);
		}

		return value;
	}

	public void setAttribute(String name, Object value) {
		if (reservedParams.contains(name) || 
				PortalClassLoaderUtil.isPortalClassLoader()) {
			super.setAttribute(name, value);
		}
		else {
			super.setAttribute(_namespace + name, value);
		}
	}

	public void setAttribute(
		String name, Object value, boolean privateRequestAttribute) {

		if (!privateRequestAttribute) {
			super.setAttribute(name, value);
		}
		else {
			setAttribute(name, value);
		}
	}

	private String _portletName;
	private String _namespace;

}