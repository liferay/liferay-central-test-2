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

import com.liferay.portlet.social.NoSuchActivitySettingException;
import com.liferay.portlet.social.model.SocialActivitySetting;
import com.liferay.portlet.social.model.impl.SocialActivitySettingModelImpl;
import com.liferay.portlet.social.service.SocialActivitySettingLocalServiceUtil;

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
public class SocialActivitySettingPersistenceTest {
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
		Iterator<SocialActivitySetting> iterator = _socialActivitySettings.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SocialActivitySetting socialActivitySetting = _persistence.create(pk);

		Assert.assertNotNull(socialActivitySetting);

		Assert.assertEquals(socialActivitySetting.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SocialActivitySetting newSocialActivitySetting = addSocialActivitySetting();

		_persistence.remove(newSocialActivitySetting);

		SocialActivitySetting existingSocialActivitySetting = _persistence.fetchByPrimaryKey(newSocialActivitySetting.getPrimaryKey());

		Assert.assertNull(existingSocialActivitySetting);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSocialActivitySetting();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SocialActivitySetting newSocialActivitySetting = _persistence.create(pk);

		newSocialActivitySetting.setGroupId(RandomTestUtil.nextLong());

		newSocialActivitySetting.setCompanyId(RandomTestUtil.nextLong());

		newSocialActivitySetting.setClassNameId(RandomTestUtil.nextLong());

		newSocialActivitySetting.setActivityType(RandomTestUtil.nextInt());

		newSocialActivitySetting.setName(RandomTestUtil.randomString());

		newSocialActivitySetting.setValue(RandomTestUtil.randomString());

		_socialActivitySettings.add(_persistence.update(
				newSocialActivitySetting));

		SocialActivitySetting existingSocialActivitySetting = _persistence.findByPrimaryKey(newSocialActivitySetting.getPrimaryKey());

		Assert.assertEquals(existingSocialActivitySetting.getActivitySettingId(),
			newSocialActivitySetting.getActivitySettingId());
		Assert.assertEquals(existingSocialActivitySetting.getGroupId(),
			newSocialActivitySetting.getGroupId());
		Assert.assertEquals(existingSocialActivitySetting.getCompanyId(),
			newSocialActivitySetting.getCompanyId());
		Assert.assertEquals(existingSocialActivitySetting.getClassNameId(),
			newSocialActivitySetting.getClassNameId());
		Assert.assertEquals(existingSocialActivitySetting.getActivityType(),
			newSocialActivitySetting.getActivityType());
		Assert.assertEquals(existingSocialActivitySetting.getName(),
			newSocialActivitySetting.getName());
		Assert.assertEquals(existingSocialActivitySetting.getValue(),
			newSocialActivitySetting.getValue());
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
	public void testCountByG_C() {
		try {
			_persistence.countByG_C(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByG_C(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_A() {
		try {
			_persistence.countByG_A(RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByG_A(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_C_A() {
		try {
			_persistence.countByG_C_A(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

			_persistence.countByG_C_A(0L, 0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_C_A_N() {
		try {
			_persistence.countByG_C_A_N(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextInt(),
				StringPool.BLANK);

			_persistence.countByG_C_A_N(0L, 0L, 0, StringPool.NULL);

			_persistence.countByG_C_A_N(0L, 0L, 0, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SocialActivitySetting newSocialActivitySetting = addSocialActivitySetting();

		SocialActivitySetting existingSocialActivitySetting = _persistence.findByPrimaryKey(newSocialActivitySetting.getPrimaryKey());

		Assert.assertEquals(existingSocialActivitySetting,
			newSocialActivitySetting);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchActivitySettingException");
		}
		catch (NoSuchActivitySettingException nsee) {
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

	protected OrderByComparator<SocialActivitySetting> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("SocialActivitySetting",
			"activitySettingId", true, "groupId", true, "companyId", true,
			"classNameId", true, "activityType", true, "name", true, "value",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SocialActivitySetting newSocialActivitySetting = addSocialActivitySetting();

		SocialActivitySetting existingSocialActivitySetting = _persistence.fetchByPrimaryKey(newSocialActivitySetting.getPrimaryKey());

		Assert.assertEquals(existingSocialActivitySetting,
			newSocialActivitySetting);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SocialActivitySetting missingSocialActivitySetting = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSocialActivitySetting);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		SocialActivitySetting newSocialActivitySetting1 = addSocialActivitySetting();
		SocialActivitySetting newSocialActivitySetting2 = addSocialActivitySetting();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSocialActivitySetting1.getPrimaryKey());
		primaryKeys.add(newSocialActivitySetting2.getPrimaryKey());

		Map<Serializable, SocialActivitySetting> socialActivitySettings = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, socialActivitySettings.size());
		Assert.assertEquals(newSocialActivitySetting1,
			socialActivitySettings.get(
				newSocialActivitySetting1.getPrimaryKey()));
		Assert.assertEquals(newSocialActivitySetting2,
			socialActivitySettings.get(
				newSocialActivitySetting2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, SocialActivitySetting> socialActivitySettings = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(socialActivitySettings.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		SocialActivitySetting newSocialActivitySetting = addSocialActivitySetting();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSocialActivitySetting.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, SocialActivitySetting> socialActivitySettings = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, socialActivitySettings.size());
		Assert.assertEquals(newSocialActivitySetting,
			socialActivitySettings.get(newSocialActivitySetting.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, SocialActivitySetting> socialActivitySettings = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(socialActivitySettings.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		SocialActivitySetting newSocialActivitySetting = addSocialActivitySetting();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSocialActivitySetting.getPrimaryKey());

		Map<Serializable, SocialActivitySetting> socialActivitySettings = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, socialActivitySettings.size());
		Assert.assertEquals(newSocialActivitySetting,
			socialActivitySettings.get(newSocialActivitySetting.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = SocialActivitySettingLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					SocialActivitySetting socialActivitySetting = (SocialActivitySetting)object;

					Assert.assertNotNull(socialActivitySetting);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SocialActivitySetting newSocialActivitySetting = addSocialActivitySetting();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivitySetting.class,
				SocialActivitySetting.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("activitySettingId",
				newSocialActivitySetting.getActivitySettingId()));

		List<SocialActivitySetting> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		SocialActivitySetting existingSocialActivitySetting = result.get(0);

		Assert.assertEquals(existingSocialActivitySetting,
			newSocialActivitySetting);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivitySetting.class,
				SocialActivitySetting.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("activitySettingId",
				RandomTestUtil.nextLong()));

		List<SocialActivitySetting> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SocialActivitySetting newSocialActivitySetting = addSocialActivitySetting();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivitySetting.class,
				SocialActivitySetting.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"activitySettingId"));

		Object newActivitySettingId = newSocialActivitySetting.getActivitySettingId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("activitySettingId",
				new Object[] { newActivitySettingId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingActivitySettingId = result.get(0);

		Assert.assertEquals(existingActivitySettingId, newActivitySettingId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivitySetting.class,
				SocialActivitySetting.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"activitySettingId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("activitySettingId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		SocialActivitySetting newSocialActivitySetting = addSocialActivitySetting();

		_persistence.clearCache();

		SocialActivitySettingModelImpl existingSocialActivitySettingModelImpl = (SocialActivitySettingModelImpl)_persistence.findByPrimaryKey(newSocialActivitySetting.getPrimaryKey());

		Assert.assertEquals(existingSocialActivitySettingModelImpl.getGroupId(),
			existingSocialActivitySettingModelImpl.getOriginalGroupId());
		Assert.assertEquals(existingSocialActivitySettingModelImpl.getClassNameId(),
			existingSocialActivitySettingModelImpl.getOriginalClassNameId());
		Assert.assertEquals(existingSocialActivitySettingModelImpl.getActivityType(),
			existingSocialActivitySettingModelImpl.getOriginalActivityType());
		Assert.assertTrue(Validator.equals(
				existingSocialActivitySettingModelImpl.getName(),
				existingSocialActivitySettingModelImpl.getOriginalName()));
	}

	protected SocialActivitySetting addSocialActivitySetting()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		SocialActivitySetting socialActivitySetting = _persistence.create(pk);

		socialActivitySetting.setGroupId(RandomTestUtil.nextLong());

		socialActivitySetting.setCompanyId(RandomTestUtil.nextLong());

		socialActivitySetting.setClassNameId(RandomTestUtil.nextLong());

		socialActivitySetting.setActivityType(RandomTestUtil.nextInt());

		socialActivitySetting.setName(RandomTestUtil.randomString());

		socialActivitySetting.setValue(RandomTestUtil.randomString());

		_socialActivitySettings.add(_persistence.update(socialActivitySetting));

		return socialActivitySetting;
	}

	private static Log _log = LogFactoryUtil.getLog(SocialActivitySettingPersistenceTest.class);
	private List<SocialActivitySetting> _socialActivitySettings = new ArrayList<SocialActivitySetting>();
	private ModelListener<SocialActivitySetting>[] _modelListeners;
	private SocialActivitySettingPersistence _persistence = SocialActivitySettingUtil.getPersistence();
}