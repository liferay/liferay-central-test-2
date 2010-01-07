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