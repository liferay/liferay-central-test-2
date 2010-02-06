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

import com.liferay.portal.model.PortletApp;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletContext;
import javax.portlet.filter.FilterConfig;

/**
 * <a href="FilterConfigFactory.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class FilterConfigFactory {

	public static FilterConfig create(
		com.liferay.portal.model.PortletFilter portletFilter,
		PortletContext ctx) {

		return _instance._create(portletFilter, ctx);
	}

	public static void destroy(
		com.liferay.portal.model.PortletFilter portletFilter) {

		_instance._destroy(portletFilter);
	}

	private FilterConfigFactory() {
		_pool = new ConcurrentHashMap<String, Map<String, FilterConfig>>();
	}

	private FilterConfig _create(
		com.liferay.portal.model.PortletFilter portletFilter,
		PortletContext ctx) {

		PortletApp portletApp = portletFilter.getPortletApp();

		Map<String, FilterConfig> filterConfigs =
			_pool.get(portletApp.getServletContextName());

		if (filterConfigs == null) {
			filterConfigs = new ConcurrentHashMap<String, FilterConfig>();

			_pool.put(portletApp.getServletContextName(), filterConfigs);
		}

		FilterConfig filterConfig = filterConfigs.get(
			portletFilter.getFilterName());

		if (filterConfig == null) {
			filterConfig = new FilterConfigImpl(
				portletFilter.getFilterName(), ctx,
				portletFilter.getInitParams());

			filterConfigs.put(portletFilter.getFilterName(), filterConfig);
		}

		return filterConfig;
	}

	private void _destroy(
		com.liferay.portal.model.PortletFilter portletFilter) {

		PortletApp portletApp = portletFilter.getPortletApp();

		_pool.remove(portletApp.getServletContextName());
	}

	private static FilterConfigFactory _instance = new FilterConfigFactory();

	private Map<String, Map<String, FilterConfig>> _pool;

}