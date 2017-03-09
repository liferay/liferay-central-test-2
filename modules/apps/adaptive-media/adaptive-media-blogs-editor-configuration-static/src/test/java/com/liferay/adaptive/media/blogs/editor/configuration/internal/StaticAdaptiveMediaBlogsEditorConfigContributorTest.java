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

package com.liferay.adaptive.media.blogs.editor.configuration.internal;

import com.liferay.adaptive.media.image.item.selector.ImageAdaptiveMediaURLItemSelectorReturnType;
import com.liferay.blogs.item.selector.criterion.BlogsItemSelectorCriterion;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Alejandro Tardín
 */
@RunWith(MockitoJUnitRunner.class)
public class StaticAdaptiveMediaBlogsEditorConfigContributorTest {

	@Before
	public void setUp() {
		Mockito.when(
			_itemSelectorPortletURL.toString()
		).thenReturn(
			"itemSelectorPortletURL"
		);

		Mockito.when(
			_itemSelector.getItemSelectedEventName(_itemSelectorURL)
		).thenReturn(
			_eventName
		);

		_staticAdaptiveMediaBlogsEditorConfigContributor =
			new StaticAdaptiveMediaBlogsEditorConfigContributor();

		_staticAdaptiveMediaBlogsEditorConfigContributor.setItemSelector(
			_itemSelector);
	}

	@Test
	public void testAddImageAdaptiveMediaURLItemSelectorReturnType()
		throws Exception {

		BlogsItemSelectorCriterion blogsItemSelectorCriterion =
			new BlogsItemSelectorCriterion();

		blogsItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.<ItemSelectorReturnType>singletonList(
				new URLItemSelectorReturnType()));

		_staticAdaptiveMediaBlogsEditorConfigContributor.
			addImageAdaptiveMediaURLItemSelectorReturnType(
				blogsItemSelectorCriterion);

		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			blogsItemSelectorCriterion.getDesiredItemSelectorReturnTypes();

		Assert.assertEquals(
			desiredItemSelectorReturnTypes.toString(), 2,
			desiredItemSelectorReturnTypes.size());
		Assert.assertTrue(
			desiredItemSelectorReturnTypes.get(0) instanceof
				ImageAdaptiveMediaURLItemSelectorReturnType);
		Assert.assertTrue(
			desiredItemSelectorReturnTypes.get(1) instanceof
				URLItemSelectorReturnType);
	}

	@Test
	public void testAddsPictureAndSourceAreAddedToAllowedContentForBlogs()
		throws Exception {

		_testAddsPictureTagToAllowedContent(
			_getItemSelectorCriterion(BlogsItemSelectorCriterion.class));
	}

	@Test
	public void testAddsPictureAndSourceAreAddedToAllowedContentForFile()
		throws Exception {

		_testAddsPictureTagToAllowedContent(
			_getItemSelectorCriterion(FileItemSelectorCriterion.class));
	}

	@Test
	public void testAddsPictureAndSourceAreAddedToAllowedContentForImage()
		throws Exception {

		_testAddsPictureTagToAllowedContent(
			_getItemSelectorCriterion(ImageItemSelectorCriterion.class));
	}

	@Test
	public void testDoesNothingForUnsupportedItemSelectorCriterion()
		throws Exception {

		JSONObject originalJSONObject = new JSONObjectImpl();

		originalJSONObject.put("allowedContent", "a[*](*); div(*);");
		originalJSONObject.put(
			"filebrowserImageBrowseLinkUrl", _itemSelectorURL);

		JSONObject jsonObject = new JSONObjectImpl(
			originalJSONObject.toJSONString());

		JSONObject expectedJSONObject = new JSONObjectImpl(
			originalJSONObject.toJSONString());

		Mockito.when(
			_itemSelector.getItemSelectorCriteria(_itemSelectorURL)
		).thenReturn(
			Collections.<ItemSelectorCriterion>emptyList()
		);

		_staticAdaptiveMediaBlogsEditorConfigContributor.
			populateConfigJSONObject(
				jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
				_requestBackedPortletURLFactory);

		JSONAssert.assertEquals(
			expectedJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	@Test
	public void testDoesNothingIfTheAdaptiveMediaPluginHasAlreadyBeenAdded()
		throws Exception {

		ItemSelectorCriterion itemSelectorCriterion = _getItemSelectorCriterion(
			BlogsItemSelectorCriterion.class);

		Mockito.when(
			_itemSelector.getItemSelectorCriteria(_itemSelectorURL)
		).thenReturn(
			Arrays.asList(itemSelectorCriterion)
		);

		Mockito.when(
			_itemSelector.getItemSelectorURL(
				_requestBackedPortletURLFactory, _eventName,
				itemSelectorCriterion)
		).thenReturn(
			_itemSelectorPortletURL
		);

		JSONObject originalJSONObject = new JSONObjectImpl();

		originalJSONObject.put("allowedContent", "a[*](*); div(*);");
		originalJSONObject.put("extraPlugins", "adaptivemedia");
		originalJSONObject.put(
			"filebrowserImageBrowseLinkUrl", _itemSelectorURL);

		JSONObject jsonObject = new JSONObjectImpl(
			originalJSONObject.toJSONString());

		JSONObject expectedJSONObject = new JSONObjectImpl(
			originalJSONObject.toJSONString());

		_staticAdaptiveMediaBlogsEditorConfigContributor.
			populateConfigJSONObject(
				jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
				_requestBackedPortletURLFactory);

		JSONAssert.assertEquals(
			expectedJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	@Test
	public void testDoesNothingIfThereIsNotFileBrowserImageBrowseLinkUrl()
		throws Exception {

		JSONObject originalJSONObject = new JSONObjectImpl();

		originalJSONObject.put("allowedContent", "a[*](*); div(*);");

		JSONObject jsonObject = new JSONObjectImpl(
			originalJSONObject.toJSONString());

		JSONObject expectedJSONObject = new JSONObjectImpl(
			originalJSONObject.toJSONString());

		_staticAdaptiveMediaBlogsEditorConfigContributor.
			populateConfigJSONObject(
				jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
				_requestBackedPortletURLFactory);

		JSONAssert.assertEquals(
			expectedJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	@Test
	public void testSetsPictureAndSourceAreAddedToAllowedContentForBlogs()
		throws Exception {

		_testSetsPictureTagToAllowedContent(
			_getItemSelectorCriterion(BlogsItemSelectorCriterion.class));
	}

	@Test
	public void testSetsPictureAndSourceAreAddedToAllowedContentForFile()
		throws Exception {

		_testSetsPictureTagToAllowedContent(
			_getItemSelectorCriterion(FileItemSelectorCriterion.class));
	}

	@Test
	public void testSetsPictureAndSourceAreAddedToAllowedContentForImage()
		throws Exception {

		_testSetsPictureTagToAllowedContent(
			_getItemSelectorCriterion(ImageItemSelectorCriterion.class));
	}

	private ItemSelectorCriterion _getItemSelectorCriterion(
			Class<? extends ItemSelectorCriterion> itemSelectorCriterionClass)
		throws IllegalAccessException, InstantiationException {

		ItemSelectorCriterion itemSelectorCriterion =
			itemSelectorCriterionClass.newInstance();

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new ArrayList<ItemSelectorReturnType>());

		return itemSelectorCriterion;
	}

	private void _testAddsPictureTagToAllowedContent(
			ItemSelectorCriterion itemSelectorCriterion)
		throws JSONException, org.json.JSONException {

		Mockito.when(
			_itemSelector.getItemSelectorCriteria(_itemSelectorURL)
		).thenReturn(
			Arrays.asList(itemSelectorCriterion)
		);

		Mockito.when(
			_itemSelector.getItemSelectorURL(
				_requestBackedPortletURLFactory, _eventName,
				itemSelectorCriterion)
		).thenReturn(
			_itemSelectorPortletURL
		);

		JSONObject originalJSONObject = new JSONObjectImpl();

		originalJSONObject.put("allowedContent", "a[*](*); div(*);");
		originalJSONObject.put(
			"filebrowserImageBrowseLinkUrl", _itemSelectorURL);

		JSONObject jsonObject = new JSONObjectImpl(
			originalJSONObject.toJSONString());

		JSONObject expectedJSONObject = new JSONObjectImpl(
			originalJSONObject.toJSONString());

		expectedJSONObject.put(
			"allowedContent", "a[*](*); div(*); picture[*](*); source[*](*);");
		expectedJSONObject.put("extraPlugins", "adaptivemedia");
		expectedJSONObject.put(
			"filebrowserImageBrowseLinkUrl",
			_itemSelectorPortletURL.toString());
		expectedJSONObject.put(
			"filebrowserImageBrowseUrl", _itemSelectorPortletURL.toString());

		_staticAdaptiveMediaBlogsEditorConfigContributor.
			populateConfigJSONObject(
				jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
				_requestBackedPortletURLFactory);

		JSONAssert.assertEquals(
			expectedJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	private void _testSetsPictureTagToAllowedContent(
			ItemSelectorCriterion itemSelectorCriterion)
		throws Exception {

		Mockito.when(
			_itemSelector.getItemSelectorCriteria(_itemSelectorURL)
		).thenReturn(
			Arrays.asList(itemSelectorCriterion)
		);

		Mockito.when(
			_itemSelector.getItemSelectorURL(
				_requestBackedPortletURLFactory, _eventName,
				itemSelectorCriterion)
		).thenReturn(
			_itemSelectorPortletURL
		);

		JSONObject originalJSONObject = new JSONObjectImpl();

		originalJSONObject.put(
			"filebrowserImageBrowseLinkUrl", _itemSelectorURL);

		JSONObject jsonObject = new JSONObjectImpl(
			originalJSONObject.toJSONString());

		_staticAdaptiveMediaBlogsEditorConfigContributor.
			populateConfigJSONObject(
				jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
				_requestBackedPortletURLFactory);

		JSONObject expectedJSONObject = new JSONObjectImpl();

		expectedJSONObject.put(
			"allowedContent", "picture[*](*); source[*](*);");
		expectedJSONObject.put("extraPlugins", "adaptivemedia");
		expectedJSONObject.put(
			"filebrowserImageBrowseLinkUrl",
			_itemSelectorPortletURL.toString());
		expectedJSONObject.put(
			"filebrowserImageBrowseUrl", _itemSelectorPortletURL.toString());

		JSONAssert.assertEquals(
			expectedJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	private static final String _eventName = "selectedEventName";
	private static final String _itemSelectorURL = "itemSelectorURL";

	private final Map<String, Object> _inputEditorTaglibAttributes =
		new HashMap<>();

	@Mock
	private ItemSelector _itemSelector;

	@Mock
	private PortletURL _itemSelectorPortletURL;

	@Mock
	private RequestBackedPortletURLFactory _requestBackedPortletURLFactory;

	private StaticAdaptiveMediaBlogsEditorConfigContributor
		_staticAdaptiveMediaBlogsEditorConfigContributor;

	@Mock
	private ThemeDisplay _themeDisplay;

}