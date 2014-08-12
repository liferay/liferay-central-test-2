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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.test.TransactionalTestRule;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

import com.liferay.portlet.shopping.NoSuchCouponException;
import com.liferay.portlet.shopping.model.ShoppingCoupon;
import com.liferay.portlet.shopping.model.impl.ShoppingCouponModelImpl;
import com.liferay.portlet.shopping.service.ShoppingCouponLocalServiceUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import org.junit.runner.RunWith;

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
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class ShoppingCouponPersistenceTest {
	@ClassRule
	public static TransactionalTestRule transactionalTestRule = new TransactionalTestRule(Propagation.REQUIRED);

	@BeforeClass
	public static void setupClass() throws TemplateException {
		try {
			DBUpgrader.upgrade();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		TemplateManagerUtil.init();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ShoppingCoupon> iterator = _shoppingCoupons.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ShoppingCoupon shoppingCoupon = _persistence.create(pk);

		Assert.assertNotNull(shoppingCoupon);

		Assert.assertEquals(shoppingCoupon.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ShoppingCoupon newShoppingCoupon = addShoppingCoupon();

		_persistence.remove(newShoppingCoupon);

		ShoppingCoupon existingShoppingCoupon = _persistence.fetchByPrimaryKey(newShoppingCoupon.getPrimaryKey());

		Assert.assertNull(existingShoppingCoupon);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addShoppingCoupon();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ShoppingCoupon newShoppingCoupon = _persistence.create(pk);

		newShoppingCoupon.setGroupId(RandomTestUtil.nextLong());

		newShoppingCoupon.setCompanyId(RandomTestUtil.nextLong());

		newShoppingCoupon.setUserId(RandomTestUtil.nextLong());

		newShoppingCoupon.setUserName(RandomTestUtil.randomString());

		newShoppingCoupon.setCreateDate(RandomTestUtil.nextDate());

		newShoppingCoupon.setModifiedDate(RandomTestUtil.nextDate());

		newShoppingCoupon.setCode(RandomTestUtil.randomString());

		newShoppingCoupon.setName(RandomTestUtil.randomString());

		newShoppingCoupon.setDescription(RandomTestUtil.randomString());

		newShoppingCoupon.setStartDate(RandomTestUtil.nextDate());

		newShoppingCoupon.setEndDate(RandomTestUtil.nextDate());

		newShoppingCoupon.setActive(RandomTestUtil.randomBoolean());

		newShoppingCoupon.setLimitCategories(RandomTestUtil.randomString());

		newShoppingCoupon.setLimitSkus(RandomTestUtil.randomString());

		newShoppingCoupon.setMinOrder(RandomTestUtil.nextDouble());

		newShoppingCoupon.setDiscount(RandomTestUtil.nextDouble());

		newShoppingCoupon.setDiscountType(RandomTestUtil.randomString());

		_shoppingCoupons.add(_persistence.update(newShoppingCoupon));

		ShoppingCoupon existingShoppingCoupon = _persistence.findByPrimaryKey(newShoppingCoupon.getPrimaryKey());

		Assert.assertEquals(existingShoppingCoupon.getCouponId(),
			newShoppingCoupon.getCouponId());
		Assert.assertEquals(existingShoppingCoupon.getGroupId(),
			newShoppingCoupon.getGroupId());
		Assert.assertEquals(existingShoppingCoupon.getCompanyId(),
			newShoppingCoupon.getCompanyId());
		Assert.assertEquals(existingShoppingCoupon.getUserId(),
			newShoppingCoupon.getUserId());
		Assert.assertEquals(existingShoppingCoupon.getUserName(),
			newShoppingCoupon.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingShoppingCoupon.getCreateDate()),
			Time.getShortTimestamp(newShoppingCoupon.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingShoppingCoupon.getModifiedDate()),
			Time.getShortTimestamp(newShoppingCoupon.getModifiedDate()));
		Assert.assertEquals(existingShoppingCoupon.getCode(),
			newShoppingCoupon.getCode());
		Assert.assertEquals(existingShoppingCoupon.getName(),
			newShoppingCoupon.getName());
		Assert.assertEquals(existingShoppingCoupon.getDescription(),
			newShoppingCoupon.getDescription());
		Assert.assertEquals(Time.getShortTimestamp(
				existingShoppingCoupon.getStartDate()),
			Time.getShortTimestamp(newShoppingCoupon.getStartDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingShoppingCoupon.getEndDate()),
			Time.getShortTimestamp(newShoppingCoupon.getEndDate()));
		Assert.assertEquals(existingShoppingCoupon.getActive(),
			newShoppingCoupon.getActive());
		Assert.assertEquals(existingShoppingCoupon.getLimitCategories(),
			newShoppingCoupon.getLimitCategories());
		Assert.assertEquals(existingShoppingCoupon.getLimitSkus(),
			newShoppingCoupon.getLimitSkus());
		AssertUtils.assertEquals(existingShoppingCoupon.getMinOrder(),
			newShoppingCoupon.getMinOrder());
		AssertUtils.assertEquals(existingShoppingCoupon.getDiscount(),
			newShoppingCoupon.getDiscount());
		Assert.assertEquals(existingShoppingCoupon.getDiscountType(),
			newShoppingCoupon.getDiscountType());
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
	public void testCountByCode() {
		try {
			_persistence.countByCode(StringPool.BLANK);

			_persistence.countByCode(StringPool.NULL);

			_persistence.countByCode((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ShoppingCoupon newShoppingCoupon = addShoppingCoupon();

		ShoppingCoupon existingShoppingCoupon = _persistence.findByPrimaryKey(newShoppingCoupon.getPrimaryKey());

		Assert.assertEquals(existingShoppingCoupon, newShoppingCoupon);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchCouponException");
		}
		catch (NoSuchCouponException nsee) {
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

	protected OrderByComparator<ShoppingCoupon> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("ShoppingCoupon",
			"couponId", true, "groupId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"code", true, "name", true, "description", true, "startDate", true,
			"endDate", true, "active", true, "limitCategories", true,
			"limitSkus", true, "minOrder", true, "discount", true,
			"discountType", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ShoppingCoupon newShoppingCoupon = addShoppingCoupon();

		ShoppingCoupon existingShoppingCoupon = _persistence.fetchByPrimaryKey(newShoppingCoupon.getPrimaryKey());

		Assert.assertEquals(existingShoppingCoupon, newShoppingCoupon);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ShoppingCoupon missingShoppingCoupon = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingShoppingCoupon);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		ShoppingCoupon newShoppingCoupon1 = addShoppingCoupon();
		ShoppingCoupon newShoppingCoupon2 = addShoppingCoupon();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newShoppingCoupon1.getPrimaryKey());
		primaryKeys.add(newShoppingCoupon2.getPrimaryKey());

		Map<Serializable, ShoppingCoupon> shoppingCoupons = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, shoppingCoupons.size());
		Assert.assertEquals(newShoppingCoupon1,
			shoppingCoupons.get(newShoppingCoupon1.getPrimaryKey()));
		Assert.assertEquals(newShoppingCoupon2,
			shoppingCoupons.get(newShoppingCoupon2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ShoppingCoupon> shoppingCoupons = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(shoppingCoupons.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		ShoppingCoupon newShoppingCoupon = addShoppingCoupon();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newShoppingCoupon.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ShoppingCoupon> shoppingCoupons = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, shoppingCoupons.size());
		Assert.assertEquals(newShoppingCoupon,
			shoppingCoupons.get(newShoppingCoupon.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ShoppingCoupon> shoppingCoupons = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(shoppingCoupons.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		ShoppingCoupon newShoppingCoupon = addShoppingCoupon();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newShoppingCoupon.getPrimaryKey());

		Map<Serializable, ShoppingCoupon> shoppingCoupons = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, shoppingCoupons.size());
		Assert.assertEquals(newShoppingCoupon,
			shoppingCoupons.get(newShoppingCoupon.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = ShoppingCouponLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					ShoppingCoupon shoppingCoupon = (ShoppingCoupon)object;

					Assert.assertNotNull(shoppingCoupon);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ShoppingCoupon newShoppingCoupon = addShoppingCoupon();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingCoupon.class,
				ShoppingCoupon.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("couponId",
				newShoppingCoupon.getCouponId()));

		List<ShoppingCoupon> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ShoppingCoupon existingShoppingCoupon = result.get(0);

		Assert.assertEquals(existingShoppingCoupon, newShoppingCoupon);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingCoupon.class,
				ShoppingCoupon.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("couponId",
				RandomTestUtil.nextLong()));

		List<ShoppingCoupon> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ShoppingCoupon newShoppingCoupon = addShoppingCoupon();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingCoupon.class,
				ShoppingCoupon.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("couponId"));

		Object newCouponId = newShoppingCoupon.getCouponId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("couponId",
				new Object[] { newCouponId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCouponId = result.get(0);

		Assert.assertEquals(existingCouponId, newCouponId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingCoupon.class,
				ShoppingCoupon.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("couponId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("couponId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		ShoppingCoupon newShoppingCoupon = addShoppingCoupon();

		_persistence.clearCache();

		ShoppingCouponModelImpl existingShoppingCouponModelImpl = (ShoppingCouponModelImpl)_persistence.findByPrimaryKey(newShoppingCoupon.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingShoppingCouponModelImpl.getCode(),
				existingShoppingCouponModelImpl.getOriginalCode()));
	}

	protected ShoppingCoupon addShoppingCoupon() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ShoppingCoupon shoppingCoupon = _persistence.create(pk);

		shoppingCoupon.setGroupId(RandomTestUtil.nextLong());

		shoppingCoupon.setCompanyId(RandomTestUtil.nextLong());

		shoppingCoupon.setUserId(RandomTestUtil.nextLong());

		shoppingCoupon.setUserName(RandomTestUtil.randomString());

		shoppingCoupon.setCreateDate(RandomTestUtil.nextDate());

		shoppingCoupon.setModifiedDate(RandomTestUtil.nextDate());

		shoppingCoupon.setCode(RandomTestUtil.randomString());

		shoppingCoupon.setName(RandomTestUtil.randomString());

		shoppingCoupon.setDescription(RandomTestUtil.randomString());

		shoppingCoupon.setStartDate(RandomTestUtil.nextDate());

		shoppingCoupon.setEndDate(RandomTestUtil.nextDate());

		shoppingCoupon.setActive(RandomTestUtil.randomBoolean());

		shoppingCoupon.setLimitCategories(RandomTestUtil.randomString());

		shoppingCoupon.setLimitSkus(RandomTestUtil.randomString());

		shoppingCoupon.setMinOrder(RandomTestUtil.nextDouble());

		shoppingCoupon.setDiscount(RandomTestUtil.nextDouble());

		shoppingCoupon.setDiscountType(RandomTestUtil.randomString());

		_shoppingCoupons.add(_persistence.update(shoppingCoupon));

		return shoppingCoupon;
	}

	private static Log _log = LogFactoryUtil.getLog(ShoppingCouponPersistenceTest.class);
	private List<ShoppingCoupon> _shoppingCoupons = new ArrayList<ShoppingCoupon>();
	private ModelListener<ShoppingCoupon>[] _modelListeners;
	private ShoppingCouponPersistence _persistence = ShoppingCouponUtil.getPersistence();
}