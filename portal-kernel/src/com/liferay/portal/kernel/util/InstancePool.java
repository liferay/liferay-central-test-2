/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="InstancePool.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class InstancePool {

	public static Object get(String className) {
		return _instance._get(className);
	}

	public static void put(String className, Object obj) {
		_instance._put(className, obj);
	}

	private InstancePool() {
		_classPool = new HashMap();
	}

	private Object _get(String className) {
		className = className.trim();

		Object obj = _classPool.get(className);

		if (obj == null) {
			try {
				ClassLoader contextClassLoader =
					Thread.currentThread().getContextClassLoader();

				Class classObj = contextClassLoader.loadClass(className);

				obj = classObj.newInstance();

				_put(className, obj);
			}
			catch (ClassNotFoundException cnofe) {
				_log.error(cnofe, cnofe);
			}
			catch (InstantiationException ie) {
				_log.error(ie, ie);
			}
			catch (IllegalAccessException iae) {
				_log.error(iae, iae);
			}
		}

		return obj;
	}

	private void _put(String className, Object obj) {
		_classPool.put(className, obj);
	}

	private static Log _log = LogFactoryUtil.getLog(InstancePool.class);

	private static InstancePool _instance = new InstancePool();

	private Map _classPool;

}