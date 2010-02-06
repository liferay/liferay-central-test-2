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

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;

/**
 * <a href="SimplePojoClp.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * A class loader proxy able to serialize simple POJOs between two class
 * loaders. It only works for simple POJOs following the Java Beans semantics.
 * The local and remote classes do not have to match or even be derived from
 * each other as long as their properties match. Any bean properties that the
 * source bean exposes but the target bean does not will silently be ignored.
 * </p>
 *
 * @author Micha Kiener
 * @author Brian Wing Shun Chan
 */
public class SimplePojoClp<T> {

	public SimplePojoClp(
			Class<? extends T> localImplementationClass,
			ClassLoader remoteClassLoader)
		throws ClassNotFoundException {

		this(
			localImplementationClass, remoteClassLoader,
			localImplementationClass.getName());
	}

	public SimplePojoClp(
			Class<? extends T> localImplementationClass,
			ClassLoader remoteClassLoader, String remoteImplementationClassName)
		throws ClassNotFoundException {

		_localImplementationClass = localImplementationClass;
		_remoteClassLoader = remoteClassLoader;
		_remoteImplementationClass = _remoteClassLoader.loadClass(
			remoteImplementationClassName);
	}

	public T createLocalObject(Object remoteInstance)
		throws IllegalAccessException, InstantiationException {

		T localInstance = _localImplementationClass.newInstance();

		BeanPropertiesUtil.copyProperties(
			remoteInstance, localInstance, _localImplementationClass);

		return localInstance;
	}

	public Object createRemoteObject(T localInstance)
		throws IllegalAccessException, InstantiationException {

		Object remoteInstance = _remoteImplementationClass.newInstance();

		BeanPropertiesUtil.copyProperties(
			localInstance, remoteInstance, _remoteImplementationClass);

		return remoteInstance;
	}

	private Class<? extends T> _localImplementationClass;
	private ClassLoader _remoteClassLoader;
	private Class<?> _remoteImplementationClass;

}