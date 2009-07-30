/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.velocity;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;

/**
 * <a href="UtilLocator.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class UtilLocator {

	public static UtilLocator getInstance() {
		return _instance;
	}

	public Object findUtil(String utilName) {
		if (utilName.endsWith(_UTIL)) {
			utilName += _VELOCITY;
		}

		return PortalBeanLocatorUtil.locate(utilName);
	}

	public Object findUtil(
		String servletContextName, String utilName) {

		if (utilName.endsWith(_UTIL)) {
			utilName += _VELOCITY;
		}

		return PortletBeanLocatorUtil.locate(servletContextName, utilName);
	}

	private UtilLocator() {
	}

	private static final String _UTIL = "Util";

	private static final String _VELOCITY = ".velocity";

	private static UtilLocator _instance = new UtilLocator();

}