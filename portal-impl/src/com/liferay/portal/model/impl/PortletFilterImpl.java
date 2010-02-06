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

package com.liferay.portal.model.impl;

import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletFilter;

import java.util.Map;
import java.util.Set;

/**
 * <a href="PortletFilterImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PortletFilterImpl implements PortletFilter {

	public PortletFilterImpl(
		String filterName, String filterClass, Set<String> lifecycles,
		Map<String, String> initParams, PortletApp portletApp) {

		_filterName = filterName;
		_filterClass = filterClass;
		_lifecycles = lifecycles;
		_initParams = initParams;
		_portletApp = portletApp;
	}

	public String getFilterName() {
		return _filterName;
	}

	public void setFilterName(String filterName) {
		_filterName = filterName;
	}

	public String getFilterClass() {
		return _filterClass;
	}

	public void setFilterClass(String filterClass) {
		_filterClass = filterClass;
	}

	public Set<String> getLifecycles() {
		return _lifecycles;
	}

	public void setLifecycles(Set<String> lifecycles) {
		_lifecycles = lifecycles;
	}

	public Map<String, String> getInitParams() {
		return _initParams;
	}

	public void setInitParams(Map<String, String> initParams) {
		_initParams = initParams;
	}

	public PortletApp getPortletApp() {
		return _portletApp;
	}

	public void setPortletApp(PortletApp portletApp) {
		_portletApp = portletApp;
	}

	private String _filterName;
	private String _filterClass;
	private Set<String> _lifecycles;
	private Map<String, String> _initParams;
	private PortletApp _portletApp;

}