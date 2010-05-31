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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.GetterUtil;

import java.io.Serializable;

import java.text.DateFormat;

import java.util.Date;

import junit.framework.TestCase;

/**
 * <a href="GetterUtilSerializableTestCase.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Raymond Augé
 */
public class GetterUtilSerializableTestCase extends TestCase {

	// Boolean tests

	public void test1() {
		Serializable value = Boolean.TRUE;

		assertTrue(GetterUtil.get(value, Boolean.TRUE));
	}

	public void test2() {
		Serializable value = Boolean.TRUE;

		assertTrue(GetterUtil.get(value, Boolean.FALSE));
	}

	public void test3() {
		Serializable value = Boolean.FALSE;

		assertFalse(GetterUtil.get(value, Boolean.FALSE));
	}

	public void test4() {
		Serializable value = Boolean.FALSE;

		assertFalse(GetterUtil.get(value, Boolean.TRUE));
	}

	public void test5() {
		Serializable value = null;

		assertTrue(GetterUtil.get(value, Boolean.TRUE));
	}

	public void test6() {
		Serializable value = null;

		assertFalse(GetterUtil.get(value, Boolean.FALSE));
	}

	public void test7() {
		Serializable value = "true";

		assertTrue(GetterUtil.get(value, Boolean.TRUE));
	}

	public void test8() {
		Serializable value = "true";

		assertTrue(GetterUtil.get(value, Boolean.FALSE));
	}

	public void test9() {
		Serializable value = "t";

		assertTrue(GetterUtil.get(value, Boolean.TRUE));
	}

	public void test10() {
		Serializable value = "t";

		assertTrue(GetterUtil.get(value, Boolean.FALSE));
	}

	public void test11() {
		Serializable value = "y";

		assertTrue(GetterUtil.get(value, Boolean.TRUE));
	}

	public void test12() {
		Serializable value = "y";

		assertTrue(GetterUtil.get(value, Boolean.FALSE));
	}

	public void test13() {
		Serializable value = "on";

		assertTrue(GetterUtil.get(value, Boolean.TRUE));
	}

	public void test14() {
		Serializable value = "on";

		assertTrue(GetterUtil.get(value, Boolean.FALSE));
	}

	public void test15() {
		Serializable value = "1";

		assertTrue(GetterUtil.get(value, Boolean.TRUE));
	}

	public void test16() {
		Serializable value = "1";

		assertTrue(GetterUtil.get(value, Boolean.FALSE));
	}

	public void test17() {
		Serializable value = "no";

		assertFalse(GetterUtil.get(value, Boolean.TRUE));
	}

	public void test18() {
		Serializable value = "no";

		assertFalse(GetterUtil.get(value, Boolean.FALSE));
	}

	public void test19() {
		Serializable value = "0";

		assertFalse(GetterUtil.get(value, Boolean.TRUE));
	}

	public void test20() {
		Serializable value = "0";

		assertFalse(GetterUtil.get(value, Boolean.FALSE));
	}

	public void test21() {
		Serializable value = new Date();

		assertTrue(GetterUtil.get(value, Boolean.TRUE));
	}

	public void test22() {
		Serializable value = new Date();

		assertFalse(GetterUtil.get(value, Boolean.FALSE));
	}

	// Date tests

	public void test23() {
		Date defaultValue = new Date(System.currentTimeMillis());

		Serializable value = new Date(System.currentTimeMillis() + 1000);

		Date result = GetterUtil.get(value, _DATE_TIME_FORMAT, defaultValue);

		assertTrue(result.equals(value));
		assertFalse(result.equals(defaultValue));
	}

	public void test25() {
		Date defaultValue = new Date();

		Serializable value = "30-May-2010 6:32:32 PM";

		Date result = GetterUtil.get(value, _DATE_TIME_FORMAT, defaultValue);

		assertTrue(value.equals(_DATE_TIME_FORMAT.format(result)));
		assertFalse(result.equals(_DATE_TIME_FORMAT.format(defaultValue)));
	}

	public void test27() {
		Date defaultValue = new Date();

		Serializable value = null;

		Date result = GetterUtil.get(value, _DATE_TIME_FORMAT, defaultValue);

		assertTrue(result.equals(defaultValue));
	}

	public void test28() {
		Date defaultValue = new Date();

		Serializable value = "Hello World!";

		Date result = GetterUtil.get(value, _DATE_TIME_FORMAT, defaultValue);

		assertTrue(result.equals(defaultValue));
	}

	// Double tests

	public void test29() {
		Double defaultValue = new Double(1.0);

		Serializable value = new Double(2.0);

		Double result = GetterUtil.get(value, defaultValue);

		assertTrue(result.equals(new Double(2.0)));
		assertFalse(result.equals(new Double(3.14)));
		assertFalse(result.equals(defaultValue));
	}

	public void test30() {
		Double defaultValue = new Double(1);

		Serializable value = 2.0d;

		Double result = GetterUtil.get(value, defaultValue);

		assertTrue(result.equals(new Double(2.0)));
		assertFalse(result.equals(new Double(3.14)));
		assertFalse(result.equals(defaultValue));
	}

	public void test31() {
		Double defaultValue = new Double(1);

		Serializable value = "2.0";

		Double result = GetterUtil.get(value, defaultValue);

		assertTrue(result.equals(new Double(2.0)));
		assertFalse(result.equals(new Double(3.14)));
		assertFalse(result.equals(defaultValue));
	}

	public void test32() {
		Double defaultValue = new Double(1);

		Serializable value = null;

		Double result = GetterUtil.get(value, defaultValue);

		assertFalse(result.equals(new Double(2.0)));
		assertFalse(result.equals(new Double(3.14)));
		assertTrue(result.equals(defaultValue));
	}

	public void test33() {
		Double defaultValue = new Double(1);

		Serializable value = "Hello World!";

		Double result = GetterUtil.get(value, defaultValue);

		assertFalse(result.equals(new Double(2.0)));
		assertFalse(result.equals(new Double(3.14)));
		assertTrue(result.equals(defaultValue));
	}

	// Float Tests

	public void test34() {
		Float defaultValue = new Float(1);

		Serializable value = new Float(2);

		Float result = GetterUtil.get(value, defaultValue);

		assertTrue(result.equals(new Float(2)));
		assertFalse(result.equals(new Float(3.14)));
		assertFalse(result.equals(defaultValue));
	}

	public void test35() {
		Float defaultValue = new Float(1);

		Serializable value = 2.0f;

		Float result = GetterUtil.get(value, defaultValue);

		assertTrue(result.equals(new Float(2.0)));
		assertFalse(result.equals(new Float(3.14)));
		assertFalse(result.equals(defaultValue));
	}

	public void test36() {
		Float defaultValue = new Float(1);

		Serializable value = "2.0";

		Float result = GetterUtil.get(value, defaultValue);

		assertTrue(result.equals(new Float(2.0)));
		assertFalse(result.equals(new Float(3.14)));
		assertFalse(result.equals(defaultValue));
	}

	public void test37() {
		Float defaultValue = new Float(1);

		Serializable value = null;

		Float result = GetterUtil.get(value, defaultValue);

		assertFalse(result.equals(new Float(2.0)));
		assertFalse(result.equals(new Float(3.14)));
		assertTrue(result.equals(defaultValue));
	}

	public void test38() {
		Float defaultValue = new Float(1);

		Serializable value = "Hello World!";

		Float result = GetterUtil.get(value, defaultValue);

		assertFalse(result.equals(new Float(2.0)));
		assertFalse(result.equals(new Float(3.14)));
		assertTrue(result.equals(defaultValue));
	}

	// Integer tests

	public void test39() {
		Integer defaultValue = new Integer(1);

		Serializable value = new Integer(2);

		Integer result = GetterUtil.get(value, defaultValue);

		assertTrue(result.equals(new Integer(2)));
		assertFalse(result.equals(new Integer(3)));
		assertFalse(result.equals(defaultValue));
	}

	public void test40() {
		Integer defaultValue = new Integer(1);

		Serializable value = 2;

		Integer result = GetterUtil.get(value, defaultValue);

		assertTrue(result.equals(new Integer(2)));
		assertFalse(result.equals(new Integer(3)));
		assertFalse(result.equals(defaultValue));
	}

	public void test41() {
		Integer defaultValue = new Integer(1);

		Serializable value = "2";

		Integer result = GetterUtil.get(value, defaultValue);

		assertTrue(result.equals(new Integer(2)));
		assertFalse(result.equals(new Integer(3)));
		assertFalse(result.equals(defaultValue));
	}

	public void test42() {
		Integer defaultValue = new Integer(1);

		Serializable value = null;

		Integer result = GetterUtil.get(value, defaultValue);

		assertFalse(result.equals(new Integer(2)));
		assertFalse(result.equals(new Integer(3)));
		assertTrue(result.equals(defaultValue));
	}

	public void test43() {
		Integer defaultValue = new Integer(1);

		Serializable value = "Hello World!";

		Integer result = GetterUtil.get(value, defaultValue);

		assertFalse(result.equals(new Integer(2)));
		assertFalse(result.equals(new Integer(3)));
		assertTrue(result.equals(defaultValue));
	}

	// Long tests

	public void test44() {
		Long defaultValue = new Long(1);

		Serializable value = new Long(2);

		Long result = GetterUtil.get(value, defaultValue);

		assertTrue(result.equals(new Long(2)));
		assertFalse(result.equals(new Long(3)));
		assertFalse(result.equals(defaultValue));
	}

	public void test45() {
		Long defaultValue = new Long(1);

		Serializable value = 2L;

		Long result = GetterUtil.get(value, defaultValue);

		assertTrue(result.equals(new Long(2)));
		assertFalse(result.equals(new Long(3)));
		assertFalse(result.equals(defaultValue));
	}

	public void test46() {
		Long defaultValue = new Long(1);

		Serializable value = "2";

		Long result = GetterUtil.get(value, defaultValue);

		assertTrue(result.equals(new Long(2)));
		assertFalse(result.equals(new Long(3)));
		assertFalse(result.equals(defaultValue));
	}

	public void test47() {
		Long defaultValue = new Long(1);

		Serializable value = null;

		Long result = GetterUtil.get(value, defaultValue);

		assertFalse(result.equals(new Long(2)));
		assertFalse(result.equals(new Long(3)));
		assertTrue(result.equals(defaultValue));
	}

	public void test48() {
		Long defaultValue = new Long(1);

		Serializable value = "Hello World!";

		Long result = GetterUtil.get(value, defaultValue);

		assertFalse(result.equals(new Long(2)));
		assertFalse(result.equals(new Long(3)));
		assertTrue(result.equals(defaultValue));
	}

	// Short tests

	public void test49() {
		Short defaultValue = new Short("1");

		Serializable value = new Short("2");

		Short result = GetterUtil.get(value, defaultValue);

		assertTrue(result.equals(new Short("2")));
		assertFalse(result.equals(new Short("3")));
		assertFalse(result.equals(defaultValue));
	}

	public void test50() {
		Short defaultValue = new Short("1");

		Serializable value = (short)2;

		Short result = GetterUtil.get(value, defaultValue);

		assertTrue(result.equals(new Short("2")));
		assertFalse(result.equals(new Short("3")));
		assertFalse(result.equals(defaultValue));
	}

	public void test51() {
		Short defaultValue = new Short("1");

		Serializable value = "2";

		Short result = GetterUtil.get(value, defaultValue);

		assertTrue(result.equals(new Short("2")));
		assertFalse(result.equals(new Short("3")));
		assertFalse(result.equals(defaultValue));
	}

	public void test52() {
		Short defaultValue = new Short("1");

		Serializable value = null;

		Short result = GetterUtil.get(value, defaultValue);

		assertFalse(result.equals(new Short("2")));
		assertFalse(result.equals(new Short("3")));
		assertTrue(result.equals(defaultValue));
	}

	public void test53() {
		Short defaultValue = new Short("1");

		Serializable value = "Hello World!";

		Short result = GetterUtil.get(value, defaultValue);

		assertFalse(result.equals(new Short("2")));
		assertFalse(result.equals(new Short("3")));
		assertTrue(result.equals(defaultValue));
	}

	// String tests

	public void test54() {
		String defaultValue = new String("1");

		Serializable value = new String("2");

		String result = GetterUtil.get(value, defaultValue);

		assertTrue(result.equals(new String("2")));
		assertFalse(result.equals(new String("3")));
		assertFalse(result.equals(defaultValue));
	}

	public void test55() {
		String defaultValue = new String("1");

		Serializable value = "2";

		String result = GetterUtil.get(value, defaultValue);

		assertTrue(result.equals(new String("2")));
		assertFalse(result.equals(new String("3")));
		assertFalse(result.equals(defaultValue));
	}

	public void test56() {
		String defaultValue = new String("1");

		Serializable value = null;

		String result = GetterUtil.get(value, defaultValue);

		assertFalse(result.equals(new String("2")));
		assertFalse(result.equals(new String("3")));
		assertTrue(result.equals(defaultValue));
	}

	private static final DateFormat _DATE_TIME_FORMAT =
		DateFormat.getDateTimeInstance();

}