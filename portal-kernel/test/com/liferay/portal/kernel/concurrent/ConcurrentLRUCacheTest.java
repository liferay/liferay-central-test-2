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

package com.liferay.portal.kernel.concurrent;

import com.liferay.portal.kernel.test.TestCase;

/**
 * <a href="ConcurrentLRUCacheTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class ConcurrentLRUCacheTest extends TestCase {

	public void testConstruct() {
		// Illegal arg
		try {
			new ConcurrentLRUCache<String, String>(0);
			fail();
		}
		catch(IllegalArgumentException iae) {
		}

		// Normal create
		ConcurrentLRUCache<String, String> concurrentLRUCache =
			new ConcurrentLRUCache<String, String>(10);
		assertEquals(10, concurrentLRUCache.maxSize());
		assertEquals(0, concurrentLRUCache.evictCount());
		assertEquals(0, concurrentLRUCache.hitCount());
		assertEquals(0, concurrentLRUCache.missCount());
		assertEquals(0, concurrentLRUCache.putCount());
		assertEquals(0, concurrentLRUCache.size());
		assertNull(concurrentLRUCache.get("testKey"));
	}

	public void testPut() {
		ConcurrentLRUCache<String, String> concurrentLRUCache =
			new ConcurrentLRUCache<String, String>(2);
		// Null key put
		try{
			concurrentLRUCache.put(null, "testValue");
			fail();
		}catch(NullPointerException ne) {

		}
		// Normal put
		concurrentLRUCache.put("testKey1", "testValue1");
		assertEquals(0, concurrentLRUCache.evictCount());
		assertEquals(0, concurrentLRUCache.hitCount());
		assertEquals(0, concurrentLRUCache.missCount());
		assertEquals(1, concurrentLRUCache.putCount());
		assertEquals(1, concurrentLRUCache.size());

		concurrentLRUCache.put("testKey2", "testValue2");
		assertEquals(0, concurrentLRUCache.evictCount());
		assertEquals(0, concurrentLRUCache.hitCount());
		assertEquals(0, concurrentLRUCache.missCount());
		assertEquals(2, concurrentLRUCache.putCount());
		assertEquals(2, concurrentLRUCache.size());

		// Overwrite put
		concurrentLRUCache.put("testKey1", "testValue1-2");
		assertEquals(0, concurrentLRUCache.evictCount());
		assertEquals(0, concurrentLRUCache.hitCount());
		assertEquals(0, concurrentLRUCache.missCount());
		assertEquals(3, concurrentLRUCache.putCount());
		assertEquals(2, concurrentLRUCache.size());

		concurrentLRUCache.put("testKey2", "testValue2-2");
		assertEquals(0, concurrentLRUCache.evictCount());
		assertEquals(0, concurrentLRUCache.hitCount());
		assertEquals(0, concurrentLRUCache.missCount());
		assertEquals(4, concurrentLRUCache.putCount());
		assertEquals(2, concurrentLRUCache.size());

		// Evict put
		concurrentLRUCache.put("testKey3", "testValue3");
		assertEquals(1, concurrentLRUCache.evictCount());
		assertEquals(0, concurrentLRUCache.hitCount());
		assertEquals(0, concurrentLRUCache.missCount());
		assertEquals(5, concurrentLRUCache.putCount());
		assertEquals(2, concurrentLRUCache.size());
		assertNull(concurrentLRUCache.get("testKey1"));
		assertEquals("testValue2-2", concurrentLRUCache.get("testKey2"));
		assertEquals("testValue3", concurrentLRUCache.get("testKey3"));

		concurrentLRUCache.put("testKey4", "testValue4");
		assertEquals(2, concurrentLRUCache.evictCount());
		assertEquals(2, concurrentLRUCache.hitCount());
		assertEquals(1, concurrentLRUCache.missCount());
		assertEquals(6, concurrentLRUCache.putCount());
		assertEquals(2, concurrentLRUCache.size());
		assertNull(concurrentLRUCache.get("testKey1"));
		assertNull(concurrentLRUCache.get("testKey2"));
		assertEquals("testValue3", concurrentLRUCache.get("testKey3"));
		assertEquals("testValue4", concurrentLRUCache.get("testKey4"));
	}

	public void testGet() {
		ConcurrentLRUCache<String, String> concurrentLRUCache =
			new ConcurrentLRUCache<String, String>(2);
		// Null get
		assertNull(concurrentLRUCache.get(null));
		assertEquals(0, concurrentLRUCache.evictCount());
		assertEquals(0, concurrentLRUCache.hitCount());
		assertEquals(1, concurrentLRUCache.missCount());
		assertEquals(0, concurrentLRUCache.putCount());
		assertEquals(0, concurrentLRUCache.size());
		// Empty get
		assertNull(concurrentLRUCache.get("testKey"));
		assertEquals(0, concurrentLRUCache.evictCount());
		assertEquals(0, concurrentLRUCache.hitCount());
		assertEquals(2, concurrentLRUCache.missCount());
		assertEquals(0, concurrentLRUCache.putCount());
		assertEquals(0, concurrentLRUCache.size());

		// Prepare data
		concurrentLRUCache.put("testKey1", "testValue1");
		concurrentLRUCache.put("testKey2", "testValue2");
		assertEquals(0, concurrentLRUCache.evictCount());
		assertEquals(0, concurrentLRUCache.hitCount());
		assertEquals(2, concurrentLRUCache.missCount());
		assertEquals(2, concurrentLRUCache.putCount());
		assertEquals(2, concurrentLRUCache.size());

		// Hit get
		assertEquals("testValue1", concurrentLRUCache.get("testKey1"));
		assertEquals(0, concurrentLRUCache.evictCount());
		assertEquals(1, concurrentLRUCache.hitCount());
		assertEquals(2, concurrentLRUCache.missCount());
		assertEquals(2, concurrentLRUCache.putCount());
		assertEquals(2, concurrentLRUCache.size());

		// Impact to evict
		concurrentLRUCache.put("testKey3", "testValue3");
		assertEquals(1, concurrentLRUCache.evictCount());
		assertEquals(1, concurrentLRUCache.hitCount());
		assertEquals(2, concurrentLRUCache.missCount());
		assertEquals(3, concurrentLRUCache.putCount());
		assertEquals(2, concurrentLRUCache.size());

		assertEquals("testValue1", concurrentLRUCache.get("testKey1"));
		assertNull(concurrentLRUCache.get("testKey2"));
		assertEquals("testValue3", concurrentLRUCache.get("testKey3"));
	}

}