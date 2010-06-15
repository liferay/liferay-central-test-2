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

/**
 * <a href="BeanPropertiesImplTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Igor Spasic
 */
public class BeanPropertiesImplTest extends BaseBeanTestCase {

	public static final String NONEXISTING = "nonexisting";

	public void testSetNonExistingProperty() {
		FooBean fooBean = new FooBean();

		try {
			bp.setProperty(fooBean, NONEXISTING, new Object());
		}
		catch (Exception ignore) {
			fail();
		}
	}

	public void testSetInvalidDestinationType() {
		FooBean fooBean = new FooBean();

		assertEquals(0, fooBean.getInt());
		assertNull(fooBean.getInteger());

		try {
			bp.setProperty(fooBean, "int", "123");
		}
		catch (IllegalArgumentException ignore) {
			fail();
		}
		try {
			bp.setProperty(fooBean, "integer", "123");
		}
		catch (IllegalArgumentException ignore) {
			fail();
		}

		assertEquals(0, fooBean.getInt());
		assertNull(fooBean.getInteger());
	}

	public void testBigBoolean() {
		FooBean fooBean = new FooBean();
		final String propertyName = "bigBoolean";

		assertNull(fooBean.getBigBoolean());

		try {
			bp.setProperty(fooBean, propertyName, Boolean.TRUE);
		}
		catch (Exception ignore) {
			fail();
		}
		assertEquals(Boolean.TRUE, fooBean.getBigBoolean());

		boolean booleanValue = bp.getBoolean(fooBean, propertyName, false);
		assertTrue(booleanValue);

		try {
			booleanValue = bp.getBoolean(fooBean, NONEXISTING, false);
			assertFalse(booleanValue);
		}
		catch (Exception ignore) {
			fail();
		}
	}

	public void testBoolean() {
		FooBean fooBean = new FooBean();
		final String propertyName = "boolean";

		assertFalse(fooBean.getBoolean());

		try {
			bp.setProperty(fooBean, propertyName, Boolean.TRUE);
		}
		catch (Exception ignore) {
			fail();
		}
		assertTrue(fooBean.getBoolean());

		boolean booleanValue = bp.getBoolean(fooBean, propertyName, false);
		assertTrue(booleanValue);

		try {
			booleanValue = bp.getBoolean(fooBean, NONEXISTING, false);
			assertFalse(booleanValue);
		}
		catch (Exception ignore) {
			fail();
		}
	}

	public void testInteger() {
		FooBean fooBean = new FooBean();
		final String propertyName = "integer";

		assertNull(fooBean.getInteger());

		try {
			bp.setProperty(fooBean, propertyName, Integer.valueOf(173));
		}
		catch (Exception ignore) {
			fail();
		}
		assertEquals(173, fooBean.getInteger().intValue());

		int integerValue = bp.getInteger(fooBean, propertyName, -1);
		assertEquals(173, integerValue);

		try {
			integerValue = bp.getInteger(fooBean, NONEXISTING, -1);
			assertEquals(-1, integerValue);
		}
		catch (Exception ignore) {
			fail();
		}
	}

	public void testInt() {
		FooBean fooBean = new FooBean();
		final String propertyName = "int";

		assertEquals(0, fooBean.getInt());

		try {
			bp.setProperty(fooBean, propertyName, Integer.valueOf(173));
		}
		catch (Exception ignore) {
			fail();
		}
		assertEquals(173, fooBean.getInt());

		int intValue = bp.getInteger(fooBean, propertyName, -1);
		assertEquals(173, intValue);

		try {
			intValue = bp.getInteger(fooBean, NONEXISTING, -1);
			assertEquals(-1, intValue);
		}
		catch (Exception ignore) {
			fail();
		}
	}

	public void testBigLong() {
		FooBean fooBean = new FooBean();
		final String propertyName = "bigLong";

		assertNull(fooBean.getBigLong());

		try {
			bp.setProperty(fooBean, propertyName, Long.valueOf(173L));
		}
		catch (Exception ignore) {
			fail();
		}
		assertEquals(173L, fooBean.getBigLong().longValue());

		long longValue = bp.getLong(fooBean, propertyName, -1);
		assertEquals(173L, longValue);

		try {
			longValue = bp.getLong(fooBean, NONEXISTING, -1);
			assertEquals(-1, longValue);
		}
		catch (Exception ignore) {
			fail();
		}
	}

	public void testLong() {
		FooBean fooBean = new FooBean();
		final String propertyName = "long";

		assertEquals(0, fooBean.getLong());

		try {
			bp.setProperty(fooBean, propertyName, Long.valueOf(173L));
		}
		catch (Exception ignore) {
			fail();
		}
		assertEquals(173L, fooBean.getLong());

		long longValue = bp.getLong(fooBean, propertyName, -1);
		assertEquals(173, longValue);

		try {
			longValue = bp.getLong(fooBean, NONEXISTING, -1);
			assertEquals(-1, longValue);
		}
		catch (Exception ignore) {
			fail();
		}
	}

	public void testBigDouble() {
		FooBean fooBean = new FooBean();
		final String propertyName = "bigDouble";

		assertNull(fooBean.getBigDouble());

		try {
			bp.setProperty(fooBean, propertyName, Double.valueOf(17.3));
		}
		catch (Exception ignore) {
			fail();
		}
		assertEquals(17.3, fooBean.getBigDouble().doubleValue(), 0.0001);

		double doubleValue = bp.getDouble(fooBean, propertyName, -1);
		assertEquals(17.3, doubleValue, 0.0001);

		try {
			doubleValue = bp.getDouble(fooBean, NONEXISTING, -1.1);
			assertEquals(-1.1, doubleValue, 0.0001);
		}
		catch (Exception ignore) {
			fail();
		}
	}

	public void testDouble() {
		FooBean fooBean = new FooBean();
		final String propertyName = "double";

		assertEquals(0.0D, fooBean.getDouble(), 0.001);

		try {
			bp.setProperty(fooBean, propertyName, Double.valueOf(17.3));
		}
		catch (Exception ignore) {
			fail();
		}
		assertEquals(17.3, fooBean.getDouble(), 0.0001);

		double doubleValue = bp.getDouble(fooBean, propertyName, -1);
		assertEquals(17.3, doubleValue, 0.0001);

		try {
			doubleValue = bp.getDouble(fooBean, NONEXISTING, -1.1);
			assertEquals(-1.1, doubleValue, 0.0001);
		}
		catch (Exception ignore) {
			fail();
		}
	}

	public void testString() {
		FooBean fooBean = new FooBean();
		final String propertyName = "string";

		assertNull(fooBean.getString());

		try {
			bp.setProperty(fooBean, propertyName, "test");
		}
		catch (Exception ignore) {
			fail();
		}
		assertEquals("test", fooBean.getString());

		String value = bp.getString(fooBean, propertyName);
		assertEquals("test", value);

		try {
			value = bp.getString(fooBean, NONEXISTING, "none");
			assertEquals("none", value);
		}
		catch (Exception ignore) {
			fail();
		}
	}

}
