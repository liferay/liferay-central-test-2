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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.lang.Object;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ClassLoaderProxy.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ClassLoaderProxy {

	public ClassLoaderProxy(Object obj, ClassLoader classLoader) {
		_obj = obj;
		_classLoader = classLoader;
	}

	public ClassLoader getClassLoader() {
		return _classLoader;
	}

	public Object invoke(String methodName, Object[] args) throws Throwable {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(_classLoader);

			Class<?> classObj = Class.forName(
				_obj.getClass().getName(), true, _classLoader);

			List<Class<?>> parameterTypes = new ArrayList<Class<?>>();

			for (int i = 0; i < args.length; i++) {
				Object arg = args[i];

				Class<?> argClass = Class.forName(
					arg.getClass().getName(), true, _classLoader);

				if (ClassUtil.isSubclass(argClass, PrimitiveWrapper.class)) {
					MethodKey methodKey = new MethodKey(
						argClass.getName(), "getValue", null);

					Method method = MethodCache.get(methodKey);

					args[i] = method.invoke(arg, (Object[])null);

					argClass = (Class<?>)argClass.getField("TYPE").get(arg);
				}

				if (ClassUtil.isSubclass(argClass, NullWrapper.class)) {
					NullWrapper nullWrapper = (NullWrapper)arg;

					argClass = Class.forName(
						nullWrapper.getClassName(), true, _classLoader);

					args[i] = null;
				}

				parameterTypes.add(argClass);
			}

			Method method = null;

			try {
				method = classObj.getMethod(
					methodName,
					parameterTypes.toArray(new Class[parameterTypes.size()]));
			}
			catch (NoSuchMethodException nsme) {
				Method[] methods = ((Class<?>)classObj).getMethods();

				for (int i = 0; i < methods.length; i++) {
					Class<?>[] methodParameterTypes =
						methods[i].getParameterTypes();

					if (methods[i].getName().equals(methodName) &&
						methodParameterTypes.length == parameterTypes.size()) {

						boolean correctParams = true;

						for (int j = 0; j < parameterTypes.size(); j++) {
							Class<?> a = parameterTypes.get(j);
							Class<?> b = methodParameterTypes[j];

							if (!ClassUtil.isSubclass(a, b)) {
								correctParams = false;

								break;
							}
						}

						if (correctParams) {
							method = methods[i];

							break;
						}
					}
				}

				if (method == null) {
					throw nsme;
				}
			}

			return method.invoke(_obj, args);
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
			UnsyncByteArrayOutputStream baos =
				new UnsyncByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);

			oos.writeObject(t1);

			oos.flush();
			oos.close();

			byte[] bytes = baos.toByteArray();

			UnsyncByteArrayInputStream bais =
				new UnsyncByteArrayInputStream(bytes);
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