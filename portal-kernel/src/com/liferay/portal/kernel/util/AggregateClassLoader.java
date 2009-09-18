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

/**
 * <a href="AggregateClassLoader.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AggregateClassLoader extends ClassLoader {

	public static ClassLoader getAggregateClassLoader(
		ClassLoader[] classLoaders) {

		if ((classLoaders == null) || (classLoaders.length == 0)) {
			return null;
		}

		if (classLoaders.length == 1) {
			return classLoaders[0];
		}

		AggregateClassLoader aggregateLoader = new AggregateClassLoader(
			classLoaders[1], classLoaders[0]);

		for (int i = 2; i < classLoaders.length; i++) {
			aggregateLoader = new AggregateClassLoader(
				classLoaders[i], aggregateLoader);
		}

		return aggregateLoader;
	}

	public AggregateClassLoader(
		ClassLoader defaultClassLoader, ClassLoader backupClassLoader) {

		super(defaultClassLoader);

		_backupClassLoader = backupClassLoader;
	}

	public Class<?> loadClass(String name) throws ClassNotFoundException {
		try {
			return super.loadClass(name);
		}
		catch (ClassNotFoundException cnfe) {
			if (_backupClassLoader != null) {
				return _backupClassLoader.loadClass(name);
			}
			throw cnfe;
		}
	}

	private ClassLoader _backupClassLoader;

}