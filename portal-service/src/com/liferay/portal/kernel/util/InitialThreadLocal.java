/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.lang.reflect.Method;

/**
 * <a href="InitialThreadLocal.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class InitialThreadLocal<T> extends ThreadLocal<T> {

	public InitialThreadLocal(T initialValue) {
		_initialValue = initialValue;

		if (_initialValue instanceof Cloneable) {
			try {
				_cloneMethod = _initialValue.getClass().getMethod(
					_METHOD_CLONE);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	protected T initialValue() {
		if (_cloneMethod != null) {
			try {
				return (T)_cloneMethod.invoke(_initialValue);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		return _initialValue;
	}

	private static final String _METHOD_CLONE = "clone";

	private static Log _log = LogFactoryUtil.getLog(InitialThreadLocal.class);

	private Method _cloneMethod;
	private T _initialValue;

}