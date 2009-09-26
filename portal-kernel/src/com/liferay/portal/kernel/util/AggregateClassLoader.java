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
		ClassLoader[] classLoaders) {

		if ((classLoaders == null) || (classLoaders.length == 0)) {
			return null;
		}

		return getAggregateClassLoader(classLoaders[0], classLoaders);
	}

	public static ClassLoader getAggregateClassLoader(
		ClassLoader parentClassLoader, ClassLoader[] classLoaders) {

		if ((classLoaders == null) || (classLoaders.length == 0)) {
			return null;
		}

		if (classLoaders.length == 1) {
			return classLoaders[0];
		}

		AggregateClassLoader aggregateLoader =
			new AggregateClassLoader(parentClassLoader);

		for (ClassLoader classLoader : classLoaders) {
			aggregateLoader.addClassLoader(classLoader);
		}

		return aggregateLoader;
	}

	public AggregateClassLoader(ClassLoader parentClassLoader) {

		super(parentClassLoader);
	}

	public void addClassLoader(ClassLoader classLoader) {
		if (_classLoaders.contains(classLoader)) {
			return;
		}

		if ((classLoader instanceof AggregateClassLoader) &&
			(classLoader.getParent().equals(getParent()))){

			AggregateClassLoader toConsolidate =
				(AggregateClassLoader)classLoader;

			for (ClassLoader childLoader : toConsolidate.getClassLoaders()) {
				addClassLoader(childLoader);
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

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof AggregateClassLoader)) {
			return false;
		}

		AggregateClassLoader that = (AggregateClassLoader) o;

		if (_classLoaders.equals(that._classLoaders) &&
			(((getParent() == null) && (that.getParent() == null)) ||
				((getParent() != null) &&
					(getParent().equals(that.getParent()))))) {
			return true;
		}

		return false;
	}

	public int hashCode() {
		return _classLoaders != null ? _classLoaders.hashCode() : 0;
	}

	List<ClassLoaderWrapper> getClassLoaders() {
		return _classLoaders;
	}

	protected Class<?> findClass(String name) throws ClassNotFoundException {
		for (ClassLoaderWrapper classLoader : _classLoaders) {
			try {
				return classLoader.findClass(name);
			}
			catch (ClassNotFoundException e) {
				//nothing to do here...
			}
		}
		throw new ClassNotFoundException("Unable to find class: " + name);
	}

	protected Class<?> loadClass(String name, boolean resolve)
		throws ClassNotFoundException {

		Class clazz = null;

		for (ClassLoaderWrapper classLoader : _classLoaders) {
			try {
				clazz = classLoader.loadClass(name, resolve);
			}
			catch (ClassNotFoundException e) {
				//nothing to do here...
			}
		}

		if (clazz == null) {
			clazz = super.loadClass(name, resolve);
		}
		else if (resolve) {
			resolveClass(clazz);
		}

		return clazz;
	}

	// the following wrapper is key since we need access to the
	// findClass findClassMethod.  An aggregate needs to be able
	// to call the parent's findClass findClassMethod.  However, since
	// this findClassMethod is normally protected, we must use reflection
	// to access. 
	private static class ClassLoaderWrapper extends ClassLoader {
		public ClassLoaderWrapper(ClassLoader classLoader) {
			super(classLoader);
		}

		public Class<?> loadClass(String name, boolean resolve)
			throws ClassNotFoundException {
			return super.loadClass(name, resolve);
		}

		public Class<?> findClass(String name) throws ClassNotFoundException {
			try {
				return (Class) findClassMethod.invoke(getParent(), name);
			}
			catch (InvocationTargetException e) {
				throw new ClassNotFoundException(
					"Unable to find class: " + name, e.getTargetException());
			}
			catch (Exception e) {
				throw new ClassNotFoundException(
					"Unable to find class: " + name, e);
			}
		}

		public boolean equals(Object obj) {
			if (!(obj instanceof ClassLoader)) {
				return false;
			}
			return getParent().equals(obj);
		}

		private static Method findClassMethod;

		static {
			try {
				findClassMethod = ClassLoader.class.getDeclaredMethod(
					"findClass", String.class);
				findClassMethod.setAccessible(true);
			}
			catch (NoSuchMethodException e) {
				if (_log.isErrorEnabled()) {
					_log.error(
						"Unable to locate findClass method", e);
				}
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AggregateClassLoader.class);

	//cannot use a Set because order of class lookup matters.
	private List<ClassLoaderWrapper> _classLoaders =
		new ArrayList<ClassLoaderWrapper>();

}