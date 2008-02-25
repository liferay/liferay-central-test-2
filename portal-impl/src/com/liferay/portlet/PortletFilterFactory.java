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

package com.liferay.portlet;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletException;
import javax.portlet.UnavailableException;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.PortletFilter;

/**
 * <a href="PortletFilterFactory.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortletFilterFactory {

	public static PortletFilter create(
			com.liferay.portal.model.PortletFilter portletFilterModel,
			PortletContextImpl ctx)
		throws PortletException {

		return _instance._create(portletFilterModel, ctx);
	}

	public static void destroy(
		com.liferay.portal.model.PortletFilter portletFilterModel) {

		_instance._destroy(portletFilterModel);
	}

	private PortletFilterFactory() {
		_pool = new ConcurrentHashMap<String, PortletFilter>();
	}

	private javax.portlet.filter.PortletFilter _create(
			com.liferay.portal.model.PortletFilter portletFilterModel,
			PortletContextImpl ctx)
		throws PortletException {

		PortletFilter portletFilter = _pool.get(
			portletFilterModel.getFilterName());

		if (portletFilter == null) {
			FilterConfig filterConfig =
				FilterConfigFactory.create(portletFilterModel, ctx);

			if (ctx.isWARFile()) {
				PortletContextBag portletContextBag = PortletContextBagPool.get(
					ctx.getPortletContextName());

				portletFilter = portletContextBag.getPortletFilters().get(
					filterConfig.getFilterName());

				portletFilter = _init(
					portletFilterModel, filterConfig, portletFilter);
			}
			else {
				portletFilter = _init(portletFilterModel, filterConfig);
			}

			_pool.put(portletFilterModel.getFilterName(), portletFilter);
		}

		return portletFilter;
	}

	private void _destroy(
		com.liferay.portal.model.PortletFilter portletFilterModel) {

		PortletFilter portletFilter = _pool.get(
			portletFilterModel.getFilterName());

		if (portletFilter == null) {
			return;
		}

		portletFilter.destroy();

		_pool.remove(portletFilterModel.getFilterName());
	}

	private PortletFilter _init(
			com.liferay.portal.model.PortletFilter portletFilterModel,
			FilterConfig filterConfig)
		throws PortletException {

		return _init(portletFilterModel, filterConfig, null);
	}

	private PortletFilter _init(
			com.liferay.portal.model.PortletFilter portletFilterModel,
			FilterConfig filterConfig, PortletFilter portletFilter)
		throws PortletException {

		try {
			if (portletFilter == null) {
				portletFilter = (javax.portlet.filter.PortletFilter)
					Class.forName(
						portletFilterModel.getFilterClass()).newInstance();
			}

			portletFilter.init(filterConfig);
		}
		catch (ClassNotFoundException cnofe) {
			throw new UnavailableException(cnofe.getMessage());
		}
		catch (InstantiationException ie) {
			throw new UnavailableException(ie.getMessage());
		}
		catch (IllegalAccessException iae) {
			throw new UnavailableException(iae.getMessage());
		}

		return portletFilter;
	}

	private static PortletFilterFactory _instance =new PortletFilterFactory();

	private Map<String, PortletFilter> _pool;

}