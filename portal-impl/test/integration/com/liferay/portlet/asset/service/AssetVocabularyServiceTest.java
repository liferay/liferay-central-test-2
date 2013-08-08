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

package com.liferay.portlet.asset.service;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.asset.model.AssetVocabulary;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class AssetVocabularyServiceTest {

	@Before
	public void setUp() {
		_locale = LocaleThreadLocal.getSiteDefaultLocale();
	}

	@After
	public void tearDown() {
		LocaleThreadLocal.setSiteDefaultLocale(_locale);
	}

	@Test
	public void testLocalizedSiteAddDefaultVocabulary() throws Exception {
		Group group = GroupTestUtil.addGroup();

		LocaleThreadLocal.setSiteDefaultLocale(new Locale("es", "ES"));

		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.addDefaultVocabulary(
				group.getGroupId());

		Assert.assertEquals(
			PropsValues.ASSET_VOCABULARY_DEFAULT,
			assetVocabulary.getTitle(Locale.US, true));
	}

	@Test
	public void testLocalizedSiteAddLocalizedVocabulary() throws Exception {
		Locale esLocale = new Locale("es", "ES");

		LocaleThreadLocal.setSiteDefaultLocale(esLocale);

		String title = ServiceTestUtil.randomString();

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		titleMap.put(Locale.US, title + "_US");
		titleMap.put(esLocale, title + "_ES");

		String description = ServiceTestUtil.randomString();

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		descriptionMap.put(Locale.US, description + "_US");
		descriptionMap.put(esLocale, description + "_ES");

		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.addVocabulary(
				TestPropsValues.getUserId(), StringPool.BLANK, titleMap,
				descriptionMap, StringPool.BLANK, serviceContext);

		Assert.assertEquals(titleMap.get(esLocale), assetVocabulary.getName());
		Assert.assertEquals(
			titleMap.get(Locale.US), assetVocabulary.getTitle(Locale.US, true));
		Assert.assertEquals(
			titleMap.get(esLocale), assetVocabulary.getTitle(esLocale, true));

		Locale deLocale = new Locale("de", "DE");

		Assert.assertEquals(
			titleMap.get(esLocale), assetVocabulary.getTitle(deLocale, true));

		Assert.assertEquals(
			descriptionMap.get(Locale.US),
			assetVocabulary.getDescription(Locale.US, true));
		Assert.assertEquals(
			descriptionMap.get(esLocale),
			assetVocabulary.getDescription(esLocale, true));
		Assert.assertEquals(
			descriptionMap.get(esLocale),
			assetVocabulary.getDescription(deLocale, true));
	}

	@Test
	public void testLocalizedSiteAddVocabulary() throws Exception {
		LocaleThreadLocal.setSiteDefaultLocale(new Locale("es", "ES"));

		String title = ServiceTestUtil.randomString();

		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.addVocabulary(
				TestPropsValues.getUserId(), title, serviceContext);

		Assert.assertEquals(title, assetVocabulary.getTitle(Locale.US, true));
		Assert.assertEquals(title, assetVocabulary.getName());
	}

	private Locale _locale;

}