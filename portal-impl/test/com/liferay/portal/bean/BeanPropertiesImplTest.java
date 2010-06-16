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

package com.liferay.portal.bean;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.util.BaseTestCase;

/**
 * <a href="BeanPropertiesImplTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Igor Spasic
 */
public class BeanPropertiesImplTest extends BaseTestCase {

	public void testBoolean() {
		Foo foo = new Foo();

		assertFalse(foo.getBoolean());

		BeanPropertiesUtil.setProperty(foo, "boolean", Boolean.TRUE);

		assertTrue(foo.getBoolean());

		boolean value = BeanPropertiesUtil.getBoolean(foo, "boolean", false);

		assertTrue(value);

		value = BeanPropertiesUtil.getBoolean(foo, _NONEXISTENT, false);

		assertFalse(value);
	}

	public void testBooleanObject() {
		Foo foo = new Foo();

		assertNull(foo.getBooleanObject());

		BeanPropertiesUtil.setProperty(foo, "booleanObject", Boolean.TRUE);

		assertEquals(Boolean.TRUE, foo.getBooleanObject());

		boolean value = BeanPropertiesUtil.getBoolean(
			foo, "booleanObject", false);

		assertTrue(value);

		value = BeanPropertiesUtil.getBoolean(foo, _NONEXISTENT, false);

		assertFalse(value);
	}

	public void testDouble() {
		Foo foo = new Foo();

		assertEquals(0.0D, foo.getDouble(), 0.001);

		BeanPropertiesUtil.setProperty(foo, "double", Double.valueOf(17.3));

		assertEquals(17.3, foo.getDouble(), 0.0001);

		double value = BeanPropertiesUtil.getDouble(foo, "double", -1);

		assertEquals(17.3, value, 0.0001);

		value = BeanPropertiesUtil.getDouble(foo, _NONEXISTENT, -1.1);

		assertEquals(-1.1, value, 0.0001);
	}

	public void testDoubleObject() {
		Foo foo = new Foo();

		assertNull(foo.getDoubleObject());

		BeanPropertiesUtil.setProperty(
			foo, "doubleObject", Double.valueOf(17.3));

		assertEquals(17.3, foo.getDoubleObject().doubleValue(), 0.0001);

		double value = BeanPropertiesUtil.getDouble(foo, "doubleObject", -1);

		assertEquals(17.3, value, 0.0001);

		value = BeanPropertiesUtil.getDouble(foo, _NONEXISTENT, -1.1);

		assertEquals(-1.1, value, 0.0001);
	}

	public void testInt() {
		Foo foo = new Foo();

		assertEquals(0, foo.getInt());

		BeanPropertiesUtil.setProperty(foo, "int", Integer.valueOf(173));

		assertEquals(173, foo.getInt());

		int value = BeanPropertiesUtil.getInteger(foo, "int", -1);

		assertEquals(173, value);

		value = BeanPropertiesUtil.getInteger(foo, _NONEXISTENT, -1);

		assertEquals(-1, value);
	}

	public void testInteger() {
		Foo foo = new Foo();

		assertNull(foo.getInteger());

		BeanPropertiesUtil.setProperty(foo, "integer", Integer.valueOf(173));

		assertEquals(173, foo.getInteger().intValue());

		int value = BeanPropertiesUtil.getInteger(foo, "integer", -1);

		assertEquals(173, value);

		value = BeanPropertiesUtil.getInteger(foo, _NONEXISTENT, -1);

		assertEquals(-1, value);
	}

	public void testLong() {
		Foo foo = new Foo();

		assertEquals(0, foo.getLong());

		BeanPropertiesUtil.setProperty(foo, "long", Long.valueOf(173L));

		assertEquals(173L, foo.getLong());

		long value = BeanPropertiesUtil.getLong(foo, "long", -1);

		assertEquals(173, value);

		value = BeanPropertiesUtil.getLong(foo, _NONEXISTENT, -1);

		assertEquals(-1, value);
	}

	public void testLongObject() {
		Foo foo = new Foo();

		assertNull(foo.getLongObject());

		BeanPropertiesUtil.setProperty(foo, "longObject", Long.valueOf(173L));

		assertEquals(173L, foo.getLongObject().longValue());

		long value = BeanPropertiesUtil.getLong(foo, "longObject", -1);

		assertEquals(173L, value);

		value = BeanPropertiesUtil.getLong(foo, _NONEXISTENT, -1);

		assertEquals(-1, value);
	}

	public void testSetInnerProperty() {
		Bar bar = new Bar();

		BeanPropertiesUtil.setProperty(bar, "foo.int", Integer.valueOf(173));

		assertEquals(173, bar.getFoo().getInt());
	}

	public void testSetInvalidDestinationType() {
		Foo foo = new Foo();

		assertEquals(0, foo.getInt());
		assertNull(foo.getInteger());

		BeanPropertiesUtil.setProperty(foo, "int", "123");
		BeanPropertiesUtil.setProperty(foo, "integer", "123");

		assertEquals(0, foo.getInt());
		assertNull(foo.getInteger());
	}

	public void testSetNonExistingProperty() {
		Foo foo = new Foo();

		BeanPropertiesUtil.setProperty(foo, _NONEXISTENT, new Object());
	}

	public void testString() {
		Foo foo = new Foo();

		assertNull(foo.getString());

		BeanPropertiesUtil.setProperty(foo, "string", "test");

		assertEquals("test", foo.getString());

		String value = BeanPropertiesUtil.getString(foo, "string");

		assertEquals("test", value);

		value = BeanPropertiesUtil.getString(foo, _NONEXISTENT, "none");

		assertEquals("none", value);
	}

	private static final String _NONEXISTENT = "nonexistent";

}