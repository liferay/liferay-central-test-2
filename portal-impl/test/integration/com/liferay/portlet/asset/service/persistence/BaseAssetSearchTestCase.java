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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portlet.asset.service.persistence.test.AssetEntryQueryTestUtil;
import com.liferay.portlet.asset.util.AssetUtil;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Eudaldo Alonso
 */
@Sync
public abstract class BaseAssetSearchTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group1 = GroupTestUtil.addGroup();

		ServiceContext group1ServiceContext =
			ServiceContextTestUtil.getServiceContext(_group1.getGroupId());

		AssetVocabulary vocabulary =
			AssetVocabularyLocalServiceUtil.addVocabulary(
				TestPropsValues.getUserId(), RandomTestUtil.randomString(),
				group1ServiceContext);

		_vocabularyId = vocabulary.getVocabularyId();

		AssetCategory fashionCategory =
			AssetCategoryLocalServiceUtil.addCategory(
				TestPropsValues.getUserId(), "Fashion", _vocabularyId,
				group1ServiceContext);

		_fashionCategoryId = fashionCategory.getCategoryId();

		AssetCategory foodCategory = AssetCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(), "Food", _vocabularyId,
			group1ServiceContext);

		_foodCategoryId = foodCategory.getCategoryId();

		AssetCategory healthCategory =
			AssetCategoryLocalServiceUtil.addCategory(
				TestPropsValues.getUserId(), "Health", _vocabularyId,
				group1ServiceContext);

		_healthCategoryId = healthCategory.getCategoryId();

		AssetCategory sportCategory = AssetCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(), "Sport", _vocabularyId,
			group1ServiceContext);

		_sportCategoryId = sportCategory.getCategoryId();

		AssetCategory travelCategory =
			AssetCategoryLocalServiceUtil.addCategory(
				TestPropsValues.getUserId(), "Travel", _vocabularyId,
				group1ServiceContext);

		_travelCategoryId = travelCategory.getCategoryId();

		_assetCategoryIds1 =
			new long[] {_healthCategoryId, _sportCategoryId, _travelCategoryId};
		_assetCategoryIds2 = new long[] {
			_fashionCategoryId, _foodCategoryId, _healthCategoryId,
			_sportCategoryId
		};

		_group2 = GroupTestUtil.addGroup();

		ServiceContext group2ServiceContext =
			ServiceContextTestUtil.getServiceContext(_group2.getGroupId());

		ServiceContext[] serviceContexts = new ServiceContext[] {
			group1ServiceContext, group2ServiceContext};

		for (ServiceContext serviceContext : serviceContexts) {
			AssetTagLocalServiceUtil.addTag(
				TestPropsValues.getUserId(), "liferay", serviceContext);

			AssetTagLocalServiceUtil.addTag(
				TestPropsValues.getUserId(), "architecture", serviceContext);

			AssetTagLocalServiceUtil.addTag(
				TestPropsValues.getUserId(), "modularity", serviceContext);

			AssetTagLocalServiceUtil.addTag(
				TestPropsValues.getUserId(), "osgi", serviceContext);

			AssetTagLocalServiceUtil.addTag(
				TestPropsValues.getUserId(), "services", serviceContext);
		}

		_assetTagsNames1 =
			new String[] {"liferay", "architecture", "modularity", "osgi"};
		_assetTagsNames2 = new String[] {"liferay", "architecture", "services"};
	}

	@Test
	public void testAllAssetCategories1() throws Exception {
		long[] allCategoryIds = {_healthCategoryId};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), null, null,
				allCategoryIds, null);

		testAssetCategorization(assetEntryQuery, 2);
	}

	@Test
	public void testAllAssetCategories2() throws Exception {
		long[] allCategoryIds = {_healthCategoryId, _sportCategoryId};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), null, null,
				allCategoryIds, null);

		testAssetCategorization(assetEntryQuery, 2);
	}

	@Test
	public void testAllAssetCategories3() throws Exception {
		long[] allCategoryIds =
			{_healthCategoryId, _sportCategoryId, _foodCategoryId};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), null, null,
				allCategoryIds, null);

		testAssetCategorization(assetEntryQuery, 1);
	}

	@Test
	public void testAllAssetCategories4() throws Exception {
		long[] allCategoryIds = {
			_healthCategoryId, _sportCategoryId, _foodCategoryId,
			_travelCategoryId
		};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), null, null,
				allCategoryIds, null);

		testAssetCategorization(assetEntryQuery, 0);
	}

	@Test
	public void testAllAssetTags1() throws Exception {
		String[] allTagNames = {"liferay"};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), null, null,
				allTagNames, null);

		testAssetCategorization(assetEntryQuery, 2);
	}

	@Test
	public void testAllAssetTags2() throws Exception {
		String[] allTagNames = {"liferay", "architecture"};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), null, null,
				allTagNames, null);

		testAssetCategorization(assetEntryQuery, 2);
	}

	@Test
	public void testAllAssetTags3() throws Exception {
		String[] allTagNames = {"liferay", "architecture", "services"};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), null, null,
				allTagNames, null);

		testAssetCategorization(assetEntryQuery, 1);
	}

	@Test
	public void testAllAssetTags4() throws Exception {
		String[] allTagNames = {"liferay", "architecture", "services", "osgi"};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), null, null,
				allTagNames, null);

		testAssetCategorization(assetEntryQuery, 0);
	}

	@Test
	public void testAllAssetTagsMultipleGroups1() throws Exception {
		String[] allTagNames = {"liferay"};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				new long[] {_group1.getGroupId(), _group2.getGroupId()},
				getBaseModelClassName(), null, null, allTagNames, null);

		testAssetCategorization(
			new Group[] {_group1, _group2}, assetEntryQuery, 4);
	}

	@Test
	public void testAllAssetTagsMultipleGroups2() throws Exception {
		String[] allTagNames = {"liferay", "architecture"};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				new long[] {_group1.getGroupId(), _group2.getGroupId()},
				getBaseModelClassName(), null, null, allTagNames, null);

		testAssetCategorization(
			new Group[] {_group1, _group2}, assetEntryQuery, 4);
	}

	@Test
	public void testAllAssetTagsMultipleGroups3() throws Exception {
		String[] allTagNames = {"liferay", "architecture", "services"};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				new long[] {_group1.getGroupId(), _group2.getGroupId()},
				getBaseModelClassName(), null, null, allTagNames, null);

		testAssetCategorization(
			new Group[] {_group1, _group2}, assetEntryQuery, 2);
	}

	@Test
	public void testAllAssetTagsMultipleGroups4() throws Exception {
		String[] allTagNames = {"liferay", "architecture", "services", "osgi"};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				new long[] {_group1.getGroupId(), _group2.getGroupId()},
				getBaseModelClassName(), null, null, allTagNames, null);

		testAssetCategorization(
			new Group[] {_group1, _group2}, assetEntryQuery, 0);
	}

	@Test
	public void testAnyAssetCategories1() throws Exception {
		long[] anyCategoryIds = {_healthCategoryId};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), null, null, null,
				anyCategoryIds);

		testAssetCategorization(assetEntryQuery, 2);
	}

	@Test
	public void testAnyAssetCategories2() throws Exception {
		long[] anyCategoryIds = {_healthCategoryId, _sportCategoryId};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), null, null, null,
				anyCategoryIds);

		testAssetCategorization(assetEntryQuery, 2);
	}

	@Test
	public void testAnyAssetCategories3() throws Exception {
		long[] anyCategoryIds =
			{_healthCategoryId, _sportCategoryId, _foodCategoryId};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), null, null, null,
				anyCategoryIds);

		testAssetCategorization(assetEntryQuery, 2);
	}

	@Test
	public void testAnyAssetCategories4() throws Exception {
		long[] anyCategoryIds = {_fashionCategoryId, _foodCategoryId};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), null, null, null,
				anyCategoryIds);

		testAssetCategorization(assetEntryQuery, 1);
	}

	@Test
	public void testAnyAssetTags1() throws Exception {
		String[] anyTagNames = {"liferay"};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), null, null, null,
				anyTagNames);

		testAssetCategorization(assetEntryQuery, 2);
	}

	@Test
	public void testAnyAssetTags2() throws Exception {
		String[] anyTagNames = {"liferay", "architecture"};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), null, null, null,
				anyTagNames);

		testAssetCategorization(assetEntryQuery, 2);
	}

	@Test
	public void testAnyAssetTags3() throws Exception {
		String[] anyTagNames = {"liferay", "architecture", "services"};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), null, null, null,
				anyTagNames);

		testAssetCategorization(assetEntryQuery, 2);
	}

	@Test
	public void testAnyAssetTags4() throws Exception {
		String[] anyTagNames = {"modularity", "osgi"};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), null, null, null,
				anyTagNames);

		testAssetCategorization(assetEntryQuery, 1);
	}

	@Test
	public void testAssetCategoryAllAndAny() throws Exception {
		long[] allCategoryIds =
			{_healthCategoryId, _sportCategoryId, _travelCategoryId};
		long[] anyCategoryIds = {_healthCategoryId};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), null, null,
				allCategoryIds, anyCategoryIds);

		testAssetCategorization(assetEntryQuery, 1);
	}

	@Test
	public void testAssetCategoryNotAllAndAll() throws Exception {
		long[] notAllCategoryIds = {_fashionCategoryId, _foodCategoryId};
		long[] allCategoryIds =
			{_healthCategoryId, _sportCategoryId, _travelCategoryId};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(),
				notAllCategoryIds, null, allCategoryIds, null);

		testAssetCategorization(assetEntryQuery, 1);
	}

	@Test
	public void testAssetCategoryNotAllAndAny() throws Exception {
		long[] notAllCategoryIds = {_fashionCategoryId};
		long[] anyCategoryIds = {_sportCategoryId, _travelCategoryId};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(),
				notAllCategoryIds, null, null, anyCategoryIds);

		testAssetCategorization(assetEntryQuery, 1);
	}

	@Test
	public void testAssetCategoryNotAllAndNotAny() throws Exception {
		long[] notAllCategoryIds = {_fashionCategoryId, _foodCategoryId};
		long[] notAnyCategoryIds = {_travelCategoryId};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(),
				notAllCategoryIds, notAnyCategoryIds, null, null);

		testAssetCategorization(assetEntryQuery, 0);
	}

	@Test
	public void testAssetCategoryNotAnyAndAll() throws Exception {
		long[] notAnyCategoryIds = {_fashionCategoryId};
		long[] allCategoryIds = {_healthCategoryId, _sportCategoryId};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), null,
				notAnyCategoryIds, allCategoryIds, null);

		testAssetCategorization(assetEntryQuery, 1);
	}

	@Test
	public void testAssetCategoryNotAnyAndAny() throws Exception {
		long[] notAnyCategoryIds = {_fashionCategoryId, _foodCategoryId};
		long[] anyCategoryIds =
			{_healthCategoryId, _sportCategoryId, _travelCategoryId};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), null,
				notAnyCategoryIds, null, anyCategoryIds);

		testAssetCategorization(assetEntryQuery, 1);
	}

	@Test
	public void testAssetTagsAllAndAny() throws Exception {
		String[] allTagNames = {"liferay", "architecture", "services"};
		String[] anyTagNames = {"liferay"};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), null, null,
				allTagNames, anyTagNames);

		testAssetCategorization(assetEntryQuery, 1);
	}

	@Test
	public void testAssetTagsNotAllAndAll() throws Exception {
		String[] notAllTagNames = {"osgi", "modularity"};
		String[] allTagNames = {"liferay", "architecture", "services"};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), notAllTagNames,
				null, allTagNames, null);

		testAssetCategorization(assetEntryQuery, 1);
	}

	@Test
	public void testAssetTagsNotAllAndAny() throws Exception {
		String[] notAllTagNames = {"services"};
		String[] anyTagNames = {"liferay", "architecture"};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), notAllTagNames,
				null, null, anyTagNames);

		testAssetCategorization(assetEntryQuery, 1);
	}

	@Test
	public void testAssetTagsNotAllAndNotAny() throws Exception {
		String[] notAllTagNames = {"osgi", "modularity"};
		String[] notAnyTagNames = {"services"};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), notAllTagNames,
				notAnyTagNames, null, null);

		testAssetCategorization(assetEntryQuery, 0);
	}

	@Test
	public void testAssetTagsNotAnyAndAll() throws Exception {
		String[] notAnyTagNames = {"modularity"};
		String[] allTagNames = {"liferay", "architecture"};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), null,
				notAnyTagNames, allTagNames, null);

		testAssetCategorization(assetEntryQuery, 1);
	}

	@Test
	public void testAssetTagsNotAnyAndAny() throws Exception {
		String[] notAnyTagNames = {"modularity", "osgi"};
		String[] anyTagNames = {"liferay", "architecture", "services"};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), null,
				notAnyTagNames, null, anyTagNames);

		testAssetCategorization(assetEntryQuery, 1);
	}

	@Test
	public void testClassName1() throws Exception {
		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), new String[]{getBaseModelClassName()});

		testClassNames(assetEntryQuery, 1);
	}

	@Test
	public void testClassName2() throws Exception {
		long[] classNameIds = AssetRendererFactoryRegistryUtil.getClassNameIds(
			TestPropsValues.getCompanyId());

		classNameIds = ArrayUtil.remove(
			classNameIds, PortalUtil.getClassNameId(getBaseModelClass()));

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), classNameIds);

		testClassNames(assetEntryQuery, 0);
	}

	@Test
	public void testClassTypeIds1() throws Exception {
		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), new String[]{getBaseModelClassName()});

		testClassTypeIds(assetEntryQuery, true);
	}

	@Test
	public void testClassTypeIds2() throws Exception {
		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), new String[]{getBaseModelClassName()});

		testClassTypeIds(assetEntryQuery, false);
	}

	@Test
	public void testGroups() throws Exception {
		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		assetEntryQuery.setClassName(getBaseModelClassName());

		Group group1 = GroupTestUtil.addGroup();
		Group group2 = GroupTestUtil.addGroup();

		assetEntryQuery.setGroupIds(
			new long[] {group1.getGroupId(), group2.getGroupId()});

		SearchContext searchContext = SearchContextTestUtil.getSearchContext();

		searchContext.setGroupIds(assetEntryQuery.getGroupIds());

		int initialEntries = searchCount(assetEntryQuery, searchContext);

		ServiceContext serviceContext1 =
			ServiceContextTestUtil.getServiceContext(group1.getGroupId());

		BaseModel<?> parentBaseModel1 = getParentBaseModel(
			group1, serviceContext1);

		addBaseModel(parentBaseModel1, getSearchKeywords(), serviceContext1);

		ServiceContext serviceContext2 =
			ServiceContextTestUtil.getServiceContext(group2.getGroupId());

		BaseModel<?> parentBaseModel2 = getParentBaseModel(
			group1, serviceContext2);

		addBaseModel(parentBaseModel2, getSearchKeywords(), serviceContext2);

		Assert.assertEquals(
			initialEntries + 2, searchCount(assetEntryQuery, searchContext));
	}

	@Test
	public void testNotAllAssetCategories1() throws Exception {
		long[] notAllCategoryIds = {_healthCategoryId};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(),
				notAllCategoryIds, null, null, null);

		testAssetCategorization(assetEntryQuery, 0);
	}

	@Test
	public void testNotAllAssetCategories2() throws Exception {
		long[] notAllCategoryIds = {_healthCategoryId, _sportCategoryId};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(),
				notAllCategoryIds, null, null, null);

		testAssetCategorization(assetEntryQuery, 0);
	}

	@Test
	public void testNotAllAssetCategories3() throws Exception {
		long[] notAllCategoryIds = {_fashionCategoryId, _foodCategoryId};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(),
				notAllCategoryIds, null, null, null);

		testAssetCategorization(assetEntryQuery, 1);
	}

	@Test
	public void testNotAllAssetCategories4() throws Exception {
		long[] notAllCategoryIds =
			{_fashionCategoryId, _foodCategoryId, _travelCategoryId};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(),
				notAllCategoryIds, null, null, null);

		testAssetCategorization(assetEntryQuery, 2);
	}

	@Test
	public void testNotAllAssetTags1() throws Exception {
		String[] notAllTagNames = {"liferay"};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), notAllTagNames,
				null, null, null);

		testAssetCategorization(assetEntryQuery, 0);
	}

	@Test
	public void testNotAllAssetTags2() throws Exception {
		String[] notAllTagNames = {"liferay", "architecture"};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), notAllTagNames,
				null, null, null);

		testAssetCategorization(assetEntryQuery, 0);
	}

	@Test
	public void testNotAllAssetTags3() throws Exception {
		String[] notAllTagNames = {"liferay", "architecture", "services"};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), notAllTagNames,
				null, null, null);

		testAssetCategorization(assetEntryQuery, 1);
	}

	@Test
	public void testNotAllAssetTags4() throws Exception {
		String[] notAllTagNames =
			{"liferay", "architecture", "services", "osgi"};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), notAllTagNames,
				null, null, null);

		testAssetCategorization(assetEntryQuery, 2);
	}

	@Test
	public void testNotAllAssetTagsMultipleGroups1() throws Exception {
		String[] notAllTagNames = {"liferay"};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				new long[] {_group1.getGroupId(), _group2.getGroupId()},
				getBaseModelClassName(), notAllTagNames, null, null, null);

		testAssetCategorization(
			new Group[] {_group1, _group2}, assetEntryQuery, 0);
	}

	@Test
	public void testNotAllAssetTagsMultipleGroups2() throws Exception {
		String[] notAllTagNames = {"liferay", "architecture"};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				new long[] {_group1.getGroupId(), _group2.getGroupId()},
				getBaseModelClassName(), notAllTagNames, null, null, null);

		testAssetCategorization(
			new Group[] {_group1, _group2}, assetEntryQuery, 0);
	}

	@Test
	public void testNotAllAssetTagsMultipleGroups3() throws Exception {
		String[] notAllTagNames = {"liferay", "architecture", "services"};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				new long[] {_group1.getGroupId(), _group2.getGroupId()},
				getBaseModelClassName(), notAllTagNames, null, null, null);

		testAssetCategorization(
			new Group[] {_group1, _group2}, assetEntryQuery, 2);
	}

	@Test
	public void testNotAllAssetTagsMultipleGroups4() throws Exception {
		String[] notAllTagNames =
			{"liferay", "architecture", "services", "osgi"};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				new long[] {_group1.getGroupId(), _group2.getGroupId()},
				getBaseModelClassName(), notAllTagNames, null, null, null);

		testAssetCategorization(
			new Group[] {_group1, _group2}, assetEntryQuery, 4);
	}

	@Test
	public void testNotAnyAssetCategories1() throws Exception {
		long[] notAnyCategoryIds = {_healthCategoryId};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), null,
				notAnyCategoryIds, null, null);

		testAssetCategorization(assetEntryQuery, 0);
	}

	@Test
	public void testNotAnyAssetCategories2() throws Exception {
		long[] notAnyCategoryIds = {_healthCategoryId, _sportCategoryId};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), null,
				notAnyCategoryIds, null, null);

		testAssetCategorization(assetEntryQuery, 0);
	}

	@Test
	public void testNotAnyAssetCategories3() throws Exception {
		long[] notAnyCategoryIds =
			{_fashionCategoryId, _foodCategoryId, _travelCategoryId};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), null,
				notAnyCategoryIds, null, null);

		testAssetCategorization(assetEntryQuery, 0);
	}

	@Test
	public void testNotAnyAssetCategories4() throws Exception {
		long[] notAnyCategoryIds = {_fashionCategoryId, _foodCategoryId};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), null,
				notAnyCategoryIds, null, null);

		testAssetCategorization(assetEntryQuery, 1);
	}

	@Test
	public void testNotAnyAssetTags1() throws Exception {
		String[] notAnyTagNames = {"liferay"};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), null,
				notAnyTagNames, null, null);

		testAssetCategorization(assetEntryQuery, 0);
	}

	@Test
	public void testNotAnyAssetTags2() throws Exception {
		String[] notAnyTagNames = {"liferay", "architecture"};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), null,
				notAnyTagNames, null, null);

		testAssetCategorization(assetEntryQuery, 0);
	}

	@Test
	public void testNotAnyAssetTags3() throws Exception {
		String[] notAnyTagNames = {"liferay", "architecture", "services"};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), null,
				notAnyTagNames, null, null);

		testAssetCategorization(assetEntryQuery, 0);
	}

	@Test
	public void testNotAnyAssetTags4() throws Exception {
		String[] notAnyTagNames = {"modularity", "osgi"};

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), getBaseModelClassName(), null,
				notAnyTagNames, null, null);

		testAssetCategorization(assetEntryQuery, 1);
	}

	@Test
	public void testOrderByCreateDateAsc() throws Exception {
		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), new String[]{getBaseModelClassName()});

		String[] titles = {
			"open", "liferay", "social", "osgi", "content", "life"
		};

		testOrderByCreateDate(assetEntryQuery, "asc", titles, titles);
	}

	@Test
	public void testOrderByCreateDateDesc() throws Exception {
		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), new String[]{getBaseModelClassName()});

		String[] titles = {
			"open", "liferay", "social", "osgi", "content", "life"
		};

		String[] orderedTitles = {
			"life", "content", "osgi", "social", "liferay", "open"
		};

		testOrderByCreateDate(assetEntryQuery, "desc", titles, orderedTitles);
	}

	@Test
	public void testOrderByExpirationDateAsc() throws Exception {
		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), new String[]{getBaseModelClassName()});

		Date[] expirationDates = generateRandomDates(new Date(), 6);

		testOrderByExpirationDate(assetEntryQuery, "asc", expirationDates);
	}

	@Test
	public void testOrderByExpirationDateDesc() throws Exception {
		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), new String[]{getBaseModelClassName()});

		Date[] expirationDates = generateRandomDates(new Date(), 6);

		testOrderByExpirationDate(assetEntryQuery, "desc", expirationDates);
	}

	@Test
	public void testOrderByTitleAsc() throws Exception {
		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), new String[]{getBaseModelClassName()});

		String[] titles = {
			"open", "liferay", "social", "osgi", "content", "life"
		};

		String[] orderedTitles = {
			"content", "life", "liferay", "open", "osgi", "social"
		};

		testOrderByTitle(assetEntryQuery, "asc", titles, orderedTitles);
	}

	@Test
	public void testOrderByTitleDesc() throws Exception {
		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), new String[]{getBaseModelClassName()});

		String[] titles = {
			"open", "liferay", "social", "osgi", "content", "life"
		};

		String[] orderedTitles = {
			"social", "osgi", "open", "liferay", "life", "content"
		};

		testOrderByTitle(assetEntryQuery, "desc", titles, orderedTitles);
	}

	@Test
	public void testPaginationTypeNone() throws Exception {
		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), new String[]{getBaseModelClassName()});

		assetEntryQuery.setPaginationType("none");

		testPaginationType(assetEntryQuery, 5);
	}

	@Test
	public void testPaginationTypeRegular() throws Exception {
		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), new String[]{getBaseModelClassName()});

		assetEntryQuery.setPaginationType("regular");

		testPaginationType(assetEntryQuery, 5);
	}

	@Test
	public void testPaginationTypeSimple() throws Exception {
		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group1.getGroupId(), new String[]{getBaseModelClassName()});

		assetEntryQuery.setPaginationType("simple");

		testPaginationType(assetEntryQuery, 5);
	}

	protected BaseModel<?> addBaseModel(
			BaseModel<?> parentBaseModel, String keywords, Date expirationDate,
			ServiceContext serviceContext)
		throws Exception {

		return addBaseModel(parentBaseModel, keywords, serviceContext);
	}

	protected abstract BaseModel<?> addBaseModel(
			BaseModel<?> parentBaseModel, String keywords,
			ServiceContext serviceContext)
		throws Exception;

	protected List<BaseModel<?>> addBaseModels(
			Group[] groups, String keywords, ServiceContext serviceContext)
		throws Exception {

		List<BaseModel<?>> baseModels = new ArrayList<>();

		for (Group group : groups) {
			User user = UserTestUtil.getAdminUser(group.getCompanyId());

			serviceContext.setCompanyId(group.getCompanyId());
			serviceContext.setScopeGroupId(group.getGroupId());
			serviceContext.setUserId(user.getUserId());

			BaseModel<?> parentBaseModel = getParentBaseModel(
				group, serviceContext);

			baseModels.add(
				addBaseModel(parentBaseModel, keywords, serviceContext));
		}

		return baseModels;
	}

	protected BaseModel<?> addBaseModelWithClassType(
			BaseModel<?> parentBaseModel, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		return addBaseModel(parentBaseModel, keywords, serviceContext);
	}

	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, String keywords, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		return addBaseModel(parentBaseModel, keywords, serviceContext);
	}

	protected Date[] generateRandomDates(Date startDate, int size) {
		Date[] dates = new Date[size];

		for (int i = 0; i < size; i++) {
			Date date = new Date(
				startDate.getTime() + (RandomUtil.nextInt(365) + 1) * Time.DAY);

			Calendar calendar = new GregorianCalendar();

			calendar.setTime(date);

			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);

			dates[i] = calendar.getTime();
		}

		return dates;
	}

	protected abstract Class<?> getBaseModelClass();

	protected String getBaseModelClassName() {
		Class<?> clazz = getBaseModelClass();

		return clazz.getName();
	}

	protected long[] getClassTypeIds() {
		return null;
	}

	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		return group;
	}

	protected abstract String getSearchKeywords();

	protected AssetEntry[] search(
			AssetEntryQuery assetEntryQuery, SearchContext searchContext)
		throws Exception {

		Hits results = AssetUtil.search(
			searchContext, assetEntryQuery, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		List<AssetEntry> assetEntries = AssetUtil.getAssetEntries(results);

		return assetEntries.toArray(new AssetEntry[assetEntries.size()]);
	}

	protected int searchCount(
			AssetEntryQuery assetEntryQuery, SearchContext searchContext)
		throws Exception {

		return searchCount(
			assetEntryQuery, searchContext, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

	protected int searchCount(
			AssetEntryQuery assetEntryQuery, SearchContext searchContext,
			int start, int end)
		throws Exception {

		Hits results = AssetUtil.search(
			searchContext, assetEntryQuery, start, end);

		return results.getLength();
	}

	protected void testAssetCategorization(
			AssetEntryQuery assetEntryQuery, int expectedResults)
		throws Exception {

		testAssetCategorization(
			new Group[] {_group1}, assetEntryQuery, expectedResults);
	}

	protected void testAssetCategorization(
			Group[] groups, AssetEntryQuery assetEntryQuery,
			int expectedResults)
		throws Exception {

		SearchContext searchContext = SearchContextTestUtil.getSearchContext();

		searchContext.setGroupIds(assetEntryQuery.getGroupIds());

		int initialEntries = searchCount(assetEntryQuery, searchContext);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groups[0].getGroupId());

		serviceContext.setAssetTagNames(_assetTagsNames1);
		serviceContext.setAssetCategoryIds(_assetCategoryIds1);

		addBaseModels(groups, getSearchKeywords(), serviceContext);

		serviceContext.setAssetTagNames(_assetTagsNames2);
		serviceContext.setAssetCategoryIds(_assetCategoryIds2);

		addBaseModels(groups, getSearchKeywords(), serviceContext);

		Assert.assertEquals(
			initialEntries + expectedResults,
			searchCount(assetEntryQuery, searchContext));
	}

	protected void testClassNames(
			AssetEntryQuery assetEntryQuery, int expectedResult)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group1.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			_group1, serviceContext);

		SearchContext searchContext = SearchContextTestUtil.getSearchContext();

		searchContext.setGroupIds(assetEntryQuery.getGroupIds());

		int initialEntries = searchCount(assetEntryQuery, searchContext);

		addBaseModel(parentBaseModel, getSearchKeywords(), serviceContext);

		Assert.assertEquals(
			initialEntries + expectedResult,
			searchCount(assetEntryQuery, searchContext));
	}

	protected void testClassTypeIds(
			AssetEntryQuery assetEntryQuery, boolean classType)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group1.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			_group1, serviceContext);

		SearchContext searchContext = SearchContextTestUtil.getSearchContext();

		searchContext.setGroupIds(assetEntryQuery.getGroupIds());

		int initialEntries = searchCount(assetEntryQuery, searchContext);

		addBaseModelWithClassType(
			parentBaseModel, getSearchKeywords(), serviceContext);

		if (classType) {
			assetEntryQuery.setClassTypeIds(getClassTypeIds());

			Assert.assertEquals(
				initialEntries + 1,
				searchCount(assetEntryQuery, searchContext));
		}
		else {
			assetEntryQuery.setClassTypeIds(new long[] {0});

			Assert.assertEquals(
				initialEntries, searchCount(assetEntryQuery, searchContext));
		}
	}

	protected void testOrderByCreateDate(
			AssetEntryQuery assetEntryQuery, String orderByType,
			String[] titles, String[] orderedTitles)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group1.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			_group1, serviceContext);

		SearchContext searchContext = SearchContextTestUtil.getSearchContext();

		searchContext.setGroupIds(assetEntryQuery.getGroupIds());

		BaseModel<?>[] baseModels = new BaseModel[titles.length];

		for (int i = 0; i < titles.length; i++) {
			String title = titles[i];

			baseModels[i] = addBaseModel(
				parentBaseModel, title, serviceContext);
		}

		assetEntryQuery.setOrderByCol1("createDate");
		assetEntryQuery.setOrderByType1(orderByType);

		AssetEntry[] assetEntries = search(assetEntryQuery, searchContext);

		for (int i = 0; i < assetEntries.length; i++) {
			AssetEntry assetEntry = assetEntries[i];

			String field = assetEntry.getTitle(LocaleUtil.getDefault());

			Assert.assertEquals(field, orderedTitles[i]);
		}
	}

	protected void testOrderByExpirationDate(
			AssetEntryQuery assetEntryQuery, String orderByType,
			Date[] expirationDates)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group1.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			_group1, serviceContext);

		SearchContext searchContext = SearchContextTestUtil.getSearchContext();

		searchContext.setGroupIds(assetEntryQuery.getGroupIds());

		for (Date expirationDate : expirationDates) {
			addBaseModel(
				parentBaseModel, RandomTestUtil.randomString(), expirationDate,
				serviceContext);
		}

		assetEntryQuery.setOrderByCol1("expirationDate");
		assetEntryQuery.setOrderByType1(orderByType);

		Arrays.sort(expirationDates);

		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			PropsValues.INDEX_DATE_FORMAT_PATTERN);

		AssetEntry[] assetEntries = search(assetEntryQuery, searchContext);

		for (int i = 0; i < assetEntries.length; i++) {
			AssetEntry assetEntry = assetEntries[i];

			String expirationDate = dateFormat.format(
				assetEntry.getExpirationDate());

			int index = i;

			if (orderByType.equals("desc")) {
				index = assetEntries.length - 1 - i;
			}

			Assert.assertEquals(
				expirationDate, dateFormat.format(expirationDates[index]));
		}
	}

	protected void testOrderByTitle(
			AssetEntryQuery assetEntryQuery, String orderByType,
			String[] titles, String[] orderedTitles)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group1.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			_group1, serviceContext);

		for (String title : titles) {
			addBaseModel(parentBaseModel, title, serviceContext);
		}

		assetEntryQuery.setOrderByCol1("title");
		assetEntryQuery.setOrderByType1(orderByType);

		SearchContext searchContext = SearchContextTestUtil.getSearchContext();

		searchContext.setGroupIds(assetEntryQuery.getGroupIds());

		AssetEntry[] assetEntries = search(assetEntryQuery, searchContext);

		for (int i = 0; i < assetEntries.length; i++) {
			AssetEntry assetEntry = assetEntries[i];

			String field = assetEntry.getTitle(LocaleUtil.getDefault());

			Assert.assertEquals(orderedTitles[i], field);
		}
	}

	protected void testPaginationType(AssetEntryQuery assetEntryQuery, int size)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group1.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			_group1, serviceContext);

		SearchContext searchContext = SearchContextTestUtil.getSearchContext();

		searchContext.setGroupIds(assetEntryQuery.getGroupIds());

		for (int i = 0; i < size; i++) {
			addBaseModel(
				parentBaseModel, RandomTestUtil.randomString(), serviceContext);
		}

		Assert.assertEquals(
			size, searchCount(assetEntryQuery, searchContext, 0, 1));
	}

	private long[] _assetCategoryIds1;
	private long[] _assetCategoryIds2;
	private String[] _assetTagsNames1;
	private String[] _assetTagsNames2;
	private long _fashionCategoryId;
	private long _foodCategoryId;

	@DeleteAfterTestRun
	private Group _group1;

	@DeleteAfterTestRun
	private Group _group2;

	private long _healthCategoryId;
	private long _sportCategoryId;
	private long _travelCategoryId;
	private long _vocabularyId;

}