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
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Alejandro Tardín
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

	private final Map<String, Object> _inputEditorTaglibAttributes =
		new HashMap<>();

	@Mock
	private ItemSelector _itemSelector;

	@Mock
	private RequestBackedPortletURLFactory _requestBackedPortletURLFactory;

	@Mock
	private ThemeDisplay _themeDisplay;

}