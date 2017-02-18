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

package com.liferay.item.selector.web.internal.util;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.web.internal.FlickrItemSelectorCriterion;
import com.liferay.item.selector.web.internal.TestFileEntryItemSelectorReturnType;
import com.liferay.item.selector.web.internal.TestStringItemSelectorReturnType;
import com.liferay.item.selector.web.internal.TestURLItemSelectorReturnType;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Iván Zaera
 * @author Roberto Díaz
 */
public class ItemSelectorCriterionSerializerImplTest {

	@Before
	public void setUp() {
		_flickrItemSelectorCriterion = new FlickrItemSelectorCriterion();

		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			new ArrayList<>();

		desiredItemSelectorReturnTypes.add(_testStringItemSelectorReturnType);
		desiredItemSelectorReturnTypes.add(_testURLItemSelectorReturnType);

		_flickrItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			desiredItemSelectorReturnTypes);

		_itemSelectorCriterionSerializerImpl.addItemSelectorReturnType(
			_testFileEntryItemSelectorReturnType);
		_itemSelectorCriterionSerializerImpl.addItemSelectorReturnType(
			_testStringItemSelectorReturnType);
		_itemSelectorCriterionSerializerImpl.addItemSelectorReturnType(
			_testURLItemSelectorReturnType);

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	@Test
	public void testGetProperties() {
		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			new ArrayList<>();

		desiredItemSelectorReturnTypes.add(_testURLItemSelectorReturnType);

		_flickrItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			desiredItemSelectorReturnTypes);

		String json = _itemSelectorCriterionSerializerImpl.serialize(
			_flickrItemSelectorCriterion);

		Class<? extends ItemSelectorReturnType>
			testURLItemSelectorReturnTypeClass =
				_testURLItemSelectorReturnType.getClass();

		json = _assert(
			"\"desiredItemSelectorReturnTypes\":\"" +
				testURLItemSelectorReturnTypeClass.getName() + "\"",
			json);

		json = _assert("\"tags\":[\"me\",\"photo\",\"picture\"]", json);
		json = _assert("\"user\":\"anonymous\"", json);

		Assert.assertEquals("{,,}", json);
	}

	@Test
	public void testSetProperties() {
		Class<? extends ItemSelectorReturnType>
			testURLItemSelectorReturnTypeClass =
				_testURLItemSelectorReturnType.getClass();

		String json =
			"{\"desiredItemSelectorReturnTypes\":\"" +
				testURLItemSelectorReturnTypeClass.getName() + "\",\"tags\":" +
					"[\"tag1\",\"tag2\",\"tag3\"],\"user\":\"Joe Bloggs\"}";

		_flickrItemSelectorCriterion =
			_itemSelectorCriterionSerializerImpl.deserialize(
				_flickrItemSelectorCriterion.getClass(), json);

		Assert.assertEquals(
			"Joe Bloggs", _flickrItemSelectorCriterion.getUser());
		Assert.assertArrayEquals(
			new String[] {"tag1", "tag2", "tag3"},
			_flickrItemSelectorCriterion.getTags());

		List<ItemSelectorReturnType> expectedDesiredItemSelectorReturnTypes =
			new ArrayList<>();

		expectedDesiredItemSelectorReturnTypes.add(
			_testURLItemSelectorReturnType);

		Assert.assertEquals(
			expectedDesiredItemSelectorReturnTypes,
			_flickrItemSelectorCriterion.getDesiredItemSelectorReturnTypes());
	}

	private String _assert(String expected, String json) {
		Assert.assertTrue(json.contains(expected));

		return json.replaceAll(Pattern.quote(expected), "");
	}

	private FlickrItemSelectorCriterion _flickrItemSelectorCriterion;
	private final StubItemSelectorCriterionSerializerImpl
		_itemSelectorCriterionSerializerImpl =
			new StubItemSelectorCriterionSerializerImpl();
	private final ItemSelectorReturnType _testFileEntryItemSelectorReturnType =
		new TestFileEntryItemSelectorReturnType();
	private final ItemSelectorReturnType _testStringItemSelectorReturnType =
		new TestStringItemSelectorReturnType();
	private final ItemSelectorReturnType _testURLItemSelectorReturnType =
		new TestURLItemSelectorReturnType();

	private class StubItemSelectorCriterionSerializerImpl
		extends ItemSelectorCriterionSerializerImpl {

		@Override
		public void addItemSelectorReturnType(
			ItemSelectorReturnType itemSelectorReturnType) {

			super.addItemSelectorReturnType(itemSelectorReturnType);
		}

	}

}