/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.MultiValueMap;

import java.util.Set;

/**
 * <a href="MultiValueMapTestCase.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 */
public abstract class MultiValueMapTestCase extends BaseTestCase {

	public void testDelete() {
		String name = multiValueMap.getClass().getSimpleName();

		multiValueMap.put(5, "hello");
		multiValueMap.put(5, "world");
		multiValueMap.put(10, "world");
		multiValueMap.put(10, "wide");
		multiValueMap.put(10, "web");
		multiValueMap.put(5, "hello");

		assertEquals(name, 5, multiValueMap.size());

		multiValueMap.remove(10);

		assertEquals(name, 2, multiValueMap.size());

		Set<String> values = multiValueMap.getAll(5);

		assertNotNull(name, values);

		assertTrue(name, values.contains("hello"));
		assertTrue(name, values.contains("world"));
	}

	public void testMultipleInsert() {
		String name = multiValueMap.getClass().getSimpleName();

		multiValueMap.put(5, "hello");
		multiValueMap.put(5, "world");
		multiValueMap.put(10, "world");
		multiValueMap.put(10, "wide");
		multiValueMap.put(10, "web");
		multiValueMap.put(5, "hello");

		assertEquals(name, 5, multiValueMap.size());

		Set<String> values = multiValueMap.getAll(5);

		assertNotNull(name, values);

		assertTrue(name, values.contains("hello"));
		assertTrue(name, values.contains("world"));
	}

	protected MultiValueMap<Integer, String> multiValueMap;

}