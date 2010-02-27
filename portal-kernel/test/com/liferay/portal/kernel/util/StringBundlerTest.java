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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.test.TestCase;

/**
 * <a href="StringBundlerTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class StringBundlerTest extends TestCase {

	public void testConstructor() {

		// Default constructor

		StringBundler sb = new StringBundler();

		assertEquals(0, sb.index());
		assertEquals(16, sb.capacity());

		// Constructor with capacity

		sb = new StringBundler(32);

		assertEquals(0, sb.index());
		assertEquals(32, sb.capacity());

		// Constructor with a String

		sb = new StringBundler("test");

		assertEquals(1, sb.index());
		assertEquals("test", sb.stringAt(0));
		assertEquals(16, sb.capacity());
	}

	public void testAppend() {

		// Append null String

		StringBundler sb = new StringBundler();

		sb.append((String)null);

		assertEquals(1, sb.index());
		assertEquals(StringPool.NULL, sb.stringAt(0));

		// Append null StringBundler

		sb = new StringBundler();

		sb.append((StringBundler)null);

		assertEquals(0, sb.index());

		// Append without growing

		sb = new StringBundler();

		sb.append("test1");

		assertEquals(1, sb.index());
		assertEquals(16, sb.capacity());
		assertEquals("test1", sb.stringAt(0));

		sb.append("test2");

		assertEquals(2, sb.index());
		assertEquals(16, sb.capacity());
		assertEquals("test1", sb.stringAt(0));
		assertEquals("test2", sb.stringAt(1));

		sb.append("test3");

		assertEquals(3, sb.index());
		assertEquals(16, sb.capacity());
		assertEquals("test1", sb.stringAt(0));
		assertEquals("test2", sb.stringAt(1));
		assertEquals("test3", sb.stringAt(2));

		// Append String with growth

		sb = new StringBundler(2);

		sb.append("test1");

		assertEquals(1, sb.index());
		assertEquals(2, sb.capacity());
		assertEquals("test1", sb.stringAt(0));

		sb.append("test2");

		assertEquals(2, sb.index());
		assertEquals(2, sb.capacity());
		assertEquals("test1", sb.stringAt(0));
		assertEquals("test2", sb.stringAt(1));

		sb.append("test3");

		assertEquals(3, sb.index());
		assertEquals(4, sb.capacity());

		assertEquals("test1", sb.stringAt(0));
		assertEquals("test2", sb.stringAt(1));
		assertEquals("test3", sb.stringAt(2));

		// Append StringBundler with growth

		sb = new StringBundler(2);

		StringBundler testSB = new StringBundler();

		testSB.append("test1");
		testSB.append("test2");
		testSB.append("test3");

		sb.append(testSB);

		assertEquals(3, sb.index());
		assertEquals(10, sb.capacity());
		assertEquals("test3", sb.stringAt(2));

		// Append StringBundler without growth

		sb = new StringBundler();

		testSB = new StringBundler();

		testSB.append("test1");
		testSB.append("test2");
		testSB.append("test3");

		sb.append(testSB);

		assertEquals(3, sb.index());
		assertEquals(16, sb.capacity());
		assertEquals("test3", sb.stringAt(2));
	}

	public void testToString() {

		// Empty toString

		StringBundler sb = new StringBundler();

		assertEquals(StringPool.BLANK, sb.toString());

		// String.concat()

		sb.append("test1");
		sb.append("test2");
		sb.append("test3");

		assertEquals("test1test2test3", sb.toString());

		// StringBuilder

		sb.append("test4");

		assertEquals("test1test2test3test4", sb.toString());
	}

	public void testSetIndex() {

		// Negative index

		StringBundler sb = new StringBundler();

		try {
			sb.setIndex(-1);

			fail();
		}
		catch (ArrayIndexOutOfBoundsException aioobe) {
		}

		// New index equals current index

		sb = new StringBundler(4);

		sb.append("test1");
		sb.append("test2");

		assertEquals(2, sb.index());
		assertEquals("test1", sb.stringAt(0));
		assertEquals("test2", sb.stringAt(1));

		sb.setIndex(2);

		assertEquals(2, sb.index());
		assertEquals("test1", sb.stringAt(0));
		assertEquals("test2", sb.stringAt(1));

		// New index is larger than the current index but smaller than the array
		// size

		assertEquals(4, sb.capacity());

		sb.setIndex(4);

		assertEquals(4, sb.capacity());
		assertEquals(4, sb.index());
		assertEquals("test1", sb.stringAt(0));
		assertEquals("test2", sb.stringAt(1));
		assertEquals(StringPool.BLANK, sb.stringAt(2));
		assertEquals(StringPool.BLANK, sb.stringAt(3));

		// New index is larger than the current index and array size

		sb.setIndex(6);

		assertEquals(6, sb.capacity());
		assertEquals(6, sb.index());
		assertEquals("test1", sb.stringAt(0));
		assertEquals("test2", sb.stringAt(1));
		assertEquals(StringPool.BLANK, sb.stringAt(2));
		assertEquals(StringPool.BLANK, sb.stringAt(3));
		assertEquals(StringPool.BLANK, sb.stringAt(4));
		assertEquals(StringPool.BLANK, sb.stringAt(5));

		// New index is smaller than current index

		sb.setIndex(1);

		assertEquals(6, sb.capacity());
		assertEquals(1, sb.index());
		assertEquals("test1", sb.stringAt(0));

		try {
			assertEquals(null, sb.stringAt(1));

			fail();
		}
		catch (ArrayIndexOutOfBoundsException aioobe) {
		}
	}

}