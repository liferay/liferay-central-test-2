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

package com.liferay.portal.kernel.cache;

import com.liferay.portal.kernel.concurrent.CompeteLatch;

import java.io.Serializable;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * <a href="BlockingPortalCache.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class BlockingPortalCache implements PortalCache {

	public BlockingPortalCache(PortalCache portalCache) {
		_portalCache = portalCache;
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
			currentCompeteLatch.await();

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
		_competeLatchMap.remove(key);
	}

	public void removeAll() {
		_portalCache.removeAll();
		_competeLatchMap.clear();
	}

	private static ThreadLocal<CompeteLatch> _competeLatch =
		new ThreadLocal<CompeteLatch>();
	private final ConcurrentMap<String, CompeteLatch> _competeLatchMap =
		new ConcurrentHashMap<String, CompeteLatch>();
	private final PortalCache _portalCache;

}