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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.SystemException;

import java.lang.reflect.Field;

import net.sf.ehcache.CacheManager;

import org.hibernate.cache.CacheProvider;

/**
 * <a href="EhCacheProvider.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
@SuppressWarnings("deprecation")
public class EhCacheProvider extends CacheProviderWrapper {

	public static CacheManager getCacheManager() throws SystemException {
		try {
			Class<?> clazz = Class.forName(
				"net.sf.ehcache.hibernate.EhCacheProvider");

			Field field = clazz.getDeclaredField("manager");

			field.setAccessible(true);

			CacheManager cacheManager = (CacheManager)field.get(
				_cacheProvider);

			if (cacheManager == null) {
				throw new SystemException("CacheManager has been initialized");
			}

			return cacheManager;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public EhCacheProvider() {
		super("net.sf.ehcache.hibernate.EhCacheProvider");

		_cacheProvider = cacheProvider;
	}

	private static CacheProvider _cacheProvider;

}