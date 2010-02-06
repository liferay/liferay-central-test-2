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

package com.liferay.portal.kernel.portlet;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <a href="PortletBagPool.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PortletBagPool {

	public static PortletBag get(String portletId) {
		return _instance._get(portletId);
	}

	public static void put(String portletId, PortletBag portletBag) {
		_instance._put(portletId, portletBag);
	}

	public static PortletBag remove(String portletId) {
		return _instance._remove(portletId);
	}

	private PortletBagPool() {
		_portletBagPool = new ConcurrentHashMap<String, PortletBag>();
	}

	private PortletBag _get(String portletId) {
		return _portletBagPool.get(portletId);
	}

	private void _put(String portletId, PortletBag portletBag) {
		_portletBagPool.put(portletId, portletBag);
	}

	private PortletBag _remove(String portletId) {
		return _portletBagPool.remove(portletId);
	}

	private static PortletBagPool _instance = new PortletBagPool();

	private Map<String, PortletBag>_portletBagPool;

}