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
import com.liferay.blogs.web.constants.BlogsPortletKeys;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringPool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.powermock.api.mockito.PowerMockito;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Sergio González
 */
public class AdaptiveMediaBlogsEditorConfigContributorTest
	extends PowerMockito {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

		when(
			_requestBackedPortletURLFactory.createActionURL(
				BlogsPortletKeys.BLOGS)
		).thenReturn(
			mock(LiferayPortletURL.class)
		);

		_inputEditorTaglibAttributes.put(
			"liferay-ui:input-editor:name", "testEditor");
	}

	@Test
	public void testAddImageAdaptiveMediaURLItemSelectorReturnType()
		throws Exception {

		AdaptiveMediaBlogsEditorConfigContributor
			adaptiveMediaBlogsEditorConfigContributor =
				new AdaptiveMediaBlogsEditorConfigContributor();

		BlogsItemSelectorCriterion blogsItemSelectorCriterion =
			new BlogsItemSelectorCriterion();

		blogsItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.<ItemSelectorReturnType>singletonList(
				new URLItemSelectorReturnType()));

		adaptiveMediaBlogsEditorConfigContributor.
			addImageAdaptiveMediaURLItemSelectorReturnType(
				blogsItemSelectorCriterion);

		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			blogsItemSelectorCriterion.getDesiredItemSelectorReturnTypes();

		Assert.assertEquals(2, desiredItemSelectorReturnTypes.size());
		Assert.assertTrue(
			desiredItemSelectorReturnTypes.get(0) instanceof
				ImageAdaptiveMediaURLItemSelectorReturnType);
		Assert.assertTrue(
			desiredItemSelectorReturnTypes.get(1) instanceof
				URLItemSelectorReturnType);
	}

	@Test
	public void testItemSelectorURLWhenNoBlogsItemSelectorCriterion()
		throws Exception {

		when(
			_itemSelector.getItemSelectorCriteria(
				"fileItemSelectorCriterionURLItemSelectorReturnType")
		).thenReturn(
			_getFileItemSelectorCriterionURLItemSelectorReturnType()
		);

		JSONObject originalJSONObject = JSONFactoryUtil.createJSONObject();

		originalJSONObject.put(
			"filebrowserImageBrowseLinkUrl",
			"fileItemSelectorCriterionURLItemSelectorReturnType");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		AdaptiveMediaBlogsEditorConfigContributor
			adaptiveMediaBlogsEditorConfigContributor =
				new AdaptiveMediaBlogsEditorConfigContributor();

		adaptiveMediaBlogsEditorConfigContributor.setItemSelector(
			_itemSelector);

		adaptiveMediaBlogsEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		Mockito.verify(
			_itemSelector, Mockito.never()
		).getItemSelectorURL(
			Mockito.any(RequestBackedPortletURLFactory.class),
			Mockito.anyString(), Mockito.any(ItemSelectorCriterion.class));

		JSONAssert.assertEquals(
			originalJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	@Test
	public void testItemSelectorURLWhenNoFileBrowserImageBrowseLinkUrl()
		throws Exception {

		JSONObject originalJSONObject = JSONFactoryUtil.createJSONObject();

		originalJSONObject.put(
			"filebrowserImageBrowseLinkUrl", StringPool.BLANK);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		AdaptiveMediaBlogsEditorConfigContributor
			adaptiveMediaBlogsEditorConfigContributor =
				new AdaptiveMediaBlogsEditorConfigContributor();

		adaptiveMediaBlogsEditorConfigContributor.setItemSelector(
			_itemSelector);

		adaptiveMediaBlogsEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		Mockito.verify(
			_itemSelector, Mockito.never()
		).getItemSelectorURL(
			Mockito.any(RequestBackedPortletURLFactory.class),
			Mockito.anyString(), Mockito.any(ItemSelectorCriterion.class));

		JSONAssert.assertEquals(
			originalJSONObject.toJSONString(), jsonObject.toJSONString(), true);
	}

	@Test
	public void testItemSelectorURLWithBlogsItemSelectorCriterion()
		throws Exception {

		PortletURL itemSelectorPortletURL = mock(PortletURL.class);

		when(
			itemSelectorPortletURL.toString()
		).thenReturn(
			"itemSelectorPortletURL"
		);

		when(
			_itemSelector.getItemSelectorURL(
				Mockito.any(RequestBackedPortletURLFactory.class),
				Mockito.anyString(), Mockito.any(ItemSelectorCriterion.class))
		).thenReturn(
			itemSelectorPortletURL
		);

		when(
			_itemSelector.getItemSelectedEventName(Mockito.anyString())
		).thenReturn(
			"selectedEventName"
		);

		when(
			_itemSelector.getItemSelectorCriteria(
				"blogsItemSelectorCriterionURLItemSelectorReturnType")
		).thenReturn(
			_getBlogsItemSelectorCriterionURLItemSelectorReturnType()
		);

		JSONObject originalJSONObject = JSONFactoryUtil.createJSONObject();

		originalJSONObject.put(
			"filebrowserImageBrowseLinkUrl",
			"blogsItemSelectorCriterionURLItemSelectorReturnType");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toJSONString());

		AdaptiveMediaBlogsEditorConfigContributor
			adaptiveMediaBlogsEditorConfigContributor =
				new AdaptiveMediaBlogsEditorConfigContributor();

		adaptiveMediaBlogsEditorConfigContributor.setItemSelector(
			_itemSelector);

		adaptiveMediaBlogsEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		Mockito.verify(
			_itemSelector
		).getItemSelectorURL(
			Mockito.any(RequestBackedPortletURLFactory.class),
			Mockito.anyString(), Mockito.any(ItemSelectorCriterion.class));
	}

	private List<ItemSelectorCriterion>
		_getBlogsItemSelectorCriterionURLItemSelectorReturnType() {

		BlogsItemSelectorCriterion blogsItemSelectorCriterion =
			new BlogsItemSelectorCriterion();

		blogsItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.<ItemSelectorReturnType>singletonList(
				new URLItemSelectorReturnType()));

		List<ItemSelectorCriterion> itemSelectorCriteria = new ArrayList<>();

		itemSelectorCriteria.add(blogsItemSelectorCriterion);

		return itemSelectorCriteria;
	}

	private List<ItemSelectorCriterion>
		_getFileItemSelectorCriterionURLItemSelectorReturnType() {

		FileItemSelectorCriterion fileItemSelectorCriterion =
			new FileItemSelectorCriterion();

		fileItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.<ItemSelectorReturnType>singletonList(
				new URLItemSelectorReturnType()));

		List<ItemSelectorCriterion> itemSelectorCriteria = new ArrayList<>();

		itemSelectorCriteria.add(fileItemSelectorCriterion);

		return itemSelectorCriteria;
	}

	private final Map<String, Object> _inputEditorTaglibAttributes =
		new HashMap<>();

	@Mock
	private ItemSelector _itemSelector;

	@Mock
	private RequestBackedPortletURLFactory _requestBackedPortletURLFactory;

	@Mock
	private ThemeDisplay _themeDisplay;

}