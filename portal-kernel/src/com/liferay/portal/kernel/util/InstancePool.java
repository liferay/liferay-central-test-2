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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="InstancePool.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class InstancePool {

	public static boolean contains(String className) {
		return _instance._contains(className);
	}

	public static Object get(String className) {
		return _instance._get(className);
	}

	public static Object get(String className, boolean logErrors) {
		return _instance._get(className, logErrors);
	}

	public static void put(String className, Object obj) {
		_instance._put(className, obj);
	}

	private InstancePool() {
		_classPool = new HashMap<String, Object>();
	}

	private boolean _contains(String className) {
		className = className.trim();

		return _classPool.containsKey(className);
	}

	private Object _get(String className) {
		return _get(className, true);
	}

	private Object _get(String className, boolean logErrors) {
		className = className.trim();

		Object obj = _classPool.get(className);

		if (obj == null) {
			ClassLoader portalClassLoader =
				PortalClassLoaderUtil.getClassLoader();

			try {
				Class<?> classObj = portalClassLoader.loadClass(className);

				obj = classObj.newInstance();

				_put(className, obj);
			}
			catch (Exception e1) {
				if (logErrors && _log.isWarnEnabled()) {
					_log.warn(
						"Unable to load " + className +
							" with the portal class loader",
						e1);
				}

				Thread currentThread = Thread.currentThread();

				ClassLoader contextClassLoader =
					currentThread.getContextClassLoader();

				try {
					Class<?> classObj = contextClassLoader.loadClass(className);

					obj = classObj.newInstance();

					_put(className, obj);
				}
				catch (Exception e2) {
					if (logErrors) {
						_log.error(
							"Unable to load " + className +
								" with the portal class loader or the " +
									"current context class loader",
							e2);
					}
				}
			}
		}

		return obj;
	}

	private void _put(String className, Object obj) {
		className = className.trim();

		_classPool.put(className, obj);
	}

	private static Log _log = LogFactoryUtil.getLog(InstancePool.class);

	private static InstancePool _instance = new InstancePool();

	private Map<String, Object> _classPool;

}