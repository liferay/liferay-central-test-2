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

package com.liferay.portlet.social.service.persistence;

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
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.test.TransactionalTestRule;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

import com.liferay.portlet.social.NoSuchActivityCounterException;
import com.liferay.portlet.social.model.SocialActivityCounter;
import com.liferay.portlet.social.model.impl.SocialActivityCounterModelImpl;
import com.liferay.portlet.social.service.SocialActivityCounterLocalServiceUtil;

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
public class SocialActivityCounterPersistenceTest {
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
		Iterator<SocialActivityCounter> iterator = _socialActivityCounters.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SocialActivityCounter socialActivityCounter = _persistence.create(pk);

		Assert.assertNotNull(socialActivityCounter);

		Assert.assertEquals(socialActivityCounter.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SocialActivityCounter newSocialActivityCounter = addSocialActivityCounter();

		_persistence.remove(newSocialActivityCounter);

		SocialActivityCounter existingSocialActivityCounter = _persistence.fetchByPrimaryKey(newSocialActivityCounter.getPrimaryKey());

		Assert.assertNull(existingSocialActivityCounter);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSocialActivityCounter();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SocialActivityCounter newSocialActivityCounter = _persistence.create(pk);

		newSocialActivityCounter.setGroupId(RandomTestUtil.nextLong());

		newSocialActivityCounter.setCompanyId(RandomTestUtil.nextLong());

		newSocialActivityCounter.setClassNameId(RandomTestUtil.nextLong());

		newSocialActivityCounter.setClassPK(RandomTestUtil.nextLong());

		newSocialActivityCounter.setName(RandomTestUtil.randomString());

		newSocialActivityCounter.setOwnerType(RandomTestUtil.nextInt());

		newSocialActivityCounter.setCurrentValue(RandomTestUtil.nextInt());

		newSocialActivityCounter.setTotalValue(RandomTestUtil.nextInt());

		newSocialActivityCounter.setGraceValue(RandomTestUtil.nextInt());

		newSocialActivityCounter.setStartPeriod(RandomTestUtil.nextInt());

		newSocialActivityCounter.setEndPeriod(RandomTestUtil.nextInt());

		newSocialActivityCounter.setActive(RandomTestUtil.randomBoolean());

		_socialActivityCounters.add(_persistence.update(
				newSocialActivityCounter));

		SocialActivityCounter existingSocialActivityCounter = _persistence.findByPrimaryKey(newSocialActivityCounter.getPrimaryKey());

		Assert.assertEquals(existingSocialActivityCounter.getActivityCounterId(),
			newSocialActivityCounter.getActivityCounterId());
		Assert.assertEquals(existingSocialActivityCounter.getGroupId(),
			newSocialActivityCounter.getGroupId());
		Assert.assertEquals(existingSocialActivityCounter.getCompanyId(),
			newSocialActivityCounter.getCompanyId());
		Assert.assertEquals(existingSocialActivityCounter.getClassNameId(),
			newSocialActivityCounter.getClassNameId());
		Assert.assertEquals(existingSocialActivityCounter.getClassPK(),
			newSocialActivityCounter.getClassPK());
		Assert.assertEquals(existingSocialActivityCounter.getName(),
			newSocialActivityCounter.getName());
		Assert.assertEquals(existingSocialActivityCounter.getOwnerType(),
			newSocialActivityCounter.getOwnerType());
		Assert.assertEquals(existingSocialActivityCounter.getCurrentValue(),
			newSocialActivityCounter.getCurrentValue());
		Assert.assertEquals(existingSocialActivityCounter.getTotalValue(),
			newSocialActivityCounter.getTotalValue());
		Assert.assertEquals(existingSocialActivityCounter.getGraceValue(),
			newSocialActivityCounter.getGraceValue());
		Assert.assertEquals(existingSocialActivityCounter.getStartPeriod(),
			newSocialActivityCounter.getStartPeriod());
		Assert.assertEquals(existingSocialActivityCounter.getEndPeriod(),
			newSocialActivityCounter.getEndPeriod());
		Assert.assertEquals(existingSocialActivityCounter.getActive(),
			newSocialActivityCounter.getActive());
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
	public void testCountByC_C() {
		try {
			_persistence.countByC_C(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByC_C(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_C_C_O() {
		try {
			_persistence.countByG_C_C_O(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByG_C_C_O(0L, 0L, 0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_C_C_N_O_S() {
		try {
			_persistence.countByG_C_C_N_O_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
				StringPool.BLANK, RandomTestUtil.nextInt(),
				RandomTestUtil.nextInt());

			_persistence.countByG_C_C_N_O_S(0L, 0L, 0L, StringPool.NULL, 0, 0);

			_persistence.countByG_C_C_N_O_S(0L, 0L, 0L, (String)null, 0, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_C_C_N_O_E() {
		try {
			_persistence.countByG_C_C_N_O_E(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
				StringPool.BLANK, RandomTestUtil.nextInt(),
				RandomTestUtil.nextInt());

			_persistence.countByG_C_C_N_O_E(0L, 0L, 0L, StringPool.NULL, 0, 0);

			_persistence.countByG_C_C_N_O_E(0L, 0L, 0L, (String)null, 0, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SocialActivityCounter newSocialActivityCounter = addSocialActivityCounter();

		SocialActivityCounter existingSocialActivityCounter = _persistence.findByPrimaryKey(newSocialActivityCounter.getPrimaryKey());

		Assert.assertEquals(existingSocialActivityCounter,
			newSocialActivityCounter);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchActivityCounterException");
		}
		catch (NoSuchActivityCounterException nsee) {
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

	protected OrderByComparator<SocialActivityCounter> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("SocialActivityCounter",
			"activityCounterId", true, "groupId", true, "companyId", true,
			"classNameId", true, "classPK", true, "name", true, "ownerType",
			true, "currentValue", true, "totalValue", true, "graceValue", true,
			"startPeriod", true, "endPeriod", true, "active", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SocialActivityCounter newSocialActivityCounter = addSocialActivityCounter();

		SocialActivityCounter existingSocialActivityCounter = _persistence.fetchByPrimaryKey(newSocialActivityCounter.getPrimaryKey());

		Assert.assertEquals(existingSocialActivityCounter,
			newSocialActivityCounter);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SocialActivityCounter missingSocialActivityCounter = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSocialActivityCounter);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		SocialActivityCounter newSocialActivityCounter1 = addSocialActivityCounter();
		SocialActivityCounter newSocialActivityCounter2 = addSocialActivityCounter();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSocialActivityCounter1.getPrimaryKey());
		primaryKeys.add(newSocialActivityCounter2.getPrimaryKey());

		Map<Serializable, SocialActivityCounter> socialActivityCounters = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, socialActivityCounters.size());
		Assert.assertEquals(newSocialActivityCounter1,
			socialActivityCounters.get(
				newSocialActivityCounter1.getPrimaryKey()));
		Assert.assertEquals(newSocialActivityCounter2,
			socialActivityCounters.get(
				newSocialActivityCounter2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, SocialActivityCounter> socialActivityCounters = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(socialActivityCounters.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		SocialActivityCounter newSocialActivityCounter = addSocialActivityCounter();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSocialActivityCounter.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, SocialActivityCounter> socialActivityCounters = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, socialActivityCounters.size());
		Assert.assertEquals(newSocialActivityCounter,
			socialActivityCounters.get(newSocialActivityCounter.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, SocialActivityCounter> socialActivityCounters = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(socialActivityCounters.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		SocialActivityCounter newSocialActivityCounter = addSocialActivityCounter();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSocialActivityCounter.getPrimaryKey());

		Map<Serializable, SocialActivityCounter> socialActivityCounters = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, socialActivityCounters.size());
		Assert.assertEquals(newSocialActivityCounter,
			socialActivityCounters.get(newSocialActivityCounter.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = SocialActivityCounterLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					SocialActivityCounter socialActivityCounter = (SocialActivityCounter)object;

					Assert.assertNotNull(socialActivityCounter);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SocialActivityCounter newSocialActivityCounter = addSocialActivityCounter();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivityCounter.class,
				SocialActivityCounter.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("activityCounterId",
				newSocialActivityCounter.getActivityCounterId()));

		List<SocialActivityCounter> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		SocialActivityCounter existingSocialActivityCounter = result.get(0);

		Assert.assertEquals(existingSocialActivityCounter,
			newSocialActivityCounter);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivityCounter.class,
				SocialActivityCounter.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("activityCounterId",
				RandomTestUtil.nextLong()));

		List<SocialActivityCounter> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SocialActivityCounter newSocialActivityCounter = addSocialActivityCounter();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivityCounter.class,
				SocialActivityCounter.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"activityCounterId"));

		Object newActivityCounterId = newSocialActivityCounter.getActivityCounterId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("activityCounterId",
				new Object[] { newActivityCounterId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingActivityCounterId = result.get(0);

		Assert.assertEquals(existingActivityCounterId, newActivityCounterId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivityCounter.class,
				SocialActivityCounter.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"activityCounterId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("activityCounterId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		SocialActivityCounter newSocialActivityCounter = addSocialActivityCounter();

		_persistence.clearCache();

		SocialActivityCounterModelImpl existingSocialActivityCounterModelImpl = (SocialActivityCounterModelImpl)_persistence.findByPrimaryKey(newSocialActivityCounter.getPrimaryKey());

		Assert.assertEquals(existingSocialActivityCounterModelImpl.getGroupId(),
			existingSocialActivityCounterModelImpl.getOriginalGroupId());
		Assert.assertEquals(existingSocialActivityCounterModelImpl.getClassNameId(),
			existingSocialActivityCounterModelImpl.getOriginalClassNameId());
		Assert.assertEquals(existingSocialActivityCounterModelImpl.getClassPK(),
			existingSocialActivityCounterModelImpl.getOriginalClassPK());
		Assert.assertTrue(Validator.equals(
				existingSocialActivityCounterModelImpl.getName(),
				existingSocialActivityCounterModelImpl.getOriginalName()));
		Assert.assertEquals(existingSocialActivityCounterModelImpl.getOwnerType(),
			existingSocialActivityCounterModelImpl.getOriginalOwnerType());
		Assert.assertEquals(existingSocialActivityCounterModelImpl.getStartPeriod(),
			existingSocialActivityCounterModelImpl.getOriginalStartPeriod());

		Assert.assertEquals(existingSocialActivityCounterModelImpl.getGroupId(),
			existingSocialActivityCounterModelImpl.getOriginalGroupId());
		Assert.assertEquals(existingSocialActivityCounterModelImpl.getClassNameId(),
			existingSocialActivityCounterModelImpl.getOriginalClassNameId());
		Assert.assertEquals(existingSocialActivityCounterModelImpl.getClassPK(),
			existingSocialActivityCounterModelImpl.getOriginalClassPK());
		Assert.assertTrue(Validator.equals(
				existingSocialActivityCounterModelImpl.getName(),
				existingSocialActivityCounterModelImpl.getOriginalName()));
		Assert.assertEquals(existingSocialActivityCounterModelImpl.getOwnerType(),
			existingSocialActivityCounterModelImpl.getOriginalOwnerType());
		Assert.assertEquals(existingSocialActivityCounterModelImpl.getEndPeriod(),
			existingSocialActivityCounterModelImpl.getOriginalEndPeriod());
	}

	protected SocialActivityCounter addSocialActivityCounter()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		SocialActivityCounter socialActivityCounter = _persistence.create(pk);

		socialActivityCounter.setGroupId(RandomTestUtil.nextLong());

		socialActivityCounter.setCompanyId(RandomTestUtil.nextLong());

		socialActivityCounter.setClassNameId(RandomTestUtil.nextLong());

		socialActivityCounter.setClassPK(RandomTestUtil.nextLong());

		socialActivityCounter.setName(RandomTestUtil.randomString());

		socialActivityCounter.setOwnerType(RandomTestUtil.nextInt());

		socialActivityCounter.setCurrentValue(RandomTestUtil.nextInt());

		socialActivityCounter.setTotalValue(RandomTestUtil.nextInt());

		socialActivityCounter.setGraceValue(RandomTestUtil.nextInt());

		socialActivityCounter.setStartPeriod(RandomTestUtil.nextInt());

		socialActivityCounter.setEndPeriod(RandomTestUtil.nextInt());

		socialActivityCounter.setActive(RandomTestUtil.randomBoolean());

		_socialActivityCounters.add(_persistence.update(socialActivityCounter));

		return socialActivityCounter;
	}

	private static Log _log = LogFactoryUtil.getLog(SocialActivityCounterPersistenceTest.class);
	private List<SocialActivityCounter> _socialActivityCounters = new ArrayList<SocialActivityCounter>();
	private ModelListener<SocialActivityCounter>[] _modelListeners;
	private SocialActivityCounterPersistence _persistence = SocialActivityCounterUtil.getPersistence();
}