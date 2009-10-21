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

package com.liferay.portal.cache;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.concurrent.CompeteLatch;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * <a href="EhcachePortalCache.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class BlockingPortalCache implements PortalCache {

	public BlockingPortalCache(PortalCache portalCache) {
		_portalCache = portalCache;
	}

	public Object get(String key) {
		Object obj = _portalCache.get(key);
		if (obj == null) {
			//miss cache need to load it
			//check last latch
			CompeteLatch lastCompeteLatch = _lastCompeteLatch.get();
			if (lastCompeteLatch != null) {
				//you already have locked up a latch, to prevent deadlock you
				//have to give up last latch first
				lastCompeteLatch.done();
				_lastCompeteLatch.set(null);
			}
			//get latch for this time
			CompeteLatch currentCompeteLatch = _competeLatchMap.get(key);
			if (currentCompeteLatch == null) {
				//it seems like no other thread arrive here before you
				//prepare a new latch
				CompeteLatch newCompeteLatch = new CompeteLatch();
				//atomicly put new latch
				currentCompeteLatch = _competeLatchMap.putIfAbsent(
					key, newCompeteLatch);
				if (currentCompeteLatch == null) {
					//success to put, you have the guarantee to be the first
					//thread arrived here.
					currentCompeteLatch = newCompeteLatch;
				}
			}
			//record current latch
			_lastCompeteLatch.set(currentCompeteLatch);
			//compete to load the cache
			if (!currentCompeteLatch.compete()) {
				//loser thread wait for result
				currentCompeteLatch.await();
				//you have passed the latch, so clean up the record
				_lastCompeteLatch.set(null);
				//This may still return null, since the winner thread maybe give
				//up the latch for a new latch, or even there is window time
				//here, right after the loser pass the latch, some other thread
				//may just happen to clear the cache, so that means the loser
				//thread may have to load the cache again. We are not trying to
				//close the window, since it won't cause any logic or deadlock
				//trouble, just a waste of performance. 100% safe blocking
				//cache can screw up concurrency, leaving this window here is
				//just a compromise
				obj = _portalCache.get(key);
			}
		}
		return obj;
	}

	public void put(String key, Object obj) {
		if (obj == null) {
			throw new IllegalArgumentException("obj is null");
		}
		_portalCache.put(key, obj);
		CompeteLatch lastCompeteLatch = _lastCompeteLatch.get();
		if (lastCompeteLatch != null) {
			lastCompeteLatch.done();
			_lastCompeteLatch.set(null);
		}
		_competeLatchMap.remove(key);
	}

	public void put(String key, Object obj, int timeToLive) {
		if (obj == null) {
			throw new IllegalArgumentException("obj is null");
		}
		_portalCache.put(key, obj, timeToLive);
		CompeteLatch lastCompeteLatch = _lastCompeteLatch.get();
		if (lastCompeteLatch != null) {
			lastCompeteLatch.done();
			_lastCompeteLatch.set(null);
		}
		_competeLatchMap.remove(key);
	}

	public void put(String key, Serializable obj) {
		if (obj == null) {
			throw new IllegalArgumentException("obj is null");
		}
		_portalCache.put(key, obj);
		CompeteLatch lastCompeteLatch = _lastCompeteLatch.get();
		if (lastCompeteLatch != null) {
			lastCompeteLatch.done();
			_lastCompeteLatch.set(null);
		}
		_competeLatchMap.remove(key);
	}

	public void put(String key, Serializable obj, int timeToLive) {
		if (obj == null) {
			throw new IllegalArgumentException("obj is null");
		}
		_portalCache.put(key, obj, timeToLive);
		CompeteLatch lastCompeteLatch = _lastCompeteLatch.get();
		if (lastCompeteLatch != null) {
			lastCompeteLatch.done();
			_lastCompeteLatch.set(null);
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

	private static ThreadLocal<CompeteLatch> _lastCompeteLatch =
		new ThreadLocal<CompeteLatch>();
	private final PortalCache _portalCache;
	private final ConcurrentMap<String, CompeteLatch> _competeLatchMap =
		new ConcurrentHashMap<String, CompeteLatch>();

}