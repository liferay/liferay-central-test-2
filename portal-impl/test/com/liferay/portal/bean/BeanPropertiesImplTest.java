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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public void testByte() {
		Foo foo = new Foo();

		assertEquals(0, foo.getByte());

		BeanPropertiesUtil.setProperty(foo, "byte", Byte.valueOf((byte)17));

		assertEquals(17, foo.getByte());

		byte value = BeanPropertiesUtil.getByte(foo, "byte", (byte)-1);

		assertEquals(17, value);

		value = BeanPropertiesUtil.getByte(foo, _NONEXISTENT, (byte)-1);

		assertEquals(-1, value);
	}

	public void testByteObject() {
		Foo foo = new Foo();

		assertNull(foo.getByteObject());

		BeanPropertiesUtil.setProperty(
			foo, "byteObject", Byte.valueOf((byte)13));

		assertEquals(13, foo.getByteObject().byteValue());

		byte value = BeanPropertiesUtil.getByte(foo, "byteObject", (byte)-1);

		assertEquals(13, value);

		value = BeanPropertiesUtil.getByte(foo, _NONEXISTENT, (byte)-1);

		assertEquals(-1, value);
	}

	public void testCopyEditableProperties() {
		Foo sourceFoo = createFoo();

		Foo destinationFoo = new Foo();

		assertFalse(destinationFoo.equals(sourceFoo));

		BeanPropertiesUtil.copyProperties(
			sourceFoo, destinationFoo, FooParent.class);

		assertFalse(destinationFoo.equals(sourceFoo));
		assertEquals(sourceFoo.getInteger(), destinationFoo.getInteger());
		assertEquals(sourceFoo.getInt(), destinationFoo.getInt());
		assertEquals(sourceFoo.getLongObject(), destinationFoo.getLongObject());
		assertEquals(sourceFoo.getLong(), destinationFoo.getLong());

		Bar sourceBar = new Bar();

		sourceBar.setFoo(sourceFoo);

		Bar destinationBar = new Bar();

		BeanPropertiesUtil.copyProperties(
			sourceBar, destinationBar, FooParent.class);
	}

	public void testCopyProperties() {
		Foo sourceFoo = createFoo();

		Foo destinationFoo = new Foo();

		assertFalse(destinationFoo.equals(sourceFoo));

		BeanPropertiesUtil.copyProperties(sourceFoo, destinationFoo);

		assertEquals(destinationFoo, sourceFoo);

		Bar sourceBar = new Bar();

		sourceBar.setFoo(sourceFoo);

		Bar destinationBar = new Bar();

		assertNotSame(destinationBar.getFoo(), sourceFoo);

		BeanPropertiesUtil.copyProperties(sourceBar, destinationBar);

		assertSame(destinationBar.getFoo(), sourceFoo);
	}

	public void testCopySomeProperties() {
		Foo sourceFoo = createFoo();

		Foo destinationFoo = new Foo();

		assertFalse(destinationFoo.equals(sourceFoo));

		BeanPropertiesUtil.copyProperties(
			sourceFoo, destinationFoo, new String[] {"string", "integer"});

		assertFalse(destinationFoo.equals(sourceFoo));
		assertNull(destinationFoo.getString());
		assertNull(destinationFoo.getInteger());

		destinationFoo.setString(sourceFoo.getString());
		destinationFoo.setInteger(sourceFoo.getInteger());

		assertEquals(destinationFoo, sourceFoo);

		Bar sourceBar = new Bar();

		sourceBar.setFoo(sourceFoo);

		Bar destinationBar = new Bar();

		BeanPropertiesUtil.copyProperties(
			sourceBar, destinationBar,
			new String[] {"foo.string", "foo.integer"});

		assertEquals(destinationBar.getFoo(), sourceBar.getFoo());
		assertSame(destinationBar.getFoo(), sourceBar.getFoo());

		destinationBar = new Bar();

		BeanPropertiesUtil.copyProperties(
			sourceBar, destinationBar, new String[] {"foo"});

		assertNotSame(destinationBar.getFoo(), sourceBar.getFoo());
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

	public void testFloat() {
		Foo foo = new Foo();

		assertEquals(0.0F, foo.getFloat(), 0.001);

		BeanPropertiesUtil.setProperty(foo, "float", Float.valueOf(17.3F));

		assertEquals(17.3F, foo.getFloat(), 0.0001);

		float value = BeanPropertiesUtil.getFloat(foo, "float", -1F);

		assertEquals(17.3F, value, 0.0001);

		value = BeanPropertiesUtil.getFloat(foo, _NONEXISTENT, -1.1F);

		assertEquals(-1.1F, value, 0.0001);
	}

	public void testFloatObject() {
		Foo foo = new Foo();

		assertNull(foo.getFloatObject());

		BeanPropertiesUtil.setProperty(
			foo, "floatObject", Float.valueOf(17.3F));

		assertEquals(17.3F, foo.getFloatObject().floatValue(), 0.0001);

		float value = BeanPropertiesUtil.getFloat(foo, "floatObject", -1F);

		assertEquals(17.3F, value, 0.0001);

		value = BeanPropertiesUtil.getFloat(foo, _NONEXISTENT, -1.1F);

		assertEquals(-1.1F, value, 0.0001);
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

	public void testList() {
		Foo foo = createFoo();

		int value = BeanPropertiesUtil.getInteger(foo, "list[1]", -1);

		assertEquals(102, value);

		value = BeanPropertiesUtil.getInteger(foo, "list[3]", -1);

		assertEquals(-1, value);
		assertEquals(3, foo.getList().size());

		BeanPropertiesUtil.setProperty(foo, "list[3]", Integer.valueOf(200));

		assertEquals(3, foo.getList().size());
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

	public void testMap() {
		Foo foo = createFoo();

		int value = BeanPropertiesUtil.getInteger(foo, "map[two]", -1);

		assertEquals(-1, value);
	}

	public void testMissingInnerProperty() {
		Bar bar = new Bar();

		bar.setFoo(null);

		BeanPropertiesUtil.setProperty(bar, "foo.int", Integer.valueOf(173));
	}

	public void testObject() {
		Foo foo = new Foo();

		foo.setString("test");

		String value = (String)BeanPropertiesUtil.getObject(
			foo, "string", "none");

		assertEquals("test", value);

		value = (String)BeanPropertiesUtil.getObject(foo, _NONEXISTENT, "none");

		assertEquals("none", value);
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

	public void testShort() {
		Foo foo = new Foo();

		assertEquals(0, foo.getShort());

		BeanPropertiesUtil.setProperty(foo, "short", Short.valueOf((short)173));

		assertEquals(173, foo.getShort());

		short value = BeanPropertiesUtil.getShort(foo, "short", (short)-1);

		assertEquals(173, value);

		value = BeanPropertiesUtil.getShort(foo, _NONEXISTENT, (short)-1);

		assertEquals(-1, value);
	}

	public void testShortObject() {
		Foo foo = new Foo();

		assertNull(foo.getShortObject());

		BeanPropertiesUtil.setProperty(
			foo, "shortObject", Short.valueOf((short)13));

		assertEquals(13, foo.getShortObject().shortValue());

		short value = BeanPropertiesUtil.getShort(
			foo, "shortObject", (short)-1);

		assertEquals(13, value);

		value = BeanPropertiesUtil.getShort(foo, _NONEXISTENT, (short)-1);

		assertEquals(-1, value);
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

	public void testStringArray() {
		Bar bar = new Bar();

		Foo foo = bar.getFoo();

		foo.setStringArray(new String[] {"one", "two", "three"});

		BeanPropertiesUtil.setProperty(bar, "foo.stringArray[1]", "TWO");

		assertEquals("TWO", foo.getStringArray()[1]);

		BeanPropertiesUtil.setProperty(bar, "foo.stringArray[3]", "four");

		assertEquals(3, foo.getStringArray().length);
	}

	protected Foo createFoo() {
		Foo foo = new Foo();

		foo.setBoolean(true);
		foo.setBooleanObject(Boolean.TRUE);
		foo.setByte((byte)13);
		foo.setByteObject(Byte.valueOf((byte)7));
		foo.setChar('L');
		foo.setCharacter(Character.valueOf('P'));
		foo.setDouble(37.1);
		foo.setDoubleObject(Double.valueOf(17.3));
		foo.setFloat(3.7f);
		foo.setFloatObject(Float.valueOf(13.7f));
		foo.setInt(173);
		foo.setInteger(Integer.valueOf(1730));

		List<Integer> list = new ArrayList<Integer>();

		list.add(Integer.valueOf(101));
		list.add(Integer.valueOf(102));
		list.add(Integer.valueOf(103));

		foo.setList(list);

		foo.setLong(1773L);

		Map<String, Integer> map = new HashMap<String, Integer>();

		map.put("one", Integer.valueOf(1));
		map.put("two", Integer.valueOf(2));

		foo.setMap(map);

		foo.setShort((short)173);
		foo.setShortObject(Short.valueOf((short)173));
		foo.setString("test");
		foo.setStringArray(new String[] {"a", "b", "c"});

		return foo;
	}

	private static final String _NONEXISTENT = "nonexistent";

}