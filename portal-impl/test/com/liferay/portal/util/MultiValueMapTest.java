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

package com.liferay.portal.util;

import java.util.Set;

/**
 * <a href="MultiValueMapTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class MultiValueMapTest extends BaseTestCase {

	public void testMultipleInsert() {
		for (MultiValueMap<Integer, String> mvm : _maps) {
			testMultipleInsert(mvm, mvm.getClass().getSimpleName());
		}
	}

	public void testDelete() {
		for (MultiValueMap<Integer, String> mvm : _maps) {
			testDelete(mvm, mvm.getClass().getSimpleName());
		}
	}

	protected void setUp() throws Exception {
		_maps = new MultiValueMap[] {
			MultiValueMap.getInstance(MultiValueMap.MEMORY),
			MultiValueMap.getInstance(MultiValueMap.FILE)
		};
	}

	protected void tearDown() throws Exception {
		_maps = null;
	}

	protected void testMultipleInsert(
		MultiValueMap<Integer, String> mvm, String name) {

		mvm.put(5, "hello");
		mvm.put(5, "world");
		mvm.put(10, "world");
		mvm.put(10, "wide");
		mvm.put(10, "web");
		mvm.put(5, "hello");

		assertEquals(name, 5, mvm.size());

		Set<String> values = mvm.getAll(5);

		assertNotNull(name, values);

		assertTrue(name, values.contains("hello"));
		assertTrue(name, values.contains("world"));
	}

	protected void testDelete(
		MultiValueMap<Integer, String> mvm, String name) {

		mvm.put(5, "hello");
		mvm.put(5, "world");
		mvm.put(10, "world");
		mvm.put(10, "wide");
		mvm.put(10, "web");
		mvm.put(5, "hello");

		assertEquals(name, 5, mvm.size());

		mvm.remove(10);

		assertEquals(name, 2, mvm.size());

		Set<String> values = mvm.getAll(5);

		assertNotNull(name, values);

		assertTrue(name, values.contains("hello"));
		assertTrue(name, values.contains("world"));
	}

	private MultiValueMap[] _maps;

}