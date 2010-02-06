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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

/**
 * <a href="DynamicQueryFactoryImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DynamicQueryFactoryImpl implements DynamicQueryFactory {

	public DynamicQuery forClass(Class<?> clazz) {
		clazz = getImplClass(clazz);

		return new DynamicQueryImpl(DetachedCriteria.forClass(clazz));
	}

	public DynamicQuery forClass(Class<?> clazz, ClassLoader classLoader) {
		clazz = getImplClass(clazz, classLoader);

		return new DynamicQueryImpl(DetachedCriteria.forClass(clazz));
	}

	public DynamicQuery forClass(Class<?> clazz, String alias) {
		clazz = getImplClass(clazz);

		return new DynamicQueryImpl(DetachedCriteria.forClass(clazz, alias));
	}

	public DynamicQuery forClass(
		Class<?> clazz, String alias, ClassLoader classLoader) {

		clazz = getImplClass(clazz, classLoader);

		return new DynamicQueryImpl(DetachedCriteria.forClass(clazz, alias));
	}

	protected Class<?> getImplClass(Class<?> clazz) {
		return getImplClass(clazz, null);
	}

	protected Class<?> getImplClass(Class<?> clazz, ClassLoader classLoader) {
		if (!clazz.getName().endsWith("Impl")) {
			String implClassName =
				clazz.getPackage().getName() + ".impl." +
					clazz.getSimpleName() + "Impl";

			clazz = _classMap.get(implClassName);

			if (clazz == null) {
				try {
					if (classLoader == null) {
						Thread currentThread = Thread.currentThread();

						classLoader = currentThread.getContextClassLoader();
					}

					clazz = classLoader.loadClass(implClassName);

					_classMap.put(implClassName, clazz);
				}
				catch (Exception e) {
					_log.error("Unable find model " + implClassName, e);
				}
			}
		}

		return clazz;
	}

	private static Log _log =
		LogFactoryUtil.getLog(DynamicQueryFactoryImpl.class);

	private Map<String, Class<?>> _classMap = new HashMap<String, Class<?>>();

}