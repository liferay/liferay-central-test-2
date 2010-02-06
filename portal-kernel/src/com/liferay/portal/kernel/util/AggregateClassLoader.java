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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <a href="AggregateClassLoader.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Michael C. Han
 */
public class AggregateClassLoader extends ClassLoader {

	public static ClassLoader getAggregateClassLoader(
		ClassLoader parentClassLoader, ClassLoader[] classLoaders) {

		if ((classLoaders == null) || (classLoaders.length == 0)) {
			return null;
		}

		if (classLoaders.length == 1) {
			return classLoaders[0];
		}

		AggregateClassLoader aggregateClassLoader = new AggregateClassLoader(
			parentClassLoader);

		for (ClassLoader classLoader : classLoaders) {
			aggregateClassLoader.addClassLoader(classLoader);
		}

		return aggregateClassLoader;
	}

	public static ClassLoader getAggregateClassLoader(
		ClassLoader[] classLoaders) {

		if ((classLoaders == null) || (classLoaders.length == 0)) {
			return null;
		}

		return getAggregateClassLoader(classLoaders[0], classLoaders);
	}

	public AggregateClassLoader(ClassLoader classLoader) {
		super(classLoader);
	}

	public void addClassLoader(ClassLoader classLoader) {
		if (_classLoaders.contains(classLoader)) {
			return;
		}

		if ((classLoader instanceof AggregateClassLoader) &&
			(classLoader.getParent().equals(getParent()))){

			AggregateClassLoader aggregateClassLoader =
				(AggregateClassLoader)classLoader;

			for (ClassLoader curClassLoader :
					aggregateClassLoader.getClassLoaders()) {

				addClassLoader(curClassLoader);
			}
		}
		else {
			if (classLoader instanceof ClassLoaderWrapper) {
				_classLoaders.add((ClassLoaderWrapper)classLoader);
			}
			else {
				_classLoaders.add(new ClassLoaderWrapper(classLoader));
			}
		}
	}

	public void addClassLoader(ClassLoader... classLoaders) {
		for (ClassLoader classLoader : classLoaders) {
			addClassLoader(classLoader);
		}
	}

	public void addClassLoader(Collection<ClassLoader> classLoaders) {
		for (ClassLoader classLoader : classLoaders) {
			addClassLoader(classLoader);
		}
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AggregateClassLoader)) {
			return false;
		}

		AggregateClassLoader aggregateClassLoader = (AggregateClassLoader)obj;

		if (_classLoaders.equals(aggregateClassLoader._classLoaders) &&
			(((getParent() == null) &&
			  (aggregateClassLoader.getParent() == null)) ||
			 ((getParent() != null) &&
			  (getParent().equals(aggregateClassLoader.getParent()))))) {

			return true;
		}

		return false;
	}

	public List<ClassLoaderWrapper> getClassLoaders() {
		return _classLoaders;
	}

	public int hashCode() {
		if (_classLoaders != null) {
			return _classLoaders.hashCode();
		}
		else {
			return 0;
		}
	}

	protected Class<?> findClass(String name) throws ClassNotFoundException {
		for (ClassLoaderWrapper classLoader : _classLoaders) {
			try {
				return classLoader.findClass(name);
			}
			catch (ClassNotFoundException cnfe) {
			}
		}

		throw new ClassNotFoundException("Unable to find class " + name);
	}

	protected Class<?> loadClass(String name, boolean resolve)
		throws ClassNotFoundException {

		Class<?> loadedClass = null;

		for (ClassLoaderWrapper classLoader : _classLoaders) {
			try {
				loadedClass = classLoader.loadClass(name, resolve);

				break;
			}
			catch (ClassNotFoundException cnfe) {
			}
		}

		if (loadedClass == null) {
			loadedClass = super.loadClass(name, resolve);
		}
		else if (resolve) {
			resolveClass(loadedClass);
		}

		return loadedClass;
	}

	private static Log _log = LogFactoryUtil.getLog(AggregateClassLoader.class);

	private List<ClassLoaderWrapper> _classLoaders =
		new ArrayList<ClassLoaderWrapper>();

	// The following wrapper is key since we need access to the findClass
	// method. An aggregate needs to be able to call the parent's findClass
	// method. However, since this findClass method is normally protected, we
	// must use reflection to access.

	private static class ClassLoaderWrapper extends ClassLoader {

		public ClassLoaderWrapper(ClassLoader classLoader) {
			super(classLoader);
		}

		public boolean equals(Object obj) {
			if (!(obj instanceof ClassLoader)) {
				return false;
			}

			return getParent().equals(obj);
		}

		public Class<?> findClass(String name) throws ClassNotFoundException {
			try {
				return (Class<?>)_findClassMethod.invoke(getParent(), name);
			}
			catch (InvocationTargetException ite) {
				throw new ClassNotFoundException(
					"Unable to find class " + name, ite.getTargetException());
			}
			catch (Exception e) {
				throw new ClassNotFoundException(
					"Unable to find class " + name, e);
			}
		}

		public Class<?> loadClass(String name, boolean resolve)
			throws ClassNotFoundException {

			return super.loadClass(name, resolve);
		}

		private static Method _findClassMethod;

		static {
			try {
				_findClassMethod = ClassLoader.class.getDeclaredMethod(
					"findClass", String.class);

				_findClassMethod.setAccessible(true);
			}
			catch (NoSuchMethodException nsme) {
				if (_log.isErrorEnabled()) {
					_log.error("Unable to locate findClass method", nsme);
				}
			}
		}

	}

}