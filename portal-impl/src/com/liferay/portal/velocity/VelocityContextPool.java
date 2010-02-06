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

package com.liferay.portal.velocity;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

/**
 * <a href="VelocityContextPool.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class VelocityContextPool {

	public static boolean containsKey(String name) {
		return _instance._containsKey(name);
	}

	public static ServletContext get(String name) {
		return _instance._get(name);
	}

	public static void put(String name, ServletContext servletContext) {
		_instance._put(name, servletContext);
	}

	public static ServletContext remove(String name) {
		return _instance._remove(name);
	}

	private Map<String, ServletContext> _pool;

	private VelocityContextPool() {
		_pool = new ConcurrentHashMap<String, ServletContext>();
	}

	private boolean _containsKey(String name) {
		boolean value = _pool.containsKey(name);

		if (_log.isDebugEnabled()) {
			_log.debug("Contains key " + name + " " + value);
		}

		return value;
	}

	private ServletContext _get(String name) {
		ServletContext servletContext = _pool.get(name);

		if (_log.isDebugEnabled()) {
			_log.debug("Get " + name + " " + servletContext);
		}

		return servletContext;
	}

	private void _put(String name, ServletContext servletContext) {
		if (_log.isDebugEnabled()) {
			_log.debug("Put " + name + " " + servletContext);
		}

		_pool.put(name, servletContext);
	}

	private ServletContext _remove(String name) {
		ServletContext servletContext = _pool.remove(name);

		if (_log.isDebugEnabled()) {
			_log.debug("Remove " + name + " " + servletContext);
		}

		return servletContext;
	}

	private static Log _log = LogFactoryUtil.getLog(VelocityContextPool.class);

	private static VelocityContextPool _instance = new VelocityContextPool();

}