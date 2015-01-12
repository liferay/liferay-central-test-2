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

package com.liferay.portlet.messageboards.service;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.AggregateTestRule;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.LiferayIntegrationTestRule;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.util.test.MBTestUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Igor Beslic
 * @author Roberto Díaz
 */
public class MBCategoryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetCategoriesCountWithExcludedCategories()
		throws Exception {

		int initialCategoriesCount =
			MBCategoryLocalServiceUtil.getCategoriesCount(
				_group.getGroupId(),
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		MBTestUtil.addCategory(_group.getGroupId());

		MBCategory excludedCategory1 = MBTestUtil.addCategory(
			_group.getGroupId());
		MBCategory excludedCategory2 = MBTestUtil.addCategory(
			_group.getGroupId());

		Assert.assertEquals(
			initialCategoriesCount + 3,
			MBCategoryLocalServiceUtil.getCategoriesCount(
				_group.getGroupId(),
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				WorkflowConstants.STATUS_ANY));
		Assert.assertEquals(
			initialCategoriesCount + 1,
			MBCategoryLocalServiceUtil.getCategoriesCount(
				_group.getGroupId(),
				new long[] {
					excludedCategory1.getCategoryId(),
					excludedCategory2.getCategoryId()
				},
				new long[] {MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID},
				WorkflowConstants.STATUS_ANY));
	}

	@Test
	public void testGetCategoriesCountWithExcludedCategory() throws Exception {
		int initialCategoriesCount =
			MBCategoryLocalServiceUtil.getCategoriesCount(
				_group.getGroupId(),
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		MBTestUtil.addCategory(_group.getGroupId());
		MBTestUtil.addCategory(_group.getGroupId());

		MBCategory excludedCategory = MBTestUtil.addCategory(
			_group.getGroupId());

		Assert.assertEquals(
			initialCategoriesCount + 3,
			MBCategoryLocalServiceUtil.getCategoriesCount(
				_group.getGroupId(),
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				WorkflowConstants.STATUS_ANY));
		Assert.assertEquals(
			initialCategoriesCount + 2,
			MBCategoryLocalServiceUtil.getCategoriesCount(
				_group.getGroupId(), excludedCategory.getCategoryId(),
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				WorkflowConstants.STATUS_ANY));
	}

	@Test
	public void testGetCategoriesCountWithStatusApprovedAndExcludedCategories()
		throws Exception {

		int initialCategoriesCount =
			MBCategoryLocalServiceUtil.getCategoriesCount(
				_group.getGroupId(),
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				WorkflowConstants.STATUS_APPROVED);

		MBTestUtil.addCategory(_group.getGroupId());

		MBCategory excludedCategory1 = MBTestUtil.addCategory(
			_group.getGroupId());
		MBCategory excludedCategory2 = MBTestUtil.addCategory(
			_group.getGroupId());

		MBCategory draftCategory = MBTestUtil.addCategory(_group.getGroupId());

		MBCategoryLocalServiceUtil.updateStatus(
			draftCategory.getUserId(), draftCategory.getCategoryId(),
			WorkflowConstants.STATUS_DRAFT);

		Assert.assertEquals(
			initialCategoriesCount + 3,
			MBCategoryLocalServiceUtil.getCategoriesCount(
				_group.getGroupId(),
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				WorkflowConstants.STATUS_APPROVED));
		Assert.assertEquals(
			initialCategoriesCount + 1,
			MBCategoryLocalServiceUtil.getCategoriesCount(
				_group.getGroupId(),
				new long[] {
					excludedCategory1.getCategoryId(),
					excludedCategory2.getCategoryId()
				},
				new long[] {MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID},
				WorkflowConstants.STATUS_APPROVED));
	}

	@Test
	public void testGetCategoriesCountWithStatusApprovedAndExcludedCategory()
		throws Exception {

		int initialCategoriesCount =
			MBCategoryLocalServiceUtil.getCategoriesCount(
				_group.getGroupId(),
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				WorkflowConstants.STATUS_APPROVED);

		MBTestUtil.addCategory(_group.getGroupId());

		MBCategory excludedCategory = MBTestUtil.addCategory(
			_group.getGroupId());

		MBCategory draftCategory = MBTestUtil.addCategory(_group.getGroupId());

		MBCategoryLocalServiceUtil.updateStatus(
			draftCategory.getUserId(), draftCategory.getCategoryId(),
			WorkflowConstants.STATUS_DRAFT);

		Assert.assertEquals(
			initialCategoriesCount + 2,
			MBCategoryLocalServiceUtil.getCategoriesCount(
				_group.getGroupId(),
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				WorkflowConstants.STATUS_APPROVED));
		Assert.assertEquals(
			initialCategoriesCount + 1,
			MBCategoryLocalServiceUtil.getCategoriesCount(
				_group.getGroupId(), excludedCategory.getCategoryId(),
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				WorkflowConstants.STATUS_APPROVED));
	}

	@Test
	public void testGetCategoriesWithExcludedCategories() throws Exception {
		List<MBCategory> initialCategories =
			MBCategoryLocalServiceUtil.getCategories(_group.getGroupId());

		List<MBCategory> expectedCategories = new ArrayList<>();

		expectedCategories.addAll(initialCategories);
		expectedCategories.add(MBTestUtil.addCategory(_group.getGroupId()));

		MBCategory excludedCategory1 = MBTestUtil.addCategory(
			_group.getGroupId());

		expectedCategories.add(excludedCategory1);

		MBCategory excludedCategory2 = MBTestUtil.addCategory(
			_group.getGroupId());

		expectedCategories.add(excludedCategory2);

		List<MBCategory> categories = MBCategoryLocalServiceUtil.getCategories(
			_group.getGroupId());

		AssertUtils.assertEquals(expectedCategories, categories);

		expectedCategories.remove(excludedCategory1);
		expectedCategories.remove(excludedCategory2);

		categories = MBCategoryLocalServiceUtil.getCategories(
			_group.getGroupId(),
			new long[] {
				excludedCategory1.getCategoryId(),
				excludedCategory2.getCategoryId()
			},
			new long[] {MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID},
			WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		AssertUtils.assertEquals(expectedCategories, categories);
	}

	@Test
	public void testGetCategoriesWithExcludedCategory() throws Exception {
		List<MBCategory> initialCategories =
			MBCategoryLocalServiceUtil.getCategories(_group.getGroupId());

		List<MBCategory> expectedCategories = new ArrayList<>();

		expectedCategories.addAll(initialCategories);
		expectedCategories.add(MBTestUtil.addCategory(_group.getGroupId()));
		expectedCategories.add(MBTestUtil.addCategory(_group.getGroupId()));

		MBCategory excludedCategory = MBTestUtil.addCategory(
			_group.getGroupId());

		expectedCategories.add(excludedCategory);

		List<MBCategory> categories = MBCategoryLocalServiceUtil.getCategories(
			_group.getGroupId());

		AssertUtils.assertEquals(expectedCategories, categories);

		expectedCategories.remove(excludedCategory);

		categories = MBCategoryLocalServiceUtil.getCategories(
			_group.getGroupId(), excludedCategory.getCategoryId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		AssertUtils.assertEquals(expectedCategories, categories);
	}

	@Test
	public void testGetCategoriesWithStatusApprovedAndExcludedCategories()
		throws Exception {

		List<MBCategory> initialCategories =
			MBCategoryLocalServiceUtil.getCategories(
				_group.getGroupId(),  WorkflowConstants.STATUS_APPROVED);

		List<MBCategory> expectedCategories = new ArrayList<>();

		expectedCategories.addAll(initialCategories);
		expectedCategories.add(MBTestUtil.addCategory(_group.getGroupId()));

		MBCategory excludedCategory1 = MBTestUtil.addCategory(
			_group.getGroupId());

		expectedCategories.add(excludedCategory1);

		MBCategory excludedCategory2 = MBTestUtil.addCategory(
			_group.getGroupId());

		expectedCategories.add(excludedCategory2);

		MBCategory draftCategory = MBTestUtil.addCategory(_group.getGroupId());

		MBCategoryLocalServiceUtil.updateStatus(
			draftCategory.getUserId(), draftCategory.getCategoryId(),
			WorkflowConstants.STATUS_DRAFT);

		List<MBCategory> categories = MBCategoryLocalServiceUtil.getCategories(
			_group.getGroupId(), WorkflowConstants.STATUS_APPROVED);

		AssertUtils.assertEquals(expectedCategories, categories);

		expectedCategories.remove(excludedCategory1);
		expectedCategories.remove(excludedCategory2);

		categories = MBCategoryLocalServiceUtil.getCategories(
			_group.getGroupId(),
			new long[] {
				excludedCategory1.getCategoryId(),
				excludedCategory2.getCategoryId()
			},
			new long[] {MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID},
			WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		AssertUtils.assertEquals(expectedCategories, categories);
	}

	@Test
	public void testGetCategoriesWithStatusApprovedAndExcludedCategory()
		throws Exception {

		List<MBCategory> initialCategories =
			MBCategoryLocalServiceUtil.getCategories(
				_group.getGroupId(), WorkflowConstants.STATUS_APPROVED);

		List<MBCategory> expectedCategories = new ArrayList<>();

		expectedCategories.addAll(initialCategories);
		expectedCategories.add(MBTestUtil.addCategory(_group.getGroupId()));

		MBCategory excludedCategory = MBTestUtil.addCategory(
			_group.getGroupId());

		expectedCategories.add(excludedCategory);

		MBCategory draftCategory = MBTestUtil.addCategory(_group.getGroupId());

		MBCategoryLocalServiceUtil.updateStatus(
			draftCategory.getUserId(), draftCategory.getCategoryId(),
			WorkflowConstants.STATUS_DRAFT);

		List<MBCategory> categories = MBCategoryLocalServiceUtil.getCategories(
			_group.getGroupId(), WorkflowConstants.STATUS_APPROVED);

		AssertUtils.assertEquals(expectedCategories, categories);

		expectedCategories.remove(excludedCategory);

		categories = MBCategoryLocalServiceUtil.getCategories(
			_group.getGroupId(), excludedCategory.getCategoryId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		AssertUtils.assertEquals(expectedCategories, categories);
	}

	@Test
	public void testGetParentCategory() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		MBCategory parentCategory = MBCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			RandomTestUtil.randomString(), StringPool.BLANK, serviceContext);

		MBCategory category = MBCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(), parentCategory.getCategoryId(),
			RandomTestUtil.randomString(), StringPool.BLANK, serviceContext);

		Assert.assertNotNull(category.getParentCategory());
		Assert.assertNull(parentCategory.getParentCategory());
	}

	@Test
	public void testGetParentDiscussionCategory() throws Exception {
		MBCategory discussionCategory = MBCategoryLocalServiceUtil.getCategory(
			MBCategoryConstants.DISCUSSION_CATEGORY_ID);

		Assert.assertNotNull(discussionCategory);
		Assert.assertNull(discussionCategory.getParentCategory());
	}

	@DeleteAfterTestRun
	private Group _group;

}