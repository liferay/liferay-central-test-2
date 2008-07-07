/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.spring.hibernate;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.hibernate.SessionFactory;

/**
 * <a href="FinderCacheUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class FinderCacheUtil {

	public static void clearCache() {
		getFinderCache().clearCache();
	}

	public static void clearCache(String className) {
		getFinderCache().clearCache(className);
	}

	public static FinderCache getFinderCache() {
		return _getUtil()._finderCache;
	}

	public static Object getResult(
		String className, String methodName, String[] params, Object[] args,
		SessionFactory sessionFactory) {

		return getFinderCache().getResult(
			className, methodName, params, args, sessionFactory);
	}

	public static Object getResult(
		String sql, String[] classNames, String methodName, String[] params,
		Object[] args, SessionFactory sessionFactory) {

		return getFinderCache().getResult(
			sql, classNames, methodName, params, args, sessionFactory);
	}

	public static void putResult(
		boolean classNameCacheEnabled, String className, String methodName,
		String[] params, Object[] args, Object result) {

		getFinderCache().putResult(
			classNameCacheEnabled, className, methodName, params, args, result);
	}

	public static void putResult(
		String sql, boolean[] classNamesCacheEnabled, String[] classNames,
		String methodName, String[] params, Object[] args, Object result) {

		getFinderCache().putResult(
			sql, classNamesCacheEnabled, classNames, methodName, params, args,
			result);
	}

	public static void invalidate() {
		getFinderCache().invalidate();
	}

	public void setFinderCache(FinderCache finderCache) {
		_finderCache = finderCache;
	}

	private static FinderCacheUtil _getUtil() {
		if (_util == null) {
			_util = (FinderCacheUtil)PortalBeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static final String _UTIL = FinderCacheUtil.class.getName();

	private static FinderCacheUtil _util;

	private FinderCache _finderCache;

}