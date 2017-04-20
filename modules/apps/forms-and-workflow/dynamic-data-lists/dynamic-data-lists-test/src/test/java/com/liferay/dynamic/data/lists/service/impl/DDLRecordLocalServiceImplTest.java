/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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
package com.liferay.dynamic.data.lists.service.impl;

import static com.liferay.portal.kernel.test.util.RandomTestUtil.randomBoolean;
import static com.liferay.portal.kernel.test.util.RandomTestUtil.randomInt;
import static com.liferay.portal.kernel.util.StringUtil.randomString;

import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.Fields;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class DDLRecordLocalServiceImplTest {

	@Test
	public void testToFieldWithBoolean() throws Exception {
		boolean fieldValue = randomBoolean();

		Field field = toField(fieldValue);

		assertFieldValues(
			Arrays.asList(fieldValue), field.getValues(Locale.US));
	}

	@Test
	public void testToFieldWithBooleanArray() throws Exception {
		boolean[] fieldValues =
			new boolean[] {randomBoolean(), randomBoolean(), randomBoolean()};

		Field field = toField(fieldValues);

		assertFieldValues(toList(fieldValues), field.getValues(Locale.US));
	}

	@Test
	public void testToFieldWithBooleanList() throws Exception {
		List<Serializable> fieldValues = new ArrayList<>();

		fieldValues.add(randomBoolean());
		fieldValues.add(randomBoolean());

		Field field = toField((Serializable)fieldValues);

		assertFieldValues(fieldValues, field.getValues(Locale.US));
	}

	@Test
	public void testToFieldWithBooleanSet() throws Exception {
		Set<Serializable> fieldValues = new HashSet<>();

		fieldValues.add(randomBoolean());
		fieldValues.add(randomBoolean());

		Field field = toField((Serializable)fieldValues);

		assertFieldValues(fieldValues, field.getValues(Locale.US));
	}

	@Test
	public void testToFieldWithInteger() throws Exception {
		int fieldValue = randomInt();

		Field field = toField(fieldValue);

		assertFieldValues(
			Arrays.asList(fieldValue), field.getValues(Locale.US));
	}

	@Test
	public void testToFieldWithIntegerArray() throws Exception {
		int[] fieldValues = new int[] {randomInt(), randomInt(), randomInt()};

		Field field = toField(fieldValues);

		assertFieldValues(toList(fieldValues), field.getValues(Locale.US));
	}

	@Test
	public void testToFieldWithIntegerList() throws Exception {
		List<Serializable> fieldValues = new ArrayList<>();

		fieldValues.add(randomInt());
		fieldValues.add(randomInt());

		Field field = toField((Serializable)fieldValues);

		assertFieldValues(fieldValues, field.getValues(Locale.US));
	}

	@Test
	public void testToFieldWithIntegerSet() throws Exception {
		Set<Serializable> fieldValues = new HashSet<>();

		fieldValues.add(randomInt());
		fieldValues.add(randomInt());

		Field field = toField((Serializable)fieldValues);

		assertFieldValues(fieldValues, field.getValues(Locale.US));
	}

	@Test
	public void testToFieldWithString() throws Exception {
		String fieldValue = randomString();

		Field field = toField(fieldValue);

		assertFieldValues(
			Arrays.asList(fieldValue), field.getValues(Locale.US));
	}

	@Test
	public void testToFieldWithStringArray() throws Exception {
		String[] fieldValues = {randomString(), randomString(), randomString()};

		Field field = toField(fieldValues);

		assertFieldValues(toList(fieldValues), field.getValues(Locale.US));
	}

	@Test
	public void testToFieldWithStringList() throws Exception {
		List<Serializable> fieldValues = new ArrayList<>();

		fieldValues.add(randomString());
		fieldValues.add(randomString());

		Field field = toField((Serializable)fieldValues);

		assertFieldValues(fieldValues, field.getValues(Locale.US));
	}

	@Test
	public void testToFieldWithStringSet() throws Exception {
		Set<Serializable> fieldValues = new HashSet<>();

		fieldValues.add(randomString());
		fieldValues.add(randomString());

		Field field = toField((Serializable)fieldValues);

		assertFieldValues(fieldValues, field.getValues(Locale.US));
	}

	protected void assertFieldValues(
		Collection<Serializable> expectedValues,
		List<Serializable> actualValues) {

		Assert.assertEquals(
			actualValues.toString(), expectedValues.size(),
			actualValues.size());

		int i = 0;

		for (Serializable expectedValue : expectedValues) {
			Assert.assertEquals(expectedValue, actualValues.get(i++));
		}
	}

	protected void assertToFields(Collection<Serializable> fieldValues)
		throws Exception {

		Map<String, Serializable> fieldsMap = new HashMap<>();

		String fieldName = randomString();

		fieldsMap.put(fieldName, (Serializable)fieldValues);

		Fields fields = _recordLocalServiceImpl.toFields(
			0, fieldsMap, Locale.US, Locale.US);

		Field field = fields.get(fieldName);

		assertFieldValues(fieldValues, field.getValues(Locale.US));
	}

	protected void assertToFields(Serializable[] fieldValues) throws Exception {
		Map<String, Serializable> fieldsMap = new HashMap<>();

		String fieldName = randomString();

		fieldsMap.put(fieldName, (Serializable)fieldValues);

		Fields fields = _recordLocalServiceImpl.toFields(
			0, fieldsMap, Locale.US, Locale.US);

		Field field = fields.get(fieldName);

		assertFieldValues(
			Arrays.asList(fieldValues), field.getValues(Locale.US));
	}

	protected Field toField(Serializable fieldValue) throws Exception {
		Map<String, Serializable> fieldsMap = new HashMap<>();

		String fieldName = randomString();

		fieldsMap.put(fieldName, fieldValue);

		Fields fields = _recordLocalServiceImpl.toFields(
			0, fieldsMap, Locale.US, Locale.US);

		return fields.get(fieldName);
	}

	protected List<Serializable> toList(boolean[] fieldValues) {
		List<Serializable> fieldValuesList = new ArrayList<>();

		for (boolean fieldValue : fieldValues) {
			fieldValuesList.add(fieldValue);
		}

		return fieldValuesList;
	}

	protected List<Serializable> toList(int[] fieldValues) {
		List<Serializable> fieldValuesList = new ArrayList<>();

		for (int fieldValue : fieldValues) {
			fieldValuesList.add(fieldValue);
		}

		return fieldValuesList;
	}

	protected List<Serializable> toList(String[] fieldValues) {
		List<Serializable> fieldValuesList = new ArrayList<>();

		for (String fieldValue : fieldValues) {
			fieldValuesList.add(fieldValue);
		}

		return fieldValuesList;
	}

	private final DDLRecordLocalServiceImpl _recordLocalServiceImpl =
		new DDLRecordLocalServiceImpl();

}