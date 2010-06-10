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
 * <a href="IncreasableEntryTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class IncreasableEntryTest extends TestCase {

	public void testGettingKey() {
		IntegerIncreasableEntry entry = new IntegerIncreasableEntry("test", 0);
		assertEquals("test", entry.getKey());
		assertEquals("test", entry.getKey());
		assertEquals("test", entry.getKey());
	}

	public void testIncreaseAndGet() {
		IntegerIncreasableEntry entry = new IntegerIncreasableEntry("test", 0);

		// Simple increase
		assertTrue(entry.increase(1));

		// Simple get
		assertEquals(1, (int)entry.getValue());

		entry = new IntegerIncreasableEntry("test", 0);
		// Continue get
		assertEquals(0, (int)entry.getValue());
		assertEquals(0, (int)entry.getValue());
		assertEquals(0, (int)entry.getValue());

		entry = new IntegerIncreasableEntry("test", 0);
		// Continue increase
		assertTrue(entry.increase(1));
		assertTrue(entry.increase(2));
		assertTrue(entry.increase(3));
		// Check value
		assertEquals(6, (int)entry.getValue());

		// Increase after get
		entry = new IntegerIncreasableEntry("test", 0);
		assertEquals(0, (int)entry.getValue());
		assertFalse(entry.increase(1));
		assertEquals(0, (int)entry.getValue());
	}

	private static class IntegerIncreasableEntry
		extends IncreasableEntry<String, Integer> {

		public IntegerIncreasableEntry(String key, Integer value) {
			super(key, value);
		}

		public Integer doIncrease(Integer originalValue, Integer deltaValue) {
			return originalValue + deltaValue;
		}

	}

}