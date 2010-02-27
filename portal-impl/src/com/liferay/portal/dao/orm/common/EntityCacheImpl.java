/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.dao.orm.common;

import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.cache.CacheRegistryItem;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.util.PropsValues;

import java.io.Serializable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.map.LRUMap;

/**
 * <a href="EntityCacheImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class EntityCacheImpl implements CacheRegistryItem, EntityCache {

	public static final String CACHE_NAME = EntityCache.class.getName();

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
		if (_localCacheAvailable) {
			Map<String, Object> localCache = _localCache.get();

			localCache.clear();
		}
	}

	public String getRegistryName() {
		return CACHE_NAME;
	}

	public Object getResult(
		boolean entityCacheEnabled, Class<?> classObj,
		Serializable primaryKeyObj, SessionFactory sessionFactory) {

		if (!PropsValues.VALUE_OBJECT_ENTITY_CACHE_ENABLED ||
			!entityCacheEnabled || !CacheRegistry.isActive()) {

			return null;
		}

		Object result = null;

		Map<String, Object> localCache = null;

		String localCacheKey = null;

		if (_localCacheAvailable) {
			localCache = _localCache.get();

			localCacheKey = _encodeLocalCacheKey(classObj, primaryKeyObj);

			result = localCache.get(localCacheKey);
		}

		if (result == null) {
			PortalCache portalCache = _getPortalCache(classObj.getName());

			String cacheKey = _encodeCacheKey(primaryKeyObj);

			result = portalCache.get(cacheKey);

			if (result == null) {
				result = StringPool.BLANK;

				portalCache.put(cacheKey, result);
			}

			if (_localCacheAvailable) {
				localCache.put(localCacheKey, result);
			}
		}

		if (result != null) {
			result = _objectToResult(result);
		}

		return result;
	}

	public void invalidate() {
		clearCache();
	}

	public Object loadResult(
		boolean entityCacheEnabled, Class<?> classObj,
		Serializable primaryKeyObj, SessionFactory sessionFactory) {

		if (!PropsValues.VALUE_OBJECT_ENTITY_CACHE_ENABLED ||
			!entityCacheEnabled || !CacheRegistry.isActive()) {

			Session session = null;

			try {
				session = sessionFactory.openSession();

				return session.load(classObj, primaryKeyObj);
			}
			finally {
				sessionFactory.closeSession(session);
			}
		}

		Object result = null;

		Map<String, Object> localCache = null;

		String localCacheKey = null;

		if (_localCacheAvailable) {
			localCache = _localCache.get();

			localCacheKey = _encodeLocalCacheKey(classObj, primaryKeyObj);

			result = localCache.get(localCacheKey);
		}

		if (result == null) {
			PortalCache portalCache = _getPortalCache(classObj.getName());

			String cacheKey = _encodeCacheKey(primaryKeyObj);

			result = portalCache.get(cacheKey);

			if (result == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Load " + classObj + " " + primaryKeyObj +
							" from session");
				}

				Session session = null;

				try {
					session = sessionFactory.openSession();

					result = session.load(classObj, primaryKeyObj);
				}
				finally {
					if (result == null) {
						result = StringPool.BLANK;
					}
					else {
						result = _objectToResult(result);
					}

					portalCache.put(cacheKey, result);

					sessionFactory.closeSession(session);
				}
			}

			if (_localCacheAvailable) {
				localCache.put(localCacheKey, result);
			}
		}

		result = _objectToResult(result);

		return result;
	}

	public void putResult(
		boolean entityCacheEnabled, Class<?> classObj,
		Serializable primaryKeyObj, Object result) {

		if (!PropsValues.VALUE_OBJECT_ENTITY_CACHE_ENABLED ||
			!entityCacheEnabled || !CacheRegistry.isActive() ||
			(result == null)) {

			return;
		}

		result = _objectToResult(result);

		if (_localCacheAvailable) {
			Map<String, Object> localCache = _localCache.get();

			String localCacheKey = _encodeLocalCacheKey(
				classObj, primaryKeyObj);

			localCache.put(localCacheKey, result);
		}

		PortalCache portalCache = _getPortalCache(classObj.getName());

		String cacheKey = _encodeCacheKey(primaryKeyObj);

		portalCache.put(cacheKey, result);
	}

	public void removeResult(
		boolean entityCacheEnabled, Class<?> classObj,
		Serializable primaryKeyObj) {

		if (!PropsValues.VALUE_OBJECT_ENTITY_CACHE_ENABLED ||
			!entityCacheEnabled || !CacheRegistry.isActive()) {

			return;
		}

		if (_localCacheAvailable) {
			Map<String, Object> localCache = _localCache.get();

			String localCacheKey = _encodeLocalCacheKey(
				classObj, primaryKeyObj);

			localCache.remove(localCacheKey);
		}

		PortalCache portalCache = _getPortalCache(classObj.getName());

		String cacheKey = _encodeCacheKey(primaryKeyObj);

		portalCache.remove(cacheKey);
	}

	public void setMultiVMPool(MultiVMPool multiVMPool) {
		_multiVMPool = multiVMPool;
	}

	private String _encodeCacheKey(Serializable primaryKeyObj) {
		return String.valueOf(primaryKeyObj);
	}

	private String _encodeLocalCacheKey(
		Class<?> classObj, Serializable primaryKeyObj) {

		return classObj.getName().concat(StringPool.PERIOD).concat(
			primaryKeyObj.toString());
	}

	private PortalCache _getPortalCache(String className) {
		String groupKey = _GROUP_KEY_PREFIX.concat(className);

		PortalCache portalCache = _portalCaches.get(groupKey);

		if (portalCache == null) {
			portalCache = _multiVMPool.getCache(
				groupKey, PropsValues.VALUE_OBJECT_ENTITY_BLOCKING_CACHE);

			_portalCaches.put(groupKey, portalCache);
		}

		return portalCache;
	}

	private Object _objectToResult(Object result) {
		if (result instanceof String) {
			return null;
		}
		else {
			result = ((BaseModel<?>)result).clone();

			BaseModel<?> model = (BaseModel<?>)result;

			model.setCachedModel(true);

			return model;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(EntityCacheImpl.class);

	private static final String _GROUP_KEY_PREFIX = CACHE_NAME.concat(
		StringPool.PERIOD);

	private static ThreadLocal<LRUMap> _localCache;
	private static boolean _localCacheAvailable;

	static {
		if (PropsValues.VALUE_OBJECT_ENTITY_THREAD_LOCAL_CACHE_MAX_SIZE > 0) {
			_localCache = new AutoResetThreadLocal<LRUMap>(new LRUMap(
				PropsValues.VALUE_OBJECT_ENTITY_THREAD_LOCAL_CACHE_MAX_SIZE));
			_localCacheAvailable = true;
		}
	}

	private MultiVMPool _multiVMPool;
	private Map<String, PortalCache> _portalCaches =
		new ConcurrentHashMap<String, PortalCache>();

}