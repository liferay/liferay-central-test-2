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

package com.liferay.portal.kernel.dao.orm;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * <a href="FinderCacheUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class FinderCacheUtil {

	public static void clearCache() {
		getFinderCache().clearCache();
	}

	public static void clearCache(String className) {
		getFinderCache().clearCache(className);
	}

	public static void clearLocalCache() {
		getFinderCache().clearLocalCache();
	}

	public static FinderCache getFinderCache() {
		return _finderCache;
	}

	/**
	 * @deprecated
	 */
	public static Object getResult(
		String className, String methodName, String[] params, Object[] args,
		SessionFactory sessionFactory) {

		_log.error(
			"Regenerate " + className +
				" via \"ant build-service\" or else caching will not work");

		return null;
	}

	public static Object getResult(
		FinderPath finderPath, Object[] args, SessionFactory sessionFactory) {

		return getFinderCache().getResult(finderPath, args, sessionFactory);
	}

	public static void invalidate() {
		getFinderCache().invalidate();
	}

	/**
	 * @deprecated
	 */
	public static void putResult(
		boolean classNameCacheEnabled, String className, String methodName,
		String[] params, Object[] args, Object result) {

		_log.error(
			"Regenerate " + className +
				" via \"ant build-service\" or else caching will not work");
	}

	public static void putResult(
		FinderPath finderPath, Object[] args, Object result) {

		getFinderCache().putResult(finderPath, args, result);
	}

	public static void removeResult(FinderPath finderPath, Object[] args) {
		getFinderCache().removeResult(finderPath, args);
	}

	public void setFinderCache(FinderCache finderCache) {
		_finderCache = finderCache;
	}

	private static Log _log = LogFactoryUtil.getLog(FinderCacheUtil.class);

	private static FinderCache _finderCache;

}