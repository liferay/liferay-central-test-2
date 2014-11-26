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

package com.liferay.portlet.shopping.service.persistence;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.AggregateTestRule;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.LiferayIntegrationTestRule;
import com.liferay.portal.test.PersistenceTestRule;
import com.liferay.portal.test.TransactionalTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

import com.liferay.portlet.shopping.NoSuchCategoryException;
import com.liferay.portlet.shopping.model.ShoppingCategory;
import com.liferay.portlet.shopping.model.impl.ShoppingCategoryModelImpl;
import com.liferay.portlet.shopping.service.ShoppingCategoryLocalServiceUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @generated
 */
public class ShoppingCategoryPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@After
	public void tearDown() throws Exception {
		Iterator<ShoppingCategory> iterator = _shoppingCategories.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ShoppingCategory shoppingCategory = _persistence.create(pk);

		Assert.assertNotNull(shoppingCategory);

		Assert.assertEquals(shoppingCategory.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ShoppingCategory newShoppingCategory = addShoppingCategory();

		_persistence.remove(newShoppingCategory);

		ShoppingCategory existingShoppingCategory = _persistence.fetchByPrimaryKey(newShoppingCategory.getPrimaryKey());

		Assert.assertNull(existingShoppingCategory);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addShoppingCategory();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ShoppingCategory newShoppingCategory = _persistence.create(pk);

		newShoppingCategory.setGroupId(RandomTestUtil.nextLong());

		newShoppingCategory.setCompanyId(RandomTestUtil.nextLong());

		newShoppingCategory.setUserId(RandomTestUtil.nextLong());

		newShoppingCategory.setUserName(RandomTestUtil.randomString());

		newShoppingCategory.setCreateDate(RandomTestUtil.nextDate());

		newShoppingCategory.setModifiedDate(RandomTestUtil.nextDate());

		newShoppingCategory.setParentCategoryId(RandomTestUtil.nextLong());

		newShoppingCategory.setName(RandomTestUtil.randomString());

		newShoppingCategory.setDescription(RandomTestUtil.randomString());

		_shoppingCategories.add(_persistence.update(newShoppingCategory));

		ShoppingCategory existingShoppingCategory = _persistence.findByPrimaryKey(newShoppingCategory.getPrimaryKey());

		Assert.assertEquals(existingShoppingCategory.getCategoryId(),
			newShoppingCategory.getCategoryId());
		Assert.assertEquals(existingShoppingCategory.getGroupId(),
			newShoppingCategory.getGroupId());
		Assert.assertEquals(existingShoppingCategory.getCompanyId(),
			newShoppingCategory.getCompanyId());
		Assert.assertEquals(existingShoppingCategory.getUserId(),
			newShoppingCategory.getUserId());
		Assert.assertEquals(existingShoppingCategory.getUserName(),
			newShoppingCategory.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingShoppingCategory.getCreateDate()),
			Time.getShortTimestamp(newShoppingCategory.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingShoppingCategory.getModifiedDate()),
			Time.getShortTimestamp(newShoppingCategory.getModifiedDate()));
		Assert.assertEquals(existingShoppingCategory.getParentCategoryId(),
			newShoppingCategory.getParentCategoryId());
		Assert.assertEquals(existingShoppingCategory.getName(),
			newShoppingCategory.getName());
		Assert.assertEquals(existingShoppingCategory.getDescription(),
			newShoppingCategory.getDescription());
	}

	@Test
	public void testCountByGroupId() {
		try {
			_persistence.countByGroupId(RandomTestUtil.nextLong());

			_persistence.countByGroupId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_P() {
		try {
			_persistence.countByG_P(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByG_P(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_N() {
		try {
			_persistence.countByG_N(RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByG_N(0L, StringPool.NULL);

			_persistence.countByG_N(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ShoppingCategory newShoppingCategory = addShoppingCategory();

		ShoppingCategory existingShoppingCategory = _persistence.findByPrimaryKey(newShoppingCategory.getPrimaryKey());

		Assert.assertEquals(existingShoppingCategory, newShoppingCategory);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchCategoryException");
		}
		catch (NoSuchCategoryException nsee) {
		}
	}

	@Test
	public void testFindAll() throws Exception {
		try {
			_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFilterFindByGroupId() throws Exception {
		try {
			_persistence.filterFindByGroupId(0, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected OrderByComparator<ShoppingCategory> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("ShoppingCategory",
			"categoryId", true, "groupId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"parentCategoryId", true, "name", true, "description", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ShoppingCategory newShoppingCategory = addShoppingCategory();

		ShoppingCategory existingShoppingCategory = _persistence.fetchByPrimaryKey(newShoppingCategory.getPrimaryKey());

		Assert.assertEquals(existingShoppingCategory, newShoppingCategory);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ShoppingCategory missingShoppingCategory = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingShoppingCategory);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		ShoppingCategory newShoppingCategory1 = addShoppingCategory();
		ShoppingCategory newShoppingCategory2 = addShoppingCategory();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newShoppingCategory1.getPrimaryKey());
		primaryKeys.add(newShoppingCategory2.getPrimaryKey());

		Map<Serializable, ShoppingCategory> shoppingCategories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, shoppingCategories.size());
		Assert.assertEquals(newShoppingCategory1,
			shoppingCategories.get(newShoppingCategory1.getPrimaryKey()));
		Assert.assertEquals(newShoppingCategory2,
			shoppingCategories.get(newShoppingCategory2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ShoppingCategory> shoppingCategories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(shoppingCategories.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		ShoppingCategory newShoppingCategory = addShoppingCategory();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newShoppingCategory.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ShoppingCategory> shoppingCategories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, shoppingCategories.size());
		Assert.assertEquals(newShoppingCategory,
			shoppingCategories.get(newShoppingCategory.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ShoppingCategory> shoppingCategories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(shoppingCategories.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		ShoppingCategory newShoppingCategory = addShoppingCategory();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newShoppingCategory.getPrimaryKey());

		Map<Serializable, ShoppingCategory> shoppingCategories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, shoppingCategories.size());
		Assert.assertEquals(newShoppingCategory,
			shoppingCategories.get(newShoppingCategory.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = ShoppingCategoryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					ShoppingCategory shoppingCategory = (ShoppingCategory)object;

					Assert.assertNotNull(shoppingCategory);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ShoppingCategory newShoppingCategory = addShoppingCategory();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingCategory.class,
				ShoppingCategory.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("categoryId",
				newShoppingCategory.getCategoryId()));

		List<ShoppingCategory> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ShoppingCategory existingShoppingCategory = result.get(0);

		Assert.assertEquals(existingShoppingCategory, newShoppingCategory);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingCategory.class,
				ShoppingCategory.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("categoryId",
				RandomTestUtil.nextLong()));

		List<ShoppingCategory> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ShoppingCategory newShoppingCategory = addShoppingCategory();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingCategory.class,
				ShoppingCategory.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("categoryId"));

		Object newCategoryId = newShoppingCategory.getCategoryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("categoryId",
				new Object[] { newCategoryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCategoryId = result.get(0);

		Assert.assertEquals(existingCategoryId, newCategoryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingCategory.class,
				ShoppingCategory.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("categoryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("categoryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		ShoppingCategory newShoppingCategory = addShoppingCategory();

		_persistence.clearCache();

		ShoppingCategoryModelImpl existingShoppingCategoryModelImpl = (ShoppingCategoryModelImpl)_persistence.findByPrimaryKey(newShoppingCategory.getPrimaryKey());

		Assert.assertEquals(existingShoppingCategoryModelImpl.getGroupId(),
			existingShoppingCategoryModelImpl.getOriginalGroupId());
		Assert.assertTrue(Validator.equals(
				existingShoppingCategoryModelImpl.getName(),
				existingShoppingCategoryModelImpl.getOriginalName()));
	}

	protected ShoppingCategory addShoppingCategory() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ShoppingCategory shoppingCategory = _persistence.create(pk);

		shoppingCategory.setGroupId(RandomTestUtil.nextLong());

		shoppingCategory.setCompanyId(RandomTestUtil.nextLong());

		shoppingCategory.setUserId(RandomTestUtil.nextLong());

		shoppingCategory.setUserName(RandomTestUtil.randomString());

		shoppingCategory.setCreateDate(RandomTestUtil.nextDate());

		shoppingCategory.setModifiedDate(RandomTestUtil.nextDate());

		shoppingCategory.setParentCategoryId(RandomTestUtil.nextLong());

		shoppingCategory.setName(RandomTestUtil.randomString());

		shoppingCategory.setDescription(RandomTestUtil.randomString());

		_shoppingCategories.add(_persistence.update(shoppingCategory));

		return shoppingCategory;
	}

	private List<ShoppingCategory> _shoppingCategories = new ArrayList<ShoppingCategory>();
	private ShoppingCategoryPersistence _persistence = ShoppingCategoryUtil.getPersistence();
}