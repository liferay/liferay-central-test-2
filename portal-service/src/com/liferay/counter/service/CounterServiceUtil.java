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

package com.liferay.counter.service;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

import java.util.List;

/**
 * <a href="CounterServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class CounterServiceUtil {

	public static List<String> getNames() throws SystemException {
		return getService().getNames();
	}

	public static CounterService getService() {
		if (_service == null) {
			_service = (CounterService)PortalBeanLocatorUtil.locate(
				CounterService.class.getName());
		}

		return _service;
	}

	public static long increment() throws SystemException {
		return getService().increment();
	}

	public static long increment(String name) throws SystemException {
		return getService().increment(name);
	}

	public static long increment(String name, int size) throws SystemException {
		return getService().increment(name, size);
	}

	public static void rename(String oldName, String newName)
		throws SystemException {

		getService().rename(oldName, newName);
	}

	public static void reset(String name) throws SystemException {
		getService().reset(name);
	}

	public static void reset(String name, long size)
		throws SystemException {

		getService().reset(name, size);
	}

	public void setService(CounterService service) {
		_service = service;
	}

	private static CounterService _service;

}