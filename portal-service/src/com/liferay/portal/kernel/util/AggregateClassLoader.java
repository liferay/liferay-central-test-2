/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Michael C. Han
 * @author Shuyang Zhou
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
		_parentClassLoaderReference = new WeakReference<ClassLoader>(
			classLoader);
	}

	public void addClassLoader(ClassLoader classLoader) {
		if (getClassLoaders().contains(classLoader)) {
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
			_classLoaderReferences.add(
				new WeakReference<ClassLoader>(classLoader));
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

		if (_classLoaderReferences.equals(
				aggregateClassLoader._classLoaderReferences) &&
			(((getParent() == null) &&
			  (aggregateClassLoader.getParent() == null)) ||
			 ((getParent() != null) &&
			  (getParent().equals(aggregateClassLoader.getParent()))))) {

			return true;
		}

		return false;
	}

	public List<ClassLoader> getClassLoaders() {
		List<ClassLoader> classLoaders = new ArrayList<ClassLoader>(
			_classLoaderReferences.size());

		Iterator<WeakReference<ClassLoader>> itr =
			_classLoaderReferences.iterator();

		while (itr.hasNext()) {
			WeakReference<ClassLoader> weakReference = itr.next();

			ClassLoader classLoader = weakReference.get();

			if (classLoader == null) {
				itr.remove();
			}
			else {
				classLoaders.add(classLoader);
			}
		}

		return classLoaders;
	}

	public int hashCode() {
		if (_classLoaderReferences != null) {
			return _classLoaderReferences.hashCode();
		}
		else {
			return 0;
		}
	}

	protected Class<?> findClass(String name) throws ClassNotFoundException {
		for (ClassLoader classLoader : getClassLoaders()) {
			try {
				return _findClass(classLoader, name);
			}
			catch (ClassNotFoundException cnfe) {
			}
		}

		throw new ClassNotFoundException("Unable to find class " + name);
	}

	protected Class<?> loadClass(String name, boolean resolve)
		throws ClassNotFoundException {

		Class<?> loadedClass = null;

		for (ClassLoader classLoader : getClassLoaders()) {
			try {
				loadedClass = _loadClass(classLoader, name, resolve);

				break;
			}
			catch (ClassNotFoundException cnfe) {
			}
		}

		if (loadedClass == null) {
			ClassLoader parentClassLoader = _parentClassLoaderReference.get();

			if (parentClassLoader == null) {
				throw new ClassNotFoundException(
					"Parent class loader has been garbage collected");
			}

			loadedClass = _loadClass(parentClassLoader, name, resolve);
		}
		else if (resolve) {
			resolveClass(loadedClass);
		}

		return loadedClass;
	}

	private static Log _log = LogFactoryUtil.getLog(AggregateClassLoader.class);

	private List<WeakReference<ClassLoader>> _classLoaderReferences =
		new ArrayList<WeakReference<ClassLoader>>();

	private static Class<?> _findClass(ClassLoader classLoader, String name)
		throws ClassNotFoundException {

		try {
			return (Class<?>) _findClassMethod.invoke(classLoader, name);
		}
		catch (InvocationTargetException ite) {
			throw new ClassNotFoundException(
				"Unable to find class " + name, ite.getTargetException());
		}
		catch (Exception e) {
			throw new ClassNotFoundException("Unable to find class " + name, e);
		}
	}

	private static Class<?> _loadClass(
			ClassLoader classLoader, String name, boolean resolve)
		throws ClassNotFoundException {

		try {
			return (Class<?>) _loadClassMethod.invoke(
				classLoader, name, resolve);
		}
		catch (InvocationTargetException ite) {
			throw new ClassNotFoundException(
				"Unable to load class " + name, ite.getTargetException());
		}
		catch (Exception e) {
			throw new ClassNotFoundException(
				"Unable to load class " + name, e);
		}
	}

	private static Method _findClassMethod;
	private static Method _loadClassMethod;

	private WeakReference<ClassLoader> _parentClassLoaderReference;

	static {
		try {
			_findClassMethod = ReflectionUtil.getDeclaredMethod(
				ClassLoader.class, "findClass", String.class);
			_loadClassMethod = ReflectionUtil.getDeclaredMethod(
				ClassLoader.class, "loadClass", String.class, boolean.class);
		}
		catch (Exception e) {
			if (_log.isErrorEnabled()) {
				_log.error("Unable to locate required methods", e);
			}
		}
	}

}