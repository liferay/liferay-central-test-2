/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Olaf Kock
 */
public class ListUtilTest {

	@Test
	public void testRemoveEmptyElement() {
		List<String> list = new ArrayList<String>();

		list.add("aaa");
		list.add("bbb");
		list.add("ccc");

		List<String> removeList = new ArrayList<String>();

		List<String> expectedList = new ArrayList<String>();

		expectedList.add("aaa");
		expectedList.add("bbb");
		expectedList.add("ccc");

		Assert.assertEquals(expectedList, ListUtil.remove(list, removeList));
	}

	@Test
	public void testRemoveFromEmptyList() {
		List<String> list = Collections.<String>emptyList();

		List<String> removeList = new ArrayList<String>();

		removeList.add("aaa");
		removeList.add("bbb");

		Assert.assertEquals(
			Collections.emptyList(),
			ListUtil.remove(list, removeList));
	}

	@Test
	public void testRemoveMultipleElements() {
		List<String> list = new ArrayList<String>();

		list.add("aaa");
		list.add("bbb");
		list.add("ccc");

		List<String> removeList = new ArrayList<String>();

		removeList.add("aaa");
		removeList.add("bbb");
		removeList.add("ccc");

		Assert.assertEquals(
			Collections.emptyList(), ListUtil.remove(list, removeList));
	}

	@Test
	public void testRemoveNullElement() {
		List<String> list = new ArrayList<String>();

		list.add("aaa");
		list.add("bbb");
		list.add("ccc");

		List<String> expectedList = new ArrayList<String>();

		expectedList.add("aaa");
		expectedList.add("bbb");
		expectedList.add("ccc");

		List<String> removeList = null;

		Assert.assertEquals(expectedList, ListUtil.remove(list, removeList));
	}

	@Test
	public void testRemoveSingleElement() {
		List<String> list = new ArrayList<String>();

		list.add("aaa");
		list.add("bbb");
		list.add("ccc");

		List<String> removeList = new ArrayList<String>();

		removeList.add("aaa");

		List<String> expectedList = new ArrayList<String>();

		expectedList.add("bbb");
		expectedList.add("ccc");

		Assert.assertEquals(expectedList, ListUtil.remove(list, removeList));
	}

	@Test
	public void testRemoveWrongElement() {
		List<String> list = new ArrayList<String>();

		list.add("aaa");
		list.add("bbb");
		list.add("ccc");

		List<String> removeList = new ArrayList<String>();

		removeList.add("ddd");

		List<String> expectedList = new ArrayList<String>();

		expectedList.add("aaa");
		expectedList.add("bbb");
		expectedList.add("ccc");

		Assert.assertEquals(expectedList, ListUtil.remove(list, removeList));
	}

	@Test
	public void testToStringIntegerList() throws Exception {
		List<Integer> list = new ArrayList<Integer>();

		list.add(111);
		list.add(222);
		list.add(333);
		list.add(444);

		String expectedString = "111, 222, 333, 444";

		Assert.assertEquals(
			expectedString,
			ListUtil.toString(list, (String)null, StringPool.COMMA_AND_SPACE));
	}

	@Test
	public void testToStringLongList() throws Exception {
		List<Long> list = new ArrayList<Long>();

		list.add(111L);
		list.add(222L);
		list.add(333L);
		list.add(444L);

		String expectedString = "111, 222, 333, 444";

		Assert.assertEquals(
			expectedString,
			ListUtil.toString(list, (String)null, StringPool.COMMA_AND_SPACE));
	}

	@Test
	public void testToStringStringList() throws Exception {
		List<String> list = new ArrayList<String>();

		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		list.add("ddd");

		String expectedString = "aaa, bbb, ccc, ddd";

		Assert.assertEquals(
			expectedString,
			ListUtil.toString(list, (String)null, StringPool.COMMA_AND_SPACE));
	}

}