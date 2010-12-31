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

package com.liferay.portal.kernel.cache;

import com.liferay.portal.kernel.concurrent.CompeteLatch;

import java.io.Serializable;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Shuyang Zhou
 */
public class BlockingPortalCache implements PortalCache {

	public BlockingPortalCache(PortalCache portalCache) {
		_portalCache = portalCache;
	}

	public void destroy() {
	}

	public Collection<Object> get(Collection<String> keys) {
		return _portalCache.get(keys);
	}

	public Object get(String key) {
		Object obj = _portalCache.get(key);

		if (obj != null) {
			return obj;
		}

		CompeteLatch lastCompeteLatch = _competeLatch.get();

		if (lastCompeteLatch != null) {
			lastCompeteLatch.done();

			_competeLatch.set(null);
		}

		CompeteLatch currentCompeteLatch = _competeLatchMap.get(key);

		if (currentCompeteLatch == null) {
			CompeteLatch newCompeteLatch = new CompeteLatch();

			currentCompeteLatch = _competeLatchMap.putIfAbsent(
				key, newCompeteLatch);

			if (currentCompeteLatch == null) {
				currentCompeteLatch = newCompeteLatch;
			}
		}

		_competeLatch.set(currentCompeteLatch);

		if (!currentCompeteLatch.compete()) {
			try {
				currentCompeteLatch.await();
			}
			catch (InterruptedException ie) {
			}

			_competeLatch.set(null);

			obj = _portalCache.get(key);
		}

		return obj;
	}

	public void put(String key, Object obj) {
		if (key == null) {
			throw new IllegalArgumentException("Key is null");
		}

		if (obj == null) {
			throw new IllegalArgumentException("Object is null");
		}

		_portalCache.put(key, obj);

		CompeteLatch competeLatch = _competeLatch.get();

		if (competeLatch != null) {
			competeLatch.done();

			_competeLatch.set(null);
		}

		_competeLatchMap.remove(key);
	}

	public void put(String key, Object obj, int timeToLive) {
		if (key == null) {
			throw new IllegalArgumentException("Key is null");
		}

		if (obj == null) {
			throw new IllegalArgumentException("Object is null");
		}

		_portalCache.put(key, obj, timeToLive);

		CompeteLatch competeLatch = _competeLatch.get();

		if (competeLatch != null) {
			competeLatch.done();

			_competeLatch.set(null);
		}

		_competeLatchMap.remove(key);
	}

	public void put(String key, Serializable obj) {
		if (key == null) {
			throw new IllegalArgumentException("Key is null");
		}

		if (obj == null) {
			throw new IllegalArgumentException("Object is null");
		}

		_portalCache.put(key, obj);

		CompeteLatch competeLatch = _competeLatch.get();

		if (competeLatch != null) {
			competeLatch.done();

			_competeLatch.set(null);
		}

		_competeLatchMap.remove(key);
	}

	public void put(String key, Serializable obj, int timeToLive) {
		if (key == null) {
			throw new IllegalArgumentException("Key is null");
		}

		if (obj == null) {
			throw new IllegalArgumentException("Object is null");
		}

		_portalCache.put(key, obj, timeToLive);

		CompeteLatch competeLatch = _competeLatch.get();

		if (competeLatch != null) {
			competeLatch.done();

			_competeLatch.set(null);
		}

		_competeLatchMap.remove(key);
	}

	public void remove(String key) {
		_portalCache.remove(key);

		CompeteLatch competeLatch = _competeLatchMap.remove(key);

		if (competeLatch != null) {
			competeLatch.done();
		}
	}

	public void removeAll() {
		_portalCache.removeAll();
		_competeLatchMap.clear();
	}

	public void setDebug(boolean debug) {
		_portalCache.setDebug(debug);
	}

	private static ThreadLocal<CompeteLatch> _competeLatch =
		new ThreadLocal<CompeteLatch>();
	private final ConcurrentMap<String, CompeteLatch> _competeLatchMap =
		new ConcurrentHashMap<String, CompeteLatch>();
	private final PortalCache _portalCache;

}