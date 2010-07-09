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

package com.liferay.portal.kernel.concurrent;

import com.liferay.portal.kernel.test.TestCase;

/**
 * @author Shuyang Zhou
 */
public class ConcurrentLRUCacheTest extends TestCase {

	public void testConstruct() {
		ConcurrentLRUCache<String, String> concurrentLRUCache =
			new ConcurrentLRUCache<String, String>(10);

		assertEquals(0, concurrentLRUCache.evictCount());
		assertEquals(0, concurrentLRUCache.hitCount());
		assertEquals(10, concurrentLRUCache.maxSize());
		assertEquals(0, concurrentLRUCache.missCount());
		assertEquals(0, concurrentLRUCache.putCount());
		assertEquals(0, concurrentLRUCache.size());

		assertNull(concurrentLRUCache.get("key"));
	}

	public void testPut() {
		ConcurrentLRUCache<String, String> concurrentLRUCache =
			new ConcurrentLRUCache<String, String>(2);

		try{
			concurrentLRUCache.put(null, "value");

			fail();
		}
		catch (NullPointerException npe) {
		}

		concurrentLRUCache.put("key1", "value1");

		assertEquals(0, concurrentLRUCache.evictCount());
		assertEquals(0, concurrentLRUCache.hitCount());
		assertEquals(0, concurrentLRUCache.missCount());
		assertEquals(1, concurrentLRUCache.putCount());
		assertEquals(1, concurrentLRUCache.size());

		concurrentLRUCache.put("key2", "value2");

		assertEquals(0, concurrentLRUCache.evictCount());
		assertEquals(0, concurrentLRUCache.hitCount());
		assertEquals(0, concurrentLRUCache.missCount());
		assertEquals(2, concurrentLRUCache.putCount());
		assertEquals(2, concurrentLRUCache.size());

		concurrentLRUCache.put("key1", "value1-2");

		assertEquals(0, concurrentLRUCache.evictCount());
		assertEquals(0, concurrentLRUCache.hitCount());
		assertEquals(0, concurrentLRUCache.missCount());
		assertEquals(3, concurrentLRUCache.putCount());
		assertEquals(2, concurrentLRUCache.size());

		concurrentLRUCache.put("key2", "value2-2");

		assertEquals(0, concurrentLRUCache.evictCount());
		assertEquals(0, concurrentLRUCache.hitCount());
		assertEquals(0, concurrentLRUCache.missCount());
		assertEquals(4, concurrentLRUCache.putCount());
		assertEquals(2, concurrentLRUCache.size());

		concurrentLRUCache.put("key3", "value3");

		assertEquals(1, concurrentLRUCache.evictCount());
		assertEquals(0, concurrentLRUCache.hitCount());
		assertEquals(0, concurrentLRUCache.missCount());
		assertEquals(5, concurrentLRUCache.putCount());
		assertEquals(2, concurrentLRUCache.size());
		assertNull(concurrentLRUCache.get("key1"));
		assertEquals("value2-2", concurrentLRUCache.get("key2"));
		assertEquals("value3", concurrentLRUCache.get("key3"));

		concurrentLRUCache.put("key4", "value4");

		assertEquals(2, concurrentLRUCache.evictCount());
		assertEquals(2, concurrentLRUCache.hitCount());
		assertEquals(1, concurrentLRUCache.missCount());
		assertEquals(6, concurrentLRUCache.putCount());
		assertEquals(2, concurrentLRUCache.size());
		assertNull(concurrentLRUCache.get("key1"));
		assertNull(concurrentLRUCache.get("key2"));
		assertEquals("value3", concurrentLRUCache.get("key3"));
		assertEquals("value4", concurrentLRUCache.get("key4"));
	}

	public void testGet() {
		ConcurrentLRUCache<String, String> concurrentLRUCache =
			new ConcurrentLRUCache<String, String>(2);

		assertNull(concurrentLRUCache.get(null));
		assertEquals(0, concurrentLRUCache.evictCount());
		assertEquals(0, concurrentLRUCache.hitCount());
		assertEquals(1, concurrentLRUCache.missCount());
		assertEquals(0, concurrentLRUCache.putCount());
		assertEquals(0, concurrentLRUCache.size());

		assertNull(concurrentLRUCache.get("key"));
		assertEquals(0, concurrentLRUCache.evictCount());
		assertEquals(0, concurrentLRUCache.hitCount());
		assertEquals(2, concurrentLRUCache.missCount());
		assertEquals(0, concurrentLRUCache.putCount());
		assertEquals(0, concurrentLRUCache.size());

		concurrentLRUCache.put("key1", "value1");
		concurrentLRUCache.put("key2", "value2");

		assertEquals(0, concurrentLRUCache.evictCount());
		assertEquals(0, concurrentLRUCache.hitCount());
		assertEquals(2, concurrentLRUCache.missCount());
		assertEquals(2, concurrentLRUCache.putCount());
		assertEquals(2, concurrentLRUCache.size());

		assertEquals("value1", concurrentLRUCache.get("key1"));
		assertEquals(0, concurrentLRUCache.evictCount());
		assertEquals(1, concurrentLRUCache.hitCount());
		assertEquals(2, concurrentLRUCache.missCount());
		assertEquals(2, concurrentLRUCache.putCount());
		assertEquals(2, concurrentLRUCache.size());

		concurrentLRUCache.put("key3", "value3");

		assertEquals(1, concurrentLRUCache.evictCount());
		assertEquals(1, concurrentLRUCache.hitCount());
		assertEquals(2, concurrentLRUCache.missCount());
		assertEquals(3, concurrentLRUCache.putCount());
		assertEquals(2, concurrentLRUCache.size());

		assertEquals("value1", concurrentLRUCache.get("key1"));
		assertNull(concurrentLRUCache.get("key2"));
		assertEquals("value3", concurrentLRUCache.get("key3"));
	}

}