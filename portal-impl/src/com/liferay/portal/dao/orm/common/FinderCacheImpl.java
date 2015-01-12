/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.cache.CacheManagerListener;
import com.liferay.portal.kernel.cache.CacheRegistryItem;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.util.PropsValues;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.collections.map.LRUMap;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
@DoPrivileged
public class FinderCacheImpl
	implements CacheManagerListener, CacheRegistryItem, FinderCache {

	public static final String CACHE_NAME = FinderCache.class.getName();

	public void afterPropertiesSet() {
		CacheRegistryUtil.register(this);
	}

	@Override
	public void clearCache() {
		clearLocalCache();

		for (PortalCache<?, ?> portalCache : _portalCaches.values()) {
			portalCache.removeAll();
		}
	}

	@Override
	public void clearCache(String className) {
		clearLocalCache();

		PortalCache<?, ?> portalCache = _getPortalCache(className, true);

		if (portalCache != null) {
			portalCache.removeAll();
		}
	}

	@Override
	public void clearLocalCache() {
		if (_LOCAL_CACHE_AVAILABLE) {
			_localCache.remove();
		}
	}

	@Override
	public void dispose() {
		_portalCaches.clear();
	}

	@Override
	public String getRegistryName() {
		return CACHE_NAME;
	}

	@Override
	public Object getResult(
		FinderPath finderPath, Object[] args,
		BasePersistenceImpl<? extends BaseModel<?>> basePersistenceImpl) {

		if (!PropsValues.VALUE_OBJECT_FINDER_CACHE_ENABLED ||
			!finderPath.isFinderCacheEnabled() ||
			!CacheRegistryUtil.isActive()) {

			return null;
		}

		Serializable primaryKey = null;

		Map<Serializable, Serializable> localCache = null;

		Serializable localCacheKey = null;

		if (_LOCAL_CACHE_AVAILABLE) {
			localCache = _localCache.get();

			localCacheKey = finderPath.encodeLocalCacheKey(args);

			primaryKey = localCache.get(localCacheKey);
		}

		if (primaryKey == null) {
			PortalCache<Serializable, Serializable> portalCache =
				_getPortalCache(finderPath.getCacheName(), true);

			Serializable cacheKey = finderPath.encodeCacheKey(args);

			primaryKey = portalCache.get(cacheKey);

			if (primaryKey != null) {
				if (_LOCAL_CACHE_AVAILABLE) {
					localCache.put(localCacheKey, primaryKey);
				}
			}
		}

		if (primaryKey != null) {
			return _primaryKeyToResult(
				finderPath, basePersistenceImpl, primaryKey);
		}

		return null;
	}

	@Override
	public void init() {
	}

	@Override
	public void invalidate() {
		clearCache();
	}

	@Override
	public void notifyCacheAdded(String name) {
	}

	@Override
	public void notifyCacheRemoved(String name) {
		_portalCaches.remove(name);
	}

	@Override
	public void putResult(FinderPath finderPath, Object[] args, Object result) {
		putResult(finderPath, args, result, true);
	}

	@Override
	public void putResult(
		FinderPath finderPath, Object[] args, Object result, boolean quiet) {

		if (!PropsValues.VALUE_OBJECT_FINDER_CACHE_ENABLED ||
			!finderPath.isFinderCacheEnabled() ||
			!CacheRegistryUtil.isActive() ||
			(result == null)) {

			return;
		}

		Serializable primaryKey = _resultToPrimaryKey((Serializable)result);

		if (_LOCAL_CACHE_AVAILABLE) {
			Map<Serializable, Serializable> localCache = _localCache.get();

			Serializable localCacheKey = finderPath.encodeLocalCacheKey(args);

			localCache.put(localCacheKey, primaryKey);
		}

		PortalCache<Serializable, Serializable> portalCache = _getPortalCache(
			finderPath.getCacheName(), true);

		Serializable cacheKey = finderPath.encodeCacheKey(args);

		if (quiet) {
			PortalCacheHelperUtil.putWithoutReplicator(
				portalCache, cacheKey, primaryKey);
		}
		else {
			portalCache.put(cacheKey, primaryKey);
		}
	}

	@Override
	public void removeCache(String className) {
		_portalCaches.remove(className);

		String groupKey = _GROUP_KEY_PREFIX.concat(className);

		_multiVMPool.removeCache(groupKey);
	}

	@Override
	public void removeResult(FinderPath finderPath, Object[] args) {
		if (!PropsValues.VALUE_OBJECT_FINDER_CACHE_ENABLED ||
			!finderPath.isFinderCacheEnabled() ||
			!CacheRegistryUtil.isActive()) {

			return;
		}

		if (_LOCAL_CACHE_AVAILABLE) {
			Map<Serializable, Serializable> localCache = _localCache.get();

			Serializable localCacheKey = finderPath.encodeLocalCacheKey(args);

			localCache.remove(localCacheKey);
		}

		PortalCache<Serializable, Serializable> portalCache = _getPortalCache(
			finderPath.getCacheName(), true);

		Serializable cacheKey = finderPath.encodeCacheKey(args);

		portalCache.remove(cacheKey);
	}

	public void setMultiVMPool(MultiVMPool multiVMPool) {
		_multiVMPool = multiVMPool;

		PortalCacheManager<? extends Serializable, ? extends Serializable>
			portalCacheManager = _multiVMPool.getCacheManager();

		portalCacheManager.registerCacheManagerListener(this);
	}

	private PortalCache<Serializable, Serializable> _getPortalCache(
		String className, boolean createIfAbsent) {

		PortalCache<Serializable, Serializable> portalCache = _portalCaches.get(
			className);

		if ((portalCache == null) && createIfAbsent) {
			String groupKey = _GROUP_KEY_PREFIX.concat(className);

			portalCache =
				(PortalCache<Serializable, Serializable>)_multiVMPool.getCache(
					groupKey, PropsValues.VALUE_OBJECT_FINDER_BLOCKING_CACHE);

			PortalCache<Serializable, Serializable> previousPortalCache =
				_portalCaches.putIfAbsent(className, portalCache);

			if (previousPortalCache != null) {
				portalCache = previousPortalCache;
			}
		}

		return portalCache;
	}

	private Serializable _primaryKeyToResult(
		FinderPath finderPath,
		BasePersistenceImpl<? extends BaseModel<?>> basePersistenceImpl,
		Serializable primaryKey) {

		if (primaryKey instanceof List<?>) {
			List<Serializable> primaryKeys = (List<Serializable>)primaryKey;

			if (primaryKeys.isEmpty()) {
				return (Serializable)Collections.emptyList();
			}

			Set<Serializable> primaryKeysSet = new HashSet<>(primaryKeys);

			Map<Serializable, ? extends BaseModel<?>> map =
				basePersistenceImpl.fetchByPrimaryKeys(primaryKeysSet);

			if (map.size() < primaryKeysSet.size()) {
				return null;
			}

			List<Serializable> list = new ArrayList<>(primaryKeys.size());

			for (Serializable curPrimaryKey : primaryKeys) {
				list.add(map.get(curPrimaryKey));
			}

			return (Serializable)Collections.unmodifiableList(list);
		}
		else if (BaseModel.class.isAssignableFrom(
					finderPath.getResultClass())) {

			return EntityCacheUtil.loadResult(
				finderPath.isEntityCacheEnabled(), finderPath.getResultClass(),
				primaryKey, basePersistenceImpl);
		}

		return primaryKey;
	}

	private Serializable _resultToPrimaryKey(Serializable result) {
		if (result instanceof BaseModel<?>) {
			BaseModel<?> model = (BaseModel<?>)result;

			return model.getPrimaryKeyObj();
		}
		else if (result instanceof List<?>) {
			List<Serializable> list = (List<Serializable>)result;

			if (list.isEmpty()) {
				return (Serializable)Collections.emptyList();
			}

			ArrayList<Serializable> cachedList = new ArrayList<>(list.size());

			for (Serializable curResult : list) {
				Serializable primaryKey = _resultToPrimaryKey(curResult);

				cachedList.add(primaryKey);
			}

			return cachedList;
		}

		return result;
	}

	private static final String _GROUP_KEY_PREFIX = CACHE_NAME.concat(
		StringPool.PERIOD);

	private static final boolean _LOCAL_CACHE_AVAILABLE;

	private static final ThreadLocal<LRUMap> _localCache;

	static {
		if (PropsValues.VALUE_OBJECT_FINDER_THREAD_LOCAL_CACHE_MAX_SIZE > 0) {
			_LOCAL_CACHE_AVAILABLE = true;

			_localCache = new AutoResetThreadLocal<LRUMap>(
				FinderCacheImpl.class + "._localCache",
				new LRUMap(
					PropsValues.
						VALUE_OBJECT_FINDER_THREAD_LOCAL_CACHE_MAX_SIZE));
		}
		else {
			_LOCAL_CACHE_AVAILABLE = false;

			_localCache = null;
		}
	}

	private MultiVMPool _multiVMPool;
	private final ConcurrentMap<String, PortalCache<Serializable, Serializable>>
		_portalCaches =
			new ConcurrentHashMap
				<String, PortalCache<Serializable, Serializable>>();

}