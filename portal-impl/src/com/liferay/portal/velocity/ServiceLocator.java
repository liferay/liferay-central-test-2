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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

public class ServiceLocator {

	public static ServiceLocator getInstance() {
		return _instance;
	}

	public Object findService(String serviceName) {
		if (serviceName.endsWith(_SERVICE)) {
			serviceName += _VELOCITY;
		}

		Object obj = null;

		try {
			obj = PortalBeanLocatorUtil.locate(serviceName);
		}
		catch (Exception e) {
			_log.error(e);
		}

		return obj;
	}

	public Object findService(String servletContextName, String serviceName) {
		if (serviceName.endsWith(_SERVICE)) {
			serviceName += _VELOCITY;
		}

		Object obj = null;

		try {
			obj = PortletBeanLocatorUtil.locate(
				servletContextName, serviceName);
		}
		catch (Exception e) {
			_log.error(e);
		}

		return obj;
	}

	private ServiceLocator() {
	}

	private static final String _SERVICE = "Service";

	private static final String _VELOCITY = ".velocity";

	private static ServiceLocator _instance = new ServiceLocator();

	private static Log _log = LogFactoryUtil.getLog(ServiceLocator.class);

}