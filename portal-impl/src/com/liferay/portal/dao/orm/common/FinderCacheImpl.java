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

package com.liferay.portal.dao.orm.common;

import com.liferay.portal.kernel.cache.CacheKVP;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.cache.CacheRegistryItem;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.util.InitialThreadLocal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.util.PropsValues;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.map.LRUMap;

/**
 * <a href="FinderCacheImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class FinderCacheImpl implements CacheRegistryItem, FinderCache {

	public static final String CACHE_NAME = FinderCache.class.getName();

	public void afterPropertiesSet() {
		CacheRegistry.register(this);
	}

	public void clearCache() {
		clearLocalCache();

		PortalCache[] portalCaches = _portalCaches.values().toArray(
			new PortalCache[_portalCaches.size()]);

		for (PortalCache portalCache : portalCaches) {
			portalCache.removeAll();
		}
	}

	public void clearCache(String className) {
		clearLocalCache();

		PortalCache portalCache = _getPortalCache(className);

		portalCache.removeAll();
	}

	public void clearLocalCache() {
		if (_localCacheEnabled.get().booleanValue()) {
			Map<String, Object> localCache = _localCache.get();

			localCache.clear();
		}
	}

	public String getRegistryName() {
		return CACHE_NAME;
	}

	public Object getResult(
		FinderPath finderPath, Object[] args, SessionFactory sessionFactory) {

		if (!PropsValues.VALUE_OBJECT_FINDER_CACHE_ENABLED ||
			!finderPath.isFinderCacheEnabled() || !CacheRegistry.isActive()) {

			return null;
		}

		Object primaryKey = null;

		Map<String, Object> localCache = null;

		String localCacheKey = null;

		if (_localCacheEnabled.get().booleanValue()) {
			localCache = _localCache.get();

			localCacheKey = finderPath.encodeLocalCacheKey(args);

			primaryKey = localCache.get(localCacheKey);
		}

		if (primaryKey == null) {
			PortalCache portalCache = _getPortalCache(
				finderPath.getClassName());

			String cacheKey = finderPath.encodeCacheKey(args);

			primaryKey = portalCache.get(cacheKey);

			if (primaryKey != null) {
				if (_localCacheEnabled.get().booleanValue()) {
					localCache.put(localCacheKey, primaryKey);
				}
			}
		}

		if (primaryKey != null) {
			return _primaryKeyToResult(finderPath, sessionFactory, primaryKey);
		}
		else {
			return null;
		}
	}

	public void invalidate() {
		clearCache();
	}

	public void putResult(FinderPath finderPath, Object[] args, Object result) {
		if (!PropsValues.VALUE_OBJECT_FINDER_CACHE_ENABLED ||
			!finderPath.isFinderCacheEnabled() || !CacheRegistry.isActive() ||
			(result == null)) {

			return;
		}

		Object primaryKey = _resultToPrimaryKey(result);

		if (_localCacheEnabled.get().booleanValue()) {
			Map<String, Object> localCache = _localCache.get();

			String localCacheKey = finderPath.encodeLocalCacheKey(args);

			localCache.put(localCacheKey, primaryKey);
		}

		PortalCache portalCache = _getPortalCache(finderPath.getClassName());

		String cacheKey = finderPath.encodeCacheKey(args);

		portalCache.put(cacheKey, primaryKey);
	}

	public void removeResult(FinderPath finderPath, Object[] args) {
		if (!PropsValues.VALUE_OBJECT_FINDER_CACHE_ENABLED ||
			!finderPath.isFinderCacheEnabled() || !CacheRegistry.isActive()) {

			return;
		}

		if (_localCacheEnabled.get().booleanValue()) {
			Map<String, Object> localCache = _localCache.get();

			String localCacheKey = finderPath.encodeLocalCacheKey(args);

			localCache.remove(localCacheKey);
		}

		PortalCache portalCache = _getPortalCache(finderPath.getClassName());

		String cacheKey = finderPath.encodeCacheKey(args);

		portalCache.remove(cacheKey);
	}

	public void setLocalCacheEnabled(boolean localCacheEnabled) {
		if (_localCacheAvailable) {
			_localCacheEnabled.set(Boolean.valueOf(localCacheEnabled));
		}
	}

	public void setMultiVMPool(MultiVMPool multiVMPool) {
		_multiVMPool = multiVMPool;
	}

	private PortalCache _getPortalCache(String className) {
		String groupKey = _GROUP_KEY_PREFIX.concat(className);

		PortalCache portalCache = _portalCaches.get(groupKey);

		if (portalCache == null) {
			portalCache = _multiVMPool.getCache(
				groupKey, PropsValues.VALUE_OBJECT_FINDER_BLOCKING_CACHE);

			_portalCaches.put(groupKey, portalCache);
		}

		return portalCache;
	}

	private Object _primaryKeyToResult(
		FinderPath finderPath, SessionFactory sessionFactory,
		Object primaryKey) {

		if (primaryKey instanceof CacheKVP) {
			CacheKVP cacheKVP = (CacheKVP)primaryKey;

			Class<?> modelClass = cacheKVP.getModelClass();
			Serializable primaryKeyObj = cacheKVP.getPrimaryKeyObj();

			return EntityCacheUtil.loadResult(
				finderPath.isEntityCacheEnabled(), modelClass, primaryKeyObj,
				sessionFactory);
		}
		else if (primaryKey instanceof List<?>) {
			List<Object> cachedList = (List<Object>)primaryKey;

			List<Object> list = new ArrayList<Object>(cachedList.size());

			for (Object curPrimaryKey : cachedList) {
				Object result = _primaryKeyToResult(
					finderPath, sessionFactory, curPrimaryKey);

				list.add(result);
			}

			return list;
		}
		else {
			return primaryKey;
		}
	}

	private Object _resultToPrimaryKey(Object result) {
		if (result instanceof BaseModel<?>) {
			BaseModel<?> model = (BaseModel<?>)result;

			Class<?> modelClass = model.getClass();
			Serializable primaryKeyObj = model.getPrimaryKeyObj();

			return new CacheKVP(modelClass, primaryKeyObj);
		}
		else if (result instanceof List<?>) {
			List<Object> list = (List<Object>)result;

			List<Object> cachedList = new ArrayList<Object>(list.size());

			for (Object curResult : list) {
				Object primaryKey = _resultToPrimaryKey(curResult);

				cachedList.add(primaryKey);
			}

			return cachedList;
		}
		else {
			return result;
		}
	}

	private static final String _GROUP_KEY_PREFIX = CACHE_NAME.concat(
		StringPool.PERIOD);

	private static ThreadLocal<LRUMap> _localCache;
	private static boolean _localCacheAvailable;
	private static ThreadLocal<Boolean> _localCacheEnabled =
		new InitialThreadLocal<Boolean>(Boolean.FALSE);

	static {
		if (PropsValues.VALUE_OBJECT_FINDER_THREAD_LOCAL_CACHE_MAX_SIZE > 0) {
			_localCache = new InitialThreadLocal<LRUMap>(new LRUMap(
				PropsValues.VALUE_OBJECT_FINDER_THREAD_LOCAL_CACHE_MAX_SIZE));
			_localCacheAvailable = true;
		}
	}

	private MultiVMPool _multiVMPool;
	private Map<String, PortalCache> _portalCaches =
		new ConcurrentHashMap<String, PortalCache>();

}