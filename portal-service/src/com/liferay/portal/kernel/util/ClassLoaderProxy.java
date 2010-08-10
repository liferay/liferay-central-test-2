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

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Brian Wing Shun Chan
 */
public class ClassLoaderProxy {

	public ClassLoaderProxy(
		Object obj, ClassLoader classLoader) {

		_obj = obj;
		_classLoader = classLoader;
	}

	public ClassLoader getClassLoader() {
		return _classLoader;
	}

	public Object invoke(MethodHandler methodHandler) throws Throwable {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(_classLoader);

			return methodHandler.invoke(_obj);
		}
		catch (InvocationTargetException ite) {
			throw translateThrowable(ite.getCause(), contextClassLoader);
		}
		catch (Throwable t) {
			_log.error(t, t);

			throw t;
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	protected Throwable translateThrowable(
		Throwable t1, ClassLoader contextClassLoader) {

		try {
			UnsyncByteArrayOutputStream ubaos =
				new UnsyncByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(ubaos);

			oos.writeObject(t1);

			oos.flush();
			oos.close();

			UnsyncByteArrayInputStream bais = new UnsyncByteArrayInputStream(
				ubaos.unsafeGetByteArray(), 0, ubaos.size());
			ObjectInputStream ois = new ClassLoaderObjectInputStream(
				bais, contextClassLoader);

			t1 = (Throwable)ois.readObject();

			ois.close();

			return t1;
		}
		catch (Throwable t2) {
			_log.error(t2, t2);

			return t2;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ClassLoaderProxy.class);

	private Object _obj;
	private ClassLoader _classLoader;

}