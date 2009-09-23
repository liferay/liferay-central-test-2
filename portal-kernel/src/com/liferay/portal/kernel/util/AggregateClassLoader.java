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

import java.util.ArrayList;
import java.util.Arrays;
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
		ClassLoader... classLoaders) {

		if ((classLoaders == null) || (classLoaders.length == 0)) {
			return null;
		}

		if (classLoaders.length == 1) {
			return classLoaders[0];
		}

		AggregateClassLoader aggregateLoader = new AggregateClassLoader();
		aggregateLoader.addClassLoader(Arrays.asList(classLoaders));

		return aggregateLoader;
	}

	public AggregateClassLoader() {
		super();
	}

	public AggregateClassLoader(ClassLoader parentClassLoader) {
		super(parentClassLoader);
	}

	@Deprecated
	/**
	 * Used for backwards compatibility to 5.1 and 5.2
	 */
	public AggregateClassLoader(
		ClassLoader classLoader1, ClassLoader classLoader2) {
		super();
		addClassLoader(classLoader1);
		addClassLoader(classLoader2);

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
			_classLoaders.add(classLoader);
		}
	}
	
	public void addClassLoader(Collection<ClassLoader> classLoaders) {
		for (ClassLoader classLoader : classLoaders) {
			addClassLoader(classLoader);
		}
	}

	protected Class<?> loadClass(String name, boolean resolve)
		throws ClassNotFoundException {

		Class clazz = null;

		for (ClassLoader classLoader : _classLoaders) {
			try {
				clazz = classLoader.loadClass(name);
			}
			catch (ClassNotFoundException e) {
				//nothing to do here...
			}
		}

		if (clazz == null) {
			clazz = getParent().loadClass(name);
		}

		if (resolve) {
			resolveClass(clazz);
		}

		return clazz;
	}

	List<ClassLoader> getClassLoaders() {
		return _classLoaders;
	}

	//cannot use a Set because order of class lookup matters.
	private List<ClassLoader> _classLoaders = new ArrayList<ClassLoader>();

}