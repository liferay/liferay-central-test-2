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

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletURLGenerationListener;
import javax.portlet.filter.PortletFilter;

/**
 * <a href="PortletContextBag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PortletContextBag {

	public PortletContextBag(String servletContextName) {
		_servletContextName = servletContextName;
	}

	public String getServletContextName() {
		return _servletContextName;
	}

	public Map<String, CustomUserAttributes> getCustomUserAttributes() {
		return _customUserAttributes;
	}

	public Map<String, PortletFilter> getPortletFilters() {
		return _portletFilters;
	}

	public Map<String, PortletURLGenerationListener> getPortletURLListeners() {
		return _urlListeners;
	}

	private String _servletContextName;
	private Map<String, CustomUserAttributes> _customUserAttributes =
		new HashMap<String, CustomUserAttributes>();
	private Map<String, PortletFilter> _portletFilters =
		new HashMap<String, PortletFilter>();
	private Map<String, PortletURLGenerationListener> _urlListeners =
		new HashMap<String, PortletURLGenerationListener>();

}