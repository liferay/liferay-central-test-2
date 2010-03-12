/*
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
 *
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.kernel.util.collections;

import junit.framework.TestCase;

/**
 * <a href="LongPrimitiveListTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class LongPrimitiveListTest extends TestCase {

	public void testAdd() {

		long[] expectedValues = new long[] {10l, 11l, 12l};

		LongPrimitiveList longList = new LongPrimitiveList();
		for (int i = 0; i < expectedValues.length; i++) {
			longList.add(expectedValues[i]);
		}
		assertEquals(expectedValues.length, longList.size());

		long[] results = longList.getArray();
		assertEquals(expectedValues.length, results.length);
		for (int i = 0; i < results.length; i++) {
			assertEquals(expectedValues[i], results[i]);
		}
	}

	public void testAddAll() {

		long[] expectedValues = new long[] {10l, 11l, 12l};

		LongPrimitiveList longList = new LongPrimitiveList();
		longList.addAll(expectedValues);
		assertEquals(expectedValues.length, longList.size());

		long[] results = longList.getArray();
		assertEquals(expectedValues.length, results.length);
		for (int i = 0; i < results.length; i++) {
			assertEquals(expectedValues[i], results[i]);
		}
	}
}
